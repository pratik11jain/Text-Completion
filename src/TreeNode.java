/**
 * Created by Anurag Jain-PC on 11/27/2016.
 */
class TreeNode {
    private String word;
    private TreeNode left;
    private TreeNode right;
    private int height;

    TreeNode() {
    }

    TreeNode(String word) {
        this.word = word;
        left = null;
        right = null;
        height = 0;
    }

    int getHeight() {
        return height;
    }

    void setHeight(int height) {
        this.height = height;
    }

    String getWord() {
        return word;
    }

    void setWord(String word) {
        this.word = word;
    }

    TreeNode getLeft() {
        return left;
    }

    void setLeft(TreeNode left) {
        this.left = left;
    }

    TreeNode getRight() {
        return right;
    }

    void setRight(TreeNode right) {
        this.right = right;
    }
}
