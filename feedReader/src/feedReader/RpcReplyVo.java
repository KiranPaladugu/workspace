/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package feedReader;

public class RpcReplyVo {

    private String messageId;
    private boolean ok;
    private boolean isError;
    private String data;

    /**
     * @param messageId
     */
    public void setMessageId(String messageId) {
        this.messageId=messageId;
    }

    /**
     * @param b
     */
    public void setOk(boolean b) {
        this.ok = b;
    }

    /**
     * @param isError
     */
    public void setErrorReceived(boolean isError) {
        this.isError = isError;
    }

    /**
     * @return
     */
    public String getMessageId() {
        return this.messageId;
    }

    /**
     * @param data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return
     */
    public String getData() {
        return data;
    }

}
