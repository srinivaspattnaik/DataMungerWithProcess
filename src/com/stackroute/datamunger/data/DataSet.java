package com.stackroute.datamunger.data;
import java.util.*;

public class DataSet
{
	private List<DataRow> resultSet=new ArrayList<DataRow>();
	
	//private LinkedHashMap<String,Float> aggregateRow=new LinkedHashMap<String,Float>();
	
	private List<AggregateColumn> aggregateRow=new ArrayList<AggregateColumn>();
	
	public List<AggregateColumn> getAggregateRow() {
		return aggregateRow;
	}

	public void setAggregateRow(List<AggregateColumn> aggregateRow) {
		this.aggregateRow = aggregateRow;
	}

	private LinkedHashMap<String,List<DataRow>> groupByDataSetNew=new LinkedHashMap<String,List<DataRow>>();
	
	private LinkedHashMap<String,LinkedHashMap<String,Float>> totalGroupedData=new LinkedHashMap<String,LinkedHashMap<String,Float>>(); 
	
	public LinkedHashMap<String,LinkedHashMap<String, Float>> getTotalGroupedData() 
	{
		return totalGroupedData;
	}

	public void setTotalGroupedData(LinkedHashMap<String,LinkedHashMap<String, Float>> totalGroupedData) 
	{
		this.totalGroupedData = totalGroupedData;
	}

	public LinkedHashMap<String, List<DataRow>> getGroupByDataSetNew() 
	{
		return groupByDataSetNew;
	}

	public void setGroupByDataSetNew(LinkedHashMap<String, List<DataRow>> groupByDataSetNew) 
	{
		this.groupByDataSetNew = groupByDataSetNew;
	}

	/*public LinkedHashMap<String, Float> getAggregateRow() {
		return aggregateRow;
	}

	public void setAggregateRow(LinkedHashMap<String, Float> aggregateRow) {
		this.aggregateRow = aggregateRow;
	}*/

	public List<DataRow> getResultSet() 
	{
		return resultSet;
	}

	public void setResultSet(List<DataRow> resultSet) 
	{
		this.resultSet = resultSet;
	}
	
	
}
