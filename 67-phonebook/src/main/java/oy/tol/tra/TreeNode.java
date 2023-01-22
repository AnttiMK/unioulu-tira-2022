package oy.tol.tra;

import oy.tol.tra.util.linkedlist.LinkedListImplementation;
import oy.tol.tra.util.linkedlist.LinkedListInterface;

import java.util.concurrent.atomic.AtomicInteger;

public class TreeNode<K extends Comparable<K>, V> {

    public static int addDepth = 0;
    public static int collisionChainLength = 0;

    private final Pair<K, V> kvPair;
    private final int hash;
    private TreeNode<K, V> leftChild;
    private TreeNode<K, V> rightChild;
    private LinkedListInterface<Pair<K, V>> collisionChain;

    public TreeNode(K key, V value) {
        this.kvPair = new Pair<>(key, value);
        this.hash = key.hashCode();
        this.leftChild = null;
        this.rightChild = null;
        this.collisionChain = null;
    }

    public int insert(K key, V value, int toSearch) {
        int added = 0;
        if (toSearch < this.hash) {
            if (leftChild == null) {
                leftChild = new TreeNode<>(key, value);
                addDepth++;
                added = 1;
            } else {
                added = leftChild.insert(key, value, key.hashCode());
                addDepth++;
            }
        } else if (toSearch > this.hash) {
            if (rightChild == null) {
                rightChild = new TreeNode<>(key, value);
                addDepth++;
                added = 1;
            } else {
                added = rightChild.insert(key, value, key.hashCode());
                addDepth++;
            }
        } else {
            if (kvPair.getKey().equals(key)) {
                kvPair.setvalue(value);
            } else {
                if (collisionChain == null) {
                    collisionChain = new LinkedListImplementation<>();
                    collisionChain.add(new Pair<>(key, value));
                    added = 1;
                    collisionChainLength = 1;
                } else {
                    Pair<K, V> pair = new Pair<>(key, value);
                    int index = collisionChain.indexOf(pair);
                    if (index < 0) {
                        collisionChain.add(pair);
                        added = 1;
                    } else {
                        collisionChain.remove(index);
                        collisionChain.add(pair);
                    }
                    collisionChainLength = collisionChain.size();
                }
            }
        }
        return added;
    }

    public V find(K key, int toSearch) {
        if (toSearch < this.hash) {
            if (leftChild != null) {
                return leftChild.find(key, toSearch);
            }
        } else if (toSearch > this.hash) {
            if (rightChild != null) {
                return rightChild.find(key, toSearch);
            }
        } else {
            if (kvPair.getKey().equals(key)) {
                return kvPair.getValue();
            } else {
                if (collisionChain != null) {
                    Pair<K, V> toFind = new Pair<>(key, null);
                    int index = collisionChain.indexOf(toFind);
                    if (index >= 0) {
                        return collisionChain.get(index).getValue();
                    }
                }
            }
        }

        return null;
    }

    public void toSortedArray(Pair<K, V>[] result, AtomicInteger toAddIndex) {
        if (leftChild != null) {
            leftChild.toSortedArray(result, toAddIndex);
        }
        result[toAddIndex.incrementAndGet()] = new Pair<>(kvPair.getKey(), kvPair.getValue());
        if (rightChild != null) {
            rightChild.toSortedArray(result, toAddIndex);
        }
    }

}
