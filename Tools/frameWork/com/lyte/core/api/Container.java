package com.lyte.core.api;

import java.util.List;

public interface Container {
    public void add(Object object);
    public boolean contains(Object object);
    public List<Object> getAll();
}
