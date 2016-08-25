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
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;

public class ETLServerDlg extends JDialog
{
  private JPanel jPanelButton;
  private JPanel jPanelData;
  private JButton jBnUpdate;
  private JButton jBnCancel;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JLabel jLabel3;
  private JLabel jLabel4;
  private InputJTextField jTextField1;
  private InputJTextField jTextField2;
  private InputJTextField jTextField3;
  private InputJTextField jTextField4;
  private ETLServerDlg dlg;
  private DefaultMutableTreeNode parentNode;
  private Connection dbcon;
  private int mode = 0;
  private ETLMainFrame mainFrame;

  public ETLServerDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "ETL Server Info", true);
    setResizable(false);
    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLServerDlg.1 local1 = new WindowAdapter(this) { private final ETLServerDlg this$0;

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

    this.jBnUpdate.addActionListener(new ActionListener(this) { private final ETLServerDlg this$0;

      public void actionPerformed() { ETLServerDlg.access$100(ETLServerDlg.access$000(this.this$0));
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLServerDlg this$0;

      public void actionPerformed() { ETLServerDlg.access$000(this.this$0).dispose();
      }

    });
    this.jPanelButton.add(this.jBnUpdate);
    this.jPanelButton.add(this.jBnCancel);

    localContainer.add(this.jPanelData, "Center");
    localContainer.add(this.jPanelButton, "South");

    this.jLabel1 = new JLabel("Server Name:");
    this.jLabel1.setBounds(new Rectangle(20, 20, 80, 25));

    this.jLabel2 = new JLabel("Description:");
    this.jLabel2.setBounds(new Rectangle(20, 50, 80, 25));

    this.jLabel3 = new JLabel("IP Address:");
    this.jLabel3.setBounds(new Rectangle(20, 80, 80, 25));

    this.jLabel4 = new JLabel("Agent Port:");
    this.jLabel4.setBounds(new Rectangle(20, 110, 80, 25));

    this.jTextField1 = new InputJTextField(InputJTextField.UPPERCASE);
    this.jTextField1.setBounds(new Rectangle(110, 20, 100, 25));
    this.jTextField1.setInputLimited(10);

    this.jTextField2 = new InputJTextField();
    this.jTextField2.setBounds(new Rectangle(110, 50, 240, 25));
    this.jTextField2.setInputLimited(50);

    this.jTextField3 = new InputJTextField();
    this.jTextField3.setBounds(new Rectangle(110, 80, 120, 25));
    this.jTextField3.setInputLimited(15);

    this.jTextField4 = new InputJTextField(InputJTextField.NUMBERONLY);
    this.jTextField4.setBounds(new Rectangle(110, 110, 50, 25));
    this.jTextField4.setInputLimited(5);

    this.jPanelData.add(this.jLabel1);
    this.jPanelData.add(this.jLabel2);
    this.jPanelData.add(this.jLabel3);
    this.jPanelData.add(this.jLabel4);

    this.jPanelData.add(this.jTextField1);
    this.jPanelData.add(this.jTextField2);
    this.jPanelData.add(this.jTextField3);
    this.jPanelData.add(this.jTextField4);

    setSize(new Dimension(400, 210));

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
    this.jTextField4.setText("6346");

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
    String str3;
    String str1 = this.jTextField3.getText().trim();
    String str2 = this.jTextField4.getText().trim();

    if (str1.equals("")) {
      str3 = "Please input the IP address\n";

      JOptionPane.showMessageDialog(this, str3, "ETL Server", 0);

      this.jTextField3.requestFocus();
      return;
    }

    if (str2.equals("")) {
      str3 = "Please input the agent port\n";

      JOptionPane.showMessageDialog(this, str3, "ETL Server", 0);

      this.jTextField4.requestFocus();
      return;
    }

    if (this.mode == 1)
      insertData();
    else
      updateData();
  }

  private void insertData()
  {
    String str1 = this.jTextField1.getText().trim();
    String str2 = this.jTextField2.getText().trim();
    String str3 = this.jTextField3.getText().trim();
    String str4 = this.jTextField4.getText().trim();

    String str5 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      int i = localStatement.executeUpdate("INSERT INTO " + str5 + "ETL_Server" + "       (ETL_Server, Description, IPAddress, AgentPort)" + "   VALUES ( '" + str1 + "', '" + str2 + "'," + "            '" + str3 + "'," + str4 + ")");

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str6 = "Could not insert a new ETL server into database\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str6, "Error", 0);

      return;
    }

    this.mainFrame.getTreeView().addETLServer(this.parentNode, str1);

    dispose();
  }

  private void updateData()
  {
    String str1 = this.jTextField1.getText().trim();
    String str2 = this.jTextField2.getText().trim();
    String str3 = this.jTextField3.getText().trim();
    String str4 = this.jTextField4.getText().trim();

    String str5 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      int i = localStatement.executeUpdate("UPDATE " + str5 + "ETL_Server SET" + "    Description = '" + str2 + "'," + "    IPAddress = '" + str3 + "'," + "    AgentPort = " + str4 + "   WHERE ETL_Server = '" + str1 + "'");

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str6 = "Could not update ETL server record\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str6, "Error", 0);

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
      ResultSet localResultSet = localStatement.executeQuery("SELECT Description, IPaddress, AgentPort   FROM " + str1 + "ETL_Server" + "   WHERE ETL_Server = '" + paramString + "'");

      if (localResultSet.next())
      {
        String str2 = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str2 = "";

        str3 = localResultSet.getString(2);
        if (localResultSet.wasNull())
          str3 = "";

        int i = localResultSet.getInt(3);
        if (localResultSet.wasNull())
          i = 0;

        this.jTextField1.setText(paramString);
        this.jTextField2.setText(str2);
        this.jTextField3.setText(str3);
        this.jTextField4.setText(String.valueOf(i));
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      str3 = "Could not select ETL server record from database\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);

      return;
    }
  }

  static ETLServerDlg access$000(ETLServerDlg paramETLServerDlg)
  {
    return paramETLServerDlg.dlg; } 
  static void access$100(ETLServerDlg paramETLServerDlg) { paramETLServerDlg.update();
  }
}