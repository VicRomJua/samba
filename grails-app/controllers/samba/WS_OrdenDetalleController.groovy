package samba

import grails.rest.RestfulController
import grails.converters.JSON
import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*
import grails.plugin.springsecurity.SpringSecurityUtils
import java.text.SimpleDateFormat

@Transactional(readOnly = true)
class WS_OrdenDetalleController extends RestfulController <WS_OrdenDetalle> {
    static responseFormats = ['json', 'xml']
	
	
	WS_OrdenDetalleController(){
		super(WS_OrdenDetalle)
	}
	
    def index() {
        respond WS_OrdenDetalle.list()
    }

	
	def obtenDetalle() { 
        def id = params.id
            	   	
        def query = OrdenDetalle.where { 
            id ==  id 
		}
		
	   	if(query != null){
           respond query.list()
        }else{
	     respond []
	   	}		             
    }	
		
		
	def detallePorOrden() { 
        def id = params.id
            	   	
        def query = OrdenDetalle.where { 
            orden.id ==  id 
		}
		
	   	if(query != null){
           respond query.list()
        }else{
	     respond []
	   	}		             
    }			
		
	def ordenPorEmbarque() { 
        def id = params.id
            	   	
        def query = OrdenDetalle.where { 
            embarque.id ==  id 
		}
		
	   	if(query != null){
           respond query.list()
        }else{
	     respond []
	   	}		             
    }
	
	def ordenPorEmbarqueAgrupado() { 
        def id = params.id
            	
	    def query = OrdenDetalle.createCriteria().list() {
           eq("embarque.id", id)     
        }
		
		def totalPlatillo = query.groupBy({ ordendetalle -> ordendetalle.platillo.id }).collectEntries { [(it.key): it.value.sum {it.cantidad}] };
		def resumenList = [] as Set;
		for(item in totalPlatillo)
		{
			for(item2 in query){
				if(item.key == item2.platillo.id){
				    resumenList.add([cantidad: item.value, producto: item2.platillo.platillo.nombre, personalizado: item2.esPersonalizado])	
				    resumenList.unique{p1,p2-> p1.producto<=>p2.producto}
				}
			}
		}
			
        //def query = OrdenDetalle.where { 
        //    embarque.id ==  id 
		//}.projections{
		//   
		//    groupProperty('platillo.id')
        //    rowCount()
		//}
		
	   	if(resumenList != null){
           respond resumenList
        }else{
	     respond []
	   	}		             
    }
	
	
	def detallePlatillo() { 
        def id = params.id
            	   	
        def query = OrdenDetalle.where { 
            platillo.id ==  id 
		}
		
	   	if(query != null){
           respond query.list()
        }else{
	     respond []
	   	}		             
    }
	
	def detallePorCliente() { 
        def id = params.id
            	   	
        def query = OrdenDetalle.where { 
            id_Autor.id ==  id
		}
		
	   	if(query != null){
           respond query.list()
        }else{
	     respond []
	   	}		             
    }
	
	
   @Transactional
   def guardar() {
		
		def jsonParams = request.JSON
		
		OrdenDetalle datos = new OrdenDetalle();
		datos.embarque  = null;
		datos.dateCreated  = new Date(); 
		datos.fechaPago    = new Date();
		def format_date = "yyyy-MM-dd'T'hh:mm:ss.S'Z'";
        def mask_date = new SimpleDateFormat(format_date);
		def entrega = mask_date.parse(jsonParams.fechaEntrega);
        entrega.clearTime();
		datos.fechaEntrega = entrega;
		datos.horaEntrega  = jsonParams.horaEntrega;
		datos.cantidad = jsonParams.cantidad as Integer;
		 if(jsonParams.modoPago.equals("Por saldo")){
		    datos.modoPago = "Por saldo";
		}else{
		    datos.modoPago = "Por n\u00f3mina";
		}
        //datos.modoPago = jsonParams.modoPago;
        if (datos.modoPago.equals("Por saldo")){
			datos.realmentePagado = true;
        }
		datos.montoPagado = jsonParams.montoPagado + 0.0;
		datos.esPersonalizado = jsonParams.esPersonalizado;
		datos.estatus = jsonParams.estatus;
		
		datos.personalizado = jsonParams.personalizado;		// OJO FALTA EL VALOR
		
		datos.id_Autor = UserLG.findById(jsonParams.autorId);
		datos.orden = Orden.findById(jsonParams.ordenId);
		datos.establecimiento = Establecimiento.findById(jsonParams.establecimiento_id);
		datos.platillo = PlatilloEstablecimiento.findById(jsonParams.platilloId);
        datos.save(failOnError: true, flush: true)
			
		if(datos != null){
			jsonParams.exito = "Ok";
			render jsonParams.exito;
		}else{
			jsonParams.exito = "Error";
			render jsonParams.exito;
		}
    }
	
}
