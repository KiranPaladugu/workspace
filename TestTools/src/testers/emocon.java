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
package testers;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class emocon extends JFrame implements ItemListener {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    JPanel row1 = new JPanel();
	JComboBox<String> choose = new JComboBox<String>();
	JPanel row2 = new JPanel();
	JTextField text = new JTextField(10);
	// image will be displayed here
	JPanel row3 = new JPanel();
	JLabel pic = new JLabel();

	// Images
	ImageIcon happy = new ImageIcon("images/happy.gif");
	ImageIcon lol = new ImageIcon("images/lol.gif");
	ImageIcon winky = new ImageIcon("images/winky.gif");
	ImageIcon sad = new ImageIcon("images/sad.gif");
	ImageIcon worried = new ImageIcon("images/worried.gif");
	ImageIcon angry = new ImageIcon("images/angry.gif");
	ImageIcon shock = new ImageIcon("images/shock.gif");
	ImageIcon uninpressed = new ImageIcon("images/uninpressed.gif");
	ImageIcon yawn = new ImageIcon("images/yawn.gif");
	ImageIcon evil = new ImageIcon("images/evil.gif");

	public emocon() {
		setTitle("Emoticon Converter");
		setSize(350, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		GridLayout two = new GridLayout(3, 1);
		setLayout(two);
		choose.addItem("Happy");
		choose.addItem("LOL");
		choose.addItem("Winky");
		choose.addItem("Sad");
		choose.addItem("Worried");
		choose.addItem("Angry");
		choose.addItem("Shock");
		choose.addItem("Uninpressed");
		choose.addItem("Yawn");
		choose.addItem("Evil");
		choose.addItemListener(this);
		row1.add(choose);
		row2.add(text);
		row3.add(pic);
		add(row1);
		add(row2);
		add(row3);

	}

	@Override
	public void itemStateChanged(ItemEvent item) {
		Object source = item.getSource();
		String emo = source.toString();
		if (emo == "Sad") {
			text.setText("hjgjhg");
		}
	}

	public static void main(String[] args) {
		new emocon();
	}
}