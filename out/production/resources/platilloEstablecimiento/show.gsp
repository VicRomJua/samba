<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'platilloEstablecimiento.label', default: 'Mi Menú')}" />
        <g:set var="entitiesName" value="${message(code: 'platilloEstablecimiento.label', default: 'Mi Menú')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
        <style>
			.order_1, .order_2{
				display:none;
			}
        </style>
        <script language="javascript">
			
			$( document ).ready(function() {
				$(".order_1 > ul").html(
					$(".order_1 > ul").children("li").sort(function (a, b) {
						return $(a).text().toUpperCase().localeCompare($(b).text().toUpperCase());
					})
				);
				$(".order_1").show();
				$(".order_2 > ul").html(
					$(".order_2 > ul").children("li").sort(function (a, b) {
						return $(a).text().toUpperCase().localeCompare($(b).text().toUpperCase());
					})
				);
				$(".order_2").show();
			});
			
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
			  <li class="active">${platilloEstablecimiento.platillo.nombre}</li>
			</ul>
		  </div>
		</div>
		<!-- .breadcrumb-box -->
        <a href="#show-platilloEstablecimiento" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav_topbar" role="navigation">
            <ul>
                <li><a href="javascript:go('edit')"		class="edit"><g:message code="default.button.edit.label" default="Edit" /></a></li>
                <li><a href="javascript:go('delete')"	class="delete"><g:message code="default.button.delete.label" default="Delete" /></a></li>
                <li><a href="javascript:go('close')"	class="close"><g:message code="default.button.close.label" default="close" /></a></li>
            </ul>
        </div>
        <div id="show-platilloEstablecimiento" class="content scaffold-show" role="main">
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            %{--
            <f:display bean="platilloEstablecimiento" />
            --}%
            
			<f:with bean="platilloEstablecimiento">
                <div class="x_panel">
                    <div class="x_content">
                        <ol class="property-list platilloEstablecimiento">

                            <div class="tituloPrincipal"><f:display property="platillo"/></div>
                            <div class="tituloSecundario"><h5>Tipo</h5><ul><li><f:display property="platillo.tipo"/></li></ul>
                            <h5>Categorías</h5><div class="order_1"><f:display property="platillo.categorias"/></div></div>
                            <div class="tituloTerciario"><h5>Ingredientes</h5><div class="order_2"><f:display property="platillo.ingredientes"/></div></div>
							
							<sec:ifAnyGranted roles='ROLE_PROPIETARIO,ROLE_PROGRAMADOR'>
                            <li class="fieldcontain">
                                <span id="establecimiento-label" class="property-label"><g:message code="platilloEstablecimiento.establecimiento.label" default="Store" /></span>
                                <div class="property-value" aria-labelledby="establecimiento-label"><f:display property="establecimiento"/></div>
                            </li>
							</sec:ifAnyGranted>
							
							<li class="fieldcontain">
								<span id="calorias-label" class="property-label"><g:message code="platillo.calorias.label" default="Calorias" /></span>
								<div class="property-value" aria-labelledby="calorias-label"><f:display property="platillo.calorias"/></div>
							</li>
                            <li class="fieldcontain">
                                <span id="precio-label" class="property-label"><g:message code="platilloEstablecimiento.precio.label" default="Price" /></span>
                                <div class="property-value" aria-labelledby="precio-label"><g:formatNumber number="${platilloEstablecimiento.precio}" currencyCode="MXN" type="currency" locale="MX" currencySymbol="\$" /></div>
                            </li>
                            <li class="fieldcontain">
                                <span id="costo-label" class="property-label"><g:message code="platilloEstablecimiento.costo.label" default="Cost" /></span>
                                <div class="property-value" aria-labelledby="costo-label"><g:formatNumber number="${platilloEstablecimiento.costo}" currencyCode="MXN" type="currency" locale="MX" currencySymbol="\$" /></div>
                            </li>
                            <li class="fieldcontain">
                                <span id="activo-label" class="property-label"><g:message code="platilloEstablecimiento.activo.label" default="Active" /></span>
                                <div class="property-value" aria-labelledby="activo-label"><f:display property="activo"/></div>
                            </li>
                            <!--
                            <li class="fieldcontain">
                                <span id="fecha_Registro-label" class="property-label"><g:message code="platilloEstablecimiento.dateCreated.label" default="Fecha de registro" /></span>
                                <div class="property-value" aria-labelledby="fecha_Registro-label"><f:display property="dateCreated"/></div>
                            </li>
                            <li class="fieldcontain">
                                <span id="fecha_Cambio-label" class="property-label"><g:message code="platilloEstablecimiento.lastUpdated.label" default="Última actualización" /></span>
                                <div class="property-value" aria-labelledby="fecha_Cambio-label"><f:display property="lastUpdated"/></div>
                            </li>
                            -->
                            <li class="fieldcontain">
                                <span id="id_Autor-label" class="property-label"><g:message code="platilloEstablecimiento.id_Autor.label" default="Autor" /></span>
                                <div class="property-value" aria-labelledby="id_Autor-label"><f:display property="id_Autor"/></div>
                            </li>
                        </ol>
                    </div>
                </div>
            </f:with>
			
            
            <g:form resource="${this.platilloEstablecimiento}" method="DELETE" name="delete_form">
				<fieldset class="buttons">
					<g:link name="footer-edit" class="edit" action="edit" resource="${this.platilloEstablecimiento}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:link name="footer-delete" class="delete" url="javascript:void(0)" onclick="jconfirm('${message(code: 'default.delete.ask.message', default: 'Are you sure?')}','${message(code: 'default.delete.info.message', default: 'You are deleting the record.')}');"><g:message code="default.button.delete.label" default="Delete" /></g:link>
                    <g:link name="footer-close" class="close" action="index"><g:message code="default.button.close.label" default="Close" /></g:link>
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
