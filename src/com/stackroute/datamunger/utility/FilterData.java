package com.stackroute.datamunger.utility;

import java.util.List;

import com.stackroute.datamunger.data.Criteria;
import com.stackroute.datamunger.data.HeaderRow;
import com.stackroute.datamunger.query.QueryParam;

public class FilterData 
{
	public static boolean evaluateWhereCondition(QueryParam queryParam,String rowValues[])
	{
		List<Criteria> listRelationalExpression=queryParam.getCriterias();
		HeaderRow headerRow=queryParam.getHeaderRow();
		List<String> logicalOperators=queryParam.getLogicalOperator();
		
		boolean expression=evaluateEachCondition(listRelationalExpression.get(0),rowValues,headerRow);
		
		int logicalOperatorCount=logicalOperators.size();
		int count=0;
		
		if(logicalOperatorCount>0)
		{
			while(count<logicalOperatorCount)
			{
				if(logicalOperators.get(count).equals("and"))
				{
					expression=expression && evaluateEachCondition(listRelationalExpression.get(count+1),rowValues,headerRow);
				}
				else
				{
					expression=expression || evaluateEachCondition(listRelationalExpression.get(count+1),rowValues,headerRow);
				}
				
				count++;
			}
		}
		
		return expression;
	}
	
	private static boolean evaluateEachCondition(Criteria condition,String rowValues[],HeaderRow headerRow)
	{
		
		boolean expression=false;
		
		String conditionColumnName=condition.getColumnName().trim().toLowerCase();
		String conditionOperator=condition.getOperator().trim();
		String conditionValue=condition.getColumnValue().trim();
			
			String columnValue=rowValues[headerRow.get(conditionColumnName)].trim();
			boolean isString=evaluateDataType(columnValue);
				
				if(isString)
				{
					if(conditionOperator.equals("="))
					{
						if(columnValue.equals(conditionValue))
						{
							expression=true;
						}
					}
					else if(conditionOperator.equals("!="))
					{
						if(!columnValue.equals(conditionValue))
						{
							expression=true;
						}
					}
				}
				else
				{
					float conditionParseValue=Float.parseFloat(conditionValue);
					float rowDataValue=Float.parseFloat(rowValues[headerRow.get(conditionColumnName)]);
					switch(conditionOperator)
					{
						case ">=":
							expression=rowDataValue>=conditionParseValue;
							break;
						case "<=":
							expression=rowDataValue<=conditionParseValue;
							break;
						case ">":
							expression=rowDataValue>conditionParseValue;
							break;
						case "<":
							expression=rowDataValue<conditionParseValue;
							break;
						case "=":
							expression=rowDataValue==conditionParseValue;
							break;
						case "!=":
							expression=rowDataValue!=conditionParseValue;
							break;
					}
				}
		
		return expression;
	}
	
	public static boolean evaluateDataType(String value)
	{
		try
		{
			Float f=Float.parseFloat(value);
			return false;
		}
		catch(Exception e)
		{
			return true;
		}
	}
	

}
