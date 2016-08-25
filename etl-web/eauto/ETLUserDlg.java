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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;

public class ETLUserDlg extends JDialog
{
  private JPanel jPanelButton;
  private JPanel jPanelData;
  private JButton jBnUpdate;
  private JButton jBnCancel;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JLabel jLabel3;
  private JLabel jLabel4;
  private JLabel jLabel5;
  private InputJTextField jTextField1;
  private InputJTextField jTextField2;
  private InputJTextField jTextField3;
  private InputJTextField jTextField4;
  private JRadioButton jrb_enabled;
  private JRadioButton jrb_disabled;
  private ETLUserDlg dlg;
  private DefaultMutableTreeNode parentNode;
  private Connection dbcon;
  private int mode = 0;
  private ETLMainFrame mainFrame;

  public ETLUserDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "Notification User", true);
    setResizable(false);
    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLUserDlg.1 local1 = new WindowAdapter(this) { private final ETLUserDlg this$0;

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

    this.jBnUpdate.addActionListener(new ActionListener(this) { private final ETLUserDlg this$0;

      public void actionPerformed() { ETLUserDlg.access$100(ETLUserDlg.access$000(this.this$0));
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLUserDlg this$0;

      public void actionPerformed() { ETLUserDlg.access$000(this.this$0).dispose();
      }

    });
    this.jPanelButton.add(this.jBnUpdate);
    this.jPanelButton.add(this.jBnCancel);

    localContainer.add(this.jPanelData, "Center");
    localContainer.add(this.jPanelButton, "South");

    this.jLabel1 = new JLabel("User Name:");
    this.jLabel1.setBounds(new Rectangle(28, 20, 95, 25));

    this.jLabel2 = new JLabel("Description:");
    this.jLabel2.setBounds(new Rectangle(28, 50, 95, 25));

    this.jLabel3 = new JLabel("Email Address:");
    this.jLabel3.setBounds(new Rectangle(28, 80, 95, 25));

    this.jLabel4 = new JLabel("Mobile Phone:");
    this.jLabel4.setBounds(new Rectangle(28, 110, 95, 25));

    this.jLabel5 = new JLabel("Status:");
    this.jLabel5.setBounds(new Rectangle(28, 140, 95, 25));

    this.jTextField1 = new InputJTextField(InputJTextField.UPPERCASE);
    this.jTextField1.setBounds(new Rectangle(135, 20, 100, 25));
    this.jTextField1.setInputLimited(15);

    this.jTextField2 = new InputJTextField();
    this.jTextField2.setBounds(new Rectangle(135, 50, 240, 25));
    this.jTextField2.setInputLimited(50);

    this.jTextField3 = new InputJTextField();
    this.jTextField3.setBounds(new Rectangle(135, 80, 240, 25));
    this.jTextField3.setInputLimited(50);

    this.jTextField4 = new InputJTextField();
    this.jTextField4.setBounds(new Rectangle(135, 110, 150, 25));
    this.jTextField4.setInputLimited(20);

    JPanel localJPanel = new JPanel();
    localJPanel.setLayout(null);
    ButtonGroup localButtonGroup = new ButtonGroup();
    this.jrb_enabled = new JRadioButton("Enabled");
    this.jrb_enabled.setBounds(new Rectangle(0, 0, 70, 25));
    this.jrb_disabled = new JRadioButton("Disabled");
    this.jrb_disabled.setBounds(new Rectangle(80, 0, 80, 25));

    localJPanel.add(this.jrb_enabled);
    localJPanel.add(this.jrb_disabled);
    localJPanel.setBounds(new Rectangle(135, 140, 200, 25));

    localButtonGroup.add(this.jrb_enabled);
    localButtonGroup.add(this.jrb_disabled);

    this.jrb_enabled.setSelected(true);

    this.jPanelData.add(this.jLabel1);
    this.jPanelData.add(this.jLabel2);
    this.jPanelData.add(this.jLabel3);
    this.jPanelData.add(this.jLabel4);
    this.jPanelData.add(this.jLabel5);

    this.jPanelData.add(this.jTextField1);
    this.jPanelData.add(this.jTextField2);
    this.jPanelData.add(this.jTextField3);
    this.jPanelData.add(this.jTextField4);
    this.jPanelData.add(localJPanel);

    setSize(new Dimension(420, 250));

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
    String str5;
    String str1 = this.jTextField1.getText().trim();
    String str2 = this.jTextField2.getText().trim();
    String str3 = this.jTextField3.getText().trim();
    String str4 = this.jTextField4.getText().trim();

    if (this.jrb_enabled.isSelected())
      str5 = "1";
    else {
      str5 = "0";
    }

    String str6 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      int i = localStatement.executeUpdate("INSERT INTO " + str6 + "ETL_User" + "       (Username, Description, Email," + "        Mobile, Status)" + "   VALUES ('" + str1 + "', '" + str2 + "'," + "           '" + str3 + "', '" + str4 + "'," + "           '" + str5 + "')");

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str7 = "Could not insert a new notify user into database\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str7, "Error", 0);

      return;
    }

    this.mainFrame.getTreeView().addNotifyUser(this.parentNode, str1);

    dispose();
  }

  private void updateData()
  {
    String str5;
    String str1 = this.jTextField1.getText().trim();
    String str2 = this.jTextField2.getText().trim();
    String str3 = this.jTextField3.getText().trim();
    String str4 = this.jTextField4.getText().trim();

    if (this.jrb_enabled.isSelected())
      str5 = "1";
    else {
      str5 = "0";
    }

    String str6 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      int i = localStatement.executeUpdate("UPDATE " + str6 + "ETL_User SET" + "    Description = '" + str2 + "'," + "    Email = '" + str3 + "'," + "    Mobile = '" + str4 + "'," + "    Status = '" + str5 + "'" + "   WHERE Username = '" + str1 + "'");

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str7 = "Could not update user record" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str7, "Error", 0);

      return;
    }

    dispose();
  }

  private void selectData(String paramString)
  {
    String str3;
    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      ResultSet localResultSet = localStatement.executeQuery("SELECT Description, Email, Mobile, Status   FROM " + str1 + "ETL_User" + "   WHERE Username = '" + paramString + "'");

      if (localResultSet.next())
      {
        String str2 = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str2 = "";

        str3 = localResultSet.getString(2);
        if (localResultSet.wasNull())
          str3 = "";

        String str4 = localResultSet.getString(3);
        if (localResultSet.wasNull())
          str4 = "Y";

        String str5 = localResultSet.getString(4);
        if (localResultSet.wasNull())
          str5 = "0";

        this.jTextField1.setText(paramString);
        this.jTextField2.setText(str2);
        this.jTextField3.setText(str3);
        this.jTextField4.setText(str4);

        if (str5.equals("1"))
          this.jrb_enabled.setSelected(true);
        else
          this.jrb_disabled.setSelected(true);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      str3 = "Could not select user record from database" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);

      return;
    }
  }

  static ETLUserDlg access$000(ETLUserDlg paramETLUserDlg)
  {
    return paramETLUserDlg.dlg; } 
  static void access$100(ETLUserDlg paramETLUserDlg) { paramETLUserDlg.update();
  }
}