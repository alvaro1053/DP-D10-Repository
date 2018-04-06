
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table pagesize="5" class="displaytag" 
	name="followingChirps" requestURI="chirp/user/list.do" id="row">


	<!-- title -->
	<spring:message code="chirp.title"
		var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="true" />


	<!-- description -->
	<spring:message code="chirp.description"
		var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"
		sortable="true" />
		
	
	<!-- moment --> <!-- format para internacionalizacion -->
 	<spring:message code="chirp.format"
  	var="format" />
 	<spring:message code="chirp.moment"
  	var="momentHeader" />
 	<display:column property="moment" title="${momentHeader}"
  	sortable="true" format = "${format}"/>
  	
  	
  		<!-- description -->
	<spring:message code="chirp.user"
		var="userHeader" />
	<display:column property="user.userAccount.username" title="${userHeader}"
		sortable="true" />

</display:table>
