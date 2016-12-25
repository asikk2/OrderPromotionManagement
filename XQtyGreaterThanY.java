import java.util.concurrent.ConcurrentHashMap;


public class XQtyGreaterThanY extends ProductPrice implements Promotion {

	double priceDiscountPD1=100; 
	public double discountAmount=0.0;
	
	/**
	 * calculate discount amount for item1 if quantity greater than or equal to 3
	 */
	@Override
	public double applyPromotion(String item, ConcurrentHashMap<String, Integer> orderItemMap) {
		if(new Integer((int)orderItemMap.get(item))>=3){
			discountAmount+=(productPriceMap.get(item)-priceDiscountPD1)*(int)orderItemMap.get(item);
		}
		return discountAmount;
	}
}
