<%@ page session="true" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="loc" uri="http://com.ua/FinalProject" %>
<html>
<head>
    <style>
        @import url(/users/css/doctor.css);
    </style>
    <script>
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
                <a href="/changeLocale.jsp?localeToSet=${locale}&pageToForward=users/doctor.jsp&basename=message_${locale}">
                    <img src="/${locale}.png" width="20" height="20"></a></p>
        </li>
    </c:forTokens>
    <li class="left"><p>Login:${globalLogin}</p></li>
</ul>
<div class="letter" align="center">
    <div class="login-page">
        <div class="form">
            <div class="three"><h1><loc:print key="Head_Button_Doctor"/></h1></div>
            <div align="center">
                <c:forEach items="${caseRecordList}" var="patient">
                    <c:if test="${patient.getDoctor().login == globalLogin}">
                        <fmt:parseNumber var="count" type="number" value="0"/>
                        <c:forEach items="${patient.getDoctorAppointmentList()}" var="appointment" varStatus="i">
                            <c:if test="${appointment.getComplete() == 'true'}">
                                <fmt:parseNumber var="count" type="number" value="${count+1}"/>
                            </c:if>
                        </c:forEach>
                        <button class="accordion">
                            <loc:print key="Patient_Anketa"/>: ${patient.getPatient().getName()}
                                ${patient.getPatient().getSurname()}
                            (${patient.getInitialDiagnosis()}) |
                            <loc:print key="Procedures"/>:${patient.getDoctorAppointmentList().size()}
                            <c:if test="${count != '0'}">
                                <span class="colortext"><loc:print key="Performed"/>:${count}</span>

                            </c:if>
                        </button>
                        <div class="panel" align="center">
                            <input type="hidden" name="id" value="${patient.id}"/>
                            <form action="/controller" method="post" class="form">
                                <input name="caseRecordId" value="${patient.id}" hidden>
                                <table align="center">
                                    <tr class="table">
                                        <th class="table" align="center" width="5%">id</th>
                                        <th class="table" width="20%"><loc:print key="Doctor's_Appointments"/></th>
                                        <th class="table" width="50%"><loc:print key="Details"/></th>
                                        <th class="table"><loc:print key="Complete"/></th>
                                    </tr>
                                    <c:forEach items="${patient.getDoctorAppointmentList()}" var="appointment"
                                               varStatus="i">
                                        <c:if test="${appointment != null}">
                                            <c:if test="${appointment.getComplete() != 'true'}">
                                                <tr class="table">
                                                    <td class="table" align="center">${i.count}</td>
                                                    <td class="table">${appointment.getType()}</td>
                                                    <td class="table">${appointment.getDescription()}</td>
                                                    <td class="table">
                                                        <input type="checkbox"
                                                               name="appointment"
                                                               value="${appointment.getId()}">
                                                    </td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${appointment.getComplete() == 'true'}">
                                                <div class="toggle-button">
                                                    <tr id="auth2" class="table1">
                                                        <td class="table" align="center">${i.count}</td>
                                                        <td class="table"><s>${appointment.getType()}</s></td>
                                                        <td class="table"><s>${appointment.getDescription()}</s></td>
                                                        <td class="table">
                                                                ${appointment.getNameStaffComplete()}
                                                        </td>
                                                    </tr>
                                                </div>
                                            </c:if>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${patient.getDoctorAppointmentList().size() == 0}">
                                        <tr class="table">
                                            <th class="table" colspan="4" class="table1" align="center">
                                                <loc:print key="No_Doctor's_Appointment"/>
                                            </th>
                                        </tr>
                                    </c:if>
                                </table>
                                <p align="center">
                                    <button type="submit" name="command" value="confirmAppointment"
                                            onclick='return confirm("<loc:print key="Confirm_Сompletion"/>")'>
                                        <loc:print key="Confirm"/>
                                    </button>
                                </p>
                            </form>
                            <form action="/controller" method="post">
                                <input name="command" value="dischargedHospital" hidden>
                                <input name="caseRecordId" value="${patient.getId()}" hidden>
                                <c:if test="${count == patient.getDoctorAppointmentList().size() &&
                patient.getDoctorAppointmentList().size() != 0}">
                                    <p align="center">
                                        <label><loc:print key="Confirm_Diagnisis"/>
                                            <input type="text" name="finalDiagnosis"
                                                   value="${patient.getInitialDiagnosis()}"/></label>
                                        <input type="submit" value="<loc:print key="Discharge"/>"
                                               onclick='return confirm
                                                       ("<loc:print key="Confirm_Discharge"/>")'/>
                                    </p>
                                </c:if>
                            </form>
                            <form id="auth" action="/controller" method="post">
                                <table class="table2" align="center">
                                    <input name="caseRecordId" value="${patient.id}" hidden>
                                    <input name="command" value="addAppointment" hidden>
                                    <tr class="table2">
                                        <th class="table2" align="center" width="5%">id</th>
                                        <th class="table2" width="20%"><loc:print key="Doctor's_Appointments"/></th>
                                        <th class="table2"><loc:print key="Details"/></th>
                                    </tr>
                                    <tr>
                                        <td class="table2" align="center">1</td>
                                        <td class="table2">
                                            <select name="select1">
                                                <option value="null" selected disabled><loc:print
                                                        key="Select_Action"/></option>
                                                <option value="Приём лекарств"><loc:print
                                                        key="Medication_Intake"/></option>
                                                <option value="Подготовка к операции"><loc:print
                                                        key="Preparing_Surgery"/></option>
                                                <option value="Операция"><loc:print key="Operation"/></option>
                                                <option value="Терапия"><loc:print key="Therapy"/></option>
                                            </select>
                                        </td>
                                        <td class="table2"><textarea name="description1" rows="1" cols="100"></textarea>
                                        </td>
                                    </tr>
                                    <tr class="table2" id="diagnosis2">
                                        <td class="table2" align="center">2</td>
                                        <td class="table2">
                                            <select name="select2">
                                                <option value="null" selected disabled><loc:print
                                                        key="Select_Action"/></option>
                                                <option value="Приём лекарств"><loc:print
                                                        key="Medication_Intake"/></option>
                                                <option value="Подготовка к операции"><loc:print
                                                        key="Preparing_Surgery"/></option>
                                                <option value="Операция"><loc:print key="Operation"/></option>
                                                <option value="Терапия"><loc:print key="Therapy"/></option>
                                            </select>
                                        </td>
                                        <td class="table2"><textarea name="description2" rows="1" cols="100"></textarea>
                                        </td>
                                    </tr>
                                    <tr class="table2" id="diagnosis3">
                                        <td class="table2" align="center">3</td>
                                        <td class="table2">
                                            <select name="select3">
                                                <option value="null" selected disabled><loc:print
                                                        key="Select_Action"/></option>
                                                <option value="Приём лекарств"><loc:print
                                                        key="Medication_Intake"/></option>
                                                <option value="Подготовка к операции"><loc:print
                                                        key="Preparing_Surgery"/></option>
                                                <option value="Операция"><loc:print key="Operation"/></option>
                                                <option value="Терапия"><loc:print key="Therapy"/></option>
                                            </select>
                                        </td>
                                        <td class="table2"><textarea name="description3" rows="1" cols="100"></textarea>
                                        </td>
                                    </tr>
                                    <tr class="table2" id="diagnosis4">
                                        <td class="table2" align="center">4</td>
                                        <td class="table2">
                                            <select name="select4">
                                                <option value="null" selected disabled><loc:print
                                                        key="Select_Action"/></option>
                                                <option value="Приём лекарств"><loc:print
                                                        key="Medication_Intake"/></option>
                                                <option value="Подготовка к операции"><loc:print
                                                        key="Preparing_Surgery"/></option>
                                                <option value="Операция"><loc:print key="Operation"/></option>
                                                <option value="Терапия"><loc:print key="Therapy"/></option>
                                            </select>
                                        </td>
                                        <td class="table2"><textarea name="description4" rows="1" cols="100"></textarea>
                                        </td>
                                    </tr>
                                </table>
                                <p align="center"><input type="submit" value="<loc:print key="Submit"/>"></p>
                            </form>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </div>
</div>


<script>
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
</script>
</body>
</html>