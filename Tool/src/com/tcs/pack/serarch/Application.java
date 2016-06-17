/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pack.serarch;

public class Application {

	/**
	 * 
	 */
	public static void CleanBeforeExit() {
		if(!ApplicationContext.isEmpty()){
			ApplicationContext.cleanTmpDirs();
		}
		
	}

	public static void start(){
//		new Mainwindow
	}
}
