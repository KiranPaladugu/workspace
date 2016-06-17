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
import java.util.ArrayList;
import java.util.List;

public class SearchResult {
	private File file;
	private List<String> names = new ArrayList<String>();

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getOccurance() {
		return names.size();
	}

	public void addFoundFile(String name){
		names.add(name);
	}
	
	public List<String> getFoundFiles(){
		return names;
	}
}