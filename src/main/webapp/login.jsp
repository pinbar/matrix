<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Welcome</title>
<link
    href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css"
    rel="stylesheet" media="screen">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/css/common.css">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="span4 offset4 well">
                <legend>Please Sign In</legend>
                <c:if test="${not empty param.error}">
                    <div class="text-error">
                        Login failed, try again.<br /> Reason:
                        ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                    </div>
                </c:if>
                <form action="login" method="POST">
                    <input type="text" id="j_username" class="span4"
                        name="j_username" placeholder="Username">
                    <input type="password" id="j_password" class="span4"
                        name="j_password" placeholder="Password">
                    <label class="checkbox"> <input
                        id="j_remember"
                        name="_spring_security_remember_me"
                        type="checkbox" /> Remember Me
                    </label>
                    <button type="submit" name="submit"
                        class="btn btn-info btn-block">Sign in</button>
                </form>
            </div>
        </div>
    </div>

</body>
</html>