<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
 <div class="bg-light p-5 rounded">
   <h1>MediCheck</h1>
   <p class="lead">This example is a quick exercise to consume the https://apimedic.com/ and diagnose patients. This is an integrated symptom checker that is effective and continuously enhanced.
   You can perform initial diagnosis and also schedule an appointment with a specialist doctor for the patient involved.</p>
   <a class="btn btn-lg btn-primary" href="<c:url value="/start-diagnosis" />" role="button">Start Diagnosis &raquo;</a>
 </div>