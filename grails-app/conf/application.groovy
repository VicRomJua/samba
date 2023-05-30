environments {
    development {
        //uploadFolder = "C:/Users/1200/Documents/Grails/samba_web_v0.61/grails-app/assets/images/"
        // uploadFolder = "/root/archivos/samba_web_v0.61/grails-app/assets/images/"
		uploadFolder = "C:/Users/victor/Documents/Grails/samba_web_v0.61/grails-app/assets/images/"
        serverAddress = "http://104.254.245.172/samba/"
        jasper_serverAddress = "http://104.254.245.172/jasperserver/"
        jasper_user = "jasperadmin"
        jasper_password = "jasperadmin"
    }
    test {
        uploadFolder = "c:/temp/upload/"
        serverAddress = "http://104.254.245.172/samba/"
        jasper_serverAddress = "http://104.254.245.172/jasperserver/"
        jasper_user = "jasperadmin"
        jasper_password = "jasperadmin"
    }
    production {
        uploadFolder = "/opt/bitnami/apache-tomcat/webapps/samba/assets/"
        serverAddress = "http://104.254.245.172/samba/"
        jasper_serverAddress = "http://104.254.245.172/jasperserver/"
        jasper_user = "jasperadmin"
        jasper_password = "jasperadmin"
    }
}



grails.plugin.databasemigration.updateOnStart = true
grails.plugin.databasemigration.updateOnStartFileNames = ['changelog.groovy']


// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.useSecurityEventListener = true
grails.plugin.springsecurity.userLookup.userDomainClassName = 'samba.UserLG'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'samba.UserLGRoleLG'
grails.plugin.springsecurity.authority.className = 'samba.RoleLG'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	[pattern: '/',               access: ['permitAll']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['permitAll']],
	[pattern: '/main/index',     access: ['permitAll']],
	[pattern: '/main/changeRange',     access: ['ROLE_ADMIN','ROLE_PROPIETARIO','ROLE_PROGRAMADOR']],
	[pattern: '/index.gsp',      access: ['permitAll']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],

	[pattern: '/empresa/*',     access: ['ROLE_ADMIN', 'ROLE_EMPRESA', 'ROLE_PROPIETARIO', 'ROLE_PROGRAMADOR']],
	[pattern: '/establecimiento/*',    access: ['ROLE_PROPIETARIO', 'ROLE_PROGRAMADOR']],

	[pattern: '/embarque/*',     access: ['ROLE_ADMIN','ROLE_PROPIETARIO', 'ROLE_COCINERO', 'ROLE_PROGRAMADOR']],
	[pattern: '/ordendetalle/*',     access: ['ROLE_ADMIN','ROLE_PROPIETARIO', 'ROLE_COCINERO', 'ROLE_PROGRAMADOR']],
	[pattern: '/orden/*',     access: ['ROLE_ADMIN','ROLE_PROPIETARIO', 'ROLE_COCINERO', 'ROLE_PROGRAMADOR']],
	
	[pattern: '/platilloEstablecimiento/*',     access: ['ROLE_ADMIN', 'ROLE_PROGRAMADOR']],
	[pattern: '/platillo/*',     access: ['ROLE_PROPIETARIO', 'ROLE_PROGRAMADOR']],
	[pattern: '/categoria/*',    access: ['ROLE_PROPIETARIO', 'ROLE_PROGRAMADOR']],
	[pattern: '/tipo/*',         access: ['ROLE_PROPIETARIO', 'ROLE_PROGRAMADOR']],
	[pattern: '/ingrediente/*',  access: ['ROLE_PROPIETARIO', 'ROLE_PROGRAMADOR']],
	[pattern: '/extra/*',        access: ['ROLE_PROPIETARIO', 'ROLE_PROGRAMADOR']],
	
	[pattern: '/informes/*',     access: ['ROLE_ADMIN', 'ROLE_PROPIETARIO', 'ROLE_COCINERO', 'ROLE_EMPRESA', 'ROLE_PROGRAMADOR']],
	
	[pattern: '/userLG/myuser',         access: ['ROLE_ADMIN', 'ROLE_PROPIETARIO', 'ROLE_COCINERO', 'ROLE_EMPRESA', 'ROLE_PROGRAMADOR']],
	[pattern: '/userLG/updateMyuser',   access: ['ROLE_ADMIN', 'ROLE_PROPIETARIO', 'ROLE_COCINERO', 'ROLE_EMPRESA', 'ROLE_PROGRAMADOR']],
	[pattern: '/userLG/empleados',       access: ['ROLE_EMPRESA', 'ROLE_PROGRAMADOR']],
	[pattern: '/userLG/solicitudes',       access: ['ROLE_EMPRESA', 'ROLE_PROGRAMADOR']],
	[pattern: '/userLG/adeudos',       access: ['ROLE_EMPRESA', 'ROLE_PROGRAMADOR']],
	[pattern: '/userLG/active',       access: ['ROLE_ADMIN', 'ROLE_PROPIETARIO', 'ROLE_EMPRESA', 'ROLE_PROGRAMADOR']],
	[pattern: '/userLG/nomina',       access: ['ROLE_EMPRESA', 'ROLE_PROGRAMADOR']],
	
	[pattern: '/userLG/*',       access: ['ROLE_ADMIN','ROLE_PROPIETARIO', 'ROLE_PROGRAMADOR']],
	[pattern: '/roleLG/*',       access: ['ROLE_PROGRAMADOR']],

	[pattern: '/userLGRoleLG/*', access: ['ROLE_ADMIN', 'ROLE_PROGRAMADOR']],
	[pattern: '/login/*',        access: ['permitAll']],
	[pattern: '/logout/*',       access: ['permitAll']],

	[pattern: '/WS_UserLG/*',   access: ['permitAll']],
	[pattern: '/WS_RoleLG/*',   access: ['permitAll']],
	[pattern: '/WS_UserLGRoleLG/*',   access: ['permitAll']],
	[pattern: '/WS_Empresa/*',   access: ['permitAll']],
	[pattern: '/WS_Embarque/*',   access: ['permitAll']],
	[pattern: '/WS_Establecimiento/*',   access: ['permitAll']],
	[pattern: '/WS_Orden/*',   access: ['permitAll']],
	[pattern: '/WS_OrdenDetalle/*',   access: ['permitAll']],
	[pattern: '/WS_Extra/*',   access: ['permitAll']],
	[pattern: '/WS_Tipo/*',   access: ['permitAll']],
	[pattern: '/WS_Categoria/*',   access: ['permitAll']],
	[pattern: '/WS_Ingrediente/*',   access: ['permitAll']],
	[pattern: '/WS_PlatilloEstablecimiento/*',   access: ['permitAll']],
	[pattern: '/Webhooks/*',   access: ['permitAll']],
	
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]

// envio de correos con SMTP
grails {
	mail {
    	host = "mail.weykhans.com"
     	port = 465
     	username = "samba@weykhans.com"
     	password = "weykhans2017"
     	props = ["mail.smtp.auth":"true", 					   
              "mail.smtp.socketFactory.port":"465",
              "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
              "mail.smtp.socketFactory.fallback":"false"]
   }
}

// encriptación
jasypt {
    algorithm = "PBEWITHSHA256AND256BITAES-CBC-BC"
    providerName = "BC"
    password = "<SambaKey>"
    keyObtentionIterations = 1000
}
