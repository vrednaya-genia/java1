package ru.progwards.java1.lessons.collections;

import java.util.ArrayList;
import java.util.Collection;

public class Finder {
    public static Collection<Integer> findMinSumPair(Collection<Integer> numbers) {
        Collection<Integer> res = new ArrayList();
        Integer[] nums = new Integer[numbers.size()];
        numbers.toArray(nums);
        int minSum = nums[0]+nums[1];
        for (int i=1; i<nums.length-1; i++) {
            if (nums[i]+nums[i+1]<minSum) {
                minSum = nums[i]+nums[i+1];
                res.clear();
                res.add(i);
                res.add(i+1);
            }
        }
        return res;
    }

    public static Collection<Integer> findLocalMax(Collection<Integer> numbers) {
        Collection<Integer> res = new ArrayList();
        Integer[] nums = new Integer[numbers.size()];
        numbers.toArray(nums);
        for (int i=1; i<nums.length-1; i++) {
            if (nums[i]>nums[i-1] && nums[i]>nums[i+1]) {
                res.add(nums[i]);
            }
        }
        return res;
    }

    public static boolean findSequence(Collection<Integer> numbers) {
        for (int i=1; i<=numbers.size(); i++) {
            if (!numbers.contains(i)) {
                return false;
            }
        }
        return true;
    }

    public static String findSimilar(Collection<String> names) {
        String[] strs = new String[names.size()];
        names.toArray(strs);
        int[] counts = new int[strs.length];
        for (int i=0; i<strs.length; i++) {
            for (int j=0; j<strs.length; j++) {
                if (strs[i].equals(strs[j])) {
                    counts[i] = counts[i]+1;
                }
            }
        }
        int max = counts[counts.length-1];
        String res = strs[counts.length-1] + ":" + counts[counts.length-1];
        for (int i=counts.length-2; i>=0; i--) {
            if (counts[i]>=max) {
                max = counts[i];
                res = strs[i] + ":" + counts[i];
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Collection<Integer> numbers = new ArrayList();
        numbers.add(3);
        numbers.add(8);
        numbers.add(1);
        numbers.add(5);
        numbers.add(4);
        Collection<Integer> res = findLocalMax(numbers);
        for (Integer i : res) {
            System.out.println(i);
        }
        System.out.println(findSequence(numbers));

        Collection<String> names = new ArrayList();
        names.add("Катя");
        names.add("Женя");
        names.add("Катя");
        names.add("Женя");
        System.out.println(findSimilar(names));
    }
}
