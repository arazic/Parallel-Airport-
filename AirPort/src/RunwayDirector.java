import java.util.Vector;

public class RunwayDirector extends Crew {
	private int length;
	protected String Name;
	private Vector Flights;
	protected int numberOfFlights;
	protected UnboundedBuffer<Flight> LogisticsQueue;
	protected UnboundedBuffer<Flight> TechnicalQueue;
	protected UnboundedBuffer<Flight> LandingQueue;
	protected UnboundedBuffer <Flight> TakeOffQueue;
	protected UnboundedBuffer <FlightDetails> ManagementQueue;
	private int Stage=1;


	public RunwayDirector(int length, String Name, Vector Flights,UnboundedBuffer TechnicalQueue,UnboundedBuffer LandingQueue, UnboundedBuffer TakeOffQueue,UnboundedBuffer LogisticsQueue, UnboundedBuffer ManagementQueue) {
		super (Name);
		this.length=length;	
		this.Flights= Flights;
		this.TechnicalQueue=TechnicalQueue;
		this.LandingQueue=LandingQueue;
		this.TakeOffQueue= TakeOffQueue;
		this.LogisticsQueue= LogisticsQueue;
		this.ManagementQueue= ManagementQueue;
	}


	public void run() {
		while(TheDayIsNotOver()){//checking if the day is over 
			while(!(LandingQueue.isEmpty())){//keep extracting landings flights while the queue is not empty 
				LandingOperation();
			}
			if(!(TakeOffQueue.isEmpty())){//extracting takeoff flights while the queue is  empty 
				TakeoffOperation();
		}
	}
	}
	
	protected void LandingOperation(){
		Flight temp= LandingQueue.extract();
		temp.SetStage(this.Stage);//setting the stage of the process to the flight
		TimeToSleep((int)5+Math.random()*5,temp);
		if(ThereIsProblem()){//checking if there is a problem 
			TechnicalQueue.insert(temp);//sending the flight to the technical crew 
			RemoveFromFlights();	
			
		}
		else{
			LogisticsQueue.insert(temp);//sending the flight to the logistics crew 
			RemoveFromFlights();	
		}
	}
	
	protected void TakeoffOperation(){
		Flight temp= TakeOffQueue.extract();
		TimeToSleep((int) (5+(Math.random()*5)),temp);
		ManagementQueue.insert(new FlightDetails(temp));
		RemoveFromFlights();	
	}

	private void RemoveFromFlights(){
		Flights.removeElementAt(0);
	}
	

	private boolean TheDayIsNotOver(){
		return Flights.size()!=0;
	}

	private boolean ThereIsProblem(){
		return Math.random()<=0.25;
	}
}


