package com.oracle.auto.web.jbehave_ext;

import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.parsers.StoryTransformer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class SplitExampleTransformer implements StoryTransformer {
	
	private static SplitExampleTransformer obj = new SplitExampleTransformer();
	private SplitExampleTransformer() { }
	
	private boolean defaultEnabled = false;
	private String enableFlag = "@SplitExample";
	private String disableFlag = "@NoSplitExample";
	
	public static SplitExampleTransformer instance() {
		return obj;
	}
	
	public String transform(String s) {
    	String storyContent = s;
    	StringBuilder newStoryBuffer = new StringBuilder(); // new story text
    	LocalizedKeywords keywords = new LocalizedKeywords();
    	String scenarioStr = keywords.scenario();
    	String exampleStr = keywords.examplesTable();
		String lineSeperator = System.getProperty("line.separator");
		BufferedReader br = new BufferedReader(new StringReader(storyContent));
		String line = null;
    	
		try {
			// append until finding first scenario
			while ((line = br.readLine()) != null && !line.startsWith(scenarioStr))
				newStoryBuffer.append(line + lineSeperator);

			while (true) {
				if (line == null) break;
				
				// start with scenario
				StringBuilder sbScenario = new StringBuilder();
				sbScenario.append(line + lineSeperator);

				boolean bSplitExample = defaultEnabled;
				
				// process until meeting next scenario
				while ((line = br.readLine()) != null && !line.startsWith(scenarioStr)) {
					
					// add into scenario until meeting example and has @SplitExample tag marked
					if (!line.startsWith(exampleStr) || !bSplitExample) {
						sbScenario.append(line + lineSeperator);
						if (line.startsWith(enableFlag))
							bSplitExample = true;
						else if (line.startsWith(disableFlag))
							bSplitExample = false;
						
						continue; // continue to read for next line.
					}
					
					// process example table, get tag
					StringBuffer sbExampleHeaders = new StringBuffer();
					sbExampleHeaders.append(line + lineSeperator);
					
					// get config
					while ((line = br.readLine()) != null && !line.isEmpty()) break;
					if (line == null) throw new RuntimeException( "**error** unexpected error: encouter end after meeting Example config. ");
					if (line.startsWith("{")) {
						sbExampleHeaders.append(line + lineSeperator); // record config 
						while ((line = br.readLine()) != null && !line.isEmpty()) break;
						if (line == null) throw new RuntimeException( "**error** unexpected error: encouter end after meeting Example header. ");
					}

					// get header
					sbExampleHeaders.append(line + lineSeperator);
					while ((line = br.readLine()) != null && !line.isEmpty()) break;
					if (line == null) throw new RuntimeException( "**error** unexpected error: encouter end after meeting Example first row. ");
					
					// process row
					do {
						newStoryBuffer.append(sbScenario); // add scenario
						newStoryBuffer.append(sbExampleHeaders); // add header
						newStoryBuffer.append(line + lineSeperator); // get row

						while ((line = br.readLine()) != null && !line.isEmpty()) break;  // go to next non-empty line
					} while (line != null && !line.isEmpty() && !line.startsWith(scenarioStr));  // not reach end and reach next scenario
					
					sbScenario = null; // finished process this scenario and meeting end or next scenario. set it as null.
					break; // break out existing scenario handling.
				}
				
				if (sbScenario != null) { // finish one scenario processing (normally caused no split for example)
					newStoryBuffer.append(sbScenario); // append last scenario in case that it's not processed by spliting
					if (line == null) 
						break; // reach end of file, exit
				} 
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
    	return newStoryBuffer.toString();
	}

	public boolean isDefaultEnabled() {
		return defaultEnabled;
	}

	public void setDefaultEnabled(boolean defaultEnabled) {
		this.defaultEnabled = defaultEnabled;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public String getDisableFlag() {
		return disableFlag;
	}

	public void setDisableFlag(String disableFlag) {
		this.disableFlag = disableFlag;
	}
	


}
