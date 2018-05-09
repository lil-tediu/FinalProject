
<%@page errorPage="error.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Dominos</title>

     <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">


<link rel='stylesheet prefetch'
	href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900'>
<link rel='stylesheet prefetch'
	href='https://fonts.googleapis.com/css?family=Montserrat:400,700'>
<link rel='stylesheet prefetch'
	href='https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>

<link rel="stylesheet" href="../css/style.css">
<link rel="stylesheet" href="../css/styles_drinks.css">
<link href="../css/style.default.css" rel="stylesheet"
	id="theme-stylesheet">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

 <link rel="stylesheet" href="../css/style_Cart.css">

<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />

<link rel="shortcut icon" type="image/png" href="../img/favicon.ico" />

</head>

<body background="../img/background_pizza.jpg">

	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="../"><img
					src="../img/small_pizza.png"
					style="width: 48px; height: 34px; border: 0;" position="relative"
					align='top' /></a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="active"><a href="../">Home <span
							class="sr-only">(current)</span></a></li>
					<li><a href="../create">Create Pizza </a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">Menu <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="../drinks">Drinks</a></li>
							<li><a href="../sauces">Sauces</a></li>
							<li><a href="../pizzas">Pizzas</a></li>
						</ul></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="../updateProfile">Profile</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">View detail <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="../addresses">Add new address</a></li>
							<li><a href="../orders">View previous orders</a></li>
							<li><a href="../cart">View Cart</a></li>
							<li role="separator" class="divider"></li>
							<li><a href="../logout">Logout</a></li>
						</ul></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>

	




<h1 class="header" align="center">YOUR ORDER FROM : ${order.datetime} </h1>
<h2 class="header" align="center">Price: ${order.price}0 lv</h2>

<div class=products>
	<c:forEach items="${order.products}" var="entry">
		<div class="product">
			<h3>Product: ${entry.key.name}</h3>
			<div class="pictures">
				<img src="../${entry.key.pictureUrl }" alt="PIZZA" class="img-responsive">
			</div>
			<p class="price">Prize for one: ${entry.key.price}0 lv
			<p>
			<p>Quantity: ${entry.value}</p>
		</div>
	</c:forEach>
</div>
<br />

<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

 <script>
	document.getElementsByClassName("active")[0].setAttribute("class",
			"not-active");
	document.getElementsByTagName("LI")[7].setAttribute("class", "active");
</script> 


</body>
</html>