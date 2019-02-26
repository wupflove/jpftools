package org.jpf.taobao.infos;



/**
 * 
 */
public class ShopEvaluationInfo {
  // "isTmall":true,"delivery":[479,1,375],"description":[486,0,0],"service":[480,1,308]
  /**
   * 
   */
  public ShopEvaluationInfo() {
    // TODO Auto-generated constructor stub
  }

  private String delivery;
  private String description;
  private String service;
  boolean isTmall;

  /**
   * @return the delivery
   */
  public String getDelivery() {
    return delivery;
  }

  /**
   * @param delivery the delivery to set
   */
  public void setDelivery(String delivery) {
    this.delivery = delivery;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the service
   */
  public String getService() {
    return service;
  }

  /**
   * @param service the service to set
   */
  public void setService(String service) {
    this.service = service;
  }

  /**
   * @return the isTmall
   */
  public boolean isTmall() {
    return isTmall;
  }

  /**
   * @param isTmall the isTmall to set
   */
  public void setTmall(boolean isTmall) {
    this.isTmall = isTmall;
  }

}
