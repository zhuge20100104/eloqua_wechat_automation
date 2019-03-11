package com.oracle.auto.web.comp.ext;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

// xtype: tree?
public class ExtContentTree  extends ExtComponentBase 
{

	public ExtContentTree(WebDriverSeleniumPageEx page, String locator) {
		super(page, locator);
	}

	private boolean isLoaded(String locator, String path)
	{
		String isLoaded = 
				
		"function isLoaded(ch, arr){" + 
		"var myVar = true;" +
		"if(arr.length==0) return true;" + 
		"for(var i = 0; i < ch.length; i++){" +
		"if(arr[0] == ch[i].raw.object_name){" +
		"myVar = isLoaded(ch[i].childNodes, arr.slice(1));" +
		"return myVar && ch[i].isExpanded();}}}" +
		"var tree = %s;" + 
		"var root = tree.getRootNode();" + 
		"root.expand();" +
		"var split_array = '%s'.split('/');" + 
		"if(root.raw.object_name == split_array[1]){" +
		"return isLoaded(root.childNodes, split_array.slice(2));}";
		isLoaded = String.format(isLoaded, locator,path);

		return page.executeScriptEx(isLoaded).AsBool();
	}
	
	@Override
	// isReady for read and write: basic Ext is loaded, then element present, rendered, visible and valid
	public boolean isReady() {
		return super.isReady() && isLoaded("", "");
	}
	
	public void selectPath(String locator, String nodePath) throws Exception
	{
		String traverseAndExpand = "var idpath = \"\";" +
				"var nodeAuto;" + 
				"function traverse(ch, arr){" +
				
				"for(var i = 0; i < ch.length; i++){" +
				"if(arr[0] == ch[i].raw.object_name){" +
				"idpath = idpath.concat(\"/\", ch[i].getId());" +
				"nodeAuto=ch[i];" + 
				"if(ch[i].isExpanded()){" +
				"traverse(ch[i].childNodes, arr.slice(1));break;}" + 
				"else {ch[i].expand();return;}}}}" + 
				
				"var tree = %s;" +
				"var root = tree.getRootNode();" +
				"root.expand();" +
				"var split_array = '%s'.split(\"/\");" +
				"if(root.raw.object_name == split_array[1]){" +
				"idpath = idpath.concat(\"/\", root.getId());" +
				"traverse(root.childNodes, split_array.slice(2));}" ;
				
		String selectPath = String.format(traverseAndExpand, locator, nodePath) + "tree.selectPath(idpath);";
		
		String[] st = nodePath.split("/");
		String subPath = "";
		
		for(int i =1;i < st.length; i ++)
		{
			subPath = subPath + "/" + st[i];
			String script = String.format(traverseAndExpand, locator, subPath);
			
			page.executeScript(script);
			
			////waitTillLoaded(locator,subPath);
		}
		
		page.executeScript(selectPath);
	}

	
	public void clickNode(String locator, String nodePath) throws Exception 
	{
		String traverseAndExpand = "var idpath = \"\";" +
				"var nodeAuto;" + 
				"function traverse(ch, arr){" +
				
				"for(var i = 0; i < ch.length; i++){" +
				"if(arr[0] == ch[i].raw.object_name){" +
				"idpath = idpath.concat(\"/\", ch[i].getId());" +
				"nodeAuto=ch[i];" + 
				"if(ch[i].isExpanded()){" +
				"traverse(ch[i].childNodes, arr.slice(1));break;}" + 
				"else {ch[i].expand();return;}}}}" + 
				
				"var tree = %s;" +
				"var root = tree.getRootNode();" +
				"root.expand();" +
				"var split_array = '%s'.split(\"/\");" +
				"if(root.raw.object_name == split_array[1]){" +
				"idpath = idpath.concat(\"/\", root.getId());" +
				"traverse(root.childNodes, split_array.slice(2));}" ;
				
		String selectPath = String.format(traverseAndExpand, locator, nodePath) + 
		
		"var path_array = idpath.split(\"/\");" +
		"var nodetoclick = root.findChild(\"id\", path_array[path_array.length-1], \"true\");" +
		"tree.fireEvent(\"itemclick\", tree, nodetoclick);";
		
		String[] st = nodePath.split("/");
		String subPath = "";
		
		for(int i =1;i < st.length; i ++)
		{
			subPath = subPath + "/" + st[i];
			String script = String.format(traverseAndExpand, locator, subPath);
			
			page.executeScript(script);
			
			//waitTillLoaded(locator,subPath);
		}
		
		page.executeScript(selectPath);
	}

	
	public void expandAll(String locator) throws Exception 
	{
		String script = "var tree = " + locator + ";" +
						"tree.expandAll();";
		
		page.executeScript(script);
	}

	
	public void collapseAll(String locator) throws Exception 
	{
		String script = "var tree = " + locator + ";" +
				"tree.collapseAll();";

		page.executeScript(script);
	}

	
	public void expandNode(String locator, String nodePath) throws Exception 
	{
		String traverseAndExpand = "var idpath = \"\";" +
				"var nodeAuto;" + 
				"function traverse(ch, arr){" +
				
				"for(var i = 0; i < ch.length; i++){" +
				"if(arr[0] == ch[i].raw.object_name){" +
				"idpath = idpath.concat(\"/\", ch[i].getId());" +
				"nodeAuto=ch[i];" + 
				"if(ch[i].isExpanded()){" +
				"traverse(ch[i].childNodes, arr.slice(1));break;}" + 
				"else {ch[i].expand();return;}}}}" + 
				
				"var tree = %s;" +
				"var root = tree.getRootNode();" +
				"root.expand();" +
				"var split_array = '%s'.split(\"/\");" +
				"if(root.raw.object_name == split_array[1]){" +
				"idpath = idpath.concat(\"/\", root.getId());" +
				"traverse(root.childNodes, split_array.slice(2));}" ;
				
		String selectPath = String.format(traverseAndExpand, locator, nodePath);
		
		String[] st = nodePath.split("/");
		String subPath = "";
		
		for(int i =1;i < st.length; i ++)
		{
			subPath = subPath + "/" + st[i];
			String script = String.format(traverseAndExpand, locator, subPath);
			
			page.executeScript(script);
			
			//waitTillLoaded(locator,subPath);
		}
		
		page.executeScript(selectPath);
	}

	
	public void collapseNode(String locator, String nodePath) throws Exception 
	{
		String traverseAndExpand = "var idpath = \"\";" +
				"var nodeAuto;" + 
				"function traverse(ch, arr){" +
				
				"for(var i = 0; i < ch.length; i++){" +
				"if(arr[0] == ch[i].raw.object_name){" +
				"idpath = idpath.concat(\"/\", ch[i].getId());" +
				"nodeAuto=ch[i];" + 
				"if(ch[i].isExpanded()){" +
				"traverse(ch[i].childNodes, arr.slice(1));break;}" + 
				"else {ch[i].expand();return;}}}}" + 
				
				"var tree = %s;" +
				"var root = tree.getRootNode();" +
				"root.expand();" +
				"var split_array = '%s'.split(\"/\");" +
				"if(root.raw.object_name == split_array[1]){" +
				"idpath = idpath.concat(\"/\", root.getId());" +
				"traverse(root.childNodes, split_array.slice(2));}" ;
				
		String selectPath = String.format(traverseAndExpand, locator, nodePath) + 
		
		"var path_array = idpath.split(\"/\");" +
		"var nodetocollapse = root.findChild(\"id\", path_array[path_array.length-1], \"true\");" +
		"nodetocollapse.collapse();";

		String[] st = nodePath.split("/");
		String subPath = "";
		
		for(int i =1;i < st.length; i ++)
		{
			subPath = subPath + "/" + st[i];
			String script = String.format(traverseAndExpand, locator, subPath);
			
			page.executeScript(script);
			
			//waitTillLoaded(locator,subPath);
		}
		
		page.executeScript(selectPath);
	}

	
	public boolean isNodeExist(String locator, String nodePath) throws Exception 
	{
		String traverseAndExpand = "var idpath = \"\";" +
				"var nodeAuto;" + 
				"function traverse(ch, arr){" +
				
				"for(var i = 0; i < ch.length; i++){" +
				"if(arr[0] == ch[i].raw.object_name){" +
				"idpath = idpath.concat(\"/\", ch[i].getId());" +
				"nodeAuto=ch[i];" + 
				"if(ch[i].isExpanded()){" +
				"traverse(ch[i].childNodes, arr.slice(1));break;}" + 
				"else {ch[i].expand();return;}}}}" + 
				
				"var tree = %s;" +
				"var root = tree.getRootNode();" +
				"root.expand();" +
				"var split_array = '%s'.split(\"/\");" +
				"if(root.raw.object_name == split_array[1]){" +
				"idpath = idpath.concat(\"/\", root.getId());" +
				"traverse(root.childNodes, split_array.slice(2));}" ;
				
		String selectPath = String.format(traverseAndExpand, locator, nodePath) + 
		
		"var path_array = idpath.split(\"/\");" +
		"var lastnode = root.findChild(\"id\", path_array[path_array.length-1], \"true\");" +
		"if(lastnode.raw.object_name == split_array[split_array.length-1]){ " +
		"return true;}else{ return false;};";
		
		String[] st = nodePath.split("/");
		String subPath = "";
		
		for(int i =1;i < st.length; i ++)
		{
			subPath = subPath + "/" + st[i];
			String script = String.format(traverseAndExpand, locator, subPath);
			
			page.executeScript(script);
			
			//waitTillLoaded(locator,subPath);
		}
		
		return page.executeScriptEx(selectPath).AsBool();
	}

	
	public void contextClickNode(String locator, String nodePath) throws Exception
	{
		selectPath(locator, nodePath);

		String traverseAndExpand = "var nodeAuto;" +
		"function traverse(ch, arr){" +
		"    if(arr.length==0) nodeAuto= ch[0].parentNode;" +
		"for(var i = 0; i < ch.length; i++){" +
		"if(arr[0] == ch[i].raw.object_name){" +
		"if(ch[i].isExpanded()){" +
		"traverse(ch[i].childNodes, arr.slice(1));" +
		"break;}else{ch[i].expand();return;}}}}" +
		"var tree = %s;" +
		"var root = tree.getRootNode();" +
		"root.expand();" +
		"var split_array = '%s'.split('/');" +
		"if(root.raw.object_name == split_array[1]){" +
		"traverse(root.childNodes, split_array.slice(2));}" + 
		"return nodeAuto.internalId;";
		String selectPath = String.format(traverseAndExpand, locator, nodePath); 
		
		String id = page.executeScriptEx(selectPath).str();
		String xpath = ".//*[contains(@id,'"+id+"')]";
		page.contextClick(xpath);
	}	
}
