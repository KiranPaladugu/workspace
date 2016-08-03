/* ********************************************************************************
 * All rights reserved to Kiran Paladugu. If you find any thing useful send your
 * valueble feeback to paladugukiran@gmail.com.
 ******************************************************************************* */
package com.pack.test.format;

import java.util.List;

public interface FixerComponent {
    public void setStartString(String startStr);

    public String getStartString();

    public void setEndString(String endStr);

    public String getEndString();

    public void addRemovableChars(char[] chars);

    public void addRemovableChar(char ch);

    public List<Character> getRemovableChars();

    public String fixFormat(String data);
}
