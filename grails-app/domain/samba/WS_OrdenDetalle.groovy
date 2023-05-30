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

@Resource(uri='/WS_OrdenDetalle', formats=['json', 'xml'])
class WS_OrdenDetalle {

    String  id
    int		cantidad
    
    Float	montoPagado
    String	modoPago
    Date    fechaPago
    boolean	realmentePagado = Boolean.FALSE
    
    Date    fechaEntrega
    String	horaEntrega
    
    String	estatus
    
	boolean	esPersonalizado = Boolean.FALSE
	String	personalizado = ""
    Date    dateCreated
    Date    lastUpdated
    UserLG  id_Autor
    Establecimiento establecimiento
    
    static  belongsTo = [platillo:PlatilloEstablecimiento, embarque:Embarque, orden: Orden]
	
    static constraints = {
		platillo		(nullable: false)
		orden		    (nullable: true)
		embarque	    (nullable: true)
        cantidad        (nullable: false)
        montoPagado     (nullable: false)
        modoPago        (nullable: false, inList: ["Por n\u00f3mina", "Por saldo"])
        fechaPago       (nullable: false)
        realmentePagado	(nullable: true)
        fechaEntrega    (nullable: false)
        horaEntrega     (nullable: false)
        estatus         (nullable: false, inList: ["Nuevo", "Cocinando", "Preparado" ,"En tránsito", "Retrasado", "Cancelado", "Entregado"])
		esPersonalizado	(nullable: false)
		personalizado	(nullable: true, widget: 'textarea')
        dateCreated     (widget:'display')
        lastUpdated     (widget:'display')
        id_Autor        (nullable:true, widget:'display')
    }

    static mapping = {
		sort platillo: "asc"
        table 'ORDENESDETALLES'
        version false
        id column: "ID", generator : 'uuid'
        observacion column:'observacion', sqlType: 'text'
        id_Autor column:'id_autor'
    }
}
