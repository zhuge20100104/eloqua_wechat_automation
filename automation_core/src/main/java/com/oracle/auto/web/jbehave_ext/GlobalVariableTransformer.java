package com.oracle.auto.web.jbehave_ext;

import org.jbehave.core.parsers.StoryTransformer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GlobalVariableTransformer implements StoryTransformer {
    private static GlobalVariableTransformer obj = new GlobalVariableTransformer();
    private Map<String, String> variableMap = new HashMap<>();
    private Map<String, UnitTransformer> transformMap = new HashMap<>();

    private String prefix = "%";
    private String postfix = "%";

//	private String prefix_recurve = "${";
//	private String postfix_recurve = "}";

    private String prefix_json = "#";
    private String postfix_json = "#";

    private boolean enableSystemProperty = true;
    private boolean enableSystemEnv = true;

    private GlobalVariableTransformer() {
    }

    public static GlobalVariableTransformer instance() {
        return obj;
    }

    protected String doTransform(String s, String prefix, String postfix, boolean escapseBacklashAndQuata) {
        Pattern p = Pattern.compile(prefix + ".*?" + postfix);
        Matcher m = p.matcher(s);
        StringBuilder sb = new StringBuilder();
        int nextStartChar = 0;
        String key;
        String value;
        while (m.find()) {
            if (nextStartChar < m.start())
                sb.append(s.subSequence(nextStartChar, m.start()));
            key = m.group().substring(prefix.length(), m.group().length() - postfix.length());
            if (key.isEmpty()) continue;

            value = variableMap.get(key);

            // transform for variable
            if (value == null && enableSystemProperty)
                value = System.getProperty(key);
            if (value == null && enableSystemEnv)
                value = System.getenv(key);

            // transform for callback
            UnitTransformer transformer = transformMap.get(key);
            if (transformer != null) {
                if (!transformer.stateType())
                    value = transformer.transform(key);
                else {
                    // check if called before, if yes, directly use it
                    String cacheValue = stateUnitTransformerCache.get(key);
                    if (cacheValue == null) {
                        cacheValue = transformer.transform(key);
                        stateUnitTransformerCache.put(key, cacheValue);
                    }
                    value = cacheValue;
                }
            }

            if (value == null)
                sb.append(prefix + key + postfix); // directly insert key instead.
            else {
                if (escapseBacklashAndQuata)
                    value = value.replace("\\", "\\\\").replace("\"", "\\\"");
                sb.append(value);
            }
            nextStartChar = m.end();
        }
        if (nextStartChar <= s.length() - 1)
            sb.append(s.substring(nextStartChar));
        return sb.toString();
    }

    // used in every calling of transform
    private Map<String, String> stateUnitTransformerCache = null;

    public String transform(String s) {
        // reset cache
        stateUnitTransformerCache = new HashMap<>();
        s = doTransform(s, prefix, postfix, false);
        return doTransform(s, prefix_json, postfix_json, true);
    }

//	public void setPrefix(String str) {
//		prefix = str;
//	}
//	
//	public void setPosfix(String str) {
//		postfix = str;
//	}

    public void insertValue(String prop, String value) {
        variableMap.put(prop, value);
    }

    public void addUnitTranformer(String prop, UnitTransformer transformer) {
        transformMap.put(prop, transformer);
    }

    public boolean isEnableSystemEnv() {
        return enableSystemEnv;
    }

    public void setEnableSystemEnv(boolean enableSystemEnv) {
        this.enableSystemEnv = enableSystemEnv;
    }

    public boolean isEnableSystemProperty() {
        return enableSystemProperty;
    }

    public void setEnableSystemProperty(boolean enableSystemProperty) {
        this.enableSystemProperty = enableSystemProperty;
    }
}
