<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'userLG.label', default: 'Usuario')}" />
        <title><g:message code="default.login.label" args="[entityName]" /></title>
        
        <link href='https://fonts.googleapis.com/css?family=Roboto:400,700' rel='stylesheet' type='text/css'>
		<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
		
		<asset:stylesheet src="logins.css"/>
		<asset:stylesheet src="public.css"/>
		
        <script language="javascript">
			function submit_login() {
				$('form#login-form').submit();
			}
			function submit_request() {
				$('form#recupera-form').submit();
			}
			$(document).ready(function(){
				
				$("#login-form").hide();
				$("#registro-form").hide();
				$("#recupera-form").hide();
				
				$(".hesap-olustur-link").click(function(e){
					$("#login-form").slideUp(0);
					$("#registro-form").fadeIn(300);
				});

				$(".zaten-hesap-var-link").click(function(e){
					$("#registro-form").slideUp(0);
					$("#recupera-form").slideUp(0);	
					$("#login-form").fadeIn(300);
				});

				$(".sifre-hatirlat-link").click(function(e){
					$("#login-form").slideUp(0);
					$("#recupera-form").fadeIn(300);
				});
				
				<g:if test="${params.sendPassword}">
                    $("#recupera-form").fadeIn(700);
                </g:if>
                <g:else>
                    $("#login-form").fadeIn(700);
                </g:else>
				
			});

        </script>
    </head>
    <body>
        <div id="content" role="main" class="background-image-login">
			<div class="login-card form-login-style">
				<div class="login-footer">
					<g:copyright startYear="2017" encodeAs="raw">SambaFresh v<g:meta name="info.app.version"/></g:copyright>
					<!--
					<a href="http://weykhans.com"><img src="${request.contextPath}/assets/ws_red.png" width="100px" style="float:right" border="0px"></a>
					-->
				</div>
				<g:hasErrors bean="${this.userLG}">
				<ul class="errors" role="alert" style="valign:top">
					<g:eachError bean="${this.userLG}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</g:hasErrors>
				
				<!-- Iniciar sesión -->
				<g:form controller="login" action="authenticate" autocomplete="off" name="login-form" style="display:none;">

					<!-- Logo -->
					<div>
						<asset:image src="samba.png" style="width:300px;"/>
					</div>
					
					<div style="clear:both;"></div>
					
					<div class="title" style="text-align:center;">
						<label>Iniciar sesión</label>
						<span>Comienza a administrar tu sucursal.</span>
					</div>
					
					<!-- Usuario -->
					<div class="group">
						<input  name="username" id="username" type="text" required autocomplete="off" value="${session.getAttribute('user')}">
						<span class="highlight"></span>
						<span class="<g:if test='${flash.message}'>bar red</g:if><g:else>bar green</g:else>" ></span>
						<label <g:if test="${flash.message}">style="color:#f58f48 !important;"</g:if> >
							<i class="material-icons input-ikon">mail_outline</i>
							<span class="span-input">Correo electrónico</span>
						</label>
						<g:if test="${flash.message}">
						<div style="color:#a7a7a7;display:inline-block;position:relative;float:right;">${flash.message}&nbsp;<asset:image src="info_small_red.png"/></div>
						</g:if>
					</div>
					
					<!-- Contraseña -->
					<div class="group">
						<input type="password" name="password" id="password" required autocomplete="off">
						<span class="highlight"></span>
						<span class="<g:if test='${flash.message}'>bar red</g:if><g:else>bar green</g:else>" ></span>
						<label <g:if test="${flash.message}">style="color:#f58f48 !important;"</g:if> >
							<i class="material-icons input-sifre-ikon">lock</i>
							<span class="span-input">Contraseña</span>
						</label>
					</div>
					
					<!-- Boton -->
					<a href="javascript:submit();" class="giris-yap-buton" id="login" name="login">Iniciar sesión</a>
					
					<!-- Enlaces -->
					<div class="forgot-and-create tab-menu">
						<a class="sifre-hatirlat-link" href="javascript:void('sifre-hatirlat-link');">¿Olvidaste tu contraseña?</a>
						<!--
						<a class="hesap-olustur-link" href="javascript:void('hesap-olustur-link');">¡Registrate!</a>
						-->
					</div>
				</g:form>
				
				<!-- Registro -->
				<form id="registro-form" class="col-lg-12" style="display:none;">

					<!-- Logo -->
					<div>
						<asset:image src="samba.png" style="width:300px;"/>
					</div>
					
					<div style="clear:both;"></div>
					
					<div class="title" style="text-align:center;">
						<label>¡Registrate!</label>
						<span>Capture sus datos de registro.</span>
					</div>

					<!-- Email -->
					<div class="group">
						<input type="text" required>
						<span class="highlight"></span>
						<span class="bar green"></span>
						<label><i class="material-icons input-ikon">mail_outline</i><span class="span-input">Correo electrónico</span></label>
					</div>

					<!-- Contraseña -->
					<div class="group">
						<input type="password" required>
						<span class="highlight"></span>
						<span class="bar green"></span>
						<label><i class="material-icons input-sifre-ikon">lock</i><span class="span-input">Contraseña</span></label>
					</div>

					<!-- Contraseña -->
					<div class="group">
						<input type="password" required>
						<span class="highlight"></span>
						<span class="bar green"></span>
						<label><i class="material-icons input-sifre-ikon">lock</i><span class="span-input">Repite la contraseña</span></label>
					</div>

					<!-- Boton -->
					<a href="javascript:void(0);" class="kayit-ol-buton">Registrar usuario</a>

					<!-- Enlaces -->
					<div class="forgot-and-create tab-menu">
						<a class="zaten-hesap-var-link" href="javascript:void('zaten-hesap-var-link');">Iniciar sesión</a>
					</div>
					
				</form>

				<!-- Recuperar contraseña -->
				<g:form controller="login" action="sendPassword" autocomplete="off" name="recupera-form" class="col-lg-12" style="display:none;">
					<!-- Logo -->
					<div>
						<asset:image src="samba.png" style="width:300px;"/>
					</div>
					
					<div style="clear:both;"></div>
					
					<div class="title" style="text-align:center;">
						<label>¿Olvidaste tu contraseña?</label>
						<span>Solicítala a tu correo electrónico.</span>
					</div>
					
					<!-- Email -->
					<div class="group">
						<input name="email" id="email" type="text" required>
						<span class="highlight"></span>
						<span class="bar green"></span>
						<label><i class="material-icons input-ikon">mail_outline</i><span class="span-input">Correo electrónico</span></label>
						<g:if test="${params.sendPassword == "true"}">
							<div style="color:#a7a7a7;display:inline-block;position:relative;float:right;">Se ha enviado su contraseña al correo proporcionado&nbsp;<asset:image src="info_small_red.png"/></div>
						</g:if>
						<g:if test="${params.sendPassword == "false"}">
							<div style="color:#a7a7a7;display:inline-block;position:relative;float:right;">El correo proporcionado es inválido&nbsp;<asset:image src="info_small_red.png"/></div>
						</g:if>
					</div>

					<!-- Boton -->
                    <a href="javascript:submit_request();" class="sifre-hatirlat-buton">Solicitar contraseña</a>
                    
					<!-- Enlaces -->
					<div class="forgot-and-create tab-menu">
						<a class="zaten-hesap-var-link" href="javascript:void('zaten-hesap-var-link');">Iniciar sesión</a>
					</div>
				</g:form>
			</div>
		</div>
    </body>
</html>
