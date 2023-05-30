<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'alumnos.label', default: 'Alumno')}" />
        <g:set var="entitiesName" value="${message(code: 'alumnos.label', default: 'Alumnos')}" />
        <title><g:message code="default.create.label" args="[entitiesName]" /></title>

  <script language="javascript">
            var options = {};
            options.singleDatePicker = true;
            options.showDropdowns = true;
            options.format =  'DD-MM-YYYY',
            options.locale = {
              daysOfWeek: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi','Sa'],
              monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
              firstDay: 1,
              opens: "center"
            };
            $(document).ready(function(e){
                //$("#filter_estatus").select2().css(['style','visible']);
                $('#visorInforme').hide();
                $('#fechaInicio').mask("00-00-0000", {placeholder: "__-__-____"});
                $('#fechaInicio').daterangepicker(options, function(start, end, label) { });
                $('#fechaTermino').mask("00-00-0000", {placeholder: "__-__-____"});
                $('#fechaTermino').daterangepicker(options, function(start, end, label) { });                
            });
            
            function showInforme(establecimientoID, establecimientoNombre){

                liga = "${grailsApplication.config.jasper_serverAddress}flow.html?_flowId=viewReportFlow&j_username=${grailsApplication.config.jasper_user}&j_password=${grailsApplication.config.jasper_password}&standAlone=true&decorate=no&ParentFolderUri=%2Freports%2Fsamba&reportUnit=%2Freports%2Fsamba%2Fplatillos_proxima_semana&standAlone=true&fechaInicioParam="+ $('#fechaInicio').val() +"&fechaTerminoParam="+ $('#fechaTermino').val() +"&establecimientoID=" + establecimientoID + "&establecimientoNombre=" + establecimientoNombre;

          
                $("#visorInforme").attr("src", liga);

                 $("#visorInforme").show();

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
                padding-bottom:5px;wi
                padding-top:5px;
                -moz-border-radius: 5px 5px 0px 0px;
                -webkit-border-radius: 5px 5px 0px 0px;
                border-radius: 5px 5px 0px px;
            }
            table>tbody>tr>td {
                border-bottom: 0px;
            }
            table{
                margin-top: 0px;
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
			  <li class="active"><g:message code="default.report.label" args="['Platillos por período']" /></li>
			</ul>	
		  </div>
		</div>
		<!-- .breadcrumb-box -->
        <a href="#create-materias" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
            <table>
                <tr>
                    <td><span><strong>Fecha de inicio:</strong></span></td>
                    <td>
                        <div class="input-group" >
							<input type="text" class="form-control" name="fechaInicio" id="fechaInicio" aria-describedby="inputSuccess2Status3" value="${proximoLunes}" style="text-indent:40px;height: 38px;">
							<span class="fa fa-calendar-o form-control-feedback left" aria-hidden="true"></span>
						</div>
                    </td>                    
                    <td><span><strong>Fecha de termino:</strong> </span></td>
                    <td>
						<div class="input-group">
							<input type="text" class="form-control" name="fechaTermino" id="fechaTermino" aria-describedby="inputSuccess2Status3" value="${proximoSabado}" style="text-indent:40px;height: 38px;">
							<span class="fa fa-calendar-o form-control-feedback left" aria-hidden="true"></span>
						</div>
                    </td>
                    <td>
                    <fieldset class="buttons">
                     <a href="javascript:showInforme('${session.getAttribute('username_establecimiento_id')}','${session.getAttribute('username_establecimiento')}')"><li class="fa fa-search fa-lg" style="vertical-align: 0%;"></li></a>
                    </fieldset>
                    </td>
                </tr>
            </table>
            <iframe frameborder="0" width="100%" height="680px" src="" id="visorInforme"></iframe>
    </body>
</html>

