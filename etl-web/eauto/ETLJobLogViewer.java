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

public class ETLJobLogViewer extends JDialog
{
  private JTable infoTable;
  private JPanel jPanelInfo;
  private JPanel jPanelButton;
  private JButton jBnClose;
  private JButton jBnRefresh;
  private JButton jBnViewLogFile;
  private JButton jBnViewScriptFile;
  private JScrollPane jScrollPane;
  private ETLJobLogViewer dlg;
  private ETLMainFrame mainFrame;
  private String lastETLSys = "";
  private String lastETLJob = "";
  private ETLJobLogViewer.TableModel tableModel;

  public ETLJobLogViewer(JFrame paramJFrame)
  {
    super(paramJFrame, "View Job Detail Log", false);

    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLJobLogViewer.1 local1 = new WindowAdapter(this) { private final ETLJobLogViewer this$0;

      public void windowClosing() { this.this$0.dispose();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(new BorderLayout());

    String[] arrayOfString = { "SessionID", "TX Date", "Script File", "Start Time", "End Time", "Return Code" };

    this.tableModel = new ETLJobLogViewer.TableModel(this);

    this.infoTable = new JTable(this.tableModel);

    for (int i = 0; i < arrayOfString.length; ++i)
    {
      this.tableModel.addColumn(arrayOfString[i]);
    }

    this.infoTable.getColumn("SessionID").setPreferredWidth(80);
    this.infoTable.getColumn("TX Date").setPreferredWidth(80);
    this.infoTable.getColumn("Script File").setPreferredWidth(200);
    this.infoTable.getColumn("Start Time").setPreferredWidth(120);
    this.infoTable.getColumn("End Time").setPreferredWidth(120);
    this.infoTable.getColumn("Return Code").setPreferredWidth(80);

    this.infoTable.setAutoResizeMode(0);
    this.infoTable.setCellEditor(null);

    this.jPanelButton = new JPanel(new FlowLayout());
    this.jPanelInfo = new JPanel(new BorderLayout());

    this.jBnRefresh = new JButton("Refresh");
    this.jBnRefresh.setMnemonic(82);
    this.jBnViewLogFile = new JButton("View Log File");
    this.jBnViewLogFile.setMnemonic(76);
    this.jBnViewScriptFile = new JButton("View Script File");
    this.jBnViewScriptFile.setMnemonic(83);
    this.jBnClose = new JButton("Close");
    this.jBnClose.setMnemonic(67);

    this.jBnClose.addActionListener(new ActionListener(this) { private final ETLJobLogViewer this$0;

      public void actionPerformed() { ETLJobLogViewer.access$000(this.this$0).dispose();
      }

    });
    this.jBnRefresh.addActionListener(new ActionListener(this) { private final ETLJobLogViewer this$0;

      public void actionPerformed() { ETLJobLogViewer.access$100(ETLJobLogViewer.access$000(this.this$0));
      }

    });
    this.jBnViewLogFile.addActionListener(new ActionListener(this) { private final ETLJobLogViewer this$0;

      public void actionPerformed() { ETLJobLogViewer.access$200(ETLJobLogViewer.access$000(this.this$0));
      }

    });
    this.jBnViewScriptFile.addActionListener(new ActionListener(this) { private final ETLJobLogViewer this$0;

      public void actionPerformed() { ETLJobLogViewer.access$300(ETLJobLogViewer.access$000(this.this$0));
      }

    });
    this.jPanelButton.add(this.jBnRefresh);
    this.jPanelButton.add(this.jBnViewLogFile);
    this.jPanelButton.add(this.jBnViewScriptFile);
    this.jPanelButton.add(this.jBnClose);

    this.jScrollPane = new JScrollPane();
    this.jScrollPane.setViewportView(this.infoTable);

    this.jPanelInfo.add(this.jScrollPane, "Center");
    this.jPanelInfo.setPreferredSize(new Dimension(700, 200));

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

  public void retrieveLog(String paramString1, String paramString2)
  {
    Statement localStatement;
    int i = 0;

    this.lastETLSys = paramString1;
    this.lastETLJob = paramString2;

    String str1 = this.mainFrame.getDBName();

    deleteLog();
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
      String str2 = "SELECT JobSessionID, TXDate, ScriptFile, StartTime, EndTime, ReturnCode   FROM " + str1 + "ETL_Job_Log" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'" + "   ORDER BY JobSessionID DESC,TXDate DESC, ScriptFile";

      ResultSet localResultSet = localStatement.executeQuery(str2);

      while (localResultSet.next())
      {
        Vector localVector = new Vector();

        int j = localResultSet.getInt(1);
        if (localResultSet.wasNull())
          j = 0;

        localVector.add(String.valueOf(j));

        java.sql.Date localDate = localResultSet.getDate(2);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(localDate.toString());

        String str3 = localResultSet.getString(3);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(str3);

        String str4 = localResultSet.getString(4);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(str4);

        String str5 = localResultSet.getString(5);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(str5);

        int k = localResultSet.getInt(6);
        localVector.add(String.valueOf(k));

        this.tableModel.addRow(localVector);
      }
      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Could not retrieve data from ETL_Job_Log", "Error", 0);

      return;
    }
    catch (Exception localException)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Unknown error occured", "Error", 0);

      return;
    }
  }

  private void deleteLog()
  {
    int i = this.tableModel.getRowCount();

    for (int j = i - 1; j >= 0; --j)
    {
      this.tableModel.removeRow(j);
    }
  }

  private void refreshLog()
  {
    retrieveLog(this.lastETLSys, this.lastETLJob);
  }

  private void viewLogFile()
  {
    int[] arrayOfInt = this.infoTable.getSelectedRows();

    if (arrayOfInt.length == 0)
      return;

    String str1 = ETLAgentInterface.getRunningServer(this.mainFrame.getConnection(), this.mainFrame.getDBName(), this.lastETLSys, this.lastETLJob);

    if (str1.equals("")) {
      str2 = "The running server for this job is not defined, unable to get the log file";

      JOptionPane.showMessageDialog(this, str2, "Error", 0);

      return;
    }

    String str2 = (String)this.tableModel.getValueAt(arrayOfInt[0], 0);
    String str3 = (String)this.tableModel.getValueAt(arrayOfInt[0], 2);

    String str4 = str3 + "." + str2 + ".log";

    String str5 = ETLAgentInterface.getJobRunningDate(this.mainFrame.getConnection(), this.mainFrame.getDBName(), this.lastETLSys, this.lastETLJob, str2);

    ETLAgentInterface localETLAgentInterface = new ETLAgentInterface();
    if (localETLAgentInterface.getServerInfo(this.mainFrame.getConnection(), this.mainFrame.getDBName(), str1) == 0)
    {
      String str6;
      if (localETLAgentInterface.connectToAgent() != 0) {
        str6 = "Unable to connect ETL Agent";

        JOptionPane.showMessageDialog(this, str6, "Error", 0);

        return;
      }

      if (localETLAgentInterface.readHeaderInfo() != 0) {
        localETLAgentInterface.disconnectAgent();

        str6 = "Read header information error\n";

        JOptionPane.showMessageDialog(this, str6, "Error", 0);

        return;
      }

      int i = localETLAgentInterface.sendCmdGetLog(this.lastETLSys, str5, str4);

      if (i != 0) {
        localETLAgentInterface.disconnectAgent();

        localObject = "Unable to get the log file\n";
        JOptionPane.showMessageDialog(this, localObject, "Error", 0);

        return;
      }

      Object localObject = new ETLViewJobLogFileDlg(this.mainFrame);
      ((ETLViewJobLogFileDlg)localObject).setLogFileName(str4);

      localETLAgentInterface.getLogFile(((ETLViewJobLogFileDlg)localObject).getTextArea());

      localETLAgentInterface.disconnectAgent();
      ((Dialog)localObject).show();
    }
  }

  private void viewScriptFile()
  {
    int[] arrayOfInt = this.infoTable.getSelectedRows();

    if (arrayOfInt.length == 0)
      return;

    String str1 = ETLAgentInterface.getRunningServer(this.mainFrame.getConnection(), this.mainFrame.getDBName(), this.lastETLSys, this.lastETLJob);

    if (str1.equals("")) {
      str2 = "The running server for this job is not defined, unable to get the log file";

      JOptionPane.showMessageDialog(this, str2, "Error", 0);

      return;
    }

    String str2 = (String)this.tableModel.getValueAt(arrayOfInt[0], 2);

    ETLAgentInterface localETLAgentInterface = new ETLAgentInterface();
    if (localETLAgentInterface.getServerInfo(this.mainFrame.getConnection(), this.mainFrame.getDBName(), str1) == 0)
    {
      String str3;
      if (localETLAgentInterface.connectToAgent() != 0) {
        str3 = "Unable to connect ETL Agent";

        JOptionPane.showMessageDialog(this, str3, "Error", 0);

        return;
      }

      if (localETLAgentInterface.readHeaderInfo() != 0) {
        localETLAgentInterface.disconnectAgent();

        str3 = "Read header information error\n";

        JOptionPane.showMessageDialog(this, str3, "Error", 0);

        return;
      }

      int i = localETLAgentInterface.sendCmdGetScr(this.lastETLSys, this.lastETLJob, str2);

      if (i != 0) {
        localETLAgentInterface.disconnectAgent();

        localObject = "Unable to get the job script file\n";
        JOptionPane.showMessageDialog(this, localObject, "Error", 0);

        return;
      }

      Object localObject = new ETLEditJobScriptDlg(this.mainFrame);
      ((ETLEditJobScriptDlg)localObject).setScriptFileName(str2);
      ((ETLEditJobScriptDlg)localObject).setServerInfo(localETLAgentInterface.getIP(), localETLAgentInterface.getPort());
      ((ETLEditJobScriptDlg)localObject).setJobInfo(this.lastETLSys, this.lastETLJob);

      localETLAgentInterface.getScriptFile(((ETLEditJobScriptDlg)localObject).getTextArea());

      localETLAgentInterface.disconnectAgent();
      ((Dialog)localObject).show();
    }
  }

  static ETLJobLogViewer access$000(ETLJobLogViewer paramETLJobLogViewer)
  {
    return paramETLJobLogViewer.dlg; } 
  static void access$100(ETLJobLogViewer paramETLJobLogViewer) { paramETLJobLogViewer.refreshLog(); } 
  static void access$200(ETLJobLogViewer paramETLJobLogViewer) { paramETLJobLogViewer.viewLogFile(); } 
  static void access$300(ETLJobLogViewer paramETLJobLogViewer) { paramETLJobLogViewer.viewScriptFile();
  }

  class TableModel extends DefaultTableModel
  {
    private final ETLJobLogViewer this$0;

    public TableModel()
    {
      this.this$0 = paramETLJobLogViewer;
    }

    public boolean isCellEditable(, int paramInt2) {
      return false;
    }
  }
}