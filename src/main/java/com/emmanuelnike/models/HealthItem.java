package com.emmanuelnike.models;

import lombok.Data;

@Data
public class HealthItem
{
	/// <summary>
    /// Item ID
    /// </summary>
    public int ID;
    
    /// <summary>
    /// Item name
    /// </summary>
    public String Name;
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return super.toString()+":"+Name;
    }
}