<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="well">
    <form:form id="changePasswordForm" class="form-horizontal"
        method="post" modelAttribute="changePass"
        action="${pageContext.request.contextPath}/admin/employee/resetpassword">
        <legend> Change Password </legend>
        <div class="control-group">
            <label class="control-label" for="userName">Username</label>
            <div class="controls">
                <form:hidden path="userName"/>
                <form:input path="userName" disabled="true" />
                <form:errors class="text-error" path="userName"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="password">Password </label>
            <div class="controls">
                <form:password path="password" />
                <form:errors class="text-error" path="password"></form:errors>
            </div>
        </div>
            <div class="control-group">
            <div class="controls">
                <input type="submit" class="btn" value="Save" /> 
            </div>
        </div>
        <div class="control-group">
            <div id="errorMessages" class="text-error">${error}</div>
            <div id="infoMessages" class="text-success">${info}</div>
        </div>
    </form:form>
</div>







