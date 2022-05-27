/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;



public class StringTool {

	public static int stringToInt(String str) {
		int i = 0;
		if (str != null && str.length() != 0) {
			try {
				i = Integer.valueOf(str.trim());
			} catch (NumberFormatException nfe) {
				i = 0;
			}
		}
		return i;
	}

//	此方法把字符串首字母转成大写
	public static String upFirstChar(String str) {
		String first = (str.substring(0, 1)).toUpperCase();
		String other = str.substring(1);
		return first + other;
	}

//	此方法把字符串首字母转成小写
	public static String lowFirstChar(String str) {
		String first = (str.substring(0, 1)).toLowerCase();
		String other = str.substring(1);
		return first + other;
	}

//	由对象成成JSON字符串	
	public static String jsonObjectString(Object o) {
		return GsonUtils.toJson(o);
	}

//	 
	public static int getIndex(String all, String part) {
		int[] tmp = new int[10];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = -1;
		}
		part = StringTool.upFirstChar(part);
		tmp[0] = all.indexOf(part);
		part = part.toUpperCase();
		tmp[1] = all.indexOf(part);
		part = part.toLowerCase();
		tmp[2] = all.indexOf(part);
		return Math.max(tmp[0], Math.max(tmp[1], tmp[2]));
	}

//	此函数为分割字符串	
	public static String[] autoSplitString(String field) {

		StringTokenizer st1 = new StringTokenizer(field, ",");
		int ic1 = st1.countTokens();
		int l1 = st1.nextToken().length();
		StringTokenizer st2 = new StringTokenizer(field, ";");
		int ic2 = st2.countTokens();
		int l2 = st2.nextToken().length();
		StringTokenizer st3 = new StringTokenizer(field, "，");
		int ic3 = st3.countTokens();
		int l3 = st3.nextToken().length();
		StringTokenizer st4 = new StringTokenizer(field, "；");
		int ic4 = st4.countTokens();
		int l4 = st4.nextToken().length();
		StringTokenizer st5 = new StringTokenizer(field, "。");
		int ic5 = st5.countTokens();
		int l5 = st5.nextToken().length();

		int max = Math.max(ic5, Math.max(ic4, Math.max(ic3, Math.max(ic1, ic2))));
		int min = Math.min(l5, Math.min(l4, Math.min(l3, Math.min(l2, l1))));

		if (max == ic1 && min == l1) {
			return tokenToStringArray(field, ",");
		} else if (max == ic2 && min == l2) {
			return tokenToStringArray(field, ";");
		} else if (max == ic3 && min == l3) {
			return tokenToStringArray(field, "，");
		} else if (max == ic4 && min == l4) {
			return tokenToStringArray(field, "；");
		} else {
			return tokenToStringArray(field, "。");
		}
	}

//此函数为从分割好的字符串生成数组	
	public static String[] tokenToStringArray(String s, String split) {
		if (s == null) {
			return null;
		}
		StringTokenizer st = new StringTokenizer(s, split);
		String[] character = new String[st.countTokens()];
		for (int i = 0; i < character.length; i++) {
			character[i] = st.nextToken();
		}
		return character;
	}

	// List转string数组
	public static Object[] listToArray(List tmp) {
		Object[] temp = tmp.toArray();
		return temp;
	}

//	string数组转List
	public static List arrayToList(Object[] tmp) {
		// List temp=Arrays.asList(tmp); //不能修改
		List temp = new ArrayList();
		for (int i = 0; i < tmp.length; i++) {
			temp.add(tmp[i]);
		}
		return temp;
	}

	public static void main(String[] args) {
		List list = new ArrayList();
		list.add("1");
		list.add("2");
		Object[] tmp = listToArray(list);
		tmp[0] = "3";
		for (int i = 0; i < tmp.length; i++) {
			System.out.println(tmp[i]);
		}

		System.out.println("****************");
		List temp = arrayToList(tmp);
		temp.add("4");
		for (int i = 0; i < temp.size(); i++) {
			System.out.println(temp.get(i));
		}
	}

}
