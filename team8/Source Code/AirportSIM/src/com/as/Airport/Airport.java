package com.as.Airport;

import java.util.LinkedList;
import java.util.Arrays;

import com.as.Aircraft.*;
import com.as.Driver.*;


public class Airport {
	public int NumberOfGates;
	public int NumberOfEmergencyLandings;
	public static int NumberOfRunway = 1;
	public static int freed_flag=0;
	public static int TaxiTime = 15;
	public static double graphArray[][];

	public int getNumberOfGates(int flights, LinkedList<Aircraft> ll) {
		System.out.println("Running....");
		
		// This function will determine number of optimal number of gates.
		NumberOfGates = 1;

		//Aircraft[] ac = new Aircraft[flights];
		int g = 1, ng = flights - 1, time = 0, buffer = 7, scount = 0;
		int sums = 0, sume = 0, sumb = 0;
		boolean timeExceedFlag=false;
		int totalWaitsum[] = new int[flights];
		
		int optimal_gates = 0;

		boolean flag = true;
		int init_value =1;
		
		if(flights>10)
		{	LinkedList<Aircraft> lltemp = new LinkedList<Aircraft>();
		
			for (int i = 0; i<flights/2; i++) {
				lltemp.add(ll.get(i));
			}
			int X = getNumberOfGates( flights/2, lltemp);
			if(X>10)
			init_value =  X-10;
			else
				init_value =  X;
		}
		
		for (NumberOfGates =init_value; NumberOfGates <= flights; NumberOfGates++) 
		{
			LinkedList<Gate> gateLinkedList = new LinkedList<Gate>();
			int counter = 0;
			while (counter < NumberOfGates) {
				Gate G = new Gate();
				G.GateNumber = counter + 1;
				gateLinkedList.add(G);

				counter++;
			}
			LinkedList<Runway> runwayLinkedList = new LinkedList<Runway>();
			int runwayCounter = 0;

			for (; runwayCounter < NumberOfRunway; runwayCounter++) {
				Runway rw = new Runway();
				rw.runwayNumber = runwayCounter;
				runwayLinkedList.add(rw);
			}
			System.out.println("for total flights of  "+ flights+" and gates of : "+ NumberOfGates);
			// System.out.println("In number of gates loop");
			//for (g = 1; g <= 3 ; g++)
			for (g = flights - 1; g >= 1 ; g--)
			{
				ng = flights -g;
				// System.out.println("For "+NumberOfGates+" gates size of gate linked list
				// is"+gateLinkedList.size());
				// System.out.println("In configuration loop");

				int flightCounter = 0;
				while (flightCounter < NumberOfGates && flightCounter < g) {
					ll.get(flightCounter).state = "c";

					ll.get(flightCounter).gateNo = flightCounter;
					gateLinkedList.get(flightCounter).status = false;
					flightCounter++;
				}
				while (flightCounter < g) {
					ll.get(flightCounter).state = "b";

					flightCounter++;
				}
				while (flightCounter < flights && flightCounter < g + buffer) {
					ll.get(flightCounter).state = "s";

					scount++;
					flightCounter++;
				}
				while (flightCounter < flights) {
					ll.get(flightCounter).state = "z";

					flightCounter++;
				}

				time=0;
				while (true) {
		
					for (int i = 0; i < flights; i++) 
					{
						if (ll.get(i).state.equals("z")) {
							if (scount < buffer) {
								ll.get(i).state = "s";
							}
						}
						if (ll.get(i).state.equals("s")) {
							// System.out.println("State s");
							boolean flags = false;
							int j;
							for (j = 0; j < NumberOfRunway; j++) {

								for (int k = 0; k < ll.size(); k++) {
									for (int l = 0; l < ll.size() && l != k; l++) {
										if (ll.get(k).sWaitTime >= ll.get(l).sWaitTime
												&& ll.get(k).sWaitTime >= ll.get(l).eWaitTime
												&& runwayLinkedList.get(j).status == true) {
											{
												ll.get(i).state = "r'";
												ll.get(i).RunwayNumber = runwayLinkedList.get(j).runwayNumber;
												scount--;
												ll.get(i).sfwt += ll.get(i).sWaitTime;

												ll.get(i).sWaitTime = 0;
												runwayLinkedList.get(j).status = false;
												flags = true;
												break;
											}
										}
									}

								}
							}
							if (flags == false)
								ll.get(i).sWaitTime++;
						}

						if (ll.get(i).state.equals("r'")) 
						{
							// System.out.println("State r'");

							ll.get(i).rdstatetimer++;
							for (int j = 0; j < NumberOfRunway; j++) {
								if (runwayLinkedList.get(j).status == false) {

									runwayLinkedList.get(j).rdt++;

								}
								if (runwayLinkedList.get(j).rdt == ll.get(i).at.runwayTime) {
									runwayLinkedList.get(j).status = true;
									runwayLinkedList.get(j).rdt = 0;
								}
							}

							if (ll.get(i).rdstatetimer == ll.get(i).at.runwayTime) {
								ll.get(i).state = "a";
								runwayLinkedList.get(ll.get(i).RunwayNumber).status = true;

							}
						}

						else if (ll.get(i).state.equals("a")) 
						{
							// System.out.println("State a");

							ll.get(i).astatetimer++;
							if (ll.get(i).astatetimer == TaxiTime) {
								ll.get(i).state = "b";
							}
						} 
						else if (ll.get(i).state.equals("b")) 
						{
							// System.out.println("State b");

							boolean flagb = false;
							int j;
							for (j = 0; j < NumberOfGates; j++) 
							{
								if (gateLinkedList.get(j).status == true) 
								{
									ll.get(i).state = "c";
									ll.get(i).gateNo = j;
									gateLinkedList.get(j).status = false;
									flagb = true;
									break;
								}
							}
							if (flagb == false)
								ll.get(i).bWaitTime++;
						}

						if (ll.get(i).state.equals("c")) {
							// System.out.println("c case");
							ll.get(i).cstatetimer++;
							int j;
							/*
							 * for(j=0;j<NumberOfGates;j++) { if(gateLinkedList.get(j).status==false) {
							 * 
							 * gateLinkedList.get(j).gatetimer++;
							 * 
							 * } if(gateLinkedList.get(j).gatetimer==ll.get(i).at.boardingTime) {
							 * gateLinkedList.get(j).status=true; gateLinkedList.get(j).gatetimer=0; }}
							 */

							if (ll.get(i).cstatetimer == ll.get(i).at.boardingTime) {
								ll.get(i).state = "d";
								gateLinkedList.get(ll.get(i).gateNo).status = true;

								// System.out.println("Sate cganksdb");
							}
							// System.out.println("Exit c");
						}

						if (ll.get(i).state.equals("d")) {
							// System.out.println("State d");

							ll.get(i).dstatetimer++;
							if (ll.get(i).dstatetimer == TaxiTime)
								ll.get(i).state = "e";
						} else if (ll.get(i).state.equals("e")) {
							// System.out.println("State e");

							boolean flage = false;
							int j;
							for (j = 0; j < NumberOfRunway; j++) {

								for (int k = 0; k < ll.size(); k++) {
									for (int l = 0; l < ll.size() && l != k; l++) {
										if (ll.get(k).eWaitTime >= ll.get(l).sWaitTime
												&& ll.get(k).eWaitTime >= ll.get(l).eWaitTime) {

											if (runwayLinkedList.get(j).status == true) {

												ll.get(i).state = "r";
												ll.get(i).RunwayNumber = runwayLinkedList.get(j).runwayNumber;
												runwayLinkedList.get(j).status = false;
												flage = true;
												break;
											}
										}
									}
								}
							}
							if (flage == false)
								ll.get(i).eWaitTime++;
						}
						if (ll.get(i).state.equals("r")) {
							// System.out.println("State r");

							ll.get(i).rstatetimer++;
							/*
							 * for (int j = 0; j < NumberOfRunway; j++) { if (runwayLinkedList.get(j).status
							 * == false) {
							 * 
							 * runwayLinkedList.get(j).RunwayTimer++;
							 * 
							 * } if (runwayLinkedList.get(j).RunwayTimer == ll.get(i).at.runwayTime) {
							 * runwayLinkedList.get(j).status = true; runwayLinkedList.get(j).RunwayTimer =
							 * 0; } }
							 */

							if (ll.get(i).rstatetimer == ll.get(i).at.runwayTime) {
								ll.get(i).state = "x";
								runwayLinkedList.get(ll.get(i).RunwayNumber).status = true;
								ll.get(i).efwt += ll.get(i).eWaitTime;
								ll.get(i).eWaitTime = 0;

								ll.get(i).cstatetimer = 0;
								ll.get(i).astatetimer = 0;
								ll.get(i).dstatetimer = 0;
								ll.get(i).rstatetimer = 0;
								ll.get(i).rdstatetimer = 0;
								// System.out.println("Sstate x");
							}
						}

					}
					int tempcounter = 0;
					//for (int i = 0; i < ll.size(); i++)
					for (int i = 0; i < flights; i++) {
						if (ll.get(i).state.equals("x")) {
							tempcounter++;

						}
					}
					if (tempcounter == flights) {

						break;

					}

					time++;

				}// time loop ends here
				
				//if(g==1 ) {
					Aircraft.freedtime=time;
					//System.out.println("freetime ist : "+Aircraft.freedtime);
					//}
				

				for (int i = 0; i < ll.size(); i++) {
					sums = ll.get(i).sfwt + sums;
					sumb = ll.get(i).bWaitTime + sumb;
					sume = ll.get(i).efwt + sume;
				}

				for (int i = 0; i < ll.size(); i++) {
					ll.get(i).sfwt = 0;
					ll.get(i).bWaitTime = 0;
					ll.get(i).efwt = 0;

				}
				
			//	System.out.println("For gate "+NumberOfGates+" In air= "+ng+" Freed time: "+Aircraft.freedtime);
				
			}// state loop ends here
			
			totalWaitsum[NumberOfGates - 1] = sums + sumb + sume;
			//totalWaitsum[NumberOfGates - 1] = Aircraft.freedtime;
			
			if(NumberOfGates>2)
			if(totalWaitsum[NumberOfGates - 1] == totalWaitsum[NumberOfGates - 2])
			{	
				System.out.println(" waitttime is "+totalWaitsum[NumberOfGates - 2]);
				optimal_gates = NumberOfGates-1;
				break;
			}
			sums = 0;
			sumb = 0;
			sume = 0;

			for (int i = 0; i < gateLinkedList.size(); i++) {
				gateLinkedList.remove(i);
			}
			for (int i = 0; i < runwayLinkedList.size(); i++) {
				runwayLinkedList.remove(i);
			}
			
		}// gate loop ends here
				
		System.out.println("Time:" + time);

		
		
		{
			int N = optimal_gates - init_value+2;	
				graphArray=new double[2][N];
		for(int i=init_value-1; i<N+init_value-1; i++)
		{
			
			graphArray[0][i-init_value+1]=i+1;
			graphArray[1][i-init_value+1]=totalWaitsum[i] /(flights*(flights-1));
			
		}
		
		}
		/*
		int nel = 0;
		OutputGUI infoObj=new OutputGUI(optimal_gates, nel, 1);
		Graph graphObj=new Graph(Airport.graphArray);*/

		System.out.println("Number of Optimal Gates "+ optimal_gates);

		return optimal_gates;
	}
	
	
	//**************************************************************************************************************************//
	
	

	public int getNumberOfEmergencyLandings(int NumberOfFlights, int OptimalNumberOfGates, LinkedList<Aircraft> ll, AircraftType Type) {
		int LastNumberOfFlights = NumberOfFlights;
		int NewNumberOfGates;
		int answer;
		boolean flag = false;
		while (true) {

			NumberOfFlights = NumberOfFlights + 1;
			Driver.Add(Type);
			NewNumberOfGates = getNumberOfGates(NumberOfFlights, ll);
			System.out.println("freedtime= "+ Aircraft.freedtime);
			if (/*NewNumberOfGates == OptimalNumberOfGates+1 ||*/ Aircraft.freedtime>500 ) {

				// flag = true;
				System.out.println("For flights= " + NumberOfFlights);
				break;
			}
		}

		answer = NumberOfFlights - LastNumberOfFlights - 1;

		return answer;
	}

}


