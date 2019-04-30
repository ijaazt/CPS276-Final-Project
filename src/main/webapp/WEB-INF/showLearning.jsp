<%@ taglib uri="http://muhammadtello.com" prefix="b" %>
	<h1 class="display-4 text-center mb">Add A Learning</h1>
<b:showLearning userId="${requestScope.userId}" method="POST" buttonValue="Add Learning"/>
<c:forEach items="${learningList}" var="learning">
	<b:showLearning learning="${learning}" userId="${requestScope.userId}"/>
</c:forEach>
