<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 27/10/2023
  Time: 8:42 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        #modal-content{
            display: none;
        }
    </style>
</head>
<body>
<form action="/room/create" method="post">

<input name="username">
<button type="submit">Create Room</button>
</form>
<button id="join">Join</button>
<br><br>
<div id="modal-content">
    <form action="/join" method="post">
        <input type="text" name="username">
        <input type="text" name="roomId">
        <div>
                <button id="confirm" type="submit">Confirm</button>
                <button id="cancel" >Cancel</button>
        </div>
    </form>
</div>
</body>
<script>

 var modal = document.getElementById('modal-content');

 var joinBtn = document.getElementById('join');
 var confirmBtn = document.getElementById('confirm');
 var cancelBtn = document.getElementById('cancel');

 joinBtn.onclick = function () {
         modal.style.display = 'block';
 }

 cancelBtn.onclick = function () {
     modal.style.display = "none";
 }








</script>
</html>
