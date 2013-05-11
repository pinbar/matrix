<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Change Password</title>
<jsp:include page="commonIncludes.jsp" />
</head>
<body>
    <jsp:include page="header.jsp" />
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span3">
                <jsp:include page="userCPSideBar.jsp" />
            </div>
            <div class="span8">
                <c:if test="${changePass != null}">
                    <jsp:include page="changePassword.jsp" />
                </c:if>
                <c:if test="${empContactInfo != null}">
                    <jsp:include page="empContactInfo.jsp" />
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>







