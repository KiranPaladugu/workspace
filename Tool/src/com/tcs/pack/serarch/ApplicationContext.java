/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.serarch;

import java.io.File;
import java.util.*;

import com.tcs.pack.resources.Constants;

public class ApplicationContext {

	private static File tmpDir = null;
	private static boolean empty = true;
	private static Map<String,String> jarMap = new HashMap<String, String>();
	private static Map<String,Object> resources = new HashMap<String, Object>();
	
	public synchronized static boolean  putResource(String name, Object resource){
		if(!resources.containsKey(name)){
			return resources.put(name, resource) != null;
		}
		return false;
	}
	
	public synchronized static Object getResource(String key){
		return resources.get(key);
	}
	

	public synchronized static void addTojarMap(String fileName,String newJarFileName){
		jarMap.put(fileName, newJarFileName);
	}
	
	public synchronized static boolean isExistsExtractedJar(String jarName){
		return jarMap.containsKey(jarName);
	}
	
	public synchronized static String getExtractedJarName(String jarName){
		return jarMap.get(jarName);
	}
	
	public static File getApplicationTempDir() {
		if (tmpDir == null) {
			tmpDir = new File(Constants.DIRECTORY_SYTEM_TEMP + File.separator + getRandomUniqueId());
			while (tmpDir.exists()) {
				tmpDir = new File(Constants.DIRECTORY_SYTEM_TEMP + File.separator + getRandomUniqueId());
			}
			empty = false;
			tmpDir.mkdirs();
		}
		return tmpDir;
	}

	public static String getRandomUniqueId() {
		return UUID.randomUUID().toString();
	}

	/**
	 * @return
	 */
	public static boolean isEmpty() {
		return empty;
	}

	/**
	 * 
	 */

	private static boolean cascaseDelete(File file) {
		if (file.isDirectory()) {
			File[] filesList = file.listFiles();
			for (File eachFile : filesList) {
				cascaseDelete(eachFile);
			}
		}
		System.out.println("Deleting:"+file.getAbsolutePath());
		return file.delete();
	}

	public static void cleanTmpDirs() {
		if (tmpDir != null) {
			if (tmpDir.exists()) {
				System.out.println("Deleting tmp Directory!");
				if(cascaseDelete(tmpDir)){
					tmpDir = null;
				}
			}
		}

	}
}
