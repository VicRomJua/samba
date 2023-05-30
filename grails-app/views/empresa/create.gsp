<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${session.getAttribute('username_rolActivo') == 'ROLE_PROPIETARIO'?'Empresa':'Empresa'}" />
		<g:set var="entitiesName" value="${session.getAttribute('username_rolActivo') == 'ROLE_PROPIETARIO'?'Empresas':'Empresas'}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <style>
			#codigo {width:10%}
            #nombre, #domicilio, #establecimiento {width:50%}
            #rfc {width:25%}
            #contacto, #contactoEmail {width:50%}
            #razonSocial {width: 50%}
            #noEmpleados {width: 20%}
        </style>
        <script language="javascript">
			$( document ).ready(function() {
				<sec:ifAnyGranted roles='ROLE_PROPIETARIO,ROLE_PROGRAMADOR'>
					$("#establecimiento").select2();
				</sec:ifAnyGranted>
				
				$("#rfc").alphanum();
				$("#noEmpleados").numeric("positiveInteger");
				
				$("#giro").select2();
				
				$("#cp").mask('00000');
				$("#telefono").mask('(000) 000-0000', {placeholder: "Ej. (111) 111-1111"});
				$("#contactoTelefono").mask('(000) 000-0000', {placeholder: "Ej. (111) 111-1111"});
				$("#horaEntrega_1").mask('00:00 XY', {
					placeholder: "Ej. 08:00 AM",
					translation: {
					  'X': {
						pattern: /[AaPp]/, optional: true
					  },
					  'Y': {
						pattern: /[Mm]/, optional: true
					  }
					}
				});
				$("#horaEntrega_2").mask('00:00 XY', {
					placeholder: "Ej. 01:00 PM",
					translation: {
					  'X': {
						pattern: /[AaPp]/, optional: true
					  },
					  'Y': {
						pattern: /[Mm]/, optional: true
					  }
					}
				});
				$("#horaEntrega_3").mask('00:00 XY', {
					placeholder: "Ej. 07:00 PM",
					translation: {
					  'X': {
						pattern: /[AaPp]/, optional: true
					  },
					  'Y': {
						pattern: /[Mm]/, optional: true
					  }
					}
				});
				
				$('INPUT[type="file"]').change(function () {
					var ext = this.value.match(/\.(.+)$/)[1].toUpperCase();
					switch (ext) {
						case 'JPG':
						case 'JPEG':
						case 'PNG':
						case 'GIF':
							break;
						default:
							this.value = '';
							swal({
								title: "Advertencia",
								text: "Esta intentando subir un archivo que NO es una imagen",
								type: "warning",
								showCancelButton: false,
								confirmButtonColor: '#FF7C24',
								confirmButtonText: 'Aceptar',
								confirmButtonClass: 'btn btn-success',
								buttonsStyling: true
							});
					}
				});
				
			});
			
			function go(id_tag){
				$('[name="footer-'+id_tag+'"]')[0].click();
			}
			
			function PreviewImage(id_upload, id_preview) {
				var ext = $("#"+id_upload).val().match(/\.(.+)$/)[1].toUpperCase();
				if (ext != "JPG" && ext != "JPEG" && ext != "GIF" && ext != "PNG"){
					document.getElementById(id_preview).src = "${request.contextPath}/assets/mes-icons/default.png";
				}else{
					var oFReader = new FileReader();
					oFReader.readAsDataURL(document.getElementById(id_upload).files[0]);

					oFReader.onload = function (oFREvent) {
						document.getElementById(id_preview).src = oFREvent.target.result;
					};
				}
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
			  <li class="active"><g:message code="default.new.label" args="[entityName]" /></li>
			</ul>	
		  </div>
		</div>
		<!-- .breadcrumb-box -->
        <a href="#create-empresa" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav_topbar" role="navigation">
            <ul>
				<li><a href="javascript:go('save')"		class="save"><g:message code="default.button.save.label" default="Edit" /></a></li>
                <li><a href="javascript:go('close')"	class="close"><g:message code="default.button.close.label" default="close" /></a></li>
            </ul>
        </div>
        <div id="create-empresa" class="content scaffold-create" role="main">
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.empresa}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.empresa}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:uploadForm action="save">
                <fieldset class="form">                    
                    <f:with bean="empresa">

                        <div class="row">

                            <div class="col-md-8 col-sm-12 col-xs-12">
                                <div class="x_panel">
                                    <div class="x_content">

                                        <div class="x_title">
                                            <h2>Información general</h2>
                                            <div class="clearfix"></div>
                                        </div>                                  
                                           
                                        <sec:ifAnyGranted roles='ROLE_PROPIETARIO,ROLE_PROGRAMADOR'>
											<f:field property="establecimiento" />
                                        </sec:ifAnyGranted>
                                        <sec:ifNotGranted roles="ROLE_PROPIETARIO,ROLE_PROGRAMADOR">
											<input type="hidden" name="establecimiento" id="establecimiento" value="${empresa.establecimiento?.id}" />
										</sec:ifNotGranted>
										
                                        <f:field property="nombre" />
                                        <f:field property="codigo" />
                                        <f:field property="razonSocial" />
                                        <f:field property="giro" />
                                        <f:field property="noEmpleados" />
                                        <f:field property="rfc" />
                                        <f:field property="telefono"/>
                                        <f:field property="url"/>
                                        <f:field property="activo"/>
                                        <br>

                                        <div class="x_title">
                                            <h2>Ubicación</h2>
                                            <div class="clearfix"></div>
                                        </div>  
                                        <f:field property="domicilio"/>
                                        <f:field property="ciudad"/>
                                        <f:field property="cp"/>
                                        <br>

                                        <div class="x_title">
                                            <h2>Contacto</h2>
                                            <div class="clearfix"></div>
                                        </div>  


                                        
                                        <f:field property="contacto"/>
                                        <f:field property="contactoCargo"/>
                                        <f:field property="contactoTelefono"/>
                                        <f:field property="contactoEmail"/>
                                        <br>

                                        <div class="x_title">
                                            <h2>Horarios de entrega</h2>
                                            <div class="clearfix"></div>
                                        </div>                                         
                                        <f:field property="horaEntrega_1" label="Desayuno"/>
                                        <f:field property="horaEntrega_2" label="Comida"/>
                                        <f:field property="horaEntrega_3" label="Cena"/>
                                    </div>
                                </div>
                            </div>                            

                            <div class="col-md-4 col-sm-12 col-xs-12">
                                <div class="x_panel">
                                    <div class="x_content">
                                        <div class="x_title">
                                            <h2>Logotipo</h2>
                                            <div class="clearfix"></div>
                                        </div>                                       
                                       <f:field property="archivo_Foto" wrapper="iconlight"/> 

                                    </div>
                                </div>
                            </div>
                        </div>             
                    
                        %{--
                        <f:with bean="empresa">
                            <f:field property="nombre"/>
                            <f:field property="OTROCAMPO" label="OTRO CAMPO"/>
                        </f:with>
    					--}%
                    </f:with>
                </fieldset>
                <g:submitButton name="footer-save" style="visibility:hidden;position:absolute;top:0px;width:0px;"/>
                <fieldset class="buttons">
					<g:link class="save" url="javascript:go('save')"><g:message code="default.button.save.label" default="Save" /></g:link>
                    <g:link name="footer-close" class="close" action="index"><g:message code="default.button.close.label" default="Close" /></g:link>
                </fieldset>
            </g:uploadForm>
        </div>
    </body>
</html>
