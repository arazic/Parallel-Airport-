import java.util.Vector;

public class BoundedBuffer <T> extends Queue<T> {
	private int maxPlace;
	
	public BoundedBuffer(int maxPlace){
		super();
		this.maxPlace=maxPlace;//the maximum size of the bounded queue
	}
	

	public synchronized void  insert(T t) {//inserting an object to the queue
		while(Queue.size()>=maxPlace)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		Queue.add(t);
		notifyAll();   
	}	    

	public synchronized T extract() {//extracting an object from the queue
		while (Queue.isEmpty()) {
			if(!TheDayIsNotOver)
				return null;
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		T item = Queue.elementAt(0);
		Queue.remove(item);
		return item;    
	}
    
	public synchronized void  WakeUp(){//waking up all the threads
		notifyAll();   
	}

}



