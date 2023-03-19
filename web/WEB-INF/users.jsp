<%-- 
    Document   : users
    Created on : 10-Mar-2023, 10:37:21 PM
    Author     : Karsten Monteiro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,300;0,400;0,500;0,600;0,700;0,800;1,300;1,400;1,500;1,600;1,700;1,800&display=swap" rel="stylesheet">
    <title>Manage Users</title>
</head>
<style>
    table {
        border: 4px solid black;
    }
    th, tr, td {
        margin: 0;
        padding: 4px;
        border: 3px solid black;
        text-align: center;
    }
    th {
        border-top: none;
        border-bottom: none;
    }
    tr {
        padding: 0;
        border-bottom: none;
        border-left: none;
        border-right: none;
    }
    td {
        border-bottom: none;
    }
    .noborder-lr {
        border-left: none;
        border-right: none;
    }
    .noborder-r {
        border-right: none;
        border-left: 3px solid black;
    }
    .pad-lr {
        padding-left: 5px;
        padding-right: 5px;
    }
    .bold600 {
        font-weight: 600;
    }
    .bold500 {
        font-weight: 500;
    }
</style>
<body style="font-family: 'Montserrat', sans-serif; font-size-adjust: .6;">
    <h1>Manage Users</h1>
    <p>
        <c:if test="${message eq 'error'}"><span style="color: crimson; font-style: italic; font-size: 15px;"><b>Sorry, something went wrong. Please try again.</b></span></c:if>
        <c:if test="${message eq 'noUsers'}"><span class='bold600' style="color: crimson; font-style: italic; font-size: 15px;">No users found. Please add a user.</span></c:if>
        <c:if test="${message eq 'allUsersDeleted'}"><span class='bold600' style="color: crimson; font-style: italic; font-size: 15px;">All users have been deleted. Please add a user.</span></c:if>
    </p>
    <c:if test="${numUsers ne null}">
        <table>
            <tr>
                <th class="noborder-lr">Email</th>
                <th class="noborder-r pad-lr">First Name</th>
                <th class="noborder-r pad-lr">Last Name</th>
                <th class="noborder-r">Role</th>
                <th class="noborder-r"></th>
                <th class="noborder-r"></th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td class="noborder-lr">${user.email}</td>
                    <td class="noborder-r">${user.firstName}</td>
                    <td class="noborder-r">${user.lastName}</td>
                    <td class="noborder-r">${user.role.getRoleName()}</td>
                    <td class="noborder-r">
                        <a href="<c:url value='/user?action=edit'><c:param name="email" value="${user.email}"></c:param></c:url>" class='bold500' name="delete" style="color: blue; text-decoration: none;">Edit</a>
                    </td>
                    <td class="noborder-r">
                        <a href="<c:url value='/user?action=delete'><c:param name="email" value="${user.email}"></c:param></c:url>" class='bold500' name="delete" style="color: blue; text-decoration: none;">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <!-- allows to still be able to display the users table, even when caught in a try-catch -->
    <c:if test="${duplicateError ne null}">
        <table>
            <tr>
                <th class="noborder-lr">Email</th>
                <th class="noborder-r pad-lr">First Name</th>
                <th class="noborder-r pad-lr">Last Name</th>
                <th class="noborder-r">Role</th>
                <th class="noborder-r"></th>
                <th class="noborder-r"></th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td class="noborder-lr">${user.email}</td>
                    <td class="noborder-r">${user.firstName}</td>
                    <td class="noborder-r">${user.lastName}</td>
                    <td class="noborder-r">${user.role.getRoleName()}</td>
                    <td class="noborder-r">
                        <input type="hidden" name="action" value="edit">
                        <a href="user?email=${user.email}&action=edit" class='bold600' name="edit" style="color: blue; text-decoration: none;">Edit</a>
                    </td>
                    <td class="noborder-r">
                        <input type="hidden" name="action" value="delete">
                        <a href="user?email=${user.email}&action=delete" class='bold600' name="delete" style="color: blue; text-decoration: none;">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
<!------------
    ADD USER 
  ------------>
    <c:if test="${editUser eq null}">
        <h2>Add User</h2>
        <form action="user" method="post">
            <b class="bold600">Email:</b> <input type="text" name="email"><br>
            <b class="bold600">First Name:</b> <input type="text" name="firstname"><br>
            <b class="bold600">Last Name:</b> <input type="text" name="lastname"><br>
            <b class="bold600">Password:</b> <input type="password" name="password"><br>
            <b class="bold600">Role:</b> <select name="rolename">
                    <option>system admin</option>
                    <option>regular user</option>
                  </select><br>
            <input type="hidden" name="action" value="insert">
            <input type="submit" value="Add user">
        </form>
    </c:if>
    <c:if test="${message eq 'duplicateUser'}"><span style="color: crimson; font-style: italic; font-size: 13px;">This email is already in use. Please try again.</span></c:if>
    <c:if test="${message eq 'existing'}"><span style="color: crimson; font-style: italic; font-size: 13px;">One or more of the fields you entered is already in use. Please try again.</span></c:if>
    <c:if test="${message eq 'emptyField'}"><span style="color: crimson; font-style: italic; font-size: 13px;">Sorry, you entered an existing email. Please try again.</span></c:if>
<!-------------
    EDIT USER 
  ------------->
    <c:if test="${editUser ne null}">
        <h2>Edit User</h2>
        <form action="" method="post">
            <b class="bold600">Email:</b> ${editUser.email}<br>
            <b class="bold600">First Name:</b> <input type="text" name="firstname" value="${editUser.firstName}"><br>
            <b class="bold600">Last Name:</b> <input type="text" name="lastname" value="${editUser.lastName}"><br>
            <b class="bold600">Password:</b> <input type="password" name="editpassword"><br>
            <b class="bold600">Role:</b> <select name="rolename">
                    <option ${sysAdmin}>system admin</option>
                    <option ${regUser}>regular user</option>
                  </select><br>
            <input type="hidden" name="action" value="edit">
            <input type="submit" value="Update">
            <a href="/"><input type="button" value="Cancel"></a>
        </form>
    </c:if>
    ${invalidPassMsg}
    <!-- displays an error message, based on the error -->
    <c:if test="${error ne null}">
        ${error}
    </c:if>
</body>
</html>