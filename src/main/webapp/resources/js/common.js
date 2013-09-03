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
