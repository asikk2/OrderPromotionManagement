import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Assumptions
 * Promotion1 : if quantity of Product1 is 3 or more then will be sold at priceDiscountPD1 each
 * Promotion2 : Product2 and Product4 is bought together then discounted price is priceDiscountTwo otherwise sold at individual price
 * Promotion3 : Product3 is sold at 50% off discount
 */


public class OrderManagementImpl implements OrderManagement {
	
	/**
	 * @return the product
	 */
	public static String[] getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public static void setProduct(String[] product) {
		OrderManagementImpl.product = product;
	}

	/**
	 * @return the productPriceMap
	 */
	public ConcurrentHashMap<String, Double> getProductPriceMap() {
		return productPriceMap;
	}

	/**
	 * @param productPriceMap the productPriceMap to set
	 */
	public void setProductPriceMap(ConcurrentHashMap<String, Double> productPriceMap) {
		this.productPriceMap = productPriceMap;
	}

	/**
	 * @return the orderTotalAmount
	 */
	public static double getOrderTotalAmount() {
		return orderTotalAmount;
	}

	/**
	 * @param orderTotalAmount the orderTotalAmount to set
	 */
	public static void setOrderTotalAmount(double orderTotalAmount) {
		OrderManagementImpl.orderTotalAmount = orderTotalAmount;
	}

	/**
	 * @return the priceDiscountPD1
	 */
	public double getPriceDiscountPD1() {
		return priceDiscountPD1;
	}

	/**
	 * @param priceDiscountPD1 the priceDiscountPD1 to set
	 */
	public void setPriceDiscountPD1(double priceDiscountPD1) {
		this.priceDiscountPD1 = priceDiscountPD1;
	}

	/**
	 * @return the priceDiscountTwo
	 */
	public double getPriceDiscountTwo() {
		return priceDiscountTwo;
	}

	/**
	 * @param priceDiscountTwo the priceDiscountTwo to set
	 */
	public void setPriceDiscountTwo(double priceDiscountTwo) {
		this.priceDiscountTwo = priceDiscountTwo;
	}

	/**
	 * @return the orderItemMap
	 */
	public ConcurrentHashMap<String, Integer> getOrderItemMap() {
		return orderItemMap;
	}

	/**
	 * @param orderItemMap the orderItemMap to set
	 */
	public void setOrderItemMap(ConcurrentHashMap<String, Integer> orderItemMap) {
		this.orderItemMap = orderItemMap;
	}

	static String product[] = { "item1", "item2","item3","item4","item5"};
	ConcurrentHashMap<String, Double> productPriceMap = new ConcurrentHashMap<String,Double>();
	static double orderTotalAmount=0.0;
    double priceDiscountPD1=100; 
    double priceDiscountTwo=120;
    
    ConcurrentHashMap<String, Integer> orderItemMap = new ConcurrentHashMap<String,Integer>();
    /**
     * Constructor for initializing the item and itemprice map
     */
    OrderManagementImpl(){
    	productPriceMap.put("item1",new Double(150.0));
    	productPriceMap.put("item2",new Double(75.0));
    	productPriceMap.put("item3",new Double(70.0));
    	productPriceMap.put("item4",new Double(75.0));
    	productPriceMap.put("item5",new Double(200.0));
    }
     
    /**
     * Calculate amount by multiplying price with quantity 
     */
	@Override
	public void orderAmountCalculate(int item) {
		 orderTotalAmount+=(int) orderItemMap.get(product[item-1]) *(double)productPriceMap.get(product[item-1]);
	}	
	
	/**
	 *  Called only for item1 order calculation and it also checks the promotion scenario 1 
	 */
	@Override
	public void promotionOneOrderCalculate() {
		if(new Integer((int)orderItemMap.get("item1"))>=3){
			orderTotalAmount+=priceDiscountPD1*(int)orderItemMap.get("item1");
		}else{
			orderTotalAmount+=(int)orderItemMap.get("item1")*(double)productPriceMap.get("item1");
		}
		
	}	
	
	/**
	 *  Called only for item3 order calculation and it also checks the promotion scenario 3
	 */
	@Override
	public void promotionThreeOrderCalculate(String item,int quantity) {
		
		orderTotalAmount+=(new Double((double)productPriceMap.get(item))/2)*quantity;
	}	
	
	/**
	 *  Called only for item2 and item4 order calculation and it also checks the promotion scenario 2
	 */
	@Override
	public void promotionTwoOrderCalculate(String item) {
		String itemtocheck="item2";
		int remainingquantity;
		int deletionquantity;
		boolean flag=false;
		if(item=="item2"){
			itemtocheck="item4";
		}
		
		Iterator<Entry<String,Integer>> i = orderItemMap.entrySet().iterator();     
	      
	      while(i.hasNext()) {
	         Map.Entry<String,Integer> prod = (Map.Entry<String,Integer>)i.next();
	         if(prod.getKey()==itemtocheck){
	        	  flag=true;
	        	 }
	         }
	         
	      if(flag){
        	  if(((int)orderItemMap.get(itemtocheck))<((int)orderItemMap.get(item))){
        		  deletionquantity=((int)orderItemMap.get(itemtocheck));
        		  remainingquantity=((int)orderItemMap.get(item))-((int)orderItemMap.get(itemtocheck));
        		  orderTotalAmount+=(remainingquantity*(double)productPriceMap.get(item));
        		 
        		 }else{
        			 deletionquantity=((int)orderItemMap.get(item));
        			 item=itemtocheck;
        		 }
        	  orderTotalAmount-=(new Double((double)productPriceMap.get(item))*deletionquantity);
 			 orderTotalAmount+=priceDiscountTwo*deletionquantity;
 			 flag=false;
        	 
         }else{
        	 orderTotalAmount+=(int)orderItemMap.get(item)*(double)productPriceMap.get(item);
         }
        
	}	
	

	/**
	 * Adds the item and qty to the orderitem map
	 */
	@Override
	public void orderItemAdd(int itemNumber,int quantity) {
		if(orderItemMap.isEmpty()){
			orderItemMap.put(product[itemNumber-1], quantity);
		}else{
			
			 Iterator<Entry<String,Integer>> i = orderItemMap.entrySet().iterator();     

		      while(i.hasNext()) {
		         Entry<String,Integer> prod = (Entry<String,Integer>) i.next();
		         if(prod.getKey()==product[itemNumber-1]){
		        	 
		        	 int newquantity=new Integer ((int) prod.getValue()) + quantity;
		        	 orderItemMap.remove(product[itemNumber-1]);
		        	 orderItemMap.put(product[itemNumber-1],newquantity);
		         }else{
		        	 orderItemMap.put(product[itemNumber-1],quantity);
		         }
		         
		      }	
		}
		
	}
    
	@Override
    public String getMenu(){
    	String menu="List of Items\t  Price\n";
    	
    	for(String item : product){
    		menu+=item+"\t\t  "+productPriceMap.get(item)+"\n";
    	}
    	return menu;
    }
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);
		int choice,quantity;
		OrderManagement object=new OrderManagementImpl();
		String ch;
		do{
			System.out.println(object.getMenu());
			System.out.println("please enter the item number");
			choice=userInput.nextInt();
			System.out.println("please enter the quantity");
			quantity=userInput.nextInt();
				switch(choice){
				case 1 : object.orderItemAdd(choice, quantity);
				         object.promotionOneOrderCalculate();
				         break;
				case 2 :  object.orderItemAdd(choice, quantity);
		                  object.promotionTwoOrderCalculate(product[choice-1]);  
		                  break;
				case 3 : object.orderItemAdd(choice, quantity);
				         object.promotionThreeOrderCalculate(product[choice-1], quantity);
				         break;
				case 4 : object.orderItemAdd(choice, quantity);
				         object.promotionTwoOrderCalculate(product[choice-1]);
				         break;                  
				default :  object.orderItemAdd(choice, quantity);
				           object.orderAmountCalculate(choice);
				          
				}
				System.out.println("has more items");
				ch=userInput.next();
		}while(ch.equalsIgnoreCase("Y"));
		
		
		System.out.println("your total amount="+orderTotalAmount);
		
		
		userInput.close();
		System.exit(0);
	}
}