/* ********************************************************************************
 * All rights reserved to Kiran Paladugu. If you find any thing useful send your
 * valueble feeback to paladugukiran@gmail.com.
 ******************************************************************************* */
package com.pack.test.format;

import java.util.ArrayList;
import java.util.List;

public class Fixer {
    private List<FixerComponent> fixComponnet = new ArrayList<>();
    
    public void addFixerComponent(FixerComponent comp){
        fixComponnet.add(comp);
    }
    
    public String exec(String data){
        for(FixerComponent comp:fixComponnet){
            data = comp.fixFormat(data);
        }
        return data;
    }
}
