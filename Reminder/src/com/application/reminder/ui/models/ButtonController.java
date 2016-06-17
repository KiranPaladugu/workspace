
package com.application.reminder.ui.models;

import java.awt.Window;

import javax.swing.JButton;

public class ButtonController extends ButtonControlPanel{
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JButton ok,cancel,close,stop;
	public static int OK_BUTTON=1;
	public static int CANCEL_BUTTON=0;
	public static int CLOSE_BUTTON=-1;
	
	public JButton getStop() {
		return stop;
	}

	public void setStop(JButton stop) {
		this.stop = stop;
	}

	/**
	 * 
	 */
	public ButtonController() {
		ok = new JButton("OK");
		cancel = new JButton("Cancel");
		close = new JButton("Close");
		stop =new JButton("Stop");
		this.add(ok);
		this.add(cancel);
		this.add(stop);
		this.add(close);
		
	}
	
	public static ButtonControlPanel getButtonController(Window parent,int type){
		return new ButtonControlPanel();
	}

	public JButton getOk() {
		return ok;
	}

	public void setOk(JButton ok) {
		this.ok = ok;
	}

	public JButton getCancel() {
		return cancel;
	}

	public void setCancel(JButton cancel) {
		this.cancel = cancel;
	}

	public JButton getClose() {
		return close;
	}

	public void setClose(JButton close) {
		this.close = close;
	}
	
}
