<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="well">
	<form:form id="admin" class="form-horizontal" method="post"
		modelAttribute="employee"
		action="${pageContext.request.contextPath}/admin/employee/save">
		<div class="control-group">
			<label class="control-label" for="id">Employee Id</label>
			<div class="controls">
				<form:input path="id" />
				<form:errors class="text-error" path="id"></form:errors>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="userName">User Name</label>
			<div class="controls">
				<form:input path="userName" />
				<form:errors class="errorMsg" path="userName"></form:errors>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="password">Password</label>
			<div class="controls">
				<form:input path="password" />
				<form:errors class="errorMsg" path="password"></form:errors>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="firstName">First Name</label>
			<div class="controls">
				<form:input path="firstName" />
				<form:errors class="errorMsg" path="firstName"></form:errors>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="lastName">Last Name</label>
			<div class="controls">
				<form:input path="lastName" />
				<form:errors class="errorMsg" path="lastName"></form:errors>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="groupId">Group Id</label>
			<div class="controls">
				<form:input path="groupId" />
				<form:errors class="errorMsg" path="groupId"></form:errors>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<input type="submit" value="Save" />
			</div>
		</div>
	</form:form>
</div>