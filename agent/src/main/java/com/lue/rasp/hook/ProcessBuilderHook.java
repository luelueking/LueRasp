package com.lue.rasp.hook;

import com.lue.rasp.context.Context;
import com.lue.rasp.context.ContextManager;

import java.util.Arrays;
import java.util.List;

public class ProcessBuilderHook implements HookInterface{

    public static boolean filter(List<String> commands) {
        Context context = ContextManager.getContext();
        if (context == null) {
            System.out.println("不是用户请求导致的命令执行，放行。。。");
            return true;
        }

        String[] commandArr = commands.toArray(new String[commands.size()]);
        System.out.println("Hacker!!! rce command:" + Arrays.toString(commandArr));
        return false;
    }
}