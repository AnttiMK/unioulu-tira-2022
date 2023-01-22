package oy.tol.tra;

import oy.tol.tra.util.Algorithms;

import java.util.concurrent.atomic.AtomicInteger;

public class KeyValueBSearchTree<K extends Comparable<K>, V> implements Dictionary<K, V> {

    private TreeNode<K, V> root;
    private int count;
    private int maxDepth;
    private int maxCollisionChainLength;

    public KeyValueBSearchTree() {
        this.root = null;
        this.count = 0;
        this.maxDepth = 0;
        this.maxCollisionChainLength = 0;
    }

    @Override
    public Type getType() {
        return Type.BST;
    }

    @Override
    public int size() {
        return this.count;
    }

    /**
     * Prints out the statistics of the tree structure usage.
     * Here you should print out member variable information which tell something about
     * your implementation.
     * <p>
     * For example, if you implement this using a hash table, update member variables of the class
     * (int counters) in add(K) whenever a collision happen. Then print this counter value here.
     * You will then see if you have too many collisions. It will tell you that your hash function
     * is good or bad (too much collisions against data size).
     */
    @Override
    public String getStatus() {
        return "Count of tree nodes: " + this.count + "\n" +
                "Max depth of tree: " + this.maxDepth + "\n" +
                "Max collision chain length: " + this.maxCollisionChainLength + "\n";
    }

    @Override
    public boolean add(K key, V value) throws IllegalArgumentException, OutOfMemoryError {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null!");
        }

        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null!");
        }

        if (root == null) {
            root = new TreeNode<>(key, value);
            count++;
            maxDepth = 1;
            maxCollisionChainLength = 0;
            return true;
        } else {
            TreeNode.addDepth = 1;
            int added = root.insert(key, value, key.hashCode());
            if (added > 0) {
                count++;
                maxDepth = Math.max(maxDepth, TreeNode.addDepth);
                maxCollisionChainLength = Math.max(maxCollisionChainLength, TreeNode.collisionChainLength);
                return true;
            }
        }
        return false;
    }

    @Override
    public V find(K key) throws IllegalArgumentException {
        if (root == null) {
            return null;
        } else {
            return root.find(key, key.hashCode());
        }
    }

    @Override
    public void ensureCapacity(int size) throws OutOfMemoryError {
        // Not implemented
    }

    @SuppressWarnings("unchecked")
    @Override
    public Pair<K, V>[] toSortedArray() {
        if (root == null) {
            return null;
        }

        Pair<K, V>[] result = new Pair[count];
        final AtomicInteger toAddIndex = new AtomicInteger(-1);
        root.toSortedArray(result, toAddIndex);
        Algorithms.fastSort(result);
        return result;
    }

    @Override
    public void compress() throws OutOfMemoryError {
        // Not implemented
    }

}
