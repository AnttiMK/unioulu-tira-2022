package oy.tol.tra;

import java.util.function.Predicate;

public class Algorithms {

    public static <T extends Comparable<T>> void sort(T[] array) {
        int length = array.length;
        for (int i = 1; i < length; i++) {
            T key = array[i];
            int j = i - 1;

            while (j >= 0 && key.compareTo(array[j]) < 0) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    public static <T> void reverse(T[] array) {
        int i = 0;
        while (i < array.length / 2) {
            swap(array, i, array.length - i - 1);
            i++;
        }
    }

    public static <T> void swap(T [] array, int first, int second) {
        T temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }

    public static <T extends Comparable<T>> ModeSearchResult<T> findMode(T [] array) {
        ModeSearchResult<T> result = new ModeSearchResult<>();
        if (array == null || array.length < 2) {
            result.theMode = null;
            result.count = -1;
            return result;
        }

        sort(array);
        int maxCount = 0;
        int currentCount = 0;
        T currentMode = null;
        for (int i = 0; i < array.length; i++) {
            if (i == 0 || array[i].equals(array[i - 1])) {
                currentCount++;
            } else {
                if (currentCount > maxCount) {
                    maxCount = currentCount;
                    currentMode = array[i - 1];
                }
                currentCount = 1;
            }
        }
        if (currentCount > maxCount) {
            maxCount = currentCount;
            currentMode = array[array.length - 1];
        }

        result.theMode = currentMode;
        result.count = maxCount;
        return result;
    }

    public static class ModeSearchResult<T> {
        public T theMode;
        public int count = 0;
    }

    public static <T> int partitionByRule(T [] array, int count, Predicate<T> rule) {
        int index = 0;
        for (int i = 0; i < count; i++) {
            if (rule.test(array[i])) {
                index = i;
                break;
            }
        }

        if (index >= count) {
            return count;
        }

        for (int i = index + 1; i < count; i++) {
            if (!rule.test(array[i])) {
                swap(array, index, i);
                index++;
            }
        }
        return index;
    }

}
