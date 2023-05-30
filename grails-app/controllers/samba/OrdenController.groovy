package samba

import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*

@Transactional(readOnly = true)
class OrdenController {
    //Identificación de la sesion del usuario
    def springSecurityService;
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //Recuperación de la pagina en que se encuentra
        if (params.offset){
			
        }else if (session.getAttribute("orden_search_offset")){
			params.offset = session.getAttribute("orden_search_offset");
        } else {
			params.offset = 0;
        }
		//Recuperación de limite maximo de registros visibles
		if (params.search_rows) {
			if (params.search_rows != session.getAttribute("orden_search_rows")) params.offset = 0;
			params.max = params.search_rows.toInteger();
        } else if (session.getAttribute("orden_search_rows")){
			params.max = session.getAttribute("orden_search_rows").toInteger();
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
			if (params.search_word != session.getAttribute("orden_search_word")) params.offset = 0;
        } else if (session.getAttribute("orden_search_word")){
			params.search_word = session.getAttribute("orden_search_word");
        }
        
        def itemsList = Orden.createCriteria().list (params) {
			/*
            if ( params.search_word ) {
				or {
					ilike("nombre_1", "%${params.search_word}%")
					ilike("nombre_2", "%${params.search_word}%")
				}
            }
            */
            if ( params.search_word ) {
				ilike("noOrden", "%${params.search_word}%")
            }
            if ( !params.sort ){
				order("noOrden","asc");
            }
            
        }
        
        session.setAttribute("orden_search_rows", params.search_rows);
        session.setAttribute("orden_search_word", params.search_word);
        session.setAttribute("orden_search_offset", params.offset);
        
        respond itemsList, model:[ordenCount: itemsList.totalCount]
    }

    def show(Orden orden) {
        respond orden
    }

    def create() {
        Orden orden = new Orden(params);
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////                                  MODIFICAR SI CONSIDERA                                        ////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			orden.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
        respond orden
    }
	
    @Transactional
    def save(Orden orden) {
        if (orden == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (orden.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond orden.errors, view:'create'
            return
        }
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////                                  MODIFICAR SI CONSIDERA                                        ////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			orden.id_Autor = UserLG.findById(springSecurityService.principal.id);
        
        orden.save flush:true
		
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'orden.label', default: 'Orden'), orden.noOrden])
                //redirect orden
                redirect action:"index", method:"GET"
            }
            '*' { respond orden, [status: CREATED] }
        }
    }

    def edit(Orden orden) {
        respond orden
    }

    @Transactional
    def update(Orden orden) {
        if (orden == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (orden.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond orden.errors, view:'edit'
            return
        }

        orden.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'orden.label', default: 'Orden'), orden.noOrden])
                //redirect orden
                redirect action:"index", method:"GET"
            }
            '*'{ respond orden, [status: OK] }
        }
    }

    @Transactional
    def delete(Orden orden) {

        if (orden == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        orden.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'orden.label', default: 'Orden'), orden.noOrden])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
    
    @Transactional
    def remove(Orden orden) {

        if (orden == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        orden.delete flush:true

		flash.message = message(code: 'default.deleted.message', args: [message(code: 'orden.label', default: 'Orden'), orden.noOrden])
		redirect action:"index", method:"GET"
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'orden.label', default: 'Orden'), params.noOrden])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
