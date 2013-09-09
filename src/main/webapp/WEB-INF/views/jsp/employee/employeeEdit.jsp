<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="well">
    <form:form id="employeeForm" class="form" method="post"
        modelAttribute="employee"
        action="${pageContext.request.contextPath}/admin/employee/save">
        <form:hidden path="id" />
        <div id="errorMessages" class="text-error"></div>
        <div class="control-group">
            <label class="control-label" for="userName">User
                Name</label>
            <div class="controls">
                <form:input class="form-control" path="userName" />
                <form:errors class="text-error" path="userName"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="firstName">First
                Name</label>
            <div class="controls">
                <form:input class="form-control" path="firstName" />
                <form:errors class="text-error" path="firstName"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="lastName">Last
                Name</label>
            <div class="controls">
                <form:input class="form-control" path="lastName" />
                <form:errors class="text-error" path="lastName"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="phone">Phone</label>
            <div class="controls">
                <form:input class="form-control" path="phone" />
                <form:errors class="text-error" path="phone"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="email">Email</label>
            <div class="controls">
                <form:input class="form-control" path="email" />
                <form:errors class="text-error" path="email"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="address">Address</label>
            <div class="controls">
                <form:textarea class="form-control" path="address" />
                <form:errors class="text-error" path="address"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="groupName">Group</label>
            <div class="controls">
                <input id="hiddenGroupName" type="hidden"
                    value="${employee.groupName}" />
                <form:select path="groupName" class="input-large">
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="managerId">Manager Id</label>
            <div class="controls">
                <form:input class="form-control" path="managerId" />
                <form:errors class="text-error" path="managerId"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="managerName">Manager Name</label>
            <div class="controls">
                <form:input class="form-control" path="managerName" />
                <form:errors class="text-error" path="managerName"></form:errors>
            </div>
        </div>

        <input id="costCodes" name="costCodes" type="hidden"
            value="${employee.costCodesStr}" />
        <div class="spacer"></div>
        <div id="projects" class="control-group"></div>
        <div class="spacer"></div>
        <div class="control-group">
            <div class="controls">
                <input id="employeeSave" type="submit"
                    class="btn btn-danger" value="Save" />
            </div>
        </div>
    </form:form>

</div>