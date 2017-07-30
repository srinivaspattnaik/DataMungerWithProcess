package com.stackroute.datamunger.query;

import com.stackroute.datamunger.data.DataSet;

public interface QueryProcessor 
{
	public DataSet executeQuery(QueryParam queryParam) throws Exception;
}
