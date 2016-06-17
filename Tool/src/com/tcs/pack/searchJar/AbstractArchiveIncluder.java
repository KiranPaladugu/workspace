/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.searchJar;

import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class AbstractArchiveIncluder {
	/**
	 * 
	 */
	public AbstractArchiveIncluder() {
	}
	
	public void addFilesToJar(List<AchivableFile> files,JarFile jarFile){
		for(AchivableFile file:files){
			ZipEntry entry = new ZipEntry(file.getNameToInclude());
			entry.setTime(System.currentTimeMillis());
//			entry.
		}
	}
}
