/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.jdbc;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import chris.sphinxsearch.SphinxQL.entity.PageBean;
import chris.sphinxsearch.SphinxQL.entity.SQLWrap;
import chris.sphinxsearch.SphinxQL.entity.SphinxBaseEntity;
import chris.sphinxsearch.SphinxQL.pool.DBConnectionPool;

public abstract class JDBCExecutor {
	
	public static final int BatchSize = 1000;
	

	private static volatile JDBCExecutor currentExecute;
	
	public static JDBCExecutor getDBExecute() {
		if (currentExecute == null) {
			currentExecute = new SphinxQLExecutor();
		}
		return currentExecute;
	}
	
	// 数据表是否存在
	abstract public boolean tableExist(String tableName);
	

//方法，得到数据LIST，每条记录为MAP	
	public List<Map<String, Object>> findMap(String sql) {
		return findMap(null, sql, null);
	}

	public List<Map<String, Object>> findMap(PageBean pageBean, String sql) {
		return findMap(pageBean, sql, null);
	}

	public List<Map<String, Object>> findMapByParams(String sql, Object... param) {
		return findMap(null, sql, param);
	}

	public List<Map<String, Object>> findMap(PageBean pageBean, String sql, Object... param) {
		List<Object> params = new ArrayList<>();
		if (param != null) {
			Collections.addAll(params, param);
		}
		return findMap(pageBean, SQLWrap.newInstance(sql, params));
	}
	
	abstract public List<Map<String, Object>> findMap(PageBean pageBean, SQLWrap sqlWrap);
	

////////////////////////////////
//	方法，得到数据LIST，每条记录为LIST,没有识别为查询顺序
	public List<List<? extends Object>> findList(String sql) {
		return findList(null, sql, null);
	}

	public List<List<? extends Object>> findList(PageBean pageBean, String sql) {
		return findList(pageBean, sql, null);
	}

	public List<List<? extends Object>> findListByParams(String sql, Object... param) {
		return findList(null, sql, param);
	}

	abstract public List<List<? extends Object>> findList(PageBean pageBean, String sql, Object... param);

///////////////////////
	public boolean excute(String sql) {
		boolean result = false;
		Connection conn = null;
		Statement stm = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionPool.getConnection();
			conn.setAutoCommit(false);

			stm = conn.createStatement();

			result = stm.execute(sql);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("callSql执行出错::" + e.getLocalizedMessage());
			try {
				conn.rollback();
			} catch (Exception ex) {
				System.err.println("无法回滚::" + e.getLocalizedMessage());
			}
		} finally {
			DBConnectionPool.close(conn, stm, rs);
		}
		return result;
	}
	
//	增删改
	public boolean executeByParams(String sql, Object... param) {
		if (sql == null || sql.length() <= 0) {
			return false;
		}
		List<Object> params = new ArrayList<>();
		if (param != null) {
			Collections.addAll(params, param);
		}
		return execute(SQLWrap.newInstance(sql, params));
	}
	
	public boolean execute(SQLWrap sqlWrap) {
		if (sqlWrap == null || sqlWrap.sql == null || sqlWrap.sql.length() <= 0) {
			return false;
		}
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnectionPool.getConnection();
			conn.setAutoCommit(false);
			
			String sql = sqlWrap.sql;
			
			stmt = conn.prepareStatement(sql);
			if (sqlWrap.parameters != null && sqlWrap.parameters.size() > 0) {
				if (sqlWrap.parameters.get(0) instanceof List) {
					List conditiond = (List) sqlWrap.parameters.get(0);
					for (int i = 0; i < conditiond.size(); i++) {
						if (conditiond.get(i) == null)
							stmt.setNull(i + 1, Types.NULL);
						else
							stmt.setObject(i + 1, conditiond.get(i));
					}
				} else {
					for (int i = 0; i < sqlWrap.parameters.size(); i++) {
						if (sqlWrap.parameters.get(i) == null)
							stmt.setNull(i + 1, Types.NULL);
						else
							stmt.setObject(i + 1, sqlWrap.parameters.get(i));
					}
				}
			}
			result = stmt.execute();
			
			conn.commit();
		} catch (Exception e) {
			// e.printStackTrace();
			System.err.println("saveDelete执行语句：" + sqlWrap.sql + "出错::" + e.getLocalizedMessage());
			try {
				conn.rollback();
			} catch (Exception ex) {
				System.err.println("无法回滚::" + e.getLocalizedMessage());
			}
		} finally {
			DBConnectionPool.close(conn, stmt, rs);
		}
		
		return result;
	}

//	批量删除和插入，无参数
	public int[] batchExecute(List<String> sqls) {
		if (sqls == null || sqls.size() <= 0) {
			return null;
		}
		
		int[] result = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionPool.getConnection();
			
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			for (int i = 0; i < sqls.size(); i++) {
				stmt.addBatch(sqls.get(i));
				
				if (i > 0 && i % BatchSize == 0) {
					stmt.executeBatch();
					conn.commit();
				}
			}
			result = stmt.executeBatch();
			conn.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.err.println("saveDeleteBatch执行出错::" + e.getLocalizedMessage());
			try {
				conn.rollback();
			} catch (Exception ex) {
				System.err.println("无法回滚::" + e.getLocalizedMessage());
			}
		} finally {
			DBConnectionPool.close(conn, stmt, rs);
		}
		return result;
	}

//	批量删除和插入，同一条语句参数不同
	public int[] batchExecute(String sql, List<List<Object>> batchValues) {
		SQLWrap sqlWrap = SQLWrap.newInstance(sql, null);
		sqlWrap.batchParameters = batchValues;
		return batchExecute(sqlWrap);
	}
	
	public int[] batchExecute(SQLWrap sqlWrap) {
		if (sqlWrap == null || sqlWrap.sql == null || sqlWrap.sql.length() <= 0 || sqlWrap.batchParameters == null || sqlWrap.batchParameters.size() <= 0) {
			return null;
		}
		
		int[] result = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionPool.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sqlWrap.sql);
			for (int i = 0; i < sqlWrap.batchParameters.size(); i++) {
				List<Object> conditon = sqlWrap.batchParameters.get(i);
				if (conditon != null) {
					for (int j = 0; j < conditon.size(); j++) {
						if (conditon.get(j) == null) {
							stmt.setNull(j + 1, Types.NULL);
						} else {
							stmt.setObject(j + 1, conditon.get(j));
						}
					}
				}
//				System.out.println(stmt);
				stmt.addBatch();
				
				if (i > 0 && i % BatchSize == 0) {
					stmt.executeBatch();
					conn.commit();
				}
			}
			result = stmt.executeBatch();
			conn.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("saveDeleteBatch执行出错::" + e.getLocalizedMessage());
			try {
				conn.rollback();
			} catch (Exception ex) {
				System.err.println("无法回滚::" + e.getLocalizedMessage());
			}
		} finally {
			DBConnectionPool.close(conn, stmt, rs);
		}
		
		return result;
	}

//	批量删除和插入，不同语句参数不同
	public int[] batchExecute(List<String> sqls, List<Object> conditions) {
		if (sqls == null || sqls.size() <= 0) {
			return null;
		}
		
		int[] result = null;
		Connection conn = null;
		Statement stmt = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionPool.getConnection();
			conn.setAutoCommit(false);

			stmt = conn.createStatement();

			if (conditions != null) {
				for (int i = 0; i < conditions.size(); i++) {
					pstmt = conn.prepareStatement(sqls.get(i).toString());
					if (conditions.get(i) != null) {
						// System.out.println("条件"+i);
						List conditon = (List) conditions.get(i);
						for (int j = 0; j < conditon.size(); j++) {
							if (conditon.get(j) == null) {
								pstmt.setNull(j + 1, Types.NULL);
							} else {
								pstmt.setObject(j + 1, conditon.get(j));
							}
						}
					}
					// stmt.addBatch();
					String sql = pstmt.toString();
					sql = sql.substring(sql.indexOf(":") + 1).trim();
					stmt.addBatch(sql);
					// System.out.println(sql);
					
					if (i > 0 && i % BatchSize == 0) {
						stmt.executeBatch();
						conn.commit();
					}
				}
			}
			
			pstmt.clearBatch();
			pstmt.close();
			result = stmt.executeBatch();
			conn.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("saveDeleteBatch执行出错::" + e.getLocalizedMessage());
			try {
				conn.rollback();
			} catch (Exception ex) {
				System.err.println("无法回滚::" + e.getLocalizedMessage());
			}
		} finally {
			DBConnectionPool.close(conn, stmt, rs);
		}
		return result;
	}
	
	
	

//	调用存储过程
	public void executeProcedure(String sql) {
		Connection conn = null;
		try {
			conn = DBConnectionPool.getConnection();
			CallableStatement cstm = conn.prepareCall(sql);
			cstm.execute();
			cstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionPool.close(conn);
		}

	}

/////////////////////////////
/////////////

//	此类方法为从字段为",x,xx,xx,"的表,取关联信息,别误解
	public List<Map> querySplitField(String sql, String field) {
		return querySplitField(sql, field, null);
	}

	public List<Map> querySplitField(String sql, String field, String separator) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Map> vec = new Vector<Map>();
		
		try {
			conn = DBConnectionPool.getConnection();
			String[] character = null;
			if (separator == null) {
				character = field.split(",");
			} else {
				character = field.split(separator);
			}
			
			stmt = conn.prepareStatement(sql);
			for (int i = 0; i < character.length; i++) {
				stmt.setString(1, character[i]);
				rs = stmt.executeQuery();
				vec.addAll(resultSetToVector(rs));
			}
		} catch (Exception e) {
			System.err.println("fieldToVector  出错了::" + e.getLocalizedMessage());
		} finally {
			DBConnectionPool.close(conn, stmt, rs);
		}
		return vec;
	}

/////////////////////////////
//////////////////////////////
	// 工具类
	protected static List<Map<String, Object>> resultSetToVector(ResultSet rs) {
		ResultSetMetaData rsmd = null;
		
		List<Map<String, Object>> result = new ArrayList<>();
		
		try {
			if (rs != null) {
				rsmd = rs.getMetaData();
			}
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				HashMap ham = new HashMap();
				for (int i = 0; i < columnCount; i++) {
					try {
						ham.put(rsmd.getColumnName(i + 1), rs.getObject(i + 1));
					} catch (Exception e) {
						ham.put(rsmd.getColumnName(i + 1), null);
						System.err.println("脏数据：从数据库记录" + ham + "所在行，获取第" + (i + 1) + "个数据" + rsmd.getColumnName(i + 1) + "出错::" + e.getLocalizedMessage());
					}
					// System.out.println(rsmd.getColumnName(i+1)+" "+rs.getObject(i+1));
				}
				result.add(ham);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("resultSetToVector 出错了::" + e.getLocalizedMessage());
		}
		return result;
	}

	protected static List<List<? extends Object>> resultSetToList(ResultSet rs) {
		List<List<? extends Object>> list = new ArrayList<>();
		try {
			ResultSetMetaData rsm = rs.getMetaData();
			List<String> rt = new ArrayList<String>();
			for (int i = 0; i < rsm.getColumnCount(); i++) {
				rt.add(rsm.getColumnName(i + 1));
//					System.out.println("zzz:"+rsm.getCatalogName(i+1));
//					System.out.println(rsm.getColumnName(i+1));
			}
			
			if (rt.size() > 0) {
				list.add(rt);
			}
			while (rs.next()) {
				rt = new ArrayList<String>();
				for (int i = 0; i < rsm.getColumnCount(); i++) {
					rt.add(rs.getString(i + 1));
				}
				list.add(rt);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("resultSetToList出错::" + e.getLocalizedMessage());
		}
		return list;
	}

	protected int getRowsCountMap(String SQL, Object... param) {
		int rowCount = 0;
		List<Map<String, Object>> list = findMapByParams(SQL, param);
		if (list != null && list.size() > 0) {
			rowCount = Long.valueOf(list.get(0).get("result").toString()).intValue();
		}
		return rowCount;
	}

	protected int getRowsCountList(String SQL, Object... param) {
		int rowCount = 0;
		List<List<? extends Object>> list = findListByParams(SQL, param);
		if (list != null && list.size() > 0) {
//			System.out.println("zzzzzzzz1:"+list.get(0).get(0));
//			System.out.println("zzzzzzzz2:"+list.get(1).get(0));
			rowCount = Long.valueOf(list.get(1).get(0).toString()).intValue();
		}
		return rowCount;
	}

}