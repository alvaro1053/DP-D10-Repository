<%--
 * edit.jsp
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

	<form:form action="article/user/edit.do" modelAttribute="articleForm">
	<form:hidden path="id" />
	<form:hidden path="version" /> 
	<form:hidden path="moment" />
	
	<acme:textarea code="article.title" path="title"/>
	<br />
	
	<acme:textbox code="article.summary" path="summary"/>
	<br />
	
	<acme:textbox code="article.body" path="body"/>
	<br />
	
<jstl:if test="${articleForm.id ==0 }">
	<!-- QUESTIONS -->
	
	
	<fieldset>

	
	<legend> <form:label path="photosURL"> <spring:message code="article.photosURL" />: </form:label> </legend>



		<div id="list1">
		<table class="displayStyle">
			<tr>
			<th>  <spring:message code="article.photosURL" /> :  </th>
			<th> </th>
			 </tr>
			
			<jstl:choose> 
			<jstl:when test="${empty articleForm.photosURL}">
			<tr class="list-item">
			
			
			
			<td>  	<form:input path="photosURL[0]" /> <form:errors cssClass="error" path="photosURL[0]" /></td>
			<td>	<a href="#" onclick="event.preventDefault();"
					class="list-remove"> <spring:message code="article.photosURL.remove" /> </a> </td>
			</tr>
			
			</jstl:when>
			<jstl:otherwise>
			<jstl:forEach items="${photosURL}" var="photoURL" varStatus="i" begin="0">
   			 <tr class="list-item">
			<td> <form:input path="photosURL[${i.index}]" /> <form:errors cssClass="error" path="photosURL[${i.index}]" /></td>
     		<td>	<a href="#" onclick="event.preventDefault();"
					class="list-remove"> <spring:message code="article.photosURL.remove" /> </a> </td>
	    </tr>
            <br />
        </jstl:forEach>
			</jstl:otherwise>
			</jstl:choose>
			
		</table>
		<a href="#" onclick="event.preventDefault();" class="list-add"><spring:message code="article.photosURL.add" /></a>
		</div>
		<br />
		<form:errors cssClass="error" path="photosURL" />

</fieldset>

</jstl:if>
	
	<spring:message code="article.isDraft"/>
	<form:checkbox path="isDraft" name="isDraft" value="true"/>
	<br />
	
	<acme:select items="${newspapers}" itemLabel="title" code="article.newspaper" path="newspaper"/>
	<br />
	
	<acme:submit name="save" code="master.page.save"/>
	<acme:cancel url="article/user/list.do" code="master.page.cancel"/>
	
	</form:form>
	
<script>
    $(document).ready(function() {
        $("#list1").dynamiclist();
    });
 </script>