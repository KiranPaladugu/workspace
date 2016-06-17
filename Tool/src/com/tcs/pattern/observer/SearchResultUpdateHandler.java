package com.tcs.pattern.observer;

import java.util.*;
import java.util.Observer;
import java.util.concurrent.Semaphore;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.tcs.pack.search.ui.SearchTreeNode;
import com.tcs.pack.searchJar.*;

public class SearchResultUpdateHandler implements Observer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	/**
	 * 
	 */
	private DefaultMutableTreeNode rootNode;
	private JTree tree;
	private Semaphore semaphore = new Semaphore(1);
	private List<Message> errors;

	public SearchResultUpdateHandler(DefaultMutableTreeNode rootNode, JTree tree, List<Message> errors) {
		this.rootNode = rootNode;
		this.tree = tree;
		this.errors = errors;
	}


	@Override
	public void update(Observable observableObj, final Object change) {
		/*Thread update = new Thread(new Runnable() {
			@Override
			public void run() {
				updateResult(change);
			}
		});
		update.start();*/
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				updateResult(change);
				
			}
		});
		
//		updateResult(change);

	}

	/**
	 * @param change
	 */
	private void updateResult(Object change) {
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			// Avoid unsafe excecution..?
			e.printStackTrace();
		}
		try {
			synchronized (rootNode) {
				if (rootNode != null && change != null && change instanceof SearchResult) {
					SearchResult result = (SearchResult) change;					
					if (!result.isDummy()) {
						SearchTreeNode node = new SearchTreeNode(result);
						List<Result> founds = result.getAllResults();
						for (Result found : founds) {
							SearchTreeNode nn = new SearchTreeNode(found);
							node.add(nn);
						}
						if (!result.getSearchResults().isEmpty()) {
							buildResultTree(result.getSearchResults(), node);
						}
						rootNode.add(node);
						updateTree();
						if (!rootNode.getUserObject().equals("Results")) {
							rootNode.setUserObject("Results");
						}
					}
					if (result.getErrors().size() > 0) {
						for (Message msg : result.getErrors()) {
							errors.add(msg);
						}

					}
				}
			}
		} finally {
			if (semaphore.availablePermits() <= 0) {
				semaphore.release();
			}
		}
	}

	/**
	 * 
	 */
	private void updateTree() {
		((DefaultTreeModel) tree.getModel()).reload();
		tree.repaint();
		tree.expandRow(0);
	}

	private void buildResultTree(List<SearchResult> results, SearchTreeNode rootNode) {
		for (SearchResult result : results) {
			if (result != null && !result.isDummy()) {
				SearchTreeNode node = new SearchTreeNode(result);
				List<Result> founds = result.getAllResults();
				for (Result found : founds) {
					SearchTreeNode nn = new SearchTreeNode(found);
					node.add(nn);
				}
				if (!result.getSearchResults().isEmpty()) {
					buildResultTree(result.getSearchResults(), node);
				}
				rootNode.add(node);
				updateTree();

			}
		}
	}

}
