package com.tcs.pack.searchJar;

import java.io.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarReader {
	private File file;
	private Result result;
	private String JarToExtract;
	private String archiveToExtract;

	public JarReader(String path) {
		file = new File(path);
	}

	public JarReader(File file) {
		this.file = file;
	}

	public String getContent(String entryName) {
		return getContent(entryName, null);
	}

	public String getContent(String entyName, String secondParent) {
		String content = "";

		if (file != null && file.exists() && file.canRead()) {
			if (JarSearch.isJavaArchive(file.getName())) {
				StringBuilder builder = new StringBuilder();
				try {
					JarFile jarFile = new JarFile(file);
					if (secondParent != null) {
						// JarInputStream jarIn = new JarInputStream(jarFile.getInputStream(jarFile.getJarEntry(entyName)));
						// JarEntry childEntry =null;
						// while((childEntry=jarIn.getNextJarEntry())!=null){
						// if(childEntry.getName().equals(entyName)){
						// jarIn.
						// }
						// }
						// JarEntry jarEntry = jarFile.getJarEntry(entyName);
						// System.out.println(jarEntry);

					} else {
						JarEntry jarEntry = jarFile.getJarEntry(entyName);
						if (jarEntry != null) {
							BufferedInputStream reader = new BufferedInputStream(jarFile.getInputStream(jarEntry));
							int read = -1;
							// BufferedR
							while ((read = reader.read()) != -1) {
								builder.append((char) read);
							}
							
							reader.close();
							jarFile.close();
							content = builder.toString();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				// Do something ?
			}
		} else {
			// Do something ?
		}
		return content;
	}

	/**
	 * @return the result
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Result result) {
		this.result = result;
	}

	/**
	 * @return the jarToExtract
	 */
	public String getJarToExtract() {
		return JarToExtract;
	}

	/**
	 * @param jarToExtract the jarToExtract to set
	 */
	public void setJarToExtract(String jarToExtract) {
		JarToExtract = jarToExtract;
	}

	/**
	 * @return the archiveToExtract
	 */
	public String getArchiveToExtract() {
		return archiveToExtract;
	}

	/**
	 * @param archiveToExtract the archiveToExtract to set
	 */
	public void setArchiveToExtract(String archiveToExtract) {
		this.archiveToExtract = archiveToExtract;
	}
}
