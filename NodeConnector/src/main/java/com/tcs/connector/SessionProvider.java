/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.connector;

public interface SessionProvider {
    
    public void openSession();
    public boolean isConnected();
    public void write(byte...bytes) throws Exception ;
    public int read(final byte[] bytes, final int offset, final int length) throws Exception;
    public void closeSession();

}
