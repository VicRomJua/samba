package samba

import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Transactional(readOnly = true)
class EmbarqueController {
    //Identificación de la sesion del usuario
    def springSecurityService;
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def changeTab(Integer option){
		session.setAttribute("embarque_tab", option);
	}
	
    def index(Integer max) {
        //Recuperación de la pagina en que se encuentra
        if (params.offset){
			
        }else if (session.getAttribute("embarque_search_offset")){
			params.offset = session.getAttribute("embarque_search_offset");
        } else {
			params.offset = 0;
        }
		//Recuperación de limite maximo de registros visibles
		/*
		if (params.search_rows) {
			if (params.search_rows != session.getAttribute("embarque_search_rows")) params.offset = 0;
			params.max = params.search_rows.toInteger();
        } else if (session.getAttribute("embarque_search_rows")){
			params.max = session.getAttribute("embarque_search_rows").toInteger();
        } else {
			params.max = 10;
        }
		*/
		//Recuperación de filtro
		if (params.filter_estatus) {
			if (params.filter_estatus != session.getAttribute("search_filter_estatus")) params.offset = 0;
        } else if (session.getAttribute("search_filter_estatus")){
			params.filter_estatus = session.getAttribute("search_filter_estatus");
        }
        
		if (params.today) {
		
		} else if (session.getAttribute("embarque_today")){
			params.today = session.getAttribute("embarque_today");
		}else{
			params.today = new Date().format('dd/MM/YYYY').toString();
		}
		params.max = 4;
		
        params.search_rows = params.max;
        //Recuperando la palabra de busqueda
        if (params.search_clean == "1") {
			params.search_word = "";
			params.offset = 0;
		} else if (params.search_word){
			//Enviada la palabra a buscar
			if (params.search_word != session.getAttribute("embarque_search_word")) params.offset = 0;
        } else if (session.getAttribute("embarque_search_word")){
			params.search_word = session.getAttribute("embarque_search_word");
        }
        
		
        def itemsList = Embarque.createCriteria().list (params) {
			/*
            if ( params.search_word ) {
				or {
					ilike("nombre_1", "%${params.search_word}%")
					ilike("nombre_2", "%${params.search_word}%")
				}
            }
			*/
			sqlRestriction "to_char(fecha_hora_entrega,'dd/MM/YYYY') LIKE '%${params.today}%'"
			
			if ( session.getAttribute("username_establecimiento_id") != "" ) {
				sqlRestriction "establecimiento_id = '"+session.getAttribute('username_establecimiento_id')+"'"
			}
			
			if (params.filter_estatus && params.filter_estatus != "ALL") {
                eq("estatus", params.filter_estatus)
            }
			
			//sqlRestriction "fecha_hora_entrega between to_date ('${params.today} 00:00:00', 'YYYY-MM-dd HH24:MI:SS') and to_date ('${params.today} 23:59:00', 'YYYY-MM-dd HH24:MI:SS')"
            if ( params.search_word ) {
				ilike("empresa", "%${params.search_word}%")
            }
			order("fechaHoraEntrega", "asc")
        }
		
		session.setAttribute("embarque_today", params.today);
        session.setAttribute("embarque_search_rows", params.search_rows);
        session.setAttribute("embarque_search_word", params.search_word);
        session.setAttribute("embarque_search_offset", params.offset);
        session.setAttribute("search_filter_estatus", params.filter_estatus);
        
        respond itemsList, model:[embarqueCount: itemsList.totalCount, estatus: ["Nuevo", "Cocinando", "Preparado" ,"En tr\u00e1nsito", "Retrasado", "Cancelado", "Entregado"]]
    }
    
    def show(Embarque embarque,Integer max,Integer offset) {
		if (!session.getAttribute("embarque_tab")){
			session.setAttribute("embarque_tab", 1);
		}
		
		if (session.getAttribute("embarque_tab") == 2){
			if (params.offset){
				session.setAttribute("embarque_search_offset", offset);
			}else{
				if (!session.getAttribute("embarque_search_offset")){
					session.setAttribute("embarque_search_offset", 0);
				}
				params.offset=session.getAttribute("embarque_search_offset");
				params.max=10;
			}
		} 
		
		def orderField = "id_Autor"
		if (params.sort) {
			orderField = params.sort
		}
		def orderMethod = "asc"
		if (params.order) {
			orderMethod = params.order
		}
		
		def ordenList = OrdenDetalle.createCriteria().list () {
            eq("embarque.id", embarque.id)
            order ("orden.id", "asc") // IGB
            order ("id", "asc") // IGB            
        }
		
		def totalPlatillo = ordenList.groupBy({ ordendetalle -> ordendetalle.platillo.id }).collectEntries { [(it.key): it.value.sum {it.cantidad}] };
		def resumenList = [] as Set;
		for(item in totalPlatillo)
		{
			for(item2 in ordenList){
				if(item.key == item2.platillo.id){
				    resumenList.add([cantidad: item.value, producto: item2.platillo.platillo.nombre, personalizado: item2.esPersonalizado])	
				    resumenList.unique{p1,p2-> p1.producto<=>p2.producto}
				}
			}
		}
		
        respond embarque, model:[ordenCount: ordenList.size(), orden: ordenList, concentradoPlatillo: resumenList]
    }
    
	@Transactional
    def finish(Embarque embarque) {
		boolean OK = true;
		//Revisar que todas las OrdenesDetalle esten Preparadas!!
		def ordenList = OrdenDetalle.createCriteria().list () {
			eq("embarque.id", embarque.id)
			order ("orden.id", "asc") // IGB
			order ("id", "asc") // IGB            
		}
        if (ordenList != null){
			for(item in ordenList){
				if (!item.estatus.equals("Preparado")){
					OK = false;
				}
			}
		}
		if (OK){
			embarque.estatus = "Preparado";
			embarque.save flush:true
			
			flash.message = message(code: 'default.finished.message', args: [message(code: 'embarque.label', default: 'Embarque'), embarque.codigo])
			redirect controller:"embarque", action: "index", method:"GET"
        }else{
			flash.message = message(code: 'default.not_finished.message', args: [message(code: 'embarque.label', default: 'Embarque'), embarque.codigo])
			redirect controller:"embarque", action: "show", id: embarque.id
        }
    }
    
    def create() {
        Embarque embarque = new Embarque(params);
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////                                  MODIFICAR SI CONSIDERA                                        ////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			embarque.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
		respond embarque
    }

    @Transactional
    def save(Embarque embarque) {
        if (embarque == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        } 

        if (embarque.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond embarque.errors, view:'create'
            return
        }
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////                                  MODIFICAR SI CONSIDERA                                        ////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// Recupera el id del autor y lo asigna		
		if (springSecurityService.principal)
			embarque.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		DateFormat hourFormat = new SimpleDateFormat("HHmmss");
        String codigo = dateFormat.format(embarque.fechaHoraEntrega) + "SMCV01" + hourFormat.format(date);
		embarque.codigo = codigo;
		
		embarque.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'embarque.label', default: 'Embarque'), embarque.codigo])
                //redirect embarque
				redirect action:"index", method:"GET"
            }
            '*' { respond embarque, [status: CREATED] }
        }
    }
	
	def procesarEmbarques() {
        EmbarqueJob.triggerNow()
        render "OK"
    }
	
    def edit(Embarque embarque) {
        respond embarque
    }

    @Transactional
    def update(Embarque embarque) {
        if (embarque == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (embarque.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond embarque.errors, view:'edit'
            return
        }

        embarque.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'embarque.label', default: 'Embarque'), embarque.codigo])
                redirect embarque
            }
            '*'{ respond embarque, [status: OK] }
        }
    }

    @Transactional
    def delete(Embarque embarque) {

        if (embarque == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        embarque.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'embarque.label', default: 'Embarque'), embarque.codigo])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
    
    @Transactional
    def remove(Embarque embarque) {

        if (embarque == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        embarque.delete flush:true

		flash.message = message(code: 'default.deleted.message', args: [message(code: 'embarque.label', default: 'Embarque'), embarque.codigo])
		redirect action:"index", method:"GET"
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'embarque.label', default: 'Embarque'), params.codigo])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
