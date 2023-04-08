package com.lue.rasp.hook;

import com.lue.rasp.context.Context;
import com.lue.rasp.context.ContextManager;
import io.undertow.util.HeaderValues;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 用于hook用户的http请求，并写入上下文中
 */
public class HttpHook implements HookInterface{

    /**
     * 绑定 request http 参数
     * ServletInitialHandler#handleFirstRequest(HttpServerExchange, ServletRequestContext)
     */
    public void storeRequestInfo(Object obj1, Object obj2) {
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

    private String toString(String[] arrays) {
        final StringBuilder buf = new StringBuilder(64);
        for (int i = 0; i < arrays.length; i++) {
            if (i > 0) {
                buf.append(",");
            }
            if (arrays[i] != null) {
                buf.append(arrays[i]);
            }
        }
        return buf.toString();
    }
}
