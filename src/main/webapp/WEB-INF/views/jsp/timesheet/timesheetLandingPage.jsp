<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../common/commonIncludes.jsp" />
<script
    src="${pageContext.request.contextPath}/resources/jquery/js/jquery.dataTables.min.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/jquery/js/dataTables.fnReloadAjax.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/jquery/js/jquery-ui-1.10.3.custom.min.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/js/timesheetLanding.js"></script>
<title>Timesheet</title>
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    <div class="container">
            <div class="col-9 col-sm-10 col-lg-12 border">
                <table id="timesheetSummaryTable"
                    class="table table-bordered table-condensed table-striped  rt">
                    <thead>
                        <tr>
                            <th>TimeSheet Id</th>
                            <th>Week Ending</th>
                            <th>Status</th>
                            <th>Hours</th>
                            <th>Edit</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <div class="clear"></div>
            </div>
        </div>
</body>
</html>