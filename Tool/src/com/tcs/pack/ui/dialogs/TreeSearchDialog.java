/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.ui.dialogs;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

import com.tcs.pack.search.ui.ControlPanel;
import com.tcs.pack.utils.DialogUtils;
import com.tcs.pack.utils.LayoutUtils;

public class TreeSearchDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private JTree tree;
	private JComboBox<String> searchString;
	private Vector<String> searchStringList = new Vector<String>();
	private JButton filter, findNext, close, clear;
	private DefaultMutableTreeNode rootNode;
	private DefaultTreeModel treeModel;

	private JLabel status;

	public TreeSearchDialog(JTree tree, Window parent) {
		super(parent);
		this.tree = tree;

		{

			if (tree == null)
				return;
			TreeModel treeModel = tree.getModel();
			if (treeModel instanceof DefaultTreeModel) {
				this.treeModel = (DefaultTreeModel) treeModel;
				Object rootObj = treeModel.getRoot();
				if (rootObj instanceof DefaultMutableTreeNode) {
					rootNode = (DefaultMutableTreeNode) rootObj;
				}
			}

		}
		init();
		initActions();
		this.setModal(true);
	}
	

	/**
	 * 
	 */
	private void initActions() {
		findNext.addActionListener(new FindNextHandler());
		filter.addActionListener(new FilterOrSearchHandler(this.tree));
		clear.addActionListener(new ClearFilterHandler(this.tree));
		close.addActionListener(new CloseHandler(this));
	}

	class CloseHandler implements ActionListener{

		/**
		 * @param dialog
		 */
		private JDialog dialog;
		public CloseHandler(JDialog dialog) {
			this.dialog = dialog;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			dialog.setVisible(false);
		}
		
	}
	class FindNextHandler implements ActionListener {

		private ArrayList<String> list = new ArrayList<String>();
		private String prevSearch="";
		private DefaultTreeModel treeModel;
		private DefaultMutableTreeNode rootNode;
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void update(){
			if (tree == null)
				return;
			TreeModel treeModel = tree.getModel();
			if (treeModel instanceof DefaultTreeModel) {
				this.treeModel = (DefaultTreeModel) treeModel;
				Object rootObj = treeModel.getRoot();
				if (rootObj instanceof DefaultMutableTreeNode) {
					this.rootNode = (DefaultMutableTreeNode) rootObj;
				}
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			update();
			String stringToSearch = searchString.getSelectedItem().toString().toLowerCase();
			if(!stringToSearch.endsWith(prevSearch)){
				list.clear();
			}
			DefaultMutableTreeNode node = findFirstNodeWithName(this.rootNode, stringToSearch);
			if(node!=null){
				TreeNode[] path = this.treeModel.getPathToRoot(node);
				TreePath selectionPath = new TreePath(path);
				list.add(node.toString());
				tree.setExpandsSelectedPaths(true);
				tree.setSelectionPath(selectionPath);				
			}
			prevSearch = stringToSearch;

		}
		
		public DefaultMutableTreeNode findFirstNodeWithName(DefaultMutableTreeNode parentNode,String strToFind){
			DefaultMutableTreeNode node=null;
			if(parentNode.toString().toLowerCase().contains(strToFind)){
				return parentNode;
			}
			for(int i =0;i<parentNode.getChildCount();i++){
				node = findFirstNodeWithName((DefaultMutableTreeNode) parentNode.getChildAt(i), strToFind);
				if(node!=null&& !list.contains(node.toString())){
					break;
				}
			}
			return node;
		}

	}
	class ClearFilterHandler implements ActionListener {

		/**
		 * @param tree
		 */

		public ClearFilterHandler(JTree tree) {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			treeModel.setRoot(rootNode);
			treeModel.reload();
		}

	}

	class FilterOrSearchHandler implements ActionListener {

		private DefaultMutableTreeNode newRootNode = new DefaultMutableTreeNode("Results");
		/**
		 * 
		 */
		public FilterOrSearchHandler(JTree tree) {
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			newRootNode = new DefaultMutableTreeNode("Results");
			if (rootNode == null)
				return;
			List<String> list = new ArrayList<String>();
			String stringToSearch = searchString.getSelectedItem().toString().toLowerCase();
//			TreeNode firstObj = rootNode.getFirstChild();
			for(int i=0;i<rootNode.getChildCount();i++){
				DefaultMutableTreeNode childNode=(DefaultMutableTreeNode) rootNode.getChildAt(i);
				searchNode(stringToSearch, childNode,rootNode, list);
			}
			/*if (firstObj instanceof DefaultMutableTreeNode) {
				DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) firstObj;
				
			}*/
			treeModel.setRoot(newRootNode);
			treeModel.reload();
		}
		public DefaultMutableTreeNode cloneTreeNode(DefaultMutableTreeNode clonableNode){
			DefaultMutableTreeNode node=null;
				if(clonableNode!=null){
					node = (DefaultMutableTreeNode) clonableNode.clone();
					for(int i=0;i<clonableNode.getChildCount();i++){
						node.add(cloneTreeNode((DefaultMutableTreeNode) clonableNode.getChildAt(i)));
					}
				}
			return node;
		}

		/**
		 * @param stringToSearch
		 * @param childNode
		 */
		private void searchNode(String stringToSearch, DefaultMutableTreeNode childNode,DefaultMutableTreeNode parentNode, List<String> list) {
			if (childNode.toString().toLowerCase().contains(stringToSearch)) {
				DefaultMutableTreeNode nodeToadd = (DefaultMutableTreeNode) findParentToadd(childNode,parentNode);
				if (nodeToadd !=null &&!list.contains(nodeToadd.toString())) {
					newRootNode.add(cloneTreeNode(nodeToadd));
					list.add(nodeToadd.toString());
				}

			} else {
				for(int i=0;i<childNode.getChildCount();i++){
					DefaultMutableTreeNode _child=(DefaultMutableTreeNode) childNode.getChildAt(i);
					searchNode(stringToSearch, _child,childNode, list);
				}
//				if(childNode.getChildCount()>0)
				
			}
			/*if (childNode.getNextSibling() != null) {
				searchNode(stringToSearch, childNode.getNextSibling(), parentNode,list);
			}*/
		}

		private MutableTreeNode findParentToadd(DefaultMutableTreeNode node,DefaultMutableTreeNode parent) {
			if (parent.equals(rootNode)) {
				return node;
			} else {
				return findParentToadd(parent,(DefaultMutableTreeNode) parent.getParent());
			}
		}
	}

	public void init() {
		DefaultComboBoxModel<String> searchModel = new DefaultComboBoxModel<String>(searchStringList);
		searchString = new JComboBox<String>(searchModel);
		searchString.setEditable(true);
		findNext = new JButton("Find Next");
		filter = new JButton("Filter");
		clear = new JButton("Clear");
		close = new JButton("Close");
		status = new JLabel(" ");

		this.add(LayoutUtils
				.arrangeComponantsInRow(true, ControlPanel.HORIZONTAL_FULL,
						LayoutUtils.arrangeComponantsInRow("Search String", searchString).setExpandable(true)
								.setExpandPolicy(ControlPanel.HORIZONTAL_FULL),
						LayoutUtils.arrangeComponantsInColoumn(findNext, filter, clear, close).setExpandable(true)
								.setExpandPolicy(ControlPanel.HORIZONTAL_FULL),
						new JSeparator(), status)
				.setExpandable(true).setExpandPolicy(ControlPanel.BOTH));

		DialogUtils.addHideOnEscapeListener(this);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setSize(600, 150);
		DialogUtils.setCenterLocation(this);
	}

	public static void main(String args[]) {
		final TreeSearchDialog dialog = new TreeSearchDialog(null, null);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				dialog.setVisible(true);
			}
		});
	}
}
