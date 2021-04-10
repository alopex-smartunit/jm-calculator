import java.io.Console;

public class JMCalculator {

	public static final String ARABIC = "0123456789", ROMAN = "NIVXLCDM", OPS = "+-*/$";

	String input;

	public int numberOfArabic = 0, numberOfRoman = 0;

	public Polish polish = new Polish();

	static void _debug(String message)
	{
		if (CNST._DEBUG_) System.out.println(message);
	}

	public JMCalculator (String[] args) throws Exception {
		if (args.length != 0) {
			if (args[0].equals("DEBUG")) CNST._DEBUG_ = true;
			if (args.length > 1) {
				input = String.join(" ", args).replaceFirst("DEBUG", "").toUpperCase() + "$";
				return;
			}
		}
		Console console = System.console();
		input = console.readLine("Enter expression: ") + "$";
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

 	public static String toRoman(int number) {
 		if (number == 0) return "N";
 		int m = number / 1000; number -= 1000 * m;
 		int cm = number / 900; number -= 900 * cm;
 		int d = number / 500; number -= 500 * d;
 		int cd = number / 400; number -= 400 * cd;
 		int c = number / 100; number -= 100 * c;
 		int xc = number / 90; number -= 90 * xc;
 		int l = number / 50; number -= 50 * l;
 		int xl = number / 40; number -= 40 * xl;
 		int x = number / 10; number -= 10 * x;
 		int ix = number / 9; number -= 9 * ix;
 		int v = number / 5; number -= 5 * v;
 		int iv = number / 4; number -= 4 * iv;
 		int i = number;
 		return "M".repeat(m) + "CM".repeat(cm) + "D".repeat(d) + "CD".repeat(cd)
 			+ "C".repeat(c) + "XC".repeat(xc) + "L".repeat(l) + "XL".repeat(xl)
 			+ "X".repeat(x) + "IX".repeat(ix) + "V".repeat(v) + "IV".repeat(iv) + "I".repeat(i);
    }	

	public String calculate() {
		if (numberOfArabic > 0) return ("" + polish.calculate());
		if (numberOfRoman > 0) return toRoman((int) polish.calculate());
		return "WTF???";
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