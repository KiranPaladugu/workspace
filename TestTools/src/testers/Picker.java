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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

class Picker {
public static void main(String[] args) {
      JLabel label = new JLabel("Selected Date:");
      final JTextField text = new JTextField(20);
      JButton b = new JButton("popup");
      JPanel p = new JPanel();
      p.add(label);
      p.add(text);
      p.add(b);
      final JFrame f = new JFrame();
      f.getContentPane().add(p);
      f.pack();
      f.setVisible(true);
      b.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent ae) {
                      text.setText(new DatePicker(f).setPickedDate());
              }
      });
}

}