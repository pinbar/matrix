<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Timesheet</title>
<jsp:include page="../common/commonIncludes.jsp" />
<jsp:include page="../common/attachmentIncludes.jsp" />
<script
    src="${pageContext.request.contextPath}/resources/js/timesheet.js"></script>

</head>
<body>
    <jsp:include page="../common/header.jsp" />
    <div class="container">
        <div class="row">
            <div id="timesheetContent"
                class="col-8 col-sm-12 col-lg-12 border">
                <jsp:include page="timesheetContent.jsp" />
            </div>
        </div>
    </div>
</body>
</html>