package zx;

import java.io.IOException;
import java.util.Arrays;

import zx.cmd.CommandLinePrime;
import zx.guis.Primes;

/**
 * This is the launcher that is used to execute either the gui based version of
 * the program or the command line based one.
 * 
 * @author ryan
 * 
 */
public class Launcher {

	/**
	 * This is the main method called by java when the program is executed
	 * 
	 * @param args
	 *            - The command line arguments
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length > 0) {
			if (args[0].equals("--cmd")) {
				CommandLinePrime.main(Arrays.copyOfRange(args, 1, args.length));
			} else {
				Primes.main(args);
			}
		} else {
			Primes.main(args);
		}
	}

}
