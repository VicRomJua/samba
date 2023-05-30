package samba

class Transaccion {
    String  id
    Float   monto
    Date    fechaOperacion	
    
    String	tipoTransaccion
    String	referencia
    String  id_orden
    
    String  tipoMedioPago
    String  estatus
	
    Date    dateCreated
    Date    lastUpdated
    UserLG  id_Autor

    static constraints = {
        monto           (nullable: false)
        fechaOperacion  (widget:'display')
        
        tipoTransaccion	(nullable: false, inList: ["Ingreso", "Egreso", "Reembolso"])
        referencia		(nullable: true)
        id_orden        (nullable: true)
        
        tipoMedioPago   (nullable: false, inList: ["Tarjeta bancaria", "Sucursal Oxxo", "SPEI", "Por saldo", "Por n\u00f3mina"])
        estatus         (nullable: false, inList: ["Exitoso", "Pendiente", "Consulta", "Fallido", "Expirado"])

        dateCreated     (widget:'display')
        lastUpdated     (widget:'display')
        id_Autor        (nullable:true, widget:'display')
    }

    static mapping = {
        table 'TRANSACCIONES'
        version false
        id column: "ID", generator : 'uuid'
        id_Autor column:'id_autor'
    }

    String toString() {
        return nombre
    }
}
