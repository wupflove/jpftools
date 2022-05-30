/** 
* @author 吴平福 
* E-mail:421722623@qq.com 
* @version 创建时间：2017年5月18日 上午10:24:54 
* 类说明 
*/ 

package org.jpf.taobao;

/**
 * 
 */
public class sku {

    /**
     * 
     */
    public sku() {
        // TODO Auto-generated constructor stub
    }
    boolean areaSold;
    boolean onlyShowOnePrice;
    String price;
    int sortOrder;
    PromotionList[] promotionList =new PromotionList[1];
    
    /**
     * @return the areaSold
     */
    public boolean isAreaSold() {
        return areaSold;
    }
    /**
     * @param areaSold the areaSold to set
     */
    public void setAreaSold(boolean areaSold) {
        this.areaSold = areaSold;
    }
    /**
     * @return the onlyShowOnePrice
     */
    public boolean isOnlyShowOnePrice() {
        return onlyShowOnePrice;
    }
    /**
     * @param onlyShowOnePrice the onlyShowOnePrice to set
     */
    public void setOnlyShowOnePrice(boolean onlyShowOnePrice) {
        this.onlyShowOnePrice = onlyShowOnePrice;
    }
    /**
     * @return the price
     */
    public String getPrice() {
        return price;
    }
    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }
    /**
     * @return the sortOrder
     */
    public int getSortOrder() {
        return sortOrder;
    }
    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
    /**
     * @return the promotionList
     */
    public PromotionList[] getPromotionList() {
        return promotionList;
    }
    /**
     * @param promotionList the promotionList to set
     */
    public void setPromotionList(PromotionList[] promotionList) {
        this.promotionList = promotionList;
    }


    
}
