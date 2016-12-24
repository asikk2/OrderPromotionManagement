
public interface OrderManagement {
	public void orderAmountCalculate(int item); 
	public void orderItemAdd(int itemNumber,int quantity); 
	public void promotionOneOrderCalculate(); 
	public void promotionTwoOrderCalculate(String item); 
	public void promotionThreeOrderCalculate(String item,int quantity); 
	public String getMenu();
}
