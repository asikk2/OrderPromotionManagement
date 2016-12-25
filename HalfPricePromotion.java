import java.util.concurrent.ConcurrentHashMap;


public class HalfPricePromotion extends ProductPrice implements Promotion {

	public double discountAmount=0.0;
	/**
	 * calculate discount amount on item3 which is at 50% off
	 */
	@Override
	public double applyPromotion(String item, ConcurrentHashMap<String, Integer> orderItemMap) {
		discountAmount+=(new Double((double)productPriceMap.get(item))/2)*orderItemMap.get(item);
		return discountAmount;
	}
}