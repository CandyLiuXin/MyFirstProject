import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class ETLViewAllBelongGroup extends JDialog
{
  private JTable infoTable;
  private JPanel jPanelInfo;
  private JPanel jPanelButton;
  private JButton jBnClose;
  private JButton jBnRefresh;
  private JScrollPane jScrollPane;
  private ETLMainFrame mainFrame;
  private String lastETLSys = "";
  private String lastETLJob = "";
  private ETLViewAllBelongGroup.TableModel tableModel;

  public ETLViewAllBelongGroup(JFrame paramJFrame)
  {
    super(paramJFrame, "All Belong Group", true);

    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLViewAllBelongGroup.1 local1 = new WindowAdapter(this) { private final ETLViewAllBelongGroup this$0;

      public void windowClosing() { this.this$0.dispose();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(new BorderLayout());

    String[] arrayOfString = { "Seq", "Belong Group", "Head Sys", "Head Job", "Status", "TxDate" };

    this.tableModel = new ETLViewAllBelongGroup.TableModel(this);

    this.infoTable = new JTable(this.tableModel);

    for (int i = 0; i < arrayOfString.length; ++i)
    {
      this.tableModel.addColumn(arrayOfString[i]);
    }

    this.infoTable.getColumn("Seq").setPreferredWidth(40);
    this.infoTable.getColumn("Belong Group").setPreferredWidth(200);
    this.infoTable.getColumn("Head Sys").setPreferredWidth(60);
    this.infoTable.getColumn("Head Job").setPreferredWidth(200);
    this.infoTable.getColumn("Status").setPreferredWidth(50);
    this.infoTable.getColumn("TxDate").setPreferredWidth(90);

    this.infoTable.setAutoResizeMode(0);
    this.infoTable.setCellEditor(null);

    this.jPanelButton = new JPanel(new FlowLayout());
    this.jPanelInfo = new JPanel(new BorderLayout());

    this.jBnRefresh = new JButton("Refresh");
    this.jBnRefresh.setMnemonic(82);
    this.jBnClose = new JButton("Close");
    this.jBnClose.setMnemonic(67);

    this.jBnClose.addActionListener(new ActionListener(this) { private final ETLViewAllBelongGroup this$0;

      public void actionPerformed() { this.this$0.dispose();
      }

    });
    this.jBnRefresh.addActionListener(new ActionListener(this) { private final ETLViewAllBelongGroup this$0;

      public void actionPerformed() { ETLViewAllBelongGroup.access$000(this.this$0);

        ETLViewAllBelongGroup.access$100(this.this$0, 0);
        ETLViewAllBelongGroup.access$100(this.this$0, 1);
        ETLViewAllBelongGroup.access$200(this.this$0);
      }

    });
    this.jPanelButton.add(this.jBnRefresh);
    this.jPanelButton.add(this.jBnClose);

    this.jScrollPane = new JScrollPane();
    this.jScrollPane.setViewportView(this.infoTable);

    this.jPanelInfo.add(this.jScrollPane, "Center");
    this.jPanelInfo.setPreferredSize(new Dimension(680, 300));

    localContainer.add(this.jPanelInfo, "Center");
    localContainer.add(this.jPanelButton, "South");

    pack();

    Dimension localDimension1 = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension localDimension2 = getSize();

    if (localDimension2.height > localDimension1.height)
      localDimension2.height = localDimension1.height;

    if (localDimension2.width > localDimension1.width)
      localDimension2.width = localDimension1.width;

    setLocation((localDimension1.width - localDimension2.width) / 2, (localDimension1.height - localDimension2.height) / 2);
  }

  private void deleteLog()
  {
    int i = this.tableModel.getRowCount();

    for (int j = i - 1; j >= 0; --j)
    {
      this.tableModel.removeRow(j);
    }
  }

  private void retrieveGroupHeadJob()
  {
    int i = this.tableModel.getRowCount();

    for (int j = 0; j < i; ++j)
    {
      String str = (String)this.tableModel.getValueAt(j, 1);
      getHeadJobStatus(j, str);
    }
  }

  private void refreshStatus(int paramInt)
  {
    Statement localStatement;
    String str1 = "";
    int i = 0;

    String str2 = this.mainFrame.getDBName();
    try
    {
      localStatement = this.mainFrame.getConnection().createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Unable to create statement object", "Error", 0);

      return;
    }

    if (paramInt == 0) {
      str1 = "SELECT A.ETL_System, A.ETL_Job, B.GroupName   FROM " + str2 + "ETL_Job A, " + str2 + "ETL_Job_GroupChild B" + "   WHERE A.ETL_System = '" + this.lastETLSys + "'" + "     AND A.ETL_Job = '" + this.lastETLJob + "'" + "     AND A.ETL_System = B.ETL_System" + "     AND A.ETL_Job = B.ETL_Job" + "   ORDER BY 3";

      i = 1;
    } else if (paramInt == 1) {
      str1 = "SELECT A.Stream_System, A.Stream_Job, B.GroupName   FROM " + str2 + "ETL_Job_Stream A, " + str2 + "ETL_Job_GroupChild B," + "        ETL_Job C" + "   WHERE A.ETL_System = '" + this.lastETLSys + "'" + "     AND A.ETL_Job = '" + this.lastETLJob + "'" + "     AND A.Stream_System = B.ETL_System" + "     AND A.Stream_Job = B.ETL_Job" + "     AND A.Stream_System = C.ETL_System" + "     AND A.Stream_Job = C.ETL_Job" + "     AND C.JobType = 'V'" + "   ORDER BY 3";

      i = 2;
    }
    try
    {
      ResultSet localResultSet = localStatement.executeQuery(str1);

      while (localResultSet.next())
      {
        Vector localVector = new Vector();

        localVector.add(String.valueOf(i++));

        String str3 = localResultSet.getString(1);

        String str4 = localResultSet.getString(2);

        String str5 = localResultSet.getString(3);
        localVector.add(str5);

        this.tableModel.addRow(localVector);
      }
      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Could not retrieve data from database\n" + localSQLException2.getMessage(), "Error", 0);

      return;
    }
    catch (Exception localException)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Unknown error occured", "Error", 0);

      return;
    }
  }

  private int getHeadJobStatus(int paramInt, String paramString)
  {
    Statement localStatement;
    String str1 = "";

    String str2 = this.mainFrame.getDBName();
    try
    {
      localStatement = this.mainFrame.getConnection().createStatement();
    }
    catch (SQLException localSQLException1)
    {
      return -1;
    }

    str1 = "SELECT B.ETL_System, B.ETL_Job, A.Last_JobStatus, A.Last_TxDate   FROM " + str2 + "ETL_Job A, " + str2 + "ETL_Job_Group B" + "   WHERE B.GroupName = '" + paramString + "'" + "     AND B.ETL_Job = A.ETL_Job " + "     AND B.ETL_System = A.ETL_System";
    try
    {
      ResultSet localResultSet = localStatement.executeQuery(str1);

      while (localResultSet.next())
      {
        String str3 = localResultSet.getString(1);
        this.tableModel.setValueAt(str3, paramInt, 2);

        String str4 = localResultSet.getString(2);
        this.tableModel.setValueAt(str4, paramInt, 3);

        String str5 = localResultSet.getString(3);
        this.tableModel.setValueAt(str5, paramInt, 4);

        java.sql.Date localDate = localResultSet.getDate(4);
        if (localResultSet.wasNull())
          this.tableModel.setValueAt("", paramInt, 5);
        else
          this.tableModel.setValueAt(localDate.toString(), paramInt, 5);
      }
      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      return -1;
    }
    catch (Exception localException)
    {
      return -1;
    }

    return 0;
  }

  public void retrieveStatus(String paramString1, String paramString2)
  {
    this.lastETLSys = paramString1;
    this.lastETLJob = paramString2;

    refreshStatus(0);
    refreshStatus(1);
    retrieveGroupHeadJob();

    show();
  }

  static void access$000(ETLViewAllBelongGroup paramETLViewAllBelongGroup)
  {
    paramETLViewAllBelongGroup.deleteLog(); } 
  static void access$100(ETLViewAllBelongGroup paramETLViewAllBelongGroup, int paramInt) { paramETLViewAllBelongGroup.refreshStatus(paramInt); } 
  static void access$200(ETLViewAllBelongGroup paramETLViewAllBelongGroup) { paramETLViewAllBelongGroup.retrieveGroupHeadJob();
  }

  class TableModel extends DefaultTableModel
  {
    private final ETLViewAllBelongGroup this$0;

    public TableModel()
    {
      this.this$0 = paramETLViewAllBelongGroup;
    }

    public boolean isCellEditable(, int paramInt2) {
      return false;
    }
  }
}