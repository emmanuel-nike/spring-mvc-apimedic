package com.emmanuelnike.models;

import lombok.Data;

@Data
public class AccessToken {
	
	/// <summary>
    /// Token string
    /// </summary>
	public String Token;
    
	/// <summary>
    /// Valid period of token in seconds
    /// </summary>
	public int ValidThrough;
}