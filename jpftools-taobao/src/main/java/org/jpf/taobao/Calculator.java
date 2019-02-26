/** 
* @author 吴平福 
* E-mail:wupf@asiainfo.com 
* @version 创建时间：2017年7月16日 下午12:25:21 
* 类说明 
*/ 

package org.jpf.taobao;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;  
import java.awt.Toolkit;  
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;  
import javax.swing.JMenuBar;  
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;  
  
public class Calculator extends JFrame  
{  
    public Calculator()  
    {  
        super("无标题 - 记事本");  
        JMenuBar menuBar = new JMenuBar();  
  
        JMenu fileMenu = new JMenu("跑标题");  
        JMenu editMenu = new JMenu("编辑");  
        JMenu formatMenu = new JMenu("格式");  
        JMenu checkMenu = new JMenu("查看");  
        JMenu helpMenu = new JMenu("帮助");  
  
        menuBar.add(fileMenu);  
        menuBar.add(editMenu);  
        menuBar.add(formatMenu);  
        menuBar.add(checkMenu);  
        menuBar.add(helpMenu);  
        
        JTabbedPane tabPane = new JTabbedPane();// 选项卡布局  
        JScrollPane scrollPane = new JScrollPane();  
        scrollPane  
                .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        scrollPane  
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);  
  
        JTextArea textArea = new JTextArea();  
        scrollPane.setViewportView(textArea);  
  
        JLabel label1 = new JLabel("甩手导出文件目录");  
        label1.setBounds(10, 10, 120, 20); 
        Container con = new Container();//  
        con.add(label1);  
        tabPane.add("跑标题",con);// 添加布局1  
        
        this.getContentPane().add(tabPane,BorderLayout.NORTH);
        this.getContentPane().add(scrollPane,BorderLayout.CENTER);  
  
        this.setJMenuBar(menuBar);  
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
  
        //用来设置窗口随屏幕大小改变  
        sizeWindowOnScreen(this, 0.6, 0.6);  
        this.setVisible(true);  
    }  
  
    /** 
    *  
    * @param calculator 
    * @param widthRate 宽度比例  
    * @param heightRate 高度比例 
    */  
    private void sizeWindowOnScreen(Calculator calculator, double widthRate,  
            double heightRate)  
    {  
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
  
        calculator.setSize(new Dimension((int) (screenSize.width * widthRate),  
                (int) (screenSize.height * heightRate)));  
    }  
  
    public static void main(String[] args)  
    {  
        Calculator calculator = new Calculator();  
    }  
}