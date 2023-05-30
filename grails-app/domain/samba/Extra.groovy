package samba

class Extra {

    //Atributos de la tabla
    String	id 
    String	nombre
    String	descripcion
    String  archivo_Foto
    float	precio
    float	costo
    Date	dateCreated
    Date	lastUpdated
    UserLG	id_Autor
    
    static  belongsTo = [categoria:Categoria]
    
    Extra(nombre, descripcion, archivo_Foto, precio, costo){
        this()
        this.nombre = nombre
        this.descripcion = descripcion
        this.archivo_Foto = archivo_Foto
        this.precio = precio
        this.costo = costo
    }
    
    //Validaciones
    static constraints = {
        id				(nullable:true)
        archivo_Foto    (nullable: true, widget:'icon')
        nombre			(nullable: false, unique: true)
        precio			(nullable: false)
        costo			(nullable: false)
        categoria		(nullable: true)
        descripcion     (nullable: true, widget: 'textarea', size: 0..150)
        dateCreated		(widget:'display')
        lastUpdated		(widget:'display')
        id_Autor		(nullable:true, widget:'display')
    }

    //Vinculación con la tabla de la BD
    static mapping = {
		sort nombre: "asc"
        table 'EXTRAS'
        version false
        id column: "ID", generator : 'uuid'
        descripcion column:'descripcion', sqlType: 'text'
        id_Autor column:'id_autor'
    }
    
    String toString() {
        return nombre
    }
}
