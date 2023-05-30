package samba

import grails.rest.RestfulController
import grails.converters.JSON
import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*
import grails.plugin.springsecurity.SpringSecurityUtils

class WS_PlatilloEstablecimientoController extends RestfulController <WS_PlatilloEstablecimiento> {
    static responseFormats = ['json', 'xml']
    
		WS_PlatilloEstablecimientoController(){
		super(WS_PlatilloEstablecimiento)
	}
	
    def index() {
        respond WS_PlatilloEstablecimiento.list()
    }
    
    
	def obtenPlatilloPorEstablecimiento() { 
        def tipo = params.tipo
		def local = params.local
             	   	
        def query = PlatilloEstablecimiento.where { 
           platillo.tipo.id == tipo 
           establecimiento.id == local 
		}
		
	   	if(query != null){
           respond query.list()
        }else{
	     respond []
	   	}		             
    }
	
    
}
