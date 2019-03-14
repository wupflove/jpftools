package org.jpf.apptool.filetool;

import org.junit.*;
import static org.junit.Assert.*;

import org.jpf.jpftool.filetool.UtfJavaFile;

/**
 * The class <code>UtfJavaFile_GTest</code> contains tests for the class <code>{@link UtfJavaFile}</code>.
 *
 * @generatedBy CodePro at 19-2-10 下午12:06
 * @author wupf
 * @version $Revision: 1.0 $
 */
public class UtfJavaFile_GTest {
  /**
   * Run the UtfJavaFile() constructor test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 19-2-10 下午12:06
   */
  @Test
  public void testUtfJavaFile_1()
    throws Exception {

    UtfJavaFile result = new UtfJavaFile();

    assertNotNull(result);
  }

  /**
   * Run the UtfJavaFile() constructor test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 19-2-10 下午12:06
   */
  @Test
  public void testUtfJavaFile_2()
    throws Exception {

    UtfJavaFile result = new UtfJavaFile();

    assertNotNull(result);
  }

  /**
   * Run the void main(String[]) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 19-2-10 下午12:06
   */
  @Test
  public void testMain_1()
    throws Exception {
    String[] args = new String[] {};

    UtfJavaFile.main(args);

  }

  /**
   * Run the String toUtf8String(String) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 19-2-10 下午12:06
   */
  @Test
  public void testToUtf8String_1()
    throws Exception {
    String s = "aa";

    String result = UtfJavaFile.toUtf8String(s);

    assertEquals("\\u61\\u61", result);
  }

  /**
   * Run the String toUtf8String(String) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 19-2-10 下午12:06
   */
  @Test
  public void testToUtf8String_2()
    throws Exception {
    String s = "";

    String result = UtfJavaFile.toUtf8String(s);

    assertEquals("", result);
  }

  /**
   * Perform pre-test initialization.
   *
   * @throws Exception
   *         if the initialization fails for some reason
   *
   * @generatedBy CodePro at 19-2-10 下午12:06
   */
  @Before
  public void setUp()
    throws Exception {
  }

  /**
   * Perform post-test clean-up.
   *
   * @throws Exception
   *         if the clean-up fails for some reason
   *
   * @generatedBy CodePro at 19-2-10 下午12:06
   */
  @After
  public void tearDown()
    throws Exception {
  }

  /**
   * Launch the test.
   *
   * @param args the command line arguments
   *
   * @generatedBy CodePro at 19-2-10 下午12:06
   */
  public static void main(String[] args) {
    new org.junit.runner.JUnitCore().run(UtfJavaFile_GTest.class);
  }
}