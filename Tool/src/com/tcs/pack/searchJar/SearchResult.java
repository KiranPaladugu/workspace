package com.tcs.pack.searchJar;

import java.util.*;

public class SearchResult extends Result {
	private String file;
	private String realName;
	private List<SearchResult> resultList = new Vector<SearchResult>();

	private String nextSearchResult;
	private boolean contentMode = false;

	public boolean isContentMode() {
		return contentMode;
	}

	public void setContentMode(boolean contentMode) {
		this.contentMode = contentMode;
	}

	public SearchResult() {
	}

	public SearchResult(boolean dummy) {
		super.setDummy(true);
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		setName(file);
		this.file = file;
	}

	public void addSearchResult(SearchResult result) {
		this.resultList.add(result);
	}

	@Override
	public List<SearchResult> getSearchResults() {
		return this.resultList;
	}

	/**
	 * @return the actualName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName
	 *            the actualName to set
	 */
	public void setrealName(String realName) {
		this.setValue(realName);
		this.realName = realName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (!contentMode)
			return file;
		else
			return getName();
	}

	/**
	 * @return the nextSearchResult
	 */
	public String getNextSearchResult() {
		return nextSearchResult;
	}

	/**
	 * @param nextSearchResult
	 *            the nextSearchResult to set
	 */
	public void setNextSearchResult(String nextSearchResult) {
		this.nextSearchResult = nextSearchResult;
	}
}