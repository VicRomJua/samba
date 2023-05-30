<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'userLG.label', default: 'Usuario')}" />
        <title><g:message code="default.login.label" args="[entityName]" /></title>
        
        <link href='https://fonts.googleapis.com/css?family=Roboto:400,700' rel='stylesheet' type='text/css'>
		<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<sec:ifNotLoggedIn>
		<asset:stylesheet src="login.css"/>
		<asset:stylesheet src="public.css"/>
		
        <script language="javascript">
			function submit_login() {
				$('form#login-form').submit();
			}
			function submit_request() {
				$('form#recupera-form').submit();
			}
			$(document).ready(function(){
				$("#login-form").hide();
				$("#registro-form").hide();
				$("#recupera-form").hide();
				
				$(".hesap-olustur-link").click(function(e){
					$("#login-form").slideUp(0);
					$("#registro-form").fadeIn(300);
				});

				$(".zaten-hesap-var-link").click(function(e){
					$("#registro-form").slideUp(0);
					$("#recupera-form").slideUp(0);	
					$("#login-form").fadeIn(300);
				});

				$(".sifre-hatirlat-link").click(function(e){
					$("#login-form").slideUp(0);
					$("#recupera-form").fadeIn(300);
				});
				
				<g:if test="${params.sendPassword}">
                    $("#recupera-form").fadeIn(700);
                </g:if>
                <g:else>
                    $("#login-form").fadeIn(700);
                </g:else>
			});

        </script>
		<style>
			.month{
				text-align: center;
			}
			.table-condensed{
				border: 10px solid white !important;
			}
		</style>
</sec:ifNotLoggedIn>
    </head>
    <body>
		<g:render template="/layouts/menu" />
<sec:ifLoggedIn>
<g:if test="${(session.getAttribute('username_establecimiento') != '' && session.getAttribute('username_rolActivo') == 'ROLE_ADMIN')|| session.getAttribute('username_rolActivo') == 'ROLE_PROPIETARIO'}">
        <div id="content" role="main">
            <br />
			<div class="">
			  <div class="row top_tiles">
				<div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
				  <div class="tile-stats">
					<div class="icon"><i class="fa fa-star"></i>
					</div>
					<div class="count">${calificacionCount ? calificacionCount: 0}/5</div>
					
					<h3>Reseñas</h3>
					<p></p>
				  </div>
				</div>
				<div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
				  <div class="tile-stats">
					<div class="icon"><i class="fa fa-clock-o"></i>
					</div>
					<div class="count"><span style="font-size:30px">${horariosCount ? horariosCount: ""}&nbsp;</span></div>

					<h3>Horario demandado</h3>
					<p></p>
				  </div>
				</div>
				<div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
				  <div class="tile-stats">
					<div class="icon"><i class="material-icons input-ikon">room_service</i>
					</div>
					<div class="count">${ordenCount ? ordenCount : 0}</div>

					<h3>Ordenes</h3>
					<p></p>
				  </div>
				</div>
				<div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
				  <div class="tile-stats">
					<div class="icon"><i class="material-icons input-ikon">attach_money</i>
					</div>
					<div class="count">${ventasCount ? ventasCount.montoPagado.sum() : 0}</div>

					<h3>Ventas</h3>
					<p></p>
				  </div>
				</div>
			  </div>

			  <div class="row">
				<div class="col-md-12">
				  <div class="x_panel">
					<div class="x_title">
					  <h2>Resumen de ventas <small>${session.getAttribute('username_rolActivo') == 'ROLE_PROPIETARIO'?"Todos los establecimientos":"Mi establecimiento" }</small>
					  </h2>
					  <div class="filter">
						<div id="reportrange" class="pull-right" style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc">
						  <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
						  <span>Diciembre 30, 2014 - Enero 28, 2015</span> <b class="caret"></b>
						</div>
					  </div>
					  <div class="clearfix"></div>
					</div>
					<div class="x_content">
					  <div class="col-md-9 col-sm-12 col-xs-12">
						<div class="demo-container" style="height:280px">
						  <div id="placeholder33x" class="demo-placeholder"></div>
						</div>
						<div class="tiles">
						  <div class="col-md-4 tile">
							<span>Total Diario</span>
							<h2>${resumen.diario}</h2>
							<span class="sparkline_diario graph" style="height: 160px;">
											<canvas width="200" height="60" style="display: inline-block; vertical-align: top; width: 94px; height: 30px;"></canvas>
							</span>
						  </div>
						  <div class="col-md-4 tile">
							<span>Total Semanal</span>
							<h2>${resumen.semanal}</h2>
							<span class="sparkline_semanal graph" style="height: 160px;">
											<canvas width="200" height="60" style="display: inline-block; vertical-align: top; width: 94px; height: 30px;"></canvas>
							</span>
						  </div>
						  <div class="col-md-4 tile">
							<span>Total Mensual</span>
							<h2>${resumen.mensual}</h2>
							<span class="sparkline_mensual graph" style="height: 160px;">
											<canvas width="200" height="60" style="display: inline-block; vertical-align: top; width: 94px; height: 30px;"></canvas>
							</span>
						  </div>
						</div>

					  </div>

					  <div class="col-md-3 col-sm-12 col-xs-12">
						<div>
						  <div class="x_title">
							<h2>TopTen Ventas</h2>
							<ul class="nav navbar-right panel_toolbox">
							  <li><a href="#"><i class="fa fa-chevron-up"></i></a>
							  </li>
							  <li class="dropdown">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
								<ul class="dropdown-menu" role="menu">
								  <li><a href="#">Settings 1</a>
								  </li>
								  <li><a href="#">Settings 2</a>
								  </li>
								</ul>
							  </li>
							  <li><a href="#"><i class="fa fa-close"></i></a>
							  </li>
							</ul>
							<div class="clearfix"></div>
						  </div>
						  <ul class="list-unstyled top_profiles scroll-view">
							<g:each in="${cocina}" var="item" status="c">
							<li class="media event">
							  <div class="media-body">
								<a class="title" href="#" style="color:#76bc1e !important;">${item.cocina}</a>
								<p><strong>${item.ventaMensual} </strong> Venta mensual</p>
								<p> <small>${item.ordenDia  ? item.ordenDia.size() : 0 } Ordenes hoy</small>
								</p>
							  </div>
							</li>
							</g:each>
						  </ul>
						</div>
					  </div>

					</div>
				  </div>
				</div>
			  </div>
			</div>
        </div>
  <script type="text/javascript">
    //define chart clolors ( you maybe add more colors if you want or flot will add it automatic )
    var chartColours = ['#96CA59', '#3F97EB', '#72c380', '#6f7a8a', '#f7cb38', '#5a8022', '#2c7282'];

    //generate random number for charts
    randNum = function() {
      return (Math.floor(Math.random() * (1 + 40 - 20))) + 20;
    }
	var plot;
	var options;
	var d1;
    $(function() {
		
	   $(".sparkline_diario").sparkline([<g:each in="${ventasDiarias}" var="venta" status="c"><g:if test="${c == 0}">${venta.v}</g:if><g:else>,${venta.v}</g:else></g:each>],
		  {
			type:"line",
			height:"40",
			width:"200",
			lineColor:"#f5c062",
			fillColor:"#ffffff",
			lineWidth:3,
			spotColor:"#f5c062",
			minSpotColor:"#f5c062"
		  }
	  );
	  $(".sparkline_semanal").sparkline([<g:each in="${ventasSemanales}" var="venta" status="c"><g:if test="${c == 0}">${venta.v}</g:if><g:else>,${venta.v}</g:else></g:each>],
		  {
			  type:"line",
			  height:"40",
			  width:"200",
			  lineColor:"#f5c062",
			  fillColor:"#ffffff",
			  lineWidth:3,
			  spotColor:"#f5c062",
			  minSpotColor:"#f5c062"
		  }
	  );
	  $(".sparkline_mensual").sparkline([<g:each in="${ventasMensuales}" var="venta" status="c"><g:if test="${c == 0}">${venta.v}</g:if><g:else>,${venta.v}</g:else></g:each>],
		  {
			  type:"line",
			  height:"40",
			  width:"200",
			  lineColor:"#f5c062",
			  fillColor:"#ffffff",
			  lineWidth:3,
			  spotColor:"#f5c062",
			  minSpotColor:"#f5c062"
		  }
	  );
	  
      d1 = [];
	  
	  <g:each in="${ventasResumen}" var="venta" status="c">
			d1.push([${venta.d}, ${venta.v}]);
	  </g:each>     

      var chartMinDate = d1[0][0]; //first day
      var chartMaxDate = d1[d1.length - 1][d1.length - 1]; //last day

      var tickSize = [1, "day"];
      var tformat = "%d/%m/%y";

      //graph options
      options = {
        grid: {
          show: true,
          aboveData: true,
          color: "#3f3f3f",
          labelMargin: 10,
          axisMargin: 0,
          borderWidth: 0,
          borderColor: null,
          minBorderMargin: 5,
          clickable: true,
          hoverable: true,
          autoHighlight: true,
          mouseActiveRadius: 100
        },
        series: {
          lines: {
            show: true,
            fill: true,
            lineWidth: 2,
            steps: false
          },
          points: {
            show: true,
            radius: 4.5,
            symbol: "circle",
            lineWidth: 3.0
          }
        },
        legend: {
          position: "ne",
          margin: [0, -25],
          noColumns: 0,
          labelBoxBorderColor: null,
          labelFormatter: function(label, series) {
            // just add some space to labes
            return label + '&nbsp;&nbsp;';
          },
          width: 40,
          height: 1
        },
        colors: chartColours,
        shadowSize: 0,
        tooltip: true, //activate tooltip
        tooltipOpts: {
          content: "%s: %y.0",
          xDateFormat: "%d/%m",
          shifts: {
            x: -30,
            y: -50
          },
          defaultTheme: false
        },
        yaxis: {
          min: 0
        },
        xaxis: {
          mode: "time",
          minTickSize: tickSize,
          timeformat: tformat,
          min: chartMinDate,
          max: chartMaxDate
        }
      };
      plot = $.plot($("#placeholder33x"), [{
        
        data: d1,
        lines: {
          fillColor: "rgba(150, 202, 89, 0.12)"
        }, //#96CA59 rgba(150, 202, 89, 0.42)
        points: {
          fillColor: "#fff"
        }
      }], options);
    });
  </script>
  <!-- /flot -->
  <!-- datepicker -->
  <script type="text/javascript">
    $(document).ready(function() {

      var cb = function(start, end, label) {
        console.log(start.toISOString(), end.toISOString(), label);
        $('#reportrange span').html(start.format('DD/MM/YYYY') + ' - ' + end.format('DD/MM/YYYY'));
        //alert("Callback has fired: [" + start.format('MMMM D, YYYY') + " to " + end.format('MMMM D, YYYY') + ", label = " + label + "]");
      }
	
	  var optionSet1 = {
        startDate: moment().subtract(29, 'days'),
        endDate: moment(),
        minDate: '01/01/2015',
        maxDate: '12/31/<g:formatDate format="yyyy" date="${new Date()}"/>',
        dateLimit: {
          days: 60
        },
        showDropdowns: true,
        showWeekNumbers: true,
        timePicker: false,
        timePickerIncrement: 1,
        timePicker12Hour: true,
        ranges: {
          //'Hoy': [moment(), moment()],
          //'Ayer': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
          'Últimos 7 días': [moment().subtract(6, 'days'), moment()],
          'Últimos 30 días': [moment().subtract(29, 'days'), moment()],
          'Este mes': [moment().startOf('month'), moment().endOf('month')],
          'Mes pasado': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        },
        opens: 'left',
        buttonClasses: ['btn btn-default'],
        applyClass: 'btn-small btn-primary',
        cancelClass: 'btn-small',
        format: 'DD/MM/YYYY',
        separator: ' hasta ',
        locale: {
          applyLabel: 'Aceptar',
          cancelLabel: 'Cancelar',
          fromLabel: 'De:',
          toLabel: 'A:',
          customRangeLabel: 'Rango',
          daysOfWeek: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'],
          monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
          firstDay: 1
        }
      };
      $('#reportrange span').html(moment().subtract(29, 'days').format('DD/MM/YYYY') + ' - ' + moment().format('DD/MM/YYYY'));
      $('#reportrange').daterangepicker(optionSet1, cb);
      $('#reportrange').on('show.daterangepicker', function() {
        console.log("show event fired");
      });
      $('#reportrange').on('hide.daterangepicker', function() {
        console.log("hide event fired");
      });
      $('#reportrange').on('apply.daterangepicker', function(ev, picker) {
		
        $.ajax({
		  type: 'POST',
		  url: "${createLink(controller:'main',action:'changeRange')}",
		  data: { finicio: picker.startDate.format('YYYY-MM-DD'), ffinal: picker.endDate.format('YYYY-MM-DD')},
		  success: function(data) {
             d1 = [];
             for (var item in data ) {
                d1.push([data[item].d, data[item].v]);
            }
			
			var chartMinDate = d1[0][0]; //first day
			var chartMaxDate = d1[d1.length - 1][d1.length - 1]; //last day

			var tickSize = [1, "day"];
			var tformat = "%d/%m/%y";

			//graph options
			options = {
				grid: {
				  show: true,
				  aboveData: true,
				  color: "#3f3f3f",
				  labelMargin: 10,
				  axisMargin: 0,
				  borderWidth: 0,
				  borderColor: null,
				  minBorderMargin: 5,
				  clickable: true,
				  hoverable: true,
				  autoHighlight: true,
				  mouseActiveRadius: 100
				},
				series: {
				  lines: {
					show: true,
					fill: true,
					lineWidth: 2,
					steps: false
				  },
				  points: {
					show: true,
					radius: 4.5,
					symbol: "circle",
					lineWidth: 3.0
				  }
				},
				legend: {
				  position: "ne",
				  margin: [0, -25],
				  noColumns: 0,
				  labelBoxBorderColor: null,
				  labelFormatter: function(label, series) {
					// just add some space to labes
					return label + '&nbsp;&nbsp;';
				  },
				  width: 40,
				  height: 1
				},
				colors: chartColours,
				shadowSize: 0,
				tooltip: true, //activate tooltip
				tooltipOpts: {
				  content: "%s: %y.0",
				  xDateFormat: "%d/%m",
				  shifts: {
					x: -30,
					y: -50
				  },
				  defaultTheme: false
				},
				yaxis: {
				  min: 0
				},
				xaxis: {
				  mode: "time",
				  minTickSize: tickSize,
				  timeformat: tformat,
				  min: chartMinDate,
				  max: chartMaxDate
				}
			  };
            
            plot = $.plot($("#placeholder33x"), [{
        
				data: d1,
				lines: {
				  fillColor: "rgba(150, 202, 89, 0.12)"
				}, //#96CA59 rgba(150, 202, 89, 0.42)
				points: {
				  fillColor: "#fff"
				}
			  }], options);
			plot.draw();
             
          },
          error: function() {
             //console.log("fail");
          },
		  async:true
		});
        
	  });
      $('#reportrange').on('cancel.daterangepicker', function(ev, picker) {
        //console.log("cancel event fired");
      });
    });
  </script>
</g:if>
</sec:ifLoggedIn>
<sec:ifNotLoggedIn>
        <div id="content" role="main" class="background-image-login">
			<div class="login-card form-login-style">
				<div class="login-footer">
					<g:copyright startYear="2017" encodeAs="raw">SambaFresh v<g:meta name="info.app.version"/></g:copyright>
					<g:set var="dateNow" value="${new Date()}" />
					<g:set var="dateShow" value="${Date.parse('yyyy-MM-dd hh:mm:ss', '2018-08-22 0:00:00')}" />
					<g:if test="${dateNow >= dateShow}">
						<a href="http://weykhans.com"><img src="${request.contextPath}/assets/ws_red.png" width="100px" style="float:right" border="0px"></a>
					</g:if>
				</div>
				<g:hasErrors bean="${this.userLG}">
				<ul class="errors" role="alert" style="valign:top">
					<g:eachError bean="${this.userLG}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</g:hasErrors>
				
				<!-- Iniciar sesión -->
				<g:form controller="login" action="authenticate" autocomplete="off" name="login-form" style="display:none;">

					<!-- Logo -->
					<div>
						<asset:image src="samba.png" style="width:300px;"/>
					</div>
					
					<div style="clear:both;"></div>
					
					<div class="title" style="text-align:center;">
						<label>Iniciar sesión</label>
						<span>Comienza a administrar tu sucursal.</span>
					</div>
					
					<!-- Usuario -->
					<div class="group">
						<input  name="username" id="username" type="text" required autocomplete="off" value="${session.getAttribute('user')}">
						<span class="highlight"></span>
						<span class="<g:if test='${flash.message}'>bar red</g:if><g:else>bar green</g:else>" ></span>
						<label <g:if test="${flash.message}">style="color:#f58f48 !important;"</g:if> >
							<i class="material-icons input-ikon">mail_outline</i>
							<span class="span-input">Correo electrónico</span>
						</label>
						<g:if test="${flash.message}">
						<div style="color:#a7a7a7;display:inline-block;position:relative;float:right;">${flash.message}&nbsp;<asset:image src="info_small_red.png"/></div>
						</g:if>
					</div>
					
					<!-- Contraseña -->
					<div class="group">
						<input type="password" value="" name="password" id="password" required autocomplete="off">
						<span class="highlight"></span>
						<span class="<g:if test='${flash.message}'>bar red</g:if><g:else>bar green</g:else>" ></span>
						<label <g:if test="${flash.message}">style="color:#f58f48 !important;"</g:if> >
							<i class="material-icons input-sifre-ikon">lock</i>
							<span class="span-input">Contraseña</span>
						</label>
					</div>
					
					<!-- Boton -->
					<a href="javascript:submit_login();" class="giris-yap-buton">Iniciar sesión</a>
					
					<!-- Enlaces -->
					<div class="forgot-and-create tab-menu">
						<a class="sifre-hatirlat-link" href="javascript:void('sifre-hatirlat-link');">¿Olvidaste tu contraseña?</a>
						<!--
						<a class="hesap-olustur-link" href="javascript:void('hesap-olustur-link');">¡Registrate!</a>
						-->
					</div>
				</g:form>
				
				<!-- Registro -->
				<form id="registro-form" class="col-lg-12" style="display:none;">

					<!-- Logo -->
					<div>
						<asset:image src="samba.png" style="width:300px;"/>
					</div>
					
					<div style="clear:both;"></div>
					
					<div class="title" style="text-align:center;">
						<label>¡Registrate!</label>
						<span>Capture sus datos de registro.</span>
					</div>

					<!-- Email -->
					<div class="group">
						<input type="text" required>
						<span class="highlight"></span>
						<span class="bar green"></span>
						<label><i class="material-icons input-ikon">mail_outline</i><span class="span-input">Correo electrónico</span></label>
					</div>

					<!-- Contraseña -->
					<div class="group">
						<input type="password" required>
						<span class="highlight"></span>
						<span class="bar green"></span>
						<label><i class="material-icons input-sifre-ikon">lock</i><span class="span-input">Contraseña</span></label>
					</div>

					<!-- Contraseña -->
					<div class="group">
						<input type="password" required>
						<span class="highlight"></span>
						<span class="bar green"></span>
						<label><i class="material-icons input-sifre-ikon">lock</i><span class="span-input">Repite la contraseña</span></label>
					</div>

					<!-- Boton -->
					<a href="javascript:void(0);" class="kayit-ol-buton">Registrar usuario</a>

					<!-- Enlaces -->
					<div class="forgot-and-create tab-menu">
						<a class="zaten-hesap-var-link" href="javascript:void('zaten-hesap-var-link');">Iniciar sesión</a>
					</div>
					
				</form>

				<!-- Recuperar contraseña -->
				<g:form controller="login" action="sendPassword" autocomplete="off" name="recupera-form" class="col-lg-12" style="display:none;">
					<!-- Logo -->
					<div>
						<asset:image src="samba.png" style="width:300px;"/>
					</div>
					
					<div style="clear:both;"></div>
					
					<div class="title" style="text-align:center;">
						<label>¿Olvidaste tu contraseña?</label>
						<span>Solicítala a tu correo electrónico.</span>
					</div>
					
					<!-- Email -->
					<div class="group">
						<input name="email" id="email" type="text" required>
						<span class="highlight"></span>
						<span class="bar green"></span>
						<label><i class="material-icons input-ikon">mail_outline</i><span class="span-input">Correo electrónico</span></label>
						
						<g:if test="${params.sendPassword == "true"}">
							<div style="color:#a7a7a7;display:inline-block;position:relative;float:right;">Se ha enviado su contraseña al correo proporcionado&nbsp;<asset:image src="info_small_red.png"/></div>
						</g:if>
						<g:if test="${params.sendPassword == "false"}">
							<div style="color:#a7a7a7;display:inline-block;position:relative;float:right;">El correo proporcionado es inválido&nbsp;<asset:image src="info_small_red.png"/></div>
						</g:if>
					</div>

					<!-- Boton -->
                    <a href="javascript:submit_request();" class="sifre-hatirlat-buton">Solicitar contraseña</a>
                    
					<!-- Enlaces -->
					<div class="forgot-and-create tab-menu">
						<a class="zaten-hesap-var-link" href="javascript:void('zaten-hesap-var-link');">Iniciar sesión</a>
					</div>
				</g:form>
			</div>
		</div>
</sec:ifNotLoggedIn>
    </body>
</html>
