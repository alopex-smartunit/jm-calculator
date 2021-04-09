import java.util.ArrayList;
import java.util.Stack;

public class Polish extends ArrayList {

	boolean DEBUG = false;

	public Stack opstack = new Stack();

	char lastop = 0;

	void _debug(String message)
	{
		if (DEBUG) System.out.println(message);
	}

	public Polish (boolean debug) {
		super();

		if (debug) DEBUG = true;
		_debug("Polish object initialized");
	}

	public void addop (char op) {
		if (lastop == 0) opstack.push(op);
		if ((lastop == '*' || lastop == '/') || ((lastop == '+' || lastop == '-') && (op == '+' || op == '-'))) {
			add(lastop);
			_debug("->" + lastop);
			opstack.pop();
			opstack.push(op);
		}
		if ((lastop == '+' || lastop == '-') && (op == '*' || op == '/')) opstack.push(op);
		lastop = op;
	}

	public void end () {
		while (!opstack.empty()) {
			lastop = opstack.pop().toString().charAt(0);
			add(lastop);
			_debug("->" + lastop);
		}
		_debug("=>" + toString());
	}
}