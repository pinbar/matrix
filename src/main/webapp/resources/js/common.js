commonUtils= function(){
   my={}; 
   my.formatTwoDecimals= function(input){
       var i = parseFloat(input);
       if(isNaN(i)) { i = 0.00; }
       var minus = '';
       if(i < 0) { minus = '-'; }
       i = Math.abs(i);
       i = parseInt((i + .005) * 100);
       i = i / 100;
       var s = new String(i);
       if(s.indexOf('.') < 0) { s += '.00'; }
       if(s.indexOf('.') == (s.length - 2)) { s += '0'; }
       s = minus + s;
       return s;
   } ;
   return my ;
}();


$(document).ready(
        function() {
            $("#admin-sidebar-nav > li > a[data-target]").parent('li').on(
                    'click',
                    function() {
                        var target = $(this).children('a[data-target]').data(
                                'target');
                        $('.expanded').each(function(index, node) {
                            $(node).collapse("hide");
                            $(node).removeClass('expanded');
                        });
                        $(target).toggleClass("expanded");
                        if ($(target).hasClass("expanded")) {
                            $(target).collapse('show');
                            $(target).removeClass('collapse');
                        } else {
                            $(target).collapse('hide');
                        }
                    });
        });
