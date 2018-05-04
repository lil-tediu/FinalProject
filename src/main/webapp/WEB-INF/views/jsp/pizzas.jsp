<%@page import="java.util.Set"%>
<%@page import="com.dominos.model.products.Pizza"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


	<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
    <link rel="stylesheet" href="css/style_allPizzas.css">
<jsp:include page="header.jsp"></jsp:include>

  
<div class="container">
  <div class="info">
   
  </div>
</div>
<script>
	document.getElementsByClassName("active")[0].setAttribute("class",
			"not-active");
	document.getElementsByTagName("LI")[2].setAttribute("class", "active");
</script>


<h1 class="header" align="center">PIZZA MENU</h1>
  <div class = "products">
	<c:forEach items="${pizzas}" var="pizza">
		<div class="product">
			<div class="text">
				<h3>${pizza.name}</h3>
				<div class="pictures">
					<img src="${pizza.pictureUrl}" alt="PIZZA"
						class="img-responsive">
				</div>
				<p class="price">Price: ${pizza.price}0 lv</p>
				<p class="description">Ingredients: ${pizza.ingradients}</p>
				<div>
				<form method = "POST">
				<input type="checkbox" name="chosen" value = "${pizza.id}">Choose
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