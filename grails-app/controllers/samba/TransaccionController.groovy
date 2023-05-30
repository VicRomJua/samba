package samba

import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*

@Transactional(readOnly = true)
class TransaccionController {
    //Identificación de la sesion del usuario
    def springSecurityService;
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //Recuperación de la pagina en que se encuentra
        if (params.offset){
			
        }else if (session.getAttribute("transaccion_search_offset")){
			params.offset = session.getAttribute("transaccion_search_offset");
        } else {
			params.offset = 0;
        }
		//Recuperación de limite maximo de registros visibles
		if (params.search_rows) {
			if (params.search_rows != session.getAttribute("transaccion_search_rows")) params.offset = 0;
			params.max = params.search_rows.toInteger();
        } else if (session.getAttribute("transaccion_search_rows")){
			params.max = session.getAttribute("transaccion_search_rows").toInteger();
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
			if (params.search_word != session.getAttribute("transaccion_search_word")) params.offset = 0;
        } else if (session.getAttribute("transaccion_search_word")){
			params.search_word = session.getAttribute("transaccion_search_word");
        }
        
        def itemsList = Transaccion.createCriteria().list (params) {
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
        
        session.setAttribute("transaccion_search_rows", params.search_rows);
        session.setAttribute("transaccion_search_word", params.search_word);
        session.setAttribute("transaccion_search_offset", params.offset);
        
        respond itemsList, model:[transaccionCount: itemsList.totalCount]
    }

    def show(Transaccion transaccion) {
        respond transaccion
    }

    def create() {
        Transaccion transaccion = new Transaccion(params);
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////                                  MODIFICAR SI CONSIDERA                                        ////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			transaccion.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
        respond transaccion
    }

    @Transactional
    def save(Transaccion transaccion) {
        if (transaccion == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (transaccion.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond transaccion.errors, view:'create'
            return
        }
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////                                  MODIFICAR SI CONSIDERA                                        ////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Recupera el id del autor y lo asigna
		/*
		if (springSecurityService.principal)
			transaccion.id_Autor = UserLG.findById(springSecurityService.principal.id);
		*/
        
        transaccion.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'transaccion.label', default: 'Transaccion'), transaccion.id])
                redirect transaccion
            }
            '*' { respond transaccion, [status: CREATED] }
        }
    }

    def edit(Transaccion transaccion) {
        respond transaccion
    }

    @Transactional
    def update(Transaccion transaccion) {
        if (transaccion == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (transaccion.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond transaccion.errors, view:'edit'
            return
        }

        transaccion.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'transaccion.label', default: 'Transaccion'), transaccion.id])
                redirect transaccion
            }
            '*'{ respond transaccion, [status: OK] }
        }
    }

    @Transactional
    def delete(Transaccion transaccion) {

        if (transaccion == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        transaccion.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'transaccion.label', default: 'Transaccion'), transaccion.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
    
    @Transactional
    def remove(Transaccion transaccion) {

        if (transaccion == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        transaccion.delete flush:true

		flash.message = message(code: 'default.deleted.message', args: [message(code: 'transaccion.label', default: 'Transaccion'), transaccion.id])
		redirect action:"index", method:"GET"
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'transaccion.label', default: 'Transaccion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
