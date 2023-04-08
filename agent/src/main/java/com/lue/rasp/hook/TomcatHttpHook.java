package com.lue.rasp.hook;

import com.lue.rasp.context.Context;
import com.lue.rasp.context.ContextManager;
import com.lue.rasp.tool.StringUtils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于hook tomcat的http请求
 */
public class TomcatHttpHook implements HookInterface{


    /**
     * 绑定 request http 参数
     * org.apache.catalina.core.StandardWrapperValve#invoke(org.apache.catalina.connector.Request, org.apache.catalina.connector.Response)
     * 需要注意参数 Request、Response的包名称
     */
    public void storeRequestInfo(Object obj1,Object obj2) {
        System.out.println("开始存储request信息。。。");
        System.out.println(obj1);
        System.out.println(obj2);
        if (ContextManager.isNull()) {
            Context context = new Context();
            ContextManager.addContext(context);
        }
        Context context = ContextManager.getContext();
        context.setRequest(obj1);
        context.setResponse(obj2);
    }

}
