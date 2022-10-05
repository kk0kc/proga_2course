<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 05.09.2022
  Time: 15:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Let's go chat!</title>--%>
<%--</head>--%>
<%--<body>--%>

<%--<h1>Enter username:</h1>--%>
<%--<form  action="/id" method="post">--%>
<%--    <label>--%>
<%--        <input type="text" name="user">--%>
<%--    </label>--%>
<%--    <button type="submit">Enter!</button>--%>
<%--</form>--%>

<%--<h1>Open chat room by id:</h1>--%>
<%--<form action="/id" method="get">--%>
<%--    <label>--%>
<%--        <input type="text" name="id_chat">--%>
<%--    </label>--%>
<%--    <button type="submit">Open!</button>--%>
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
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        /* Set a style for all buttons */
        button {
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 100%;
            border-radius: 10px;
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
            width: 40%;
            border-radius: 50%;
        }

        .container {
            border-radius: 10px;
            padding: 16px;
        }

        span.psw {
            float: right;
            padding-top: 16px;
        }

        /* The Modal (background) */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
            padding-top: 60px;
        }

        /* Modal Content/Box */
        .modal-content {
            border-radius: 10px;
            background-color: #fefefe;
            margin: 5% auto 15% auto; /* 5% from the top, 15% from the bottom and centered */
            border: 1px solid #888;
            width: 80%; /* Could be more or less, depending on screen size */
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


<br><br><br><br><br>
<center><button onclick="document.getElementById('id01').style.display='block'" style="width:auto;">User name</button></center>
<center><button onclick="document.getElementById('id02').style.display='block'" style="width:auto;">Chat ID</button></center>
<div id="id01" class="modal">

    <form class="modal-content animate" action="/id" method="post">
        <div class="container">
            <input type="text" placeholder="Enter user name" name="user" required>
            <button type="submit">Enter</button>
        </div>
    </form>
</div>

<div id="id02" class="modal">

    <form class="modal-content animate" action="/room" method="get">
        <div class="container">
            <input type="text" placeholder="Enter room ID" name="id_chat" required>
            <button type="submit">Open</button>
        </div>
    </form>
</div>

<script>
    // Get the modal
    var modal = document.getElementById('id01');

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
</script>

</body>
</html>


