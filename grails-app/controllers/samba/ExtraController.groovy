package samba

import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*

@Transactional(readOnly = true)
class ExtraController {
    //Identificación de la sesion del usuario
    def springSecurityService;
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //Recuperación de la pagina en que se encuentra
        if (params.offset){
			
        }else if (session.getAttribute("extra_search_offset")){
			params.offset = session.getAttribute("extra_search_offset");
        } else {
			params.offset = 0;
        }
		//Recuperación de limite maximo de registros visibles
		if (params.search_rows) {
			if (params.search_rows != session.getAttribute("extra_search_rows")) params.offset = 0;
			params.max = params.search_rows.toInteger();
        } else if (session.getAttribute("extra_search_rows")){
			params.max = session.getAttribute("extra_search_rows").toInteger();
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
			if (params.search_word != session.getAttribute("extra_search_word")) params.offset = 0;
        } else if (session.getAttribute("extra_search_word")){
			params.search_word = session.getAttribute("extra_search_word");
        }
        
        def itemsList = Extra.createCriteria().list (params) {
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
        
        session.setAttribute("extra_search_rows", params.search_rows);
        session.setAttribute("extra_search_word", params.search_word);
        session.setAttribute("extra_search_offset", params.offset);
        
        respond itemsList, model:[extraCount: itemsList.totalCount]
    }

    def show(Extra extra) {
        respond extra
    }

    def create() {
        Extra extra = new Extra(params);
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////                                  MODIFICAR SI CONSIDERA                                        ////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			extra.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
        respond extra
    }

    @Transactional
    def save(Extra extra) {
        if (extra == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (extra.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond extra.errors, view:'create'
            return
        }
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////                                  MODIFICAR SI CONSIDERA                                        ////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			extra.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
		//Recuperando el archivo
        def file = request.getFile('tmp-archivo_Foto')

        if (!file.empty) {
			if (file.getInputStream() != null){
				def directory		= grailsApplication.config.uploadFolder + "mes-icons/";
				def fileName		= file.originalFilename;
				String extension	= fileName.substring(fileName.lastIndexOf("."));
				//def minutesName	= Math.round(System.currentTimeMillis()/1000/60);
				def minutesName		= System.currentTimeMillis();
				extra.archivo_Foto  = "Extra_" + minutesName.toString() + extension ;

				def origen			= directory + extra.archivo_Foto;
				def destino			= directory + "Extra_" + minutesName.toString() + "_mini.jpg";
				file.transferTo(new File(origen))
				
				//Activar conversión de archivo de imagen del producto considerando:
				//resolución 800 pixeles de ancho
				def command = "/usr/bin/convert " + (origen) + " -resize 800>> -quality 80 " + (origen);
				//println command;
				
				Runtime rt = Runtime.getRuntime();
				Object runobject = rt.exec(command);
				int i = runobject.waitFor();
			}
		}
		
        extra.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'extra.label', default: 'Extra'), extra.nombre])
                redirect extra
            }
            '*' { respond extra, [status: CREATED] }
        }
    }

    def edit(Extra extra) {
        respond extra
    }

    @Transactional
    def upchange(Extra extra) {
        if (extra == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (extra.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond extra.errors, view:'edit'
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
				extra.archivo_Foto  = "Extra_" + minutesName.toString() + extension ;

				def origen			= directory + extra.archivo_Foto;
				def destino			= directory + "Extra_" + minutesName.toString() + "_mini.jpg";
				file.transferTo(new File(origen))
				
				//Activar conversión de archivo de imagen del producto considerando:
				//resolución 800 pixeles de ancho
				def command = "/usr/bin/convert " + (origen) + " -resize 800>> -quality 80 " + (origen);
				//println command;
				
				Runtime rt = Runtime.getRuntime();
				Object runobject = rt.exec(command);
				int i = runobject.waitFor();
			}
		}

        extra.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'extra.label', default: 'Extra'), extra.nombre])
                redirect extra
            }
            '*'{ respond extra, [status: OK] }
        }
    }

    @Transactional
    def delete(Extra extra) {

        if (extra == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        
        //Borrando archivo anexo 1
		def directory = grailsApplication.config.uploadFolder + "mes-icons/";
		def file_real = new File( directory + extra.archivo_Foto );
		if( file_real.exists() ){
			file_real.delete()
		}

        extra.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'extra.label', default: 'Extra'), extra.nombre])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
    
    @Transactional
    def remove(Extra extra) {

        if (extra == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
		
		//Borrando archivo anexo 1
		def directory = grailsApplication.config.uploadFolder + "mes-icons/";
		def file_real = new File( directory + extra.archivo_Foto );
		if( file_real.exists() ){
			file_real.delete()
		}
		
        extra.delete flush:true

		flash.message = message(code: 'default.deleted.message', args: [message(code: 'extra.label', default: 'Extra'), extra.nombre])
		redirect action:"index", method:"GET"
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'extra.label', default: 'Extra'), params.nombre])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
