package com.lue.rasp.transform;

import com.lue.rasp.visitor.HttpVisitor;
import com.lue.rasp.visitor.MySqlVisitor;
import com.lue.rasp.visitor.ProcessBuilderVisitor;
import com.lue.rasp.visitor.TomcatHttpVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.logging.Logger;

public class AgentTransform implements ClassFileTransformer {

    private static final Logger logger = Logger.getLogger(AgentTransform.class.getName());

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
            if (className.contains("StandardWrapperValve")) { // tomcat http hook
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

            if (className.contains("ProcessBuilder")) { // 判断类名是否包含ProcessBuilder

                logger.warning("Attention Load Class: " + className);

                ClassReader classReader  = new ClassReader(classfileBuffer);
                ClassWriter classWriter  = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
                ClassVisitor classVisitor = new ProcessBuilderVisitor(classWriter);

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classfileBuffer;
    }

}