import java.util.ArrayList;
import java.util.List;

/**
 * This class is a generic version of the PhoneBook class
 *
 * @param <T> the Key
 * @param <E> the Value
 */
public class MyHashTable<T, E> implements IMapGeneric<T, E> {
	/**
	 * The logical size of the hash table
	 */
	public int size;
	/**
	 * The backing structure of the hash table that stores the linked list of Entry objects
	 */
	public Entry<T, E>[] table;

	/**
	 * Constructs a new HashTable of size 21
	 */
	public MyHashTable() {
		this.table = new Entry[21];
	}

	/**
	 * Constructs a new HashTable of passed size
	 *
	 * @param size The size of the table
	 */
	public MyHashTable(int size) {
		this.table = new Entry[size];
	}

	/**
	 * Adds a Key/Value pair to the hash table
	 *
	 * @param key the Key to be added
	 * @param val the Value to be added
	 * @return the Entry that was previously mapped to the key
	 */
	@Override
	public E put(T key, E val) throws IllegalArgumentException {
		int hc = key.hashCode();
		Entry<T, E> entry = new Entry<>(key, val);
		Entry<T, E> curr = this.table[hc];
		if(curr != null) {
			while(curr.next != null) {
				curr = curr.next;

				if(curr.key.equals(key)) {
					throw new IllegalArgumentException("Person Already Exists in Phone Book"); // already exists
				}
			}
			curr.next = entry;
			size++;
			return curr.val;
		} else {
			this.table[hc] = entry;
			size++;
			return null;
		}
	}

	/**
	 * Gets an entry from the hash table
	 *
	 * @param key the Key to retrieve
	 * @return the Value of the passed Key
	 */
	@Override
	public E get(T key) {
		int hc = key.hashCode();
		Entry<T, E> temp = this.table[hc];
		while(temp != null) {
			if(temp.key.equals(key)) {
				return temp.val;
			}
			temp = temp.next;
		}
		return null;
	}

	/**
	 * Removes an element from the hash table
	 *
	 * @param key the Key to be removed
	 * @return the value of the removed key
	 */
	@Override
	public E remove(T key) {
		int hc = key.hashCode();
		Entry<T, E> pepe = this.table[hc];
		if(pepe.key.equals(key)) {
			this.table[hc] = pepe.next;
			return pepe.val;
		} else {
			Entry<T, E> successor = pepe.next;
			while(successor != null && ! successor.key.equals(key)) {
				pepe = pepe.next;
				successor = successor.next;
			}

			if(successor == null) {
				return null;
			} else {
				pepe.next = successor.next;
				this.size--;
				return successor.val;
			}
		}
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
	 * Returns a String representation of the hash table grouped by buckets
	 *
	 * @return a String representation of the hash table grouped by buckets
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
	 * The Entry class is used for Key/Value pairs in the hash table
	 *
	 * @param <T> the Key
	 * @param <E> the Value
	 */
	class Entry<T, E> {
		/**
		 * Stores the next Entry in the LinkedList
		 */
		public Entry<T, E> next;

		/**
		 * Stores the key of this Entry
		 */
		public T key;

		/**
		 * Stores the value of this Entry
		 */
		public E val;

		/**
		 * Creates an Entry object with the passed values
		 *
		 * @param k The Key of the Entry
		 * @param v The Value of the Entry
		 */
		public Entry(T k, E v) {
			this.key = k;
			this.val = v;
			this.next = null;
		}

		/**
		 * Returns a String representation of this Entry
		 *
		 * @return a String representation of this Entry
		 */
		@Override
		public String toString() {
			return this.key.toString() + ": " + this.val.toString();
		}
	}
}