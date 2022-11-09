package content;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	Connect con = Connect.getConnection();
	Scanner sc = new Scanner(System.in);
	
	ArrayList <Food> food = new ArrayList<>();
	ArrayList <Transaction> transaction = new ArrayList<>();
	
	public Main() {
		// TODO Auto-generated constructor stub
		loadFood();
		int opt = 0;
		
		do
		{
			System.out.println("Go-Grocery");
			System.out.println("1. Buy Grocery");
			System.out.println("2. View Transaction History");
			System.out.println("3. Delete Transaction");
			System.out.println("4. Exit");
			System.out.print("Choose> ");
			do
			{
				try {
					opt = sc.nextInt();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sc.nextLine();
			}
			while(opt < 1 || opt > 4);
			
			switch(opt)
			{
				case 1:
					printFood();
					
					String foodId;
					boolean foodFound = false;
					int idx = 0;
					do
					{
						System.out.printf("Enter food Id: ");
						foodId = sc.nextLine();
						
						int tempidx = 0;
						for(Food f : food)
						{
							if(f.getFoodId().equals(foodId))
							{
								foodFound = true;
								break;
							}
							tempidx++;
						}
						idx = tempidx;
					}
					while(foodFound == false);
					
					int qty = 0;
					do
					{
						System.out.print("Input quantity [1-50]: ");
						try {
							qty = sc.nextInt();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						sc.nextLine();
					}
					while(qty < 1 || qty > 50);
					
					// calc total
					int total = 0;
					if(food.get(idx) instanceof ProcessedFood)
					{
						total = food.get(idx).calcTotal(qty);
					}
					else if(food.get(idx) instanceof UnprocessedFood)
					{
						total = food.get(idx).calcTotal(qty);
					}
					
					//lupa detail XD
					printTransactionDetail(food.get(idx).getFoodName(), food.get(idx).getFoodPrice(), qty, total);
					
					insertTransaction(food.get(idx).getFoodId(), qty);
					
				break;
			
				case 2:
					transaction.clear();
					loadTransaction();
					printTransaction();
				break;
			
				case 3:
					printTransaction();
					
					int selTr = -1;
					do
					{
						System.out.printf("Input transaction to delete [1-%d]: ", selTr);
						try {
							selTr = sc.nextInt();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						sc.nextLine();
					}
					while(selTr < 1 || selTr > transaction.size());
					
					String id = transaction.get(selTr).getId();
					deleteTransaction(id);
				break;
			}
		}
		while(opt != 4);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}
	
	public void loadFood()
	{
		loadProcessedFood();
		loadUnprocessedFood();
	}
	
	public void loadProcessedFood()
	{
		String foodId, foodName;
		Date expiredDate;
		int foodPrice;
		
		String query = String.format("SELECT * FROM `processed food`");
		ResultSet rs = con.executeQuery(query);
		
		try {
			while(rs.next())
			{
				foodId = rs.getString("FoodId");
				foodName = rs.getString("FoodName");
				foodPrice = rs.getInt("FoodPrice");
				expiredDate = rs.getDate("ExpiredDate");
				
				food.add(new ProcessedFood(foodId, foodName, foodPrice, expiredDate.toString()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadUnprocessedFood()
	{
		String foodId, foodName;
		int foodPrice, foodWeight;
		
		String query = String.format("SELECT * FROM `unprocessed food`");
		ResultSet rs = con.executeQuery(query);
		
		try {
			while(rs.next())
			{
				foodId = rs.getString("FoodId");
				foodName = rs.getString("FoodName");
				foodPrice = rs.getInt("FoodPrice");
				foodWeight = rs.getInt("FoodWeight");
				
				food.add(new UnprocessedFood(foodId, foodName, foodPrice, foodWeight));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printFood()
	{	
		String foodInput = null;
		do
		{
			System.out.print("Input Food Type [processed | unprocessed]: ");
			foodInput = sc.nextLine();
		}
		while(!(foodInput.equals("processed") || foodInput.equals("unprocessed")));
		
		if(foodInput.equals("processed"))
		{
			printProcessedFood();
		}
		else if(foodInput.equals("unprocessed"))
		{
			printUnprocessedFood();
		}
	}
	
	public void printProcessedFood()
	{
		System.out.printf("| %-6s | %-20s | %-10s | %-10s |\n", "ID", "Name", "Price", "Exp Date");
		for(Food f : food)
		{
			if(f instanceof ProcessedFood)
			{
				System.out.printf("| %-6s | %-20s | Rp. %-6d | %-10s |\n", f.getFoodId(), f.getFoodName(), f.getFoodPrice(), (f instanceof ProcessedFood) ? ((ProcessedFood)f).getExpiredDate() : "-");		
			}
		}
	}
	
	public void printUnprocessedFood()
	{
		System.out.printf("| %-6s | %-20s | %-10s | %-8s |\n", "ID", "Name", "Price", "Weight");
		for(Food f : food)
		{
			if(f instanceof UnprocessedFood)
			{
				System.out.printf("| %-6s | %-20s | Rp. %-6d | %-5d gr |\n", f.getFoodId(), f.getFoodName(), f.getFoodPrice(), (f instanceof UnprocessedFood) ? ((UnprocessedFood)f).getFoodWeight() : "-");
		
			}
		}
	}
	
	public void loadTransaction()
	{
		String trId, foodId;
		int qty;
		
		String query = String.format
				("SELECT TransactionId, FoodName, Quantity FROM `transaction` tr\r\n"
				+ "JOIN `processed food` pf\r\n"
				+ "WHERE tr.GroceryId = pf.FoodId\r\n"
				+ "UNION\r\n"
				+ "SELECT TransactionId, FoodName, Quantity FROM `transaction` tr\r\n"
				+ "JOIN `unprocessed food` uf\r\n"
				+ "WHERE tr.GroceryId = uf.FoodId");
		ResultSet rs = con.executeQuery(query);
		
		try {
			while(rs.next())
			{
				trId = rs.getString("TransactionId");
				foodId = rs.getString("FoodName");
				qty = rs.getInt("Quantity");
				
				transaction.add(new Transaction(trId, foodId, qty));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printTransaction()
	{
		System.out.printf("| %-3s | %-5s | %-20s | %-10s |\n", "No", "ID", "Name", "Quantity");
		int num = 1;
		for(Transaction t : transaction)
		{
			System.out.printf("| %-3d | %-5s | %-20s | %-10d |\n", num, t.getId(), t.getFoodName(), t.getQty());
			num++;
		}
	}
	
	public void insertTransaction(String foodID, int qty)
	{
		String query = String.format
				("INSERT INTO `transaction`(`TransactionID`, `GroceryId`, `Quantity`) "
				+ "VALUES ('%s','%s','%d')", randId(), foodID, qty);
		con.executeUpdate(query);
	}
	
	public String randId()
	{
		Random rand = new Random();
		return "TR" + rand.nextInt(10) + rand.nextInt(10) + rand.nextInt(10);
	}
	
	public void deleteTransaction(String id)
	{
		String query = String.format("DELETE FROM `transaction` WHERE TransactionId = '%s'", id);
		con.executeUpdate(query);
	}
	
	public void printTransactionDetail(String foodName, int foodPrice, int qty, int total)
	{
		System.out.println("Transaction Detail");
		System.out.printf("Food's Name\t\t: %s\n", foodName);
		System.out.printf("Food's Base Price\t: %d\n", foodPrice);
		System.out.printf("Quantity\t\t: %d\n", qty);
		System.out.printf("Total Price\t\t: %d\n", total);
	}
}
