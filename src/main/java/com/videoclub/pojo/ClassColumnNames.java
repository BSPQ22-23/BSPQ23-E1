package com.videoclub.pojo;

public class ClassColumnNames<T> {
	private Class<T> clazz;
	private String columnName;
	public ClassColumnNames(Class<T> clazz, String columnName) {
		this.clazz = clazz;
		this.columnName = columnName;
	}
	public Class<T> getClazz() {
		return clazz;
	}
	public String getColumnName() {
		return columnName;
	}
	
}
