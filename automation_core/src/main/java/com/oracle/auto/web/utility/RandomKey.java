/**
 * 
 */
package com.oracle.auto.web.utility;

/**
 * @author madirv
 * Jul 31, 2014
 */
public class RandomKey {
	
	public static String getRandomUniqueKey(){
		String key = Long.toString(System.currentTimeMillis()).substring(8);
		return "rand_"+key;
	}

}
