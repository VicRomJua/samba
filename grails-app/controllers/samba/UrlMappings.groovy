package samba

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(action:"index", controller:"main")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
