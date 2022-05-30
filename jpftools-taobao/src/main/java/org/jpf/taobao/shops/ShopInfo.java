/** 
* @author 吴平福 
* E-mail:421722623@qq.com 
* @version 创建时间：2017年9月24日 下午9:19:55 
* 类说明 
*/ 

package org.jpf.taobao.shops;

import java.util.Vector;

/**
 * 
 */
public class ShopInfo {

    /**
     * 
     */
    public ShopInfo() {
        // TODO Auto-generated constructor stub
    }

    String shopUrl;
    String shopName;
    Vector<GoodInfo> vGoodInfos=new Vector<>();
}
