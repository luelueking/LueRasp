package com.lue.rasp.visitor;

import com.lue.rasp.visitor.adapter.ProcessBuilderVisitorAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class ProcessBuilderVisitor extends ClassVisitor implements Opcodes {

    public ProcessBuilderVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

        if ("start".equals(name) && "()Ljava/lang/Process;".equals(desc)) {
            System.out.println(name + "方法的描述符是：" + desc);

            return new ProcessBuilderVisitorAdapter(mv, access, name, desc);
//            return new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
//                @Override
//                public void visitCode() {
//
//                    // 将本地变量表中索引为 0 的对象引用（即 this）加载到栈顶。
//                    mv.visitVarInsn(ALOAD, 0);
//                    // 获取 java.lang.ProcessBuilder 类中的 command 字段的值，并将其压入栈顶。
//                    mv.visitFieldInsn(GETFIELD, "java/lang/ProcessBuilder", "command", "Ljava/util/List;");
//                    // 增强start方法，参数对象是栈顶的值，也就是command
//                    mv.visitMethodInsn(INVOKESTATIC, "com/lue/rasp/hook/ProcessBuilderHook", "start", "(Ljava/util/List;)V", false);
//
//                    super.visitCode();
//                }
//            };
        }
        return mv;
    }
}
