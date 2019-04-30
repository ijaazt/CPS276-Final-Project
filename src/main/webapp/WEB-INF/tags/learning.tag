<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="learning" type="model.Learning" %>
<%@attribute name="method" %>
<%@attribute name="buttonValue" %>
<%@attribute name="userId" required="true" %>

<form action="learning" method="post">
	<div class="form-row">
		<div class="col form-group">
			<input class="form-control" type="text" placeholder="category" name="category" value="${learning.category}">
		</div>
		<div class="col form-group">
			<input class="form-control" type="text" name="learning" placeholder="learning" value="${learning.learning}">
		</div>
		<div class="col form-group">
			<input class="form-control" type="date" name="date" placeholder="01/01/2001" value="${learning.date}">
		</div>
	</div>
	<input type="hidden" name="userId" value="${userId}">
	<input type="hidden" name="id" value="${learning.id}">
	<c:choose>
		<c:when test='${empty method}'>
				<button type="submit" name="method" value="PUT" class="btn btn-secondary">Edit</button>
				<button type="submit" name="method" value="DELETE" class="btn btn-danger">Delete</button>
		</c:when>
		<c:otherwise>
				<button type="submit" name="method" value="${method}" class="btn btn-primary">${buttonValue}</button>
		</c:otherwise>
	</c:choose>

</form>