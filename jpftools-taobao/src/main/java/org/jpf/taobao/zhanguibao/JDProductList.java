/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年4月24日 下午2:33:00 
* 类说明 
*/ 

package org.jpf.taobao.zhanguibao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author InJavaWeTrust
 *
 */
public class JDProductList implements ProductList{
    
private String jdUrl;
    
    private String productName;
    
    private static PriceCheckUtil pcu = PriceCheckUtil.getInstance();
    
    public JDProductList(String jdUrl, String productName){
        this.jdUrl = jdUrl;
        this.productName = productName;
    }

    public List<ProductInfo> getProductList() {
        List<ProductInfo> jdProductList = new ArrayList<ProductInfo>();
        ProductInfo productInfo = null;
        String url = "";
        for(int i = 0; i < 10; i++){
            try {
                System.out.println("JD Product 第[" + (i + 1) + "]页");
                if(i == 0) {
                    url = jdUrl;
                }else{
                    url = Constants.JDURL + pcu.getGbk(productName) + Constants.JDENC + Constants.JDPAGE + (i + 1);
                }
                System.out.println(url);
                Document document = Jsoup.connect(url).timeout(5000).get();
                Elements uls = document.select("ul[class=gl-warp clearfix]");
                Iterator<Element> ulIter = uls.iterator();
                while(ulIter.hasNext()) {
                    Element ul = ulIter.next();
                    Elements lis = ul.select("li[data-sku]");
                    Iterator<Element> liIter = lis.iterator();
                    while(liIter.hasNext()) {
                        Element li = liIter.next();
                        Element div = li.select("div[class=gl-i-wrap]").first();
                        Elements title = div.select("div[class=p-name p-name-type-2]>a");
                        String productName = title.attr("title"); //得到商品名称
                        Elements price = div.select(".p-price>strong");
                        String productPrice =price.attr("data-price"); //得到商品价格
                        productInfo = new ProductInfo();
                        productInfo.setProductName(productName);
                        productInfo.setProductPrice(productPrice);
                        jdProductList.add(productInfo);
                    }
                }
            } catch(Exception e) {
                System.out.println("Get JD product has error [" + url + "]");
                System.out.println(e.getMessage());
            }
        }
        return jdProductList;
    }
    
    public static void main(String[] args) {
        try {
            String productName = "书包";
            String jdUrl = Constants.JDURL + pcu.getGbk(productName)  + Constants.JDENC;
            List<ProductInfo> list = new JDProductList(jdUrl, productName).getProductList();
            System.out.println(list.size());
            for(ProductInfo pi : list){
                System.out.println(pi.getProductName() + "  " + pi.getProductPrice());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
