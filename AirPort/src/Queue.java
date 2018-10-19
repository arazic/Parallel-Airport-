import java.util.Vector;

abstract public class Queue<T> {
	protected Vector<T> Queue;
	protected boolean TheDayIsNotOver;


	public Queue(){
		this.Queue= new Vector<T>();
		this.TheDayIsNotOver= true;
	}

	public synchronized void  insert(T t) {
		Queue.add(t);
		notifyAll();   
	}	    

	public synchronized boolean isEmpty() {
		return Queue.size()==0;
	}

	public synchronized int size(){
		return Queue.size();
	}


	public synchronized T extract() {
		while (Queue.isEmpty() ) {
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
		notifyAll();
		return item;    
	}

	public synchronized void wake(){
		TheDayIsNotOver= false;
		notifyAll();
	}
}
