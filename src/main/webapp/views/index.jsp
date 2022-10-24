<%@ page import="java.sql.Connection" %>
<%@ page import="app.DataBaseConnection.PostgresConnectionToDataBase" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="app.entities.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedList" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="app.entities.Shop" %><%--
  Created by IntelliJ IDEA.
  User: danii
  Date: 01.10.2022
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <style>
    div{
      position: static;
      width: 400px;
      height: 400px;
    }
  </style>
  <h1>Список книг и магазинов</h1>
  <c:set var="p" value="${requestScope.outList}"/>
  <c:choose>
    <c:when test="${p!=null}">
      <table border="2" style="margin-right: 100px; margin-bottom: 30px; float: left">
        <tr>
          <c:set var="c" value="${requestScope.ColumnsLinkTable}"/>
          <c:forEach var="i" items="${c}">
          <th> <c:out value="${i}"/></th>
          </c:forEach>
        </tr>
        <c:forEach var="i" items="${p}">
          <tr>
            <c:forEach var="i1" items="${i}">
            <td><c:out value="${i1}"/></td>
            </c:forEach>
          </tr>
        </c:forEach>
      </table>
    </c:when>
  </c:choose>


  <c:set var="e" value="${requestScope.books}"/>
  <form method="get">
    <input type="hidden" name="Delete" value="Delete">
    <c:choose>
      <c:when test="${e!=null}">
  <table border="2" style=" margin-right: 70px; margin-bottom: 30px; float: left">
    <tr>
      <c:set var="c" value="${requestScope.ColumnsBookTable}"/>
      <th></th>
      <c:forEach var="i" items="${c}">
        <th><c:out value="${i}"/></th>
      </c:forEach>
    </tr>
    <c:forEach var="i" items="${e}">
      <tr>
        <td><input type="checkbox" name="book${i.id}" value="${i.id}"></td>
        <td><c:out value="${i.id}"/></td>
        <td><c:out value="${i.name}"/></td>
        <td><c:out value="${i.author}"/></td>
      </tr>
    </c:forEach>
  </table>
      </c:when>
    </c:choose>

    <c:set var="t" value="${requestScope.shops}"/>
    <c:choose>
      <c:when test="${t!=null}">
  <table border="2" style="float: left; margin-right: 70px; margin-bottom: 30px;">
    <tr>
      <c:set var="c" value="${requestScope.ColumnsShopTable}"/>
      <th></th>
      <c:forEach var="i" items="${c}">
        <th><c:out value="${i}"></c:out></th>
      </c:forEach>
    </tr>
    <c:forEach var="i" items="${t}">
      <tr>
        <td><input type="checkbox" name="shop${i.id}" value="${i.id}"></td>
        <td><c:out value="${i.id}"/></td>
        <td><c:out value="${i.name}"/></td>
        <td><c:out value="${i.address}"/></td>
      </tr>
    </c:forEach>
  </table>
      </c:when>
    </c:choose>
    <button type="submit" style="float: left;">Удалить выделенные сущности</button>
  </form>

<div>
  <form method="get">
    <h4>Упорядочить книги</h4>
    <label>
      Укажите столбец
      <input type="text" name="column">
    </label><br>
    <label>
      Укажите порядок "DESC ASC"
      <input type="text" name="ascendingOrDescending">
    </label><br>
    <button type="submit">Упорядочить</button>
  </form>


  <form method="get">
    <H4>Упорядочить магфзины</H4>
    <label>
      Укажите столбец
      <input type="text" name="column1">
    </label><br>
    <label>
      Укажите порядок "DESC ASC"
      <input type="text" name="ascendingOrDescending1">
    </label><br>
    <button type="submit">Упорядочить</button>
  </form>
  <button onclick="location.href='/change'">Создать запись или  отредактировать существуюшую</button>
</div>
  </body>
</html>
