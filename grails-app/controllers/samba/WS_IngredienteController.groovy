package samba


class WS_IngredienteController {
    static responseFormats = ['json', 'xml']
	
    def index() {
        respond WS_Ingrediente.list()
    }
}
