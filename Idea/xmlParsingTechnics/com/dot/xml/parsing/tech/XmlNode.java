/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.xml.parsing.tech;

import java.util.*;

public class XmlNode {
    private String tagName;

    protected static final String OPEN_TAG = "<";
    protected static final String CLOSE_TAG = ">";
    protected static final String END_OPEN_TAG = "</";
    protected static final String END_CLOSE_TAG = CLOSE_TAG;
    protected static final String INLINE_END_OPEN_TAG = OPEN_TAG;
    protected static final String INLINE_END_CLOSE_TAG = "/>";

    private XmlNode parent;
    private List<XmlNode> children = new LinkedList<XmlNode>();
    private Map<String, String> attributes = new LinkedHashMap<String, String>();
    private int level;
    private String uri = "";
    private String space = "  ";
    private StringBuffer value = new StringBuffer("");
    private boolean isAttribute;

    public XmlNode(String name) {
        this.tagName = name;
    }

    public XmlNode(String name, String uri) {
        this.tagName = name;
        this.uri = uri;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public XmlNode getFirstChild() {
        XmlNode firstChild = null;
        if (!children.isEmpty()) {
            firstChild = children.get(0);
        }
        return firstChild;
    }
    
    public XmlNode getLastChild(){
        XmlNode lastChild = null;
        if(!children.isEmpty()){
            lastChild = children.get(children.size()-1);
        }
        return lastChild;
    }

    /**
     * @return the parent
     */
    public XmlNode getParent() {
        return parent;
    }

    /**
     * @param parent
     *            the parent to set
     */
    @Deprecated
    public void setParent(XmlNode parent) {
        this.parent = parent;
        /*
         * if(!parent.getChildren().contains(this)){ parent.addChild(this); }
         */
    }

    /**
     * @return the children
     */
    public List<XmlNode> getChildren() {
        return children;
    }

    /**
     * @param children
     *            the children to set
     */
    public void setChildren(List<XmlNode> children) {
        this.children = children;
    }

    public void addChild(XmlNode child) {
        this.children.add(child);
        child.setParent(this);
    }
    
    public boolean removeChild(XmlNode childToDelete){
        return children.remove(childToDelete);
    }

    public boolean hasParent() {
        return parent != null;
    }

    public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    public String addAttribute(String key, String value) {
        return this.attributes.put(key, value);
    }

    public String getAttribute(String key) {
        return this.attributes.get(key);
    }

    public Set<String> getAttributeNames() {
        return attributes.keySet();
    }

    public String makeStartTag() {

        final StringBuilder builder = new StringBuilder(OPEN_TAG);
        if (uri.length() > 0) {
            builder.append(uri);
            builder.append(':');
        }
        builder.append(this.tagName);
        if (!attributes.isEmpty()) {
            final Set<String> names = getAttributeNames();
            for (final String name : names) {
                builder.append(" " + name);
                builder.append(" =\"" + attributes.get(name));
                builder.append('\"');
            }
        }
        builder.append(CLOSE_TAG);
        return builder.toString();
    }

    public String makeEndTag() {
        return END_OPEN_TAG + tagName + END_CLOSE_TAG;
    }

    public String makeEmptyValueTag() {
        final StringBuilder builder = new StringBuilder(INLINE_END_OPEN_TAG);
        if (uri.length() > 0) {
            builder.append(uri);
            builder.append(':');
        }
        builder.append(this.tagName);
        if (!attributes.isEmpty()) {
            final Set<String> names = getAttributeNames();
            for (final String name : names) {
                builder.append(" " + name);
                builder.append(" =\"" + attributes.get(name));
                builder.append('\"');
            }
        }
        builder.append(INLINE_END_CLOSE_TAG);
        return builder.toString();
    }

    public String toXml() {
        final StringBuilder str = new StringBuilder("");
        if (!hasChildren() && (value == null || getValue().length() == 0)) {
            return makeEmptyValueTag();
        }
        str.append(makeStartTag());
        if (hasChildren()) {
            final String tab = getSpace();
            for (final XmlNode node : children) {
                node.setSpace(tab + "  ");
                str.append('\n');
                str.append(space + node.toXml());
            }
            String s = "";
            if (space.length() > 1) {
                s = space.substring(0, space.lastIndexOf(' '));
            }
            str.append('\n' + s);
        } else {
            str.append(value.toString());
        }
        str.append(makeEndTag());
        return str.toString();
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value.toString().trim();
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = new StringBuffer(value);
    }

    public void appendValue(String chunkValue) {
        this.value.append(chunkValue);
    }

    public void append(char[] ch, int start, int length) {
        this.value.append(ch, start, length);
        if(this.value.toString().trim().length()>0){
            isAttribute = true;
        }
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level
     *            the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri
     *            the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.tagName;
    }

    /**
     * @return the isAttribute
     */
    public boolean isAttribute() {
        return isAttribute;
    }

    /**
     * @param isAttribute the isAttribute to set
     */
    public void setAttribute(boolean isAttribute) {
        this.isAttribute = isAttribute;
    }

}
