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

<h1 class="header" align="center">YOUR ORDER</h1>
	
	<c:choose>
    <c:when test="${fn:length(order.products) == 0}">
       <p>You have no products in the cart yet!</p>
        <br />
    </c:when>    
    <c:otherwise>
       <p>Order:
		<c:forEach items="${order.products}" var="entry">
			<p>Product: ${entry.key.name}</p>
			<p>Prize for one: ${entry.key.price}0 lv<p>
			<img src = "${entry.key.pictureUrl }" />
			<p>Quantity: ${entry.value}</p>
				<form method = "POST">
				<input type="hidden" name="chosen" value = "${entry.key.id}">
				<input class="button" type="submit" value="Remove"/>
				</form>
		</c:forEach>
		</p>
        <br />
        <p>
			Price: ${price}
		</p>	
		<form action = "submitOrder" method="get">
			<input type = "submit" value="submit" >
		</form>
    </c:otherwise>
</c:choose>

		

</body>
</html>