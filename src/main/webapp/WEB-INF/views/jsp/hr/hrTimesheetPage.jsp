<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
                <jsp:include page="hrTimesheetSideBar.jsp" />
            </div>

            <div class="col-10 well">
                <div id="errorMessages" class="text-error">${error}</div>
                <table class="table table-bordered table-condensed">
                    <thead>
                        <tr>
                            <th>Week Ending</th>
                            <th>Employee</th>
                            <th>Status</th>
                            <th>Hours</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${hrTimesheetList}"
                            var="hrTimesheet">
                            <tr>
                                <td>${hrTimesheet.weekEnding}</td>
                                <td>${hrTimesheet.employeeName}</td>
                                <td>${hrTimesheet.status}</td>
                                <td>${hrTimesheet.hours}</td>
                            </tr>

                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>