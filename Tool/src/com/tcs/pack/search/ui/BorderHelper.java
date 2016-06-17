
package com.tcs.pack.search.ui;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class BorderHelper {
	
	public static void setLineBorder(Component component){
		if(component instanceof JPanel){
			((JPanel)component).setBorder(BorderFactory.createLineBorder(Color.GRAY));
		}
	}
	
	public static void setTitleBorder(Component component, String title){
		if(component instanceof JPanel){
			((JPanel)component).setBorder(BorderFactory.createTitledBorder(title));
		}
	}

}
