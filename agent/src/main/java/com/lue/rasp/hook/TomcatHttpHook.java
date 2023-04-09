package com.lue.rasp.hook;

import com.lue.rasp.context.Context;
import com.lue.rasp.context.ContextManager;


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
            System.out.println("context 为null，新建一个");
            Context context = new Context();
            ContextManager.addContext(context);
        }
        Context context = ContextManager.getContext();
        context.setRequest(obj1);
        context.setResponse(obj2);
    }

    /**
     * 清除上下文
     * 这里"取巧" 在下次请求之前清除线程变量
     * org.apache.catalina.connector.CoyoteAdapter#service(org.apache.coyote.Request, org.apache.coyote.Response)
     */
    public void cleanRequestInfo(Object obj1,Object obj2) {
        System.out.println("正在清除context...");
        ContextManager.requestContext.remove();
        System.out.println("清除完成"+ContextManager.isNull());
    }
}
