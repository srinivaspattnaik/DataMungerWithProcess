package com.statckroute.datamunger.test;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.stackroute.datamunger.data.Criteria;
import com.stackroute.datamunger.query.Query;
import com.stackroute.datamunger.query.QueryParam;

public class QueryParserTestCase 
{
	static Query query;
	
	@BeforeClass
	public static void initialize()
	{
		query=new Query();
	}
	
	/*@Test
	public void simpleQueryParsingwithAllColumns()throws Exception
	{
		query.executeQuery("select * from c:\\Emp.csv");
		QueryParam queryParam=query.getQueryParam();
		displayAllData("select * from c:\\Emp.csv",queryParam);
	}
	
	@Test
	public void simpleQueryParsingWithSelectedColumns()throws Exception
	{
		query.executeQuery("select EmpID,EmpName from c:\\Emp.csv");
		QueryParam queryParam=query.getQueryParam();
		displayAllData("select EmpID,EmpName from c:\\Emp.csv",queryParam);
	}*/
	
	@Test
	public void simpleQueryParsingwithAllColumnsWithWhereClause()throws Exception
	{
		query.executeQuery("select * from c:\\Emp.csv where Salary>20000 and Salary<38000 or City=Hyderabad");
		QueryParam queryParam=query.getQueryParam();
		displayAllData("select * from c:\\Emp.csv where Salary>20000 and Salary<38000 or City=Hyderabad",queryParam);
	}
	
	/*@Test
	public void simpleQueryWithSelectedColumnsWithWhereClause()throws Exception
	{
		query.executeQuery("select EmpID,EmpName from c:\\Emp.csv where Salary>20000");
		QueryParam queryParam=query.getQueryParam();
		displayAllData("select EmpID,EmpName from c:\\Emp.csv where Salary>20000",queryParam);
	}
	
	@Test
	public void simpleQueryWithAllColumnsWithOrderBy()throws Exception
	{
		query.executeQuery("select * from c:\\Emp.csv  where Salary>20000 order by Salary");
		QueryParam queryParam=query.getQueryParam();
		displayAllData("select * from c:\\Emp.csv  where Salary>20000 order by Salary",queryParam);
	}*/
	
	
	public void displayAllData(String query,QueryParam queryParam)
	{
		System.out.println("Query:"+query);	
		System.out.println("File Path:"+queryParam.getFilePath());
		
		List<Criteria> listCriterias=queryParam.getCriterias();
		if(listCriterias!=null)
		{
			for(Criteria criteria:listCriterias)
			{
			System.out.println("Column Name:"+criteria.getColumnName()+":"+"Column Value:"+criteria.getColumnValue()+" Column Operator:"+criteria.getOperator());
			}
		}
		
		System.out.println("Order By Column:"+queryParam.getOrderByColumn());
		System.out.println("Group By Column:"+queryParam.getGroupByColumn());
		System.out.println("Query Type:"+queryParam.getQueryType());
		
		System.out.println("Select Fields:"+queryParam.getColumnList());
		
	}
	

}