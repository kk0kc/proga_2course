<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 05.09.2022
  Time: 15:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Let's go chat!</title>
</head>
<body>

<h1>Create id of your own chat room:</h1>
<form  method="post">
    <input type="text" name="id">
    <button type="submit">Create!</button>
</form>
<br>

<h1>Open chat room by id:</h1>
<form action="" method="get">
    <label>
        <input type="text" name="id_chat">
    </label>
    <button type="submit">Open!</button>
</form>

</body>
</html>
