/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tools;

import com.tcs.application.Plugin;

public class NetconfNodeConnectorPlugin implements Plugin {
    
    private String name;
    private String plugId;
    private String identifier;

    /* (non-Javadoc)
     * @see com.tcs.application.Plugin#setPlugId(java.lang.String)
     */
    @Override
    public void setPlugId(String id) {
        this.plugId = id;
    }

    /* (non-Javadoc)
     * @see com.tcs.application.Plugin#getPlugId()
     */
    @Override
    public String getPlugId() {
        return plugId;
    }

    /* (non-Javadoc)
     * @see com.tcs.application.Plugin#setName(java.lang.String)
     */
    @Override
    public void setName(String name) {
       this.name = name;
        
    }

    /* (non-Javadoc)
     * @see com.tcs.application.Plugin#getName()
     */
    @Override
    public String getName() {
       return this.name;
    }

    /* (non-Javadoc)
     * @see com.tcs.application.Plugin#setIdentifier(java.lang.String)
     */
    @Override
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /* (non-Javadoc)
     * @see com.tcs.application.Plugin#getIdentifier()
     */
    @Override
    public String getIdentifier() {
        return identifier;
    }
    
    public void startConnectorPlugin(){
        
    }
    
    public void shutDownConnectorPlugin(){
        
    }

}
