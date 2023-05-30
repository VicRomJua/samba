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

@Resource(uri='/WS_Empresa', formats=['json', 'xml'])
class WS_Empresa {

    String  id
    String	codigo
    String  nombre
    String  razonSocial
    String  giro
    int		noEmpleados
    String  archivo_Foto
    String	url
    String  domicilio
    String  rfc
    String  ciudad
    String  cp
    String  telefono
    String  contacto
    String  contactoCargo
    String  contactoTelefono
    String  contactoEmail
    String  horaEntrega_1
    String  horaEntrega_2
    String  horaEntrega_3
    Date    dateCreated
    Date    lastUpdated
    UserLG  id_Autor
	boolean	activo = Boolean.TRUE
	
	static  belongsTo = [establecimiento:Establecimiento]
	
    static constraints = {
		establecimiento	(nullable: false)
		codigo          (nullable: false, unique: true, size: 4..4)
        nombre          (nullable: false, unique: true)
        razonSocial     (nullable: false)
        giro		    (nullable: false, inList: ["Manufactura", "Servicios" , "Minería", "Pesca", "Bienes raíces", "Construcción", "Ganadería", "Turismo", "Software", "Telecomunicaciones", "Metalurgia", "Cinematográfica", "Editorial", "Electricidad", "Otros"])
        noEmpleados		(nullable: false)
        rfc             (nullable: false, size: 12..13)
        archivo_Foto    (nullable: true, widget:'icon')
        url				(nullable: true)
        domicilio       (nullable: true, widget: 'textarea')
        ciudad          (blank: false, nullable: false)
        cp              (nullable: true, size: 5..5) /*, matches: "[0-9]*"*/
	    telefono        (nullable: true, size: 7..20) /*, matches: "[\\s-()0-9]*"*/
        contacto        (nullable: true)
        contactoCargo   (nullable: true)
        contactoTelefono (nullable: false, size: 7..20) /*, matches: "[\\s-()0-9]*"*/
        contactoEmail   (nullable: false, email: true)
        horaEntrega_1   (nullable: true, size: 7..8) /* , matches: "\\d{2}:\\d{2}\\s*[APap][Mm]" */
        horaEntrega_2   (nullable: true, size: 7..8) /* , matches: "\\d{2}:\\d{2}\\s*[APap][Mm]" */
        horaEntrega_3   (nullable: true, size: 7..8) /* , matches: "\\d{2}:\\d{2}\\s*[APap][Mm]" */
        dateCreated     (widget:'display')
        lastUpdated     (widget:'display')
        id_Autor        (nullable:true, widget:'display')
    }

    static mapping = {
		sort nombre: "asc"
        table 'EMPRESAS'
        version false
        id column: "ID", generator : 'uuid'
        domicilio column:'domicilio', sqlType: 'text'
        id_Autor column:'id_autor'
    }

    String toString() {
        return nombre
    }
}
