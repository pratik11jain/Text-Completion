import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.jar.JarFile;

//https://users.cs.duke.edu/~ola/courses/cps108/fall96/joggle/TrieCopy/TrieCopy.java
public class TrieArray
{
    TrieNodeArray head = new TrieNodeArray();

    /**
     * Add a string to the TrieArray
     *
     * @param s The string added to TrieArray
     */

    public void addString(String s)
    {
        TrieNodeArray t = this.head;
        int k;
        int limit = s.length();
        for(k=0; k < limit; k++)
        {
            int index = s.charAt(k) - 'a';
            if (t.getChildren()[index] == null)
            {
                t.getChildren()[index] = new TrieNodeArray();
            }
            t = t.getChildren()[index];
        }
        t.setWord(true);
    }

    public void addCString(char s[])
    {
        TrieNodeArray t = this.head;
        int k=0;
        while (s[k] != '\0')
        {
            int index = s[k] - 'a';
            if (t.getChildren()[index] == null)
            {
                t.getChildren()[index] = new TrieNodeArray();
            }
            t = t.getChildren()[index];
            k++;
        }
        t.setWord(true);
    }
    public boolean isWord(String s)
    {
        TrieNodeArray t = this.head;
        int k;
        int limit = s.length();
        for(k=0; k < limit; k++)
        {
            int index = s.charAt(k) - 'a';
            if (t.getChildren()[index] == null) return false;
            t = t.getChildren()[index];
        }
        return t.isWord();
    }

    public boolean delete(String word) {
        TrieNodeArray temp = this.head;
        ArrayList<TrieNodeArray> headsInPath = new ArrayList<>();
        word = word.toLowerCase();
        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);
            int index = word.charAt(i) - 'a';
            if (temp.getChildren()[index] == null)
                return false;
            TrieNodeArray t = temp;
            headsInPath.add(t);
            temp = temp.getChildren()[index];

            if (i == word.length() - 1 && temp.isWord())
            {
                temp.setWord(false);
            }
            else if (i == word.length() - 1) {
                return false;
            }
        }
        return false;
    }
    public ArrayList<String> autoComplete(String word) {
        int count = 10;
        TrieNodeArray temp = this.head;
        word = word.toLowerCase();
        ArrayList<String> list = new ArrayList<String>();
        int i = 0;
        for (; i < word.length(); i++) {
            char character = word.charAt(i);
            int index = word.charAt(i) - 'a';
            if (temp.getChildren()[index] == null)
                return list;
            temp = temp.getChildren()[index];

        }

        if (temp.isWord()) {
            list.add(word);
            count--;
        }

        ArrayList<String> keyFringe = new ArrayList<>();
        ArrayList<TrieNodeArray> valueFringe = new ArrayList<>();

        for(i = 0; i < 26; i++){
            char c = (char)(i + 'a');
            if(temp.getChildren()[i] != null){
                keyFringe.add(""+c);
                valueFringe.add(temp.getChildren()[i]);
            }
        }

        for(i=0; i<keyFringe.size(); i++) {
            String c = keyFringe.get(i);
            if (valueFringe.get(i) != null && valueFringe.get(i).isWord()) {
                list.add(word + c);
                count--;
            }
            for(int j = 0; j < 26; j++) {
                char c1 = (char)(j + 'a');
                if(valueFringe.get(i).getChildren()[j] != null){
                    keyFringe.add("" + c + c1);
                    valueFringe.add(valueFringe.get(i).getChildren()[j]);
                }
            }

            if (count == 0)
                break;
        }
        return list;
    }

    public ArrayList<Long> testOperations(HashSet<String> insertWords, HashSet<String> searchWords, HashSet<String> autoCompleteWords, HashSet<String> deleteWords) {
        TrieArray tree = new TrieArray();
        ArrayList<Long> results = new ArrayList<Long>();

        // Insertion
        long startTime = System.nanoTime();
        for (String word : insertWords)
        {
            tree.addString(word);
        }
        long endTime = System.nanoTime();
        results.add(endTime-startTime);

        // If Exists
        startTime = System.nanoTime();
        for (String word : searchWords)
        {
            tree.isWord(word);
        }
        endTime = System.nanoTime();
        results.add(endTime-startTime);

        // AutoComplete
        startTime = System.nanoTime();
        for (String word : autoCompleteWords)
        {
            tree.autoComplete(word);
        }
        endTime = System.nanoTime();
        results.add(endTime-startTime);

        // Delete
        startTime = System.nanoTime();
        for (String word : deleteWords)
        {
            //tree.delete(word);
        }
        endTime = System.nanoTime();
        results.add(endTime-startTime);

        return results;
    }

    public static void main(String[] args) {
        TrieArray t = new TrieArray();
        HashSet<String> words = new HashSet<String>();
        int i = 0;
        try(BufferedReader br = new BufferedReader(new FileReader("F:\\Algo\\project\\dicitionary.txt"))) {
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
        for(String word: words)
            t.addString(word);


        System.out.println(t.isWord("allow"));
        System.out.println(t.autoComplete("allow"));
    }
}
