package samba

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserLGController {
	//Identificación de la sesion del usuario
    def springSecurityService;
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    
	def index(Integer max) {
		//Recuperación de la pagina en que se encuentra
		if (params.offset){

		}else if (session.getAttribute("userLG_search_offset")){
			params.offset = session.getAttribute("userLG_search_offset");
		} else {
			params.offset = 0;
		}

		//Recuperación de limite maximo de registros visibles
		if (params.search_rows) {
			if (params.search_rows != session.getAttribute("userLG_search_rows")) params.offset = 0;
				params.max = params.search_rows.toInteger();
		} else if (session.getAttribute("userLG_search_rows")){
			params.max = session.getAttribute("userLG_search_rows").toInteger();
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
			if (params.search_word != session.getAttribute("userLG_search_word")) params.offset = 0;
		} else if (session.getAttribute("userLG_search_word")){
			params.search_word = session.getAttribute("userLG_search_word");
		}

		def userLGList = UserLG.createCriteria().list (params) {
			if ( params.search_word ) {
				or {
					ilike("nombre", "%${params.search_word}%")
					ilike("username", "%${params.search_word}%")
				}
			}
			if ( session.getAttribute("username_establecimiento_id") != "" ) {
				sqlRestriction "establecimiento_id = '"+session.getAttribute('username_establecimiento_id')+"'"
			}
			if (session.getAttribute("username_rolActivo") == "ROLE_PROPIETARIO"){
				sqlRestriction """ exists (SELECT * FROM userlg_rolelg ur, rolelg r
										   WHERE this_.id = ur.userlg_id and ur.rolelg_id = r.id
										   AND r.authority != 'ROLE_CLIENTE'
										   AND r.authority != 'ROLE_PROGRAMADOR'
										   AND r.authority != 'ROLE_COCINERO'
										   AND r.authority != 'ROLE_REPARTIDOR')"""
			} else if (session.getAttribute("username_rolActivo") == "ROLE_ADMIN"){
				sqlRestriction """ exists (SELECT * FROM userlg_rolelg ur, rolelg r
										   WHERE this_.id = ur.userlg_id and ur.rolelg_id = r.id
										   AND r.authority != 'ROLE_CLIENTE'
										   AND r.authority != 'ROLE_PROGRAMADOR')"""
			}
			if ( !params.sort ){
				order("nombre","asc");
			}
		}

		session.setAttribute("userLG_search_rows", params.search_rows.toString());
		session.setAttribute("userLG_search_word", params.search_word);
		session.setAttribute("userLG_search_offset", params.offset.toString());

		respond userLGList, model:[userLGCount: userLGList.totalCount]
    }
	
	def empleados(Integer max) {
		//Recuperación de la pagina en que se encuentra
        if (params.offset){
			
        }else if (session.getAttribute("userLG_search_offset")){
			params.offset = session.getAttribute("userLG_search_offset");
        } else {
			params.offset = 0;
        }
		//Recuperación de limite maximo de registros visibles
		if (params.search_rows) {
			if (params.search_rows != session.getAttribute("userLG_search_rows")) params.offset = 0;
			params.max = params.search_rows.toInteger();
        } else if (session.getAttribute("userLG_search_rows")){
			params.max = session.getAttribute("userLG_search_rows").toInteger();
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
			if (params.search_word != session.getAttribute("userLG_search_word")) params.offset = 0;
        } else if (session.getAttribute("userLG_search_word")){
			params.search_word = session.getAttribute("userLG_search_word");
        }
        
        def userLGList = UserLG.createCriteria().list (params) {
            if ( params.search_word ) {
				or {
					ilike("nombre", "%${params.search_word}%")
					ilike("username", "%${params.search_word}%")
				}
            }
			
			if ( session.getAttribute("username_empresa_id") != "" ) {
				eq("empresa.id", session.getAttribute("username_empresa_id"))
            }
            
			sqlRestriction """ exists (SELECT * FROM userlg_rolelg ur, rolelg r
									   WHERE this_.id = ur.userlg_id and ur.rolelg_id = r.id
									   AND r.authority != 'ROLE_PROGRAMADOR'
									   AND r.authority != 'ROLE_PROPIETARIO'
									   AND r.authority != 'ROLE_ADMIN'
									   AND r.authority != 'ROLE_COCINERO'
									   AND r.authority != 'ROLE_REPARTIDOR'
									   AND r.authority != 'ROLE_EMPRESA')"""
            
            if ( !params.sort ){
				order("nombre","asc");
            }
        }
		
        session.setAttribute("userLG_search_rows", params.search_rows.toString());
        session.setAttribute("userLG_search_word", params.search_word);
        session.setAttribute("userLG_search_offset", params.offset.toString());
        
        respond userLGList, model:[userLGCount: userLGList.size()]
    }
    
    def adeudos(Integer max) {
		//Recuperación de la pagina en que se encuentra
        if (params.offset){
			
        }else if (session.getAttribute("userLG_search_offset")){
			params.offset = session.getAttribute("userLG_search_offset");
        } else {
			params.offset = 0;
        }
		//Recuperación de limite maximo de registros visibles
		if (params.search_rows) {
			if (params.search_rows != session.getAttribute("userLG_search_rows")) params.offset = 0;
			params.max = params.search_rows.toInteger();
        } else if (session.getAttribute("userLG_search_rows")){
			params.max = session.getAttribute("userLG_search_rows").toInteger();
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
			if (params.search_word != session.getAttribute("userLG_search_word")) params.offset = 0;
        } else if (session.getAttribute("userLG_search_word")){
			params.search_word = session.getAttribute("userLG_search_word");
        }
        
        def userLGList = UserLG.createCriteria().list (params) {
            if ( params.search_word ) {
				or {
					ilike("nombre", "%${params.search_word}%")
					ilike("username", "%${params.search_word}%")
				}
            }
			
			eq("pagoNomina", true)
			
			if ( session.getAttribute("username_empresa_id") != "" ) {
				eq("empresa.id", session.getAttribute("username_empresa_id"))
            }
            
            sqlRestriction """ exists (SELECT * FROM userlg_rolelg ur, rolelg r
									   WHERE this_.id = ur.userlg_id and ur.rolelg_id = r.id
									   AND r.authority != 'ROLE_PROGRAMADOR'
									   AND r.authority != 'ROLE_PROPIETARIO'
									   AND r.authority != 'ROLE_ADMIN'
									   AND r.authority != 'ROLE_COCINERO'
									   AND r.authority != 'ROLE_REPARTIDOR'
									   AND r.authority != 'ROLE_EMPRESA')"""
            
            if ( !params.sort ){
				order("nombre","asc");
            }
        }
        
        session.setAttribute("userLG_search_rows", params.search_rows.toString());
        session.setAttribute("userLG_search_word", params.search_word);
        session.setAttribute("userLG_search_offset", params.offset.toString());
        
        respond userLGList, model:[userLGCount: userLGList.size()]
    }
    
    def solicitudes(Integer max) {
		//Recuperación de la pagina en que se encuentra
        if (params.offset){
			
        }else if (session.getAttribute("userLG_search_offset")){
			params.offset = session.getAttribute("userLG_search_offset");
        } else {
			params.offset = 0;
        }
		//Recuperación de limite maximo de registros visibles
		if (params.search_rows) {
			if (params.search_rows != session.getAttribute("userLG_search_rows")) params.offset = 0;
			params.max = params.search_rows.toInteger();
        } else if (session.getAttribute("userLG_search_rows")){
			params.max = session.getAttribute("userLG_search_rows").toInteger();
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
			if (params.search_word != session.getAttribute("userLG_search_word")) params.offset = 0;
        } else if (session.getAttribute("userLG_search_word")){
			params.search_word = session.getAttribute("userLG_search_word");
        }
        
        def userLGList = UserLG.createCriteria().list (params) {
            if ( params.search_word ) {
				or {
					ilike("nombre", "%${params.search_word}%")
					ilike("username", "%${params.search_word}%")
				}
            }
            
			eq("solicitarPagoNomina", true)
			
			eq("enabled", true)
			
			if ( session.getAttribute("username_empresa_id") != "" ) {
				eq("empresa.id", session.getAttribute("username_empresa_id"))
            }
            
            sqlRestriction """ exists (SELECT * FROM userlg_rolelg ur, rolelg r
									   WHERE this_.id = ur.userlg_id and ur.rolelg_id = r.id
									   AND r.authority != 'ROLE_PROGRAMADOR'
									   AND r.authority != 'ROLE_PROPIETARIO'
									   AND r.authority != 'ROLE_ADMIN'
									   AND r.authority != 'ROLE_COCINERO'
									   AND r.authority != 'ROLE_REPARTIDOR'
									   AND r.authority != 'ROLE_EMPRESA')"""
            
            if ( !params.sort ){
				order("nombre","asc");
            }
        }
        
        session.setAttribute("userLG_search_rows", params.search_rows.toString());
        session.setAttribute("userLG_search_word", params.search_word);
        session.setAttribute("userLG_search_offset", params.offset.toString());
        
        respond userLGList, model:[userLGCount: userLGList.size()]
    }

    def show(UserLG userLG) {
		if (userLG == null) {
            notFound()
            return
        }
		userLG.decodePassword()
        respond userLG
    }
	
	def myuser() {
		String username = principal.username;
		UserLG userLG = UserLG.find("FROM UserLG as u WHERE u.username='"+username+"'");
 		if (userLG == null) {
            notFound()
            return
        }
		userLG.decodePassword()
        respond userLG
    }
    
	@Transactional
    def restart(UserLG userLG) {
		userLG.password = userLG.username
		userLG.save flush:true
		userLG.decodePassword()
		flash.message = message(code: 'default.restarted.message', args: [message(code: 'userLG.label', default: 'Usuario'), userLG.username])
        //redirect action:"show", id:userLG.id
        redirect action:"index", method:"GET"
    }
	
	@Transactional
    def active(UserLG userLG) {
		userLG.enabled = !userLG.enabled
		userLG.password = userLG.session_id
		userLG.save flush:true
		userLG.decodePassword()
		flash.message = message(code: 'default.reactive.message', args: [message(code: 'userLG.label', default: 'Usuario'), userLG.username, (userLG.enabled?'activado':'desactivado')])
        //redirect action:"show", id:userLG.id
        if (session.getAttribute('username_rolActivo') == "ROLE_EMPRESA"){
			redirect action:"empleados", method:"GET"
        }else{
			redirect action:"index", method:"GET"
        }
    }
    
    @Transactional
    def nomina(String user_id, float limite) {
		UserLG userLG = UserLG.findById(user_id);
		if (userLG != null){
			userLG.pagoNomina = !userLG.pagoNomina
			userLG.montoLimite = limite
			userLG.password = userLG.session_id
			userLG.save flush:true
			userLG.decodePassword()
			flash.message = message(code: 'default.reactive.message', args: ['El pago por n\u00f3mina de ', userLG.username, (userLG.pagoNomina?'ha sido habilitado':'ha sido inhabilitado')])
		}else{
			//ERROR USUARIO NO ENCONTRADO
		}
        if (session.getAttribute('username_rolActivo') == "ROLE_EMPRESA"){
			redirect action:"solicitudes", method:"GET"
        }else{
			redirect action:"index", method:"GET"
        }
    }
	
    def create() {
		UserLG userLG = new UserLG(params)
		if (springSecurityService.principal)
			userLG.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
		if (session.getAttribute('username_establecimiento_id'))
			userLG.establecimiento = Establecimiento.findById(session.getAttribute('username_establecimiento_id'));
		
		def Roles = RoleLG.list();
		if (session.getAttribute('username_rolActivo') == "ROLE_ADMIN"){
		    for(int i=Roles.size()-1; i>=0; i--){
				String rol = Roles.get(i).authority;
				if(rol.equals("ROLE_PROPIETARIO")){ 
                    Roles.remove(i);
                }else if(rol.equals("ROLE_PROGRAMADOR")){ 
                    Roles.remove(i);
                //}else if(rol.equals("ROLE_ADMIN")){ 
                //    Roles.remove(i);
                }else if(rol.equals("ROLE_CLIENTE")){ 
                    Roles.remove(i);
                }
            }
		}else if (session.getAttribute('username_rolActivo') == "ROLE_PROPIETARIO"){
		    for(int i=Roles.size()-1; i>=0; i--){
				String rol = Roles.get(i).authority;
				if(rol.equals("ROLE_PROGRAMADOR")){ 
                    Roles.remove(i);
                }else if(rol.equals("ROLE_CLIENTE")){ 
                    Roles.remove(i);
                }else if(rol.equals("ROLE_COCINERO")){ 
                    Roles.remove(i);
                }else if(rol.equals("ROLE_REPARTIDOR")){ 
                    Roles.remove(i);
                }
            }
		}
		
        respond userLG, model:[roles:Roles, establecimientos:Establecimiento.list(), empresas: Empresa.list()]
    }

    @Transactional
    def save(UserLG userLG) {
        if (userLG == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        UserLG usuario = UserLG.find("FROM  UserLG AS u WHERE u.username='" + userLG.username + "'");
		if (usuario != null) {
			userLG.errors.rejectValue('username', userLG.username, 'El nombre de usuario que ha indicado ya fue previamente registrado');
		}

		def Roles = RoleLG.list();
		if (session.getAttribute('username_rolActivo') == "ROLE_ADMIN"){
		    for(int i=Roles.size()-1; i>=0; i--){
				String rol = Roles.get(i).authority;
				if(rol.equals("ROLE_PROPIETARIO")){ 
                    Roles.remove(i);
                }else if(rol.equals("ROLE_PROGRAMADOR")){ 
                    Roles.remove(i);
                }else if(rol.equals("ROLE_ADMIN")){ 
                    Roles.remove(i);
                }else if(rol.equals("ROLE_CLIENTE")){ 
                    Roles.remove(i);
                }
            }
		}else if (session.getAttribute('username_rolActivo') == "ROLE_PROPIETARIO"){
		    for(int i=Roles.size()-1; i>=0; i--){
				String rol = Roles.get(i).authority;
				if(rol.equals("ROLE_PROGRAMADOR")){ 
                    Roles.remove(i);
                }else if(rol.equals("ROLE_CLIENTE")){ 
                    Roles.remove(i);
                }else if(rol.equals("ROLE_COCINERO")){ 
                    Roles.remove(i);
                }else if(rol.equals("ROLE_REPARTIDOR")){ 
                    Roles.remove(i);
                }
            }
		}
		
        if (userLG.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond userLG.errors, view:'create', model:[roles:Roles, establecimientos:Establecimiento.list(), empresas: Empresa.list()]
            return
        }
        
        //Recuperando el archivo
        def file = request.getFile('tmp-archivo_Foto')
        
        if (!file.empty) {
			if (file.getInputStream() != null){
				def directory		= grailsApplication.config.uploadFolder + "mes-users/";
				def fileName		= file.originalFilename;
				String extension	= fileName.substring(fileName.lastIndexOf("."));
				//def minutesName	= Math.round(System.currentTimeMillis()/1000/60);
				def minutesName		= System.currentTimeMillis();
				userLG.archivo_Foto = "Usuario_" + minutesName.toString() + extension ;
				
				//println directory+userLG.archivo_Foto
				//println System.properties['base.dir']
				//println request.getSession().getServletContext().getRealPath("/")
				//println System.getProperty("user.home")
				//println servletContext.getRealPath("/")
				
				//println "${basedir}/grails-app/assets/images/mes-users"

				file.transferTo(new File(directory+userLG.archivo_Foto))
			}
		}
		
		// Recupera el id del autor
		if (springSecurityService.principal)
			userLG.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
        userLG.save flush:true

        // Asignar rol
        UserLGRoleLG.create userLG, RoleLG.findByAuthority(params.role)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'userLG.label', default: 'UserLG'), userLG.username])
                //redirect userLG
                redirect action:"index", method:"GET"
            }
            '*' { respond userLG, [status: CREATED] }
        }
    }
	
	@Transactional
    def updateMyUser(UserLG userLG) {
		

		String passwordNuevo = params.passwordNuevo.value.toString();
		String cambiarPassword = params.cambiarPassword.value.toString();
		
		if (cambiarPassword=="true"){
			userLG.password = passwordNuevo;
		}
		
        if (userLG == null) {
            transactionStatus.setRollbackOnly()
            //notFound()
            respond userLG.errors, view:'myuser'
            return
        }
        
        UserLG usuario = UserLG.find("FROM UserLG AS u WHERE u.username='" + userLG.username + "' AND u.id!="+userLG.id);
		if (usuario != null) {
			userLG.errors.rejectValue('username', userLG.username, 'El nombre de usuario que ha indicado ya fue previamente registrado');
		}
        
        if (userLG.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond userLG.errors, view:'myuser'
            return
        }
        
        //Recuperando el archivo
        def file = request.getFile('tmp-archivo_Foto')
        
        if (!file.empty) {
			if (file.getInputStream() != null){
				def directory		= grailsApplication.config.uploadFolder + "mes-users/";
				def fileName		= file.originalFilename;
				String extension	= fileName.substring(fileName.lastIndexOf("."));
				//def minutesName	= Math.round(System.currentTimeMillis()/1000/60);
				def minutesName		= System.currentTimeMillis();
				//Borrando archivo anterior
				def file_real = new File( directory + userLG.archivo_Foto )
				if( file_real.exists() ){
					file_real.delete()
				}
				userLG.archivo_Foto = "Usuario_" + minutesName.toString() + extension ;
								
				file.transferTo(new File(directory+userLG.archivo_Foto))
			}
		}
        
        userLG.save flush:true

        // Actualizar rol
        if( params.role != userLG.getRol() ) {
            UserLGRoleLG.removeAll(userLG, true)
            UserLGRoleLG.create userLG, RoleLG.findByAuthority(params.role)
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Usuario', default: 'Usuario'), userLG.username])
                redirect action: "myuser"
            }
            '*'{ respond userLG, [status: OK] }
        }
    }
	
    def edit(UserLG userLG) {
		userLG.decodePassword()
		
		def Roles = RoleLG.list();
		if (session.getAttribute('username_rolActivo') == "ROLE_ADMIN"){
		    for(int i=Roles.size()-1; i>=0; i--){
				String rol = Roles.get(i).authority;
				if(rol.equals("ROLE_PROPIETARIO")){ 
                    Roles.remove(i);
                }else if(rol.equals("ROLE_PROGRAMADOR")){ 
                    Roles.remove(i);
                //}else if(rol.equals("ROLE_ADMIN")){ 
                //    Roles.remove(i);
                }else if(rol.equals("ROLE_CLIENTE")){ 
                    Roles.remove(i);
                }
            }
		}else if (session.getAttribute('username_rolActivo') == "ROLE_PROPIETARIO"){
		    for(int i=Roles.size()-1; i>=0; i--){
				String rol = Roles.get(i).authority;
				if(rol.equals("ROLE_PROGRAMADOR")){ 
                    Roles.remove(i);
                }else if(rol.equals("ROLE_CLIENTE")){ 
                    Roles.remove(i);
                }else if(rol.equals("ROLE_COCINERO")){ 
                    Roles.remove(i);
                }else if(rol.equals("ROLE_REPARTIDOR")){ 
                    Roles.remove(i);
                }
            }
		}
		
        respond userLG, model:[roles:Roles, establecimientos:Establecimiento.list(), empresas: Empresa.list()]
    }

    @Transactional
    def upchange(UserLG userLG) {
		
		String passwordNuevo = params.passwordNuevo.value.toString();
		String cambiarPassword = params.cambiarPassword.value.toString();
		
		if (cambiarPassword=="true"){
			userLG.password = passwordNuevo;
		}
		
        if (userLG == null) {
            transactionStatus.setRollbackOnly()
            //notFound()
            respond userLG.errors, view:'edit'
            return
        }
        
        UserLG usuario = UserLG.find("FROM  UserLG AS u WHERE u.username='" + userLG.username + "' AND u.id!="+userLG.id);
		if (usuario != null) {
			userLG.errors.rejectValue('username', userLG.username, 'El nombre de usuario que ha indicado ya fue previamente registrado');
		}
        
        if (userLG.hasErrors()) {
        	println userLG.errors;
            transactionStatus.setRollbackOnly()
            
            respond userLG.errors, view:'edit', model:[roles:Roles, establecimientos:Establecimiento.list(), empresas: Empresa.list()]
            return
        }
        
        //Recuperando el archivo
        def file = request.getFile('tmp-archivo_Foto')
        
        if (!file.empty) {
			if (file.getInputStream() != null){
				def directory		= grailsApplication.config.uploadFolder + "mes-users/";
				def fileName		= file.originalFilename;
				String extension	= fileName.substring(fileName.lastIndexOf("."));
				//def minutesName	= Math.round(System.currentTimeMillis()/1000/60);
				def minutesName		= System.currentTimeMillis();
				//Borrando archivo anterior
				def file_real = new File( directory + userLG.archivo_Foto )
				if( file_real.exists() ){
					file_real.delete()
				}
				userLG.archivo_Foto = "Usuario_" + minutesName.toString() + extension ;
								
				file.transferTo(new File(directory+userLG.archivo_Foto))
			}
		}
        
        userLG.save flush:true

        // Actualizar rol
        if( params.role != userLG.getRol() ) {
            UserLGRoleLG.removeAll(userLG, true)
            UserLGRoleLG.create userLG, RoleLG.findByAuthority(params.role)
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Usuario', default: 'Usuario'), userLG.username])
                //redirect userLG
                redirect action:"index", method:"GET"
            }
            '*'{ respond userLG, [status: OK] }
        }
    }

    @Transactional
    def delete(UserLG userLG) {

        if (userLG == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        
		if (Establecimiento.findAll("from Establecimiento as items where items.administrador.id = '"+userLG.id+"'").toList().size() > 0 ||
			Categoria.findAll("from Categoria as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			Embarque.findAll("from Embarque as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			Empresa.findAll("from Empresa as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			Establecimiento.findAll("from Establecimiento as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			Extra.findAll("from Extra as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			Ingrediente.findAll("from Ingrediente as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			Orden.findAll("from Orden as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			OrdenDetalle.findAll("from OrdenDetalle as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			Platillo.findAll("from Platillo as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			PlatilloEstablecimiento.findAll("from PlatilloEstablecimiento as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			Tipo.findAll("from Tipo as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			Transaccion.findAll("from Transaccion as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			UserLG.findAll("from UserLG as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0){
			
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'userLG.label', default: 'Usuario'), userLG.username, 'de Cocinas u otras entidades'])
			
		}else{
			//Borrando archivo anexo
			def directory = grailsApplication.config.uploadFolder + "mes-users/";
			def file_real = new File( directory + userLG.archivo_Foto )
			if( file_real.exists() ){
				file_real.delete()
			}
		
			UserLGRoleLG.executeUpdate("DELETE FROM UserLGRoleLG as ur WHERE ur.userLG.id="+userLG.id.toString())
			userLG.delete flush:true
			
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'userLG.label', default: 'UserLG'), userLG.username])
		}
		
        request.withFormat {
            form multipartForm {
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
	
	@Transactional
    def remove(UserLG userLG) {

        if (userLG == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
		
		if (Establecimiento.findAll("from Establecimiento as items where items.administrador.id = '"+userLG.id+"'").toList().size() > 0 ||
			Categoria.findAll("from Categoria as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			Embarque.findAll("from Embarque as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			Empresa.findAll("from Empresa as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			Establecimiento.findAll("from Establecimiento as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			Extra.findAll("from Extra as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			Ingrediente.findAll("from Ingrediente as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			Orden.findAll("from Orden as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			OrdenDetalle.findAll("from OrdenDetalle as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			Platillo.findAll("from Platillo as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			PlatilloEstablecimiento.findAll("from PlatilloEstablecimiento as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			Tipo.findAll("from Tipo as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			Transaccion.findAll("from Transaccion as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0 ||
			UserLG.findAll("from UserLG as items where items.id_Autor.id = '"+userLG.id+"'").toList().size() > 0){
			
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'userLG.label', default: 'Usuario'), userLG.username, 'de Cocinas u otras entidades'])
			
		}else{
		
			//Borrando archivo anexo
			def directory = grailsApplication.config.uploadFolder + "mes-users/";
			def file_real = new File( directory + userLG.archivo_Foto )
			if( file_real.exists() ){
				file_real.delete()
			}
			
			UserLGRoleLG.executeUpdate("DELETE FROM UserLGRoleLG as ur WHERE ur.userLG.id="+userLG.id.toString())
			userLG.delete flush:true
			
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'userLG.label', default: 'UserLG'), userLG.username])
		}
        redirect action:"index", method:"GET"
    }
	
    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'userLG.label', default: 'UserLG'), params.username])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
