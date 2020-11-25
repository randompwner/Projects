import java.io.IOException;
import java.util.Scanner;

/**
 * This class is a runner for HuffmanCoding
 * <p>
 * The class will compress .txt files into a .short, the compressed file, and a
 * .code, the encoded file
 * <p>
 * The class will expand .short files into a .new
 */
public class Runner {
	private static final String PATH = "assets/";

	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the file the compress and expand: ");
		String filename = sc.nextLine();

		if (filename.contains("."))
			filename = filename.substring(0, filename.indexOf("."));

		HuffmanCompressor compressor = new HuffmanCompressor();
		System.out.println("Compressing " + filename + ".txt...\n");

		long t1 = System.nanoTime();
		compressor.compress(filename + ".txt");
		System.out.printf("Compression takes %.2f seconds%n", (System.nanoTime() - t1) / 1e9);

		long t2 = System.nanoTime();
		compressor.expand(filename + ".short", filename + ".new");
		System.out.printf("Expansion takes %.2f seconds%n%n", (System.nanoTime() - t2) / 1e9);

		System.out.printf("Total Time: %.2f seconds", (System.nanoTime() - t1) / 1e9);

	}
}