<%@page import="java.util.Set"%>
<jsp:include page="header.jsp"></jsp:include>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<link rel="stylesheet" href="css/addrStyle.css">

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/style_errors.css">

</head>

<body>

	<script>
		document.getElementsByClassName("active")[0].setAttribute("class",
				"not-active");
		document.getElementsByTagName("LI")[7].setAttribute("class", "active");
	</script>

	<div class="row">
		<div class="col-75">
			<div class="container">
				<form method="post">

					<div class="row">
						<div class="col-50">
							<h3>Billing Address</h3>



							<label for="adr"><i class="fa fa-address-card-o"></i>
								Address</label> <input type="text" id="adr" name="address"
								placeholder="ul.Bulgaria, 12" pattern="^[a-zA-Z0-9,.!? ]*$"
								required> <label for="city"><i
								class="fa fa-institution"></i> City</label> <input type="text" id="city"
								name="city" placeholder="Sofia" pattern="([A-z\s]){2,}" required>

							<div class="row">
								<div class="col-50">
									<label for="state">State</label> <input type="text" id="state"
										name="state" placeholder="Sofia" pattern="([A-z\s]){2,}"
										required>
								</div>
								<div class="col-50">
									<label for="zip">Zip</label> <input type="text" id="zip"
										name="zip" placeholder="1000" pattern="\d{4}" required>
								</div>
							</div>
						</div>



					</div>
					<bt> <c:if test="${not empty error}">
						<p class="errorField">${error}</p>

					</c:if> <input type="submit" value="Add address" class="btn">
				</form>

				<button type="button" class="btn"
					onclick="location.href='./viewaddresses'">View all
					addresses</button>

			</div>
		</div>

	</div>

</body>
</html>
