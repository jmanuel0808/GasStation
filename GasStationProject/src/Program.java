import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;




public class Program {
	
	private static Logger gasStationLogger = Logger.getLogger("gasStationLogger");
	
	
	public static void main(String[] args) throws SecurityException, IOException {
		
		gasStationLogger.setUseParentHandlers(false); // remove the Console Handler

		
		
		// try 
		MainFuelPool mfp = new MainFuelPool(100,40);
		
		CoffeeHouse ch = new CoffeeHouse(new Vector<Cashier>());
		
		Vector<Cashier> cashiers= new Vector<Cashier>();
		for (int i=0; i< 2; i++){
			Cashier cashier = new Cashier(ch);
			cashiers.add( cashier);
			
			Thread cashierThread = new Thread(cashier);
			cashierThread.start();
			
		}
		
		ch.setCashiers(cashiers);
		
		Vector<Pump> pumps = new Vector<Pump>();
		
		for (int i=0; i< 2; i++){
			Pump pump = new Pump(mfp);
			pumps.add( pump);
			
			Thread pumpThread = new Thread(pump);
			pumpThread.start();
			
		}
		GasStation gasStation = new GasStation(mfp, ch, pumps);
		Vector<Car> cars = new Vector<Car>();
		
		
		
		cars.add(new Car());
		cars.add(new Car());
		cars.add(new Car());
		
		

		// start the Menu:
		
		do{
			
			System.out.println("Wellcome To Gas Station:\n");
			System.out.println("\tPlease Choose One of The Following Options:");
			System.out.println("\t1. To Add a New Car - Press 1");
			System.out.println("\t2. To change Car status in Coffee House - press 2");
			System.out.println("\t3. To Fill Main Fuel Pool - press 3");
			
			Scanner scanner = new Scanner(System.in);
			int choice = scanner.nextInt();
			
			 switch (choice) {
		        case 1: { // Add car 
		        	//int random = 1 + (int)(Math.random() * ((3 - 1) + 1));
		        	int random =2;
		        	System.out.println("the rand is" + random);
		        	switch (random) {
		 
		        		case 1: { // just coffee 
		        		
		        			Car car = new Car();
		        			car.setAmount_to_pay((int)(Math.random()*100+1));
		        			gasStation.getCoffeeHouse().getAroundCars().add(car); //add the car to the list	
		        			gasStationLogger.log(Level.INFO,"Car #" + car.getId() + " is entering  to  CoffeeHouse \n", gasStation.getCoffeeHouse());
		        			break;
		        		}
		        
		        
		        		case 2: { // just fuel 
		        		
		        			Car car = new Car(); // random num_of_liters
		        			car.setNum_of_liters((int)(Math.random()*100+1));
		        			gasStation.insertToShortestLinePump(car); //add this function to GasStation
		        			
		        			break;
		        		}
		        	
		        	
		        		case 3: { // fuel and coffee
		        			
		        			Car car = new Car(); // random num_of_liters
		        			car.setNum_of_liters((int)(Math.random()*100+1));
		        			car.setAmount_to_pay((int)(Math.random()*100+1));
		        			
		        			gasStation.getCoffeeHouse().getAroundCars().add(car); 
		        			gasStationLogger.log(Level.INFO,"Car #" + car.getId() + " is entering  to  CoffeeHouse \n", gasStation.getCoffeeHouse());
		        			gasStation.insertToShortestLinePump(car);
		        			break;
		        		}
		        		default: break;
		        	}
		        	break; // end of Case 1 
		        }
		        case 2: { // Move from Around to inline
		        	
		        	Car theCar;
		        	do{
		        		System.out.println("Choose a car by index:");
			        	System.out.println(ch.getAroundCars().toString());
			        	Scanner s = new Scanner(System.in);
			        	int carId = s.nextInt();
			        	
			        	
			        	theCar = gasStation.getCoffeeHouse().getCarById(carId);
			        	
			        	if (theCar == null){
			        		System.out.println("Wrong choise, please try again");
			        	}
		        	}while (theCar == null);
		        	System.out.println(theCar.toString() + " Add to line");
		        	
		        	gasStation.getCoffeeHouse().moveCartoInline(theCar);
		        	
		        	// should add the option to move from cashier to around.
		        	// need to remove cashier service from car and catch the car 
		        	//when pull out from queue.
		          
		            break;
		        }
		        case 3: {
		        	gasStation.getMainFuelPool().putFuel();
		        	System.out.println("Main fuel pool had been filled, current Capacity: " +gasStation.getMainFuelPool().getCurrentCapacity());
		        
		            break;
		        }
		        default:
		        	break;
		        	
		    }
		
			
			
		}while (true);
	}
}
