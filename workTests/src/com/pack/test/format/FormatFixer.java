/* ********************************************************************************
 * All rights reserved to Kiran Paladugu. If you find any thing useful send your
 * valueble feeback to paladugukiran@gmail.com.
 ******************************************************************************* */
package com.pack.test.format;

import java.util.*;

public class FormatFixer implements FixerComponent {
    private String startElement = null;
    private String endElement = ";";
    private List<Character> removableChars = new ArrayList<Character>();
    private int cursor = -1;
    private int counter;
    private int counterToAllow = -1;

    public FormatFixer() {
        removableChars.add('\"');
    }

    public FormatFixer(String startString, String endString) {
        this();
        this.startElement = startString;
        this.endElement = endString;
    }

    public FormatFixer(String startString, String endString, int counter) {
        this(startString, endString);
        this.counterToAllow = counter;
    }

    public void addCharToRemov(char ch) {
        removableChars.add(ch);
    }

    @Override
    public String fixFormat(String data) {
        if (startElement == null || endElement == null || "".equals(endElement) || "".equals(startElement)
                || removableChars.isEmpty()) {
            return data;
        }

        cursor = 0;
        String temp = null;
        String formatted = data;
        while ((temp = fix(formatted, cursor)) != null) {
            formatted = temp;
        }

        return formatted;
    }

    private String fix(String data, int start) {
        int startIndex = data.indexOf(startElement, start);
        if (startIndex != -1) {
            startIndex += startElement.length();
            int endIndex = data.indexOf(endElement, startIndex);
            if (endIndex != -1) {
                StringBuffer _toReturn = new StringBuffer();
                StringBuffer buffer = new StringBuffer();
                String subStr = data.substring(startIndex, endIndex);
                for (int i = 0; i < subStr.length(); i++) {
                    if (isTobeSkipped(subStr.charAt(i))) {
                        counter++;
                    } else {
                        buffer.append(subStr.charAt(i));
                    }
                }
                cursor = startIndex + subStr.length();
                _toReturn.append(data.substring(0, startIndex));
                _toReturn.append(buffer.toString());
                _toReturn.append(data.substring(endIndex));
                counter = 0;
                return _toReturn.toString();
            }
        }
        counter = 0;
        return null;

    }

    private boolean isTobeSkipped(char ch) {
        if (removableChars.contains(ch)) {
            if (counterToAllow != -1) {
                if (counter >= counterToAllow) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getStartString() {
        return startElement;
    }

    @Override
    public String getEndString() {
        return endElement;
    }

    @Override
    public List<Character> getRemovableChars() {
        return this.removableChars;
    }

    @Override
    public void setStartString(String startStr) {
        this.startElement = startStr;
    }

    @Override
    public void setEndString(String endStr) {
        this.endElement = endStr;
    }

    @Override
    public void addRemovableChars(char[] chars) {
        for (char ch : chars) {
            this.removableChars.add(ch);
        }
    }

    @Override
    public void addRemovableChar(char ch) {
        this.removableChars.add(ch);
    }

}
