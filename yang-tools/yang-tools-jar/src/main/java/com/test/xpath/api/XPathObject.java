package com.test.xpath.api;

public interface XPathObject {
    void setName(String name);
    void setValue(Object value);
    String getName();
    Object getValue();
    XPathObject getNext();
    XPathObject getPrevious();
    XPathObject getParent();
    String evaluate(String var);
    String getPath();
    boolean hasNext();
    boolean hasPrevious();
    boolean hasParent();
    boolean hasChildren();
    XPathObject findPathObject(String xpath);
    boolean validatePath(String xpath);
    XPathContext getContext();
    boolean isParent();
}
