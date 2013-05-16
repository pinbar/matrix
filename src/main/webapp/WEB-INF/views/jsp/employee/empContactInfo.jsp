<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="well">
    <form:form id="contactInfoForm" class="form-horizontal"
        method="post" modelAttribute="empContactInfo"
        action="${pageContext.request.contextPath}/usercp/empcontactinfo">

        <legend> Contact Information </legend>

        <form:hidden path="employeeId" />
        <div class="control-group">
            <label class="control-label" for="phone">Phone</label>
            <div class="controls">
                <form:input path="phone" />
                <form:errors class="text-error" path="phone"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="email">Email</label>
            <div class="controls">
                <form:input path="email" />
                <form:errors class="text-error" path="email"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="address">Address</label>
            <div class="controls">
                <form:textarea path="address" />
                <form:errors class="text-error" path="address"></form:errors>
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







