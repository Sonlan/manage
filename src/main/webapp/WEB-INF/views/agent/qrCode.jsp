<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/27
  Time: 10:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String key = (String) request.getAttribute("key");
    String mail = (String) request.getAttribute("mail");
%>
<html>
<head>
    <title>微信账户绑定</title>
</head>
<body>
    <div style="margin:10em auto;width:294px">
        <h1>账户激活成功，请扫码绑定微信账号</h1>
        <img src="<%= basePath%>agent/qrCode?key=<%= key%>&mail=<%= mail%>"/>
    </div>
</body>
</html>
