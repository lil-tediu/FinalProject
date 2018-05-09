<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:include page="header.jsp"></jsp:include>
 <link rel="stylesheet" href="css/updateProfStyle.css">

<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>




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
								id="fname" name="firstname" placeholder="${user.firstName}" pattern="([A-z\s]){2,}" required>

							<label for="lname">Last Name</label> <input type="text"
								id="lname" name="lastname" placeholder="${user.lastName}" pattern="([A-z\s]){2,}" required>

							<label for="email">E-mail</label> <input type="text" id="email"
								name="email" placeholder="${user.email }" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required>
								
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
