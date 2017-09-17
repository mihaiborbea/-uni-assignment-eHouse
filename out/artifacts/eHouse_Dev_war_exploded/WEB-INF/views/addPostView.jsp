<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Post</title>
</head>
<body>

<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_menu.jsp"></jsp:include>

<h3>Add Post Page</h3>

<p style="color: red;">${errorString}</p>

<form method="POST" action="doAddPost">
    <table border="0">
        <tr>
            <td>Title</td>
            <td><input type="text" name="title" value= "${post.getTitle()}" /> </td>
        </tr>
        <tr>
            <td>Address</td>
            <td><input type="text" name="address" value= "${post.getAddress()}" /> </td>
        </tr>
        <tr>
            <td>City</td>
            <td><input type="text" name="city" value= "${post.getCity()}" /> </td>
        </tr>
        <tr>
            <td>Country</td>
            <td><input type="text" name="country" value= "${post.getCountry()}" /> </td>
        </tr>
        <tr>
            <td>Price</td>
            <td><input type="text" name="price" value= "${post.getPrice()}" /> </td>
        </tr>
        <tr>
            <td>Image</td>
            <td><input type="file" name="image" accept="image/*"/> </td>
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
