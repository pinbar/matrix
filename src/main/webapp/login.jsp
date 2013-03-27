<!DOCTYPE html>
<html lang="en">
<head>
<title>Welcome</title>
<link
	href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="span4 offset4 well">
				<legend>Please Sign In</legend>
				<form action="login" method="POST">
					<input type="text" id="j_username" class="span4" name="j_username" placeholder="Username"> 
					<input type="password" id="j_password" class="span4" name="j_password" placeholder="Password">
					<label class="checkbox"> <input type="checkbox"
						name="remember" value="1"> Remember Me
					</label>
					<button type="submit" name="submit" class="btn btn-info btn-block">Sign
						in</button>
				</form>
			</div>
		</div>
	</div>

</body>
</html>