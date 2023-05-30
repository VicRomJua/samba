package samba

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

@Resource(uri='/WS_Embarque', formats=['json', 'xml'])
class WS_Embarque {

    String  id
	String	codigo
	String	archivo_Evidencia
	UserLG	id_Repartidor
	int		demora = 0
    Date    fechaHoraEntrega
    String	estatus = "Nuevo"
    Date    dateCreated
    Date    lastUpdated
    UserLG  id_Autor
	
	static  belongsTo = [establecimiento: Establecimiento, empresa: Empresa]
	static  hasMany = [ordenes:OrdenDetalle]
	
    static constraints = {
		codigo				(nullable:true, unique: true, widget:'display')
		archivo_Evidencia   (nullable: true, widget:'icon')
        establecimiento     (nullable: false)
        id_Repartidor     	(nullable:true, widget:'display')
        empresa     		(nullable: false)
        fechaHoraEntrega    (nullable: false)
        estatus         	(nullable: false, inList: ["Nuevo", "Cocinando", "Preparado" ,"En tr\u00e1nsito", "Retrasado", "Cancelado", "Entregado"])
        dateCreated     	(widget:'display')
        lastUpdated     	(widget:'display')
        id_Autor        	(nullable:true, widget:'display')
    }

    static mapping = {
		table 'EMBARQUES'
        version false
        id column: "ID", generator : 'uuid'
        id_Autor column:'id_autor'
		ordenes cascade: "all-delete-orphan"
    }

}
