/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年4月24日 下午2:30:37 
* 类说明 
*/ 

package org.jpf.taobao.zhanguibao;

import java.util.List;

/**
 * 
 */
public interface  ProductList {


    /**
     * 爬取商品列表
     * @return
     */
    public List<ProductInfo> getProductList();
}
