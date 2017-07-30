package com.stackroute.datamunger.query;

import java.util.ArrayList;
import java.util.List;

import com.stackroute.datamunger.data.AggregateColumn;
import com.stackroute.datamunger.data.Criteria;
import com.stackroute.datamunger.data.HeaderRow;

public class QueryParam 
{
	private String queryString;
	private String filePath;
	private String orderByColumn;
	private String groupByColumn;
	
	private List<Criteria> criterias;
	private List<String> columnList;
	private String queryType="SIMPLE_QUERY";
	private HeaderRow headerRow;
	
	private List<String> logicalOperator=new ArrayList<String>();
	private List<AggregateColumn> listAggregateColumn=new ArrayList<AggregateColumn>();
	
	
	public List<AggregateColumn> getListAggregateColumn() {
		return listAggregateColumn;
	}

	public void setListAggregateColumn(List<AggregateColumn> listAggregateColumn) {
		this.listAggregateColumn = listAggregateColumn;
	}

	public List<String> getLogicalOperator() {
		return logicalOperator;
	}

	public void setLogicalOperator(List<String> logicalOperator) {
		this.logicalOperator = logicalOperator;
	}

	public HeaderRow getHeaderRow() 
	{
		return headerRow;
	}

	public List<String> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}

	public void setHeaderRow(HeaderRow headerRow) {
		this.headerRow = headerRow;
	}

	public String getQueryType() 
	{
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public List<Criteria> getCriterias() {
		return criterias;
	}

	public void setCriterias(List<Criteria> criterias) {
		this.criterias = criterias;
	}

	public String getGroupByColumn() 
	{
		return groupByColumn;
	}

	public void setGroupByColumn(String groupByColumn) 
	{
		this.groupByColumn = groupByColumn;
	}

	public String getOrderByColumn() 
	{
		return orderByColumn;
	}

	public void setOrderByColumn(String orderByColumn) {
		this.orderByColumn = orderByColumn;
	}

	public String getQueryString() 
	{
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
