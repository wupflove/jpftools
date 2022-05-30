/**
 * copyrigth by wupf@ 2019年2月24日
 */
package org.jpf.fuzz;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author 421722623@qq.com
 *
 */
public class GsonFuzz {

  /**
   * 
   */
  public GsonFuzz() {
    // TODO Auto-generated constructor stub
    fuzz1();
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
    new GsonFuzz();
  }

  /**
   * 
   * @category: Exception cannot cast String to Integer 说明 （非 JDK bug）Gson 通过 TypeToken 转换 List<T>
   *            能写入不属于 T 类型的数据，get 出来赋值给 T 类型的变量/常量报错。 解决方案： 1.手动检查列表内数据都符合泛型 T 2.改用
   *            fastjson等其它能静态检查类型的库。
   * @Title: fuzz1
   * @author:421722623@qq.com
   * @date:2019年2月24日
   */
  public void fuzz1() {
    String json = "[1, '2', 'a']";
    Type type = new TypeToken<Integer>() {}.getType();
    Gson gson = new Gson();
    List<Integer> list = gson.fromJson(json, type);

    Integer i = list == null || list.isEmpty() ? null : list.get(1);
  }
}
