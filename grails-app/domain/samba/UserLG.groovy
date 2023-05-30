package samba

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import java.util.regex.*

import com.bloomhealthco.jasypt.GormEncryptedStringType

@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class UserLG implements Serializable {
	private static final long serialVersionUID = 1
	
	//static hasOne = RoleLG
	
	transient springSecurityService
	
	String	username 
	String	password 
	String	session_id
	String	nombre
	String	rfc
	String	sexo
	Date	fechaNacimiento
	String	telefono_Movil
	String	archivo_Foto
	String	ciudad
	String	tipoDieta
	String	enfermedadesCronicas
	boolean	aceptaTerminos = Boolean.FALSE
	boolean	consumeAlcohol = Boolean.FALSE
	boolean	realizaDeportes = Boolean.FALSE
	int		peso
	float	estatura
	boolean	sesionActiva = Boolean.FALSE
	boolean	enabled = Boolean.TRUE
	boolean	pagoNomina = Boolean.FALSE
	boolean	solicitarPagoNomina = Boolean.FALSE
	float	montoLimite = 0.0
	float	montoAdeudo = 0.0
	float	montoSaldo = 0.0
	boolean	accountExpired
	boolean	accountLocked
	boolean	passwordExpired
	Date	dateCreated
    Date	lastUpdated
    Empresa empresa
    Establecimiento	establecimiento //No guarda el administrador en el establecimiento
    //static belongsTo = [establecimiento:Establecimiento] //Guarda en autor y administrador el establecimiento, y destrosa la contraseña
    //static hasOne = [establecimiento:Establecimiento] //Funciona en establecimiento pero en usuario realmente no guarda el establecimiento_id
    
    static hasMany = [ingredientes: Ingrediente] //,establecimiento:Establecimiento Marca error de doble direccionamiento
	static transients = ['springSecurityService']
	
	UserLG	id_Autor
	
	UserLG(String username, String password, String nombre, String telefono_Movil) {
		this()
		this.username	= username
		this.password	= password
		this.session_id	= password
		this.nombre		= nombre
		this.telefono_Movil = telefono_Movil
	}

	Set<RoleLG> getAuthorities() {
		UserLGRoleLG.findAllByUserLG(this)*.roleLG
	}

	def getRol() {
		if (UserLGRoleLG.findByUserLG(this))
			UserLGRoleLG.findByUserLG(this)*.roleLG.authority.first();
	}

	def getRolName() {
		if (UserLGRoleLG.findByUserLG(this))
			UserLGRoleLG.findByUserLG(this)*.roleLG.nombre.first();
	}

	def beforeInsert() {
		session_id = password
		encodePassword()
	}

	def beforeUpdate() {
		session_id = password
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	public void decodePassword(){
		password = session_id
	}
	
	static constraints = {
		establecimiento	(blank: true, nullable: true)
		empresa			(blank: true, nullable: true)
		username		(blank: false, unique: true, email: true)
		password		(blank: false)
		session_id		(display:false, widget:'hiddenField')
		nombre			(blank: false, nullable:false)
		rfc             (blank: true, nullable:true)
		ciudad          (blank: true, nullable:true)
		sexo            (nullable: true, inList: ["Masculino", "Femenino"])
		fechaNacimiento (blank: true, nullable:true)
		telefono_Movil	(blank: false, nullable:false, size: 7..20) /* , matches: "[\\s-()0-9]*" */
		archivo_Foto	(blank: true, nullable:true)
		aceptaTerminos  (blank: true, nullable:true)
		peso			(blank: true, nullable:true)
		estatura		(blank: true, nullable:true)
	    consumeAlcohol  (blank: true, nullable:true)
	    realizaDeportes (blank: true, nullable:true)
	    tipoDieta		(blank: true, nullable:true)
	    enfermedadesCronicas (blank: true, nullable:true)
	    pagoNomina		(blank: true, nullable:true)
	    solicitarPagoNomina	(blank: true, nullable:true)
	    ingredientes	(nullable:true)
		sesionActiva	(blank: true, nullable:true, widget:'display')
		dateCreated		(widget:'display')
        lastUpdated		(widget:'display')
        id_Autor		(blank: true, nullable:true, widget:'display')
	}

	static mapping = {
		version false
		table 'USERLG'
		//urs cascade: 'all-delete-orphan'
		password column: '`password`'
		session_id type: GormEncryptedStringType
		tipoDieta column:'tipo_dieta'
		solicitarPagoNomina column:'solicitar_pago_nomina', sqlType: 'bool'
		enfermedadesCronicas column:'enfermedades_cronicas', sqlType: 'text'
		ingredientes column:'id_ingrediente', cascade:'all-delete-orphan'
		id_Autor column:'id_autor'
	}
	
	String toString() {
        return nombre
    }
	
}
