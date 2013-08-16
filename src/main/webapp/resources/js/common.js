$(document).ready(
        function() {
            /*
             * side nav bar
             */
            /*
             * if (!("ontouchstart" in document.documentElement)) {
             * $("#admin-sidebar-nav > li > a[data-target]").parent('li')
             * .hover( function() { target = $(this).children('a[data-target]')
             * .data('target'); $(target).collapse('show'); }, function() {
             * target = $(this).children('a[data-target]') .data('target');
             * $(target).collapse('hide'); }); } else {
             */
            $("#admin-sidebar-nav > li > a[data-target]").parent('li').click(
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
            // }
            /* side nav bar end */

        });
