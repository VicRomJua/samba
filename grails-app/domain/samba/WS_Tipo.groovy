package samba

import grails.rest.*

@Resource(uri='/WS_Tipo', formats=['json', 'xml'])
class WS_Tipo {

    String  id
    String	superTipo
    String  nombre
    String  descripcion
    String  archivo_Foto
    Date    dateCreated
    Date    lastUpdated
    UserLG  id_Autor
	
    static constraints = {
		archivo_Foto    (nullable: true, widget:'icon')
		superTipo		(nullable: false, inList: ["Comidas", "Bebidas"])
        nombre          (nullable: false)
        descripcion     (nullable: true, widget: 'textarea')
        dateCreated     (widget:'display')
        lastUpdated     (widget:'display')
        id_Autor        (nullable:true, widget:'display')
    }

    static mapping = {
		sort nombre: "asc"
        table 'TIPOS'
        version false
        id column: "ID", generator : 'uuid'
        descripcion column:'descripcion', sqlType: 'text'
        id_Autor column:'id_autor'
    }

    String toString() {
        return nombre
    }
}
