package samba

import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*

@Transactional(readOnly = true)
class PlatilloEstablecimientoController {
    //Identificación de la sesion del usuario
    def springSecurityService;
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //Recuperación de la pagina en que se encuentra
        if (params.offset){
			
        }else if (session.getAttribute("platilloEstablecimiento_search_offset")){
			params.offset = session.getAttribute("platilloEstablecimiento_search_offset");
        } else {
			params.offset = 0;
        }
		//Recuperación de limite maximo de registros visibles
		if (params.search_rows) {
			if (params.search_rows != session.getAttribute("platilloEstablecimiento_search_rows")) params.offset = 0;
			params.max = params.search_rows.toInteger();
        } else if (session.getAttribute("platilloEstablecimiento_search_rows")){
			params.max = session.getAttribute("platilloEstablecimiento_search_rows").toInteger();
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
			if (params.search_word != session.getAttribute("platilloEstablecimiento_search_word")) params.offset = 0;
        } else if (session.getAttribute("platilloEstablecimiento_search_word")){
			params.search_word = session.getAttribute("platilloEstablecimiento_search_word");
        }
        
        def itemsList = PlatilloEstablecimiento.createCriteria().list (params) {
			
			if ( params.search_word ) {
				sqlRestriction """ exists (SELECT * FROM platillos p
										   WHERE this_.platillo_id = p.id
										   AND UPPER(p.nombre) LIKE UPPER('%${params.search_word}%'))"""
            }
			
			if ( session.getAttribute("username_establecimiento_id") != "" ) {
				eq("establecimiento.id", session.getAttribute("username_establecimiento_id"))
            }
			
			if ( !params.sort ){
				order("precio","desc");
            }
			
        }
        
        session.setAttribute("platilloEstablecimiento_search_rows", params.search_rows);
        session.setAttribute("platilloEstablecimiento_search_word", params.search_word);
        session.setAttribute("platilloEstablecimiento_search_offset", params.offset);
        
        respond itemsList, model:[platilloEstablecimientoCount: itemsList.totalCount]
    }

    def show(PlatilloEstablecimiento platilloEstablecimiento) {
        respond platilloEstablecimiento
    }

    def create() {
        PlatilloEstablecimiento platilloEstablecimiento = new PlatilloEstablecimiento(params);
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////                                  MODIFICAR SI CONSIDERA                                        ////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			platilloEstablecimiento.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
		def platillos = Platillo.list();
		
		if (platillos != null){
			if (session.getAttribute('username_establecimiento_id')){
				//Asigna el establecimiento por default al administrador
				platilloEstablecimiento.establecimiento = Establecimiento.findById(session.getAttribute('username_establecimiento_id'));
				
				//def platillosEstablecimiento = PlatilloEstablecimiento.findByEstablecimiento(platilloEstablecimiento.establecimiento)?.list();

				def platillosEstablecimiento = PlatilloEstablecimiento.findAll("from PlatilloEstablecimiento as p where p.establecimiento.id = '" + session.getAttribute('username_establecimiento_id') + "' ");
				if (platillosEstablecimiento != null) {
					for(int i=platillos.size()-1; i>=0; i--){
						for(int j=platillosEstablecimiento.size()-1; j>=0; j--){
							if (platillos.get(i).id == platillosEstablecimiento.get(j).platillo.id){
								platillos.remove(i);
								break;
							}
						}
					}
				}
			}
		}
        respond platilloEstablecimiento, model:[platillosList:platillos]
    }

    @Transactional
    def save(PlatilloEstablecimiento platilloEstablecimiento) {
        if (platilloEstablecimiento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        
		////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////                                  MODIFICAR SI CONSIDERA                                        ////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Recupera el id del autor y lo asigna
		if (springSecurityService.principal)
			platilloEstablecimiento.id_Autor = UserLG.findById(springSecurityService.principal.id);
		
		def platillos = Platillo.list();
		
		if (platillos != null){
			if (session.getAttribute('username_establecimiento_id')){
				platilloEstablecimiento.establecimiento = Establecimiento.findById(session.getAttribute('username_establecimiento_id'));
				//def platillosEstablecimiento = PlatilloEstablecimiento.findByEstablecimiento(platilloEstablecimiento.establecimiento)?.list();
				def platillosEstablecimiento = PlatilloEstablecimiento.findAll("from PlatilloEstablecimiento as p where p.establecimiento.id = '" + session.getAttribute('username_establecimiento_id') + "' ");
				if (platillosEstablecimiento != null) {
					for(int i=platillos.size()-1; i>=0; i--){
						for(int j=platillosEstablecimiento.size()-1; j>=0; j--){
							if (platillos.get(i).id == platillosEstablecimiento.get(j).platillo.id){
								platillos.remove(i);
								break;
							}
						}
					}
				}
			}
		}
		
        if (platilloEstablecimiento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond platilloEstablecimiento.errors, view:'create', model:[platillosList:platillos]
            return
        }
		
        platilloEstablecimiento.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'platilloEstablecimiento.label', default: 'PlatilloEstablecimiento'), platilloEstablecimiento.platillo])
                //redirect platilloEstablecimiento
                redirect action:"index", method:"GET"
            }
            '*' { respond platilloEstablecimiento, [status: CREATED] }
        }
    }

    def edit(PlatilloEstablecimiento platilloEstablecimiento) {
        respond platilloEstablecimiento
    }

    @Transactional
    def update(PlatilloEstablecimiento platilloEstablecimiento) {
        if (platilloEstablecimiento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        if (platilloEstablecimiento.hasErrors()) {
			def platillos = Platillo.list();
			
			if (platillos != null){
				if (session.getAttribute('username_establecimiento_id')){
					platilloEstablecimiento.establecimiento = Establecimiento.findById(session.getAttribute('username_establecimiento_id'));
					//def platillosEstablecimiento = PlatilloEstablecimiento.findByEstablecimiento(platilloEstablecimiento.establecimiento)?.list();
					def platillosEstablecimiento = PlatilloEstablecimiento.findAll("from PlatilloEstablecimiento as p where p.establecimiento.id = '" + session.getAttribute('username_establecimiento_id') + "' ");
					if (platillosEstablecimiento != null) {
						for(int i=platillos.size()-1; i>=0; i--){
							for(int j=platillosEstablecimiento.size()-1; j>=0; j--){
								if (platillos.get(i).id == platillosEstablecimiento.get(j).platillo.id){
									platillos.remove(i);
									break;
								}
							}
						}
					}
				}
			}
            transactionStatus.setRollbackOnly()
            respond platilloEstablecimiento.errors, view:'edit', model:[platillosList:platillos]
            return
        }
		
        platilloEstablecimiento.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'platilloEstablecimiento.label', default: 'PlatilloEstablecimiento'), platilloEstablecimiento.platillo])
                //redirect platilloEstablecimiento
                redirect action:"index", method:"GET"
            }
            '*'{ respond platilloEstablecimiento, [status: OK] }
        }
    }
    
    @Transactional
    def active(PlatilloEstablecimiento platilloEstablecimiento) {
		platilloEstablecimiento.activo = !platilloEstablecimiento.activo
		platilloEstablecimiento.save flush:true
		flash.message = message(code: 'default.reactive.message', args: [message(code: 'platilloEstablecimiento.label', default: 'Platillo'), platilloEstablecimiento.platillo, (platilloEstablecimiento.activo?'activado':'desactivado')])
		redirect action:"index", method:"GET"
    }

    @Transactional
    def delete(PlatilloEstablecimiento platilloEstablecimiento) {
		
        if (platilloEstablecimiento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
		
		if (OrdenDetalle.findAll("from OrdenDetalle as items where items.platillo.id = '"+platilloEstablecimiento.id+"'").toList().size() > 0){
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'platilloEstablecimiento.label', default: 'PlatilloEstablecimiento'), platilloEstablecimiento.platillo, 'de detalles en Ordenes o Embarques'])
		}else{
			platilloEstablecimiento.delete flush:true
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'platilloEstablecimiento.label', default: 'PlatilloEstablecimiento'), platilloEstablecimiento.platillo])
		}
		
        request.withFormat {
            form multipartForm {
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
    
    @Transactional
    def remove(PlatilloEstablecimiento platilloEstablecimiento) {

        if (platilloEstablecimiento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
		if (OrdenDetalle.findAll("from OrdenDetalle as items where items.platillo.id = '"+platilloEstablecimiento.id+"'").toList().size() > 0){
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'platilloEstablecimiento.label', default: 'PlatilloEstablecimiento'), platilloEstablecimiento.platillo, 'de detalles en Ordenes o Embarques'])
		}else{
			platilloEstablecimiento.delete flush:true
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'platilloEstablecimiento.label', default: 'PlatilloEstablecimiento'), platilloEstablecimiento.platillo])
		}
		
		redirect action:"index", method:"GET"
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'platilloEstablecimiento.label', default: 'PlatilloEstablecimiento'), params.platillo])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
