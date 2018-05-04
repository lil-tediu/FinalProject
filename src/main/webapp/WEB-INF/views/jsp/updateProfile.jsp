<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:include page="header.jsp"></jsp:include>

<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>

<style>
.nameField {
	border: 1px solid red;
}

.errorField {
	color:  solid red;
	background-color: red;
}
</style>
<div class="container">
	<div class="info"></div>
</div>
<div class="form">

	<div class="thumbnail">
		<img src="img/small_pizza.png" />
	</div>

	<h3>Change profile information:</h3>
	<h4>Please insert all fields:</h4>
		
	<h5 style="text-transform:capitalize; text-align: center">
		
		<f:form commandName="user">				
			First name:<f:input path="firstName"/>
			<f:errors path="firstName" cssClass="error" style = "color:red"></f:errors><br>
			Last name:<f:input path="lastName"/>
			<f:errors path="lastName" cssClass="error" style = "color:red"></f:errors><br>
			E-mail:<f:input path="email"/>
			<f:errors path="email" cssClass="error" style = "color:red"></f:errors><br>
			Password:<f:password path="password" placeholder="At least 6 symbols"/>
			<f:errors path="password" cssClass="error" style = "color:red" ></f:errors><br>
			
			<input type="submit" value="Change">			
		</f:form>
	</h5>	

</div>
<script src="js/jquery.min.js"></script>
<script src="js/index.js"></script>
</body>

</html>
