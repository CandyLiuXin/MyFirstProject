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

public class ETLViewChildJobStatus extends JDialog
{
  private JTable infoTable;
  private JPanel jPanelInfo;
  private JPanel jPanelButton;
  private JButton jBnClose;
  private JButton jBnRefresh;
  private JScrollPane jScrollPane;
  private ETLMainFrame mainFrame;
  private String lastGroup = "";
  private ETLViewChildJobStatus.TableModel tableModel;

  public ETLViewChildJobStatus(JFrame paramJFrame)
  {
    super(paramJFrame, "All Child Job Status", true);

    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLViewChildJobStatus.1 local1 = new WindowAdapter(this) { private final ETLViewChildJobStatus this$0;

      public void windowClosing() { this.this$0.dispose();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(new BorderLayout());

    String[] arrayOfString = { "System", "Job Name", "Flag", "TX Date" };

    this.tableModel = new ETLViewChildJobStatus.TableModel(this);

    this.infoTable = new JTable(this.tableModel);

    for (int i = 0; i < arrayOfString.length; ++i)
    {
      this.tableModel.addColumn(arrayOfString[i]);
    }

    this.infoTable.getColumn("System").setPreferredWidth(60);
    this.infoTable.getColumn("Job Name").setPreferredWidth(250);
    this.infoTable.getColumn("Flag").setPreferredWidth(50);
    this.infoTable.getColumn("TX Date").setPreferredWidth(80);

    this.infoTable.setAutoResizeMode(0);
    this.infoTable.setCellEditor(null);

    this.jPanelButton = new JPanel(new FlowLayout());
    this.jPanelInfo = new JPanel(new BorderLayout());

    this.jBnRefresh = new JButton("Refresh");
    this.jBnRefresh.setMnemonic(82);
    this.jBnClose = new JButton("Close");
    this.jBnClose.setMnemonic(67);

    this.jBnClose.addActionListener(new ActionListener(this) { private final ETLViewChildJobStatus this$0;

      public void actionPerformed() { this.this$0.dispose();
      }

    });
    this.jBnRefresh.addActionListener(new ActionListener(this) { private final ETLViewChildJobStatus this$0;

      public void actionPerformed() { ETLViewChildJobStatus.access$000(this.this$0);
      }

    });
    this.jPanelButton.add(this.jBnRefresh);
    this.jPanelButton.add(this.jBnClose);

    this.jScrollPane = new JScrollPane();
    this.jScrollPane.setViewportView(this.infoTable);

    this.jPanelInfo.add(this.jScrollPane, "Center");
    this.jPanelInfo.setPreferredSize(new Dimension(480, 300));

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
      String str2 = "SELECT ETL_System, ETL_Job, CheckFlag, TXDate   FROM " + str1 + "ETL_Job_GroupChild" + "   WHERE GroupName = '" + this.lastGroup + "'" + "   ORDER BY 1, 2";

      ResultSet localResultSet = localStatement.executeQuery(str2);

      while (localResultSet.next())
      {
        Vector localVector = new Vector();

        String str3 = localResultSet.getString(1);
        localVector.add(str3);

        String str4 = localResultSet.getString(2);
        localVector.add(str4);

        String str5 = localResultSet.getString(3);
        if (localResultSet.wasNull())
          str5 = "N";

        localVector.add(str5);

        String str6 = localResultSet.getString(4);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(str6);

        this.tableModel.addRow(localVector);
      }
      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Could not retrieve data from database", "Error", 0);

      return;
    }
    catch (Exception localException)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Unknown error occured", "Error", 0);

      return;
    }
  }

  public void retrieveStatus(String paramString)
  {
    this.lastGroup = paramString;

    refreshStatus();
    show();
  }

  static void access$000(ETLViewChildJobStatus paramETLViewChildJobStatus)
  {
    paramETLViewChildJobStatus.refreshStatus();
  }

  class TableModel extends DefaultTableModel
  {
    private final ETLViewChildJobStatus this$0;

    public TableModel()
    {
      this.this$0 = paramETLViewChildJobStatus;
    }

    public boolean isCellEditable(, int paramInt2) {
      return false;
    }
  }
}