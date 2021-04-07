<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <jsp:include page="include/head.jsp"></jsp:include>
  </head>
  <body>
    <jsp:include page="include/navigation.jsp">
    	<jsp:param value="deleted" name="active"/>
    </jsp:include>
	<div class="container deleted">
		<div class="col-lg-6">
		  <c:choose>
			  <c:when test="${deleteResult == 'success'}">
			  	<div class="alert alert-success top-alert">
			      <strong>Success!</strong> "${bookTitle}" has been removed from your library.
			    </div>
			  </c:when>
			  <c:when test="${deleteResult == 'failed'}">
			  	<div class="alert alert-danger top-alert">
			      <strong>Failure!</strong> Could not delete "${bookTitle}" from library.
			    </div>
			  </c:when>
			 </c:choose>
		</div>
    </div>
    <jsp:include page="include/footer.jsp"></jsp:include>
  </body>
</html>
