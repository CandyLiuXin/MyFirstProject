import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;

public class ETLGroupMemberDlg extends JDialog
{
  private JPanel jPanelButton;
  private JPanel jPanelData;
  private JButton jBnUpdate;
  private JButton jBnCancel;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private InputJTextField jTextField1;
  private JComboBox jcb_user;
  private ETLGroupMemberDlg dlg;
  private DefaultMutableTreeNode parentNode;
  private Connection dbcon;
  private int mode = 0;
  private ETLMainFrame mainFrame;
  private String lastUser;

  public ETLGroupMemberDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "User Group Member", true);
    setResizable(false);
    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLGroupMemberDlg.1 local1 = new WindowAdapter(this) { private final ETLGroupMemberDlg this$0;

      public void windowClosing() { this.this$0.dispose();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(new BorderLayout());

    this.jPanelButton = new JPanel();
    this.jPanelData = new JPanel();
    this.jPanelButton.setLayout(new FlowLayout());
    this.jPanelData.setLayout(null);

    this.jBnUpdate = new JButton("Update");
    this.jBnUpdate.setMnemonic(85);
    this.jBnCancel = new JButton("Cancel");
    this.jBnCancel.setMnemonic(67);

    this.jBnUpdate.addActionListener(new ActionListener(this) { private final ETLGroupMemberDlg this$0;

      public void actionPerformed() { ETLGroupMemberDlg.access$100(ETLGroupMemberDlg.access$000(this.this$0));
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLGroupMemberDlg this$0;

      public void actionPerformed() { ETLGroupMemberDlg.access$000(this.this$0).dispose();
      }

    });
    this.jPanelButton.add(this.jBnUpdate);
    this.jPanelButton.add(this.jBnCancel);

    localContainer.add(this.jPanelData, "Center");
    localContainer.add(this.jPanelButton, "South");

    this.jLabel1 = new JLabel("User Group Name:");
    this.jLabel1.setBounds(new Rectangle(28, 20, 120, 25));

    this.jLabel2 = new JLabel("Member User Name:");
    this.jLabel2.setBounds(new Rectangle(28, 50, 120, 25));

    this.jTextField1 = new InputJTextField(InputJTextField.UPPERCASE);
    this.jTextField1.setBounds(new Rectangle(155, 20, 100, 25));
    this.jTextField1.setInputLimited(15);

    this.jcb_user = new JComboBox();
    this.jcb_user.setBounds(new Rectangle(155, 50, 240, 25));

    this.jPanelData.add(this.jLabel1);
    this.jPanelData.add(this.jLabel2);

    this.jPanelData.add(this.jTextField1);
    this.jPanelData.add(this.jcb_user);

    setSize(new Dimension(420, 180));

    Dimension localDimension1 = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension localDimension2 = getSize();

    if (localDimension2.height > localDimension1.height)
      localDimension2.height = localDimension1.height;

    if (localDimension2.width > localDimension1.width)
      localDimension2.width = localDimension1.width;

    setLocation((localDimension1.width - localDimension2.width) / 2, (localDimension1.height - localDimension2.height) / 2);
  }

  public void add(Connection paramConnection, DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString)
  {
    this.dbcon = paramConnection;
    this.parentNode = paramDefaultMutableTreeNode;

    ETLUtilityInterface.getNotifyUser(paramConnection, this.mainFrame.getDBName(), this.jcb_user);

    this.jTextField1.setEditable(false);

    this.jTextField1.setText(paramString);
    this.mode = 1;

    this.jcb_user.requestFocus();
    show();
  }

  public void modify(Connection paramConnection, DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString1, String paramString2)
  {
    this.dbcon = paramConnection;
    this.parentNode = paramDefaultMutableTreeNode;

    ETLUtilityInterface.getNotifyUser(paramConnection, this.mainFrame.getDBName(), this.jcb_user);

    this.jTextField1.setEditable(false);

    this.jTextField1.setText(paramString2);
    this.jcb_user.setSelectedItem(paramString1);
    this.mode = 2;

    this.lastUser = paramString1;

    this.jcb_user.setEnabled(false);

    show();
  }

  private void update()
  {
    if (this.mode == 1)
      insertData();
    else
      updateData();
  }

  private void insertData()
  {
    String str1 = this.jTextField1.getText().trim();
    String str2 = (String)this.jcb_user.getSelectedItem();

    String str3 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      int i = localStatement.executeUpdate("INSERT INTO " + str3 + "ETL_GroupMember" + "       (GroupName, UserName)" + "   VALUES ('" + str1 + "', '" + str2 + "')");

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str4 = "Could not insert a new group member into database\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str4, "Error", 0);

      return;
    }

    this.mainFrame.getTreeView().addNotifyGroupMember(this.parentNode, str2);

    dispose();
  }

  private void updateData()
  {
    String str1 = this.jTextField1.getText().trim();
    String str2 = (String)this.jcb_user.getSelectedItem();

    String str3 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      int i = localStatement.executeUpdate("UPDATE " + str3 + "ETL_GroupMember SET" + "   UserName = '" + str2 + "'" + "   WHERE GroupName = '" + str1 + "'" + "     AND UserName = '" + this.lastUser + "'");

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str4 = "Could not update group member record" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str4, "Error", 0);

      return;
    }

    dispose();
  }

  static ETLGroupMemberDlg access$000(ETLGroupMemberDlg paramETLGroupMemberDlg)
  {
    return paramETLGroupMemberDlg.dlg; } 
  static void access$100(ETLGroupMemberDlg paramETLGroupMemberDlg) { paramETLGroupMemberDlg.update();
  }
}