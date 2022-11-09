package content;

public class Transaction {
	
	private String id;
	private String foodName;
	private int qty;
	
	public Transaction(String id, String foodName, int qty) {
		super();
		this.id = id;
		this.foodName = foodName;
		this.qty = qty;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
}
