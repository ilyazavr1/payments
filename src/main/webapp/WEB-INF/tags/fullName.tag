<%@ attribute name="firstName" required="true" type="java.lang.String" description="User first name" %>
<%@ attribute name="lastName" required="true" type="java.lang.String" description="User last name" %>
<%@ attribute name="surname" required="true" type="java.lang.String" description="User surame" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<p>${lastName} ${firstName} ${surname}</p>