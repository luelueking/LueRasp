package com.lue.rasp.hook;

import com.lue.rasp.context.Context;
import com.lue.rasp.context.ContextManager;

import java.util.Arrays;

public class NativeProcessHook implements HookInterface{

    public boolean filter(Object obj1,Object obj2) {
        Context context = ContextManager.getContext();
        if (context == null) {
            System.out.println("不是用户请求导致的命令执行，放行。。。");
            return true;
        }

        byte[] prog = (byte[]) obj1;     // 命令
        byte[] argBlock = (byte[]) obj2; // 参数
        String cmd = getCommand(prog);
        String args = getArgs(argBlock);

        System.out.println("Hacker!! rce command:" + cmd + " " + args);
        return false;
    }

    /**
     * 命令如何转为字符串，参考jdk的方法即可
     *
     * @param command
     * @return
     */
    public static String getCommand(byte[] command) {
        if (command != null && command.length > 0) {
            // cmd 字符串的范围: [0,command.length - 1), 因为command最后一位为 \u0000 字符，需要去掉
            return new String(command, 0, command.length - 1);
        }
        return "";
    }

    // 参数
    public static String getArgs(byte[] args) {
        StringBuilder stringBuffer = new StringBuilder();
        if (args != null && args.length > 0) {
            int position = 0;
            for (int i = 0; i < args.length; i++) {
                // 空格是字符或者参数的分割符号
                if (args[i] == 0) {
                    stringBuffer.append(new String(Arrays.copyOfRange(args, position, i)));
                    position = i + 1;
                }
            }
        }
        return stringBuffer.toString();
    }
}
