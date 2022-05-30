/**
 * @author 421722623@qq.com
 */
package org.jpf.taobao.httputils;


import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author wupf
 * 
 */
public class TaobaoHttpSearch {
    private static final Logger logger = LogManager.getLogger();

    /**
     * 
     */
    public TaobaoHttpSearch() {
        // TODO Auto-generated constructor stub
    }


    public static String getWebSourceCode(String strUrl) throws Exception {
        String strReturn = "";

        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response1 = null;
        try {
            // String strUrl
            // ="https://item.taobao.com/item.htm?spm=a1z10.3-c.w4002-1271539722.88.DqT3rP&id=538432774073";
            // String strUrl =
            // "https://tui.taobao.com/recommend?_ksTS=1487342252020_1126&callback=detail_recommend_bought&appid=11&count=12&sellerid=402647666&itemid=538432774073&categoryid=50012027";
            // String
            // strUrl="https://detail.tmall.com/item.htm?spm=a1z0d.6639537.1997196601.269.UcelW2&id=546537478311&skuId=3303726648816";
            httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(strUrl);
            httpGet.setHeader("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");
            //httpGet.getParams().setParameter("http.protocol.allow-circular-redirects", true);

            response1 = httpclient.execute(httpGet);
            logger.debug(response1.getStatusLine());
            HttpEntity entity;
            int status = response1.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                entity = response1.getEntity();
                strReturn = entity != null ? EntityUtils.toString(entity) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
            EntityUtils.consume(entity);
        }catch (Exception ex) {
            // TODO: handle exception
            ex.printStackTrace();
        
        } finally {
            try {
                response1.close();
                httpclient.close();
            } catch (Exception ex) {
            }
        }
        // Thread.sleep(1);
        // logger.info(strReturn);
        return strReturn;
    }

    public static String getWebSourceCode(String strUrl, String strReferURl) throws Exception {
        String strReturn = "";

        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response1 = null;
        try {
            // String strUrl
            // ="https://item.taobao.com/item.htm?spm=a1z10.3-c.w4002-1271539722.88.DqT3rP&id=538432774073";
            // String strUrl =
            // "https://tui.taobao.com/recommend?_ksTS=1487342252020_1126&callback=detail_recommend_bought&appid=11&count=12&sellerid=402647666&itemid=538432774073&categoryid=50012027";
            // String
            // strUrl="https://detail.tmall.com/item.htm?spm=a1z0d.6639537.1997196601.269.UcelW2&id=546537478311&skuId=3303726648816";
            httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(strUrl);
            httpGet.addHeader("Referer", strReferURl);// Referer必须要设置
            httpGet.setHeader("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");
            response1 = httpclient.execute(httpGet);
            logger.debug(response1.getStatusLine());
            HttpEntity entity;
            int status = response1.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                entity = response1.getEntity();
                strReturn = entity != null ? EntityUtils.toString(entity) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
            EntityUtils.consume(entity);

        } finally {
            response1.close();
            httpclient.close();
        }
        // Thread.sleep(1);
        // logger.info(strReturn);
        return strReturn;
    }
}
