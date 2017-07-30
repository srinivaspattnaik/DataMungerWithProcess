package com.stackroute.datamunger.query;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.stackroute.datamunger.data.AggregateColumn;
import com.stackroute.datamunger.data.Criteria;
import com.stackroute.datamunger.data.DataSet;
import com.stackroute.datamunger.data.HeaderRow;

public class Query 
{
	private QueryParam queryParam;
	private QueryProcessor queryProcessor;
	
	public QueryParam getQueryParam() 
	{
		return queryParam;
	}

	public void setQueryParam(QueryParam queryParam) 
	{
		this.queryParam = queryParam;
	}

	public DataSet executeQuery(String queryString)throws Exception
	{
			queryParam=queryParser(queryString);
			String queryType=queryParam.getQueryType();
			
			switch(queryType)
			{
			case "SIMPLE_QUERY":
				queryProcessor=new SimpleQueryProcessor();
				break;
			case "GROUP_BY_QUERY":	
				queryProcessor=new GroupByQueryProcessor();
				break;
			case "SIMPLEQUERY_WITHCONDITION":
				queryProcessor=new ConditionQueryProcessor();
				break;
			case "AGGREGATE_COLUMN":
				queryProcessor=new AggregateQueryProcessor();
				break;
				
			}
			
			DataSet dataSet=queryProcessor.executeQuery(queryParam);
			return dataSet;
	}
	
	private QueryParam queryParser(String queryString)throws Exception
	{	
		queryParam=new QueryParam();
		queryParam.setFilePath(getFilePath(queryString));
		queryParam.setGroupByColumn(getGroupByColumn(queryString));
		queryParam.setOrderByColumn(getOrderByColumn(queryString));
		queryParam.setCriterias(getCriteriasList(queryString));
		queryParam.setColumnList(getColumnList(queryString));
		queryParam.setHeaderRow(getHeaderRow(queryString));
		return queryParam;
	}

	private List<String> getColumnList(String queryString) 
	{
		String selectColumnString=queryString.split("select")[1].split("from")[0];
		List<String> columnList=this.fieldParsing(selectColumnString);
		return columnList;
	}

	private HeaderRow getHeaderRow(String queryString)throws Exception
	{
		BufferedReader bufferedReader=new BufferedReader(new FileReader(queryParam.getFilePath()));
		HeaderRow headerRow=new HeaderRow();
		
		if(bufferedReader!=null)
		{
			String rowData=bufferedReader.readLine();
			String rowValues[]=rowData.split(",");
			int columnIndex=0;
			for(String rowvalue:rowValues)
			{
				headerRow.put(rowvalue.toLowerCase(),columnIndex);
				columnIndex++;
			}
		}
		
		return headerRow;
	}

	private List<Criteria> getCriteriasList(String queryString) 
	{
		List<Criteria> listCriteria=null;
		if(queryString.contains("where"))
		{
			String conditionString=queryString.split("where")[1].split("order by")[0].split("group by")[0].trim();
			listCriteria=this.whereConditionStringParsing(conditionString);
			if(!(queryParam.getQueryType().equals("GROUP_BY_QUERY")))
			{
			queryParam.setQueryType("SIMPLEQUERY_WITHCONDITION");
			}
		}
		return listCriteria;
	}

	private String getOrderByColumn(String queryString) 
	{
		String orderByColumn=null;
		
		if(queryString.contains("order by"))
		{
			orderByColumn=queryString.split("order by")[1].split("group by")[0].trim();
		}
		return orderByColumn;
	}

	private String getGroupByColumn(String queryString) 
	{
		String groupByColumn=null;
		if(queryString.contains("group by"))
		{
			groupByColumn=queryString.split("group by")[1].trim();
			queryParam.setQueryType("GROUP_BY_QUERY");
		}
		return groupByColumn;
	}

	private String getFilePath(String queryString) 
	{
		String filePath=queryString.split("select")[1].split("from")[1].split("where")[0].split("order by")[0].split("group by")[0];
		return filePath.trim();
	}
	
	private List<Criteria> whereConditionStringParsing(String conditionString)
	{
		List<Criteria> listCriteria=new ArrayList<Criteria>();
		String relationalConditions[]=conditionString.trim().split("\\s+and\\s+|\\s+or\\s+");
		
		for(String condition:relationalConditions)
		{	
			condition=condition.trim();
			Criteria criteria=new Criteria();
			String expr[]=condition.split(">=|<=|>|<|!=|=");
			criteria.setColumnName(expr[0].toLowerCase().trim());
			criteria.setColumnValue(expr[1]);
			criteria.setOperator(condition.split(expr[0].trim())[1].split(expr[1].trim())[0]);
			listCriteria.add(criteria);
		}	
		
		int criteriaCount=listCriteria.size();
		if(criteriaCount>1)
		{
			int count=0;
			while(count<criteriaCount-1)
			{
				String logicalOperator=conditionString.split(relationalConditions[count])[1].split(relationalConditions[count+1])[0];
				queryParam.getLogicalOperator().add(logicalOperator.trim());
				count++;
			}
		}
		
		return listCriteria;
	}
	
	private List<String> fieldParsing(String selectFieldString)
	{
		List<String> selectFieldList=new ArrayList<String>();
		
		selectFieldString=selectFieldString.trim();
		
		if(selectFieldString.equals("*") && selectFieldString.length()==1)
		{
			return null;
		}
		else if(selectFieldString.contains("sum")||selectFieldString.contains("avg")||selectFieldString.contains("min")||selectFieldString.contains("max")||selectFieldString.contains("count"))
		{
			String columnList[]=selectFieldString.split(",");
			int count=0;
			int fieldCount=columnList.length;
			while(count<fieldCount)
			{
				if(!columnList[count].trim().equals(queryParam.getGroupByColumn()))
				{
				AggregateColumn aggregateColumn=new AggregateColumn();
				String aggregateColumnName=columnList[count].substring(columnList[count].indexOf('(')+1,columnList[count].indexOf(')'));
				aggregateColumn.setAggregateColumnName(aggregateColumnName.trim());
				aggregateColumn.setAggregateFunction(columnList[count].split("\\(")[0].trim());
				//aggregateColumn.setAggregatecolumnPosition(queryParam.getHeaderRow().get(aggregateColumnName));
				queryParam.getListAggregateColumn().add(aggregateColumn);
				}
				count++;
			}
			queryParam.setQueryType("AGGREGATE_COLUMN");
		}
		else
		{
			String columnList[]=selectFieldString.split(",");
			
			for(String columnName:columnList)
			{
				selectFieldList.add(columnName.trim().toLowerCase());
			}
		}
		
		return selectFieldList;
	}
}