package com.lue.rasp.hook;

import java.util.Arrays;
import java.util.List;

public class ProcessBuilderHook {

    public static void start(List<String> commands) {
        String[] commandArr = commands.toArray(new String[commands.size()]);

    }

    public static boolean filter(List<String> commands) {
        String[] commandArr = commands.toArray(new String[commands.size()]);
        System.out.println("Hacker!!! rce command:" + Arrays.toString(commandArr));
        return false;
    }
}