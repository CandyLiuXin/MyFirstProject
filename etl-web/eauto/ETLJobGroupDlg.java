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

public class ETLJobGroupDlg extends JDialog
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
  private JComboBox jcb_etlsys;
  private JComboBox jcb_etljob;
  private JCheckBox jcb_autoOnChild;
  private ETLJobGroupDlg dlg;
  private DefaultMutableTreeNode parentNode;
  private Connection dbcon;
  private int mode = 0;
  private ETLMainFrame mainFrame;
  private int retrieveSystemFlag = 0;

  public ETLJobGroupDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "ETL Job Group", true);
    setResizable(false);
    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLJobGroupDlg.1 local1 = new WindowAdapter(this) { private final ETLJobGroupDlg this$0;

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

    this.jBnUpdate.addActionListener(new ActionListener(this) { private final ETLJobGroupDlg this$0;

      public void actionPerformed() { ETLJobGroupDlg.access$100(ETLJobGroupDlg.access$000(this.this$0));
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLJobGroupDlg this$0;

      public void actionPerformed() { ETLJobGroupDlg.access$000(this.this$0).dispose();
      }

    });
    this.jPanelButton.add(this.jBnUpdate);
    this.jPanelButton.add(this.jBnCancel);

    localContainer.add(this.jPanelData, "Center");
    localContainer.add(this.jPanelButton, "South");

    this.jLabel1 = new JLabel("Group Name:");
    this.jLabel1.setBounds(new Rectangle(28, 20, 95, 25));

    this.jLabel2 = new JLabel("Description:");
    this.jLabel2.setBounds(new Rectangle(28, 50, 95, 25));

    this.jLabel3 = new JLabel("Trigger System:");
    this.jLabel3.setBounds(new Rectangle(28, 80, 95, 25));

    this.jLabel4 = new JLabel("Trigger Job:");
    this.jLabel4.setBounds(new Rectangle(28, 110, 95, 25));

    this.jTextField1 = new InputJTextField(InputJTextField.UPPERCASE);
    this.jTextField1.setBounds(new Rectangle(135, 20, 250, 25));
    this.jTextField1.setInputLimited(50);

    this.jTextField2 = new InputJTextField();
    this.jTextField2.setBounds(new Rectangle(135, 50, 300, 25));
    this.jTextField2.setInputLimited(50);

    this.jcb_etlsys = new JComboBox();
    this.jcb_etlsys.setBounds(new Rectangle(135, 80, 70, 25));

    this.jcb_etlsys.addActionListener(new ActionListener(this) { private final ETLJobGroupDlg this$0;

      public void actionPerformed() { if (ETLJobGroupDlg.access$200(this.this$0) == 1)
          return;

        JComboBox localJComboBox = (JComboBox)paramActionEvent.getSource();
        String str = (String)localJComboBox.getSelectedItem();
        ETLJobGroupDlg.access$300(this.this$0).removeAllItems();
        ETLUtilityInterface.getETLJob(ETLJobGroupDlg.access$400(this.this$0), ETLJobGroupDlg.access$500(this.this$0).getDBName(), str, ETLJobGroupDlg.access$300(this.this$0));
      }

    });
    this.jcb_etljob = new JComboBox();
    this.jcb_etljob.setBounds(new Rectangle(135, 110, 300, 25));

    this.jcb_autoOnChild = new JCheckBox("Auto turn on group child job after done");
    this.jcb_autoOnChild.setBounds(new Rectangle(135, 140, 260, 25));

    this.jcb_autoOnChild.setSelected(true);

    this.jPanelData.add(this.jLabel1);
    this.jPanelData.add(this.jLabel2);
    this.jPanelData.add(this.jLabel3);
    this.jPanelData.add(this.jLabel4);

    this.jPanelData.add(this.jTextField1);
    this.jPanelData.add(this.jTextField2);
    this.jPanelData.add(this.jcb_etlsys);
    this.jPanelData.add(this.jcb_etljob);
    this.jPanelData.add(this.jcb_autoOnChild);

    setSize(new Dimension(480, 250));

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

    this.retrieveSystemFlag = 1;
    ETLUtilityInterface.getETLSystem(paramConnection, this.mainFrame.getDBName(), this.jcb_etlsys);
    this.retrieveSystemFlag = 0;

    this.jcb_etlsys.setSelectedIndex(0);

    show();
  }

  public void modify(Connection paramConnection, DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString)
  {
    this.dbcon = paramConnection;
    this.parentNode = paramDefaultMutableTreeNode;

    this.jTextField1.setEditable(false);

    this.mode = 2;

    this.retrieveSystemFlag = 1;
    ETLUtilityInterface.getETLSystem(paramConnection, this.mainFrame.getDBName(), this.jcb_etlsys);
    this.retrieveSystemFlag = 0;

    this.jcb_etlsys.setSelectedIndex(0);

    selectData(paramString);

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
    String str3 = (String)this.jcb_etlsys.getSelectedItem();
    String str4 = (String)this.jcb_etljob.getSelectedItem();
    String str5 = "N";

    if (this.jcb_autoOnChild.isSelected()) {
      str5 = "Y";
    }

    String str6 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      int i = localStatement.executeUpdate("INSERT INTO " + str6 + "etl_job_group" + "       (groupname, etl_system, etl_job," + "        autoonchild, description)" + "   VALUES ('" + str1 + "', '" + str3 + "'," + "           '" + str4 + "', '" + str5 + "'," + "           '" + str2 + "')");

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str7 = "Could not insert a new job group into database\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str7, "Error", 0);

      return;
    }

    this.mainFrame.getTreeView().addJobGroup(this.parentNode, str1);

    dispose();
  }

  private void updateData()
  {
    String str1 = this.jTextField1.getText().trim();
    String str2 = this.jTextField2.getText().trim();
    String str3 = (String)this.jcb_etlsys.getSelectedItem();
    String str4 = (String)this.jcb_etljob.getSelectedItem();
    String str5 = "N";

    if (this.jcb_autoOnChild.isSelected()) {
      str5 = "Y";
    }

    String str6 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      String str7 = "UPDATE " + str6 + "etl_job_group SET" + "    etl_system = '" + str3 + "'," + "    etl_job = '" + str4 + "'," + "    autoonchild = '" + str5 + "'," + "    description = '" + str2 + "'" + "   WHERE groupname = '" + str1 + "'";

      int i = localStatement.executeUpdate(str7);

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str8 = "Could not update job group record" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str8, "Error", 0);

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
      ResultSet localResultSet = localStatement.executeQuery("SELECT etl_system, etl_job, autoonchild, description   FROM " + str1 + "etl_job_group" + "   WHERE groupname = '" + paramString + "'");

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
          str5 = "";

        this.jTextField1.setText(paramString);
        this.jTextField2.setText(str5);

        this.jcb_etlsys.setSelectedItem(str2);
        this.jcb_etljob.setSelectedItem(str3);

        if (str4.equals("N"))
          this.jcb_autoOnChild.setSelected(false);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      str3 = "Could not select job group record from database" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);

      return;
    }
  }

  static ETLJobGroupDlg access$000(ETLJobGroupDlg paramETLJobGroupDlg)
  {
    return paramETLJobGroupDlg.dlg; } 
  static void access$100(ETLJobGroupDlg paramETLJobGroupDlg) { paramETLJobGroupDlg.update(); } 
  static int access$200(ETLJobGroupDlg paramETLJobGroupDlg) { return paramETLJobGroupDlg.retrieveSystemFlag; } 
  static JComboBox access$300(ETLJobGroupDlg paramETLJobGroupDlg) { return paramETLJobGroupDlg.jcb_etljob; } 
  static Connection access$400(ETLJobGroupDlg paramETLJobGroupDlg) { return paramETLJobGroupDlg.dbcon; } 
  static ETLMainFrame access$500(ETLJobGroupDlg paramETLJobGroupDlg) { return paramETLJobGroupDlg.mainFrame;
  }
}