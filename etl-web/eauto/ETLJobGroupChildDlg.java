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
import java.util.EventObject;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;

public class ETLJobGroupChildDlg extends JDialog
{
  private JPanel jPanelButton;
  private JPanel jPanelData;
  private JButton jBnUpdate;
  private JButton jBnCancel;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JLabel jLabel3;
  private InputJTextField jTextField3;
  private JComboBox jcb_etlsys;
  private JComboBox jcb_etljob;
  private JCheckBox jcb_disabled;
  private JCheckBox jcb_autoTurnOn;
  private ETLJobGroupChildDlg dlg;
  private DefaultMutableTreeNode parentNode;
  private Connection dbcon;
  private int mode = 0;
  private ETLMainFrame mainFrame;
  private String groupName;
  private int retrieveSystemFlag = 0;

  public ETLJobGroupChildDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "ETL Job Group Child", true);
    setResizable(false);
    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLJobGroupChildDlg.1 local1 = new WindowAdapter(this) { private final ETLJobGroupChildDlg this$0;

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

    this.jBnUpdate.addActionListener(new ActionListener(this) { private final ETLJobGroupChildDlg this$0;

      public void actionPerformed() { ETLJobGroupChildDlg.access$100(ETLJobGroupChildDlg.access$000(this.this$0));
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLJobGroupChildDlg this$0;

      public void actionPerformed() { ETLJobGroupChildDlg.access$000(this.this$0).dispose();
      }

    });
    this.jPanelButton.add(this.jBnUpdate);
    this.jPanelButton.add(this.jBnCancel);

    localContainer.add(this.jPanelData, "Center");
    localContainer.add(this.jPanelButton, "South");

    this.jLabel1 = new JLabel("Child ETL System:");
    this.jLabel1.setBounds(new Rectangle(25, 20, 110, 25));

    this.jLabel2 = new JLabel("Child ETL Job:");
    this.jLabel2.setBounds(new Rectangle(25, 50, 110, 25));

    this.jLabel3 = new JLabel("Description:");
    this.jLabel3.setBounds(new Rectangle(25, 80, 110, 25));

    this.jcb_etlsys = new JComboBox();
    this.jcb_etlsys.setBounds(new Rectangle(140, 20, 70, 25));

    this.jcb_etlsys.addActionListener(new ActionListener(this) { private final ETLJobGroupChildDlg this$0;

      public void actionPerformed() { if (ETLJobGroupChildDlg.access$200(this.this$0) == 1)
          return;

        JComboBox localJComboBox = (JComboBox)paramActionEvent.getSource();
        String str = (String)localJComboBox.getSelectedItem();
        ETLJobGroupChildDlg.access$300(this.this$0).removeAllItems();
        ETLUtilityInterface.getETLJob(ETLJobGroupChildDlg.access$400(this.this$0), ETLJobGroupChildDlg.access$500(this.this$0).getDBName(), str, ETLJobGroupChildDlg.access$300(this.this$0));
      }

    });
    this.jcb_etljob = new JComboBox();
    this.jcb_etljob.setBounds(new Rectangle(140, 50, 250, 25));

    this.jTextField3 = new InputJTextField();
    this.jTextField3.setBounds(new Rectangle(140, 80, 300, 25));
    this.jTextField3.setInputLimited(50);

    this.jcb_autoTurnOn = new JCheckBox(" Auto turn on this child job ");
    this.jcb_autoTurnOn.setBounds(new Rectangle(140, 110, 300, 25));

    this.jcb_disabled = new JCheckBox(" Disable this child job checking condition ");
    this.jcb_disabled.setBounds(new Rectangle(140, 140, 300, 25));

    this.jPanelData.add(this.jLabel1);
    this.jPanelData.add(this.jLabel2);
    this.jPanelData.add(this.jLabel3);

    this.jPanelData.add(this.jcb_etlsys);
    this.jPanelData.add(this.jcb_etljob);
    this.jPanelData.add(this.jTextField3);
    this.jPanelData.add(this.jcb_autoTurnOn);
    this.jPanelData.add(this.jcb_disabled);

    setSize(new Dimension(480, 240));

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
    this.groupName = paramString;

    this.mode = 1;

    this.retrieveSystemFlag = 1;
    ETLUtilityInterface.getETLSystem(paramConnection, this.mainFrame.getDBName(), this.jcb_etlsys);
    this.retrieveSystemFlag = 0;

    this.jcb_etlsys.setSelectedIndex(0);

    show();
  }

  public void modify(Connection paramConnection, DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString1, String paramString2, String paramString3)
  {
    this.dbcon = paramConnection;
    this.parentNode = paramDefaultMutableTreeNode;
    this.groupName = paramString1;

    this.retrieveSystemFlag = 1;
    ETLUtilityInterface.getETLSystem(paramConnection, this.mainFrame.getDBName(), this.jcb_etlsys);
    this.retrieveSystemFlag = 0;

    selectData(paramString1, paramString2, paramString3);

    this.jcb_etlsys.setEnabled(false);
    this.jcb_etljob.setEnabled(false);

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
    String str1 = (String)this.jcb_etlsys.getSelectedItem();
    String str2 = (String)this.jcb_etljob.getSelectedItem();
    String str3 = this.jTextField3.getText().trim();
    String str4 = "1";
    String str5 = "N";

    if (isJobExist(str1, str2) == 0)
    {
      JOptionPane.showMessageDialog(this, "The child job does not exist at database!", "Error", 0);

      return;
    }

    String str6 = this.mainFrame.getDBName();
    if (this.jcb_disabled.isSelected())
      str4 = "0";
    else
      str4 = "1";

    if (this.jcb_autoTurnOn.isSelected())
      str5 = "Y";
    else
      str5 = "N";
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      String str7 = "INSERT INTO " + str6 + "ETL_Job_GroupChild" + "       (GroupName, ETL_System, ETL_Job," + "        Description, Enable, TurnOnFlag)" + "   VALUES ('" + this.groupName + "', '" + str1 + "'," + "           '" + str2 + "', '" + str3 + "'," + "           '" + str4 + "', '" + str5 + "')";

      int i = localStatement.executeUpdate(str7);

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str8 = "Could not insert a new child job into group\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str8, "Error", 0);

      return;
    }

    this.mainFrame.getTreeView().addJobGroupChild(this.parentNode, str1, str2, str4);

    dispose();
  }

  private void updateData()
  {
    String str1 = (String)this.jcb_etlsys.getSelectedItem();
    String str2 = (String)this.jcb_etljob.getSelectedItem();
    String str3 = this.jTextField3.getText().trim();
    String str4 = "1";
    String str5 = "N";

    String str6 = this.mainFrame.getDBName();
    if (this.jcb_disabled.isSelected())
      str4 = "0";
    else
      str4 = "1";

    if (this.jcb_autoTurnOn.isSelected())
      str5 = "Y";
    else
      str5 = "N";
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      int i = localStatement.executeUpdate("UPDATE " + str6 + "ETL_Job_GroupChild SET" + "    Description = '" + str3 + "'," + "    Enable = '" + str4 + "'," + "    TurnOnFlag = '" + str5 + "'" + "   WHERE GroupName = '" + this.groupName + "'" + "     AND ETL_System = '" + str1 + "'" + "     AND ETL_Job = '" + str2 + "'");

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str7 = "Could not update a group child job record" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str7, "Error", 0);

      return;
    }

    dispose();
  }

  private void selectData(String paramString1, String paramString2, String paramString3)
  {
    String str3;
    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      ResultSet localResultSet = localStatement.executeQuery("SELECT Description, Enable, TurnOnFlag   FROM " + str1 + "ETL_Job_GroupChild" + "   WHERE GroupName = '" + paramString1 + "'" + "     AND ETL_System = '" + paramString2 + "'" + "     AND ETL_Job = '" + paramString3 + "'");

      if (localResultSet.next())
      {
        String str2 = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str2 = "";

        str3 = localResultSet.getString(2);
        if (localResultSet.wasNull())
          str3 = "1";

        String str4 = localResultSet.getString(3);
        if (localResultSet.wasNull())
          str4 = "N";

        this.jcb_etlsys.setSelectedItem(paramString2);
        this.jcb_etljob.setSelectedItem(paramString3);

        this.jTextField3.setText(str2);

        if (str3.equals("0"))
          this.jcb_disabled.setSelected(true);

        if (str4.equals("Y"))
          this.jcb_autoTurnOn.setSelected(true);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      str3 = "Could not select group child job record from database" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);

      return;
    }
  }

  private int isJobExist(String paramString1, String paramString2)
  {
    int i;
    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      String str2 = "SELECT count(*) FROM " + str1 + "ETL_Job" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      ResultSet localResultSet = localStatement.executeQuery(str2);
      localResultSet.next();
      i = localResultSet.getInt(1);

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      return 0;
    }

    if (i == 1)
      return 1;

    return 0;
  }

  private int isJobInGroup(String paramString1, String paramString2)
  {
    int i;
    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      String str2 = "SELECT count(*) FROM " + str1 + "ETL_Job_GroupChild" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      ResultSet localResultSet = localStatement.executeQuery(str2);
      localResultSet.next();
      i = localResultSet.getInt(1);

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      return 0;
    }

    if (i == 1)
      return 1;

    return 0;
  }

  public int getEnableStatus()
  {
    if (this.jcb_disabled.isSelected())
      return 0;

    return 1;
  }

  static ETLJobGroupChildDlg access$000(ETLJobGroupChildDlg paramETLJobGroupChildDlg)
  {
    return paramETLJobGroupChildDlg.dlg; } 
  static void access$100(ETLJobGroupChildDlg paramETLJobGroupChildDlg) { paramETLJobGroupChildDlg.update(); } 
  static int access$200(ETLJobGroupChildDlg paramETLJobGroupChildDlg) { return paramETLJobGroupChildDlg.retrieveSystemFlag; } 
  static JComboBox access$300(ETLJobGroupChildDlg paramETLJobGroupChildDlg) { return paramETLJobGroupChildDlg.jcb_etljob; } 
  static Connection access$400(ETLJobGroupChildDlg paramETLJobGroupChildDlg) { return paramETLJobGroupChildDlg.dbcon; } 
  static ETLMainFrame access$500(ETLJobGroupChildDlg paramETLJobGroupChildDlg) { return paramETLJobGroupChildDlg.mainFrame;
  }
}