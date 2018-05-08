<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
	
<jsp:include page="header.jsp"></jsp:include>
 <link rel="stylesheet" href="css/createPizzaStyle.css">

<script> 
	document.getElementsByClassName("active")[0].setAttribute("class", "not-active");
	document.getElementsByTagName("LI")[1].setAttribute("class", "active");
</script>


<div class="a">
<h1 class="b">You created a pizza</h1>
<h3>Your pizza is with: </h3>
<c:forEach items="${stringSupplements}" var="suplement">
	<p>${suplement}</p>
</c:forEach>
<h3>Your pizza is size: </h3>
<p>${newPizza.stringSize}</p>
<h3>Your pizza is with dough: </h3>
<p>${newPizza.stringDough}</p>
<h3>Your pizza is with pizza sauce: </h3>
<p>${newPizza.stringPizzaSauce}</p>
<h3>Total price for one: ${newPizza.price}</h3>
<form method ="post"  action="added">


	<h3>Choose quantity:</h3> <br>
<input type="number" name="quantity" min="1" max="10" required/> 
<br>
<input type = "hidden" name="id" value="${newPizza.id}">
<br>
<input type = "submit" value = "Add to cart"/>

</form>


</div>
</body>
</html>