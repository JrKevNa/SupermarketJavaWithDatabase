package content;

public abstract class Food 
{
	protected String FoodId;
	protected String FoodName;
	protected int FoodPrice;
	public Food(String foodId, String foodName, int foodPrice) {
		super();
		FoodId = foodId;
		FoodName = foodName;
		FoodPrice = foodPrice;
	}
	public String getFoodId() {
		return FoodId;
	}
	public void setFoodId(String foodId) {
		FoodId = foodId;
	}
	public String getFoodName() {
		return FoodName;
	}
	public void setFoodName(String foodName) {
		FoodName = foodName;
	}
	public int getFoodPrice() {
		return FoodPrice;
	}
	public void setFoodPrice(int foodPrice) {
		FoodPrice = foodPrice;
	}

	public abstract int calcTotal(int qty);

}
