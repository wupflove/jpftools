/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年4月24日 下午2:35:37 
* 类说明 
*/ 

package org.jpf.taobao.zhanguibao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 
 * @author InJavaWeTrust
 *
 */
public class PriceCheckUtil {
    private static final Logger logger = LogManager.getLogger();
    private PriceCheckUtil() {
        
    }
    
    private static final PriceCheckUtil instance = new PriceCheckUtil();
    
    public static PriceCheckUtil getInstance() {
        return instance;
    }

    
    /**
     * 商品汉字转码
     * @param productName 商品名称
     * @return
     */
    public String getGbk(String productName){
        String retGbk = "";
        try {
            retGbk = new String(productName.getBytes("UTF-8"), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retGbk;
    }
    /**
     * 对淘宝浏览器汉字进行转换
     * @param productName 商品名称
     * @return
     */
    public String getUrlCode(String productName){
        String retUrlCode = "";
        try {
            retUrlCode = URLEncoder.encode(productName, "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retUrlCode;
    }
    
    /**
     * 从列表list中找到与productName相似度最高的ProductInfo
     *
     * @param productName
     * @param list
     * @return 相似度最高的productName
     */
    public ProductInfo getSimilarity(String productName, List<ProductInfo> list) {
        ProductInfo productInfo = null;
        /**
         * 找到list中所有的productName与字符串productName的相似度，保存在lens数组中
         */
        double lens[] = new double[list.size()];
        for (int i = 0; i < list.size() - 1; i++) {
            lens[i] = sim(productName, list.get(i).getProductName());
        }
        /**
         * 遍历出最大的相似度maxLen
         */
        double maxLen = 0.0;
        for (int i = 0; i < lens.length; i++) {
            if (maxLen < lens[i]) {
                maxLen = lens[i];
            }
        }
        /**
         * 遍历出最大的相似度的索引maxLenIndex
         */
        int maxLenIndex = 0;
        for (int i = 0; i < lens.length; i++) {
            if (maxLen == lens[i]) {
                maxLenIndex = i;
            }
        }
        productInfo = list.get(maxLenIndex);
        return productInfo;
    }
    
    /**
     * 求三个数中最小的一个
     * @param one
     * @param two
     * @param three
     * @return
     */
    public int min(int one, int two, int three) {
        int min = one;
        if(two < min) {
            min = two;
        }
        if(three < min) {
            min = three;
        }
        return min;
    }

    /**
     * 计算矢量距离
     * Levenshtein Distance(LD)
     * @param str1
     * @param str2
     * @return
     */
    public int ld(String str1, String str2) {
        int d[][];    //矩阵
        int n = str1.length();
        int m = str2.length();
        int i;    //遍历str1的
        int j;    //遍历str2的
        char ch1;    //str1的
        char ch2;    //str2的
        int temp;    //记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if(n == 0) {
            return m;
        }
        if(m == 0) {
            return n;
        }
        d = new int[n+1][m+1];
        for(i=0; i<=n; i++) {    //初始化第一列
            d[i][0] = i;
        }
        for(j=0; j<=m; j++) {    //初始化第一行
            d[0][j] = j;
        }
        for(i=1; i<=n; i++) {    //遍历str1
            ch1 = str1.charAt(i-1);
            //去匹配str2
            for(j=1; j<=m; j++) {
                ch2 = str2.charAt(j-1);
                if(ch1 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                //左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i-1][j]+1, d[i][j-1]+1, d[i-1][j-1]+temp);
            }
        }
        return d[n][m];
    }

    /**
     * 计算相似度
     * @param str1
     * @param str2
     * @return
     */
    public double sim(String str1, String str2) {
        int ld = ld(str1, str2);
        return 1 - (double) ld / Math.max(str1.length(), str2.length());
    }
    
    /** 
     * 毫秒转换成hhmmss 
     * @param ms 毫秒 
     * @return hh:mm:ss 
     */  
    public String msToss(long ms) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");  
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));  
        String ss = formatter.format(ms);  
        return ss;  
    }
    
    /**
     * 禁止htmlunit日志输出
     */
    public void offLog(){
        //LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",                "org.apache.commons.logging.impl.NoOpLog");
    }
    /**
     * 获取淘宝数据
     * @param url
     * @return
     * @throws Exception
     */
    public String getXmlByHtmlunit(String url) throws Exception {
        offLog();
        String ret = "";
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        // 1 启动JS
        webClient.getOptions().setJavaScriptEnabled(true);
        // 2 禁用Css，可避免自动二次请求CSS进行渲染
        webClient.getOptions().setCssEnabled(false);
        // 3 启动客户端重定向
        webClient.getOptions().setRedirectEnabled(true);
        // 4 JS运行错误时，是否抛出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        // 5AJAX support
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        // 6 设置超时
        webClient.getOptions().setTimeout(Constants.TIMEOUT);
        WebRequest webRequest = new WebRequest(new URL(url));
        webRequest.setHttpMethod(HttpMethod.GET);
        HtmlPage page = webClient.getPage(webRequest);
        webClient.waitForBackgroundJavaScript(10000);
        ret = page.asXml();
        webClient.close();
        return ret;
    }
    
    /**
     * 通过Phantomjs得到html页面
     * @param url
     * @return
     */
    public String getHtmlByPhantomjs(String url) {
        StringBuilder html = new StringBuilder();
        try {
            Runtime rt = Runtime.getRuntime();
            logger.info(Constants.PHANTOMJSPATH + Constants.SCRIPT + url);
            Process p = rt.exec(Constants.PHANTOMJSPATH + Constants.SCRIPT + url);
            InputStream is = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String tmp = "";
            while ((tmp = br.readLine()) != null) {
                html.append(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html.toString();
    }

}
