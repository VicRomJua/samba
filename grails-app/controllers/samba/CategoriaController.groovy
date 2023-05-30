package samba

import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*

@Transactional(readOnly = true)
class CategoriaController {
    //Identificación de la sesion del usuario
    def springSecurityService;
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //Recuperación de la pagina en que se encuentra
        if (params.offset){
			
        }else if (session.getAttribute("categoria_search_offset")){
			params.offset = session.getAttribute("categoria_search_offset");
        } else {
			params.offset = 0;
        }
		//Recuperación de limite maximo de registros visibles
		if (params.search_rows) {
			if (params.search_rows != session.getAttribute("categoria_search_rows")) params.offset = 0;
			params.max = params.search_rows.toInteger();
        } else if (session.getAttribute("categoria_search_rows")){
			params.max = session.getAttribute("categoria_search_rows").toInteger();
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
			if (params.search_word != session.getAttribute("categoria_search_word")) params.offset = 0;
        } else if (session.getAttribute("categoria_search_word")){
			params.search_word = session.getAttribute("categoria_search_word");
        }
        
        def itemsList = Categoria.createCriteria().list (params) {
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
        
        session.setAttribute("categoria_search_rows", params.search_rows);
        session.setAttribute("categoria_search_word", params.search_word);
        session.setAttribute("categoria_search_offset", params.offset);
        
        respond itemsList, model:[categoriaCount: itemsList.totalCount]
    }

    def show(Categoria categoria) {
        respond categoria
    }

    def create() {
        Categoria categoria = new Categoria(params);
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////                                  MODIFICAR SI CONSIDERA                                        ////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			categoria.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
        respond categoria
    }

    @Transactional
    def save(Categoria categoria) {
        if (categoria == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (categoria.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond categoria.errors, view:'create'
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
				categoria.archivo_Foto = "Categoria_" + minutesName.toString() + extension ;
				
				def origen			= directory + categoria.archivo_Foto;
				def destino			= directory + "Categoria_" + minutesName.toString() + "_mini.jpg";
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
		
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			categoria.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
        categoria.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'categoria.label', default: 'Categoria'), categoria.nombre])
                //redirect categoria
                redirect action:"index", method:"GET"
            }
            '*' { respond categoria, [status: CREATED] }
        }
    }

    def edit(Categoria categoria) {
        respond categoria
    }

    @Transactional
    def upchange(Categoria categoria) {
        if (categoria == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (categoria.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond categoria.errors, view:'edit'
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
				categoria.archivo_Foto = "Categoria_" + minutesName.toString() + extension ;
				
				def origen			= directory + categoria.archivo_Foto;
				def destino			= directory + "Categoria_" + minutesName.toString() + "_mini.jpg";
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
		
        categoria.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'categoria.label', default: 'Categoria'), categoria.nombre])
                //redirect categoria
                redirect action:"index", method:"GET"
            }
            '*'{ respond categoria, [status: OK] }
        }
    }

    @Transactional
    def delete(Categoria categoria) {

        if (categoria == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        categoria.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'categoria.label', default: 'Categoria'), categoria.nombre])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
    
    @Transactional
    def remove(Categoria categoria) {

        if (categoria == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        categoria.delete flush:true

		flash.message = message(code: 'default.deleted.message', args: [message(code: 'categoria.label', default: 'Categoria'), categoria.nombre])
		redirect action:"index", method:"GET"
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'categoria.label', default: 'Categoria'), params.nombre])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
