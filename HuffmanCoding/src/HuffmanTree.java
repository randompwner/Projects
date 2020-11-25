import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.Scanner;

public class HuffmanTree {

	/**
	 * Stores the root Node of this HuffmanTree
	 */
	public Node root;

	/**
	 * Constructs a HuffmanTree with the passed counts of
	 * characters and the number of occurrences that there are
	 *
	 * @param count array that holds the occurrences of each ascii value
	 */
	public HuffmanTree(int[] count) {
		PriorityQueue<Node> nodes = new PriorityQueue<>();
		for(int i = 0; i < count.length; i++) {
			int numCharacters = count[i];
			if(numCharacters > 0) {
				nodes.add(new Node((char) i, numCharacters));
			}
		}
		nodes.add(new Node((char) 256, 1));
		while(nodes.size() != 1) {
			Node newNode = new Node(null, 0);
			newNode.setLeftChild(nodes.poll());
			newNode.setRightChild(nodes.poll());
			nodes.add(newNode);
		}
		this.root = nodes.poll();
	}

	/**
	 * Constructor that will reconstruct the tree from a
	 * file. This is used for decoding an encoded message.
	 *
	 * @param codeFile The name of the file we are reading in from
	 * @throws IOException if File does not exist in path
	 */
	public HuffmanTree(String codeFile) throws IOException {
		Scanner scan = new Scanner(new File(codeFile));
		this.buildTree(scan);
	}

	/**
	 * Reads from the scanner and instantiates the tree
	 * using the path and ascii values from the input.
	 *
	 * @param scan The scanner where we are reading in input from
	 */
	private void buildTree(Scanner scan) {
		this.root = new Node(null, 0);
		while(scan.hasNextLine()) {
			int asciiValue = Integer.parseInt(scan.nextLine());
			String[] path = scan.nextLine().split("");
			Node current = this.root;
			for(String choice : path) {
				if(choice.equals("0") && current.left == null) {
					current.setLeftChild(new Node(null, 0));
				} else if(current.right == null) {
					current.setRightChild(new Node(null, 0));
				}
				current = choice.equals("0") ? current.left : current.right;
			}
			current.setCurrent((char) asciiValue, 0);
		}
	}

	/**
	 * Writes the Huffman encoding tree in a file using
	 * a standard format to the specified filename
	 *
	 * @param filename The name of the file to output the
	 *                 decoder of the current HuffmanTree
	 * @throws FileNotFoundException if File does not exist in path
	 */
	public void write(String filename) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File(filename));
		this.walk(pw, this.root, "");
		pw.close();
	}

	/**
	 * Walks through this HuffmanTree and prints out its values
	 *
	 * @param pw   The PrintWriter used to output to a file
	 * @param curr The current node being searched
	 * @param path The current path represented as a string of 1's and 0's
	 */
	private void walk(PrintWriter pw, Node curr, String path) {
		if(curr != null) {
			this.walk(pw, curr.left, path + "0");
			if(curr.isLeaf()) {
				pw.println((int) curr.character);
				pw.println(path);
			}
			this.walk(pw, curr.right, path + "1");
		}
	}

	/**
	 * Takes the input stream and outputs the decoded message into
	 * the passed filename of outFile
	 *
	 * @param in      The input stream of the decoded message
	 * @param outFile The name of the file to output to
	 * @throws FileNotFoundException if File does not exist in path
	 */
	public void decode(BitInputStream in, String outFile) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File(outFile));
		int currBit = in.readBit();
		Node current = this.root;
		while(currBit != - 1) {
			current = currBit == 0 ? current.left : current.right;
			if(current.isLeaf()) {
				if(current.character < 256) {
					pw.print(current.character);
					current = this.root;
				} else {
					pw.close();
					return;
				}

			}
			currBit = in.readBit();
		}
	}
}

class Node implements Comparable<Node> {
	/**
	 * Stores the Character of this Node
	 */
	public Character character;

	/**
	 * Stores the weight of this Huffman Tree
	 */
	public Integer weight;

	/**
	 * Store the left and right children of this Node
	 */
	public Node left, right;

	/**
	 * Constructs a Node object with the passed character and weight
	 *
	 * @param character The character of this node
	 * @param weight    The weight of this node
	 */
	public Node(Character character, Integer weight) {
		this.character = character;
		this.weight = weight;
		this.left = null;
		this.right = null;
	}

	/**
	 * Update the current node to have a new character and weight
	 * of that from the passed variables.
	 *
	 * @param character The character to change this node to be equal to
	 * @param weight    The weight to change this node to be equal to
	 */
	public void setCurrent(Character character, Integer weight) {
		this.character = character;
		this.weight = weight;
		this.left = null;
		this.right = null;
	}

	/**
	 * Update the right node of this node to be the passed
	 * value of right. Update the weight and change the type.
	 *
	 * @param n The node that is being added to the right
	 *          of the current node
	 */
	public void setRightChild(Node n) {
		this.weight += n.weight;
		this.right = n;
	}

	/**
	 * Update the left node of this node to be the passed
	 * value of left. Update the weight and change the type.
	 *
	 * @param n The node that is being added to the left
	 *          of the current node
	 */
	public void setLeftChild(Node n) {
		this.weight += n.weight;
		this.left = n;
	}

	/**
	 * Determines if the current node is a leaf
	 *
	 * @return If the current node is a leaf
	 */
	public boolean isLeaf() {
		return this.left == null && this.right == null;
	}

	@Override
	public int compareTo(Node other) {
		return this.weight.compareTo(other.weight);
	}

	@Override
	public String toString() {
		return "" + (this.isLeaf() ? (char) this.character : this.weight);
	}
}