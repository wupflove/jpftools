/**
 * @author 吴平福 E-mail:421722623@qq.com
 * @version 创建时间：2017年7月5日 上午10:10:18 类说明
 */

package org.jpf.taobao;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;

public class ImageUtils {

    private static final Logger logger = LogManager.getLogger();


    /**
     * 
     * @Title: 构造图片
     * @Description: 生成水印并返回java.awt.image.BufferedImage
     * @param file 源文件(图片)
     * @param waterFile 水印文件(图片)
     * @param x 距离右下角的X偏移量
     * @param y 距离右下角的Y偏移量
     * @param alpha 透明度, 选择值从0.0~1.0: 完全透明~完全不透明
     * @return BufferedImage 输出水印图片
     * @param buffImg 图像加水印之后的BufferedImage对象
     * @param savePath 图像加水印之后的保存路径
     */
    public  void generateWaterFile(String sourceFilePath, String waterFilePath) {

        OutputStream output=null;
        try {

            // 输出水印图片
            int x = 0;
            int y = 0;
            float alpha = 0.2f;
            File file = new File(sourceFilePath);

            File waterFile = new File(waterFilePath);
            // 获取底图
            BufferedImage buffImg = ImageIO.read(file);
            // 获取层图
            BufferedImage waterImg = ImageIO.read(waterFile);
            // 获取层图的宽度
            int waterImgWidth = waterImg.getWidth();
            // 获取层图的高度
            int waterImgHeight = waterImg.getHeight();

            if (buffImg.getWidth() > waterImgWidth) {
                x = (buffImg.getWidth() - waterImgWidth) / 2;
            }
            // 创建Graphics2D对象，用在底图对象上绘图
            Graphics2D g2d = buffImg.createGraphics();
            // 在图形和图像中实现混合和透明效果
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 绘制
            g2d.drawImage(waterImg, x, y, waterImgWidth, waterImgHeight, null);
            g2d.dispose();// 释放图形上下文使用的系统资源
             output = new java.io.FileOutputStream(file.getAbsolutePath());
            ImageIO.write(buffImg, "jpg", output);
            
            logger.debug("增加水印:" + sourceFilePath);
            // TextAreaLogAppender.log("增加水印:"+sourceFilePath);
        } catch (Exception ex) {
            // TODO: handle exception
            logger.error(sourceFilePath + " 增加水印读取文件错误");
            ex.printStackTrace();
        }finally {
            try {
                if (null!=output)
                {
                    output.close();
                }
            } catch (Exception ex2) {
                // TODO: handle exception
            }
            
        }

    }

    private  final String JPG_HEX = "ff";
    private  final String PNG_HEX = "89";
    private  final String JPG_EXT = "jpg";
    private  final String PNG_EXT = "png";

    public  String getFileExt(File file) {
        FileInputStream fis = null;
        String extension = "";
        try {
            fis = new FileInputStream(file);
            byte[] bs = new byte[1];
            fis.read(bs);
            String type = Integer.toHexString(bs[0] & 0xFF);
            if (JPG_HEX.equals(type))
                extension = JPG_EXT;
            if (PNG_HEX.equals(type))
                extension = PNG_EXT;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return extension;
    }

    /**
     * 
     * @category @author 吴平福
     * @param file
     * @return
     * @throws IOException update 2017年7月5日
     */
    public  java.awt.Rectangle getRectRandom(File file) throws IOException {
        try {

            int iRandom1 = RandomUtil.getRandomInt(RunParam.iRandomSize);
            int iRandom2 = RandomUtil.getRandomInt(RunParam.iRandomSize);
            /*
             * String strExtension=getFileExt(file); logger.info(strExtension);
             * 
             * Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(strExtension);
             * ImageReader reader = (ImageReader) readers.next(); ImageInputStream iis =
             * ImageIO.createImageInputStream(file); reader.setInput(iis, true);
             * 
             * 
             * int waterImgWidth=reader.getWidth(0); int waterImgHeight= reader.getHeight(0);
             */
            BufferedImage bi = ImageIO.read(file);
            int waterImgWidth = bi.getWidth();
            int waterImgHeight = bi.getHeight();

            int x = waterImgWidth * iRandom1 / 200;
            int y = waterImgHeight * iRandom2 / 200;
            int width = waterImgWidth * (100 - iRandom1) / 100;
            int height = waterImgHeight * (100 - iRandom2) / 100;
            return new java.awt.Rectangle(x, y, width, height);
        } catch (Exception ex) {
            // TODO: handle exception
            logger.error(file.getAbsolutePath());
            ex.printStackTrace();
            throw ex;
        }

    }

    /**
     * 
     * @category 根据原图与裁切size截取局部图片
     * @author 吴平福
     * @param srcImgFileName update 2017年7月7日
     */
    public  boolean cutImage(String srcImgFileName) {

        try {

            File file = new File(srcImgFileName);

            if (file.exists()) {
                java.awt.Rectangle rect = getRectRandom(file);

                ImageInputStream iis = ImageIO.createImageInputStream(file);

                Iterator<ImageReader> iterator = ImageIO.getImageReaders(iis);

                ImageReader reader = (ImageReader) iterator.next();

                reader.setInput(iis, true);
                ImageReadParam param = reader.getDefaultReadParam();
                param.setSourceRegion(rect);
                BufferedImage bi = reader.read(0, param);
                File file2 = new File(srcImgFileName);
                ImageIO.write(bi, reader.getFormatName(), file2);
            } else {
                logger.warn("the src image is not exist.");
            }

        } catch (Exception ex) {
            // TODO: handle exception
            // ex.printStackTrace();
            logger.error(ex.getMessage() + " " + srcImgFileName);
            return false;
        }
        return true;
    }

    /**
     * <p>
     * Title: thumbnailImage
     * </p>
     * <p>
     * Description: 根据图片路径生成缩略图
     * </p>
     * 
     * @param imagePath 原图片路径
     * @param w 缩略图宽
     * @param h 缩略图高
     * @param prevfix 生成缩略图的前缀
     * @param force 是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
     */
    public  void thumbnailImage(File srcImg, OutputStream output, int w, int h,
            boolean force) {
        if (srcImg.exists()) {
            try {
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF,
                // gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]", ",");
                String suffix = null;
                // 获取图片后缀
                if (srcImg.getName().indexOf(".") > -1) {
                    suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
                } // 类型和图片后缀全部小写，然后判断后缀是否合法
                if (suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase() + ",") < 0) {
                    logger.error(
                            "Sorry, the image suffix is illegal. the standard image suffix is {}."
                                    + types);
                    return;
                }
                logger.debug("target image's size, width:{}, height:{}.", w, h);
                Image img = ImageIO.read(srcImg);
                // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
                if (!force) {
                    int width = img.getWidth(null);
                    int height = img.getHeight(null);
                    if ((width * 1.0) / w < (height * 1.0) / h) {
                        if (width > w) {
                            h = Integer.parseInt(new java.text.DecimalFormat("0")
                                    .format(height * w / (width * 1.0)));
                            logger.debug("change image's height, width:{}, height:{}.", w, h);
                        }
                    } else {
                        if (height > h) {
                            w = Integer.parseInt(new java.text.DecimalFormat("0")
                                    .format(width * h / (height * 1.0)));
                            logger.debug("change image's width, width:{}, height:{}.", w, h);
                        }
                    }
                }
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
                // 将图片保存在原目录并加上前缀
                ImageIO.write(bi, suffix, output);
                output.close();
            } catch (IOException e) {
                logger.error("generate thumbnail image failed.", e);
            }
        } else {
            logger.warn("the src image is not exist.");
        }
    }

    /**
     * 
     * @category 选取白底图片
     * @author 吴平福
     * @param strFilePath
     * @param vector
     * @throws IOException update 2017年7月24日
     */
    public void getWhiteGroundPic(String strFilePath, Vector<String> vector)
            throws IOException {
        /** * 要处理的图片目录 */
        File dir = new File(strFilePath);
        /** * 列出目录中的图片，得到数组 */
        // File[] files = dir.listFiles();

        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (!pathname.getAbsolutePath().endsWith(".tbi")) {
                    return false;
                }
                if (pathname.length() <= 0) {
                    return false;

                }
                return true;
            }
        });
        if (null==files)
        {
            return;
        }
        String strFileName = "";
        /** * 遍历数组 */
        for (int x = 0; x < files.length; x++) {
            /** * 定义一个RGB的数组，因为图片的RGB模式是由三个 0-255来表示的 比如白色就是(255,255,255) */

            int[] rgb = new int[3];
            /** * 用来处理图片的缓冲流 */
            BufferedImage bi = null;
            try {
                /** * 用ImageIO将图片读入到缓冲中 */
                strFileName = files[x].getAbsolutePath();
                bi = ImageIO.read(files[x]);

                /** * 得到图片的长宽 */
                int width = bi.getWidth();
                int height = bi.getHeight();

                int minx = bi.getMinX();
                int miny = bi.getMinY();
                int iPercent = 0;
                logger.debug("正在处理：" + files[x].getName());
                /** * 这里是遍历图片的像素，因为要处理图片的背色，所以要把指定像素上的颜色换成目标颜色 * 这里 是一个二层循环，遍历长和宽上的每个像素 */
                for (int i = minx; i < width; i++) {
                    for (int j = miny; j < height; j++) {
                        // System.out.print(bi.getRGB(jw, ih)); /** * 得到指定像素（i,j)上的RGB值， */
                        int pixel = bi.getRGB(i, j);
                        /** * 分别进行位操作得到 r g b上的值 */
                        rgb[0] = (pixel & 0xff0000) >> 16;
                        rgb[1] = (pixel & 0xff00) >> 8;
                        rgb[2] = (pixel & 0xff);
                        /** * 进行换色操作，我这里是要把蓝底换成白底，那么就判断图片中rgb值是否在蓝色范围的像素 */
                        /*
                         * if (rgb[0] < 155 && rgb[0] > 0 && rgb[1] < 256 && rgb[1] > 105 && rgb[2]
                         * < 256 && rgb[2] > 105) { // 这里是判断通过，则把该像素换成白色 bi.setRGB(i, j, 0xffffff);
                         * }
                         */
                        if (rgb[0] > 245 && rgb[1] > 245 && rgb[2] > 245) {
                            iPercent++;
                        }
                    }
                }
                // Double double1=(double)iPercent/(width*height);
                // DecimalFormat df2 = new DecimalFormat("###.00%");
                if (100 * iPercent / (width * height) > 60) {
                    logger.trace(iPercent + "/" + width * height + "="
                            + 100 * iPercent / (width * height) + "%");
                    vector.add(files[x].getAbsolutePath());
                }
                logger.debug("处理完毕：" + files[x].getName());

            } catch (Exception e) {
                logger.error(strFileName);
                e.printStackTrace();

            }
        }

        for (int i = 0; i < vector.size(); i++) {
            logger.debug(vector.get(i));
        }
    }

    /**
     * 
     * @category 判断图片大小
     * @author 吴平福
     * @param strPicPathName
     * @return update 2017年7月24日
     */
    public boolean justPic(String strPicPathName) {
        try {
            // logger.debug(strPicPathName);
            File picture = new File(strPicPathName);
            BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
            // System.out.println(String.format("%.1f",picture.length()/1024.0));
            // System.out.println(sourceImg.getWidth());
            // System.out.println(sourceImg.getHeight());
            if (500 <= sourceImg.getWidth() && 1000 > sourceImg.getWidth()
                    && 500 <= sourceImg.getHeight() && 1000 > sourceImg.getHeight()) {

                return true;
            }
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
        return false;
    }


    /**
     * 把图片印刷到图片上
     * 
     * @param pressImg -- 水印文件
     * @param targetImg -- 目标文件
     * @param x --x坐标
     * @param y --y坐标
     */
    public final void pressImage(String pressImg, String targetImg, int x, int y) {
        try {
            // 目标文件
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);

            // 水印文件
            File _filebiao = new File(pressImg);
            Image src_biao = ImageIO.read(_filebiao);
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2,
                    wideth_biao, height_biao, null);
            // 水印文件结束
            g.dispose();
            
            FileOutputStream out=null;
            try {
                out = new FileOutputStream(targetImg);
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                encoder.encode(image);
            } finally {
                // TODO: handle finally clause
                out.close();
            }

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
    /**
     * 
     * @category 缩放图片 
     * @author 吴平福 
     * @param originalFile
     * @param resizedFile
     * @param newWidth
     * @param quality
     * @throws IOException
     * update 2017年9月25日
     */
    public  void resize(String originalFileName,  
            int newWidth, float quality) throws IOException {  
  
        NarrowImage narrowImage = new NarrowImage();  
        narrowImage.writeHighQuality(narrowImage.zoomImage(originalFileName), originalFileName);
    }
}
