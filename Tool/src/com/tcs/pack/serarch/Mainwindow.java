package com.tcs.pack.serarch;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.tcs.pack.listerners.TreeListener;
import com.tcs.pack.search.ui.*;
import com.tcs.pack.searchJar.*;
import com.tcs.pack.ui.dialogs.TreeSearchDialog;
import com.tcs.pack.ui.popup.*;
import com.tcs.pack.utils.LayoutUtils;
import com.tcs.pack.windows.TextViewWindow;
import com.tcs.pattern.observer.SearchPathUpdateHandler;
import com.tcs.pattern.observer.SearchResultUpdateHandler;

public class Mainwindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lbl_searchStaus;
	private JCheckBox matchCase, contentSearch, wholeWord;
	private JComboBox<String> cmb_search, cmb_searchPath;
	private JButton browse;
	private JTree resultTree;
	private ButtonController controller = new ButtonController();
	private Mainwindow window;
	private Semaphore semaphore = new Semaphore(1);
	private boolean searchProgess;
	private static int searchThreadcount = 1;
	private Vector<String> searchStrs = new Vector<String>();
	private Vector<String> paths = new Vector<String>();
	private JarSearch currentSearch = null;
	private MainWindowMenuBar menubar = new MainWindowMenuBar(this);
	private TreeSearchDialog treeSearch;

	private Mainwindow() {
		window = this;
		init();

	}

	/**
	 * 
	 */
	private void init() {
		searchStrs.add("");
		paths.add("");
		// txt_search = new JTextField("");
		cmb_search = new JComboBox<String>();
		DefaultComboBoxModel<String> searchModel = new DefaultComboBoxModel<String>(searchStrs);
		cmb_search.setModel(searchModel);
		cmb_search.setEditable(true);
		cmb_search.setSelectedIndex(0);
		EditorPopup searchPopup = new EditorPopup((JTextField) cmb_search.getEditor().getEditorComponent());
		cmb_search.getEditor().getEditorComponent().addMouseListener(new CommonMouseAdaptor(searchPopup));

		// lbl_errors = new JLabel();
		// lbl_errors.setForeground(Color.RED);
		cmb_searchPath = new JComboBox<String>();
		matchCase = new JCheckBox("Match case");
		matchCase.setSelected(true);
		contentSearch = new JCheckBox("Content search");
		wholeWord = new JCheckBox("Whole word");

		DefaultComboBoxModel<String> pathModel = new DefaultComboBoxModel<String>(paths);
		cmb_searchPath.setModel(pathModel);
		cmb_searchPath.setEditable(true);
		cmb_searchPath.setSelectedIndex(0);

		EditorPopup searchPathPopup = new EditorPopup((JTextField) cmb_searchPath.getEditor().getEditorComponent());
		cmb_searchPath.getEditor().getEditorComponent().addMouseListener(new CommonMouseAdaptor(searchPathPopup));

		// txt_path = new JTextField("");
		resultTree = new JTree();
		final SearchTreeNode rootNode = new SearchTreeNode("Press Search to start search. Search results will be displayed here");
		final DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
		resultTree.setModel(treeModel);
		lbl_searchStaus = new JLabel("Click Search to start search with given inputs");
		browse = new JButton("Browse");

		ControlPanel inputBox = LayoutUtils.arrangeComponantsInColoumn(false, ControlPanel.HORIZONTAL_FULL,
				LayoutUtils.arrangeComponantsInRow(null, false, ControlPanel.HORIZONTAL_FULL,
						LayoutUtils.arrangeComponantsInRow("Search String", true, cmb_search).setExpandPolicy(ControlPanel.HORIZONTAL_FULL),
						LayoutUtils.arrangeComponantsInColoumn("Select Path", true, cmb_searchPath, browse)
								.setExpandPolicy(ControlPanel.HORIZONTAL_FULL)),
				LayoutUtils.arrangeComponantsInRow("Options", false, matchCase, contentSearch, wholeWord)
						.setExpandPolicy(ControlPanel.TWENTY_PERCENT));

		JPanel view = LayoutUtils.arrangeComponantsInRow(null, false, ControlPanel.HORIZONTAL_FULL,
				inputBox.setExpandPolicy(ControlPanel.HORIZONTAL_FULL),new JSeparator(), controller, new JSeparator(),
				LayoutUtils.arrangeComponantsInColoumn(new JScrollPane(resultTree)).setExpandPolicy(ControlPanel.BOTH), new JSeparator(),
				lbl_searchStaus);

		controller.getOk().addActionListener(new SearchHelper(resultTree, lbl_searchStaus));
		controller.getCancel().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					semaphore.acquire();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				cmb_search.setSelectedIndex(0);
				cmb_searchPath.setSelectedIndex(0);
				resultTree.removeAll();
				DefaultTreeModel model = (DefaultTreeModel) resultTree.getModel();
				((DefaultMutableTreeNode) model.getRoot()).removeAllChildren();
				((DefaultMutableTreeNode) (model.getRoot())).setUserObject("Press Search to start search. Search results will be displayed here");
				model.reload();
				resultTree.repaint();
				lbl_searchStaus.setText("Click Search to start search with given inputs");
				semaphore.release();

			}
		});
		
		resultTree.addKeyListener(new KeyAdapter() {
			/* (non-Javadoc)
			 * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getModifiers()== KeyEvent.CTRL_MASK && e.getKeyCode()==KeyEvent.VK_F){
					perfromTreeSearch();
				}
			}
		});
		
		controller.getClose().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				performExitOperation();
			}
		});

		controller.getStop().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (currentSearch != null) {
					synchronized (currentSearch) {
						currentSearch.setStop();
						lbl_searchStaus.setText("Stopping Search ... Please wait..");

					}
				}
			}
		});
		browse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int value = fileChooser.showOpenDialog(window);
				if (value == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					if (file == null) {
						file = fileChooser.getCurrentDirectory();
					}
					if (!paths.contains(file.getAbsolutePath().trim())) {
						((DefaultComboBoxModel<String>) cmb_searchPath.getModel()).addElement(file.getAbsolutePath().trim());
						cmb_searchPath.setSelectedIndex(paths.size() - 1);
					} else {
						((DefaultComboBoxModel<String>) cmb_searchPath.getModel()).setSelectedItem(file.getAbsolutePath().trim());
					}
				}
				// txt_path.setText(file.getAbsolutePath());
			}
		});

		MouseHoverAnimation hoverAnime = new MouseHoverAnimation(lbl_searchStaus);
		// controller.getOk().addMouseListener(hoverAnime);
		controller.getOk().setText("Search");
		controller.getCancel().setText("Clear");
		controller.getCancel().addMouseListener(hoverAnime);
		controller.getClose().addMouseListener(hoverAnime);
		browse.addMouseListener(hoverAnime);
		resultTree.addMouseListener(new TreeListener(resultTree));
		this.add(view);
		this.setJMenuBar(menubar);
		lbl_searchStaus.setText("Click Search to start search with given inputs");
		lbl_searchStaus.addMouseListener(new CommonMouseAdaptor(searchPathPopup));

		this.addWindowListener(new WindowAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				performExitOperation();
			}
		});
	}

	/**
	 * 
	 */
	public synchronized void performExitOperation() {
		boolean exit = true;
		if (searchProgess) {
			int value = JOptionPane.showConfirmDialog(window, "Search is in progress.. do you really want to exit ?", "Confirmation",
					JOptionPane.YES_NO_OPTION);
			if (value == JOptionPane.NO_OPTION) {
				exit = false;
			}else if(currentSearch!=null){
				currentSearch.setStop();
			}
		}
		if (exit) {
			window.setVisible(false);
			doHouseKeeping();
			System.exit(0);
		}
	}
	
	private void doHouseKeeping(){
		Application.CleanBeforeExit();
	}

	public synchronized void perfromTreeSearch() {
		if (treeSearch != null) {
			treeSearch.setVisible(true);
		}
	}

	public static void main(String args[]) {
		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			// javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}
		SwingUtilities.invokeLater(new StartUI());
	}

	/**
	 * @author ekirpal
	 *
	 */
	public final class SearchHelper implements ActionListener {
		/**
		 * 
		 */
		private DefaultMutableTreeNode rootNode;
		private JTree tree;
		private JLabel status;

		/**
		 * @param rootNode
		 * @param lbl_currentSearch
		 */
		public SearchHelper(JTree tree, JLabel lbl_currentSearch) {
			this.rootNode = new DefaultMutableTreeNode("Press Search to start search. Search results will be displayed here");
			this.tree = tree;
			tree.removeAll();
			tree.setModel(new DefaultTreeModel(rootNode));
			this.status = lbl_currentSearch;
			this.status.setText("Click Search to start search with given inputs");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			rootNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
			rootNode.removeAllChildren();
			((DefaultTreeModel) tree.getModel()).reload();
			tree.repaint();
			if (cmb_search.getSelectedIndex() == 0 || cmb_searchPath.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(null, "Input required... ", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				SearchThread thread = new SearchThread(rootNode);
				thread.start();
			}

		}

		public final class SearchThread extends Thread {
			private final DefaultMutableTreeNode rootNode;

			public SearchThread(DefaultMutableTreeNode rootNode2) {
				this.rootNode = rootNode2;
				this.setName("Search Thread - " + (searchThreadcount++));
			}

			public void run() {
				try {
					semaphore.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				controller.getOk().setEnabled(false);
				controller.getCancel().setEnabled(false);
				searchProgess = true;
				status.setText("Please wait while Searching....");
				// lbl_errors.setText("");
				currentSearch = new JarSearch("", "", matchCase.isSelected(), contentSearch.isSelected());
				String searchStr = (String) cmb_search.getModel().getSelectedItem();
				if (!searchStrs.contains(searchStr.trim())) {
					((DefaultComboBoxModel<String>) cmb_search.getModel()).addElement(searchStr.trim());
					cmb_search.setSelectedIndex(searchStrs.size() - 1);
				}
				String selectedPath = (String) cmb_searchPath.getModel().getSelectedItem();
				if (!paths.contains(selectedPath)) {
					((DefaultComboBoxModel<String>) cmb_searchPath.getModel()).addElement(selectedPath.trim());
					cmb_searchPath.setSelectedIndex(paths.size() - 1);
				} else {
					((DefaultComboBoxModel<String>) cmb_searchPath.getModel()).setSelectedItem(selectedPath);
				}
				currentSearch.addSearchPath((String) cmb_searchPath.getSelectedItem());
				currentSearch.setSearchString(searchStr.trim());
				currentSearch.setWholeWord(wholeWord.isSelected());
				currentSearch.setContentSearch(contentSearch.isSelected());
				List<Message> errors = new Vector<Message>();
				SearchResultUpdateHandler observer = new SearchResultUpdateHandler(rootNode, tree, errors);
				currentSearch.addSearchPathObserver(new SearchPathUpdateHandler(status));
				currentSearch.addResultObserver(observer);
				long start = System.currentTimeMillis();
				List<SearchResult> results = currentSearch.doSearch();
				// buildResultTree(results, rootNode);

				if (results.isEmpty()) {
					rootNode.setUserObject("0 Results found ! Nothing to display");
					((DefaultTreeModel) tree.getModel()).reload();
					tree.repaint();
				}
				long end = System.currentTimeMillis();
				String err = "";
				if (errors.size() > 0) {
					err = ", " + errors.size() + " errors occured.";
				}
				tree.setComponentPopupMenu(new TreePopUp(tree, window));
				status.setText("Search finished in " + getSearchTime(start, end) + " and found " + results.size() + " record(s)" + err);
				// lbl_errors.setText();
				controller.getOk().setEnabled(true);
				controller.getCancel().setEnabled(true);
				searchProgess = false;
				treeSearch = new TreeSearchDialog(resultTree, window);
				semaphore.release();
				if (window.getExtendedState() == JFrame.ICONIFIED) {
					window.setExtendedState(JFrame.NORMAL);
				}
				window.requestFocus();
				window.toFront();
				if (errors.size() > 0) {
					int option = JOptionPane.showConfirmDialog(window, "Some errors occured while searching\n Do you want to see erros ?",
							"Errors Occured", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION) {
						final StringBuilder builder = new StringBuilder();
						for (Message str : errors) {
							builder.append(str + "\n");
						}
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								TextViewWindow window = new TextViewWindow().showContent(builder.toString());
								window.setTitle("Errors List");
								window.setVisible(true);
							}
						});
					}
				}
				currentSearch = null;
			}

			/**
			 * @param start
			 * @param end
			 * @return
			 */
			private String getSearchTime(long start, long end) {
				String time = "0 Sec";
				double second = ((double) end - (double) start) / (double) 1000;
				if (second > 60) {
					int mins = (int) (second / 60);
					second = second % 60;
					if (mins > 60) {
						int hours = mins / 60;
						mins = mins % 60;
						time = hours + "Hours " + mins + "Min " + second + "Sec";
					} else {
						time = mins + "Min " + second + "Sec";
					}
				} else {
					time = second + " Sec";
				}
				return time;
			}

			/**
			 * @param results
			 */
			/*
			 * private void buildResultTree(List<SearchResult> results, SearchTreeNode rootNode) { for (SearchResult result : results) { if (result !=
			 * null) { SearchTreeNode node = new SearchTreeNode(result.getFile()); List<String> founds = result.getFoundFiles(); for (String found :
			 * founds) { SearchTreeNode nn = new SearchTreeNode(found); node.add(nn); } if (!result.getOtherResults().isEmpty()) {
			 * buildResultTree(result.getOtherResults(), node); } rootNode.add(node); } } }
			 */
		}
	}

	

	static class StartUI implements Runnable {
		public void run() {
			Mainwindow window = new Mainwindow();
			// window.setResizable(false);
//			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setSize(800, 630);
			window.setTitle("Jar Search...");
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			window.setLocation(dim.width / 2 - window.getSize().width / 2, dim.height / 2 - window.getSize().height / 2);
			window.setVisible(true);
		}
	}
}
