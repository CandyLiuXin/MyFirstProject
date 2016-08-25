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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;

public class ETLUserGroupDlg extends JDialog
{
  private JPanel jPanelButton;
  private JPanel jPanelData;
  private JButton jBnUpdate;
  private JButton jBnCancel;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private InputJTextField jTextField1;
  private InputJTextField jTextField2;
  private ETLUserGroupDlg dlg;
  private DefaultMutableTreeNode parentNode;
  private Connection dbcon;
  private int mode = 0;
  private ETLMainFrame mainFrame;

  public ETLUserGroupDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "Notification UserGroup", true);
    setResizable(false);
    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLUserGroupDlg.1 local1 = new WindowAdapter(this) { private final ETLUserGroupDlg this$0;

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

    this.jBnUpdate.addActionListener(new ActionListener(this) { private final ETLUserGroupDlg this$0;

      public void actionPerformed() { ETLUserGroupDlg.access$100(ETLUserGroupDlg.access$000(this.this$0));
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLUserGroupDlg this$0;

      public void actionPerformed() { ETLUserGroupDlg.access$000(this.this$0).dispose();
      }

    });
    this.jPanelButton.add(this.jBnUpdate);
    this.jPanelButton.add(this.jBnCancel);

    localContainer.add(this.jPanelData, "Center");
    localContainer.add(this.jPanelButton, "South");

    this.jLabel1 = new JLabel("User Group Name:");
    this.jLabel1.setBounds(new Rectangle(28, 20, 110, 25));

    this.jLabel2 = new JLabel("Description:");
    this.jLabel2.setBounds(new Rectangle(28, 50, 110, 25));

    this.jTextField1 = new InputJTextField(InputJTextField.UPPERCASE);
    this.jTextField1.setBounds(new Rectangle(145, 20, 100, 25));
    this.jTextField1.setInputLimited(15);

    this.jTextField2 = new InputJTextField();
    this.jTextField2.setBounds(new Rectangle(145, 50, 240, 25));
    this.jTextField2.setInputLimited(50);

    this.jPanelData.add(this.jLabel1);
    this.jPanelData.add(this.jLabel2);

    this.jPanelData.add(this.jTextField1);
    this.jPanelData.add(this.jTextField2);

    setSize(new Dimension(420, 180));

    Dimension localDimension1 = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension localDimension2 = getSize();

    if (localDimension2.height > localDimension1.height)
      localDimension2.height = localDimension1.height;

    if (localDimension2.width > localDimension1.width)
      localDimension2.width = localDimension1.width;

    setLocation((localDimension1.width - localDimension2.width) / 2, (localDimension1.height - localDimension2.height) / 2);
  }

  public void add(Connection paramConnection, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    this.dbcon = paramConnection;
    this.parentNode = paramDefaultMutableTreeNode;

    this.mode = 1;

    show();
  }

  public void modify(Connection paramConnection, DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString)
  {
    this.dbcon = paramConnection;
    this.parentNode = paramDefaultMutableTreeNode;

    selectData(paramString);

    this.jTextField1.setText(paramString);
    this.jTextField1.setEditable(false);

    this.mode = 2;

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
    String str2 = this.jTextField2.getText().trim();

    String str3 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      int i = localStatement.executeUpdate("INSERT INTO " + str3 + "ETL_UserGroup" + "       (GroupName, Description)" + "   VALUES ('" + str1 + "', '" + str2 + "')");

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str4 = "Could not insert a new notify user group into database\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str4, "Error", 0);

      return;
    }

    this.mainFrame.getTreeView().addNotifyGroup(this.parentNode, str1);

    dispose();
  }

  private void updateData()
  {
    String str1 = this.jTextField1.getText().trim();
    String str2 = this.jTextField2.getText().trim();

    String str3 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      int i = localStatement.executeUpdate("UPDATE " + str3 + "ETL_UserGroup SET" + "    Description = '" + str2 + "'" + "   WHERE GroupName = '" + str1 + "'");

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str4 = "Could not update user group record" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str4, "Error", 0);

      return;
    }

    dispose();
  }

  private void selectData(String paramString)
  {
    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      ResultSet localResultSet = localStatement.executeQuery("SELECT Description   FROM " + str1 + "ETL_UserGroup" + "   WHERE GroupName = '" + paramString + "'");

      if (localResultSet.next())
      {
        String str2 = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str2 = "";

        this.jTextField2.setText(str2);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str3 = "Could not select user group record from database" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);

      return;
    }
  }

  static ETLUserGroupDlg access$000(ETLUserGroupDlg paramETLUserGroupDlg)
  {
    return paramETLUserGroupDlg.dlg; } 
  static void access$100(ETLUserGroupDlg paramETLUserGroupDlg) { paramETLUserGroupDlg.update();
  }
}