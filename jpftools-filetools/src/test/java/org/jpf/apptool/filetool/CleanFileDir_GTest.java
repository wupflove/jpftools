package org.jpf.apptool.filetool;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>CleanFileDir_GTest</code> contains tests for the class <code>{@link CleanFileDir}</code>.
 *
 * @generatedBy CodePro at 19-2-10 下午12:06
 * @author wupf
 * @version $Revision: 1.0 $
 */
public class CleanFileDir_GTest {
  /**
   * Run the CleanFileDir() constructor test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 19-2-10 下午12:06
   */
  @Test
  public void testCleanFileDir_1()
    throws Exception {

    CleanFileDir result = new CleanFileDir();

    assertNotNull(result);
  }

  /**
   * Run the CleanFileDir() constructor test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 19-2-10 下午12:06
   */
  @Test
  public void testCleanFileDir_2()
    throws Exception {

    CleanFileDir result = new CleanFileDir();

    assertNotNull(result);
  }

  /**
   * Run the void DelDirWithFiles(String) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 19-2-10 下午12:06
   */
  @Test
  public void testDelDirWithFiles_1()
    throws Exception {
    CleanFileDir fixture = new CleanFileDir();
    String path = "";

    fixture.DelDirWithFiles(path);

  }

  /**
   * Run the void DelDirWithFiles(String) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 19-2-10 下午12:06
   */
  @Test
  public void testDelDirWithFiles_2()
    throws Exception {
    CleanFileDir fixture = new CleanFileDir();
    String path = "";

    fixture.DelDirWithFiles(path);

  }

  /**
   * Run the void DelDirWithFiles(String) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 19-2-10 下午12:06
   */
  @Test
  public void testDelDirWithFiles_3()
    throws Exception {
    CleanFileDir fixture = new CleanFileDir();
    String path = "";

    fixture.DelDirWithFiles(path);

  }

  /**
   * Run the void DelDirWithFiles(String) method test.
   *
   * @throws Exception
   *
   * @generatedBy CodePro at 19-2-10 下午12:06
   */
  @Test
  public void testDelDirWithFiles_4()
    throws Exception {
    CleanFileDir fixture = new CleanFileDir();
    String path = "";

    fixture.DelDirWithFiles(path);

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

    CleanFileDir.main(args);

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
    new org.junit.runner.JUnitCore().run(CleanFileDir_GTest.class);
  }
}