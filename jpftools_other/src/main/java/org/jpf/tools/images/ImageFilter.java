/** 
* @author 吴平福 
* E-mail:421722623@qq.com 
* @version 创建时间：2011-5-2 下午12:07:23 
* 类说明 
*/ 

package org.jpf.tools.images;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.*;

public class ImageFilter {
    /** Automatically generated javadoc for: INT_100 */
    private static final int INT_10 = 10;
    /** Automatically generated javadoc for: INT_600 */
    private static final int INT_600 = 480;
    /** Automatically generated javadoc for: INT_800 */
    private static final int INT_800 = 600;
    private static void error () {
        System.out.println("Too Few Argument.\njava ImgFileter c:/images 800(width) 600(height)");
    }
    public static void main (String[] args) {
        if (args.length < 3) {
            error();
            return;
        }
        ImageFilter bot = new ImageFilter();
        bot.execute(args);
        System.out.println("--------\n");
    }
    /**
     * Check the input image size pixel(s)
     *
     * @param fp
     * @param minWidth
     * @param minHeight
     * @return
     */
    private int checkSize (String fp, int minWidth, int minHeight) {
        InputStream in = null;
        try {
            BufferedImage mImage;
            in = new FileInputStream(fp);
            mImage = ImageIO.read(in);
            int height = mImage.getHeight();
            int width = mImage.getWidth();
            long minSqure = minWidth * minHeight;
            long squre = width * height;
            if (width < minWidth && height < minHeight) {
               if(width<minHeight && height<minWidth)
               {
                 return 0;
               }
            }
            if (squre < minSqure) {
                return 0;
            }
            return 1;
        } catch (Exception ex) {
            System.err.println(fp + ": " + ex.toString());
            return -1;
        } finally {
            try {
                in.close();
            } catch (IOException ignored) {
            }
        }
    }
    /**
     * Main entrance of the ImgFilter Class
     *
     * @param args
     */
    private void execute (String[] args) {
        String fd = args[0];
        System.out.println(args[0]);
        String extend = "jpg";
        int width = INT_800, height = INT_600;
        try {
            width = Integer.parseInt(args[1]);
            height = Integer.parseInt(args[2]);
        } catch (Exception ignored) {
        }
        String[] lstFiles = retrieveDirList(fd, extend);
        if (lstFiles == null) {
            return;
        }
        int max = lstFiles.length;
        List lstOk = new ArrayList();
        List lstBad = new ArrayList();
        List lstUn = new ArrayList();
        for (int i = 0; i < lstFiles.length; i++) {
            String fn = lstFiles[i];
            String fp = fd + "/" + fn;
            int rs = checkSize(fp, width, height);
            if (rs == 1) {
                //lstOk.add(fn);
            } else if (rs == 0) {
                //lstBad.add(fn);
                File f=new File(fp);
                f.delete();
              System.out.println("delete file "+fp);
            } else {
              File f=new File(fp);
              f.delete();
              System.out.println("delete file "+fp);
              //lstUn.add(fn);
            }
            if (i % INT_10 == 0) {
                System.out.println(i + "/" + max);
            }
        } // end for
        System.out.println(max + "/" + max);
        writeFile(fd + "/ok.lst", lstOk);
        writeFile(fd + "/fail.lst", lstBad);
        moveLstFile(fd + "/fail.lst", "small");
        writeFile(fd + "/bad.lst", lstUn);
        moveLstFile(fd + "/bad.lst", "bad");
    }
    /**
     * generate move file list
     *
     * @param fp
     * @param dirname
     */
    private void moveLstFile (String fp, String dirname) {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        try {
            br = new BufferedReader(new FileReader(fp));
            String line = br.readLine();
            while (line != null) {
                sb.append("move ").append(line).append(" ").append(dirname)
                        .append("\\ \r\n");
                line = br.readLine();
            }
            this.writeFile(fp + ".cmd", sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                // ignored
            }
        }
    }
    /**
     * Retrieve the spec extend name file list
     *
     * @return
     */
    private String[] retrieveDirList (String fd, final String ext) {
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept (File dir, String name) {
                return name.endsWith(ext);
            }
        };
        File dir = new File(fd);
        String[] list = dir.list(filter);
        return list;
    }
    /**
     * write input list to file
     *
     * @param string
     * @param lstUn
     */
    private void writeFile (String string, List lst) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < lst.size(); i++) {
            sb.append(lst.get(i)).append("\r\n");
        }
        writeFile(string, sb.toString());
    }
    /**
     * Write input content to input fp
     *
     * @param fp
     * @param content
     */
    private void writeFile (String fp, String content) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(fp);
            fw.write(content);
        } catch (IOException e) {
            System.err.println("Write:" + fp + "failed:" + e.toString());
        } finally {
            try {
                fw.close();
            } catch (IOException e1) {
            }
        }
    }
} // eof
