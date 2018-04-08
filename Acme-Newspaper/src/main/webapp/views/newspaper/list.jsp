

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<jstl:if test="${searching == true}"> 
<h2> <spring:message code="newspaper.results" />  </h2>
</jstl:if>

<spring:message code="newspaper.search" var="searchNewspapers"  />
 
<form action="newspaper/search.do" method="post" > 

<label for=""> <spring:message code="newspaper.search.keyword"/>:  </label>
<input type="text"  id="keyWord" name="keyword">
<br/>
<br/>

<input type="submit" name="save" value="${searchNewspapers}" >


</form>





<display:table pagesize="5" class="displaytag" 
	name="newspapers" requestURI="newspaper${uri}/list.do" id="row">
	

	
	<!-- title -->
	<spring:message code="newspaper.title"
		var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="true" />
	
	<!-- publicationDate -->
	<spring:message code="newspaper.publicationDate"
		var="publicationDateHeader" />
	<display:column property="publicationDate" title="${publicationDateHeader}"
		sortable="true" />	
		
	<!-- description -->
	<spring:message code="newspaper.description"
		var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"
		sortable="true" />
		
	<!-- pictureURL -->
	<spring:message code="newspaper.pictureURL" var="pictureHeader" />
	<spring:message code="newspaper.pictureError" var="pictureError" />
	<display:column title="${pictureHeader}" sortable="true" > <img src="${row.pictureURL}"  width="auto" height="200"></display:column> 

	<!-- isPrivate -->
		<spring:message code="newspaper.isPrivate"
		var="isPrivateHeader" />
	<display:column property="isPrivate" title="${isPrivateHeader}"
		sortable="true" />
		
	<!-- articles -->
	<spring:message code="newspaper.articles"
  	var="articles" />
	<display:column title="${articles}">
	<ul>
		<jstl:forEach items="${row.articles}" var="article"> 
			 <li>
				 <a href="article/display.do?articleId=${article.id}">
				 	<jstl:out value ="${article.title}" />
				 </a>
			 </li>
		</jstl:forEach>
	</ul>
	</display:column>
	

	<!-- Publisher -->
	<spring:message code="newspaper.user"
		var="userHeader" />
	<display:column title="${userHeader}" sortable="true" > 
		<a href="user${uri}/display.do?userId=${row.user.id}">
			<jstl:out value="${row.user.name} ${row.user.surname}"/>
		</a>
	</display:column>
	
	
	<display:column>
		<a href="newspaper${uri}/display.do?newspaperId=${row.id}"> <spring:message
			code="newspaper.display" />
		</a>
	</display:column>

		<display:column>
		<jsp:useBean id="now" class="java.util.Date"/>
			<jstl:if test="${principal.newspapers.contains(row) && row.publicationDate > now}">
		<a href="newspaper/user/publish.do?newspaperId=${row.id}"> <spring:message
			code="newspaper.publish" />
		</a>
		</jstl:if>
	</display:column>
	
<security:authorize access="hasRole('ADMIN')">
		<display:column>
		<a href="newspaper/admin/delete.do?newspaperId=${row.id}"> <spring:message
			code="master.page.delete" />
		</a>
	</display:column>
</security:authorize>
		
</display:table>

<security:authorize access="hasRole('USER')">
<a href="newspaper/user/create.do"> <spring:message
			code="newspaper.create" /> </a>
</security:authorize>
