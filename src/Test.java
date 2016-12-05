import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Anurag Jain-PC on 12/3/2016.
 */
public class Test
{

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
        System.out.println(i);
        return words;
    }

    public HashSet<String> createList(HashSet<String> orignalList, int number) {
        HashSet<String> words = new HashSet<String>();
        double prob = number*1.0/orignalList.size()*1.0;
        for(String word: orignalList) {
           double x = Math.random();
           if (x < prob) {
               words.add(word);
               if(number <= words.size()){
                   break;
               }
           }
        }
        return  words;
    }

    public long[] addArray(long[] arr, ArrayList<Long> arr2) {

        for (int k = 0; k < 4; k++) {
            arr[k] += arr2.get(k);
        }
        return arr;
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        BinarySearchTree bst = new BinarySearchTree();
        AVLTree avlt = new AVLTree();
        TrieArray ta = new TrieArray();
        Test t = new Test();
        HashSet<String> allWords = t.readWords("F:\\Algo\\project\\dicitionary.txt");




        long[] rBst ={0, 0, 0, 0}, rTa={0, 0, 0, 0}, rAvl={0, 0, 0, 0}, rTh = {0, 0, 0, 0};
        long[] dataSize = {0,0,0,0};
        for(int i = 1; i <= 4; i++)
        {
            int arr[] = {300000, 10, 10, 10};
            for(int j = 0; j<4; j++) {
                dataSize[j] += arr[j];
            }


            for(int j = 1; j < 10; j++)
            {
                HashSet<String> words = words = t.createList(allWords, arr[0]);
                HashSet<String> searchWords = t.createList(words, arr[1]);
                HashSet<String> autoWordsComplete = t.createList(words, arr[2]);
                HashSet<String> deletedWords = t.createList(words, arr[3]);
                rBst = t.addArray(rBst, bst.testOperations(words, searchWords, autoWordsComplete, deletedWords));
                rAvl = t.addArray(rAvl, avlt.testOperations(words, searchWords, autoWordsComplete, deletedWords));
                rTa = t.addArray(rTa, ta.testOperations(words, searchWords, autoWordsComplete, deletedWords));
                rTh = t.addArray(rTh, trie.testOperations(words, searchWords, autoWordsComplete, deletedWords));
            }


            System.out.println("\n\n\n");
            System.out.println(arr[0] + ", " + arr[1] + ", " + arr[2] + ", " + arr[3]);

            System.out.println("BST," + rBst[0] + ", " + rBst[1] + ", " + rBst[2] + ", " + rBst[3]);
            System.out.println("AVL," + rAvl[0] + ", " + rAvl[1] + ", " + rAvl[2] + ", " + rAvl[3]);
            System.out.println("TA," + rTa[0] + ", " + rTa[1] + ", " + rTa[2] + ", " + rTa[3]);
            System.out.println("TH," + rTh[0] + ", " + rTh[1] + ", " + rTh[2] + ", " + rTh[3]);
        }

        System.out.println("TH," + dataSize[0] + ", " + dataSize[1] + ", " + dataSize[2] + ", " + dataSize[3]);

    }

}

//
        /*ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);
            if (!temp.getPointers().keySet().contains(character))
                return list;
            temp = temp.getPointers().get(character);
            if (i == word.length() - 1 && temp.isWord()) {
                list.add(word);
                count--;
            }
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
        }*/