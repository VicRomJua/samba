<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'orden.label', default: 'Orden')}" />
        <g:set var="entitiesName" value="${message(code: 'orden.label', default: 'Ordenes')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
        <script language="javascript">
            function jconfirm(pregunta,mensaje){
                swal({
                    title: pregunta,
                    text: mensaje,
                    type: "question",
                    showCancelButton: true,
                    confirmButtonColor: "#DA291C",
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
			  <li class="active">${orden.noOrden}</li>
			</ul>
		  </div>
		</div>
		<!-- .breadcrumb-box -->
        <a href="#show-orden" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav_topbar" role="navigation">
            <ul>
                <li><a href="javascript:go('edit')"		class="edit"><g:message code="default.button.edit.label" default="Edit" /></a></li>
                <li><a href="javascript:go('delete')"	class="delete"><g:message code="default.button.delete.label" default="Delete" /></a></li>
                <li><a href="javascript:go('close')"	class="close"><g:message code="default.button.close.label" default="close" /></a></li>
            </ul>
        </div>
        <div id="show-orden" class="content scaffold-show" role="main">
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            %{--
            <f:display bean="orden" />
            --}%
            <f:with bean="orden">
                <div class="x_panel">
                    <div class="x_content">
                        <ol class="property-list ingrediente">
                            <div class="tituloPrincipal"><f:display property="noOrden"/></div>                         
                            <li class="fieldcontain">
                                <span id="montoPagado-label" class="property-label"><g:message code="orden.montoPagado.label" default="Payment" /></span>
                                <div class="property-value" aria-labelledby="montoPagado-label"><f:display property="montoPagado"/></div>
                            </li>                            
                            <li class="fieldcontain">
                                <span id="modoPago-label" class="property-label"><g:message code="orden.modoPago.label" default="Way's payment" /></span>
                                <div class="property-value" aria-labelledby="modoPago-label"><f:display property="modoPago"/></div>
                            </li>
                            <li class="fieldcontain">
                                <span id="fechaPago-label" class="property-label"><g:message code="orden.fechaPago.label" default="Date's payment" /></span>
                                <div class="property-value" aria-labelledby="fechaPago-label"><f:display property="fechaPago"/></div>
                            </li>
                            <li class="fieldcontain">
                                <span id="detalles-label" class="property-label"><g:message code="orden.detalles.label" default="Details" /></span>
                                <div class="property-value" aria-labelledby="detalles-label"><f:display property="detalles"/></div>
                            </li>
                            <li class="fieldcontain">
                                <span id="modoPago-label" class="property-label"><g:message code="orden.modoPago.label" default="Way's payment" /></span>
                                <div class="property-value" aria-labelledby="modoPago-label"><f:display property="modoPago"/></div>
                            </li>
                            <li class="fieldcontain">
                                <span id="id_Autor-label" class="property-label"><g:message code="orden.id_Autor.label" default="Autor" /></span>
                                <div class="property-value" aria-labelledby="id_Autor-label"><f:display property="id_Autor"/></div>
                            </li>
                        </ol>
                    </div>
                </div>
            </f:with>
			
            
            <g:form resource="${this.orden}" method="DELETE" name="delete_form">
				<fieldset class="buttons">
					<g:link name="footer-edit" class="edit" action="edit" resource="${this.orden}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:link name="footer-delete" class="delete" url="javascript:void(0)" onclick="jconfirm('${message(code: 'default.delete.ask.message', default: 'Are you sure?')}','${message(code: 'default.delete.info.message', default: 'You are deleting the record.')}');"><g:message code="default.button.delete.label" default="Delete" /></g:link>
                    <g:link name="footer-close" class="close" action="index"><g:message code="default.button.close.label" default="Close" /></g:link>
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
