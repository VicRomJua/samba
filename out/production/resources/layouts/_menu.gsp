    <script language="javascript">
		$(document).ready(function(){
			$('input[type="text"],input[type="password"],input[type="email"],textarea').alphanum({
				allowPlus           : true, // Allow the + sign
				allowMinus          : true,  // Allow the - sign
				allowThouSep        : true,  // Allow the thousands separator, default is the comma eg 12,000
				allowDecSep         : true,
				allow				: '@,:.#-_',
				disallow 			: '∞&;ª()¿?!¡*$%/+=¨☺☻♥♦♣♠•◘○◙♂♀♪♫☼►◄↕‼¶§▬↨↑↓→←∟↔▲▼"<>^`{|}~⌂ÇçæÆ¢£¥₧ƒ⌐¬½¼«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αßΓπΣσµτΦΘΩδ∞φε∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■ '
			});
		});
				
        function delete_all(url){
            swal({
                title: '${message(code: 'default.deleteall.info.message', default: 'Eliminará TODOS los datos')}',
                text: '${message(code: 'default.deleteall.ask.message', default: 'Esta usted seguro?')}',
                type: 'question',
                showCancelButton: true,
                confirmButtonColor: '#DA291C',
                confirmButtonText: 'Si, eliminar!',
                cancelButtonText: 'No',
                confirmButtonClass: 'btn btn-success',
                cancelButtonClass: 'btn btn-danger',
                buttonsStyling: true
            }).then(function() {
                swal({
                    title: '${message(code: 'default.deleteconfirm.info.message', default: 'NO podrá revertir la acción')}',
                    text: '${message(code: 'default.deleteconfirm.ask.message', default: 'Esta usted seguro?')}',
                    type: 'question',
                    showCancelButton: true,
                    confirmButtonColor: '#DA291C',
                    confirmButtonText: 'Si, seguro!',
                    cancelButtonText: 'No',
                    confirmButtonClass: 'btn btn-success',
                    cancelButtonClass: 'btn btn-danger',
                    buttonsStyling: true
                }).then(function() {
                    location.href=url;
                });
            });
        }
    </script>
    <content tag="nav">
    <sec:ifLoggedIn>
		<sec:ifAnyGranted roles='ROLE_ADMIN'>
			<li>
				<a href="${request.contextPath}/"><i class="fa fa-home"></i> Inicio</span></a>
			</li>
            <g:if test="${session.getAttribute('username_establecimiento') != ''}">
            <li>
				<a href="${request.contextPath}/embarque/index"><i class="fa fa-truck"></i> Embarques</span></a>
			</li>
            <li>
				<a href="${request.contextPath}/userLG/index"><i class="fa fa-users"></i> Empleados</span></a>
			</li>
			<li>
				<a href="${request.contextPath}/empresa/index"><i class="fa fa-industry"></i> Empresas</span></a>
			</li>
			<li>
				<a href="${request.contextPath}/platilloEstablecimiento/index"><i class="fa fa-coffee"></i> Menú</span></a>
			</li>
			<li>
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-file-text-o"></i> Informes <span class="fa fa-chevron-down"></span></a>
                <ul class="nav child_menu">
					<!-- IGB  Inicio -->
                    <li><a href="${request.contextPath}/informes/platillos_proxima_semana">Platillos por período</a></li>
                    <li><a href="${request.contextPath}/informes/consumos_por_empresa_cocina">Consumos por empresa</a></li>
                    <li><a href="${request.contextPath}/informes/consumos_por_nomina_empresa_cocina">Consumos por nómina</a></li>
                    <!-- IGB  Fin -->  
					<li><a href="${request.contextPath}/informes/listadoPlatillosVendidos_establecimiento">Platillos más vendidos</a></li>
                    <li><a href="${request.contextPath}/informes/graficoVentas_establecimiento">Ventas por día</a></li>
                  	<li><a href="${request.contextPath}/informes/listadoEmbarques_establecimiento">Listado de embarques</a></li>
					<li><a href="${request.contextPath}/informes/listadoMenus_establecimiento">Listado de menú</a></li>
					<li><a href="${request.contextPath}/informes/listadoUsuarios_establecimiento">Listado de usuarios</a></li>
                  	<li><a href="${request.contextPath}/informes/listadoEmpresas_establecimiento">Listado de empresas</a></li>
                </ul>
            </li>
            </g:if>
            <g:else>
            <div style="text-align:justify;padding:10px;width:100%;">
                <center>
                <img src="${request.contextPath}/assets/warning.png" class="img-responsive">
                </center>
                </br></br>
                <span style="width:100%;color:white;" class="profile_info profile clearfix">
                Debe estar vinculado a una cocina para tener acceso a sus funciones.
                </span>
            </div>
            </g:else>
        </sec:ifAnyGranted>
		<sec:ifAnyGranted roles='ROLE_PROPIETARIO'>
			<li>
				<a href="${request.contextPath}/"><i class="fa fa-home"></i> Inicio</span></a>
			</li>
			<li>
				<a href="${request.contextPath}/embarque/index"><i class="fa fa-truck"></i> Embarques</span></a>
			</li>
			<li>
				<a href="${request.contextPath}/userLG/index"><i class="fa fa-users"></i> Empleados</span></a>
			</li>
			<li>
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-cubes"></i> Catálogos <span class="fa fa-chevron-down"></span></a>
				<ul class="nav child_menu">
					<li><a href="${request.contextPath}/tipo/index">Tipo de platillos</a></li>
					<li><a href="${request.contextPath}/categoria/index">Categorías de platillos</a></li>
					<li><a href="${request.contextPath}/ingrediente/index">Ingredientes</a></li>
					<li><a href="${request.contextPath}/extra/index">Extras</a></li>
				</ul>
			</li>
			<li>
				<a href="${request.contextPath}/platillo/index"><i class="fa fa-cutlery"></i> Platillos</span></a>
			</li>
			<li>
				<a href="${request.contextPath}/empresa/index"><i class="fa fa-industry"></i> Empresas</span></a>
			</li>
			<li>
				<a href="${request.contextPath}/establecimiento/index"><i class="fa fa-building-o"></i> Cocinas</span></a>
			</li>
			<li>
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-file-text-o"></i> Informes <span class="fa fa-chevron-down"></span></a>
                <ul class="nav child_menu">
					<!-- IGB  Inicio -->
                    <li><a href="${request.contextPath}/informes/consumos_por_empresa">Consumos por empresa</a></li>
                    <li><a href="${request.contextPath}/informes/consumos_por_nomina_empresa">Consumos por nómina</a></li>
                    <!-- IGB  Fin -->    
					<li><a href="${request.contextPath}/informes/listadoPlatillosVendidos">Platillos más vendidos</a></li>
					<li><a href="${request.contextPath}/informes/informeVentas">Ventas por establecimiento</a></li>
					<li><a href="${request.contextPath}/informes/listadoEmbarques">Historial de Embarques</a></li>
					<li><a href="${request.contextPath}/informes/listadoPlatillos">Listado de platillos</a></li>
					<li><a href="${request.contextPath}/informes/listadoExtras">Listado de extras</a></li>
					<li><a href="${request.contextPath}/informes/listadoIngredientes">Listado de ingredientes</a></li>
					<li><a href="${request.contextPath}/informes/listadoUsuarios">Listado de usuarios</a></li>
                  	<li><a href="${request.contextPath}/informes/listadoEmpresas">Listado de empresas</a></li>
					<li><a href="${request.contextPath}/informes/listadoEstablecimientos">Listado de cocinas</a></li>
                </ul>
            </li>
		</sec:ifAnyGranted>
		<sec:ifAnyGranted roles='ROLE_COCINERO'>
			<li>
				<a href="${request.contextPath}/"><i class="fa fa-home"></i> Inicio</span></a>
			</li>
			<g:if test="${session.getAttribute('username_establecimiento') != ''}">
			<li>
				<a href="${request.contextPath}/embarque/index"><i class="fa fa-truck"></i> Embarques</span></a>
			</li>
			<li>
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-file-text-o"></i> Informes <span class="fa fa-chevron-down"></span></a>
                <ul class="nav child_menu">
                  	<li><a href="${request.contextPath}/informes/listadoEmbarques_establecimiento">Mis embarques</a></li>
                  	<!-- IGB  Inicio -->
                    <li><a href="${request.contextPath}/informes/platillos_proxima_semana">Platillos por período</a></li>
                    <!-- IGB  Fin -->   
                </ul>
            </li>
            </g:if>
            <g:else>
            <div style="text-align:justify;padding:10px;width:100%;">
                <center>
                <img src="${request.contextPath}/assets/warning.png" class="img-responsive">
                </center>
                </br></br>
                <span style="width:100%;color:white;" class="profile_info profile clearfix">
                Debe estar vinculado a una cocina para tener acceso a sus funciones.
                </span>
            </div>
            </g:else>
		</sec:ifAnyGranted>
		<sec:ifAnyGranted roles='ROLE_REPARTIDOR'>
			<li>
				<a href="${request.contextPath}/"><i class="fa fa-home"></i> Inicio</span></a>
			</li>
			<g:if test="${session.getAttribute('username_establecimiento') != ''}">
			
			
			
			
			
			
			
            </g:if>
            <g:else>
            <div style="text-align:justify;padding:10px;width:100%;">
                <center>
                <img src="${request.contextPath}/assets/warning.png" class="img-responsive">
                </center>
                </br></br>
                <span style="width:100%;color:white;" class="profile_info profile clearfix">
                Debe estar vinculado a una cocina para tener acceso a sus funciones.
                </span>
            </div>
            </g:else>
		</sec:ifAnyGranted>
		<sec:ifAnyGranted roles='ROLE_EMPRESA'>
			<li>
				<a href="${request.contextPath}/"><i class="fa fa-home"></i> Inicio</span></a>
			</li>
			<g:if test="${session.getAttribute('username_empresa') != ''}">
			<li>
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-money"></i> Vía nómina <span class="fa fa-chevron-down"></span></a>
                <ul class="nav child_menu">
                  	<li><a href="${request.contextPath}/userLG/solicitudes">Solicitudes</a></li>
                  	<li><a href="${request.contextPath}/userLG/adeudos">Adeudos</a></li>
                </ul>
            </li>
			<li>
				<a href="${request.contextPath}/userLG/empleados"><i class="fa fa-users"></i> Empleados</span></a>
			</li>
			<li>
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-file-text-o"></i> Informes <span class="fa fa-chevron-down"></span></a>
                <ul class="nav child_menu">				
                  	<li><a href="${request.contextPath}/informes/listadoPagos_empresa">Mis pagos pendientes</a></li>			
                </ul>
            </li>
            </g:if>
            <g:else>
            <div style="text-align:justify;padding:10px;width:100%;">
                <center>
                <img src="${request.contextPath}/assets/warning.png" class="img-responsive">
                </center>
                </br></br>
                <span style="width:100%;color:white;" class="profile_info profile clearfix">
                Debe estar vinculado a una empresa para tener acceso a sus funciones.
                </span>
            </div>
            </g:else>
		</sec:ifAnyGranted>
		<sec:ifAnyGranted roles='ROLE_PROGRAMADOR'>
			<li>
				<a href="${request.contextPath}/"><i class="fa fa-home"></i> Inicio</span></a>
			</li>
			<li>
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-lock"></i> Seguridad <span class="fa fa-chevron-down"></span></a>
                <ul class="nav child_menu">
                  	<li><a href="${request.contextPath}/userLG/index">Gestión de usuarios</a></li>
                  	<li><a href="${request.contextPath}/roleLG/index">Gestión de roles</a></li>
                </ul>
            </li>
			<li>
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-cubes"></i> Catálogos <span class="fa fa-chevron-down"></span></a>
				<ul class="nav child_menu">
					<li><a href="${request.contextPath}/tipo/index">Tipo de platillos</a></li>
					<li><a href="${request.contextPath}/categoria/index">Categorías de platillos</a></li>
					<li><a href="${request.contextPath}/ingrediente/index">Ingredientes</a></li>
					<li><a href="${request.contextPath}/extra/index">Extras</a></li>
				</ul>
			</li>
			<li>
				<a href="${request.contextPath}/empresa/index"><i class="fa fa-industry"></i> Empresas</span></a>
			</li>
			<li>
				<a href="${request.contextPath}/establecimiento/index"><i class="fa fa-building-o"></i> Cocinas</span></a>
			</li>
			<li>
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-money"></i> Vía nómina <span class="fa fa-chevron-down"></span></a>
                <ul class="nav child_menu">
                  	<li><a href="${request.contextPath}/userLG/solicitudes">Solicitudes</a></li>
                  	<li><a href="${request.contextPath}/userLG/adeudos">Adeudos</a></li>
                </ul>
            </li>
			<li>
				<a href="${request.contextPath}/userLG/empleados"><i class="fa fa-users"></i> Empleados</span></a>
			</li>
			<li>
				<a href="${request.contextPath}/orden/index"><i class="fa fa-cart-arrow-down"></i> Ordenes</span></a>
			</li>
			<li>
				<a href="${request.contextPath}/embarque/index"><i class="fa fa-truck"></i> Embarques</span></a>
			</li>
			<li>
				<a href="${request.contextPath}/platillo/index"><i class="fa fa-cutlery"></i> Platillos</span></a>
			</li>
            <li>
				<a href="${request.contextPath}/platilloEstablecimiento/index"><i class="fa fa-coffee"></i> Menú</span></a>
			</li>
			<li>
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-file-text-o"></i> Informes <span class="fa fa-chevron-down"></span></a>
                <ul class="nav child_menu">
					<li><a href="${request.contextPath}/informes/listadoPlatillos">Listado de platillos</a></li>
					<li><a href="${request.contextPath}/informes/listadoExtras">Listado de extras</a></li>
					<li><a href="${request.contextPath}/informes/listadoIngredientes">Listado de ingredientes</a></li>
					<li><a href="${request.contextPath}/informes/listadoUsuarios">Listado de usuarios</a></li>
                  	<li><a href="${request.contextPath}/informes/listadoEmpresas">Listado de empresas</a></li>
					<li><a href="${request.contextPath}/informes/listadoEstablecimientos">Listado de cocinas</a></li>
					<li><a href="${request.contextPath}/informes/listadoPagos">Mis pagos pendientes</a></li>
					<li><a href="${request.contextPath}/informes/listadoEmbarques">Listado de embarques</a></li>
					<li><a href="${request.contextPath}/informes/listadoMenus">Listado de menú</a></li>
                </ul>
            </li>
		</sec:ifAnyGranted>
    </sec:ifLoggedIn>
    <sec:ifNotLoggedIn>
        <li class="dropdown">
            <a href="${request.contextPath}/login/auth" class="dropdown-toggle" role="button" aria-haspopup="true" aria-expanded="false">Ingresar <img src="${request.contextPath}/assets/caret_login.png" border="0"></a>
        </li>
    </sec:ifNotLoggedIn>
    </content>
