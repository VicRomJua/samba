package samba

import grails.rest.*

@Resource(uri='/WS_Categoria', formats=['json', 'xml'])
class WS_Categoria {

    String  id
    String  nombre
    String  descripcion
    String  archivo_Foto
    Date    dateCreated
    Date    lastUpdated
    UserLG  id_Autor
	
    static constraints = {
		archivo_Foto    (nullable: true, widget:'icon')
        nombre          (nullable: false)
        descripcion     (nullable: true, widget: 'textarea')
        dateCreated     (widget:'display')
        lastUpdated     (widget:'display')
        id_Autor        (nullable:true, widget:'display')
    }

    static mapping = {
		sort nombre: "asc"
        table 'CATEGORIAS'
        version false
        id column: "ID", generator : 'uuid'
        descripcion column:'descripcion', sqlType: 'text'
        id_Autor column:'id_autor'
    }

    String toString() {
        return nombre
    }
}
