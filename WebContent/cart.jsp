<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/includes/css/style.css">
</head>
<body style='background-color:#eaeaea;'>
	<%@include file="/includes/navbar.jsp"%>

	<div class="container my-3">
		<div class="d-flex py-3" style="margin-left: 950px">
<%--			<h3>Total Price: $ ${(total>0)?dcf.format(total):0}</h3>--%>
			<a href="cart-check-out" class="mx-3 btn btn-danger" style=" height:35px; width: 90px;border-radius: 15px;background-color:  #6184ff; border: 0px ">Check all</a>
		</div>
		<table class="table table-hover" style='background-color:#ffffff; border-radius: 20px'>
			<thead>
				<tr style="border-radius: 20px">
					<th style="border-width: 0px;" scope="col"></th>
					<th scope="col">Name</th>
					<th scope="col">Category</th>
					<th scope="col">Year</th>
					<th scope="col">IMDb</th>
					<th scope="col"></th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody  >
				<c:forEach var="cartProduct" items="${cartProduct}">
				<tr>
					<td>
<%--					<a class="videofront" style="background-image: url('includes/product-image/${cartProduct.getImage()}'); " >--%>
<%--					<video  style="" autoplay="" loop="" muted="" playsinline="" height="150" width="254.9" >--%>
<%--					<source src="${cartProduct.getGif()}" type="video/mp4">--%>
<%--					</video></a>--%>
						<img src="includes/product-image/${cartProduct.getImage()}" height="150" width="270">
					</td>
					<td><c:out value="${cartProduct.getName()}"/></td>
					<td><c:out value="${cartProduct.getCategory()}"/></td>
					<td><c:out value="${cartProduct.getYear()}"/></td>
					<td><c:out value="${dcf.format(cartProduct.getImdb())}"/></td>
					<td>
						<form action="order-now" method="post" class="form-inline">
						<input type="hidden" name="id" value="${cartProduct.getId()}" class="form-input">
<%--							<div class="form-group d-flex justify-content-between">--%>
<%--								<a class="btn bnt-sm btn-incre" href="quantity-inc-dec?action=inc&id=<%=c.getId()%>"><i class="fas fa-plus-square"></i></a>--%>
<%--								<input type="text" name="quantity" class="form-control"  value="<%=c.getQuantity()%>" readonly style='background-color:#FFF6FA;'> --%>
<%--								<a class="btn btn-sm btn-decre" href="quantity-inc-dec?action=dec&id=<%=c.getId()%>"><i class="fas fa-minus-square"></i></a>--%>
<%--							</div>--%>
							<button type="submit" style=" height:35px; width: 90px; border-radius: 15px;background-color:  #6184ff; border: 0px " class="btn btn-primary btn-sm">Viewed</button>
						</form>
					</td>
					<td><a href="remove-from-cart?id=${cartProduct.getId()}" class="btn btn-sm btn-danger" style="height:35px; width: 90px;border-radius: 15px;background-color:  #dcdcdc; border: 0px ">Remove</a></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<%@include file="/includes/footer.jsp"%>
           <%@include file="/includes/html/foot.html"%>
</body>
</html>















