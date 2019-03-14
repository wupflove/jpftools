/**
 * copyrigth by wupf@ 2019年2月22日
 */
package org.jpf.fuzz;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author wupf@asiainfo.com
 *
 */
public class Fuzzer {
  private static final Logger logger = LogManager.getLogger();

  /**
   * 
   */
  public Fuzzer() {
    // TODO Auto-generated constructor stub

    try {
      File f = new File("d:\\sh.properties");
      fuzz(f, 1, 200);
      logger.info("game over");
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }

  /**
   * @category:
   * @Title: main
   * @author:wupf@asiainfo.com
   * @date:2019年2月22日
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    Fuzzer cFuzzer = new Fuzzer();
  }

  private Random random = new SecureRandom();
  private int count = 1;

  public File fuzz(File in, int start, int length) throws IOException {

    byte[] data = new byte[(int) in.length()];
    DataInputStream din = new DataInputStream(new FileInputStream(in));
    din.readFully(data);
    fuzz(data, start, length);
    String name = "fuzz_" + count + "_" + in.getName();
    File fout = new File(name);
    FileOutputStream out = new FileOutputStream(fout);
    out.write(data);
    out.close();
    din.close();
    count++;
    return fout;
  }


  // Modifies byte array in place
  public void fuzz(byte[] in, int start, int length) {

    byte[] fuzz = new byte[length];
    random.nextBytes(fuzz);
    System.arraycopy(fuzz, 0, in, start, fuzz.length);

  }
}
