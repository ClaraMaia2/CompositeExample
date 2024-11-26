package composite;

import java.util.ArrayList;
import java.util.List;

class Order {
	@SuppressWarnings("unused")
	private double additional_cost;
	private List<Item> items = new ArrayList<>();
	
	public Order(double additional_cost) {
		this.additional_cost = additional_cost;
	}
	
	public void addProduct(Product product) {		
		items.add(product);
	}
	
	public void addBox(Box box) {		
		items.add(box);
	}
	
}
