<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
    <link rel="stylesheet" type="text/css" href="../../resources/style.css">
</head>
<body>

<jsp:include page="_header.jsp"></jsp:include>

<h3>Home Page</h3>
<c:forEach items="${myPosts}" var="post" varStatus="status">
    <a href="/viewPost?postID=${post.getID()}" style="text-decoration: none; color: black">
    <div class="gallery">
            <img src="../../uploads/${images.get(status.index).getPath()}" width="300" height="200">
        <div class="desc" style="font-size: 20pt"><b>${post.getTitle()}</b></div>
        <div class="desc">Price: $${post.getPrice()}</div>
        <div class="desc">Address: ${post.getAddress()}</div>
        <div class="desc">City: ${post.getCity()}</div>
        <div class="desc">Country: ${post.getCountry()}</div>
    </div>
    </a>
</c:forEach>

</body>
</html>
