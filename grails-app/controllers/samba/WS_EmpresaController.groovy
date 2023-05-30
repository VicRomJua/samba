package samba

import grails.rest.RestfulController
import grails.converters.JSON
import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*
import grails.plugin.springsecurity.SpringSecurityUtils

@Transactional(readOnly = true)
class WS_EmpresaController extends RestfulController <WS_Empresa> {
    static responseFormats = ['json', 'xml']
	
    WS_EmpresaController(){
		super(WS_Empresa)
	}

    def index() {
        respond WS_Empresa.list()
    }

    def getEmpresas() {
    	String ciudad = params.ciudad.toString().toUpperCase();

        def empresas = Empresa.createCriteria().list{
        	sqlRestriction ("upper(ciudad) in ('${ciudad}')")
		}
		
		def empresasList = [];
		for (empresa in empresas) {
			if (empresa.nombre != null)	empresasList.add(empresa.nombre)
		}

	   	if(empresas != null){
           respond empresasList
        }else{
	     respond []
	   	}		             
    }

}
