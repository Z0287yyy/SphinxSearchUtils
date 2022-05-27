/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.jdbc;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import chris.sphinxsearch.SphinxQL.entity.PageBean;
import chris.sphinxsearch.SphinxQL.entity.SQLWrap;
import chris.sphinxsearch.SphinxQL.entity.SphinxBaseEntity;
import chris.sphinxsearch.SphinxQL.pool.DBConnectionPool;
import chris.sphinxsearch.SphinxQL.sql.SphinxQLQuery;
import chris.sphinxsearch.SphinxQL.utils.GsonUtils;

public class SphinxQLExecutor extends JDBCExecutor {
	
	
	public boolean tableExist(String tableName) {
		Connection conn = null;
		List<List<? extends Object>> list = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = "SHOW TABLES LIKE '%" + tableName + "%'";
		
		try {
			conn = DBConnectionPool.getConnection();

			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			list = resultSetToList(rs);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("findListByList查询：" + sql + "出错::" + e.getLocalizedMessage());
		} finally {
			DBConnectionPool.close(conn, stmt, rs);
		}
		return list != null && list.size() > 1;
	}
	
	
	//
	public <T extends SphinxBaseEntity> List<T> find(Class<? extends T> clazz, PageBean pageBean, SQLWrap sqlWrap) {
		List<Map<String, Object>> res = findMap(pageBean, sqlWrap);
		List<T> result = GsonUtils.fromJson(GsonUtils.toJson(res), TypeToken.getParameterized(List.class, clazz).getType());
		return result;
	}
	
	
//方法，得到数据LIST，每条记录为MAP	

	public List<Map<String, Object>> findMap(PageBean pageBean, SQLWrap sqlWrap) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<Map<String, Object>> vec = new ArrayList<>();

		try {
			conn = DBConnectionPool.getConnection();
			
			String sql = sqlWrap.sql;

			// System.out.println("zzzzz:"+sql);
			if (pageBean != null) {
				pageBean.setTotalCount(getRowsCountMap(SphinxQLQuery.getCountQueryString(sql), sqlWrap.parameters));
				sql += " limit " + pageBean.getStart() + "," + pageBean.getPageSize();
			}

			stmt = conn.prepareStatement(sql);
			if (sqlWrap.parameters != null && sqlWrap.parameters.size() > 0) {
				for (int i = 0; i < sqlWrap.parameters.size(); i++) {
//					System.out.println(param.length);
//					stmt.setString(i + 1, param[i].toString());
					stmt.setObject(i + 1, sqlWrap.parameters.get(i));
//					System.out.println("2222");
				}
			}
			rs = stmt.executeQuery();
			vec = resultSetToVector(rs);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("findMap查询：" + sqlWrap + "出错::" + e.getLocalizedMessage());
		} finally {
			DBConnectionPool.close(conn, stmt, rs);
		}

		return vec;
	}

////////////////////////////////
//	方法，得到数据LIST，每条记录为LIST,没有识别为查询顺序

	public List<List<? extends Object>> findList(PageBean pageBean, String sql, Object... param) {
		Connection conn = null;
		List<List<? extends Object>> list = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBConnectionPool.getConnection();

			if (pageBean != null) {
				pageBean.setTotalCount(getRowsCountList(SphinxQLQuery.getCountQueryString(sql)));
				sql += " limit " + pageBean.getStart() + "," + pageBean.getPageSize();
			}

			stmt = conn.prepareStatement(sql);
			if (param != null && param.length > 0) {
				if (param[0] instanceof List) {
					List<Object> conditiond = (List<Object>) param[0];
					for (int i = 0; i < conditiond.size(); i++) {
						stmt.setString(i + 1, conditiond.get(i).toString());
					}
				} else {
					for (int i = 0; i < param.length; i++) {
						stmt.setString(i + 1, param[i].toString());
					}
				}
			}
			rs = stmt.executeQuery();
			list = resultSetToList(rs);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("findListByList查询：" + sql + "出错::" + e.getLocalizedMessage());
		} finally {
			DBConnectionPool.close(conn, stmt, rs);
		}
		return list;
	}

	

}