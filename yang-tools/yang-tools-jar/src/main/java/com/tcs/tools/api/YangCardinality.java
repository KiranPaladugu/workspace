package com.tcs.tools.api;

public interface YangCardinality {

	public static final String ONE = "1";
	public static String ONE_OR_MANY = "1..n";
	public static String NONE_OR_ONE = "0..1";
	public static String NONE_OR_MANY = "0..n";
	public static String OPTIONAL = "";
	
	public YangCardinality parse(String cardinalityString);
}
