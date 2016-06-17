/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.searchJar;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.jar.JarFile;

public class JarContentExplorer extends AbstractArchiveContentExplorer {
	/**
	 * 
	 */
	public JarContentExplorer(String path) {
		File file = new File(path);
		if (file.exists()) {
			this.setArhiveFile(file);
		}
	}
	
	public SearchResult getArchiveContents(){
		SearchResult result = new SearchResult(true);
		if(getArchiveFile()!=null){
			if(AbstractArchiveSearch.isJavaArchive(getArchiveFileName())){
				try {
					JarFile jarFile = new JarFile(getArchiveFile());
					getContents(result, jarFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			result.setFile(archive.getAbsolutePath());
			result.setrealName(archive.getAbsolutePath());
			result.setFile(archive.getAbsolutePath());
			result.setDirectory(archive.isDirectory());
		}
		return result;
	}
	
	
	public boolean isValidArchive(String fileName){
		return AbstractArchiveSearch.isJavaArchive(fileName);
	}
	
	public static void main(String args[]){
		String jarFilePath = "C:\\Users\\ekirpal\\Desktop\\"
				+ "netconf-filter-get-handler-jar-1.0.8-SNAPSHOT.jar";
//		+"_DEFAULT__netconfEcimSyncNodeHandlerEar_netconf-ecim-sync-node-handler-ear-1.0.8.ear";
		JarContentExplorer exp = new JarContentExplorer(jarFilePath);
		SearchResult res= exp.getArchiveContents();
		System.out.println(res);
		List<SearchResult> results = res.getSearchResults();
		printResult(results,"");
		
	}

	/**
	 * @param results
	 */
	private static void printResult(List<SearchResult> results,String space) {
		if(!results.isEmpty()){
			for(SearchResult result:results){
				System.out.println(space+result.getName());
				if(!result.getSearchResults().isEmpty()){
					printResult(result.getSearchResults(),space+"\t");
				}
			}
		}
	}
}
