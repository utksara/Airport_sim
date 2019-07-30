package com.as.Aircraft;

import java.util.LinkedList;

public class AircraftType {
	
	protected int noOfPlanes = 0;
	public int runwayTime; //This variable denotes runway time of each aircraft.
	public int boardingTime; //This variable denotes runway time of each aircraft.
	
	public AircraftType(int runwayTime,int boardingTime)
	{
		this.runwayTime=runwayTime;
		this.boardingTime=boardingTime;
	}

	public AircraftType() {
		runwayTime=0;
		boardingTime=0;		
	}

	public void createAircrafts(LinkedList<Aircraft> aircraftLinkedList, int noOfType) {
		
		for(int i=0;i<noOfType;i++)
		{
			Aircraft ac=new Aircraft(this);
			aircraftLinkedList.add(ac);
		}
		
	}
}
