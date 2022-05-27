/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.desc;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Set;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public enum SphinxQLAttributes {

	INTEGER(Integer.class, "unsigned 32-bit integer"),    // rt_attr_uint
	BIGINT(Long.class, "signed 64-bit integer"),    // rt_attr_bigint
	FLOAT(Double.class, "32-bit (single precision) floating point"),    // rt_attr_float
	BOOL(Boolean.class, "1-bit boolean"),    // rt_attr_bool
	STRING(String.class, "a text string"),    // rt_attr_string
//	DATETIME(Date.class, "Timestamp attribute declaration. Multi-value (an arbitrary number of attributes is allowed), optional. Introduced in version 1.10-beta."),    // rt_attr_timestamp
	JSON(JsonObject.class, "a JSON document"),    // rt_attr_json
	MVA(new TypeToken<Set<Integer>>() {}.getType(), "an order-insensitive set of unique INTEGERs"),    // rt_attr_multi
	MVA64(new TypeToken<Set<Long>>() {}.getType(), "an order-insensitive set of unique BIGINTs"),    // rt_attr_multi_64
	
	;
	
	public final Type type;
	public final String desc;
	
	private SphinxQLAttributes(Type type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	public static SphinxQLAttributes getAttributeByType(Type type) {
		if (type != null) {
			for (SphinxQLAttributes attr : values()) {
				if (attr.type.equals(type)) {
					return attr;
				} else if (type instanceof Class) {
					Class clazz = (Class) type;
					while (true) {
						clazz = clazz.getSuperclass();
						
						if (clazz == null) {
							break;
						}
						
						if (clazz.equals(attr.type)) {
							return attr;
						}
					}
				}
			}
		}
		
		return STRING;
	}
	
	public boolean supportRTField() {
		return this == SphinxQLAttributes.STRING;
	}
	
	public boolean supportIndex() {
		return this == SphinxQLAttributes.INTEGER || this == SphinxQLAttributes.BIGINT || this == SphinxQLAttributes.FLOAT || this == SphinxQLAttributes.MVA || this == SphinxQLAttributes.MVA64;
	}
	
	
}
