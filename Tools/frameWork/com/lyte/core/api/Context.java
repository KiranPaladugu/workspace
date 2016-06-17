package com.lyte.core.api;

public interface Context {
    public Object get(String key);
    public void put(String key, Object value);
}
