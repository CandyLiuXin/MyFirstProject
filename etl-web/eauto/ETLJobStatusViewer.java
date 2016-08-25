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

public class ETLJobStatusViewer extends JDialog
{
  private JTable infoTable;
  private JPanel jPanelInfo;
  private JPanel jPanelButton;
  private JButton jBnClose;
  private JButton jBnRefresh;
  private JScrollPane jScrollPane;
  private ETLJobStatusViewer dlg;
  private ETLMainFrame mainFrame;
  private String lastETLSys = "";
  private String lastETLJob = "";
  private ETLJobStatusViewer.TableModel tableModel;

  public ETLJobStatusViewer(JFrame paramJFrame)
  {
    super(paramJFrame, "View Job Status Log", true);

    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLJobStatusViewer.1 local1 = new WindowAdapter(this) { private final ETLJobStatusViewer this$0;

      public void windowClosing() { this.this$0.dispose();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(new BorderLayout());

    String[] arrayOfString = { "SessionID", "TX Date", "Start Time", "End Time", "Job Status", "File Count", "ExpectedRecord" };

    this.tableModel = new ETLJobStatusViewer.TableModel(this);

    this.infoTable = new JTable(this.tableModel);

    for (int i = 0; i < arrayOfString.length; ++i)
    {
      this.tableModel.addColumn(arrayOfString[i]);
    }

    this.infoTable.getColumn("SessionID").setPreferredWidth(80);
    this.infoTable.getColumn("TX Date").setPreferredWidth(80);
    this.infoTable.getColumn("Start Time").setPreferredWidth(120);
    this.infoTable.getColumn("End Time").setPreferredWidth(120);
    this.infoTable.getColumn("Job Status").setPreferredWidth(100);
    this.infoTable.getColumn("File Count").setPreferredWidth(80);
    this.infoTable.getColumn("ExpectedRecord").setPreferredWidth(140);

    this.infoTable.setAutoResizeMode(0);
    this.infoTable.setCellEditor(null);

    this.jPanelButton = new JPanel(new FlowLayout());
    this.jPanelInfo = new JPanel(new BorderLayout());

    this.jBnRefresh = new JButton("Refresh");
    this.jBnRefresh.setMnemonic(82);
    this.jBnClose = new JButton("Close");
    this.jBnClose.setMnemonic(67);

    this.jBnClose.addActionListener(new ActionListener(this) { private final ETLJobStatusViewer this$0;

      public void actionPerformed() { ETLJobStatusViewer.access$000(this.this$0).dispose();
      }

    });
    this.jBnRefresh.addActionListener(new ActionListener(this) { private final ETLJobStatusViewer this$0;

      public void actionPerformed() { ETLJobStatusViewer.access$100(ETLJobStatusViewer.access$000(this.this$0));
      }

    });
    this.jPanelButton.add(this.jBnRefresh);
    this.jPanelButton.add(this.jBnClose);

    this.jScrollPane = new JScrollPane();
    this.jScrollPane.setViewportView(this.infoTable);

    this.jPanelInfo.add(this.jScrollPane, "Center");
    this.jPanelInfo.setPreferredSize(new Dimension(700, 400));

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
      String str2 = "SELECT JobSessionID, TXDate, StartTime, EndTime, JobStatus,       FileCnt, ExpectedRecord   FROM " + str1 + "ETL_Job_Status" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'" + "   ORDER BY JobSessionID DESC, TXDate DESC";

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

        int l = localResultSet.getInt(7);
        localVector.add(String.valueOf(l));

        this.tableModel.addRow(localVector);
      }
      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Could not retrieve data from ETL_Job_Status", "Error", 0);

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

  static ETLJobStatusViewer access$000(ETLJobStatusViewer paramETLJobStatusViewer)
  {
    return paramETLJobStatusViewer.dlg; } 
  static void access$100(ETLJobStatusViewer paramETLJobStatusViewer) { paramETLJobStatusViewer.refreshLog();
  }

  class TableModel extends DefaultTableModel
  {
    private final ETLJobStatusViewer this$0;

    public TableModel()
    {
      this.this$0 = paramETLJobStatusViewer;
    }

    public boolean isCellEditable(, int paramInt2) {
      return false;
    }
  }
}