<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
	
<jsp:include page="header.jsp"></jsp:include>

<script> 
	document.getElementsByClassName("active")[0].setAttribute("class", "not-active");
	document.getElementsByTagName("LI")[1].setAttribute("class", "active");
</script>

<h3>You created a pizza</h3>
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
<p>${newPizza.price}</p>
<form method ="post"  action="added">
<input type = "checkbox" name="id" value="${newPizza.id }">Choose 
<input type = "submit" value = "Add to cart"/>
</form>



</body>
</html>