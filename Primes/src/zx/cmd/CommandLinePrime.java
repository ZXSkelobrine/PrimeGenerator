package zx.cmd;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class CommandLinePrime {

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

	static Random random = new Random();

	public static String generateLongNumber(int length) {
		StringBuilder builder = new StringBuilder();//Create a string builder for forming the number
		for (int i = 0; i != length; i++) {//Until i equals the length
			builder.append(random.nextInt(9) + 1);//Append a new random number 1-9
		}
		return builder.toString();//Then reurn the string version of the number
	}

	static File output = new File("Outputs/" + new SimpleDateFormat().format(Calendar.getInstance().getTime()).replace('/', '-').replace(':', '-') + ".txt");
	static PrintWriter writer;

	public static void output(String text) throws IOException {
		if (writer == null) {//if the writer is null
			if (!output.exists())//If the file does not exist
				output.createNewFile();//Create the file
			writer = new PrintWriter(output);//Form the output stream
		}
		writer.println(text);//Print the text
		writer.flush();//Flush
		System.out.println(text);//And output it
	}

	public static boolean parseable(String s) {
		try {//Try to 
			Integer.parseInt(s);//Parse the int
			return true;//And return true
		} catch (NumberFormatException e) {//If it does not work
			return false;//Return false
		}
	}
}
