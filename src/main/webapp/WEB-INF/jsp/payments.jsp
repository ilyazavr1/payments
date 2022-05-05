<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@taglib prefix="ufn" tagdir="/WEB-INF/tags" %>
<%@ page import="ua.epam.payments.payments.model.entity.Payment" %>
<html>
<head>
    <title>My payments</title>
</head>
<body>
<%@include file="/jspf/navBar.jspf" %>

<form>


</form>


<form action="${Path.PAYMENTS_PATH}" method="get">
    <input type="hidden" value="${requestScope.page}" name="page">
    <input type="hidden" value="${requestScope.records}" name="records">
    <div style="width: 30%" class="row">
        <div class="col">
            <label for="typeSort"><fmt:message key="sortBy"/>:</label>
            <select name="sortingType" class="form-control" id="typeSort">
                <c:choose>
                    <c:when test="${requestScope.sortingType.equals('creation_timestamp')}">
                        <option selected value="creation_timestamp"><fmt:message key="date"/></option>
                        <option value="payment.id"><fmt:message key="number"/></option>
                    </c:when>
                    <c:otherwise>
                        <option value="creation_timestamp"><fmt:message key="date"/></option>
                        <option selected value="payment.id"><fmt:message key="number"/></option>
                    </c:otherwise>
                </c:choose>
            </select>
        </div>
        <div class="col">
            <label for="orderSort"><fmt:message key="sortingOrder"/>:</label>
            <select name="sortingOrder" class="form-control" id="orderSort">
                <c:choose>
                    <c:when test="${requestScope.sortingOrder.equals('asc')}">
                        <option selected value="asc"><fmt:message key="ascending"/></option>
                        <option value="desc"><fmt:message key="descending"/></option>
                    </c:when>
                    <c:otherwise>
                        <option value="asc"><fmt:message key="ascending"/></option>
                        <option selected value="desc"><fmt:message key="descending"/></option>
                    </c:otherwise>
                </c:choose>
            </select>
        </div>
        <div class="col">
            <input class="btn btn-primary" type="submit" value="<fmt:message key="sort"/>">
        </div>
    </div>
</form>

<div class="container">


    <div class="row col-md-6 col-md-offset-2 custyle">
        <table class="table table-striped custab">
            <thead>


            <tr>
                <th><fmt:message key="number"/></th>
                <th style="padding-right: 150px"><fmt:message key="senderCard"/></th>
                <th><fmt:message key="balanceBefore"/></th>
                <th><fmt:message key="paymentSum"/></th>
                <th><fmt:message key="balanceAfter"/></th>
                <th style="padding-right: 150px"><fmt:message key="recipientName"/></th>
                <th style="padding-right: 150px"><fmt:message key="recipientCard"/></th>
                <th style="padding-right: 150px"><fmt:message key="date"/></th>
                <th><fmt:message key="status"/></th>
                <th class="text-center"><fmt:message key="cardAction"/></th>
                <th>PDF</th>
            </tr>
            </thead>
            <c:if test="${requestScope.payments != null}">
                <c:forEach var="payment" items="${requestScope.payments }">
                    <tr>
                        <td>${payment.id}</td>
                        <td><cardFormat:formatCardNumber
                                number="${payment.senderCardNumber}"> </cardFormat:formatCardNumber></td>
                        <td>${payment.senderBalance}</td>
                        <td>${payment.money}</td>
                        <td>${payment.senderBalance - payment.money}</td>
                        <td><ufn:fullName firstName="${payment.destinationFirstName}"
                                          lastName="${payment.destinationLastName}"
                                          surname="${payment.destinationSurname}"> </ufn:fullName></td>
                        <td style="width: 2000px"><cardFormat:formatCardNumber
                                number="${payment.destinationCardNumber}"> </cardFormat:formatCardNumber></td>
                        <td><cardFormat:dateTimeFormat
                                dateTime="${payment.creationTimestamp}"> </cardFormat:dateTimeFormat></td>


                        <c:choose>
                            <c:when test="${payment.status.equals('PREPARED')}">
                                <td><span class="badge badge-primary"><fmt:message key="statusPrepared"/></span></td>
                                <form action="${Path.PAYMENTS_CONFIRM_PATH}" method="post">
                                    <td><input type="submit" class="btn btn-success" value="<fmt:message key="confirm"/>"></td>
                                    <input type="hidden" name="paymentId" value="${payment.id}">
                                </form>
                            </c:when>
                            <c:otherwise>
                                <td><span class="badge badge-primary"><fmt:message key="statusSent"/></span></td>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${requestScope.invalidPayment == payment.id}">
                            <td style="color: red"><fmt:message key="outOfMoney"> </fmt:message></td>
                        </c:if>
                        <td></td>
                    </tr>

                </c:forEach>
            </c:if>

        </table>
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
                           href="${Path.PAYMENTS_PATH}?page=${num}&records=${rec}&sortingType=${sortType}&sortingOrder=${sortOrder}">${num}</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link"
                           href="${Path.PAYMENTS_PATH}?page=${num}&records=${rec}&sortingType=${sortType}&sortingOrder=${sortOrder}">${num}</a>
                    </li>
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
<form action="${Path.PAYMENTS_PATH}" method="get">
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
