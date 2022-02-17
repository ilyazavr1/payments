<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@taglib prefix="cardFormat" uri="/WEB-INF/customCardNumberFromat.tld" %>
<%@taglib prefix="cardStatus" uri="/WEB-INF/customCardBooleanFromat.tld" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<html>
<head>
    <title>Cards</title>
</head>
<body>
<%@include file="/jspf/navBar.jspf" %>


<c:if test="${requestScope.cards == null}">
    <h1>Yot do not have any cards</h1>
</c:if>
<c:out value="${requestScope.cards.size()}"> size</c:out>


<div class="container">

    <div class="row">

        <c:if test="${requestScope.cards != null}">
            <c:forEach var="card" items="${requestScope.cards }">

                <c:url var="cardID" value="${Path.CARD_TOP_UP_PATH}">
                    <c:param name="id" value="${card.id}"/>
                </c:url>
                <c:if test="${card.blocked == false}">
                    <div class="col-sm-4 py-2">
                        <div class="card h-100 border-primary">
                            <div class="card-body">
                                <h3 class="card-title"><cardFormat:formatCardNumber number="${card.number}"/></h3>
                                <p class="card-text"><c:out value="${card.money}"> </c:out></p>
                                <p class="card-text"><cardFormat:formatCardBoolean
                                        status="${card.blocked}"> </cardFormat:formatCardBoolean></p>
                                <a style="background-color: lightgreen" href="${cardID}"
                                   class="btn btn-outline-secondary">TOP UP</a>
                                <a style="margin-left: 150px; background-color: lightcoral" href="${cardID}"
                                   class="btn btn-outline-secondary">BLOCK</a>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${card.blocked == true}">
                    <div class="col-sm-4 py-2">
                        <div style="background-color: red" class="card h-100 border-primary">
                            <div class="card-body">
                                <h3 class="card-title"><cardFormat:formatCardNumber number="${card.number}"/></h3>
                                <p class="card-text"><c:out value="${card.money}"> </c:out></p>
                                <p class="card-text"><cardFormat:formatCardBoolean
                                        status="${card.blocked}"> </cardFormat:formatCardBoolean></p>
                                <a  style="background-color: lightgreen" onclick="alert('Card is blocked')"
                                   class="btn btn-outline-secondary" >TOP UP</a>
                                <a style="margin-left: 150px; background-color: lightcoral" href="${cardID}"
                                   class="btn btn-outline-secondary">BLOCK</a>
                            </div>
                        </div>
                    </div>
                </c:if>

            </c:forEach>
        </c:if>
    </div>

</div>

<form action="${Path.CARD_PATH}" method="post">

    <input type="submit" name="createCart" value="Add card">
</form>


</body>
</html>
