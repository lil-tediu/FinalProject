<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:include page="header.jsp"></jsp:include>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>

<style>
.nameField {
	border: 1px solid red;
}

.errorField {
	color: solid red;
	background-color: red;
}

body {
	font-family: Arial;
	font-size: 17px;
	padding: 8px;
}

* {
	box-sizing: border-box;
}

.row {
	display: -ms-flexbox; /* IE10 */
	display: flex;
	-ms-flex-wrap: wrap; /* IE10 */
	flex-wrap: wrap;
	margin: 0 -16px;
}

.col-25 {
	-ms-flex: 25%; /* IE10 */
	flex: 25%;
}

.col-50 {
	-ms-flex: 50%; /* IE10 */
	flex: 50%;
}

.col-75 {
	-ms-flex: 75%; /* IE10 */
	flex: 75%;
}

.col-25, .col-50, .col-75 {
	padding: 0 16px;
}

.container {
	background-color: #f2f2f2;
	padding: 5px 20px 15px 20px;
	border: 1px solid lightgrey;
	border-radius: 3px;
}

input[type=text] {
	width: 100%;
	margin-bottom: 20px;
	padding: 12px;
	border: 1px solid #ccc;
	border-radius: 3px;
}

label {
	margin-bottom: 10px;
	display: block;
}

.icon-container {
	margin-bottom: 20px;
	padding: 7px 0;
	font-size: 24px;
}

.btn {
	background-color: #4CAF50;
	color: white;
	padding: 12px;
	margin: 10px 0;
	border: none;
	width: 100%;
	border-radius: 3px;
	cursor: pointer;
	font-size: 17px;
}

.btn:hover {
	background-color: #45a049;
}

a {
	color: #2196F3;
}

hr {
	border: 1px solid lightgrey;
}

span.price {
	float: right;
	color: grey;
}

/* Responsive layout - when the screen is less than 800px wide, make the two columns stack on top of each other instead of next to each other (also change the direction - make the "cart" column go on top) */
@media ( max-width : 800px) {
	.row {
		flex-direction: column-reverse;
	}
	.col-25 {
		margin-bottom: 20px;
	}
}
</style>


<script>
	document.getElementsByClassName("active")[0].setAttribute("class",
			"not-active");
	document.getElementsByTagName("LI")[6].setAttribute("class", "active");
</script>


<body>
	<div class="row">
		<div class="col-75">
			<div class="container">
				<form method="post">

					<div class="row">
						<div class="col-50">

							<h3>Change profile information:</h3>
							<h4>Please insert all fields:</h4>


							<label for="fname">First Name</label> <input type="text"
								id="fname" name="firstname" placeholder="Ivan" required>

							<label for="lname">Last Name</label> <input type="text"
								id="lname" name="lastname" placeholder="Petrov" required>

							<label for="email">E-mail</label> <input type="text" id="email"
								name="email" placeholder="ivanpetrov@gmail.com" required>

							<label for="password">Password</label> <input type="password"
								id="password" name="password" required> <br> <br>
							<label for="password2">Repeat password</label> <input
								type="password" id="password2" name="password2" required>

			

						</div>
					</div>


					<input type="submit" value="Change" class="btn">


				</form>
			</div>


		</div>
	</div>

	<script src="js/jquery.min.js"></script>
	<script src="js/index.js"></script>

</body>

</html>
