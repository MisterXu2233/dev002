<%--
  Created by IntelliJ IDEA.
  User: 111
  Date: 2022/3/23
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form method="post" action="/user/register1">
        <table align="center">
            <tr>
                <td>姓名</td>
                <td><input type="text" name="name"></td>
            </tr>
            <tr>
                <td>年龄</td>
                <td><input type="text" name="age"></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="reset" value="重置">
                    <input type="submit" value="注册">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
