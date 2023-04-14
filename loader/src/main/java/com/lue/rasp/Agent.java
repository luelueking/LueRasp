package com.lue.rasp;

import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;

public class Agent {

    // TODO 写死了 不优雅
    private static final String raspJarPath = "/Users/zhchen/Downloads/github-workspace/LueRasp/agent/target/agent.jar";
    private static final String raspLoaderJarPath = "/Users/zhchen/Downloads/github-workspace/LueRasp/loader/target/loader.jar";
    private static final String RASPLauncherClassName = "com.lue.rasp.RASPLauncher";

//    public static void premain(String agentArgs, Instrumentation inst) {
//        inst.addTransformer(new AgentTransform(inst));
//    }

    /**
     * 运行前加载agent
     *
     * @param agentArgs
     * @param instrumentation
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        init(instrumentation);
    }

    /**
     * 初始化
     * @param instrumentation
     */
    private static synchronized void init(Instrumentation instrumentation) {
        System.out.println("[*]CloudRASP start loading");
        try {
            install(instrumentation);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("[*]CloudRASP load successfully");
    }

    /**
     * 安装RASP
     * @param instrumentation inst
     * @throws Throwable 安装异常
     */
    private static synchronized void install(Instrumentation instrumentation) throws Throwable {
        instrumentation.appendToBootstrapClassLoaderSearch(new JarFile(raspLoaderJarPath));
        RASPLoader raspLoader = new RASPLoader();
        raspLoader.loaderJar(raspJarPath);
        Class<?> RASPLauncher = RASPLoader.RASPClassLoader.loadClass(RASPLauncherClassName);
        RASPLauncher.getDeclaredMethod("launch",Instrumentation.class).invoke(RASPLauncher.newInstance(),instrumentation);
    }
}
