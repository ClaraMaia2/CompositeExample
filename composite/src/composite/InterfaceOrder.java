package composite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.tree.DefaultMutableTreeNode;


public class InterfaceOrder extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private JTextField product_name = new JTextField();
	private JTextField product_price = new JTextField();
	private JTextField box_description = new JTextField();
	private JTextField wrapping_cost = new JTextField();
	private JTextArea content_area = new JTextArea();
	private Box complete_order;
	private List<Item> items = new ArrayList<>();
		
	public InterfaceOrder() {
		this.complete_order = new Box("Main Order", 5.0);
		items.add(complete_order);
		
		setupUI();
	}
	
	private void setupUI() {
		setTitle("Ordering System - Composite Pattern");
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JPanel main_panel = new JPanel();
		main_panel.setLayout(new GridLayout(2, 1));
		
		// PANEL FOR PRODUCTS
		JPanel product_panel = new JPanel();
		product_panel.setBorder(BorderFactory.createTitledBorder("Add Product"));
		product_panel.setLayout(new GridLayout(3, 2));
		
		product_panel.add(new JLabel("Product's name:"));
		product_panel.add(product_name);
		product_panel.add(new JLabel("Product's price:"));
		product_panel.add(product_price);
		
		JButton add_product_btn = new JButton("Add Product");
		add_product_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addProduct();
			}
		});
		product_panel.add(add_product_btn);
		
		// PANEL FOR BOXES
		JPanel box_panel = new JPanel();
		box_panel.setBorder(BorderFactory.createTitledBorder("Add box"));
		box_panel.setLayout(new GridLayout(3, 2));
		
		box_panel.add(new JLabel("Box's description:"));
		box_panel.add(box_description);
		box_panel.add(new JLabel("Wrapping cost:"));
		box_panel.add(wrapping_cost);
		
		JButton add_box_btn = new JButton("Add Box");
		add_box_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addBox();
			}
		});
		box_panel.add(add_box_btn);
		
		// ADDING PANELS TO THE MAIN PANEL
		main_panel.add(product_panel);
		main_panel.add(box_panel);
		
		content_area.setEditable(false);
		
		// SETTING UP CALCULATE TOTAL BUTTON
		JButton calculate_price_btn = new JButton("Total Price");
		calculate_price_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculateTotal();
			}
		});
		
		JButton show_structure_btn = new JButton("Show Structure");
		show_structure_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showStructure();
			}
		});
		
		JPanel buttons_panel = new JPanel();
		buttons_panel.setLayout(new FlowLayout());
		buttons_panel.add(calculate_price_btn);
		buttons_panel.add(show_structure_btn);
		
		add(main_panel, BorderLayout.NORTH);
		add(new JScrollPane(content_area), BorderLayout.CENTER);
		add(buttons_panel, BorderLayout.SOUTH);
		
		setVisible(true);
		
	}
	
	private void addProduct() {
		String name = product_name.getText();
		double price;
		
		try {
			price = Double.parseDouble(product_price.getText());
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Please, enter a valid price.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Product product = new Product(name, price);
		items.add(product);
		
		Box selected_box = selectBox();
		
		if(selected_box != null) {
			selected_box.addItem(product);
			
			refreshContent();
		}
	}
	
	private void addBox() {
		String description = box_description.getText();
		double cost;
		
		try {				
			cost = Double.parseDouble(wrapping_cost.getText());

		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Please, enter a valid cost.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Box box = new Box(description, cost);
		items.add(box);
		
		Box selected_box = selectBox();
		
		if(selected_box != null) {
			selected_box.addItem(box);
			
			refreshContent();
		}
	}
	
	private Box selectBox() {
		List<Box> boxes = new ArrayList<>();
		
		for(Item i : items) {
			if(i instanceof Box) {
				boxes.add((Box) i);
			}
		}
		
		Box[] options = boxes.toArray(new Box[0]);
		Box selected_box = (Box) JOptionPane.showInputDialog(this, "Choose a box to add the item:", "Select box", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		
		return selected_box;
	}
	
	private void calculateTotal() {
		Stack<String> calculate_history = new Stack<>();
		double total = complete_order.getPrice(calculate_history);
		
		StringBuilder history_text = new StringBuilder("Step by Step: \n");
		
		for(String step : calculate_history) {
			history_text.append(step).append("\n");
		}
		
		JOptionPane.showMessageDialog(this, history_text.toString(), "Sum History", JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(this, "Total order price: R$" + total);
	}
	
	private void refreshContent() {
		content_area.setText("");
		listContent(complete_order, "");
	}
	
	private void listContent(Box box, String indent) {
		content_area.append(indent + box.toString() + "\n");
		
		for(Item item : box.getItems()) {
			if(item instanceof Box) {
				listContent((Box) item, indent + " ");
			} else {
				content_area.append(indent + " " + item + "\n"); 
			}
		}
	}
	
	private void showStructure() {
		JTree tree = new JTree(createTree(complete_order));
		JScrollPane scroll_pane = new JScrollPane(tree);
		
		scroll_pane.setPreferredSize(new Dimension(300, 400));
		
		JOptionPane.showMessageDialog(this, scroll_pane, "Order Structure", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private DefaultMutableTreeNode createTree(Box box) {
		DefaultMutableTreeNode box_node = new DefaultMutableTreeNode(box.toString());
		
		for(Item item : box.getItems()) {
			if(item instanceof Box) {
				box_node.add(createTree((Box) item));
			} else {
				box_node.add(new DefaultMutableTreeNode(item.toString()));
			}
		}
		
		return box_node;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(InterfaceOrder::new);
	}
}
