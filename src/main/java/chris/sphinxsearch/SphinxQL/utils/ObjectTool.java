/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

public class ObjectTool {
//	获取类的所有字段
	
//	此方法为从MAP转为对象
	public static void MapToObject(Map map, Object o) {
		Iterator<String>it=map.keySet().iterator();
		Class c=o.getClass();
		Method m1=null;
		Method m2=null;
		while(it.hasNext()) {
			String property=it.next();
			try {
				m1=c.getMethod("get"+StringTool.upFirstChar(property), null);
				m2=c.getMethod("set"+StringTool.upFirstChar(property),m1.getReturnType());
				//Object tmp=m1.invoke(o, null);
				if(map.get(property)!=null) {
					try {
						m2.invoke(o, map.get(property));
					} catch (Exception e) {
						//e.printStackTrace();
						// TODO Auto-generated catch block
						try {
							m2.invoke(o,m1.getReturnType().cast(map.get(property)));
						} catch (Exception e1) {
							//e1.printStackTrace();
							try {
								m2.invoke(o, map.get(property).toString());
							} catch (Exception e2){
								//e2.printStackTrace();
								System.err.println("MAP转换对象属性："+property+"出错");
							}
						}
					}
				}
				
			} catch (Exception e) {
				//e.printStackTrace();
				System.err.println("解析："+property+"错误！");
			}
		}
	}
	
	
//	此方法，将02对象中不为空的值赋给O1，O1更新对象，避免空值覆盖掉嘞原来的值
	public static void copyObject(Object o1,Object o2) {
		if(o2==null) {
			return;
		}
		if(o1==null) {
			o1=o2;
			return;
		}
		int j=0;
		Class oo=o2.getClass();
		Field [] ff=oo.getDeclaredFields();
//		Method [] mm=oo.getMethods();
		//System.out.println(ff.length);

		try {			
			while(j<ff.length) {
				String propety=ff[j].getName();
				if(propety.indexOf("id")==-1) {
					propety=StringTool.upFirstChar(propety);
					Method m1=oo.getMethod("get"+propety, null);
					Method m2=oo.getMethod("set"+propety, ff[j].getType());
					Object ot=m1.invoke(o2, null);
					if(isNotNull(ot)) {
						m2.invoke(o1, ot);
					}
				}
				j++;
			}
		}  catch (Exception e) {
			System.err.println("对象拷贝有错误");
		}
	}
	
	
//	判断对象是否为空或者空值
	public static boolean isNotNull(Object o) {
		if(o==null) {
			return false;
		} else if( o instanceof Integer && ((Integer)o==0) ) {
//			System.out.println("整数");
			return false;
		} else if(o instanceof Double && ((Double)o==0.0)) {
//			System.out.println("浮点");
//			System.out.println( "浮点测试1"+((Double)o==0.0) );
//			System.out.println( "浮点测试2"+((Double)o).equals(Double.valueOf(0.0)) );
			return false;
		} else if(o instanceof Short && ((Short)o==0)) {
//			System.out.println("短整形");
			return false;
		} else if(o instanceof Long && ((Long)o==0)) {
//			System.out.println("长整形");
			return false;
		} else if( o instanceof String && ((String)o).equals("") ) {
//			System.out.println("字符串");
			return false;
		}
		return true;
	}
	
//	根据propertyName,自动把OBJECT对应属性+1
	public static void autoAdd(String propertyName,Object o) {
		try {
//			System.out.println("get"+upFirstChar(propertyName));
			Method m1=o.getClass().getMethod("get"+StringTool.upFirstChar(propertyName),null );
			Object oo=m1.invoke(o, null);
			Method m2=o.getClass().getMethod("set"+StringTool.upFirstChar(propertyName), oo.getClass() );
//			m2.invoke(0, oo.getClass().cast((Double.valueOf(oo.toString()).doubleValue()+1)));
			m2.invoke(o, Integer.valueOf(((Integer)oo).intValue()+1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("出错了！");
			try {
				Method m2 = o.getClass().getMethod("set"+StringTool.upFirstChar(propertyName), Integer.class );
				m2.invoke(o, new Integer(1));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.err.println("挽救失败！");
			}			
		}
	}

//	根据propertyName,自动把OBJECT对应属性+指定值
	public static void autoAddValue(String propertyName,Object value,Object o) {
		try {
//			System.out.println("get"+upFirstChar(propertyName));
			Method m1=o.getClass().getMethod("get"+StringTool.upFirstChar(propertyName),null );
			Object oo=m1.invoke(o, null);
			Method m2=o.getClass().getMethod("set"+StringTool.upFirstChar(propertyName), oo.getClass() );
			if(oo instanceof Integer) {
				m2.invoke(o, Integer.valueOf(((Integer)oo).intValue()+(Integer.valueOf(String.valueOf(value)))).intValue());
			} else if (oo instanceof Double ) {
				m2.invoke(o, Double.valueOf(((Double)oo).doubleValue()+(Double.valueOf(String.valueOf(value)))).doubleValue());
			} else if (oo instanceof Long) {
				m2.invoke(o, Long.valueOf(((Long)oo).longValue()+(Long.valueOf(String.valueOf(value)))).longValue());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("出错了，尝试挽救！");
			try {
				Class c=o.getClass().getMethod("get"+StringTool.upFirstChar(propertyName), null).getReturnType();
				Method m2 = o.getClass().getMethod("set"+StringTool.upFirstChar(propertyName), c );
				m2.invoke(o, c.cast(value));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.err.println("挽救失败！");
			}			
		}
	}
}
