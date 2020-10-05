import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class WordChain {
    private static Set<String> DICTIONARY = new HashSet<>();

    public static void main(String[] args) {
        long a = System.currentTimeMillis();
        makeDictionary();
        makeChain();
        System.out.println(System.currentTimeMillis() - a);
    }

    private static Stack<String> chain(String start, String end) {
        Stack<String> first = new Stack<>();
        first.push(start);

        if (start.equals(end)) {
            return first;
        }
        Queue<Stack<String>> chains = new LinkedList<Stack<String>>();
        Set<String> used = new HashSet<>();
        chains.offer(first);

        if (!DICTIONARY.contains(start) || !DICTIONARY.contains(end) || start.length() != end.length())
            return null;

        while (!chains.isEmpty()) {
            Stack<String> s = chains.poll();
            char[] top = s.peek().toCharArray();
            for (int i = 0; i < start.length(); i++) {
                char orig = top[i];
                for (char c = 'a'; c <= 'z'; c++) {
                    top[i] = c;
                    String str = String.valueOf(top);

                    if (str.equals(end)) {
                        s.add(str);
                        return s;
                    }

                    if (DICTIONARY.contains(str) && !used.contains(str)) {
                        Stack<String> newRung = new Stack<>();
                        newRung.addAll(s);
                        newRung.add(str);
                        chains.offer(newRung);
                        used.add(str);
                    }
                }
                top[i] = orig;
            }
        }
        return null;
    }

    private static void makeChain() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");

                Stack<String> attempt = chain(words[0], words[1]);
                if (attempt != null) {
                    System.out.println("Found a ladder! >>> " + attempt);
                } else {
                    System.out.printf("No ladder between %s and %s%n", words[0], words[1]);
                }

            }
            br.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void makeDictionary() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("dictionary.txt"));
            String word;
            while ((word = br.readLine()) != null) {
                DICTIONARY.add(word.toLowerCase());
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}