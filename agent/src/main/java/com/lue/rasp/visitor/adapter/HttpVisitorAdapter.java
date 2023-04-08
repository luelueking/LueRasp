package com.lue.rasp.visitor.adapter;

import com.lue.rasp.hook.HttpHook;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

public class HttpVisitorAdapter extends AdviceAdapter {

    public HttpVisitorAdapter(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM5, mv, access, name, desc);
    }

    // TODO 进入方法时调用
    @Override
    public void onMethodEnter() {
        System.out.println("开始HttpHook逻辑处理");
        super.onMethodEnter();

        // 创建HttpHook对象
        mv.visitTypeInsn(NEW, "com/lue/rasp/hook/HttpHook");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "com/lue/rasp/hook/HttpHook", "<init>", "()V", false);
        mv.visitVarInsn(ASTORE, 2);

        // 加载需要传入到storeRequestInfo()方法中的两个对象
        mv.visitVarInsn(ALOAD, 2); // 将HttpHook对象加载到栈顶
        mv.visitVarInsn(ALOAD, 1); // 将第一个参数加载到栈顶
        mv.visitVarInsn(ALOAD, 3); // 将第二个参数加载到栈顶

        // 调用storeRequestInfo()方法
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/lue/rasp/hook/HttpHook", "storeRequestInfo", "(Ljava/lang/Object;Ljava/lang/Object;)V", false);

        // 追加异常处理代码
        Label label = new Label(); // 创建一个标签，表示后续的跳转位置。
        mv.visitJumpInsn(GOTO, label); // 跳过异常处理的代码
        mv.visitLabel(label); // 标记label位置，用于异常处理的代码跳转
        System.out.println("结束HttpHook逻辑处理");
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack, maxLocals);
    }
}
