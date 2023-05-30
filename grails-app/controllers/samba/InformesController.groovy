package samba

import java.text.DateFormatSymbols;
import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*

/*import org.apache.commons.io.FileUtils
import grails.plugins.jasper.JasperExportFormat
import grails.plugins.jasper.JasperReportDef*/

@Transactional(readOnly = true)
class InformesController {
    //Identificación de la sesion del usuario
    def springSecurityService;
    //def jasperService 
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
	/*  IGB  Inicio */
    def platillos_mas_vendidos_por_cocina(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
        def partes = servidor.tokenize('/:')
        servidor = partes[0]
        session.setAttribute("servidor", servidor)
    }

    def platillos_mas_vendidos(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
        def partes = servidor.tokenize('/:')
        servidor = partes[0]
        session.setAttribute("servidor", servidor)
    }
    
	def ventas_por_establecimiento_por_dia(){
		String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
	}
	
    def consumos_por_empresa(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
        def partes = servidor.tokenize('/:')
        servidor = partes[0]
        session.setAttribute("servidor", servidor)
        
        //Set mesesConDatos = consultaMesesConDatos();
        //println mesesConDatos;

       // [mesesConDatos: mesesConDatos]        
    }
	
    def consumos_por_empresa_cocina(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
        def partes = servidor.tokenize('/:')
        servidor = partes[0]
        session.setAttribute("servidor", servidor)
        //Set mesesConDatos = consultaMesesConDatos();
        //[mesesConDatos: mesesConDatos]         
    }

		def consumos_por_nomina_empresa_cocina(){
		String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
		//Set mesesConDatos = consultaMesesConDatos();
		//[mesesConDatos: mesesConDatos]         
	}

		def consumos_por_nomina_empresa(){
		String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
		//Set mesesConDatos = consultaMesesConDatos();
		//[mesesConDatos: mesesConDatos]         
	}
	
    def platillos_proxima_semana(){
		String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)

		Calendar fechaActual = Calendar.getInstance();
		def hoy = new Date()
		int diaDeLaSemana = fechaActual.get(Calendar.DAY_OF_WEEK);
		def proximoLunes = new Date()
		def proximoSabado  = new Date()
		switch (diaDeLaSemana) {
			case 1: proximoLunes = proximoLunes + 1; break;
			case 2: proximoLunes = proximoLunes + 7; break;
			case 3: proximoLunes = proximoLunes + 6; break;
			case 4: proximoLunes = proximoLunes + 5; break;
			case 5: proximoLunes = proximoLunes + 4; break;
			case 6: proximoLunes = proximoLunes + 3; break;
			case 7: proximoLunes = proximoLunes + 2; break;
		}        
		switch (diaDeLaSemana) {
			case 1: proximoSabado = proximoSabado + 6; break;
			case 2: proximoSabado = proximoSabado + 12; break;
			case 3: proximoSabado = proximoSabado + 11; break;
			case 4: proximoSabado = proximoSabado + 10; break;
			case 5: proximoSabado = proximoSabado + 9; break;
			case 6: proximoSabado = proximoSabado + 8; break;
			case 7: proximoSabado = proximoSabado + 7; break;
		}
		proximoLunes = proximoLunes.format("dd-MM-yyy");
		proximoSabado = proximoSabado.format("dd-MM-yyy");
		[hoy:hoy.format("dd-MM-yyy"), proximoLunes:proximoLunes, proximoSabado:proximoSabado]
	}    

	private Set consultaMesesConDatos() {
        //def mesesConDatos = [["etiqueta:Enero 2015", "valor:01/2015"], ["etiqueta:Febrero 2015", "valor:02/2015"]] as Set;
        def criteria = OrdenDetalle.createCriteria()
        def result = criteria.list {
            projections {
                max('fechaPago')
                min('fechaPago')
            }
        }
        def anioMaximo = result[0][0].toString().substring(0,4).toInteger()
        def anioMesMaximo = result[0][0].toString().substring(6,7).toInteger()
        def anioMinimo  = result[0][1].toString().substring(0,4).toInteger()
        def anioMesMinimo  = result[0][1].toString().substring(6,7).toInteger()
        //println "The maximum fechaPago is ${result[0][0]}" + "  anioMaximo " + anioMaximo + "  anioMesMaximo " + anioMesMaximo 
        //println "The minimum fechaPago is ${result[0][1]}"+ "  anioMinimo " + anioMinimo + "  anioMesMinimo " + anioMesMinimo
        def anioActual = 0
        def mesActual = 0
        def mesActualTxt = ""
        mesActual = anioMesMinimo
        def mesesConDatos = [] as Set;
        for (anioActual = anioMinimo; anioActual <=anioMaximo; anioActual++) {
            //println("Anio Actual " + anioActual)
            if (anioActual!=anioMaximo){
                for (mesActual; mesActual <=12; mesActual++) {
                   mesActualTxt = new DateFormatSymbols().getMonths()[mesActual-1].capitalize();
                   //println("Anio Actual " + anioActual + " Mes actual  " + mesActualTxt)
                   mesesConDatos.add([etiqueta: "${mesActualTxt} ${anioActual} ", valor: "${mesActual}/${anioActual}"])    
                }
                mesActual = 1
            }else{
                for (mesActual; mesActual <=anioMesMaximo; mesActual++) {
                   mesActualTxt = new DateFormatSymbols().getMonths()[mesActual-1].capitalize();
                   //println("Anio Actual " + anioActual + " Mes actual  " + mesActualTxt)
                   mesesConDatos.add([etiqueta: "${mesActualTxt} ${anioActual}", valor: "${mesActual}/${anioActual}"])                      
                }                
            }
        }        
        return mesesConDatos;
    }
    /*  IGB  Fin */
	
	/*def pruebaInforme(){
		//try {
			//def reportDef = new JasperReportDef(name:'listado_embarques.jrxml', fileFormat:JasperExportFormat.PDF_FORMAT)
			//def reportDef = new JasperReportDef(name:'listado_embarques.jrxml', fileFormat:JasperExportFormat.HTML_FORMAT)
            //response.contentType = 'application/pdf'
			//response.outputStream << jasperService.generateReport(reportDef).toByteArray()			
		//} catch (Exception e) {
			//throw new RuntimeException("No es posible generar el reporte.", e); 
		//}
		
		params.put("_file", "listado_embarques.jrxml")
		params.put("_format", "PDF")
		//params.put("user_id", 1)

		def reportDef = jasperService.buildReportDefinition(params, request.getLocale(), [])
		response.contentType = 'application/pdf'
		response.outputStream << jasperService.generateReport(reportDef).toByteArray()

	}*/
		
	def listadoEmbarques(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
    }

    def listadoUsuarios(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
    }
    
    def listadoMenus(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
    }
    
    def listadoPlatillos(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
    }
    
    def listadoEmpresas(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
    }

	def listadoExtras(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
    }
    
    def listadoEstablecimientos(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
    }
    
    def listadoIngredientes(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
    }
    
    def listadoPagos(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
    }
    
    def listadoPagos_empresa(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
    }
    
    def listadoUsuarios_establecimiento(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
    }
    
    def listadoMenus_establecimiento(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
    }
    
    def listadoEmpresas_establecimiento(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
    }
    
    def listadoEmbarques_establecimiento(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
    }
    
    def informeVentas(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
    }
    
    def graficoVentas_establecimiento(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
    }
    
    def listadoPlatillosVendidos(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
    }
    
    def listadoPlatillosVendidos_establecimiento(){
        String servidor = request.getRequestURL().toString().replace('http://', '').replace('https://', '').replace('HTTP://', '').replace('HTTPS://', '')
		def partes = servidor.tokenize('/:')
		servidor = partes[0]
		session.setAttribute("servidor", servidor)
    }
    
    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'ingrediente.label', default: 'Ingrediente'), params.nombre])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
