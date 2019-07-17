package com.as.airport;

import com.as.aicraft.*;
import java.util.Scanner;

public class Airport {
   //int NumberOfFlights;
	//public int NumberOfEmergencyLandings;
	//public int NumberOfRunways;
	public static int TaxiTime=15;
	
	
	int getNumberOfGates()
	{
		//This function will determine number of optimal number of gates.
		
		
		return 0;
	}
	int getTaxiTime()
	{
		return TaxiTime;
	}
	
	int numberofflightspassed(int NumberOfGates, int NumberOfFlightsInParking, int NumberOfFlightsInAir) {
		
		return 0;
	}
	
	public int getNumberOfEmergencyLandings(int numberOfFlights, int runwayTime, int boardingTime) {
		int NumberOfEmergencyLandings = 0;
		int NumberOfGates = 1;
		int NumberOfRunways = 1;
		int NumberOfFlightsInParking = numberOfFlights - 1 ;
		int NumberOfFlightsInAir;
		
		
		Aircraft ac[] = new Aircraft[numberOfFlights];
		for(int i = 1; i< numberOfFlights;i++)
		{
			ac[i].AircraftNumber = i;
			ac[i].WaitTime = 0;
			ac[i].state = "S";
		}
		
		for(NumberOfGates = 1 ; NumberOfGates <= numberOfFlights; NumberOfGates++ )
		{
			for(NumberOfFlightsInAir = 1; NumberOfFlightsInAir <= numberOfFlights-1;NumberOfFlightsInAir++ )
			{
				NumberOfFlightsInParking = numberOfFlights - NumberOfFlightsInAir;
				
				for(int i = 1; i< NumberOfFlightsInParking;i++)
				{
					ac[i].AircraftNumber = i;
					ac[i].WaitTime = 0;
					ac[i].state = "B";
				}
				
				
				for (int time = 0; time<1400;time++) {
					
					for(int i = 1; i< numberOfFlights;i++) {
					ac[i].goto_new_position();
					if(ac[i].state == "X") {
						freedplane++;
					}
					
					}
				}
				
			}
		}
		
		
		
		
		
		return NumberOfEmergencyLandings;
		// TODO Auto-generated method stub
		
	}
	
}
