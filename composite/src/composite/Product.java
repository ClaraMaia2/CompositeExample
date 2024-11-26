package composite;

import java.util.Stack;

public class Product implements Item {
	private String name;
	private double price;
	
	public Product(String name, double price) {
		this.name = name;
		this.price = price;
	}
	
	@Override
	public double getPrice(Stack<String> calculate_history) {
		calculate_history.push("Product:" + name + " - Price: R$" + price);
		
		return price;
	}
	
	@Override
	public String toString() {
		return name + " - R$" + price;
	}
}
