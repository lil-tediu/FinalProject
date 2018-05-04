<%@page import="java.util.Set"%>
<%@page import="com.dominos.model.products.Drink"%>	

<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
	
	  <link rel="stylesheet" href="css/style_allDrinks.css">
		

	<jsp:include page="header.jsp"></jsp:include>

<script> 
	document.getElementsByClassName("active")[0].setAttribute("class", "not-active");
	document.getElementsByTagName("LI")[2].setAttribute("class", "active");
</script>

<h1 class="header" align="center">DRINKS MENU</h1>
  <div class = "products">
	<c:forEach items="${drinks}" var="drink">
		<div class="product">
			<div class="text">
				<h3>${drink.name}</h3>
				<div class="pictures">
					<img src="${drink.pictureUrl}" alt="PIZZA"
						class="img-responsive">
				</div>
				<p class="price">Price: ${drink.price}0 lv</p>
				<div>
				<form method = "POST">
				<input type="checkbox" name="chosen" value = "${drink.id}">Choose
					<p class = "quantity"> Choose quantity: </p>
					<input type="number" name="quantity" min="1" max="5"> 
					 <input class="button" type="submit" value="Add to cart"/>
				</form>
				</div>
			</div>
		</div>
	</c:forEach>
  </div>
</body>
</html>