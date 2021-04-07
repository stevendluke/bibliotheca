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
    	<jsp:param value="login" name="active"/>
    </jsp:include>
	<div class="container login">
		<div class="col-lg-4 col-md-6 col-sm-10 col-xs-10">
		  <c:if test="${not empty loginError}">
		     <div class="alert alert-danger top-alert">${loginError}</div>
		  </c:if>
	      <form:form class="form-signin" role="form" method="post" action="login" modelAttribute="user">
	        <h2 class="form-signin-heading">Please sign in</h2>
	        <form:input path="email" type="email" class="form-control" placeholder="Email address" required="required" autofocus="true" />
	        <form:errors path="email" cssClass="error" />
	        <form:input path="password" type="password" class="form-control" placeholder="Password" required="required" />
	        <form:errors path="password" cssClass="error" />
	        <%--<form:label path="rememberMe" class="checkbox">
	          <form:checkbox path="rememberMe" /> Remember me
	        </form:label>--%>
	        <form:button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</form:button>
	      </form:form>
		</div>
    </div>
    <jsp:include page="include/footer.jsp"></jsp:include>
  </body>
</html>
