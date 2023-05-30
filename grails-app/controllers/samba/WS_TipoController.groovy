package samba

import grails.rest.RestfulController
import grails.converters.JSON
import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*
import grails.plugin.springsecurity.SpringSecurityUtils

@Transactional(readOnly = true)
class WS_TipoController extends RestfulController <WS_Tipo> {
    static responseFormats = ['json', 'xml']
	
	WS_TipoController(){
		super(WS_Tipo)
	}
	
    def index() {
		respond WS_Tipo.list();
    }
    
    
    def getTipos() {
    
		def superTipo = params.superTipo;
       	
		def listaTipos = WS_Tipo.findAll("from Tipo as t where t.superTipo = '" + superTipo + "' ");
		
		if(listaTipos != null)
			respond listaTipos
		else
			respond null 
    }
    
    
}
