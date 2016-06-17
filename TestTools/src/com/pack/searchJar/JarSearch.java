/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.pack.searchJar;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarSearch {
	/**
	 * 
	 */

	

	private String searchPath = null;
	private String searchFile = null;

	public JarSearch(String path, String file) {
		this.searchFile = file;
		if (path != null && path.length() > 0)
			this.searchPath = path;
		// doSearch();
	}

	public String getPath() {
		return this.searchPath;
	}

	public void setSearchPath(String searchPath) {
		this.searchPath = searchPath;
	}

	public void setSearchString(String searchString) {
		this.searchFile = searchString;
	}

	public String getSearchString() {
		return this.searchFile;
	}

	public List<SearchResult> doSearch() {
		return searchFileOrPath(new File(this.searchPath));
	}

	private List<SearchResult> searchFileOrPath(File pathToSearch) {
//		System.out.println("search Path : "+pathToSearch);
		List<SearchResult> jarsList = new ArrayList<SearchResult>();
		if (pathToSearch != null  && pathToSearch.exists()) {
			if (pathToSearch.isFile()) {
//				System.out.println("search File : "+pathToSearch);
				if ((pathToSearch.getName().endsWith(".jar"))
						|| (pathToSearch.getName().endsWith(".JAR"))) {
					SearchResult result = isFound(pathToSearch);
					if (result != null) {
						jarsList.add(result);
					}
				}
			} else if (pathToSearch.isDirectory()) {
//				System.out.println("search Directory : "+pathToSearch);
				File[] list = getFileList(pathToSearch);
				for (File file : list) {
					List<SearchResult> foundJars = searchFileOrPath(file);
					if (!foundJars.isEmpty()) {
						jarsList.addAll(foundJars);
					}
				}
			}
		}
		return jarsList;
	}

	private SearchResult isFound(File jarFile) {
		SearchResult result = null;
		try {
			JarFile jar = new JarFile(jarFile);
			Enumeration<JarEntry> entries = jar.entries();
//			System.out.println("Cheking Jar .. : "+jarFile.getName());
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				String packedName = buildPackagedName(entry);
//				System.out.println("  >Cheking entry .. : "+packedName+" -- "+entry.getName().contains(searchFile));
				if (packedName.contains(searchFile)) {
					if (result == null) {
						result = new SearchResult();
						result.setFile(jarFile);
					}
					result.addFoundFile(packedName);
				}
			}
			jar.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private String buildPackagedName(JarEntry entry){
		String packageName="";
		String name= entry.getName().replace("/", ".");
		if(name.endsWith(".")){
			name = name.substring(0, name.length()-1);
		}
		packageName = name;
		return packageName;
	}

	public File[] getFileList(File path) {
		if (path != null && path.exists() && path.isDirectory()) {
			return path.listFiles();
		}
		return null;
	}

	public static void main(String args[]) {
		JarSearch search = new JarSearch("", "");
		search.setSearchPath("C:\\Users\\ekirpal\\Desktop\\tester");
		search.setSearchString("RunmeToTest");
		List<SearchResult> list = search.doSearch();
		if(list.isEmpty()){
			System.out.println("Nothing found..");
		}
		for(SearchResult result : list){
			System.out.println("JarFile :"+result.getFile().getName()+" - occurance : "+result.getOccurance());
			for(String name:result.getFoundFiles()){
				System.out.println(">> File :"+name);
			}
		}
	}
}
