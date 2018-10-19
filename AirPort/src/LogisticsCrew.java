
public class LogisticsCrew extends Crew{
	private int Cargo;
	protected UnboundedBuffer<Flight> TechnicalQueue;
	protected UnboundedBuffer<Flight> LogisticsQueue;
	protected UnboundedBuffer<Flight> SecurityQueue;
	private int Stage=2;



	public LogisticsCrew(int Cargo, String Name,UnboundedBuffer TechnicalQueue, UnboundedBuffer LogisticsQueue, UnboundedBuffer SecurityQueue){
		super(Name);
		this.Cargo= Cargo;
		this.TechnicalQueue=TechnicalQueue;
		this.LogisticsQueue= LogisticsQueue;
		this.SecurityQueue=SecurityQueue;
	}

	public void run() {
		while(TheDayIsNotOver){//keep working until the manager send us that the day is over 
			Landing temp= (Landing) LogisticsQueue.extract();//extracting one flight from the unbounded queue
			if(isTheDayIsNotOver(temp)){
				temp.SetStage(Stage);//setting the stage of the process to the flight
				UnloadFlight(temp);//unloading the bug's from the airport
				if(ThereIsProblem()){//checking if there is a problem 
					TechnicalQueue.insert(temp);//sending the flight to the technical crew 
				}
				else{
					SecurityQueue.insert(temp);//sending the flight to the security crew
				}
			}
		}
	}


	private void UnloadFlight(Landing temp){
		if(Cargo>=temp.getBuggegCount()){//checking if can unload the cargo 
			TimeToSleep(((Cargo/10)*1), temp);
		}
		else{
			TimeToSleep(2,temp);
			TimeToSleep(((Cargo/10)*1), temp);
		}
	}


	private boolean ThereIsProblem(){
		return Math.random()<=0.10;
	}
}
