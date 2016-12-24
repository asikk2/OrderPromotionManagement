import java.util.concurrent.ConcurrentHashMap;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class OrderManagementTest {

	OrderManagementImpl ord=null;
	private static ConcurrentHashMap<String, Double> productPriceMap = new ConcurrentHashMap<String,Double>(); 
	private ConcurrentHashMap<String, Integer> orderItemMap;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		productPriceMap.put("item1",new Double(150.0));
    	productPriceMap.put("item2",new Double(75.0));
    	productPriceMap.put("item3",new Double(70.0));
    	productPriceMap.put("item4",new Double(75.0));
    	productPriceMap.put("item5",new Double(200.0));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		productPriceMap = null;
	}

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
	public void testOrderManagementImpl() {
    	ord = new OrderManagementImpl();
    	Assert.assertEquals(productPriceMap, ord.getProductPriceMap());
	}
/**
 * Calculate amount by multiplying price with quantity 
 */
	@Test
	public void testOrderAmountCalculate() {
		orderItemMap.put("item1", 3);
		orderItemMap.put("item2", 4);
		orderItemMap.put("item3", 2);
		ord.setOrderItemMap(orderItemMap); 
		ord.orderAmountCalculate(2);
		Assert.assertEquals(300.0, OrderManagementImpl.getOrderTotalAmount());
	}

	/**
	 * Calculate order amount  for item1 for qty less than 3
	 */
	
	@Test
	public void testPromotionOneOrderCalculate1() {
		orderItemMap.put("item1", 2);
		ord.setOrderItemMap(orderItemMap); 
		ord.promotionOneOrderCalculate();
		Assert.assertEquals(300.0, OrderManagementImpl.getOrderTotalAmount());
	}

	/**
	 * Calculate order amount  for item1 for qty greater than or equal to 3
	 */
	@Test
	public void testPromotionOneOrderCalculate2() {
		orderItemMap.put("item1", 5);
		ord.setOrderItemMap(orderItemMap); 
		ord.promotionOneOrderCalculate();
		Assert.assertEquals(500.0, OrderManagementImpl.getOrderTotalAmount());
	}
	
	/**
	 * Calculate order amount for item3 which is at 50% off 
	 */
	@Test
	public void testPromotionThreeOrderCalculate() {
		ord.promotionThreeOrderCalculate("item3",6);
		Assert.assertEquals(210.0, OrderManagementImpl.getOrderTotalAmount());
	}

	/**
	 * Calculate order amount for item2 when item4 already present and quantity of item2 greater than item4 
	 */
	
	@Test
	public void testPromotionTwoOrderCalculate1() {
		orderItemMap.put("item4", 3);
		orderItemMap.put("item2", 4);
		ord.setOrderItemMap(orderItemMap);
		OrderManagementImpl.setOrderTotalAmount(225.0);
		ord.promotionTwoOrderCalculate("item2");
		Assert.assertEquals(435.0, OrderManagementImpl.getOrderTotalAmount());
	}
	
	/**
	 * Calculate order amount for item4 when item2 already present and quantity of item2 greater than item4 
	 */
	@Test
	public void testPromotionTwoOrderCalculate2() {
		orderItemMap.put("item4", 3);
		orderItemMap.put("item2", 4);
		ord.setOrderItemMap(orderItemMap);
		OrderManagementImpl.setOrderTotalAmount(300.0);
		ord.promotionTwoOrderCalculate("item4");
		Assert.assertEquals(435.0, OrderManagementImpl.getOrderTotalAmount());
	}
	
	/**
	 * Calculate order amount for item2 when item4 already present and quantity of item2 less than item4 
	 */
	@Test
	public void testPromotionTwoOrderCalculate3() {
		orderItemMap.put("item4", 3);
		orderItemMap.put("item2", 2);
		ord.setOrderItemMap(orderItemMap);
		OrderManagementImpl.setOrderTotalAmount(225.0);
		ord.promotionTwoOrderCalculate("item2");
		Assert.assertEquals(315.0, OrderManagementImpl.getOrderTotalAmount());
	}
	
	/**
	 * Calculate order amount for item4 when item2 already present and quantity of item4 greater than item2 
	 */
	@Test
	public void testPromotionTwoOrderCalculate4() {
		orderItemMap.put("item4", 3);
		orderItemMap.put("item2", 2);
		ord.setOrderItemMap(orderItemMap);
		OrderManagementImpl.setOrderTotalAmount(150.0);
		ord.promotionTwoOrderCalculate("item4");
		Assert.assertEquals(315.0, OrderManagementImpl.getOrderTotalAmount());
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