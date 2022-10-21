package com.emmanuelnike.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.emmanuelnike.services.AuthRequest;
import com.emmanuelnike.services.DiagnosisClient;

@Controller
//@EnableCaching
public class TestController {
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response)
	{
//		AuthRequest aRequest = new AuthRequest();
//		String resp = aRequest.makeAuthRequest();
//		System.out.println(resp);
		System.out.println(request.getRequestURL().toString());
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Home Page Object");
		mv.setViewName("welcome");
		
		return mv;
	}
	
	@RequestMapping(value = "start-diagnosis", method = RequestMethod.GET)
	public ModelAndView startDiagnosis(HttpServletRequest request, HttpServletResponse response)
	{
		DiagnosisClient _diagnosisClient;
		try {
			_diagnosisClient = new DiagnosisClient();
			_diagnosisClient.loadBodyLocations().toString();
			_diagnosisClient.loadSymptoms();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(request.getRequestURL().toString());
		ModelAndView mv = new ModelAndView();
		mv.setViewName("start-diagnosis");
		
		return mv;
	}
	
	@RequestMapping("/process-form")
	public void processForm()
	{
		System.out.println("got here");
	}

}
