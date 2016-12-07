import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Anurag Jain-PC on 11/27/2016.
 */
public class BinarySearchTree {
    private TreeNode head;

    // This method mainly calls deleteRec()
    // http://quiz.geeksforgeeks.org/binary-search-tree-set-2-delete/
    public void delete(String word)
    {
        head = deleteRec(head, word);
    }

    /* A recursive function to insert a new key in BST */
    TreeNode deleteRec(TreeNode root, String word)
    {
        /* Base Case: If the tree is empty */
        if (root == null)  return root;

        /* Otherwise, recur down the tree */
        if (root.getWord().compareTo(word) > 0)
            root.setLeft(deleteRec(root.getLeft(), word));
        else if (root.getWord().compareTo(word) < 0)
            root.setRight(deleteRec(root.getRight(), word));

            // if key is same as root's key, then This is the node
            // to be deleted
        else
        {
            // node with only one child or no child
            if (root.getLeft() == null)
                return root.getRight();
            else if (root.getRight() == null)
                return root.getLeft();

            // node with two children: Get the inorder successor (smallest
            // in the right subtree)
            root.setWord(minValue(root.getRight()));

            // Delete the inorder successor
            root.setRight(deleteRec(root.getRight(), root.getWord()));
        }

        return root;
    }

    String minValue(TreeNode root)
    {
        String minv = root.getWord();
        while (root.getLeft() != null)
        {
            minv = root.getLeft().getWord();
            root = root.getLeft();
        }
        return minv;
    }

    public boolean insert(String word){
        TreeNode temp = this.head;
        word = word.toLowerCase();
        if (temp == null){
            head = new TreeNode();
            head.setWord(word);
            head.setLeft(null);
            head.setRight(null);
            return true;
        }

        if (temp.getWord().equals(word)) {
            return true;
        }
        TreeNode previous;
        while(true){
            previous = temp;
            if (temp.getWord().compareTo(word) < 0) {
                temp = temp.getRight();
            }
            else if (temp.getWord().compareTo(word) > 0) {
                temp = temp.getLeft();
            }
            else {
                return true;
            }

            if (temp == null){
                break;
            }
        }

        TreeNode t = new TreeNode();
        t.setWord(word);
        t.setRight(null);
        t.setLeft(null);

        if (previous.getWord().compareTo(word) < 0) {
            previous.setRight(t);
        }
        else if (previous.getWord().compareTo(word) > 0) {
            previous.setLeft(t);
        }
        return true;
    }

    public boolean ifExists(String word){
        TreeNode temp = this.head;
        word = word.toLowerCase();
        if (temp == null){
            return false;
        }

        if (temp.getWord().equals(word)) {
            return true;
        }


        while(true){
            if (temp.getWord().compareTo(word) < 0) {
                temp = temp.getRight();
            }
            else if (temp.getWord().compareTo(word) > 0) {
                temp = temp.getLeft();
            }
            else {
                return true;
            }

            if (temp == null){
                return false;
            }
        }
    }

    public ArrayList<String> autoComplete(String word){
        ArrayList<String> list = new ArrayList<>();
        if (head == null) {
            return list;
        }
        word = word.toLowerCase();
        int count = 10;
        ArrayList<TreeNode> fringe = new ArrayList<>();
        fringe.add(head);
        while(!fringe.isEmpty() && count > 0) {
            TreeNode temp = fringe.remove(0);
            if (temp == null) {
                continue;
            }
            if (temp.getWord().startsWith(word)) {
                list.add(temp.getWord());
                count--;
            }

            if (temp.getWord().startsWith(word)) {
                fringe.add(temp.getRight());
                fringe.add(temp.getLeft());
            }
            else if (temp.getWord().compareTo(word) < 0) {

                fringe.add(temp.getRight());
            }
            else {
                fringe.add(temp.getLeft());

            }

            //fringe.add(temp.getLeft());
            //fringe.add(temp.getRight());
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


    public ArrayList<Long> testOperations(HashSet<String> insertWords, HashSet<String> searchWords, HashSet<String> deleteWords, HashSet<String> autoCompleteWords) {
        BinarySearchTree tree = new BinarySearchTree();
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
            tree.autoComplete(word);
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
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert("abc");
        bst.insert("bc");
        bst.insert("aac");

        bst.insert("a");
        bst.insert("aa");
        bst.insert("aaa");
        bst.insert("bc");


        System.out.println(bst.ifExists("abc"));
        System.out.println(bst.ifExists("aa"));
        System.out.println(bst.ifExists("z"));

        System.out.println(bst.autoComplete("aa"));
        bst.delete("abc");
        System.out.println(bst.ifExists("abc"));
        System.out.println(bst);
        bst.insert("abc");
        System.out.println(bst.ifExists("abc"));
        String filename = "F:\\Algo\\project\\dicitionary.txt";
        HashSet<String> words = bst.readWords(filename);
        long startTime = System.nanoTime();
        for (String word : words)
            bst.insert(word);
        long endTime = System.nanoTime();
        System.out.println("Initialization:" + (endTime-startTime));
        startTime = System.nanoTime();
        System.out.println(bst.autoComplete("allow"));

        endTime = System.nanoTime();
        bst.delete("allow");
        System.out.println(bst.autoComplete("allow"));
        System.out.println("AutoComplete:" + (endTime-startTime));
    }
}
