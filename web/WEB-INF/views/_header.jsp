<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<div style="background: #E0E0E0; height: 55px; padding: 5px;">
    <div style="float: left">
        <h1>My Site</h1>
    </div>

    <div style="float: right; padding: 10px; text-align: right;">

        <c:choose>
            <c:when test="${loggedUser.getEmail() != null}">
                Hello <b>${loggedUser.getEmail()}</b>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login">Login</a>
                <a href="${pageContext.request.contextPath}/register">Register</a>
            </c:otherwise>
        </c:choose>
        <br/>
        ${loggedUser.getEmail() != null ? "hello" : "bye"}
        Search <input name="search">

    </div>

</div>