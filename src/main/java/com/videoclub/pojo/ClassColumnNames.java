package com.videoclub.pojo;

public class ClassColumnNames<T> {
	private Class<T> clazz;
	private String columnName;
	/**
	 * This method is used as a filter to be able to select different class objects from the database
	 * @param clazz the class of the object we are looking for
	 * @param columnName the column from which we are going to select from the database
	 */
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
