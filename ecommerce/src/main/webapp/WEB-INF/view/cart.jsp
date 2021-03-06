
<jsp:useBean id="cart" scope="session" class="sessions.cart" />

<jsp:setProperty name="cart" property="*" />
<%
	cart.processRequest(request);
%>
<c:if test="${not empty cart}">

	<FONT size=5 COLOR="#CC0000"> <br> You have the following
		items in your cart:
		<ul>
			<c:forEach var="item" items="${cart}">
				<li>${item.name},${item.quantitie}</li>
			</c:forEach>
		</ul>

		<FORM ACTION="purchase" METHOD=POST>
			<INPUT TYPE=SUBMIT VALUE="Purchase">
		</FORM>
	</FONT>
</c:if>

</html>