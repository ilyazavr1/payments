<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@include file="/jspf/navBar.jspf" %>


<html>
<head>
    <title>Create card</title>
</head>
<body>

<form action="${Path.CARD_CREATE_PATH}" method="post">

    <p>Input name <input type="text" value="card" name="cardName"></p>
    <c:if test="${requestScope.emptyCardName != null}">
        <p style="color: red"><fmt:message key="emptyCardName"> </fmt:message> </p>
    </c:if>
    <input type="submit" value="Create">
</form>


</body>
</html>
