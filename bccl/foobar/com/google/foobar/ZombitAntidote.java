/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.google.foobar;

import java.util.*;

public class ZombitAntidote {
    private static int count;
    private static int arrayCount;

    final static Comparator<int[]> arrayComparator = new Comparator<int[]>() {
        @Override
        public int compare(int[] first, int[] second) {
            if (Integer.compare(first[0], second[0]) == 0) {

                return Integer.compare(first[1], second[1]);
            } else
                return Integer.compare(first[0], second[0]);
        }
    };

    public static int answser(int[][] meetings) {
        arrayCount = meetings.length;
        List<int[]> list = Arrays.asList(meetings);
        Collections.sort(list, arrayComparator);
        printArray(list);
        List<int[]> bestmeetings = new ArrayList<>();
        int[] found = null;
        int startFrom = list.get(0)[0];
        do {
            List<int[]> output = new ArrayList<>();
            found = findNextMeeting(list, startFrom, output);
            list = output;
            printArray(list);
            if (found != null) {
                startFrom = found[1];
                System.out.println("* Found next available Best schedule: [" + found[0] + "," + found[1] + "]");
                bestmeetings.add(found);
            }
        } while (found != null);
        System.out.println("BestMeetings:");
        printArray(bestmeetings);
        return bestmeetings.size();
    }

    public static int[] findNextMeeting(List<int[]> list, int startFrom, List<int[]> output) {
        int bestAvailable_sart = -1;
        int bestAvailable_end = -1;
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            count++;
            int[] meeting = list.get(i);
            int start = meeting[0];
            int end = meeting[1];
            if (bestAvailable_sart == -1) {
                if (start >= startFrom) {
                    bestAvailable_sart = start;
                    bestAvailable_end = end;
                }

            } else if (bestAvailable_sart <= start) {
                bestAvailable_sart = start;
            } else {
                continue;
            }

            if (bestAvailable_sart > -1) {
                if (bestAvailable_end > -1 && bestAvailable_end >= end) {
                    if (start >= startFrom) {
                        bestAvailable_sart = start;
                        bestAvailable_end = end;
                        index = i;
                    }
                } else {
                    break;
                }
            }
        }
        //        currentStart = bestAvailable_end;
        if (index != -1) {
            int from = index + 1;
            int to = list.size();
            //            System.out.println("cut list from " + (from + 1) + " to " + to);
            if (from <= to)
                output.addAll(list.subList(from, to));
            return list.get(index);
        }
        return null;
    }

    public static int getFactorSum(int number) {
        if (number > 0) {
            return number + getFactorSum(number - 1);
        }
        return number;
    }

    public static void main(String[] args) {
        int limit = 20;
        int size = getFactorSum(limit - 1);
        int x[][] = new int[size * 3][2];
        //        Random start = new Random(10);
        int index = 0;
        for (int i = 0; i <= limit; i++) {
            int k = i + 1;
            int j = k + 1;
            while (j <= limit) {
                x[index][0] = k;
                x[index][1] = j;
                x[index + 1][0] = k;
                x[index + 1][1] = j;
                x[index + 2][0] = k;
                x[index + 2][1] = j;
                index += 3;
                j++;
            }
        }

        List<int[]> list = Arrays.asList(x);
        //        Collections.sort(list, arrayComparator);
        System.out.println("Printing generated Schedule array");
        printArray(list);
        System.out.println();

        //        int num = answser(x);
        //        System.out.println("best Meeting count = " + num);
        printArray(x);
        //        String input = "[[3,5],[0, 1],[3,6],[6,7],[5,6],[5,7],[4,5],[4,5], [1, 2], [2, 3], [3,7], [4, 7], [5,7],[10,11]]";
        String input = "[[0, 1000000], [42, 43], [0, 1000000], [42, 43],[40,42]]";
        //        System.out.println("Input String:" + input);
        int[][] genArray = generateArrayFromInput(input);
        //        printArray(genArray);
        int num = answser(x);
        System.out.println("best Meeting count = " + num);
        System.out.println("meetings:" + arrayCount);
        System.out.println("ITR:" + count);
    }

    private static void printArray(List<int[]> list) {
        for (int[] meet : list) {
            System.out.print("[" + meet[0] + "," + meet[1] + "],");
        }
        System.out.println();
    }

    private static void printArray(int[][] intArray) {
        System.out.print("[");
        for (int i = 0; i < intArray.length; i++) {
            System.out.print("[");
            for (int j = 0; j < intArray[i].length; j++) {
                System.out.print(intArray[i][j]);
                if (j + 1 < intArray[i].length) {
                    System.out.print(",");
                }
            }
            System.out.print("]");
            if (i + 1 < intArray.length) {
                System.out.print(",");
            }
        }
        System.out.print("]\n");
    }

    /**
     * Generates 2D integer array from input string.
     * 
     * @param input
     *            should be formatted. Ex [[0, 1], [1, 2], [2, 3], [3, 5], [4, 5]]
     * @return
     */
    private static int[][] generateArrayFromInput(String input) {
        String open = "[";
        String close = "]";
        String separator = ",";
        //        char arry[] =input.trim().toCharArray();
        int oneDStart = input.indexOf(open);
        if (oneDStart != -1) {
            int oneDend = input.lastIndexOf(close);
            if (oneDend != -1) {
                String oneDArary = input.substring(oneDStart + 1, oneDend);
                String oneDArarys[] = oneDArary.split(close + separator);
                int[][] intArray = new int[oneDArarys.length][2];
                int index = 0;
                for (String arry : oneDArarys) {
                    String[] singles = arry.split(separator);
                    for (String single : singles) {
                        int start = single.indexOf(open);
                        if (start != -1) {
                            intArray[index][0] = Integer.parseInt(single.substring(start + 1).trim());
                        } else {
                            int end = single.indexOf(close);
                            if (end != -1) {
                                intArray[index][1] = Integer.parseInt(single.substring(0, end).trim());
                            } else
                                intArray[index][1] = Integer.parseInt(single.trim());
                        }
                    }
                    index++;
                }
                return intArray;
            }
        }
        return null;

    }
}
