/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2013
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.pack.mp2mim;


public class Attribute  {
    private String name;
    private String value;
    private boolean required;
    private String dataType;

    /**
     * @param name
     * @param value
     */
    public Attribute(final String name, final String value) {
        super();
        this.name = name;
        this.value = value;
    }

    /**
     * @param name
     * @param value
     * @param required
     */
    public Attribute(final String name, final String value, final boolean required) {
        super();
        this.name = name;
        this.value = value;
        this.required = required;
    }

    /**
     * @return the name
     */
 
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
 
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
 
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
 
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * @return the required
     */
 
    public boolean isRequired() {
        return required;
    }

    /**
     * @param required
     *            the required to set
     */
 
    public void setRequired(final boolean required) {
        this.required = required;
    }

    /**
     * @return the dataType
     */
 
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType
     *            the dataType to set
     */
 
    public void setDataType(final String dataType) {
        this.dataType = dataType;
    }

 
    public String toString() {
        if (value != null && value.length() > 0) {
            return name + " =\"" + value + "\"";
        } else {
            return "";
        }
    }

    /**
     * @return
     */
 
    public boolean validate() {
        if (!name.trim().matches("[a-z0-9A-Z.:_]*")) {
            System.err.println("AttributeName is not valid :" + name);
            return false;
        }
        if (required && (value == null || value.length() == 0)) {
            System.out.println("Value of an Attribute is null or empty ,value is required for :" + name);
            return false;
        }
        return true;
    }

 
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataType == null) ? 0 : dataType.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + (required ? 1231 : 1237);
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

 
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Attribute other = (Attribute) obj;
        if (dataType == null) {
            if (other.dataType != null) {
                return false;
            }
        } else if (!dataType.equals(other.dataType)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (required != other.required) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

}
