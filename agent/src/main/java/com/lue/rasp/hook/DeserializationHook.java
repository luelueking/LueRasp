package com.lue.rasp.hook;

import com.lue.rasp.context.Context;
import com.lue.rasp.context.ContextManager;

import java.io.ObjectStreamClass;

public class DeserializationHook implements HookInterface{

    public static boolean filter(Object forCheck) {

        Context context = ContextManager.getContext();
        if (context == null) {
            System.out.println("不是用户请求导致的反序列化，放行。。。");
            return true;
        }
        ObjectStreamClass desc = (ObjectStreamClass) forCheck;
        String className = desc.getName();
        // TODO 增加模式 白名单 黑名单这种 目前直接拒绝
        return false;
    }
}
