package com.lue.rasp.visitor;

import com.lue.rasp.visitor.adapter.TomcatCleanHttpVisitorAdapter;
import com.lue.rasp.visitor.adapter.TomcatHttpVisitorAdapter;
import com.lue.rasp.visitor.adapter.XXEVisitorAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class XXEVisitor extends ClassVisitor implements Opcodes {
    public XXEVisitor(ClassVisitor cv) {super(Opcodes.ASM5,cv);}

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

        //
        if ("read".equals(name) && "(Lorg/xml/sax/InputSource;)Lorg/dom4j/Document;".equals(desc)) {
            System.out.println(name + "方法的描述符是：" + desc);
            return new XXEVisitorAdapter(mv, access, name, desc);
        }
        return mv;
    }
}
