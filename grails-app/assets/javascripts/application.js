// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require jquery.alphanum
//= require jquery.mask
//= require jquery.limitkeypress
//= require bootstrap/bootstrap.min
//= require switchery/switchery.min
//= require build/custom.min
//= require sweetalert2.min
//= require jquery.print
//= require jquery-ui.min
//= require select2.min
//= require progressbar/bootstrap-progressbar.min
//= require icheck/icheck.min
//= require moment/moment.min
//= require datepicker/daterangepicker
//= require chartjs/chart.min
//= require sparkline/jquery.sparkline.min
//= require flot/jquery.flot
//= require flot/jquery.flot.pie
//= require flot/jquery.flot.orderBars
//= require flot/jquery.flot.time.min
//= require flot/date
//= require flot/jquery.flot.spline
//= require flot/jquery.flot.stack
//= require flot/curvedLines
//= require flot/jquery.flot.resize
//= require pace/pace.min

if (typeof jQuery !== 'undefined') {
    (function($) {
        $('#spinner').ajaxStart(function() {
            $(this).fadeIn();
        }).ajaxStop(function() {
            $(this).fadeOut();
        });
    })(jQuery);
}
