<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <jsp:include page="include/head.jsp"></jsp:include>
	<link rel="stylesheet" href="/resources/css/multilevel-dropdown.css">
  </head>
  <body>
    <jsp:include page="include/navigation.jsp">
    	<jsp:param value="edit" name="active"/>
    </jsp:include>
	<div class="container add">
		<div class="col-lg-6 col-md-8 col-sm-12 col-xs-12">
		  <c:choose>
			  <c:when test="${param.statusResult == 'success'}">
			  	<div class="alert alert-success top-alert">
			      <strong>Success!</strong> "${param.bookTitle}" has been updated.
			    </div>
			  </c:when>
			  <c:when test="${statusResult == 'failed'}">
			  	<div class="alert alert-danger top-alert">
			      <strong>Failure!</strong> Could not update "${bookTitle}".
			    </div>
			  </c:when>
		  </c:choose>
	      <form:form class="form-signin" role="form" method="post" action="/edit/${book.isbn}" modelAttribute="book">
	        <h2 class="form-signin-heading">Edit a Book</h2>
	        <h4>ISBN: ${book.isbn}</h4>
	        <form:input id="isbn" path="isbn" type="hidden" class="form-control" placeholder="ISBN" required="required" autofocus="true" />
		    <form:errors path="isbn" cssClass="error" />
	        <form:input id="title" path="title" type="text" class="form-control" placeholder="Title" required="required" />
	        <form:errors path="title" cssClass="error" />
	        <form:input id="author" path="author" type="text" class="form-control" placeholder="Author" required="required" />
	        <form:errors path="author" cssClass="error" />
	        <div class="dropdown-container">
		        <div class="dropdown" style="float: left;">
		        	<a data-toggle="dropdown" type="button" class="btn btn-default data-toggle">Select Genre <span class="caret"></span></a>
		        	<ul class="dropdown-menu multi-level" role="menu">
		        		<c:forEach var="genreType" items="${genres}">
			        		<li>
			        			<a class="genreType">${genreType.name}</a>
			        		</li>
			        	</c:forEach>
		        	</ul>
		        </div>
		        <div style="float: left; padding-top: 7px; padding-left: 10px;">
		        	<div style="float: left;"><span id="display-genreType"></span><form:input id="genreType" path="genreType" type="hidden" class="form-control" /><form:errors path="genreType" cssClass="error" /></div>
		        </div>
		        <div style="clear: both;"></div>
	        </div>
	        
	        <div class="dropdown-container">
		        <div class="dropdown" style="float: left;">
		        	<a data-toggle="dropdown" type="button" class="btn btn-default data-toggle">Select Bookcase <span class="caret"></span></a>
		        	<ul class="dropdown-menu multi-level" role="menu">
		        		<c:forEach var="bookcaseItem" items="${bookcases}">
			        		<li class="dropdown-submenu">
			        			<a class="bookcase">${bookcaseItem.name}</a>
			        			<ul class="dropdown-menu">
			        				<c:forEach var="bookshelfItem" items="${bookcaseItem.shelves}">
				        				<li>
				        					<a class="bookshelf">${bookshelfItem}</a>
				        				</li>
			        				</c:forEach>
			        			</ul>
			        		</li>
		        		</c:forEach>
		        	</ul>
		        </div>
		        <div style="float: left; padding-top: 7px; padding-left: 10px;">
		        	<div style="float: left;"><span id="display-bookcase"></span><form:input id="bookcase" path="bookcase" type="hidden" class="form-control" /><form:errors path="bookcase" cssClass="error" /></div> <div class="bookcase-level-two" style="float: left;">&nbsp;<span class="glyphicon glyphicon-chevron-right"></span>&nbsp;</div> <div class="bookcase-level-two" style="float: left;"><span id="display-bookshelf"></span><form:input id="bookshelf" path="bookshelf" type="hidden" class="form-control" /><form:errors path="bookshelf" cssClass="error" /></div>
		        </div>
		        <div style="clear: both;"></div>
	        </div>
	        <form:button class="btn btn-lg btn-primary btn-block" type="submit">Update Book</form:button>
	      </form:form>
		</div>
    </div>
    <jsp:include page="include/footer.jsp"></jsp:include>
    <script type="text/javascript">
    	$(document).ready(function(){
    		

    		$(".bookcase").mouseover(function(){
    			setValue("bookcase", $(this).html());
    			$(".bookcase-level-two").hide();
    		});
    		
    		$(".bookshelf").click(function(){
    			setValue("bookshelf", $(this).html());
    			$(".bookcase-level-two").show();
    		});
    		
    		$(".bookshelf").mouseover(function(){
    			setValue("bookshelf", $(this).html());
    			$(".bookcase-level-two").show();
    		});
    		
    		$(".genreType").mouseover(function(){
    			setValue("genreType", $(this).html());
    			$(".genre-level-two").hide();
    			$(".genre-level-three").hide();
    		});
    		
    		$(".genre").mouseover(function(){
    			setValue("genre", $(this).html());
    			$(".genre-level-two").show();
    			$(".genre-level-three").hide();
    		});
    		
    		$(".subGenre").click(function(){
    			setValue("subGenre", $(this).html());
    			$(".genre-level-three").show();
    		});
    		
    		$(".subGenre").mouseover(function(){
    			setValue("subGenre", $(this).html());
    			$(".genre-level-three").show();
    		});

    		populateIfSelected("genreType");
    		populateIfSelected("bookcase");
    		populateIfSelected("bookshelf");

    		displayIfPopulated("genre", "genre-level-two");
    		displayIfPopulated("subGenre", "genre-level-three");

    		displayIfPopulated("bookshelf", "bookcase-level-two");
    	});
    	
    	function setValue(name, val){
			$("#" + name).val(val);
			$("#display-" + name).html(val);
    	}
    	
    	function populateIfSelected(field){
    		var selected = $("#" + field).val();
    		
    		if(selected != ""){
    			$("#display-" + field).html(selected);
    		}
    	}
    	
    	function displayIfPopulated(fieldRef, classRef){
    		if($("#" + fieldRef).val() != "") $("." + classRef).show();
    	}
    </script>
  </body>
</html>
