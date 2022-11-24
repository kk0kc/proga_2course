<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
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


	<style>
			.star-rating{
			color: #bebebe;
			font-size:2em;
			}
	.my-star::before{
	content:"\002605";
	}
	.my-star{
	font-style: unset !important;
	}
	.is-active{
	color:#fb8900;
	}
	.my-star:not(.is-active):hover{
	color: #fb8900;
	}
	</style>
</head>
<body style='background-color:#eaeaea;'>
	<%@include file="/includes/navbar.jsp"%>
	<div class="container my-3">
		<div class="card-header my-3">Orders</div>
		<table class="table table-hover" style='background-color:#ffffff; border-radius: 20px'>
			<thead>
				<tr style="border-radius: 20px">
					<th scope="col"></th>
					<th scope="col">Name</th>
					<th scope="col">Date</th>
					<th scope="col">Rate</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			<tbody>

			<c:forEach var="orders" items="${orders}">
					<tr>
						<td><img src="includes/product-image/${orders.getImage()}" width="270" height="150" alt="img"> </td>
						<td>${orders.getName()}</td>
						<td>${orders.getDate()}</td>
						<td>
							<c:if test="${orders.getQuantity() == -1}">
								<form method="get" action="${pageContext.request.contextPath}/add-rate">
									<input name="rate" title="Оценка от 0 до 5 звезд!" required pattern="(?=.*[0-5]).{1,1}" onchange="
                  this.setCustomValidity(this.validity.patternMismatch ? this.title : '');">
									<input hidden name="idor" value="${orders.getOrderId()}">
									<input type="submit" value="Save" />
								</form>
							</c:if>
							<c:if test="${orders.getQuantity() != -1}">
								<c:forEach begin="1" end="${orders.getQuantity()}" varStatus="loop">

										<i class="my-star star-1" data-star="1" style="color: #fb8900; font-size:2em" ></i>
								</c:forEach>
								<c:forEach begin="1" end="${5 - orders.getQuantity()}" varStatus="loop">

									<i class="my-star star-1" data-star="1" style="color: #bebebe; font-size:2em" ></i>
								</c:forEach>
							</c:if>

						</td>
						<td><a href="cancel-order?id=${orders.getOrderId()}" class="btn btn-sm btn-danger" style="height:35px; width: 90px;border-radius: 15px;background-color:  #dcdcdc; border: 0px ">Remove</a></td>
					</tr>
			</c:forEach>
			
			</tbody>
		</table>
	</div>
	<%@include file="/includes/footer.jsp"%>
            <%@include file="/includes/html/foot.html"%>
</body>
</html>