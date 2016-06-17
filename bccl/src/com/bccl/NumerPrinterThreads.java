/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.bccl;

public class NumerPrinterThreads {
    
 public static void main(String[] args){
     ConsolePrinter printer = new ConsolePrinter();
     NumberPrinter.getEvenNumberPrinterThread(30, printer);
     NumberPrinter.getOddNumberPrinterThread(30, printer);
    
     
 }
}
