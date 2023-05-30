<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'platillo.label', default: 'Platillo')}" />
        <g:set var="entitiesName" value="${message(code: 'platillo.label', default: 'Platillos')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        <style>
            #nombre, #descripcion {width:50%}
            #tipo {width:30%}
			#ingredientes, #categorias {width:50%}
        </style>
        <script language="javascript">
			$( document ).ready(function() {
				$("#tipo, #categorias").select2({
					language: "es"
				});
				
				$("#ingredientes").select2();
				
				$('INPUT[type="file"]').change(function () {
					var ext = this.value.match(/\.(.+)$/)[1].toUpperCase();
					switch (ext) {
						case 'JPG':
						case 'JPEG':
						case 'PNG':
						case 'GIF':
							break;
						default:
							this.value = '';
							swal({
								title: "Advertencia",
								text: "Esta intentando subir un archivo que NO es una imagen",
								type: "warning",
								showCancelButton: false,
								confirmButtonColor: '#FF7C24',
								confirmButtonText: 'Aceptar',
								confirmButtonClass: 'btn btn-success',
								buttonsStyling: true
							});
					}
				});
			});
			
			function go(id_tag){
				$('[name="footer-'+id_tag+'"]')[0].click();
			}
			
			function PreviewImage(id_upload, id_preview) {
				var ext = $("#"+id_upload).val().match(/\.(.+)$/)[1].toUpperCase();
				if (ext != "JPG" && ext != "JPEG" && ext != "GIF" && ext != "PNG"){
					document.getElementById(id_preview).src = "${request.contextPath}/assets/mes-icons/default.png";
				}else{
					var oFReader = new FileReader();
					oFReader.readAsDataURL(document.getElementById(id_upload).files[0]);

					oFReader.onload = function (oFREvent) {
						document.getElementById(id_preview).src = oFREvent.target.result;
					};
				}
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
			  <li><g:link action="show" resource="${this.platillo}"><g:message code="default.show.label" args="[entityName]" /></g:link></li>
			  <li class="active">${platillo.nombre}</li>
			</ul>	
		  </div>
		</div>
		<!-- .breadcrumb-box -->
        <a href="#edit-platillo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav_topbar" role="navigation">
            <ul>
				<li><a href="javascript:go('save')"		class="save"><g:message code="default.button.save.label" default="Edit" /></a></li>
                <li><a href="javascript:go('close')"	class="close"><g:message code="default.button.close.label" default="close" /></a></li>
            </ul>
        </div>
        <div id="edit-platillo" class="content scaffold-edit" role="main">
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.platillo}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.platillo}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:uploadForm resource="${this.platillo}" action="upchange" method="POST">
                <g:hiddenField name="version" value="${this.platillo?.version}" />
                <fieldset class="form">
                    %{--
                    <f:all bean="platillo"/>
                    --}%
                    

                    

                    <f:with bean="platillo">

                        <div class="row">

                            <div class="col-md-12 col-sm-12 col-xs-12">
                                <div class="x_panel">
                                    <div class="x_content">                       
                                        <f:field property="nombre"/>
                                        <f:field property="descripcion"/>
                                        <f:field property="archivo_Foto" wrapper="icon"/>                                        
                                        <f:field property="calorias"/>
                                        <f:field property="tipo"/>
                                        <div class="fieldcontain">
                                            <label for="categorias" class="">Categorías</label>
                                            <g:select multiple="true" optionKey="id" optionValue="${{ categoria -> "${categoria}" }}" name="categorias" value="${platillo?.categorias*.id}" from="${categoriasList}" />
                                        </div>
                                        <div class="fieldcontain">
                                            <label for="ingredientes" class="">Ingredientes</label>
                                            <g:select multiple="true" optionKey="id" optionValue="${{ ingrediente -> "${ingrediente}" }}" name="ingredientes" value="${platillo?.ingredientes*.id}" from="${ingredientesList}" />
                                        </div>
                                        <f:field property="activo"/>
                                        <f:field property="id_Autor" wrapper="display"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </f:with>                    
                </fieldset>
                <g:submitButton name="footer-save" style="visibility:hidden;position:absolute;top:0px;width:0px;"/>
                <fieldset class="buttons">
					<g:link class="save" url="javascript:go('save')"><g:message code="default.button.save.label" default="Save" /></g:link>
                    <g:link name="footer-close" class="close" action="show" resource="${this.platillo}"><g:message code="default.button.close.label" default="Close" /></g:link>
                </fieldset>
            </g:uploadForm>
        </div>
    </body>
</html>
