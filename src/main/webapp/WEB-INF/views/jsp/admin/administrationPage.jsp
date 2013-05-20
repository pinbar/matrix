<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Administration</title>
<jsp:include page="../common/commonIncludes.jsp" />
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    <div class="container-fluid">
        <div class="row-fluid">
            <div id="sidebar" class="span2">
                <jsp:include page="adminSideBar.jsp" />
            </div>
            <div id="content" class="span10">
                <div id="adminMsgs"
                    class="${form !='admin' ? 'hide' : ''}">
                    <ul>
                        <li>Use the sidebar menu to manage users
                            and groups.</li>
                        <li>Hover over the headings to see
                            sub-menu.</li>
                        <li>Only 'Group/Employee List' and 'Add
                            Group/Employee' menu items work at this
                            time.</li>
                        <li>Groups can be edited or deleted from
                            the 'Group List' page (existing groups
                            cannot be deleted).</li>
                    </ul>
                </div>
                <div id="empList"
                    class="${form != 'employeeList' ? 'hide' : ''}">
                    <jsp:include page="../employee/employeeList.jsp" />
                </div>
                <div id="empUpdate"
                    class="${form != 'employeeEdit' ? 'hide' : ''}">
                    <jsp:include page="../employee/employeeEdit.jsp" />
                </div>
                <div id="empResetPassword"
                    class="${form != 'empPassReset' ? 'hide' : ''}">
                    <jsp:include page="adminChangePassword.jsp" />
                </div>
                <div id="grpList"
                    class="${form != 'groupList' ? 'hide' : ''}">
                    <jsp:include page="../group/groupList.jsp" />
                </div>
                <div id="grpUpdate"
                    class="${form != 'groupEdit' ? 'hide' : ''}">
                    <jsp:include page="../group/groupEdit.jsp" />
                </div>
                <div id="costCenterList"
                    class="${form != 'costCenterList' ? 'hide' : ''}">
                    <jsp:include page="../costcenter/costCenterList.jsp" />
                </div>
                <div id="costCenterUpdate"
                    class="${form != 'costCenterEdit' ? 'hide' : ''}">
                    <jsp:include page="../costcenter/costCenterEdit.jsp" />
                </div>
                <div id="clientList"
                    class="${form != 'clientList' ? 'hide' : ''}">
                    <jsp:include page="../client/clientList.jsp" />
                </div>
                <div id="clientUpdate"
                    class="${form != 'clientUpdate' ? 'hide' : ''}">
                    <jsp:include page="../client/clientEdit.jsp" />
                </div>

            </div>
        </div>
    </div>
    <script
        src="${pageContext.request.contextPath}/resources/js/adminSidebarController.js"></script>
    <script
        src="${pageContext.request.contextPath}/resources/js/employee.js"></script>
    <script
        src="${pageContext.request.contextPath}/resources/js/admin.js"></script>
    <script
        src="${pageContext.request.contextPath}/resources/jquery/js/jquery.dataTables.min.js"></script>
    <script
        src="${pageContext.request.contextPath}/resources/jquery/js/dataTables.fnReloadAjax.js"></script>
</body>
</html>