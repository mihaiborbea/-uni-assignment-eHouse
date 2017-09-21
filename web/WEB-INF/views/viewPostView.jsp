<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Post</title>
</head>
<body>

<jsp:include page="_header.jsp"></jsp:include>

<h3>${post.getTitle()}</h3>
<img src="../../uploads/${image}" width="600" height="400">
<p>Phone: ${phone}</p>
<p>Price: $${post.getPrice()}</p>
<p>City: ${post.getCity()}$</p>
<p>Country: ${post.getCountry()}$</p>

<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>