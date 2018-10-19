
abstract public class Flight implements Runnable{
	protected String ID;
	protected int Passangers;
	protected double Time;
	protected int Stage;
	protected int TechnicalCost;
	protected int Full;
	protected boolean SuspiciousObject;
	protected int fullTruck;

	public Flight(String[] split) {
		this.ID=(split[0]);
		this.Passangers=(Integer.parseInt(split[1]));
		this.Time=(Integer.parseInt(split[2]));
		this.Stage=0;
		this.TechnicalCost=0;
		this.Full=0;
		this.SuspiciousObject=false;
		this.fullTruck=0;
	}

	public void run() {
	}

	public void SetStage (int set){
		this.Stage=set;
	}

	public int getStage() {
		return Stage;
	}

	public int getTechnicalCost() {
		return TechnicalCost;
	}

	public void setTechnicalCost(int technicalCost) {
		TechnicalCost = technicalCost+technicalCost;
	}

	public String getID() {
		return ID;
	}

	public int getPassangers() {
		return Passangers;
	}

	public double getTime() {
		return Time;
	}

	public void AddTime(double add){
			Time=Time+add;
		Time= Time+add;
	}

	public void AddFull(){
		Full=1000;
	}

	public boolean getSuspiciousObject() {
		return SuspiciousObject;
	}

	public void setSuspiciousObject() {
		SuspiciousObject=true;
	}

	public int getFullTruck() {
		return fullTruck;
	}

	public void setFullTruck(int fullTruck) {
		this.fullTruck ++;
	}

	public int getFull() {
		return Full;
	}
	protected void TimeToSleep(double add,Flight temp){//sending threads to sleep and adding it to the total time in the airport
		try {
			Thread.sleep((long) (add*1000));
			temp.AddTime(add);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}



