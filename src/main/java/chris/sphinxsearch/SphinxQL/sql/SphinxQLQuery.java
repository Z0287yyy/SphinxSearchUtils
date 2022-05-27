/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.sql;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import chris.sphinxsearch.SphinxQL.desc.SphinxQLAttributes;
import chris.sphinxsearch.SphinxQL.entity.SphinxBaseEntity;
import chris.sphinxsearch.SphinxQL.utils.DateTool;
import chris.sphinxsearch.SphinxQL.utils.ObjectUtils;
import chris.sphinxsearch.SphinxQL.utils.SUReflectUtils;
import chris.sphinxsearch.SphinxQL.utils.StringTool;

public class SphinxQLQuery {

	

//	生产获取查询结果条数的SQL语句
	public static String getCountQueryString(String SQL) {
		SQL = SQL.toLowerCase();
		StringBuilder result = new StringBuilder();

		int union = SQL.indexOf("union");
		int groupBy = SQL.indexOf("group by");
		if (union > 0 || groupBy > 0) {
			result.append("select count(*) as result from (" + SQL + ") as model");
		} else {
			// String select=SQL.substring(SQL.indexOf("select"),
			// SQL.indexOf("from")).trim();
			SQL = SQL.substring(SQL.indexOf("from"));
			result.append("select count(*) as result " + SQL);
		}
		// System.out.println("result:"+result);
		return result.toString();
	}

//	通过Map获得模糊HQL条件语句
	public static String getFuzzyHQLCondition(Map<String, String> m) {
		StringBuilder result = new StringBuilder();
		for (Map.Entry<String, String> condition : m.entrySet()) {
			if (!condition.getKey().toString().equals("")) {

				if (condition.getValue() != null) {
					result.append(
							condition.getKey().toString() + " like '%" + condition.getValue().toString() + "%' and ");
				} else {
					result.append(condition.getKey().toString() + " is null and ");
				}
			}
		}
		if (result.indexOf("and") >= 0) {
			result.delete(result.lastIndexOf("and"), result.length());
		}
		return result.toString();
	}

//	通过Map获得模糊HQL条件语句,条件带对象前缀，此方法不管
	public static String getFuzzyHQLCondition(String objName, Map<String, String> m) {
		StringBuilder result = new StringBuilder();
		for (Map.Entry<String, String> condition : m.entrySet()) {
			if (!condition.getKey().toString().equals("")) {

				if (condition.getValue() != null) {
					result.append(objName + "." + condition.getKey().toString() + " like '%"
							+ condition.getValue().toString() + "%' and ");
				} else {
					result.append(objName + "." + condition.getKey().toString() + " is null and ");
				}
			}
		}
		result.delete(result.lastIndexOf("and"), result.length());
		return result.toString();
	}
///////////

	public static void main(String[] args) {
		getCountQueryString("select `column_id`,`menu_id` from `article1_column` group by `menu_id`");
	}

}