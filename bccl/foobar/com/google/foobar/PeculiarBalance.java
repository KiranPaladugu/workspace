/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.google.foobar;

import java.util.Stack;

public class PeculiarBalance {
    private static String left = "L";
    private static String right = "R";
    private static String none = "-";
    private static int multiplier = 3;

    public static String[] answer(int x) {
        if (x <= 0) {
            return null;
        }
        Stack<String> stack = new Stack<>();
        int leftVal = x;
        int rightVal = 0;
        int pow = findPow(x);
        int threshold = getValue(pow + 1) / 2;
        int current = pow;
        if (leftVal == getValue(pow)) {
            stack.push(right + "=" + getValue(pow));
            current--;
            while (current >= 0) {
                stack.push(none);
                current--;
            }

        } else {
            if (leftVal > threshold) {
                current++;
            }
            stack.push(right + "=" + getValue(current));
            rightVal += getValue(current);
            current--;

            while (current >= 0) {
                int newVal = getValue(current);
                if (leftVal == rightVal) {
                    stack.push(none);
                } else if (leftVal > rightVal) {
                    if (leftVal < (rightVal + newVal)) {
                        if (((rightVal + newVal) - leftVal) > getPowSum(current - 1)) {
                            stack.push(none);
                        } else {
                            stack.push(right + "=" + getValue(current));
                            rightVal += newVal;
                        }
                    } else {
                        stack.push(right + "=" + getValue(current));
                        rightVal += newVal;
                    }
                } else if (leftVal < rightVal) {
                    if ((leftVal + newVal) > rightVal) {
                        if (((leftVal + newVal) - rightVal) > getPowSum(current - 1)) {
                            stack.push(none);
                        } else {
                            stack.push(left + "=" + getValue(current));
                            leftVal += newVal;
                        }
                    } else {
                        stack.push(left + "=" + getValue(current));
                        leftVal += newVal;
                    }
                }
                current--;
            }
        }

        String[] bal = new String[stack.size()];
        stack.toArray(bal);
        return reverseArray(bal);
    }

    private static int getPowSum(int val) {
        int sum = 0;
        while (val >= 0) {
            sum += getValue(val);
            val--;
        }
        return sum;
    }

    private static String[] reverseArray(String[] array) {
        int size = array.length;
        String newArray[] = new String[size];
        size--;
        int index = 0;
        while (size >= 0) {
            newArray[size] = array[index];
            size--;
            index++;
        }
        return newArray;
    }

    public static int findPow(int x) {
        return findPow(x, 0);
    }

    private static int findPow(int x, int initVal) {
        int val = x / multiplier;
        //        System.out.println(" reminder for x:"+x%3);
        if (val >= 1) {
            initVal += 1;
            return findPow(val, initVal);
        }
        return initVal;
    }

    private static int getValue(int current) {
        return (int) Math.pow(multiplier, current);
    }

    public static void main(String ags[]) {
        int i = 0;
        long start = System.currentTimeMillis();
        while (i < 200) {
            //            System.out.println("____________________________________________________________ " + i);
            System.out.print("L=" + i + ",");
            String strs[] = answer(i);
            if (strs != null)
                for (String str : strs) {
                    System.out.print(str + ",");
                }
            System.out.println();
            i++;
        }
        long end = System.currentTimeMillis();
        System.out.println("Time taken :"+(end-start)+" ms");
    }

}
