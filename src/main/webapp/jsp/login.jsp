<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>


<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login</h1>


<form action="${Path.LOGIN_PATH}" method="post">

    <label for="email">Input email</label>
    <input type="email" name="email" id="email">
    <br>

    <label for="password">Input password</label>
    <input type="password" name="password" id="password">
    <br>

    <input type="submit" name="login" value="Login">

</form>

<p>Qwerty12345</p>

</body>
</html>
