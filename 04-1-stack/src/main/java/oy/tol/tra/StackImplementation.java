package oy.tol.tra;

/**
 * An implementation of the StackInterface.
 * <p>
 *
 * Note that you need to implement construtor(s) for your concrete StackImplementation, which
 * allocates the internal Object array for the Stack:
 * - a default constructor, calling the StackImplementation(int size) with value of 10.
 * - StackImplementation(int size), which allocates an array of Object's with size.
 *  -- remember to maintain the capacity and/or currentIndex when the stack is manipulated.
 */
public class StackImplementation<E> implements StackInterface<E> {
   private static final int DEFAULT_CAPACITY = 10;
   private static final int MINIMUM_CAPACITY = 2;
   private static final int DEFAULT_INDEX = -1;
   private static final int REALLOCATE_INCREMENT = 1;

   private Object [] itemArray;
   private int capacity;
   private int currentIndex;

   /**
    * Allocates a stack with a default capacity.
    * @throws StackAllocationException If cannot allocate room for the internal array.
    */
   public StackImplementation() throws StackAllocationException {
        this(DEFAULT_CAPACITY);
   }

   /**
    * - if the size is less than 2, throw StackAllocationException
    * - if the allocation of the array throws with Java exception,
    *   throw StackAllocationException.
    * @param capacity The capacity of the stack.
    * @throws StackAllocationException If cannot allocate room for the internal array.
    */
   public StackImplementation(int capacity) throws StackAllocationException {
      if (capacity < MINIMUM_CAPACITY) {
         throw new StackAllocationException("Stack capacity must be >= 2");
      }

      try {
         itemArray = new Object[capacity];
      } catch (Exception e) {
         throw new StackAllocationException("Could not allocate stack");
      }

      this.capacity = capacity;
      this.currentIndex = DEFAULT_INDEX;
   }

   @Override
   public int capacity() {
      return capacity;
   }

   @Override
   public void push(E element) throws StackAllocationException, NullPointerException {
      if (element == null) {
         throw new NullPointerException("Cannot push a null element");
      }

      // Check if we need to reallocate
      if (currentIndex == capacity - 1) {
         try {
            Object[] newArray = new Object[capacity + REALLOCATE_INCREMENT];
            // Note: Could use a for loop here, but System.arraycopy is native code and hence faster
            System.arraycopy(itemArray, 0, newArray, 0, capacity);
            itemArray = newArray;
            capacity += REALLOCATE_INCREMENT;
         } catch (StackAllocationException e) {
            throw new StackAllocationException("Could not allocate more space for stack");
         }
      }

      itemArray[++currentIndex] = element;
   }

   @SuppressWarnings("unchecked")
   @Override
   public E pop() throws StackIsEmptyException {
      if (currentIndex == DEFAULT_INDEX) {
          throw new StackIsEmptyException("Cannot pop from an empty stack");
      }

      E element = (E) itemArray[currentIndex];
      itemArray[currentIndex] = null;
      currentIndex--;
      return element;
   }

   @SuppressWarnings("unchecked")
   @Override
   public E peek() throws StackIsEmptyException {
      if (currentIndex == DEFAULT_INDEX) {
         throw new StackIsEmptyException("Cannot peek an empty stack");
      }

      return (E) itemArray[currentIndex];
   }

   @Override
   public int size() {
      return currentIndex + 1;
   }

   @Override
   public void clear() {
      for (int i = 0; i <= currentIndex; i++) {
         itemArray[i] = null;
      }
      currentIndex = DEFAULT_INDEX;
   }

   @Override
   public boolean isEmpty() {
        return currentIndex == DEFAULT_INDEX;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("[");

      for (int i = 0; i <= currentIndex; i++) {
         sb.append(itemArray[i].toString());
         if (i < currentIndex) {
            sb.append(", ");
         }
      }

      sb.append("]");
      return sb.toString();
   }
}
