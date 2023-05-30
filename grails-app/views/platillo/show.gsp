<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'platillo.label', default: 'Platillo')}" />
        <g:set var="entitiesName" value="${message(code: 'platillo.label', default: 'Platillos')}" />
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
			  <li class="active">${platillo.nombre}</li>
			</ul>
		  </div>
		</div>
		<!-- .breadcrumb-box -->
        <a href="#show-platillo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav_topbar" role="navigation">
            <ul>
                <li><a href="javascript:go('edit')"		class="edit"><g:message code="default.button.edit.label" default="Edit" /></a></li>
                <li><a href="javascript:go('delete')"	class="delete"><g:message code="default.button.delete.label" default="Delete" /></a></li>
                <li><a href="javascript:go('close')"	class="close"><g:message code="default.button.close.label" default="close" /></a></li>
            </ul>
        </div>
        <div id="show-platillo" class="content scaffold-show" role="main">
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            %{--
            <f:display bean="platillo" />
            --}%
            
			<f:with bean="platillo">
                <div class="col-md-8 col-sm-12 col-xs-12">
                    <div class="x_panel">
                        <div class="x_content">
                            <ol class="property-list platillo">

                                <div class="tituloPrincipal"><f:display property="nombre"/></div>         
                                <div class="tituloSecundario"><f:display property="categorias"/></div>                           
                                <div><f:display property="descripcion"/></div>
                                <li class="fieldcontain">
                                    <div class="property-value" aria-labelledby="archivo_Foto-label">
                                        <g:if test="${platillo.archivo_Foto}">
                                            <img src="${request.contextPath}/assets/mes-icons/<f:display property='archivo_Foto'/>" style="width:200px;height:200px;"/>
                                        </g:if>
                                        <g:else>
                                            <img src="${request.contextPath}/assets/mes-icons/default.png" style="width:200px;height:200px;"/>
                                        </g:else>
                                    </div>
                                </li>                                
                                <li class="fieldcontain">
                                    <span id="calorias-label" class="property-label"><g:message code="platillo.calorias.label" default="Calorias" /></span>
                                    <div class="property-value" aria-labelledby="calorias-label"><f:display property="calorias"/></div>
                                </li>
                                <li class="fieldcontain">
                                    <span id="tipo-label" class="property-label"><g:message code="platillo.tipo.label" default="Tipo de platillo" /></span>
                                    <div class="property-value" aria-labelledby="tipo-label"><f:display property="tipo"/></div>
                                </li>
                                <li class="fieldcontain">
                                    <span id="categorias-label" class="property-label"><g:message code="platillo.categorias.label" default="Categorías" /></span>
                                    <div class="property-value" aria-labelledby="categorias-label"><f:display property="categorias"/></div>
                                </li>
								
                                
                                <li class="fieldcontain">
                                    <span id="activo-label" class="property-label"><g:message code="platillo.activo.label" default="Es un platillo activo" /></span>
                                    <div class="property-value" aria-labelledby="activo-label"><f:display property="activo"/></div>
                                </li>
                                
                                <li class="fieldcontain">
                                    <span id="id_Autor-label" class="property-label"><g:message code="platillo.id_Autor.label" default="Autor" /></span>
                                    <div class="property-value" aria-labelledby="id_Autor-label"><f:display property="id_Autor"/></div>
                                </li>
                            </ol>


                        </div>
                    </div>
                </div>
                <div class="col-md-4 col-sm-12 col-xs-12">
                    <div class="x_panel">
                        <div class="x_content">
                                <div class="x_title">
                                        <h2>Ingredientes</h2>
                                        <div class="clearfix"></div>
                                </div> 
                                <div class="fieldcontain">
                                    <div class="property-value" aria-labelledby="ingredientes-label"><f:display property="ingredientes"/></div>
                                </div>                            
                           
                        </div>
                    </div>
                </div>                






               
            </f:with>
            
            <g:form resource="${this.platillo}" method="DELETE" name="delete_form">
				<fieldset class="buttons">
					<g:link name="footer-edit" class="edit" action="edit" resource="${this.platillo}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:link name="footer-delete" class="delete" url="javascript:void(0)" onclick="jconfirm('${message(code: 'default.delete.ask.message', default: 'Are you sure?')}','${message(code: 'default.delete.info.message', default: 'You are deleting the record.')}');"><g:message code="default.button.delete.label" default="Delete" /></g:link>
                    <g:link name="footer-close" class="close" action="index"><g:message code="default.button.close.label" default="Close" /></g:link>
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
