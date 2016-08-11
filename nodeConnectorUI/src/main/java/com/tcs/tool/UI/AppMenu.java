/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.tcs.application.Application;
import com.tcs.application.Subscriber;
import com.tcs.application.SubscriptionEvent;
import com.tcs.tools.UI.utils.UIConstants;

public class AppMenu extends JMenuBar implements Subscriber {
	private static final long serialVersionUID = 1L;
	private final SMenu file, options, history, help;
	private final SMenuItem exit, clear, credits;
	private final List<JMenu> menus = new Vector<>();

	public AppMenu() {

		file = new SMenu("File");
		exit = new SMenuItem();
		history = new SMenu("History");
		clear = new SMenuItem();
		credits = new SMenuItem();
		options = new SMenu("Options");
		help = new SMenu("Help");
		menus.add(file);
		menus.add(options);
		menus.add(history);
		menus.add(help);
		init();
		Application.getSubscriptionManager().subscribe("exit", exit);
		Application.getSubscriptionManager().subscribe(this, UIConstants.ADD_NEW_MENU, UIConstants.ADD_NEW_MENU_ITEM);
	}

	private void init() {
		exit.setText("Exit");
		clear.setText("Clear History");
		credits.setText("Credits");
		new CreditsDialog();

		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				Application.getSubscriptionManager().notifySubscriber("exit", exit);
			}
		});

		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				Application.getSubscriptionManager().notifySubscriber(UIConstants.CLEAR_HISTORY, e.getSource());
			}
		});

		credits.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				Application.getSubscriptionManager().notifySubscriber(CreditsDialog.CREDITS_SHOW);
			}
		});

		file.add(exit);
		history.add(clear);
		help.add(credits);
		this.add(file);
		this.add(history);
		this.add(options);
		this.add(help);
	}

	@Override
	public synchronized void onSubscriptionEvent(final SubscriptionEvent event) throws Exception {
		final Object obj = event.getData();
		final String caller = event.getCaller().getClassName();
		switch (event.getEvent()) {
		case UIConstants.ADD_NEW_MENU:
			if (obj instanceof JMenu) {
				if (!menus.contains(obj)) {
					this.add((JMenu) obj);
					this.menus.add((JMenu) obj);
					sendOk(caller, event.getEvent());
				}
			} else if (obj instanceof Map<?, ?>) {
				updateMenuAndMenuItem((Map<String, JMenuItem>) obj);
				sendOk(caller, event.getEvent());
			}
			break;
		case UIConstants.ADD_NEW_MENU_ITEM:
			if (obj instanceof Map<?, ?>) {
				updateMenuAndMenuItem((Map<String, JMenuItem>) obj);
				sendOk(caller, event.getEvent());
			}
			break;
		default:
			break;
		}
	}

	private void updateMenuAndMenuItem(final Map<String, JMenuItem> map) {
		for (final Entry<String, JMenuItem> entry : map.entrySet()) {
			final String menuName = entry.getKey();
			final JMenuItem item = entry.getValue();
			final JMenu menu = getMenu(menuName.trim());
			if (menu != null && item != null) {
				menu.add(item);
				if (item instanceof JMenu) {
					this.menus.add((JMenu) item);
				}
			}
		}
	}

	private JMenu getMenu(final String menuName) {
		for (final JMenu menu : menus) {
			if (menu.getText().trim().equals(menuName)) {
				return menu;
			}
		}
		return null;
	}

	private void sendOk(final String caller, final String string) {
		Application.getSubscriptionManager().notifySubscriber(caller + "." + string + UIConstants.SVC_RESPONSE, this);
	}
}
