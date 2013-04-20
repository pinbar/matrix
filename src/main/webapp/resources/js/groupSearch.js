$(function() {
    $("select").selectBoxIt().data("selectBoxIt");
    $("select#id").bind({
        "changed" : function(e) {
            doSelectChange(e, "id");
        }
    });
    $("select#name").bind({
        "changed" : function(e) {
            doSelectChange(e, "name");
        }
    });
    $("select#authority").bind({
        "changed" : function(e) {
            doSelectChange(e, "authority");
        }
    });

    function doSelectChange(e, id) {
        alert(e.target.value)
        $.ajax({
            url : contextPath + "/admin/group/search",
            type : "post",
            data : {
                id : e.target.value
            },
            success : function(response, textStatus, jqXHR) {
                alert("success");
            },
            error : function(response, textStatus, jqXHR) {
                alert("error");
            }
        });
        event.preventDefault();
    }

});