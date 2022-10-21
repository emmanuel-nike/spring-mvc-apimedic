<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Emmanuel N">
    <title>MediCheck - <tiles:insertAttribute name="title" ignore="true" /></title>

    <link rel="canonical" href="/">

    <!-- Bootstrap core CSS -->
	<link href="<c:url value="/static/assets/plugins/bootstrap5/css/bootstrap.min.css" />" rel="stylesheet">
    
    <!-- Custom styles for this template -->
    <link href="<c:url value="/static/assets/css/navbar-top-fixed.css" />" rel="stylesheet">
  </head>
  <body>
    <tiles:importAttribute name="menu"/>
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
	  <div class="container-fluid">
	    <a class="navbar-brand" href="<c:url value="/" />">MediCheck</a>
	    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
	      <span class="navbar-toggler-icon"></span>
	    </button>
	    <div class="collapse navbar-collapse" id="navbarCollapse">
	      <ul class="navbar-nav me-auto mb-2 mb-md-0">
	        <li class="nav-item">
	          <a class="nav-link ${menu == 'home' ? 'active' : ''} }" href="<c:url value="/" />">Home</a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link ${menu == 'start-diagnosis' ? 'active' : ''}" href="<c:url value="/start-diagnosis" />">Start Diagnosis</a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link ${menu == 'appointments' ? 'active' : ''}" href="<c:url value="/appointments" />">Appointments</a>
	        </li>
	        
	      </ul>
	      <form class="d-flex">
	        <input class="form-control me-2" type="search" placeholder="Search" value="" aria-label="Search">
	        <button class="btn btn-outline-success" type="submit">Search</button>
	      </form>
	    </div>
	  </div>
	</nav>
	
	<main class="container">
		<tiles:insertAttribute name="body" />
	</main>

    <script src="<c:url value="/static/assets/plugins/bootstrap5/js/bootstrap.bundle.min.js" />"></script>
    <script src="<c:url value="/static/assets/plugins/axios/axios.min.js" />"></script>
    <script src="<c:url value="/static/assets/plugins/vuejs/vue.js" />"></script>
    <script>
    	const baseURL = "<c:url value="/" />";
    	console.log(baseURL);
    </script>
    <tiles:insertAttribute name="scripts" ignore="true" />
  </body>
</html>
    