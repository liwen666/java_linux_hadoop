package com.java.hadoop.utils;


import java.net.URL;

public class SystemConfig {

	public static String getConfigPath() {

		String os = System.getProperty("os.name");
		URL resource = SystemConfig.class.getResource("");
		String dataPath = resource.getPath();
		String configPath=null;
		if(os.toLowerCase().startsWith("win") || os.toLowerCase().startsWith("mac")){
			String replace = dataPath.substring(0, dataPath.indexOf("/com/")).replace("/target/classes", "");
			 configPath= replace+"/bin/config/";
		}else{
			String substring = dataPath.substring(0, dataPath.indexOf("bin/config/"));
			 configPath =substring.replace("file:","");
		}
		return configPath;
	}



}
