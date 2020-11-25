import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

class PhoneBookJUnitTests {

	private static PhoneBook table;

	private static final String FILENAME = "White Pages.txt";

	@BeforeAll
	public static void setUp() throws FileNotFoundException {
		table = new PhoneBook(227);

		Scanner scan = new Scanner(new File(FILENAME));

		while(scan.hasNextLine()) {
			String[] parts = scan.nextLine().split(",");
			Person p = new Person(parts[0], parts[1]);
			PhoneNumber n = new PhoneNumber(parts[2]);
			table.put(p, n);
		}
	}

	@Test
	void getExistingElement() {
		Assertions.assertEquals(new PhoneNumber("180-837-9567"), table.get(new Person("Jada", "Glanville")));
	}

	@Test
	void getNonExistingElement() {
		Assertions.assertEquals(null, table.get(new Person("Ari", "Hu")));
	}

	@Test
	void removeExistingElement() {
		Assertions.assertEquals(new PhoneNumber("488-670-5376"), table.remove(new Person("Elena", "Tomasz")));
	}

	@Test
	void removeNonExistingElement() {
		Assertions.assertEquals(null, table.remove(new Person("Ari", "Hu")));
	}

	@Test
	void putExisitingElement() {
		boolean caught = false;
		try {
			table.put(new Person("Lorna", "Kenningham"), new PhoneNumber("470-841-4741"));
		} catch(IllegalArgumentException e) {
			caught = true;
		}
		Assertions.assertEquals(true, caught);
	}

	@Test
	void putNonExistingElement() {
		Assertions.assertEquals(new PhoneNumber("932-641-6002"), table.put(new Person("Ethan", "Maher"), new PhoneNumber("123-456-7890")));
	}
}