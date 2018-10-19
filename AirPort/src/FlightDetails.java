
public class FlightDetails {
	private String ID;
	private int Passangers;
	private double Time;
	private int Stage;
	private int TechnicalCost;
	private int Cargo;
	private String Destenation;
	private int Full;
	protected boolean SuspiciousObject;
	protected int fullTruck;
	private String Kind;


	public FlightDetails(Flight Flight){
		this.ID=Flight.getID();
		this.Passangers=Flight.getPassangers();
		this.Time=Flight.getTime();	
		this.TechnicalCost=Flight.getTechnicalCost();
		this.Full=Flight.getFull();
		this.SuspiciousObject=Flight.getSuspiciousObject();
		this.fullTruck=Flight.getFullTruck();
		this.Kind=Kind(Flight);
		Cargo(Flight);
		Destenation(Flight);
	}

	private String Kind(Flight Flight){//returning the kind of the Flight
		if(Flight instanceof Landing)
			return "landings";
		return "takeoffs";
	}

	private void Cargo(Flight Flight){
		if(Flight instanceof Landing){
			this.Cargo=((Landing)Flight).getBuggegCount();
		}
	}

	private void Destenation(Flight Flight){
		if(Flight instanceof Takeoff){
			this.Destenation=((Takeoff)Flight).getDestenation();
		}
	}	

	public int getFull() {
		return Full;
	}

	public String getKind() {
		return Kind;
	}

	public int getCargo() {
		return Cargo;
	}

	public String getDestenation() {
		return Destenation;
	}

	public String toString(){
		return this.ID;
	}

	public String getID() {
		return ID;
	}


	public double getTime() {
		return Time;
	}

	public int getTechnicalCost() {
		return TechnicalCost;
	}

	public int getPassangers() {
		return Passangers;
	}

	public boolean getSuspiciousObject() {
		return SuspiciousObject;
	}

	public int getFullTruck() {
		return fullTruck;
	}




}
