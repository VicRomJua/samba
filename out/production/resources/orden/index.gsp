<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'orden.label', default: 'Orden')}" />
        <g:set var="entitiesName" value="${message(code: 'orden.label', default: 'Ordenes')}" />
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
                    confirmButtonColor: '#DA291C',
                    confirmButtonText: 'Si, eliminar!',
                    cancelButtonText: 'No',
                    confirmButtonClass: 'btn btn-success',
                    cancelButtonClass: 'btn btn-danger',
                    buttonsStyling: true
                }).then(function() {
                     location.href = "${request.contextPath}/orden/remove/"+id;
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
			  <li class="active"><g:message code="default.list.label" args="[entitiesName]" /></li>
			</ul>
		  </div>
		</div>
		<!-- .breadcrumb-box -->
		<div class="x_panel">
			<a href="#list-orden" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
			<div class="nav_topbar" role="navigation">
				<ul>
					<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
					<div class="col-xs-6 col-xs-offset-2" style="float:right;padding: 0px;">
						<g:if test="${ordenCount != 0 || session.getAttribute('ingrediente_search_word')}">
						<g:form action="index" method="POST" name="search_form">
							<div class="input-group">
								<div class="input-group-btn search-panel">
									<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
										<span id="search_concept">${session.getAttribute('orden_search_rows')} Registros</span> <span class="caret"></span>
									</button>
									<ul class="dropdown-menu" role="menu">
									  <li><a href="#10">10 Registros</a></li>
									  <li><a href="#20">20 Registros</a></li>
									  <li><a href="#50">50 Registros</a></li>
									  <li><a href="#100">100 Registros</a></li>
									</ul>
								</div>
								<input type="hidden" name="search_rows" id="search_rows" value="${session.getAttribute('orden_search_rows')}">
								<input type="hidden" name="search_clean" id="search_clean" value="0">
								<input type="text" class="form-control" name="search_word" id="search_word" placeholder="Palabra a buscar..." value="${session.getAttribute('orden_search_word')}">
								<span class="input-group-btn">
									<button id="btn-search" class="btn btn-primary" type="button"><li class="fa fa-search fa-lg"></li></button>
								</span>
							</div>
						</g:form>
						</g:if>
					</div>
				</ul>
			</div>
			<div id="list-orden" class="content scaffold-list" role="main">
				<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
				</g:if>
				%{--
				<f:table collection="${ordenList}" />
				--}%
				
				<table>
					<g:if test="${ordenCount != 0}">
					<thead>
						<tr>
							<g:sortableColumn property="noOrden" title="${message(code: 'ingrediente.noOrden.label', default: '# Orden')}" />
							<g:sortableColumn property="montoPagado" title="${message(code: 'ingrediente.montoPagado.label', default: 'Monto pagado')}" />
							<g:sortableColumn property="modoPago" title="${message(code: 'ingrediente.modoPago.label', default: 'Modo de pago')}" />
							<g:sortableColumn property="fechaPago" title="${message(code: 'ingrediente.fechaPago.label', default: 'Fecha de pago')}" />
							<th style="width:100px" class="sortable"><a href="#">Operaciones</a></th>
						</tr>
					</thead>
					</g:if>
					<tbody>
						<g:each in="${ordenList}" var="item" status="i">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
								<td><g:link method="GET" resource="${item}">${item.noOrden}</g:link></td>
								<td>${item.montoPagado}</td>
								<td>${item.modoPago}</td>
								<td>${item.fechaPago.format("dd/MM/YYYY hh:mm:ss")}</td>
								<td style="width:100px;">
									<center>
										<a class="operations_icon red" onclick="jconfirm('${message(code: 'default.delete.ask.message', default: 'Are you sure?')}','${message(code: 'default.delete.info_personal.message', default: 'You are deleting the record.', args:[item.noOrden])}','${item.id}');" title="¡Eliminar!"><li class="fa fa-trash"></li></a>
									</center>
								</td>
							</tr>
						</g:each>
						<g:if test="${ordenCount == 0}">
							<tr>
								<td colspan="5" style="text-align:center"></br></br><img src="${request.contextPath}/assets/empty/ordenes.png" border="0px"/></br><span style="font-size:18px;color:#b1b1b1;"><g:message code="default.table.empty.label" args="[entitiesName]"/></span></br></br></td>
							</tr>
						</g:if>
					</tbody>
				</table>
				<div class="pagination">
					<g:paginate total="${ordenCount ?: 0}" />
				</div>
			</div>
		</div>
    </body>
</html>
