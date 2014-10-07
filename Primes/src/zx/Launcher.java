package zx;

import java.io.IOException;
import java.util.Arrays;

import zx.cmd.CommandLinePrime;
import zx.guis.Primes;

public class Launcher {

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
