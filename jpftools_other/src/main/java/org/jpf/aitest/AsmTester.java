/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2016年6月21日 下午9:19:26 
* 类说明 
*/

package org.jpf.aitest;

import java.io.IOException;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * ASM测试
 * 
 * @author 吴平福
 */
public class AsmTester {

    // public final static float VERSION = 1.6f;

    public static void main(String[] args) throws IOException {
        testRead();
    }

    /**
     * 
     * @category @author 吴平福 update 2016年6月21日
     */
    public void aaa() {

    }

    private static void testRead() throws IOException {
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM5) {
            @Override
            public void visit(int version, int access, String name,
                    String signature, String superName, String[] interfaces) {
                System.out.println("class name:" + name);
                System.out.println("super class name:" + superName);
                System.out.println("class version:" + version);
                System.out.println("class access:" + access);
                System.out.println("class signature:" + signature);
                if (interfaces != null && interfaces.length > 0) {
                    for (String str : interfaces) {
                        System.out.println("implemented interface name:" + str);
                    }
                }
                System.out.println(name + " extends " + superName + " {");
            }

            @Override
            public void visitSource(String source, String debug) {
                System.out.println(" " + source + " " + debug);
            }

            @Override
            public void visitOuterClass(String owner, String name, String desc) {
                System.out.println(" " + owner + " " + name);
            }

            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visiable) {
                System.out.println(" " + desc + " ");
                return null;
            }

            @Override
            public void visitAttribute(Attribute attr) {
            }

            @Override
            public void visitInnerClass(String name, String outerName,
                    String innerName, int access) {
                System.out.println(" " + innerName + " ");
            }

            @Override
            public FieldVisitor visitField(int access, String name,
                    String desc, String signature, Object value) {
                System.out.println(" " + desc + " " + name + "(" + value + ")");
                return null;
            }

            @Override
            public MethodVisitor visitMethod(int access, String name,
                    String desc, String signature, String[] exceptions) {
                System.out.println(" " + name + desc);
                return null;
            }

            @Override
            public void visitEnd() {
                System.out.println("}");
            }

        };
        ClassReader cr = new ClassReader("org.jpf.aitest.AsmTester");
        cr.accept(visitor, 0);
    }

}
