import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;

public class HuffmanCompressor {
	/**
	 * Used to store the Huffman Tree Decoder
	 */
	public HuffmanTree decoder;
	private final String PATH = "assets/";

	/**
	 * Compresses a file using a huffman tree and ascii encoding
	 *
	 * @param filename The name of the original file
	 * @throws IOException if File does not exist in path
	 */
	public void compress(String filename) throws IOException {
		Scanner sc = new Scanner(new File(PATH + filename));
		int[] asciiValues = new int[256];
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			if(!line.equals("")) {
				String[] parts = line.split("");
				for(String temp : parts) {
					asciiValues[temp.charAt(0)]++;
				}
			}
			asciiValues['\n']++;
		}
		sc.close();


		String outFile = PATH + filename.substring(0, filename.indexOf('.')) + ".code";
		decoder = new HuffmanTree(asciiValues);
		decoder.write(outFile);

		TreeMap<Character, String> charMap = new TreeMap<>();
		sc = new Scanner(new File(outFile));
		while(sc.hasNextLine()) {
			char curr = (char) Integer.parseInt(sc.nextLine());
			String path = sc.nextLine();
			charMap.put(curr, path);
		}

		BitOutputStream out = new BitOutputStream(PATH + filename.substring(0, filename.indexOf('.')) + ".short");
		sc = new Scanner(new File(PATH + filename));
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			if(!line.equals("")) {
				String[] parts = line.split("");
				for(String temp : parts) {
					String[] path = (charMap.get(temp.charAt(0))).split("");
					for(String bit : path) {
						out.writeBit(Integer.parseInt(bit));
					}
				}
			}
			String[] newLinePath = charMap.get('\n').split("");
			for(String bit : newLinePath) {
				out.writeBit(Integer.parseInt(bit));
			}
		}

		String[] path = charMap.get((char) 256).split("");
		for(String bit : path) {
			out.writeBit(Integer.parseInt(bit));
		}
		out.close();

	}

	/**
	 * output the expanded file into the fileName specified
	 *
	 * @param codeFile The name of the compressed file
	 * @param fileName The name of the file to output to
	 * @throws IOException if File does not exist in path
	 */
	public void expand(String codeFile, String fileName) throws IOException {
		decoder.decode(new BitInputStream(PATH + codeFile), PATH + fileName);
	}
}