<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'embarque.label', default: 'Embarque')}" />
        <g:set var="entitiesName" value="${message(code: 'embarque.label', default: 'Embarques')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <script language="javascript">
			var options = {};
			options.singleDatePicker = true;
			options.showDropdowns = true;
			options.format =  'DD/MM/YYYY',
			options.locale = {
			  daysOfWeek: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi','Sa'],
			  monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
			  firstDay: 1,
			  opens: "center"
            };
			$(document).ready(function(e){
				$("#filter_estatus").select2().css(['style','visible']);
				$('#today').daterangepicker(options, function(start, end, label) { });
				$('#today').on("keydown", function (e) {
					if (e.keyCode == 13) {
						$( "#search_form" ).submit();
					}
				});
				$("#btn-search").click(function(e) {
					$( "#search_form" ).submit();
				});
				//$("#today").focus();
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
                     location.href = "${request.contextPath}/embarque/remove/"+id;
                });
            }
        </script>
		<style>
			.month{
				text-align: center;
			}
			.table-condensed{
				border: 10px solid white !important;
				margin-top: 0px !important;
			}
			.table-condensed > thead > tr > th {
				color:#989898 !important;
				background-color: #FFFFFF !important;
				border-bottom: 0px;
				vertical-align: middle !important;
			}
			.table-condensed > thead > tr > .prev, .table-condensed > thead > tr > .next{
				color: #76BD1D !important;
			}
			.table-condensed > tbody > tr > td {
				text-align:center !important;
			}
			.table-condensed > tbody > tr > .off {
				color: #efefef;
			}
			.table-condensed > tbody > tr > .active {
				background: #76BD1D !important;
				color: #FFFFFF !important;
				-moz-border-radius: 15px !important;
				-webkit-border-radius: 15px !important;
				border-radius: 15px !important;
				width: 30px;
			}
			.table-condensed > tbody > tr > td {
				border-bottom: 0px !important;
			}
			.table-datacell {
				-moz-border-radius: 5px;
				-webkit-border-radius: 5px;
				border-radius: 5px;
				border:1px solid #E6E9ED;
				width:25%;
				position:relative;
				padding-left:5px !important;
				padding-right:5px !important;
			}
			.table-datacell > center > div{
				font-weight:bold;
				font-size:16px;
				width:100%;
				background-color:#6f6f71;
				padding-bottom:5px;
				padding-top:5px;
				-moz-border-radius: 5px 5px 0px 0px;
				-webkit-border-radius: 5px 5px 0px 0px;
				border-radius: 5px 5px 0px px;
			}
		</style>
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
			<a href="#list-embarque" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
			<div class="nav_topbar" role="navigation">
				<ul>
					<sec:ifAnyGranted roles='ROLE_PROGRAMADOR'>
					<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
					</sec:ifAnyGranted>
					<li style="color:#f5c062;"><img src="${request.contextPath}/assets/personalizado.png" border="0px"/>&nbsp;Producto personalizado</li>
					
					<div class="col-xs-5" style="float:right;padding: 0px;width:420px;">
						<g:form action="index" method="POST" name="search_form">
							<div class="input-group filter-panel" style="text-align:right;width:200px;display:inline-block;top:-26px;margin:0px;">
								<g:select name="filter_estatus" id="filter_estatus" from="${estatus}" noSelection="['ALL':'Todos los embarques']" value="${session.getAttribute('search_filter_estatus')}" style="visibility:hidden"/>
							</div>
							<div style="width:200px;display:inline-block">
								<div class="input-group" style="display:table;">
									<input type="text" class="form-control" name="today" id="today" aria-describedby="inputSuccess2Status3" value="${session.getAttribute('embarque_today')}" style="text-indent:40px;height: 38px; border-top-left-radius: 4px; -moz-border-top-left-radius: 4px; -webkit-border-top-left-radius:4px;border-bottom-left-radius: 4px; -moz-border-bottom-left-radius: 4px; -webkit-border-bottom-left-radius:4px;">
									<span class="fa fa-calendar-o form-control-feedback left" aria-hidden="true"></span>
									<span id="inputSuccess2Status4" class="sr-only">(success)</span>
									<span class="input-group-btn">
										<button id="btn-search" class="btn btn-primary" type="button" style="padding: 8px;"><li class="fa fa-search fa-lg" style="vertical-align: 0%;"></li></button>
									</span>
								</div>
							</div>
						</g:form>
					</div>
				</ul>
			</div>
			<div id="list-embarque" class="content scaffold-list" role="main">
				<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
				</g:if>
				%{--
				<f:table collection="${embarqueList}" />
				--}%
				
				<table style="border-collapse:separate;border-spacing:10px;color:#bababa;">
					<tbody>
						<tr>
						<g:each in="${embarqueList}" var="item" status="i">
							<td class="even table-datacell">
								<center>
									<div>
										<g:link method="GET" resource="${item}" style="color:#ffffff;">${item.codigo}</g:link>
									</div>
								Estar lista antes de ${item.fechaHoraEntrega.format('HH:mm a').replace("01:","00:").replace("02:","01:").replace("03:","02:").replace("04:","03:").replace("05:","04:").replace("06:","05:").replace("07:","06:").replace("08:","07:").replace("09:","08:").replace("10:","09:").replace("11:","10:").replace("12:","11:").replace("13:","12:").replace("14:","13:").replace("15:","14:").replace("16:","15:").replace("17:","16:").replace("18:","17:").replace("19:","18:").replace("20:","19:").replace("21:","20:").replace("22:","21:").replace("23:","22:").replace("24:","23:")}</br><span style="font-size:16px;font-weight:bold;color:#B4D048;">${item.estatus?.toUpperCase()}</span>
								<sec:ifAnyGranted roles='ROLE_PROPIETARIO,ROLE_PROGRAMADOR'>
								</br><span style="font-size:12px;font-weight:bold;">${item.establecimiento?.nombre}</span>
								</sec:ifAnyGranted>
								</br></br></center>
								<span style="font-weight:bold;color:#6f6f71;font-size:16px;">${item.empresa}</span></br>
								<sec:ifAnyGranted roles='ROLE_ADMIN'>
								<g:if test="${item.empresa.domicilio}">
								${item.empresa.domicilio} ${item.empresa.ciudad} ${item.empresa.cp}
								</g:if>
								<g:else>
								¡Sin dirección asignada!
								</g:else>
								</sec:ifAnyGranted>
								<div style="border-right:3px solid #b1b1b1;width:100%;">Hora de entrega: ${item.fechaHoraEntrega.format('HH:mm a')}</div>
								<span style="font-size:16px;">${message(code: 'embarque.productos.label', default: 'Products')}:</span></br>
								<g:if test="${item.ordenes}">
								<g:each in="${item.ordenes}" var="orden" status="j">
								<div style="display:inline-block;width:20px;">
									<g:if test="${orden.esPersonalizado == true}">
									<img src="${request.contextPath}/assets/personalizado.png" border="0px"/>
									</g:if>
								</div>
								<div style="display:inline-block;">•&nbsp;(${orden.orden.noOrden})&nbsp;&nbsp;${orden.cantidad}&nbsp;${orden.platillo.platillo.nombre}</div>
								</br>
								</g:each>
								</g:if>
								</br></br></br>
								<center>
								<fieldset class="buttons" style="position: absolute; bottom: 0px;">
									<sec:ifAnyGranted roles='ROLE_ADMIN'>
									<g:link name="footer-close" action="show" resource="${item}" style="width:70%;"><g:message code="default.button.consultar.label" default="Check" /></g:link>
									</sec:ifAnyGranted>
									<sec:ifAnyGranted roles='ROLE_COCINERO'>
									
									<g:if test="${item.estatus == 'Nuevo' || item.estatus == 'Cocinando'}">
										<g:link name="footer-close" action="show" resource="${item}" style="width:70%;"><g:message code="default.button.cocinar.label" default="Coock" /></g:link>
									</g:if>
									<g:else>
										 <g:link name="footer-close" action="show" resource="${item}" style="width:70%;"><g:message code="default.button.consultar.label" default="Check" /></g:link>
									</g:else>
									
									</sec:ifAnyGranted>
								</fieldset>
								</center>
							</td>
						</g:each>
						</tr>
						<g:if test="${embarqueCount == 0}">
						<tr>
							<td style="text-align:center"></br></br><img src="${request.contextPath}/assets/empty/embarques.png" border="0px"/></br><span style="font-size:18px;color:#b1b1b1;">
							<sec:ifAnyGranted roles='ROLE_PROGRAMADOR'>
							<g:message code="default.table.empty.label" args="[entitiesName]"/>
							</sec:ifAnyGranted>
							<sec:ifAnyGranted roles='ROLE_PROPIETARIO,ROLE_ADMIN,ROLE_COCINERO'>
							<g:message code="default.table.empty_wait.label" args="[entitiesName]"/>
							</sec:ifAnyGranted>
							</span></br></br></td>
						</tr>
						</g:if>
					</tbody>
				</table>
				
				<div class="pagination">
					<g:paginate total="${embarqueCount ?: 0}" />
				</div>
			</div>
		</div>
    </body>
</html>
