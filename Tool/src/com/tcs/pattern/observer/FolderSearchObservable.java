/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.pattern.observer;

import java.util.Observable;

public class FolderSearchObservable extends Observable {
	public void folderSearchStarted(String pathName){
		setChanged();
		notifyObservers(pathName);
	}
	
	
}
