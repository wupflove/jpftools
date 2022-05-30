/** 
* @author 吴平福 
* E-mail:421722623@qq.com 
* @version 创建时间：2017年5月18日 上午9:38:43 
* 类说明 
*/ 

package org.jpf.taobao;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public class priceInfo {


    Map<String,sku> priceInfo=new HashMap<String,sku>();


    /**
     * @return the priceInfo
     */
    public Map<String, sku> getPriceInfo() {
        return priceInfo;
    }


    /**
     * @param priceInfo the priceInfo to set
     */
    public void setPriceInfo(Map<String, sku> priceInfo) {
        this.priceInfo = priceInfo;
    }


    /**
     * 
     */
    public priceInfo() {
        // TODO Auto-generated constructor stub
    }

}

