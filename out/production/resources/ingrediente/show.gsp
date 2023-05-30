<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'ingrediente.label', default: 'Ingrediente')}" />
        <g:set var="entitiesName" value="${message(code: 'ingrediente.label', default: 'Ingredientes')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
        <script language="javascript">
            function jconfirm(pregunta,mensaje){
                swal({
                    title: pregunta,
                    text: mensaje,
                    type: "question",
                    showCancelButton: true,
                    confirmButtonColor: "#FF7C24",
                    confirmButtonText: "Si, eliminar!",
                    cancelButtonText: "No",
                    confirmButtonClass: "btn btn-success",
                    cancelButtonClass: "btn btn-danger",
                    buttonsStyling: true
                }).then(function() {
                     $( "#delete_form" ).submit();
                });
            }
            
            function go(id_tag){
				$('[name="footer-'+id_tag+'"]')[0].click();
			}
        </script>
    </head>
    <body>
		<g:render template="/layouts/menu" />
		<!-- .menu-box -->
		<div class="breadcrumb-box">
		  <div class="container">
			<ul class="breadcrumb">
			  <li><a href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			  <li><g:link action="index"><g:message code="default.list.label" args="[entitiesName]" /></g:link></li>
			  <li class="active">${ingrediente.nombre}</li>
			</ul>
		  </div>
		</div>
		<!-- .breadcrumb-box -->
        <a href="#show-ingrediente" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav_topbar" role="navigation">
            <ul>
                <li><a href="javascript:go('edit')"		class="edit"><g:message code="default.button.edit.label" default="Edit" /></a></li>
                <li><a href="javascript:go('delete')"	class="delete"><g:message code="default.button.delete.label" default="Delete" /></a></li>
                <li><a href="javascript:go('close')"	class="close"><g:message code="default.button.close.label" default="close" /></a></li>
            </ul>
        </div>
        <div id="show-ingrediente" class="content scaffold-show" role="main">
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            %{--
            <f:display bean="ingrediente" />
            --}%
            
			<f:with bean="ingrediente">
                <div class="x_panel">
                    <div class="x_content">
                        <ol class="property-list ingrediente">
                            <div class="tituloPrincipal"><f:display property="nombre"/></div>
                            <li class="fieldcontain">
                                <div class="property-value" aria-labelledby="archivo_Foto-label">
                                    <g:if test="${ingrediente.archivo_Foto}">
                                        <img src="${request.contextPath}/assets/mes-icons/<f:display property='archivo_Foto'/>" style="width:200px;height:200px;"/>
                                    </g:if>
                                    <g:else>
                                        <img src="${request.contextPath}/assets/mes-icons/default.png" style="width:200px;height:200px;"/>
                                    </g:else>
                                </div>
                            </li>                            
                            <li class="fieldcontain">
                                <span id="unidadMedicion-label" class="property-label"><g:message code="ingrediente.unidadMedicion.label" default="Unidad de medición" /></span>
                                <div class="property-value" aria-labelledby="unidadMedicion-label"><f:display property="unidadMedicion"/></div>
                            </li>                            
                            <li class="fieldcontain">
                                <span id="id_Autor-label" class="property-label"><g:message code="ingrediente.id_Autor.label" default="Autor" /></span>
                                <div class="property-value" aria-labelledby="id_Autor-label"><f:display property="id_Autor"/></div>
                            </li>
                        </ol>
                    </div>
                </div>
            </f:with>
			
            
            <g:form resource="${this.ingrediente}" method="DELETE" name="delete_form">
				<fieldset class="buttons">
					<g:link name="footer-edit" class="edit" action="edit" resource="${this.ingrediente}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:link name="footer-delete" class="delete" url="javascript:void(0)" onclick="jconfirm('${message(code: 'default.delete.ask.message', default: 'Are you sure?')}','${message(code: 'default.delete.info.message', default: 'You are deleting the record.')}');"><g:message code="default.button.delete.label" default="Delete" /></g:link>
                    <g:link name="footer-close" class="close" action="index"><g:message code="default.button.close.label" default="Close" /></g:link>
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
