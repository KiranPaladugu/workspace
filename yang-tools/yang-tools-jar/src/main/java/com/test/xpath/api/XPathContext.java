package com.test.xpath.api;

public interface XPathContext {
    public static final int SCOPE_GLOBAL=0;
    public static final int SCOPE_LOCAL=1;
    public static final int SCOPE_ELEMENT=2;
    public static final int SCOPE_NONE=-1;
    public String evaluate(String xVar);
    public boolean putXVar(String xVar,String value);
    public boolean putXVar(String xVar, String value, int scope);
}
