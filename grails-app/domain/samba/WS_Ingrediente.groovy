package samba

import grails.rest.*

@Resource(uri='/WS_Ingrediente', formats=['json', 'xml'])
class WS_Ingrediente {

    //Atributos de la tabla
    String	id 
    String	nombre
    String  archivo_Foto
    String	unidadMedicion
    Date	dateCreated
    Date	lastUpdated
    UserLG	id_Autor
    
    //Validaciones
    static constraints = {
        id				(nullable:true)
        archivo_Foto    (nullable: true, widget:'icon')
        nombre			(nullable:false)
        unidadMedicion	(nullable:true)
        dateCreated		(widget:'display')
        lastUpdated		(widget:'display')
        id_Autor		(nullable:true, widget:'display')
    }

    //Vinculación con la tabla de la BD
    static mapping = {
		sort nombre: "asc"
        table 'INGREDIENTES'
        version false
        id column: "ID", generator : 'uuid'
        id_Autor column:'id_autor'
    }
    
    String toString() {
        return nombre
    }
}
