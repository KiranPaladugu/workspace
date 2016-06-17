/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.mp2mim;

public class Version {
    private String version = "1";
    private String release = "0";
    private String correction = "0";
    private final String dot = ".";
    private boolean empty = true;

    public boolean isEmpty() {
        return empty;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        if (version != null && version.length() > 0) {
            this.version = version.trim();
            empty = false;
        }
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        if (release != null && release.length() > 0) {
            this.release = release.trim();
            empty = false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return version + dot + release + dot + correction;
    }

    /**
     * @return the correction
     */
    public String getCorrection() {
        return correction;
    }

    /**
     * @param correction
     *            the correction to set
     */
    public void setCorrection(String correction) {
        if (correction != null && correction.length() > 0) {
            this.correction = correction;
            empty = false;
        }
    }
}
