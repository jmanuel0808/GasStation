import java.util.logging.Level;


public class MainFuelPool  {
	
	int maxCapacity;
	
	int currentCapacity;
	
	static final public int limitedPrecent=20;
	
	private FuelSupplier fuelSupplier;


	

	public MainFuelPool(int maxCapacity, int currentCapacity) {
		
		setFuelSupplier(new FuelSupplier(this));
		this.maxCapacity = maxCapacity;
		this.currentCapacity = currentCapacity;
	}
	
	public boolean IsFull(){
		return (this.currentCapacity == this.maxCapacity);
	}
	
	public boolean IsEmpty(){
		return (this.currentCapacity == 0);
	}
	
	public boolean IsLessThenLimit(){
		return ((maxCapacity/100 )*limitedPrecent) > currentCapacity ;
	}
	
	public synchronized void putFuel(){
		
		try { 
			  Thread.sleep(4000);		
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setCurrentCapacity(maxCapacity);
		notifyAll(); // announce that the mainFuel Pool reached the capacity, release from waiting
		System.out.println("notify that reached capaxity");
	}
	
	public synchronized void alertToFillFuel(){
		if (IsLessThenLimit()){
			putFuel(); // alert to fuel Supplier so calling to putFuel()
		}
	}

	public synchronized void updateCurrentCapacity(int liters){
		currentCapacity -= liters;
	}
	
	
	
	

	@Override
	public String toString() {
		return "MainFuelPool:: maxCapacity=" + maxCapacity
				+ ", currentCapacity=" + currentCapacity ;
	}



	@Override
	public boolean equals(Object obj) {
		MainFuelPool other = (MainFuelPool) obj;
		if (currentCapacity != other.currentCapacity)
			return false;
		if (maxCapacity != other.maxCapacity)
			return false;
		return true;
	}





	public int getMaxCapacity() {
		return maxCapacity;
	}


	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}


	public int getCurrentCapacity() {
		return currentCapacity;
	}


	public void setCurrentCapacity(int currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

	public FuelSupplier getFuelSupplier() {
		return fuelSupplier;
	}

	public void setFuelSupplier(FuelSupplier fuelSupplier) {
		this.fuelSupplier = fuelSupplier;
	}


	
	
	
	
	
	
	
}
