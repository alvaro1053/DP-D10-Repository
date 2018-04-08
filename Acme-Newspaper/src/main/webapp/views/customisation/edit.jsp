
<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<script>
    $(document).ready(function() {
        $("#list1").dynamiclist();
    });
</script>
<form:form action="customisation/admin/edit.do" modelAttribute="customisation">


	<jstl:forEach items = "${customisation.tabooWords}" var="word" varStatus="j" begin="0">
	
		<div class="list-item">
		
			<form:input path="tabooWords[${j.index}]"/>
			<a href="#" class="list-remove" onclick="event.preventDefault();">
				<spring:message code="customisation.removeWord"/>
			</a>
		</div>
		<br/>
	</jstl:forEach>
	
    <a href="#" class="list-add" onclick="event.preventDefault();"> <spring:message code="customisation.addWord"/> </a>

</form:form>