<%@ page session="true" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="loc" uri="http://com.ua/FinalProject" %>
<html>
<head>
    <style>
        @import url(/users/css/doctorAppointmentArchive.css);
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
                <a href="/changeLocale.jsp?localeToSet=${locale}&pageToForward=users/doctorAppointmentArchive.jsp&basename=message_${locale}">
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
            <div class="three"><h1><loc:print key="Medical_Appointments"/></h1></div>
            <hr class="hr-shadow">
            <table align="center">
                <tr class="table1">
                    <th class="table1" width="5%">id</th>
                    <th class="table1" width="15%">Тип</th>
                    <th class="table1" width="55%">Подробно</th>
                    <th class="table1" width="25%">Выполнил</th>
                </tr>
                <c:forEach items="${doctorAppointments}" var="appointment" varStatus="i">
                    <tr class="table1">
                        <td class="table1">${i.count}</td>
                        <td class="table1">${appointment.getType()}</td>
                        <td class="table1">${appointment.getDescription()}</td>
                        <td class="table1">${appointment.getNameStaffComplete()}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>