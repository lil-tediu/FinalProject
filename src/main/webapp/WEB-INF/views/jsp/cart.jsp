<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
     <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

  <link rel="stylesheet" href="css/style_Cart.css">
  
<jsp:include page="header.jsp"></jsp:include>

<script>
	document.getElementsByClassName("active")[0].setAttribute("class",
			"not-active");
	document.getElementsByTagName("LI")[7].setAttribute("class", "active");
</script>

<h1 class="header" align="center">YOUR ORDER SO FAR</h1>
<h2 class = "header" align = "center">Price so far: ${price}0 lv</h2>
	
	<c:choose>
    <c:when test="${fn:length(order.products) == 0}">
       <p>You have no products in the cart yet!</p>
        <br />
    </c:when>    
    <c:otherwise>
       <div class=products>
		<c:forEach items="${order.products}" var="entry">
			<div class="product">
			<h3>Product: ${entry.key.name}</h3>
				<div class="pictures">
				<img src="${entry.key.pictureUrl }" alt="PIZZA"
						class="img-responsive">
				</div>
			<p class = "price">Prize for one: ${entry.key.price}0 lv<p>
			<p>Quantity: ${entry.value}</p>
				<form method = "POST">
				<input type="hidden" name="chosen" value = "${entry.key.id}">
				<input class="button" type="submit" value="Remove"/>
				</form>
			</div>
		</c:forEach>
		</div>
        <br />
		<form action = "submitOrder" method="get">
			<input type = "submit" value=" Submit Order" class = "button1" >
		</form>
    </c:otherwise>
</c:choose>

		

</body>
</html>