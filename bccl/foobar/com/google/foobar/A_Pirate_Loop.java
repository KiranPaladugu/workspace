/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.google.foobar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class A_Pirate_Loop {
    public static int answer(int pirates[]) {
        int who = 0;
        List<Integer> list = new ArrayList<>();
        boolean cont = true;
        int start = -1;
        do {
            who = findNextPirate(pirates, who);
            if (who > pirates.length - 1) {
                cont = false;
            }
            if (list.contains(who)) {
                int index = getIndex(list, who);
                if(index!=-1){
                    start=index;
                    cont = false;
                }
            }
            list.add(who);
        } while (cont);
        List<Integer> newList =  list.subList(start+1, list.size());
        System.out.println("pirates Forming loop:"+newList);
        return newList.size();
    }
    
    public static int getIndex(List<Integer> list , int value){
        int index = -1;
        for(int i =0;i<list.size();i++){
            Integer integer = list.get(i);
            if(integer.intValue()==value){
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * @param pirates
     */
    private static int findNextPirate(int[] pirates, int who) {
        System.out.println("Who:" + who);
        return pirates[who];
    }

    public static void main(String[] args) {
        //        printintArray(generateRandom(20));
        //        printintArray(generateArrayFromInput("1,3,0,1"));
//        int arr[] = generateArrayFromInput("1,3,0,1");
        int arr[] =generateRandom(10000);
        printintArray(arr);
        int value = answer(arr);
        System.out.println("Pirates forming loop:" + value);
    }

    public static int[] generateRandom(int length) {
        int[] random = new int[length];
        int size = length / 2;
        int previous = 0;
        for (int i = 0; i < size; i++) {
            int value = ThreadLocalRandom.current().nextInt(size);
            if (value == previous || i == value) {
                value = ThreadLocalRandom.current().nextInt(size);
            }
            previous = value;
            random[i] = value;
        }
        for (int i = size; i < length; i++) {
            int value = ThreadLocalRandom.current().nextInt(size);
            if (value == previous || i == value) {
                value = ThreadLocalRandom.current().nextInt(size);
            }
            previous = value;
            random[i] = value;
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
