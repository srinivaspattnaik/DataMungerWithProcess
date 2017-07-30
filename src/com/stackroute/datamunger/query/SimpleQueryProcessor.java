package com.stackroute.datamunger.query;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.stackroute.datamunger.data.DataRow;
import com.stackroute.datamunger.data.DataSet;
import com.stackroute.datamunger.data.HeaderRow;
import com.stackroute.datamunger.utility.SortData;

public class SimpleQueryProcessor implements QueryProcessor 
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
			
			if(queryParam.getColumnList()==null)
			{
				while((row=bufferedReader.readLine())!=null)
				{
					rowData=new DataRow();
					String rowValues[]=row.trim().split(",");
					int count=0;
					while(count<columnCount)
					{
						rowData.put(count,rowValues[count].trim());
						count++;
					}
					dataSet.getResultSet().add(rowData);
				}
			}
			else
			{
				while((row=bufferedReader.readLine())!=null)
				{
					rowData=new DataRow();
					String rowValues[]=row.trim().split(",");
					List<String> columnList=queryParam.getColumnList();
					
					for(String columnName:columnList)
					{
						for(String actualColumnName:columnNames)
						{
							if(actualColumnName.equals(columnName))
							{
								rowData.put(headerRow.get(columnName),rowValues[headerRow.get(columnName)].trim());
							}
						}
					}	
					dataSet.getResultSet().add(rowData);
				}
			}
			
			if(queryParam.getOrderByColumn()!=null)
			{
				SortData sortData=new SortData();
				sortData.setSortingIndex(queryParam.getHeaderRow().get(queryParam.getOrderByColumn()));
				Collections.sort(dataSet.getResultSet(),sortData);
			}
			
		return dataSet;
	}
}
