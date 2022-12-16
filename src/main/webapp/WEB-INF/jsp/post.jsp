<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="utils.Utils" %>
<%@ page import="services.UserService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
</head>
<body style="background-color: #e0f0ff">
	<jsp:include page="/jsp/navbar.jsp" />
	<hr>
	<a href="add_fav?id=${post.getId()}" style="background-color: #3b91fa; border-color: #3b91fa" class="btn btn-primary">Добавить в избранные</a>
	<div class="w-50 mx-auto">
		<h2 class="mb-5">${post.getTitle()}</h2>
		<div class="mb-5">
			<img style="width: 75%; border-radius: 5%" src="http://localhost:8081/images/${post.getImgName()}" alt="img">
		</div>

		Тег: <p class="mb-5">${post.getText()}</p>

		<c:if test="${Utils.isPostBelongsToUser(post.getUserID()) || UserService.isAdmin()}">
			<form action="<c:url value="/posts/update"/>" method="get" class="d-inline-block mb-5">
				<button name="postID" value="${post.getId()}" class="btn btn-outline-info">Редактировать</button>
			</form>
			<form action="<c:url value="/posts/delete"/>" method="post" class="d-inline-block">
				<button name="postID" value="${post.getId()}" class="btn btn-outline-danger">Удалить</button>
			</form>
		</c:if>

<%--		<h3 class="text-center">Теги</h3>--%>
<%--		<hr>--%>

<%--		<ul class="list-group mb-5">--%>
<%--			<c:forEach var="comment" items="${comments}">--%>
<%--				<li class="list-group-item list-group-item-secondary mb-3">--%>
<%--						${comment.getText()}--%>
<%--				</li>--%>
<%--			</c:forEach>--%>
<%--		</ul>--%>

<%--		<c:if test="${UserService.isAuth()}">--%>
<%--			<form action="" method="post">--%>
<%--				<div class="input-group mb-3">--%>
<%--					<input name="text" type="text" class="form-control">--%>
<%--				</div>--%>
<%--				<button class="btn btn-success">Добавить тег</button>--%>
<%--			</form>--%>
<%--		</c:if>--%>
	</div>


	<h3 class="text-center">Похожие</h3>
	<div class="container"  >

		<div class="row">
			<c:forEach var="posts" items="${posts}">
				<c:if test="${posts.getNsfw().equals('visible')}">
				<c:if test="${post.getText().equals(posts.getText())}">
<%--					<c:forEach var="comment" items="${comments}">--%>
<%--					<c:forEach var="allcomment" items="${allcomments}">--%>
<%--						<c:if test="${comment.getText().equals(allcomment.getText())}">--%>
					<div class="col-md-3 my-3">
						<div class="card w-100" style="height: 400px">
							<img src="http://localhost:8081/images/${posts.getImgName()}" class="card-img-top"style="width: 304px; height: 280px"  alt="...">
							<div class="card-body">
								<h5 class="card-title">${posts.getTitle()}</h5>
								<a href="/posts/${post.getId()}" style="background-color: #3b91fa; border-color: #3b91fa" class="btn btn-primary">Читать</a>
							</div>
						</div>
					</div>
						</c:if>
<%--					</c:forEach>--%>
<%--					</c:forEach>--%>
				</c:if>
			</c:forEach>
		</div>
	</div>

</body>
</html>
