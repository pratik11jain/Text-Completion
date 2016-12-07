import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by Anurag Jain-PC on 12/3/2016.
 */
public class Test
{
    public Trie trie = new Trie();
    public BinarySearchTree bst = new BinarySearchTree();
    public AVLTree avlt = new AVLTree();
    public TrieArray ta = new TrieArray();

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

    public void maintest(){
        Trie trie = new Trie();
        BinarySearchTree bst = new BinarySearchTree();
        AVLTree avlt = new AVLTree();
        TrieArray ta = new TrieArray();
        Test t = new Test();
        HashSet<String> allWords = t.readWords("F:\\Algo\\project\\dicitionary.txt");
        Scanner s = new Scanner(System.in);
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


    public static void main(String[] args) {

        Test t2 = new Test();
        while (true ){
            t2.menu();
        }

    }


    public void menu() {
        Scanner s = new Scanner(System.in);
        int ds, op;
        String word = null;


        System.out.println("Choose the data structure you wish to test:\n1> Standard Trie\n2> HashMap based Trie" +
                "\n3> BST\n4> AVL Tree\nEnter choice:");

        ds = s.nextInt();
        while(true) {
            System.out.println("Choose the operation you wish to perform:\n1> Insert\n2> Spell Check" +
                    "\n3> Text Completion\n4> Delete\n5> Exit to main menu\nEnter choice:");

            op = s.nextInt();
            if(op!=5){
                System.out.println("Please enter query word(spaces not allowed)::");
                word = s.next();
            }
            switch (ds) {
                case 1:
                    opTrieArray(op, word);
                    break;
                case 2:
                    opTrie(op, word);
                    break;
                case 3:
                    opBST(op, word);
                    break;
                case 4:
                    opAVT(op, word);
                    break;
                case 5:
                    break;
                default:
                    System.exit(1);
            }
        }
    }

    public void opTrie(int op, String word){
        switch(op)
        {
            case 1:
                if (trie.insert(word)){
                    System.out.println(word + " inserted to trie");
                }
                else {
                    System.out.println("Insert operation failed");
                }
                break;
            case 2:
                if (trie.ifExists(word)){
                    System.out.println(word + " exists");
                }
                else {
                    System.out.println(word + " not found");
                }
                break;
            case 3:
                ArrayList<String> result = trie.autoComplete(word);
                if (result.size() == 0){
                    System.out.println("No word found with prefix " + word);
                }
                else {
                    System.out.println(result);
                }
                break;
            case 4:
                if (trie.delete(word)){
                    System.out.println(word + " deleted from trie");
                }
                else {
                    System.out.println("Delete operation failed");
                }
                break;
            default:
                System.exit(1);
        }
    }

    public void opTrieArray(int op, String word){
        switch(op)
        {
            case 1:
                ta.addString(word);
                System.out.println(word + "added to trie");
                break;
            case 2:
                if (ta.isWord(word)){
                    System.out.println(word + " exists");
                }
                else {
                    System.out.println(word + " not found");
                }
                break;
            case 3:
                ArrayList<String> result = ta.autoComplete(word);
                if (result.size() == 0){
                    System.out.println("No word found with prefix " + word);
                }
                else {
                    System.out.println(result);
                }
                break;
            case 4:
                if (ta.delete(word)){
                    System.out.println(word + " deleted from trie");
                }
                else {
                    System.out.println("Delete operation failed");
                }
                break;
            default:
                System.exit(1);
        }
    }

    public void opBST(int op, String word){
        switch(op)
        {
            case 1:
                if (bst.insert(word)){
                    System.out.println(word + " inserted to trie");
                }
                else {
                    System.out.println("Insert operation failed");
                }
                break;
            case 2:
                if (bst.ifExists(word)){
                    System.out.println(word + " exists");
                }
                else {
                    System.out.println(word + " not found");
                }
                break;
            case 3:
                ArrayList<String> result = bst.autoComplete(word);
                if (result.size() == 0){
                    System.out.println("No word found with prefix " + word);
                }
                else {
                    System.out.println(result);
                }
                break;
            case 4:
                System.out.println(word + " deleted from trie");

                break;
            default:
                System.exit(1);
        }
    }


    public void opAVT(int op, String word){
        switch(op)
        {
            case 1:
                avlt.insert(avlt.root, word);
                break;
            case 2:
                if (avlt.ifExists(avlt.root, word)){
                    System.out.println(word + " exists");
                }
                else {
                    System.out.println(word + " not found");
                }
                break;
            case 3:
                ArrayList<String> result = avlt.autoComplete(avlt.root, word);
                if (result.size() == 0){
                    System.out.println("No word found with prefix " + word);
                }
                else {
                    System.out.println(result);
                }
                break;
            case 4:
                avlt.deleteNode(avlt.root, word);
                System.out.println(word + " deleted from trie");
                break;
            default:
                System.exit(1);
        }
    }
}
