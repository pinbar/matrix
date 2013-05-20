<%@ taglib uri="http://www.springframework.org/security/tags"
    prefix="sec"%>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a class="btn btn-navbar" data-toggle="collapse"
                data-target=".nav-collapse"> <span class="icon-bar"></span>
                <span class="icon-bar"></span> <span class="icon-bar"></span>
            </a> <a class="brand" href="#" name="top">Web Matrix</a>
            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li><a
                        href="${pageContext.request.contextPath}/"><i
                            class="icon-home"></i> Home</a>
                    </li>
                    <li class="divider-vertical"></li>
                    <li><a
                        href="${pageContext.request.contextPath}/timesheet/"><i
                            class="icon-time"></i> Timesheets</a>
                    </li>
                    <li class="divider-vertical"></li>
                    <li><a
                        href="${pageContext.request.contextPath}/database/"><i
                            class="icon-hdd"></i> DB Tables</a>
                    </li>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li class="divider-vertical"></li>
                        <li><a
                            href="${pageContext.request.contextPath}/admin/"><i
                                class="icon-lock"></i> Administration</a>
                        </li>
                    </sec:authorize>
                    <li class="divider-vertical"></li>
                    <li><a
                        href="${pageContext.request.contextPath}/report/"><i
                            class="icon-file"></i> Reports</a>
                    </li>
                    <li class="divider-vertical"></li>
                </ul>
                <div class="btn-group pull-right">
                    <a class="btn dropdown-toggle"
                        data-toggle="dropdown" href="#"> <i
                        class="icon-user"></i> <sec:authentication
                            property="principal.username" /> <span
                        class="caret"></span> </a>
                    <ul class="dropdown-menu">
                        <li><a href="#"><i class="icon-wrench"></i>
                                Preferences</a>
                        </li>
                        <li><a
                            href="${pageContext.request.contextPath}/usercp/changepassword"><i
                                class="icon-pencil"></i> Change Password</a>
                        </li>
                        <li><a
                            href="${pageContext.request.contextPath}/usercp/empcontactinfo"><i
                                class="icon-envelope"></i> Contact
                                Information</a>
                        </li>
                        <li class="divider"></li>
                        <li><a
                            href="${pageContext.request.contextPath}/logout"><i
                                class="icon-share"></i> Logout</a>
                        </li>
                    </ul>
                </div>
            </div>
            <!--/.nav-collapse -->
        </div>
        <!--/.container-fluid -->
    </div>
    <!--/.navbar-inner -->
</div>
<!--/.navbar -->