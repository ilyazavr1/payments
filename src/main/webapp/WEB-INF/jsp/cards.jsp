<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>


<html>
<head>

    <%@include file="/jspf/bootstrap.jspf" %>
    <title>Cards</title>
</head>
<body>
<%@include file="/jspf/navBar.jspf" %>


<c:if test="${requestScope.cards == null}">
    <h1>Yot do not have any cards</h1>
</c:if>
<form action="${Path.CARDS_PATH}" method="get">
    <input type="hidden" value="${requestScope.page}" name="page">
    <input type="hidden" value="${requestScope.records}" name="records">
    <div style="width: 30%" class="row">
        <div class="col">
            <label for="typeSort">Sort by:</label>
            <select name="sortingType" class="form-control" id="typeSort">
                <c:choose>
                    <c:when test="${requestScope.sortingType.equals('number')}">
                        <option selected value="number">Number</option>
                        <option value="name">Name</option>
                        <option value="money">Money</option>
                    </c:when>
                    <c:when test="${requestScope.sortingType.equals('name')}">
                        <option value="number">Number</option>
                        <option selected value="name">Name</option>
                        <option value="money">Money</option>
                    </c:when>
                    <c:otherwise>
                        <option value="number">Number</option>
                        <option value="name">Name</option>
                        <option selected value="money">Money</option>
                    </c:otherwise>
                </c:choose>
            </select>
        </div>
        <div class="col">
            <label for="orderSort">Sorting order:</label>
            <select name="sortingOrder" class="form-control" id="orderSort">
                <c:choose>
                    <c:when test="${requestScope.sortingOrder.equals('asc')}">
                        <option selected value="asc">Ascending</option>
                        <option value="desc">Descending</option>
                    </c:when>
                    <c:otherwise>
                        <option value="asc">Ascending</option>
                        <option selected value="desc">Descending</option>
                    </c:otherwise>
                </c:choose>
            </select>
        </div>
        <div class="col">
            <input type="submit" value="Sort">
        </div>
    </div>
</form>

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
                                <h3 class="card-title">${card.name}</h3>
                                <p class="card-text"><c:out value="${card.money}"> </c:out></p>
                                <p class="card-text"><cardFormat:formatActiveBlockedBoolean
                                        status="${card.blocked}"> </cardFormat:formatActiveBlockedBoolean></p>
                                <a style="background-color: lightgreen" href="${cardID}"
                                   class="btn btn-outline-secondary">TOP UP</a>
                                <a style="margin-left: 140px; background-color: lightcoral" href="${cardID}"
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
                                <p class="card-text"><cardFormat:formatActiveBlockedBoolean
                                        status="${card.blocked}"> </cardFormat:formatActiveBlockedBoolean></p>
                                <a style="background-color: lightgreen" onclick="alert('Card is blocked')"
                                   class="btn btn-outline-secondary">TOP UP</a>
                                <a style="margin-left: 140px; background-color: lightcoral" href="${cardID}"
                                   class="btn btn-outline-secondary">BLOCK</a>
                            </div>
                        </div>
                    </div>
                </c:if>

            </c:forEach>
        </c:if>
    </div>

</div>


<nav aria-label="Page navigation example">

    <ul class="pagination">
        <li class="page-item">
            <a class="page-link" href="#" aria-label="Previous">
                <span aria-hidden="true"><</span>
                <span class="sr-only">Previous</span>
            </a>
        </li>

        <c:set var="rec" value="${requestScope.records}" scope="request"> </c:set>
        <c:set var="lenght" value="${requestScope.loopPagination}" scope="request"> </c:set>
        <c:set var="sortType" value="${requestScope.sortingType}" scope="request"> </c:set>
        <c:set var="sortOrder" value="${requestScope.sortingOrder}" scope="request"> </c:set>


        <c:forEach begin="1" end="${requestScope.loopPagination}" varStatus="loop">
            <c:set var="num" value="${loop.index}" scope="page"> </c:set>
            <c:choose>
                <c:when test="${requestScope.page == num}">
                    <li class="page-item active">
                        <a class="page-link"
                           href="${Path.CARDS_PATH}?page=${num}&records=${rec}&sortingType=${sortType}&sortingOrder=${sortOrder}">${num}</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="${Path.CARDS_PATH}?page=${num}&records=${rec}&sortingType=${sortType}&sortingOrder=${sortOrder}">${num}</a></li>
                </c:otherwise>
            </c:choose>


        </c:forEach>


        <li class="page-item">
            <a class="page-link" href="#" aria-label="Next">
                <span aria-hidden="true">></span>
                <span class="sr-only">Next</span>
            </a>
        </li>
    </ul>


</nav>
<form action="${Path.CARDS_PATH}" method="get">
    <input type="hidden" name="page" value="${requestScope.page}">

    <select name="records">

        <c:choose>
        <c:when test="${requestScope.records == 3}">
        <option selected value="3">3</option>
        </c:when>
        <c:otherwise>
        <option value="3">3</option>
        </c:otherwise>
        </c:choose>

        <c:choose>
        <c:when test="${requestScope.records == 6}">
        <option selected value="6">6</option>
        </c:when>
        <c:otherwise>
        <option value="6">6</option>
        </c:otherwise>
        </c:choose>
        <c:choose>
        <c:when test="${requestScope.records == 9}">
        <option selected value="9">9</option>
        </c:when>
        <c:otherwise>
        <option value="9">9</option>
        </c:otherwise>
        </c:choose>
        <input type="hidden" name="sortingType" value="${requestScope.sortingType}">
        <input type="hidden" name="sortingOrder" value="${requestScope.sortingOrder}">
        <input type="submit" value="go">

</form>


</body>
</html>
