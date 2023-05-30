package samba

import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*

@Transactional(readOnly = true)
class EmpresaController {
    //Identificación de la sesion del usuario
    def springSecurityService;
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //Recuperación de la pagina en que se encuentra
        if (params.offset){
			
        }else if (session.getAttribute("empresa_search_offset")){
			params.offset = session.getAttribute("empresa_search_offset");
        } else {
			params.offset = 0;
        }
		//Recuperación de limite maximo de registros visibles
		if (params.search_rows) {
			if (params.search_rows != session.getAttribute("empresa_search_rows")) params.offset = 0;
			params.max = params.search_rows.toInteger();
        } else if (session.getAttribute("empresa_search_rows")){
			params.max = session.getAttribute("empresa_search_rows").toInteger();
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
			if (params.search_word != session.getAttribute("empresa_search_word")) params.offset = 0;
        } else if (session.getAttribute("empresa_search_word")){
			params.search_word = session.getAttribute("empresa_search_word");
        }
        
        def itemsList = Empresa.createCriteria().list (params) {
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
            
            if ( session.getAttribute("username_establecimiento_id") != "" ) {
				eq("establecimiento.id", session.getAttribute("username_establecimiento_id"))
            }
        }
        
        session.setAttribute("empresa_search_rows", params.search_rows);
        session.setAttribute("empresa_search_word", params.search_word);
        session.setAttribute("empresa_search_offset", params.offset);
        
        respond itemsList, model:[empresaCount: itemsList.totalCount]
    }

    def show(Empresa empresa) {
        respond empresa
    }

    def create() {
        Empresa empresa = new Empresa(params);
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////                                  MODIFICAR SI CONSIDERA                                        ////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			empresa.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
		if (session.getAttribute('username_establecimiento_id'))
			empresa.establecimiento = Establecimiento.findById(session.getAttribute('username_establecimiento_id'));
		
        respond empresa
    }

    @Transactional
    def save(Empresa empresa) {
		
		UserLG usuario = UserLG.find("FROM  UserLG AS u WHERE u.username='" + empresa.contactoEmail + "'");
		if (usuario != null) {
			empresa.errors.rejectValue('contactoEmail', empresa.contactoEmail, 'El correo de contacto que ha indicado ya fue previamente registrado');
		}
		
		//Determinar si tiene un horario
		if (empresa.horaEntrega_1 == null && empresa.horaEntrega_2 == null && empresa.horaEntrega_3 == null){
			empresa.errors.rejectValue('horaEntrega_1', empresa.horaEntrega_1, 'Asigne por lo menos un horario de entregas a la empresa');
		}
		
        if (empresa == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (empresa.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond empresa.errors, view:'create'
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
				empresa.archivo_Foto = "Empresa_" + minutesName.toString() + extension ;

				file.transferTo(new File(directory+empresa.archivo_Foto))
			}
		}
		
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			empresa.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
        empresa.save flush:true
		
		//Guarda usuario para la empresa creada		
		def user = new UserLG();
		user.empresa = empresa;
		user.username = empresa.contactoEmail;
		user.telefono_Movil = empresa.contactoTelefono;
		user.password = empresa.rfc;
		user.session_id = empresa.rfc;
		user.nombre = empresa.nombre;
		user.rfc = empresa.rfc;
		user.save flush:true
		
		//Guarda rol para el usuario creado	
		def rol = RoleLG.findByAuthority("ROLE_EMPRESA");
		
		def role = new UserLGRoleLG
		(
			userLG: user,
			roleLG: rol
		)
		role.save flush:true
		
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.createda.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresa.nombre]);
                flash.warning =  message(code: 'default.created_user.message', args: [user.username,empresa.rfc]);
                //redirect empresa
                redirect action:"index", method:"GET"
            }
            '*' { respond empresa, [status: CREATED] }
        }
    }

    def edit(Empresa empresa) {
        respond empresa
    }

    @Transactional
    def upchange(Empresa empresa) {
		
		//Determinar si tiene un horario
		if (empresa.horaEntrega_1 == null && empresa.horaEntrega_2 == null && empresa.horaEntrega_3 == null){
			empresa.errors.rejectValue('horaEntrega_1', empresa.horaEntrega_1, 'Asigne por lo menos un horario de entregas a la empresa');
		}
		
        if (empresa == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (empresa.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond empresa.errors, view:'edit'
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
				empresa.archivo_Foto = "Empresa_" + minutesName.toString() + extension ;

				file.transferTo(new File(directory+empresa.archivo_Foto))
			}
		}
		
        empresa.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresa.nombre])
                //redirect empresa
                redirect action:"index", method:"GET"
            }
            '*'{ respond empresa, [status: OK] }
        }
    }
	
	@Transactional
    def active(Empresa empresa) {
		empresa.activo = !empresa.activo
		empresa.save flush:true
		
		if (empresa.activo){
			UserLG.findAll("from UserLG as items where items.empresa.id = '"+empresa.id+"'").each {
				it.enabled = true
				it.save flush:true
			}
		}else{
			UserLG.findAll("from UserLG as items where items.empresa.id = '"+empresa.id+"'").each {
				it.enabled = false
				it.save flush:true
			}
		}
		
		flash.message = message(code: 'default.reactive.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresa.nombre, (empresa.activo?'activado':'desactivado')])
        redirect action:"index", method:"GET"
    }
	
    @Transactional
    def delete(Empresa empresa) {

        if (empresa == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
		
		if (UserLG.findAll("from UserLG as items where items.empresa.id = '"+empresa.id+"'").toList().size() > 0 ||
			Embarque.findAll("from Embarque as items where items.empresa.id = '"+empresa.id+"'").toList().size() > 0){
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresa.nombre, 'de Embarques o Usuarios'])
		}else{
			//Borrando archivo anexo 1
			def directory = grailsApplication.config.uploadFolder + "mes-icons/";
			def file_real = new File( directory + empresa.archivo_Foto );
			if( file_real.exists() ){
				file_real.delete()
			}
			
			empresa.delete flush:true
			
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresa.nombre])
		}
        request.withFormat {
            form multipartForm {
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
    
    @Transactional
    def remove(Empresa empresa) {

        if (empresa == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        
        if (UserLG.findAll("from UserLG as items where items.empresa.id = '"+empresa.id+"'").toList().size() > 0 ||
			Embarque.findAll("from Embarque as items where items.empresa.id = '"+empresa.id+"'").toList().size() > 0){
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresa.nombre, 'de Embarques o Usuarios'])
		}else{
			//Borrando archivo anexo 1
			def directory = grailsApplication.config.uploadFolder + "mes-icons/";
			def file_real = new File( directory + empresa.archivo_Foto );
			if( file_real.exists() ){
				file_real.delete()
			}

			empresa.delete flush:true
			
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresa.nombre])
		}
		
		redirect action:"index", method:"GET"
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), params.nombre])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
