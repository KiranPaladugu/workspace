package com.tcs.pack.utils;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.*;
import java.util.Enumeration;
import java.util.jar.*;

import javax.swing.JFileChooser;
import javax.swing.text.JTextComponent;

import com.tcs.pack.searchJar.Result;

public class EditorUtils {

	public static synchronized void copyOperation(String data) {
		if (data != null && data.length() > 0) {
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(data), null);
		}
	}

	public static synchronized void add2CopyOperation(String data) {
		if (data != null && data.length() > 0) {
			String text = "";
			try {
				text = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (UnsupportedFlavorException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			data = text + "\n" + data;
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(data), null);
		}
	}

	public static synchronized String cutOperation(String fullData, String strToCut) {
		String data = fullData;

		return data;
	}

	public static synchronized String cutOperation(String fullData, int startIndex, int endIndex) {
		String data = fullData;
		if (startIndex != -1 && endIndex != -1 && startIndex != endIndex && startIndex < endIndex && endIndex <= fullData.length()) {
			String str1 = data.substring(0, startIndex);
			copyOperation(data.substring(startIndex, endIndex));
			String str2 = data.substring(endIndex, fullData.length());
			data = str1 + str2;
		}
		return data;
	}

	/**
	 * @param component
	 */
	public static synchronized void pasteOpertion(JTextComponent component) {
		if (component != null) {
			component.paste();
		}

	}

	/**
	 * @param results
	 * @param text
	 */
	public static synchronized File saveOperation(Component parent, Result result, String text, File fileToSave, String fileNameToSave) {
		if (result != null && result.getParent() != null) {
			if (result.getParent()!=null) {
				JarFile jarFile;
				try {
					jarFile = new JarFile(result.getParent().getName());
					if (jarFile != null) {
						JarEntry entry = jarFile.getJarEntry(result.getValue());
						if (entry != null) {
							if (entry.isDirectory()) {

							} else {
								if (fileToSave == null) {
									if (fileNameToSave != null && fileNameToSave.length() > 0) {
										JFileChooser fileChooser = new JFileChooser();
										fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
										int value = fileChooser.showSaveDialog(parent);
										if (value == JFileChooser.APPROVE_OPTION) {
											fileToSave = fileChooser.getSelectedFile();
										}
										String newFileName = fileToSave.getAbsolutePath()+File.separator+fileNameToSave;
										fileToSave = new File(newFileName);
									} else {
										JFileChooser fileChooser = new JFileChooser();
										int value = fileChooser.showSaveDialog(parent);
										if (value == JFileChooser.APPROVE_OPTION) {
											fileToSave = fileChooser.getSelectedFile();
										}
									}
								}
								if (fileToSave == null) {
									return null;
								}
								JarOutputStream jarOut = new JarOutputStream(new FileOutputStream(fileToSave));
								JarEntry jarEntry = null;
								Enumeration<JarEntry> jarEntries = jarFile.entries();
								try {
									while (jarEntries.hasMoreElements()) {
										jarEntry = jarEntries.nextElement();
										if (jarEntry.getName().equals(entry.getName())) {
											JarEntry newEntry = new JarEntry(entry.getName());
											newEntry.setTime(fileToSave.lastModified());
											jarOut.putNextEntry(newEntry);
											jarOut.write(text.getBytes());
											jarOut.closeEntry();
										} else {
											if (jarEntry != null) {
												BufferedInputStream reader = new BufferedInputStream(jarFile.getInputStream(jarEntry));
												JarEntry newEntry = new JarEntry(jarEntry.getName());
												newEntry.setTime(jarEntry.getTime());
												try {
													jarOut.putNextEntry(newEntry);
													int val = -1;
													while ((val = reader.read()) != -1) {
														jarOut.write(val);
														System.out.print(val);
													}
												} finally {
													jarOut.closeEntry();
													reader.close();
												}
											}
										}

									}
								} finally {
									jarOut.close();
								}
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
					fileToSave=null;
				}

			}
		}
		return fileToSave;
	}

}
