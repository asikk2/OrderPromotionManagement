import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class BuyXAndYAtPriceZ extends ProductPrice implements Promotion {

	public double discountAmount=0.0;
	double priceDiscountTwo=120;
	
	/**
	 *  Calculates the discount amount if item2 and item4 are bought together  
	 */
	@Override
	public double applyPromotion(String item, ConcurrentHashMap<String, Integer> orderItemMap) {
		
		int promoquantity;
		String itemtocheck="item2";
		if(item=="item2"){
			itemtocheck="item4";
		}
		
		Iterator<Entry<String,Integer>> i = orderItemMap.entrySet().iterator();     
	      
	    while(i.hasNext()) {
	    	Map.Entry<String,Integer> prod = (Map.Entry<String,Integer>)i.next();
	        if(prod.getKey()==itemtocheck){
	        	if(((int)orderItemMap.get(itemtocheck))<((int)orderItemMap.get(item))){
	        		promoquantity=((int)orderItemMap.get(itemtocheck));
	        	}else{
	        		promoquantity=((int)orderItemMap.get(item));
	        	}
	        	discountAmount+=(promoquantity*((double)productPriceMap.get(item)+(double)productPriceMap.get(itemtocheck)-priceDiscountTwo));
	        }
	    }
	    return discountAmount;
	}
}