package com.emmanuelnike.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.emmanuelnike.models.Gender;

public class CustomConverter {
	public static List<Integer> convertJSONArrayToIntegerList(JSONArray data){
		List<Object> objects = data.toList();
		List<Integer> ret = new ArrayList<>();
		for(int i = 0; i<objects.size(); i++) {
			ret.add((Integer) objects.get(i));
		}
		return ret;
	}
	
	public static Gender stringToGender(String gender) {
		System.out.println("Gender: "+gender);
		if(gender.equalsIgnoreCase("0")) return Gender.Male;
		if(gender.equalsIgnoreCase("1")) return Gender.Female;
		return Gender.Undefined;
	}
}
