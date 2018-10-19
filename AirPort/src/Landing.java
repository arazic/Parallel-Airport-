
public class Landing extends Flight {
	private int buggegCount;
	protected UnboundedBuffer<Flight> LandingBuffer;
	
	public Landing (String[] split, UnboundedBuffer<Flight> LandingBuffer){
		super(split);
		this.buggegCount= (Integer.parseInt(split[3]));
		this.LandingBuffer=LandingBuffer;
	}
	
	public void run() {
			TimeToSleep(Time,this);
			LandingBuffer.insert(this);
		}
	
	public int getBuggegCount() {
		return buggegCount;
	}
}
