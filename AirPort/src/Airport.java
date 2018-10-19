import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import java.sql.*;


public class Airport {
	static String path = "src/";
	public static int numberOfFlights;
	public static DataBase DataBase;

	Vector <Flight> Flights = new Vector <Flight>();
	Vector  <Thread> Threads= new Vector <Thread>();
	
	UnboundedBuffer<Flight> LandingQueue= new UnboundedBuffer<Flight>();
	UnboundedBuffer<Flight> TakeOffQueue= new UnboundedBuffer<Flight>();
	UnboundedBuffer<Flight> LogisticsQueue= new UnboundedBuffer<Flight>();
	UnboundedBuffer<Flight> SecurityQueue = new UnboundedBuffer<Flight>();
	BoundedBuffer<Flight> FuelQueue = new BoundedBuffer<Flight>(8);
	UnboundedBuffer<Flight> TechnicalQueue= new UnboundedBuffer<Flight>();
	UnboundedBuffer<FlightDetails> ManagementQueue= new UnboundedBuffer<FlightDetails>();
	
	HashMap <String, Integer> NumPerDestination= new  HashMap<String, Integer>();

	public Airport (String fileFlight,int numTechnicalCrew, int workSecurity){

	//	creatDataBase();
		ReadFRomFile(fileFlight);//reading data from the files 
		BuildingTheAirport(numTechnicalCrew,workSecurity);//building all the crews in the airport
		RunAirPort();//starting the Threads 
	}

	private void creatDataBase(){
		DataBase=new DataBase();
		DataBase.createTables("Landings","Takeoffs", true);
	}
	
	private void  ReadFRomFile(String filename) {//reading data from the files 
		BufferedReader  bf = null;
		try {
			bf = new BufferedReader (new FileReader(path+filename+".txt"));
			String str=bf.readLine();
			while((bf.ready())){
				str=bf.readLine();
				String[] split=str.split("\\t");
				if (filename.equals("FlightsData") && split.length!=0){
					if(IsLanding(split)){
						Landing Landing= new Landing(split,LandingQueue);
						Flights.add(Landing);
						Threads.add(new Thread(Landing));
					}
					else{						
						Takeoff Takeoff= new Takeoff(split,TakeOffQueue, NumPerDestination);
						Flights.add(Takeoff);
						Threads.add(new Thread(Takeoff));
					}
				}
			}
			numberOfFlights=Flights.size();
		}

		catch (FileNotFoundException exception){
			System.out.println ("The file " + filename + " was not found.");}

		catch (IOException e) {
			System.out.println("Couldn't read file");
		}
	}
	private boolean IsLanding(String[] split){
		if(split[3].length()<=2)	
			return true;
		return false;
	}
	private void BuildingTheAirport(int numTechnicalCrew, int workSecurity){//building all the crews in the air port 
		BuildingRunway();
		BuildingLogisticsCrew();
		BuildingSecurityMan(workSecurity);//getting the time for working on technical problems from the GUI
		BuildingFuelCrew();
		BuildingTechnicalCrew(numTechnicalCrew);//getting the number of technical crews from the Gui
		BuildingManagementCrew();
	}
	
	private void BuildingRunway(){//building runway crew's
		RunwayDirector Runway1=new RunwayDirector(1300, "Runway1", Flights,TechnicalQueue, LandingQueue,TakeOffQueue, LogisticsQueue, ManagementQueue);
		Threads.add(new Thread(Runway1));
		RunwayDirector Runway2=new RunwayDirector(1700, "Runway2",Flights,TechnicalQueue, LandingQueue,TakeOffQueue, LogisticsQueue, ManagementQueue);
		Threads.add(new Thread(Runway2));
		RunwayDirector Runway3=new RunwayDirector(1500, "Runway3",Flights,TechnicalQueue, LandingQueue,TakeOffQueue, LogisticsQueue, ManagementQueue);
		Threads.add(new Thread(Runway3));
	}

	private void BuildingManagementCrew(){//building management crew's
		ManagementCrew ManagementCrew=new ManagementCrew("ManagementCrew",numberOfFlights, ManagementQueue, LandingQueue, TakeOffQueue,
				LogisticsQueue, SecurityQueue, FuelQueue,TechnicalQueue, NumPerDestination,DataBase);
		Threads.add(new Thread(ManagementCrew));
	}

	private void BuildingTechnicalCrew(int numTechnicalCrew){//building technical crew's
		for(int i=0;i<numTechnicalCrew;i++){
			TechnicalCrew TechnicalCrew=new TechnicalCrew("TechnicalCrew"+i ,LogisticsQueue,SecurityQueue,FuelQueue,TechnicalQueue,ManagementQueue);
			Threads.add(new Thread(TechnicalCrew));
		}
	}

	private void BuildingFuelCrew(){////building fuel crew's
		FuelCrew FuelCrew1=new FuelCrew(10000,"FuelCrew1", TechnicalQueue,ManagementQueue,FuelQueue);
		Threads.add(new Thread(FuelCrew1));
		FuelCrew FuelCrew2=new FuelCrew(5000 ,"FuelCrew2", TechnicalQueue,ManagementQueue,FuelQueue);
		Threads.add(new Thread(FuelCrew2));
	}

	private void BuildingSecurityMan(int workSecurity){//building security crew's
		SecurityMan Security1=new SecurityMan("Bachir","Security1", workSecurity ,SecurityQueue, FuelQueue ); //לזכור שמכניסים בשניות
		Threads.add(new Thread(Security1));
		SecurityMan Security2=new SecurityMan("Zoutar","Security2",  workSecurity ,SecurityQueue, FuelQueue);
		Threads.add(new Thread(Security2));
	}

	private void BuildingLogisticsCrew(){//building logistics crew's
		LogisticsCrew Logis1=new LogisticsCrew(50,"Logis1",TechnicalQueue,  LogisticsQueue,  SecurityQueue);
		Threads.add(new Thread(Logis1));
		LogisticsCrew Logis2=new LogisticsCrew(70,"Logis2", TechnicalQueue,  LogisticsQueue,  SecurityQueue);
		Threads.add(new Thread(Logis2));
		LogisticsCrew Logis3=new LogisticsCrew(90,"Logis3", TechnicalQueue,  LogisticsQueue,  SecurityQueue);
		Threads.add(new Thread(Logis3));
	}

	private void  RunAirPort() {//starting all the threads
		for(int i=0;i< Threads.size();i++){
			Threads.elementAt(i).start();	
		}
	}
	

}