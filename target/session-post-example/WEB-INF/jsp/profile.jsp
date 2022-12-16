<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Profile</title>
</head>
<body style="background-color: #e0f0ff">
	<jsp:include page="/jsp/navbar.jsp" />
	<hr>
	<h2 class="text-center">Ваш профиль</h2>
	<div>
		<form action="" method="post" class="w-50 mx-auto">
			<div class="mb-3">
				<label for="login" class="form-label">Логин</label>
				<input value="${login}" name="login" type="text" class="form-control" id="login">
			</div>
			<div class="mb-3">
				<label for="info" class="form-label">Информация о пользователе</label>
				<textarea name="info" class="form-control" id="info" rows="3">
					${info}
				</textarea>
			</div>

			<button class="btn btn-outline-success">Сохранить изменения</button>
		</form>
	</div>

	<div class="container"  >

		<div class="row">
			<c:forEach var="fav" items="${favs}">
				<c:if test="${post.getId().equals(fav.getPostId())}">
					<div class="col-md-3 my-3">
						<div class="card w-100" style="height: 400px">
							<img src="http://localhost:8081/images/${post.getImgName()}" class="card-img-top"style="width: 304px; height: 280px"  alt="...">
							<div class="card-body">
								<h5 class="card-title">${post.getTitle()}</h5>
								<a href="/posts/${post.getId()}" style="background-color: #3b91fa; border-color: #3b91fa" class="btn btn-primary">Читать</a>
							</div>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</div>
</body>
</html>
