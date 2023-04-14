package com.lue.rasp;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class RASPLoader {

    public static ClassLoader RASPClassLoader;

    /**
     * 不是应用加载器（Application ClassLoader）也不是扩展类加载器（Extension ClassLoader）
     * 而是它们的父加载器——系统类加载器（System ClassLoader），也就是应用程序类加载器所在的层次结构中的父加载器。
     **/
    public RASPLoader(){
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        while (systemClassLoader.getParent() != null
                && !"sun.misc.Launcher$ExtClassLoader".equals(systemClassLoader.getClass().getName())) {
            systemClassLoader = systemClassLoader.getParent();
        }
        RASPClassLoader = systemClassLoader;
    }

    public void loaderJar(String jarName) throws Throwable{
        File originFile = new File(jarName);
        if(!originFile.exists()){
            throw new IOException("rasp-core.jar not found");
        }
        if (ClassLoader.getSystemClassLoader() instanceof URLClassLoader) {
            Method method = Class.forName("java.net.URLClassLoader").getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(RASPClassLoader, originFile.toURI().toURL());
            method.invoke(ClassLoader.getSystemClassLoader(), originFile.toURI().toURL());
        }
    }

}
