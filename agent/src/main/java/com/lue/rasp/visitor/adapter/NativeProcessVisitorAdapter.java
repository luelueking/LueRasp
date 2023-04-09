package com.lue.rasp.visitor.adapter;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

public class NativeProcessVisitorAdapter extends AdviceAdapter {

    public NativeProcessVisitorAdapter(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM5, mv, access, name, desc);
    }
    @Override
    public void visitEnd() {
        System.out.println("开始处理NativeProcess逻辑");
//        Label beginLabel = new Label();
//        Label endLabel = new Label();
//        Label startCatchBlock = new Label();
//        Label endCatchBlock = new Label();
//        int newlocal = -1;


        // 创建Hook对象
        mv.visitTypeInsn(NEW, "com/lue/rasp/hook/NativeProcessHook");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "com/lue/rasp/hook/NativeProcessHook", "<init>", "()V", false);
        mv.visitVarInsn(ASTORE, 12);
        System.out.println("成功创建hook对象");

        // 加载需要传入到storeRequestInfo()方法中的两个对象
        mv.visitVarInsn(ALOAD, 12); // 把hook加到栈顶
        mv.visitVarInsn(ALOAD, 2); // 将第一个参数加载到栈顶
        mv.visitVarInsn(ALOAD, 3); // 将第二个参数加载到栈顶

        mv.visitMethodInsn(INVOKEVIRTUAL, "com/lue/rasp/hook/NativeProcessHook", "filter", "(Ljava/lang/Object;Ljava/lang/Object;)Z", false);


        Label label = new Label(); // 创建一个标签，表示后续的跳转位置。
        mv.visitJumpInsn(IFNE, label);// 判断栈顶的布尔值是否是 true，如果是，则跳转到标签 label 执行后续代码；否则，继续执行下一条指令
        mv.visitTypeInsn(NEW, "java/io/IOException"); // 创建个异常对象
        mv.visitInsn(DUP); // 将刚创建的对象复制一份，两份都保留在栈顶。
        mv.visitLdcInsn("Fucking Hacker?!");// 将一个字符串压入栈顶，表示异常消息。
        mv.visitMethodInsn(INVOKESPECIAL, "java/io/IOException", "<init>", "(Ljava/lang/String;)V", false);// 调用 java.io.IOException 的构造函数，并初始化新创建的对象。
        mv.visitInsn(ATHROW);// 抛出刚创建的 java.io.IOException 异常，并且终止该方法的执行。
        mv.visitLabel(label);// 根据label返回现场
    }

}
