import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ManagementCrew extends Crew {
	private int numberOfFlights;
	private String CommonDestination;
	private int NumSuspiciousObject;
	private int NumFulltruck;
	private int AllFull;
	private int Allcost;
	private int AllCargoLandings;
	private int AllPassengers;
	private int CountFlights;
	private DataBase DataBase;

	UnboundedBuffer <FlightDetails> ManagementQueue;
	UnboundedBuffer <Flight> LandingQueue;
	UnboundedBuffer <Flight> TakeOffQueue;
	UnboundedBuffer <Flight> LogisticsQueue;
	UnboundedBuffer <Flight> SecurityQueue;
	BoundedBuffer <Flight> FuelQueue;
	UnboundedBuffer <Flight> TechnicalQueue;
	HashMap<String,Integer> NumPerDestination;

	public ManagementCrew(String Name, int numberOfFlights,UnboundedBuffer ManagementQueue, UnboundedBuffer LandingQueue,UnboundedBuffer TakeOffQueue,
			UnboundedBuffer LogisticsQueue,	UnboundedBuffer SecurityQueue, 	BoundedBuffer FuelQueue, UnboundedBuffer TechnicalQueue, HashMap NumPerDestination,DataBase DataBase){
		super(Name);
		this.numberOfFlights=numberOfFlights;
		this.ManagementQueue=ManagementQueue;
		this.LandingQueue=LandingQueue;
		this.TakeOffQueue=TakeOffQueue;
		this.LogisticsQueue=LogisticsQueue;
		this.SecurityQueue= SecurityQueue;
		this.FuelQueue= FuelQueue;
		this.NumPerDestination=NumPerDestination;
		this.TechnicalQueue=TechnicalQueue;
		this.NumSuspiciousObject=0;
		this.TheDayIsNotOver=true;
		this.NumFulltruck=0;
		this.AllFull=0;
		this.Allcost=0;
		this.AllCargoLandings=0;
		this.AllPassengers=0;
		this.CountFlights=0;
		this.DataBase=DataBase;
	}

	public void run() {
		while(TheDayIsNotOver()){//keep working until we dont have more flights
			FlightDetails FlightDetails= ManagementQueue.extract();
			//documentationInSQL(FlightDetails,DataBase);//sending the details of the flight to the SQL 
			CalculationPerFlight(FlightDetails);//calculating all the flight details of the day  
			PrintFlight(FlightDetails);//printing  flight details   
		}
		GeneralCalculation();//calculating the most common destination
		PrintDay();//printing the details of the day
		LetKnowDayOver();//telling all the crew that the day is over 
	}

	private void LetKnowDayOver(){//waking up all the threads 

		DayOver();	
		LandingQueue.wake();
		TakeOffQueue.wake(); 
		LogisticsQueue.wake();
		SecurityQueue.wake();
		FuelQueue.wake();
		TechnicalQueue.wake();

	}

	private void GeneralCalculation(){
		CommonDestination=BestDestination(NumPerDestination);
	}

	private void CalculationPerFlight(FlightDetails tempDetails){
		CalculateNumPassenger(tempDetails);
		CalculateCargo(tempDetails);
		CalculateCost(tempDetails);
		CalculateFull(tempDetails);
		CalculateSuspiciousObject(tempDetails);
		CalculateFulltruck(tempDetails);
		CountFlights++;		
	}

	private String BestDestination(HashMap <String,Integer> NumPerDestination){//calculating the most common destination
		int MaxDestination=0;
		String popularDestination="";
		for(Map.Entry<String, Integer> entry: NumPerDestination.entrySet()){
			if(entry.getValue()>=MaxDestination){
				MaxDestination=entry.getValue();
				popularDestination=entry.getKey();
			}
		}
		return popularDestination;
	}

	private void documentationInSQL(FlightDetails FlightDetails,DataBase DataBase){//sending the data to the SQl
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(FlightDetails.getKind().equals("landings")){
			DataBase.insertToTable("landings", FlightDetails);}
		else{
			DataBase.insertToTable("takeoffs", FlightDetails);}
	}


	private boolean TheDayIsNotOver(){//checking if the day is over 
		if(CountFlights==numberOfFlights){
			TheDayIsNotOver=false;
			return false;}
		return true;	
	}
	private void CalculateNumPassenger(FlightDetails tempDetails){//summing the number of passangers 
		AllPassengers=AllPassengers+tempDetails.getPassangers();
	}
	private void CalculateCargo(FlightDetails tempDetails){//summing the cargo
		AllCargoLandings=AllCargoLandings+tempDetails.getCargo();
	}
	private void CalculateCost(FlightDetails tempDetails){//summing the cost
		Allcost=Allcost+tempDetails.getTechnicalCost();
	}

	private void CalculateFull(FlightDetails tempDetails){//summing the fuel we used  
		AllFull=AllFull+tempDetails.getFull();
	}
	private void CalculateSuspiciousObject(FlightDetails tempDetails){//summing the number of SuspiciousObject 
		if(tempDetails.getSuspiciousObject())
			NumSuspiciousObject= NumSuspiciousObject+1;

	}
	private void CalculateFulltruck(FlightDetails tempDetails){//summing the number of fuel trucks we used  
		NumFulltruck=NumFulltruck+tempDetails.getFullTruck();
	}

	private void PrintFlight(FlightDetails tempDetails){
		System.out.println("Flight name : "+tempDetails.getID()+" Time in prcess: "+tempDetails.getTime()+" Total Cost: "+tempDetails.getTechnicalCost());
	}
	private void  PrintDay(){
		System.out.println();
		System.out.println("Summery of the day:");
		System.out.println("Number of pasengar used the AirPort: "+AllPassengers);
		System.out.println("Total Cargo : "+AllCargoLandings);
		System.out.println("Most popular destintion: "+CommonDestination);
		System.out.println("Total Cost: "+Allcost);
		System.out.println("Fual Consumption :"+AllFull);
		System.out.println("Number of Suspicious Objects:"+NumSuspiciousObject);
		System.out.println("Number of Full trucks: "+NumFulltruck);
	}

}
