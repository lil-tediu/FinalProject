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


<h1 class="header" align="center">Choose address for your order</h1>
  <div class = "products">
	<c:forEach items="${adresses}" var="address" > 
		<div class="product">
				<form method = "POST">
				<input type="hidden" name="chosenAddress" value = "${address.address_id}">${address.address}
					 <input type="submit" value="Choose address"/>
				</form>
		</div>
	</c:forEach>
   </div>
	
</body>
</html>