package samba

import grails.rest.RestfulController
import grails.converters.JSON
import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*
import grails.plugin.springsecurity.SpringSecurityUtils

@Transactional(readOnly = true)
class WebhooksController  extends RestfulController <WS_UserLG>{
    static responseFormats = ['json', 'xml']
	
    WebhooksController(){
		super(WS_UserLG)
	}
	
	@Transactional
	def receive(){
		def jsonParams = request.JSON
		
		//print jsonParams
		//println "-----------------"
		//println jsonParams.type
		if (jsonParams.type == 'charge.paid'){
			
			//println "referencia:" + jsonParams.data.object.id
			//println "referencia:" + jsonParams.id
			
			//Buscar la transacción con la referencia indicada
			Transaccion transaccion = Transaccion.find("FROM Transaccion as t WHERE t.estatus='Pendiente' AND t.referencia='"+jsonParams.data.object.id+"'");
			if (transaccion != null) {
				//print "Encontro transacción..."
				transaccion.estatus = "Consulta"
            	transaccion.save flush:true
				//Buscar al usuario de la transacción para reflejar saldo
				UserLG usuario = UserLG.findById(transaccion.id_Autor.id);
				if (usuario != null){
					//print "Encontro usuario..."
					//Actualizando montos!!
					usuario.decodePassword();
					usuario.montoSaldo += transaccion.monto;
					usuario.save flush:true
					//Actualizando estatus de transacción
					transaccion.estatus = "Exitoso";
					transaccion.save flush:true
					
					//println "OK..."
				}else{
					transaccion.estatus = "Fallido"
            		transaccion.save flush:true
				}
			}
			//payment_method = data['charges']['data']['object']['payment_method']['type']
			//msg = 'Tu pago con #{payment_method} ha sido comprobado'
			//ExampleMailer.email(data, msg)
		}
		render status: 200
	}
}
