public class JMCalculator {

	String[] input;

	public int numberOfArabic = 0, numberOfRoman = 0, numberOfInteger;

	double result;

	public JMCalculator (String[] args) {
		input = args;
	}

	public void parse() throws Exception {
		if (input.length == 0) throw new Exception("Exception: no arguments");

		for (String s : input)
            System.out.println(s);

		if (numberOfArabic * numberOfRoman != 0) throw new Exception("Exception: numbers must be all arabic or all roman");
	}

	public String print() {
		return String.valueOf(result);
	}

	public static void main (String[] args) {

		JMCalculator calc = new JMCalculator(args);

		try {
			calc.parse();
		}
		catch (Exception e)	{
			System.out.println(e.getMessage());
		}
		finally {
			System.out.println(calc.print());
		}
	}
}