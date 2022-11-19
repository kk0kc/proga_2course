<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/includes/head.jsp"%>
<title>Shopping Cart</title>
</head>
<body style='background-color:#FFF6FA;'>
	<%@include file="/includes/navbar.jsp"%>

	<div class="container">
		<div class="card w-50 mx-auto my-5">
			<div class="card-header text-center bg-danger text-white">User Login</div>
			<div class="card-body" style='background-color:#FFDCDC;'>
				<form action="user-login" method="post">
					<div class="form-group">
						<label>Email </label> 
						<input type="email" name="login-email" class="form-control" placeholder="Enter email" style='background-color:#FFF6FA;'>
					</div>
					<div class="form-group">
						<label>Password</label> 
						<input type="password" name="login-password" class="form-control" placeholder="Enter password" style='background-color:#FFF6FA;'>
					</div>
					<div class="text-center">
						<button type="submit" class="btn btn-danger">Login</button>
						<a href="/sign-up" class="btn btn-danger">SignUp</a>
					</div>
				</form>
			</div>
		</div>
	</div>
           <br>
           <br>
           <br>
	<%@include file="/includes/footer.jsp"%>
	<%@include file="/includes/html/foot.html"%>
</body>
</html>