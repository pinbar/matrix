<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Welcome</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
    href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css"
    rel="stylesheet" media="screen">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/css/common.css">
<script
    src="${pageContext.request.contextPath}/resources/jquery/js/jquery-1.9.1.min.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/bootstrap/js/respond.js"></script>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-offset-4 well">
                <legend>Please Sign In</legend>
                <c:if test="${not empty param.error}">
                    <div class="error">
                        Login failed, try again.<br /> Reason:
                        ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                    </div>
                    <div class="spacer"></div>
                </c:if>
                <form action="login" method="POST">
                    <div class="form-group">
                        <input type="text" id="j_username"
                            class="form-control" name="j_username"
                            placeholder="Username">
                    </div>
                    <div class="form-group">
                        <input type="password" id="j_password"
                            class="form-control" name="j_password"
                            placeholder="Password">
                    </div>
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