<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
 <div class="bg-light p-5 rounded" id="vue-diagnosis">
   <h3>MediCheck</h3>
   <p class="lead">Get patients data and initiate initial diagnosis.</p>
   <form>
	   <div class="row mb-2">
	   		<div class="col-md-5">
	   			<div class="mb-3">
				  <label class="form-label">First Name</label>
				  <input class="form-control" placeholder="e.g. John" v-model="form_data.first_name">
				</div>
	   		</div>
	   		<div class="col-md-5">
	   			<div class="mb-3">
				  <label class="form-label">Last Name</label>
				  <input class="form-control" placeholder="e.g. Doe" v-model="form_data.last_name">
				</div>
	   		</div>
	   		<div class="col-md-2">
	   			<div class="mb-3">
				  <label class="form-label">Gender</label>
				  <select class="form-control" v-model="form_data.gender">
				  	<option value="0">Male</option>
				  	<option value="1">Female</option>
				  </select>
				</div>
	   		</div>
	   </div>
	   <div class="row mb-2">
	   		<div class="col-md-4">
	   			<div class="mb-3">
				  <label class="form-label">Email</label>
				  <input type="email" class="form-control" placeholder="example@mail.com" v-model="form_data.email">
				</div>
	   		</div>
	   		<div class="col-md-4">
	   			<div class="mb-3">
				  <label class="form-label">Phone</label>
				  <input class="form-control" placeholder="e.g. Doe" v-model="form_data.phone">
				</div>
	   		</div>
	   		<div class="col-md-4">
	   			<div class="mb-3">
				  <label class="form-label">Age</label>
				  <input type="number" min="1" max="200" class="form-control" placeholder="e.g. Doe" v-model="form_data.age">
				</div>
	   		</div>
	   </div>
	   <div class="row mb-2 sb-container">
	   		<span class="mb-2">Body Locations</span>
	   		<div class="col-md-3" v-for="bl in body_locations" :key="'body'+bl.ID">
	 			<div class="form-check">
				  <input class="form-check-input" type="checkbox" :value="bl.ID" v-model="form_data.body_locations" :disabled="diagnosis.length > 0" />
				  <label class="form-check-label" v-text="bl.Name"></label>
				</div>
	 		</div>
	   </div>
	   <div class="row mb-2 sb-container">
	   		<span class="mb-2">Symptoms</span>
	   		<div class="col-md-12 mb-3">
	   			<input v-model="search" placeholder="Search & filter" />
	   		</div>
	   		<div class="col-md-4" v-for="bl in symptoms" :key="'symptom'+bl.ID" v-if="filterSearch(bl.Name)">
	 			<div class="form-check">
				  <input class="form-check-input" type="checkbox" :value="bl.ID" v-model="form_data.symptoms" :disabled="diagnosis.length > 0" />
				  <label class="form-check-label" v-text="bl.Name"></label>
				</div>
	 		</div>
	   </div>
	   <div class="row mb-2 sb-container" v-if="diagnosis.length > 0">
	   		<span class="mb-2">Initial Diagnosis</span>
	   		<div class="col-md-12 table-responsive">
	   		<table class="table">
	   			<thead>
	   				<tr>
	   					<th>Valid</th>
	   					<th>Diagnosis</th>
	   					<!-- <th>ICD Name</th> -->
	   					<th>ICD</th>
	   					<th>Accuracy</th>
	   					<th>Specialisations</th>
	   				</tr>
	   			</thead>
	   			<tbody>
	   				<tr v-for="dg in diagnosis" :key="'diagnosis'+dg.Issue.ID">
	   					<td><input class="form-check-input" type="checkbox" :value="dg.Issue.ID" v-model="form_data.diagnosis" /></td>
	   					<td><span v-text="dg.Issue.ProfName"></span></td>
	   					<!-- <td><span v-text="dg.Issue.IcdName"></span></td> -->
	   					<td><span v-text="dg.Issue.Icd"></span></td>
	   					<td><span v-text="dg.Issue.Accuracy"></span></td>
	   					<td><span v-text="specializationReduce(dg.Specialisation)"></span></td>
	   				</tr>
	   			</tbody>
	   		</table>
	   		<a href="#" class="float-end" v-on:click="resetDiagnosis">Reset Diagnosis</a>
	   		</div>
	   </div>
   </form>
   <a class="btn btn-primary" href="#" v-on:click="getDiagnosis" role="button" v-if="diagnosis.length == 0">Get Diagnosis</a>
   <a class="btn btn-primary" href="#" v-on:click="getDiagnosis" role="button" v-else>Schedule Appointment</a>
   
 </div>