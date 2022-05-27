/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.entity;

import java.util.List;

public class SQLWrap {

	public String sql;
	
	
	public List<Object> parameters;
	
	
	public List<String> addtionsSqls;
	
	
	public List<List<Object>> batchParameters;
	

	public static SQLWrap newInstance(String sql, List<Object> parameters) {
		SQLWrap wrap = new SQLWrap();
		wrap.sql = sql;
		wrap.parameters = parameters;
		
		return wrap;
	}


	
	
	@Override
	public String toString() {
		return "SQLWrap [sql=" + sql + ", parameters=" + parameters + ", addtionsSqls=" + addtionsSqls + ", batchParameters=" + batchParameters + "]";
	}




	
	
	
	
}
