/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.searchJar;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

import com.tcs.pack.utils.ArchiveExtractor;

public class ExtractAndLinkJar {
	/**
	 * 
	 */
	public ExtractAndLinkJar(Result entryToExtract, Result jarName) {
		ArchiveExtractor extractor = new ArchiveExtractor();
		String entyNameToExtract = entryToExtract.getValue();
		String jar = jarName.getValue();
		try {
			ZipFile zipFile = new ZipFile(new File(jar));
			File extractedFile = extractor.extractArchiveEntry(entyNameToExtract, zipFile);
			entryToExtract.setValue(extractedFile.getAbsolutePath());
			entryToExtract.setExtracted(true);
			entryToExtract.setArchive(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
