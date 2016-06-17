package com.tcs.pack.ui.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.tree.TreePath;

import com.tcs.pack.search.ui.SearchTreeNode;
import com.tcs.pack.searchJar.*;
import com.tcs.pack.serarch.Mainwindow;
import com.tcs.pack.utils.EditorUtils;
import com.tcs.pack.windows.JarExplorer;

public class TreePopUp extends JPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuItem copyName, copyJavaPackageName, copyRelativePathName,find, add2Copy,showInJarExplorer;
	private JTree tree;
	private Mainwindow parent;

	/**
	 * 
	 */
	public TreePopUp(JTree tree,Mainwindow parent) {
		this.tree = tree;
		this.parent = parent;
		initDisplay();
	}

	/**
	 * 
	 */
	private void initDisplay() {
		copyName = new JMenuItem("Copy File Name  (\u04BF)");
		copyName.setToolTipText("Copies Name of the file - [Experimental]");
		copyJavaPackageName = new JMenuItem("Copy java packaged name");
		copyJavaPackageName.setToolTipText("java package format");
		copyRelativePathName = new JMenuItem("Copy Relative path Name");
		copyRelativePathName.setToolTipText("Relative path to the parent jar");	
		showInJarExplorer = new JMenuItem("Show In Jar Explorer");
		find = new JMenuItem("Find");
		find.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.perfromTreeSearch();
				
			}
		});
		add2Copy = new JMenuItem("Add2Copy  (\u04BF)");
		add2Copy.setToolTipText("Appends at the end of copied content of Clipboard [Experimental]");
		copyName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StringBuilder data = new StringBuilder("");
				TreePath[] paths = tree.getSelectionPaths();
				for (TreePath path : paths) {
					System.out.println(path.getLastPathComponent().getClass());
					if (path.getLastPathComponent() instanceof SearchTreeNode) {
						SearchTreeNode node = (SearchTreeNode) path.getLastPathComponent();
						if (node.getUserObject() instanceof SearchResult) {
							data.append(JarSearch.getFileName((node.getUserObject().toString())) + "\n");
						} else if (node.getUserObject() instanceof Result) {
							Result result = (Result) node.getUserObject();
							data.append(JarSearch.getFileName(result.getValue()) + "\n");
						}
					}
				}
				EditorUtils.copyOperation(data.toString());
			}
		});
		copyJavaPackageName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StringBuilder data = new StringBuilder("");
				TreePath[] paths = tree.getSelectionPaths();
				for (TreePath path : paths) {
					System.out.println(path.getLastPathComponent().getClass());
					if (path.getLastPathComponent() instanceof SearchTreeNode) {
						SearchTreeNode node = (SearchTreeNode) path.getLastPathComponent();
						if (node.getUserObject() instanceof SearchResult) {
							data.append(node.getUserObject().toString() + "\n");
						} else if (node.getUserObject() instanceof Result) {
							Result result = (Result) node.getUserObject();
							data.append(JarSearch.buildJavaPackageName(result.getValue()) + "\n");
						}
					}
				}
				EditorUtils.copyOperation(data.toString());
			}
		});

		copyRelativePathName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StringBuilder data = new StringBuilder("");
				TreePath[] paths = tree.getSelectionPaths();
				for (TreePath path : paths) {
					System.out.println(path.getLastPathComponent().getClass());
					if (path.getLastPathComponent() instanceof SearchTreeNode) {
						SearchTreeNode node = (SearchTreeNode) path.getLastPathComponent();
						if (node.getUserObject() instanceof SearchResult) {
							data.append(node.getUserObject().toString() + "\n");
						} else if (node.getUserObject() instanceof Result) {
							Result result = (Result) node.getUserObject();
							data.append(result.getValue() + "\n");
						}
					}
				}
				EditorUtils.copyOperation(data.toString());
			}
		});

		this.add2Copy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StringBuilder data = new StringBuilder("");
				TreePath[] paths = tree.getSelectionPaths();
				for (TreePath path : paths) {
					System.out.println(path.getLastPathComponent().getClass());
					if (path.getLastPathComponent() instanceof SearchTreeNode) {
						SearchTreeNode node = (SearchTreeNode) path.getLastPathComponent();
						if (node.getUserObject() instanceof Result) {
							Result result = (Result) node.getUserObject();
							data.append(result.getName() + "\n");
						}
					}
				}
				EditorUtils.add2CopyOperation(data.toString());
			}
		});
		
		this.showInJarExplorer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Explorer(tree);
			}
		});
		
		
		
		this.add(copyName);
		this.add(copyJavaPackageName);
		this.add(copyRelativePathName);
		this.add(new JSeparator());
		this.add(find);
		this.add(new JSeparator());
		this.add(add2Copy);
		this.addSeparator();
		this.add(this.showInJarExplorer);

	}
	static class Explorer implements Runnable{

		private JTree _tree ;
		private static int count=1;
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		/**
		 * 
		 */
		public Explorer(JTree tree) {
			this._tree = tree;
			Thread t = new Thread(this);
			t.setName("[Explorer Thread -"+(count++)+"]");
			t.start();
			
		}
		@Override
		public void run() {
			
			StringBuilder data = new StringBuilder("");
			TreePath[] paths = _tree.getSelectionPaths();
			for (TreePath path : paths) {
				if (path.getLastPathComponent() instanceof SearchTreeNode) {
					SearchTreeNode node = (SearchTreeNode) path.getLastPathComponent();
					if (node.getUserObject() instanceof SearchResult) {
						data.append(node.getUserObject().toString());
					} else if (node.getUserObject() instanceof Result) {
						Result result = (Result) node.getUserObject();
						data.append(result.getValue());
					}
				}
			}
			new JarExplorer(data.toString(), true);
		}
		
	}
}
