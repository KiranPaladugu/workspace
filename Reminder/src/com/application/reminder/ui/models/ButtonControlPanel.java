/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2013
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.application.reminder.ui.models;


public class ButtonControlPanel extends ControlPanel {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
	 * 
	 */
	public ButtonControlPanel() {
		this.setExpandPolicy(ControlPanel.HORIZONTAL_FULL);
		this.setWeightX(1);
		this.setWeightY(0);
		this.setExpandable(true);
	}
}
