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
            <div class="col-sm-4 col-lg-3 col-3 navbar-collapse collapse sidebar-collapse">
                <jsp:include page="reportSideBar.jsp" />
            </div>
            <div class="col-sm-8 col-lg-9 col-9">
                    <jsp:include page="../client/clientList.jsp" />
            </div>
        </div>
    </div>
</body>
</html>