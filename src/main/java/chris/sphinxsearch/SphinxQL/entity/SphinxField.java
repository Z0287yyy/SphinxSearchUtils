/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.entity;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) 
@Retention(RetentionPolicy.RUNTIME) 
@Documented
public @interface SphinxField {    
	// 加入RTField的字段，无法用sql eq 查出，需要用MATCH查询
	// http://sphinxsearch.com/docs/current/conf-rt-field.html
	public boolean isRTField() default false;
	
	// attribute index only supports integer, float, and MVA types
	public boolean isIndex() default false;
}
