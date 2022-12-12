package oy.tol.tra;

public class LinkedListImplementation<E> implements LinkedListInterface<E> {

    private Node<E> head = null;
    private int size = 0;

    @Override
    public void add(E element) throws NullPointerException, LinkedListAllocationException {
        if (element == null) {
            throw new NullPointerException("Element must not be null!");
        }

        Node<E> node = new Node<>(element);
        if (head == null) {
            head = node;
        } else {
            Node<E> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = node;
        }
        size++;
    }

    @Override
    public void add(int index, E element) throws NullPointerException, LinkedListAllocationException, IndexOutOfBoundsException {
        if (element == null) {
            throw new NullPointerException("Element must not be null!");
        }

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + size);
        }

        Node<E> node = new Node<>(element);
        if (index == 0) {
            node.next = head;
            head = node;
        } else {
            Node<E> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            node.next = current.next;
            current.next = node;
        }
        size++;
    }

    @Override
    public boolean remove(E element) throws NullPointerException {
        if (element == null) {
            throw new NullPointerException("Element must not be null!");
        }

        if (head == null) {
            return false;
        }

        if (head.element.equals(element)) {
            head = head.next;
            size--;
            return true;
        }

        Node<E> current = head;
        while (current.next != null) {
            if (current.next.element.equals(element)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + size);
        }

        if (index == 0) {
            E element = head.element;
            head = head.next;
            size--;
            return element;
        }

        Node<E> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        E element = current.next.element;
        current.next = current.next.next;
        size--;
        return element;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + size);
        }

        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.element;
    }

    @Override
    public int indexOf(E element) throws NullPointerException {
        if (element == null) {
            throw new NullPointerException("Element must not be null!");
        }

        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            if (current.element.equals(element)) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public void reverse() {
        if (head == null) {
            return;
        }

        Node<E> current = head;
        Node<E> previous = null;
        Node<E> next;
        while (current != null) {
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        head = previous;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<E> current = head;
        sb.append("[");
        while (current != null) {
            sb.append(current.element);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    private class Node<T> {

        T element;
        Node<T> next;
        Node(T data) {
            element = data;
            next = null;
        }

        @Override
        public String toString() {
            return element.toString();
        }

    }

}
