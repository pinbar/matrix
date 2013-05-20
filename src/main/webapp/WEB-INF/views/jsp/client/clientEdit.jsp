<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="well">
    <form:form id="clientForm" class="form-horizontal" method="post"
        modelAttribute="client"
        action="${pageContext.request.contextPath}/admin/client/save">
        <form:hidden path="id" />
        <div class="control-group">
            <label class="control-label" for="name">Name</label>
            <div class="controls">
                <form:input path="name" />
                <form:errors class="text-error" path="name"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="name">Phone</label>
            <div class="controls">
                <form:input path="phone" />
                <form:errors class="text-error" path="phone"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="name">Email</label>
            <div class="controls">
                <form:input path="email" />
                <form:errors class="text-error" path="email"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="name">Address</label>
            <div class="controls">
                <form:textarea path="address" />
                <form:errors class="text-error" path="address"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="name">Primary
                Contact</label>
            <div class="controls">
                <form:textarea path="primaryContact" />
                <form:errors class="text-error" path="primaryContact"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <input type="submit" value="Save" />
            </div>
        </div>
    </form:form>
</div>