/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import chris.sphinxsearch.SphinxQL.Config;

public class DBConnectionPool {

	private static int _poolSize = 10;
	
	private static int _timeoutSecond = 10;
	
	private static final Map<Connection, Long> _connections = new ConcurrentHashMap<>();
	
	
	public static void setPoolSize(int poolSize) {
		if (poolSize <= 0) {
			return;
		}
		_poolSize = poolSize;
	}
	
	public static void setTimeoutInSecond(int timeoutInSecond) {
		if (timeoutInSecond <= 0) {
			return;
		}
		_timeoutSecond = timeoutInSecond;
	}
	
	public static Connection getConnection() {
		while (true) {
			Iterator<Connection> conIt = _connections.keySet().iterator();
			
			while (conIt.hasNext()) {
				Connection conn = conIt.next();
				Long begin = _connections.get(conn);
				
				// cached
				if (begin == null || begin <= 0l) {
					_connections.put(conn, System.currentTimeMillis());
					return conn;
				}
				
				// expired
				if (System.currentTimeMillis() - begin > _timeoutSecond * 1000l) {
					try {
						conn.close();
					} catch (Exception e) {
					}
					
					conn = createConnection();
					if (conn != null) {
						_connections.put(conn, System.currentTimeMillis());
						return conn;
					}
				}
			}
			
			// new
			if (_connections.size() < _poolSize) {
				Connection conn = createConnection();
				if (conn != null) {
					_connections.put(conn, System.currentTimeMillis());
					return conn;
				}
			}
			
			try {
				Thread.sleep(1000l);
			} catch (Exception e) {
			}
		}
	}
	
	
	public static void close(Connection connection) {
		if (connection == null) {
			return;
		}
		try {
			if (connection.isClosed()) {
				_connections.remove(connection);
				return;
			}
		} catch (Exception e) {
			_connections.remove(connection);
			return;
		}
		
		_connections.put(connection, 0l);
	}
	
	
	public static void close(Connection con, Statement st, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			System.err.println("ResultSet关闭失败::" + e.getLocalizedMessage());
		}
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException e) {
			System.err.println("Statement关闭失败::" + e.getLocalizedMessage());
		}
		
		close(con);
	}
	
	
	// utils
	
	private static Connection createConnection() {
		Connection conn = null;
		try {
			Class.forName(Config.JDBCDriverName);
			
			DriverManager.setLoginTimeout(10);
			conn = DriverManager.getConnection(Config.getDefaultSphinxUrl(), Config.UserName, Config.Password);
		} catch (Exception e) {
//			e.printStackTrace();
			System.err.println("获取数据库连接失败::" + e.getLocalizedMessage());
		}
		try {
			conn.setAutoCommit(true);
		} catch (Exception e) {
		}
		try {
			conn.setTransactionIsolation(Connection.TRANSACTION_NONE);
		} catch (Exception e) {
		}
		return conn;
	}

	private static void closeConnection(Connection conn, Statement st, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			System.err.println("ResultSet关闭失败::" + e.getLocalizedMessage());
		}
		try {
			if (st != null) {
				st.close();
			}
		} catch (Exception e) {
			System.err.println("Statement关闭失败::" + e.getLocalizedMessage());
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			System.err.println("Connection关闭失败::" + e.getLocalizedMessage());
		}
	}
	
}
