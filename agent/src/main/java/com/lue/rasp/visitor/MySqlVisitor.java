package com.lue.rasp.visitor;

import com.lue.rasp.visitor.adapter.MySqlVisitorAdapter;
import com.lue.rasp.visitor.adapter.ProcessBuilderVisitorAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashSet;
import java.util.Set;

public class MySqlVisitor extends ClassVisitor implements Opcodes {

    public MySqlVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    private static final Set<String> methodName = new HashSet<String>() {{
        add("executeQuery");
        add("execute");
        add("executeUpdate");
        add("addBatch");
    }};

    private static final Set<String> descName = new HashSet<String>() {{
        add("(Ljava/lang/String;I)I");
        add("(Ljava/lang/String;[Ljava/lang/String;)I");
        add("(Ljava/lang/String;[I)I");
        add("(Ljava/lang/String;)Ljava/sql/ResultSet;");
        add("(Ljava/lang/String;)Z");
        add("(Ljava/lang/String;I)Z");
        add("(Ljava/lang/String;[Ljava/lang/String;)Z");
        add("(Ljava/lang/String;[I)Z");
        add("(Ljava/lang/String;[I)J");
        add("(Ljava/lang/String;[Ljava/lang/String;)J");
        add("(Ljava/lang/String;I)J");
        add("(Ljava/lang/String;)J");
        add("(Ljava/lang/String;)I");
    }};


    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

        if (methodName.contains(name) && descName.contains(desc)) {
            return new MySqlVisitorAdapter(mv,access,name,desc);
        }
        return mv;
    }
}
