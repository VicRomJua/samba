package samba

class Establecimiento {

    String  id
    String	codigo
    String  nombre
    String  domicilio
    String  ciudad
    String  cp
    String	telefono
    float	totalVendido = 0.0
    Date    dateCreated
    Date    lastUpdated
    
    UserLG	administrador
	//static hasOne = [administrador: UserLG]
	//static belongsTo = [administrador:UserLG]
	
	UserLG  id_Autor
	
    static constraints = {
        codigo          (nullable: false, unique: true, size: 4..4)
        nombre          (nullable: false, unique: true)
        administrador	(blank: false, nullable: false)
        telefono		(nullable: true) /* , matches: "[\\s-()0-9]*" */
        domicilio       (nullable: true, widget: 'textarea')
        ciudad          (blank: false, nullable: false)
        cp              (nullable: true, size: 5..5) /* , matches: "[0-9]{5}" */
        dateCreated     (widget:'display')
        lastUpdated     (widget:'display')
        totalVendido    (nullable: true, widget:'display')
        id_Autor        (nullable:true, widget:'display')
    }

    static mapping = {
		sort nombre: "asc"
        table 'ESTABLECIMIENTOS'
        version false
        id column: "ID", generator : 'uuid'
        domicilio column:'domicilio', sqlType: 'text'
        id_Autor column:'id_autor'
    }
	
    String toString() {
        return nombre
    }
}
