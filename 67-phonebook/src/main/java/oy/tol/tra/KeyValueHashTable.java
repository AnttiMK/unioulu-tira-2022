package oy.tol.tra;

import oy.tol.tra.util.Algorithms;

public class KeyValueHashTable<K extends Comparable<K>, V> implements Dictionary<K, V> {

    private static final int DEFAULT_CAPACITY = 20;
    private static final double LOAD_FACTOR = 0.5;

    private Pair<K, V>[] array;
    private int capacity = 0;
    private int count = 0;

    private int reallocationCount = 0;
    private int collisionCount = 0;
    private int maxProbingCount = 0;
    private int pairsUpdated = 0;

    public KeyValueHashTable(int capacity) throws OutOfMemoryError {
        ensureCapacity(capacity);
    }

    public KeyValueHashTable() throws OutOfMemoryError {
        ensureCapacity(DEFAULT_CAPACITY);
    }

    @Override
    public Type getType() {
        return Type.HASHTABLE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void ensureCapacity(int size) throws OutOfMemoryError {
        array = (Pair<K, V>[]) new Pair[size];
        this.capacity = size;
    }

    @Override
    public int size() {
        return count;
    }

    /**
     * Prints out the statistics of the hash table.
     * Here you should print out member variable information which tell something
     * about your implementation.
     * <p>
     * For example, if you implement this using a hash table, update member
     * variables of the class (int counters) in add() whenever a collision
     * happen. Then print this counter value here.
     * You will then see if you have too many collisions. It will tell you that your
     * hash function is not good.
     */
    @Override
    public String getStatus() {
        return "Load factor: " + LOAD_FACTOR + "\n" +
                "Capacity: " + capacity + "\n" +
                "Count: " + count + "\n" +
                "Fill percentage: " + (double) count / capacity * 100 + " %\n" +
                "Reallocation count: " + reallocationCount + "\n" +
                "Collisions: " + collisionCount + "\n" +
                "Max probing count: " + maxProbingCount + "\n" +
                "Pairs updated: " + pairsUpdated + "\n";
    }

    @Override
    public boolean add(K key, V value) throws IllegalArgumentException, OutOfMemoryError {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null!");
        }

        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null!");
        }

        if (count >= capacity * LOAD_FACTOR) {
            reallocate((int) (capacity * (1.0 / LOAD_FACTOR)));
        }

        int index;
        int modifier = 0;
        int probes = 0;
        boolean added = false;

        do {
            index = getIndex(key, modifier);
            if (array[index] == null) {
                array[index] = new Pair<>(key, value);
                added = true;
                count++;
            } else if (!array[index].getKey().equals(key)) {
                modifier++;
                collisionCount++;
                probes++;
            } else {
                array[index].setvalue(value);
                added = true;
                pairsUpdated++;
            }
        } while (!added);

        maxProbingCount = Math.max(maxProbingCount, probes);
        return true;
    }

    private int getIndex(K key, int modifier) {
        return ((key.hashCode() + modifier) & 0x7FFFFFFF) % capacity;
    }

    @SuppressWarnings("unchecked")
    private void reallocate(int size) {
        Pair<K, V>[] oldArray = array;
        array = (Pair<K, V>[]) new Pair[size];
        this.count = 0;
        this.capacity = size;

        for (Pair<K, V> pair : oldArray) {
            if (pair != null) {
                add(pair.getKey(), pair.getValue());
            }
        }

        reallocationCount++;
    }

    @Override
    public V find(K key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null!");
        }

        int index;
        int modifier = 0;
        boolean found = false;
        V value = null;

        do {
            index = getIndex(key, modifier);
            if (array[index] != null) {
                if (array[index].getKey().equals(key)) {
                    found = true;
                    value = array[index].getValue();
                } else {
                    modifier++;
                }
            } else {
                found = true;
            }
        } while (!found);

        return value;
    }

    @Override
    @java.lang.SuppressWarnings({"unchecked"})
    public Pair<K,V> [] toSortedArray() {
        Pair<K, V>[] sortedArray = (Pair<K, V>[]) new Pair[count];

        int index = 0;
        for (int i = 0; i < capacity; i++) {
            if (array[i] != null) {
                sortedArray[index] = array[i];
                index++;
            }
        }

        Algorithms.fastSort(sortedArray);
        return sortedArray;
    }

    @Override
    public void compress() throws OutOfMemoryError {
        int newCapacity = (int) (count * (1.0 / LOAD_FACTOR));
        if (newCapacity < capacity) {
            reallocate(newCapacity);
        }
    }
 

}
