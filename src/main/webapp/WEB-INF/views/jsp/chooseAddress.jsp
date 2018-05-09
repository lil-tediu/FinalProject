<%@page import="java.util.Set"%>
<%@page import="com.dominos.model.products.Sauce"%>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<link rel="stylesheet" href="css/style_allDrinks.css">
<jsp:include page="header.jsp"></jsp:include>


<div class="w3-container">

<script>
	document.getElementsByClassName("active")[0].setAttribute("class",
			"not-active");
	document.getElementsByTagName("LI")[2].setAttribute("class", "active");
</script>

<div class="w3-ul w3-card-4">

<h1 class="header" align="center">Choose address for your order</h1>

	<c:forEach items="${adresses}" var="address">
		<div class="w3-bar">
		 <img src="img/addrLogo.png" class="w3-bar-item w3-circle w3-hide-small" style="width:60px">
			<div class="w3-bar-item">
				
				<form method="POST">
					<input type="hidden" name="chosenAddress"
						value="${address.address_id}">
						<span class="w3-large">${address.address}</span><br> <input
						type="submit" value="Choose address" />
				</form>
			</div>
		</div>
	</c:forEach>

</div>
</div>
</body>
</html>