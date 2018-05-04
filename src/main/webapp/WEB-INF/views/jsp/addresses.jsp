<%@page import="java.util.Set"%>
<jsp:include page="header.jsp"></jsp:include>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>




<script>
	document.getElementsByClassName("active")[0].setAttribute("class",
			"not-active");
	document.getElementsByTagName("LI")[2].setAttribute("class", "active");
</script>



${sessionScope.user} 
<f:form commandName="address">
	Insert address<f:input path="address" />
	<input type="submit" value="Save address">

</f:form>
</body>
</html>