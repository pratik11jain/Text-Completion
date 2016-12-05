import java.util.HashMap;

/**
 * Created by Pratik on 11/16/2016.
 */
class TrieNode {
    private boolean isWord;
    private HashMap <Character, TrieNode> pointers = new HashMap<>();

    boolean isWord() {
        return isWord;
    }

    HashMap<Character, TrieNode> getPointers() {
        return pointers;
    }

    void setWord(boolean word) {
        this.isWord = word;
    }

    void addPointer(Character c) {
        this.pointers.put(c, new TrieNode());
    }

}
