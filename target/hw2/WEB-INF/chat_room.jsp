<%@ page import="orrg.MessageEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="orrg.SessionCreatedListener" %>
<%@ page import="java.util.Objects" %><%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 29.09.2022
  Time: 0:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chat Room</title>
</head>
<body>

<form action="/room" method="post">
    <label>
        <input type="text" name="message">
    </label>
    <button>Send message</button>
</form>

<% ServletContext servletContext = request.getServletContext(); %>
<% String activeRoom = (String) servletContext.getAttribute("room"); %>
<% List<MessageEntity> list = SessionCreatedListener.messageEntities; %>


<% for (MessageEntity entity : list) { %>
<% if (Objects.equals(entity.getLogin(), activeRoom))  { %>
 <%= entity.getUserMessage()  %> <br>
<% } %>
<% } %>

</body>
</html>
