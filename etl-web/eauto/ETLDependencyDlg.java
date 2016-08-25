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

public class ETLDependencyDlg extends JDialog
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
  private ETLDependencyDlg dlg;
  private DefaultMutableTreeNode parentNode;
  private Connection dbcon;
  private int mode = 0;
  private String etlsys;
  private String etljob;
  private String etlsys1;
  private String etljob1;
  private ETLMainFrame mainFrame;
  private int retrieveSystemFlag = 0;

  public ETLDependencyDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "ETL Job Dependency", true);
    setResizable(false);
    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLDependencyDlg.1 local1 = new WindowAdapter(this) { private final ETLDependencyDlg this$0;

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

    this.jBnUpdate.addActionListener(new ActionListener(this) { private final ETLDependencyDlg this$0;

      public void actionPerformed() { ETLDependencyDlg.access$100(ETLDependencyDlg.access$000(this.this$0));
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLDependencyDlg this$0;

      public void actionPerformed() { ETLDependencyDlg.access$000(this.this$0).dispose();
      }

    });
    this.jPanelButton.add(this.jBnUpdate);
    this.jPanelButton.add(this.jBnCancel);

    localContainer.add(this.jPanelData, "Center");
    localContainer.add(this.jPanelButton, "South");

    this.jLabel1 = new JLabel("Dependency System:");
    this.jLabel1.setBounds(new Rectangle(20, 20, 120, 25));

    this.jLabel2 = new JLabel("Dependency Job:");
    this.jLabel2.setBounds(new Rectangle(20, 50, 120, 25));

    this.jLabel3 = new JLabel("Description:");
    this.jLabel3.setBounds(new Rectangle(20, 80, 120, 25));

    this.jcb_etlsys = new JComboBox();
    this.jcb_etlsys.setBounds(new Rectangle(155, 20, 70, 25));

    this.jcb_etlsys.addActionListener(new ActionListener(this) { private final ETLDependencyDlg this$0;

      public void actionPerformed() { if (ETLDependencyDlg.access$200(this.this$0) == 1)
          return;

        JComboBox localJComboBox = (JComboBox)paramActionEvent.getSource();
        String str = (String)localJComboBox.getSelectedItem();
        ETLDependencyDlg.access$300(this.this$0).removeAllItems();
        ETLUtilityInterface.getETLJob(ETLDependencyDlg.access$400(this.this$0), ETLDependencyDlg.access$500(this.this$0).getDBName(), str, ETLDependencyDlg.access$600(this.this$0), ETLDependencyDlg.access$700(this.this$0), ETLDependencyDlg.access$300(this.this$0));
      }

    });
    this.jcb_etljob = new JComboBox();
    this.jcb_etljob.setBounds(new Rectangle(155, 50, 300, 25));

    this.jTextField3 = new InputJTextField();
    this.jTextField3.setBounds(new Rectangle(155, 80, 300, 25));
    this.jTextField3.setInputLimited(50);

    this.jcb_disabled = new JCheckBox(" Disable the dependency checking ");
    this.jcb_disabled.setBounds(new Rectangle(155, 110, 300, 25));

    this.jPanelData.add(this.jLabel1);
    this.jPanelData.add(this.jLabel2);
    this.jPanelData.add(this.jLabel3);

    this.jPanelData.add(this.jcb_etlsys);
    this.jPanelData.add(this.jcb_etljob);
    this.jPanelData.add(this.jTextField3);
    this.jPanelData.add(this.jcb_disabled);

    setSize(new Dimension(480, 210));

    Dimension localDimension1 = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension localDimension2 = getSize();

    if (localDimension2.height > localDimension1.height)
      localDimension2.height = localDimension1.height;

    if (localDimension2.width > localDimension1.width)
      localDimension2.width = localDimension1.width;

    setLocation((localDimension1.width - localDimension2.width) / 2, (localDimension1.height - localDimension2.height) / 2);
  }

  public void add(Connection paramConnection, DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString1, String paramString2)
  {
    this.dbcon = paramConnection;
    this.parentNode = paramDefaultMutableTreeNode;

    this.mode = 1;

    this.etlsys = paramString1;
    this.etljob = paramString2;

    this.retrieveSystemFlag = 1;
    ETLUtilityInterface.getETLSystem(paramConnection, this.mainFrame.getDBName(), this.jcb_etlsys);
    this.retrieveSystemFlag = 0;

    this.jcb_etlsys.setSelectedIndex(0);

    show();
  }

  public void add1(Connection paramConnection, DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString1, String paramString2)
  {
    this.dbcon = paramConnection;
    this.parentNode = paramDefaultMutableTreeNode;

    this.mode = 3;

    this.etlsys = paramString1;
    this.etljob = paramString2;

    this.retrieveSystemFlag = 1;
    ETLUtilityInterface.getETLSystem(paramConnection, this.mainFrame.getDBName(), this.jcb_etlsys);
    this.retrieveSystemFlag = 0;

    this.jcb_etlsys.setSelectedIndex(0);

    show();
  }

  public void modify(Connection paramConnection, DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.dbcon = paramConnection;
    this.parentNode = paramDefaultMutableTreeNode;

    this.mode = 2;

    this.etlsys = paramString1;
    this.etljob = paramString2;
    this.etlsys1 = paramString3;
    this.etljob1 = paramString4;

    this.jcb_etlsys.addItem(paramString3);
    this.jcb_etljob.removeAllItems();
    this.jcb_etljob.addItem(paramString4);

    this.jcb_etlsys.setEnabled(false);
    this.jcb_etljob.setEnabled(false);

    ETLUtilityInterface.getDependencyJobDesc(paramConnection, this.mainFrame.getDBName(), paramString1, paramString2, paramString3, paramString4, this.jTextField3, this.jcb_disabled);

    show();
  }

  private void update()
  {
    if ((this.mode == 1) || (this.mode == 3))
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

    if (isSameJob(this.etlsys, this.etljob, str1, str2) == 1)
    {
      JOptionPane.showMessageDialog(this, "Can not set the same job as dependent job!", "Error", 0);

      return;
    }

    if (isJobExist(str1, str2) == 0)
    {
      JOptionPane.showMessageDialog(this, "The dependent job does not exist at database!", "Error", 0);

      return;
    }

    if (isDeadLock(this.etlsys, this.etljob, str1, str2) == 1)
    {
      JOptionPane.showMessageDialog(this, "Dead lock dependency occur!", "Error", 0);

      return;
    }

    String str5 = this.mainFrame.getDBName();

    if (this.jcb_disabled.isSelected())
      str4 = "0";
    else
      str4 = "1";
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      String str6 = "INSERT INTO " + str5 + "ETL_Job_Dependency" + "            (ETL_System, ETL_Job, Dependency_System," + "             Dependency_Job, Description, Enable)" + "   VALUES ( '" + this.etlsys + "', '" + this.etljob + "'," + "            '" + str1 + "', '" + str2 + "'," + "            '" + str3 + "', '" + str4 + "')";

      int i = localStatement.executeUpdate(str6);

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str8 = "Could not insert the new dependent job record\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str8, "Error", 0);

      return;
    }

    if (this.mode == 3)
    {
      String str7 = "[" + str1 + "] " + str2;
      this.mainFrame.getTreeView().addDependentJob(this.parentNode, str1, str2, str7, str4);
    }

    dispose();
  }

  private void updateData()
  {
    String str1 = (String)this.jcb_etlsys.getSelectedItem();
    String str2 = (String)this.jcb_etljob.getSelectedItem();
    String str3 = this.jTextField3.getText().trim();
    String str4 = "1";

    if (isJobExist(str1, str2) == 0)
    {
      JOptionPane.showMessageDialog(this, "The dependent job does not exist at database!", "Error", 0);

      return;
    }

    String str5 = this.mainFrame.getDBName();

    if (this.jcb_disabled.isSelected())
      str4 = "0";
    else
      str4 = "1";
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      String str6 = "UPDATE " + str5 + "ETL_Job_Dependency SET " + "       Dependency_System = '" + str1 + "'," + "       Dependency_Job = '" + str2 + "'," + "       Description = '" + str3 + "'," + "       Enable = '" + str4 + "'" + "   WHERE ETL_System = '" + this.etlsys + "'" + "     AND ETL_Job = '" + this.etljob + "'" + "     AND Dependency_System = '" + this.etlsys1 + "'" + "     AND Dependency_Job = '" + this.etljob1 + "'";

      int i = localStatement.executeUpdate(str6);

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str7 = "Could not update the dependent job\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str7, "Error", 0);

      return;
    }

    dispose();
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

  private int isDeadLock(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    int i;
    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      String str2 = "SELECT count(*) FROM " + str1 + "ETL_Job_Dependency" + "   WHERE ETL_System = '" + paramString3 + "'" + "     AND ETL_Job = '" + paramString4 + "'" + "     AND Dependency_System = '" + paramString1 + "'" + "     AND Dependency_Job = '" + paramString2 + "'";

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

  private int isSameJob(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if ((paramString1.equals(paramString3)) && (paramString2.equals(paramString4)))
      return 1;

    return 0;
  }

  public int getEnableStatus()
  {
    if (this.jcb_disabled.isSelected())
      return 0;

    return 1;
  }

  static ETLDependencyDlg access$000(ETLDependencyDlg paramETLDependencyDlg)
  {
    return paramETLDependencyDlg.dlg; } 
  static void access$100(ETLDependencyDlg paramETLDependencyDlg) { paramETLDependencyDlg.update(); } 
  static int access$200(ETLDependencyDlg paramETLDependencyDlg) { return paramETLDependencyDlg.retrieveSystemFlag; } 
  static JComboBox access$300(ETLDependencyDlg paramETLDependencyDlg) { return paramETLDependencyDlg.jcb_etljob; } 
  static Connection access$400(ETLDependencyDlg paramETLDependencyDlg) { return paramETLDependencyDlg.dbcon; } 
  static ETLMainFrame access$500(ETLDependencyDlg paramETLDependencyDlg) { return paramETLDependencyDlg.mainFrame; } 
  static String access$600(ETLDependencyDlg paramETLDependencyDlg) { return paramETLDependencyDlg.etlsys; } 
  static String access$700(ETLDependencyDlg paramETLDependencyDlg) { return paramETLDependencyDlg.etljob;
  }
}