<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'embarque.label', default: 'Embarque')}" />
        <g:set var="entitiesName" value="${message(code: 'embarque.label', default: 'Embarques')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
        <script language="javascript">
		    function changeTab(opcion){
				url = "${request.contextPath}/embarque/changeTab?option="+opcion;
				$("#cmd_space")[0].src = url;
			}
			
            function jconfirm(pregunta,mensaje){
                swal({
                    title: pregunta,
                    text: mensaje,
                    type: "question",
                    showCancelButton: true,
                    confirmButtonColor: "#DA291C",
                    confirmButtonText: "Si, eliminar!",
                    cancelButtonText: "No",
                    confirmButtonClass: "btn btn-success",
                    cancelButtonClass: "btn btn-danger",
                    buttonsStyling: true
                }).then(function() {
                     $( "#delete_form" ).submit();
                });
            }
            
            function go(id_tag){
				$('[name="footer-'+id_tag+'"]')[0].click();
			}
			/* IGB inicio */
			function showDetalle(data){
				swal({
				  title: "Detalle de la Orden",
				  html:
					  '<div style="text-align:center">' 
					+ '  <span>'+ data.fecha + '</span> <br/><span>Estar lista antes de: '+ data.hora +'</span>'
					+ '</div><br/>'
					+ '<div style="text-align:left">'
					+ '  <span>'+ data.empresa +'</span><br/>'
					+ '  <span>Numero de orden: '+ data.noOrden + '</span><br/>'
					+ '  <span>Cantidad: '+ data.cantidad + '</span><br/>'
					+ '  <span>Estatus: '+ data.estatus + '</span><br/>'
					+ '  <span>¿Es Personalizado?: '+ data.esPersonalizado +'</span><br/>'
					+ '  <span>Detalles: '+ data.personalizado +'</span>'
					+ '</div>',
				  showCloseButton: true,
				  confirmButtonText:
					'<i class="fa fa-thumbs-up"></i> Aceptar'
				})
			}
			/* IGB fin */
			/* IGB inicio */
			function showEtiqueta(data){
				swal({
				  html:
				  		'<div id="Imprimir">' +
						'<table  id="tablaEtiqueta" class="etiqueta" style="width:100%">' +
						'  <tr>' +
						'    <td rowspan="5"><img src="${request.contextPath}/assets/samba_byn.png" style="width:128px;height:128px;"></td>' +
						'    <td class="etiquetaOrden">Orden #'+ data.noOrden + '</td>' +
						'  </tr>' +
						'  <tr>' +	
						'    <td class="etiquetaEmbarque">EMBARQUE - '+ data.embarque + '</td>' +
						'  </tr>' +
						'  <tr>' +
						'    <td class="etiquetaFecha">Empresa: <strong>'+ data.empresa + '</strong></td>' +
						'  </tr>' +
						'  <tr>' +
						'    <td class="etiquetaFecha">Fecha de entrega: <strong>'+ data.fechaEntrega + '</strong></td>' +
						'  </tr>' +
						'  <tr>' +
						'    <td class="etiquetaHora">Hora: <strong>'+ data.horaEntrega + '</strong></td>' +
						'  </tr>  ' +
						'  <tr>' +
						'    <td class="etiquetaPara">Cliente:</td>' +
						'    <td class="etiquetaParaNombre">'+ data.cliente + '</td>' +
						'  </tr> ' +
						'  <tr>' +
						'    <td class="etiquetaPersonalizado">Pedido:</td>' +
						'    <td class="etiquetaPersonalizadoDetalle">'+ data.contenido + '</td>' +
						'  </tr>   ' +
						'</table> '	+ 
						'</div>'
					,
				  showCancelButton: true,
				  confirmButtonColor: "#76BD1D",
				  cancelButtonText: "Cerrar",
				  confirmButtonText: "Imprimir",
				  confirmButtonClass: "btn btn-success",
				  cancelButtonClass: "btn btn-danger",
				  buttonsStyling: true
				}).then(function() {
					$("#tablaEtiqueta").css("width", "60%");
                    $( "#Imprimir" ).print();
                });
			}		
			/* IGB fin */	
			
			function showAlertaEmbarque(mensaje){
				swal(
				  'Aviso...',
				  mensaje,
				  'info'
				)
			}
			function showEntrega(url){
				swal({
				  title: "Documento de entrega",
				  html:'<img src="'+url+'" style="width:400px;height:300px;">',
				  showCloseButton: true,
				  confirmButtonText:
					'<i class="fa fa-thumbs-up"></i> Aceptar'
				})
			}			
        </script>
        <style>
			.datacell {
				-moz-border-radius: 5px;
				-webkit-border-radius: 5px;
				border-radius: 5px;
				border:1px solid #E6E9ED;
				color:#bababa;
			}
			.even {
				border: 0px;
			}
			.order-details{
				width:100%;
				margin:0px 0px;
			}
			.order-details > thead > tr > th:first-child {
				-moz-border-radius: 5px 0px 0px 0px;
				-webkit-border-radius: 5px 0px 0px 0px;
				border-radius: 5px 0px 0px 0px;
			}
			.order-details > thead > tr > th:last-child {
				-moz-border-radius: 0px 5px 0px 0px;
				-webkit-border-radius: 0px 5px 0px 0px;
				border-radius: 0px 5px 0px px;
			}
			.order-details > tbody > tr > td {
				border-bottom: 1px solid #E6E9ED;
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
			  <li><g:link action="index"><g:message code="default.list.label" args="[entitiesName]" /></g:link></li>
			  <li class="active">${embarque.codigo}</li>
			</ul>
		  </div>
		</div>
		<!-- .breadcrumb-box -->
        <a href="#show-embarque" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav_topbar" role="navigation">
            <ul>
				<!--
                <li><a href="javascript:go('edit')"		class="edit"><g:message code="default.button.edit.label" default="Edit" /></a></li>
                <li><a href="javascript:go('delete')"	class="delete"><g:message code="default.button.delete.label" default="Delete" /></a></li>
                -->
                <li><a href="javascript:go('print')"	class="print"><g:message code="default.button.print_shipment.label" default="Print shipment" /></a></li>
                <li><a href="javascript:go('close')"	class="close"><g:message code="default.button.close.label" default="Close" /></a></li>
            </ul>
        </div>
        <div id="show-embarque" class="content scaffold-show" role="main">
            <g:if test="${flash.message}">
				<g:if test="${flash.message.indexOf('El Embarque') >= 0}">
				<script>jQuery(function(){showAlertaEmbarque('${flash.message}');});</script>
				</g:if>
				<g:else>
				<div class="message" role="status">${flash.message}</div>
				</g:else>
            </g:if>
            
			%{--
            <f:display bean="embarque" />
            --}%
			<div class="row">
                <div class="col-md-8 col-sm-12 col-xs-12">
                    <div class="x_panel">
                        <div class="x_content">
							<table class="order-details">
								<thead>
									<tr>
										<g:sortableColumn property="orden.noOrden" title="${message(code: 'ordenDetalle.orden.label', default: 'No. Orden')}" style="width:100px;" />
										<g:sortableColumn property="platillo.platillo.nombre" title="${message(code: 'ordenDetalle.platillos.label', default: 'Resumen de orden')}" style="width:100px;" />
										<g:sortableColumn property="esPersonalizado" title="${message(code: 'ordenDetalle.esPersonalizado.label', default: 'Personalizable')}"  style="width:30%" />
										<sec:ifAnyGranted roles='ROLE_COCINERO'>
										<th style="width:100px" class="sortable"><a href="#">Operaciones</a></th>
										</sec:ifAnyGranted>
										<sec:ifAnyGranted roles='ROLE_ADMIN'>
										<g:sortableColumn property="estatus" title="Estatus" style="width:100px;text-align:center;"/>
										</sec:ifAnyGranted>
									</tr>
								</thead>
								<tbody>
									<g:each in="${orden}" var="item" status="i">
										<tr class="${(i % 2) == 0 ? 'even' : 'even'}">
											<td style="width:100px;">#${item.orden.noOrden}</td>
											<!-- IGB inicio -->
											<td style="width:99%;">
												<a href="javascript:showDetalle({ 'noOrden':'${item.orden.noOrden}', 'cantidad':'${item.cantidad} ${item.platillo.platillo.nombre}', 
												'fecha':'${item.fechaEntrega.format('EEEEEE, dd MMMMMM yyyy')}', 'hora':'${item.horaEntrega}', 'estatus':'${item.estatus}', 'empresa':'${item.embarque.empresa}',
												'cliente':'${item.id_Autor}',
												'esPersonalizado':'${item.esPersonalizado == true ? 'Si' : 'No'}',
												'personalizado':'${item.personalizado}'});">(${item.cantidad}) ${item.platillo.platillo.nombre}</a>
											</td>
											<!-- IGB fin -->
											<td style="width:100px;text-align:center"><g:if test="${item.esPersonalizado}"><img src="${request.contextPath}/assets/personalizado.png" border="0px"/></g:if><g:else><!--<img src="${request.contextPath}/assets/no.png" border="0px" width="25px" height="25px"/>--></g:else></td>
											<td>
												<sec:ifAnyGranted roles='ROLE_COCINERO'>
												<g:if test="${item.estatus == 'Nuevo'}">
													<center>
													<fieldset class="buttons">
														<g:link action="next" resource="${item}" class="cocinar"><g:message code="default.button.cocinar.label" default="Cook" /></g:link>
													</fieldset>
													</center>
												</g:if>
												<g:elseif test="${item.estatus == 'Cocinando'}">
													<center>
													<fieldset class="buttons">
														<g:link action="next" resource="${item}" class="listo"><g:message code="default.button.preparar.label" default="Ready" /></g:link>
													</fieldset>
													</center>
												</g:elseif>
												<g:elseif test="${item.estatus == 'Preparado'}">
													<center>
													<fieldset class="buttons">
													<a href="javascript:showEtiqueta({ 'noOrden':'${item.orden.noOrden}', 'contenido':'${item.getPaquete()}', 
													'fechaEntrega':'${item.fechaEntrega.format('dd/MMM/yyyy')}', 'horaEntrega':'${item.horaEntrega}', 
													'embarque':'${item.embarque.codigo}',
													'empresa':'${item.embarque.empresa}',
													'cliente':'${item.id_Autor}'});">
													<g:message code="default.button.print_label.label" default="Imprimir Etiqueta	" />	
													</a>
													</fieldset>
													</center>
												</g:elseif>
												</sec:ifAnyGranted>
												<sec:ifAnyGranted roles='ROLE_ADMIN'>
													<center>${item.estatus}</center>
												</sec:ifAnyGranted>
											</td>
										</tr>		
									</g:each>
										<g:if test="${ordenCount == 0}">
											<tr>
												<td colspan="4"><g:message code="default.table.empty.label"/></td>
											</tr>
										</g:if>
								</tbody>							
							</table>
						</div>
					</div>
				</div>
				<div class="col-md-4 col-sm-12 col-xs-12">
					<div class="x_panel">
						<div class="x_content">
							<table style="margin-top:0px;">
								<tbody>
									<tr>
									<g:each in="${embarque}" var="item" status="i">
										<td class="even">
											<div style="text-align: center">
												<span style="font-weight:bold;font-size:16px;color:#6f6f71;">${item.fechaHoraEntrega.format('EEEEEE, dd MMMMMM yyyy')}</span></br>
												Estar lista antes de ${item.fechaHoraEntrega.format('HH:mm a').replace("01:","00:").replace("02:","01:").replace("03:","02:").replace("04:","03:").replace("05:","04:").replace("06:","05:").replace("07:","06:").replace("08:","07:").replace("09:","08:").replace("10:","09:").replace("11:","10:").replace("12:","11:").replace("13:","12:").replace("14:","13:").replace("15:","14:").replace("16:","15:").replace("17:","16:").replace("18:","17:").replace("19:","18:").replace("20:","19:").replace("21:","20:").replace("22:","21:").replace("23:","22:").replace("24:","23:")}
											</div>
											<br/>
											<center>
												<span style="font-size:16px;font-weight:bold;color:#B4D048;">${item.estatus?.toUpperCase()}</span>
												<g:if test="${item.archivo_Evidencia}">
												<center><b><a href="javascript:showEntrega('${request.contextPath}/assets/mes-evidencia/${item.archivo_Evidencia}');">Ver documento de entrega</a></b></center>
												</g:if>
												<sec:ifAnyGranted roles='ROLE_PROPIETARIO,ROLE_PROGRAMADOR'>
												</br><span style="font-size:14px;font-weight:bold;color:#6f6f71;">${item.establecimiento?.nombre}</span>
												</sec:ifAnyGranted>
											</center>
											<br/>
											<div>
												<span style="font-weight:bold;color:#6f6f71;font-size:16px;">${item.empresa}</span><br/>
												<br/>
												<span style="font-size:16px;">${message(code: 'embarque.productos.label', default: 'Products')}:</span></br>
												<ul>
												<g:each in="${concentradoPlatillo}" var="itemPlatillo" status="r">
												<li><g:if test="${itemPlatillo.personalizado}"><img src="${request.contextPath}/assets/personalizado.png" border="0px"/></g:if><g:else>&nbsp;&nbsp;&nbsp;&nbsp;</g:else>${itemPlatillo.cantidad} ${itemPlatillo.producto}</li>
												</g:each>
												</ul>
												</br>
												<sec:ifAnyGranted roles='ROLE_COCINERO'>
												<g:if test="${item.estatus == 'Nuevo' || item.estatus == 'Cocinando'}">
													</br></br>
													<center>
													<fieldset class="buttons" style="position: absolute; bottom: 0px;">
														<g:link name="finish" action="finish" resource="${item}" style="width:70%;"><g:message code="default.button.finalizar.label" default="finish" /></g:link>
													</fieldset>
													</center>
												</g:if>
												</sec:ifAnyGranted>
											</div>
										</td>
									</g:each>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			
            <g:form resource="${this.embarque}" method="DELETE" name="delete_form">
				<fieldset class="buttons">
					<!--
					<g:link name="footer-edit" class="edit" action="edit" resource="${this.embarque}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:link name="footer-delete" class="delete" url="javascript:void(0)" onclick="jconfirm('${message(code: 'default.delete.ask.message', default: 'Are you sure?')}','${message(code: 'default.delete.info.message', default: 'You are deleting the record.')}');"><g:message code="default.button.delete.label" default="Delete" /></g:link>
					-->
					<a name="footer-print" class="print" href="${grailsApplication.config.jasper_serverAddress}flow.html?_flowId=viewReportFlow&j_username=${grailsApplication.config.jasper_user}&j_password=${grailsApplication.config.jasper_password}&standAlone=true&decorate=no&_flowId=viewReportFlow&ParentFolderUri=%2Freports%2Fsamba&reportUnit=%2Freports%2Fsamba%2Fembarque&embarqueID=${embarque.id}" target="_blank"><g:message code="default.button.print_shipment.label" default="Print shipment" /></a>
                    <g:link name="footer-close" class="close" action="index"><g:message code="default.button.close.label" default="Close" /></g:link>
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
