<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
</head>
<body>

<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_menu.jsp"></jsp:include>

<h3>Register Page</h3>

<p style="color: red;">${errorString}</p>

<form method="POST" action="doRegister">
    <table border="0">
        <tr>
            <td>First Name</td>
            <td><input type="text" name="fname" value= "${user.getFirstName()}" /> </td>
        </tr>
        <tr>
            <td>Last Name</td>
            <td><input type="text" name="lname" value= "${user.getLastName()}" /> </td>
        </tr>
        <tr>
            <td>Email</td>
            <td><input type="text" name="email" value= "${user.getEmail()}" /> </td>
        </tr>
        <tr>
            <td>Phone</td>
            <td><input type="text" name="phone" value= "${user.getPhone()}" /> </td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="text" name="password" value= "${user.getPassword()}" /> </td>
        </tr>
        <tr>
            <td>Confirm Password</td>
            <td><input type="text" name="confpassword" value= "${user.getPassword()}" /> </td>
        </tr>
        <tr>
            <td colspan ="2">
                <input type="submit" value= "Submit" />
                <a href="${pageContext.request.contextPath}/"><button value="Cancel">Cancel</button></a>
            </td>
        </tr>
    </table>
</form>

<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>
