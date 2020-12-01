import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class is used for finding all of the solutions to a BoggleBoard
 *
 * @author Ethan Maher
 */
public class BoggleSolver {

	/**
	 * Prefix tree for the dictionary
	 */
	private Trie<Integer> pre;

	/**
	 * Used to store the words that can be made
	 */
	private static HashSet<String> words;

	private final String PATH = "./data/";

	private class Node {
		private Object val;
		private int depth;
		private boolean hasChild;
		private Node[] next;

		Node(int R, int d) {
			next = new Node[R];
			depth = d;
			hasChild = false;
		}
	}

	/**
	 * Prefix tree used in optimizing for larger trees
	 *
	 * @param <Value> the type of the prefix
	 */
	private class Trie<Value> {
		private static final int R = 26;
		private Node root = new Node(R, 0);

		/**
		 * Returns whether the Trie contains a prefix
		 *
		 * @param key  the prefix to search the tree for
		 * @param node the root node to search from
		 * @return whether the Trie contains the key
		 */
		public boolean contains(String key, Node node) {
			return get(key, node) != null;
		}

		/**
		 * Searches the Trie for a prefix
		 *
		 * @param prefix the prefix
		 * @param node   the current node
		 * @return the Node of the prefix
		 */
		public Node searchPrefix(String prefix, Node node) {
			if(node == null) {
				return get(root, prefix, 0);
			} else {
				return get(node, prefix, node.depth);
			}
		}

		/**
		 * Returns a Value of the Node
		 *
		 * @param key  the prefix to get
		 * @param node the current Node
		 * @return the Value of the Node if it exists
		 */
		public Value get(String key, Node node) {
			Node x;
			if(node == null) {
				x = get(root, key, 0);
			} else {
				x = get(node, key, node.depth);
			}
			if(x == null) return null;
			return (Value) x.val;
		}

		/**
		 * Helper to get from Trie
		 *
		 * @param x   the current Node
		 * @param key the prefix
		 * @param d   the depth
		 * @return the Node of the prefix
		 */
		private Node get(Node x, String key, int d) {
			if(x == null) return null;
			if(d == key.length()) return x;
			char c = key.charAt(d);
			return get(x.next[c - 'A'], key, d + 1);
		}

		/**
		 * adds a prefix to the Trie
		 *
		 * @param key the prefix to add to the Trie
		 * @param val the value of the prefix
		 */
		public void put(String key, Value val) {
			if(root == null) {
				root = new Node(R, 0);
			}
			root.hasChild = true;
			root = put(root, key, val, 0);
		}

		/**
		 * Helper to add prefix to Trie
		 *
		 * @param x   the current Node
		 * @param key the prefix
		 * @param val the value of the prefix
		 * @param d   the current depth
		 * @return the Node added to the Trie
		 */
		private Node put(Node x, String key, Value val, int d) {
			if(d == key.length()) {
				x.val = val;
				return x;
			}
			char c = key.charAt(d);
			if(x.next[c - 'A'] == null) {
				x.next[c - 'A'] = new Node(R, d + 1);
			}
			x.hasChild = true;
			x.next[c - 'A'] = put(x.next[c - 'A'], key, val, d + 1);
			return x;
		}
	}

	// Initializes the data structure using the given array of strings as the dictionary.
	// (You can assume each word in the dictionary contains only the uppercase letters A - Z.)
	public BoggleSolver(String dictionaryName) {
		if(dictionaryName.contains(PATH)) {
			dictionaryName = dictionaryName.replace(PATH, "");
		}

		Scanner sc;
		try {
			sc = new Scanner(new File(PATH + dictionaryName));
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		List<String> arrList = new ArrayList<>();

		while(sc.hasNextLine()) {
			String s = sc.nextLine();
			if(!s.equals(""))
				arrList.add(s);
		}

		pre = new Trie<>();
		for(int i = 0; i < arrList.size(); i++) {
			pre.put(arrList.get(i), i);
		}
		words = new HashSet<>();
	}

	// Returns the set of all valid words in the given Boggle board, as an Iterable object
	public Iterable<String> getAllValidWords(BoggleBoard board) {
		words.clear();
		boolean[][] marked = new boolean[board.rows()][board.cols()];
		for(int i = 0; i < board.rows(); i++) {
			for(int j = 0; j < board.cols(); j++) {
				dfs(new StringBuilder(), null, i, j, marked, board);
			}
		}
		return words;
	}

	/**
	 * Depth first searches the board for words
	 *
	 * @param word   the current status of a word
	 * @param n      the Node of the word
	 * @param i      the row of the character in the board
	 * @param j      the col of the character in the board
	 * @param marked the current boolean[][] used for marking coords as visited
	 * @param board  the BoggleBoard
	 */
	private void dfs(StringBuilder word, Node n, int i, int j, boolean[][] marked, BoggleBoard board) {
		if(!marked[i][j]) {
			marked[i][j] = true;
			word.append(board.getLetter(i, j));
			if(board.getLetter(i, j) == 'Q') {
				word.append("U");
			}
			String str = word.toString();
			if(str.length() >= 3 && pre.contains(str, n) && !words.contains(str)) {
				words.add(str);
			}
			Node childNode = pre.searchPrefix(str, n);
			if(childNode != null && childNode.hasChild) {
				for(int k = i - 1; k <= i + 1; k++) {
					for(int l = j - 1; l <= j + 1; l++) {
						if(inBounds(k, l, board)) {
							if(k == i && l == j)
								continue;
							dfs(word, childNode, k, l, marked, board);
						}
					}
				}
			}

			if(word.toString().endsWith("QU")) {
				word.delete(word.length() - 2, word.length());
			} else {
				word.deleteCharAt(word.length() - 1);
			}
			System.out.println(i + " " + j);
			marked[i][j] = false;
		}
		return;
	}

	private boolean inBounds(int i, int j, BoggleBoard board) {
		return i >= 0 && i < board.rows() && j < board.cols() && j >= 0;
	}

	// Returns the score of the given word if it is in the dictionary, zero otherwise.
	// (You can assume the word contains only the uppercase letters A - Z.)
	public int scoreOf(String word) {
		if(words.contains(word)) {
			switch(word.length()) {
				case 0:
				case 1:
				case 2:
					return 0;
				case 3:
				case 4:
					return 1;
				case 5:
					return 2;
				case 6:
					return 3;
				case 7:
					return 5;
				default:
					return 11;
			}
		}
		return 0;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("What dictionary would you like to use? ");
		BoggleSolver bs = new BoggleSolver(sc.nextLine());
		System.out.print("What board would you like to use? ");
		BoggleBoard bb = new BoggleBoard(sc.nextLine());
		sc.close();
		int score = 0;
		Iterator<String> it = bs.getAllValidWords(bb).iterator();
		while(it.hasNext()) {
			String s = it.next();
			System.out.println(s);
			score += bs.scoreOf(s);

		}
		System.out.println("Score: " + score);
	}
}