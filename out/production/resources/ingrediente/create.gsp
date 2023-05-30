<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'ingrediente.label', default: 'Ingrediente')}" />
        <g:set var="entitiesName" value="${message(code: 'ingrediente.label', default: 'Ingredientes')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <style>
            #nombre {width:50%}
        </style>
        <script language="javascript">
			$( document ).ready(function() {
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
			  <li class="active"><g:message code="default.new.label" args="[entityName]" /></li>
			</ul>	
		  </div>
		</div>
		<!-- .breadcrumb-box -->
        <a href="#create-ingrediente" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav_topbar" role="navigation">
            <ul>
				<li><a href="javascript:go('save')"		class="save"><g:message code="default.button.save.label" default="Edit" /></a></li>
                <li><a href="javascript:go('close')"	class="close"><g:message code="default.button.close.label" default="close" /></a></li>
            </ul>
        </div>
        <div id="create-ingrediente" class="content scaffold-create" role="main">
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.ingrediente}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.ingrediente}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:uploadForm action="save">
                <fieldset class="form">
                    <f:with bean="ingrediente">
                        <div class="row">
                            <div class="col-md-12 col-sm-12 col-xs-12">
                                <div class="x_panel">
                                    <div class="x_content">
                                        <f:field property="nombre"/>
                                        <f:field property="unidadMedicion"/>  
                                        <f:field property="archivo_Foto"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </f:with>
                    
                </fieldset>
                <g:submitButton name="footer-save" style="visibility:hidden;position:absolute;top:0px;width:0px;"/>
                <fieldset class="buttons">
					<g:link class="save" url="javascript:go('save')"><g:message code="default.button.save.label" default="Save" /></g:link>
                    <g:link name="footer-close" class="close" action="index"><g:message code="default.button.close.label" default="Close" /></g:link>
                </fieldset>
            </g:uploadForm>
        </div>
    </body>
</html>
