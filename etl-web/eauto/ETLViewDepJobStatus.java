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

public class ETLViewDepJobStatus extends JDialog
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
  private ETLViewDepJobStatus.TableModel tableModel;

  public ETLViewDepJobStatus(JFrame paramJFrame)
  {
    super(paramJFrame, "All Dependent Job Status", true);

    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLViewDepJobStatus.1 local1 = new WindowAdapter(this) { private final ETLViewDepJobStatus this$0;

      public void windowClosing() { this.this$0.dispose();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(new BorderLayout());

    String[] arrayOfString = { "Level", "System", "Job Name", "Current Status", "TX Date" };

    this.tableModel = new ETLViewDepJobStatus.TableModel(this);

    this.infoTable = new JTable(this.tableModel);

    for (int i = 0; i < arrayOfString.length; ++i)
    {
      this.tableModel.addColumn(arrayOfString[i]);
    }

    this.infoTable.getColumn("Level").setPreferredWidth(50);
    this.infoTable.getColumn("System").setPreferredWidth(60);
    this.infoTable.getColumn("Job Name").setPreferredWidth(250);
    this.infoTable.getColumn("Current Status").setPreferredWidth(120);
    this.infoTable.getColumn("TX Date").setPreferredWidth(80);

    this.infoTable.setAutoResizeMode(0);
    this.infoTable.setCellEditor(null);

    this.jPanelButton = new JPanel(new FlowLayout());
    this.jPanelInfo = new JPanel(new BorderLayout());

    this.jBnRefresh = new JButton("Refresh");
    this.jBnRefresh.setMnemonic(82);
    this.jBnClose = new JButton("Close");
    this.jBnClose.setMnemonic(67);

    this.jBnClose.addActionListener(new ActionListener(this) { private final ETLViewDepJobStatus this$0;

      public void actionPerformed() { this.this$0.dispose();
      }

    });
    this.jBnRefresh.addActionListener(new ActionListener(this) { private final ETLViewDepJobStatus this$0;

      public void actionPerformed() { ETLViewDepJobStatus.access$000(this.this$0);

        ETLViewDepJobStatus.access$100(this.this$0);
        ETLViewDepJobStatus.access$200(this.this$0, 2);
      }

    });
    this.jPanelButton.add(this.jBnRefresh);
    this.jPanelButton.add(this.jBnClose);

    this.jScrollPane = new JScrollPane();
    this.jScrollPane.setViewportView(this.infoTable);

    this.jPanelInfo.add(this.jScrollPane, "Center");
    this.jPanelInfo.setPreferredSize(new Dimension(580, 300));

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

  private void refreshStatus()
  {
    Statement localStatement;
    int i = 0;
    int j = 1;

    String str1 = this.mainFrame.getDBName();
    try
    {
      localStatement = this.mainFrame.getConnection().createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Unable to create statement object", "Error", 0);

      return;
    }
    try
    {
      String str2 = "SELECT A.Dependency_System, A.Dependency_Job, B.Last_JobStatus, B.Last_TXDate   FROM " + str1 + "ETL_Job_Dependency A, " + str1 + "ETL_Job B" + "   WHERE A.ETL_System = '" + this.lastETLSys + "'" + "     AND A.ETL_Job = '" + this.lastETLJob + "'" + "     AND A.Dependency_System = B.ETL_System" + "     AND A.Dependency_Job = B.ETL_Job" + "   ORDER BY 1, 2";

      ResultSet localResultSet = localStatement.executeQuery(str2);

      while (localResultSet.next())
      {
        Vector localVector = new Vector();

        localVector.add(String.valueOf(j));

        String str3 = localResultSet.getString(1);
        localVector.add(str3);

        String str4 = localResultSet.getString(2);
        localVector.add(str4);

        String str5 = localResultSet.getString(3);
        if (localResultSet.wasNull())
          str5 = "Ready";

        localVector.add(str5);

        java.sql.Date localDate = localResultSet.getDate(4);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(localDate.toString());

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

  private int insertChildStatus(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    Statement localStatement;
    int i = 0;

    String str1 = this.mainFrame.getDBName();
    try
    {
      localStatement = this.mainFrame.getConnection().createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Unable to create statement object", "Error", 0);

      return 0;
    }
    try
    {
      String str2 = "SELECT A.Dependency_System, A.Dependency_Job, B.Last_JobStatus, B.Last_TXDate   FROM " + str1 + "ETL_Job_Dependency A, " + str1 + "ETL_Job B" + "   WHERE A.ETL_System = '" + paramString1 + "'" + "     AND A.ETL_Job = '" + paramString2 + "'" + "     AND A.Dependency_System = B.ETL_System" + "     AND A.Dependency_Job = B.ETL_Job" + "   ORDER BY 1, 2";

      ResultSet localResultSet = localStatement.executeQuery(str2);

      while (localResultSet.next())
      {
        Vector localVector = new Vector();

        localVector.add(String.valueOf(paramInt1));

        String str3 = localResultSet.getString(1);
        localVector.add(str3);

        String str4 = localResultSet.getString(2);
        localVector.add(str4);

        String str5 = localResultSet.getString(3);
        if (localResultSet.wasNull())
          str5 = "Ready";

        localVector.add(str5);

        java.sql.Date localDate = localResultSet.getDate(4);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(localDate.toString());

        if (isUpperLevelJob(str3, str4, paramInt1) == 0) {
          this.tableModel.insertRow(paramInt2++, localVector);
          ++i;
        }
      }
      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Could not retrieve data from database\n" + localSQLException2.getMessage(), "Error", 0);

      return 0;
    }
    catch (Exception localException)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Unknown error occured", "Error", 0);

      return 0;
    }

    return i;
  }

  private void retrieveNextLevel(int paramInt)
  {
    int i = this.tableModel.getRowCount();
    int j = 0;
    int k = 0;

    for (int l = i - 1; l >= 0; --l)
    {
      j = Integer.parseInt((String)this.tableModel.getValueAt(l, 0));

      if (j == paramInt - 1) {
        String str1 = (String)this.tableModel.getValueAt(l, 1);
        String str2 = (String)this.tableModel.getValueAt(l, 2);

        if (insertChildStatus(str1, str2, paramInt, l + 1) > 0)
          k = 1;
      }
    }

    if (k == 0)
      return;

    retrieveNextLevel(paramInt + 1);
  }

  private int isUpperLevelJob(String paramString1, String paramString2, int paramInt)
  {
    int i = this.tableModel.getRowCount();
    int j = 0;

    for (int k = 0; k < i; ++k)
    {
      j = Integer.parseInt((String)this.tableModel.getValueAt(k, 0));
      String str1 = (String)this.tableModel.getValueAt(k, 1);
      String str2 = (String)this.tableModel.getValueAt(k, 2);

      if ((str1.equals(paramString1)) && (str2.equals(paramString2)) && (j < paramInt))
      {
        return 1;
      }
    }

    return 0;
  }

  public void retrieveStatus(String paramString1, String paramString2)
  {
    this.lastETLSys = paramString1;
    this.lastETLJob = paramString2;

    refreshStatus();
    retrieveNextLevel(2);

    show();
  }

  static void access$000(ETLViewDepJobStatus paramETLViewDepJobStatus)
  {
    paramETLViewDepJobStatus.deleteLog(); } 
  static void access$100(ETLViewDepJobStatus paramETLViewDepJobStatus) { paramETLViewDepJobStatus.refreshStatus(); } 
  static void access$200(ETLViewDepJobStatus paramETLViewDepJobStatus, int paramInt) { paramETLViewDepJobStatus.retrieveNextLevel(paramInt);
  }

  class TableModel extends DefaultTableModel
  {
    private final ETLViewDepJobStatus this$0;

    public TableModel()
    {
      this.this$0 = paramETLViewDepJobStatus;
    }

    public boolean isCellEditable(, int paramInt2) {
      return false;
    }
  }
}