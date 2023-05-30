<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'extra.label', default: 'Extra')}" />
        <g:set var="entitiesName" value="${message(code: 'extra.label', default: 'Extras')}" />
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
			  <li class="active">${extra.nombre}</li>
			</ul>
		  </div>
		</div>
		<!-- .breadcrumb-box -->
        <a href="#show-extra" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav_topbar" role="navigation">
            <ul>
                <li><a href="javascript:go('edit')"		class="edit"><g:message code="default.button.edit.label" default="Edit" /></a></li>
                <li><a href="javascript:go('delete')"	class="delete"><g:message code="default.button.delete.label" default="Delete" /></a></li>
                <li><a href="javascript:go('close')"	class="close"><g:message code="default.button.close.label" default="close" /></a></li>
            </ul>
        </div>
        <div id="show-extra" class="content scaffold-show" role="main">
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            
            <f:with bean="extra">
                <div class="x_panel">
                    <div class="x_content">              
                        <ol class="property-list ingrediente">

                            
                            <div class="tituloPrincipal"><f:display property="nombre"/></div>
                            <div><f:display property="descripcion"/></div>

                            <li class="fieldcontain">
                                <div class="property-value" aria-labelledby="archivo_Foto-label">
                                    <g:if test="${extra.archivo_Foto}">
                                        <img src="${request.contextPath}/assets/mes-icons/<f:display property='archivo_Foto'/>" style="width:200px;height:200px;"/>
                                    </g:if>
                                    <g:else>
                                        <img src="${request.contextPath}/assets/mes-icons/default.png" style="width:200px;height:200px;"/>
                                    </g:else>
                                </div>
                            </li>                            
                            <li class="fieldcontain">
                                <span id="precio-label" class="property-label"><g:message code="extra.precio.label" default="Precio" /></span>
                                <div class="property-value" aria-labelledby="precio-label"><g:formatNumber number="${extra.precio}" currencyCode="MXN" type="currency" locale="MX" currencySymbol="\$" /></div>
                            </li>       
                            <li class="fieldcontain">
                                <span id="costo-label" class="property-label"><g:message code="extra.costo.label" default="Costo" /></span>
                                <div class="property-value" aria-labelledby="costo-label"><g:formatNumber number="${extra.costo}" currencyCode="MXN" type="currency" locale="MX" currencySymbol="\$" /></div>
                            </li>                                                  
                            <li class="fieldcontain">
                                <span id="id_Autor-label" class="property-label"><g:message code="ingrediente.id_Autor.label" default="Autor" /></span>
                                <div class="property-value" aria-labelledby="id_Autor-label"><f:display property="id_Autor"/></div>
                            </li>
                        </ol>
                    </div>
                </div>
            </f:with>


            
            <g:form resource="${this.extra}" method="DELETE" name="delete_form">
				<fieldset class="buttons">
					<g:link name="footer-edit" class="edit" action="edit" resource="${this.extra}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:link name="footer-delete" class="delete" url="javascript:void(0)" onclick="jconfirm('${message(code: 'default.delete.ask.message', default: 'Are you sure?')}','${message(code: 'default.delete.info.message', default: 'You are deleting the record.')}');"><g:message code="default.button.delete.label" default="Delete" /></g:link>
                    <g:link name="footer-close" class="close" action="index"><g:message code="default.button.close.label" default="Close" /></g:link>
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
