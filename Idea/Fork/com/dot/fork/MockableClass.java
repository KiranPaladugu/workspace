/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.fork;

public class MockableClass {
    public void callableMethod(String someValue) {
        System.out.println("This is mockable method with one String input and input is :" + someValue);
    }

    public void callableMethod() {
        System.out.println("This is mockable method with no inputs");
    }

    public void callableMethod(int someValue) {
        System.out.println("This is mockable method with one int input and input is :" + someValue);
    }

    public void callableMethod(boolean someValue) {
        System.out.println("This is mockable method with one boolean input and input is :" + someValue);
    }

    public void callableMethod(long someValue) {
        System.out.println("This is mockable method with one long input and input is :" + someValue);
    }

    public void callableMethod(int[] someValue) {
        System.out.println("This is mockable method with one int[] input and input is :" + someValue);
    }

    public void callableMethod(byte[] someValue) {
        System.out.println("This is mockable method with one byte[] input and input is :" + someValue);
    }

}
