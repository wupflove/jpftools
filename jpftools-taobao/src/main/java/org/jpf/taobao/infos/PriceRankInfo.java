package org.jpf.taobao.infos;

/**
 * 
 */
public class PriceRankInfo {

  /**
   * 
   */
  public PriceRankInfo() {
    // TODO Auto-generated constructor stub
  }

  private int percent;
  private String start = "";
  private String end = "";

  /**
   * @return the percent
   */
  public int getPercent() {
    return percent;
  }

  /**
   * @param percent the percent to set
   */
  public void setPercent(int percent) {
    this.percent = percent;
  }

  /**
   * @return the start
   */
  public String getStart() {
    return start;
  }

  /**
   * @param start the start to set
   */
  public void setStart(String start) {
    this.start = start;
  }

  /**
   * @return the end
   */
  public String getEnd() {
    return end;
  }

  /**
   * @param end the end to set
   */
  public void setEnd(String end) {
    this.end = end;
  }
}
