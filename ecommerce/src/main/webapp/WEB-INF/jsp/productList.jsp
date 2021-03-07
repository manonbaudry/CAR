<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8" />
<title>Products</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
	<h1>Product list</h1>

	<c:if test="${not empty stock}">
		<FORM ACTION="addToCart" METHOD=GET modelAttribute="shoppingFormData">
			<ul>
				<c:forEach var="product" items="${stock}">
					<li>${product.getProduct().getName()}, 
						Quantity to command: <input type="number" name="${product.getProduct().getId()}" max="${product.getRemainingProducts()}" value="0">
					</li>
				</c:forEach>
			</ul>

			<INPUT TYPE=SUBMIT VALUE="Add to cart">
		</FORM>
	</c:if>
</body>
</html>