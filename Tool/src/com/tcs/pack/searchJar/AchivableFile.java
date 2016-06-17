/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.searchJar;

import java.io.File;

public class AchivableFile {
	private File file;
	private String nameToInclude;
	/**
	 * @return the nameToInclude
	 */
	public String getNameToInclude() {
		return nameToInclude;
	}
	/**
	 * @param nameToInclude the nameToInclude to set
	 */
	public void setNameToInclude(String nameToInclude) {
		this.nameToInclude = nameToInclude;
	}
	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}
	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}
	
}
