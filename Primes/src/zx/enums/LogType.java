package zx.enums;

import zx.guis.PrimeGenerator;

/**
 * These are the log types used when output messages in {@link PrimeGenerator}.
 * 
 * @author ryan
 * 
 */
public enum LogType {

	/**
	 * This is the log type used if it is the begin message
	 */
	BEGIN,
	/**
	 * This is the log type used if it is an attempt to find a prime
	 */
	TYRS,
	/**
	 * This is used when the program has found a prime but it is not final
	 */
	SEMI,
	/**
	 * This is used when all of the requested prime numbers have been found
	 */
	FULL

}
