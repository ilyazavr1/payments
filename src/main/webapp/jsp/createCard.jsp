<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@include file="/jspf/navBar.jspf" %>


<html>
<head>
    <title>Create card</title>
</head>
<body>

<form action="${Path.CARD_CREATE_PATH}" method="post">

   <p>Input name  <input type="text" value="card" name="cardName"></p>
    <input type="submit" value="Create">
</form>


</body>
</html>
