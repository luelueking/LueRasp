package com.lue.rasp.visitor;

import com.lue.rasp.visitor.adapter.TomcatCleanHttpVisitorAdapter;
import com.lue.rasp.visitor.adapter.TomcatHttpVisitorAdapter;
import org.apache.catalina.connector.CoyoteAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class TomcatHttpVisitor extends ClassVisitor implements Opcodes{
    public TomcatHttpVisitor(ClassVisitor cv) {super(Opcodes.ASM5,cv);}


    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

        // 添加请求上下文
        if ("invoke".equals(name) && "(Lorg/apache/catalina/connector/Request;Lorg/apache/catalina/connector/Response;)V".equals(desc)) {
            System.out.println(name + "方法的描述符是：" + desc);
            return new TomcatHttpVisitorAdapter(mv, access, name, desc);
        }
        // 移除请求上下文
        if ("service".equals(name) && "(Lorg/apache/coyote/Request;Lorg/apache/coyote/Response;)V".equals(desc)) {
            System.out.println(name + "方法的描述符是：" + desc);
            return new TomcatCleanHttpVisitorAdapter(mv, access, name, desc);
        }
        return mv;
    }
}
