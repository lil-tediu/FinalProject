<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
     <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="header.jsp"></jsp:include>
<h1 class="header" align="center">YOUR PREVIOUS ORDERS</h1>

  <p>Orders:
		<c:forEach items="${ordersAndAddresses}" var="entry">
		  <p>Products:</p>
			<c:forEach items = "${entry.key.products }" var = "product">
			<p> ${product.key.name }</p>
			<img src = "${product.key.pictureUrl }" />
			</c:forEach>
			<p>Price: ${entry.key.price}</p>
			<p>Address: ${entry.value }
		</c:forEach>
		</p>

</body>
</html>