package samba

import grails.rest.RestfulController
import grails.converters.JSON
import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*
import grails.plugin.springsecurity.SpringSecurityUtils

import io.conekta.*
import org.json.JSONObject

@Transactional(readOnly = true)
class WS_OrdenController extends RestfulController <WS_Orden> {
    static responseFormats = ['json', 'xml']
	static allowedMethods = [save: "POST"]
	
	WS_OrdenController(){
		super(WS_Orden)
	}
	
    def index() {
        respond WS_Orden.list()
    }
    
	
   	def obtenOrden() { 
        def id = params.id
            	   	
        def query = Orden.where { 
            id ==  id 
		}
		
	   	if(query != null){
           respond query.list()
        }else{
	     respond []
	   	}		             
    }	
	
	def obtenOrdenPorEstablecimiento() { 
        def id = params.id
            	   	
        def query = Orden.where { 
            establecimiento.id ==  id 
		}
		
	   	if(query != null){
           respond query.list()
        }else{
	     respond []
	   	}		             
    }
	
	
	def obtenOrdenPorEmpresa() { 
        def id = params.id
            	   	
        def query = Orden.where { 
            empresa.id ==  id 
		}
		
	   	if(query != null){
           respond query.list()
        }else{
	     respond []
	   	}		             
    }
    
    def ordenesEntregadas() { 
        def id = params.id
            	   	
        def query = Orden.where { 
            id_Autor.id ==  id  && estatus == "Entregado" 
		}
		
	   	if(query != null){
           respond query.list()
        }else{
	     respond []
	   	}		             
    }
	
	
	def detallePorCliente() { 
        def id = params.id
            	   	
        def query = Orden.where { 
            id_Autor.id ==  id && estatus != "Entregado" && estatus != "Cancelado"  
		}
		
	   	if(query != null){
           respond query.list()
        }else{
	     respond []
	   	}		             
    }
	
	
	@Transactional
	def cancelarOrden() {
    	def jsonParams = request.JSON
	
		def id_orden = jsonParams.id_orden;
		
		Orden datos = Orden.findById(id_orden);
		if (datos != null){
	     	datos.estatus = "Cancelado";
			datos.save flush:true;
			//Actualizando a las OrdenesDetalles
			def detalles = OrdenDetalle.findAll {
				 orden.id == id_orden
			}
			for(detalle in detalles){
				detalle.estatus = "Cancelado";
				detalle.save flush:true;
			}
		}
	}
	
	
    @Transactional
	def guardarCalificacion() {
    	def jsonParams = request.JSON
	
		def idOrden = jsonParams.id_orden;
		
		def calificacionServicio     = jsonParams.calificacionServicio as Integer;
        def calificacionComida      =  jsonParams.calificacionComida as Integer
        def calificacionComentarios =  jsonParams.calificacionComentarios
		
		Orden datos = Orden.findById(idOrden);
		if (datos != null){
	     	datos.calificacionServicio = calificacionServicio;
		    datos.calificacionComida   = calificacionComida;
		    datos.calificacionComentario = calificacionComentarios;
			datos.save flush:true
		}
	}
	
    @Transactional
    def guardar() {
		//Recuperando los datos del json para asignarlos
		def jsonParams = request.JSON
		
		Orden datos = new Orden();
		datos.empresa = Empresa.findById(jsonParams.empresa_id);
		Establecimiento cocina = Establecimiento.findById(jsonParams.establecimiento_id);
		datos.establecimiento = cocina;
		datos.fechaPago = new Date();
		datos.id_Autor = UserLG.findById(jsonParams.id_autor);
		datos.montoPagado = jsonParams.monto_pagado + 0.0;
		 if(jsonParams.modoPago.equals("Por saldo")){
		    datos.modoPago = "Por saldo";
		}else{
		    datos.modoPago = "Por n\u00f3mina";
		}
		//datos.modoPago = jsonParams.modo_pago;
		datos.calificacionServicio = 0;
		datos.calificacionComida   = 0;
		datos.calificacionComentario = "";
		datos.estatus="Nuevo"
		int no_orden = 0;
		if (Orden.createCriteria().get{projections {max "noOrden"}} as Integer != null){
			no_orden = Orden.createCriteria().get{projections {max "noOrden"}} as int
		}
		no_orden++;
		datos.noOrden = String.format("%07d", no_orden);
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		////                                    DESCONTANDO SALDOS                                          ////
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Buscando al usuario
		def isSaldo = true;
		String user_id = jsonParams.id_autor.toString();
		UserLG usuario;
		if (user_id.isInteger()) usuario = UserLG.findById(Integer.parseInt(user_id));
		if (usuario != null){
			usuario.decodePassword();
			// Tipo de forma de pago
			if (datos.modoPago.equals("Por saldo")){
				if (usuario.montoSaldo > datos.montoPagado){
					usuario.montoSaldo -= datos.montoPagado;
				}else{
					isSaldo = false;
				}
			}else{
				if (usuario.pagoNomina){
					if ((usuario.montoAdeudo+datos.montoPagado) <= usuario.montoLimite){
						usuario.montoAdeudo += datos.montoPagado;
					}else{
						isSaldo = false;
					}
				}else{
					isSaldo = false;
				}
			}
			usuario.save flush:true
			if (isSaldo){
				//Registrar la transacción al usuario
				Transaccion transaccion = new Transaccion();
				transaccion.monto = datos.montoPagado;
				transaccion.fechaOperacion = new Date();
				transaccion.tipoTransaccion = "Egreso";
				transaccion.referencia = "";
				transaccion.id_orden = "";
				// Tipo de forma de pago
				if (datos.modoPago.equals("Por saldo")){
					transaccion.tipoMedioPago = "Por saldo";
				}else{
					transaccion.tipoMedioPago = "Por n\u00f3mina";
				}
				transaccion.id_Autor = usuario;
				transaccion.id_orden = "";
				transaccion.estatus = "Exitoso";
				transaccion.save flush:true
			}
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		////                                INCREMENTAR VENTAS DE COCINA                                    ////
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (cocina != null) {
			if (isSaldo){
				cocina.totalVendido += datos.montoPagado;
				cocina.save flush:true
			}
		}
		
		datos.save(failOnError: true, flush: true)
		
		if(datos!= null && isSaldo){
		   render datos as JSON;
		}else{
			if (!isSaldo) {
				render "Error. ¡No dispone de saldo para su compra!";
			}else{
				render "Error";
			}
		}
    }
	
	@Transactional
	def setTransaction() {
		def jsonParams = request.JSON
		//println jsonParams.nombre_usuario + "|" + jsonParams.usuario_id + "|" + jsonParams.token_id + "|" + jsonParams.paymentType + "|" + jsonParams.cantidad;
		
		String user_id = jsonParams.usuario_id.toString();
		UserLG usuario;
		if (user_id.isInteger()) usuario = UserLG.findById(Integer.parseInt(user_id));
		if (usuario != null){
			Conekta.setApiKey("key_pJEAU1R6G1Yf2gxmx8zthA");
			if (jsonParams.paymentType=="card"){
				
				//Generando objeto json
				JSONObject orderDetails = new JSONObject("{" +
				"'currency': 'mxn'," +
				"'metadata': {" +
				"    'test': true"+
				"}," +
				"'line_items': [{" +
				"    'name': 'Prepago Samba Smoothie'," +
				"    'description': 'Recarga de saldo usando tarjeta bancaria.'," +
				"    'unit_price': "+jsonParams.cantidad+"," +
				"    'shippable': false," +
				"    'quantity': 1," +
				"    'type': 'virtual'" +
				"}]," +
				"'customer_info': { " +
				"    'name': '"+jsonParams.nombre_usuario+"'," +
				"    'phone': '"+usuario.telefono_Movil+"'," +
				"    'email': '"+usuario.username+"'" +
				"}," +
				"'charges': [{" +
				"    'payment_method': {" +
				"        'type': 'card'," +
				"        'token_id': '"+jsonParams.token_id+"'" +
				"    }, " +
				"    'amount': "+jsonParams.cantidad+
				"}]" +
				"}")
				
				//Registrar la transacción al usuario
				Transaccion transaccion = new Transaccion();
				transaccion.monto = (jsonParams.cantidad/100);
				transaccion.fechaOperacion = new Date();
				transaccion.tipoTransaccion = "Ingreso";
				transaccion.referencia = "";
				transaccion.id_orden = "";
				transaccion.tipoMedioPago = "Tarjeta bancaria";
				transaccion.id_Autor = usuario;
				
				try{
					Order order = Order.create(orderDetails);

					//print order.charges.get(0).status + "|" + order.id;

					if (order.charges.get(0).status=='paid'){
						//Actualizando montos!!
						usuario.decodePassword();
						usuario.montoSaldo += (jsonParams.cantidad/100);
						usuario.save flush:true
						
						transaccion.id_orden = order.id;
						transaccion.estatus = "Exitoso";
						//System.out.println("PAGADO!!!");
					}
				}catch (ErrorList e) {
					transaccion.estatus = "Fallido";
				}
				
				transaccion.save flush:true
				render transaccion.estatus
			} else if(jsonParams.paymentType=="oxxo_cash"){
				//println "OXXO_CASH";
				//Generando objeto json
				JSONObject orderDetails = new JSONObject("{" +
				"'currency': 'mxn'," +
				"'metadata': {" +
				"    'test': true"+
				"}," +
				"'line_items': [{" +
				"    'name': 'Prepago Samba Smoothie'," +
				"    'description': 'Recarga de saldo usando OXXO Pay.'," +
				"    'unit_price': "+jsonParams.cantidad+"," +
				"    'shippable': false," +
				"    'quantity': 1," +
				"    'type': 'virtual'" +
				"}]," +
				"'customer_info': { " +
				"    'name': '"+jsonParams.nombre_usuario+"'," +
				"    'phone': '"+usuario.telefono_Movil+"'," +
				"    'email': '"+usuario.username+"'" +
				"}," +
				"'charges': [{" +
				"    'payment_method': {" +
				"        'type': 'oxxo_cash'," +
				"    }, " +
				"    'amount': "+jsonParams.cantidad+
				"}]" +
				"}")
				
				//Registrar la transacción al usuario
				Transaccion transaccion = new Transaccion();
				transaccion.monto = (jsonParams.cantidad/100);
				transaccion.fechaOperacion = new Date();
				transaccion.tipoTransaccion = "Ingreso";
				transaccion.referencia = "";
				transaccion.id_orden = "";
				transaccion.tipoMedioPago = "Sucursal Oxxo";
				transaccion.id_Autor = usuario;
				
				//println "Usuario:"+ jsonParams.usuario_id
				try{
					Order order = Order.create(orderDetails);
					//System.out.println(order.charges.get(0).status);
					//System.out.println(order.charges.get(0).payment_method.reference);
					render order.charges.get(0).payment_method.reference;
					
					//println "id:>" + order.charges.get(0).id + "|" + order.id;

					transaccion.id_orden = order.id;
					transaccion.referencia = order.charges.get(0).id;
					transaccion.estatus = "Pendiente";
				}catch (ErrorList e) {
					transaccion.estatus = "Fallido";
					//System.out.println("ERROR!!!");
					//render "ERROR";
				}
				
				transaccion.save flush:true
				render transaccion.estatus
			} else if(jsonParams.paymentType=="spei"){
				//println "SPEI";
				//Generando objeto json
				JSONObject orderDetails = new JSONObject("{" +
				"'currency': 'mxn'," +
				"'metadata': {" +
				"    'test': true"+
				"}," +
				"'line_items': [{" +
				"    'name': 'Prepago Samba Smoothie'," +
				"    'description': 'Recarga de saldo usando transferencia bancaria (SPEI).'," +
				"    'unit_price': "+jsonParams.cantidad+"," +
				"    'shippable': false," +
				"    'quantity': 1," +
				"    'type': 'virtual'" +
				"}]," +
				"'customer_info': { " +
				"    'name': '"+jsonParams.nombre_usuario+"'," +
				"    'phone': '"+usuario.telefono_Movil+"'," +
				"    'email': '"+usuario.username+"'" +
				"}," +
				"'charges': [{" +
				"    'payment_method': {" +
				"        'type': 'spei'," +
				"    }, " +
				"    'amount': "+jsonParams.cantidad+
				"}]" +
				"}")
				
				//Registrar la transacción al usuario
				Transaccion transaccion = new Transaccion();
				transaccion.monto = (jsonParams.cantidad/100);
				transaccion.fechaOperacion = new Date();
				transaccion.tipoTransaccion = "Ingreso";
				transaccion.referencia = "";
				transaccion.id_orden = "";
				transaccion.tipoMedioPago = "SPEI";
				transaccion.id_Autor = usuario;
				
				//println "Usuario:"+ jsonParams.usuario_id
				try{
					Order order = Order.create(orderDetails);
					//System.out.println(order.charges.get(0).status);
					//System.out.println(order.charges.get(0).payment_method.reference);
					//println "Metodo:>" +  order.charges.get(0).payment_method.type;
					//println "SPEI:>" +  order.charges.get(0).payment_method.clabe;
					
					render order.charges.get(0).payment_method.clabe;
					
					//println "id:>" + order.charges.get(0).id + "|" + order.id;
					
					transaccion.id_orden = order.id;
					transaccion.referencia = order.charges.get(0).id;
					transaccion.estatus = "Pendiente";
				}catch (ErrorList e) {
					transaccion.estatus = "Fallido";
					//System.out.println("ERROR!!!");
					//render "ERROR";
				}	
				transaccion.save flush:true
				render transaccion.estatus
			}
		}else{
			render "Fallido"
		}
	}
}
