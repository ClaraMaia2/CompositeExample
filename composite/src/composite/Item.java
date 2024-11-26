package composite;

import java.util.Stack;

public interface Item {
	double getPrice(Stack<String> calculate_history);
}
