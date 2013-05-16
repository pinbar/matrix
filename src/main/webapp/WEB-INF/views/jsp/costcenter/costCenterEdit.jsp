<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="well">
    <form:form id="costCenterForm" class="form-horizontal" method="post"
        modelAttribute="costCenter"
        action="${pageContext.request.contextPath}/admin/costCenter/save">
        <form:hidden path="id" />
        <div class="control-group">
            <label class="control-label" for="costCode">Cost
                Code</label>
            <div class="controls">
                <form:input path="costCode" />
                <form:errors class="text-error" path="costCode"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="name">Cost Center
                Name</label>
            <div class="controls">
                <form:input path="name" />
                <form:errors class="text-error" path="name"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="clientName">Client</label>
            <div class="controls">
                <input id="hiddenClientName" type="hidden"
                    value="${costCenter.clientName}" />
                <form:select path="clientName" class="input-medium">
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