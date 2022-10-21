package com.emmanuelnike.services;

public class AuthRequest {
	
	public String makeAuthRequest() {
		
		
			
			try {
				DiagnosisClient _diagnosisClient = new DiagnosisClient();
				
				return _diagnosisClient.loadBodyLocations().toString();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";
	}
}
