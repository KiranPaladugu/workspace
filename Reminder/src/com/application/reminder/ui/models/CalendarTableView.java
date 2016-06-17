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
package com.application.reminder.ui.models;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("unused")
public class CalendarTableView extends JTable {
    /**
     * 
     */

    private static final long serialVersionUID = 1L;
    private String[] week_med = { "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN" };
    private String[] week_short = { "Mo", "Tu", "We", "Th", "Fr", "Sa", "Su" };

    private String[] week_long = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
    private DefaultTableModel tableModel = new DefaultTableModel();
    private DefaultTableColumnModel coloumnModel = new DefaultTableColumnModel();

    /**
	 * 
	 */
    public CalendarTableView() {
        this.setColumnModel(coloumnModel);
        init();
        this.setModel(tableModel);

    }

    /**
	 * 
	 */
    private void init() {
        tableModel.setColumnIdentifiers(week_short);

        //		tableModel.addRow(week_med);
        tableModel.addRow(new Vector<String>());
        tableModel.addRow(new Vector<String>());
        tableModel.addRow(new Vector<String>());
        tableModel.addRow(new Vector<String>());
        tableModel.addRow(new Vector<String>());
        tableModel.addRow(new Vector<String>());
        coloumnModel.setColumnSelectionAllowed(true);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }
}
