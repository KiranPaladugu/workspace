/* ********************************************************************************
 * All rights reserved to Kiran Paladugu. If you find any thing useful send your
 * valueble feeback to paladugukiran@gmail.com.
 ******************************************************************************* */
package com.pack.test.format;

public class YangVersionParser {
    public static String toVersionString(String version) {
        String versionString = null;
        String[] tokens = version.split("\\-");
        if (tokens.length == 3) {
            versionString = Integer.parseInt(tokens[0]) + "." + Integer.parseInt(tokens[1]) + "." + Integer.parseInt(tokens[2]);
        } else {
            if (version.split("\\.").length == 3) {
                versionString = version;
            } else if (version.split("\\.").length == 2) {
                versionString = version + ".0";
            } else {
                versionString = version + ".0.0";
            }
        }
        return versionString;
    }

    public static void main(String args[]) {
        System.out.println(toVersionString("2015-05-6"));
        System.out.println(toVersionString("12304.0"));
        System.out.println(toVersionString("1.0.5"));
    }
}
