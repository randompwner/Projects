/**
 * The PhoneNumber class represents phone numbers.
 * In this case, numbers to be input should be delimited by a '-'.
 *
 * @author Ethan Maher
 */
public class PhoneNumber implements Comparable<PhoneNumber> {
	/**
	 * Stores the String representation of the PhoneNumber
	 */
	private String number;

	/**
	 * Stores each section of the PhoneNumber delimited by "-"
	 */
	private int[] sections;

	/**
	 * Stores the delimiter tp be used in the phone number
	 */
	private String delimiter = "-";

	/**
	 * Creates a PhoneNumber object from a given String delimited by "-"
	 *
	 * @param number the String to turn into a PhoneNumber
	 */
	public PhoneNumber(String number) {
		this.number = number;
		String[] parts = number.split(delimiter);
		this.sections = new int[parts.length];
		for(int i = 0; i < parts.length; i++) {
			if(! parts[i].equals(""))
				this.sections[i] = Integer.parseInt(parts[i]);
			else
				this.sections[i] = 0;
		}
	}

	/**
	 * Hashes the PhoneNumber by right shifting all of the sections by 3 and adding them together
	 *
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		return ((sections[0] >> 3) + (sections[1] >> 3) + (sections[2] >> 3));
	}

	/**
	 * Compares this PhoneNumber to the specified {@code obj}
	 *
	 * @param obj the PhoneNumber to compare
	 * @return whether the PhoneNumbers are equal
	 */
	@Override
	public boolean equals(Object obj) {
		PhoneNumber pn = (PhoneNumber) obj;
		return this.number.equals(pn.number);
	}

	/**
	 * Compares two PhoneNumbers by the differences of each section
	 *
	 * @param p the PhoneNumber to compare to this
	 * @return the difference if the sections are not equal to each other, 0 otherwise
	 */
	@Override
	public int compareTo(PhoneNumber p) {
		if(this.sections[0] != p.sections[0]) {
			return this.sections[0] - p.sections[0];
		} else if(this.sections[1] != p.sections[1]) {
			return this.sections[1] - p.sections[1];
		} else if(this.sections[2] != p.sections[2]) {
			return this.sections[2] - p.sections[2];
		} else {
			return 0;
		}
	}

	/**
	 * Returns the String representation of the PhoneNumber
	 *
	 * @return the String representation of the PhoneNumber
	 */
	@Override
	public String toString() {
		return this.number;
	}
}
