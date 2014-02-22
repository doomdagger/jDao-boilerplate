package com.doomdagger.config;

import java.io.IOException;
import java.util.Properties;

/**
 * for configuration. load properties from file -- project.properties
 * @author Li He
 *
 */
public class ProjectConfig {
	private static Properties props;
	static {
		props = new Properties();
		try {
			props.load(ProjectConfig.class
					.getResourceAsStream("/project.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String name) {
		return (String) props.get(name);
	}

	public static boolean getBooleanProperty(String name) {
		return Boolean.valueOf((String) props.get(name));
	}

	public static int getIntegerProperty(String name) {
		return Integer.valueOf((String) props.get(name));
	}

	public static double getDoubleProperty(String name) {
		return Double.valueOf((String) props.get(name));
	}

	public static void reloadProps() {
		props = new Properties();
		try {
			props.load(ProjectConfig.class
					.getResourceAsStream("/project.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
