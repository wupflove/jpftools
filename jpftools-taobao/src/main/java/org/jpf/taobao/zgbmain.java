/**
 * @author 吴平福 E-mail:421722623@qq.com
 * @version 创建时间：2017年7月16日 上午9:24:35 类说明
 */

package org.jpf.taobao;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jpf.taobao.shops.ShopManager;
import org.jpf.taobao.titles.SameTypeTitle;
import org.jpf.taobao.titles.SelectGood;
import org.jpf.utils.logUtil.TextAreaLogAppender;

import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 */
public class zgbmain extends JFrame implements ActionListener {

    private JPanel contentPane;
    JScrollPane scrollPane = new JScrollPane();
    JButton button1 = new JButton("...");// 选择
    JButton button2 = new JButton("...");// 选择
    JButton button3 = new JButton("...");// 选择
    JButton button4 = new JButton("开始跑标题");// 选择
    JButton button5 = new JButton("检查");// 选择

    JButton button21 = new JButton("选品");// 选择
    JButton button31 = new JButton("抓标题");// 选择
    JButton button41 = new JButton("抓标题");// 选择

    JTextField text1 = new JTextField("d:\\zgb\\原始文件\\工作室\\7.17\\两朵会11");// TextField 目录的路径
    JTextField text2 = new JTextField("d:\\zgb\\原始文件\\工作室\\7.17\\两朵会11");// TextField 目录的路径
    JTextField text3 = new JTextField("d:\\zgb");// TextField 目录的路径

    // 价格波动
    JTextField text4 = new JTextField("2");
    // 折扣
    JTextField text5 = new JTextField("1");
    // 分店
    JTextField text6 = new JTextField("1");

    JTextField text21 = new JTextField();// TextField 目录的路径

    JTextField text31 = new JTextField();
    JTextField text32 = new JTextField();

    JCheckBox checkBox1 = new JCheckBox("描述图增加促销");
    JCheckBox checkBox2 = new JCheckBox("描述图增加流程");
    JCheckBox checkBox3 = new JCheckBox("主图增加水印");
    JCheckBox checkBox4 = new JCheckBox("精华模式(10)");

    JFileChooser jfc = new JFileChooser();// 文件选择器

    JTextArea textArea = new JTextArea();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    zgbmain frame = new zgbmain();
                    frame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void init() {
        try {
            TextAreaLogAppender.setTextAreaLogAppender(textArea);
            new ShowLogThread(textArea).start();
        } catch (Exception ex) {
            // TODO: handle exception
        }
    }

    /**
     * Create the frame.
     */
    public zgbmain() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 710);
        setTitle("掌柜宝");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);


        contentPane.add(scrollPane, BorderLayout.SOUTH);

        textArea.setRows(29);
        textArea.setEditable(false);
        textArea.setAutoscrolls(true);
        scrollPane.setViewportView(textArea);
        scrollPane.setAutoscrolls(true);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        Container con = new Container();//
        JLabel label1 = new JLabel("甩手导出文件目录");
        JLabel label2 = new JLabel("标题文件目录");
        JLabel label3 = new JLabel("自定义图片目录");
        JLabel label4 = new JLabel("价格波动范围%");
        JLabel label5 = new JLabel("折扣");
        JLabel label6 = new JLabel("分店");

        label1.setBounds(10, 10, 120, 20);
        text1.setBounds(125, 10, 320, 20);
        button1.setBounds(450, 10, 50, 20);

        label2.setBounds(10, 35, 120, 20);
        text2.setBounds(125, 35, 320, 20);
        button2.setBounds(450, 35, 50, 20);

        label3.setBounds(10, 60, 120, 20);
        text3.setBounds(125, 60, 320, 20);
        button3.setBounds(450, 60, 50, 20);

        label4.setBounds(10, 85, 120, 20);
        text4.setBounds(125, 85, 60, 20);

        label5.setBounds(200, 85, 30, 20);
        text5.setBounds(240, 85, 60, 20);

        label6.setBounds(340, 85, 30, 20);
        text6.setBounds(380, 85, 60, 20);

        checkBox1.setBounds(10, 110, 130, 20);
        checkBox2.setBounds(140, 110, 130, 20);
        checkBox3.setBounds(270, 110, 110, 20);
        checkBox4.setBounds(380, 110, 100, 20);

        button5.setBounds(10, 135, 120, 20);
        button4.setBounds(360, 135, 120, 20);

        button1.addActionListener(this); // 添加事件处理
        button2.addActionListener(this); // 添加事件处理
        button3.addActionListener(this); // 添加事件处理
        button4.addActionListener(this); // 添加事件处理
        button5.addActionListener(this); // 添加事件处理

        con.add(label1);
        con.add(text1);
        con.add(button1);

        con.add(label2);
        con.add(text2);
        con.add(button2);

        con.add(label3);
        con.add(text3);
        con.add(button3);
        con.add(label4);
        con.add(text4);

        con.add(label5);
        con.add(text5);
        con.add(label6);
        con.add(text6);

        con.add(checkBox1);
        con.add(checkBox2);
        con.add(checkBox3);
        con.add(checkBox4);

        con.add(button4);
        con.add(button5);
        ///////////////////////////////////////////////////////
        Container con2 = new Container();//

        JLabel label21 = new JLabel("输入商品关键字");


        label21.setBounds(10, 10, 120, 20);
        text21.setBounds(125, 10, 320, 20);

        button21.setBounds(10, 135, 120, 20);
        button21.addActionListener(this); // 添加事件处理
        con2.add(label21);
        con2.add(text21);
        con2.add(button21);
        ///////////////////////////////////////////////////////
        Container con3 = new Container();//
        JLabel label31 = new JLabel("输入同款商品地址");
        JLabel label32 = new JLabel("输入同款商品名称");

        label31.setBounds(10, 10, 120, 20);
        text31.setBounds(125, 10, 350, 20);

        label32.setBounds(10, 35, 120, 20);
        text32.setBounds(125, 35, 350, 20);

        con3.add(label31);
        con3.add(text31);
        con3.add(label32);
        con3.add(text32);
        button31.setBounds(10, 135, 120, 20);
        button31.addActionListener(this); // 添加事件处理
        con3.add(button31);

        ///////////////////////////////////////////////////////
        Container con4 = new Container();
        button41.setBounds(10, 135, 120, 20);
        button41.addActionListener(this); // 添加事件处理
        con4.add(button41);

        tabbedPane.add("跑标题", con);
        tabbedPane.add("选商品", con2);
        tabbedPane.add("根据同款抓标题", con3);
        tabbedPane.add("根据店铺抓标题", con4);
        init();
    }

    /**
     * 时间监听的方法
     */
    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource().equals(button1)) {// 判断触发方法的按钮是哪个
            jfc.setFileSelectionMode(1);// 设定只能选择到文件夹
            int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
            if (state == 1) {
                return;
            } else {
                File f = jfc.getSelectedFile();// f为选择到的目录
                text1.setText(f.getAbsolutePath());
            }
        }
        // 绑定到选择文件，先择文件事件
        if (e.getSource().equals(button2)) {
            jfc.setFileSelectionMode(1);// 设定只能选择到文件
            int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
            if (state == 1) {
                return;// 撤销则返回
            } else {
                File f = jfc.getSelectedFile();// f为选择到的文件
                text2.setText(f.getAbsolutePath());
            }
        }
        // 绑定到选择文件，先择文件事件
        if (e.getSource().equals(button3)) {
            jfc.setFileSelectionMode(1);// 设定只能选择到文件
            int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
            if (state == 1) {
                return;// 撤销则返回
            } else {
                File f = jfc.getSelectedFile();// f为选择到的文件
                text3.setText(f.getAbsolutePath());
            }
        }
        // 跑标题
        if (e.getSource().equals(button4)) {
            // 弹出对话框可以改变里面的参数具体得靠大家自己去看，时间很短
            JOptionPane.showMessageDialog(null, text2.getText() + " " + text1.getText(), "提示", 2);
            TextAreaLogAppender.clearLog();

            CopyGoods2 cCopyGoods = new CopyGoods2(text1.getText().trim(), text2.getText().trim());
            cCopyGoods.setShopCount(Integer.parseInt(text6.getText()));
            RunParam.iChangeLevel = Integer.parseInt(text4.getText());
            RunParam.zhekou = Double.parseDouble(text5.getText());
            RunParam.b_WaterPic = checkBox3.isSelected();
            RunParam.ADD_BEGIN_PIC = checkBox1.isSelected();
            RunParam.ADD_END_PIC = checkBox2.isSelected();
            RunParam.B_Essence = checkBox4.isSelected();

            try {
                cCopyGoods.start();

            } catch (Exception ex) {
                // TODO: handle exception
                ex.printStackTrace();
            }

        }
        // 检查
        if (e.getSource().equals(button5)) {

            TextAreaLogAppender.clearLog();

            CopyGoods2 cCopyGoods = new CopyGoods2(text1.getText().trim(), text2.getText().trim());
            RunParam.iChangeLevel = Integer.parseInt(text4.getText());
            RunParam.zhekou = Double.parseDouble(text5.getText());
            RunParam.b_WaterPic = checkBox3.isSelected();
            RunParam.ADD_BEGIN_PIC = checkBox1.isSelected();
            RunParam.ADD_END_PIC = checkBox2.isSelected();
            RunParam.B_Essence = checkBox4.isSelected();
            // System.out.println(MakeOutPutDir("D:\\zgb\\1拖3新店1套\\33_学步带_500.csv"));
            try {
                cCopyGoods.start();

            } catch (Exception ex) {
                // TODO: handle exception
                ex.printStackTrace();
            }

        }
        // 选品
        if (e.getSource().equals(button21)) {

            TextAreaLogAppender.clearLog();

            // System.out.println(MakeOutPutDir("D:\\zgb\\1拖3新店1套\\33_学步带_500.csv"));
            try {
                new SelectGood(text21.getText().trim()).start();

            } catch (Exception ex) {
                // TODO: handle exception
                ex.printStackTrace();
            }

        }
        // 抓同款标题
        if (e.getSource().equals(button31)) {

            TextAreaLogAppender.clearLog();

            // System.out.println(MakeOutPutDir("D:\\zgb\\1拖3新店1套\\33_学步带_500.csv"));
            try {
                new SameTypeTitle(text31.getText().trim(), text32.getText().trim()).start();

            } catch (Exception ex) {
                // TODO: handle exception
                ex.printStackTrace();
            }

        }
        // 根据店铺抓商品
        if (e.getSource().equals(button41)) {

            TextAreaLogAppender.clearLog();
            // System.out.println(MakeOutPutDir("D:\\zgb\\1拖3新店1套\\33_学步带_500.csv"));
            try {
                new ShopManager().start();

            } catch (Exception ex) {
                // TODO: handle exception
                ex.printStackTrace();
            }

        }
    }

    /**
     * @category 显示日志到界面上，需要用线程方式
     */
    class ShowLogThread extends Thread {
        JTextArea jTextPane;

        public ShowLogThread(JTextArea jTextPane) {
            this.jTextPane = jTextPane;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    sleep(100);
                    if (TextAreaLogAppender.getLogVector().size() > 0) {
                        jTextPane.append(TextAreaLogAppender.getLogVector().get(0) + "\n");
                        TextAreaLogAppender.getLogVector().remove(0);
                    }
                    jTextPane.setCaretPosition(jTextPane.getDocument().getLength());
                }
            } catch (Exception ex) {
                // TODO: handle exception
            }
        }
    }

}
