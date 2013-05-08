<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="well">
    <form:form id="employeeForm" class="form-horizontal" method="post"
        modelAttribute="employee"
        action="${pageContext.request.contextPath}/admin/employee/save">
        <form:hidden path="id" />
        <div class="control-group">
            <label class="control-label" for="userName">User
                Name</label>
            <div class="controls">
                <form:input path="userName" />
                <form:errors class="errorMsg" path="userName"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="password">Password</label>
            <div class="controls">
                <form:password path="password" />
                <form:errors class="errorMsg" path="password"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="firstName">First
                Name</label>
            <div class="controls">
                <form:input path="firstName" />
                <form:errors class="errorMsg" path="firstName"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="lastName">Last
                Name</label>
            <div class="controls">
                <form:input path="lastName" />
                <form:errors class="errorMsg" path="lastName"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="phone">Phone</label>
            <div class="controls">
                <form:input path="phone" />
                <form:errors class="errorMsg" path="phone"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="email">Email</label>
            <div class="controls">
                <form:input path="email" />
                <form:errors class="errorMsg" path="email"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="address">Address</label>
            <div class="controls">
                <form:textarea path="address" />
                <form:errors class="errorMsg" path="address"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="groupName">Group</label>
            <div class="controls">
                <input id="hiddenGroupName" type="hidden"
                    value="${employee.groupName}" />
                <form:select path="groupName" class="input-medium">
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <input type="submit" value="Save" />
            </div>
        </div>
    </form:form>
</div>