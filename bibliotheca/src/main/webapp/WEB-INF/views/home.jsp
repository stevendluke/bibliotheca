<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <jsp:include page="include/head.jsp"></jsp:include>
  </head>
  <body>
    <jsp:include page="include/navigation.jsp">
    	<jsp:param value="home" name="active"/>
    </jsp:include>
    
	<div class="container">
	    <h2>My Library</h2>
	    <c:choose>
	    	<c:when test="${fn:length(page.books) == 0}">
			    <div class="alert alert-warning top-alert">
			    	<strong>There are no books in the Library. Click on "<a href="/add"><strong>Add Book</strong></a>" to start adding books to your library.</strong>
			    </div>
		    </c:when>
		    <c:otherwise>
			    <c:forEach var="book" items="${page.books}">
			    	<div class="panel panel-primary">
		  				<div class="panel-heading">
		  					<h3 class="panel-title">${book.title}</h3>
		  				</div>
		  				<div class="panel-body">
					    	<p>by: ${book.author}<br />
					    	ISBN: ${book.isbn}
				    		<br />Genre: ${book.genreType}
				    		<br />Book Shelf: ${book.bookcase} <span class="glyphicon glyphicon-chevron-right"></span> ${book.bookshelf}						    
					    	</p>
				        	<c:if test="${sessionScope.validated}">
				        		<a class="btn btn-default btn-lg" href="/edit/${book.isbn}"><span class="glyphicon glyphicon-edit"></span> Edit</a>
				        		<button class="btn btn-default btn-lg pull-right" data-toggle="modal" data-target="#modal-${book.isbn}"><span class="glyphicon glyphicon-remove-sign"></span> Delete</button>
					        	<div class="modal fade" tabindex="-1" id="modal-${book.isbn}" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
								  <div class="modal-dialog modal-sm">
								    <div class="modal-content">
								        <div class="modal-header">
								          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">�</button>
								          <h4 class="modal-title" id="mySmallModalLabel">Delete "${book.title}"?</h4>
								        </div>
								        <div class="modal-body">
								        	<a class="btn btn-default btn-md" href="/delete/${book.isbn}">Yes</a>
								      		<button type="button" class="btn btn-default btn-md" data-dismiss="modal" aria-hidden="true">No</button>
								        </div>
								      </div>
								  </div>
								</div>
				        	</c:if>
		        		</div>
		        	</div>
			    </c:forEach>
		    </c:otherwise>
	    </c:choose>
		
	    <c:url var="firstUrl" value="?page=0" />
	    <c:url var="lastUrl" value="?page=${page.total}" />
	    <ul class="pagination pagination-lg">
		    <c:choose>
	            <c:when test="${page.current == 0}">
  					<li class="disabled"><a>&laquo;</a></li>
	            </c:when>
	            <c:otherwise>
	                <li><a href="${firstUrl}">&laquo;</a></li>
	            </c:otherwise>
	        </c:choose>
	        <c:forEach var="i" begin="${page.begin}" end="${page.end}">
	            <c:url var="pageUrl" value="?page=${i}" />
	            <c:choose>
	                <c:when test="${i == page.current}">
	                    <li class="active"><a href="${pageUrl}"><c:out value="${i+1}" /></a></li>
	                </c:when>
	                <c:otherwise>
	                    <li><a href="${pageUrl}"><c:out value="${i+1}" /></a></li>
	                </c:otherwise>
	            </c:choose>
	        </c:forEach>
	        <c:choose>
	            <c:when test="${page.current == page.total}">
	                <li class="disabled"><a>&raquo;</a></li>
	            </c:when>
	            <c:otherwise>
	                <li><a href="${lastUrl}">&raquo;</a></li>
	            </c:otherwise>
	        </c:choose>
		</ul>
	</div>
    <jsp:include page="include/footer.jsp"></jsp:include>
  </body>
</html>
