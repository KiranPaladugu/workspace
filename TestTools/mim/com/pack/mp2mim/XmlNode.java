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

import java.util.*;


public class XmlNode {
    // tag value cannot be null
    private String tag = null;
    private String space = "\t";
    // value can be null or empty.
    private String data = null;
    protected static final String OPEN_TAG = "<";
    protected static final String CLOSE_TAG = ">";
    protected static final String END_OPEN_TAG = "</";
    protected static final String END_CLOSE_TAG = CLOSE_TAG;
    protected static final String INLINE_END_OPEN_TAG = OPEN_TAG;
    protected static final String INLINE_END_CLOSE_TAG = "/>";
  
    //parent
    private XmlNode parent;
    private Map<String, List<XmlNode>> childrenMap = new HashMap<String, List<XmlNode>>();
    private Map<String, Attribute> attributes = new HashMap<String, Attribute>();
    // children can be empty
    private List<XmlNode> children = new ArrayList<XmlNode>();
    private int level = 0;

    public int getLevel() {
        return level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }



    public XmlNode(final String tag, final String data) {
        this.tag = tag;
        this.data = data;
    }


    /**
     * @param string
     */
    public XmlNode(final String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public XmlNode getParent() {
        return parent;
    }

    public void setParent(final XmlNode parent) {
        if (this.parent != null && !this.parent.equals(parent)) {
            this.parent.deleteChild(this);
        }
        this.parent = parent;
    }

    public void setTag(final String tag) {
        this.tag = tag;
    }

    public String getData() {
        return data;
    }

    public void setData(final String data) {
        this.data = data;
    }

    public List<XmlNode> getChildren() {
        return children;
    }

    public void setChildren(final List<XmlNode> subTags) {
        this.children = subTags;
    }

    public void addChild(final XmlNode node) {
        if (node != null) {
            this.children.add(node);
            node.setParent(this);
            node.setLevel(level + 1);
            addToMap(node);
        }
    }

    private void addToMap(final XmlNode node) {
        final List<XmlNode> obj = childrenMap.get(node.getName());
        if (obj == null) {
            final List<XmlNode> list = new ArrayList<XmlNode>();
            list.add(node);
            childrenMap.put(node.getName(), list);
        } else {
            obj.add(node);
        }
    }

    public List<XmlNode> getNodeByTagName(final String name) {
        return childrenMap.get(name);
    }

    public void setSpce(final String space) {
        this.space = space;
    }

    public boolean isPresentSubTags() {
        return !(children.isEmpty());
    }

    protected String getSpce() {
        return space;
    }

    public Map<String, Attribute> getAttributes() {
        return this.attributes;
    }

    public String makeStartTag() {
        final StringBuilder builder = new StringBuilder(OPEN_TAG + tag);
        if (!attributes.isEmpty()) {
            final Set<String> names = getAttributeNames();
            for (final String name : names) {
                builder.append(" " + attributes.get(name));
            }
        }
        builder.append(CLOSE_TAG);
        return builder.toString();
    }

    public String makeEndTag() {
        return END_OPEN_TAG + tag + END_CLOSE_TAG;
    }

    public void addAttribute(final String name, final String value) {
        addAttribute(name, value, false);
    }

    public void addAttribute(final String name, final String value, final boolean required) {
        attributes.put(name, new Attribute(name, value, required));
    }

    public Set<String> getAttributeNames() {
        return attributes.keySet();
    }

    public Attribute getAttribute(final String name) {
        return attributes.get(name);
    }

    public boolean validate() {
        return validateNode();
    }

    /**
     *
     */
    private boolean validateAttributes() {
        final boolean flag = true;
        final Set<String> keys = attributes.keySet();
        for (final String key : keys) {
            if (!attributes.get(key).validate()) {
                return false;
            }
        }
        return flag;
    }

    @Override
    public String toString() {
        return tosXMLFormat();
    }

    public String makeEmptyValueTag() {
        final StringBuilder builder = new StringBuilder(INLINE_END_OPEN_TAG + this.tag);
        if (!attributes.isEmpty()) {
            final Set<String> names = getAttributeNames();
            for (final String name : names) {
                builder.append(" " + attributes.get(name));
            }
        }
        builder.append(INLINE_END_CLOSE_TAG);
        return builder.toString();
    }

    public String tosXMLFormat() {
        final StringBuilder str = new StringBuilder("");
        if (!isPresentSubTags() && (data == null || data.equals(""))) {
            return makeEmptyValueTag();
        }
        str.append(makeStartTag());
        if (isPresentSubTags()) {
            final String tab = getSpce();
            for (final XmlNode node : children) {
                node.setSpce(tab + "\t");
                str.append('\n');
                str.append(space + node.tosXMLFormat());
            }
            String s = "";
            if (space.length() > 1) {
                s = space.substring(0, space.lastIndexOf('\t'));
            }
            str.append('\n' + s);
        } else {
            str.append(data);
        }
        str.append(makeEndTag());
        return str.toString();
    }

    public boolean deleteChild(final XmlNode child) {
        final boolean flag = false;
        if (child != null) {
            this.childrenMap.get(child.getName()).remove(child);
            this.children.remove(child);
        }
        return flag;
    }

    private boolean validateNode() {       
        if (!validateAttributes()) {
            System.out.println("Attributes validation failed for :" + tag);
            return false;
        }
        if (!this.children.isEmpty()) {
            for (final XmlNode child : children) {
                if (!child.validate()) {
                    return false;
                }
            }
        }

        return true;
    }


    public void setName(final String name) {
        this.setTag(name);

    }

    public String getName() {
        return getTag();
    }

    public void addAttribute(final Attribute attribute) {
        this.attributes.put(attribute.getName(), attribute);
    }

    public boolean isPresentChild(final String name) {
        final Set<String> childrenNames = this.childrenMap.keySet();
        return childrenNames.contains(name);
    }

}
