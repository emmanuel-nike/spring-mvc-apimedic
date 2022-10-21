package com.emmanuelnike.controllers;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.emmanuelnike.models.Gender;
import com.emmanuelnike.models.HealthDiagnosis;
import com.emmanuelnike.models.HealthItem;
import com.emmanuelnike.services.DiagnosisClient;
import com.emmanuelnike.utils.CustomConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DiagnosisController {
	
	@RequestMapping(value = "api/body-locations", method = RequestMethod.GET)
	public @ResponseBody List<HealthItem> bodyLocations(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<HealthItem> healthItems = new ArrayList<>();
		try {
			DiagnosisClient _diagnosisClient = new DiagnosisClient();
			healthItems = _diagnosisClient.loadBodyLocations();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return healthItems;
	}
	
	
	@RequestMapping(value = "api/symptoms", method = RequestMethod.GET)
	public @ResponseBody List<HealthItem> symptoms(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<HealthItem> healthItems = new ArrayList<>();
		try {
			DiagnosisClient _diagnosisClient = new DiagnosisClient();
			healthItems = _diagnosisClient.loadSymptoms();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return healthItems;
	}
	
	@RequestMapping(value = "api/diagnosis", method = RequestMethod.POST)
	public @ResponseBody List<HealthDiagnosis> diagnosis(@RequestBody String data) throws Exception{
		JSONObject obj = new JSONObject(data);
		
		String first_name = (String) obj.get("first_name");
		String last_name = (String) obj.get("last_name");
		String age = (String) obj.get("age");
		Gender gender = CustomConverter.stringToGender((String) obj.get("gender"));
		List<Integer> body_locations = CustomConverter.convertJSONArrayToIntegerList(obj.getJSONArray("body_locations"));
		List<Integer> symptoms = CustomConverter.convertJSONArrayToIntegerList(obj.getJSONArray("symptoms"));
		
		List<HealthDiagnosis> healthDiagnosis = new ArrayList<>();
		
		try {
			System.out.println(data);
			DiagnosisClient _diagnosisClient = new DiagnosisClient();
			healthDiagnosis = _diagnosisClient.loadDiagnosis(symptoms, gender, Year.now().getValue() - Integer.valueOf(age));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return healthDiagnosis;
	}
}
