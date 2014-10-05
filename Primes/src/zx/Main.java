package zx;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.ObjectOutputStream.PutField;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Main {

	public static void main(String[] args) throws IOException {
		int value = 500;//Then amount of digits
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

			String text = integer + "\t|\t" + isPrime;//Create the output

			output(text);//Log it to file
			counter++;//increment the counter

			time = System.currentTimeMillis();//And set the time
			completed = isPrime;//Set completed to if it is prime
		}

		output("\nEnd.\n");
		int characterCount = integer.toString().length();
		output("[Gen] It took " + counter + " attempts to find a prime number under " + value + " that prime number was: " + integer + " (" + characterCount + " chars this " + (characterCount == value ? "does" : "does not") + " equal the requested value of " + value + " this is " + (characterCount == value ? "good" : "bad") + ") and it took approximatly " + ((time - startTime) / 1000) + " seconds (" + (time - startTime) + " millis)");
	}

	static Random random = new Random();

	public static String generateLongNumber(int length) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i != length; i++) {
			builder.append(random.nextInt(9) + 1);
		}
		return builder.toString();
	}

	static File output = new File("Outputs/" + new SimpleDateFormat().format(Calendar.getInstance().getTime()).replace('/', '-').replace(':', '-') + ".txt");
	static PrintWriter writer;

	public static void output(String text) throws IOException {
		if (writer == null) {
			if (!output.exists())
				output.createNewFile();
			writer = new PrintWriter(output);
		}
		writer.println(text);
		writer.flush();
		System.out.println(text);
	}
}
