<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${session.getAttribute('username_rolActivo') == 'ROLE_PROPIETARIO'?'Empresa':'Empresa'}" />
		<g:set var="entitiesName" value="${session.getAttribute('username_rolActivo') == 'ROLE_PROPIETARIO'?'Empresas':'Empresas'}" />
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
			  <li class="active">${empresa.nombre}</li>
			</ul>
		  </div>
		</div>
		<!-- .breadcrumb-box -->
        <a href="#show-empresa" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav_topbar" role="navigation">
            <ul>
                <li><a href="javascript:go('edit')"		class="edit"><g:message code="default.button.edit.label" default="Edit" /></a></li>
                <li><a href="javascript:go('delete')"	class="delete"><g:message code="default.button.delete.label" default="Delete" /></a></li>
                <li><a href="javascript:go('close')"	class="close"><g:message code="default.button.close.label" default="close" /></a></li>
            </ul>
        </div>
        <div id="show-empresa" class="content scaffold-show" role="main">

                <div class="row">
                <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
                </g:if>
                %{--
                <f:display bean="empresa" />
                --}%            
                <div class="col-md-8 col-sm-12 col-xs-12">
                    <div class="x_panel">
                        <div class="x_content">
                            <f:with bean="empresa">
                                <ol class="property-list empresa">

                                    <div class="tituloPrincipal"><f:display property="nombre"/> (<f:display property="codigo"/>) </div>
                                    <div class="tituloSecundario"><f:display property="establecimiento"/></div>
                                    <div class="tituloTerciario"><f:display property="giro"/></div>
                                

                                    <li class="fieldcontain">
                                        <span id="razonSocial-label" class="property-label"><g:message code="empresa.razonSocial.label" default="Razón social" /></span>
                                        <div class="property-value" aria-labelledby="razonSocial-label"><f:display property="razonSocial"/></div>
                                    </li>
                                   
                                    <li class="fieldcontain">
                                        <span id="noEmpleados-label" class="property-label"><g:message code="empresa.noEmpleados.label" default="# Empleados" /></span>
                                        <div class="property-value" aria-labelledby="noEmpleados-label"><f:display property="noEmpleados"/></div>
                                    </li>
                                    <li class="fieldcontain">
                                        <span id="rfc-label" class="property-label"><g:message code="empresa.rfc.label" default="R.F.C." /></span>
                                        <div class="property-value" aria-labelledby="rfc-label"><f:display property="rfc"/></div>
                                    </li>
                                    <g:if test="${empresa.telefono}">
                                    <li class="fieldcontain">
                                        <span id="telefono-label" class="property-label"><g:message code="empresa.telefono.label" default="Teléfono" /></span>
                                        <div class="property-value" aria-labelledby="telefono-label"><f:display property="telefono"/></div>
                                    </li>
                                    </g:if>
									<g:if test="${empresa.url}">
                                    <li class="fieldcontain">
                                        <span id="url-label" class="property-label"><g:message code="empresa.url.label" default="URL" /></span>
                                        <div class="property-value" aria-labelledby="url-label"><f:display property="url"/></div>
                                    </li>
                                    </g:if>
									<li class="fieldcontain">
										<span id="activo-label" class="property-label"><g:message code="empresa.activo.label" default="Es una empresa activa" /></span>
										<div class="property-value" aria-labelledby="activo-label"><f:display property="activo"/></div>
									</li>
                                    <br>
                                    <div class="x_title">
                                        <h2>Ubicación</h2>
                                        <div class="clearfix"></div>
                                    </div>
                                    <g:if test="${empresa.domicilio}">
                                    <li class="fieldcontain">
                                        <span id="domicilio-label" class="property-label"><g:message code="empresa.domicilio.label" default="Domicilio" /></span>
                                        <div class="property-value" aria-labelledby="domicilio-label"><f:display property="domicilio"/></div>
                                    </li>
                                    </g:if>
                                    <g:if test="${empresa.ciudad}">
                                    <li class="fieldcontain">
                                        <span id="ciudad-label" class="property-label"><g:message code="empresa.ciudad.label" default="Ciudad" /></span>
                                        <div class="property-value" aria-labelledby="ciudad-label"><f:display property="ciudad"/></div>
                                    </li>
                                    </g:if>
                                    <g:if test="${empresa.cp}">
                                    <li class="fieldcontain">
                                        <span id="cp-label" class="property-label"><g:message code="empresa.cp.label" default="C.P." /></span>
                                        <div class="property-value" aria-labelledby="cp-label"><f:display property="cp"/></div>
                                    </li>
                                    </g:if>
                                    <br>
                                    <div class="x_title">
                                        <h2>Horarios de entrega</h2>
                                        <div class="clearfix"></div>
                                    </div>
                                    <g:if test="${empresa.horaEntrega_1}">
                                    <li class="fieldcontain">
                                        <span id="horaEntrega_1-label" class="property-label">Desayuno</span>
                                        <div class="property-value" aria-labelledby="horaEntrega_1-label"><f:display property="horaEntrega_1"/></div>
                                    </li>
                                    </g:if>
                                    <g:if test="${empresa.horaEntrega_2}">
                                    <li class="fieldcontain">
                                        <span id="horaEntrega_2-label" class="property-label">Comida</span>
                                        <div class="property-value" aria-labelledby="horaEntrega_2-label"><f:display property="horaEntrega_2"/></div>
                                    </li>
                                    </g:if>
                                    <g:if test="${empresa.horaEntrega_3}">
                                    <li class="fieldcontain">
                                        <span id="horaEntrega_3-label" class="property-label">Cena</span>
                                        <div class="property-value" aria-labelledby="horaEntrega_3-label"><f:display property="horaEntrega_3"/></div>
                                    </li>
                                    </g:if>
                                </ol>
                            </f:with>
                        </div>
                    </div>
                </div>

               <div class="col-md-4 col-sm-12 col-xs-12">
                    <div class="x_panel">
                        <div class="x_content"> 
                            <f:with bean="empresa">                       
                                <div class="x_title">
                                    <h2>Logotipo</h2>
                                    <div class="clearfix"></div>
                                </div>
                                <center>
                                <div class="property-value" aria-labelledby="archivo_Foto-label">
                                    <g:if test="${empresa.archivo_Foto}">
                                        <img src="${request.contextPath}/assets/mes-icons/<f:display property='archivo_Foto'/>" style="width:200px;height:200px;"/>
                                    </g:if>
                                    <g:else>
                                        <img src="${request.contextPath}/assets/mes-icons/default.png" style="width:200px;height:200px;"/>
                                    </g:else>
                                </div> 
                                </center>
                            </f:with>                          
                        </div>
                    </div>
                </div>        

               <div class="col-md-4 col-sm-12 col-xs-12">
                    <div class="x_panel">
                        <div class="x_content"> 
                            <f:with bean="empresa">                       
                                <div class="x_title">
                                    <h2>Contacto</h2>
                                    <div class="clearfix"></div>
                                </div>
                                <ol class="property-list empresa">
									<g:if test="${empresa.contacto}">
                                    <li class="fieldcontain">
                                        <span id="contacto-label" class="property-label"><g:message code="empresa.contacto.label" default="Nombre del contacto" /></span>
                                        <div class="property-value" aria-labelledby="contacto-label"><f:display property="contacto"/></div>
                                    </li>
                                    </g:if>
                                    <g:if test="${empresa.contactoCargo}">
                                    <li class="fieldcontain">
                                        <span id="contactoCargo-label" class="property-label"><g:message code="empresa.contactoCargo.label" default="Cargo del contacto" /></span>
                                        <div class="property-value" aria-labelledby="contactoCargo-label"><f:display property="contactoCargo"/></div>
                                    </li>
                                    </g:if>
                                    <g:if test="${empresa.contactoTelefono}">
                                    <li class="fieldcontain">
                                        <span id="contactoTelefono-label" class="property-label"><g:message code="empresa.contactoTelefono.label" default="Teléfono de contacto" /></span>
                                        <div class="property-value" aria-labelledby="contactoTelefono-label"><f:display property="contactoTelefono"/></div>
                                    </li>
                                    </g:if>
                                    <g:if test="${empresa.contactoEmail}">
                                    <li class="fieldcontain">
                                        <span id="contactoEmail-label" class="property-label"><g:message code="empresa.contactoEmail.label" default="Email de contacto" /></span>
                                        <div class="property-value" aria-labelledby="contactoEmail-label"><f:display property="contactoEmail"/></div>
                                    </li>
                                    </g:if>
                                </ol>
                            </f:with>
                        </div>
                    </div>
                </div>                         
            </div>

            
            
			
            
            <g:form resource="${this.empresa}" method="DELETE" name="delete_form">
				<fieldset class="buttons">
					<g:link name="footer-edit" class="edit" action="edit" resource="${this.empresa}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:link name="footer-delete" class="delete" url="javascript:void(0)" onclick="jconfirm('${message(code: 'default.delete.ask.message', default: 'Are you sure?')}','${message(code: 'default.delete.info.message', default: 'You are deleting the record.')}');"><g:message code="default.button.delete.label" default="Delete" /></g:link>
                    <g:link name="footer-close" class="close" action="index"><g:message code="default.button.close.label" default="Close" /></g:link>
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
