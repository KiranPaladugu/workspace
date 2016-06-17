/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.searchJar;

import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

public class NameSearch extends AbstractJarSearcher {

	private String searchString="";
	private boolean matchCase;
	private boolean wholeWord;
	private AbstractNameSearch nameSearch;
	private Pattern pattern;

	
	/**
	 * @param searchString
	 * @param matchCase
	 * @param wholeWord
	 */
	public NameSearch(String searchString, boolean matchCase, boolean wholeWord) {
		super();
		this.searchString = searchString;
		this.matchCase = matchCase;
		this.wholeWord = wholeWord;
		init();
	}
	/**
	 * 
	 */
	private void init() {
		if (isMatchCase()) {
			if (wholeWord) {
				nameSearch = new CaseSensitiveNameWholeWordSearch();
			} else
				nameSearch = new CaseSensitiveNameSearch();
		} else {
			this.searchString =searchString.toLowerCase(); 
			if (wholeWord) {
				nameSearch = new WholeWordNameSearch();
			} else {
				nameSearch = new PlainNameSearch();
			}
		}
		
	}
	
	protected void initPatternSearch(){

		String patterModifier="";
		 String patternString="";
		if (this.wholeWord) {
			patterModifier = "\\b";
		}
		patternString = ".*" + patterModifier + this.searchString + patterModifier + ".*";
		if (this.matchCase) {
			this.pattern = Pattern.compile(patternString);
		} else {
			this.pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		}
		nameSearch = new PatternSearch();
	
	}
	/* (non-Javadoc)
	 * @see com.tcs.pack.searchJar.AbstractJarSearcher#doSearch(java.lang.String, com.tcs.pack.searchJar.SearchResult, java.util.jar.JarEntry)
	 */
	@Override
	public SearchResult doSearch(JarFile jarFile,String jarFileName, SearchResult result, JarEntry jarEntry) {
		String packedName=buildPackagedName(jarEntry);
		if (nameSearch.isValidNameResult(packedName, searchString) || nameSearch.isValidNameResult(jarEntry.getName(), searchString)) {
			if (result == null) {
				result = new SearchResult(true);
			}
			if (result.isDummy()) {
				result.setFile(jarFileName);
				result.setDummy(false);
			}
			result.setrealName(jarEntry.getName());
			Result res = new Result();
			res.setDirectory(jarEntry.isDirectory());
			res.setName(packedName);
			res.setValue(jarEntry.getName());
			result.addResult(res);
		}
		return result;
	}
	
	abstract class AbstractNameSearch{
		public abstract boolean isValidNameResult(String entityName,String searchString);		
		
	}
	
	class PatternSearch extends AbstractNameSearch{

		/* (non-Javadoc)
		 * @see com.tcs.pack.searchJar.NameSearch.AbstractNameSearch#isValidNameResult(java.lang.String, java.lang.String)
		 */
		@Override
		public boolean isValidNameResult(String entityName, String searchString) {
			return pattern.matcher(entityName).matches();
		}
		
	}
	class CaseSensitiveNameWholeWordSearch extends AbstractNameSearch{

		/* (non-Javadoc)
		 * @see com.tcs.pack.searchJar.NameSearch.AbstractNameSearch#isValidNameSearch(java.lang.String, java.lang.String)
		 */
		@Override
		public boolean isValidNameResult(String entityName, String searchString) {
			return entityName.equals(searchString);
		}
		
	}
	class CaseSensitiveNameSearch extends AbstractNameSearch{

		/* (non-Javadoc)
		 * @see com.tcs.pack.searchJar.NameSearch.AbstractNameSearch#isValidNameSearch(java.lang.String, java.lang.String)
		 */
		@Override
		public boolean isValidNameResult(String entityName, String searchString) {
			return entityName.contains(searchString);
		}
	}
	class WholeWordNameSearch extends AbstractNameSearch{

		/* (non-Javadoc)
		 * @see com.tcs.pack.searchJar.NameSearch.AbstractNameSearch#isValidNameSearch(java.lang.String, java.lang.String)
		 */
		@Override
		public boolean isValidNameResult(String entityName, String searchString) {
			return entityName.toLowerCase().equals(searchString);
		}
	}
	class PlainNameSearch extends AbstractNameSearch{

		/* (non-Javadoc)
		 * @see com.tcs.pack.searchJar.NameSearch.AbstractNameSearch#isValidNameSearch(java.lang.String, java.lang.String)
		 */
		@Override
		public boolean isValidNameResult(String entityName, String searchString) {
			return entityName.toLowerCase().contains(searchString);
		}
	}
	/**
	 * @return
	 */
	private boolean isMatchCase() {
		return matchCase;
	}

}
