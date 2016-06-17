/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package my.tester.test;

public class MainClassToRun {
    public static void main(String[] args){
        SuperClass superClass = new FirstChildClass();
        superClass.methodA();
        superClass.methodB();
    }
}
