package com.test.xpath.api;

public interface Attribute extends TreeObject {
    String getName();
    String getValue();
    Attribute getNext();
    Attribute getPrevious();
}
