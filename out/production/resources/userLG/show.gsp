<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
		<g:set var="entityName" value="${session.getAttribute('username_rolActivo') == 'ROLE_PROPIETARIO'?'Empleado':'Empleado'}" />
		<g:set var="entitiesName" value="${session.getAttribute('username_rolActivo') == 'ROLE_PROPIETARIO'?'Empleados':'Empleados'}" />
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

            function restart(id){
                swal({
                    title: '<g:message code="default.restart.ask.message" default="Do you want continue?"/>',
                    text: '<g:message code="default.restart.info.message" default="The password will be restart using the nick name"/>',
                    type: 'question',
                    showCancelButton: true,
                    confirmButtonColor: '#B4D14B',
                    confirmButtonText: 'Si, continuar!',
                    cancelButtonText: 'No',
                    confirmButtonClass: 'btn btn-success',
                    cancelButtonClass: 'btn btn-danger',
                    buttonsStyling: true
                }).then(function() {
					location.href = "${request.contextPath}/userLG/restart/"+id;
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
			  <li class="active">${userLG.nombre}</li>
			</ul>
		  </div>
		</div>
		<!-- .breadcrumb-box -->
        <a href="#show-userLG" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav_topbar" role="navigation">
            <ul>
                <li><a href="javascript:go('edit')"		class="edit"><g:message code="default.button.edit.label" default="Edit" /></a></li>
                <li><a href="javascript:go('delete')"	class="delete"><g:message code="default.button.delete.label" default="Delete" /></a></li>
                <li><a href="javascript:go('password')"	class="password"><g:message code="default.button.restart.label" default="Restart" /></a></li>
                <li><a href="javascript:go('close')"	class="close"><g:message code="default.button.close.label" default="close" /></a></li>
            </ul>
        </div>

        <br>
        <div id="show-userLG" class="content scaffold-show" role="main">
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>

			<div class="row">
                <div class="col-md-8 col-sm-12 col-xs-12">
                    <div class="x_panel">
                        <div class="x_content">
							<f:with bean="userLG">
								<ol class="property-list userLG">
									
									<div class="tituloPrincipal"><f:display property="nombre"/></div>
									<div class="tituloSecundario"><f:display property="establecimiento"/></div>
									<div class="tituloTerciario">${userLG.getRolName()}</div>
									
									<li class="fieldcontain">
										<span id="username-label" class="property-label"><g:message code="userLG.username.label" default="Usuario" /></span>
										<div class="property-value" aria-labelledby="username-label"><f:display property="username"/></div>
									</li>
									
									<li class="fieldcontain">
										<span id="password-label" class="property-label"><g:message code="userLG.password.label" default="Contraseña" /></span>
										<div class="property-value" aria-labelledby="password-label"><f:display property="session_id"/></div>
									</li>
									<g:if test="${userLG.telefono_Movil}">
									<li class="fieldcontain">
										<span id="telefono_Movil-label" class="property-label"><g:message code="userLG.telefono_Movil.label" default="Teléfono Móvil" /></span>
										<div class="property-value" aria-labelledby="telefono_Movil-label"><f:display property="telefono_Movil"/></div>
									</li>
									</g:if>
									
									<sec:ifAnyGranted roles='ROLE_PROGRAMADOR'>
									
									<g:if test="${userLG.ciudad}">
									<li class="fieldcontain">
										<span id="ciudad-label" class="property-label"><g:message code="userLG.ciudad.label" default="Ciudad" /></span>
										<div class="property-value" aria-labelledby="ciudad-label"><f:display property="ciudad"/></div>
									</li>
									</g:if>
									
									<g:if test="${userLG.fechaNacimiento}">
									<li class="fieldcontain">
										<span id="fechaNacimiento-label" class="property-label"><g:message code="userLG.fechaNacimiento.label" default="Fecha de nacimiento" /></span>
										<div class="property-value" aria-labelledby="fechaNacimiento-label">${userLG.fechaNacimiento.format('dd/MM/YYYY')}</div>
									</li>
									</g:if>
									
									<g:if test="${userLG.sexo}">
									<li class="fieldcontain">
										<span id="sexo-label" class="property-label"><g:message code="userLG.sexo.label" default="Sexo" /></span>
										<div class="property-value" aria-labelledby="sexo-label"><f:display property="sexo"/></div>
									</li>
									</g:if>
									
									<g:if test="${userLG.rfc}">
									<li class="fieldcontain">
										<span id="rfc-label" class="property-label"><g:message code="userLG.rfc.label" default="RFC" /></span>
										<div class="property-value" aria-labelledby="rfc-label"><f:display property="rfc"/></div>
									</li>
									</g:if>
									</sec:ifAnyGranted>
									
								</ol>

							</f:with>
							<sec:ifAnyGranted roles='ROLE_PROGRAMADOR'>
							<br>
							<div class="x_title">
								<h2>Hábitos y enfermedades</h2>
								<div class="clearfix"></div>
							</div>   
							<f:with bean="userLG">
								<ol class="property-list userLG">


									<li class="fieldcontain">
										<span id="consumeAlcohol-label" class="property-label"><g:message code="userLG.consumeAlcohol.label" default="Consume alcohol" /></span>
										<div class="property-value" aria-labelledby="consumeAlcohol-label"><f:display property="consumeAlcohol"/></div>
									</li>

									<li class="fieldcontain">
										<span id="realizaDeportes-label" class="property-label"><g:message code="userLG.realizaDeportes.label" default="Realiza deportes" /></span>
										<div class="property-value" aria-labelledby="realizaDeportes-label"><f:display property="realizaDeportes"/></div>
									</li>
									
									<g:if test="${userLG.enfermedadesCronicas}">
									<li class="fieldcontain">
										<span id="enfermedadesCronicas-label" class="property-label"><g:message code="userLG.enfermedadesCronicas.label" default="Enfermedad crónica" /></span>
										<div class="property-value" aria-labelledby="enfermedadesCronicas-label"><f:display property="enfermedadesCronicas"/></div>
									</li>
									</g:if>
									
									<g:if test="${userLG.tipoDieta}">
									<li class="fieldcontain">
										<span id="tipoDieta-label" class="property-label"><g:message code="userLG.tipoDieta.label" default="Tipo de dieta" /></span>
										<div class="property-value" aria-labelledby="tipoDieta-label"><f:display property="tipoDieta"/></div>
									</li>
									</g:if>
								</ol>

							</f:with>
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
                                            <f:with bean="userLG">
                                                <ol class="property-list userLG">


                                                    <li class="fieldcontain">
                                                        <center>
                                                        <div class="property-value" aria-labelledby="archivo_Foto-label">
                                                            <g:if test="${userLG.archivo_Foto}">
                                                                <img src="${request.contextPath}/assets/mes-users/<f:display property="archivo_Foto"/>" style="width:200px;height:200px;"/>
                                                            </g:if>
                                                            <g:else>
                                                                <img src="${request.contextPath}/assets/mes-users/default.png" style="width:200px;height:200px;"/>
                                                            </g:else>
                                                        </div>
                                                        </center>
                                                    </li>
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


                                            </f:with>

                        </div>
                    </div>
                </div>   
             
            </div>

            <g:form resource="${this.userLG}" method="DELETE" name="delete_form">
                <fieldset class="buttons">
					<g:link name="footer-edit" class="edit" action="edit" resource="${this.userLG}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:link name="footer-delete" class="delete" url="javascript:void(0)" onclick="jconfirm('${message(code: 'default.delete.ask.message', default: 'Are you sure?')}','${message(code: 'default.delete.info.message', default: 'You are deleting the record.')}');"><g:message code="default.button.delete.label" default="Delete" /></g:link>
                    <g:link name="footer-password" class="password" url="javascript:restart(${this.userLG.id})"><g:message code="default.button.restart.label" default="Restart" /></g:link>
                    <g:link name="footer-close" class="close" action="index"><g:message code="default.button.close.label" default="Close" /></g:link>
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
