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
import chris.sphinxsearch.SphinxQL.entity.SQLWrap;
import chris.sphinxsearch.SphinxQL.entity.SphinxBaseEntity;
import chris.sphinxsearch.SphinxQL.entity.SphinxField;
import chris.sphinxsearch.SphinxQL.utils.DateTool;
import chris.sphinxsearch.SphinxQL.utils.ObjectUtils;
import chris.sphinxsearch.SphinxQL.utils.SUReflectUtils;
import chris.sphinxsearch.SphinxQL.utils.StringTool;

public class SphinxQLCreateTable {

	public static SQLWrap createTableSql(Class<? extends SphinxBaseEntity> clazz) {
		if (clazz == null) {
			return null;
		}
		
		List<String> indexSqls = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder("create table " + clazz.getSimpleName() + " (");
		
		Collection<Field> fields = SUReflectUtils.getAllFields(clazz);
		int i = 0;
		for (Field field : fields) {
			if (field.getName().equals("id")) {
				continue;
			}
			
			if (i > 0) {
				sql.append(", ");
			}
//			sql.append(field.getName() + " field stored");
			SphinxQLAttributes attr = SphinxQLAttributes.getAttributeByType(field.getGenericType());
			
			String fieldPart = attr.name();
			SphinxField rtF = field.getAnnotation(SphinxField.class);
			if (rtF != null) {
				if (rtF.isRTField()) {
					if (attr.supportRTField()) {
						fieldPart = "FIELD STORED";
					}
				} else if (rtF.isIndex()) {
					if (attr.supportIndex()) {
						indexSqls.add("CREATE INDEX idx_" + field.getName() + " ON " + clazz.getSimpleName() + "(" + field.getName() + ")");
					}
				}
			}
			
			sql.append(field.getName() + " " + fieldPart);
			i ++;
		}
		
		sql.append(")");
		
		SQLWrap sqlWrap = SQLWrap.newInstance(sql.toString(), null);
		sqlWrap.addtionsSqls = indexSqls;
		return sqlWrap;
	}
	
}