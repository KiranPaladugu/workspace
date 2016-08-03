/* ********************************************************************************
 * All rights reserved to Kiran Paladugu. If you find any thing useful send your
 * valueble feeback to paladugukiran@gmail.com.
 ******************************************************************************* */
package com.practice.algo.sorting;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ArrayGenerator {
    public static int[] generateRandom(int length) {
        int[] random = new int[length];
        List<Integer> list = new ArrayList<Integer>();
        boolean generate = true;
        int index = 0;
        while (generate) {
            int value = ThreadLocalRandom.current().nextInt(length);
            //            System.out.println("Generated:" + value);
            if (!list.contains(value)) {
                //                System.out.println("adding value:" + value);
                random[index] = value;
                list.add(value);
                index++;
                if (index >= length) {
                    generate = false;
                    //                    System.out.println("break;");
                }
            }
        }
        return random;
    }

    public static void printintArray(int[] array) {
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i + 1 < array.length) {
                System.out.print(",");
            }
        }
        System.out.print("]");
        System.out.println();
    }

    public static int[] generateArrayFromInput(String input) {
        if (input != null && input.length() > 0) {
            String[] values = input.split(",");
            int[] array = new int[values.length];
            int index = 0;
            for (String value : values) {
                array[index] = Integer.parseInt(value.trim());
                index++;
            }
            return array;
        }
        return null;
    }
}
