<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="well">
    <form:form id="changePasswordForm" class="form-horizontal"
        method="post" modelAttribute="changePass"
        action="${pageContext.request.contextPath}/usercp/changepassword">

        <legend> Change Password </legend>

        <form:hidden path="userId" />
        <form:hidden path="userName" />
        <div class="control-group">
            <label class="control-label" for="userName">Username</label>
            <div class="controls">
                <form:input path="userName" disabled="true" />
                <form:errors class="text-error" path="userName"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="password">Old
                Password </label>
            <div class="controls">
                <form:password path="password" />
                <form:errors class="text-error" path="password"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="newPassword1">New
                Password </label>
            <div class="controls">
                <form:password path="newPassword1" />
                <form:errors class="text-error" path="newPassword1"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="newPassword2">New
                Password </label>
            <div class="controls">
                <form:password path="newPassword2" />
                <form:errors class="text-error" path="newPassword2"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <input type="submit" class="btn" value="Save" /> <input
                    type="reset" class="btn" value="Reset" />
            </div>
        </div>
        <div class="control-group">
            <div id="errorMessages" class="text-error">${error}</div>
            <div id="infoMessages" class="text-success">${info}</div>
        </div>
    </form:form>
</div>







