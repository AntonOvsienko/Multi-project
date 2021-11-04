<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="currentLocale" 
	value="${param.localeToSet}"
	scope="session" />

<c:set var="basename"
	   value="${param.basename}"
	   scope="session" />

<%-- <jsp:forward page="index.jsp"/> --%>
<jsp:forward page="${param.pageToForward}"/>

