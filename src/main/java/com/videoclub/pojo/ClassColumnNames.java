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
	/**Function to get the class
	 * @return The class of the object
	 */
	public Class<T> getClazz() {
		return clazz;
	}
	/**Function to get the column name
	 * @return String of the column name
	 */
	public String getColumnName() {
		return columnName;
	}
	
}
