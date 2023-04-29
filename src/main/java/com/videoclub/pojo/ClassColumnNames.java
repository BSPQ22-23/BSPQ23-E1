package com.videoclub.pojo;

public class ClassColumnNames<T> {
	private Class<T> clazz;
	private String columnName;
	protected ClassColumnNames(Class<T> clazz, String columnName) {
		
	}
	public Class<T> getClazz() {
		return clazz;
	}
	public String getColumnName() {
		return columnName;
	}
	
}
