/**
 * copyrigth by wupf@ 2019年2月24日
 */
package org.jpf.fuzz;

/**
 * @author wupf@asiainfo.com
 *
 */
public class ValFuzz {

  /**
   * 局部变量和同名的全局变量能在一个方法内，编译通过，运行也正常。 如果两个变量中间隔了比较长的其它代码，很可能会导致开发人员将两者混淆，导致逻辑认知错误，从而写出或改出有问题的代码。
   * 
   * 解决方案：
   * 
   * 命名局部变量前先搜素，确保没有已声明的同名全局变量。
   */
  public ValFuzz() {
    // TODO Auto-generated constructor stub
    FuzzNull();
    this.toString();
  }

  /**
   * @category:
   * @Title: main
   * @author:wupf@asiainfo.com
   * @date:2019年2月24日
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    new ValFuzz();
  }

  int val;

  @Override
  public String toString() {
    val = 1;
    String val = "";
    return super.toString();
  }

  /**
   * 
   * @category: 基本类型在三元表达式内可赋值为null，编译通过但运行出错 首次发现所在项目：ZBLibrary
   * 
   *            解决方案：
   * 
   *            在给基础类型用3元表达式赋值时，null 先转为基础类型的默认值。
   * @Title: FuzzNull
   * @author:wupf@asiainfo.com
   * @date:2019年2月24日
   */
  public void FuzzNull() {
    // Exception in thread "main" java.lang.NullPointerException
    try {
      int i = true ? null : 0;
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }

  }
}
