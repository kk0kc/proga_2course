
<%@page import="val.shop.dao.ProductDao"%>
<%@page import="val.shop.model.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page import="val.shop.DataBaseConnection.PostgresConnectionToDataBase" %>
<%@ page import="val.shop.dao.CartDao" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
DecimalFormat dcf = new DecimalFormat("#.##");
request.setAttribute("dcf", dcf);
User auth = (User) request.getSession().getAttribute("auth");
List<Cart> cartProduct = null;
ArrayList<Cart> cart_list = null;
if (auth != null) {
    request.setAttribute("person", auth);
	CartDao cartDao = new CartDao(PostgresConnectionToDataBase.getConnection());
	cartProduct = cartDao.userCart(auth.getId());
	cart_list = (ArrayList<Cart>) cartDao.userCart(auth.getId());
} else {
	response.sendRedirect("login.jsp");
}

//ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
if (cart_list != null) {
	ProductDao pDao = new ProductDao(PostgresConnectionToDataBase.getConnection());
	cartProduct = pDao.getCartProducts(cart_list);
	double total = pDao.getTotalCartPrice(cart_list);
	request.setAttribute("total", total);
	request.setAttribute("cart_list", cart_list);
}
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/includes/head.jsp"%>
<title>Shopping Cart</title>
<style type="text/css">

.table tbody td{
	border-width: 15px;
	border-color: #eaeaea;
vertical-align: middle;
}
.table thead th{
	border-width: 0px;
}
.btn-incre, .btn-decre{
box-shadow: none;
font-size: 25px;
}
</style>
	<style type="text/css" >
		.videofront {display:block;
			width:260px;
			height:146px;
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
			border:0;
		}
	</style>
</head>
<body style='background-color:#eaeaea;'>
	<%@include file="/includes/navbar.jsp"%>

	<div class="container my-3">
		<div class="d-flex py-3">
<%--			<h3>Total Price: $ ${(total>0)?dcf.format(total):0}</h3>--%>
			<a class="mx-3 btn btn-danger" href="cart-check-out">Check Out</a></div>
		<table class="table table-hover" style='background-color:#ffffff;'>
			<thead >
				<tr>
					<th style="border-width: 0px" scope="col"></th>
					<th scope="col">Name</th>
					<th scope="col">Category</th>
					<th scope="col">Year</th>
					<th scope="col">IMDb</th>
					<th scope="col"></th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody  >
				<%
				if (cart_list != null) {
					for (Cart c : cartProduct) {
				%>
				<tr>
<%--					<td><img class="card-img-top" src="product-image/<%=c.getImage()%>"--%>
<%--							 alt="Card image cap"></td>--%>
<%--					<td><div class="movies-trailers">--%>
						<td><a class="videofront" style="background-image: url('product-image/<%=c.getImage()%>'); ">
							<video style=" height: auto; background-size: cover;" autoplay="" loop="" muted="" playsinline="" height="107" width="260" >
								<source src="<%=c.getGif() %>" type="video/mp4">
							</video></a>
						</td>
<%--						<video autoplay="" loop="" muted="" playsinline="">--%>
<%--							<source src="https://media.giphy.com/media/P1xNu32vTu89wA2FdG/source.mp4" type="video/mp4">--%>
<%--						</video>--%>
					</div>
<%--					</td>--%>
					<td><%=c.getName()%></td>
					<td><%=c.getCategory()%></td>
					<td><%=c.getYear()%></td>
					<td><%= dcf.format(c.getImdb())%></td>
					<td>
						<form action="order-now" method="post" class="form-inline">
						<input type="hidden" name="id" value="<%= c.getId()%>" class="form-input">
<%--							<div class="form-group d-flex justify-content-between">--%>
<%--								<a class="btn bnt-sm btn-incre" href="quantity-inc-dec?action=inc&id=<%=c.getId()%>"><i class="fas fa-plus-square"></i></a>--%>
<%--								<input type="text" name="quantity" class="form-control"  value="<%=c.getQuantity()%>" readonly style='background-color:#FFF6FA;'> --%>
<%--								<a class="btn btn-sm btn-decre" href="quantity-inc-dec?action=dec&id=<%=c.getId()%>"><i class="fas fa-minus-square"></i></a>--%>
<%--							</div>--%>
							<button type="submit" style=" height:35px; width: 90px; border-radius: 15px;background-color:  #6184ff; border: 0px " class="btn btn-primary btn-sm">Viewed</button>
						</form>
					</td>
					<td><a href="remove-from-cart?id=<%=c.getId() %>" class="btn btn-sm btn-danger" style="height:35px; width: 90px;border-radius: 15px;background-color:  #dcdcdc; border: 0px ">Remove</a></td>
				</tr>

				<%
				}}%>
			</tbody>
		</table>
	</div>

	<%@include file="/includes/footer.jsp"%>
           <%@include file="/includes/html/foot.html"%>
</body>
</html>















