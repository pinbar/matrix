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
            <div class="col-2">
                <jsp:include page="timesheetSideBar.jsp" />
            </div>

         <div class="col-10">
           <div id="errorMessages" class="error">${error}</div>
              
            <jsp:include page="timesheetContent.jsp" />  
        </div>
    </div>

    </body>
</html>