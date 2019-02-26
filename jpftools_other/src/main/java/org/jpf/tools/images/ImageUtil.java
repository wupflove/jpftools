package org.jpf.tools.images;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.*;

public class ImageUtil
{
  public ImageUtil()
  {
    checkSize("E:\\private\\cd\\cd2\\1.jpg");
  }
  public static void main(String[] args)
  {
    ImageUtil iu=new ImageUtil();
  }
  /**
   * Check the input image size pixel(s)
   *
   * @param fp
   * @param minWidth
   * @param minHeight
   * @return
   */
  private void checkSize (String fp) {
      InputStream in = null;
      try {
          BufferedImage mImage;
          in = new FileInputStream(fp);
          mImage = ImageIO.read(in);
          System.out.println("Height="+mImage.getHeight());
          System.out.println("Width="+mImage.getWidth());
      } catch (Exception ex) {
          System.err.println(fp + ": " + ex.toString());
      } finally {
          try {
              in.close();
          } catch (IOException ignored) {
          }
      }
  }

}
