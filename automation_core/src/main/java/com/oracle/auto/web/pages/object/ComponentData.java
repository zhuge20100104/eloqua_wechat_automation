package com.oracle.auto.web.pages.object;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;


public class ComponentData {
	public String name;
	public String type;
	public String jsonValue;
	
	public ComponentData clone() {
		ComponentData data = new ComponentData();
		data.name = this.name;
		data.type = this.type;
		data.jsonValue = this.jsonValue;	
		
		return data;
	}
	
	private String replaceString(Map<String, String> props, String target) {
		String ret = target;
		for (String key : props.keySet()) {
			ret = ret.replace(key, props.get(key));
		}
		
		return ret;
	}

	public void replaceJsonFirstLevel(String key, String value) {
		Map<String, String> props = new HashMap<String, String>();
		props.put(key, value);

		replaceJsonFirstLevel(props);
	}

	
	public void replaceJsonFirstLevel(Map<String, String> props) {
		Map<String, JsonElement> fields = JsonHelper.fromJson(jsonValue, new TypeToken<Map<String, JsonElement>>() {}.getType());
		for (String key : fields.keySet()) {
			JsonElement field = fields.get(key);
			if (field.isJsonPrimitive()) {
				fields.put(key, JsonHelper.fromJson(replaceString(props, field.toString()), JsonElement.class));
			}
		}
		
		jsonValue = fields.toString();
	}
	
//	public Map<String, String> configure;
//	public Map<String, JsonElement> objects;
//	
//	@Override
//	public String toString() {
//		return "name : " + name + " type: " + type + " configure: " + configure.toString();
//	}

//	public static final String CONFIG_NAME = "name";
}
