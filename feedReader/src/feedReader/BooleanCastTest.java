/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package feedReader;

public class BooleanCastTest {
    public static void print(String str, boolean bool){
        System.out.println(str);
        System.out.println(bool);
    }
    public static void main(String args[]){
        Object[] obj =new Object[] { "request", true };
        Boolean bool = (boolean)obj[1];
        print((String) obj[0], bool);
    }
}
