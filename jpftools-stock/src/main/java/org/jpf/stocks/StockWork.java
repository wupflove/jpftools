package org.jpf.stocks ;

import org.jpf.stocks.util.*;
import org.jpf.utils.conf.AiConfigUtil;

/**
 *
 * <p>Title: </p>
 * <p>Description: 主类</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @author wupingfu
 * @version 1.0
 */
public class StockWork
{
  public StockWork()
  {
    String sWorkType=AiConfigUtil.GetConfigString("WORKINGTYPE");
    String[] sWorkTypes=sWorkType.split(",");
    if(sWorkTypes==null)
    {
      System.out.println("没有配置工作类型");
      return;
    }
    for(int i=0;i<sWorkTypes.length;i++)
    {
       StartWork(Integer.parseInt(sWorkTypes[i]));
    }

  }
  private void StartWork(int iWorkType)
  {
    if (1 == iWorkType) {
      //查找基金持股情况
      System.out.println("查找社保持股情况") ;
      FindFund m_ff = new FindFund() ;
      m_ff.DoWork() ;
    }
    if (2 == iWorkType) {
      System.out.println("查找净资产") ;
      FindEquity m_ff = new FindEquity() ;
      m_ff.DoWork() ;
    }
    if (3 == iWorkType) {
      System.out.println("查找评级情况") ;
      FindReview m_ff = new FindReview() ;
      m_ff.DoWork() ;
    }
    if (4 == iWorkType) {
      System.out.println("查找持股情况") ;
      FindStockHold m_ff = new FindStockHold() ;
      m_ff.DoWork() ;
    }
    if (5 == iWorkType) {
      System.out.println("查找大小非情况") ;
      FindDecontrol m_ff = new FindDecontrol() ;
      m_ff.DoWork() ;
    }
    if (6 == iWorkType) {
      System.out.println("查找发行价情况") ;
      FindIssuePrice m_ff = new FindIssuePrice() ;
      m_ff.DoWork() ;
    }

  }
  public static void main(String[] args)
  {

    StockWork m_StockClass = new StockWork() ;

  }

}
