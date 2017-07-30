package com.stackroute.datamunger.utility;

import java.util.Comparator;
import com.stackroute.datamunger.data.DataRow;

public class SortData implements Comparator<DataRow> 
{
	private int sortingIndex;
	
	public int getSortingIndex() 
	{
		return sortingIndex;
	}

	public void setSortingIndex(int sortingIndex) 
	{
		this.sortingIndex = sortingIndex;
	}

	@Override
	public int compare(DataRow arg0, DataRow arg1) 
	{
		if(FilterData.evaluateDataType(arg0.get(sortingIndex)))
		{
		return arg0.get(sortingIndex).compareTo(arg1.get(sortingIndex));
		}
		else
		{
			Float argument1=Float.parseFloat(arg0.get(sortingIndex));
			Float argument2=Float.parseFloat(arg1.get(sortingIndex));
		return argument1.compareTo(argument2);	
		}
	}

}
