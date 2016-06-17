/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.searchJar;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import com.tcs.pattern.observer.FolderSearchObservable;
import com.tcs.pattern.observer.SearchResultHolder;

public class AbstractArchiveSearch {
	protected static String javaArchive[] = { ".jar",".ear", ".war" };
	protected static String readableFiles[] = { ".xml", ".properties", ".java", ".mf", ".txt" };
	protected static String binaryFiles[] = { ".class", ".jar", ".png", ".jpg", ".gif",".ear" };
	protected String searchPath = null;
	protected String searchString = null;
	protected List<String> searchPaths = new Vector<String>();
	protected SearchResultHolder resultHolder = new SearchResultHolder();
	protected boolean matchCase = false;
	protected boolean contentSearch = false;
	protected AtomicBoolean stop = new AtomicBoolean(false);
	protected boolean wholeWord = false;
	protected AbstractJarSearcher finder;
	protected FolderSearchObservable folderObserver = new FolderSearchObservable();
	
	public void addSearchPathObserver(Observer observer){
		folderObserver.addObserver(observer);
	}

	public void setWholeWord(boolean wholeWord) {
		this.wholeWord = wholeWord;
	}
	
	public void setSearchResultHolder(SearchResultHolder holder) {
		this.resultHolder = holder;
	}

	public SearchResultHolder getResultHolder() {
		return this.resultHolder;
	}

	public void addResultObserver(Observer observer) {
		if (resultHolder != null)
			this.resultHolder.addObserver(observer);
	}

	public String getPath() {
		return this.searchPath;
	}
	
	public void addSearchPath(String searchPath) {
		this.searchPath = searchPath;
		if (!searchPaths.contains(searchPath)) {
			searchPaths.add(searchPath);
		}
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchString() {
		return this.searchString;
	}
	
	public static boolean isJavaArchive(String name) {
		boolean flag = false;
		for (String arhive : JarSearch.javaArchive) {
			if (name.endsWith(arhive) || name.endsWith(arhive.toUpperCase())) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public static boolean isBinaryFile(String fileName) {
		boolean flag = false;
		for (String fileExt : JarSearch.binaryFiles) {
			if (fileName.endsWith(fileExt) || fileName.endsWith(fileExt.toUpperCase())) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public static boolean isValidFileForContentSearch(String entryName) {
		boolean flag = false;
		for (String arhive : JarSearch.readableFiles) {
			if (entryName.endsWith(arhive) || entryName.endsWith(arhive.toUpperCase())) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	

	public static synchronized String getFileName(String fullString) {
		String name = fullString;
		if (fullString != null) {
			if (fullString.endsWith("/") || fullString.endsWith("\\")) {
				name = name.substring(0, name.length() - 1);
			}
			int index = name.lastIndexOf('/');
			if (index == -1) {
				index = name.lastIndexOf('\\');
			}
			if (index != -1) {
				name = name.substring(index + 1, name.length());
			}
		}
		return name;
	}

	public static synchronized String buildJavaPackageName(final String name) {
		String data = name.replace("/", ".");
		if (data.endsWith(".")) {
			data = data.substring(0, data.length() - 1);
		}
		return data;
	}

	public File[] getFileList(File path) {
		if (path != null && path.exists() && path.isDirectory()) {
			return path.listFiles();
		}
		return null;
	}
	
	/**
	 * @return the stop
	 */
	public boolean isStoped() {
		return stop.get();
	}

	/**
	 * @param stop
	 *            the stop to set
	 */
	public void setStop() {
		this.stop.set(true);
	}
	
	/**
	 * @return the matchCase
	 */
	public boolean isMatchCase() {
		return matchCase;
	}

	/**
	 * @param matchCase
	 *            the matchCase to set
	 */
	public void setMatchCase(boolean matchCase) {
		this.matchCase = matchCase;
	}

	/**
	 * @return the contentSearch
	 */
	public boolean isContentSearch() {
		return contentSearch;
	}

	/**
	 * @param contentSearch
	 *            the contentSearch to set
	 */
	public void setContentSearch(boolean contentSearch) {
		this.contentSearch = contentSearch;
	}


	
}
