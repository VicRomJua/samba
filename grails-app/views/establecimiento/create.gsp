<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'establecimiento.label', default: 'Cocina')}" />
        <g:set var="entitiesName" value="${message(code: 'establecimiento.label', default: 'Cocinas')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <style>
			#codigo {width:10%}
			#telefono {width:20%}
            #nombre, #domicilio, #administrador {width:50%}
        </style>
        <script language="javascript">
			$( document ).ready(function() {
				$("#administrador").select2();
				
				$("#cp").mask('00000');
				$("#telefono").mask('(000) 000-0000', {placeholder: "Ej. (111) 111-1111"});
				
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
			  <li class="active"><g:message code="default.new.label_a" args="[entityName]" /></li>
			</ul>	
		  </div>
		</div>
		<!-- .breadcrumb-box -->
        <a href="#create-establecimiento" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav_topbar" role="navigation">
            <ul>
				<li><a href="javascript:go('save')"		class="save"><g:message code="default.button.save.label" default="Edit" /></a></li>
                <li><a href="javascript:go('close')"	class="close"><g:message code="default.button.close.label" default="close" /></a></li>
            </ul>
        </div>
        <div id="create-establecimiento" class="content scaffold-create" role="main">
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.establecimiento}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.establecimiento}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="save">
                <fieldset class="form">
                    <f:with bean="establecimiento">
                        <div class="x_panel">
                            <div class="x_content">
                                <div class="x_title">
                                    <h2>Información general</h2>
                                    <div class="clearfix"></div>
                                </div>
                                <f:field property="nombre"/>
                                <f:field property="codigo"/>
                                <f:field property="telefono"/>
                                <f:field property="administrador">
									<g:select id="administrador" name="administrador" from="${administradores}" optionKey="id" optionValue="nombre" />
								</f:field>
                                
                                <f:field property="totalVendido"/>                                
                                <div class="x_title">
                                    <h2>Ubicación</h2>
                                    <div class="clearfix"></div>
                                </div>
                                <f:field property="domicilio"/>
                                <f:field property="ciudad"/>
                                <f:field property="cp"/>


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
