

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;









import java.io.File;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


public class XMLreader {

	
	private static Logger gasStationLogger = Logger.getLogger("gasStationLogger");
	
	public static GasStation createGasStationFromXml()  {
		 try {
			 
				File fXmlFile = new File("gesStation.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				doc.getDocumentElement().normalize();
				
				// Getting Root Element:: --> (Gas Station)*********************/
				
				Node rootNode = doc.getDocumentElement();
				System.out.print("Root element :" + rootNode.getNodeName());
				
				NamedNodeMap map =  rootNode.getAttributes();
				
			//	System.out.println("Attributes:: 1.NumOfPumps: [" + map.getNamedItem("numOfPumps").getNodeValue()+
			//					   "]   2.pricePerLiter: [" + map.getNamedItem("pricePerLiter").getNodeValue() + "]");
			//	System.out.println("--------------------------------------------");
				
				int numOfPumps = Integer.parseInt(map.getNamedItem("numOfPumps").getNodeValue());
				int pricePerLiter = Integer.parseInt(map.getNamedItem("pricePerLiter").getNodeValue());
				
				
				
				
			
				// Getting Main Fuel Pool::*************************************/
				
				Node MainFuelPool_Node = doc.getElementsByTagName("MainFuelPool").item(0); 
				
				map = MainFuelPool_Node.getAttributes();
				
			//	System.out.println("MainFuelPool : Atrributes:: 1.maxCapcity: [" + map.getNamedItem("maxCapacity").getNodeValue() + "]"+
			//					   "   2.currentCapacity: [" + map.getNamedItem("currentCapacity").getNodeValue() + "]");
			//	System.out.println("--------------------------------------------");
				
				int maxCapacity = Integer.parseInt(map.getNamedItem("maxCapacity").getNodeValue());
				int currentCapacity = Integer.parseInt(map.getNamedItem("currentCapacity").getNodeValue());
				
				MainFuelPool mainFuelPool = new MainFuelPool(maxCapacity, currentCapacity);
				
				Vector<Pump> pumps = new Vector<Pump>();
				
				for (int i=0; i< numOfPumps; i++){
					Pump pump = new Pump(mainFuelPool);
					pumps.add( pump);
					
					Thread pumpThread = new Thread(pump);
					pumpThread.start();
					
				}
				
				// Getting Coffee House::*************************************/
				
				Node CoffeeHouse_Node = doc.getElementsByTagName("CoffeeHouse").item(0); 
				
				map = CoffeeHouse_Node.getAttributes();
				
			//	System.out.println("CoffeeHouse : Atrributes:: 1.numOfCashier: [" + map.getNamedItem("numOfCashier").getNodeValue() + "]");
			//	System.out.println("--------------------------------------------");		
				
				int numOfCashier = Integer.parseInt(map.getNamedItem("numOfCashier").getNodeValue());
				CoffeeHouse coffeeHouse = new CoffeeHouse(new Vector<Cashier>(numOfCashier));
		
				Vector<Cashier> cashiers= new Vector<Cashier>();
				for (int i=0; i< numOfCashier; i++){
					Cashier cashier = new Cashier(coffeeHouse);
					cashiers.add(cashier);
					
					Thread cashierThread = new Thread(cashier);
					cashierThread.start();
					
				}
				
				GasStation gasStation = new GasStation(mainFuelPool, coffeeHouse, pumps);
				
				// Getting Cars :***********************************************/
				
				Node cars_Node = doc.getElementsByTagName("Cars").item(0);
				NodeList carsNodeList =  cars_Node.getChildNodes();
			 
				for (int i = 0; i < carsNodeList.getLength(); i++) { 
			 
					Node car_Node = carsNodeList.item(i); // Present Each Car Element
			
					if (car_Node.getNodeName().equals("Car")) {  // validate that this Node represent a Car - Node
			 
						//System.out.println("\nCurrent Car  id: [" + car_Node.getAttributes().getNamedItem("id").getNodeValue() + "]");
						
						
						NodeList ServicesNodeList =  car_Node.getChildNodes();
						
						for (int j=0; j<ServicesNodeList.getLength(); j++){
							
							Node service_Node = ServicesNodeList.item(j);
							map = service_Node.getAttributes();
							Car car = new Car();
							if (service_Node.getNodeName().equals("WantsCoffee")){
							//	System.out.println("\n\t WantsCoffee: locationInCoffeeHouse: [" + service_Node.getAttributes().getNamedItem("locationInCoffeeHouse").getNodeValue() + "]" +
							//					   "   amountToPay: ["+ service_Node.getAttributes().getNamedItem("amountToPay").getNodeValue()+ "]");
								
								String locationInCoffeeHouse = map.getNamedItem("locationInCoffeeHouse").getNodeValue();
								int amountToPay =Integer.parseInt(map.getNamedItem("amountToPay").getNodeValue());
								
								car.setAmount_to_pay(amountToPay);
								
								if (locationInCoffeeHouse == "around"){
									gasStation.getCoffeeHouse().getAroundCars().add(car); //add the car to the list	
				        			gasStationLogger.log(Level.INFO,"Car #" + car.getId() + " is entering  to  CoffeeHouse \n", gasStation.getCoffeeHouse());
								}
								else if (locationInCoffeeHouse == "inLine"){
									gasStation.getCoffeeHouse().moveCartoInline(car);
								}
								
								
							}
							if (service_Node.getNodeName().equals("WantsFuel")){
						//		System.out.println("\n\t WantsFuel: numOfLiters: [" + service_Node.getAttributes().getNamedItem("numOfLiters").getNodeValue() + "]" +
						//						   "   pumpNum: ["+ service_Node.getAttributes().getNamedItem("pumpNum").getNodeValue()+ "]");
								
				
								int numOfLiters =Integer.parseInt(map.getNamedItem("numOfLiters").getNodeValue());
								car.setNum_of_liters(numOfLiters);
								gasStation.insertToShortestLinePump(car); //add this function to GasStation
								
							}
						}
						
			 
					}
				}
			    } catch (Exception e) {
				e.printStackTrace();
			    }
		 return null;
	}
		
	
		
		
	
		
		
		
}
	

	
