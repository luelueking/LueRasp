package com.lue.rasp.visitor.adapter;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class DeserializationVisitorAdapter extends AdviceAdapter {

    public DeserializationVisitorAdapter(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM5, mv, access, name, desc);
    }

    @Override
    protected void onMethodEnter() {
        mv.visitTypeInsn(NEW, "xbear/javaopenrasp/filters/rce/DeserializationFilter");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "xbear/javaopenrasp/filters/rce/DeserializationFilter", "<init>", "()V", false);
        mv.visitVarInsn(ASTORE, 2);
        mv.visitVarInsn(ALOAD, 2);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKEVIRTUAL, "xbear/javaopenrasp/filters/rce/DeserializationFilterr", "filter", "(Ljava/lang/Object;)Z", false);

        Label label = new Label();
        mv.visitJumpInsn(IFNE, label);
        mv.visitTypeInsn(NEW, "java/io/IOException");
        mv.visitInsn(DUP);
        mv.visitLdcInsn("Fucking Hacker? deserialization security");
        mv.visitMethodInsn(INVOKESPECIAL, "java/io/IOException", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitInsn(ATHROW);
        mv.visitLabel(label);
    }
    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack, maxLocals);
    }
}
