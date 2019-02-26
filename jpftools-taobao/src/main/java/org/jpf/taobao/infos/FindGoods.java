/**
 * 
 */

package org.jpf.taobao.infos;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpf.taobao.httputils.TaobaoHttpSearch;



/**
 * 
 */
public class FindGoods {
    private static final Logger logger = LogManager.getLogger();

    /**
     * 
     */
    public FindGoods() {
        // TODO Auto-generated constructor stub
        try {
            String strUrl="https://s.taobao.com/search?q=���ɺ�+����&js=1&stats_click=search_radio_all%3A1&initiative_id=staobaoz_20170329&ie=utf8&filter=reserve_price%5B0%2C0%5D&bcoffset=0&ntoffset=0&p4ppushleft=%2C44&sort=sale-desc&s=44";
            String strMsg=
            TaobaoHttpSearch.getWebSourceCode(strUrl);
            ShopDetail cShopDetail=new ShopDetail(strMsg);

        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }
    }

    /**
     * 
     * @category 
     * @author 吴平福 
     * @param args
     * update 2017年4月27日
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        FindGoods cFindGoods=new FindGoods();
    }

}
