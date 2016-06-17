package com.tcs.pattern.observer;

import java.util.*;

import com.tcs.pack.searchJar.SearchResult;

public class SearchResultHolder extends Observable{
    private List<SearchResult> results = new Vector<SearchResult>();
    
    public void addResult(SearchResult result){
        this.results.add(result);
        setChanged();
        notifyObservers(result);
    }
    
    public void addAll(Collection<SearchResult> data){
        this.results.addAll(data);
    }
    
    public List<SearchResult> getAllResults(){
        return this.results;
    }
        
}
