<%--
  Created by IntelliJ IDEA.
  User: smiya
  Date: 08.02.2022
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign up</title>
</head>
<body>
<h1>Sign up</h1>


<form action="signup" method="post">

    <label for="firstName">Input first name</label>
    <input type="text" name="firstName" id="firstName">

    <label for="lastName">Input last name</label>
    <input type="text" name="lastName" id="lastName">

    <label for="surname">Input surname</label>
    <input type="text" name="surname" id="surname">

    <label for="email">Input email</label>
    <input type="email" name="email" id="email">

    <label for="password">Input password</label>
    <input type="password" name="password" id="password">

    <input type="submit" name="register" value="Register">
</form>

</body>
</html>