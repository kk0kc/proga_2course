<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="/includes/head.jsp"%>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/includes/css/style.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/includes/css/admin-page.css">
<title>AnWatch</title>
</head>
<body style='background-color:#eaeaea;'>
	<%@include file="/includes/navbarAdmin.jsp"%>
           <%@include file="/includes/carousel.jsp"%>

	<div class="container" style="background-color: #eaeaea"  >
		<div class="card-header my-3 text-center" style='background-color:#eaeaea; '><h3>ADMIN PAGE</h3></div>
		<div class="row" >
			<c:forEach var="products" items="${products}">
			<div class="col-md-3 my-3">

				<div class="card w-100" style=" border-bottom-left-radius: 7px; border-bottom-right-radius: 30%; border: 0px">
					<a class="videofront" style="background-image: url('includes/product-image/${products.getImage()}'); " >
					<video  style=" height: auto; background-size: cover;" autoplay="" loop="" muted="" playsinline="" height="180" width="254.9" >
						<source src="${products.getGif()}" type="video/mp4">
					</video></a>
					<div class="card-body" style='background-color:#ffffff; border-bottom-left-radius: 7px; border-bottom-right-radius: 30%'>
						<h6 class="card-title"><c:out value="${products.getName()}"/></h6><br>
						IMDb: <h7 class="imdb"><c:out value="${products.getImdb()}"/></h7><br>
						Year: <h7 class="year"><c:out value="${products.getYear()}"/></h7><br>
						Genre: <h7 class="category"><c:out value="${products.getCategory()}"/></h7>
						<div class="mt-3 d-flex justify-content-between" >
							<a class="btn btn-primary" style="border-radius: 15px;background-color:  #dcdcdc; border: 0px;margin-right: 45px "  href="remove-admin?id=${products.getId()}">Remove</a>
						</div>
					</div>
				</div>
			</div>
			</c:forEach>
			<button class="open-button" onclick="openForm()">Add new</button>


			<div class="form-popup" id="myForm">
				<form action="${pageContext.request.contextPath}/add-product" class="form-container" enctype="multipart/form-data" method="post">
					<center><h2>Add new</h2></center>

					<input type="text" placeholder="Name" name="name" required>
					<input type="text" placeholder="Category" name="category" required>
					<input type="text" placeholder="Year" name="year" required>
					<input type="text" placeholder="IMDb" name="imdb" required>
					   Image: <input style="border-radius: 0px; margin: 10px" type="file" placeholder="Image" name="file" accept="image/jpg, image/png" required>
					<input type="text" placeholder="Gif/video URL" name="gif" required>

					<button type="submit" class="btn">Add</button>
					<button type="button" class="btn cancel" onclick="closeForm()">Close</button>
				</form>
			</div>
		</div>
	</div>
	<%@include file="/includes/footer.jsp"%>
	<%@include file="/includes/html/foot.html"%>
	<script src="${pageContext.request.contextPath}/includes/js/admin-page.js"></script>
</body>
</html>


