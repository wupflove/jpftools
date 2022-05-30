/**
 * copyrigth by wupf@ 2019年2月24日
 */
package org.jpf.fuzz;

import com.alibaba.fastjson.JSONArray;

/**
 * @author 421722623@qq.com
 *
 */
public class JsonFuzz {

  /**
   * 
   */
  public JsonFuzz() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @category:
   * @Title: main
   * @author:421722623@qq.com
   * @date:2019年2月24日
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

  /**
   * ArrayList可通过构造函数传入非指定泛型的List并在get时出错,JDK1.7。 解决方案：
   * 
   * 1.改用 Open JDK8
   * 
   * 2.升级 JDK
   * 
   * @category:
   * @Title: fastjson_fuzz
   * @author:421722623@qq.com
   * @date:2019年2月24日
   */
  public void fastjson_fuzz() {
    JSONArray arr = new JSONArray(); // com.alibaba.fastjson.JSONArray
    arr.add("s");


    // List<Long> list = new ArrayList<>(arr);
    // list.get(0); // Exception cannot cast String to Long
  }
}
