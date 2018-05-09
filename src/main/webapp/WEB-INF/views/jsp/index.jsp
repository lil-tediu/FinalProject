<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:include page="headerNotLogged.jsp"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<link rel="stylesheet" href="css/style_errors.css">



<div class="container">
	<div class="info"></div>
</div>
<div class="form">

	<div class="thumbnail">
		<img src="img/small_pizza.png" />
	</div>
	<f:form class="login-form" modelAttribute="user" method="post">

		<h1>Register</h1>
		

		E-mail:<f:input path="email" />
		<f:errors path="email" cssClass="nameField" cssErrorClass="errorField" />
		<br>
		<br>
		
		Password:<f:input type="password" path="password" />
		<f:errors path="password" cssClass="nameField"
			cssErrorClass="errorField" />
		<br>
		<br>

		First name:<f:input path="firstName" />
		<f:errors path="firstName" cssClass="nameField"
			cssErrorClass="errorField" />
		<br>
		<br>

		Last name:<f:input path="lastName" />
		<f:errors path="lastName" cssClass="nameField"
			cssErrorClass="errorField" />

		<c:if test="${not empty error}">
			<p class="errorField">${error}</p>

		</c:if>

		<br>
		<br>

		<input type="submit" value="Register">

		<p class="message">
			Already registered? <a href="./login">Sign In</a>
		</p>
	</f:form>

</div>
<script src="js/jquery.min.js"></script>
<script src="js/index.js"></script>
</body>

</html>
