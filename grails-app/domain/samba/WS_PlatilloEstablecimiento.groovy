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
import javax.validation.constraints.NotNull
import grails.rest.*

@Resource(uri='/WS_PlatilloEstablecimiento', formats=['json', 'xml'])
class WS_PlatilloEstablecimiento {

    String  id
    Float	costo
    Float	precio
    boolean	activo = Boolean.TRUE
    Date    dateCreated
    Date    lastUpdated
    UserLG  id_Autor
    
    static  belongsTo = [establecimiento:Establecimiento, platillo:Platillo]
	
    static constraints = {
        establecimiento	(nullable: false)
        platillo		(nullable: false)
        precio			(nullable: false)
        costo			(nullable: false)
        dateCreated     (widget:'display')
        lastUpdated     (widget:'display')
        id_Autor        (nullable:true, widget:'display')
    }

    static mapping = {
        table 'PLATILLOSESTABLECIMIENTOS'
        version false
        id column: "ID", generator : 'uuid'
        id_Autor column:'id_autor'
    }

}
