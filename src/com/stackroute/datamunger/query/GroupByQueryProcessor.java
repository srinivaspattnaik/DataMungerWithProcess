package com.stackroute.datamunger.query;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.stackroute.datamunger.data.AggregateColumn;
import com.stackroute.datamunger.data.DataRow;
import com.stackroute.datamunger.data.DataSet;
import com.stackroute.datamunger.data.HeaderRow;
import com.stackroute.datamunger.utility.FilterData;

public class GroupByQueryProcessor implements QueryProcessor 
{

	@Override
	public DataSet executeQuery(QueryParam queryParam) throws Exception 
	{
		DataSet dataSet=new DataSet();
		HeaderRow headerRow=queryParam.getHeaderRow();
		Set<String> columnNames=headerRow.keySet();
		int columnCount=columnNames.size();
		
		BufferedReader bufferedReader=new BufferedReader(new FileReader(queryParam.getFilePath()));
		DataRow rowData;
		bufferedReader.readLine();
		String row;
		
		if(queryParam.getCriterias()==null)
		{
			while((row=bufferedReader.readLine())!=null)
			{
				int count=0;
				rowData=new DataRow();
			
				String rowValues[]=row.trim().split(",");
				while(count<columnCount)
				{
					rowData.put(count,rowValues[count].trim());
					count++;
				}
				
					String groupByColumnValue=rowData.get(headerRow.get(queryParam.getGroupByColumn()));
					List<DataRow> dataValues=null;
					if(dataSet.getGroupByDataSetNew().containsKey(groupByColumnValue))
					{
						dataValues=dataSet.getGroupByDataSetNew().get(groupByColumnValue);
						dataValues.add(rowData);
					}
					else
					{
						dataValues=new ArrayList<DataRow>();
						dataValues.add(rowData);
					}
					dataSet.getGroupByDataSetNew().put(groupByColumnValue,dataValues);
			}
		}
		else
		{
			while((row=bufferedReader.readLine())!=null)
			{
				int count=0;
				rowData=new DataRow();
				
				String rowValues[]=row.trim().split(",");
				while(count<columnCount)
				{
					rowData.put(count,rowValues[count].trim());
					count++;
				}
				
				if(FilterData.evaluateWhereCondition(queryParam,rowValues))
				{
					String groupByColumnValue=rowData.get(headerRow.get(queryParam.getGroupByColumn()));
					List<DataRow> dataValues=null;
					if(dataSet.getGroupByDataSetNew().containsKey(groupByColumnValue))
					{
						dataValues=dataSet.getGroupByDataSetNew().get(groupByColumnValue);
						dataValues.add(rowData);
					}
					else
					{
						dataValues=new ArrayList<DataRow>();
						dataValues.add(rowData);
					}
					dataSet.getGroupByDataSetNew().put(groupByColumnValue,dataValues);
				}
			}
		}
		
		LinkedHashMap<String,List<DataRow>> groupDataSet=dataSet.getGroupByDataSetNew();
		Set<String> groupByColumnValues=groupDataSet.keySet(); 
		for(String groupByColumnValue:groupByColumnValues)
		{
			List<DataRow> groupRows=dataSet.getGroupByDataSetNew().get(groupByColumnValue);
			List<AggregateColumn> eachGroupAggregateRow=evaluateAggregateColumns(groupRows,queryParam);
			dataSet.getTotalGroupedData().put(groupByColumnValue,eachGroupAggregateRow);
		}
	
		return dataSet;
	}
	
	public List<AggregateColumn> evaluateAggregateColumns(List<DataRow> groupRows,QueryParam queryParam)
	{
		int aggregateColumnCount=queryParam.getListAggregateColumn().size();
		int count=0,columnCount=0;
		
		int groupRowSize=groupRows.size();
		List<AggregateColumn> listAggregateColumn=new ArrayList<AggregateColumn>();
		for(AggregateColumn aggregateColumn:queryParam.getListAggregateColumn())
		{
			try 
			{
				listAggregateColumn.add(aggregateColumn.clone());
			} catch (CloneNotSupportedException e) 
			{
				e.printStackTrace();
			}
		}
		
		HeaderRow headerRow=queryParam.getHeaderRow();
		
		while(count<groupRowSize)
		{
			columnCount=0;	
			while(columnCount<aggregateColumnCount)
			{
				String actualRowValue=groupRows.get(count).get(headerRow.get(listAggregateColumn.get(columnCount).getAggregateColumnName()));
				String aggregateFunctionName=listAggregateColumn.get(columnCount).getAggregateFunction().toLowerCase();
				float value=listAggregateColumn.get(columnCount).getAggregateValue();
				
				if(!FilterData.evaluateDataType(actualRowValue))
				{
					switch(aggregateFunctionName)
					{
					case "sum":
						listAggregateColumn.get(columnCount).setAggregateValue(value+Float.parseFloat(actualRowValue));
						break;
					case "avg":
						listAggregateColumn.get(columnCount).setAggregateValue(value+Float.parseFloat(actualRowValue));
						break;
					case "min":
						if(count==0)
						{
							listAggregateColumn.get(columnCount).setAggregateValue(Float.parseFloat(actualRowValue));
						}
						else
						{
							if(value>Float.parseFloat(actualRowValue))
								listAggregateColumn.get(columnCount).setAggregateValue(Float.parseFloat(actualRowValue));
						}
						break;
					case "max":
						if(value<Float.parseFloat(actualRowValue))
							listAggregateColumn.get(columnCount).setAggregateValue(Float.parseFloat(actualRowValue));
						break;
					case "count":
						listAggregateColumn.get(columnCount).setAggregateValue(value+1);
						break;
					}
				}
				else
				{
					switch(aggregateFunctionName)
					{
					case "count":
						listAggregateColumn.get(columnCount).setAggregateValue(value+1);
					}
				}
				
			columnCount++;
			}
			count++;
		}
		return listAggregateColumn;
	}


}
