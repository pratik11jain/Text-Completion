/**
 * Created by Pratik on 12/04/2016.
 */
public class TrieNodeArray {
    private TrieNodeArray[]  children;
    private boolean isWord;

    public TrieNodeArray[] getChildren() {
        return children;
    }

    public void setChildren(TrieNodeArray[] children) {
        this.children = children;
    }

    public boolean isWord() {
        return isWord;
    }

    public void setWord(boolean word) {
        isWord = word;
    }

    public TrieNodeArray()
    {
        children = new TrieNodeArray[26];
        isWord = false;
    }
}
