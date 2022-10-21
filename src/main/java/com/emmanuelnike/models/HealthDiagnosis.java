package com.emmanuelnike.models;

import java.util.List;

import lombok.Data;

@Data
public class HealthDiagnosis {
	/// <summary>
    /// Diagnosed issue
    /// </summary>
    public DiagnosedIssue Issue;
    
    /// <summary>
    /// List of suggested doctor specialisation for this issue
    /// </summary>
    public List<DiagnosedSpecialisation> Specialisation;
}
