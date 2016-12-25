import java.util.concurrent.ConcurrentHashMap;


public class ProductPrice {

	ConcurrentHashMap<String, Double> productPriceMap = new ConcurrentHashMap<String,Double>();
	String product[] = null;
	
	/**
	 * constructor for populating the productPriceMap and products
	 */
	public ProductPrice() {
		productPriceMap.put("item1",new Double(150.0));
    	productPriceMap.put("item2",new Double(75.0));
    	productPriceMap.put("item3",new Double(70.0));
    	productPriceMap.put("item4",new Double(75.0));
    	productPriceMap.put("item5",new Double(200.0));
    	product = new String[]{ "item1", "item2","item3","item4","item5"};
	}
	
	public ConcurrentHashMap<String, Double> getProductPriceMap(){
		return productPriceMap;
	}

	public void addNewProductNPrice(String item, Double price){
		productPriceMap.put(item, price);
		product[product.length]=item;
	}
	
	public String[] getProducts() {
		return product; 
	}
}