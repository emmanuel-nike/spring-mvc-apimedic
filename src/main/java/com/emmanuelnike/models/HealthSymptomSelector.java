package com.emmanuelnike.models;

import java.util.List;

public class HealthSymptomSelector extends HealthItem {
	
	///<summary>
    /// Professional name
    /// </summary>
	public String ProfName;
	
	/// <summary>
    /// Red flag indicator
    /// </summary>
    public Boolean HasRedFlag;

    /// <summary>
    /// List of sub location ids where this symptom is shown
    /// </summary>
    public List<Integer> HealthSymptomLocationIDs;

    /// <summary>
    /// Symptom synonyms (comma separated)
    /// </summary>
    public List<String> Synonyms;
}
