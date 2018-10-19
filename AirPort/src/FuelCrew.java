
public class FuelCrew extends Crew {
	private int FuelCapacity;
	protected UnboundedBuffer<Flight> TechnicalQueue;
	protected UnboundedBuffer<FlightDetails> ManagementQueue;
	protected BoundedBuffer<Flight> FuelQueue;
	private int FullTank;
	private int Stage=4;


	public FuelCrew(int FuelCapacity, String Name,UnboundedBuffer TechnicalQueue,UnboundedBuffer ManagementQueue,BoundedBuffer FuelQueue){
		super(Name);
		this.FuelCapacity= FuelCapacity;
		this.TechnicalQueue=TechnicalQueue;
		this.ManagementQueue=ManagementQueue;
		this.FuelQueue=FuelQueue;
		this.FullTank=FuelCapacity;
	}

	public void run() {
		while(TheDayIsNotOver)	{//keep working until the manager send us that the day is over 
			Landing temp= (Landing) FuelQueue.extract();//extracting one flight from the bounded queue
			if (isTheDayIsNotOver(temp)){
				temp.SetStage(this.Stage);//setting the stage of the process to the flight
				FuelOperationForFlight(temp);//fueling the flight
			}
		}
	}

	private void FuelOperationForFlight(Landing temp){
		if(EnoughFuel()){//checking if there is enough fuel in the truck 
			FuelOperation(temp);//fueling the flight 
			if(ThereIsProblem()){//checking if there is a problem 
				TechnicalQueue.insert(temp);//if there is sending it to the technical crew
				FuelQueue.WakeUp();
			}
			else{
				ManagementQueue.insert(new FlightDetails(temp));//sending the details of the flight to the manager 
				FuelQueue.WakeUp();
			}
		}
		else{
			NotEnoughFuelOperation(temp);
		}
	}
	
	private void NotEnoughFuelOperation(Flight temp){
		FuelQueue.insert(temp);//returning the flight to the bounded queue
		FuelQueue.WakeUp();
		TimeToSleep(5,temp);
		ReFuling();//refilling the truck
		temp.setFullTruck(1);//counting how much fuel truck we used 
	}

	private boolean EnoughFuel(){
		return FuelCapacity>=1000;
	}

	private void FuelOperation(Flight temp){//fueling the flight
		FuelCapacity= FuelCapacity-1000;
		temp.AddFull();
		TimeToSleep((long) (3+(Math.random())),temp);
	}

	private boolean ThereIsProblem(){
		return Math.random()<=0.3;
	}
	private void ReFuling(){//refilling the truck 
		this.FuelCapacity=FullTank;
	}

}
