<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'userLG.label', default: 'Usuario')}" />
        <g:set var="entitiesName" value="${message(code: 'userLG.label', default: 'Usuarios')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
        <style>
            #nombre {width:50%}
            #username {width:30%}
        </style>
        <script language="javascript">
			$( document ).ready(function() {
				
				$("#telefono_Movil").mask('(000) 000-0000', {placeholder: "Ej. (111) 111-1111"});
				
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
			
			function jconfirm(pregunta,mensaje){
                swal({
                    title: pregunta,
                    text: mensaje,
                    type: 'question',
                    showCancelButton: true,
                    confirmButtonColor: '#76BD1D',
                    confirmButtonText: 'Si, continuar!',
                    cancelButtonText: 'No',
                    confirmButtonClass: 'btn btn-success',
                    cancelButtonClass: 'btn btn-danger',
                    buttonsStyling: true
                }).then(function() {
                     go('save');
                });
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
            	if (id_tag=="save"){
	            	txtPasswordAnterior = $("#txtPasswordAnterior").val();
	            	passwordAnterior = $("#passwordAnterior").val();
	            	password = $("#password").val();
	            	passwordNuevo = $("#passwordNuevo").val();
	            	passwordRepeat = $("#passwordRepeat").val();

	            	if (txtPasswordAnterior != ""){
						if (txtPasswordAnterior != passwordAnterior){
							swal({
								title:"Advertencia",
								text: "Tu contraseña anterior NO es correcta.",
								type: "warning",
								showCancelButton: false,
								confirmButtonColor: '#FF7C24',
								confirmButtonText: 'Aceptar',
								confirmButtonClass: 'btn btn-success',
								buttonsStyling: true
							});							
						} else {
							if (passwordNuevo == ""){
								swal({
									title:"Advertencia",
									text: "Por favor, indica tu nueva contraseña.",
									type: "warning",
									showCancelButton: false,
									confirmButtonColor: '#FF7C24',
									confirmButtonText: 'Aceptar',
									confirmButtonClass: 'btn btn-success',
									buttonsStyling: true
								});										
							} else {
								if (passwordNuevo!=passwordRepeat){
									swal({
										title:"Advertencia",
										text: "Tu nueva contraseña NO empata con la que repetiste.",
										type: "warning",
										showCancelButton: false,
										confirmButtonColor: '#FF7C24',
										confirmButtonText: 'Aceptar',
										confirmButtonClass: 'btn btn-success',
										buttonsStyling: true
									});									
								} else if (passwordNuevo == passwordAnterior) {
									swal({
										title:"Advertencia",
										text: "La nueva contraseña debe ser diferente a la anterior.",
										type: "warning",
										showCancelButton: false,
										confirmButtonColor: '#FF7C24',
										confirmButtonText: 'Aceptar',
										confirmButtonClass: 'btn btn-success',
										buttonsStyling: true
									});	
								} else {
									$("#cambiarPassword").val("true");
									$('[name="footer-'+id_tag+'"]')[0].click();
								}
							}

						}
					} else {
						$('[name="footer-'+id_tag+'"]')[0].click();
					}
				} else {
					$('[name="footer-'+id_tag+'"]')[0].click();
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
			  <li class="active"><g:message code="default.myuser.label" args="[entityName]" /></li>
			</ul>	
		  </div>
		</div>
		<!-- .breadcrumb-box -->
        <a href="#show-userLG" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav_topbar" role="navigation">
            <ul>
				<li><a href="javascript:jconfirm('${message(code: 'default.myuser.ask.message', default: 'Are you sure?')}','${message(code: 'default.myuser.info.message', default: 'You are changing your personal data.')}')" class="save"><g:message code="default.button.save.label" default="Edit" /></a></li>
                <li><a href="javascript:go('close')"	class="close"><g:message code="default.button.close.label" default="close" /></a></li>
            </ul>
        </div>
        <div id="show-userLG" class="content scaffold-show" role="main">
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message.replace("&#64;","@")}</div>
            </g:if>
			<g:hasErrors bean="${this.userLG}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.userLG}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
			<g:uploadForm resource="${this.userLG}" action="updateMyUser" method="POST">
				<f:with bean="userLG">


                    <div class="col-md-8 col-sm-12 col-xs-12">
                        <div class="x_panel">
                            <div class="x_content">
                                            
                                            <div class="x_title">
                                                <h2>Datos generales <small>Información personal</small></h2>
                                                <div class="clearfix"></div>
                                            </div>   

								<ol class="property-list userLG">
									<div class="tituloPrincipal"><f:display property="nombre"/></div>
									<div class="tituloSecundario"><f:display property="establecimiento"/></div>
									<div class="tituloTerciario">${userLG.getRolName()}</div>
									
									<li class="fieldcontain">
										<span id="username-label" class="property-label"><g:message code="userLG.username.label" default="Usuario" /></span>
										<div class="property-value" aria-labelledby="username-label"><f:display property="username"/></div>
									</li>
									<f:field property="telefono_Movil" id="telefono_Movil" wrapper="textlight"/>
									
									<g:hiddenField name="username" value="${userLG.username}" />
									<g:hiddenField name="nombre" value="${userLG.nombre}" />
									<g:hiddenField name="role" value="${userLG.getRol()}" />
									<g:hiddenField name="enabled" value="${userLG.enabled}" />
									<g:hiddenField name="accountExpired" value="${userLG.accountExpired}" />
									<g:hiddenField name="accountLocked" value="${userLG.accountLocked}" />
									<g:hiddenField name="passwordExpired" value="${userLG.passwordExpired}" />
									<br>
									<br>


									<div class="x_title">
										<h2>Contraseña - <span style="font-size: 14px;">Si deseas cambiarla llena los campos, de lo contrario déjalos como están.</span></h2>
										<div class="clearfix"></div>
									</div>  

							
									<!--<f:field property="password" label="Contraseña"/>-->
									<label style="width: 29.7%; text-align: right; color:#666666;">Contraseña anterior</label>

									<g:field type="password" id="txtPasswordAnterior" name="txtPasswordAnterior" minlength="8" maxlength="16" style="width: 50%; margin-top: 11px"/>
									<g:hiddenField name="passwordAnterior" id="passwordAnterior" value="${userLG.password}" />

										<g:hiddenField name="cambiarPassword" id="cambiarPassword" value="false" />
										<g:hiddenField name="password" id="password" value="${userLG.password}" />

									<label for="Contraseña" style="width: 29.7%; text-align: right; color:#666666;">Nueva contraseña</label>

									<g:field type="password" id="passwordNuevo" name="passwordNuevo" minlength="8" maxlength="16" style="width: 50%; margin-top: 11px"/>

									<label for="Contraseña" style="width: 29.7%; text-align: right; color:#666666;">Repite tu nueva contraseña</label>
									<g:field type="password" id="passwordRepeat" name="passwordRepeat" minlength="8" maxlength="16" style="width: 50%; margin-top: 11px"/>


									<!--

									<f:field property="password" label="Contraseña" wrapper="textlight"/>
									<g:hiddenField name="session_id" value="${userLG.session_id}" />
									-->
									
									
								</ol>                                            
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
								<ol class="property-list userLG">
									<!--
									<li class="fieldcontain">
										<span id="accountLocked-label" class="property-label"><g:message code="userLG.accountLocked.label" default="Cuenta bloqueada" /></span>
										<div class="property-value" aria-labelledby="accountLocked-label"><f:display property="accountLocked"/></div>
									</li>

									<li class="fieldcontain">
										<span id="accountExpired-label" class="property-label"><g:message code="userLG.accountExpired.label" default="Expira la cuenta" /></span>
										<div class="property-value" aria-labelledby="accountExpired-label"><f:display property="accountExpired"/></div>
									</li>

									<li class="fieldcontain">
										<span id="passwordExpired-label" class="property-label"><g:message code="userLG.passwordExpired.label" default="Expira la contraseña" /></span>
										<div class="property-value" aria-labelledby="passwordExpired-label"><f:display property="passwordExpired"/></div>
									</li>
									-->
									<li class="fieldcontain">
										<span id="enabled-label" class="property-label"><g:message code="userLG.enabled.label" default="Activo" /></span>
										<div class="property-value" aria-labelledby="enabled-label"><f:display property="enabled"/></div>
									</li>
								</ol>
                        	</div>
                    	</div>
                    </div>


				</f:with>
				<g:submitButton name="footer-save" style="visibility:hidden;position:absolute;top:0px;width:0px;"/>
                <fieldset class="buttons">
					<g:link class="save" url="javascript:go('save')"><g:message code="default.button.save.label" default="Save" /></g:link>
                    <g:link name="footer-close" class="close" uri="/"><g:message code="default.button.close.label" default="Close" /></g:link>
				</fieldset>
			</g:uploadForm>
        </div>
    </body>
</html>
