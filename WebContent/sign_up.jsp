<%@ page contentType="text/html;charset=utf-8" %>
<!doctype html>
<html lang="en">
  <head>
	  <%@include file="/includes/head.jsp"%>
  	<title>Login 10</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<link href="https://fonts.googleapis.com/css?family=Lato:300,400,700&display=swap" rel="stylesheet">

	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/includes/css/sign-up.css">

	</head>
	<body class="img js-fullheight" style="background-image: url(includes/product-image/sign_up.jpg);">
	<%@include file="/includes/navbar.jsp"%>
	<section class="ftco-section">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-6 col-lg-4">
					<div class="login-wrap p-0">
		      	<h3 class="mb-4 text-center">Create account</h3>
		      	<form method="post" class="signin-form">
		      		<div class="form-group">
		      			<input type="text" name="name" class="form-control" placeholder="Username" required>
		      		</div>
					<div class="form-group">
						<input type="email" name="email" class="form-control" placeholder="Email" required>
					</div>
	            <div class="form-group" >
					<input id="password-field" title="Пароль должен содержать минимум 6 знаков, включая нижний/верхний регистр и цифры" type="password"
						   class="form-control" placeholder="Password"
						   required pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}" name="password" onchange="
                  this.setCustomValidity(this.validity.patternMismatch ? this.title : '');">
	              <span toggle="#password-field" class="fa fa-fw fa-eye field-icon toggle-password"></span>
	            </div>
	            <div class="form-group">
	            	<button type="submit" class="form-control btn btn-primary submit px-3">Sign In</button>
	            </div>
	          </form>
		      </div>
				</div>
			</div>
		</div>
	</section>

	<script src="${pageContext.request.contextPath}/includes/js/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/includes/js/popper.js"></script>
  <script src="${pageContext.request.contextPath}/includes/js/bootstrap.min.js"></script>
  <script src="${pageContext.request.contextPath}/includes/js/main.js"></script>

	</body>
</html>

