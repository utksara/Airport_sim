package com.as.airport;
import java.io.*;
import java.util.*;
public class Runway {
	
	public static int total_vacant_runways = 1;
	public boolean status = false;
	public int RunwayNumber;
	public static int count = 1;
	public Runway()
	{
		status = false;
		RunwayNumber =  count;
		count++;		
	}
	public boolean getstatus() 
	{
		//this function will provide the status about whether runway is occupied or not. 
		return status;
	}
	public void changestatus()
	{
		if(status == true)
		{
			status = false;
		}
		else
			status = true;
	}
}