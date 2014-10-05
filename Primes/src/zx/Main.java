package zx;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Main {

	public static void main(String[] args) throws IOException {
		int value = 400;//Then amount of digits
		int counter = 0;//The amount of attempts
		long startTime = System.currentTimeMillis();//When it started
		long time = 0;//The time of generation
		BigInteger integer = null;//The number
		output("\t\t\t\t\tValue\t\t\t\t\t\t\t\t|\tPrime");//Ouput the header to file
		System.out.println("\t\t\t\t\tValue\t\t\t\t\t\t\t\t|\tPrime");//And print out the message
		boolean completed = false;//The variable for the while loop
		while (!completed) {//While it has not been completed
			integer = new BigInteger(generateLongNumber(value));//Set the big int to a newly generated number
			boolean isPrime = integer.isProbablePrime(Integer.MAX_VALUE);//Check if it is prime with a certanty of max integer
			String text = integer + "\t|\t" + isPrime;//Create the output
			System.out.println(text);//Print it out
			output(text);//Log it to file
			counter++;//increment the counter
			time = System.currentTimeMillis();//And set the time
			completed = isPrime;//Set completed to if it is prime
		}
		System.out.println("[Gen] It took " + counter + " attempts to find a prime number under " + value + " that prime number was: " + integer + " and it took " + ((time - startTime) / 1000) + " seconds (" + (time - startTime) + " millis)");//Finally output the log message.
	}

	static Random random = new Random();

	public static String generateLongNumber(int length) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			builder.append(random.nextInt(10));
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
	}
}
