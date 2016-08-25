import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class Frame1 extends JFrame
{
  JPanel contentPane;
  BorderLayout borderLayout1 = new BorderLayout();
  JTree jTree1 = new JTree();
  JButton jButton1 = new JButton();

  public Frame1()
  {
    enableEvents(64L);
    try {
      jbInit();
    }
    catch (Exception localException) {
      localException.printStackTrace();
    }
  }

  private void jbInit() throws Exception
  {
    this.contentPane = ((JPanel)super.getContentPane());
    this.contentPane.setLayout(this.borderLayout1);
    setSize(new Dimension(400, 300));
    setTitle("Frame Title");
    this.jButton1.setText("jButton1");
    this.jButton1.addActionListener(new ActionListener(this) { private final Frame1 this$0;

      public void actionPerformed() { this.this$0.jButton1_actionPerformed(paramActionEvent);
      }

    });
    this.contentPane.add(this.jTree1, "Center");
    this.contentPane.add(this.jButton1, "North");
  }

  protected void processWindowEvent(WindowEvent paramWindowEvent)
  {
    super.processWindowEvent(paramWindowEvent);
    if (paramWindowEvent.getID() == 201)
      System.exit(0);
  }

  void jButton1_actionPerformed(ActionEvent paramActionEvent)
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = null;
    Object localObject = null;
    DefaultMutableTreeNode localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)this.jTree1.getModel().getRoot();

    for (Enumeration localEnumeration = localDefaultMutableTreeNode2.depthFirstEnumeration(); localEnumeration.hasMoreElements(); ) {
      DefaultMutableTreeNode localDefaultMutableTreeNode3 = (DefaultMutableTreeNode)localEnumeration.nextElement();
      if (!(localDefaultMutableTreeNode3.getUserObject().equals("hockey")))
        break label71;
      localObject = localDefaultMutableTreeNode3;
      localDefaultMutableTreeNode1 = (DefaultMutableTreeNode)localDefaultMutableTreeNode3.getParent();
      label71: break;
    }

    if (localDefaultMutableTreeNode1 != null) {
      JOptionPane.showMessageDialog(this, new TreePath(localDefaultMutableTreeNode1.getPath()));
      this.jTree1.expandPath(new TreePath(localDefaultMutableTreeNode1.getPath()));
      this.jTree1.setSelectionPath(new TreePath(localObject.getPath()));
    }
  }

  public static void main(String[] paramArrayOfString) {
    new Frame1().show();
  }
}