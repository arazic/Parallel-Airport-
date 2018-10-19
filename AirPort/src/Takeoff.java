import java.util.HashMap;

public class Takeoff extends Flight {
	private String Destenation;
	protected UnboundedBuffer <Flight> TakeOffBuffer;
	private HashMap <String, Integer> NumPerDestination;
	
	public Takeoff (String[] split, UnboundedBuffer<Flight> TakeOffBuffer,HashMap <String, Integer> NumPerDestination){
		super(split);
		this.Destenation= split[3];
		this.TakeOffBuffer=TakeOffBuffer;
		this.NumPerDestination=NumPerDestination;
		setDestenationToMap();
	}
	
	public void setDestenationToMap(){//adding the Destination to a map 
		if(NumPerDestination.containsKey(this.Destenation)){
			NumPerDestination.put(Destenation, (NumPerDestination.get(this.Destenation)+1));}
		else{
		NumPerDestination.put(Destenation, 1);
		}
	}

	public void run() {
		TimeToSleep(Time,this);
			TakeOffBuffer.insert(this);
	}

	public String getDestenation() {
		return Destenation;
	}

}


