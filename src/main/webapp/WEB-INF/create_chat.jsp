<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 29.09.2022
  Time: 23:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Create chat</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h1>Create id of your own chat room:</h1>--%>
<%--<form  action="/start" method="post">--%>
<%--  <input type="text" name="id">--%>
<%--  <button type="submit">Create!</button>--%>
<%--</form>--%>
<%--<br>--%>
<%--<form action="/id" method="post">--%>
<%--    <button type="submit">I already have room</button>--%>
<%--</form>--%>
<%--</body>--%>
<%--</html>--%>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body {font-family: Arial, Helvetica, sans-serif;}

        /* Full-width input fields */
        input[type=text], input[type=password] {
            border-radius: 10px;
            width: 30%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        /* Set a style for all buttons */
        button {
            border-radius: 10px;
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 30%;
        }

        button:hover {
            opacity: 0.8;
        }

        /* Extra styles for the cancel button */
        .cancelbtn {
            width: auto;
            padding: 10px 18px;
            background-color: #f44336;
        }

        /* Center the image and position the close button */
        .imgcontainer {
            text-align: center;
            margin: 24px 0 12px 0;
            position: relative;
        }

        img.avatar {
            width: 24.5%;
            /*border-radius: 50%;*/
        }

        .container {
            padding: 16px;
        }

        span.psw {
            float: right;
            padding-top: 16px;
        }

        /* The Modal (background) */
        .modal {
            border-radius: 10px;
            /*display: none; !* Hidden by default *!*/
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 40%; /* Full height */
            /*overflow: auto; !* Enable scroll if needed *!*/
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
            padding-top: 30px;
        }

        /* Modal Content/Box */
        .modal-content {
            background-color: #fefefe;
            margin: 5% auto 15% auto; /* 5% from the top, 15% from the bottom and centered */
            border: 1px solid #888;
            width: 20%; /* Could be more or less, depending on screen size */
        }

        /* The Close Button (x) */
        .close {
            position: absolute;
            right: 25px;
            top: 0;
            color: #000;
            font-size: 35px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: red;
            cursor: pointer;
        }

        /* Add Zoom Animation */
        .animate {
            -webkit-animation: animatezoom 0.6s;
            animation: animatezoom 0.6s
        }

        @-webkit-keyframes animatezoom {
            from {-webkit-transform: scale(0)}
            to {-webkit-transform: scale(1)}
        }

        @keyframes animatezoom {
            from {transform: scale(0)}
            to {transform: scale(1)}
        }

        /* Change styles for span and cancel button on extra small screens */
        @media screen and (max-width: 300px) {
            span.psw {
                display: block;
                float: none;
            }
            .cancelbtn {
                width: 100%;
            }
        }
    </style>
</head>
<body>

<center><h2>Create your own chat room!</h2></center>
<br> <br> <br> <br> <br> <br> <br> <br> <br> <br> <br> <br> <br>
<center><img src="images/cat.png" alt="Avatar" class="avatar"></center>
<div class="modal">
    <form action="/id" method="post">
        <center><input type="text" placeholder="Enter Chat ID" name="id" required></center>
        <br>
        <center><button type="submit">Create</button></center>
    </form>
    <form action="/id" method="get">
        <center><button type="submit">I already have room</button></center>
    </form>
</div>

</body>
</html>
