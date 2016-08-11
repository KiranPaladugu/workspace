package com.tcs.tool.UI;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.tcs.application.Application;

public class ListItemComponent<E> extends JComboBox<E> {

	private static final long serialVersionUID = 1L;
	private final Vector<E> items;
	private final DefaultComboBoxModel<E> model;
	private String confFileName;
	private boolean updated;

	public ListItemComponent() {
		items = new Vector<>();
		model = new DefaultComboBoxModel<>(items);
		this.setModel(model);
	}

	public ListItemComponent(final Collection<E> items) {
		this.items = new Vector<>(items);
		model = new DefaultComboBoxModel<>(this.items);
		this.setModel(model);
	}

	public void addListItems(final Collection<E> items) {
		for (final E e : items) {
			if (!items.contains(e)) {
				model.addElement(e);
				updated = true;
			}
		}
		this.repaint();
	}

	public void addListItem(final E item) {
		if (!items.contains(item)) {
			model.addElement(item);
			updated = true;
			repaint();
		}
	}

	public void removeListItem(final E item) {
		model.removeElement(item);
		repaint();
	}

	public void clearList() {
		model.removeAllElements();
		repaint();
	}

	@SuppressWarnings("unchecked")
	public boolean update() {
		E item = (E) this.getSelectedItem();
		if (item instanceof String) {
			item = (E) ((String) item).trim();
		}
		if (!items.contains(item)) {
			model.addElement(item);
			this.setSelectedIndex(items.size() - 1);
			updated = true;
			return true;
		}
		return false;
	}

	public String getConfFileName() {
		return confFileName;
	}

	public void setConfFileName(final String confFileName) {
		this.confFileName = confFileName;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Only works if Entities are of type String
	 *
	 * @return
	 */
	public boolean writeAsStringConfFile() {
		if (confFileName != null && !"".equals(confFileName) && updated) {
			try {
				Application.getConfigurationManager().writeStringConfiguration(confFileName, getListAsData((List<String>) items));
				updated = false;
				return true;
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean writeAsObjectConfFile() {
		if (confFileName != null && !"".equals(confFileName) && updated) {
			try {
				Application.getConfigurationManager().writeConfigurationAsObject(confFileName, items);
				updated = false;
				return true;
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	private String getListAsData(final List<String> list) {
		if (list.isEmpty()) {
			return null;
		}
		final StringBuffer buffer = new StringBuffer();
		for (final String str : list) {
			buffer.append(str + "\n");
		}
		return buffer.toString();
	}
}
