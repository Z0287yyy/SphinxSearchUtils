/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.utils;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class BooleanAdapter implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {

	public JsonElement serialize(Boolean src, Type arg1, JsonSerializationContext arg2) {
		return new JsonPrimitive(src);
	}

	public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		Boolean res = null;

		try {
			String jr = json.getAsString();
			if (jr.equals("0")) {
				res = false;
			} else if (jr.equals("1")) {
				res = true;
			} else {
				res = Boolean.valueOf(jr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}
