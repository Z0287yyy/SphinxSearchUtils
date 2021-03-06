/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.dao;
//package com.sphinxsearch.SphinxQL.dao;
//
//import java.io.Serializable;
//import java.lang.reflect.Field;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//import org.apache.catalina.authenticator.jaspic.PersistentProviderRegistrations.Property;
//
//import com.sphinxsearch.SphinxQL.entity.SortDirection;
//import com.sphinxsearch.SphinxQL.entity.SortInfo;
//
//import chris.SharedUtils.Object.ObjectUtils;
//import chris.SharedUtils.String.StringUtils;
//
//
//public class CriteriaQuery implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//	private int curPage = 1;// 当前页
//	private int pageSize = 10;// 默认一页条数
//	private String myAction;// 请求的action 地址
//	
//	private String myForm;// form 名字
//	
//	private List criterionList=new CriterionList();//自定义查询条件集合
//	private List jqcriterionList=new CriterionList();//jquery datatable控件生成查询条件集合
//	private int isUseimage = 0;// 翻页工具条样式
//	private DetachedCriteria detachedCriteria;
//	private static Map<String, Object> map;
//	private LinkedHashMap<String, Object> ordermap;//排序字段
//	private boolean flag = true;// 对同一字段进行第二次重命名查询时值设置FASLE不保存重命名查询条件
//	private String field="";//查询需要显示的字段
//	private Class entityClass;//POJO
//	private List results;// 结果集
//	private int total;
//	
//	private JoinType aliasJoinType;
//	private final Map<String, String> alias = new ConcurrentHashMap<String, String>();//保存创建的aliasName 防止重复创建
//	
//	public List getResults() {
//		return results;
//	}
//
//	public void setResults(List results) {
//		this.results = results;
//	}
//
//	public int getTotal() {
//		return total;
//	}
//
//	public void setTotal(int total) {
//		this.total = total;
//	}
//
//	private DataGrid dataGrid;
//	private DataTables dataTables;
//
//	public DataTables getDataTables() {
//		return dataTables;
//	}
//
//	public void setDataTables(DataTables dataTables) {
//		this.dataTables = dataTables;
//	}
//
//	public DataGrid getDataGrid() {
//		return dataGrid;
//	}
//
//	public void setDataGrid(DataGrid dataGrid) {
//		this.dataGrid = dataGrid;
//	}
//
//	public Class getEntityClass() {
//		return entityClass;
//	}
//
//	public void setEntityClass(Class entityClass) {
//		this.entityClass = entityClass;
//	}
//	public CriterionList getJqcriterionList() {
//		return jqcriterionList;
//	}
//
//	public void setJqcriterionList(CriterionList jqcriterionList) {
//		this.jqcriterionList = jqcriterionList;
//	}
//
//	public CriteriaQuery(Class c) {
//		this.entityClass = c;
//		this.detachedCriteria = DetachedCriteria.forClass(c);
//		this.map = new HashMap<String, Object>();
//		this.ordermap = new LinkedHashMap<String, Object>();
//	}
//
//	public CriteriaQuery(Class c, int curPage, String myAction, String myForm) {
//		this.entityClass = c;
//		this.curPage = curPage;
//		this.myAction = myAction;
//		this.myForm = myForm;
//		this.detachedCriteria = DetachedCriteria.forClass(c);
//	}
//
//	public CriteriaQuery(Class c, int curPage, String myAction) {
//		this.entityClass = c;
//		this.myAction = myAction;
//		this.curPage = curPage;
//		this.detachedCriteria = DetachedCriteria.forClass(c);
//		this.map = new HashMap<String, Object>();
//		this.ordermap = new LinkedHashMap<String, Object>();
//	}
//
//	public CriteriaQuery(Class entityClass, int curPage) {
//		this.entityClass = entityClass;
//		this.curPage = curPage;
//		this.detachedCriteria = DetachedCriteria.forClass(entityClass);
//		this.map = new HashMap<String, Object>();
//	}
//	public CriteriaQuery(Class entityClass,DataGrid dg) {
//		this.curPage = dg.getPage();
//		//String[] fieldstring=dg.getField().split(",");
//		//this.detachedCriteria = DetachedCriteriaUtil
//		//.createDetachedCriteria(c, "start", "_table",fieldstring);
//		this.detachedCriteria = DetachedCriteria.forClass(entityClass);
//		//Criteria criteria = null;
//
//		this.field=dg.getField();
//		this.entityClass=entityClass;
//		this.dataGrid=dg;
//		this.pageSize=dg.getRows();
//		this.map = new HashMap<String, Object>();
//		this.ordermap = new LinkedHashMap<String, Object>();
//	}
//	public CriteriaQuery(Class entityClass,DataTables dataTables) {
//		this.curPage = dataTables.getDisplayStart();
//		String[] fieldstring=dataTables.getsColumns().split(",");
//		this.detachedCriteria = DetachedCriteriaUtil
//		.createDetachedCriteria(entityClass, "start", "_table",fieldstring);
//		//this.detachedCriteria = DetachedCriteria.forClass(c);
//		this.field=dataTables.getsColumns();
//		this.entityClass=entityClass;
//		this.dataTables=dataTables;
//		this.pageSize=dataTables.getDisplayLength();
//		this.map = new HashMap<String, Object>();
//		this.ordermap = new LinkedHashMap<String, Object>();
//		addJqCriteria(dataTables);
//	}
//
//	public CriteriaQuery(Class c, int pageSize, int curPage,
//			String myAction, String myForm) {
//		this.entityClass = c;
//		this.pageSize = pageSize;
//		this.curPage = curPage;
//		this.myAction = myAction;
//		this.myForm = myForm;
//		this.detachedCriteria = DetachedCriteria.forClass(c);
//	}
//	
//	public JoinType getAliasJoinType() {
//		return aliasJoinType;
//	}
//
//	public void setAliasJoinType(JoinType aliasJoinType) {
//		this.aliasJoinType = aliasJoinType;
//	}
//
//	/**
//	 * 加载条件(条件之间有关联) hql((this_.0 like ?0 and this_.1 like ?1) or this_.2 like ?2)
//	 * 表示法cq.add(cq.or(cq.and(cq, 0, 1), cq, 2))----- hql2:(this_.0 like ?0 or
//	 * this_.1 like ?1) 表示法:cq.add(cq.or(cq, 0, 1));
//	 * 例子：cq.in("TBPrjstatus.code", status);
//		cq.eq("attn", user.getUserName());
//		cq.isNull("attn");
//		cq.add(cq.and(cq.or(cq, 1, 2), cq, 0));
//	 */
//	public void add(Criterion c) {
//		detachedCriteria.add(c);
//	}
//
//	/**
//	 * 加载条件
//	 */
//	public void add() {
//		for (int i = 0; i < getCriterionList().size(); i++) {
//			add(getCriterionList().getParas(i));
//		}
//		getCriterionList().removeAll(getCriterionList());
//	}
//	
//	/**
//	 * 加载dataTables 默认查询条件
//	 * @param dataTables
//	 */
//	public void addJqCriteria(DataTables dataTables) {
//		String search=dataTables.getSearch();//查询关键字
//		SortInfo[] sortInfo=dataTables.getSortColumns();//排序字段
//		String[] sColumns=dataTables.getsColumns().split(",");//字段
//		if(StringUtils.isNotEmpty(search))
//		{
//			for (String string : sColumns) {
//				if(string.indexOf("_")==-1)
//				{
//					jqcriterionList.addPara(Restrictions.like(string, "%" + search
//							+ "%"));
//				}
//			}
//			add(getOrCriterion(jqcriterionList));
//			
//		}
//		if(sortInfo.length>0)
//		{
//			for (SortInfo sortInfo2 : sortInfo) {
//				addOrder(""+sColumns[sortInfo2.getColumnId()]+"",sortInfo2.getSortOrder());
//			}
//		}
//	}
//
//	public void createCriteria(String name) {
//		detachedCriteria.createCriteria(name);
//	}
//
//	public void createCriteria(String name, String value) {
//		detachedCriteria.createCriteria(name, value);
//	}
//
//	/**
//	 * 创建外键表关联对象
//	 * 
//	 * @param name外键表实体名
//	 * @param value引用名
//	 */
//	public void createAlias(String name, String value) {
//		if(!alias.containsKey(name) && !alias.containsValue(value)) {
//			if (this.aliasJoinType == null) {
//				detachedCriteria.createAlias(name, value);
//			} else {
//				detachedCriteria.createAlias(name, value, this.aliasJoinType);
//			}
//			alias.put(name, value);
//		} else {
////			if (alias.containsKey(name)) {
////				System.err.println(name + "已存在别名:" + alias.get(name) + ",不能再生成别名:" + value);
////			} else {
////				System.err.println("别名:" + value + ",已存在不能再生成别名:" + name + "-" + value);
////			}
//		}
//	}
//	
//	public void createAliasLeftJoin(String name, String value) {
//		if(!alias.containsKey(name) && !alias.containsValue(value)){
//			detachedCriteria.createAlias(name, value, JoinType.LEFT_OUTER_JOIN);
//			alias.put(name, value);
//		} else {
////			if (alias.containsKey(name)) {
////				System.err.println(name + "已存在别名:" + alias.get(name) + ",不能再生成别名:" + value);
////			} else {
////				System.err.println("别名:" + value + ",已存在不能再生成别名:" + name + "-" + value);
////			}
//		}
//	}
//
//	public void setResultTransformer(Class class1) {
//		detachedCriteria.setResultTransformer(Transformers.aliasToBean(class1));
//	}
//
//	public void setProjection(Property property) {
//		detachedCriteria.setProjection(property);
//	}
//
//	/**
//	 * 设置条件之间and关系
//	 * 
//	 * @param query
//	 * @param source
//	 * @param dest
//	 *            hql((this_.0 like ?0 and this_.1 like ?1) or this_.2 like ?2)
//	 *            表示法cq.add(cq.or(cq.and(cq, 0, 1), cq, 2))
//	 * @return
//	 */
//	public Criterion and(CriteriaQuery query, int source, int dest) {
//		return Restrictions.and(query.getCriterionList().getParas(source),
//				query.getCriterionList().getParas(dest));
//	}
//
//	/**
//	 * 设置条件之间and关系
//	 * 
//	 * @param query
//	 * @param source
//	 * @param dest
//	 *            hql:(this_.0 like ?0 or this_.1 like ?1) 表示法:cq.add(cq.or(cq, 0,
//	 *            1));
//	 * @return
//	 */
//	public Criterion and(Criterion c, CriteriaQuery query, int souce) {
//		return Restrictions.and(c, query.getCriterionList().getParas(souce));
//	}
//	
//	/**
//	 *根据CriterionList组合嵌套条件
//	 */
//	public Criterion getOrCriterion(CriterionList list) {
//		Criterion c1=null;
//		Criterion c2=null;
//		Criterion c3=null;
//		c1=list.getParas(0);
//		for (int i = 1; i < list.size(); i++) {
//			c2=list.getParas(i);
//			c3=getor(c1, c2);
//			c1=c3;
//		}
//		return c3;
//	}
//	/**
//	 * 设置组合后的Criterion OR关系
//	 * 
//	 * @param query
//	 * @param source
//	 * @param dest
//	 * @return
//	 */
//	public Criterion getor(Criterion c1,Criterion c2) {
//		return Restrictions.or(c1, c2);
//	}
//	
//
//	/**
//	 * 设置条件之间and关系
//	 * 
//	 * @param query
//	 * @param source
//	 * @param dest
//	 * @return
//	 */
//	public Criterion and(Criterion c1, Criterion c2)
//
//	{
//		return Restrictions.and(c1, c2);
//	}
//
//	/**
//	 * 设置Or查询
//	 * 
//	 * @param query
//	 * @param source条件1
//	 * @param dest条件2
//	 * @return
//	 */
//	public Criterion or(CriteriaQuery query, int source, int dest) {
//		return Restrictions.or(query.getCriterionList().getParas(source), query
//				.getCriterionList().getParas(dest));
//	}
//
//	/**
//	 * 设置or(Criterion c, CriteriaQuery query, int source)（或）查询条件
//	 * 
//	 * @param keyname
//	 * @param keyvalue1
//	 * @param keyvalue2
//	 */
//	public Criterion or(Criterion c, CriteriaQuery query, int source) {
//		return Restrictions.or(c, query.getCriterionList().getParas(source));
//	}
//
//	/**
//	 * 设置or(Criterion c1, Criterion c2)（或）查询条件
//	 * 
//	 * @param keyname
//	 * @param keyvalue1
//	 * @param keyvalue2
//	 *            两个条件或查询： Restrictions.or(Restrictions.in("username",list1),
//	 *            Restrictions.idEq(1)); 三个或多个条件查询:（使用嵌套方式）
//	 *            criteria.add(Restrictions
//	 *            .or(Restrictions.in("username",list1),
//	 *            Restrictions.or(Restrictions.idEq(3), Restrictions.idEq(4))));
//	 */
//	public void or(Criterion c1, Criterion c2) {
//		this.detachedCriteria.add(Restrictions.or(c1, c2));
//	}
//
//	/**
//	 * 设置order（排序）查询条件
//	 * 
//	 * @param ordername
//	 *            ：排序字段名
//	 * @param ordervalue
//	 *            ：排序字段值（"asc","desc"）
//	 */
//	public void addOrder(String ordername, SortDirection ordervalue) {
//		judgecreateAlias(ordername);
//		ordermap.put(getAliasProperty(ordername),ordervalue);
//
//	}
//	/**
//	 * 设置order（排序）查询条件
//	 * 
//	 * @param ordername
//	 *            ：排序字段名
//	 * @param ordervalue
//	 *            ：排序字段值（"asc","desc"）
//	 */
//	public void setOrder(LinkedHashMap<String, Object> map) {
//		if (map == null || map.isEmpty()) {
//			addIdOrder();
//			return;
//		}
//		
//		boolean hasIdOrder = false;
//		
//		for (Map.Entry<String, Object> entry : map.entrySet()) {
//			if (entry.getKey().equals("id")) {
//				hasIdOrder = true;
//			}
//			
//			judgecreateAlias(entry.getKey());
//			if (SortDirection.asc.equals(entry.getValue())) {
//				detachedCriteria.addOrder(Order.asc(getAliasProperty(entry.getKey())));
//			} else {
//				detachedCriteria.addOrder(Order.desc(getAliasProperty(entry.getKey())));
//			}
//		}
//		
//		if (!hasIdOrder) {
//			addIdOrder();
//		}
//	}
//	
//	private void addIdOrder() {
//		if (this.entityClass == null) {
//			return;
//		}
//		
//		try {
//			String crtName = "createDate";
//			Field crtField = this.entityClass.getDeclaredField(crtName);
//			if (crtField == null) {
//				crtField = this.entityClass.getField(crtName);
//			}
//			
//			if (crtField == null) {
//				crtName = "crtDate";
//				crtField = this.entityClass.getDeclaredField(crtName);
//				if (crtField == null) {
//					crtField = this.entityClass.getField(crtName);
//				}
//			}
//			
//			if (crtField != null) {
//				detachedCriteria.addOrder(Order.desc(getAliasProperty(crtName)));
//			}
//		} catch (Exception e) {
//		}
//		
//		try {
//			Field idField = this.entityClass.getDeclaredField("id");
//			if (idField == null) {
//				idField = this.entityClass.getField("id");
//			}
//			
//			if (idField != null) {
//				detachedCriteria.addOrder(Order.desc(getAliasProperty("id")));
//			}
//		} catch (Exception e) {
//		}
//	}
//	
//	/**
//	 * 创建 alias 
//	 * @param entitys
//	 * 规则 entitys 为a.b.c 这种将会创建 alias a和alias  b而不会创建c
//	 * 因为这样更加容易传值
//	 */
//	public void judgecreateAlias(String entitys) {
//		String[] aliass = entitys.split("\\.");
//		StringBuilder perfix = new StringBuilder();
//		for (int i = 0 ;i<aliass.length-1;i++){
//			String alis = perfix + aliass[i];
//			createAlias(alis, alis.replaceAll("\\.", "_"));
//			perfix.append(aliass[i] + ".");
//		}
//	}
//
//	public LinkedHashMap<String, Object> getOrdermap() {
//		return ordermap;
//	}
//
//	public void setOrdermap(LinkedHashMap<String, Object> ordermap) {
//		this.ordermap = ordermap;
//	}
//
//	/**
//	 * 设置eq(相等)查询条件
//	 * 
//	 * @param keyname
//	 *            :字段名
//	 * @param keyvalue
//	 *            ：字段值
//	 */
//	public void eq(String keyname, Object keyvalue) {
//		//if (keyvalue != null && keyvalue != "") {
//		keyname = getAliasProperty(keyname);
//		if (keyvalue != null) {
//			try {
//				Field field = entityClass.getDeclaredField(keyname);
//				if (field.getType().equals(Integer.class)) {
//					criterionList.addPara(Restrictions.eq(keyname, ObjectUtils.getInteger(keyvalue.toString())));
//				} else if (field.getType().equals(Long.class)) {
//					criterionList.addPara(Restrictions.eq(keyname, ObjectUtils.getLong(keyvalue.toString())));
//				} else if (field.getType().equals(Double.class)) {
//					criterionList.addPara(Restrictions.eq(keyname, ObjectUtils.getDouble(keyvalue.toString())));
//				} else {
//					criterionList.addPara(Restrictions.eq(keyname, keyvalue));
//				}
//			} catch (Exception e) {
//				criterionList.addPara(Restrictions.eq(keyname, keyvalue));
//			}
//			if (flag) {
//				this.put(keyname, keyvalue);
//			}
//			flag = true;
//		}
//	}
//
//	/**
//	 * 设置notEq(不等)查询条件
//	 * 
//	 * @param keyname
//	 * @param keyvalue1
//	 * @param keyvalue2
//	 */
//	public void notEq(String keyname, Object keyvalue) {
//		keyname = getAliasProperty(keyname);
////		if (keyvalue != null && keyvalue != "") {
//		if (keyvalue != null) {
//			try {
//				Field field = entityClass.getDeclaredField(keyname);
//				if (field.getType().equals(Integer.class)) {
//					criterionList.addPara(Restrictions.ne(keyname, ObjectUtils.getInteger(keyvalue.toString())));
//				} else if (field.getType().equals(Long.class)) {
//					criterionList.addPara(Restrictions.ne(keyname, ObjectUtils.getLong(keyvalue.toString())));
//				} else if (field.getType().equals(Double.class)) {
//					criterionList.addPara(Restrictions.ne(keyname, ObjectUtils.getDouble(keyvalue.toString())));
//				} else {
//					criterionList.addPara(Restrictions.ne(keyname, keyvalue));
//				}
//			} catch (Exception e) {
//				criterionList.addPara(Restrictions.ne(keyname, keyvalue));
//			}
//			if (flag) {
//				this.put(keyname, keyvalue);
//			}
//			flag = true;
//		}
//	}
//
//	/**
//	 * 设置like(模糊)查询条件
//	 * 
//	 * @param keyname
//	 * @param keyvalue1
//	 * @param keyvalue2
//	 */
//	public void like(String keyname, Object keyvalue) {
//		keyname = getAliasProperty(keyname);
//		if (keyvalue != null && keyvalue != "") {
//			//criterionList.addPara(Restrictions.like(keyname, "%" + keyvalue+ "%"));
//			criterionList.addPara(Restrictions.like(keyname, keyvalue));
//			if (flag) {
//				this.put(keyname, keyvalue);
//			}
//			flag = true;
//		}
//	}
//
//	/**
//	 * 设置gt(>)查询条件
//	 * 
//	 * @param keyname
//	 * @param keyvalue1
//	 * @param keyvalue2
//	 */
//	public void gt(String keyname, Object keyvalue) {
//		keyname = getAliasProperty(keyname);
//		if (keyvalue != null && keyvalue != "") {
//			try {
//				Field field = entityClass.getDeclaredField(keyname);
//				if (field.getType().equals(Integer.class)) {
//					criterionList.addPara(Restrictions.gt(keyname, ObjectUtils.getInteger(keyvalue.toString())));
//				} else if (field.getType().equals(Long.class)) {
//					criterionList.addPara(Restrictions.gt(keyname, ObjectUtils.getLong(keyvalue.toString())));
//				} else if (field.getType().equals(Double.class)) {
//					criterionList.addPara(Restrictions.gt(keyname, ObjectUtils.getDouble(keyvalue.toString())));
//				} else {
//					criterionList.addPara(Restrictions.gt(keyname, keyvalue));
//				}
//			} catch (Exception e) {
//				criterionList.addPara(Restrictions.gt(keyname, keyvalue));
//			}
//			if (flag) {
//				this.put(keyname, keyvalue);
//			}
//			flag = true;
//		}
//	}
//
//	/**
//	 * 设置lt(<)查询条件
//	 * 
//	 * @param keyname
//	 * @param keyvalue1
//	 * @param keyvalue2
//	 */
//	public void lt(String keyname, Object keyvalue) {
//		keyname = getAliasProperty(keyname);
//		if (keyvalue != null && keyvalue != "") {
//			try {
//				Field field = entityClass.getDeclaredField(keyname);
//				if (field.getType().equals(Integer.class)) {
//					criterionList.addPara(Restrictions.lt(keyname, ObjectUtils.getInteger(keyvalue.toString())));
//				} else if (field.getType().equals(Long.class)) {
//					criterionList.addPara(Restrictions.lt(keyname, ObjectUtils.getLong(keyvalue.toString())));
//				} else if (field.getType().equals(Double.class)) {
//					criterionList.addPara(Restrictions.lt(keyname, ObjectUtils.getDouble(keyvalue.toString())));
//				} else {
//					criterionList.addPara(Restrictions.lt(keyname, keyvalue));
//				}
//			} catch (Exception e) {
//				criterionList.addPara(Restrictions.lt(keyname, keyvalue));
//			}
//			if (flag) {
//				this.put(keyname, keyvalue);
//			}
//			flag = true;
//		}
//	}
//
//	/**
//	 * 设置le(<=)查询条件
//	 * 
//	 * @param keyname
//	 * @param keyvalue1
//	 * @param keyvalue2
//	 */
//	public void le(String keyname, Object keyvalue) {
//		keyname = getAliasProperty(keyname);
//		if (keyvalue != null && keyvalue != "") {
//			try {
//				Field field = entityClass.getDeclaredField(keyname);
//				if (field.getType().equals(Integer.class)) {
//					criterionList.addPara(Restrictions.le(keyname, ObjectUtils.getInteger(keyvalue.toString())));
//				} else if (field.getType().equals(Long.class)) {
//					criterionList.addPara(Restrictions.le(keyname, ObjectUtils.getLong(keyvalue.toString())));
//				} else if (field.getType().equals(Double.class)) {
//					criterionList.addPara(Restrictions.le(keyname, ObjectUtils.getDouble(keyvalue.toString())));
//				} else {
//					criterionList.addPara(Restrictions.le(keyname, keyvalue));
//				}
//			} catch (Exception e) {
//				criterionList.addPara(Restrictions.le(keyname, keyvalue));
//			}
//			if (flag) {
//				this.put(keyname, keyvalue);
//			}
//			flag = true;
//		}
//	}
//
//	/**
//	 * 设置ge(>=)查询条件
//	 * 
//	 * @param keyname
//	 * @param keyvalue1
//	 * @param keyvalue2
//	 */
//	public void ge(String keyname, Object keyvalue) {
//		keyname = getAliasProperty(keyname);
//		if (keyvalue != null && keyvalue != "") {
//			try {
//				Field field = entityClass.getDeclaredField(keyname);
//				if (field.getType().equals(Integer.class)) {
//					criterionList.addPara(Restrictions.ge(keyname, ObjectUtils.getInteger(keyvalue.toString())));
//				} else if (field.getType().equals(Long.class)) {
//					criterionList.addPara(Restrictions.ge(keyname, ObjectUtils.getLong(keyvalue.toString())));
//				} else if (field.getType().equals(Double.class)) {
//					criterionList.addPara(Restrictions.ge(keyname, ObjectUtils.getDouble(keyvalue.toString())));
//				} else {
//					criterionList.addPara(Restrictions.ge(keyname, keyvalue));
//				}
//			} catch (Exception e) {
//				criterionList.addPara(Restrictions.ge(keyname, keyvalue));
//			}
//			if (flag) {
//				this.put(keyname, keyvalue);
//			}
//			flag = true;
//		}
//	}
//
//	/**
//	 * 设置in(包含)查询条件
//	 * 
//	 * @param keyname
//	 * @param keyvalue1
//	 * @param keyvalue2
//	 */
//	public void in(String keyname, Object[] keyvalue) {
//		keyname = getAliasProperty(keyname);
//		if (keyvalue != null && keyvalue.length > 0 && keyvalue[0] != "") {
//			criterionList.addPara(Restrictions.in(keyname, keyvalue));
//		} else {
//			//TODO, should be double check
//			criterionList.addPara(Restrictions.isNull(keyname));
//		}
//	}
//	
//	public void in(String keyname, Collection keyvalue) {
//		keyname = getAliasProperty(keyname);
//		if (keyvalue != null && keyvalue.size() > 0) {
//			criterionList.addPara(Restrictions.in(keyname, keyvalue));
//		} else {
//			//TODO, should be double check
//			criterionList.addPara(Restrictions.isNull(keyname));
//		}
//	}
//	
//	public void notIn(String keyname, Object[] keyvalue) {
//		keyname = getAliasProperty(keyname);
//		if (keyvalue != null && keyvalue.length > 0 && keyvalue[0] != "") {
//			criterionList.addPara(Restrictions.not(Restrictions.in(keyname, keyvalue)));
//		} else {
//			//TODO, should be double check
//			criterionList.addPara(Restrictions.isNotNull(keyname));
//		}
//	}
//	
//	public void notIn(String keyname, Collection keyvalue) {
//		keyname = getAliasProperty(keyname);
//		if (keyvalue != null && keyvalue.size() > 0) {
//			criterionList.addPara(Restrictions.not(Restrictions.in(keyname, keyvalue)));
//		} else {
//			//TODO, should be double check
//			criterionList.addPara(Restrictions.isNotNull(keyname));
//		}
//	}
//	
//
//	/**
//	 * 设置isNull查询条件
//	 * 
//	 * @param keyname
//	 * @param keyvalue1
//	 * @param keyvalue2
//	 */
//	public void isNull(String keyname) {
//		keyname = getAliasProperty(keyname);
//		criterionList.addPara(Restrictions.isNull(keyname));
//	}
//
//	/**
//	 * 设置isNull查询条件
//	 * 
//	 * @param keyname
//	 * @param keyvalue1
//	 * @param keyvalue2
//	 */
//	public void isNotNull(String keyname) {
//		keyname = getAliasProperty(keyname);
//		criterionList.addPara(Restrictions.isNotNull(keyname));
//	}
//
//	/**
//	 * 保存查询条件
//	 * 
//	 * @param keyname
//	 * @param keyvalue1
//	 * @param keyvalue2
//	 */
//	public void put(String keyname, Object keyvalue) {
//		keyname = getAliasProperty(keyname);
//		if (keyvalue != null && keyvalue != "") {
//			map.put(keyname, keyvalue);
//		}
//	}
//
//	/**
//	 * 设置between(之间)查询条件
//	 * 
//	 * @param keyname
//	 * @param keyvalue1
//	 * @param keyvalue2
//	 */
//	public void between(String keyname, Object keyvalue1, Object keyvalue2) {
//		keyname = getAliasProperty(keyname);
//		Criterion c = null;// 写入between查询条件
//
//		if (!keyvalue1.equals(null) && !keyvalue2.equals(null)) {
//			c = Restrictions.between(keyname, keyvalue1, keyvalue2);
//		} else if (!keyvalue1.equals(null)) {
//			c = Restrictions.ge(keyname, keyvalue1);
//		} else if (!keyvalue2.equals(null)) {
//			c = Restrictions.le(keyname, keyvalue2);
//		}
//		criterionList.add(c);
//	}
//
//	public void sql(String sql) {
//		Restrictions.sqlRestriction(sql);
//	}
//
//	public void sql(String sql, Object[] objects, Type[] type) {
//		Restrictions.sqlRestriction(sql, objects, type);
//	}
//
//	public void sql(String sql, Object objects, Type type) {
//		Restrictions.sqlRestriction(sql, objects, type);
//	}
//
//	public Integer getCurPage() {
//		return curPage;
//	}
//
//	public void setCurPage(Integer curPage) {
//		this.curPage = curPage;
//	}
//
//	public int getPageSize() {
//		return pageSize;
//	}
//
//	/**
//	 * 设置分页显示数
//	 * 
//	 * @param pageSize
//	 */
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//	}
//
//	public String getMyAction() {
//		return myAction;
//	}
//
//	public void setMyAction(String myAction) {
//		this.myAction = myAction;
//	}
//
//	public String getMyForm() {
//		return myForm;
//	}
//
//	public void setMyForm(String myForm) {
//		this.myForm = myForm;
//	}
//
//	public CriterionList getCriterionList() {
//		return criterionList;
//	}
//
//	public void setCriterionList(CriterionList criterionList) {
//		this.criterionList = criterionList;
//	}
//
//	public DetachedCriteria getDetachedCriteria() {
//		return detachedCriteria;
//	}
//
//	public String getField() {
//		return field;
//	}
//
//	public void setField(String field) {
//		this.field = field;
//	}
//
//	public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
//		this.detachedCriteria = detachedCriteria;
//	}
//
//	public int getIsUseimage() {
//		return isUseimage;
//	}
//
//	/**
//	 * 设置工具条样式（0:不带图片：1带图片）
//	 * 
//	 * @param isUseimage
//	 */
//	public void setIsUseimage(int isUseimage) {
//		this.isUseimage = isUseimage;
//	}
//
//	public Map<String, Object> getMap() {
//		return map;
//	}
//
//	public void setMap(Map<String, Object> map) {
//		this.map = map;
//	}
//
//	public boolean isFlag() {
//		return flag;
//	}
//
//	/**
//	 * 对同一字段进行第二次重命名查询时值设置FASLE不保存重命名查询条件
//	 * 
//	 * @param flag
//	 */
//	public void setFlag(boolean flag) {
//		this.flag = flag;
//	}
//	
//	
//	
//	
//	
//	private String getAliasProperty(String keyname) {
//		try {
//			if (keyname != null && keyname.trim().length() > 0) {
//				int dbParts = keyname.split("\\.").length;
//				if (dbParts > 1) {
//					judgecreateAlias(keyname);
//				}
//				if (dbParts > 2) {
//					int dot = keyname.lastIndexOf(".");
//					String entry = keyname.substring(0, dot);
//					String property = keyname.substring(dot);
//					if (alias.containsKey(entry)) {
//						keyname = alias.get(entry) + property;
//					}
//				}
//			}
//		} catch (Exception e) {
//			System.err.println(e);
//		}
//		return keyname;
//	}
//
//}
