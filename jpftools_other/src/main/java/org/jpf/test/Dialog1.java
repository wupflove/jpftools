package org.jpf.test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author 吴平福
 * @version 1.0
 */
public class Dialog1 extends Dialog
{
  Panel panel1 = new Panel();
  BorderLayout borderLayout1 = new BorderLayout();
  public Dialog1(Frame owner, String title, boolean modal)
  {
    super(owner, title, modal);
    try
    {
      enableEvents(AWTEvent.WINDOW_EVENT_MASK);
      jbInit();
      pack();
    } catch (Exception exception)
    {
      exception.printStackTrace();
    }
  }

  public Dialog1()
  {
    this(new Frame(), "Dialog1", false);
  }

  private void jbInit() throws Exception
  {
    panel1.setLayout(borderLayout1);
    panel1.addFocusListener(new Dialog1_panel1_focusAdapter(this));
    add(panel1);
  }

  protected void processWindowEvent(WindowEvent windowEvent)
  {
    if (windowEvent.getID() == WindowEvent.WINDOW_CLOSING)
    {
      dispose();
    }
    super.processWindowEvent(windowEvent);
  }

  public void panel1_focusGained(FocusEvent e)
  {

  }
}

class Dialog1_panel1_focusAdapter extends FocusAdapter
{
  private Dialog1 adaptee;
  Dialog1_panel1_focusAdapter(Dialog1 adaptee)
  {
    this.adaptee = adaptee;
  }

  public void focusGained(FocusEvent e)
  {
    adaptee.panel1_focusGained(e);
  }
}
