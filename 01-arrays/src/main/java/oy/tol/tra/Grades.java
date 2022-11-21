package oy.tol.tra;

/**
 * A simple array of student grades to be used in testing
 * misbehaving algorithm for reversing the array.
 */
public class Grades {
   
   private Integer [] grades = null;

   /**
    * A constructor for building IntArrays.
    * @param grades the plain Java integer array with numbers to add.
    */
   public Grades(Integer [] grades) {
      this.grades = new Integer [grades.length];
      for (int counter = 0; counter < grades.length; counter++) {
         this.grades[counter] = grades[counter];
      }
   }

   /**
    * The method to reverse the internal Java int array.
    */
   public void reverse() {
      int i = 0;
      while (i < grades.length / 2) {
         int temp = grades[i];
         grades[i] = grades[grades.length - i - 1];
         grades[grades.length - i - 1] = temp;
         i++;
      }
   }

   /**
    * Sorts the array to ascending order.
    */
   public void sort() {
      int length = grades.length;
      for (int i = 1; i < length; i++) {
         int key = grades[i];
         int j = i - 1;

         while (j >= 0 && grades[j] > key) {
            grades[j + 1] = grades[j];
            j--;
         }
         grades[j + 1] = key;
      }
   }


   /**
    * Returns the plain Java int [] array for investigation.
    * @return The int array.
    */
   public Integer [] getArray() {
      return grades;
   }
}
