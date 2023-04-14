package com.lue.rasp.hook;


import org.dom4j.io.SAXReader;

public class XXEHook implements HookInterface{

    private static final String FEATURE_DEFAULTS_1 = "http://apache.org/xml/features/disallow-doctype-decl";

    public static void filter(Object obj){
        System.out.println("开启xxe保护");
        System.out.println(obj);
        if (obj instanceof SAXReader) {
            try {
                SAXReader factory = (SAXReader) obj;
                // 进行xxe安全处理
                factory.setFeature(FEATURE_DEFAULTS_1, true);
            } catch (Exception e) {
                // 处理异常并打印错误信息
                e.printStackTrace();
            }
        }
    }
}
