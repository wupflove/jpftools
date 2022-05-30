/**
 * @author 吴平福 E-mail:421722623@qq.com
 * @version 创建时间：2017年4月23日 下午9:32:05 类说明
 */

package org.jpf.taobao.zhanguibao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jpf.taobao.priceInfo;
import org.jpf.taobao.sku;

import com.alibaba.fastjson.JSON;


/**
 * 
 */
public class PromotionalPrice {

    /**
     * 
     */
    public PromotionalPrice() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @category @author 吴平福
     * @param args update 2017年4月23日
     */

    public static void main(String[] args) throws ClientProtocolException, IOException {
        try {



            HttpClient httpclient = new DefaultHttpClient();
            // String
            // url="http://detail.tmall.com/item.htm?spm=a230r.1.14.44.RoTYht&id=43508885384&ns=1&abbucket=20";
            String strURL = "https://item.taobao.com/item.htm?id=545851901545";
            // https://detail.tmall.com/item.htm?spm=a1z0d.6639537.1997196601.269.UcelW2&id=546537478311
            String id = getStrByPrePost(strURL, "id=", "&");// 根据url获取商品的id
            if (id != null) {
                // 组装出获取商品信息的url
                String detailUrl =
                        "https://mdskip.taobao.com/core/initItemDetail.htm?isForbidBuyItem=false&cartEnable=true&isAreaSell=false&addressLevel=2&tmallBuySupport=true&cachedTimestamp=1492939104236&isRegionLevel=false&isSecKill=false&service3C=false&sellerPreview=false&itemId="
                                + id
                                + "&isApparel=true&isUseInventoryCenter=false&isPurchaseMallPage=false&queryMemberRight=true&showShopProm=false&household=false&offlineShop=false&tryBeforeBuy=false&callback=setMdskip&timestamp=1492955646416&isg=Ajw8TliN-7cTiokPSBIgawsvjNTvMuBf&isg2=ApubrmCSR94oSrSjTTxuqQZCKv9FP69yBBj9mY3ZcBqxbLtOFUA_wrmucEG-&ref=https%3A%2F%2Fcart.taobao.com%2Fcart.htm%3Fspm%3Da1z09.2.1997525049.1.TB8p7E%26from%3Dmini%26pm_id%3D1501036000a02c5c3739";
                System.out.println(detailUrl);
                HttpGet get = new HttpGet(detailUrl);
                get.addHeader("Referer", strURL);// Referer必须要设置
                HttpResponse response = httpclient.execute(get);
                String content = getContent(response);
                System.out.println("content:" + content);
                content = getStrByPrePost(content, "setMdskip(", ")");
                // 返回的内容是json格式，可以通过gson等来解析，为了方便，此处直接截取。
                // System.out.println("商品信息:"+content);
                String priceInfo = getStrByPrePost(content, "priceInfo", "queryProm");
                // System.out.println(priceInfo);
                // RespJsonData cRespJsonData = JSON.parseObject(strResult, RespJsonData.class);
                priceInfo = "{\"priceInfo" + priceInfo.substring(0, priceInfo.length() - 2) + "}";
                System.out.println(priceInfo);
                priceInfo cpriceInfo = JSON.parseObject(priceInfo, priceInfo.class);
                System.out.println(cpriceInfo.getPriceInfo().size());
                for (String key : cpriceInfo.getPriceInfo().keySet()) {
                    sku value = cpriceInfo.getPriceInfo().get(key);
                    System.out.println("skuid:" + key);
                    if (value.getPromotionList()[0] != null) {
                        System.out.println(
                                "手机商品价格:" + value.getPromotionList()[0].getExtraPromPrice());
                        System.out.println("商品价格:" + value.getPromotionList()[0].getPrice());
                    } else {
                        System.out.println("商品价格:" + value.getPrice());
                    }
                }
            }
        } catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        }

    }

    /**
     * 获取返回内容的string
     * 
     * @param response
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    private static String getContent(HttpResponse response)
            throws IllegalStateException, IOException {
        java.io.InputStream is = response.getEntity().getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));// response.getEntity().getContentEncoding().getName()
        String line = "";
        String temp = null;
        while ((temp = br.readLine()) != null) {
            line += temp;
        }
        return line;

    }

    /**
     * 根据前后的文本截取指定的内容。
     * 
     * @param str
     * @param pre
     * @param post
     * @return
     */
    public static String getStrByPrePost(String str, String pre, String post) {
        if (str != null) {
            if (pre != null) {
                int s = str.indexOf(pre);
                if (s > -1) {
                    str = str.substring(s + pre.length(), str.length());
                } else {
                    return null;
                }

            }
            if (post != null) {
                int e = str.indexOf(post);
                if (e > -1) {
                    str = str.substring(0, e);

                }

            }
        }

        return str;
    }

}
