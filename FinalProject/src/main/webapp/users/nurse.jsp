<%@ page session="true" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="loc" uri="http://com.ua/FinalProject" %>
<html>
<head>
    <style>
        @import url(/users/css/nurse.css);
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
                <a href="/changeLocale.jsp?localeToSet=${locale}&pageToForward=users/nurse.jsp&basename=message_${locale}">
                    <img src="/${locale}.png" width="20" height="20"></a></p>
        </li>
    </c:forTokens>
    <li class="left"><p>
        <loc:print key="Head_Button_Login"/>${globalLogin}</p></li>
</ul>
<div class="letter">
    <div class="login-page">
        <div class="form">
            <div class="three"><h1><loc:print key="Patient_Case_Anketa"/></h1></div>
            <div class="p">
                Page ${page} of ${pageCount}

                |

                <c:choose>
                    <c:when test="${page - 1 > 0}">
                        <a href="page?page=${page-1}&pageSize=${pageSize}">Previous</a>
                    </c:when>
                    <c:otherwise>
                        Previous
                    </c:otherwise>
                </c:choose>

                <c:forEach var="p" begin="${minPossiblePage}" end="${maxPossiblePage}">
                    <c:choose>
                        <c:when test="${page == p}">${p}</c:when>
                        <c:otherwise>
                            <a href="page?page=${p}&pageSize=${pageSize}">${p}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:choose>
                    <c:when test="${page + 1 <= pageCount}">
                        <a href="page?page=${page+1}&pageSize=${pageSize}">Next</a>
                    </c:when>
                    <c:otherwise>
                        Next
                    </c:otherwise>
                </c:choose>
                |
                <form action="page" style='display:inline;'>
                    <select name="page">
                        <c:forEach begin="1" end="${pageCount}" var="p">
                            <option value="${p}" ${p == param.page ? 'selected' : ''}>${p}</option>
                        </c:forEach>
                    </select>
                    <input name="pageSize" value="${pageSize}" type="hidden"/>
                    <input type="submit" value="Go"/>
                </form>
                <form action="page" style='display:inline;'>
                    <input type="number" name="pageSize"
                           min="1" max="10"/>
                    <input name="page" value="${page}" type="hidden"/>
                    <input type="submit" value="count"/>
                </form>
            </div>
            <div align="center">
                <c:forEach items="${caseRecordList}" var="patient" varStatus="i">
                    <c:if test="${i.count>(page-1)*pageSize && i.count<= page*pageSize}">
                        <fmt:parseNumber var="count" type="number" value="0"/>
                        <c:forEach items="${patient.getDoctorAppointmentList()}" var="appointment">
                            <c:if test="${appointment.getComplete() == 'true'}">
                                <fmt:parseNumber var="count" type="number" value="${count+1}"/>
                            </c:if>
                        </c:forEach>
                        <button class="accordion">
                            <loc:print key="Head_Button_Patient"/>: ${patient.getPatient().getName()}
                                ${patient.getPatient().getSurname()}
                            (${patient.getInitialDiagnosis()}) |
                            <loc:print key="Head_Button_Doctor"/>: ${patient.getDoctor().getName()}
                                ${patient.getDoctor().getSurname()} |
                            <loc:print key="Medical_Appointments"/>:${patient.getDoctorAppointmentList().size()}
                            <c:if test="${count != '0'}">/
                                <span class="colortext">Выполненно:${count}</span>
                            </c:if>
                        </button>
                        <div class="panel" align="center">
                            <input type="hidden" name="id" value="${patient.getId()}"/>
                            <input type="hidden" name="pageSize" value="${pageSize}"/>
                            <form action="/controller" method="post" class="form">
                                <table align="center">
                                    <tr class="table">
                                        <th class="table" align="center" width="5%">id</th>
                                        <th class="table" width="20%"><loc:print key="Medical_Appointments"/></th>
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
                                                        <c:if test="${appointment.getType() != 'Операция'}">
                                                            <c:if test="${appointment.getType() != 'Терапия'}">
                                                                <input type="checkbox"
                                                                       name="appointment"
                                                                       value="${appointment.getId()}">
                                                            </c:if>
                                                        </c:if>
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
                                            <th class="table" colspan="4" class="table1" align="center">Нет назначений
                                                врача
                                            </th>
                                        </tr>
                                    </c:if>
                                </table>
                                <p align="center">
                                    <button type="submit" name="command" value="confirmNurseAppointment"
                                            onclick='return confirm("<loc:print key="Confirm"/>")'>
                                        <loc:print key="Performed"/>
                                    </button>
                                </p>
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