<div>
    <div class="sidebar-nav visible-lg">
        <ul class="nav nav-list">
            <jsp:include page="timesheetPreview.jsp"></jsp:include>
        </ul>
    </div>

    <div class="sidebar-nav visible-md">
        <ul class="nav nav-list">
            <jsp:include page="timesheetPreview.jsp"></jsp:include>
        </ul>
    </div>

    <div class="sidebar-nav visible-sm">
        <ul class="nav nav-list-sm navbar-nav">
            <jsp:include page="timesheetPreview.jsp"></jsp:include>
        </ul>
    </div>


    <div class="input-group form-control">
        <input id="dp" type="text" class="input-sm"
            value="01-01-2013" data-date-format="mm-dd-yyyy"><input
            id="tsCreateBtn" type="button" class="btn btn-info"
            value="create">
    </div>
</div>
