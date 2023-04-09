package com.lue.rasp.visitor;

import com.lue.rasp.transform.AgentTransform;
import com.lue.rasp.visitor.adapter.NativeProcessVisitorAdapter;
import com.lue.rasp.visitor.adapter.TomcatCleanHttpVisitorAdapter;
import com.lue.rasp.visitor.adapter.TomcatHttpVisitorAdapter;
import com.lue.rasp.weaver.RaspMethod;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.instrument.Instrumentation;

public class NativeProcessVisitor extends ClassVisitor implements Opcodes {

    private final Instrumentation inst;
    private final AgentTransform transformer;
    private RaspMethod method = null;

    public NativeProcessVisitor(ClassVisitor cv, Instrumentation inst, AgentTransform transformer) {
        super(Opcodes.ASM5,cv);
        this.inst = inst;
        this.transformer = transformer;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        // linux
        if ("forkAndExec".equals(name) && "(I[B[B[BI[BI[B[IZ)I".equals(desc)) {
            if (inst.isNativeMethodPrefixSupported()) {
                inst.setNativeMethodPrefix(transformer,"RASP");
            } else {
                throw new UnsupportedOperationException("Native RaspMethod Prefix Unsupported");
            }
            int newAccess = access & ~Opcodes.ACC_NATIVE;
            method = new RaspMethod(access,"RASP"+name,desc);
            MethodVisitor mv = super.visitMethod(newAccess, name, desc, signature, exceptions);
            System.out.println(name + "方法的描述符是：" + desc);
            return new NativeProcessVisitorAdapter(mv, newAccess, name, desc);
        }

        // TODO windows
        if ("create".equals(name) && "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[JZ)J".equals(desc)) {
            System.out.println(name + "方法的描述符是：" + desc);
        }
        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        if (method != null) {
            int newAccess = (Opcodes.ACC_PRIVATE | Opcodes.ACC_NATIVE | Opcodes.ACC_FINAL);
            MethodVisitor mv = cv.visitMethod(newAccess, method.getName(), method.getDescriptor(), null, null);
            mv.visitEnd();
        }
        super.visitEnd();
    }
}
