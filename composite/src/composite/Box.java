package composite;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Box implements Item {
	private String description;
	private double wrap_cost;
	public List<Item> items;
	
	public Box(String description, double wrap_cost) {
		this.description = description;
		this.wrap_cost = wrap_cost;
		this.items = new ArrayList<>();
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	@Override
	public double getPrice(Stack<String> calculate_history) {
		calculate_history.push("Box:" + description + " - Wrapping cost: R$" + wrap_cost);
		double total = wrap_cost;
		
		for(Item item : items) {
			total += item.getPrice(calculate_history);
		}
		
		calculate_history.push("Box" + description + " - Total: R$" + total);
		
		return total;
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	@Override
	public String toString() {
		return description + " - Wrapping cost: R$" + wrap_cost;
	}
	
	public void showContent() {
		System.out.println("Content of " + description + ":");
		
		for(Item item : items) {
			System.out.println(" - " + item);
		}
	}
}
