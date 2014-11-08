<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="activeClass" value=" class=\"active\"" scope="request" />
<c:choose>
	<c:when test="${param.active == 'add'}">
		<c:set var="addActive" value="${activeClass}" scope="request" />
	</c:when>
	<c:when test="${param.active == 'home'}">
		<c:set var="homeActive" value="${activeClass}" scope="request" />
	</c:when>
	<c:when test="${param.active == 'login'}">
		<c:set var="userActive" value=" active" scope="request" />
		<c:set var="loginActive" value="${activeClass}" scope="request" />
	</c:when>
</c:choose>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
  <div class="container">
<div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
    </div>
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li${homeActive}><a href="/">Library</a></li>
        <c:if test="${sessionScope.validated}">
        	<li${addActive}><a href="/add">Add Book</a></li>
        </c:if>
      </ul>
      <form class="navbar-form navbar-left" role="search" method="get" action="/search">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Search" name="s" value="${search}">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
      </form>
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown${userActive}">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">User <b class="caret"></b></a>
          <ul class="dropdown-menu">
          <c:choose>
          	<c:when test="${sessionScope.validated}">
          		<li><a href="/logout">Logout</a></li>
          	</c:when>
          	<c:otherwise>
          		<li${loginActive}><a href="/login">Login</a></li>
          	</c:otherwise>
          </c:choose>
          </ul>
        </li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container -->
</nav>
<div class="container">
	<a href="/"><img src="/resources/images/logo.jpg" /></a>
</div>