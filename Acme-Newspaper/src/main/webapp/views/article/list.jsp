
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



<!-- Buscar  -->

<form action="article${uri}/list.do" method="get">
	<spring:message code="article.search.placeholder" var="placeholder" />
	<input name="filter" placeholder="${placeholder}"/>
	<br />
<spring:message code="article.search" var="search" />
	<input type="submit"
		value="${search}" />

</form>


<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" name="articles"  requestURI="article/list.do" id="row">


	<security:authorize access="hasRole('USER')">
		<display:column>
			<jstl:if test="${principal.id == row.user.id}">
				<a href="article/user/edit.do?articleId=${row.id}"><spring:message code ="article.edit"/></a>
			</jstl:if>
		</display:column>
	</security:authorize>
	
	
	<security:authorize access="hasRole('ADMIN')">
		<spring:message code="article.confirm" var="confirmArticle"  />
		<display:column>
				<a href="article/admin/delete.do?articleId=${row.id}" onclick="return confirm('${confirmArticle}')"><spring:message code ="article.delete" /></a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('USER')">
		<jstl:if test="">
			<display:column>
				<a href="article/user/edit.do?articleId=${row.id}" ><spring:message code ="article.edit" /></a>
			</display:column>
		</jstl:if>
	</security:authorize>
	
	
	<spring:message code="article.title" var="titleHeader" />
	<display:column title="${titleHeader}"><a href="article/display.do?articleId=${row.id}"><jstl:out value="${row.title}"></jstl:out></a></display:column>
	
	<spring:message code="article.summary" var="summary" />
	<display:column property="summary" title="${summary}"/>
	
	<spring:message code="article.moment" var="moment" />
	<spring:message code="master.page.date.format" var="dateFormat" />
	<display:column property="moment" format="{0,date,${dateFormat}}" title="${moment}"/>
	
	<spring:message code="article.photosURL" var="picture" />
	<display:column  title="${picture}"> <jstl:forEach var="picture" items="${row.photosURL}"><img src="${picture}" height="150" width=auto /> </jstl:forEach></display:column>


</display:table>

	<security:authorize access="hasRole('USER')">
		<a href="article/user/create.do"> <spring:message code="article.create" /> </a>
	</security:authorize>