import java.util.ArrayList;
import java.util.Stack;

public class Polish extends ArrayList {

	public Stack<Object> opstack = new Stack<>();

	char lastop = 0;

	void _debug(String message)
	{
		if (CNST._DEBUG_) System.out.println(message);
	}

	public Polish () {
		super();
	}

	public void addop (char op) {
		while (!opstack.empty()) {
			char top = opstack.peek().toString().charAt(0);
			if ((top == '*' || top == '/') || ((top == '+' || top == '-') && (op == '+' || op == '-'))) {
				add(opstack.pop().toString().charAt(0));
			}
			else break;
		}
		opstack.push(op);
	}

	public void end () {
		while (!opstack.empty()) {
			add(opstack.pop().toString().charAt(0));
		}
		_debug("Reverse polish notation array: " + toString());
	}

	public double calculate() {
		for (Object el : this) {
			if (el.getClass() == Double.class) {
				opstack.push(el);
			}
			else switch ((char) el) {
				case '+':
					opstack.push((double) opstack.pop() + (double) opstack.pop());
					break;
				case '-':
					opstack.push(-(double) opstack.pop() + (double) opstack.pop());
					break;
				case '*':
					opstack.push((double) opstack.pop() * (double) opstack.pop());
					break;
				case '/':
					opstack.push(1/(double) opstack.pop() * (double) opstack.pop());
					break;
			}
 		}
 		return (double) opstack.pop();
	}
}