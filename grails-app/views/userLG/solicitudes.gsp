<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
		<g:set var="entityName" value="Empleado" />
		<g:set var="entitiesName" value="Empleados" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <script language="javascript">
			$(document).ready(function(e){
				$(".search-panel .dropdown-menu").find("a").click(function(e) {
					e.preventDefault();
					var param_max = $(this).attr("href").replace("#","");
					var concept = $(this).text();
					$(".search-panel span#search_concept").text(concept);
					$("#search_rows").val(param_max);
					if ($("#search_word").val() == ""){
						$("#search_clean").val("1");
					}
					$( "#search_form" ).submit();
				});
				$("#btn-search").click(function(e) {
					if ($("#search_word").val() == ""){
						$("#search_clean").val("1");
					}
					$( "#search_form" ).submit();
				});
				$("#search_word").on("keydown", function (e) {
					if (e.keyCode == 13) {
						if ($("#search_word").val() == ""){
							$("#search_clean").val("1");
						}
						$( "#search_form" ).submit();
					}
				});
				/*
				$("#search_word").donetyping(function(){
					if ($("#search_word").val() == ""){
						$("#search_clean").val("1");
					}
					$( "#search_form" ).submit();
				});
				*/
				$("#search_word").focus();
			});

            function jconfirm(pregunta,mensaje,id){
                swal({
                    title: pregunta,
                    text: mensaje,
                    type: 'question',
                    showCancelButton: true,
                    confirmButtonColor: '#FF7C24',
                    confirmButtonText: 'Si, eliminar!',
                    cancelButtonText: 'No',
                    confirmButtonClass: 'btn btn-success',
                    cancelButtonClass: 'btn btn-danger',
                    buttonsStyling: true
                }).then(function() {
                     location.href = "${request.contextPath}/userLG/remove/"+id;
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

            function showImage(nombre, archivo){
				swal({
				  title: nombre,
				  confirmButtonColor: '#B4D14B',
				  html:
					'<img src="${request.contextPath}/assets/mes-users/'+archivo+'" border="0px" style="width:250px; height:300px"/>',
				  showCloseButton: true,
				  confirmButtonText:
					'<i class="fa fa-thumbs-up"></i> Aceptar'
				})
			}
			
			
			function showLimite(user){
				swal({
				  title: "Indique el monto límite",
				  html:
					'<span style="font-size:26px;font-weight:bold;">$</span> <input type="text" id="limite" name="limite" onkeypress="return justNumbers(event);">',
				  showCancelButton: true,
                  confirmButtonColor: '#B4D14B',
                  confirmButtonText: 'Aceptar',
                  cancelButtonText: 'Cancelar',
                  confirmButtonClass: 'btn btn-success',
                  cancelButtonClass: 'btn btn-danger',
                  buttonsStyling: true
                }).then(function() {
					var valor = document.getElementById("limite").value;      
					location.href =  "${request.contextPath}/userLG/nomina?user_id=" + user + "&limite=" + valor;
                }, function (dismiss) {
					if (dismiss === 'cancel') {
						var tmp_switch = document.querySelector("#sw-"+user);
						tmp_switch.checked = false;
						onChange(tmp_switch);
					}
				});
			}
			
			function justNumbers(e){
			   var keynum = window.event ? window.event.keyCode : e.which;
			   if ((keynum == 8) || (keynum == 46))
					return true;
				return /\d/.test(String.fromCharCode(keynum));
			}
			
			function onChange(el) {
				if (typeof Event === 'function' || !document.fireEvent) {
					var event = document.createEvent('HTMLEvents');
					event.initEvent('change', true, true);
					el.dispatchEvent(event);
				} else {
					el.fireEvent('onchange');
				}
			}
			
			function showCancel(user,nombre_usuario){
                swal({
                    title: "Cancelará el pago por nomina",
                    text: "Si continua el usuario \""+nombre_usuario+"\" no podrá efectuar pagos por nómina en la plataforma Samba Smoothie",
                    type: 'question',
                    showCancelButton: true,
                    confirmButtonColor: '#FF7C24',
                    confirmButtonText: 'Si, continuar!',
                    cancelButtonText: 'No',
                    confirmButtonClass: 'btn btn-success',
                    cancelButtonClass: 'btn btn-danger',
                    buttonsStyling: true
                }).then(function() {
                     location.href = "${request.contextPath}/userLG/nomina?user_id=" + user + "&limite=0.0";
                }, function (dismiss) {
					if (dismiss === 'cancel') {
						var tmp_switch = document.querySelector("#sw-"+user);
						tmp_switch.checked = true;
						onChange(tmp_switch);
					}
				});
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
			  <li class="active"><g:message code="default.list.label" args="['solicitudes']" /></li>
			</ul>
		  </div>
		</div>
		<!-- .breadcrumb-box -->
		<div class="x_panel">
	        <a href="#list-userLG" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
	        <div class="nav_topbar" role="navigation">
	            <ul>
	                <div class="col-xs-6 col-xs-offset-2" style="float:right;padding: 0px;">
						<g:if test="${userLGCount != 0 || session.getAttribute('userLG_search_word')}">
						<g:form action="solicitudes" method="POST" name="search_form">
							<div class="input-group">
								<div class="input-group-btn search-panel">
									<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
										<span id="search_concept">${session.getAttribute('userLG_search_rows')} Registros</span> <span class="caret"></span>
									</button>
									<ul class="dropdown-menu" role="menu">
									  <li><a href="#10">10 Registros</a></li>
									  <li><a href="#20">20 Registros</a></li>
									  <li><a href="#50">50 Registros</a></li>
									  <li><a href="#100">100 Registros</a></li>
									</ul>
								</div>
								<input type="hidden" name="search_rows" id="search_rows" value="${session.getAttribute('userLG_search_rows')}">
								<input type="hidden" name="search_clean" id="search_clean" value="0">
								<input type="text" class="form-control" name="search_word" id="search_word" placeholder="Palabra a buscar..." value="${session.getAttribute('userLG_search_word')}">
								<span class="input-group-btn">
									<button id="btn-search" class="btn btn-primary" type="button"><li class="fa fa-search fa-lg"></li></button>
								</span>
							</div>
						</g:form>
						</g:if>
					</div>
				</ul>
	        </div>
	        <div id="list-userLG" class="content scaffold-list" role="main">
	            <g:if test="${flash.message}">
	                <div class="message" role="status">${flash.message.replace("&#64;","@")}</div>
	            </g:if>
				<!--
	            <f:table collection="${userLGList}" properties="['username', 'accountLocked', 'accountExpired', 'passwordExpired', 'enabled']"/>
				-->
	            <table>
					<g:if test="${userLGCount != 0}">
	                <thead>
	                    <tr>
							<g:sortableColumn property="archivo_Foto" title="${message(code: 'userLG.archivo_Foto.label', default: 'Avatar')}" />
	                        <g:sortableColumn property="nombre" title="${message(code: 'userLG.nombre.label', default: 'Nombre')}" />
	                        <g:sortableColumn property="username" title="${message(code: 'userLG.username.label', default: 'Email')}" />
	                        <g:sortableColumn property="telefono_Movil" title="${message(code: 'userLG.telefono_Movil.label', default: 'Teléfono')}" />
	                        <g:if test="${session.getAttribute('username_rolActivo') == 'ROLE_PROPIETARIO' || session.getAttribute('username_rolActivo') == 'ROLE_PROGRAMADOR'}">
	                        <g:sortableColumn property="establecimiento" title="Establecimiento" />
	                        </g:if>
	                        <g:sortableColumn property="pagoNomina" title="${message(code: 'userLG.pagoNomina.label', default: 'Pago x nómina')}" style="width:100px" />
	                        <g:sortableColumn property="montoLimite" title="${message(code: 'userLG.montoLimite.label', default: 'Monto límite')}" style="width:100px" />
	                    </tr>
	                </thead>
					</g:if>
	                <tbody>
	                    <g:each in="${userLGList}" var="user" status="i">
	                        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
								<td style="width:100px;">
									<g:if test="${user.archivo_Foto}">
									<center>
										<a href="javascript:showImage('${user.nombre}','${user.archivo_Foto}');"><img src="${request.contextPath}/assets/mes-users/${user.archivo_Foto}" border="0px" style="width:60px; height:60px"/></a>
									</center>
									</g:if>
									<g:else>
										<center>
											<a href="javascript:showImage('${user.nombre}','default.png');"><img src="${request.contextPath}/assets/mes-users/default.png" border="0px" style="width:60px; height:60px"/></a>
										</center>
									</g:else>
								</td>
	                            <td>${user.nombre}</br><span style="color:#6D6E70;font-size:12px">${user.getRolName()}</span></td>
								<td>${user.username}</td>
	                            <td>${user.telefono_Movil}</td>
	                            <g:if test="${session.getAttribute('username_rolActivo') == 'ROLE_PROPIETARIO' || session.getAttribute('username_rolActivo') == 'ROLE_PROGRAMADOR'}">
	                            <td>${user.establecimiento}</td>
	                            </g:if>
								<td class="celda_centrada">
									<g:if test="${user.pagoNomina}">
										<center>
											<g:link name="show-limite" url="javascript:void(0)" onclick="showCancel('${user.id}','${user.nombre}');" title="¡Habilitar!">
												<input id="sw-${user.id}" class="js-switch" checked="" style="display: none;" type="checkbox">	
											</g:link>				
										</center>
									</g:if>
									<g:else>
										<center>
											<g:link name="show-limite" url="javascript:void(0)" onclick="showLimite('${user.id}');" title="¡Inhabilitar!">
												<input id="sw-${user.id}" class="js-switch" style="display: none;" type="checkbox">	
											</g:link>	
										</center>
									</g:else>	
								</td>
								<td>
									<g:if test="${user.pagoNomina}">
										<g:formatNumber number="${user.montoLimite}" format="\$ ###,###,##0.00" />
									</g:if>
								</td>
	                        </tr>
	                    </g:each>
	                    <g:if test="${userLGCount == 0}">
							<tr>
								<td colspan="7" style="text-align:center"></br></br><img src="${request.contextPath}/assets/empty/usuarios.png" border="0px"/></br><span style="font-size:18px;color:#b1b1b1;">Aún no ahi empleados registrados.</br>Pida a sus empleados registrarse en la plataforma de Samba Smoothie.</span></br></br></td>
							</tr>
	                    </g:if>
	                </tbody>
	            </table>
	            <div class="pagination">
	                <g:paginate total="${userLGCount ?: 0}" />
	            </div>
	        </div>
        </div>
    </body>
</html>
