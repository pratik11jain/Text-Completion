import java.io.*;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.UnmodifiableClassException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;

public class Trie {
    private TrieNode head = new TrieNode();

    public boolean insert(String word) {
        TrieNode temp = this.head;
        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);
            if (!temp.getPointers().keySet().contains(character))
                temp.addPointer(character);
            temp = temp.getPointers().get(character);
            if (i == word.length() - 1)
                temp.setWord(true);
        }
        return false;
    }

    public TrieNode getHead() {
        return head;
    }

    public void setHead(TrieNode head) {
        this.head = head;
    }

    public boolean createTrie() {
        return false;
    }

    public boolean delete(String word) {
        TrieNode temp = this.head;
        ArrayList<TrieNode> headsInPath = new ArrayList<TrieNode>();
        word = word.toLowerCase();
        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);
            if (!temp.getPointers().keySet().contains(character))
                return false;
            TrieNode t = temp;
            headsInPath.add(t);
            temp = temp.getPointers().get(character);

            if (i == word.length() - 1 && temp.isWord())
            {
                if (temp.getPointers().isEmpty()) {
                    temp.setWord(false);
                    //deleteHead(headsInPath, word);
                }
                else {
                    temp.setWord(false);

                }
            }
            else if (i == word.length() - 1) {

                return false;
            }
        }
        return false;
    }

    public void deleteHead(ArrayList<TrieNode> headInPath, String word) {
        int size = headInPath.size();
        TrieNode node = headInPath.remove(size-1);
        while(true) {
            size = headInPath.size();
            node = headInPath.remove(size-1);
            char c = word.charAt(word.length() - 1);
            word = word.substring(0, word.length() - 1);
            System.out.println("Out:" + c);
            if(node.getPointers().get(c).isWord()) {
                break;
            }
            node.getPointers().remove(c);
            if (node.getPointers().isEmpty() && headInPath.size() > 0) {
                continue;
            } else {
                break;
            }
        }

    }


    public boolean ifExists(String word) {
        TrieNode temp = this.head;
        word = word.toLowerCase();
        int i = 0;
        for (; i < word.length(); i++) {
            char character = word.charAt(i);
            if (!temp.getPointers().keySet().contains(character))
                return false;
            temp = temp.getPointers().get(character);

        }
        if (temp.isWord())
            return true;
        return false;
    }

    public ArrayList<String> autoComplete(String word) {
        int count = 20;
        TrieNode temp = this.head;
        word = word.toLowerCase();
        // new code
        ArrayList<String> list = new ArrayList<String>();
        int i = 0;
        for (i = 0; i < word.length(); i++) {
            char character = word.charAt(i);
            if (!temp.getPointers().keySet().contains(character))
                return list;
            temp = temp.getPointers().get(character);

            }

        if (temp.isWord())
        {
            list.add(word);
            count--;
        }

        ArrayList<String> keyFringe = new ArrayList<>();
        ArrayList<TrieNode> valueFringe = new ArrayList<>();
        HashMap<String, TrieNode> fringe = new HashMap<>();
        for(Character c: temp.getPointers().keySet()){
            //fringe.put("" + c,temp.getPointers().get(c));
            keyFringe.add(""+c);
            valueFringe.add(temp.getPointers().get(c));
        }

        for(i=0; i<keyFringe.size(); i++) {
            String c = keyFringe.get(i);
            if (valueFringe.get(i).isWord()) {
                list.add(word + c);
                count--;
            }
            list = goIn(valueFringe.get(i), list, word, count);

            if (count == 0)
                break;
        }
        //
        return list;
    }

    public ArrayList<String> autoComplete2(String word){
        int count = 10;
        TrieNode temp = this.head;
        word = word.toLowerCase();
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);
            if (!temp.getPointers().keySet().contains(character))
                return list;
            temp = temp.getPointers().get(character);
        }

        if (temp.isWord()) {
            list.add(word);
            count--;
        }

        ArrayList<String> keyFringe = new ArrayList<>();
        ArrayList<TrieNode> valueFringe = new ArrayList<>();

        for(Character c: temp.getPointers().keySet()){
            keyFringe.add(""+c);
            valueFringe.add(temp.getPointers().get(c));
        }

        for(int i=0; i<keyFringe.size(); i++) {
            String c = keyFringe.get(i);
            if (valueFringe.get(i).isWord()) {
                list.add(word + c);
                count--;
            }
            for (Character c1 : valueFringe.get(i).getPointers().keySet()) {
                keyFringe.add("" + c + c1);
                valueFringe.add(valueFringe.get(i).getPointers().get(c1));
            }

            if (count == 0)
                break;
        }
        return list;
    }

    ArrayList<String> goIn(TrieNode t, ArrayList<String> list, String word, int count){
        HashMap<Character, TrieNode> ptrs = t.getPointers();
        for(Character a: ptrs.keySet()) {
            if (count == 0)
                break;
            if (ptrs.get(a).isWord()){
                list.add(word + a);
                count--;
            }
            else
                list = goIn(ptrs.get(a), list, word+a, count);
        }
        return list;
    }

    public HashSet<String> readWords(String fileName) {
        HashSet<String> words = new HashSet<String>();
        int i = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            for(String line; (line = br.readLine()) != null; ) {
                if (line.matches("[a-zA-Z]+")) {
                    words.add(line);
                    System.out.println(i++);
                }
            }
            // line is not visible here.
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    public HashSet<String> readFileAddToMainFile(String fileName, String writeToFile) {
        Scanner s = null;
        HashSet<String> words = new HashSet<String>();
        try {
            s = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (s.hasNext()) {
            words.add(s.next().replaceAll("[^A-Za-z]", "").toLowerCase());
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(writeToFile, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (String word : words) {
            writer.println(word);
        }
        writer.close();

        return words;
    }

    public Trie initializeMyProgram(String filename) {
        Trie dict = new Trie();
        HashSet<String> words = dict.readWords(filename);
        long startTime = System.nanoTime();
        for (String word : words)
            dict.insert(word);
        long endTime = System.nanoTime();
        System.out.println("Initialization:" + (endTime-startTime));
        return dict;
    }


    public ArrayList<Long> testOperations(HashSet<String> insertWords, HashSet<String> searchWords, HashSet<String> autoCompleteWords, HashSet<String> deleteWords) {
        Trie tree = new Trie();
        ArrayList<Long> results = new ArrayList<Long>();

        // Insertion
        long startTime = System.nanoTime();
        for (String word : insertWords)
        {
            tree.insert(word);
        }
        long endTime = System.nanoTime();
        results.add(endTime-startTime);

        // If Exists
        startTime = System.nanoTime();
        for (String word : searchWords)
        {
            tree.ifExists(word);
        }
        endTime = System.nanoTime();
        results.add(endTime-startTime);

        // AutoComplete
        startTime = System.nanoTime();
        for (String word : autoCompleteWords)
        {
            tree.autoComplete2(word);
        }
        endTime = System.nanoTime();
        results.add(endTime-startTime);

        // Delete
        startTime = System.nanoTime();
        for (String word : deleteWords)
        {
            tree.delete(word);
        }
        endTime = System.nanoTime();
        results.add(endTime-startTime);

        return results;
    }



    public static void main(String[] args) {
        // https://raw.githubusercontent.com/dwyl/english-words/master/words.txt
        long startTime = System.nanoTime();
        String filename = "F:\\Algo\\project\\dicitionary.txt";
        long endTime;
        Trie trie = new Trie();
        trie = trie.initializeMyProgram(filename);


        System.out.println();
        System.out.println();

        System.out.println(trie.autoComplete("allow"));
        System.out.println(trie.ifExists("allow"));


        startTime = System.nanoTime();
        System.out.println(trie.autoComplete2("allow"));
        endTime = System.nanoTime();
        System.out.println("AutoComplete:" + (endTime-startTime));
    }
}