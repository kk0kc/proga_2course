<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/includes/css/style.css">
</head>
<body>
<h1>Sign Up</h1>

<form method="post">
    <label>Name
        <input name="name" type="text">
    </label>
    <label>Email
        <input name="email" type="email">
    </label>
    <label>Password
        <input name="password" type="password">
    </label>
    <input type="submit">
</form>

</body>
</html>