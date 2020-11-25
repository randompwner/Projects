import java.util.ArrayList;
import java.util.List;

/**
 * The PhoneBook class stores the hashtable of Entry objects that are Key/Value pairs of Person/PhoneNumber
 */
public class PhoneBook implements IMap {
	/**
	 * Stores the logical size of the hashtable
	 */
	public int size;

	/**
	 * Stores Entry objects as a hash table
	 */
	public Entry[] table;

	/**
	 * Instantiates the hash table to be size 21
	 */
	public PhoneBook() {
		this.table = new Entry[21];
	}

	/**
	 * Instantiates a hash table of a passed size
	 *
	 * @param size the size of the hash table
	 */
	public PhoneBook(int size) {
		this.table = new Entry[size];
	}

	/**
	 * Inserts a Person/PhoneNumber pair into the HashTable and returns the previous value at the same key
	 *
	 * @param key the Person object to be added to the hash table
	 * @param val the PhoneNumber object to be added to the table
	 * @return the previous value mapped at the key
	 */
	@Override
	public PhoneNumber put(Person key, PhoneNumber val) {
		int hc = key.hashCode();
		Entry entry = new Entry(key, val);
		Entry curr = this.table[hc];
		if(curr != null) {
			while(curr.next != null) {
				curr = curr.next;

				if(curr.person.equals(key)) {
					throw new IllegalArgumentException("Person Already Exists in Phone Book"); // already exists
				}
			}
			curr.next = entry;
			size++;
			return curr.phoneNumber;
		} else {
			this.table[hc] = entry;
			size++;
			return null;
		}
	}

	/**
	 * Returns the PhoneNumber of the Person object passed
	 *
	 * @param person the Person to get the PhoneNumber of
	 * @return the PhoneNumber of Person person
	 */
	@Override
	public PhoneNumber get(Person person) {
		int hc = person.hashCode();
		Entry temp = this.table[hc];
		while(temp != null) {
			if(temp.person.equals(person)) {
				return temp.phoneNumber;
			}
			temp = temp.next;
		}
		return null;
	}

	/**
	 * Returns the logical size of the hash table
	 *
	 * @return the logical size of the hash table
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Removes a Person from the hash table and Returns the Person who was removed if applicable
	 *
	 * @param person the Person the be removed from the hash table
	 * @return the Person that was removed from the hash table
	 */
	@Override
	public PhoneNumber remove(Person person) {
		int hc = person.hashCode();
		Entry curr = this.table[hc];

		if(curr.person.equals(person)) {
			this.table[hc] = curr.next;
			return curr.phoneNumber;
		} else {
			Entry successor = curr.next;
			while(successor != null && ! successor.person.equals(person)) {
				curr = curr.next;
				successor = successor.next;
			}

			if(successor == null) {
				return null;
			} else {
				curr.next = successor.next;
				this.size--;
				return successor.phoneNumber;
			}
		}
	}

	/**
	 * Returns a String representation of the hash table by bucket
	 *
	 * @return a String representation of the hash table by bucket
	 */
	@Override
	public String toString() {
		String output = "";
		int bucket = 1;

		for(Entry e : this.table) {
			output += "Bucket " + bucket++ + ": ";
			List<Entry> bucketEntries = new ArrayList<>();

			while(e != null) {
				bucketEntries.add(e);
				e = e.next;
			}

			output += bucketEntries.toString().substring(1, bucketEntries.toString().length() - 1) + "\n";
		}

		return output;
	}


	/**
	 * This class represents Entry objects that are used in the hash table
	 *
	 * @author Ethan Maher
	 */
	class Entry {
		/**
		 * Stores the next Entry in the LinkedList
		 */
		public Entry next;

		/**
		 * Stores the Person for this Entry
		 */
		public Person person;

		/**
		 * Stores the PhoneNumber for this Entry
		 */
		public PhoneNumber phoneNumber;

		/**
		 * Instantiates the Entry object with the passed Person and PhoneNumber, next defaults to null
		 *
		 * @param person      the Person to be at this Entry
		 * @param phoneNumber the PhoneNumber to be at this Entry
		 */
		public Entry(Person person, PhoneNumber phoneNumber) {
			this.person = person;
			this.phoneNumber = phoneNumber;
			this.next = null;
		}

		/**
		 * Returns a String representation of this Entry
		 *
		 * @return a String representation of this Entry
		 */
		@Override
		public String toString() {
			return this.person + ":" + this.phoneNumber;
		}
	}
}
