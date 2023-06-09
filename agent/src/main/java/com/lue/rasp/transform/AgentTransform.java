package com.lue.rasp.transform;

import com.lue.rasp.visitor.*;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.logging.Logger;

public class AgentTransform implements ClassFileTransformer {

    private final Instrumentation inst;
    private static final Logger logger = Logger.getLogger(AgentTransform.class.getName());

    public AgentTransform(Instrumentation inst) {
        this.inst = inst;
    }
    /**
     * @param loader
     * @param className
     * @param classBeingRedefined
     * @param protectionDomain
     * @param classfileBuffer
     * @return
     * @throws IllegalClassFormatException
     */
    // TODO 优化transform逻辑
    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        className = className.replace("/", ".");
        try {
            if (className.contains("StandardWrapperValve") || className.contains("CoyoteAdapter")) { // tomcat http hook
                logger.warning("Attention Load Class: " + className);
                ClassReader classReader = new ClassReader(classfileBuffer);
                ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                ClassVisitor classVisitor  = new TomcatHttpVisitor(classWriter);

                classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

                classfileBuffer = classWriter.toByteArray();
            }
            if (className.contains("ServletInitialHandler")) { // http hook
                logger.warning("Attention Load Class: " + className);

                ClassReader classReader = new ClassReader(classfileBuffer);
                ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                ClassVisitor classVisitor  = new HttpVisitor(classWriter);

                classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

                classfileBuffer = classWriter.toByteArray();
            }

            if (className.contains("StatementImpl")) {
                logger.warning("Attention Load Class: " + className);

                ClassReader classReader  = new ClassReader(classfileBuffer);
                ClassWriter classWriter  = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                ClassVisitor classVisitor = new MySqlVisitor(classWriter);

                classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

                classfileBuffer = classWriter.toByteArray();
            }

            // rce
//            if (className.contains("ProcessBuilder")) { // 判断类名是否包含ProcessBuilder
//
//                logger.warning("Attention Load Class: " + className);
//
//                ClassReader classReader  = new ClassReader(classfileBuffer);
//                ClassWriter classWriter  = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
//                ClassVisitor classVisitor = new ProcessBuilderVisitor(classWriter);
//
//                classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
//
//                classfileBuffer = classWriter.toByteArray();
//            }

            // native process rce
            if (className.contains("UNIXProcess") || className.contains("ProcessImpl")) {
                logger.warning("Attention Load Class: " + className);

                ClassReader classReader  = new ClassReader(classfileBuffer);
                ClassWriter classWriter  = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                ClassVisitor classVisitor = new NativeProcessVisitor(classWriter,inst,this);

                classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

                classfileBuffer = classWriter.toByteArray();
            }

            // deserialization
            if (className.contains("ObjectInputStream")) {
                logger.warning("Attention Load Class: " + className);

                ClassReader classReader  = new ClassReader(classfileBuffer);
                ClassWriter classWriter  = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                ClassVisitor classVisitor = new DeserializationVisitor(classWriter);

                classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

                classfileBuffer = classWriter.toByteArray();
            }

            // DocumentBuilderFactory xxe
            if (className.contains("SAXReader")) {
                logger.warning("Attention Load Class: " + className);

                ClassReader classReader  = new ClassReader(classfileBuffer);
                ClassWriter classWriter  = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                ClassVisitor classVisitor = new XXEVisitor(classWriter);

                classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

                classfileBuffer = classWriter.toByteArray();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return classfileBuffer;
    }

}