<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="well">
    <form:form id="groupForm" class="form-horizontal" method="post"
        modelAttribute="group"
        action="${pageContext.request.contextPath}/admin/group/save">
        <form:hidden path="id" />
        <div class="control-group">
            <label class="control-label" for="name">Group Name</label>
            <div class="controls">
                <form:input path="name" />
                <form:errors class="error" path="name"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="authority">Authority</label>
            <div class="controls">
                <form:input path="authority" />
                <form:errors class="error" path="authority"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <input type="submit" value="Save" />
            </div>
        </div>
    </form:form>
</div>