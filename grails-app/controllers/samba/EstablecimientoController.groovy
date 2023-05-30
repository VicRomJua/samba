package samba

import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*

@Transactional(readOnly = true)
class EstablecimientoController {
    //Identificación de la sesion del usuario
    def springSecurityService;
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //Recuperación de la pagina en que se encuentra
        if (params.offset){
			
        }else if (session.getAttribute("establecimiento_search_offset")){
			params.offset = session.getAttribute("establecimiento_search_offset");
        } else {
			params.offset = 0;
        }
		//Recuperación de limite maximo de registros visibles
		if (params.search_rows) {
			if (params.search_rows != session.getAttribute("establecimiento_search_rows")) params.offset = 0;
			params.max = params.search_rows.toInteger();
        } else if (session.getAttribute("establecimiento_search_rows")){
			params.max = session.getAttribute("establecimiento_search_rows").toInteger();
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
			if (params.search_word != session.getAttribute("establecimiento_search_word")) params.offset = 0;
        } else if (session.getAttribute("establecimiento_search_word")){
			params.search_word = session.getAttribute("establecimiento_search_word");
        }
        
        def itemsList = Establecimiento.createCriteria().list (params) {
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
        
        session.setAttribute("establecimiento_search_rows", params.search_rows);
        session.setAttribute("establecimiento_search_word", params.search_word);
        session.setAttribute("establecimiento_search_offset", params.offset);
        
        respond itemsList, model:[establecimientoCount: itemsList.totalCount]
    }

    def show(Establecimiento establecimiento) {
        respond establecimiento
    }

    def create(Establecimiento establecimiento) {
		if (establecimiento == null) establecimiento = new Establecimiento(params);
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////                                  MODIFICAR SI CONSIDERA                                        ////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			establecimiento.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
		//Recuperando la lista de usuarios administradores
		def Administradores = UserLG.createCriteria().list () {
			order("nombre","Desc")
        }
		
		for(int i=Administradores.size()-1; i>=0; i--){
			String rol = Administradores.get(i).getRol();
			if(!rol.equals("ROLE_ADMIN")){ 
				Administradores.remove(i);
			}else if (Administradores.get(i).establecimiento != null && Administradores.get(i).id != establecimiento.administrador?.id){
				Administradores.remove(i);
			}
		}
		
        respond establecimiento, model:[administradores:Administradores]
    }

    @Transactional
    def save(Establecimiento establecimiento) {
        if (establecimiento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
		
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			establecimiento.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
		//Recuperando la lista de usuarios administradores
		def Administradores = UserLG.createCriteria().list () {
			order("nombre","Desc")
        }
		
		for(int i=Administradores.size()-1; i>=0; i--){
			String rol = Administradores.get(i).getRol();
			if(!rol.equals("ROLE_ADMIN")){ 
				Administradores.remove(i);
			}else if (Administradores.get(i).establecimiento != null && Administradores.get(i).id != establecimiento.administrador?.id){
				Administradores.remove(i);
			}
		}
		
        if (establecimiento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond establecimiento.errors, view:'create', model:[administradores:Administradores]
            return
        }
		
        establecimiento.save flush:true
		
		//Buscar usuario admin si tiene para asignar el establecimiento
		if (establecimiento.administrador){
			UserLG administrador = UserLG.findById(establecimiento.administrador.id);
			if (administrador){
				administrador.establecimiento = establecimiento;
				administrador.decodePassword();
				administrador.save flush:true
			}
		}
		
		
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.createda.message', args: [message(code: 'establecimiento.label', default: 'Establecimiento'), establecimiento.nombre])
                //redirect establecimiento
                redirect action:"index", method:"GET"
            }
            '*' { respond establecimiento, [status: CREATED] }
        }
    }

    def edit(Establecimiento establecimiento) {
		//Recuperando la lista de usuarios administradores
		def Administradores = UserLG.createCriteria().list () {
			order("nombre","Desc")
        }
		
		for(int i=Administradores.size()-1; i>=0; i--){
			String rol = Administradores.get(i).getRol();
			if(!rol.equals("ROLE_ADMIN")){ 
				Administradores.remove(i);
			}else if (Administradores.get(i).establecimiento != null && Administradores.get(i).id != establecimiento.administrador?.id){
				Administradores.remove(i);
			}
		}
		
        respond establecimiento, model:[administradores:Administradores]
    }

    @Transactional
    def update(Establecimiento establecimiento) {
        if (establecimiento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
		
		//Recuperando la lista de usuarios administradores
		def Administradores = UserLG.createCriteria().list () {
			order("nombre","Desc")
        }
		
		for(int i=Administradores.size()-1; i>=0; i--){
			String rol = Administradores.get(i).getRol();
			if(!rol.equals("ROLE_ADMIN")){ 
				Administradores.remove(i);
			}else if (Administradores.get(i).establecimiento != null && Administradores.get(i).id != establecimiento.administrador?.id){
				Administradores.remove(i);
			}
		}
		
        if (establecimiento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond establecimiento.errors, view:'edit', model:[administradores:Administradores]
            return
        }
		
		def tmp_usuarios_establecimiento = UserLG.findByEstablecimiento(establecimiento).list();
		
		for(int i=tmp_usuarios_establecimiento.size()-1; i>=0; i--){
			String rol = tmp_usuarios_establecimiento.get(i).getRol();
			if(rol.equals("ROLE_ADMIN")){ 
				def tmp_admin = tmp_usuarios_establecimiento.get(i);
				tmp_admin.establecimiento = null;
				tmp_admin.decodePassword();
				tmp_admin.save flush:true
				break;
			}
		}
		
        establecimiento.save flush:true
		
		//Buscar usuario admin si tiene para asignar el establecimiento
		if (establecimiento.administrador){
			UserLG administrador = UserLG.findById(establecimiento.administrador.id);
			if (administrador){
				administrador.establecimiento = establecimiento;
				administrador.decodePassword();
				administrador.save flush:true
			}
		}
		
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'establecimiento.label', default: 'Establecimiento'), establecimiento.nombre])
                //redirect establecimiento
                redirect action:"index", method:"GET"
            }
            '*'{ respond establecimiento, [status: OK] }
        }
    }

    @Transactional
    def delete(Establecimiento establecimiento) {

        if (establecimiento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
		
		if (UserLG.findAll("from UserLG as items where items.establecimiento.id = '"+establecimiento.id+"'").toList().size() > 0 ||
			PlatilloEstablecimiento.findAll("from PlatilloEstablecimiento as items where items.establecimiento.id = '"+establecimiento.id+"'").toList().size() > 0 ||
			Embarque.findAll("from Embarque as items where items.establecimiento.id = '"+establecimiento.id+"'").toList().size() > 0){
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'establecimiento.label', default: 'Establecimiento'), establecimiento.nombre, 'de Platillos, Embarques o Usuarios'])
		}else{
			establecimiento.delete flush:true
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'establecimiento.label', default: 'Establecimiento'), establecimiento.nombre])
		}
		
        request.withFormat {
            form multipartForm {
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
    
    @Transactional
    def remove(Establecimiento establecimiento) {

        if (establecimiento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
		
		if (UserLG.findAll("from UserLG as items where items.establecimiento.id = '"+establecimiento.id+"'").toList().size() > 0 ||
			PlatilloEstablecimiento.findAll("from PlatilloEstablecimiento as items where items.establecimiento.id = '"+establecimiento.id+"'").toList().size() > 0 ||
			Embarque.findAll("from Embarque as items where items.establecimiento.id = '"+establecimiento.id+"'").toList().size() > 0){
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'establecimiento.label', default: 'Establecimiento'), establecimiento.nombre, 'de Platillos, Embarques o Usuarios'])
		}else{
			establecimiento.delete flush:true
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'establecimiento.label', default: 'Establecimiento'), establecimiento.nombre])
		}
		
		redirect action:"index", method:"GET"
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'establecimiento.label', default: 'Establecimiento'), params.nombre])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
