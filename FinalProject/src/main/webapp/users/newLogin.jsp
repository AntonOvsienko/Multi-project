<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="loc" uri="http://com.ua/FinalProject" %>
<html>
<head>
    <title>Title</title>
    <style>
        @import url(/users/css/newLogin_blank.css);
        .selcls {
            padding: 9px;
            margin: 0px 0px 15px;
            border: solid 1px Black;
            outline: 0;
            background: #f1f1f1;
            align-content: center;
            box-shadow: rgba(0, 0, 0, 0.1) 0px 0px 8px;
            -moz-box-shadow: rgba(0, 0, 0, 0.1) 0px 0px 8px;
            -webkit-box-shadow: rgba(0, 0, 0, 0.1) 0px 0px 8px;
            border-radius: 3px;
            width: 100%;
        }
    </style>
    <script type="text/javascript">

    </script>
</head>
<body>
<ul id="nav">
    <li class="left"><p><a href="/controller?command=exit">
        <loc:print key="Head_Button_Exit"/></a></p></li>
    <c:forTokens items="${initParam['locales']}"
                 var="locale" delims=" ">
        <li class="left">
            <p>
                <a href="/changeLocale.jsp?localeToSet=${locale}&pageToForward=users/newLogin.jsp&basename=message_${locale}">
                    <img src="/${locale}.png" width="20" height="20"></a></p>
        </li>
    </c:forTokens>
    <li class="left"><p>
        <loc:print key="Head_Button_Login"/>${globalLogin}</p></li>
    <li class="left"><p><a href="/controller?command=redirect&address=users/doctorList.jsp">
        <loc:print key="Head_Button_Return"/></a></p></li>
</ul>
<div class="letter">
    <div class="login-page">
        <div class="form">
            <form class="login-form" id="auth" action="/controller" method="post">
                <h2 align="center"><loc:print key="New_Login"/></h2>
                <hr class="hr-shadow">
                <c:choose>
                    <c:when test="${checkLogin == 'true'}">
                        <input name="command" value="checkNewLogin" hidden/>
                        <input name="newLogin" value="${newLogin}" required disabled type="text"/>
                        <input name="password" type="password" value="${password}" required disabled/>
                        <input name="password_repeat" type="password" value="${password_repeat}" required disabled
                               placeholder="confirm password"/>
                    </c:when>
                    <c:otherwise>
                        <input name="command" value="checkNewLogin" hidden/>
                        <input name="newLogin" required type="text" placeholder="<loc:print key="Login"/>"/>
                        <input name="password" type="password" required placeholder="<loc:print key="Password"/>"/>
                        <input name="password_repeat" type="password" required
                               placeholder="<loc:print key="Confirm_Password"/>"/>

                        <select name="role" class="selcls">
                            <option disabled><loc:print key="Select_Position"/></option>
                            <option value="doctor"><loc:print key="Head_Button_Doctor"/></option>
                            <option value="nurse"><loc:print key="Head_Button_Nurse"/></option>
                        </select>
                    </c:otherwise>
                </c:choose>
                <c:if test="${checkLogin != 'true'}">
                    <button type="submit" name="command" value="checkNewLogin">
                        <loc:print key="Checked"/></button>
                </c:if>
            </form>
            <c:if test="${messageFalse == 1}">
                <p style="color:RED"><loc:print key="Re_Password_Error"/></p>
            </c:if>
            <c:if test="${messageFalse == 2}">
                <p style="color:RED"><loc:print key="Username_Is_Occupied"/></p>
            </c:if>

            <c:if test="${checkLogin == 'true'}">
                <hr class="hr-shadow">
                <form action="/controller" method="post">
                    <input name="newLogin" value="${newLogin}" hidden>
                    <input name="password" value="${password}" hidden>
                    <input name="role" value="${role}" hidden>
                    <p align="center"><input name="passport" placeholder="<loc:print key="Passport_Number"/>"
                                             pattern="[А-Яа-яЁё]{2,2}\d{8,8}" type="text" required>
                    </p>
                    <p align="center"><input type="text" name="name" placeholder="<loc:print key="Name_Anketa"/>"
                                             pattern="([А-Яа-яЁё]+)|([A-Za-z]+)" required></p>
                    <p align="center"><input type="text" name="surname" placeholder="<loc:print key="Surname_Anketa"/>"
                                             pattern="([А-Яа-яЁё]+)|([A-Za-z]+)" required></p>
                    <p align="center"><input id="online_phone" name="telephone" type="tel" maxlength="50"
                                             pattern="[0-9]{12,12}"
                                             placeholder="<loc:print key="Number_Telephone"/>"
                                             name="telephone"></p>
                    <c:if test="${role == 'doctor'}">
                        <p align="center"><select name="department" class="selcls">
                            <c:forEach items="${departments}" var="department">
                                <option value="${department.description}">${department.description}</option>
                            </c:forEach>
                        </select></p>
                    </c:if>
                    <button type="submit" name="command" value="createNewLogin"><loc:print key="Create"/></button>
                </form>
            </c:if>
            <c:if test="${error != null}">
                <h4 style="color:red" name="error" align="center" color="#ff0000" c>${error}</h4>
            </c:if>
            <c:if test="${successfully != null}">
                <h4 style="color:green" name="error" align="center" color="#ff0000" c>${successfully}</h4>
            </c:if>

        </div>
    </div>
</div>
</body>
</html>
