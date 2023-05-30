package samba

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='authority')
@ToString(includes='authority', includeNames=true, includePackage=false)
class RoleLG implements Serializable {

	private static final long serialVersionUID = 1

	String	nombre
	String	authority
	Date	dateCreated
    Date	lastUpdated
    UserLG	id_Autor

	RoleLG(String nombre, String authority) {
		this()
		this.nombre = nombre
		this.authority = authority
	}

	static constraints = {
		authority		(blank: false, unique: true)
		nombre			(nullable:false)
		dateCreated		(widget:'display')
        lastUpdated		(widget:'display')
        id_Autor		(nullable:true, widget:'display')
	}

	static mapping = {
		sort nombre: "asc"
		cache true
		id_Autor column:'id_autor'
	}

	String toString() {
        return nombre
    }
}
