/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.windows;

import java.util.List;

import javax.swing.*;
import javax.swing.tree.*;

import com.tcs.pack.listerners.TreeListener;
import com.tcs.pack.search.ui.ControlPanel;
import com.tcs.pack.search.ui.SearchTreeNode;
import com.tcs.pack.searchJar.JarContentExplorer;
import com.tcs.pack.searchJar.SearchResult;
import com.tcs.pack.utils.LayoutUtils;
import com.tcs.pack.utils.WindowUtils;

public class JarExplorer extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTree treeView;
	private JLabel statusBar;
	private String jarPath;
	
	/**
	 * 
	 */
	public JarExplorer(String pathOfJar,boolean isChild) {
		this.jarPath = pathOfJar;
		init();		
		initDisplay();
		treeView.addMouseListener(new TreeListener(treeView));
	}
	
	/**
	 * 
	 */
	private void initDisplay() {
		final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Explorer");
		final DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
		treeView.setModel(treeModel);
		JarContentExplorer explorer = new JarContentExplorer(jarPath);
		SearchResult result = explorer.getArchiveContents();
		if(!result.isDummy()){
			updateTree(result, rootNode);			
		}
		treeModel.reload();
		treeView.expandRow(1);
		this.setSize(600, 600);
		WindowUtils.setCenterLocation(this);
		this.setVisible(true);
	}
	
	private void updateTree(SearchResult result,DefaultMutableTreeNode parent){
		if(!result.isDummy()){
			SearchTreeNode node = new SearchTreeNode(result);
			parent.add(node);
			if(!result.getSearchResults().isEmpty()){
				updateTree(result.getSearchResults(), node);
			}
		}
	}

	/**
	 * @param searchResults
	 * @param node
	 */
	private void updateTree(List<SearchResult> searchResults, SearchTreeNode parent) {
		for(SearchResult result:searchResults){
			SearchTreeNode node = new SearchTreeNode(result);
			parent.add(node);
			if(!result.getSearchResults().isEmpty()){
				updateTree(result.getSearchResults(), node);
			}
		}
		
	}

	/**
	 * 
	 */
	public JarExplorer(String jarPath) {
		init();
	}

	/**
	 * 
	 */
	private void init() {
		treeView = new JTree();
		statusBar = new JLabel(" ");
		this.add(LayoutUtils.arrangeComponantsInRow(false, ControlPanel.BOTH,
				LayoutUtils.arrangeComponantsInRow(true, ControlPanel.BOTH, new JScrollPane(treeView)).setExpandable(true)
						.setExpandPolicy(ControlPanel.BOTH),
				new JSeparator(), LayoutUtils.arrangeComponantsInRow(false, ControlPanel.NONE, statusBar).setExpandable(false)));
		
		WindowUtils.addEscapeListener(this);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public static void main(String args[]){
		String jarFilePath = "C:\\Users\\ekirpal\\Desktop\\"
//				+ "netconf-filter-get-handler-jar-1.0.8-SNAPSHOT.jar";
		+"_DEFAULT__netconfEcimSyncNodeHandlerEar_netconf-ecim-sync-node-handler-ear-1.0.8.ear";
		JarExplorer exp = new JarExplorer(jarFilePath,false);
		exp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
}
