import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Assumptions Promotion1 : if quantity of Product1 is 3 or more then will be
 * sold at priceDiscountPD1 each Promotion2 : Product2 and Product4 is bought
 * together then discounted price is priceDiscountTwo otherwise sold at
 * individual price Promotion3 : Product3 is sold at 50% off discount
 */

public class OrderManagementImpl implements OrderManagement {

	static double orderTotalAmount = 0.0;
	ConcurrentHashMap<String, Integer> orderItemMap = null;
	ProductPrice productPrice = null;

	/**
	 * @return the orderTotalAmount
	 */
	public static double getOrderTotalAmount() {
		return orderTotalAmount;
	}

	/**
	 * @param orderTotalAmount
	 *            the orderTotalAmount to set
	 */
	public static void setOrderTotalAmount(double orderTotalAmount) {
		OrderManagementImpl.orderTotalAmount = orderTotalAmount;
	}

	/**
	 * @return the orderItemMap
	 */
	public ConcurrentHashMap<String, Integer> getOrderItemMap() {
		return orderItemMap;
	}

	/**
	 * @param orderItemMap
	 *            the orderItemMap to set
	 */
	public void setOrderItemMap(ConcurrentHashMap<String, Integer> orderItemMap) {
		this.orderItemMap = orderItemMap;
	}

	/**
	 * Constructor for initializing the item and itemprice map
	 */
	public OrderManagementImpl() {
		orderItemMap = new ConcurrentHashMap<String, Integer>();
		productPrice = new ProductPrice();
	}

	/**
	 * Calculate amount by multiplying price with quantity
	 */
	@Override
	public void orderAmountCalculate(String item) {
		orderTotalAmount += (int) orderItemMap.get(item) * (double) productPrice.getProductPriceMap().get(item);
		Promotion promo = null;
		switch(item){
			case "item1" :  promo = new XQtyGreaterThanY();
							orderTotalAmount-=promo.applyPromotion(item, orderItemMap);
							break;
			case "item2" :
			case "item4" : 	promo = new BuyXAndYAtPriceZ();
							orderTotalAmount-=promo.applyPromotion(item, orderItemMap);
							break;
			case "item3" :  promo = new HalfPricePromotion();
							orderTotalAmount-=promo.applyPromotion(item, orderItemMap);
							break;
			default 	 :	break;
		}
	}

	/**
	 * Adds the item and qty to the orderitem map
	 */
	@Override
	public void orderItemAdd(int itemNumber, int quantity) {
		String item=productPrice.getProducts()[itemNumber - 1];
		boolean flag= false;
		if (orderItemMap.isEmpty()) {
			orderItemMap.put(item,quantity);
		} else {
			
			Iterator<Entry<String, Integer>> i = orderItemMap.entrySet().iterator();

			while (i.hasNext()) {
				
				Entry<String, Integer> prod = (Entry<String, Integer>) i.next();
				if (prod.getKey() == item) {
					flag=true;
					int newquantity = new Integer((int) prod.getValue())
							+ quantity;
					orderItemMap.remove(item);
					orderItemMap.put(item,newquantity);
				} 
			}
			if(!flag){
				orderItemMap.put(item, quantity);
			}
		}
		orderAmountCalculate(item);
	}

	@Override
	public String getMenu() {
		String menu = "List of Items\t  Price\n";

		for (String item : productPrice.getProducts()) {
			menu += item + "\t\t  " + productPrice.getProductPriceMap().get(item) + "\n";
		}
		return menu;
	}

	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);
		int choice, quantity;
		OrderManagement object = new OrderManagementImpl();
		String ch;
		do {
			System.out.println(object.getMenu());
			System.out.println("please enter the item number");
			choice = userInput.nextInt();
			System.out.println("please enter the quantity");
			quantity = userInput.nextInt();
			object.orderItemAdd(choice, quantity);
			System.out.println("has more items");
			ch = userInput.next();
		} while (ch.equalsIgnoreCase("Y"));

		System.out.println("your total amount=" + orderTotalAmount);
		userInput.close();
		System.exit(0);
	}
}