package com.stackroute.datamunger.data;
import java.util.*;

public class DataSet
{
	private List<DataRow> resultSet=new ArrayList<DataRow>();
	
	private List<AggregateColumn> aggregateRow=new ArrayList<AggregateColumn>();
	
	public List<AggregateColumn> getAggregateRow() {
		return aggregateRow;
	}

	public void setAggregateRow(List<AggregateColumn> aggregateRow) {
		this.aggregateRow = aggregateRow;
	}

	private LinkedHashMap<String,List<DataRow>> groupByDataSetNew=new LinkedHashMap<String,List<DataRow>>();
		
	
	private LinkedHashMap<String,List<AggregateColumn>> totalGroupedData=new LinkedHashMap<String,List<AggregateColumn>>();
	
	public LinkedHashMap<String, List<AggregateColumn>> getTotalGroupedData() 
	{
		return totalGroupedData;
	}

	public void setTotalGroupedData(LinkedHashMap<String, List<AggregateColumn>> totalGroupedData) 
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

	public List<DataRow> getResultSet() 
	{
		return resultSet;
	}

	public void setResultSet(List<DataRow> resultSet) 
	{
		this.resultSet = resultSet;
	}
	
	
}
