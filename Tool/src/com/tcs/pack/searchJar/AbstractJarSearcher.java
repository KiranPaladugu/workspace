/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.searchJar;

import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public abstract class AbstractJarSearcher {
	public static String buildPackagedName(JarEntry entry) {
		String packageName = "";
		String name = entry.getName().replace("/", ".");
		if (name.endsWith(".")) {
			name = name.substring(0, name.length() - 1);
		}
		packageName = name;
		return packageName;
	}

	public abstract SearchResult doSearch(JarFile jarFile,String jarFileName,SearchResult parentToAdd, JarEntry entry );
}
