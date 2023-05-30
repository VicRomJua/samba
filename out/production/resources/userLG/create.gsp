<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
		<g:set var="entityName" value="${session.getAttribute('username_rolActivo') == 'ROLE_PROPIETARIO'?'Empleado':'Empleado'}" />
		<g:set var="entitiesName" value="${session.getAttribute('username_rolActivo') == 'ROLE_PROPIETARIO'?'Empleados':'Empleados'}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <style>
            #establecimiento, #nombre {width:50%}
            #username {width:50%}
            #tipoDieta {width:50%}
            #enfermedadesCronicas {width:50%}
        </style>
        <script language="javascript">
            $( document ).ready(function() {
            	$("#telefono_Movil").mask('(000) 000-0000', {placeholder: "Ej. (111) 111-1111"});

                $("#role").select2();
                
                <sec:ifAnyGranted roles='ROLE_PROPIETARIO,ROLE_PROGRAMADOR'>
                $("#sexo").select2();
                $("#establecimiento").select2();
				</sec:ifAnyGranted>

                hiddeEstablecimiento();
                $("#role").change(function() {
					hiddeEstablecimiento();
				});
				
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
            
            function hiddeEstablecimiento(){
				opcion = $( "#role option:selected" ).text();
				if (opcion == 'Propietario'){
					$("#establecimiento").parent().hide();
					$("label[for='establecimiento']").addClass("hidden");
				}else{
					$("#establecimiento").parent().show();
					$("label[for='establecimiento']").removeClass("hidden");
				}
			}
            
            function PreviewImage(id_upload, id_preview) {
                var ext = $("#"+id_upload).val().match(/\.(.+)$/)[1].toUpperCase();
				if (ext != "JPG" && ext != "JPEG" && ext != "GIF" && ext != "PNG"){
					document.getElementById(id_preview).src = "${request.contextPath}/assets/mes-users/default.png";
				}else{
					var oFReader = new FileReader();
					oFReader.readAsDataURL(document.getElementById(id_upload).files[0]);

					oFReader.onload = function (oFREvent) {
						document.getElementById(id_preview).src = oFREvent.target.result;
					};
				}
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
              <li class="active"><g:message code="default.new.label" args="[entityName]" /></li>
            </ul>
          </div>
        </div>
        <!-- .breadcrumb-box -->
        <a href="#create-userLG" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav_topbar" role="navigation">
            <ul>
                <li><a href="javascript:go('save')"     class="save"><g:message code="default.button.save.label" default="Edit" /></a></li>
                <li><a href="javascript:go('close')"    class="close"><g:message code="default.button.close.label" default="close" /></a></li>
            </ul>
        </div>
        <div id="create-userLG" class="content scaffold-create" role="main">
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.userLG}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.userLG}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:uploadForm action="save">
                <fieldset class="form">
                    <f:with bean="userLG">            
                        <div class="row">

                            <div class="col-md-8 col-sm-12 col-xs-12">
                                <div class="x_panel">
                                    <div class="x_content">
                                                    
										<div class="x_title">
											<h2>Datos generales <small>Información personal</small></h2>
											<div class="clearfix"></div>
										</div>
										<f:field property="nombre"/>
										<f:field property="username"/>
										<!--<f:field property="password" label="Contraseña"/>-->
										<label for="Contraseña" style="width: 29.7%; text-align: right; color:#666666;">Contraseña <span class='required-indicator'>*</span></label>
										<g:field type="text" name="password" minlength="8" maxlength="16" required="" style="width: 50%; margin-top: 11px"/>
										<g:hiddenField name="session_id" value="hidden" />
										<f:field property="telefono_Movil"/>
										
										<sec:ifAnyGranted roles='ROLE_PROGRAMADOR'>
											<f:field property="ciudad"/>
										</sec:ifAnyGranted>
										
										<br>
										<div class="x_title">
											<h2>Privilegios</h2>
											<div class="clearfix"></div>
										</div>
										<div class="fieldcontain">
											<label for="role">Rol</label>
											<g:select id="role" name="role" from="${roles}" optionKey="authority" optionValue="nombre" />
										</div>
										
										<sec:ifAnyGranted roles='ROLE_PROPIETARIO,ROLE_PROGRAMADOR'>
											<f:field property="establecimiento">
												<g:select id="establecimiento" name="establecimiento" from="${establecimientos}" optionKey="id" optionValue="nombre" noSelection="['':'NO asignada']"/>
											</f:field>	
                                        </sec:ifAnyGranted>
                                        <sec:ifNotGranted roles="ROLE_PROPIETARIO,ROLE_PROGRAMADOR">
											<input type="hidden" name="establecimiento" id="establecimiento" value="${userLG.establecimiento?.id}" />
										</sec:ifNotGranted>
										
										<sec:ifAnyGranted roles='ROLE_PROGRAMADOR'>
											<f:field property="empresa">
												<g:select id="empresa" name="empresa" from="${empresas}" optionKey="id" optionValue="nombre" noSelection="['':'NO asignada']" />
											</f:field>
											<f:field property="fechaNacimiento"/>
											<f:field property="sexo"/>
											<f:field property="rfc"/>
											<br>
											<div class="x_title">
												<h2>Hábitos y enfermedades</h2>
												<div class="clearfix"></div>
											</div>  
											<f:field property="consumeAlcohol"/>
											<f:field property="realizaDeportes"/>
											<f:field property="enfermedadesCronicas"/>
											<f:field property="tipoDieta"/>
										</sec:ifAnyGranted>
										
                                    </div>
                                </div>
                             </div>

                            <div class="col-md-4 col-sm-12 col-xs-12">
                                <div class="x_panel">
                                    <div class="x_content"> 
										<div class="x_title">
											<h2>Fotografía</h2>
											<div class="clearfix"></div>
										</div> 
										<f:field property="archivo_Foto" wrapper="photolight"/>

										<div class="x_title">
											<h2>Configuración de la cuenta</h2>
											<div class="clearfix"></div>
										</div> 
										<div class="checks">
											<f:field property="accountLocked" wrapper="hidden"/>
											<f:field property="accountExpired" wrapper="hidden"/>
											<f:field property="passwordExpired" wrapper="hidden"/>
											<f:field property="enabled" wrapper="checkboxFirst"/>
										</div>                                     
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
