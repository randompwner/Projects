/**
 * The Person class represents a person to be used in the Hash Table.
 *
 * @author Ethan Maher
 */
public class Person implements Comparable<Person> {
	/**
	 * Stores the first name of the Person object
	 */
	private String firstName;

	/**
	 * Stores the last name of the Person object
	 */
	private String lastName;

	/**
	 * Creates a Person object with a given first and last name
	 *
	 * @param firstName the given first name
	 * @param lastName  the given last name
	 */
	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Hashes the persons name by taking 31 times the summing hash + the current character in the name and returning
	 * the absolute value of that mod the size of the table
	 *
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		int hash = 0;

		for(int i = 0; i < firstName.length(); i++) {
			hash += 31 * hash + firstName.charAt(i);
		}

		for(int i = 0; i < lastName.length(); i++) {
			hash += 31 * hash + lastName.charAt(i);
		}

		return Math.abs(hash % MyHashTableTester.tableSize);

	}

	/**
	 * Compares two Person objects and returns if they are equal
	 *
	 * @param obj the Person to compare to this
	 * @return true if equal false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		Person p = (Person) obj;
		return this.firstName.equals(p.firstName) && this.lastName.equals(p.lastName);
	}

	/**
	 * Compares two people using the compareTo of each of their names
	 *
	 * @param p the Person to compare to this
	 * @return the compareTo of the names if they are not equal, 0 if they are equal
	 */
	@Override
	public int compareTo(Person p) {
		if(! this.firstName.equals(p.firstName)) {
			return this.firstName.compareTo(p.firstName);
		} else if(! this.lastName.equals(p.lastName)) {
			return this.lastName.compareTo(p.lastName);
		} else {
			return 0;
		}
	}

	/**
	 * Returns the Persons name
	 *
	 * @return the Persons name
	 */
	@Override
	public String toString() {
		return this.firstName + " " + this.lastName;
	}
}
