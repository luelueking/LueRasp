package com.lue.rasp.visitor.adapter;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

public class XXEVisitorAdapter extends AdviceAdapter {

    private Type returnType;

    public XXEVisitorAdapter(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM5, mv, access, name, desc);
    }

    @Override
    public void onMethodEnter() {
        super.onMethodEnter();
        System.out.println("开始处理xxe逻辑");

        // 创建hook对象，并将其存储在本地变量表索引为 1 的位置
        mv.visitTypeInsn(NEW, "com/lue/rasp/hook/XXEHook");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "com/lue/rasp/hook/XXEHook", "<init>", "()V", false);
        mv.visitVarInsn(ASTORE, 1);

        mv.visitVarInsn(ALOAD, 1); // 把hook加到栈顶
        mv.visitVarInsn(ALOAD, 0); // 把this加到栈顶
        mv.visitMethodInsn(INVOKESTATIC, "com/lue/rasp/hook/XXEHook", "filter", "(Ljava/lang/Object;)V", false);

    }


    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack, maxLocals);
    }
}
