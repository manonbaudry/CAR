<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8" />
<title>Welcome</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
	<h1>${message}</h1>

	 <FORM ACTION="checkLogin" METHOD=GET modelAttribute="userFormData">
		Mail <INPUT type="text" NAME="mail" required autofocus>
		<P>
			Password <INPUT type="password" Name="password" required>
		<P>
			<INPUT TYPE=SUBMIT VALUE="Send">
	</FORM>


	<a href="${pageContext.request.contextPath}/inscription">Sign up</a>

</body>

</html>