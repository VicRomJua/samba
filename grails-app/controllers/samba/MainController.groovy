package samba

import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus
import grails.transaction.*
import grails.converters.*
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Transactional(readOnly = true)
class MainController {
    //Identificación de la sesion del usuario
    def springSecurityService;
    
    def index(Integer max) {
        
        def principal = springSecurityService.principal
		
		def establecimientosList = Establecimiento.createCriteria().list () {
			order("totalVendido","desc")
        }
		
		def platillosList = Platillo.list ();
        if (!principal.authorities.toString().equals("[ROLE_PROPIETARIO]") ){
			platillosList = PlatilloEstablecimiento.createCriteria().list () {
				if ( session.getAttribute("username_establecimiento_id") != "" ) {
					eq("establecimiento.id", session.getAttribute("username_establecimiento_id"))
				}
			}
        }
        
        def empresasList = Empresa.createCriteria().list () {
            if ( session.getAttribute("username_establecimiento_id") != "" ) {
				eq("establecimiento.id", session.getAttribute("username_establecimiento_id"))
            }
        }
        
        def ordenList = Orden.list ();
        if (!principal.authorities.toString().equals("[ROLE_PROPIETARIO]") ){
			ordenList = Orden.createCriteria().list () {
				if ( session.getAttribute("username_establecimiento_id") != "" ) {
					eq("establecimiento.id", session.getAttribute("username_establecimiento_id"))
				}
			}
        }
        Date date = new Date();
		DateFormat mesFormat = new SimpleDateFormat("MM/YYYY");
        String mes = mesFormat.format(date);
		DateFormat diaFormat = new SimpleDateFormat("dd/MM/YYYY");
        String dia = diaFormat.format(date);
		
		def cocinaList = Establecimiento.list();
		def ordenesCocinaList = [];
		for(int x = 0; x < cocinaList.size(); x++){
			def numOrden = Orden.createCriteria().list () {
				sqlRestriction "to_char(date_created,'dd/MM/YYYY') LIKE '%${dia}%'"
				eq("establecimiento", cocinaList[x])
			}
			def  ventaOrden = Orden.createCriteria().list () {
				sqlRestriction "to_char(date_created,'MM/YYYY') LIKE '%${mes}%'"
				eq("establecimiento", cocinaList[x])
			}
			ordenesCocinaList.add([cocina: cocinaList[x].nombre, ordenDia: numOrden, ventaMensual: ventaOrden ? ventaOrden.montoPagado.sum(): 0.0])
		}
		if(ordenesCocinaList.size > 0){			
			def onlyFive = ordenesCocinaList.sort{a,b-> b.ventaMensual<=>a.ventaMensual};
			ordenesCocinaList = [];
			for(int x = 0; x < onlyFive.size(); x++){
				if(ordenesCocinaList.size < 5){
					ordenesCocinaList.add(onlyFive[x])
				}
			}
		}
		
		DateFormat postgresqlFormat = new SimpleDateFormat("YYYY-MM-dd");
		int diaActual = date.getDay();
		Date inicioSemana = new Date() - diaActual;
		Date finSemana = new Date() + (6-diaActual);
	
		def diario = Orden.createCriteria().list () {
			sqlRestriction "to_char(date_created,'dd/MM/YYYY') LIKE '%${dia}%'"
			order("dateCreated","desc")
		}				
		def semanal = Orden.createCriteria().list () {
			sqlRestriction "date_created BETWEEN '${postgresqlFormat.format(inicioSemana)}' AND '${postgresqlFormat.format(finSemana)}'"
			order("dateCreated","desc")
		}
		def	mensual = Orden.createCriteria().list () {
			sqlRestriction "to_char(date_created,'MM/YYYY') LIKE '%${mes}%'"
			order("dateCreated","asc")
		}		
	
		if (!principal.authorities.toString().equals("[ROLE_PROPIETARIO]") ){
			diario = Orden.createCriteria().list () {
				sqlRestriction "to_char(date_created,'dd/MM/YYYY') LIKE '%${dia}%'"
				eq("establecimiento.id", session.getAttribute("username_establecimiento_id"))
				order("dateCreated","desc")
			}	
			semanal = Orden.createCriteria().list () {
				sqlRestriction "date_created BETWEEN '${postgresqlFormat.format(inicioSemana)}' AND '${postgresqlFormat.format(finSemana)}'"
				eq("establecimiento.id", session.getAttribute("username_establecimiento_id"))
				order("dateCreated","desc")
			}		
			mensual = Orden.createCriteria().list () {
				sqlRestriction "to_char(date_created,'MM/YYYY') LIKE '%${mes}%'"
				eq("establecimiento.id", session.getAttribute("username_establecimiento_id"))
				order("dateCreated","asc")
			}			
		}
				
		def resumenDSM = [diario: diario ? diario.montoPagado.sum() : 0.0, mensual: mensual ? mensual.montoPagado.sum() : 0.0, semanal: semanal ? semanal.montoPagado.sum() : 0.0]
		
		def puntosDiarios = [];	
		def puntosSemanales = [];
		def puntosMensuales = [];	
		
		DateFormat puntoDFormat = new SimpleDateFormat("HH");
		DateFormat puntoSFormat = new SimpleDateFormat("dd");
		DateFormat puntoMFormat = new SimpleDateFormat("Y,M,d");
		
		int montoTotal = 0;
		for (int h = 0; h < 24; h++){
			String hora = h < 10 ? "0" + h : h;
			montoTotal = 0;
			for(int x=0; x < diario.size(); x++){
				if(hora == puntoDFormat.format(diario[x].dateCreated).toString()){
					montoTotal = montoTotal + diario[x].montoPagado;
				}
			}
			puntosDiarios.add(h: hora, v: montoTotal)
		}
		
		for(int d = 0; d < 7; d++){
			montoTotal = 0;
			for(int x=0; x < semanal.size(); x++){
				if(d == semanal[x].dateCreated.getDay()){
					montoTotal = montoTotal + semanal[x].montoPagado;
				}
			}
			puntosSemanales.add(d: d, v: montoTotal)
		}
		
		Calendar c = Calendar.getInstance();
		String dayTotal = puntoMFormat.format(date);
		def dt = dayTotal.split(",")
		c.set(Integer.parseInt(dt[0]), Integer.parseInt(dt[1]) + 1, Integer.parseInt(dt[2])); 
		int days = c.getActualMaximum(Calendar.DAY_OF_MONTH); 
		
		for(int dm = 1; dm <= days; dm++){
			montoTotal = 0;
			for(int x=0; x < mensual.size(); x++){
				if(dm == mensual[x].dateCreated.getDate()){
					montoTotal = montoTotal + mensual[x].montoPagado;
				}
			}
			puntosMensuales.add(d: dm, v: montoTotal)
		}
		
        Date from = new Date() + (diaActual - 30);
		Date to = new Date() + 1;
		def puntosMain = [];
		
		def graphicMain = Orden.createCriteria().list () {
			sqlRestriction "date_created BETWEEN '${postgresqlFormat.format(from)}' AND '${postgresqlFormat.format(to)}'"
		}	
		if (!principal.authorities.toString().equals("[ROLE_PROPIETARIO]") ){
			graphicMain = Orden.createCriteria().list () {
				sqlRestriction "date_created BETWEEN '${postgresqlFormat.format(from)}' AND '${postgresqlFormat.format(to)}'"
				eq("establecimiento.id", session.getAttribute("username_establecimiento_id"))
			}
		}
		for(Date d = from; d < to; d++){
			montoTotal = 0;
			for(int x=0; x < graphicMain.size(); x++){
				
				if(postgresqlFormat.format(d) == postgresqlFormat.format(graphicMain[x].dateCreated)){
					montoTotal = montoTotal + graphicMain[x].montoPagado;
				}
			}
			puntosMain.add(d: d.getTime(), v: montoTotal)
		}
        
		def resenia = ordenList ? Math.round(ordenList.calificacionServicio.sum() / ordenList.size) : 0;
		
		def ordenDetalleList = OrdenDetalle.list();
        if (!principal.authorities.toString().equals("[ROLE_PROPIETARIO]") ){
			ordenDetalleList = OrdenDetalle.createCriteria().list () {
				if ( session.getAttribute("username_establecimiento_id") != "" ) {
					eq("establecimiento.id", session.getAttribute("username_establecimiento_id"))
				}
			}
        }
		def horaEntregaList = ordenDetalleList ? ordenDetalleList.horaEntrega : [];
		if(horaEntregaList.size() > 0){
			horaEntregaList.unique()
		}
		
		def horarios = [];
		int repeticiones = 0;		
		for (int h = 0; h < horaEntregaList.size(); h++){
			repeticiones = 0;
			for(int x=0; x < ordenDetalleList.size(); x++){
				if(horaEntregaList[h] == ordenDetalleList[x].horaEntrega){
					repeticiones++;
				}
			}
			horarios.add(hora: horaEntregaList[h], repeticiones: repeticiones)
		}
		
		if(horarios.size > 0){			
			def ordenHorario = horarios.sort{a,b-> b.repeticiones<=>a.repeticiones};
			horarios = [];
			horarios = ordenHorario[0].hora;
		
		}
		
        respond establecimientosList, 
		model:[
				establecimientoCount: establecimientosList.size, 
				platillosCount: platillosList.size,
				empresasCount: empresasList.size,
				ordenCount: ordenList.size, 
				ventasCount: ordenList, 
				cocina: ordenesCocinaList,
				resumen: resumenDSM,
				ventasDiarias: puntosDiarios,
				ventasSemanales: puntosSemanales,
				ventasMensuales: puntosMensuales,
				ventasResumen: puntosMain,
				calificacionCount: resenia,
				horariosCount: horarios
			  ]
    }
	
	@Transactional
	def changeRange() {
		
		DateFormat postgresqlFormat = new SimpleDateFormat("YYYY-MM-dd");
		
		Date from = Date.parse("yyyy-MM-dd",params.finicio);
		Date to = Date.parse("yyyy-MM-dd",params.ffinal) + 1;
		
		def puntosMain = [];
		
		def graphicMain = Orden.createCriteria().list () {
			sqlRestriction "date_created BETWEEN '${postgresqlFormat.format(from)}' AND '${postgresqlFormat.format(to)}'"
		}	
		if (!principal.authorities.toString().equals("[ROLE_PROPIETARIO]") ){
			graphicMain = Orden.createCriteria().list () {
				sqlRestriction "date_created BETWEEN '${postgresqlFormat.format(from)}' AND '${postgresqlFormat.format(to)}'"
				eq("establecimiento.id", session.getAttribute("username_establecimiento_id"))
			}
		}
		int montoTotal = 0;
		for(Date d = from; d < to; d++){
			montoTotal = 0;
			for(int x=0; x < graphicMain.size(); x++){
				if(postgresqlFormat.format(d) == postgresqlFormat.format(graphicMain[x].dateCreated)){
					montoTotal = montoTotal + graphicMain[x].montoPagado;
				}
			}
			puntosMain.add(d: d.getTime(), v: montoTotal)
		}
		//respond puntosMain
		render puntosMain as JSON
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
