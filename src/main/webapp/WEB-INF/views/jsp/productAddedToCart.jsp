<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
     <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Added Successfully!</h1>
		<p>Number of products:  ${fn:length(order.products)} </p>
		<p>Order:
		<c:forEach items="${order.products}" var="entry">
			<p>Product: ${entry.key.name}</p>
			<p>Quantity: ${entry.value}</p>
			<p>User Id: ${user.id}</p>
		</c:forEach>
		</p>
		<p>Number of orders: ${fn:length(user.orders)}</p>
	<a href="./drinks">Continue shopping</a>
</body>
</html>