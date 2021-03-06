<html>
<body>
	<h1>Product list</h1>

	<c:if test="${not empty lists}">

		<ul>
			<c:forEach var="listValue" items="${lists}">
				<li>${listValue}<input>Amount</input></li>
			</c:forEach>
		</ul>
		<FORM ACTION="CustomerService.find()" METHOD=POST>
			<INPUT TYPE=SUBMIT VALUE="Add to cart">
		</FORM>
	</c:if>
</body>


</body>
</html>