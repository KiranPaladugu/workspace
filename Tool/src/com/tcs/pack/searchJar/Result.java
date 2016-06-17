package com.tcs.pack.searchJar;

import java.util.*;

public class Result {
    private String name;
    private String value;
    private Result parent;
    private boolean directory;
    private boolean Archive;
    private boolean hasParentArchive;
    private boolean isExtracted;
    private boolean dummy;
    private boolean isFile;
    
    private List<Message> errors = new Vector<Message>();
    private List<Result> ownResult = new ArrayList<Result>();
	private List<Result> resultList = new Vector<Result>();
	
	public Result(){
		this.dummy =false;
	}
	
	public Result(boolean dummy) {
		this.dummy = dummy;
	}

	public boolean isDummy() {
		return dummy;
	}

	public void setDummy(boolean value) {
		this.dummy = value;
	}
	
	public int getResultCount() {
		return ownResult.size();
	}

	public void addResult(Result name) {
		ownResult.add(name);
	}

	public List<Result> getAllResults() {
		return ownResult;
	}

	public void addSearchResult(SearchResult result) {
		this.resultList.add(result);
	}

	public List<? extends Result> getSearchResults() {
		return this.resultList;
	}
	
	/**
	 * @return the errors
	 */
	public List<Message> getErrors() {
		return errors;
	}

	/**
	 * @param error
	 *            the errors to set
	 */
	public void addError(Message error) {
		this.errors.add(error);
	}
	
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    public boolean hasParent(){
    	return parent!=null;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    	if(name!=null)
        return this.name;
    	else
    		return "DUMMY";
    }
	/**
	 * @return the parent
	 */
	public Result getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Result parent) {
		this.parent = parent;
	}
	/**
	 * @return the folder
	 */
	public boolean isDirectory() {
		return directory;
	}
	/**
	 * @param folder the folder to set
	 */
	public void setDirectory(boolean folder) {
		this.directory = folder;
	}
	public boolean isArchive() {
		return Archive;
	}
	public void setArchive(boolean archive) {
		Archive = archive;
	}
	/**
	 * @return the hasParentArchive
	 */
	public boolean hasParentArchive() {
		return hasParentArchive;
	}
	/**
	 * @param hasParentArchive the hasParentArchive to set
	 */
	public void setHasParentArchive(boolean hasParentArchive) {
		this.hasParentArchive = hasParentArchive;
	}
	/**
	 * @return the isExtracted
	 */
	public boolean isExtracted() {
		return isExtracted;
	}
	/**
	 * @param isExtracted the isExtracted to set
	 */
	public void setExtracted(boolean isExtracted) {
		this.isExtracted = isExtracted;
	}
	
	public boolean hasEmptyResults(){
		return (ownResult.isEmpty() && resultList.isEmpty());
	}

	/**
	 * @return the isFile
	 */
	public boolean isFile() {
		return isFile;
	}

	/**
	 * @param isFile the isFile to set
	 */
	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}
}
