/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.sql;

import static chris.sphinxsearch.SphinxQL.sql.SphinxQLUtils.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import chris.sphinxsearch.SphinxQL.desc.SphinxQLAttributes;
import chris.sphinxsearch.SphinxQL.entity.SQLWrap;
import chris.sphinxsearch.SphinxQL.entity.SphinxBaseEntity;
import chris.sphinxsearch.SphinxQL.utils.DateTool;
import chris.sphinxsearch.SphinxQL.utils.ObjectUtils;
import chris.sphinxsearch.SphinxQL.utils.SUReflectUtils;
import chris.sphinxsearch.SphinxQL.utils.StringTool;

public class SphinxQLDelete {

	

//	生成删除语句
	public static String deleteSqlString(SphinxBaseEntity o) {
		if (o == null) {
			return null;
		}
		return deleteSqlString(o.getClass().getSimpleName(), "id", o.id, o);
	}
	
	public static String deleteSqlString(String tableName, String fieldName, Object fieldValue, SphinxBaseEntity o) {
		if (tableName == null || fieldName == null) {
			return null;
		}
		
		StringBuilder sql = new StringBuilder("delete from " + tableName + " where " + fieldName + " " + getSqlFieldValue(fieldValue, SqlMode.query));
		return sql.toString();
	}
	
	
	public static SQLWrap deleteSqlWrap(SphinxBaseEntity o) {
		if (o == null) {
			return null;
		}
		return deleteSqlWrap(o.getClass().getSimpleName(), "id", o.id);
	}
	
	public static SQLWrap deleteSqlWrap(String tableName, String fieldName, Object fieldValue) {
		if (tableName == null || fieldName == null) {
			return null;
		}
		
		StringBuilder sql = new StringBuilder("delete from " + tableName + " where " + fieldName + " = ?");
		List<Object> params = new ArrayList<>();
		params.add(fieldValue);
		
		return SQLWrap.newInstance(sql.toString(), params);
	}
	
	
}