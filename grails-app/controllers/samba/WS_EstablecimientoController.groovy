package samba

import grails.rest.RestfulController
import grails.converters.JSON
import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*
import grails.plugin.springsecurity.SpringSecurityUtils
import org.hibernate.SessionFactory;

@Transactional(readOnly = true)
class WS_EstablecimientoController extends RestfulController <WS_Establecimiento> {
	
	SessionFactory sessionFactory;
	
    static responseFormats = ['json', 'xml']
	
	WS_EstablecimientoController(){
		super(WS_Establecimiento)
	}

    def index() {
        respond WS_Establecimiento.list()
    }

    def getCiudades() {
		String query = "SELECT DISTINCT ciudad FROM empresas";
		def empresas = sessionFactory.getCurrentSession().createSQLQuery(query).list();
		
		def ciudadesList = [];
		for (empresa in empresas) {
			if (empresa != null) ciudadesList.add(empresa)
		}

	   	if(empresas != null){
           respond ciudadesList
        }else{
	     respond []
	   	}
    }

}
