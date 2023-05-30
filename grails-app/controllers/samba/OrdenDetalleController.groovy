package samba

import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*

@Transactional(readOnly = true)
class OrdenDetalleController {
    //Identificación de la sesion del usuario
    def springSecurityService;
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //Recuperación de la pagina en que se encuentra
        if (params.offset){
			
        }else if (session.getAttribute("ordenDetalle_search_offset")){
			params.offset = session.getAttribute("ordenDetalle_search_offset");
        } else {
			params.offset = 0;
        }
		//Recuperación de limite maximo de registros visibles
		if (params.search_rows) {
			if (params.search_rows != session.getAttribute("ordenDetalle_search_rows")) params.offset = 0;
			params.max = params.search_rows.toInteger();
        } else if (session.getAttribute("ordenDetalle_search_rows")){
			params.max = session.getAttribute("ordenDetalle_search_rows").toInteger();
        } else {
			params.max = 10;
        }
        params.search_rows = params.max;
        //Recuperando la palabra de busqueda
        if (params.search_clean == "1") {
			params.search_word = "";
			params.offset = 0;
		} else if (params.search_word){
			//Enviada la palabra a buscar
			if (params.search_word != session.getAttribute("ordenDetalle_search_word")) params.offset = 0;
        } else if (session.getAttribute("ordenDetalle_search_word")){
			params.search_word = session.getAttribute("ordenDetalle_search_word");
        }
        
        def itemsList = OrdenDetalle.createCriteria().list (params) {
			/*
            if ( params.search_word ) {
				or {
					ilike("nombre_1", "%${params.search_word}%")
					ilike("nombre_2", "%${params.search_word}%")
				}
            }
            */
            if ( params.search_word ) {
				ilike("nombre", "%${params.search_word}%")
            }
        }
        
        session.setAttribute("ordenDetalle_search_rows", params.search_rows);
        session.setAttribute("ordenDetalle_search_word", params.search_word);
        session.setAttribute("ordenDetalle_search_offset", params.offset);
        
        respond itemsList, model:[ordenDetalleCount: itemsList.totalCount]
    }

    def show(OrdenDetalle ordenDetalle) {
        respond ordenDetalle
    }

    def create() {
        OrdenDetalle ordenDetalle = new OrdenDetalle(params);
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////                                  MODIFICAR SI CONSIDERA                                        ////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			ordenDetalle.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
        respond ordenDetalle
    }
    
	@Transactional
    def next(OrdenDetalle ordenDetalle) {
		if (ordenDetalle.estatus.equals("Nuevo")){
			ordenDetalle.estatus = "Cocinando";
			ordenDetalle.save flush:true
			//Se inicia el cocinado del embarque
			Embarque embarque = Embarque.findById(ordenDetalle.embarque.id);
			embarque.estatus = "Cocinando";
			embarque.save flush:true;
			//Se inicia el cocinado de la orden
			Orden orden = Orden.findById(ordenDetalle.orden.id);
			if (orden.estatus.equals("Nuevo")) {
				orden.estatus = "Cocinando";
				orden.save flush:true;
			}
		}else if (ordenDetalle.estatus.equals("Cocinando")){
			ordenDetalle.estatus = "Preparado";
			ordenDetalle.save flush:true
			//Se inicia el cocinado de la orden
			Orden orden = Orden.findById(ordenDetalle.orden.id);
			boolean OK = true;
			//Revisar que todas las OrdenesDetalle esten Preparadas!!
			def ordenList = OrdenDetalle.createCriteria().list () {
				eq("orden.id", orden.id)        
			}
			if (ordenList != null){
				for(item in ordenList){
					if (item.estatus.equals("Nuevo") || item.estatus.equals("Cocinando")){
						OK = false;
					}
				}
			}
			if (OK){
				orden.estatus = "Preparado";
				orden.save flush:true;
			}
		}
		
        flash.message = message(code: 'default.updated.message', args: [message(code: 'ordenDetalle.label', default: 'Orden'), ordenDetalle.orden.noOrden])
        redirect controller:"embarque", action: "show", id:ordenDetalle.embarque.id
	}
	
    @Transactional
    def save(OrdenDetalle ordenDetalle) {
        if (ordenDetalle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (ordenDetalle.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ordenDetalle.errors, view:'create'
            return
        }
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////                                  MODIFICAR SI CONSIDERA                                        ////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			ordenDetalle.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
        ordenDetalle.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'ordenDetalle.label', default: 'OrdenDetalle'), ordenDetalle.id])
                redirect ordenDetalle
            }
            '*' { respond ordenDetalle, [status: CREATED] }
        }
    }

    def edit(OrdenDetalle ordenDetalle) {
        respond ordenDetalle
    }

    @Transactional
    def update(OrdenDetalle ordenDetalle) {
        if (ordenDetalle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (ordenDetalle.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ordenDetalle.errors, view:'edit'
            return
        }

        ordenDetalle.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ordenDetalle.label', default: 'OrdenDetalle'), ordenDetalle.id])
                redirect ordenDetalle
            }
            '*'{ respond ordenDetalle, [status: OK] }
        }
    }

    @Transactional
    def delete(OrdenDetalle ordenDetalle) {

        if (ordenDetalle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        ordenDetalle.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ordenDetalle.label', default: 'OrdenDetalle'), ordenDetalle.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
    
    @Transactional
    def remove(OrdenDetalle ordenDetalle) {

        if (ordenDetalle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        ordenDetalle.delete flush:true

		flash.message = message(code: 'default.deleted.message', args: [message(code: 'ordenDetalle.label', default: 'OrdenDetalle'), ordenDetalle.id])
		redirect action:"index", method:"GET"
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'ordenDetalle.label', default: 'OrdenDetalle'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
