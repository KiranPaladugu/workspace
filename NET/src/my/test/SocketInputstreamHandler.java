/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package my.test;

import java.io.*;
import java.net.Socket;

public class SocketInputstreamHandler implements Runnable{

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    private static int count;
    private String name = this.getClass().getName();
    private BufferedInputStream bufferedStream;
    private Socket socket;
     
    
    /**
     * @param inputStream
     * @param socket
     */
    public SocketInputstreamHandler(InputStream inputStream, Socket socket) {
        super();
        this.socket = socket;
        this.bufferedStream = new BufferedInputStream(inputStream);
    }


    @Override
    public void run() {
        this.name = this.getClass().getSimpleName()+""+count++;
        Thread.currentThread().setName(name);
        System.out.println("Handling clint request...");
        byte[] data= new byte[256];
        int size =-1;
        try {
            while((size=bufferedStream.read(data))!=-1){
                String stringValue = new String(data,0,size);
                System.out.print(stringValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
