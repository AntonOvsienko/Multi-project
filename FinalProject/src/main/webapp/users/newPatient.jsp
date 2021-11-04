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
            border-radius: 1px;
            width: 100%;
        }
    </style>
    <script type="text/javascript">
        function showForm() {
            if (document.getElementById("diagnosis2").hidden == true) {
                document.getElementById("line2").hidden = false;
                document.getElementById("diagnosis2").hidden = false;
                document.getElementById("selectDoctor2").hidden = false;
            } else if (document.getElementById("diagnosis3").hidden == true) {
                document.getElementById("line3").hidden = false;
                document.getElementById("diagnosis3").hidden = false;
                document.getElementById("selectDoctor3").hidden = false;
            } else if (document.getElementById("diagnosis4").hidden == true) {
                document.getElementById("line4").hidden = false;
                document.getElementById("diagnosis4").hidden = false;
                document.getElementById("diagnosis5").hidden = false;
                document.getElementById("selectDoctor4").hidden = false;
            }
        }
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
                <a href="/changeLocale.jsp?localeToSet=${locale}&pageToForward=users/newPatient.jsp&basename=message_${locale}">
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
            <form action="/controller" method="post">
                <input name="command" value="addNewPatient" hidden>
                <div class="three"><h1><loc:print key="Patient_Case_Anketa"/></h1></div>
                <hr class="hr-shadow">
                <p align="center"><input name="passport" placeholder="<loc:print key="Passport_Number"/>"
                                         pattern="[А-Яа-яЁё]{2,2}\d{8,8}" type="text"
                                         required>
                </p>
                <p align="center"><input type="text" name="name" placeholder="<loc:print key="Name_Anketa"/>"
                                         pattern="([А-Яа-яЁё]+)|([A-Za-z]+)" required></p>
                <p align="center"><input type="text" name="surname" placeholder="<loc:print key="Surname_Anketa"/>"
                                         pattern="([А-Яа-яЁё]+)|([A-Za-z]+)" required></p>
                <p align="center"><input type="date" name="date" placeholder="<loc:print key="Birthday_Anketa"/>"/></p>
                <p align="center"><input id="online_phone" name="telephone" type="tel" maxlength="50"
                                         pattern="[0-9]{12,12}"
                                         placeholder="<loc:print key="Number_Telephone"/>"
                                         name="telephone"></p>
                <hr class="hr-shadow">
                <p align="center"><input type="text" name="diagnosis1" required
                                         placeholder="<loc:print key="Provisional_Diagnosis"/>"></p>
                <select name="selectDoctor1" class="selcls" required>
                    <option value="${null}" selected disabled><loc:print key="Choose_Doctor"/></option>
                    <c:forEach items="${doctors}" var="patient">
                        <option value="${patient.getId()}">${patient.getName()}
                                ${patient.getSurname()} - ${patient.getDepartment()}</option>
                    </c:forEach>
                </select>
                <hr class="hr-shadow" id="line2" hidden>
                <p id="diagnosis2" align="center" hidden>
                <input type="text" name="diagnosis2"
                       placeholder="<loc:print key="Provisional_Diagnosis"/>"></p>
                <select name="selectDoctor2" id="selectDoctor2" class="selcls" hidden>
                    <option value="${null}" selected disabled><loc:print key="Choose_Doctor"/></option>
                    <c:forEach items="${doctors}" var="patient">
                        <option value="${patient.getId()}">${patient.getName()}
                                ${patient.getSurname()} - ${patient.getDepartment()}</option>
                    </c:forEach>
                </select>
                <hr class="hr-shadow" id="line3" hidden>
                <p id="diagnosis3" align="center" hidden>
                <input type="text" name="diagnosis3"
                       placeholder="<loc:print key="Provisional_Diagnosis"/>"></p>
                <select name="selectDoctor3" class="selcls" id="selectDoctor3" hidden>
                    <option value="${null}" selected disabled><loc:print key="Choose_Doctor"/></option>
                    <c:forEach items="${doctors}" var="patient">
                        <option value="${patient.getId()}">${patient.getName()}
                                ${patient.getSurname()} - ${patient.getDepartment()}</option>
                    </c:forEach>
                </select>
                <hr class="hr-shadow" id="line4" hidden>
                <p id="diagnosis4" align="center" hidden>
                <input type="text" name="diagnosis4"
                       placeholder="<loc:print key="Provisional_Diagnosis"/>"></p>
                <select name="selectDoctor4" class="selcls" id="selectDoctor4" hidden>
                    <option value="${null}" selected disabled><loc:print key="Choose_Doctor"/></option>
                    <c:forEach items="${doctors}" var="patient">
                        <option value="${patient.getId()}">${patient.getName()}
                                ${patient.getSurname()} - ${patient.getDepartment()}</option>
                    </c:forEach>
                </select>
                <hr class="hr-shadow" id="line5" hidden>
                <p align="center" id="button_diagnosis"><input type="button" value="<loc:print key="Add_Diagnosis_Field"/>"
                                         onclick="showForm()"></p>
                <p align="center">
                    <button type="submit" name="command" value="addNewPatient">
                        <loc:print key="Create"/></button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>
