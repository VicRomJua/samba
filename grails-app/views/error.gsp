<!DOCTYPE html>
<html>
    <head>
        <title><g:if env="development">Grails Runtime Exception</g:if><g:else>Error</g:else></title>
        <meta name="layout" content="main" />
        <g:if env="development"><asset:stylesheet src="errors.css"/></g:if>
    </head>
    <body>
        <g:render template="/layouts/menu" />
        <g:if env="development">
			<div class="x_panel">
				<div class="x_content" style="text-align: center;">
							<img src="${request.contextPath}/assets/error.png" border="0px"/>
							</br>
							<span style="font-size:22px;color:#6D6E70;">
								Ocurrió un error inesperado...
							</span>
				</div>
			</div>
			<div class="x_panel">                    
				<div class="x_content">
					<div>
						<strong>Error ${request.'javax.servlet.error.status_code'}:</strong>
						${request.'javax.servlet.error.message'.encodeAsHTML()}<br/>
						<strong>Servlet:</strong> ${request.'javax.servlet.error.servlet_name'}<br/>
						<strong>URI:</strong> ${request.'javax.servlet.error.request_uri'}<br/>
						<g:if test="${exception}">
							<strong>Exception Message:</strong> ${exception.message?.encodeAsHTML()} <br/>
							<strong>Caused by:</strong> ${exception.cause?.message?.encodeAsHTML()} <br/>
							<strong>Class:</strong> ${exception.className} <br/>
							<strong>At Line:</strong> [${exception.lineNumber}] <br/>
							<strong>Code Snippet:</strong><br/>

							<div class="stack">
								<pre><g:each in="${exception.stackTraceLines}">${it.encodeAsHTML()}<br/></g:each></pre>
							</div>
						</g:if>
					</div>
				</div>
			</div>            
        </g:if>
        <g:else>
			<div class="x_panel">
				<div class="x_content" style="text-align: center;">
							<img src="${request.contextPath}/assets/error.png" border="0px"/>
							</br>
							<span style="font-size:22px;color:#6D6E70;">
								Ocurrió un error inesperado...
							</span>
				</div>
			</div>
			<div class="x_panel">                    
				<div class="x_content">
					<div>
						<strong>Error ${request.'javax.servlet.error.status_code'}:</strong>
						${request.'javax.servlet.error.message'.encodeAsHTML()}<br/>
						<strong>Servlet:</strong> ${request.'javax.servlet.error.servlet_name'}<br/>
						<strong>URI:</strong> ${request.'javax.servlet.error.request_uri'}<br/>
						<g:if test="${exception}">
							<strong>Exception Message:</strong> ${exception.message?.encodeAsHTML()} <br/>
							<strong>Caused by:</strong> ${exception.cause?.message?.encodeAsHTML()} <br/>
							<strong>Class:</strong> ${exception.className} <br/>
							<strong>At Line:</strong> [${exception.lineNumber}] <br/>
							<strong>Code Snippet:</strong><br/>

							<div class="stack">
								<pre><g:each in="${exception.stackTraceLines}">${it.encodeAsHTML()}<br/></g:each></pre>
							</div>
						</g:if>
					</div>
				</div>
			</div> 
        </g:else>
    </body>
</html>
