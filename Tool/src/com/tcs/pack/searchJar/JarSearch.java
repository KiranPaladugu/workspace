package com.tcs.pack.searchJar;

import java.io.*;
import java.util.*;
import java.util.jar.*;

import com.tcs.pack.searchJar.Message.MESSAGE_TYPE;

public class JarSearch extends AbstractArchiveSearch {

	public JarSearch(String path, String searchString, boolean matchCase, boolean contentSearch) {
		this.searchString = searchString;
		if (path != null && path.length() > 0) {
			this.searchPath = path;
			if (!searchPaths.contains(path)) {
				searchPaths.add(path);
			}
		}
		this.matchCase = matchCase;
		this.contentSearch = contentSearch;
		// doSearch();
	}

	public List<SearchResult> doSearch() {

		if (contentSearch) {
			finder = new ContentSearch(searchString, matchCase, wholeWord);
		} else {
			finder = new NameSearch(searchString, matchCase, wholeWord);
		}
		List<SearchResult> results = new Vector<SearchResult>();
		try {
			for (String searchPath : searchPaths) {
				List<SearchResult> list = searchFileOrPath(new File(searchPath));
				if (!list.isEmpty()) {
					results.addAll(list);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return results;
	}

	private List<SearchResult> searchFileOrPath(File pathToSearch) {
		// System.out.println("search Path : "+pathToSearch);
		List<SearchResult> jarsList = new Vector<SearchResult>();
		if (pathToSearch != null && pathToSearch.exists()) {
			if (pathToSearch.isFile()) {
				// System.out.println("search File : "+pathToSearch);
				if (isJavaArchive(pathToSearch.getName())) {
					List<SearchResult> results = doSearchIn(pathToSearch);
					if (!results.isEmpty()) {
						jarsList.addAll(results);

					}
				}
			} else if (pathToSearch.isDirectory() && !isStoped()) {
				folderObserver.folderSearchStarted(pathToSearch.getAbsolutePath());
				// System.out.println("search Directory : "+pathToSearch);
				File[] list = getFileList(pathToSearch);
				if (list != null && list.length > 0)
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

	private List<SearchResult> doSearchIn(File jarFile) {
		List<SearchResult> results = new Vector<SearchResult>();
		if (jarFile != null) {
			try {
				JarFile jar = new JarFile(jarFile);
				Enumeration<JarEntry> entries = jar.entries();

				SearchResult result = new SearchResult();
				result.setParent(null);
				result.setFile(jarFile.getAbsolutePath());
				result.setValue(jarFile.getAbsolutePath());
				result.setName(jarFile.getAbsolutePath());
				result.setrealName(jarFile.getAbsolutePath());
				result.setArchive(true);
				result.setFile(true);
				// System.out.println("Cheking Jar .. : "+jarFile.getName());
				while (entries.hasMoreElements() && !isStoped()) {
					JarEntry entry = entries.nextElement();
					try {
						result = searchInJar(jarFile.getAbsolutePath(), jar, result, entry);
					} catch (Exception e) {
						// e.printStackTrace();
						// System.out.println("Error in Opening file: "+jarFile.getAbsolutePath());
						addErrorResult(jarFile.getAbsolutePath(), result, entry, e);
					}

				}

				if (result.hasEmptyResults()) {
					result.setDummy(true);
				}
				if (!result.isDummy() && !result.hasEmptyResults()) {
					results.add(result);
					result.setNextSearchResult(jarFile.getParent());
					resultHolder.addResult(result);
				}

				jar.close();
			} catch (IOException e) {
				// e.printStackTrace();
				// System.out.println("Error in Opening file: "+jarFile.getAbsolutePath());
				SearchResult result = new SearchResult(true);
				Message message = new Message();
				message.setMessageType(MESSAGE_TYPE.EXCEPTION);
				message.setMessage(e.getMessage());
				if (jarFile.canRead())
					message.setDescription("Unable to Open Jar File");
				else
					message.setDescription("Jar file is not readable");
				message.setSource(jarFile.getAbsolutePath());
				result.addError(message);
				resultHolder.addResult(result);
			}
		}
		return results;
	}

	/**
	 * @param jarFile
	 * @param result
	 * @param entry
	 * @param e
	 */
	protected static void addErrorResult(String jarFileName, SearchResult result, JarEntry entry, Exception e) {
		SearchResult errorResult = new SearchResult(true);
		Message message = new Message();
		message.setMessageType(MESSAGE_TYPE.EXCEPTION);
		message.setMessage(e.getMessage());
		message.setDescription("Unable to read Jar File :" + jarFileName);
		message.setSource(entry.getName());
		errorResult.addError(message);
		result.addResult(errorResult);
	}

	// private String getChildJarName(String name) {
	// int x = name.lastIndexOf(File.pathSeparator);
	// System.out.println("string : index :" + x);
	// System.out.println("string : lenght :" + name.length());
	// System.out.println("String :" + name);
	// return name.substring(x, name.length() - 1);
	// }

	/**
	 * @param jarFileName
	 * @param jarFile
	 * @param result
	 * @param jarEntry
	 * @return
	 * @throws IOException
	 */
	private SearchResult searchInJar(String jarFileName, JarFile jarFile, SearchResult result, JarEntry jarEntry) throws IOException {
		if (isStoped()) {
			return result;
		}
		SearchResult childResult = null;
		// System.out.println(" >Cheking entry .. : "+packedName+" -- "+entry.getName().contains(searchFile));
		if (isJavaArchive(jarEntry.getName())) {

			// jar.getInputStream(jar.getEntry(entry.getName()))
			InputStream stream = jarFile.getInputStream(jarEntry);
			if (stream != null) {
				JarInputStream jarIn = new JarInputStream(stream);
				JarEntry childJar = null;
				childResult = new SearchResult();
				childResult.setArchive(true);
				childResult.setName(jarEntry.getName());
				childResult.setValue(jarEntry.getName());
				childResult.setFile(jarEntry.getName());
				result.setHasParentArchive(true);
				childResult.setParent(result);
				while (((childJar = jarIn.getNextJarEntry()) != null)) {
					// System.out.println("Searching in child Jar:"+childJar.getName());					
					try {
						childResult = searchInJar(jarEntry.getName(), jarFile, childResult, childJar);
					} catch (Exception e) {
						e.printStackTrace();
						addErrorResult(jarFile.getName(), childResult, jarEntry, e);
					}
				}

				if (childResult.hasEmptyResults()) {
					childResult.setDummy(true);
				} else {
					result.addSearchResult(childResult);
				}

				jarIn.close();
			}
		}
		result = finder.doSearch(jarFile, jarFileName, result, jarEntry);

		return result;
	}

	public void printResult(List<SearchResult> list, String space) {
		if (space == null) {
			space = "";
		}
		if (list.isEmpty()) {
			System.out.println("Nothing found..");
		}
		for (SearchResult result : list) {
			System.out.println(space + "JarFile :" + result.getFile() + " - occurance : " + result.getResultCount());
			for (Result res : result.getAllResults()) {
				System.out.println(space + "  >> File :" + res.getName());
			}

			if (!result.getSearchResults().isEmpty()) {
				System.out.println(space + "--------------------------------------------------------------------------");
				System.out.println(space + "=>[START] child of :" + result.getFile());
				printResult(result.getSearchResults(), (space + "    "));
				System.out.println(space + "=>[END] child of :" + result.getFile());
				System.out.println(space + "--------------------------------------------------------------------------");
			}

		}
	}

	public static void main(String args[]) {
		if (args.length < 2) {
			System.out.println("Invalid aruments or emtpy argument..\n argument Length:" + args.length);
			System.out.println("Usage : java JarSearch <searchString> <searchPath>");
			return;
		}
		JarSearch search = new JarSearch("", "", true, false);
		search.addSearchPath(args[1]);
		search.setSearchString(args[0]);
		List<SearchResult> list = search.doSearch();
		search.printResult(list, "");
	}

}
