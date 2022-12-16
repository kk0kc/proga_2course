<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>Add post</title>
</head>
<body style="background-color: #e0f0ff">
	<jsp:include page="/jsp/navbar.jsp" />
	<hr>

	<h2 class="text-center mb-5">Добавить пост</h2>
	<form action="" method="post" enctype="multipart/form-data" class="w-75 mx-auto">
		<div class="mb-3">
			<label for="exampleFormControlInput1" class="form-label">Название</label>
			<input required name="title" type="text" class="form-control" id="exampleFormControlInput1">
		</div>
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Тег</label>
			<textarea required name="text" class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
		</div>

		<div class="mb-3">
			<label for="formFile" class="form-label">Загрузите картинку</label>
			<input required name="img" class="form-control" type="file" id="formFile">
		</div>

		<c:if test="${user.getStatus().equals('admin')}">
			<div>
			<input type="checkbox" id="nsfw" name="nsfw">
			<label for="nsfw">Nsfw</label>
			</div>
		</c:if>

		<button class="btn btn-outline-primary mt-5">Добавить</button>
	</form>
</body>
</html>
