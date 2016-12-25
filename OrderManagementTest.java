import java.util.concurrent.ConcurrentHashMap;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class OrderManagementTest {

	OrderManagementImpl ord=null;
	private static ConcurrentHashMap<String, Double> productPriceMap = new ConcurrentHashMap<String,Double>(); 
	private ConcurrentHashMap<String, Integer> orderItemMap;
	
	@Before
	public void setUp() throws Exception {
		ord = new OrderManagementImpl();
		orderItemMap = new ConcurrentHashMap<String,Integer>();
		ord.setOrderItemMap(orderItemMap);
		OrderManagementImpl.setOrderTotalAmount(0.0);
	}

	@After
	public void tearDown() throws Exception {
		orderItemMap = null;
		ord = null;
	}

	@Test
	public void testProductPrice() {
		productPriceMap.put("item1",new Double(150.0));
    	productPriceMap.put("item2",new Double(75.0));
    	productPriceMap.put("item3",new Double(70.0));
    	productPriceMap.put("item4",new Double(75.0));
    	productPriceMap.put("item5",new Double(200.0));
    	ProductPrice productPrice = new ProductPrice();
    	Assert.assertEquals(productPriceMap,productPrice.getProductPriceMap());
	}
	
	/**
	 * Calculate amount by multiplying price with quantity 
	 */
	@Test
	public void testOrderAmountCalculate() {
		orderItemMap.put("item1", 3);
		orderItemMap.put("item3", 2);
		orderItemMap.put("item2", 4);
		ord.setOrderItemMap(orderItemMap);
		OrderManagementImpl.setOrderTotalAmount(370);
		ord.orderAmountCalculate("item2");
		Assert.assertEquals(670.0, OrderManagementImpl.getOrderTotalAmount());
	}

	/**
	 * Calculate order amount  for item1 for qty less than 3
	 */
	@Test
	public void testXQtyGreaterThanY1() {
		orderItemMap.put("item1", 2);
		Promotion promo = new XQtyGreaterThanY();
		Assert.assertEquals(0.0, promo.applyPromotion("item1", orderItemMap));
	}

	/**
	 * Calculate order amount  for item1 for qty greater than or equal to 3
	 */
	@Test
	public void testXQtyGreaterThanY2() {
		orderItemMap.put("item1", 5);
		Promotion promo = new XQtyGreaterThanY();
		Assert.assertEquals(250.0, promo.applyPromotion("item1", orderItemMap));
	}
	
	/**
	 * Calculate order amount for item3 which is at 50% off 
	 */
	@Test
	public void testPromotionThreeOrderCalculate() {
		orderItemMap.put("item1", 5);
		orderItemMap.put("item3", 4);
		Promotion promo = new HalfPricePromotion();
		Assert.assertEquals(140.0, promo.applyPromotion("item3", orderItemMap));
	}

	/**
	 * Calculate order amount for item2 when item4 already present and quantity of item2 greater than item4 
	 */
	
	@Test
	public void testPromotionTwoOrderCalculate1() {
		orderItemMap.put("item4", 3);
		orderItemMap.put("item2", 4);
		Promotion promo = new BuyXAndYAtPriceZ();
		Assert.assertEquals(90.0, promo.applyPromotion("item2", orderItemMap));
	}
	
	/**
	 * Calculate order amount for item4 when item2 already present and quantity of item2 greater than item4 
	 */
	@Test
	public void testPromotionTwoOrderCalculate2() {
		orderItemMap.put("item4", 3);
		orderItemMap.put("item2", 4);
		Promotion promo = new BuyXAndYAtPriceZ();
		Assert.assertEquals(90.0, promo.applyPromotion("item4", orderItemMap));
	}
	
	/**
	 * Calculate order amount for item2 when item4 already present and quantity of item2 less than item4 
	 */
	@Test
	public void testPromotionTwoOrderCalculate3() {
		orderItemMap.put("item4", 3);
		orderItemMap.put("item2", 2);
		Promotion promo = new BuyXAndYAtPriceZ();
		Assert.assertEquals(60.0, promo.applyPromotion("item2", orderItemMap));
	}
	
	/**
	 * Calculate order amount for item4 when item2 already present and quantity of item4 greater than item2 
	 */
	@Test
	public void testPromotionTwoOrderCalculate4() {
		orderItemMap.put("item4", 3);
		orderItemMap.put("item2", 2);
		Promotion promo = new BuyXAndYAtPriceZ();
		Assert.assertEquals(60.0, promo.applyPromotion("item2", orderItemMap));
	}

	/**
	 * Adding item to orderItemMap when map is empty
	 */
	@Test
	public void testOrderItemAddWhenEmpty() {
		orderItemMap.put("item2", 2);
		ord.orderItemAdd(2,2);
		Assert.assertEquals(orderItemMap, ord.getOrderItemMap());
	}
	
	/**
	 * Updating the value of already existing item of map
	 */
	@Test
	public void testOrderItemAddWhenNotEmpty() {
		orderItemMap.put("item2", 2);
		ord.setOrderItemMap(orderItemMap);
		ord.orderItemAdd(2,2);
		Assert.assertEquals(new Integer(4), ord.getOrderItemMap().values().iterator().next());
	}
}