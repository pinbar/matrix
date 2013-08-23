<%@ taglib uri="http://www.springframework.org/security/tags"
    prefix="sec"%>
<div class="navbar navbar-default" role="navigation">
    <div class="container">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle"
            data-toggle="collapse" data-target=".sidebar-collapse">
            <span class="icon-bar"><span>SideBar</span></span> <span
                class="icon-bar"></span> <span class="icon-bar"></span>
        </button>
        <button type="button" class="navbar-toggle"
            data-toggle="collapse"
            data-target=".navbar-main-collapse">
            <span class="icon-bar"></span> <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">Web Matrix</a>
        </div>
        <div class="navbar-collapse collapse navbar-main-collapse">
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/"><i
                        class="glyphicon glyphicon-home active"></i>
                        Home</a></li>
                <li class="divider-vertical"></li>
                <li><a
                    href="${pageContext.request.contextPath}/timesheet/"><i
                        class="glyphicon glyphicon-time"></i> Timesheets</a></li>
                <li class="divider-vertical"></li>
                <li><a
                    href="${pageContext.request.contextPath}/hr/timesheets"><i
                        class="glyphicon glyphicon-lock"></i> HR
                        Timesheets</a></li>
                <li class="visible-md"><a
                    href="${pageContext.request.contextPath}/database/"><i
                        class="glyphicon glyphicon-hdd"></i> DB Tables</a></li>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li class="divider-vertical"></li>
                    <li><a
                        href="${pageContext.request.contextPath}/admin/"><i
                            class="glyphicon glyphicon-lock"></i>
                            Administration</a></li>
                </sec:authorize>
                <li class="divider-vertical"></li>
                <li><a
                    href="${pageContext.request.contextPath}/report/"><i
                        class="glyphicon glyphicon-file"></i> Reports</a></li>
                <li class="divider-vertical"></li>
            </ul>
            <div class="btn-group pull-right">
                <a class="btn dropdown-toggle" data-toggle="dropdown"
                    href="#"> <i class="glyphicon glyphicon-user"></i>
                    <sec:authentication property="principal.username" />
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="#"><i class="icon-wrench"></i>
                            Preferences</a></li>
                    <li><a
                        href="${pageContext.request.contextPath}/usercp/changepassword"><i
                            class="glyphicon glyphicon-pencil"></i>
                            Change Password</a></li>
                    <li><a
                        href="${pageContext.request.contextPath}/usercp/empcontactinfo"><i
                            class="glyphicon glyphicon-envelope"></i>
                            Contact Information</a></li>
                    <li class="divider"></li>
                    <li><a
                        href="${pageContext.request.contextPath}/logout"><i
                            class="glyphicon glyphicon-share"></i>
                            Logout</a></li>
                </ul>
            </div>
        </div>
        <!--/.nav-collapse -->
    </div>
    <!--/.container -->
</div>
<!--/.navbar -->