
abstract public class Crew implements Runnable{
	protected String Name;
	protected boolean TheDayIsNotOver;

	public Crew(String Name){
		this.Name=Name;
		this.TheDayIsNotOver=true;
	}

	protected void TimeToSleep(double add,Flight temp){//sending threads to sleep and adding it to the total time in the airport 
		try {
			Thread.sleep( (long) (add*1000));
			temp.AddTime((double) add);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void DayOver(){
		TheDayIsNotOver=false;
	}

	protected boolean isTheDayIsNotOver(Landing temp) {
		if (temp==(null)){
			TheDayIsNotOver=false;
			return false;}
		return true;
	}

}
