<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Change Password</title>
<jsp:include page="../common/commonIncludes.jsp" />
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    <div class="container">
        <div class="row">
            <div class="col-3">
                <jsp:include page="userCPSideBar.jsp" />
            </div>
            <div class="col-9">
                <c:if test="${changePass != null}">
                    <jsp:include page="changePassword.jsp" />
                </c:if>
                <c:if test="${empContactInfo != null}">
                    <jsp:include page="../employee/empContactInfo.jsp" />
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>







