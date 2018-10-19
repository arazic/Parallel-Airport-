
public class SecurityMan extends Crew {
	protected String Rank;
	protected String Name;
	private int TimeGui;
	protected UnboundedBuffer<Flight> SecurityQueue;
	protected BoundedBuffer<Flight> FuelQueue;
	private int Stage=3;



	public SecurityMan(String Rank, String Name, int TimeGui ,UnboundedBuffer SecurityQueue, BoundedBuffer FuelQueue){
		super(Name);
		this.Rank=Rank;
		this.TimeGui=TimeGui;
		this.SecurityQueue=SecurityQueue;
		this.FuelQueue=FuelQueue;
	}

	public void run() {
		while(TheDayIsNotOver){//keep working until the manager send us that the day is over 
			Landing temp= (Landing) SecurityQueue.extract();
			if(isTheDayIsNotOver(temp)){
				temp.SetStage(Stage);//setting the stage of the process to the flight
				SecurityCheckForFlight(temp);
			}
		}
	}
	
	public void SecurityCheckForFlight(Landing temp){
		TimeToSleep((TimeGui*1), temp);//we received the working time from the gui 
		if(ThereIsSuspiciousObject()){//checking if there is a problem Suspicious Object
			TimeToSleep(2,temp);
			temp.setSuspiciousObject();
		}
		FuelQueue.insert(temp);
		FuelQueue.WakeUp();
	}

	public boolean ThereIsSuspiciousObject(){
		return Math.random()<=0.05;
	}
}
