/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.utils;

import java.io.*;
import java.util.Enumeration;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.tcs.pack.searchJar.AbstractArchiveSearch;
import com.tcs.pack.serarch.Application;
import com.tcs.pack.serarch.ApplicationContext;

public class ArchiveExtractor {
	
	@Deprecated
	public File extractArchiveEntryNonBuffered(ZipEntry entryToExtract,ZipFile zipFile){		
		File tmpFile = null;
		String extn = getExtension(entryToExtract);
		while(tmpFile==null || tmpFile.exists()){
			tmpFile = new File(getNewTmpFile(extn));
		}
		try {
			InputStream zipIn = zipFile.getInputStream(entryToExtract);
			OutputStream out = new FileOutputStream(tmpFile);
			int x = -1;
			while((x=zipIn.read())!=-1){
				out.write(x);
			}
			out.close();
			zipIn.close();
			return tmpFile;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 5x Faster than {@link ArchiveExtractor}.extractArchiveEntryNonBuffered()
	 * @param zipEntryToExtract
	 * @param zipFileFromToExtract
	 * @return
	 */
	
	public File extractArchiveEntry(ZipEntry zipEntryToExtract,ZipFile zipFileFromToExtract){		
		File tmpFile = null;
		String extn = getExtension(zipEntryToExtract);
		
		while(tmpFile==null || tmpFile.exists()){
			tmpFile = new File(getNewTmpFile(extn));
		}
		try {
			BufferedInputStream zipIn = new BufferedInputStream(zipFileFromToExtract.getInputStream(zipEntryToExtract));
			BufferedOutputStream zipOut = new BufferedOutputStream(new FileOutputStream(tmpFile));
			byte[] array = new byte[2500];			
			int lenght=-1;
			while((lenght=zipIn.read(array))!=-1){
				zipOut.write(array, 0, lenght);
			}
			zipOut.close();
			zipIn.close();
			return tmpFile;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public File extractArchiveEntry(String fileToExtract,ZipFile zipFile){
		Enumeration<? extends ZipEntry> entriesItr = zipFile.entries();
		ZipEntry entry=null;
		while(entriesItr.hasMoreElements()){
			ZipEntry ent= entriesItr.nextElement();
			if(ent.getName().equals(fileToExtract)){
				entry = ent;
				break;
			}
		}
		if(entry!=null){
			return extractArchiveEntry(entry, zipFile);
		}
		return null;
	}
	/**
	 * @param entry
	 * @return
	 */
	private String getExtension(ZipEntry entry) {
		String name = entry.getName();
		String extn="";
		int offset=name.lastIndexOf('.');
		if(offset!=-1){
			extn=name.substring(offset, name.length());
		}
		if(extn.length()<1){
			extn=".archive";
		}
		return extn;
	}
	
	private String getNewTmpFile(String extn){
		UUID uuid = UUID.randomUUID();
		String tmpnFileame= uuid.toString()+extn;
		String tmpFilePath =ApplicationContext.getApplicationTempDir()+File.separator+tmpnFileame;
		return tmpFilePath;
		
	}
	
	public static void main(String args[]){
		String fileName = "C:\\Users\\ekirpal\\Desktop\\Desk\\arquillian\\_DEFAULT__mediationServiceEar_mediation-service-ear-6.4.4.ear";
		
		try {
			JarFile jarFile = new JarFile(new File(fileName));
			Enumeration<JarEntry> entries = jarFile.entries();
			long start = System.currentTimeMillis();
			while(entries.hasMoreElements()){
				JarEntry entry = entries.nextElement();
				if(AbstractArchiveSearch.isJavaArchive(entry.getName())){
					System.out.println("Seleted Candidate to test is : "+entry.getName());
					ArchiveExtractor ext = new ArchiveExtractor();
					File extracted = ext.extractArchiveEntry(entry, jarFile);
					System.out.println("Extracted FILE: "+extracted);
					System.out.println("Extracted FILE Location :"+extracted.getAbsolutePath());					
				}
			}
			long end = System.currentTimeMillis();
			System.out.println("Done in :"+((end-start))+"ms");
			Application.CleanBeforeExit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
