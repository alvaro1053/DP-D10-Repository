<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>


<div>
	<a href="/Acme-Newspaper"><img src="images/logo.png" alt="Sample Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="dashboard/admin/display.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
						
				</ul>
			</li>
		</security:authorize>
		
		
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message	code="master.page.user" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="chirp/user/followingChirps.do"><spring:message code="master.page.chirp.followingChirps" /> </a></li>
					<li><a href="user/user/list.do"><spring:message code="master.page.userList" /> </a></li>		
					<li><a href="user/user/followingList.do"><spring:message code="master.page.chirp.followingList" /> </a></li>
					<li><a href="user/user/followersList.do"><spring:message code="master.page.chirp.followersList" /> </a></li>
				</ul>
			</li>
		</security:authorize>
		
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="newspaper/list.do"><spring:message code="master.page.newspaper" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv" href="newspaper/user/list.do"><spring:message code="master.page.newspaper" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv" href="article/admin/list.do"><spring:message code="master.page.article" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="user/list.do"><spring:message code="master.page.userList" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
					<li><a href="chirp/user/create.do"><spring:message code="master.page.chirp.create" /> </a></li>
					<li><a href="user/user/displayUserProfile.do"><spring:message code="master.page.chirp.displayUserProfile" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

