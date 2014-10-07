package zx.guis;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import zx.enums.LogType;

public class PrimeGenerator {

	/**
	 * This stores the time in millis that the prime computation ended at.
	 */
	private long currentTime;
	/**
	 * This stores the time in millis that all the prime computations ended at.
	 */
	private long finalEndTime;
	/**
	 * This stores the time in millis that the prime computation started at.
	 */
	private long startTime;

	/**
	 * This stores the required length of the prime number
	 */
	private int length;
	/**
	 * This stores the amount of prime numbers the generator should make
	 */
	private int generations;
	/**
	 * This stores the amount of attempts it took to generate one prime number 
	 */
	private int localCounter;
	/**
	 * This stores the amount of attempts it took to generate all the prime numbers
	 */
	private int globalCounter;

	/**
	 * This stores whether the current number being processed is prime.
	 */
	private boolean isCurrentPrime;
	/**
	 * This stores whether callback to the starting class is enabled. This is currently deprecated as the calling function does not work
	 * @deprecated
	 */
	private boolean isCallbackEnabled;
	/**
	 * This stores whether the program should log to a file.
	 */
	private boolean isFileLogEnabled;
	/**
	 * This stores whether the program should log to three {@link JTextComponent}s.
	 */
	private boolean isTxtLogEnabled;
	/**
	 * This stores whether the program should output a begin message including the username.
	 */
	private boolean isBeginEnabled = true;

	//These variables are used in the currently deprecated callback system
	private Class<?> callback_class;
	private String callback_format;
	private Method callback_method;
	private Object[] callback_arguments;
	private Class<?>[] callback_classes;

	/**
	 * This is used to generate the random numbers
	 */
	private Random random;
	/**
	 * This is used to storer the current number for testing
	 */
	private BigInteger currentNumber;
	/**
	 * This stores the file that the program will be writing to.
	 */
	private File outputFile;
	/**
	 * This is the print writer that the program will be writing out to.
	 */
	private PrintWriter outputWriter;
	
	/**
	 * This is the logging area used for all general log message
	 */
	private JTextComponent logArea;
	/**
	 * This is the logging area used for the prime number outputs
	 */
	private JTextComponent logPrime;
	/**
	 * This is the logging area used for the counter output
	 */
	private JTextComponent logCounter;

	public PrimeGenerator(int length, int amount, Random random, Class<?> generatorCallbackClass, Method generatorCallback, String callbackArgumentFormat, Class<?>[] callbackArgumentTypes) {
		this.random = random;
		this.length = length;
		this.generations = amount;
		this.callback_class = generatorCallbackClass;
		this.callback_method = generatorCallback;
		this.callback_format = callbackArgumentFormat;
		this.callback_arguments = new Object[callbackArgumentFormat.split("|").length];
		this.callback_classes = callbackArgumentTypes;
		this.isCallbackEnabled = true;
	}

	public PrimeGenerator(int length, Random random, Class<?> generatorCallbackClass, Method generatorCallback, String callbackArgumentFormat, Class<?>[] callbackArgumentTypes) {
		this.random = random;
		this.length = length;
		this.generations = 1;
		this.callback_class = generatorCallbackClass;
		this.callback_method = generatorCallback;
		this.callback_format = callbackArgumentFormat;
		this.callback_arguments = new Object[callbackArgumentFormat.split("|").length];
		this.callback_classes = callbackArgumentTypes;
		this.isCallbackEnabled = true;
	}

	public PrimeGenerator(int length, int amount, Random random) {
		this.random = random;
		this.generations = amount;
		this.length = length;
		this.isCallbackEnabled = false;
	}

	public PrimeGenerator(int length, Random random) {
		this.random = random;
		this.length = length;
		this.generations = 1;
		this.isCallbackEnabled = false;
	}

	public PrimeGenerator(int length, int amount) {
		this.random = new Random();
		this.length = length;
		this.generations = amount;
		this.isCallbackEnabled = false;
	}

	public PrimeGenerator(int length) {
		this.random = new Random();
		this.length = length;
		this.generations = 1;
		this.isCallbackEnabled = false;
	}

	/**
	 * This will set the {@link JTextComponent} fields that the program logs to and enable the logging system.
	 * @param field - The general logging field
	 * @param logPrime - The prime number logging field
	 * @param logAmount - The counter logging field
	 */
	public void setLogOutput(JTextComponent field, JTextComponent logPrime, JTextComponent logAmount) {
		this.logArea = field;
		this.logCounter = logAmount;
		this.logPrime = logPrime;
		this.isTxtLogEnabled = true;
	}

	/**
	 * This will set the file output and enable the file logging system
	 * @param file - The file to log to.
	 */
	public void setFile(File file) {
		try {
			File parent = file.getParentFile();
			if (parent != null) {
				if (!parent.exists())
					parent.mkdirs();
			}
			if (!file.exists())
				file.createNewFile();
			outputFile = file;
			outputWriter = new PrintWriter(file);
			isFileLogEnabled = true;
		} catch (IOException e) {
			isFileLogEnabled = false;
		}
	}

	/**
	 * This will start the loop to find the prime numbers
	 */
	public void startPrimeLoop() {
		this.startTime = System.currentTimeMillis();
		log(LogType.BEGIN);
		for (int i = 0; i != generations; i++) {
			localCounter = 0;
			isCurrentPrime = false;
			currentNumber = null;
			while (!isCurrentPrime) {
				localCounter++;
				currentNumber = new BigInteger(generateRandomNumber());
				testPrime();
				log(LogType.TYRS);
			}
			globalCounter += localCounter;
			log(LogType.SEMI);
		}
		finalEndTime = System.currentTimeMillis();
		log(LogType.FULL);
	}

	/**
	 * This will disable the beginning message that contains the users name
	 */
	public void disableBeginMessage() {
		isBeginEnabled = false;
	}

	/**
	 * This will output a log message
	 * @param logType - Where the message is coming from
	 */
	private void log(LogType logType) {
		fileLog(logType);
		callbackLog(logType);
		txtLog(logType);
	}

	/**
	 * This will attempt to log the the file but it the system is disabled it will just call {@link #stdOutLog(LogType)}.
	 * @param logType - Where the message is coming from
	 */
	private void fileLog(LogType logType) {
		if (isFileLogEnabled) {
			switch (logType) {
			case FULL:
				writeToFile(generateFinalMessage());
				break;
			case SEMI:
				writeToFile(generateSemiFinalMessage());
				break;
			case TYRS:
				writeToFile(generateTryMessage());
				break;
			case BEGIN:
				if (isBeginEnabled)
					writeToFile(generateBeginMessage());
			default:
				break;
			}
		} else {
			stdOutLog(logType);
		}
	}

	/**
	 * This will attempt to log a message to the standard output
	 * @param logType - Where the message is coming from
	 */
	private void stdOutLog(LogType logType) {
		switch (logType) {
		case FULL:
			writeToStdOut(generateFinalMessage());
			break;
		case SEMI:
			writeToStdOut(generateSemiFinalMessage());
			break;
		case TYRS:
			writeToStdOut(generateTryMessage());
			break;
		case BEGIN:
			if (isBeginEnabled)
				writeToStdOut(generateBeginMessage());
		default:
			break;
		}
	}

	@Deprecated
	private void callbackLog(LogType logType) {
		if (isCallbackEnabled) {
			switch (logType) {
			case FULL:
				formArgumentsForFinalLog(true);
				break;
			case SEMI:
				formArgumentsForFinalLog(false);
				break;
			case TYRS:
				formArgumentsForFinalLog(false);
				break;
			default:
				break;
			}
			callbackToCaller();
		}
	}

	/**
	 * This will attempt to log to the text fields
	 * @param logType - Where the message is coming from.
	 */
	private void txtLog(LogType logType) {
		switch (logType) {
		case FULL:
			logArea.setText(logArea.getText() + generateFinalMessage() + "\n");
			logPrime.setText(currentNumber.toString());
			logCounter.setText("" + globalCounter);
			break;
		case SEMI:
			logArea.setText(logArea.getText() + generateSemiFinalMessage() + "\n");
			logPrime.setText(currentNumber.toString());
			logCounter.setText("" + localCounter);
			break;
		case TYRS:
			logArea.setText(logArea.getText() + generateTryMessage() + "\n");
			break;
		case BEGIN:
			logArea.setText(logArea.getText() + generateBeginMessage() + "\n");
			break;
		default:
			break;
		}
		logArea.setCaretPosition(logArea.getText().length());
	}

	private String generateBeginMessage() {
		return "[Gen - ENAB] Attempting to generate " + generations + " prime number" + (generations > 1 ? "s" : "") + " with " + length + " digits. This test is being run by: '" + System.getProperty("user.name") + "' at: '" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + "' on: '" + new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime()) + "'";
	}

	private String generateTryMessage() {
		int chount = currentNumber.toString().length();
		String extension = "th";
		if (localCounter == 1)
			extension = "st";
		if (localCounter == 2)
			extension = "nd";
		if (localCounter == 3)
			extension = "rd";
		return "[Gen - TRYS] This is the " + localCounter + extension + " attempt to find a prime number with " + length + " digits. The current number is: " + currentNumber + " (This number is " + chount + " characters long. This is a " + (chount == length ? "good" : "bad") + " result). This number " + (isCurrentPrime ? "IS" : "IS NOT") + " a prime number.";
	}

	private String generateSemiFinalMessage() {
		int chount = currentNumber.toString().length();
		return "\n[Gen - SEMI] It took " + localCounter + " attempts to find a prime number with " + length + " digits. That prime number was: " + currentNumber + " (This prime number ended up being " + chount + " characters long. This is a " + (chount == length ? "good" : "bad") + " result) and according to the system it took approximatly " + ((currentTime - startTime) / 1000) + " seconds (" + (currentTime - startTime) + " millis)";
	}

	private String generateFinalMessage() {
		return "\n[Gen - FULL] It took " + globalCounter + " attempts to find " + generations + " prime number" + (generations > 1 ? "s" : "") + " with " + length + " digits. According to the system this complete opperation took approximatly " + ((finalEndTime - startTime) / 1000) + " seconds or " + (finalEndTime - startTime) + " milliseconds.";
	}

	private String generateRandomNumber() {
		StringBuilder builder = new StringBuilder();//Create a string builder for forming the number
		for (int i = 0; i != length; i++) {//Until i equals the length
			builder.append(random.nextInt(9) + 1);//Append a new random number 1-9
		}
		return builder.toString();//Then return the string version of the number
	}

	private void testPrime() {
		isCurrentPrime = currentNumber.isProbablePrime(Integer.MAX_VALUE);
		currentTime = System.currentTimeMillis();
	}

	private void formArgumentsForFinalLog(boolean isFinal) {
		String[] split = callback_format.split("|");
		Object[] returned = new Object[split.length];//Also known as Object[] illBeBack = new Object[split.length];
		for (int i = 0; i < split.length; i++) {
			String s = split[i];
			switch (s) {
			case "result":
				returned[i] = currentNumber;
				break;
			case "time":
				returned[i] = currentTime;
				break;
			case "start":
				returned[i] = startTime;
				break;
			case "attempts":
				returned[i] = localCounter;
				break;
			case "length":
				returned[i] = length;
				break;
			case "amount":
				returned[i] = generations;
				break;
			case "calc_time":
				returned[i] = ((currentTime - startTime) / 1000);
				break;
			case "final_time":
				returned[i] = finalEndTime;
				break;
			case "final_calc_time":
				returned[i] = ((finalEndTime - startTime) / 1000);
				break;
			case "final_count":
				returned[i] = globalCounter;
				break;
			case "is_final":
				returned[i] = isFinal;
				break;
			case "try_message":
				returned[i] = generateTryMessage();
				break;
			case "semi_message":
				returned[i] = generateSemiFinalMessage();
				break;
			case "full_message":
				returned[i] = generateFinalMessage();
				break;
			}
		}
		callback_arguments = returned;
	}

	private void callbackToCaller() {
		//		try {
		//			callback_method.invoke(callback_arguments, callback_classes);
		//		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		//			e.printStackTrace();
		//		}
	}

	private void writeToFile(String message) {
		writeToStdOut(message);
		outputWriter.println(message);
		outputWriter.flush();
	}

	private void writeToStdOut(String message) {
		System.out.println(message);
	}

}
