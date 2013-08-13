<div class="sidebar-nav visible-lg">
    <ul class="nav nav-list" id="admin-sidebar-nav">
        <jsp:include page="adminSideBarMenu.jsp" />
    </ul>
</div>

<div class="sidebar-nav visible-md">
    <ul class="nav nav-list" id="admin-sidebar-nav">
        <jsp:include page="adminSideBarMenu.jsp" />
    </ul>
</div>

<div class="sidebar-nav visible-sm">
    <ul class="nav nav-list-sm nav navbar-nav" id="admin-sidebar-nav">
        <jsp:include page="adminSideBarMenu.jsp" />
    </ul>
</div>


<!-- different menu styles -->
<!-- 
<div class="sidebar-nav">
	<div class="well">
		<ul class="nav nav-list">
			<li class="nav-header">Admin Menu</li>
			<li class="active"><a
				href="${pageContext.request.contextPath}/admin/"><i
					class="icon-home"></i> Dashboard</a></li>
			<li><a
				href="${pageContext.request.contextPath}/admin/groups/new"><i
					class="icon-edit"></i> Add Group</a></li>
			<li><a
				href="${pageContext.request.contextPath}/admin/groups/tab"><i
					class="icon-user"></i> Group List</a></li>

			<li class="dropdown-submenu"><a tabindex="-1" href="#">Group
					Options</a>
				<ul class="dropdown-menu well"
					style="width: 150px; padding: 8px 0;">
					<li><a href="#"><i class="icon-add"></i>Add Group</a></li>
					<li><a href="#"><i class="icon-delete"></i>Group List</a></li>
				</ul></li>
		</ul>
	</div>
</div>
			 -->
