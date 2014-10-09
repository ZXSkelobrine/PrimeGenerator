package zx.cmd;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * This is the command line version of this program
 * 
 * @author ryan
 * 
 */
public class CommandLinePrime {

	/**
	 * The programs main method when it is executed.
	 * 
	 * @param args
	 *            - The parameters given to the java command
	 * @throws IOException
	 *             - If there is an error when logging to file.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length == 2) {
			if (parseable(args[0]) && parseable(args[1])) {
				for (int i = 0; i != Integer.parseInt(args[1]); i++) {
					calculatePrime(Integer.parseInt(args[0]));
				}
			} else {
				System.err.println("First and second arguemnt must be an integer");//If the number cannot be cast then tell the user that they did it wrong. Silly users. Always getting things wrong.
			}
		} else {
			System.err.println("Must have two argument");//If there are no arguments then tell the user that they did it wrong. Always doing things wrong *shakes head*
		}
	}

	/**
	 * This method will calculate a prime at the given length.
	 * 
	 * @param value
	 *            - The length
	 * @throws IOException
	 *             - If logging to file fails.
	 */
	public static void calculatePrime(int value) throws IOException {
		int counter = 0;//The amount of attempts

		long startTime = System.currentTimeMillis();//When it started
		long time = 0;//The time of generation

		BigInteger integer = null;//The number

		boolean completed = false;//The variable for the while loop

		output("Finding first prime number that should be " + value + " digits long.");
		output("\nBegin.\n");

		while (!completed) {//While it has not been completed
			integer = new BigInteger(generateLongNumber(value));//Set the big int to a newly generated number
			boolean isPrime = integer.isProbablePrime(Integer.MAX_VALUE);//Check if it is prime with a certanty of max integer

			String text = (counter + 1) + "\t|\t" + integer + "\t|\t" + isPrime;//Create the output

			output(text);//Log it to file
			counter++;//increment the counter

			completed = isPrime;//Set completed to if it is prime
		}
		output("\nEnd.\n");//Output the end message
		int characterCount = integer.toString().length();//Get the character count
		time = System.currentTimeMillis();//And set the time
		output("[Gen] It took " + counter + " attempts to find a prime number under " + value + " that prime number was: " + integer + " (" + characterCount + " chars this " + (characterCount == value ? "does" : "does not") + " equal the requested value of " + value + " this is " + (characterCount == value ? "good" : "bad") + ") and it took approximatly " + ((time - startTime) / 1000) + " seconds (" + (time - startTime) + " millis)");
	}

	/**
	 * This is the random object used to generate the numbers.
	 */
	private static Random random = new Random();

	/**
	 * This method will return a number in string form that is the given length
	 * long
	 * 
	 * @param length
	 *            - The length of the number
	 * @return {@link String} - A number in string form that is the given length
	 *         long.
	 */
	public static String generateLongNumber(int length) {
		StringBuilder builder = new StringBuilder();//Create a string builder for forming the number
		for (int i = 0; i != length; i++) {//Until i equals the length
			builder.append(random.nextInt(9) + 1);//Append a new random number 1-9
		}
		return builder.toString();//Then reurn the string version of the number
	}

	/**
	 * This is the file that is being used to output the prime search log
	 */
	private static File output = new File("Outputs/" + new SimpleDateFormat().format(Calendar.getInstance().getTime()).replace('/', '-').replace(':', '-') + ".txt");
	/**
	 * This is the print writer being used to write to the file.
	 */
	private static PrintWriter writer;

	/**
	 * THis method will print the given message to a file.
	 * 
	 * @param text
	 *            - The text to output
	 * @throws IOException
	 *             - If logging to the file fails.
	 */
	public static void output(String text) throws IOException {
		if (writer == null) {//if the writer is null
			if (!output.exists()) //If the file does not exist
				output.createNewFile();//Create the file
			writer = new PrintWriter(output);//Form the output stream
		}
		writer.println(text);//Print the text
		writer.flush();//Flush
		System.out.println(text);//And output it
	}

	/**
	 * This method will check if a string is able to be parsed using
	 * {@link Integer#parseInt(String)}.
	 * 
	 * @param s
	 *            - The string to test
	 * @return {@link Boolean}[In primative form] - If the given string can be
	 *         parsed as an integer
	 */
	public static boolean parseable(String s) {
		try {//Try to 
			Integer.parseInt(s);//Parse the int
			return true;//And return true
		} catch (NumberFormatException e) {//If it does not work
			return false;//Return false
		}
	}
}
