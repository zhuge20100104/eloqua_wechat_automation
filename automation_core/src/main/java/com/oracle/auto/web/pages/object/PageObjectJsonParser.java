package com.oracle.auto.web.pages.object;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.oracle.auto.web.jbehave_ext.GlobalVariableTransformer;
import com.oracle.auto.web.utility.ResourceLoaderCross;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageObjectJsonParser {
	public static String getJsonStringFromResourcePage(String jsonName) {
		String value = ResourceLoaderCross.instance().loadResourceAsText(jsonName);
		return GlobalVariableTransformer.instance().transform(value);
	}
	
	private static int compIndex = 0;
	
	
	private static class PageData {
		public List<String> imports;
		public List<ComponentData> elements;
	}
	

	private static class CompConfigData {
		public List<JsonElement> elements;
	}
	
	
	public static ComponentData loadOneComponent(String jsonValue) {
		ComponentData data = JsonHelper.fromJson(jsonValue, ComponentData.class);
		data.jsonValue = jsonValue;
		
		return data;		
	}
	

	private static Map<String, ComponentData> loadComponents(String jsonValue) {
		Map<String, ComponentData> compList = new HashMap<String, ComponentData>();
		PageData data = JsonHelper.fromJson(jsonValue, PageData.class);
		CompConfigData configData = JsonHelper.fromJson(jsonValue, CompConfigData.class);
		
		if (data.elements == null)
			return compList;		
		
		int index = 0;
		// verify component data list fields
		for (ComponentData compData : data.elements) {
			if (compData.name == null || compData.name.isEmpty()) 
				compData.name = "Component" + (compIndex++);
			
			compData.jsonValue = configData.elements.get(index++).toString();
			
			if (compList.containsKey(compData.name))
				throw new RuntimeException("there's duplicated component in the json value: " + compData.name + ", jsonValue: " + jsonValue);
			
			compList.put(compData.name, compData);
		}
		
		return compList;
		
		

//		Map<String, Map<String, String>> compConfigMap = new HashMap<String, Map<String, String>>();
//		Map<String, Map<String, JsonElement>> compJsonMap = new HashMap<String, Map<String, JsonElement>>();
//		for (Map<String, JsonElement> config : configData.elements) {
//			Map<String, String> fields = new HashMap<String, String>();
//			
//			String compName = "Component" + (compIndex++);
//			for (String fieldName : config.keySet()) {
//				JsonElement el = config.get(fieldName);
//				if (el == null || el.isJsonNull()) continue;
//				
//				if (el.isJsonPrimitive()) {
//					String value = "";
//					if (el.getAsJsonPrimitive().isString())
//						value = el.getAsString();
//					else if (el.getAsJsonPrimitive().isNumber())
//						value = el.getAsNumber().toString();
//					else if (el.getAsJsonPrimitive().isBoolean())
//						value = Boolean.toString(el.getAsBoolean());
//					
//					fields.put(fieldName, value);
//					if (fieldName.equals(ComponentData.CONFIG_NAME))
//						compName = value;
//				} else {
//					
//				}
//			}
			
//			compConfigMap.put(compName, fields);
//			compJsonMap.put(compName, config);
//		}


//		for (ComponentData comp : data.elements) {
////			comp.configure = compConfigMap.get(comp.name);
////			comp.objects = compJsonMap.get(comp.name);
//			compList.put(comp.name, comp);
//		}
//		
//		return compList;
	}

	public static Map<String, ComponentData> loadComponentsDeep(String jsonName) {
		List<String> jsonFiles = new ArrayList<String>();
		jsonFiles.add(jsonName);
		doParseJsonImportField(jsonFiles, 0);
		
		// now we have a list of imported json files, 
		//   we need to merge them one by one, the later will override any duplicated values with previous one
		//   for elements > { name: "" }, we will have special handle: will consider them as similar items
		// diorder to handle
		int size = jsonFiles.size();
		String firstJson = getJsonStringFromResourcePage(jsonFiles.get(size-1));
		Map<String, ComponentData> compList1 = loadComponents(firstJson);
		for (int i = size-2 ; i >= 0; --i) {
			String secondJson = getJsonStringFromResourcePage(jsonFiles.get(i));
			Map<String, ComponentData> compList2 = loadComponents(secondJson);
			compList1 = doMergeComponents(compList1, compList2);
		}
		
		return compList1;
	}

	public static Map<String, String> loadPageDataDeep(String jsonName) {
		List<String> jsonFiles = new ArrayList<String>();
		jsonFiles.add(jsonName);
		doParseJsonImportField(jsonFiles, 0);
		
		// now we have a list of imported json files, 
		//   we need to merge them one by one, the later will override any duplicated values with previous one
		//   for elements > { name: "" }, we will have special handle: will consider them as similar items
		// diorder to handle
		int size = jsonFiles.size();
		String firstJson = getJsonStringFromResourcePage(jsonFiles.get(size-1));
		Map<String, String> map1 = extractPrimitiveFromJson(firstJson);
		for (int i = size-2 ; i >= 0; --i) {
			String secondJson = getJsonStringFromResourcePage(jsonFiles.get(i));
			Map<String, String> map2 = extractPrimitiveFromJson(secondJson);
			map2 = mergeMap(map1, map2);
		}
		return map1;
	}
	
	private static Map<String, String> extractPrimitiveFromJson(String jsonValue) {
		Map<String, String> ret = new HashMap<String, String>();
		Map<String, JsonElement> map = JsonHelper.fromJson(jsonValue, new TypeToken<Map<String, JsonElement> >() {}.getType());
		for (String key : map.keySet()) {
			if (map.get(key).isJsonPrimitive())
				ret.put(key, map.get(key).getAsJsonPrimitive().toString());
		}	
		return ret;
	}
	
	// parse jsonFiles and put all "imports" into it (skip existing ones)
	private static void doParseJsonImportField(List<String> jsonFiles, int fileIndex) {
		if (fileIndex >= jsonFiles.size()) return; // finish parsing

		String jsonName = jsonFiles.get(fileIndex);
		
		String jsonValue = getJsonStringFromResourcePage(jsonName);
		PageData data = JsonHelper.fromJson(jsonValue, PageData.class);
		int size = data.imports == null ? 0 : data.imports.size();
		
		// diorder to handle
		for (int i = size-1 ; i >= 0; --i) {
			if (jsonFiles.contains(data.imports.get(i))) continue; // ignore recursively referred file
			jsonFiles.add(data.imports.get(i));
			// move forward
			doParseJsonImportField(jsonFiles, fileIndex++);
		}
	}

	// the second one will override the first one, return the first one
	private static Map<String, ComponentData> doMergeComponents(Map<String, ComponentData> compList1, Map<String, ComponentData> compList2) {
		// iterate second, then override the first one
		ComponentData compData1  = null;
		ComponentData compData2  = null;
		for (String compName : compList2.keySet()) {
			if (!compList1.containsKey(compName))
				compList1.put(compName, compList2.get(compName));
			else {
				// need to merge
				compData1 = compList1.get(compName);
				compData2 = compList2.get(compName);
				// merge type
				if (compData2.type != null && !compData2.type.isEmpty())
					compData1.type = compData2.type;
				
				// merge first object levels
				compData1.jsonValue = mergeJson(compData1.jsonValue, compData2.jsonValue);
			}
		}
		
		return compList1;
	}
	
	// merge the two json file, the second will override the first one.
	// only merge first level so far.
	public static String mergeJson(String jsonValue1, String jsonValue2) {
		Map<String, JsonElement> fields1 = JsonHelper.fromJson(jsonValue1, new TypeToken<Map<String, JsonElement>>() {}.getType());
		Map<String, JsonElement> fields2 = JsonHelper.fromJson(jsonValue2, new TypeToken<Map<String, JsonElement>>() {}.getType());
		for (String key : fields1.keySet()) {
			if (fields2.containsKey(key)) {
				fields1.put(key, fields2.get(key));
			}
		}
		for (String key : fields2.keySet()) {
			if (!fields1.containsKey(key)) {
				fields1.put(key, fields2.get(key));
			}
		}
		
		return new Gson().toJson(fields1);
	}


	// the second one will override the first one, return the first one
	private static Map<String, String> mergeMap(Map<String, String> first, Map<String, String> second) {
		for (String key : second.keySet()) {
			if (!first.containsKey(key)) 
				first.put(key, second.get(key));
			else {
				if (second.get(key) != null) {
					first.put(key, second.get(key));
				}
			}
		}
		
		return first;
	}
}
