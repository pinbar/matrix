<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Dashboard</title>
<jsp:include page="../common/commonIncludes.jsp" />
</head>

<body>
    <jsp:include page="../common/header.jsp" />
    <div class="container">
        <div class="row">
            <div class="col-2">
                <jsp:include page="reportSideBar.jsp" />
            </div>
            <div class="col-10 well">
                <div class="span3">
                    <jsp:include page="../client/clientList.jsp" />
                </div>
            </div>
        </div>
    </div>
</body>
</html>