package com.as.driver;
import java.util.Scanner;

import com.as.airport.*;
public class Driver {
	static Scanner sc;
	public static int RunwayTime;
    public static int BoardingTime;
	public static Airport ap;
	public static Runway rn = new Runway();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int numberOfFlights;
		System.out.println("Enter Number of Flights:");
		sc = new Scanner(System.in);
		numberOfFlights = sc.nextInt();
		System.out.println("Enter Runway Time");
		RunwayTime = sc.nextInt();
		System.out.println("Enter Boarding Time:");
		BoardingTime = sc.nextInt();
		ap = null;
		int answer;
		answer = ap.getNumberOfEmergencyLandings(numberOfFlights,RunwayTime,BoardingTime);
		System.out.println(" Number of Emergency Landings :" + answer);
	}
}
