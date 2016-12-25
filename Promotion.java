import java.util.concurrent.ConcurrentHashMap;


public interface Promotion {
	public double applyPromotion(String item, ConcurrentHashMap<String, Integer> orderItemMap);
}
