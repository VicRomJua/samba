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

class Platillo {

    String  id
    
    String  nombre
    String  archivo_Foto
    String  descripcion
    int		calorias
    boolean	activo = Boolean.TRUE
    
    Date    dateCreated
    Date    lastUpdated
    UserLG  id_Autor
    
    static hasMany = [ingredientes: Ingrediente, categorias:Categoria]
    static  belongsTo = [tipo:Tipo]
	
	Platillo(nombre, calorias, archivo_Foto){
        this()
        this.nombre = nombre
        this.calorias = calorias
        this.archivo_Foto = archivo_Foto
    }
	
    static constraints = {
        archivo_Foto    (nullable: true, widget:'icon')
        nombre          (nullable: false, unique: true)
        descripcion     (nullable: true, widget: 'textarea', size: 0..150)
        calorias		(nullable: true)
        tipo			(nullable: true)
        categorias		(nullable: true)
        ingredientes	(nullable: true)
        activo			(nullable: true)
        dateCreated     (widget:'display')
        lastUpdated     (widget:'display')
        id_Autor        (nullable:true, widget:'display')
    }

    static mapping = {
		sort nombre: "asc"
        table 'PLATILLOS'
        version false
        id column: "ID", generator : 'uuid'
        descripcion column:'descripcion', sqlType: 'text'
        //ingredientes column:'platillo_id', cascade:'all-delete-orphan'
        id_Autor column:'id_autor'
    }

    String toString() {
        return nombre
    }
}
