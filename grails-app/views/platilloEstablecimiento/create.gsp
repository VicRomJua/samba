<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'platilloEstablecimiento.label', default: 'Mi Menú')}" />
        <g:set var="entitiesName" value="${message(code: 'platilloEstablecimiento.label', default: 'Mi Menú')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <style>
			#costo, #precio {width:20%}
            #nombre {width:50%}
            #establecimiento, #platillo {width:50%}
        </style>
        <script language="javascript">
			$( document ).ready(function() {
				<sec:ifAnyGranted roles='ROLE_PROPIETARIO,ROLE_PROGRAMADOR'>
					$("#establecimiento").select2();
				</sec:ifAnyGranted>
				
				$("#platillo").select2({
					language: "es"
				});
				
				$("#precio").limitkeypress({ rexp: /^\d*\.?\d*$/ }); // /^[0-9]*.[0-9]*$/
				$("#costo").limitkeypress({ rexp: /^\d*\.?\d*$/ });
				
			});
			
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
			  <li class="active"><g:message code="default.new.label" args="[entityName]" /></li>
			</ul>	
		  </div>
		</div>
		<!-- .breadcrumb-box -->
        <a href="#create-platilloEstablecimiento" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav_topbar" role="navigation">
            <ul>
				<li><a href="javascript:go('save')"		class="save"><g:message code="default.button.save.label" default="Edit" /></a></li>
                <li><a href="javascript:go('close')"	class="close"><g:message code="default.button.close.label" default="close" /></a></li>
            </ul>
        </div>
        <div id="create-platilloEstablecimiento" class="content scaffold-create" role="main">
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.platilloEstablecimiento}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.platilloEstablecimiento}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="save">
                <fieldset class="form">
					
                    %{--
                    <f:all bean="platilloEstablecimiento"/>
                    --}%
                    
					<f:with bean="platilloEstablecimiento">

                        <div class="row">

                            <div class="col-md-12 col-sm-12 col-xs-12">
                                <div class="x_panel">
                                    <div class="x_content">
										
										<sec:ifAnyGranted roles='ROLE_PROPIETARIO,ROLE_PROGRAMADOR'>
											<f:field property="establecimiento" />
                                        </sec:ifAnyGranted>
                                        <sec:ifNotGranted roles="ROLE_PROPIETARIO,ROLE_PROGRAMADOR">
											<input type="hidden" name="establecimiento" id="establecimiento" value="${platilloEstablecimiento.establecimiento?.id}" />
										</sec:ifNotGranted>
										
										<f:field property="platillo">
											<g:select id="platillo" name="platillo" from="${platillosList}" optionKey="id" optionValue="nombre" />
										</f:field>
                                        <f:field property="precio"/>
                                        <f:field property="costo"/>        
                                        <f:field property="activo"/>
                                        <f:field property="id_Autor" wrapper="display"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </f:with>
                    
                </fieldset>
                <g:submitButton name="footer-save" style="visibility:hidden;position:absolute;top:0px;width:0px;"/>
                <fieldset class="buttons">
					<g:link class="save" url="javascript:go('save')"><g:message code="default.button.save.label" default="Save" /></g:link>
                    <g:link name="footer-close" class="close" action="index"><g:message code="default.button.close.label" default="Close" /></g:link>
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
