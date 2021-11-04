<%@ page import="com.ua.entity.Doctor" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="loc" uri="http://com.ua/FinalProject" %>

<head>
    <script>
        document.getElementById("defaultOpen").click();

        function openCity(evt, login) {

            var i, tabcontent, tablinks;
            tabcontent = document.getElementsByClassName("tabcontent");
            for (i = 0; i < tabcontent.length; i++) {
                tabcontent[i].style.display = "none";
            }
            tablinks = document.getElementsByClassName("tablinks");
            for (i = 0; i < tablinks.length; i++) {
                tablinks[i].className = tablinks[i].className.replace(" active", "");
            }
            document.getElementById(login).style.display = "block";
            evt.currentTarget.className += " active";
        }

        function openCity2(evt, cityName) {
            var i, tabcontent, tablinks;
            tabcontent = document.getElementsByClassName("tabcontent2");
            for (i = 0; i < tabcontent.length; i++) {
                tabcontent[i].style.display = "none";
            }
            tablinks = document.getElementsByClassName("tablinks2");
            for (i = 0; i < tablinks.length; i++) {
                tablinks[i].className = tablinks[i].className.replace(" active", "");
            }
            document.getElementById(cityName).style.display = "block";
            evt.currentTarget.className += " active";

            var acc = document.getElementsByClassName("accordion");
            var i;

            for (i = 0; i < acc.length; i++) {
                acc[i].addEventListener("click", function () {
                    this.classList.toggle("active");
                    var panel = this.nextElementSibling;
                    if (panel.style.maxHeight) {
                        panel.style.maxHeight = null;
                    } else {
                        panel.style.maxHeight = panel.scrollHeight + "px";
                    }
                });
            }
        }
    </script>
    <style>
        @import url(/users/css/doctorList_center2.css);
        @import url(/users/css/table_case_record.css);
        @import url(/users/css/newLogin_blank2.css);
        @import url(/users/css/list.css);
        @import url(/users/css/three_element.css);

    </style>
</head>
<body>
<ul id="nav">
    <li class="right"><p class="tablinks2" onclick="openCity2(event, 'Staff')" id="defaultOpen">
        <loc:print key="Employee"/></p></li>
    <li class="right"><p class="tablinks2" onclick="openCity2(event, 'Patient')">
        <loc:print key="Head_Button_Patient"/></p></li>
    <li class="right"><p class="tablinks2" onclick="openCity2(event, 'Archive')">
        <loc:print key="Head_Button_Archive"/></p></li>

    <li class="left"><p><a href="/controller?command=exit">
        <loc:print key="Head_Button_Exit"/></a></p></li>
    <c:forTokens items="${initParam['locales']}"
                 var="locale" delims=" ">
        <li class="left">
            <p>
                <a href="/changeLocale.jsp?localeToSet=${locale}&pageToForward=users/doctorList.jsp&basename=message_${locale}">
                    <img src="/${locale}.png" width="20" height="20"></a></p>
        </li>
    </c:forTokens>
    <li class="left"><p>
        <loc:print key="Head_Button_Login"/>${globalLogin}</p></li>
    <li class="left"><p><a href="/controller?command=viewStaff">
        <loc:print key="Head_Button_Refresh"/></a></p></li>
</ul>
<div class="letter">
    <div class="login-page">
        <div class="form">
            <div id="Staff" class="tabcontent2">
                <div class="three"><h1><loc:print key="Employee"/></h1></div>
                <hr class="hr-shadow">
                <h2 align="center"><loc:print key="Head_Button_Doctor"/></h2>
                <div class="button">
                    <p align="left">
                    <form class="button" action="/controller" method="get">
                        <select name="sort" class="selcls">
                            <option value="name"><loc:print key="Sorted_By_Name"/></option>
                            <option value="count"><loc:print key="Sorted_By_Patients"/></option>
                        </select>
                        <select name="categorize" class="selcls">
                            <option value="all" selected><loc:print key="All"/></option>
                            <c:forEach items="${departments}" var="entry">
                                <option value="${entry.getDescription()}">${entry.getDescription()}</option>
                            </c:forEach>
                        </select>
                        <button class="shine-button" type="submit" name="command" value="sortDoctorList" width="100%">
                            <loc:print key="Sorted"/>
                        </button>
                        <button class="shine-button" type="submit" name="command" value="redirect" width="100%">
                            <loc:print key="Add_Employee"/>
                        </button>
                        <input name="address" value="users/newLogin.jsp" hidden>
                    </form>
                    </p>
                </div>
                <table align="center">
                        <tr class="table1">
                            <th class="table1" width="5%">id</th>
                            <th class="table1" width="15%"><loc:print key="Login"/></th>
                            <th class="table1" width="22%"><loc:print key="Name_Anketa"/></th>
                            <th class="table1" width="22%"><loc:print key="Surname_Anketa"/></th>
                            <th class="table1" width="26%"><loc:print key="Department_Anketa"/></th>
                            <th class="table1"><loc:print key="Details"/></th>
                        </tr>
                        <c:forEach items="${doctors}" var="entry" varStatus="i">
                            <tr>
                                <th class="table1">${i.count}</th>
                                <th class="table1">${entry.getLogin()}</th>
                                <th class="table1">${entry.getName()}</th>
                                <th class="table1">${entry.getSurname()}</th>
                                <th class="table1">${entry.getDepartment()} (${entry.getCaseRecords().size()} -
                                    <loc:print key="Patient_Anketa"/>)
                                </th>
                                <th class="table1">
                                <a href="/controller?command=redirect&address=users/anketaDoctor.jsp&loginDoctor=${entry.getLogin()}">
                                    <loc:print key="Details"/></a>
                                </th>
                            </tr>
                        </c:forEach>
               </table>
                <hr class="hr-shadow">
                <h2 align="center"><loc:print key="Head_Button_Nurse"/></h2>
                <table align="center">
                        <tr class="table1">
                            <th class="table1" width="5%">id</th>
                            <th class="table1" width="15%"><loc:print key="Login"/></th>
                            <th class="table1" width="22%"><loc:print key="Name_Anketa"/></th>
                            <th class="table1" width="22%"><loc:print key="Surname_Anketa"/></th>
                            <th class="table1" width="26%"><loc:print key="Department_Anketa"/></th>
                            <th class="table1"><loc:print key="Details"/></th>
                        </tr>
                        <c:forEach items="${nurses}" var="entry" varStatus="i">
                           <tr>
                               <th class="table1">${i.count}</th>
                               <th class="table1">${entry.getLogin()}</th>
                               <th class="table1">${entry.getName()}</th>
                               <th class="table1">${entry.getSurname()}</th>
                               <th class="table1"><loc:print key="Head_Button_Nurse"/></th>
                               <th class="table1">
                                   <a href="/controller?command=redirect&address=users/anketaNurse.jsp&loginNurse=${entry.getLogin()}">
                                       <loc:print key="Details"/></a>
                               </th>
                           </tr>
                        </c:forEach>
                </table>
                <hr class="hr-shadow">
            </div>
            <div id="Patient" class="tabcontent2">
                <div class="three"><h1><loc:print key="Head_Button_Patient"/></h1></div>
                <hr class="hr-shadow">
                <form class="button" action="/controller" method="get">
                    <select name="sort2" class="selcls">
                        <option value="sortByName"><loc:print key="Sorted_By_Name"/></option>
                        <option value="sortByBirthday"><loc:print key="Sorted_By_Age"/></option>
                    </select>
                    <button class="shine-button" type="submit" name="command" value="sortPatientList" width="100%">
                        <loc:print key="Sorted"/>
                    </button>
                    <button class="shine-button" type="submit" name="command" value="redirect" width="100%">
                        <loc:print key="Add_Patient"/>
                    </button>
                    <input name="address" value="users/newPatient.jsp" hidden>
                </form>
                <table align="center">
                        <tr class="table1">
                            <th class="table1" width="5%">id</th>
                            <th class="table1" width="20%"><loc:print key="Name_Anketa"/> <loc:print key="Surname_Anketa"/></th>
                            <th class="table1" width="20%"><loc:print key="Birthday_Anketa"/></th>
                            <th class="table1" width="40%"><loc:print key="Provisional_Diagnosis"/></th>
                            <th class="table1"><loc:print key="Details"/></th>
                        </tr>
                        <c:forEach items="${patients}" var="entry" varStatus="i">
                            <tr>
                                <th class="table1">${i.count}</th>
                                <th class="table1">
                                        ${entry.getName()} ${entry.getSurname()}
                                </th>
                                <th class="table1">
                                        ${entry.getYearBorn()}/${entry.getMonthBorn()}/${entry.getDayBorn()}
                                    (${entry.getYears()})
                                </th>
                                <th class="table1">
                                        <c:forEach items="${entry.getCaseRecords()}" var="caseRecord">
                                        (${caseRecord.getInitialDiagnosis()})
                                        </c:forEach>
                                </th>
                                <th class="table1"><a href="/controller?command=redirect&address=users/anketaPatient.jsp&loginId=${entry.getId()}">
                                    <loc:print key="Details"/></a></th>
                            </tr>
                        </c:forEach>
                </table>
                <hr class="hr-shadow">
            </div>

            <div id="Archive" class="tabcontent2">
                <div class="three"><h1><loc:print key="Head_Button_Archive"/></h1></div>
                <hr class="hr-shadow">
                <table align="center">
                    <tr class="table1">
                        <th class="table1" width="5%">id</th>
                        <th class="table1" width="15%"><loc:print key="Passport_Anketa"/></th>
                        <th class="table1" width="20%"><loc:print key="Patient_Anketa"/></th>
                        <th class="table1" width="23%"><loc:print key="Provisional_Diagnosis"/></th>
                        <th class="table1" width="22%"><loc:print key="Final_Diagnosis"/></th>
                        <th class="table1"><loc:print key="Medical_Appointments"/></th>
                    </tr>
                    <c:forEach items="${archivePatient}" var="patient" varStatus="i">
                        <c:if test="${patient.getCaseRecords().size() != 0}">
                            <c:forEach items="${patient.getCaseRecords()}" var="cr">
                                <tr>
                                    <td class="table1">${i.count}</td>
                                    <td class="table1">${patient.getPassport()}</td>
                                    <td class="table1">${patient.getName()} ${patient.getSurname()}</td>
                                    <td class="table1">${cr.getInitialDiagnosis()}</td>
                                    <td class="table1">${cr.getFinalDiagnosis()}</td>
                                    <td class="table1"><a
                                            href="/controller?command=archiveAppointment&appointmentId=${cr.getId()}"><loc:print key="Details"/></a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </c:forEach>
                </table>
                <hr class="hr-shadow">
            </div>
        </div>
    </div>
</div>

<script>
    // Get the element with id="defaultOpen" and click on it
    document.getElementById("defaultOpen").click();
</script>
</body>
</html>