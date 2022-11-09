package content;

public class ProcessedFood extends Food
{
	private String ExpiredDate;
	
	public ProcessedFood(String foodId, String foodName, int foodPrice, String expiredDate) {
		super(foodId, foodName, foodPrice);
		ExpiredDate = expiredDate;
	}

	public String getExpiredDate() {
		return ExpiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		ExpiredDate = expiredDate;
	}

	@Override
	public int calcTotal(int qty) {
		// TODO Auto-generated method stub
		return qty * FoodPrice;
	}

}
