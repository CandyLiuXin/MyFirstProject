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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;

public class ETLJobRelatedJobDlg extends JDialog
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
  private JRadioButton jrb_sameday;
  private JRadioButton jrb_priorday;
  private ETLJobRelatedJobDlg dlg;
  private DefaultMutableTreeNode parentNode;
  private Connection dbcon;
  private int mode = 0;
  private String etlsys;
  private String etljob;
  private String etlsys1;
  private String etljob1;
  private ETLMainFrame mainFrame;
  private int retrieveSystemFlag = 0;

  public ETLJobRelatedJobDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "ETL Job Related Job", true);
    setResizable(false);
    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLJobRelatedJobDlg.1 local1 = new WindowAdapter(this) { private final ETLJobRelatedJobDlg this$0;

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

    this.jBnUpdate.addActionListener(new ActionListener(this) { private final ETLJobRelatedJobDlg this$0;

      public void actionPerformed() { ETLJobRelatedJobDlg.access$100(ETLJobRelatedJobDlg.access$000(this.this$0));
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLJobRelatedJobDlg this$0;

      public void actionPerformed() { ETLJobRelatedJobDlg.access$000(this.this$0).dispose();
      }

    });
    this.jPanelButton.add(this.jBnUpdate);
    this.jPanelButton.add(this.jBnCancel);

    localContainer.add(this.jPanelData, "Center");
    localContainer.add(this.jPanelButton, "South");

    this.jLabel1 = new JLabel("Related System");
    this.jLabel1.setBounds(new Rectangle(20, 20, 125, 25));

    this.jLabel2 = new JLabel("Related Job");
    this.jLabel2.setBounds(new Rectangle(20, 50, 125, 25));

    this.jLabel3 = new JLabel("Description");
    this.jLabel3.setBounds(new Rectangle(20, 80, 125, 25));

    ButtonGroup localButtonGroup = new ButtonGroup();
    this.jrb_sameday = new JRadioButton("Check same date at Data Calendar");
    this.jrb_sameday.setBounds(new Rectangle(23, 110, 300, 25));
    this.jrb_priorday = new JRadioButton("Check prior date at Data Calendar");
    this.jrb_priorday.setBounds(new Rectangle(23, 140, 300, 25));

    localButtonGroup.add(this.jrb_sameday);
    localButtonGroup.add(this.jrb_priorday);

    this.jcb_etlsys = new JComboBox();
    this.jcb_etlsys.setBounds(new Rectangle(155, 20, 70, 25));

    this.jcb_etlsys.addActionListener(new ActionListener(this) { private final ETLJobRelatedJobDlg this$0;

      public void actionPerformed() { if (ETLJobRelatedJobDlg.access$200(this.this$0) == 1)
          return;

        JComboBox localJComboBox = (JComboBox)paramActionEvent.getSource();
        String str = (String)localJComboBox.getSelectedItem();
        ETLJobRelatedJobDlg.access$300(this.this$0).removeAllItems();
        ETLUtilityInterface.getETLJob(ETLJobRelatedJobDlg.access$400(this.this$0), ETLJobRelatedJobDlg.access$500(this.this$0).getDBName(), str, ETLJobRelatedJobDlg.access$600(this.this$0), ETLJobRelatedJobDlg.access$700(this.this$0), ETLJobRelatedJobDlg.access$300(this.this$0));
      }

    });
    this.jcb_etljob = new JComboBox();
    this.jcb_etljob.setBounds(new Rectangle(155, 50, 300, 25));

    this.jTextField3 = new InputJTextField();
    this.jTextField3.setBounds(new Rectangle(155, 80, 300, 25));
    this.jTextField3.setInputLimited(50);

    this.jPanelData.add(this.jLabel1);
    this.jPanelData.add(this.jLabel2);
    this.jPanelData.add(this.jLabel3);

    this.jPanelData.add(this.jcb_etlsys);
    this.jPanelData.add(this.jcb_etljob);
    this.jPanelData.add(this.jTextField3);

    this.jPanelData.add(this.jrb_sameday);
    this.jPanelData.add(this.jrb_priorday);

    this.jrb_sameday.setSelected(true);

    setSize(new Dimension(480, 250));

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

    getInfo(paramString1, paramString2, paramString3, paramString4);

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
    String str4;
    String str1 = (String)this.jcb_etlsys.getSelectedItem();
    String str2 = (String)this.jcb_etljob.getSelectedItem();
    String str3 = this.jTextField3.getText().trim();

    if (this.jrb_sameday.isSelected())
      str4 = "0";
    else
      str4 = "1";

    if (isSameJob(this.etlsys, this.etljob, str1, str2) == 1)
    {
      JOptionPane.showMessageDialog(this, "Can not set the same job as related job!", "Error", 0);

      return;
    }

    if (isJobExist(str1, str2) == 0)
    {
      JOptionPane.showMessageDialog(this, "The related job does not exist at database!", "Error", 0);

      return;
    }

    String str5 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      String str6 = "INSERT INTO " + str5 + "ETL_RelatedJob" + "            (ETL_System, ETL_Job, " + "            RelatedSystem, RelatedJob, CheckMode, Description)" + "   VALUES ( '" + this.etlsys + "', '" + this.etljob + "', '" + str1 + "', '" + str2 + "', '" + str4 + "', '" + str3 + "')";

      int i = localStatement.executeUpdate(str6);

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str8 = "Could not insert the new related job record\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str8, "Error", 0);

      return;
    }

    if (this.mode == 3)
    {
      String str7 = "[" + str1 + "] " + str2;
      this.mainFrame.getTreeView().addRelatedJob(this.parentNode, str7);
    }

    dispose();
  }

  private void updateData()
  {
    String str4;
    String str1 = (String)this.jcb_etlsys.getSelectedItem();
    String str2 = (String)this.jcb_etljob.getSelectedItem();
    String str3 = this.jTextField3.getText().trim();

    if (this.jrb_sameday.isSelected())
      str4 = "0";
    else
      str4 = "1";

    if (isJobExist(str1, str2) == 0)
    {
      JOptionPane.showMessageDialog(this, "The down stream job does not exist at database!", "Error", 0);

      return;
    }

    String str5 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      String str6 = "UPDATE " + str5 + "ETL_RelatedJob SET" + "       RelatedSystem = '" + str1 + "'," + "       RelatedJob = '" + str2 + "'," + "       CheckMode = '" + str4 + "'," + "       Description = '" + str3 + "'" + "   WHERE ETL_System = '" + this.etlsys + "'" + "     AND ETL_Job = '" + this.etljob + "'" + "     AND RelatedSystem = '" + this.etlsys1 + "'" + "     AND RelatedJob = '" + this.etljob1 + "'";

      int i = localStatement.executeUpdate(str6);

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str7 = "Could not update the related job record\n" + localSQLException.getMessage();

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
      String str2 = "SELECT count(*) FROM " + str1 + "etl_job" + "   WHERE etl_system = '" + paramString1 + "'" + "     AND etl_job = '" + paramString2 + "'";

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

  private void getInfo(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    String str1;
    String str2;
    String str3 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      String str4 = "SELECT CheckMode, Description FROM " + str3 + "ETL_RelatedJob" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'" + "     AND RelatedSystem = '" + paramString3 + "'" + "     AND RelatedJob = '" + paramString4 + "'";

      ResultSet localResultSet = localStatement.executeQuery(str4);
      localResultSet.next();
      str1 = localResultSet.getString(1);
      str2 = localResultSet.getString(2);

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str5 = "Could not retrieve the related job record\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str5, "Error", 0);

      return;
    }

    if (str1.equals("0"))
      this.jrb_sameday.setSelected(true);
    else
      this.jrb_priorday.setSelected(true);

    this.jTextField3.setText(str2);
  }

  private int isSameJob(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if ((paramString1.equals(paramString3)) && (paramString2.equals(paramString4)))
      return 1;

    return 0;
  }

  static ETLJobRelatedJobDlg access$000(ETLJobRelatedJobDlg paramETLJobRelatedJobDlg)
  {
    return paramETLJobRelatedJobDlg.dlg; } 
  static void access$100(ETLJobRelatedJobDlg paramETLJobRelatedJobDlg) { paramETLJobRelatedJobDlg.update(); } 
  static int access$200(ETLJobRelatedJobDlg paramETLJobRelatedJobDlg) { return paramETLJobRelatedJobDlg.retrieveSystemFlag; } 
  static JComboBox access$300(ETLJobRelatedJobDlg paramETLJobRelatedJobDlg) { return paramETLJobRelatedJobDlg.jcb_etljob; } 
  static Connection access$400(ETLJobRelatedJobDlg paramETLJobRelatedJobDlg) { return paramETLJobRelatedJobDlg.dbcon; } 
  static ETLMainFrame access$500(ETLJobRelatedJobDlg paramETLJobRelatedJobDlg) { return paramETLJobRelatedJobDlg.mainFrame; } 
  static String access$600(ETLJobRelatedJobDlg paramETLJobRelatedJobDlg) { return paramETLJobRelatedJobDlg.etlsys; } 
  static String access$700(ETLJobRelatedJobDlg paramETLJobRelatedJobDlg) { return paramETLJobRelatedJobDlg.etljob;
  }
}