package oy.tol.tra;

/**
 * A generic interface to queue class. Queues work following
 * the first-in-first-out principle.
 */
public class QueueImplementation<E> implements QueueInterface<E> {

    private Object[] itemArray;
    private int size;
    private int head;
    private int tail;

    public QueueImplementation(int capacity) {
        itemArray = new Object[capacity];
    }

    @Override
    public int capacity() {
        return itemArray.length;
    }

    @Override
    public void enqueue(Object element) throws QueueAllocationException, NullPointerException {
        if (element == null) {
            throw new NullPointerException("Element must not be null!");
        }

        if (size == itemArray.length) {
            try {
                Object[] temp = new Object[itemArray.length * 2];
                for (int i = 0; i < itemArray.length; i++) {
                    temp[i] = itemArray[(head + i) % itemArray.length];
                }
                itemArray = temp;
                head = 0;
                tail = size;
            } catch (Exception e) {
                throw new QueueAllocationException("Queue reallocation failed!");
            }
        }

        itemArray[tail] = element;
        tail = (tail + 1) % itemArray.length;
        size++;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E dequeue() throws QueueIsEmptyException {
        if (size == 0) {
            throw new QueueIsEmptyException("Queue is empty!");
        }

        E element = (E) itemArray[head];
        head = (head + 1) % itemArray.length;
        size--;
        return element;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E element() throws QueueIsEmptyException {
        if (size == 0) {
            throw new QueueIsEmptyException("Queue is empty!");
        }

        return (E) itemArray[head];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
        head = 0;
        tail = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(itemArray[(head + i) % itemArray.length]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
