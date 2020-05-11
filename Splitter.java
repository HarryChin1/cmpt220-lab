package lab11;

import java.io.*;

public class Splitter {
	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Usage: java Splitter SourceFile numberOfPieces");
			System.exit(1);
		}

		// Get the number of bits/pieces from source file
		int amountOfBits = Integer.parseInt(args[1]);

		RandomAccessFile[] split = new RandomAccessFile[amountOfBits];

		try (RandomAccessFile inout = new RandomAccessFile(args[0], "r");) {

			for (int i = 0; i < split.length; i++) {
				split[i] = new RandomAccessFile(args[0] + "." + (i + 1), "rw");
			}
			int amount = Math.round(inout.length() / amountOfBits);
			int compute = 0; // sums up the number of pieces written
			byte[] b;

			for (int i = 0; i < amountOfBits - 1; i++) {
				inout.seek(compute * amount);
				b = new byte[amount];
				inout.read(b);
				split[i].write(b);
				compute++;
			}
			inout.seek(compute * amount);
			b = new byte[(int) inout.length() - (compute * amount)];
			inout.read(b);
			split[amountOfBits - 1].write(b);
		}
	}
}
