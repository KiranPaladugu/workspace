/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.searchJar;

import java.io.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import com.tcs.pack.searchJar.Message.MESSAGE_TYPE;

public class ContentSearch extends AbstractJarSearcher{

	private String searchString="";
	private boolean matchCase;
	private boolean wholeWord;
	
	private Pattern pattern;
	
	

	/**
	 * @param searchString
	 * @param matchCase
	 * @param wholeWord
	 */
	public ContentSearch(String searchString, boolean matchCase, boolean wholeWord) {
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
	}



	/* (non-Javadoc)
	 * @see com.tcs.pack.searchJar.AbstractJarSearcher#doSearch(java.util.jar.JarFile, java.lang.String, com.tcs.pack.searchJar.SearchResult, java.util.jar.JarEntry)
	 */
	@Override
	public SearchResult doSearch(JarFile jarFile, String jarFileName, SearchResult result, JarEntry jarEntry) {
		if (jarFile != null && JarSearch.isValidFileForContentSearch(jarEntry.getName())) {
			try {
				InputStream entyAsStream = jarFile.getInputStream(jarEntry);
				if (entyAsStream != null) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(entyAsStream));

					String line = null;
					int count = 1;
					while ((line = reader.readLine()) != null) {
						if (pattern.matcher(line).matches()) {
							// System.out.println("Found Math @ line:" + count + " in File:" + jar.getName() + "->" + jarEntry.getName());
							/*if (result == null) {
								result = new SearchResult(true);
							}
							if (result.isDummy()) {
								result.setFile(jarFile.getName());
								result.setDummy(false);
							}
							result.setrealName(jarEntry.getName());*/
							Result res = new Result();
							res.setParent(result);
							res.setName(jarEntry.getName() + "@ Line:" + count);
							res.setValue(jarEntry.getName());
							result.addResult(res);

						}
						count++;
					}
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				SearchResult result1 = new SearchResult(true);
				Message message = new Message();
				message.setMessageType(MESSAGE_TYPE.ERROR);
				message.setMessage(e.getMessage());
				message.setSource(jarFile.getName());
				result1.addError(message);
				result.addResult(result1);
			}
		}
		return result;
	}
	
	public boolean isContentPresent(String line){
//		boolean flag= false;
		return line.toLowerCase().contains(searchString.toLowerCase());
//		return flag;
	}
	public boolean isContentPresentCaseSense(String line){
//		boolean flag= false;
		return line.contains(searchString);
//		return flag;
	}

}
