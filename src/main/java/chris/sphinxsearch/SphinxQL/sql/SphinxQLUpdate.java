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

public class SphinxQLUpdate {

	
//	生成修改语句
	public static String updateSqlString(SphinxBaseEntity o) {
		if (o == null) {
			return null;
		}
		return updateSqlString(o.getClass().getSimpleName(), "id", o.id, o, true);
	}
	
	public static String updateSqlString(SphinxBaseEntity o, boolean setNullValue) {
		if (o == null) {
			return null;
		}
		return updateSqlString(o.getClass().getSimpleName(), "id", o.id, o, setNullValue);
	}
	
	public static String updateSqlString(String tableName, String fieldName, Object fieldValue, SphinxBaseEntity o, boolean setNullValue) {
		if (o == null || tableName == null) {
			return null;
		}
		
		Collection<Field> ff = SUReflectUtils.getAllFields(o.getClass());
		StringBuilder sql = new StringBuilder("update " + tableName + " set ");
		int i = 0;
		for (Field f: ff) {
			if (f.getName().equals("id")) {
				continue;
			}
			
			String propety = f.getName();
			try {
				Object ot = SUReflectUtils.getFieldValue(o, f);
				
				// 不设置空值到数据库
				if (ot == null && !setNullValue) {
					continue;
				}
				
				if (i > 0) {
					sql.append(", ");
				}
				
				sql.append(propety + " " + getSqlFieldValue(ot, SqlMode.update));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.err.println("sqlUpdateString生成修改语句:属性" + propety + "出错::" + e.getLocalizedMessage());
			}
			
			i ++;
		}
		
		sql.append(" where " + fieldName + "='" + fieldValue + "'");
//		System.out.println(sql);
		return sql.toString();
	}

	
	
	public static SQLWrap updateSqlWrap(SphinxBaseEntity o) {
		return updateSqlWrap(o, true);
	}
	
	public static SQLWrap updateSqlWrap(SphinxBaseEntity o, boolean setNullValue) {
		if (o == null) {
			return null;
		}
		return updateSqlWrap(o.getClass().getSimpleName(), "id", o.id, o, setNullValue);
	}
	
	public static SQLWrap updateSqlWrap(String tableName, String fieldName, Object fieldValue, SphinxBaseEntity o, boolean setNullValue) {
		if (o == null || tableName == null) {
			return null;
		}
		
		List<Object> params = new ArrayList<>();
		
		Collection<Field> ff = SUReflectUtils.getAllFields(o.getClass());
		StringBuilder sql = new StringBuilder("update " + tableName + " set ");
		int i = 0;
		for (Field f: ff) {
			if (f.getName().equals("id")) {
				continue;
			}
			
			String propety = f.getName();
			try {
				Object ot = SUReflectUtils.getFieldValue(o, f);
				
				// 不设置空值到数据库
				if (ot == null && !setNullValue) {
					continue;
				}
				
				if (i > 0) {
					sql.append(", ");
				}
				
				params.add(getSqlFieldValue(ot, SqlMode.param));
				sql.append(propety + " = ?");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.err.println("sqlUpdateString生成修改语句:属性" + propety + "出错::" + e.getLocalizedMessage());
			}
			
			i ++;
		}
		
		if (i <= 0) {
			return null;
		}
		
		sql.append(" where " + fieldName + " = ?");
		params.add(fieldValue);
		
//		System.out.println(sql);
		return SQLWrap.newInstance(sql.toString(), params);
	}
	
	
	public static SQLWrap batchUpdateSqlWrap(List<? extends SphinxBaseEntity> os) {
		if (os == null || os.size() <= 0) {
			return null;
		}
		
		return batchUpdateSqlWrap(os.get(0).getClass().getSimpleName(), "id", os);
	}
	

	public static SQLWrap batchUpdateSqlWrap(String tableName, String fieldName, List<? extends SphinxBaseEntity> os) {
		if (os == null || os.size() <= 0) {
			return null;
		}
		
		List<List<Object>> params = new ArrayList<>();
		List<Object> filedValues = new ArrayList<>();
		
		Collection<Field> ff = SUReflectUtils.getAllFields(os.get(0).getClass());
		StringBuilder sql = new StringBuilder("update " + tableName + " set ");
		int i = 0;
		for (Field f: ff) {
			String propety = f.getName();
			
			if (propety.equals(fieldName)) {
				for (int j = 0; j < os.size(); j++) {
					Object val = null;
					try {
						SphinxBaseEntity o = os.get(j);
						val = SUReflectUtils.getFieldValue(o, f);
					} catch (Exception e) {
						System.err.println("sqlUpdateString生成修改语句:属性" + propety + "出错::" + e.getLocalizedMessage());
					}
					filedValues.add(val);
				}
				continue;
			}
			
			if (i > 0) {
				sql.append(", ");
			}
			
			sql.append(propety + " = ?");
			
			for (int j = 0; j < os.size(); j++) {
				Object ot = null;
				
				List<Object> param;
				if (params.size() > j) {
					param = params.get(j);
				} else {
					param = new ArrayList<>();
					params.add(param);
				}
				
				try {
					SphinxBaseEntity o = os.get(j);
					ot = SUReflectUtils.getFieldValue(o, f);
				} catch (Exception e) {
					System.err.println("sqlUpdateString生成修改语句:属性" + propety + "出错::" + e.getLocalizedMessage());
				}
				param.add(getSqlFieldValue(ot, SqlMode.param));
			}
			
			i ++;
		}
		
		if (i <= 0) {
			return null;
		}
		
		sql.append(" where id = ?");
		
		for (int j = 0; j < os.size(); j++) {
			List<Object> param = params.get(j);
			param.add(filedValues.get(j));
		}
		
//		System.out.println(sql);
		SQLWrap sqlWrap = SQLWrap.newInstance(sql.toString(), null);
		sqlWrap.batchParameters = params;
		
		return sqlWrap;
	}
	
}