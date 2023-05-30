package samba

import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*
import org.hibernate.SessionFactory

@Transactional(readOnly = true)
class IngredienteController {
	SessionFactory sessionFactory;

    //Identificación de la sesion del usuario
    def springSecurityService;
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //Recuperación de la pagina en que se encuentra
        if (params.offset){
			
        }else if (session.getAttribute("ingrediente_search_offset")){
			params.offset = session.getAttribute("ingrediente_search_offset");
        } else {
			params.offset = 0;
        }
		//Recuperación de limite maximo de registros visibles
		if (params.search_rows) {
			if (params.search_rows != session.getAttribute("ingrediente_search_rows")) params.offset = 0;
			params.max = params.search_rows.toInteger();
        } else if (session.getAttribute("ingrediente_search_rows")){
			params.max = session.getAttribute("ingrediente_search_rows").toInteger();
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
			if (params.search_word != session.getAttribute("ingrediente_search_word")) params.offset = 0;
        } else if (session.getAttribute("ingrediente_search_word")){
			params.search_word = session.getAttribute("ingrediente_search_word");
        }
        
        def itemsList = Ingrediente.createCriteria().list (params) {
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
        
        session.setAttribute("ingrediente_search_rows", params.search_rows);
        session.setAttribute("ingrediente_search_word", params.search_word);
        session.setAttribute("ingrediente_search_offset", params.offset);
        
        respond itemsList, model:[ingredienteCount: itemsList.totalCount]
    }

    def show(Ingrediente ingrediente) {
        respond ingrediente
    }

    def create() {
        Ingrediente ingrediente = new Ingrediente(params);
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////                                  MODIFICAR SI CONSIDERA                                        ////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			ingrediente.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
        respond ingrediente
    }

    @Transactional
    def save(Ingrediente ingrediente) {
        if (ingrediente == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (ingrediente.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ingrediente.errors, view:'create'
            return
        }
		
		//Recuperando el archivo
        def file = request.getFile('tmp-archivo_Foto')

        if (!file.empty) {
			if (file.getInputStream() != null){
				def directory		= grailsApplication.config.uploadFolder + "mes-icons/";
				def fileName		= file.originalFilename;
				String extension	= fileName.substring(fileName.lastIndexOf("."));
				//def minutesName	= Math.round(System.currentTimeMillis()/1000/60);
				def minutesName		= System.currentTimeMillis();
				ingrediente.archivo_Foto = "Ingrediente_" + minutesName.toString() + extension ;

				file.transferTo(new File(directory+ingrediente.archivo_Foto))
			}
		}
		
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			ingrediente.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
        ingrediente.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'ingrediente.label', default: 'Ingrediente'), ingrediente.nombre])
                //redirect ingrediente
                redirect action:"index", method:"GET"
            }
            '*' { respond ingrediente, [status: CREATED] }
        }
    }

    def edit(Ingrediente ingrediente) {
        respond ingrediente
    }

    @Transactional
    def upchange(Ingrediente ingrediente) {
        if (ingrediente == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (ingrediente.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ingrediente.errors, view:'edit'
            return
        }
        
        //Recuperando el archivo
        def file = request.getFile('tmp-archivo_Foto')

        if (!file.empty) {
			if (file.getInputStream() != null){
				def directory		= grailsApplication.config.uploadFolder + "mes-icons/";
				def fileName		= file.originalFilename;
				String extension	= fileName.substring(fileName.lastIndexOf("."));
				//def minutesName	= Math.round(System.currentTimeMillis()/1000/60);
				def minutesName		= System.currentTimeMillis();
				ingrediente.archivo_Foto = "Ingrediente_" + minutesName.toString() + extension ;

				file.transferTo(new File(directory+ingrediente.archivo_Foto))
			}
		}

        ingrediente.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ingrediente.label', default: 'Ingrediente'), ingrediente.nombre])
                //redirect ingrediente
                redirect action:"index", method:"GET"
            }
            '*'{ respond ingrediente, [status: OK] }
        }
    }

    @Transactional
    def delete(Ingrediente ingrediente) {

        if (ingrediente == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
		String query = "SELECT * FROM platillos_ingredientes WHERE ingrediente_id='${ingrediente.id}'";
		def records = sessionFactory.getCurrentSession().createSQLQuery(query).list();
		if (records.size() > 0){
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'ingrediente.label', default: 'Ingrediente'), ingrediente.nombre, 'de Platillos'])
		}else{
			//Borrando archivo anexo 1
			def directory = grailsApplication.config.uploadFolder + "mes-icons/";
			def file_real = new File( directory + ingrediente.archivo_Foto );
			if( file_real.exists() ){
				file_real.delete()
			}
			
			ingrediente.delete flush:true
			
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'ingrediente.label', default: 'Ingrediente'), ingrediente.nombre])
		}
		
        request.withFormat {
            form multipartForm {        
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
    
    @Transactional
    def remove(Ingrediente ingrediente) {

        if (ingrediente == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
		
		String query = "SELECT * FROM platillos_ingredientes WHERE ingrediente_id='${ingrediente.id}'";
		def records = sessionFactory.getCurrentSession().createSQLQuery(query).list();
		if (records.size() > 0){
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'ingrediente.label', default: 'Ingrediente'), ingrediente.nombre, 'de Platillos'])
		}else{
			//Borrando archivo anexo 1
			def directory = grailsApplication.config.uploadFolder + "mes-icons/";
			def file_real = new File( directory + ingrediente.archivo_Foto );
			if( file_real.exists() ){
				file_real.delete()
			}
			
			ingrediente.delete flush:true
			
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'ingrediente.label', default: 'Ingrediente'), ingrediente.nombre])
		}
		redirect action:"index", method:"GET"
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'ingrediente.label', default: 'Ingrediente'), params.nombre])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
