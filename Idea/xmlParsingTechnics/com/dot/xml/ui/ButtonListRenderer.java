/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.xml.ui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class ButtonListRenderer extends DefaultListCellRenderer{
   
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }
}
