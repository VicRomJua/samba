<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'platilloEstablecimiento.label', default: 'Mi Menú')}" />
        <g:set var="entitiesName" value="${message(code: 'platilloEstablecimiento.label', default: 'Mi Menú')}" />
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
                     location.href = "${request.contextPath}/platilloEstablecimiento/remove/"+id;
                });
            }
            
            function showImage(nombre, archivo){
				swal({
				  title: nombre,
				  html:
					"<img src=\"${request.contextPath}/assets/mes-icons/"+archivo+"\" border=\"0px\" style=\"width:400px;height:240px;\"/>",
				  showCloseButton: true,
				  confirmButtonColor: '#B4D14B',
				  confirmButtonText:
					"<i class=\"fa fa-thumbs-up\"></i> Aceptar"
				})
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
			  <li class="active"><g:message code="default.list.label" args="['Mi menú']" /></li>
			</ul>
		  </div>
		</div>
		<!-- .breadcrumb-box -->
		<div class="x_panel">		
	        <a href="#list-platilloEstablecimiento" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
	        <div class="nav_topbar" role="navigation">
	            <ul>
	                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
	                <div class="col-xs-6 col-xs-offset-2" style="float:right;padding: 0px;">
						<g:if test="${platilloEstablecimientoCount != 0 || session.getAttribute('platilloEstablecimiento_search_word')}">
						<g:form action="index" method="POST" name="search_form">
							<div class="input-group">
								<div class="input-group-btn search-panel">
									<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
										<span id="search_concept">${session.getAttribute('platilloEstablecimiento_search_rows')} Registros</span> <span class="caret"></span>
									</button>
									<ul class="dropdown-menu" role="menu">
									  <li><a href="#10">10 Registros</a></li>
									  <li><a href="#20">20 Registros</a></li>
									  <li><a href="#50">50 Registros</a></li>
									  <li><a href="#100">100 Registros</a></li>
									</ul>
								</div>
								<input type="hidden" name="search_rows" id="search_rows" value="${session.getAttribute('platilloEstablecimiento_search_rows')}">
								<input type="hidden" name="search_clean" id="search_clean" value="0">
								<input type="text" class="form-control" name="search_word" id="search_word" placeholder="Palabra a buscar..." value="${session.getAttribute('platilloEstablecimiento_search_word')}">
								<span class="input-group-btn">
									<button id="btn-search" class="btn btn-primary" type="button"><li class="fa fa-search fa-lg"></li></button>
								</span>
							</div>
						</g:form>
						</g:if>
					</div>
	            </ul>
	        </div>
	        <div id="list-platilloEstablecimiento" class="content scaffold-list" role="main">
	            <g:if test="${flash.message}">
	                <div class="message" role="status">${flash.message}</div>
	            </g:if>
	            %{--
	            <f:table collection="${platilloEstablecimientoList}" />
				--}%
				
				<table>
					<g:if test="${platilloEstablecimientoCount != 0}">
	                <thead>
	                    <tr>
							<th style="width:100px" class="sortable"><a href="#">${message(code: 'platilloEstablecimiento.archivo_Foto.label', default: 'Photo')}</a></th>
	                        <g:sortableColumn property="platillo" title="${message(code: 'platilloEstablecimiento.platillo.label', default: 'Food')}" />
	                        <g:if test="${session.getAttribute('username_rolActivo') == 'Propietario'}">
	                        <g:sortableColumn property="establecimiento" title="${message(code: 'platilloEstablecimiento.establecimiento.label', default: 'Kitchen')}" />
	                        </g:if>
	                        <g:sortableColumn property="precio" title="${message(code: 'platilloEstablecimiento.precio.label', default: 'Price')}" />
	                        <g:sortableColumn property="costo" title="${message(code: 'platilloEstablecimiento.costo.label', default: 'Cost')}" />
	                        <g:sortableColumn property="activo" title="${message(code: 'platilloEstablecimiento.activo.label', default: 'Active')}" class="celda_centrada" />
	                        <th style="width:100px" class="sortable"><a href="#">Operaciones</a></th>
	                    </tr>
	                </thead>
					</g:if>
	                <tbody>
	                    <g:each in="${platilloEstablecimientoList}" var="item" status="i">
	                        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
	                            <td style="width:100px;text-align:center">
									<g:if test="${item.platillo.archivo_Foto}">
									<center>
										<a href="javascript:showImage('${item.platillo.nombre.replaceAll("'", "").trim()}','${item.platillo.archivo_Foto}');"><img src="${request.contextPath}/assets/mes-icons/${item.platillo.archivo_Foto}" border="0px" style="width:60px; height:60px"/></a>
									</center>
									</g:if>
									<g:else>
										<center>
											<a href="javascript:showImage('Sin Foto','default.png');"><img src="${request.contextPath}/assets/mes-icons/default.png" border="0px" style="width:60px; height:60px"/></a>
										</center>
									</g:else>
								</td>
								<td>
									<g:link method="GET" resource="${item}">
									${item.platillo}</br>
									</g:link>
									<font color="#666666">
									<b>Tipo: </b>${item.platillo?.tipo} <b>Categorías: </b>${item.platillo?.categorias}</br>
									<b>Ingredientes: </b>${item.platillo?.ingredientes}
									</font>
								</td>
								<g:if test="${session.getAttribute('username_rolActivo') == 'Propietario'}">
	                            <td>${item.establecimiento}</td>
	                            </g:if>
	                            <td style="width:100px;"><g:formatNumber number="${item.precio}" currencyCode="MXN" type="currency" locale="MX" currencySymbol="\$" /></td>
	                            <td style="width:100px;"><g:formatNumber number="${item.costo}" currencyCode="MXN" type="currency" locale="MX" currencySymbol="\$" /></td>
	                            <td class="celda_centrada">
									<g:if test="${item.activo}">
										<center>
											<g:link action="active" resource="${item}" title="¡Desactivar!" >
												<input class="js-switch" checked="" style="display: none;" type="checkbox">	
											</g:link>
										</center>
									</g:if>
									<g:else>
										<center>
											<g:link class="js-switch" action="active" resource="${item}" title="¡Activar!">
												<input class="js-switch" style="display: none;" type="checkbox">	
											</g:link>
										</center>
									</g:else>	
								</td>
	                            <td style="width:100px;">
									<center>
										<a class="operations_icon red" onclick="jconfirm('${message(code: 'default.delete.ask.message', default: 'Are you sure?')}','${message(code: 'default.delete.info_personal.message', default: 'You are deleting the record.', args:[item.platillo])}','${item.id}');" title="¡Eliminar!"><li class="fa fa-trash"></li></a>
									</center>
								</td>
	                        </tr>
	                    </g:each>
	                    <g:if test="${platilloEstablecimientoCount == 0}">
							<tr>
								<td colspan="5" style="text-align:center"></br></br><img src="${request.contextPath}/assets/empty/mimenu.png" border="0px"/></br><span style="font-size:18px;color:#b1b1b1;"><g:message code="default.table.empty.label" args="[entitiesName]"/></span></br></br></td>
							</tr>
	                    </g:if>
	                </tbody>
	            </table>
				
				
	            <div class="pagination">
	                <g:paginate total="${platilloEstablecimientoCount ?: 0}" />
	            </div>
	        </div>
	    </div>
    </body>
</html>
