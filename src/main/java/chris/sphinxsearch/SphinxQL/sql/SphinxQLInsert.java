/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.sql;

import static chris.sphinxsearch.SphinxQL.sql.SphinxQLUtils.getSqlFieldValue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import chris.sphinxsearch.SphinxQL.entity.SQLWrap;
import chris.sphinxsearch.SphinxQL.entity.SphinxBaseEntity;
import chris.sphinxsearch.SphinxQL.sql.SphinxQLUtils.SqlMode;
import chris.sphinxsearch.SphinxQL.utils.SUReflectUtils;
import test.Transactions;

public class SphinxQLInsert {

	
	
	
	
	
	public static SQLWrap insertSqlWarp(SphinxBaseEntity o) {
		return insertSqlWarp(o.getClass().getSimpleName(), o);
	}
	
	public static SQLWrap insertSqlWarp(String tableName, SphinxBaseEntity o) {
		if (o == null) {
			return null;
		}
		
		List<Object> params = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder("insert into " + tableName + " (");
		StringBuilder tail = new StringBuilder(" values (");
		
		Collection<Field> fs = SUReflectUtils.getAllFields(o.getClass());
		int i = 0;
		for (Field f: fs) {
			String propety = f.getName();
			// System.out.println(propety);
			try {
				Object ot = SUReflectUtils.getFieldValue(o, f);
				
				if (ot == null) {
					continue;
				}
				
				if (i > 0) {
					sql.append(", ");
					tail.append(", ");
				}
				
				sql.append(propety);
				tail.append("?");
				params.add(getSqlFieldValue(ot, SqlMode.param));
				
				i ++;
			} catch (Exception e) {
				System.err.println("sqlInsertString无法解析属性:" + propety + "：：" + e.getLocalizedMessage());
			}
		}
		
		if (i == 0) {
			return null;
		}
		
		sql.append(")");
		tail.append(")");
		sql.append(tail);
//		System.out.println(sql);
		return SQLWrap.newInstance(sql.toString(), params);
	}
	
	
	public static SQLWrap batchInsertSqlWarp(List<? extends SphinxBaseEntity> os) {
		if (os == null || os.size() <= 0) {
			return null;
		}
		
		SphinxBaseEntity bo = os.get(0);
		
		return batchInsertSqlWarp(bo.getClass().getSimpleName(), os);
	}
		
	public static SQLWrap batchInsertSqlWarp(String tableName, List<? extends SphinxBaseEntity> os) {
		if (os == null || os.size() <= 0) {
			return null;
		}
		
		List<List<Object>> batchParams = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder("insert into " + tableName + " (");
		StringBuilder tail = new StringBuilder(" values (");
		
		SphinxBaseEntity bo = os.get(0);
		Collection<Field> fs = SUReflectUtils.getAllFields(bo.getClass());
		
		int curFIdx = 0;
		for (Field f: fs) {
			String propety = f.getName();
			try {
				if (curFIdx > 0) {
					sql.append(", ");
					tail.append(", ");
				}
				
				sql.append(propety);
				tail.append("?");
				
				for (int idx = 0; idx < os.size(); idx ++) {
					SphinxBaseEntity o = os.get(idx);
					
					List<Object> params = null;
					if (batchParams.size() > idx) {
						params = batchParams.get(idx);
					}
					if (params == null) {
						params = new ArrayList<>();
						batchParams.add(params);
					}
					
					Object ot = SUReflectUtils.getFieldValue(o, f);
					params.add(getSqlFieldValue(ot, SqlMode.param));
				}
				
				curFIdx ++;
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("sqlInsertString无法解析属性:" + propety + "：：" + e.getLocalizedMessage());
			}
		}
		
		if (curFIdx == 0) {
			return null;
		}
		
		sql.append(")");
		tail.append(")");
		sql.append(tail);
//		System.out.println(sql);
		
		SQLWrap sqlWrap = SQLWrap.newInstance(sql.toString(), null);
		sqlWrap.batchParameters = batchParams;
		
		return sqlWrap;
	}
	
	
//	从传进来的对象获取插入的SQL语句，局限性：对象名与表明相同，对象字段与表字段相同
	public static String insertSqlString(SphinxBaseEntity o) {
		if (o == null) {
			return null;
		}
		return insertSqlString(o.getClass().getSimpleName(), o);
	}
	
	public static String insertSqlString(String tableName, SphinxBaseEntity o) {
		if (o == null) {
			return null;
		}
		
		StringBuilder sql = new StringBuilder("insert into " + tableName + " (");
		StringBuilder tail = new StringBuilder(" values (");
		
		Collection<Field> fs = SUReflectUtils.getAllFields(o.getClass());
		int i = 0;
		for (Field f: fs) {
			String propety = f.getName();
			// System.out.println(propety);
			try {
				Object ot = SUReflectUtils.getFieldValue(o, f);
				
				if (ot == null) {
					continue;
				}
				
				if (i > 0) {
					sql.append(", ");
					tail.append(", ");
				}
				
				sql.append(propety);
				tail.append(getSqlFieldValue(ot, SqlMode.insert));
				
				i ++;
			} catch (Exception e) {
				System.err.println("sqlInsertString无法解析属性:" + propety + "：：" + e.getLocalizedMessage());
			}
		}
		
		if (i == 0) {
			return null;
		}
		
		sql.append(")");
		tail.append(")");
		sql.append(tail);
//		System.out.println(sql);
		return sql.toString();
	}

	public static String insertSqlString(List<? extends SphinxBaseEntity> os) {
		if (os == null || os.size() <= 0) {
			return null;
		}
		
		SphinxBaseEntity o = os.get(0);
		
		return insertSqlString(o.getClass().getSimpleName(), os);
	}
	
	public static String insertSqlString(String tableName, List<? extends SphinxBaseEntity> os) {
		if (os == null || os.size() <= 0) {
			return null;
		}
		
		SphinxBaseEntity bo = os.get(0);
		
		StringBuilder sql = new StringBuilder("insert into " + tableName + " (");
		
		List<StringBuilder> values = new ArrayList<>();
		
		Collection<Field> fs = SUReflectUtils.getAllFields(bo.getClass());
		int i = 0;
		for (Field f: fs) {
			String propety = f.getName();
			// System.out.println(propety);
			if (i > 0) {
				sql.append(", ");
			}
			
			sql.append(propety);
			
			try {
				for (int j = 0; j < os.size(); j++) {
					SphinxBaseEntity o = os.get(j);
					Object ot = SUReflectUtils.getFieldValue(o, f);
					
					StringBuilder tail;
					if (values.size() > j) {
						tail = values.get(j);
					} else {
						tail = new StringBuilder("(");
						values.add(tail);
					}
					
					if (i > 0) {
						tail.append(", ");
					}
					
					tail.append(getSqlFieldValue(ot, SqlMode.insert));
				}
				
				i ++;
			} catch (Exception e) {
				System.err.println("sqlInsertString无法解析属性:" + propety + "：：" + e.getLocalizedMessage());
			}
		}
		
		if (i == 0) {
			return null;
		}
		
		sql.append(") values ");
		
		for (int j = 0; j < os.size(); j++) {
			if (j > 0) {
				sql.append(", ");
			}
			
			StringBuilder tail = values.get(j);
			tail.append(")");
			
			sql.append(tail);
		}
		
//		System.out.println(sql);
		return sql.toString();
	}
	
	
	
	public static void main(String[] args) {
		
		List<Transactions> trans = new ArrayList<>();
		
		for (int i = 0; i < 7; i++) {
			Transactions t = new Transactions();
			t._id = "_id_" + i;
			t.address = "address_" + i;
			t._count = i + 0.5;
			t.txtime = 123 * 1l;
			t.block = i * 1l;
			t.txid = "txid_" + i;
			t.side = true;
			
			trans.add(t);
		}
		
		System.out.println(insertSqlString(trans));
	}
	
	

}