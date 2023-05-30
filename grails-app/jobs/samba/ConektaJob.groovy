package samba

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import io.conekta.*;
import org.json.JSONObject;

class ConektaJob {
    static triggers = {
        cron cronExpression: '0 */5 * * * ?'
    }

    def execute() {
        //println "ConektaJob..."
        //Recuperar todas las transacciones pendientes
        def transacciones = Transaccion.findAll {
             estatus == "Pendiente"
        }
        Conekta.setApiKey("key_pJEAU1R6G1Yf2gxmx8zthA");
        for (transaccion in transacciones) {
            transaccion.estatus = "Consulta"
            transaccion.save flush:true
            //println "id_orden:" + transaccion.id_orden
            //Consultar el estatus en conekta
            Order order = Order.find(transaccion.id_orden);
            //print order.charges.get(0).status
            
            if (order.charges.get(0).status=='paid'){
                //print "YA PAGARON!!!"
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
        }
        return true
    }
}
