/** 
* @author 吴平福 
* E-mail:421722623@qq.com 
* @version 创建时间：2017年4月28日 下午6:33:09 
* 类说明 
*/ 

package org.jpf.taobao.infos;

/**
 * 
 */
public class CommodityInfo {

    /**
     * 
     */
    public CommodityInfo() {
        // TODO Auto-generated constructor stub
    }
    
    private String Title;
    private String name;
    private String url;
    private String keyword;
    
    public String toString()
    {
        return Title+":"+url+":"+keyword+":"+name;
    }
    /**
     * @return the title
     */
    public String getTitle() {
        return Title;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        Title = title;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }
    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


}
