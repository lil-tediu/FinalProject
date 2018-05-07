<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <jsp:include page="header.jsp"></jsp:include>
  
<html>
<head>
 <meta name="viewport" content="width=device-width, initial-scale=1">
 <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title>View addresses</title>
</head>
<body>

<div class="w3-container">
<h1 class="header" align="center" >Addresses</h1>
  <script>
	document.getElementsByClassName("active")[0].setAttribute("class",
			"not-active");
	document.getElementsByTagName("LI")[2].setAttribute("class", "active");
</script>

<c:if test="${empty addresses}">
   <h4>There are no addresses yet</h4>
</c:if>

<div class="w3-ul w3-card-4">



<c:forEach items="${addresses}" var="address">
<div class="w3-bar">
      <img src="img/addrLogo.png" class="w3-bar-item w3-circle w3-hide-small" style="width:60px">
      <div class="w3-bar-item">
        <span class="w3-large">${address.address}</span><br>
        <form method = "POST">
				<input type="checkbox" name="chosen" style="display:none" checked="checked" value = "${address.address_id}">
				<input class="button" type="submit" value="Remove"/>
				</form>
      </div>
	
</div>

</c:forEach>

</div>
</div>
</body>
</html>