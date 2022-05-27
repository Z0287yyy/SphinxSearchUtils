/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {
	public static final Gson gson = new Gson();
	
	public static Gson gsonWithAdapter(Class clzz, Class adpaterClass) {
		try {
			return new GsonBuilder().registerTypeAdapter(clzz, adpaterClass.newInstance()).create();
		} catch (Exception e) {
		}
		return null;
	}
	
	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}
	
	public static <T> T fromJson(String json, Type typeOfT) {
		return gson.fromJson(json, typeOfT);
	}
	
}