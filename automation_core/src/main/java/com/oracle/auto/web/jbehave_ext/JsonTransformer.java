package com.oracle.auto.web.jbehave_ext;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.parsers.StoryTransformer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonTransformer implements StoryTransformer {
    private static Logger log = Logger.getLogger(JsonTransformer.class);

    public static final String JSON_KEYWORD = "Json:";
    public static final String JSON_PREFIX = "@Json{";
    public static final String JSON_SUFFIX = "}";
    public static final String JSON_PREFIX_ESCAPE = "\\@Json\\{";  // escaped
    public static final String JSON_SUFFIX_ESCAPE = "\\}";        // escaped

    private static JsonTransformer obj = new JsonTransformer();

    private JsonTransformer() {
    }

    public static JsonTransformer instance() {
        return obj;
    }

    String parseJSONField(String value, String paths) {

        JsonElement element = null;
        try {
            element = new Gson().fromJson(value, JsonElement.class);
        } catch (Exception ex) {
            throw new RuntimeException("fail to parse JSON data the paths [" + paths + "] of value [" + value + "]", ex);
        }

        if (element == null || !element.isJsonObject())
            throw new RuntimeException("JSON is not JSON object as expected, value: " + value);

        JsonObject root = element.getAsJsonObject();

        JsonElement current = root;

        for (String path : paths.split("\\.")) {
            path = path.trim(); // trim
            if (path.isEmpty()) continue; // filter empty

            // process array first
            if (path.matches("[\\w\\d_]+\\s*\\[[\\s\\d]+\\]")) {
                String field = path.split("\\[")[0].trim();
                String indexStr = path.split("\\[")[1].split("\\]")[0].trim();
                int index = Integer.parseInt(indexStr);

                // get array list
                JsonElement newNode = current.getAsJsonObject().get(field);
                if (newNode == null || newNode.isJsonNull())
                    throw new RuntimeException("element has no field for path field: " + field + ", paths: " + paths + ", value: " + value);
                if (!newNode.isJsonArray())
                    throw new RuntimeException("element is not arry as expected for path: " + path + ", paths: " + paths + ", passed node: " + newNode);


                newNode = newNode.getAsJsonArray().get(index);
                if (newNode == null || newNode.isJsonNull())
                    throw new RuntimeException("element has no field for path with index: " + path + ", paths: " + paths + ", value: " + value);

                current = newNode;
                continue;
            }

            // process non-arrary
            if (path.matches("[\\w\\d_]+")) {
                if (!current.isJsonObject())
                    throw new RuntimeException("element is not object as expected for path: " + path + ", paths: " + paths + ", current value: " + current);

                JsonElement newNode = current.getAsJsonObject().get(path);
                if (newNode == null || newNode.isJsonNull())
                    throw new RuntimeException("element has no field for path: " + path + ", paths: " + paths + ", value: " + value);

                current = newNode;
                continue;
            }

            throw new RuntimeException("invalid path format to process: " + path + ", paths: " + paths + ", value: " + value);
        }

        if (current.isJsonPrimitive() && current.getAsJsonPrimitive().isString())
            return current.getAsString(); // will trm the " "

        return current.toString();
    }

    public boolean doReplaceJsonReferenceInHash(Map<String, String> blockMap) {
        // handle cross reference in hashmap
        // process Json key recursively (one json might contians another json.)

        boolean bChanged = false;

        for (Entry<String, String> entry : blockMap.entrySet()) {
            String value = entry.getValue();
            String newValue = doReplaceJsonReference(value, blockMap);

            if (!newValue.equals(value)) {
                blockMap.put(entry.getKey(), newValue);
                bChanged = true;
            }
        }

        return bChanged;
    }

    public String transform(String storyAsText) {
        try {
            Map<String, String> blockMap = new TreeMap<String, String>();
            String s = doExtractJsonVariable(storyAsText, blockMap);

            // handle cross reference in hashmap
            // process Json key recursively (one json might contians another json.)
            for (int i = 0; i < 10; ++i) {
                if (!doReplaceJsonReferenceInHash(blockMap)) break;
            }

            int size = s.length();

            for (int i = 0; i < 10; ++i) { // max to 10
                s = doReplaceJsonReference(s, blockMap);
                if (s.length() != size) {
                    size = s.length();
                    continue; // size change keep transform
                }

                break;
            }

            return s;
        } catch (Exception e) {
            log.error("fail to extract json variable from story: " + storyAsText, e);
            throw new RuntimeException("fail to extract json variable from story: " + storyAsText, e);
        }

    }

    public String doReplaceJsonReference(String text, Map<String, String> blockMap) {
        // replace json references
        String patternStr = JSON_PREFIX_ESCAPE + "(.+?)" + JSON_SUFFIX_ESCAPE;
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(text);

        String result = text;
        while (m.find()) {
            String jsonObjectNameWithPrefix = JSON_PREFIX + m.group(1) + JSON_SUFFIX; // raw variable in story file
            String jsonObjectNameNoPrefix = m.group(1).trim();

            // check if contains further fields
            if (!jsonObjectNameNoPrefix.contains(".")) {
                // simple replace
                String jsonString = blockMap.get(jsonObjectNameNoPrefix);
                if (jsonString == null) {
                    log.warn("cannot get referred JSON varaible: variable = " + jsonObjectNameWithPrefix);
                    break; // do nothing to this captured variable
                }

                result = result.replace(jsonObjectNameWithPrefix, jsonString);

            } else {
                // get json object value firstly
                String jsonObjectName = jsonObjectNameNoPrefix.split("\\.")[0].trim();
                String jsonString = blockMap.get(jsonObjectName);
                if (jsonString == null) {
                    log.warn("cannot get referred JSON for varaible: variable = " + jsonObjectNameWithPrefix);
                    break; // do nothing to this captured variable
                }

                // get fields accordingly
                String paths = jsonObjectNameNoPrefix.substring(jsonObjectNameNoPrefix.indexOf("."));

                String jsonFieldString = parseJSONField(jsonString, paths);

                result = result.replace(jsonObjectNameWithPrefix, jsonFieldString);
            }

        }

        log.debug("Story text after transformation: \n--------------------------------\n" + result
                + "\n--------------------------------");

        return result;

    }

    public String doExtractJsonVariable(String storyAsText, Map<String, String> blockMap) throws IOException {
        // parse json blocks

        String lineSeperator = System.getProperty("line.separator");
        LocalizedKeywords keywords = new LocalizedKeywords();

        StringBuilder parsedStoryText = new StringBuilder();
        StringBuilder block = null;
        String name = null;
        boolean inBlock = false;

        BufferedReader br = new BufferedReader(new StringReader(storyAsText));
        String line = br.readLine();
        while (line != null) {
            if (inBlock) {
                if (line.startsWith(JSON_KEYWORD) || line.startsWith(keywords.scenario()) || line.startsWith(keywords.examplesTable())) {
                    inBlock = false;
                    blockMap.put(name, block.toString());
                } else {
                    block.append(line.trim()).append(" ");
                    line = br.readLine();
                }
            } else {
                if (line.startsWith(JSON_KEYWORD)) {
                    inBlock = true;
                    block = new StringBuilder();
                    name = StringUtils.removeStart(line, JSON_KEYWORD).trim();
                    line = br.readLine();
                } else {
                    parsedStoryText.append(line).append(lineSeperator);
                    line = br.readLine();
                }
            }
        }

        if (inBlock) {
            inBlock = false;
            blockMap.put(name, block.toString());
        }

        log.debug("Json objects found in total: " + blockMap.size());

        // process Json key recursively (one json might contians another json.)
        for (Entry<String, String> entry : blockMap.entrySet()) {
            String key = JSON_PREFIX + entry.getKey() + JSON_SUFFIX;
            log.debug(key + " = " + entry.getValue());
        }

        // replace json references
        String result = parsedStoryText.toString();

        log.debug("Story text after extract Json variable: \n--------------------------------\n" + result
                + "\n--------------------------------");

        return result;

    }
}
