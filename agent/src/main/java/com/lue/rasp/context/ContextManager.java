package com.lue.rasp.context;

import com.lue.rasp.hook.HookInterface;
import com.lue.rasp.tool.ReflectUtils;

import java.lang.reflect.Field;


public class ContextManager {

    // 使用ThreadLocal 保存请求上下文
    public static ThreadLocal<Context> requestContext = new ThreadLocal<Context>();


//    /**
//     * 为hook传递上下文
//     * @param hook hook
//     */
//    private void injectContext(HookInterface hook) {
//        try {
//            Field context = hook.getClass().getDeclaredField("requestContext");
//            ReflectUtils.writeField(context,hook,requestContext,true);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }

    public static Context getContext() {
        return requestContext.get();
    }

}
