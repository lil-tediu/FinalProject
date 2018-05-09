<%@page import="java.util.Set"%>
<%@page import="com.dominos.model.products.Drink"%>	

<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
	
	  <link rel="stylesheet" href="css/style_allDrinks.css">
		

	<jsp:include page="headerNotLogged.jsp"></jsp:include>

<script> 
	document.getElementsByClassName("active")[0].setAttribute("class", "not-active");
	document.getElementsByTagName("LI")[2].setAttribute("class", "active");
</script>

<h1 class="header" align="center">SAUCES MENU</h1>
  <div class = "products">
	<c:forEach items="${sauces}" var="sauce">
		<div class="product">
			<div class="text">
				<h3>${sauce.name}</h3>
				<div class="pictures">
					<img src="${sauce.pictureUrl}" alt="PIZZA"
						class="img-responsive">
				</div>
				<p class="price">Price: ${sauce.price}0 lv</p>
			</div>
		</div>
	</c:forEach>
  </div>
</body>
</html>