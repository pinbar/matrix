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
				<jsp:include page="reportSideBar.jsp" />
			</div>
			<div class="span10 well">			
				<div class="span3">
					<jsp:include page="clientList.jsp" />
				</div>
			</div>
		</div>
	</div>
</body>
</html>