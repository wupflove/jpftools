/**
 * @author 吴平福 E-mail:wupf@asiainfo.com
 * @version 创建时间：2017年6月17日 上午10:13:31 类说明
 */

package org.jpf.taobao;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 
 */
public class RandomUtil {
    private static final Logger logger = LogManager.getLogger();

    /**
     * 
     */
    public RandomUtil() {
        // TODO Auto-generated constructor stub
    }

    /**
     * DecimalFormat转换最简便
     */
    public static String m2(double d) {
        DecimalFormat df = new DecimalFormat("#.0");
        return df.format(d);

    }

    /**
     * 
     * @category 随机价格
     * @author 吴平福
     * @param inPrice
     * @return update 2017年6月17日
     */
    public static String getRandomPrice(final String inPrice) {
        double x = Double.parseDouble(inPrice)*RunParam.zhekou;
        double y = x *RunParam.iChangeLevel/100;
        double d = x + Math.random() * y % (y - x + 1);
        // logger.debug(m2(d));
        return m2(d);
    }

    /**
     * 
     * @category 调整描述图顺序
     * @author 吴平福
     * @param iMainPicCount
     * @return update 2017年6月17日
     */
    public static String getRandomDescPicOrder(final String strDescPic) throws Exception {
        logger.debug("strDescPic "+strDescPic);
        String[] imgs=strDescPic.split("<img");
        logger.debug("imgs.length "+imgs.length);
        if(imgs.length<2)
        {
            return strDescPic;
        }
        
        for(int i=1;i<imgs.length-1;i++)
        {
            int iImgPos=imgs[i].indexOf("<img");
            int iPos=-1;
            if (iImgPos>0)
            {
                iPos=imgs[i].indexOf(">", iImgPos);
            }else
            {
                iPos=imgs[i].indexOf(">");
            }
            
            if (iPos>0)
            {
                imgs[i+1]=imgs[i].substring(iPos+1,imgs[i].length())+"<img "+ imgs[i+1];
                imgs[i]=imgs[i].substring(0,iPos+1);
                //logger.debug(i+":"+imgs[i]);
                //logger.debug((i+1)+":"+imgs[i+1]);
            }
        }
        imgs[1]="<img "+ imgs[1];
        int iPos2=imgs[imgs.length-1].indexOf(">");
        final String strEnd=imgs[imgs.length-1].substring(iPos2+1, imgs[imgs.length-1].length());
        //logger.debug("strEnd:"+strEnd);
        //logger.debug("imgs[imgs.length-1]:"+imgs[imgs.length-1]);
        imgs[imgs.length-1]=imgs[imgs.length-1].substring(0, iPos2+1);
        //logger.debug("imgs[imgs.length-1]:"+imgs[imgs.length-1]);
        
        StringBuilder sb=new StringBuilder();
        sb.append(imgs[0]);
        String[] strOrders=getRandomIntOrder(imgs.length).split(",");
        for(int i=0;i<strOrders.length;i++)
        {
            int iPos=Integer.parseInt(strOrders[i]);
            if (iPos>0)
            {
                sb.append(imgs[iPos]);
            }
        }
        sb.append(strEnd);
        logger.debug(sb.toString());
        return sb.toString();
    }

    public static void main(String[] args)
    {
        String strPicOrder="0092f1dac7tb26rya___2403502057:1:0:|;7qz0doyn3u1esrvobswe8jnk1fv5yd:1:1:|;0093997a14tb2fooa___2403502057:1:2:|;wb9o2oi1yb198sd7ooe0rwoga7gv79:1:3:|;0094da2163tb2soxa___2403502057:1:4:|;0096e1ce12tb2txha___2403502057:2:0:1627207:-1001|;0097bc949ctb2_lla___2403502057:2:0:1627207:-1002|;0098f666cetb2arfa___2403502057:2:0:1627207:-1003|;00998245b4tb2g45a___2403502057:2:0:1627207:-1004|;01002216d8tb2okpa___2403502057:2:0:1627207:-1005|;0101056f32tb2iyda___2403502057:2:0:1627207:-1006|;010259a324tb2mvna___2403502057:2:0:1627207:-1007|;0103df654ctb2x38a___2403502057:2:0:1627207:-1008|;01046d8603tb2xuna___2403502057:2:0:1627207:-1009|;010568c8b5tb23n4a___2403502057:2:0:1627207:-1010|;0106447b46tb2kjxa___2403502057:2:0:1627207:-1011|;01070ebb31tb2caxa___2403502057:2:0:1627207:-1012|;";
        int iPos=strPicOrder.substring(0,strPicOrder.lastIndexOf(":|")).lastIndexOf(":|");
        System.out.println(strPicOrder.substring(iPos + 3, strPicOrder.length()));
        
        for(int i=0;i<20;i++)
        {
        System.out.println(getRandomPrice("100.00"));
        }
    }



    /**
     * 
     * @category 商家编码模糊
     * @author 吴平福
     * @param strShopCode
     * @return update 2017年6月17日
     */
    public static String getShopCode(String strShopCode) {
        int i = strShopCode.length() / 2;
        return strShopCode.substring(0, i) + getRandomString(2)
                + strShopCode.substring(i, strShopCode.length());
    }

    /**
     * 
     * @category 产生随机编码
     * @author 吴平福
     * @param length
     * @return update 2017年6月17日
     */
    public static String getRandomString(int length) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; i++) {

            int number = random.nextInt(26);

            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 
     * @category 产生随机编码
     * @author 吴平福
     * @param length
     * @return update 2017年6月17日
     */
    public static String getRandomStringInt(int length) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; i++) {

            int number = random.nextInt(36);

            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 
     * @category @author 吴平福
     * @param length
     * @return update 2017年6月30日
     */
    public static String getRandomLowerStringInt(int length) {
        String str = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; i++) {

            int number = random.nextInt(36);

            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandomInt(final int iMax, final int iCount) {
        try {
            Vector<String> vector = new Vector<String>();
            logger.debug("iMax="+iMax);
            logger.debug("iCount="+iCount);
            for (int i = 0; i < iMax; i++) {
                vector.add(String.valueOf(i));
            }
            StringBuilder sb = new StringBuilder();
            Random random = new Random();

            for (int i = 0; i < iCount; i++) {
                int number = random.nextInt(vector.size());
                sb.append(vector.get(number) + ",");
                vector.remove(number);
            }
            // logger.debug(sb.toString());
            return sb.toString();
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
            logger.error("iMax="+iMax);
            logger.error("iCount="+iCount);
        }
        return "";
    }

    /**
     * 
     * @category 产生随机数队列 
     * @author 吴平福 
     * @param iMax
     * @return
     * update 2017年7月5日
     */
    public static String getRandomIntOrder(final int iMax) {
        Vector<String> vector = new Vector<String>();
        for (int i = 0; i < iMax; i++) {
            vector.add(String.valueOf(i));
        }
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < iMax; i++) {

            int number = random.nextInt(vector.size());

            sb.append(vector.get(number) + ",");
            vector.remove(number);
        }
        return sb.toString();
    }

    /**
     * 
     * @category 产生随机数 
     * @author 吴平福 
     * @param iMax
     * @return
     * update 2017年7月5日
     */
    public static int getRandomInt(final int iMax) {
        Vector<String> vector = new Vector<String>();
        for (int i =1 ; i <= iMax; i++) {
            vector.add(String.valueOf(i));
        }

        Random random = new Random();

         return random.nextInt(vector.size());

    }





}
