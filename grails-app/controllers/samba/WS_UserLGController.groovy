package samba

import grails.rest.RestfulController
import grails.converters.JSON
import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*
import grails.plugin.springsecurity.SpringSecurityUtils
import java.text.SimpleDateFormat

@Transactional(readOnly = true)
class WS_UserLGController extends RestfulController <WS_UserLG> {
    static responseFormats = ['json', 'xml']
	
	WS_UserLGController(){
		super(WS_UserLG)
	}
	
    def index() {
        respond WS_UserLG.list()
    }

	def valida() {
		
    	def email = params.email;
    	String resultado = "Error";	
       	
		UserLG usuario = UserLG.find("from UserLG as u WHERE u.username = '" + email + "'");
		
		if(usuario != null){
			respond usuario;
		}else{
			render resultado;
		}
    }
    
    def datosUsuario(){
	
	    def userId = params.id;
       	String resultado = "Error";	
       	
		UserLG usuario = UserLG.find("from UserLG as u where u.id=" + userId);
		
		if(usuario != null){
			respond usuario;
		}else{
			render resultado;
		}
	}
	
	@Transactional
    def setSaldoCancelado() {
    	def jsonParams = request.JSON;
    	String user_id = jsonParams.id_usuario.toString();
		def mododepago = jsonParams.tipo;
		def devolucion = jsonParams.dinero;
						
	    String resultado = "";					
		UserLG usuario;
		
		if (user_id.isInteger()) usuario = UserLG.findById(Integer.parseInt(user_id));
		
		if (usuario != null){
			usuario.decodePassword(); 
	       if (mododepago == "Por saldo"){				 
				usuario.montoSaldo += devolucion ;			    				
			}else{
	           usuario.montoAdeudo -= devolucion ;
			}
			usuario.save flush:true;
			//Registrar la transacción al usuario
			Transaccion transaccion = new Transaccion();
			transaccion.monto = devolucion + 0.0;
			transaccion.fechaOperacion = new Date();
			transaccion.tipoTransaccion = "Reembolso";
			transaccion.referencia = "";
			transaccion.id_orden = "";
			// Tipo de forma de pago
			if (mododepago == "Por saldo"){
				transaccion.tipoMedioPago = "Por saldo";
			}else{
				transaccion.tipoMedioPago = "Por n\u00f3mina";
			}
			transaccion.id_Autor = usuario;
			transaccion.id_orden = "";
			transaccion.estatus = "Exitoso";
			transaccion.save flush:true
			
			resultado = "Exito";
		}
		render resultado;
    }
    
    @Transactional
    def setSolicitudNomina() {
    	def jsonParams = request.JSON;
    	String user_id = jsonParams.id_usuario.toString();
		
		String resultado = "Error";					
	   		
		UserLG usuario;
		
		if (user_id.isInteger()) usuario = UserLG.findById(Integer.parseInt(user_id));
		
		if (usuario != null){
		     usuario.decodePassword();
	         usuario.solicitarPagoNomina = true;
			 usuario.save flush:true
			 resultado = "Exito";
		}
		render resultado;
    }
    
    @Transactional
    def setPassword() {
    	def jsonParams = request.JSON;
    	String user_id = jsonParams.id.toString();
    	String password = jsonParams.password.toString();
		String resultado = "Fallido";
		UserLG usuario;
		
		if (user_id.isInteger()) usuario = UserLG.findById(Integer.parseInt(user_id));
		
		if (usuario != null){
			usuario.password = password;
			usuario.session_id = password;
			usuario.save flush:true
			resultado = "Exito";
		}
		render resultado;
    }
    
     @Transactional
	 def setEnfermedades() {
	    def jsonParams = request.JSON;
	    
    	String user_id = jsonParams.id.toString();
    	String enfermedades = jsonParams.enfermedadesCronicas;
		String resultado = "Fallido";
		UserLG usuario;
		
		if (user_id.isInteger()) usuario = UserLG.findById(Integer.parseInt(user_id));
		
		if (usuario != null){
			usuario.decodePassword(); 
			usuario.enfermedadesCronicas = enfermedades
			usuario.save flush:true
			resultado = "Exito";
		}
		render resultado;
    }
	                    
	@Transactional
	def setActualizaPerfil() {
	    def jsonParams = request.JSON;
		
    	String user_id = jsonParams.id.toString();
    	
		String resultado = "Fallido";
		         
		def formatString = "yyyy-MM-dd";
        def sdf = new SimpleDateFormat(formatString);
		def datePrueba = sdf.parse(jsonParams.fechaNacimiento);
		         
		UserLG usuario;
		
		if (user_id.isInteger()) usuario = UserLG.findById(Integer.parseInt(user_id));
		
		if (usuario != null){
	     	usuario.password = jsonParams.password;
			usuario.session_id = jsonParams.password;
			usuario.nombre = jsonParams.nombre;
			usuario.rfc = jsonParams.rfc;
			usuario.sexo = jsonParams.sexo;
			usuario.telefono_Movil = jsonParams.telefono_movil;
			usuario.estatura = jsonParams.estatura;
			usuario.peso = jsonParams.peso as Integer;
			usuario.consumeAlcohol = jsonParams.consumeAlcohol;
			usuario.realizaDeportes = jsonParams.realizaDeportes;
			usuario.fechaNacimiento =datePrueba;
			usuario.save flush:true;
			resultado = "Exito";
		}
		render resultado;
    }
    
    @Transactional
	def setCrearPerfil() {
		def jsonParams = request.JSON
		String resultado = "Fallido";
		
		def formatString = "yyyy-MM-dd";
        def sdf = new SimpleDateFormat(formatString);
		def datePrueba = sdf.parse(jsonParams.fechaNacimiento);
		
		UserLG usuario = new UserLG();

		Empresa oficina = Empresa.findByNombre(jsonParams.empresa);

		usuario.username = jsonParams.user;
		usuario.password = jsonParams.password;
		usuario.session_id = jsonParams.password;
		usuario.nombre = jsonParams.nombre;
		usuario.telefono_Movil = jsonParams.telefono;
		usuario.ciudad = jsonParams.ciudad;
		usuario.empresa = oficina;
		usuario.tipoDieta = jsonParams.tipoDieta;
		usuario.sexo = jsonParams.sexo;
		usuario.fechaNacimiento = datePrueba;
		usuario.estatura = jsonParams.estatura;
		usuario.peso = jsonParams.peso;
		usuario.enabled = false;
		usuario.aceptaTerminos = true;
		usuario.save(failOnError: true, flush: true);
		
		//Asignando el rol cliente
		UserLGRoleLG.create usuario, RoleLG.findByAuthority("ROLE_CLIENTE")

		boolean ok = false;
		if(usuario){
			//print "Email a:"+oficina.contactoEmail;
			ok = true;
			resultado =  "Exito";
			sendMail {
				from "info@sambasmoothie.com"
			    to oficina.contactoEmail
			    subject "Activación de cuenta en Samba Smoothie"
			    html view: "/emailEmpresa/sendEmpresa", model:[email:usuario.username, password: usuario.nombre]
			}
		}

		redirect controller: "main", action: 'index', params: [sendPassword: ok.toString()]

		render resultado;
	}
	
	@Transactional
	def setPagoNomina() {
		def jsonParams = request.JSON
		String user_id = jsonParams.id.toString();
    	String solicitar = jsonParams.solicitar;
		String resultado = "Fallido";
		UserLG usuario;
		
		if (user_id.isInteger()) usuario = UserLG.findById(Integer.parseInt(user_id));
		
		if (usuario != null){
			usuario.decodePassword();
			usuario.solicitarPagoNomina = true;
			usuario.save flush:true;
			resultado = "Exito";
		}
		render resultado;
	}
	
	
    @Transactional
	def setCancelaPagoNomina() {
		def jsonParams = request.JSON
		String user_id = jsonParams.id.toString();
    	String solicitar = jsonParams.solicitar;
		String resultado = "Fallido";
		UserLG usuario;
		
		if (user_id.isInteger()) usuario = UserLG.findById(Integer.parseInt(user_id));
		
		if (usuario != null){
			usuario.decodePassword();
			usuario.solicitarPagoNomina = false;
			usuario.pagoNomina = false;
			usuario.save flush:true;
			resultado = "Exito";
		}
		render resultado;
	}
	
	@Transactional
	def setTipoDieta() {
		def jsonParams = request.JSON
		String user_id = jsonParams.id.toString();
    	String dieta = jsonParams.tipoDieta;
		String resultado = "Fallido";
		UserLG usuario;
		
		if (user_id.isInteger()) usuario = UserLG.findById(Integer.parseInt(user_id));
		
		if (usuario != null){
			usuario.decodePassword();
			usuario.tipoDieta = dieta;
			usuario.save flush:true;
			resultado = "Exito";
		}
		render resultado;
	}
	
	def recuperaPassword() {
		
    	def email = params.email;
    	String resultado = "Error";	
       			
		UserLG usuario = UserLG.find("from UserLG as u WHERE u.username = '" + email + "' AND enabled=true");
			   
		boolean ok = false;
		if(usuario){
		print "Email a:"+usuario.username;
			ok = true;
			resultado =  "Exito";
			sendMail {
				from "info@sambasmoothie.com"
			    to usuario.username
			    subject "Datos de acceso a tu cuenta Samba Smoothie"
			    html view: "/email/sendPassword", model:[email:usuario.username, password: usuario.session_id]
			}
		}

		redirect controller: "main", action: 'index', params: [sendPassword: ok.toString()]
		
		render resultado;
    }
}
