package com.tcs.pack.windows;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.UndoManager;

import com.tcs.pack.search.ui.*;
import com.tcs.pack.searchJar.*;
import com.tcs.pack.ui.dialogs.FindTextDialog;
import com.tcs.pack.ui.popup.TextViewerMenuBar;
import com.tcs.pack.ui.popup.TextViewerPopup;
import com.tcs.pack.utils.EditorUtils;
import com.tcs.pack.utils.LayoutUtils;

public class TextViewWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextPane viewPane = new JTextPane();
	private TextViewerMenuBar menubar = new TextViewerMenuBar(this);
	private Result result;
	private JLabel statusBar = new JLabel(" ");
	private JDialog findDialog = null;
	private UndoManager undoMan = new UndoManager();

	public JDialog getFindDialog() {
		return findDialog;
	}

	public void setFindDialog(JDialog findDialog) {
		this.findDialog = findDialog;
	}

	public JLabel getStatusBar() {
		return statusBar;
	}

	public void setStatusBar(JLabel statusBar) {
		this.statusBar = statusBar;
	}

	private SearchTreeNode treeNode;
	private boolean saveRequired;
	private TextViewWindow window;
	private File fileToSave;

	public TextViewWindow() {
		super();
		initDisplay();
	}

	public TextViewWindow(SearchTreeNode treeNode) {
		super();
		initDisplay();
		this.treeNode = treeNode;
		prepareAndDisplayContent();
	}

	/**
	 * 
	 */
	private void prepareAndDisplayContent() {
		if (treeNode.getChildCount() == 0) {
			if (treeNode != null && treeNode.getObject() instanceof Result) {
				Result res = (Result) treeNode.getObject();
				if (res == null) {
					return;
				}
				this.result = res;
				if (JarSearch.isBinaryFile(res.getName())) {
					int input = JOptionPane.showConfirmDialog(this,
							"Selected file is a binary file, which contain binary data.\n Do you still want to open the file ?",
							"Binary file selected", JOptionPane.YES_NO_OPTION);
					if (input == JOptionPane.NO_OPTION) {
						this.dispose();
						return;
					}
					this.viewPane.setEditable(false);
					MouseListener listeners[] = viewPane.getMouseListeners();
					for (MouseListener listener : listeners) {
						if (listener instanceof CommonMouseAdaptor) {
							viewPane.removeMouseListener(listener);
							break;
						}
					}
					statusBar.setText("Disaplaying Non-editable data.");
				} else if (res.isDirectory()) {
					JOptionPane.showMessageDialog(window, "Sorry! I cannot open direcory in editor.\nPress OK to return", "Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					this.setJMenuBar(menubar);
				}
				String jarName = getJarNameFromParent(treeNode);
				if (jarName != null) {
//					result.setParent(jarName);
					JarReader reader = new JarReader(jarName);
					this.showContent(reader.getContent(res.getValue()), res.getName());
				}
				menubar.setEditableResult(result);
				menubar.setJTextComponant(viewPane);
			} else {
				JOptionPane.showMessageDialog(this, "Operation not applicable" + treeNode.getUserObject().getClass(), "No Operation Defined",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		viewPane.getDocument().addUndoableEditListener(undoMan);
		this.viewPane.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (!isSaveRequired()) {
					setSaveRequired(true);
					window.setTitle("* " + window.getTitle());
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (!isSaveRequired()) {
					setSaveRequired(true);
					window.setTitle("* " + window.getTitle());
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if (!isSaveRequired()) {
					setSaveRequired(true);
					window.setTitle("* " + window.getTitle());
				}
			}
		});

		this.setVisible(true);
	}

	/**
	 * @param node
	 * @return
	 */
	private String getJarNameFromParent(SearchTreeNode node) {
		if (node.getParent() != null && (node.getParent() instanceof SearchTreeNode)) {
			return getJarNameFromParent((SearchTreeNode) node.getParent());
		} else if (!(node.getParent() instanceof SearchTreeNode)) {
			return ((Result) (node).getObject()).getName();
		}
		return null;
	}

	/**
	 * 
	 */
	private void initDisplay() {
		setWindow(this);
		this.init();
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(800, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		findDialog = new FindTextDialog(viewPane, this);
		this.viewPane.addMouseListener(new CommonMouseAdaptor(new TextViewerPopup(viewPane, this)));

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				performExitOperation();
			}
		});

		this.viewPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getModifiers() == KeyEvent.CTRL_MASK) {
					if ( e.getKeyCode() == KeyEvent.VK_F) {
						performFindOperation();
					} else if ( e.getKeyCode() == KeyEvent.VK_S) {
//						window.performSaveOperation();
					} else if ( e.getKeyCode() == KeyEvent.VK_Z) {
						window.performUndoOperation();
					} else if ( e.getKeyCode() == KeyEvent.VK_Y) {
						window.performRedoOperation();
					}
				}
			}
		});
	}

	/**
	 * 
	 */
	public synchronized void performExitOperation() {
		if (isSaveRequired()) {
			int x = JOptionPane.showConfirmDialog(viewPane, "Content has changed do you want to Save ?", "Confirmation", JOptionPane.YES_NO_OPTION);
			if (x == JOptionPane.YES_OPTION) {
				performSaveOperation();
			}
		}
		window.dispose();
	}

	/**
	 * 
	 */
	public synchronized void performFindOperation() {
		if (findDialog != null) {
			findDialog.setVisible(true);
		} else {
			findDialog = new FindTextDialog(viewPane, window);
		}
	}

	/**
	 * 
	 */
	public synchronized void performSaveOperation() {
		fileToSave = EditorUtils.saveOperation(window, result, viewPane.getText(), fileToSave, JarSearch.getFileName(result.getParent().getName()));
		if (fileToSave != null) {
			statusBar.setText("File Saved @ " + fileToSave.getAbsolutePath());
			saveRequired = false;
		} else
			statusBar.setText("Saving file Failed for " + result.getValue());
	}

	public synchronized void performSaveAsOperation() {
		fileToSave = EditorUtils.saveOperation(window, result, viewPane.getText(), null, null);
		if (fileToSave != null) {
			statusBar.setText("File Saved @ " + fileToSave.getAbsolutePath());
			saveRequired = false;
		} else
			statusBar.setText("Saving file Failed for " + result.getValue());
	}

	public synchronized void performUndoOperation() {
		if (undoMan.canUndo()) {
			undoMan.undo();
			statusBar.setText("EDIT : UNDO");
		}
	}

	public synchronized void performRedoOperation() {
		if (undoMan.canRedo()) {
			undoMan.redo();
			statusBar.setText("EDIT : REDO");
		}
	}

	public synchronized void performPasteOperation() {
		viewPane.paste();
	}

	public JTextPane getTextView() {
		return this.viewPane;
	}

	public TextViewWindow setWindowTitle(String title) {
		this.setTitle(title);
		return this;
	}

	public TextViewWindow init() {
		this.setLayout(new GridLayout(1, 1));
		JScrollPane scrollPane = new JScrollPane(viewPane);
		statusBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.add(LayoutUtils.arrangeComponantsInRow(true, ControlPanel.BOTH, scrollPane, statusBar).setExpandable(true)
				.setExpandPolicy(ControlPanel.BOTH));
		return this;
	}

	public void setContent(String content) {
		this.viewPane.setText(content);
	}

	public void addContent(String content) {
		getTextView().setText(this.viewPane.getText() + content);
	}

	public TextViewWindow showContent(String content) {
		this.getTextView().setText(content);
		return this;
	}

	public TextViewWindow showErrorContent(String content) {
		this.getTextView().setText(content);
		return this;
	}

	public void showContent(String content, String title) {
		this.getTextView().setText(content);
		this.setTitle(title);
	}

	/**
	 * @return the saveRequired
	 */
	public boolean isSaveRequired() {
		return saveRequired;
	}

	/**
	 * @param saveRequired
	 *            the saveRequired to set
	 */
	public void setSaveRequired(boolean saveRequired) {
		this.saveRequired = saveRequired;
	}

	/**
	 * @return the fileToSave
	 */
	public File getFileToSave() {
		return fileToSave;
	}

	/**
	 * @param fileToSave
	 *            the fileToSave to set
	 */
	public void setFileToSave(File fileToSave) {
		this.fileToSave = fileToSave;
	}

	/**
	 * @return the window
	 */
	public TextViewWindow getWindow() {
		return window;
	}

	/**
	 * @param window
	 *            the window to set
	 */
	public void setWindow(TextViewWindow window) {
		this.window = window;
	}

	/**
	 * 
	 */
	public synchronized void performCutOperation() {
		viewPane.cut();
	}

}
