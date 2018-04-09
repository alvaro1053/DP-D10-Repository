<%--
 * 
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<table class="displayStyle" >


<tr>
<td class ="left-display"> <strong> <spring:message code="newspaper.title" /> : </strong> </td>
<td class="right-display">  <jstl:out value = "${newspaper.title}"/> &nbsp;  </td>
</tr>

<tr>
<td class ="left-display"> <strong> <spring:message code="newspaper.publicationDate" /> : </strong> </td>
<td class="right-display">  <jstl:out value = "${newspaper.publicationDate}"/> &nbsp;  </td>
</tr>

<tr>
<td class ="left-display"> <strong> <spring:message code="newspaper.description" /> : </strong> </td>
<td class="right-display">  <jstl:out value = "${newspaper.description}"/> &nbsp;  </td>
</tr>

<jstl:if test="${newspaper.pictureURL != null}">
<tr>
<td class ="left-display"> <strong> <spring:message code="newspaper.pictureURL" /> : </strong> </td>
<td class="right-display">  <img src="${newspaper.pictureURL}" width="auto" height="200"> &nbsp; </td>
</tr>
</jstl:if>

<tr>
<td class ="left-display"> <strong> <spring:message code="newspaper.articles" /> : </strong> </td>
<td class="right-display"> 

<jstl:choose>
<jstl:when test="${not empty newspaper.articles}"> 
<ul>
<jstl:forEach items="${newspaper.articles}" var="article">
<li> <jstl:out value = "${article.title}" /> &nbsp; </li>
</jstl:forEach>
</ul> 
</jstl:when>
<jstl:otherwise>

<spring:message code="newspaper.articles.empty" />

</jstl:otherwise>
</jstl:choose>

</td>
</tr>

<tr>
<td class ="left-display"> <strong> <spring:message code="newspaper.user" /> : </strong> </td>
<td class="right-display">  <jstl:out value="${newspaper.user.name}" /> &nbsp; </td>
</tr>


</table>