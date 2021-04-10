public class JMCalculator {

	public static final String ARABIC = "0123456789", ROMAN = "NIVXLCDM", OPS = "+-*/$";

	String input;

	public int numberOfArabic = 0, numberOfRoman = 0;

	public Polish polish = new Polish();

	public JMCalculator (String[] args) throws Exception {
		if (args.length == 0) throw new Exception("Exception: no arguments");
		if (args[0].equals("DEBUG")) CNST._DEBUG_ = true;
		input = String.join(" ", args).replaceFirst("DEBUG", "").toUpperCase() + "$";
	}

	void _debug(String message)
	{
		if (CNST._DEBUG_) System.out.println(message);
	}

	public void parse() throws Exception {
		String arabic = "", roman = "";
		char op = ' ';
		boolean space = false;
		int number = -1;

		_debug("Input string: " + input);

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
					number =  Integer.parseInt(arabic); arabic = ""; numberOfArabic++;
				}
				if (roman.length() > 0) {
					number = toArabic(roman); roman = ""; numberOfRoman++;
				}
				op = ch; space = false;
			}
			else {
				throw new Exception("Exception: illegal character");
			}

			if (number >= 0) {
				polish.add((double) number);
				number = -1;
			}
			if (op == '$') {
				polish.end();
				break;
			}
			if (op != 0 && space == false) {
				polish.addop(op);
			}
		}
		if (op != 0 && op != '$') throw new Exception("Exception: last operand missed");

		_debug("Number of arabic numbers: " + numberOfArabic);
		_debug("Number of roman numbers: " + numberOfRoman);
		if (numberOfArabic * numberOfRoman != 0) throw new Exception("Exception: numbers must be all arabic or all roman");
	}

 	public static int toArabic(String str) throws Exception {
        if (str.equals("N") || str.equals("")) return 0;
        if (str.startsWith("M")) return 1000 + toArabic(str.substring(1));
        if (str.startsWith("CM")) return 900 + toArabic(str.substring(2));
        if (str.startsWith("D")) return 500 + toArabic(str.substring(1));
        if (str.startsWith("CD")) return 400 + toArabic(str.substring(2));
        if (str.startsWith("C")) return 100 + toArabic(str.substring(1));
        if (str.startsWith("XC")) return 90 + toArabic(str.substring(2));
        if (str.startsWith("L")) return 50 + toArabic(str.substring(1));
        if (str.startsWith("XL")) return 40 + toArabic(str.substring(2));
        if (str.startsWith("X")) return 10 + toArabic(str.substring(1));
        if (str.startsWith("IX")) return 9 + toArabic(str.substring(2));
        if (str.startsWith("V")) return 5 + toArabic(str.substring(1));
        if (str.startsWith("IV")) return 4 + toArabic(str.substring(2));
        if (str.startsWith("I")) return 1 + toArabic(str.substring(1));
        throw new Exception("Exception: error converting roman number");
    }	

	public double calculate() {
		return polish.calculate();
	}

	public static void main (String[] args) {

		try {
			JMCalculator calc = new JMCalculator(args);
			calc.parse();
			System.out.println(calc.calculate());
		}
		catch (Exception e)	{
			System.out.println(e.getMessage());
		}
		finally {
		}
	}
}