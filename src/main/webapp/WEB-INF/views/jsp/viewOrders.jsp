<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
     <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="header.jsp"></jsp:include>

<script>
	document.getElementsByClassName("active")[0].setAttribute("class",
			"not-active");
	document.getElementsByTagName("LI")[7].setAttribute("class", "active");
</script>

<h1 class="header" align="center">YOUR PREVIOUS ORDERS</h1>

  <h3 align= "center">Orders</h3>
		<c:forEach items="${ordersAndAddresses}" var="entry">
<%-- 		  <p>Products:</p>
			<c:forEach items = "${entry.key.products }" var = "product">
			<p> ${product.key.name }</p>
			<img src = "${product.key.pictureUrl }" />
			</c:forEach> --%>
			<div align = "center">
				<p>Price: ${entry.key.price}</p>
				<p>Date : ${entry.key.datetime }</p>
				<p>Address: ${entry.value }
			</div>
		</c:forEach>
</body>
</html>