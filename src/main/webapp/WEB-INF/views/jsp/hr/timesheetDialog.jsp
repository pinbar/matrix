<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Modal dialog  -->
<div class="modal fade" id="timesheet">

    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                    aria-hidden="true">&times;</button>
                <h4 class="modal-title">Modal title</h4>
                <!-- TODO change the title in js -->
            </div>
            <div class="modal-body">
                <!--  <jsp:include
                        page="../timesheet/timesheetContent.jsp" /> -->
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                    data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save
                    changes</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->