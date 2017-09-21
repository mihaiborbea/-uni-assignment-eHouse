<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<div style="background: #E0E0E0; height: 55px; padding: 5px;">
    <div style="float: left">
        <a href="${pageContext.request.contextPath}/" style="text-decoration: none; color: #000;"><h1>eHouse</h1></a>
    </div>

    <div style="float: right; padding: 10px; text-align: right;">

        <c:if test="${loggedUser != null}">
            Hello <a href="${pageContext.request.contextPath}/userInfo" style="text-decoration: none;"><b>${loggedUser.getFirstName()} ${loggedUser.getLastName()}</b></a><br>
            <a href="${pageContext.request.contextPath}/addPost">Add Post</a>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </c:if>
        <c:if test="${loggedUser == null}">
            <a href="${pageContext.request.contextPath}/login">Login</a>
            <a href="${pageContext.request.contextPath}/register">Register</a>
        </c:if>

    </div>

</div>