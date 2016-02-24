package com.codingforcookies.betterrecords.common.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ClasspathInjector{

	private static final Class<?>[] parameters = new Class[]{URL.class};

	public static void addFile(File f) throws IOException{
		addURL(f.toURI().toURL());
	}

	public static void addURL(URL url) throws IOException{
		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class<?> sysclass = URLClassLoader.class;
		try{
			Method method = sysclass.getDeclaredMethod("addURL", parameters);
			method.setAccessible(true);
			method.invoke(sysloader, new Object[]{url});
		}catch(Throwable t){
			t.printStackTrace();
			throw new IOException("Error, could not inject file to system classloader");
		}
	}
}