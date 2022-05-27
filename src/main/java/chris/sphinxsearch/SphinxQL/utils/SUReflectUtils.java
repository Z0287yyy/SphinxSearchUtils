/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SUReflectUtils {

	
	public static Collection<Field> getAllFields(Class clazz) {
		Set<Field> fields = new HashSet<Field>();
		Field[] fields1 = clazz.getFields();
		Field[] fields2 = clazz.getDeclaredFields();
		if (fields1 != null && fields1.length > 0) {
			fields.addAll(Arrays.asList(fields1));
		}
		if (fields2 != null && fields2.length > 0) {
			fields.addAll(Arrays.asList(fields2));
		}
		return fields;
	}

	public static Field getField(Object entity, String name) throws Exception {
		try {
			return entity.getClass().getDeclaredField(name);
		} catch (Exception e) {
			return entity.getClass().getField(name);
		}
	}
	
	public static Field getField(Class clazz, String name) throws Exception {
		try {
			return clazz.getDeclaredField(name);
		} catch (Exception e) {
			return clazz.getField(name);
		}
	}

	public static Method getMethod(Object entity, String methodName, Object... methodParams) throws Exception {
		Class<?>[] parameterTypes = null;
		if (methodParams != null) {
			parameterTypes = new Class[methodParams.length];
			for (int i = 0; i < methodParams.length; i ++) {
				Object methodParam = methodParams[i];
				if (methodParam == null) {
					continue;
				}
				Class clazz = null;
				if (methodParam instanceof Class) {
					clazz = (Class) methodParam;
				} else {
					clazz = methodParam.getClass();
				}
				parameterTypes[i] = clazz;
			}
		}
			
		try {
			if (methodParams != null) {
				return entity.getClass().getMethod(methodName, parameterTypes);
			} else {
				return entity.getClass().getMethod(methodName);
			}
		} catch (Exception e) {
			if (methodParams != null) {
				return entity.getClass().getDeclaredMethod(methodName, parameterTypes);
			} else {
				return entity.getClass().getDeclaredMethod(methodName);
			}
		}
	}

	public static <T> T getFieldValue(Object entity, String fieldName) throws Exception {
		Field field = null;
		try {
			field = getField(entity, fieldName);
		} catch (Exception e) {
		}
		return getFieldValue(entity, field);
	}
	
	public static <T> T getFieldValue(Object entity, Field field) throws Exception {
		T idValue = null;

		final String fieldName = field.getName();
		
		Method getMethod = null;
		if (field != null) {
			if (field.getType() == Boolean.class || field.getType() == boolean.class) {
				if (fieldName.indexOf("is") == 0) {
					try {
						getMethod = getMethod(entity, fieldName, null);
					} catch (Exception e1) {
						try {
							getMethod = getMethod(entity, "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), null);
						} catch (Exception e2) {
							try {
								getMethod = getMethod(entity, "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), null);
							} catch (Exception e3) {
							}
						}
					}
				}
			} else {
				try {
					getMethod = getMethod(entity, "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), null);
				} catch (Exception e1) {
				}
			}
		} else {
			if (fieldName.indexOf("is") == 0) {
				try {
					getMethod = getMethod(entity, fieldName, null);
				} catch (Exception e1) {
					try {
						getMethod = getMethod(entity, "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), null);
					} catch (Exception e2) {
						try {
							getMethod = getMethod(entity, "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), null);
						} catch (Exception e3) {
						}
					}
				}
			} else {
				try {
					getMethod = getMethod(entity, "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), null);
				} catch (Exception e1) {
					try {
						getMethod = getMethod(entity, "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), null);
					} catch (Exception e2) {
					}
				}
			}
		}
		

		if (getMethod != null) {
			idValue = (T) getMethod.invoke(entity);
		} else {
			if (field != null) {
				boolean ogAccessible = field.isAccessible();
				field.setAccessible(true);
				idValue = (T) field.get(entity);
				field.setAccessible(ogAccessible);

			}
		}

		return idValue;
	}

	

	public static void setFieldValue(Object entity, final String fieldName, Object fieldValue) throws Exception {
		Field field = null;
		try {
			field = getField(entity, fieldName);
		} catch (Exception e) {
		}
		
		Method setMethod = null;
		if (field != null) {
			if (field.getType() == Boolean.class || field.getType() == boolean.class) {
				if (fieldName.indexOf("is") == 0) {
					try {
						String fName = fieldName.replace("is", "");
						setMethod = getMethod(entity, "set" + fName.substring(0, 1).toUpperCase() + fName.substring(1), field.getType());
					} catch (Exception e1) {
						try {
							setMethod = getMethod(entity, "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), field.getType());
						} catch (Exception e2) {
						}
					}
				}
			} else {
				try {
					setMethod = getMethod(entity, "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), field.getType());
				} catch (Exception e1) {
				}
			}
		} else {
			try {
				setMethod = getMethod(entity, "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), fieldValue.getClass());
			} catch (Exception e1) {
				try {
					String fName = fieldName.replace("is", "");
					setMethod = getMethod(entity, "set" + fName.substring(0, 1).toUpperCase() + fName.substring(1), fieldValue);
				} catch (Exception e2) {
				}
			}
		}
		
		
		if (setMethod != null) {
			setMethod.invoke(entity, fieldValue);
		} else {
			if (field != null) {
				boolean ogAccessible = field.isAccessible();
				field.setAccessible(true);
				try {
					field.set(entity, fieldValue);
				} finally {
					field.setAccessible(ogAccessible);
				}
			}
		}
	}

	public static boolean isPrimitiveOrWrapClass(Class clz) {
		try {
			return CharSequence.class.isAssignableFrom(clz) || Date.class.isAssignableFrom(clz) || clz.isPrimitive() || ((Class) clz.getField("TYPE").get(null)).isPrimitive();
		} catch (Exception e) {
			return false;
		}
	}

	public static void copyValues(Object fromObj, Object toObj) {
		try {
			Collection<Field> fields = getAllFields(fromObj.getClass());
			for (Field field : fields) {
				try {
					Object value = getFieldValue(fromObj, field.getName());
					setFieldValue(toObj, field.getName(), value);
				} catch (Exception e) {
				}

			}

		} catch (Exception e) {
		}
	}

	public static void copyNoNullValues(Object fromObj, Object toObj) {
		try {
			Collection<Field> fields = getAllFields(fromObj.getClass());
			for (Field field : fields) {
				try {
					Object value = getFieldValue(fromObj, field.getName());
					if (null == value) {
						continue;
					}
					setFieldValue(toObj, field.getName(), value);
				} catch (Exception e) {
				}

			}

		} catch (Exception e) {
		}
	}

}
