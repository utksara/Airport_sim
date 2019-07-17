package com.as.aicraft;

import com.as.airport.*;
import com.as.driver.Driver;

public class Aircraft {
	public int AircraftNumber;
	public AircraftType at=null;
	public int WaitTime;
	public boolean status;
	public String state;
	public int Gatetime = 0;
	public int Waittime = 0;
	
	
	public int getAircraftNumber() {
		return AircraftNumber;
	}
	public void setAircraftNumber(int aircraftNumber) {
		AircraftNumber = aircraftNumber;
	}
	public AircraftType getAt() {
		return at;
	}
	public void setAt(AircraftType at) {
		this.at = at;
	}
	public int getWaitTime() {
		return WaitTime;
	}
	public void setWaitTime(int waitTime) {
		WaitTime = waitTime;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public void goto_new_position() 
	{
		if(state == "S")
		{
		if(Gate.total_vacant_gates>0){
			state = "C";
			Gate.total_vacant_gates--;
			Gatetime = 0; 
		 }
		else
			Waittime++;
		}
		
		if(state == "C") {
			Gatetime++;
			if(Gatetime >= Driver.BoardingTime) {
			state = "E";
			Gate.total_vacant_gates++;
			}
		}
		
		if(state == "S")
		{
		if(Runway.total_vacant_runways>0){
			state = "X";
			Runway.total_vacant_runways--;
		 }
		else
			Waittime++;
		}
	}
		
}
