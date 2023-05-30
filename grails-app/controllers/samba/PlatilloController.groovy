package samba

import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*
import org.hibernate.SessionFactory;

@Transactional(readOnly = true)
class PlatilloController {

	SessionFactory sessionFactory;
	
    //Identificación de la sesion del usuario
    def springSecurityService;
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //Recuperación de la pagina en que se encuentra
        if (params.offset){
			
        }else if (session.getAttribute("platillo_search_offset")){
			params.offset = session.getAttribute("platillo_search_offset");
        } else {
			params.offset = 0;
        }
		//Recuperación de limite maximo de registros visibles
		if (params.search_rows) {
			if (params.search_rows != session.getAttribute("platillo_search_rows")) params.offset = 0;
			params.max = params.search_rows.toInteger();
        } else if (session.getAttribute("platillo_search_rows")){
			params.max = session.getAttribute("platillo_search_rows").toInteger();
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
			if (params.search_word != session.getAttribute("platillo_search_word")) params.offset = 0;
        } else if (session.getAttribute("platillo_search_word")){
			params.search_word = session.getAttribute("platillo_search_word");
        }
        
        def itemsList = Platillo.createCriteria().list (params) {
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
        
        session.setAttribute("platillo_search_rows", params.search_rows);
        session.setAttribute("platillo_search_word", params.search_word);
        session.setAttribute("platillo_search_offset", params.offset);
        
        respond itemsList, model:[platilloCount: itemsList.totalCount]
    }

    def show(Platillo platillo) {
        respond platillo
    }

    def create() {
        Platillo platillo = new Platillo(params);
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////                                  MODIFICAR SI CONSIDERA                                        ////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			platillo.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
        respond platillo, model:[ingredientesList:Ingrediente.list(), categoriasList:Categoria.list()]
    }

    @Transactional
    def save(Platillo platillo) {
        if (platillo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
		
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			platillo.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
        if (platillo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond platillo.errors, view:'create', model:[ingredientesList:Ingrediente.list(), categoriasList:Categoria.list()]
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
				platillo.archivo_Foto = "Platillo_" + minutesName.toString() + extension;
				
				def origen			= directory + platillo.archivo_Foto;
				def destino			= directory + "Platillo_" + minutesName.toString() + "_mini.jpg";
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
			platillo.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
        platillo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'platillo.label', default: 'Platillo'), platillo.nombre])
                //redirect platillo
                redirect action:"index", method:"GET"
            }
            '*' { respond platillo, [status: CREATED] }
        }
    }

    def edit(Platillo platillo) {
        respond platillo, model:[ingredientesList:Ingrediente.list(), categoriasList:Categoria.list()]
    }

    @Transactional
    def upchange(Platillo platillo) {
        if (platillo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
		
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			platillo.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
        if (platillo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond platillo.errors, view:'edit', model:[ingredientesList:Ingrediente.list(), categoriasList:Categoria.list()]
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
				platillo.archivo_Foto = "Platillo_" + minutesName.toString() + extension ;
				
				def origen			= directory + platillo.archivo_Foto;
				def destino			= directory + "Platillo_" + minutesName.toString() + "_mini.jpg";
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
		
		//Borrar los ingredientes no existentes en el platillo
		if (platillo.ingredientes != null){
			String ingredientes = platillo.ingredientes.id.toString().replace("[","'").replace("]","'").replace(", ","','");
			String query = "DELETE FROM platillos_ingredientes WHERE platillo_ingredientes_id='${platillo.id}' AND ingrediente_id NOT IN (${ingredientes})";
			Integer rowsEffected = sessionFactory.getCurrentSession().createSQLQuery(query).executeUpdate();
		}
		
		//Borrar las categorias no existentes en el platillo
		if (platillo.categorias != null){
			String categorias = platillo.categorias.id.toString().replace("[","'").replace("]","'").replace(", ","','");
			String query = "DELETE FROM platillos_categorias WHERE platillo_categorias_id='${platillo.id}' AND categoria_id NOT IN (${categorias})";
			Integer rowsEffected = sessionFactory.getCurrentSession().createSQLQuery(query).executeUpdate();
		}
		
        platillo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'platillo.label', default: 'Platillo'), platillo.nombre])
                //redirect platillo
                redirect action:"index", method:"GET"
            }
            '*'{ respond platillo, [status: OK] }
        }
    }
	
	@Transactional
    def active(Platillo platillo) {
		platillo.activo = !platillo.activo
		platillo.save flush:true
		flash.message = message(code: 'default.reactive.message', args: [message(code: 'platillo.label', default: 'Platillo'), platillo.nombre, (platillo.activo?'activado':'desactivado')])
		redirect action:"index", method:"GET"
    }
	
    @Transactional
    def delete(Platillo platillo) {

        if (platillo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
		
		String query = "SELECT * FROM platillosestablecimientos WHERE platillo_id='${platillo.id}'";
		def records = sessionFactory.getCurrentSession().createSQLQuery(query).list();
		if (records.size() > 0){
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'platillo.label', default: 'Platillo'), platillo.nombre, 'de Menús'])
		}else{
			
			//Borrando archivo anexo 1
			def directory = grailsApplication.config.uploadFolder + "mes-icons/";
			def file_real = new File( directory + platillo.archivo_Foto );
			if( file_real.exists() ){
				file_real.delete()
			}
			
			platillo.delete flush:true
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'platillo.label', default: 'Platillo'), platillo.nombre])
		}
		
        request.withFormat {
            form multipartForm {
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
    
    @Transactional
    def remove(Platillo platillo) {

        if (platillo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
		String query = "SELECT * FROM platillosestablecimientos WHERE platillo_id='${platillo.id}'";
		def records = sessionFactory.getCurrentSession().createSQLQuery(query).list();
		if (records.size() > 0){
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'platillo.label', default: 'Platillo'), platillo.nombre, 'de Menús'])
		}else{
			//Borrando archivo anexo 1
			def directory = grailsApplication.config.uploadFolder + "mes-icons/";
			def file_real = new File( directory + platillo.archivo_Foto );
			if( file_real.exists() ){
				file_real.delete()
			}
			
			platillo.delete flush:true
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'platillo.label', default: 'Platillo'), platillo.nombre])
		}
		redirect action:"index", method:"GET"
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'platillo.label', default: 'Platillo'), params.nombre])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
