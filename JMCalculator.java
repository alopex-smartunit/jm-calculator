public class JMCalculator {

	public static final String ARABIC = "0123456789", ROMAN = "NIVXLCDM", OPS = "+-*/$";

	boolean DEBUG = false;

	String input;

	public int numberOfArabic = 0, numberOfRoman = 0;

	double result;

	public JMCalculator (String[] args) throws Exception {
		if (args.length == 0) throw new Exception("Exception: no arguments");
		if (args[0].equals("DEBUG")) DEBUG = true;
		input = String.join(" ", args).replaceFirst("DEBUG", "").toUpperCase() + "$";
	}

	void _debug(String message)
	{
		if (DEBUG) System.out.println(message);
	}

	public void parse() throws Exception {
		String arabic = "", roman = "", number = "";
		char op = '$';
		boolean space = false;

		_debug("input: " + input);

		for (char ch: input.toCharArray()) {
			if (ch == ' ') {
				space = true;
			}
			else if (ARABIC.indexOf(ch) >= 0) {
				if (space == true && op == 0) throw new Exception("Exception: unexpected number");
				if (roman.length() > 0) throw new Exception("Exception: illegal roman number");
				arabic += ch; op = 0; space = false;
			}
			else if (ROMAN.indexOf(ch) >= 0) {
				if (space == true && op == 0) throw new Exception("Exception: unexpected number");
				if (arabic.length() > 0) throw new Exception("Exception: illegal arabic number");
				roman += ch; op = 0; space = false;
			}
			else if (OPS.indexOf(ch) >= 0) {
				if (arabic.length() == 0 && roman.length() == 0) throw new Exception("Exception: unexpected operator");
				if (arabic.length() > 0) {
					number = arabic; arabic = ""; numberOfArabic++;
				}
				if (roman.length() > 0) {
					number = roman; roman = ""; numberOfRoman++;
				}
				op = ch; space = false;
			}
			else {
				throw new Exception("Exception: illegal character");
			}

			if (number.length() > 0) {
				_debug(number);
				number = "";
			}
			if (op != 0 && space == false) {
				_debug("" + op);
			}
		}
		if (op != 0 && op != '$') throw new Exception("Exception: last operand missed");

		_debug("numberOfArabic: " + numberOfArabic);
		_debug("numberOfRoman: " + numberOfRoman);
		if (numberOfArabic * numberOfRoman != 0) throw new Exception("Exception: numbers must be all arabic or all roman");
	}

	public String print() {
		return String.valueOf(result);
	}

	public static void main (String[] args) {

		try {
			JMCalculator calc = new JMCalculator(args);
			calc.parse();
			System.out.println(calc.print());
		}
		catch (Exception e)	{
			System.out.println(e.getMessage());
		}
		finally {
		}
	}
}