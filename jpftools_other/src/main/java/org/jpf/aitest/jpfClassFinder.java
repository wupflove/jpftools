/** 
* @author 吴平福 
* E-mail:421722623@qq.com 
* @version 创建时间：2016年6月21日 下午8:46:56 
* 类说明 
*/

package org.jpf.aitest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public class jpfClassFinder {
    private static final Logger logger = LogManager.getLogger();

    /**
     * 
     */
    public jpfClassFinder(final String strClassName) {
        // TODO Auto-generated constructor stub
        try
        {
            getClassImport();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    private void getClassImport()throws Exception
    {
        Class cls3=Class.forName("java.lang.String");
        logger.info(cls3.getName());
    }
    private void getClasses()
    {
        
    }
    private void getJars()
    {
        
    }
    /**
     * @category @author 吴平福
     * @param args
     *            update 2016年6月21日
     */

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        if (args.length == 1) {
            jpfClassFinder cJpfClassFinder = new jpfClassFinder(args[0]);
        } else {
            logger.warn("no imput classes");
        }

    }

}
