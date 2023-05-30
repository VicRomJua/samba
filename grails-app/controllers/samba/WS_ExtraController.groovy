package samba


class WS_ExtraController {
    static responseFormats = ['json', 'xml']
	
    def index() {
        respond WS_Extra.list()
    }
}
