/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import chris.sphinxsearch.SphinxQL.entity.PageBean;
import chris.sphinxsearch.SphinxQL.entity.SQLWrap;
import chris.sphinxsearch.SphinxQL.entity.SphinxBaseEntity;
import chris.sphinxsearch.SphinxQL.entity.SphinxField;
import chris.sphinxsearch.SphinxQL.jdbc.JDBCExecutor;
import chris.sphinxsearch.SphinxQL.sql.SphinxQLCreateTable;
import chris.sphinxsearch.SphinxQL.sql.SphinxQLInsert;
import chris.sphinxsearch.SphinxQL.sql.SphinxQLUpdate;
import chris.sphinxsearch.SphinxQL.utils.BooleanAdapter;
import chris.sphinxsearch.SphinxQL.utils.GsonUtils;
import chris.sphinxsearch.SphinxQL.utils.SUReflectUtils;

public class BasicDao<T extends SphinxBaseEntity> {
	private final Gson gson = GsonUtils.gsonWithAdapter(Boolean.class, BooleanAdapter.class);
	
	private final AtomicLong currentIndex = new AtomicLong(-1);

	public final Class<? extends SphinxBaseEntity> clazz;
	protected final JDBCExecutor execute;
	

	public BasicDao(Class<? extends SphinxBaseEntity> clazz) {
		execute = JDBCExecutor.getDBExecute();
		this.clazz = clazz;
	}
	
	public boolean exist() {
		return execute.tableExist(clazz.getSimpleName());
	}
	
	public void createTableIfNotExist() {
		if (!exist()) {
			SQLWrap wrap = SphinxQLCreateTable.createTableSql(clazz);
			execute.excute(wrap.sql);
			
			if (wrap.addtionsSqls != null) {
				for (String sql : wrap.addtionsSqls) {
					execute.excute(sql);
				}
			}
		}
	}
	
	public synchronized long getMaxId(boolean forceRefresh) {
		if (forceRefresh || currentIndex.get() < 0) {
			// get current maxId
			List<Map<String, Object>> res = execute.findMap("select max(id) as maxid from " + clazz.getSimpleName());
			if (res == null || res.size() <= 0) {
				currentIndex.set(0l);
			} else {
				currentIndex.set(Long.valueOf((long) res.get(0).get("maxid")));
			}
		}
		
		return currentIndex.get();
	}

	public void insert(T t) {
		getMaxId(false);
		
		if (t.id > 0 && t.id > currentIndex.get()) {
			currentIndex.getAndSet(t.id);
		} else {
			t.id = currentIndex.addAndGet(1l);
		}
		
		SQLWrap sql = SphinxQLInsert.insertSqlWarp(t);
		execute.execute(sql);
	}
	
	public void update(T t) {
		String sql = SphinxQLUpdate.updateSqlString(t);
		execute.executeByParams(sql);
	}

	public void update(T t, String fieldName, Object fieldValue) {
		String sql = SphinxQLUpdate.updateSqlString(t.getClass().getSimpleName(), fieldName, fieldValue, t, true);
		execute.executeByParams(sql);
	}
	
	public void save(T t) {
		getMaxId(false);
		
		if (t.id > 0 && t.id > currentIndex.get()) {
			currentIndex.getAndSet(t.id);
			update(t);
		} else {
			insert(t);
		}
	}
	
	public void batchInsert(List<T> ts) {
		getMaxId(false);
		
		for (T t : ts) {
			if (t.id > 0 && t.id > currentIndex.get()) {
				currentIndex.getAndSet(t.id);
			} else {
				t.id = currentIndex.addAndGet(1l);
			}
		}
		
		SQLWrap sql = SphinxQLInsert.batchInsertSqlWarp(ts);
		execute.batchExecute(sql);
	}
	
	public void batchUpdate(List<T> ts) {
		SQLWrap sql = SphinxQLUpdate.batchUpdateSqlWrap(ts);
		
		execute.batchExecute(sql);
	}
	
	public void batchSave(List<T> ts) {
		getMaxId(false);
		
		List<T> it = new ArrayList<>();
		List<T> ut = new ArrayList<>();
		
		for (T t : ut) {
			if (t.id > 0 && t.id > currentIndex.get()) {
				currentIndex.getAndSet(t.id);
				it.add(t);
			} else {
				it.add(t);
			}
		}
		
		batchInsert(it);
		batchUpdate(ut);
	}

/////////////////
	public T findOneByField(String fieldName, Object fieldValue) {
		try {
			return findByField(fieldName, fieldValue).get(0);
		} catch (Exception e) {
		}
		return null;
	}
	
	public List<T> findByField(String fieldName, Object fieldValue) {
		try {
			Field field = SUReflectUtils.getField(clazz, fieldName);
			SphinxField spxF = field.getAnnotation(SphinxField.class);
			
			String sql = "select * from " + clazz.getSimpleName() + " where " + fieldName + " = ?";
			
			if (spxF != null && spxF.isRTField()) {
				sql = "select * from " + clazz.getSimpleName() + " where MATCH(?)";
			}
			
			List<Map<String, Object>> res = execute.findMapByParams(sql, fieldValue);
			
			return convert(res);
		} catch (Exception e) {
		}
		return new ArrayList<T>();
	}

	
//////////////////////////

	public void delete(T t) {
		deleteByField("id", t.id);
	}

	public void deleteByField(String fieldName, Object fieldValue) {
		String sql = "delete from " + clazz.getSimpleName() + " where " + fieldName + " = '" + fieldValue + "'";
		execute.executeByParams(sql);
	}

///////////////////////
	public List<T> findBySql(String sql) {
		List<Map<String, Object>> res = execute.findMap(sql);
		return convert(res);
	}
	
	public List<T> findBySqlPart(String conditionPart, String valuesPart) {
		String sql = "select * from " + clazz.getSimpleName() + " where " + conditionPart + " = '" + valuesPart + "'";
		List<Map<String, Object>> res = execute.findMap(sql);
		return convert(res);
	}
	

	/**
	 * 
	 * @return top 20 record
	 */
	public List<T> findAll() {
		String sql = "select * from " + clazz.getSimpleName();
		List<Map<String, Object>> res = execute.findMap(sql);
		return convert(res);
	}
	
	public List<T> find(PageBean page) {
		String sql = "select * from " + clazz.getSimpleName() + " limit " + (page.getPage() - 1) * page.getPageSize() + "," + page.getPageSize();
		List<Map<String, Object>> res = execute.findMap(sql);
		return convert(res);
	}
	
	public List<T> convert(List<Map<String, Object>> res) {
		List<T> result = gson.fromJson(gson.toJson(res), TypeToken.getParameterized(List.class, clazz).getType());
		return result;
	}

}
