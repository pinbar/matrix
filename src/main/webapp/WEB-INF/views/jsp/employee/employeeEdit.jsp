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
                <form:errors class="text-error" path="userName"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="firstName">First
                Name</label>
            <div class="controls">
                <form:input path="firstName" />
                <form:errors class="text-error" path="firstName"></form:errors>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="lastName">Last
                Name</label>
            <div class="controls">
                <form:input path="lastName" />
                <form:errors class="text-error" path="lastName"></form:errors>
            </div>
        </div>
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
                <input type="button" class="btn btn-danger" id ="projectsToggle"
                value="Show/Hide Projects">
            </div>
        </div>
        <div id="projects" class="collapse control-group">
            <div class="control-group">
<<<<<<< HEAD:src/main/webapp/WEB-INF/views/jsp/employee/employeeEdit.jsp
                <input type="button" id ="addProjectRow" class="btn btn-danger"
                    value="Add Project Row">
                <input type="button" id ="saveTable" class="btn btn-danger"
                    value="Save Table">
                    
                    <input type="button" id ="fillDummyData" class="btn btn-danger"
                    value="fillDummyData"> 
            </div>
            <table id ="empCostCodeListTable" class="table table-bordered table-condensed">
=======
                <input type="button" class="btn btn-danger"
                    value="Add Project Row">
            </div>
            <table class="table table-bordered table-condensed">
>>>>>>> ab41f415a9740cc7d604edbfde2dde2b679e2141:src/main/webapp/WEB-INF/views/jsp/employee/employeeEdit.jsp
                <thead>
                    <tr>
                        <th>Cost Code</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th></th>
<<<<<<< HEAD:src/main/webapp/WEB-INF/views/jsp/employee/employeeEdit.jsp
                        <th></th>
                    </tr>
                </thead>
                <tbody>
=======
                    </tr>
                </thead>
                <tbody>
                 <tr>
                 <td>
                 <select class ="costCode "></select>
                 </td>
                 <td></td>
                 <td></td>
                 </tr>
                  <tr>
                 <td>
                 <select class ="costCode"></select>
                 </td>
                 <td></td>
                 <td></td>
                 </tr>
>>>>>>> ab41f415a9740cc7d604edbfde2dde2b679e2141:src/main/webapp/WEB-INF/views/jsp/employee/employeeEdit.jsp
                </tbody>
            </table>
        </div>

    </form:form>
</div>