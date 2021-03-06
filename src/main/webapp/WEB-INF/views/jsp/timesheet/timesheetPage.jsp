<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Dashboard</title>
<jsp:include page="../common/commonIncludes.jsp" />
<jsp:include page="../common/attachmentIncludes.jsp" />
<script
    src="${pageContext.request.contextPath}/resources/js/timesheet.js"></script>
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    <div class="container">
        <div class="row">
            <div
                class="col-4 col-sm-3 col-lg-3 navbar-collapse collapse sidebar-collapse">
                <jsp:include page="timesheetSideBar.jsp" />
                <br />
            </div>

            <div id="timesheetContent" class="col-8 col-sm-9 col-lg-9 border">
                <jsp:include page="timesheetContent.jsp" />
            </div>
        </div>
    </div>
</body>
</html>