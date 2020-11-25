import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MyHashTableTester {
	/**
	 * Stores the size that the hash table should be which can be passed to the constructor
	 * <p>
	 * The default constructor for MyHashTable creates a table of size 21.
	 */
	public static final int tableSize = 221;
	private static final String PATH = "assets/";

	public static void main(String[] args) throws IOException {

		MyHashTable<Person, PhoneNumber> book = new MyHashTable<>(tableSize);
		Scanner scan = new Scanner(new File(PATH + "White Pages.txt"));

		while(scan.hasNextLine()) {
			String[] parts = scan.nextLine().split(",");
			Person p = new Person(parts[0], parts[1]);
			PhoneNumber n = new PhoneNumber(parts[2]);

			try {
				book.put(p, n);
			} catch(IllegalArgumentException e) {
				e.printStackTrace();
			}
		}

		PrintWriter pw = new PrintWriter(new FileWriter(PATH + "MyHashTableTesting.out"));

		pw.println(book);
	}
}