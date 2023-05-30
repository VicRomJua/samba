package samba

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

class EmbarqueJob {
    static triggers = {
      cron cronExpression: '0 0,15,30,45,59 * * * ?'
    }

    def execute() {
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
}
