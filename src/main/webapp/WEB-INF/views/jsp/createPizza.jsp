<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>    
<jsp:include page="header.jsp"></jsp:include>


<script> 
	document.getElementsByClassName("active")[0].setAttribute("class", "not-active");
	document.getElementsByTagName("LI")[1].setAttribute("class", "active");
</script>

<link rel="stylesheet" href="css/createPizza.css"/>

<f:form method="POST" commandName="newPizza">
<table>
    <tr>
    <td>
    <p class="header" >Choose your meat: </p>
<ul>
<f:checkboxes class="checkboxes" element="li" path="stringSupplements" items="${meat}"/>
</ul>
    </td>
     <td>
      <p class="header" >Choose your cheese: </p>
<ul>
<f:checkboxes class="checkboxes" element="li" path="stringSupplements" items="${cheese}"/>
</ul>
    </td>
    <td>
     <p class="header">Choose your spices: </p>
<ul>
<f:checkboxes class="checkboxes" element="li" path="stringSupplements" items="${spices}"/>
</ul>
    </td> 
    <td>
     <p class="header">Choose your vegetables: </p>
<ul>
<f:checkboxes class="checkboxes" element="li" path="stringSupplements" items="${vegetables}"/>
</ul>
    </td>
    <td>
     <p class="header">Choose your size: </p>
<ul>
<f:radiobuttons class="checkboxes" element="li" path="stringSize" items="${size}" checked="checked"/>
</ul>
    </td>
    <td>
     <p class="header">Choose your dough: </p>
<ul>
<f:radiobuttons class="checkboxes" element="li" path="stringDough" items="${dough}" checked="checked"/>
</ul>
    </td>
    <td>
     <p class="header">Choose your pizza sauce: </p>
<ul>
<f:radiobuttons class="checkboxes" element="li" path="stringPizzaSauce" items="${pizzaSauce}" checked="checked"/>
</ul>
    </td>
    </tr>
    <tr>
        <td colspan = "7" >
            <input class="button" type="submit" value="Create Pizza"/>
        </td>
    </tr>
</table>
</f:form>

<img src="${newPizza.pictureUrl}" class="pizza"/>

<img src="img/cook.png"  class = "cook"/>


</body>
</html>