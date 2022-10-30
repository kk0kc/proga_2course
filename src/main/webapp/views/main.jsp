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
    <title>HW BD</title>
  </head>
  <style>
    <%@include file="/WEB-INF/table.css"%>
  </style>
  <center><h1>Список книг и магазинов</h1></center>
  <c:set var="p" value="${requestScope.mainTab}"/>
  <c:choose>
    <c:when test="${p!=null}">
      <table border="2" >
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
  <br><br>

<div>
  <c:set var="e" value="${requestScope.books}"/>
  <form method="get">
    <input type="hidden" name="Delete" value="Delete">
    <c:choose>
      <c:when test="${e!=null}">
  <table border="2" style=" margin-left: 50px;  margin-bottom: 30px; float: left; margin-right: 110px">
    <tr>
      <c:set var="c" value="${requestScope.ColumnsBookTable}"/>
      <th style="background: #d3f5eb"></th>
      <c:forEach var="i" items="${c}">
        <th style="background: #d3f5eb"><c:out value="${i}"/></th>
      </c:forEach>
    </tr>
    <c:forEach var="i" items="${e}">
      <tr>
        <td style="background: white"><input type="checkbox" name="book${i.id}" value="${i.id}"></td>
        <td style="background: white"><c:out value="${i.id}"/></td>
        <td style="background: white"><c:out value="${i.name}"/></td>
        <td style="background: white"><c:out value="${i.author}"/></td>
      </tr>
    </c:forEach>
  </table>
      </c:when>
    </c:choose>
    <button type="submit" class="floating-button">Удалить выделенное</button>
    <c:set var="t" value="${requestScope.shops}"/>
    <c:choose>
      <c:when test="${t!=null}">
  <table border="2" style="margin-right:50px;  float: right; margin-bottom: 30px;">
    <tr>
      <c:set var="c" value="${requestScope.ColumnsShopTable}"/>
      <th style="background: #d3f5eb"></th>
      <c:forEach var="i" items="${c}">
        <th style="background: #d3f5eb"><c:out value="${i}"></c:out></th>
      </c:forEach>
    </tr>
    <c:forEach var="i" items="${t}">
      <tr>
        <td style="background: white"><input type="checkbox" name="shop${i.id}" value="${i.id}"></td>
        <td style="background: white"><c:out value="${i.id}"/></td>
        <td style="background: white"><c:out value="${i.name}"/></td>
        <td style="background: white"><c:out value="${i.address}"/></td>
      </tr>
    </c:forEach>
  </table>
      </c:when>
    </c:choose>
  </form>
  <button style="margin-left: 22px" class="floating-button" onclick="location.href='..'">Обновить таблицы</button>
  </div>
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

  <div class="pumpum">
    <form class="decor" style="margin-right: 50px; margin-left: 50px" method="get">
      <div class="form-inner" style="margin-top: 30px">
        <h4>Отфильтровать книги</h4>
        <br><br><br>
        <label>
          Укажите название столбца
          <input type="text" name="columnBook">
        </label><br><br><br>
        <label>
          Укажите значение для фильтрации
          <input type="text" name="filterBook">
        </label><br><br><br>
        <button class="floating-button" type="submit">Отфильтровать</button><br><br><br>
        <button class="floating-button" onclick="location.href='..'">Отменить</button>
      </div>
    </form>


    <form class="decor" style="margin-right: 50px; margin-left: 50px" method="get">
      <div class="form-inner" style="margin-top: 30px">
      <H4>Отфильтровать магазины</H4>
        <br><br><br>
      <label>
        Укажите название столбца
        <input type="text" name="columnShop">
      </label><br><br><br>
      <label>
        Укажите значение для фильтрации
        <input type="text" name="filterShop">
      </label><br><br><br>
      <button class="floating-button" type="submit">Отфильтровать</button><br><br><br>
        <button class="floating-button" onclick="location.href='..'">Отменить</button>
      </div>
    </form>



    <div>

      <form class="decor" style="margin-right: 50px; margin-left: 50px" method="post">
        <div class="form-inner" style="padding: 20px" >
          <H4>Новая книга</H4>
          <input  type="hidden" value="newBook" name="newBook">
          Название
          <input type="text" name="value1"><br>
          Автор
          <input type="text" name="value2"><br>
          <button class="floating-button" style="height: 30px; font-size: 6px; line-height: 30px" type="submit">Добавить</button>
        </div>
      </form>
    <form class="decor" style="margin-right: 50px; margin-left: 50px" method="post">
      <div class="form-inner" style="padding: 20px">
      <H4>Новый магазин</H4>
      <input type="hidden" value="newShop" name="newShop">
      Название
      <input type="text" name="value11"><br>
      Адрес
      <input type="text" name="value12"><br>
      <button class="floating-button"  style="height: 30px; font-size: 6px; line-height: 30px" type="submit">Добавить</button>
      </div>
    </form>
    </div>
  </div>




  <div class="pumpum">


    <form class="decor" style="margin-right: 50px; margin-left: 50px" method="post">
      <div class="form-inner">
        <H4>Изменить данные книги</H4>
      <input type="hidden" value="changeBook" name="changeBook">
      ID
      <input type="text" name="valueForUpdate0"><br>
      Название
      <input type="text" name="valueForUpdate1"><br>
        Автор
      <input type="text" name="valueForUpdate2"><br>
      <button class="floating-button" type="submit">Изменить</button>
      </div>
    </form>



    <form class="decor" style="margin-right: 50px; margin-left: 50px" method="post">
      <div class="form-inner">
        <H4>Изменить данные магазина</H4>
      <input type="hidden" value="changeShop" name="changeShop">
      ID
      <input type="text" name="valueForUpdate10"><br>
      Название
      <input type="text" name="valueForUpdate11"><br>
      Адрес
      <input type="text" name="valueForUpdate12"><br>
      <button class="floating-button" type="submit">Изменить</button>
      </div>
    </form>


    <form class="decor" style="margin-right: 50px; margin-left: 50px" method="post">
      <div class="form-inner" style="margin-top: 20px">
        <H4>Закрепить книгу за магазином</H4>
        <br><br>
      <input type="hidden" value="CreateLink" name="CreateLink">
      ID книги
      <input type="text" name="valueForCreateLink1"><br><br>
      ID магазина
      <input type="text" name="valueForCreateLink2"><br><br>
      <button class="floating-button" type="submit">Закрепить</button>
      </div>
    </form>
  </div>
  </body>
</html>
