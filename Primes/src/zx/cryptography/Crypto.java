package zx.cryptography;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * This method is the start of a trip down cryptography lane.
 * 
 * @author ryan
 * 
 */
public class Crypto {

	/**
	 * This is the value of <code>p</code> which is a >250 digit prime.
	 */
	private static BigInteger algeP;
	/**
	 * This is the value of <code>q</code> which is a >250 digit prime.
	 */
	private static BigInteger algeQ;
	/**
	 * This is the value of <code>n</code> which is the value of <code>pq</code>
	 * .
	 */
	private static BigInteger algeN;

	//(x^3)%n Where:
	//	n = pq 
	//	q = algeQ 
	//	p = algeP
	//	x = Ascii code
	/**
	 * This is the main mehtod called when the program is executed.
	 * 
	 * @param args
	 *            - The command line arguments
	 */
	public static void main(String[] args) {
		char x = 'a';
		algeP = new BigInteger("53554724938233921566192616528881714969845116188682896464167941311419246488652546941575976575392989898191474711453287817985329546392672567822232169131483476387648275972772994781632186134661695952469864996358454218966622154746888215162175146658342972555375942859872185576181163738211766488945538571835546257541572456177337651477854356354925567638713335223952635334293796744754197253523611393467626452529587494193297851441846944656596785446547721652493293126397333179657958425242769241141799451421559183");
		algeQ = new BigInteger("99276531623336435697479198538685676516961462982146998979164772635219498457789684175397145653322572184418666362225216112727617853333978454328482185937734542962484765472371857675877157624737681448544114764998775173543968238285678685549398911975576485296368552147197628247438846738269535828653883711183123329214586885313741691894942626398625229894567753396417452565768731757589315685798349257423264922325616932375144997967526914418586396135686392732371879892584731642652996629842323575626665369137825773");
		algeN = algeP.multiply(algeQ);
		//		System.out.println(7 % 6);
		reverse(forward(x));
		//		System.out.println(Math.pow(5, 4));
		//		System.out.println(root(Math.pow(5, 7), 7));
	}

	/**
	 * This will find the given root of the given number
	 * 
	 * @param num
	 *            - The number
	 * @param root
	 *            - The root to find (x)
	 * @return {@link Double}[In primitive form] - The x root.
	 */
	public static double root(double num, double root) {
		if (num < 0) {
			return -Math.pow(Math.abs(num), (1 / root));
		}
		return Math.pow(num, 1.0 / root);
	}

	/**
	 * This method will attempt to encrypt the given character by raising it to
	 * the power of three and and getting the remainder when it is divided by n
	 * 
	 * @param x
	 *            - The character to be encrypted
	 * @return {@link BigInteger} - The encrypted value
	 */
	private static BigInteger forward(char x) {
		double d = Math.pow(x, 3);
		System.out.println("D: " + d);
		BigDecimal decimal = new BigDecimal(d);
		BigInteger result = algeN.mod(decimal.toBigInteger());
		System.out.println(result);
		return result;
	}

	/**
	 * This will attempt to reverse the first encryption method
	 * 
	 * @param value
	 *            - The encrypted value
	 */
	private static void reverse(BigInteger value) {
		BigInteger step1 = algeN.modInverse(value);
		System.out.println("Step1: " + step1);
		double val = root(step1.doubleValue(), 3);
		System.out.println(val);
	}

	/**
	 * This is used for cube rooting.
	 */
	private static BigInteger number;

	/**
	 * This method will find the cube root of the given BigInteger
	 * 
	 * @param n
	 *            - The number to cube root
	 * @return {@link BigInteger} - The cube root.
	 */
	public static BigInteger cbrt(BigInteger n) {
		BigInteger guess = n.divide(BigInteger.valueOf((long) n.bitLength() / 3));
		boolean go = true;
		int c = 0;
		BigInteger test = guess;
		while (go) {
			BigInteger numOne = n.divide(guess.multiply(guess));
			BigInteger numTwo = guess.multiply(new BigInteger("2"));
			guess = numOne.add(numTwo).divide(new BigInteger("3"));
			if (numOne.equals(numTwo)) {
				go = false;
			}
			if (guess.mod(new BigInteger("2")).equals(BigInteger.ONE)) {
				guess = guess.add(BigInteger.ONE);
			}
			// System.out.println(guess.toString());
			c++;
			c %= 5;
			if (c == 4 && (test.equals(guess))) {
				return guess;
			}
			if (c == 2) {
				test = guess;
			}
		}

		if ((guess.multiply(guess)).equals(number)) {
			return guess;
		}
		return guess.add(BigInteger.ONE);
	}

}
