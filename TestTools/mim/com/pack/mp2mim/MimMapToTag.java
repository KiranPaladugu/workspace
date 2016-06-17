/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.mp2mim;

@SuppressWarnings("unused")
public class MimMapToTag{
    private static final String MIM_MAPTO="mimMappedTo";
    private static final String NAMESPACE="namespace";
    private static final String VERSION="version";
    private static final String BASE_MIM_NS="referenceMimNamespace";
    private static final String BASE_MIM_NS_VERSION="referenceMimVersion";
    private String namespace;
    private String version;
    private String referenceMimNamespace;
    private String referenceMimVersion;
    /**
     * @param tag
     */
    public MimMapToTag() {
    }
    public String getNameSpace() {
        return namespace;
    }
    public void setNameSpace(String nameSpace) {
        this.namespace = nameSpace;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getReferenceMimNamespace() {
        return referenceMimNamespace;
    }
    public void setReferenceMimNamespace(String referenceMimNamespace) {
        this.referenceMimNamespace = referenceMimNamespace;
    }
    public String getReferenceMimVersion() {
        return referenceMimVersion;
    }
    public void setReferenceMimVersion(String referenceMimVersion) {
        this.referenceMimVersion = referenceMimVersion;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((namespace == null) ? 0 : namespace.hashCode());
        result = prime * result + ((referenceMimNamespace == null) ? 0 : referenceMimNamespace.hashCode());
        result = prime * result + ((referenceMimVersion == null) ? 0 : referenceMimVersion.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MimMapToTag other = (MimMapToTag) obj;
        if (namespace == null) {
            if (other.namespace != null)
                return false;
        } else if (!namespace.equals(other.namespace))
            return false;
        if (referenceMimNamespace == null) {
            if (other.referenceMimNamespace != null)
                return false;
        } else if (!referenceMimNamespace.equals(other.referenceMimNamespace))
            return false;
        if (referenceMimVersion == null) {
            if (other.referenceMimVersion != null)
                return false;
        } else if (!referenceMimVersion.equals(other.referenceMimVersion))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        } else if (!version.equals(other.version))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "<mimMappedTo nameSpace=\"" + namespace + "\" version=\"" + version + "\"\n referenceMimNamespace=\"" + referenceMimNamespace
                + "\" referenceMimVersion=\"" + referenceMimVersion + "\" />";
    }
    
   

}
