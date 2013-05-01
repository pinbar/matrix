<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Dashboard</title>
<jsp:include page="commonIncludes.jsp" />
</head>

<body>
    <jsp:include page="header.jsp" />
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span2">
                <jsp:include page="databaseSideBar.jsp" />
            </div>
            <div class="span10">
                <h4>
                    <u>Database Tables</u>
                </h4>
                <div class="span3">
                    <h4>
                        <u>groups</u>
                    </h4>
                    <table
                        class="table table-striped table-bordered table-condensed table-auto">
                        <thead>
                            <tr>
                                <th><u>id</u></th>
                                <th><u>group_name</u></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="group" items="${db.groups}">
                                <tr>
                                    <td>${group.id}</td>
                                    <td>${group.name}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="span3">
                    <h4>
                        <u>group_members</u>
                    </h4>
                    <table
                        class="table table-striped table-bordered table-condensed table-auto">
                        <thead>
                            <tr>
                                <th><u>id</u></th>
                                <th><u>username</u></th>
                                <th><u>group_id</u></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="groupMember"
                                items="${db.groupMembers}">
                                <tr>
                                    <td>${groupMember.id}</td>
                                    <td>${groupMember.userName}</td>
                                    <td>${groupMember.groupId}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="span3">
                    <h4>
                        <u>group_authorities</u>
                    </h4>
                    <table
                        class="table table-striped table-bordered table-condensed table-auto">
                        <thead>
                            <tr>
                                <th><u>group_id</u></th>
                                <th><u>authority</u></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="groupAuthority"
                                items="${db.groupAuthorities}">
                                <tr>
                                    <td>${groupAuthority.groupId}</td>
                                    <td>${groupAuthority.authority}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="span3">
                    <h4>
                        <u>users</u>
                    </h4>
                    <table
                        class="table table-striped table-bordered table-condensed table-auto">
                        <thead>
                            <tr>
                                <th><u>username</u></th>
                                <th><u>password</u></th>
                                <th><u>enabled</u></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="user" items="${db.users}">
                                <tr>
                                    <td>${user.userName}</td>
                                    <td>${user.password}</td>
                                    <td>${user.enabled}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="span3">
                    <h4>
                        <u>employees</u>
                    </h4>
                    <table
                        class="table table-striped table-bordered table-condensed table-auto">
                        <thead>
                            <tr>
                                <th><u>id</u></th>
                                <th><u>first_name</u></th>
                                <th><u>last_name</u></th>
                                <th><u>username</u></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="employee"
                                items="${db.employees}">
                                <tr>
                                    <td>${employee.id}</td>
                                    <td>${employee.firstName}</td>
                                    <td>${employee.lastName}</td>
                                    <td>${employee.userName}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="span3">
                    <h4>
                        <u>timesheets</u>
                    </h4>
                    <table
                        class="table table-striped table-bordered table-condensed table-auto">
                        <thead>
                            <tr>
                                <th><u>id</u></th>
                                <th><u>week_ending</u></th>
                                <th><u>status</u></th>                                
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ts" items="${db.timesheets}">
                                <tr>
                                    <td>${ts.id}</td>
                                    <td>${ts.weekEnding}</td>
                                    <td>${ts.status}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="span3">
                    <h4>
                        <u>timesheet_items</u>
                    </h4>
                    <table
                        class="table table-striped table-bordered table-condensed table-auto">
                        <thead>
                            <tr>
                                <th><u>id</u></th>
                                <th><u>cost_code</u></th>
                                <th><u>item_date</u></th>
                                <th><u>hours</u></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="tsItems"
                                items="${db.timesheetItems}">
                                <tr>
                                    <td>${tsItems.id}</td>
                                    <td>${tsItems.costCode}</td>
                                    <td>${tsItems.date}</td>
                                    <td>${tsItems.hours}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="span3">
                    <h4>
                        <u>cost_center</u>
                    </h4>
                    <table
                        class="table table-striped table-bordered table-condensed table-auto">
                        <thead>
                            <tr>
                                <th><u>cost_code</u></th>
                                <th><u>name</u></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="cc"
                                items="${db.costCenters}">
                                <tr>
                                    <td>${cc.costCode}</td>
                                    <td>${cc.name}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>