public class JMCalculator {

	String input, arabic;

	public boolean isArabic, isRoman, isInteger;

	double result;

	public JMCalculator (String str) {
		input = str;
	}

	public void parse() {

	}

	public String print() {
		return input;
	}

	public static void main (String[] args) {
		JMCalculator calc = new JMCalculator(args[0]);
		calc.parse();
		if (!calc.isArabic || !calc.isRoman)
			System.out.println("Exception! Numbers must be all arabic or all roman");
		System.out.println(calc.print());
	}
}