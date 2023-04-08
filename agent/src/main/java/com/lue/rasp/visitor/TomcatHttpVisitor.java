package com.lue.rasp.visitor;

import com.lue.rasp.visitor.adapter.TomcatHttpVisitorAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class TomcatHttpVisitor extends ClassVisitor implements Opcodes{
    public TomcatHttpVisitor(ClassVisitor cv) {super(Opcodes.ASM5,cv);}


    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

        if ("invoke".equals(name) && "(Lorg/apache/catalina/connector/Request;Lorg/apache/catalina/connector/Response;)V".equals(desc)) {
            System.out.println(name + "方法的描述符是：" + desc);
            return new TomcatHttpVisitorAdapter(mv, access, name, desc);
        }
        return mv;
    }
}
