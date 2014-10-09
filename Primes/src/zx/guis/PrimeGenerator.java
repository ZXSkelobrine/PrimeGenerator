package zx.guis;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javax.swing.text.JTextComponent;

import zx.enums.LogType;

/**
 * This is the object used to generate the prime number. It comes will a lot (<a
 * href=
 * "http://thewritepractice.com/wp-content/uploads/2012/05/Alot-vs-a-lot1-600x450.png"
 * >Alot</a> more features so it looks quite bloated.
 * 
 * @author ryan
 * 
 */
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
	 * This stores the amount of attempts it took to generate all the prime
	 * numbers
	 */
	private int globalCounter;
	/**
	 * This is the index of the current prime. (Eg if it is generating 10 primes
	 * and it is on the 5th this will equal 5)
	 */
	private int currentPrimeSearch;

	/**
	 * This stores whether the current number being processed is prime.
	 */
	private boolean isCurrentPrime;
	/**
	 * This stores whether callback to the starting class is enabled. This is
	 * currently deprecated as the calling function does not work
	 * 
	 * @deprecated
	 */
	private boolean isCallbackEnabled;
	/**
	 * This stores whether the program should log to a file.
	 */
	private boolean isFileLogEnabled;
	/**
	 * This stores whether the system has been canceled
	 */
	private boolean isCanceled;

	/**
	 * This array stores whether the program should log to 5
	 * {@link JTextComponent}s.
	 */
	private boolean[] isTxtLogEnabled = new boolean[5];
	/**
	 * This stores whether the program should output a begin message including
	 * the username.
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
	private JTextComponent logLocalCounter;
	/**
	 * This is the logging area used for the global counter output
	 */
	private JTextComponent logGlobalCounter;
	/**
	 * This is the logging area used for the current prime.
	 */
	private JTextComponent logCurrentPrime;

	/**
	 * This will spawn a Prime generator with the given length and amount. It
	 * will use the given random so seeds can be used. It also takes 4 arguments
	 * relating to the callback system that is currently disabled.
	 * 
	 * @param length
	 *            - The length of the prime number
	 * @param amount
	 *            - The amount of primes to generate
	 * @param random
	 *            - The random object to use
	 * @param generatorCallbackClass
	 *            - The class to call back to
	 * @param generatorCallback
	 *            - The method to call back
	 * @param callbackArgumentFormat
	 *            - The format of the aguments
	 * @param callbackArgumentTypes
	 *            - The class list of arguments
	 */
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

	/**
	 * This will spawn a Prime generator with the given length. It
	 * will use the given random so seeds can be used. It also takes 4 arguments
	 * relating to the callback system that is currently disabled.
	 * 
	 * @param length
	 *            - The length of the prime number
	 * @param random
	 *            - The random object to use
	 * @param generatorCallbackClass
	 *            - The class to call back to
	 * @param generatorCallback
	 *            - The method to call back
	 * @param callbackArgumentFormat
	 *            - The format of the aguments
	 * @param callbackArgumentTypes
	 *            - The class list of arguments
	 */
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

	/**
	 * This will spawn a Prime generator with the given length and amount. It
	 * will use the given random so seeds can be used.
	 * 
	 * @param length
	 *            - The length of the prime number
	 * @param amount
	 *            - The amount of primes to generate
	 * @param random
	 *            - The random object to use
	 */
	public PrimeGenerator(int length, int amount, Random random) {
		this.random = random;
		this.generations = amount;
		this.length = length;
		this.isCallbackEnabled = false;
	}

	/**
	 * This will spawn a Prime generator with the given length. It
	 * will use the given random so seeds can be used.
	 * 
	 * @param length
	 *            - The length of the prime number
	 * @param random
	 *            - The random object to use
	 */
	public PrimeGenerator(int length, Random random) {
		this.random = random;
		this.length = length;
		this.generations = 1;
		this.isCallbackEnabled = false;
	}

	/**
	 * This will spawn a Prime generator with the given length and amount.
	 * 
	 * @param length
	 *            - The length of the prime number
	 * @param amount
	 *            - The amount of primes to generate
	 */
	public PrimeGenerator(int length, int amount) {
		this.random = new Random();
		this.length = length;
		this.generations = amount;
		this.isCallbackEnabled = false;
	}

	/**
	 * This will spawn a Prime generator with the given length.
	 * 
	 * @param length
	 *            - The length of the prime number
	 */
	public PrimeGenerator(int length) {
		this.random = new Random();
		this.length = length;
		this.generations = 1;
		this.isCallbackEnabled = false;
	}

	/**
	 * This will set the {@link JTextComponent} fields that the program logs to
	 * and enable the logging system.
	 * 
	 * @param logMain
	 *            - The general logging field
	 * @param logPrime
	 *            - The prime number logging field
	 * @param logLocalAttempts
	 *            - The counter logging field
	 * @param logGlobalAttempts
	 *            - This is the field to log the global attempts to
	 * @param logCurrentPrimeSearch
	 *            - This is the field to log the current prime being searched
	 *            for to.
	 */
	public void setLogOutput(JTextComponent logMain, JTextComponent logPrime, JTextComponent logLocalAttempts, JTextComponent logGlobalAttempts, JTextComponent logCurrentPrimeSearch) {
		if (logMain != null) {
			this.logArea = logMain;
			isTxtLogEnabled[0] = true;
		}
		if (logLocalAttempts != null) {
			this.logLocalCounter = logLocalAttempts;
			isTxtLogEnabled[1] = true;
		}
		if (logPrime != null) {
			this.logPrime = logPrime;
			isTxtLogEnabled[2] = true;
		}
		if (logGlobalAttempts != null) {
			this.logGlobalCounter = logGlobalAttempts;
			isTxtLogEnabled[3] = true;
		}
		if (logCurrentPrimeSearch != null) {
			this.logCurrentPrime = logCurrentPrimeSearch;
			isTxtLogEnabled[4] = true;
		}
	}

	/**
	 * This will set the file output and enable the file logging system
	 * 
	 * @param file
	 *            - The file to log to.
	 */
	public void setFile(File file) {
		try {
			File parent = file.getParentFile();
			if (parent != null) {
				if (!parent.exists()) parent.mkdirs();
			}
			if (!file.exists()) file.createNewFile();
			outputFile = file;
			outputWriter = new PrintWriter(file);
			isFileLogEnabled = true;
		} catch (IOException e) {
			isFileLogEnabled = false;
		}
	}

	/**
	 * This will cancel the search for a prime.
	 */
	public void cancelSearch() {
		isCanceled = true;
	}

	/**
	 * This will start the loop to find the prime numbers
	 */
	public void startPrimeLoop() {
		this.startTime = System.currentTimeMillis();
		this.isCanceled = false;
		log(LogType.BEGIN);
		for (int i = 0; i != generations; i++) {
			localCounter = 0;
			isCurrentPrime = false;
			currentNumber = null;
			while (!isCurrentPrime && !isCanceled) {
				localCounter++;
				globalCounter++;
				currentNumber = new BigInteger(generateRandomNumber());
				testPrime();
				log(LogType.TYRS);
			}
			currentPrimeSearch++;
			log(LogType.SEMI);
			if (isCanceled) break;
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
	 * 
	 * @param logType
	 *            - Where the message is coming from
	 */
	private void log(LogType logType) {
		fileLog(logType);
		callbackLog(logType);
		txtLog(logType);
	}

	/**
	 * This will attempt to log the the file but it the system is disabled it
	 * will just call {@link #stdOutLog(LogType)}.
	 * 
	 * @param logType
	 *            - Where the message is coming from
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
				if (isBeginEnabled) writeToFile(generateBeginMessage());
			default:
				break;
			}
		} else {
			stdOutLog(logType);
		}
	}

	/**
	 * This will attempt to log a message to the standard output
	 * 
	 * @param logType
	 *            - Where the message is coming from
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
			if (isBeginEnabled) writeToStdOut(generateBeginMessage());
		default:
			break;
		}
	}

	/**
	 * This method will callback to the started
	 * 
	 * @param logType
	 *            - The log type.
	 * @deprecated
	 */
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
	 * 
	 * @param logType
	 *            - Where the message is coming from.
	 */
	private void txtLog(LogType logType) {
		switch (logType) {
		case FULL:
			if (isTxtLogEnabled[0]) logArea.setText(logArea.getText() + generateFinalMessage() + "\n");
			//PRIME
			if (isTxtLogEnabled[2]) logLocalCounter.setText("" + localCounter);
			if (isTxtLogEnabled[3]) logGlobalCounter.setText("" + globalCounter);
			//CURRENT PRIME
			break;
		case SEMI:
			if (isTxtLogEnabled[0]) logArea.setText(logArea.getText() + generateSemiFinalMessage() + "\n");
			if (isTxtLogEnabled[1]) logPrime.setText(currentNumber.toString());
			if (isTxtLogEnabled[2]) logLocalCounter.setText("" + localCounter);
			if (isTxtLogEnabled[3]) logGlobalCounter.setText("" + globalCounter);
			if (isTxtLogEnabled[4]) logCurrentPrime.setText("" + currentPrimeSearch);
			break;
		case TYRS:
			if (isTxtLogEnabled[0]) logArea.setText(logArea.getText() + generateTryMessage() + "\n");
			//PRIME
			if (isTxtLogEnabled[2]) logLocalCounter.setText("" + localCounter);
			if (isTxtLogEnabled[3]) logGlobalCounter.setText("" + globalCounter);
			//CURRENT PRIME
			break;
		case BEGIN:
			if (isTxtLogEnabled[0]) logArea.setText(logArea.getText() + generateBeginMessage() + "\n");
			if (isTxtLogEnabled[4]) logCurrentPrime.setText("" + currentPrimeSearch);
			break;
		default:
			break;
		}
		if (isTxtLogEnabled[0]) logArea.setCaretPosition(logArea.getText().length());
	}

	/**
	 * This will generate the message when the search starts.
	 * 
	 * @return {@link String} - The begin message
	 */
	private String generateBeginMessage() {
		return "[Gen - ENAB] Attempting to generate " + generations + " prime number" + (generations > 1 ? "s" : "") + " with " + length + " digits. This test is being run by: '" + System.getProperty("user.name") + "' at: '" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + "' on: '" + new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime()) + "'";
	}

	/**
	 * This will generate the message when the program tries a prime starts.
	 * 
	 * @return {@link String} - The try message
	 */
	private String generateTryMessage() {
		int chount = currentNumber.toString().length();
		String extension = "th";
		if (localCounter == 1) extension = "st";
		if (localCounter == 2) extension = "nd";
		if (localCounter == 3) extension = "rd";
		return "[Gen - TRYS] This is the " + localCounter + extension + " attempt to find a prime number with " + length + " digits. The current number is: " + currentNumber + " (This number is " + chount + " characters long. This is a " + (chount == length ? "good" : "bad") + " result). This number " + (isCurrentPrime ? "IS" : "IS NOT") + " a prime number.";
	}

	/**
	 * This will generate the message when the program finds a prime.
	 * 
	 * @return {@link String} - The semi-final message
	 */
	private String generateSemiFinalMessage() {
		int chount = currentNumber.toString().length();
		return "\n[Gen - SEMI] It took " + localCounter + " attempts to find a prime number with " + length + " digits. That prime number was: " + currentNumber + " (This prime number ended up being " + chount + " characters long. This is a " + (chount == length ? "good" : "bad") + " result) and according to the system it took approximatly " + ((currentTime - startTime) / 1000) + " seconds (" + (currentTime - startTime) + " millis)";
	}

	/**
	 * This will generate the message when the program completes its search.
	 * 
	 * @return {@link String} - The final message
	 */
	private String generateFinalMessage() {
		return "\n[Gen - FULL] It took " + globalCounter + " attempts to find " + generations + " prime number" + (generations > 1 ? "s" : "") + " with " + length + " digits. According to the system this complete opperation took approximatly " + ((finalEndTime - startTime) / 1000) + " seconds or " + (finalEndTime - startTime) + " milliseconds.";
	}

	/**
	 * This method will generate a random number up to the length that was given
	 * 
	 * @return {@link String} - The number
	 */
	private String generateRandomNumber() {
		StringBuilder builder = new StringBuilder();//Create a string builder for forming the number
		for (int i = 0; i != (length - 1); i++) {//Until i equals the length
			builder.append(random.nextInt(9) + 1);//Append a new random number 1-9
		}
		builder.append(generatePsudorandomOddNumber());
		return builder.toString();//Then return the string version of the number
	}

	/**
	 * This method will test if the current number is a prime.
	 */
	private void testPrime() {
		isCurrentPrime = currentNumber.isProbablePrime(Integer.MAX_VALUE);
		currentTime = System.currentTimeMillis();
	}

	/**
	 * This will attempt to form the arguments into an array for callback.
	 * 
	 * @param isFinal
	 *            - If it is the final log.
	 */
	@Deprecated
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

	/**
	 * This will attempt to callback to the caller.
	 * 
	 * @deprecated
	 */
	@Deprecated
	private void callbackToCaller() {
		//		try {
		//			callback_method.invoke(callback_arguments, callback_classes);
		//		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		//			e.printStackTrace();
		//		}
	}

	/**
	 * This will attempt to write the given string to file.
	 * 
	 * @param message
	 *            - The message to write to file.
	 */
	private void writeToFile(String message) {
		writeToStdOut(message);
		outputWriter.println(message);
		outputWriter.flush();
	}

	/**
	 * This will attempt to write the given string to the standard output.
	 * 
	 * @param message
	 *            - The message
	 */
	private void writeToStdOut(String message) {
		System.out.println(message);
	}

	/**
	 * This will generate an odd number using the random object
	 * 
	 * @return {@link Integer}[In primitive form] - The odd number
	 */
	private int generatePsudorandomOddNumber() {
		int value = 0;
		while (value % 2 == 0) {
			value = random.nextInt(9) + 1;
		}
		return value;
	}

	/**
	 * @return the currentTime
	 */
	public long getCurrentTime() {
		return currentTime;
	}

	/**
	 * @param currentTime
	 *            the currentTime to set
	 */
	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}

	/**
	 * @return the finalEndTime
	 */
	public long getFinalEndTime() {
		return finalEndTime;
	}

	/**
	 * @param finalEndTime
	 *            the finalEndTime to set
	 */
	public void setFinalEndTime(long finalEndTime) {
		this.finalEndTime = finalEndTime;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @return the generations
	 */
	public int getGenerations() {
		return generations;
	}

	/**
	 * @param generations
	 *            the generations to set
	 */
	public void setGenerations(int generations) {
		this.generations = generations;
	}

	/**
	 * @return the localCounter
	 */
	public int getLocalCounter() {
		return localCounter;
	}

	/**
	 * @param localCounter
	 *            the localCounter to set
	 */
	public void setLocalCounter(int localCounter) {
		this.localCounter = localCounter;
	}

	/**
	 * @return the globalCounter
	 */
	public int getGlobalCounter() {
		return globalCounter;
	}

	/**
	 * @param globalCounter
	 *            the globalCounter to set
	 */
	public void setGlobalCounter(int globalCounter) {
		this.globalCounter = globalCounter;
	}

	/**
	 * @return the isCurrentPrime
	 */
	public boolean isCurrentPrime() {
		return isCurrentPrime;
	}

	/**
	 * @param isCurrentPrime
	 *            the isCurrentPrime to set
	 */
	public void setCurrentPrime(boolean isCurrentPrime) {
		this.isCurrentPrime = isCurrentPrime;
	}

	/**
	 * @return the isCallbackEnabled
	 */
	public boolean isCallbackEnabled() {
		return isCallbackEnabled;
	}

	/**
	 * @param isCallbackEnabled
	 *            the isCallbackEnabled to set
	 */
	public void setCallbackEnabled(boolean isCallbackEnabled) {
		this.isCallbackEnabled = isCallbackEnabled;
	}

	/**
	 * @return the isFileLogEnabled
	 */
	public boolean isFileLogEnabled() {
		return isFileLogEnabled;
	}

	/**
	 * @param isFileLogEnabled
	 *            the isFileLogEnabled to set
	 */
	public void setFileLogEnabled(boolean isFileLogEnabled) {
		this.isFileLogEnabled = isFileLogEnabled;
	}

	/**
	 * @return the isTxtLogEnabled
	 */
	public boolean[] isTxtLogEnabled() {
		return isTxtLogEnabled;
	}

	/**
	 * @param isTxtLogEnabled
	 *            the isTxtLogEnabled to set
	 */
	public void setTxtLogEnabled(boolean isTxtLogEnabled[]) {
		this.isTxtLogEnabled = isTxtLogEnabled;
	}

	/**
	 * @return the isBeginEnabled
	 */
	public boolean isBeginEnabled() {
		return isBeginEnabled;
	}

	/**
	 * @param isBeginEnabled
	 *            the isBeginEnabled to set
	 */
	public void setBeginEnabled(boolean isBeginEnabled) {
		this.isBeginEnabled = isBeginEnabled;
	}

	/**
	 * @return the callback_class
	 */
	public Class<?> getCallback_class() {
		return callback_class;
	}

	/**
	 * @param callback_class
	 *            the callback_class to set
	 */
	public void setCallback_class(Class<?> callback_class) {
		this.callback_class = callback_class;
	}

	/**
	 * @return the callback_format
	 */
	public String getCallback_format() {
		return callback_format;
	}

	/**
	 * @param callback_format
	 *            the callback_format to set
	 */
	public void setCallback_format(String callback_format) {
		this.callback_format = callback_format;
	}

	/**
	 * @return the callback_method
	 */
	public Method getCallback_method() {
		return callback_method;
	}

	/**
	 * @param callback_method
	 *            the callback_method to set
	 */
	public void setCallback_method(Method callback_method) {
		this.callback_method = callback_method;
	}

	/**
	 * @return the callback_arguments
	 */
	public Object[] getCallback_arguments() {
		return callback_arguments;
	}

	/**
	 * @param callback_arguments
	 *            the callback_arguments to set
	 */
	public void setCallback_arguments(Object[] callback_arguments) {
		this.callback_arguments = callback_arguments;
	}

	/**
	 * @return the callback_classes
	 */
	public Class<?>[] getCallback_classes() {
		return callback_classes;
	}

	/**
	 * @param callback_classes
	 *            the callback_classes to set
	 */
	public void setCallback_classes(Class<?>[] callback_classes) {
		this.callback_classes = callback_classes;
	}

	/**
	 * @return the random
	 */
	public Random getRandom() {
		return random;
	}

	/**
	 * @param random
	 *            the random to set
	 */
	public void setRandom(Random random) {
		this.random = random;
	}

	/**
	 * @return the currentNumber
	 */
	public BigInteger getCurrentNumber() {
		return currentNumber;
	}

	/**
	 * @param currentNumber
	 *            the currentNumber to set
	 */
	public void setCurrentNumber(BigInteger currentNumber) {
		this.currentNumber = currentNumber;
	}

	/**
	 * @return the outputFile
	 */
	public File getOutputFile() {
		return outputFile;
	}

	/**
	 * @param outputFile
	 *            the outputFile to set
	 */
	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	/**
	 * @return the outputWriter
	 */
	public PrintWriter getOutputWriter() {
		return outputWriter;
	}

	/**
	 * @param outputWriter
	 *            the outputWriter to set
	 */
	public void setOutputWriter(PrintWriter outputWriter) {
		this.outputWriter = outputWriter;
	}

	/**
	 * @return the logArea
	 */
	public JTextComponent getLogArea() {
		return logArea;
	}

	/**
	 * @param logArea
	 *            the logArea to set
	 */
	public void setLogArea(JTextComponent logArea) {
		this.logArea = logArea;
	}

	/**
	 * @return the logPrime
	 */
	public JTextComponent getLogPrime() {
		return logPrime;
	}

	/**
	 * @param logPrime
	 *            the logPrime to set
	 */
	public void setLogPrime(JTextComponent logPrime) {
		this.logPrime = logPrime;
	}

	/**
	 * @return the logCounter
	 */
	public JTextComponent getLogCounter() {
		return logLocalCounter;
	}

	/**
	 * @param logCounter
	 *            the logCounter to set
	 */
	public void setLogCounter(JTextComponent logCounter) {
		this.logLocalCounter = logCounter;
	}

}
