<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title><g:message code="default.create.label" args="[entitiesName]" /></title>
    </head>
    <body>
        <g:render template="/layouts/menu" />
		<!-- .menu-box -->
		<div class="breadcrumb-box">
		  <div class="container">
			<ul class="breadcrumb">
			  <li><a href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			  <li class="active"><g:message code="default.report.label" args="['Consumos por nómina']" /></li>
			</ul>	
		  </div>
		</div>
		<!-- .breadcrumb-box -->     

        <div id="upload-data" class="content scaffold-create" role="main">
            <iframe frameborder="0" width="100%" height="1050px" src="${grailsApplication.config.jasper_serverAddress}flow.html?_flowId=viewReportFlow&j_username=${grailsApplication.config.jasper_user}&j_password=${grailsApplication.config.jasper_password}&standAlone=true&decorate=no&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2Freports%2Fsamba%2Fconsumos_por_nomina_empresa_cocina&establecimientoNombre=${session.getAttribute('username_establecimiento')}&establecimientoID=${session.getAttribute('username_establecimiento_id')}"></iframe>
            
        </div>             
        
    </body>
</html>

