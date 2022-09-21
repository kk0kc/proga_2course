<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 06.09.2022
  Time: 21:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="utf-8">
  <title>Cat calculator</title>
</head>
<body>
<form name="calc" >
  <center><table border="1" >
    <caption>Cat calculator</caption>
    <tr> <td colspan="4"><input type="text" name="input"></td></tr>
    <tr>
      <td><input type="button" value="7" OnClick="calc.input.value += '7'"></td>
      <td><input type="button" value="8" OnClick="calc.input.value += '8'"></td>
      <td><input type="button" value="9" OnClick="calc.input.value += '9'"></td>
      <td><input type="button" value="/" OnClick="calc.input.value += '/'"></td>
    </tr>
    <tr>
      <td><input type="button" value="4" OnClick="calc.input.value += '4'"></td>
      <td><input type="button" value="5" OnClick="calc.input.value += '5'"></td>
      <td><input type="button" value="6" OnClick="calc.input.value += '6'"></td>
      <td><input type="button" value="*" OnClick="calc.input.value += '*'"></td>
    </tr>
    <tr>
      <td><input type="button" value="3" OnClick="calc.input.value += '3'"></td>
      <td><input type="button" value="2" OnClick="calc.input.value += '2'"></td>
      <td><input type="button" value="1" OnClick="calc.input.value += '1'"></td>
      <td><input type="button" value="-" OnClick="calc.input.value += '-'"></td>
    </tr>
    <tr>
      <td><input type="button" value="c" OnClick="calc.input.value = ''"></td>
      <td><input type="button" value="0" OnClick="calc.input.value += '0'"></td>
      <td><input type="button" value="=" OnClick="calc.input.value = eval(calc.input.value)"></td>
      <td><input type="button" value="+" OnClick="calc.input.value += '+'"></td>
    </tr>
  </table></center>
</form>
</body>
</html>
