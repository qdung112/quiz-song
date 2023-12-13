<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 29/10/2023
  Time: 8:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/join" method="post">
    <input type="hidden" name="username" value="<%=request.getAttribute("username")%>">
    <input type="text" name="roomId">
    <button type="submit">Submit</button>
</form>
</body>
</html>
