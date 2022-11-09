package content;

public class UnprocessedFood extends Food{
	
	private int FoodWeight;
	
	public UnprocessedFood(String foodId, String foodName, int foodPrice, int foodWeight) {
		super(foodId, foodName, foodPrice);
		FoodWeight = foodWeight;
	}

	public int getFoodWeight() {
		return FoodWeight;
	}

	public void setFoodWeight(int foodWeight) {
		FoodWeight = foodWeight;
	}

	@Override
	public int calcTotal(int qty) {
		// TODO Auto-generated method stub
		return (qty * FoodWeight)/100 * FoodPrice;
		
	}

}
