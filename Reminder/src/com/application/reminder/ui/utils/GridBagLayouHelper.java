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
package com.application.reminder.ui.utils;

import java.awt.*;

import javax.swing.JPanel;

public class GridBagLayouHelper {
	public static GridBagConstraints getDefaultGC() {
		return new GridBagConstraints(0, 0, 1, 1, 1, 1,
				GridBagConstraints.LAST_LINE_START, GridBagConstraints.BOTH,
				new Insets(1, 1, 1, 1), 0, 0);
	}
	
	public static GridBagConstraints getDefaultGC(int x, int y){
		GridBagConstraints gc = getDefaultGC();
		gc.gridx=x;
		gc.gridy=y;
		return gc;
	}

	public static JPanel arrageInRow(int column,Component... components) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());		
		for(int i=0;i<components.length;i++){
			panel.add(components[i],getDefaultGC(column, i));
		}
		return panel;
	}
	
	public static JPanel arrageInColumn(int row,Component... components) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());		
		for(int i=0;i<components.length;i++){
			panel.add(components[i],getDefaultGC(i,row));
		}
		return panel;
	}
	
	public static JPanel arrageInRow(Component ...components){
		return arrageInRow(0, components);
	}
	
	public static JPanel arrangeInColoumn(Component ...components){
		return arrageInColumn(0, components);
	}
	
	private GridBagLayouHelper(){
		
	}
	
	public static GridBagLayouHelper getInstance(){
		return new GridBagLayouHelper();
	}
	
	private int x =0;
	private int y=0;
	private double weightx=1;
	private double weighty=1;
	
	public GridBagConstraints getNextColumnGC(){
		GridBagConstraints constraints = getDefaultGC();
		constraints.gridx=x;
		constraints.weightx=this.weightx;
		constraints.weighty=this.weighty;
		x++;
		return constraints;
	}
	
	public GridBagConstraints getNextRowGC(){
		GridBagConstraints constraints = getDefaultGC();
		constraints.gridy=y;
		constraints.weightx=this.weightx;
		constraints.weighty=this.weighty;
		y++;
		x=0;
		return constraints;
	}
	
}
