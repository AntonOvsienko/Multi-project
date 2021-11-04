<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="loc" uri="http://com.ua/FinalProject" %>
<html>
<head>
    <title>Title</title>
    <style>
        @import url(/users/css/anketa.css);
    </style>
</head>
<body>
<ul id="nav">
    <li class="left"><p><a href="/controller?command=exit">
        <loc:print key="Head_Button_Exit"/></a></p></li>
    <c:forTokens items="${initParam['locales']}"
                 var="locale" delims=" ">
        <li class="left">
            <p>
                <a href="/changeLocale.jsp?localeToSet=${locale}&pageToForward=users/anketaNurse.jsp&basename=message_${locale}">
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
            <div class="three"><h1><loc:print key="Anketa"/></h1></div>
            <hr class="hr-shadow">
            <c:if test="${changeProfile != true}">
                <table align="center" width="100%">
                    <c:forEach items="${nurses}" var="entry">
                        <c:if test="${entry.getLogin() == loginNurse}">
                            <tr>
                                <th align="right"><loc:print key="Passport_Number_Empty"/></th>
                                <th><input name="passport" placeholder="${entry.getPassport()}"
                                           pattern="[А-Яа-яЁё]{2,2}\d{8,8}" type="text" readonly></th>
                                </p>
                            </tr>
                            <tr>
                                <th align="right"><loc:print key="Name_Anketa"/></th>
                                <th><input type="text" name="name" placeholder="${entry.getName()}"
                                           pattern="([А-Яа-яЁё]+)|([A-Za-z]+)" readonly></th>
                            </tr>
                            <tr>
                                <th align="right"><loc:print key="Surname_Anketa"/></th>
                                <th><input type="text" name="surname" placeholder="${entry.getSurname()}"
                                           pattern="([А-Яа-яЁё]+)|([A-Za-z]+)" readonly></th>
                            </tr>
                            <tr>
                                <th align="right"><loc:print key="Number_Telephone"/></th>
                                <th><input id="online_phone" name="telephone" type="tel" maxlength="50"
                                            pattern="[0-9]{12,12}"
                                            placeholder="${entry.getTelephone()}" readonly></th>
                            </tr>

                        </c:if>
                    </c:forEach>
                </table>
            </c:if>
            <form action="/controller" method="post">
            <c:if test="${changeProfile == true}">
                <table align="center" width="100%">
                    <c:forEach items="${nurses}" var="entry">
                        <c:if test="${entry.getLogin() == loginNurse}">
                            <tr>
                                <th align="right"><loc:print key="Passport_Number_Empty"/></th>
                                <th><input name="passport" placeholder="${entry.getPassport()}"
                                           value="${entry.getPassport()}"
                                           pattern="[А-Яа-яЁё]{2,2}\d{8,8}" type="text"></th>
                                </p>
                            </tr>
                            <tr>
                                <th align="right"><loc:print key="Name_Anketa"/></th>
                                <th><input type="text" name="name" placeholder="${entry.getName()}"
                                           value="${entry.getName()}" pattern="([А-Яа-яЁё]+)|([A-Za-z]+)"></th>
                            </tr>
                            <tr>
                                <th align="right"><loc:print key="Surname_Anketa"/></th>
                                <th><input type="text" name="surname" placeholder="${entry.getSurname()}"
                                           value ="${entry.getSurname()}" pattern="([А-Яа-яЁё]+)|([A-Za-z]+)"></th>
                            </tr>
                            <tr>
                            <th align="right"><loc:print key="Number_Telephone"/></th>
                            <th><input name="telephone" maxlength="50" value="${entry.getTelephone()}"
                                       pattern="[0-9]{12,12}"
                                       placeholder="${entry.getTelephone()}"></th>
                            </tr>
                            <tr>
                                <th align="right"><loc:print key="Password"/></th>
                                <th><input name="password" type="password" required
                                           value="${entry.getPassword()}"
                                           placeholder="${entry.getPassword()}"/></th>
                            </tr>
                            <tr>
                                <th align="right"><loc:print key="Confirm_Password"/></th>
                                <th><input name="password_repeat" type="password" required
                                           value="${entry.getPassword()}"
                                           placeholder="${entry.getPassword()}"></th>
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
            </c:if>
            <hr class="hr-shadow">

                <button class="shine-button" type="submit" name="command" value="deleteNurse" width="100%">
                    <loc:print key="Delete_Employee"/>
                </button>
                <c:if test="${changeProfile != true}">
                    <button class="shine-button" type="submit" name="command" value="updateNurse" width="100%">
                        <loc:print key="Edit"/>
                    </button>
                </c:if>
                <c:if test="${changeProfile == true}">
                    <button class="shine-button" type="submit" name="command" value="updateNurse" width="100%">
                        <loc:print key="Confirm"/>
                    </button>
                </c:if>
                <input name="loginNurse" value="${loginNurse}" hidden>
                <input name="address" value="users/anketaNurse.jsp" hidden>
            </form>
            </input>

            <c:if test="${messageFalse == 1}">
                <p style="color:RED"><loc:print key="Re_Password_Error"/></p>
            </c:if>
            <c:if test="${successfully == 'true'}">
                <h4 style="color:green" name="error" align="center" color="#ff0000" c><loc:print key="Login_Update"/></h4>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
