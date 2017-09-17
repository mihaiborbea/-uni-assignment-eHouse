<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Info</title>
</head>
<body>

<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_menu.jsp"></jsp:include>

<h3>Hello: ${user.getFirstName()} ${user.getLastName()}</h3>

Email: <b>${user.getEmail()}</b>
<br />
Phone: ${user.getPhone()} <br />
<br />
<script>
    function myFunction() {
        if(confirm("Are you sure?") == true){document.location.href = '/deleteUser?userID=${user.getID()}'}
    }
</script>
<button onclick = "myFunction()">Delete account</button>
<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>