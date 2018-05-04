
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


<jsp:include page="headerNotLogged.jsp"></jsp:include>

<style>
.nameField {
	border: 1px solid red;
}

.errorField {
	color: solid red;
	background-color: red;
}
</style>
<div class="container">
	<div class="info">
		<h1>Login</h1>
	</div>
</div>
<div class="form">
	<div class="thumbnail">
		<img src="img/small_pizza.png" />
	</div>

	<sf:form class="login-form" method="post" modelAttribute="user">
		<sf:label path="email">Username:</sf:label>
		<sf:input path="email" name="email" />
		<sf:errors path="email" cssClass="nameField"
			cssErrorClass="errorField" />
		<br>
		<br>
		<sf:label path="password">Password:</sf:label>

		<sf:password path="password" name="password" />
		<sf:errors path="password" cssClass="nameField"
			cssErrorClass="errorField" />
		<br>
		<br>
		<button>login</button>
		<p class="message">
			Not registered? <a href="./index">Create an account</a>
		</p>
	</sf:form>
</div>
<script src="js/jquery.min.js"></script>



<script src="js/index.js"></script>




</body>

</html>
