/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package my.tester.test;

public class FirstChildClass extends SuperClass{
    private String varA="FirstChild";
    /* (non-Javadoc)
     * @see my.tester.test.SuperClass#methodB()
     */
    @Override
    public void methodB() {
        super.methodB();
    }
    /**
     * @return the varA
     */
    public String getVarA() {
        return varA;
    }
}
