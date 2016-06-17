/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.bccl;

public class NumberPrinter extends Thread {
    private int start;
    private int max;
    private ConsolePrinter printer;

    public static NumberPrinter getEvenNumberPrinterThread(int max,ConsolePrinter printer) {
        return new NumberPrinter(0, max, printer,"EvenNumberPrinter");
    }
    public static NumberPrinter getOddNumberPrinterThread(int max,ConsolePrinter printer) {
        return new NumberPrinter(1, max, printer,"OddNumberPrinter");
    }

    public NumberPrinter(int start, int max, ConsolePrinter printer,String threadName) {
        this.start = start;
        this.max = max;
        this.printer = printer;
        if(threadName !=null && threadName.length()>0)
            this.setName(threadName);
        if (max > 0)
            this.start();

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        do {
            printer.printNumber(start, max);
            start += 2;
        } while (start <= max);
    }
}
