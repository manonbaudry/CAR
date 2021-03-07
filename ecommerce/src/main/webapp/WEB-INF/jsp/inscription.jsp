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

	<FORM ACTION="CustomerService.find()" METHOD=POST>
		Mail <INPUT type="email" NAME="mail">
		<P>
			Password <INPUT type="password" Name="password">
		<P>
			<INPUT TYPE=SUBMIT VALUE="Send">
	</FORM>


	<a href="${pageContext.request.contextPath}/connection">Connect</a>
</body>
</html>