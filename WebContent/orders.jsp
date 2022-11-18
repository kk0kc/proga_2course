<%@page import="java.text.DecimalFormat"%>
<%@page import="val.shop.dao.OrderDao"%>
<%@page import="val.shop.dao.ProductDao"%>
<%@page import="val.shop.model.*"%>
<%@page import="java.util.*"%>
<%@ page import="val.shop.DataBaseConnection.PostgresConnectionToDataBase" %>
<%@ page import="val.shop.dao.CartDao" %>
<%@ page import="static java.lang.Integer.parseInt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
	<%
	DecimalFormat dcf = new DecimalFormat("#.##");
	request.setAttribute("dcf", dcf);
	User auth = (User) request.getSession().getAttribute("auth");
	List<Order> orders = null;
	if (auth != null) {
	    request.setAttribute("person", auth);
	    OrderDao orderDao  = new OrderDao(PostgresConnectionToDataBase.getConnection());
		orders = orderDao.userOrders(auth.getId());
		CartDao cartDao = new CartDao(PostgresConnectionToDataBase.getConnection());
		ArrayList<Cart> cart_list = (ArrayList<Cart>) cartDao.userCart(auth.getId());
		if (cart_list != null) {
			request.setAttribute("cart_list", cart_list);
		}
	}else{
		response.sendRedirect("login.jsp");
	}
//	ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
	
	%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/includes/head.jsp"%>
<title>Shopping Cart</title>


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
<%--	<script>document.addEventListener('DOMContentLoaded', function(){--%>

<%--		(function(){--%>

<%--			let sr = document.querySelectorAll('.my-star');--%>


<%--			let i = 0;--%>

<%--			//loop through stars--%>

<%--			while (i < sr.length){--%>

<%--				//attach click event--%>

<%--				// sr[i].addEventListener('click', function(){--%>

<%--					//current star--%>

<%--					// let cs = parseInt(this.getAttribute("data-star"));--%>
<%--					let cs = 3;--%>

<%--					//output current clicked star value--%>

<%--					document.querySelector('#output').value = cs;--%>

<%--					/*our first loop to set the class on preceding star elements*/--%>

<%--					let pre = cs; //set the current star value--%>

<%--					//loop through and set the active class on preceding stars--%>

<%--					while(1 <= pre){--%>
<%--						document.querySelector('.star-'+pre).classList.add('is-active');--%>

<%--						//check if the classlist contains the active class, if not, add the class--%>

<%--						if(!document.querySelector('.star-'+pre).classList.contains('is-active')){--%>

<%--							document.querySelector('.star-'+pre).classList.add('is-active');--%>

<%--						}--%>

<%--						//decrement our current index--%>

<%--						--pre;--%>

<%--					}//end of first loop--%>

<%--					/*our second loop to unset the class on succeeding star elements*/--%>

<%--					//loop through and unset the active class, skipping the current star--%>

<%--					let succ = cs+1;--%>

<%--					while(5 >= succ){--%>
<%--						document.querySelector('.star-'+succ).classList.remove('is-active');--%>

<%--						//check if the classlist contains the active class, if yes, remove the class--%>

<%--						if(document.querySelector('.star-'+succ).classList.contains('is-active')){--%>

<%--							document.querySelector('.star-'+succ).classList.remove('is-active');--%>

<%--						}--%>

<%--						//increment current index--%>

<%--						++succ;--%>

<%--					}--%>

<%--				// })//end of click event--%>

<%--				i++;--%>

<%--			}//end of while loop--%>

<%--		})();//end of function--%>


<%--	})</script>--%>


<%--	<style--%>
<%--			*{--%>
<%--			margin: 0;--%>
<%--			padding: 0;--%>
<%--			}--%>
<%--			.rate {--%>
<%--			float: left;--%>
<%--			height: 46px;--%>
<%--			padding: 0 10px;--%>
<%--			}--%>
<%--			.rate:not(:checked) > input {--%>
<%--		position:absolute;--%>
<%--		top:-9999px;--%>
<%--	}--%>
<%--	.rate:not(:checked) > label {--%>
<%--		float:right;--%>
<%--		width:1em;--%>
<%--		overflow:hidden;--%>
<%--		white-space:nowrap;--%>
<%--		cursor:pointer;--%>
<%--		font-size:30px;--%>
<%--		color:#ccc;--%>
<%--	}--%>
<%--	.rate:not(:checked) > label:before {--%>
<%--		content: '★ ';--%>
<%--	}--%>
<%--	.rate > input:checked ~ label {--%>
<%--		color: #ffc700;--%>
<%--	}--%>
<%--	.rate:not(:checked) > label:hover,--%>
<%--	.rate:not(:checked) > label:hover ~ label {--%>
<%--		color: #deb217;--%>
<%--	}--%>
<%--	.rate > input:checked + label:hover,--%>
<%--	.rate > input:checked + label:hover ~ label,--%>
<%--	.rate > input:checked ~ label:hover,--%>
<%--	.rate > input:checked ~ label:hover ~ label,--%>
<%--	.rate > label:hover ~ input:checked ~ label {--%>
<%--		color: #c59b08;--%>
<%--	}</style>--%>
</head>
<body style='background-color:#FFF6FA;'>
	<%@include file="/includes/navbar.jsp"%>



<%--	<div class="rate">--%>
<%--		<input type="radio" id="star5" name="rate" value="5" />--%>
<%--		<label for="star5" title="text">5 stars</label>--%>
<%--		<input type="radio" id="star4" name="rate" value="4" />--%>
<%--		<label for="star4" title="text">4 stars</label>--%>
<%--		<input type="radio" id="star3" name="rate" value="3" />--%>
<%--		<label for="star3" title="text">3 stars</label>--%>
<%--		<input type="radio" id="star2" name="rate" value="2" />--%>
<%--		<label for="star2" title="text">2 stars</label>--%>
<%--		<input type="radio" id="star1" name="rate" value="1" />--%>
<%--		<label for="star1" title="text">1 star</label>--%>
<%--	</div>--%>
	<div class="container">
		<div class="card-header my-3">Orders</div>
		<table class="table table-light" style='background-color:#FEE5E5;'>
			<thead>
				<tr>
					<th scope="col"></th>
					<th scope="col">Name</th>
					<th scope="col">Date</th>
					<th scope="col">Rate</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			<tbody>
			
			<%
			if(orders != null){
				for(Order o:orders){%>
					<tr>
						<td><img src="product-image/<%=o.getImage()%>" width="250" height="160" alt="img"> </td>
						<td><%=o.getName() %></td>
						<td><%=o.getDate() %></td>
						<td>
							<p class="star-rating">
							<i class="my-star star-1" data-star="1"></i>
							<i class="my-star star-2" data-star="2"></i>
							<i class="my-star star-3" data-star="3"></i>
							<i class="my-star star-4" data-star="4"></i>
							<i class="my-star star-5" data-star="5"></i></p>
<%--							<input hidden type="number" name="id" readonly  value="<%=o.getOrderId()%>">--%>
							<input type="number" name="rate" readonly id="output" value="9">
<%--							<%=o.setQunatity();%>--%>
							<a class="btn btn-sm btn-danger" type="submit" href="add-rate?id=<%=o.getId()%>">Save</a></td>
						<td><a class="btn btn-sm btn-danger" href="cancel-order?id=<%=o.getOrderId()%>">Cancel Order</a></td>
					</tr>
				<%}
			}
			%>
			
			</tbody>
		</table>
	</div>
	<%@include file="/includes/footer.jsp"%>
            <%@include file="/includes/html/foot.html"%>
</body>
</html>