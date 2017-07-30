package com.statckroute.datamunger.test;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.stackroute.datamunger.data.AggregateColumn;
import com.stackroute.datamunger.data.DataRow;
import com.stackroute.datamunger.data.DataSet;
import com.stackroute.datamunger.query.Query;

public class QueryProcessTestCase 
{
	static Query query;
	
	@BeforeClass
	public static void initialize()
	{
		query=new Query();
	}
	
	@Test
	public void simpleQueryParsingwithAllColumns()throws Exception
	{
		String queryString="select * from c:\\Emp.csv";
		DataSet dataSet=query.executeQuery(queryString);
		assertNotNull(dataSet);
		System.out.println(queryString);
		displayAllResultSetData(dataSet);
	}
	
	@Test
	public void simpleQueryParsingwithCertainColumns()throws Exception
	{
		String queryString="select EmpID,EmpName from c:\\Emp.csv";
		DataSet dataSet=query.executeQuery(queryString);
		assertNotNull(dataSet);
		System.out.println(queryString);
		displayAllResultSetData(dataSet);
	}
	
	@Test
	public void orderByQueryParsingwithCertainColumns()throws Exception
	{
		String queryString="select empid,city from c:\\emp1.csv order by city";
		DataSet dataSet=query.executeQuery(queryString);
		assertNotNull(dataSet);
		System.out.println(queryString);
		displayAllResultSetData(dataSet);
	}
	
	@Test
	public void orderByQueryParsingwithAllColumns()throws Exception
	{
		String queryString="select * from c:\\emp1.csv order by salary";
		DataSet dataSet=query.executeQuery(queryString);
		assertNotNull(dataSet);
		System.out.println(queryString);
		displayAllResultSetData(dataSet);
	}
	
	@Test
	public void simpleQueryParsingwithAllColumnsWithWhereClause()throws Exception
	{
		String queryString="select * from c:\\emp1.csv where Salary>20000 and Salary<38000 or City=Hyderabad";
		DataSet dataSet=query.executeQuery(queryString);
		assertNotNull(dataSet);
		System.out.println(queryString);
		displayAllResultSetData(dataSet);
	}
	
	@Test
	public void simpleQueryParsingwithSelectedColumnsWithWhereClause()throws Exception
	{
		String queryString="select empid,city,name from c:\\emp1.csv where Salary>20000 and Salary<38000 or City=Hyderabad";
		DataSet dataSet=query.executeQuery(queryString);
		assertNotNull(dataSet);
		System.out.println(queryString);
		displayAllResultSetData(dataSet);
	}
	
	@Test
	public void aggregateDataDisplay()throws Exception
	{
		String queryString="select sum(salary),min(salary),max(salary) from c:\\emp1.csv";
		DataSet dataSet=query.executeQuery(queryString);
		assertNotNull(dataSet.getAggregateRow());
		System.out.println(queryString);
		displayAllResultSetData(dataSet);
	}
	
	@Test
	public void aggregateDataDisplayWithWhereClause()throws Exception
	{
		String queryString="select sum(salary),min(salary),max(salary),count(city) from c:\\emp1.csv where city=Bangalore";
		DataSet dataSet=query.executeQuery(queryString);
		assertNotNull(dataSet.getAggregateRow());
		System.out.println(queryString);
		displayAllResultSetData(dataSet);
	}

	public void displayAllResultSetData(DataSet dataSet)
	{
		int i=0;
		int j=1;
		
		if(dataSet.getAggregateRow().isEmpty())
		{
			for(DataRow rowData:dataSet.getResultSet())
			{
				Set<Integer> rowIndex=rowData.keySet();
				
				for(int index:rowIndex)
				{
					System.out.print(rowData.get(index)+"\t");
				}
				
				System.out.println();
			}
		
		}
		else
		{
			List<AggregateColumn> listAggregateColumn=dataSet.getAggregateRow();
			
			int count=0;
			while(count<listAggregateColumn.size())
			{
				System.out.print(listAggregateColumn.get(count).getAggregateValue()+"	");
				count++;
			}
		}
	}

}
