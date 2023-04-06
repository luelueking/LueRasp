package com.lue.rasp.visitor.adapter;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class MySqlVisitorAdapter extends AdviceAdapter {

    public MySqlVisitorAdapter(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM5, mv, access, name, desc);
    }

    // 进入方法时调用
    @Override
    public void onMethodEnter() {
        super.onMethodEnter();

        // 创建hook对象，并将其存储在本地变量表索引为 1 的位置
        mv.visitTypeInsn(NEW, "com/lue/rasp/hook/MySqlHook");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "com/lue/rasp/hook/MySqlHook", "<init>", "()V", false);
        mv.visitVarInsn(ASTORE, 2);
        mv.visitVarInsn(ALOAD, 2);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/lue/rasp/hook/MySqlHook", "filter", "(Ljava/lang/String;)Z", false);

        Label label = new Label(); // 创建一个标签，表示后续的跳转位置。
        mv.visitJumpInsn(IFNE, label);
        mv.visitTypeInsn(NEW, "java/sql/SQLException");
        mv.visitInsn(DUP);
        mv.visitLdcInsn("Fucking Hacker?!");
        mv.visitMethodInsn(INVOKESPECIAL, "java/sql/SQLException", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitInsn(ATHROW);
        mv.visitLabel(label);// 根据label返回现场

    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack, maxLocals);
    }
}
