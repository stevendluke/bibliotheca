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
    	<jsp:param value="add" name="active"/>
    </jsp:include>
	<div class="container add">
		<div class="col-lg-6 col-md-8 col-sm-12 col-xs-12">
		  <c:choose>
			  <c:when test="${param.statusResult == 'success'}">
			  	<div class="alert alert-success top-alert">
			      <strong>Success!</strong> "${param.bookTitle}" has been added to your library.
			    </div>
			  </c:when>
			  <c:when test="${statusResult == 'failed'}">
			  	<div class="alert alert-danger top-alert">
			      <strong>Failure!</strong> Could not upload "${bookTitle}" to library.
			    </div>
			  </c:when>
		  </c:choose>
	      <form:form class="form-signin" role="form" method="post" action="add" modelAttribute="book">
	        <h2 class="form-signin-heading">Add a Book</h2>
	        
	        <div class="input-group isbn-group">
		      <form:input id="isbn" path="isbn" type="text" class="form-control" placeholder="ISBN" required="required" autofocus="true" />
		      <span class="input-group-btn">
		        <button id="lookup" class="btn btn-default" type="button">Lookup ISBN</button>
		      </span>
		    </div>
	        <form:errors path="isbn" cssClass="error" />
	        <div class="panel panel-default add-panel">
			  <div class="panel-heading">
			    <h3 class="panel-title">Choose</h3>
			  </div>
			  <div class="panel-body">
			  <div class="please-wait-panel">
			  	<span class="glyphicon glyphicon-time"></span>
			  	<span class="glyphicon-class">Please wait...</span>
			  </div>
			  <div class="no-data-panel">
			  	<div class="alert alert-danger alert-no-results">
				  	<span class="glyphicon glyphicon-ban-circle"></span>
				  	<span class="glyphicon-class">No Results</span>
				</div>
			  </div>
			  <div class="exists-panel">
			  	<div class="alert alert-danger alert-no-results">
				  	<span class="glyphicon glyphicon-ban-circle"></span>
				  	<span class="glyphicon-class">Book already exists in Library</span>
				</div>
			  </div>
			  <div class="display-books-panel">
				  <ul class="nav nav-pills nav-stacked book-select">
				  </ul>	
			  </div>
			  </div>
			</div>
			  
	        <form:input id="title" path="title" type="text" class="form-control" placeholder="Title" required="required" />
	        <form:errors path="title" cssClass="error" />
	        <form:input id="author" path="author" type="text" class="form-control" placeholder="Author" required="required" />
	        <form:errors path="author" cssClass="error" />
	        <div class="dropdown-container">
		        <div class="dropdown" style="float: left;">
		        	<a href="javascript:;" data-toggle="dropdown" type="button" class="btn btn-default data-toggle">Select Genre <span class="caret"></span></a>
		        	<ul class="dropdown-menu multi-level" role="menu">
		        		<c:forEach var="genreType" items="${genres}">
			        		<li>
			        			<a href="javascript:;" class="genreType">${genreType.name}</a>
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
		        	<a href="javascript:;" data-toggle="dropdown" type="button" class="btn btn-default data-toggle">Select Bookcase <span class="caret"></span></a>
		        	<ul class="dropdown-menu multi-level" role="menu">
		        		<c:forEach var="bookcaseItem" items="${bookcases}">
			        		<li class="dropdown-submenu">
			        			<a href="javascript:;" class="bookcase">${bookcaseItem.name}</a>
			        			<ul class="dropdown-menu">
			        				<c:forEach var="bookshelfItem" items="${bookcaseItem.shelves}">
				        				<li>
				        					<a href="javacript:;" class="bookshelf">${bookshelfItem}</a>
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
	        
	        <form:button class="btn btn-lg btn-primary btn-block" type="submit">Add to Library</form:button>
	      </form:form>
		</div>
    </div>
    <jsp:include page="include/footer.jsp"></jsp:include>
    <script type="text/javascript">
    	$(document).ready(function(){
    		
    		$('#isbn').keypress(function(e) {
    		    if(e.which == 13) {
    		    	event.preventDefault();
    		        lookupIsbn($(this).val());
    		    }
    		});
    		
    		$("#lookup").click(function(){
		        lookupIsbn($("#isbn").val());
    		});
    		
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
    	
    	function createListItem(title, author){
    		return "<li><a><span class=\"bookTitle\">" + title + "</span><br />by: <span class=\"bookAuthor\">" + author + "</span></a>"
    	}
    	
    	function lookupIsbn(isbn){
			$('#title').val("");
			$('#author').val("");
			$('.please-wait-panel').css("display", "block");
			$('.display-books-panel').css("display", "none");
			$('.no-data-panel').css("display", "none");
			$('.add-panel').css("display", "block");
			$('.exists-panel').css("display", "none");
			
    		$.getJSON("/lookup/" + isbn, function(data){
    			$('.book-select').html("");
    			
    			if(data["exists"] == true){
	    			$('.please-wait-panel').css("display", "none");
	    			$('.no-data-panel').css("display", "none");
	    			$('.exists-panel').css("display", "block");
    			}
    			else{
	    			if(data["success"] == false){
		    			$('.please-wait-panel').css("display", "none");
		    			$('.exists-panel').css("display", "none");
		    			$('.no-data-panel').css("display", "block");
	    			}
	    			else{
		    			if($.isArray(data["books"])){
			    			$.each(data["books"], function(i, item){
			    				$(".book-select").append(createListItem(item["title"], item["author"]));
			    			});
		    			}
		    			else{
		    				$(".book-select").append(createListItem(data["books"]["title"], data["books"]["author"]));
		    			}
		
		        		$('.book-select li').click(function(){
		        			if($(this).hasClass("active")){
		    	    			$('#title').val("");
		    	    			$('#author').val("");
		    	    			$(this).removeClass("active");
		        			}
		        			else{
		    	    			$('.book-select li').removeClass("active");
		    	    			$('#title').val($(this).find(".bookTitle").html());
		    	    			$('#author').val($(this).find(".bookAuthor").html());
		    	    			$(this).addClass("active");
		        			}
		        		});
		    			$('.please-wait-panel').css("display", "none");
		    			$('.exists-panel').css("display", "none");
		    			$('.display-books-panel').css("display", "block");
	    			}
    			}
    		});
    	}
    </script>
  </body>
</html>
