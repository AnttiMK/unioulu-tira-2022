package oy.tol.tra;

/**
 * This class instantiates different types of stacks implementing the {@code StackInterface} interface.
 * <p>
 * 
 * @author Antti Juustila
 * @version 1.0
 */
public class StackFactory {

   private StackFactory() {
   }

   /**
    * Creates an instance of StackImplementation for Integer type.
    * @param capacity Number of elements the stack can hold.
    * @return The stack object.
    */
   public static StackInterface<Integer> createIntegerStack(int capacity) {
      return new StackImplementation<>(capacity);
   }

   /**
    * Instantiates a stack of Characters.
    * @param capacity The initial size for the new stack.
    * @return The stack implementation holding Characters.
    */
   public static StackInterface<Character> createCharacterStack(int capacity) {
      return new StackImplementation<>(capacity);
   }
}
