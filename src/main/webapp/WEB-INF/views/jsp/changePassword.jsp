<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

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
            <div class="span2">
                <jsp:include page="landingSideBar.jsp" />
            </div>
            <div class="span9">
                <div class="well">
                    <form:form id="changePasswordForm"
                        class="form-horizontal" method="post"
                        modelAttribute="user"
                        action="${pageContext.request.contextPath}/usercp/password">

                        <legend> Change Password </legend>

                        <form:hidden path="id" />
                        <form:hidden path="userName" />
                        <div class="control-group">
                            <label class="control-label" for="userName">Username</label>
                            <div class="controls">
                                <form:input path="userName"
                                    disabled="true" />
                                <form:errors class="errorMsg"
                                    path="userName"></form:errors>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="password">Old
                                Password </label>
                            <div class="controls">
                                <form:password path="password" />
                                <form:errors class="errorMsg"
                                    path="password"></form:errors>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"
                                for="newPassword1">New Password
                            </label>
                            <div class="controls">
                                <form:password path="newPassword1" />
                                <form:errors class="errorMsg"
                                    path="newPassword1"></form:errors>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"
                                for="newPassword2">New Password
                            </label>
                            <div class="controls">
                                <form:password path="newPassword2" />
                                <form:errors class="errorMsg"
                                    path="newPassword2"></form:errors>
                            </div>
                        </div>
                        <div class="control-group">
                            <div class="controls">
                                <input type="submit" class= "btn" value="Save" />
                                <input type="reset" class= "btn" value="Reset" />
                            </div>
                        </div>
                        <div class="control-group">
                            <div id="errorMessages" class="text-error">${error}</div>
                            <div id="infoMessages" class="text-success">${info}</div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>







