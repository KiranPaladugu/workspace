/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package my.tester.test;

public class SuperClass {
    private String varA = "Super";

    public void methodA() {
        System.out.println(this.getClass().getSimpleName() + ".methodA()");
    }

    public void methodB() {
//        System.out.println(this.getClass().getSimpleName()+".methodB()");
        System.out.println(this.getClass().getSimpleName()+".methodB()="+getVarA());
    }
    
    public String getVarA(){
        return this.varA;
    }
}
