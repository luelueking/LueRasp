package com.lue.rasp.visitor;

import com.lue.rasp.visitor.adapter.ProcessBuilderVisitorAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

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
        }
        return mv;
    }
}
