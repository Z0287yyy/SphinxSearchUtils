/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import chris.sphinxsearch.SphinxQL.utils.DateTool;
import chris.sphinxsearch.SphinxQL.utils.SUReflectUtils;
import chris.sphinxsearch.SphinxQL.utils.StringTool;

public class SphinxQLUtils {

	
	
///////////////////////////////
//////////////////////////////

//	根据SQL语句，与OBJECT(FORM)自动生成需要的List
	public static List<Object> sqlList(String sql, Object o) {
		if (StringTool.getIndex(sql, "insert") != -1) {
			return sqlInsertList(sql, o);
		} else if (StringTool.getIndex(sql, "update") != -1) {
			return sqlUpdateList(sql, o);
		}
		return null;
	}

//插入语句的	List自动生成
	public static List<Object> sqlInsertList(String sql, Object o) {
		List<Object> vector = new ArrayList<>();
		String tmpsql = sql.substring(sql.indexOf("("));
//		System.out.println(sql);
		if (tmpsql.indexOf("values") == -1) { // 全部插入
			try {
				String sqlValues = sql.substring(sql.indexOf("(") + 1, sql.indexOf(")"));
				StringTokenizer st = new StringTokenizer(sqlValues, ",");
//				System.out.println("全部插入");
				
				if (o instanceof Map) {
					Map<Object, Object> m = (Map<Object, Object>) o;
					for (Object key: m.keySet()) {
						String sqlValue = st.nextToken().trim();
						if (sqlValue == null || sqlValue.equals("null")) {
							continue;
						}
						if (!sqlValue.equals("?")) {
							continue;
						}
						
						vector.add(m.get(key));
//						System.out.println("AutoTool成功赋值："+propety+"，值："+m.invoke(o, null));
					}
				} else {
					Collection<Field> fields = SUReflectUtils.getAllFields(o.getClass());
					for (Field filed: fields) {
						String sqlValue = st.nextToken().trim();
						if (sqlValue == null || sqlValue.equals("null")) {
							continue;
						}
						if (!sqlValue.equals("?")) {
							continue;
						}
						String propety = filed.getName();
						vector.add(SUReflectUtils.getFieldValue(o, propety));
//						System.out.println("AutoTool成功赋值："+propety+"，值："+m.invoke(o, null));
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
//			System.out.println("选择插入");
			tmpsql = sql.substring(sql.indexOf("(") + 1, sql.indexOf(")"));
//			System.out.println(tmpsql);
			StringTokenizer kst = new StringTokenizer(tmpsql, ",");
			///
			String sqlValues = sql.substring(sql.lastIndexOf("(") + 1, sql.lastIndexOf(")"));
			StringTokenizer vst = new StringTokenizer(sqlValues, ",");
			while (kst.hasMoreElements()) {
				try {
					String propety = kst.nextToken().trim();
					String value = vst.nextToken().trim();
					if (!value.equals("?")) {
						continue;
					}
					vector.add(SUReflectUtils.getFieldValue(o, propety));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.err.println("sqlInsertList自动生成INSERT语句出错::" + e.getLocalizedMessage());
				}
			}

		}
//		for(int i=0;i<vector.size();i++) {
//			System.out.println(vector.get(i));
//		}
		return vector;
	}

//	更新语句的	List自动生成
	public static List<Object> sqlUpdateList(String sql, Object o) {
		List<Object> vector = new ArrayList<>();
		String tmpsql = null;
//		System.out.println(sql);
		int tmp = sql.lastIndexOf("where");
		if (tmp == -1) {
			tmp = sql.lastIndexOf(";");
		}
		if (tmp != -1) {
			tmpsql = sql.substring(sql.indexOf("set") + 4, tmp).trim();
		} else {
			tmpsql = sql.substring(sql.indexOf("set") + 4).trim();
		}
		StringTokenizer st = new StringTokenizer(tmpsql, ",");
		while (st.hasMoreTokens()) {
			tmpsql = st.nextToken();
			String key = tmpsql.substring(0, tmpsql.indexOf("=")).trim();
			String value = tmpsql.substring(tmpsql.indexOf("=") + 1).trim();
			if (value.equals("?")) {
				try {
					vector.add(SUReflectUtils.getFieldValue(o, key));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				System.out.println(key+"   "+value);
			}
		}
		// 第二部分
		tmp = sql.lastIndexOf("where");
		if (tmp != -1) {
			int temp = sql.lastIndexOf(";");
			if (temp != -1) {
				tmpsql = sql.substring(tmp + 5, temp).trim();
			} else {
				tmpsql = sql.substring(tmp + 5).trim();
			}
			st = new StringTokenizer(tmpsql, ",");
			while (st.hasMoreTokens()) {
				tmpsql = st.nextToken();
				String key = tmpsql.substring(0, tmpsql.indexOf("=")).trim();
				String value = tmpsql.substring(tmpsql.indexOf("=") + 1).trim();
				if (value.equals("?")) {
					try {
						vector.add(SUReflectUtils.getFieldValue(o, key));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.err.println("sqlUpdateList，出错!");
					}
				}
			}
		}
//		System.out.println(tmpsql);
		return vector;
	}
	
	public static enum SqlMode {
		insert,
		query,
		update,
		param,
	}
	
	// 判断类型，返回不同的值
	public static Object getSqlFieldValue(Object value, SqlMode mode) {
		// ? 参数模式，直接返回对象
		if (mode == SqlMode.param) {
			Object res = value;
			if (res != null) {
				if (value instanceof Integer || value instanceof Short || value instanceof Long || value instanceof Float || value instanceof Double || value instanceof Boolean) {
					// do nothing
				} else if (value instanceof Date) {
					res = DateTool.getTimeString((Date) value);
				} else {
					res = value.toString();
				}
			}
			return res;
		}
		
		// 其余返回拼接字符串
		String prefix = "= ";
		if (mode == SqlMode.insert) {
			prefix = "";
		}
		
		String sql;
		if (value == null) {
			if (mode == SqlMode.query) {
				prefix = "is ";
			} else {
				prefix = "= ";
			}
			sql = "null";
		} else if (value instanceof Integer || value instanceof Short || value instanceof Long || value instanceof Float || value instanceof Double || value instanceof Boolean) {
			sql = value.toString();
		} else if (value instanceof Date) {
			sql = "'" + DateTool.getTimeString((Date) value) + "'";
		} else {
			sql = "'" + value + "'";
		}
		
		return prefix + sql;
	}
	

	public static void main(String[] args) {
		// Class clazz=HQLConditionTool.class;
//		System.out.println(insertSqlString(tool));
//		System.out.println(clazz.getName().substring(clazz.getName().lastIndexOf(".")+1));
//		System.out.println(clazz.getSimpleName());
	}

}