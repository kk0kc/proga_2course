<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/includes/head.jsp"%>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/includes/css/style.css">

<title>AnWatch</title>
</head>
<body style='background-color:#eaeaea;'>
	<%@include file="/includes/navbar.jsp"%>
           <%@include file="/includes/carousel.jsp"%>

	<div class="container" style="background-color: #eaeaea"  >
		<div class="card-header my-3 text-center" style='background-color:#eaeaea; '><h3>Time to choose!</h3></div>
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
							<a class="btn btn-dark" style="border-radius: 15px; background-color: #6184ff; border: 0px; " href="add-to-cart?id=${products.getId()}">See later</a>
							<a class="btn btn-primary" style="border-radius: 15px;background-color:  #dcdcdc; border: 0px;margin-right: 45px "  href="order-now?quantity=1&id=${products.getId()}">Viewed</a>
						</div>
					</div>
				</div>
			</div>
			</c:forEach>
		</div>
	</div>

	<%@include file="/includes/footer.jsp"%>
           <%@include file="/includes/html/foot.html"%>


</body>
</html>



