package org.jpf.stocks ;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jpf.stocks.util.*;
public class StockTest {

  public StockTest() {

  }

  public static void main(String[] args) {
    String a = "*ST大  亚科技" ;
    //System.out.println(a.charAt(3));
    //System.out.println("code=" + StockUtil.GetStockCode(a)) ;
    //System.out.println("name=" + StockUtil.GetStockName(a)) ;
    for (int i=0;i<a.length();i++)
    {
      System.out.println(a.charAt(i));
    }
    String str = "2008-07-07 增持      无评级    调高  0.7000  0.8900 凯基证券   王志霖";
    String b[]=str.split("[ ]+");

    System.out.println(b.length);
    System.out.println("before conversion:"+str);
    str = str.replaceAll("[ ]+", " ");//逗号前的中括号内是一个空格,逗号后的双引号中也是一个空格。
    System.out.println("after conversion:"+str);
    String c[]=str.split(" ");

    System.out.println(c.length);


  }

  public static int strLen(String in_Str) {
    return in_Str.getBytes().length ;

  }

}
