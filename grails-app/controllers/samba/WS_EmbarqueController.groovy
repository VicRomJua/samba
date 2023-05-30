package samba

import grails.rest.RestfulController
import grails.converters.JSON
import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*
import grails.plugin.springsecurity.SpringSecurityUtils
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Transactional(readOnly = true)
class WS_EmbarqueController extends RestfulController <WS_Embarque>{
    static responseFormats = ['json', 'xml']
	
    WS_EmbarqueController(){
		super(WS_Embarque)
	}
	
    def index() {
        respond WS_Embarque.list()
    }
	
	@Transactional
	def runJob() {
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        DateFormat hourFormat = new SimpleDateFormat("HHmm");

        // fecha de entrega en 4 dias
        def entrega = new Date() + 4
        entrega.clearTime()
        //println "Procesando embarques para " + entrega
        // buscar ordenes a embarcar
        def detalles = OrdenDetalle.findAll {
		     estatus == "Nuevo"
             fechaEntrega == entrega
             embarque == null
        }

        for(detalle in detalles) {
            def horario = detalle.horaEntrega
			//println "horario:" + horario;
			//if (detalle.horaEntrega == detalle.orden.empresa.horaEntrega_1)
			
			def format_date = "yyyy-MM-dd hh:mm a";
			def mask_date = new SimpleDateFormat(format_date);
			def fechahora = mask_date.parse(entrega.format('yyyy-MM-dd')+" "+horario);
			
			//println "Entrega:" + entrega.format('yyyy-MM-dd')+" "+horario;
			//println "Entrega:" + fechahora;
			
            // revisar si el embarque existe
            def embarque = Embarque.find {
                establecimiento == detalle.orden.establecimiento
                empresa == detalle.orden.empresa
                fechaHoraEntrega == fechahora //entrega
            }

            // si no hay embarque, crearlo
            if(!embarque) {
                embarque = new Embarque()
                embarque.fechaHoraEntrega = fechahora //entrega
                embarque.codigo = dateFormat.format(embarque.fechaHoraEntrega) + detalle.orden.empresa.codigo + hourFormat.format(fechahora)
                embarque.empresa = detalle.orden.empresa
                embarque.establecimiento = detalle.orden.establecimiento
            }

            embarque.save flush:true
			
            detalle.embarque = embarque
            detalle.save  flush:true
        }
        return true
    }
	
	@Transactional
	def asignarEmbarque() {
		
    	def jsonParams = request.JSON
	
		def id_Embarque = jsonParams.id_Embarque;
		def id_Repartidor = jsonParams.id_Repartidor;
		Embarque embarque = Embarque.findById(id_Embarque);
		if (embarque != null){
	     	embarque.id_Repartidor =  UserLG.findById(jsonParams.id_Repartidor);
			embarque.estatus = "En tr\u00e1nsito"
			embarque.save flush:true
			//Hereda el estatus a los hijos
		    def query = OrdenDetalle.createCriteria().list() {
                 eq("embarque.id", id_Embarque)     
            }
			for(item in query){
			   Orden datos = Orden.findById(item.orden.id);
		
			   if (datos != null){
				   datos.estatus = "En tr\u00e1nsito"
				   datos.save flush:true
			   
			   }
			}
		}
		render "OK"
	}
	
	@Transactional
	def desasignarEmbarque() {
	
    	def jsonParams = request.JSON
	
		def id_Embarque = jsonParams.id_Embarque;
		def id_Repartidor = jsonParams.id_Repartidor;
		Embarque embarque = Embarque.findById(id_Embarque);
		if (embarque != null){
	     	embarque.id_Repartidor =  UserLG.findById(jsonParams.id_Repartidor);
			embarque.estatus = "Preparado"
			embarque.save flush:true
			//Hereda el estatus a los hijos
		    def query = OrdenDetalle.createCriteria().list() {
                 eq("embarque.id", id_Embarque)     
            }
			for(item in query){
			   Orden datos = Orden.findById(item.orden.id);
		
			   if (datos != null){
				   datos.estatus = "Preparado"
				   datos.save flush:true
			   
			   }
			}
		}
		render "OK"
	}
	
	@Transactional
	def entregadoEmbarque() {
	    def jsonParams = request.JSON
		def id_Embarque = jsonParams.id_Embarque;
		Embarque embarque = Embarque.findById(id_Embarque);
		if (embarque != null){
			embarque.estatus = "Entregado"
			embarque.save flush:true
			
			//Hereda el estatus a los hijos
		    def query = OrdenDetalle.createCriteria().list() {
                 eq("embarque.id", id_Embarque)     
            }
			for(item in query){
			   Orden datos = Orden.findById(item.orden.id);
		
			   if (datos != null){
				   datos.estatus = "Entregado"
				   datos.save flush:true
			   
			   }
			}
		}
		render "OK"
	}
	
	@Transactional
	def demoraEmbarque() {
	    def jsonParams = request.JSON
		def id_Embarque = jsonParams.id_Embarque;
		Embarque embarque = Embarque.findById(id_Embarque);
		if (embarque != null){
			int demora = 0;
			try{
				demora = Integer.parseInt(jsonParams.demora.toString());
			} catch(NumberFormatException e) {
			  // handle here
			}
			embarque.demora = demora;
			embarque.estatus = "Retrasado";
			embarque.save flush:true
			//Hereda el estatus a los hijos
		    def query = OrdenDetalle.createCriteria().list() {
                 eq("embarque.id", id_Embarque)     
            }
			for(item in query){
			   Orden datos = Orden.findById(item.orden.id);
		
			   if (datos != null){
				   datos.estatus = "Retrasado"
				   datos.save flush:true
			   
			   }
			}
		}
		render "OK"
	}
	
	@Transactional
	def fotoUpload(){
		//Subir el archivo al servidor
		def file = request.getFile('file')
		if (!file.empty) {
			if (file.getInputStream() != null){
			
		        def  nombreArchivo      = file.originalFilename.split('@');
			
				def directory		= grailsApplication.config.uploadFolder + "mes-evidencia/";
				def fileName		= nombreArchivo[0];
				String extension	= fileName.substring(fileName.lastIndexOf("."));
				//def minutesName	= Math.round(System.currentTimeMillis()/1000/60);
			    def minutesName		= System.currentTimeMillis();
				
				file.transferTo(new File( directory+"Evidencia_" + minutesName.toString() + extension ));
				
				def nombreArchivoServidor = "Evidencia_" + minutesName.toString() + extension;
		        def idEmbarque = nombreArchivo[1];
				
				Embarque embarque = Embarque.findById(idEmbarque);
				if (embarque != null){
					
					//Guardado de los datos de entrega
					embarque.archivo_Evidencia = nombreArchivoServidor;
					embarque.estatus = "Entregado"
					embarque.save flush:true
					
					//Hereda el estatus a los hijos
					def query = OrdenDetalle.createCriteria().list() {
						 eq("embarque.id", idEmbarque)     
					}
					for(item in query){
					   Orden datos = Orden.findById(item.orden.id);
				
					   if (datos != null){
						   datos.estatus = "Entregado"
						   datos.save flush:true
					   
					   }
					}
				}
				
			}	
		}
		render "OK"
	}
	
	@Transactional
    def fotoEmbarque() {
	    def jsonParams = request.JSON
		def id_Embarque = jsonParams.id_Embarque;
		Embarque embarque = Embarque.findById(id_Embarque);
		if (embarque != null){
			
			//Guardado de los datos de entrega
			embarque.archivo_Evidencia = jsonParams.foto
			embarque.estatus = "Entregado"
			embarque.save flush:true
			
			//Hereda el estatus a los hijos
		    def query = OrdenDetalle.createCriteria().list() {
                 eq("embarque.id", id_Embarque)     
            }
			for(item in query){
			   Orden datos = Orden.findById(item.orden.id);
		
			   if (datos != null){
				   datos.estatus = "Entregado"
				   datos.save flush:true
			   
			   }
			}
		}
		render "OK"
	}
	
   
   	def obtenEmbarques() { 
        def id = params.id
            	   	
        def query = Embarque.where { 
            establecimiento.id ==  id && estatus=='Preparado'
		}
		
	   	if(query != null){
           respond query.list(sort:"fechaHoraEntrega")
        }else{
	     respond []
	   	}		             
    }
	
	
	def obtenMisEmbarques() {
        def id = params.id
            	   	
        def query = Embarque.where { 
           id_Repartidor.id ==  id && (estatus=='En tr\u00e1nsito' || estatus=='Retrasado')
		} 
		
	   	if(query != null){
           respond query.list(sort:"fechaHoraEntrega")
        }else{
	     respond []
	   	}		             
    }
}
