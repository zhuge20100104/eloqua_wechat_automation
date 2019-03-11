package com.oracle.auto.web.pages.object;

import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonHelper {
	// add component register
	public static <T> T fromJson(String jsonValue, Class<T> clazz) {
		return getGson().fromJson(jsonValue, clazz);
	}
	
	// add component register
	public static <T> T fromJson(String jsonValue, Type clazz) {
		return getGson().fromJson(jsonValue, clazz);
	}
	
	// get gson instance which will only generate from fields to fields with @Expose anotation.
	public static Gson getGsonExportOnly() {
		GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
		gsonBuilder.registerTypeAdapter(ComponentData.class, new ComponentDataDeserializer());
		return gsonBuilder.create();
	}

	// get gson instance which will only generate from fields to fields with @Expose anotation.
	private static Gson getGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(ComponentData.class, new ComponentDataDeserializer());
		return gsonBuilder.create();
	}
	
	private static int nameStart = 1;


	private static class ComponentDataDeserializer implements
			JsonDeserializer<ComponentData> {

		@Override
		public ComponentData deserialize(JsonElement json, Type type,
				JsonDeserializationContext context) throws JsonParseException {
			if (!json.isJsonObject()) {
				return null;
			}
			
			ComponentData data = new Gson().fromJson(json, ComponentData.class);
			if (data.name == null || data.name.isEmpty())
				data.name = "Component-Auto-" + String.valueOf(nameStart++);
			data.jsonValue = json.toString();			
			
			return data;
		}
	}
}
