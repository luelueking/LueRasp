package com.lue.rasp.visitor;

import com.lue.rasp.visitor.adapter.HttpVisitorAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


// TODO 目前仅支持tomcat，准备支持更多
public class HttpVisitor extends ClassVisitor implements Opcodes {
    public HttpVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

        if ("handleFirstRequest".equals(name) && "(Lio/undertow/server/HttpServerExchange;Lio/undertow/servlet/api/ServletRequestContext;)V".equals(desc)) {
            System.out.println(name + "方法的描述符是：" + desc);
            return new HttpVisitorAdapter(mv, access, name, desc);
        }
        return mv;
    }
}
