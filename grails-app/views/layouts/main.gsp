<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>SambaFresh</title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
   
    <asset:stylesheet src="application.css"/>
    <asset:javascript src="jquery-2.2.0.min.js"/>
    
<sec:ifNotLoggedIn>
	<asset:stylesheet src="login.css"/>
	<asset:stylesheet src="public.css"/>
	<script language="javascript">
			function submit() {
				$('form#login-form').submit();
			}
			$(document).ready(function(){
				
				$( "#username" ).focus();
				
				$("#kayit-form").hide();
				$("#sifre-hatirlat-form").hide();
				
				$(".hesap-olustur-link").click(function(e){
					$("#login-form").slideUp(0);	
					$("#kayit-form").fadeIn(300);	
				});

				$(".zaten-hesap-var-link").click(function(e){
					$("#kayit-form").slideUp(0);
					$("#sifre-hatirlat-form").slideUp(0);	
					$("#login-form").fadeIn(300);	
				});

				$(".sifre-hatirlat-link").click(function(e){
					$("#login-form").slideUp(0);	
					$("#sifre-hatirlat-form").fadeIn(300);	
				});
			});

        </script>
</sec:ifNotLoggedIn>
    <g:layoutHead/>
</head>
    
<body class="nav-md" >
<g:if test="${session.getAttribute("redirect")}">
	<script languaje="javascript">
		location.href="${request.contextPath}${session.getAttribute("redirect")}";
	</script>
</g:if>
<g:else>
<sec:ifLoggedIn>
	<div class="container body">
		<div class="main_container">
			<div class="col-md-3 left_col">    
				<div class="left_col ">
					<div class="navbar nav_title">
						<a class="site_title" style="padding:0px; margin:0px;text-align:center"><img src="${request.contextPath}/assets/samba_white.png" width="150px"></a>
					</div>
					<div class="clearfix"></div>
					<!-- menu profile quick info -->
					<div class="profile clearfix">
						<div class="profile_pic">
							<img src="${request.contextPath}/assets/mes-users/${session.getAttribute('username_archivo_Foto')}" class="img-circle profile_img" />
						</div>
						<div class="profile_info">
							<span>¡Bienvenido!</span>
							<h2>${session.getAttribute("username_rolActivoNombre")}</h2>
						</div>
					</div>
					<br>
					<!-- sidebar menu -->
					<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
						<div class="menu_section container">
							<ul class="nav side-menu" >
								<g:pageProperty name="page.nav" />
							</ul>
						</div>
					</div>                    
					<!-- /menu footer buttons -->
					<div class="sidebar-footer hidden-small">
						<a data-toggle="tooltip" data-placement="top" title="Mi cuenta" href="${request.contextPath}/userLG/myuser" style="width:50%">
							<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
						</a>
						<a data-toggle="tooltip" data-placement="top" title="Cerrar sesión" href="${request.contextPath}/logout" style="width:50%">
							<span class="glyphicon glyphicon-off" aria-hidden="true"></span>
						</a>
					</div>
				</div>
			</div>
			<!-- top navigation (perfil de usuario) -->
			<div class="top_nav">
				<div class="nav_menu" >
					<nav>      
						<div class="nav_collapse">
							<a id="menu_toggle"><i class="fa fa-arrows-h" aria-hidden="true"></i></a>
						</div>
						<sec:ifAnyGranted roles='ROLE_ADMIN,ROLE_COCINERO,ROLE_REPARTIDOR'>
							<g:if test="${session.getAttribute('username_establecimiento') != ''}">
							<div style="display:inline-block;font-size:20px;font-weight:bold;"><font color="#333">Establecimiento: </font><font color="#b4d048">${session.getAttribute("username_establecimiento")}</font></div>
							</g:if>
						</sec:ifAnyGranted>
						<sec:ifAnyGranted roles='ROLE_EMPRESA'>
							<g:if test="${session.getAttribute('username_empresa') != ''}">
							<div style="display:inline-block;font-size:20px;font-weight:bold;"><font color="#333">Empresa: </font><font color="#b4d048">${session.getAttribute("username_empresa")}</font></div>
							</g:if>
						</sec:ifAnyGranted>
						<ul class="nav navbar-nav navbar-right" >
							<li class="dropdown">
								<a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
									<img src="${request.contextPath}/assets/user.png" alt="">${session.getAttribute("username_main")} 
									<span class=" fa fa-angle-down"></span>
							    </a>
								<ul class="dropdown-menu dropdown-usermenu animated fadeInDown pull-right">
									<li><a href="${request.contextPath}/userLG/myuser">Mi usuario</a></li>
									<li><a href="${request.contextPath}/logout"><i class="fa fa-sign-out pull-right"></i>Cerrar sesión</a></li>
								</ul>
							</li>
						</ul>
					</nav>
				</div>
			</div>
			<!-- page content -->
			<div class="right_col" role="main">
				<div class="">
					<g:layoutBody/>
				</div>
				</br>
			</div>
		</div>
    </div>
</sec:ifLoggedIn>
    <asset:javascript src="application.js"/>
<sec:ifNotLoggedIn>	

    <g:layoutBody/>
    
</sec:ifNotLoggedIn>

    <div id="spinner" class="spinner" style="display:none;">
        <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div>
</g:else>
</body> 
</html>
