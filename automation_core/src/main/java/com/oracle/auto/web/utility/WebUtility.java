package com.oracle.auto.web.utility;

public class WebUtility {
	// utility for javascript special charactor escape
	public static String escape2JS(String s) {
	    StringBuffer sb = new StringBuffer ();    
	    for (int i=0; i<s.length(); i++) {
	        char c = s.charAt(i);    
	        switch (c) {    
	        case '\"':    
	            sb.append("\\\"");    
	            break;    
	        case '\'':    
	            sb.append("\\'");    
	            break;    
	        case '\\':    
	            sb.append("\\\\");    
	            break;    
	        case '/':    
	            sb.append("\\/");    
	            break;    
	        case '\b':    
	            sb.append("\\b");    
	            break;    
	        case '\f':    
	            sb.append("\\f");    
	            break;    
	        case '\n':    
	            sb.append("\\n");    
	            break;    
	        case '\r':    
	            sb.append("\\r");    
	            break;    
	        case '\t':    
	            sb.append("\\t");    
	            break;    
	        default:    
	            sb.append(c);    
	        }
	    }
	    return sb.toString();    
	}

	// utility for javascript special charactor escape
	public static String escape2JSReg(String s, String browser) {
		// basic escapte
		s = escape2JS(s);
		
		// further escape
	    StringBuffer sb = new StringBuffer ();    
	    for (int i=0; i<s.length(); i++) {
	        char c = s.charAt(i);    
	        switch (c) {    
	        case '$':    
	            sb.append("[$]");    
	            break;    
	        case '^':    
	            sb.append("[^]");    
	            break;    
	        case '&':    
	            sb.append("[&]");    
	            break;    
	        case '*':    
	            sb.append("[*]");    
	            break;    
	        case '(':    
	            sb.append("[(]");    
	            break;    
	        case ')':    
	            sb.append("[)]");    
	            break;    
	        case '+':    
	            sb.append("[+]");    
	            break;    
	        case '{':    
	            sb.append("[{]");    
	            break;    
	        case '}':    
	            sb.append("[}]");    
	            break;    
	        case ':':    
	            sb.append("[:]");    
	            break;    
	        case '<':    
	            sb.append(browser.contains("iexplore") ? "." : "[<]");    
	            break;    
	        case '>':    
	            sb.append("[>]");    
	            break;    
	        case '?':    
	            sb.append("[?]");
	            break;    
	        case '.':    
	            sb.append("[.]");    
	            break;    
	        case '[':    
	            sb.append("[[]");    
	            break;    
	        default:    
	            sb.append(c);    
	        }
	    }
	    return sb.toString();    
	}

}
