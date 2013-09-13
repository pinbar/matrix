<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../common/commonIncludes.jsp" />
<link
    href="${pageContext.request.contextPath}/resources/datepicker/css/datepicker.css"
    rel="stylesheet" media="screen">
<script
    src="${pageContext.request.contextPath}/resources/jquery/js/jquery.dataTables.min.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/jquery/js/dataTables.fnReloadAjax.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/jquery/js/jquery-ui-1.10.3.custom.min.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/datepicker/js/bootstrap-datepicker.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/js/timesheetLanding.js"></script>
<title>Timesheet</title>
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    <div class="container">
        <div class="col-9 col-sm-10 col-lg-12 border">
          <div class="col-offset-3 col-sm-offset-3 col-lg-offset-5"><h3>Time Sheet Summary</h3></div>
            <div class="horizontalRule"></div>
            <div class="col-5 col-sm-5 col-lg-5 spacer-tall ">
                <jsp:useBean id="today" class="java.util.Date"
                    scope="page" />
                <div class="input-group control-group">
                    <input id="dp" type="text" class="input-sm"
                        value=<fmt:formatDate value="${today}" pattern="MM-dd-yyyy" />
                        data-date-format="mm-dd-yyyy"> <input
                        id="tsCreateBtn" type="button"
                        class="btn btn-info" value="create">
                </div>
            </div>
            <div class="clear"></div>
              <div class="horizontalRule"></div>
               <div class="spacer-tall"></div>
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
             <div class="horizontalRule"></div>
        </div>
    </div>
</body>
</html>