
<%@page import="val.shop.dao.ProductDao"%>
<%@page import="val.shop.model.*"%>
<%@page import="java.util.*"%>
<%@ page import="val.shop.DataBaseConnection.PostgresConnectionToDataBase" %>
<%@ page import="val.shop.dao.CartDao" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
User auth = (User) request.getSession().getAttribute("auth");
//if (auth != null) {
//    request.setAttribute("person", auth);
//	CartDao cartDao = new CartDao(PostgresConnectionToDataBase.getConnection());
//	ArrayList<Cart> cart_list = (ArrayList<Cart>) cartDao.userCart(auth.getId());
//	if (cart_list != null) {
//		request.setAttribute("cart_list", cart_list);
//	}
//}
//ProductDao pd = new ProductDao(PostgresConnectionToDataBase.getConnection());
//List<Product> products = pd.getAllProducts();
//ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/includes/head.jsp"%>
	<style type="text/css" >
		.videofront {display:block;
			/*width: 100%;*/
			/*height: 100%;*/
			/*width:250px;*/
			/*height:197px;*/
				height: 143.9px;
			background-size: cover;

			background-image:url(product-image/tokyo.png);
		}
		a.videofront video {
			visibility:hidden;
		}
		a.videofront:hover {
			background:none;
		}
		a.videofront:hover video {
			visibility:visible;

		}
	</style>

<title>AnWatch</title>
</head>
<body style='background-color:#eaeaea;'>
	<%@include file="/includes/navbar.jsp"%>
           <%@include file="/includes/carousel.jsp"%>

	<div class="container" style="background-color: #eaeaea"  >
		<div class="card-header my-3 text-center" style='background-color:#eaeaea; '><h3>Time to choose!</h3></div>
		<div class="row" >
<%--			<c:set var="c" value="${requestScope.products}"/>--%>
			<c:forEach var="products" items="${products}">
<%--			<%--%>
<%--			if (!products.isEmpty()) {--%>
<%--				for (Product p : products) {--%>
<%--			%>--%>
			<div class="col-md-3 my-3">

				<div class="card w-100" style=" border-bottom-left-radius: 7px; border-bottom-right-radius: 30%; border: 0px">
					<a class="videofront" style="background-image: url('product-image/${products.getImage()}'); " >
					<video  style=" height: auto; background-size: cover;" autoplay="" loop="" muted="" playsinline="" height="180" width="254.9" >
						<source src="${products.getGif()}" type="video/mp4">
					</video></a>
					<div class="card-body" style='background-color:#ffffff; border-bottom-left-radius: 7px; border-bottom-right-radius: 30%'>
						<h6 class="card-title"><c:out value="${products.getName()}"/></h6><br>
<%--						<h6 class="card-title"><%=p.getName() %></h6><br>--%>
<%--						<h7 class="imdb">IMDb <%=p.getImdb() %></h7><br>--%>
						IMDb: <h7 class="imdb"><c:out value="${products.getImdb()}"/></h7><br>
<%--						<h7 class="year">Year <%=p.getYear() %></h7><br>--%>
						Year: <h7 class="year"><c:out value="${products.getYear()}"/></h7><br>
<%--						<h7 class="category">Category: <%=p.getCategory() %></h7>--%>
						Genre: <h7 class="category"><c:out value="${products.getCategory()}"/></h7>
						<div class="mt-3 d-flex justify-content-between" >
							<a class="btn btn-dark" style="border-radius: 15px; background-color: #6184ff; border: 0px; " href="add-to-cart?id=${products.getId()}">See later</a>
							<a class="btn btn-primary" style="border-radius: 15px;background-color:  #dcdcdc; border: 0px;margin-right: 45px "  href="order-now?quantity=1&id=${products.getId()}">Viewed</a>
						</div>
					</div>
				</div>
			</div>
			</c:forEach>
<%--			<%--%>
<%--//			}--%>
<%--			} else {--%>
<%--			System.out.println("There is no proucts");--%>
<%--			}--%>
<%--			%>--%>

		</div>
	</div>

	<%@include file="/includes/footer.jsp"%>
           <%@include file="/includes/html/foot.html"%>


</body>
</html>



