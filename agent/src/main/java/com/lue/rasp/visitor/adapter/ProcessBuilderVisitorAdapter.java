package com.lue.rasp.visitor.adapter;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class ProcessBuilderVisitorAdapter extends AdviceAdapter {

    public ProcessBuilderVisitorAdapter(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM5, mv, access, name, desc);
    }


    // 进入方法时调用
    @Override
    public void onMethodEnter() {
        super.onMethodEnter();

        // 创建hook对象，并将其存储在本地变量表索引为 1 的位置
        mv.visitTypeInsn(NEW, "com/lue/rasp/hook/ProcessBuilderHook");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "com/lue/rasp/hook/ProcessBuilderHook", "<init>", "()V", false);
        mv.visitVarInsn(ASTORE, 1);

        mv.visitVarInsn(ALOAD, 1); // 把hook加到栈顶
        mv.visitVarInsn(ALOAD, 0); // 把this加到栈顶
        mv.visitFieldInsn(GETFIELD, "java/lang/ProcessBuilder", "command", "Ljava/util/List;");// 获取command字段值，并将其压入栈顶。
        // 调用过滤器对象的 filter(Object obj) 方法，并将栈顶的 java.util.List 对象作为参数传递给该方法。它返回一个布尔值指示是否应该允许此命令行参数。
        mv.visitMethodInsn(INVOKESTATIC, "com/lue/rasp/hook/ProcessBuilderHook", "filter", "(Ljava/util/List;)Z", false);

        Label label = new Label(); // 创建一个标签，表示后续的跳转位置。
        mv.visitJumpInsn(IFNE, label);// 判断栈顶的布尔值是否是 true，如果是，则跳转到标签 label 执行后续代码；否则，继续执行下一条指令
        mv.visitTypeInsn(NEW, "java/io/IOException"); // 创建个异常对象
        mv.visitInsn(DUP); // 将刚创建的对象复制一份，两份都保留在栈顶。
        mv.visitLdcInsn("Fucking Hacker?!");// 将一个字符串压入栈顶，表示异常消息。
        mv.visitMethodInsn(INVOKESPECIAL, "java/io/IOException", "<init>", "(Ljava/lang/String;)V", false);// 调用 java.io.IOException 的构造函数，并初始化新创建的对象。
        mv.visitInsn(ATHROW);// 抛出刚创建的 java.io.IOException 异常，并且终止该方法的执行。
        mv.visitLabel(label);// 根据label返回现场
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack, maxLocals);
    }
}
