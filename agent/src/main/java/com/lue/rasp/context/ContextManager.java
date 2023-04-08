package com.lue.rasp.context;

public class ContextManager {

    // 使用ThreadLocal 保存请求上下文
    public static ThreadLocal<Context> requestContext = new ThreadLocal<Context>();


    public static Context getContext() {
        return requestContext.get();
    }

    public static boolean isNull() {
        return requestContext.get()==null;
    }

    public static void addContext(Context context) {
        requestContext.set(context);
    }

}
