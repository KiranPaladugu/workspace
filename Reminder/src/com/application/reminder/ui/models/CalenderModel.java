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

import java.awt.*;
import java.util.Date;

import javax.swing.*;

import com.application.reminder.ui.utils.LayoutUtils;

public class CalenderModel extends ViewModel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JComboBox<String> txt_month;
    private JFormattedTextField txt_year;
    private JButton btn_year_up, btn_year_down;

    private String[] months = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
    @SuppressWarnings("unused")
    private String[] months_long = { "January", "February", "March", "April", "May", "June", "July", "Augus", "September",
            "October", "November", "December" };

    // private Integer tableData[][] ={{},{},{},{},{},{}};
    private JTable table;

    /**
	 * 
	 */
    @SuppressWarnings("deprecation")
    public CalenderModel() {

        txt_month = new JComboBox<String>(months);
        btn_year_down = new JButton("-");
        txt_year = new JFormattedTextField();
        txt_year.setEditable(false);
        txt_year.setText((new Date()).getYear() + 1900 + "");
        btn_year_up = new JButton("+");
        table = new CalendarTableView();
        JLabel lbl_month = new JLabel("Month: ");
        JLabel lbl_Year = new JLabel("Year: ");
        JScrollPane tbl_scroll = new JScrollPane(table);
        ;
        //        this.setLayout(new GridBagLayout());
        this.setLayout(new GridLayout(1, 1));
        //LayoutUtils layoutUtils = LayoutUtils.getUtils("");
        //		 this.add(LayoutUtils.arrageComponantsInColoumn(txt_month,btn_year_down,txt_year,btn_year_up),layoutUtils.getNextRowConstaints());
        //		 this.add(tbl_scroll,layoutUtils.getNextRowConstaints());
        //		 this.add(new ButtonController(),layoutUtils.getNextRowConstaints());

        this.add(LayoutUtils.arrangeComponantsInRow(((ControlPanel) LayoutUtils.arrangeComponantsInColoumn(null, true,
                ControlPanel.HORIZONTAL_FULL, lbl_month, txt_month, lbl_Year, txt_year,
                LayoutUtils.arrangeComponantsInRow(null, true, ControlPanel.TWENTY_PERCENT, btn_year_up, btn_year_down)
                        .setExpandable(false).setExpandPolicy(ControlPanel.HALF))).setExpandable(true)
                .setExpandPolicy(ControlPanel.HORIZONTAL_FULL), LayoutUtils.arrangeComponantsInRow(tbl_scroll).setExpandable(true)
                .setExpandPolicy(ControlPanel.BOTH), new ButtonController()));

    }

    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new CalenderModel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setVisible(true);
    }
}
