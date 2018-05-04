<%@page import="java.util.Set"%>
<%@page import="com.dominos.model.products.Sauce"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


	<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
    <link rel="stylesheet" href="css/style_allDrinks.css">
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


<h1 class="header" align="center">SAUCES MENU</h1>
  <div class = "products">
	<c:forEach items="${sauces}" var="sauce">
		<div class="product">
			<div class="text">
				<h3>${sauce.name}</h3>
				<div class="pictures">
					<img src="${sauce.pictureUrl}" alt="SAUCE"
						class="img-responsive">
				</div>
				<p class="price">Price: ${sauce.price}0 lv</p>
				<div>
				<form method = "POST">
				<input type="checkbox" name="chosen" value = "${sauce.id}">Choose
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