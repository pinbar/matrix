<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>

<html>
<head>
<title>Dashboard</title>
<jsp:include page="../common/commonIncludes.jsp" />
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/jQuery-File-Upload/css/jquery.fileupload-ui.css">
</head>

<body>
    <jsp:include page="../common/header.jsp" />
    <div class="container">
        <div class="row">
            <div
                class="col-4 col-sm-3 col-lg-2 navbar-collapse collapse sidebar-collapse">
                <jsp:include page="timesheetSideBar.jsp" />
            </div>

            <div id="timesheetContent" class="col-8 col-sm-9 col-lg-10">
                <jsp:include page="timesheetContent.jsp" />
            </div>
        </div>
     </div>   
</body>
</html>