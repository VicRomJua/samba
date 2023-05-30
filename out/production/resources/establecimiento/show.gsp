<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'establecimiento.label', default: 'Cocina')}" />
        <g:set var="entitiesName" value="${message(code: 'establecimiento.label', default: 'Cocinas')}" />
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
			  <li class="active">${establecimiento.nombre}</li>
			</ul>
		  </div>
		</div>
		<!-- .breadcrumb-box -->
        <a href="#show-establecimiento" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav_topbar" role="navigation">
            <ul>
                <li><a href="javascript:go('edit')"		class="edit"><g:message code="default.button.edit.label" default="Edit" /></a></li>
                <li><a href="javascript:go('delete')"	class="delete"><g:message code="default.button.delete.label" default="Delete" /></a></li>
                <li><a href="javascript:go('close')"	class="close"><g:message code="default.button.close.label" default="close" /></a></li>
            </ul>
        </div>
        <div id="show-establecimiento" class="content scaffold-show" role="main">
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            %{--
            <f:display bean="establecimiento" />
            --}%
            
			<f:with bean="establecimiento">
                <div class="x_panel">
                    <div class="x_content">                
                        <ol class="property-list establecimiento">

                            <div class="tituloPrincipal"><f:display property="nombre"/></div>
                            <div class="tituloSecundario"><f:display property="codigo"/></div>
							<div class="tituloTerciario"><i class="fa fa-phone"></i>&nbsp;<f:display property="telefono"/></div>
							
                            <li class="fieldcontain">
                                <span id="administrador-label" class="property-label"><g:message code="establecimiento.administrador.label" default="Admin" /></span>
                                <div class="property-value" aria-labelledby="administrador-label"><f:display property="administrador"/></div>
                            </li>
                            <li class="fieldcontain">
                                <span id="totalVendido-label" class="property-label"><g:message code="establecimiento.totalVendido.label" default="Total Vendido" /></span>
                                <div class="property-value" aria-labelledby="totalVendido-label"><f:display property="totalVendido"/></div>
                            </li>

                            <li class="fieldcontain">
                                <span id="domicilio-label" class="property-label"><g:message code="establecimiento.domicilio.label" default="Address" /></span>
                                <div class="property-value" aria-labelledby="domicilio-label"><f:display property="domicilio"/></div>
                            </li>
                            <li class="fieldcontain">
                                <span id="ciudad-label" class="property-label"><g:message code="establecimiento.ciudad.label" default="City" /></span>
                                <div class="property-value" aria-labelledby="ciudad-label"><f:display property="ciudad"/></div>
                            </li>
                            <li class="fieldcontain">
                                <span id="cp-label" class="property-label"><g:message code="establecimiento.cp.label" default="Postal code" /></span>
                                <div class="property-value" aria-labelledby="cp-label"><f:display property="cp"/></div>
                            </li>
                            <!--
                            <li class="fieldcontain">
                                <span id="fecha_Registro-label" class="property-label"><g:message code="establecimiento.dateCreated.label" default="Fecha de registro" /></span>
                                <div class="property-value" aria-labelledby="fecha_Registro-label"><f:display property="dateCreated"/></div>
                            </li>
                            <li class="fieldcontain">
                                <span id="fecha_Cambio-label" class="property-label"><g:message code="establecimiento.lastUpdated.label" default="Última actualización" /></span>
                                <div class="property-value" aria-labelledby="fecha_Cambio-label"><f:display property="lastUpdated"/></div>
                            </li>
                            -->
                            <li class="fieldcontain">
                                <span id="id_Autor-label" class="property-label"><g:message code="establecimiento.id_Autor.label" default="Autor" /></span>
                                <div class="property-value" aria-labelledby="id_Autor-label"><f:display property="id_Autor"/></div>
                            </li>
                        </ol>
                    </div>
                </div>
            </f:with>
			
            
            <g:form resource="${this.establecimiento}" method="DELETE" name="delete_form">
				<fieldset class="buttons">
					<g:link name="footer-edit" class="edit" action="edit" resource="${this.establecimiento}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:link name="footer-delete" class="delete" url="javascript:void(0)" onclick="jconfirm('${message(code: 'default.delete.ask.message', default: 'Are you sure?')}','${message(code: 'default.delete.info.message', default: 'You are deleting the record.')}');"><g:message code="default.button.delete.label" default="Delete" /></g:link>
                    <g:link name="footer-close" class="close" action="index"><g:message code="default.button.close.label" default="Close" /></g:link>
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
