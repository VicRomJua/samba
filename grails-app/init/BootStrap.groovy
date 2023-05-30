package samba

class BootStrap {
    //dbm-generate-gorm-changelog changelog.groovy and then ran dbm-update
    def init = { servletContext ->
		boolean flag;
        try {
            flag = true;
            println RoleLG.count().toString()
        }
        catch (Exception e) {
            flag = false;
        }
		if (flag){
			if (RoleLG.count()<1){
				
				/******ingredientes*******/
				def ingrediente0 = new Ingrediente('Jitomate','Grs.', 'jitomate.png').save(failOnError: true, flush: true)
				def ingrediente1 = new Ingrediente('Cebolla Morada','Grs.', 'cebolla_morada.png').save(failOnError: true, flush: true)
				def ingrediente2 = new Ingrediente('Pan','Pzas.', 'pan.png').save(failOnError: true, flush: true)
				def ingrediente3 = new Ingrediente('Jamon','Pzas.', 'jamon.png').save(failOnError: true, flush: true)
				def ingrediente4 = new Ingrediente('Pan Frances','Pzas.', 'pan_frances.png').save(failOnError: true, flush: true)
				def ingrediente5 = new Ingrediente('Lechuga','Grs.', 'lechuga.jpg').save(failOnError: true, flush: true)
				def ingrediente6 = new Ingrediente('Pepino','Grs.', 'pepino.jpg').save(failOnError: true, flush: true)
				
				/******categorias*******/
				def categoria0 = new Categoria('Diabetes','Alimentos sanos para diabéticos.', 'diabetes.png').save(failOnError: true, flush: true)
				def categoria1 = new Categoria('Vegano','Alimentos que no incluye productos de origen animal.', 'vegano.png').save(failOnError: true, flush: true)
				def categoria2 = new Categoria('Obesidad y sobrepeso','Manten la línea y mejora tu salud.', 'obesidad.png').save(failOnError: true, flush: true)
				def categoria3 = new Categoria('Vegetarianos','Solo verde para vivir bien y contento.', 'vegetarianos.png').save(failOnError: true, flush: true)
				def categoria4 = new Categoria('Colitis y gastritis','Mantiene feliz a tu intestino.', 'colitis.png').save(failOnError: true, flush: true)
				def categoria5 = new Categoria('Intolerante a la lactosa','Alimentos libres de lactosa.', 'intolerante_lactosa.png').save(failOnError: true, flush: true)
				def categoria6 = new Categoria('Intolerante al gluten','Alimentos libres de gluten.', 'intolerante_gluten.png').save(failOnError: true, flush: true)
				
				/******tipos*******/
				
				def tipo0 = new Tipo('Bebidas','Smoothie Energy','Ideal para quienes buscan una fuente adicional de energía. Contiene un energetizante llamado Guaraná, el cual se deriva de una semilla proveniente de Brazil. El guaraná ayuda a elevar los niveles de energía, combatir la fatiga, incrementar adrenalina, mejorar la memoria, y a bajar de peso. Esta línea es ideal al comenzar el dia o antes de hacer ejercicio.', 'energy.png').save(failOnError: true, flush: true)
				def tipo1 = new Tipo('Bebidas','Smoothie Kids','Tiene como propósito mejorar la alimentación de los niños. Nuestras bebidas incluyen ingredientes como frutas, leche, y otros nutrientes que ellos necesitan para llevar una dieta balanceada y saludable que los hara crecer sanos y con mejor calidad de vida. ', 'kids.png').save(failOnError: true, flush: true)
				def tipo2 = new Tipo('Bebidas','Smoothie Slim','Rica combinación de frutas bajas en calorías que ayuda a hidratar, depurar y vitalizar el organismo. Estos smoothies son recomendados como una colación saludable para personas que quieren bajar de peso ya que contienen de 140 a 210 calorías solamente.', 'slim.png').save(failOnError: true, flush: true)
				def tipo3 = new Tipo('Bebidas','Smoothie Muscle','Combinación rica en proteínas, carbohidratos y grasas, que ayudan a recuperarte después de hacer ejercicio. Esta línea es altamente recomendada para los deportistas de alto rendimiento que buscan recuperar su cuerpo al máximo para poder seguir entrenando a un nivel fuerte y competitivo.','muscle.png').save(failOnError: true, flush: true)
				def tipo4 = new Tipo('Bebidas','Smoothie Tropical','Frutas exóticas y tropicales con antioxidantes, Omega 3, y vitamina C que ayudan a prevenir enfermedades. Son bebidas altamente refrescantes e ideales para aquellos días de calor que nos da la sensación de bienestar como si estuviéramos en la playa.', 'tropical.png').save(failOnError: true, flush: true)
				def tipo5 = new Tipo('Bebidas','Smoothie Wellness','Fuente de vitaminas y minerales a base de frutas e ingredientes altos en fibra como el salvado, granola, y cereales que ayudan al sistema digestivo y a la asimilación de insulina. Tiene altas propiedades nutricionales que disminuyen riesgos cardiacos y niveles de colesterol no deseado.', 'wellnes.png').save(failOnError: true, flush: true)
				def tipo6 = new Tipo('Bebidas','Smoothie Sunrise','', 'sunrise.png').save(failOnError: true, flush: true)
				def tipo7 = new Tipo('Comidas','Snacks','', 'snack.png').save(failOnError: true, flush: true)
				def tipo8 = new Tipo('Bebidas','Licuados','', 'licuados.png').save(failOnError: true, flush: true)
				def tipo9 = new Tipo('Bebidas','Jugos','', 'jugos.png').save(failOnError: true, flush: true)
				def tipo10 = new Tipo('Comidas','Flats','', 'flats.png').save(failOnError: true, flush: true)
				def tipo11 = new Tipo('Comidas','Emparedados','', 'emparedados.png').save(failOnError: true, flush: true)
				def tipo12 = new Tipo('Comidas','Desayunos','', 'desayunos.png').save(failOnError: true, flush: true)
				def tipo13 = new Tipo('Comidas','Ensaladas','Deliciosa variedad de ensaladas con ingredientes como lechuga, espinacas, queso de cabra, queso bajo en grasa, pollo, pavo, nueces, pasas y aderezos bajos en grasa', 'ensaladas.png').save(failOnError: true, flush: true)
				def tipo14 = new Tipo('Comidas','Postres','', 'postres.png').save(failOnError: true, flush: true)
				
				/******extras*******/
				def extra0 = new Extra('Almendras','', 'almendras.png',10.0,5.0).save(failOnError: true, flush: true)
				def extra1 = new Extra('Cacahuate','', 'cacahuate.png',10.0,5.0).save(failOnError: true, flush: true)
				def extra2 = new Extra('Curcuma','', 'curcuma.png',10.0,5.0).save(failOnError: true, flush: true)
				def extra3 = new Extra('Avena','', 'avena.png',10.0,5.0).save(failOnError: true, flush: true)
				def extra4 = new Extra('Amaranto','', 'amaranto.png',10.0,5.0).save(failOnError: true, flush: true)
				def extra5 = new Extra('Salvado','', 'salvado.png',10.0,5.0).save(failOnError: true, flush: true)
				def extra6 = new Extra('Chia','', 'chia.png',10.0,5.0).save(failOnError: true, flush: true)
				def extra7 = new Extra('Granola','', 'granola.png',10.0,5.0).save(failOnError: true, flush: true)
				def extra8 = new Extra('Moutarde','', 'moutarde.png',10.0,5.0).save(failOnError: true, flush: true)
				
				/******roles*******/
				//Los roles siempre deben comenzar con ROLE_
				def programadorRole = new RoleLG('Programador','ROLE_PROGRAMADOR').save(failOnError: true, flush: true)
				def propietarioRole = new RoleLG('Propietario','ROLE_PROPIETARIO').save(failOnError: true, flush: true)
				def administradorRole = new RoleLG('Administrador','ROLE_ADMIN').save(failOnError: true, flush: true)
				def empresaRole = new RoleLG('Empresa','ROLE_EMPRESA').save(failOnError: true, flush: true)
				def cocineroRole = new RoleLG('Cocinero','ROLE_COCINERO').save(failOnError: true, flush: true)
				def repartidorRole = new RoleLG('Repartidor','ROLE_REPARTIDOR').save(failOnError: true, flush: true)
				def clienteRole = new RoleLG('Cliente','ROLE_CLIENTE').save(failOnError: true, flush: true)
				
				/*******users********/
				def user0 = new UserLG('propietario@gmail.com', 'password2017', 'Agustín Tristán', '(555) 345-2121').save(failOnError: false, flush: true)
				// def user1a = new UserLG('admin1@gmail.com', 'password2017', 'Jorge Ceyca', '(777) 197-1711').save(failOnError: true, flush: true)
				// def user1b = new UserLG('admin2@gmail.com', 'password2017', 'Israel Galvan', '(777) 245-1515').save(failOnError: true, flush: true)
				def user3 = new UserLG('chef@gmail.com', 'password2017', 'Rafael Acosta', '(777) 891-9090').save(failOnError: false, flush: true)
				def user4 = new UserLG('repartidor@gmail.com', 'password2017', 'Jesús López', '(777) 188-1029').save(failOnError: false, flush: true)
				def user5 = new UserLG('cliente@gmail.com', 'password2017', 'Andrea Vidaña', '(777) 188-2919').save(failOnError: false, flush: true)
				def user6 = new UserLG('programador@gmail.com', 'programador', 'Julio Vidaña', '(777) 551-6261').save(failOnError: false, flush: true)
				
				/*********** asignamos los roles a los users********/
				if (user0) UserLGRoleLG.create user0, propietarioRole, true
				// if (user1a) UserLGRoleLG.create user1a, administradorRole, true
				// if (user1b) UserLGRoleLG.create user1b, administradorRole, true
				if (user3) UserLGRoleLG.create user3, cocineroRole, true
				if (user4) UserLGRoleLG.create user4, repartidorRole, true
				if (user5) UserLGRoleLG.create user5, clienteRole, true
				if (user6) UserLGRoleLG.create user6, programadorRole, true
			}
		}
    }
    def destroy = {
    }
}
