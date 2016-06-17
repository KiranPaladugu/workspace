/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.searchJar;

import java.io.*;
import java.util.*;
import java.util.zip.*;

public class AbstractArchiveContentExplorer {

	protected static String[] ARCHIVES = { ".zip", ".rar", ".7z" };

	protected File archive;

	public File getArchiveFile() {
		return archive;
	}

	public String getArchiveFilePath() {
		return archive.getParent();
	}

	public String getArchiveFileName() {
		return archive.getName();
	}

	public String getArchivePath() {
		return archive.getAbsolutePath();
	}

	public void setArhiveFile(File file) {
		this.archive = file;
	}

	public SearchResult getArchiveContents() {
		SearchResult result = new SearchResult(true);
		if (archive.exists()) {
			try {
				ZipFile file = new ZipFile(archive);
				getContents(result, file);
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * @param result
	 * @param file
	 */
	protected void getContents(SearchResult result, ZipFile file) {
		Enumeration<? extends ZipEntry> entryItr = file.entries();
		while (entryItr.hasMoreElements()) {
			ZipEntry zipEntry = entryItr.nextElement();
			if (zipEntry != null) {
				SearchResult res = new SearchResult(false);
				res.setrealName(zipEntry.getName());
				res.setDirectory(zipEntry.isDirectory());
				res.setFile(zipEntry.getName());				
				List<String> structure = findFolderStructure(zipEntry.getName());
				res.setName(JarSearch.getFileName(zipEntry.getName()));
				if (result.isDummy()) {
					result.setDummy(false);
				}
				updateStrcture(result, structure, res);
				if (isValidArchive(zipEntry.getName())) {
					getContentsInArchive(file, zipEntry, res);
				}
				// result.addSearchResult(res);
			}
		}
	}

	/**
	 * @param zipFile
	 * @param zipEntry
	 * @param res
	 */
	private void getContentsInArchive(ZipFile zipFile, ZipEntry zipEntry, SearchResult res) {
		try {
			ZipInputStream zipIn = new ZipInputStream(zipFile.getInputStream(zipEntry));
			ZipEntry nextEntry = null;
			while ((nextEntry = zipIn.getNextEntry()) != null) {
				if (nextEntry != null) {
					SearchResult newResult = new SearchResult(false);
					newResult.setrealName(nextEntry.getName());
					newResult.setDirectory(nextEntry.isDirectory());
					newResult.setFile(nextEntry.getName());
					List<String> structure = findFolderStructure(nextEntry.getName());
					newResult.setName(JarSearch.getFileName(nextEntry.getName()));
					if (res.isDummy()) {
						res.setDummy(false);
					}
					updateStrcture(res, structure, newResult);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param result
	 * @param structure
	 * @param res
	 */
	protected void updateStrcture(SearchResult result, List<String> structure, SearchResult resultToAdd) {
		SearchResult current = result;
		resultToAdd.setContentMode(true);
		for (int index = 0; index < structure.size(); index++) {
			String folderOrFile = structure.get(index);
			List<SearchResult> currentResults = current.getSearchResults();
			if (index == (structure.size() - 1)) {
				current.addSearchResult(resultToAdd);
			} else {
				for (SearchResult res : currentResults) {
					if (res.getName().equals(folderOrFile)) {
						current = res;
						break;
					}
				}
			}
		}
	}

	public boolean isValidArchive(String packageName) {
		boolean flag = false;
		for (String arhive : ARCHIVES) {
			if (packageName.endsWith(arhive) || packageName.endsWith(arhive.toUpperCase())) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public List<String> findFolderStructure(String name) {
		List<String> folders = new ArrayList<String>();
		String ff[] = name.split("/");
		if (ff.length > 0) {
			for (int index = 0; index < ff.length; index++) {
				folders.add(ff[index]);
			}
		}
		return folders;
	}

}
