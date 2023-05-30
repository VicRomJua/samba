package samba

import javax.persistence.CascadeType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import grails.rest.*

@Resource(uri='/WS_Orden', formats=['json', 'xml'])
class WS_Orden {

    String  id
    String  noOrden
    Float	montoPagado
    String	modoPago
    Date    fechaPago
    Date    dateCreated
    Date    lastUpdated
    UserLG  id_Autor
    
    String	estatus
    
    int		calificacionServicio
    int		calificacionComida
    String	calificacionComentario
	
	static  belongsTo = [establecimiento: Establecimiento, empresa: Empresa]
	static hasMany = [detalles: OrdenDetalle]
	
    static constraints = {
        noOrden         (nullable: false)
        establecimiento (nullable: false)
        empresa			(nullable: false)
        montoPagado     (nullable: false)
        modoPago        (nullable: false, inList: ["Por Nómina", "Por saldo"])
        fechaPago       (nullable: false)
        detalles		(nullable: true)
        estatus         (nullable: false, inList: ["Nuevo", "Cocinando", "Preparado" ,"En tránsito", "Retrasado", "Cancelado", "Entregado"])
        dateCreated     (widget:'display')
        lastUpdated     (widget:'display')
        id_Autor        (nullable:true, widget:'display')
    }

    static mapping = {
		sort noOrden: "desc"
        table 'ORDENES'
        version false
        id column: "ID", generator : 'uuid'
        id_Autor column:'id_autor'
    }

    String toString() {
        return noOrden
    }
}
