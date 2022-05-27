/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.desc;

public enum ConfigIndexFieldType {

	
	rt_field(""),
	
/*
 
To declare an array attribute, use the following syntax:
 {rt|sql|xmlpipe|csvpipe|tsvpipe}_attr_{int|int8|float}_array = NAME[SIZE]
 Where NAME is the attribute name, and SIZE is the array size, in elements
	
	rt_attr_uint = gid # regular attribute
    rt_attr_float_array = vec1[5] # 5D array of floats
    rt_attr_int8_array = vec2[3] # 3D array of small 8-bit integers
*/
	
	
	attr_int_array("that stores signed 32-bit integers"),
	attr_int8_array("that stores signed 8-bit integers (-128 to 127 range)"),
	attr_float_array("that stores 32-bit floats"),
	
	;
	
	public final String desc;
	
	private ConfigIndexFieldType(String desc) {
		this.desc = desc;
		
	}
	
}
