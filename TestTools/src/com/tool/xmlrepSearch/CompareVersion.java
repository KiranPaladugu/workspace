/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.tool.xmlrepSearch;

import java.io.*;

public class CompareVersion {
    private File file2;
    private File file1;
    /**
     * 
     */
    public CompareVersion(File file1, File file2) {
        this.file1 = file1;
        this.file2 = file2;
    }
    
    public void doSearch(){
        try {
            char content[] = new char[(int) file1.length()];
//            BufferedReader reader2 = new BufferedReader(new FileReader(file2));
            BufferedInputStream bisr = new BufferedInputStream(new FileInputStream(file1));
            int x=-1;
            int index =0;
            while((x=bisr.read())!=-1){
                content[index] = (char)x;
                index++;
            }
            bisr.close();
//            System.out.println(content);
            BufferedReader reader = new BufferedReader(new FileReader(file2));
            String line=null;
            while((line=reader.readLine())!=null){
                fileSearch(content,line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    /**
     * @param content
     * @param line
     */
    private void fileSearch(char[] content, String line) {
//        System.out.println("Searching :"+line);
       BufferedReader reader = new BufferedReader(new CharArrayReader(content));    
       NameValues nvalue = parseXmlTag(line.trim());
       if(nvalue == null)
           return;
//       System.out.println("Searching :"+nvalue.name);
        String l = null;
        try {
            boolean found = false;
            while((l=reader.readLine())!=null){
//                System.out.println(l);
                NameValues val = parse(l.trim());
                if(val == null)
                    continue;
//                if(nvalue.name.contains("ERICcbanetconfconnecthandlermodel_CXP9031346")){
//                    System.out.println(nvalue.name+"===="+val.name);
//                }
//                if(val.name.contains("ERICcbanetconfconnecthandlermodel_CXP9031346")){
//                    System.out.println(nvalue.name+"****"+val.name);
//                }
                val.name.equals(nvalue.name);
//                System.out.println(val);
                if(val.name.contains(nvalue.name)){
                    System.out.println(nvalue.name+"\t"+nvalue.value+"\t"+val.value);
                    found = true;
                    break;
                }
            }
            if(!found){
                System.out.println(nvalue.name+"\t"+nvalue.value+"\t"+"EMPTY");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param l
     */
    private NameValues parseXmlTag(String l) {
        int valS = l.indexOf(">");
        int valE=l.lastIndexOf("<");
        if(valE==-1||valS==-1|| l.indexOf("<")==-1|| valS>=valE){
            return null;
        }
        NameValues nv = new NameValues();
        try{
        nv.value=l.substring(valS+1, valE);
        }catch(StringIndexOutOfBoundsException se){
            System.out.println("Start :"+valS+1);
            System.out.println("END :"+valE);
            System.out.println("Lenght:"+l.length());
            throw se;
        }
        nv.name=l.substring(l.indexOf("<")+1,valS);
//        System.out.println(nv);
        return nv;
        
    }

    /**
     * @param line
     * @return
     */
    private NameValues parse(String line) {
        int index=line.lastIndexOf("\t");
        if(index!=-1){
        String name = line.substring(0,index);
        String str = line.substring(index+1, line.length());
//        str=str.replace(".rpm", "");
        NameValues nv = new NameValues();
        nv.name=name;
        nv.value = str;
        return nv;
        }
        return null;
    }
    
    class NameValues{
        public String name;
        public String value;
        @Override
        public String toString() {
            return "NameValues [name=" + name + ", vaule=" + value + "]";
        }
        
    }
    
    public static void main(String args[]){
        CompareVersion cv = new CompareVersion(new File("C:\\Users\\ekirpal\\Documents\\server\\rpmList.txt"), new File("C:\\Users\\ekirpal\\Documents\\server\\properties.txt"));
        cv.doSearch();
    }
}
