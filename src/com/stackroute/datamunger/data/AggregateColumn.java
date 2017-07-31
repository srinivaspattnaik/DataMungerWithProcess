package com.stackroute.datamunger.data;

public class AggregateColumn implements Cloneable
{
	private String aggregateColumnName;
	
	private String aggregateFunction;
	
	private int aggregatecolumnPosition;
	
	private float aggregateValue;
	
	public String getAggregateColumnName() 
	{
		return aggregateColumnName;
	}
	public void setAggregateColumnName(String aggregateColumnName) 
	{
		this.aggregateColumnName = aggregateColumnName;
	}
	public String getAggregateFunction() 
	{
		return aggregateFunction;
	}
	public void setAggregateFunction(String aggregateFunction) 
	{
		this.aggregateFunction = aggregateFunction;
	}
	public float getAggregateValue() 
	{
		return aggregateValue;
	}
	public void setAggregateValue(float aggregateValue) 
	{
		this.aggregateValue = aggregateValue;
	}
	public int getAggregatecolumnPosition() 
	{
		return aggregatecolumnPosition;
	}
	public void setAggregatecolumnPosition(int aggregatecolumnPosition) 
	{
		this.aggregatecolumnPosition = aggregatecolumnPosition;
	}
	
	public AggregateColumn clone() throws CloneNotSupportedException
	{
		return (AggregateColumn)super.clone();
	}
	
}
