package com.stackroute.datamunger.data;

public class Criteria 
{
	private String columnName;
	private String operator;
	private String columnValue;
	private int coloumnPosition;
	
	public String getColumnName() 
	{
		return columnName;
	}
	public void setColumnName(String columnName) 
	{
		this.columnName = columnName;
	}
	public String getOperator() 
	{
		return operator;
	}
	public void setOperator(String operator) 
	{
		this.operator = operator;
	}
	public String getColumnValue() 
	{
		return columnValue;
	}
	public void setColumnValue(String columnValue) 
	{
		this.columnValue = columnValue;
	}
	public int getColoumnPosition() 
	{
		return coloumnPosition;
	}
	public void setColoumnPosition(int coloumnPosition) 
	{
		this.coloumnPosition = coloumnPosition;
	}
	
}
