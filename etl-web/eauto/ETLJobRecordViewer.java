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

public class ETLJobRecordViewer extends JDialog
{
  private JTable infoTable;
  private JPanel jPanelInfo;
  private JPanel jPanelButton;
  private JButton jBnClose;
  private JButton jBnRefresh;
  private JScrollPane jScrollPane;
  private ETLJobRecordViewer dlg;
  private ETLMainFrame mainFrame;
  private String lastETLSys = "";
  private String lastETLJob = "";
  private ETLJobRecordViewer.TableModel tableModel;

  public ETLJobRecordViewer(JFrame paramJFrame)
  {
    super(paramJFrame, "Job Record Log", true);

    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLJobRecordViewer.1 local1 = new WindowAdapter(this) { private final ETLJobRecordViewer this$0;

      public void windowClosing() { this.this$0.dispose();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(new BorderLayout());

    String[] arrayOfString = { "SessionID", "Record Time", "Inserted", "Updated", "Deleted", "Duplicate", "Output", "ET", "UV", "ER1", "ER2" };

    this.tableModel = new ETLJobRecordViewer.TableModel(this);

    this.infoTable = new JTable(this.tableModel);

    for (int i = 0; i < arrayOfString.length; ++i)
    {
      this.tableModel.addColumn(arrayOfString[i]);
    }

    this.infoTable.getColumn("SessionID").setPreferredWidth(80);
    this.infoTable.getColumn("Record Time").setPreferredWidth(140);
    this.infoTable.getColumn("Inserted").setPreferredWidth(80);
    this.infoTable.getColumn("Updated").setPreferredWidth(80);
    this.infoTable.getColumn("Deleted").setPreferredWidth(80);
    this.infoTable.getColumn("Duplicate").setPreferredWidth(80);
    this.infoTable.getColumn("Output").setPreferredWidth(80);
    this.infoTable.getColumn("ET").setPreferredWidth(80);
    this.infoTable.getColumn("UV").setPreferredWidth(80);
    this.infoTable.getColumn("ER1").setPreferredWidth(80);
    this.infoTable.getColumn("ER2").setPreferredWidth(80);

    this.infoTable.setAutoResizeMode(0);
    this.infoTable.setCellEditor(null);

    this.jPanelButton = new JPanel(new FlowLayout());
    this.jPanelInfo = new JPanel(new BorderLayout());

    this.jBnRefresh = new JButton("Refresh");
    this.jBnRefresh.setMnemonic(82);
    this.jBnClose = new JButton("Close");
    this.jBnClose.setMnemonic(67);

    this.jBnClose.addActionListener(new ActionListener(this) { private final ETLJobRecordViewer this$0;

      public void actionPerformed() { ETLJobRecordViewer.access$000(this.this$0).dispose();
      }

    });
    this.jBnRefresh.addActionListener(new ActionListener(this) { private final ETLJobRecordViewer this$0;

      public void actionPerformed() { ETLJobRecordViewer.access$100(ETLJobRecordViewer.access$000(this.this$0));
      }

    });
    this.jPanelButton.add(this.jBnRefresh);
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
      String str2 = "SELECT JobSessionID, RecordTime, InsertedRecord, UpdatedRecord,       DeletedRecord, DuplicateRecord, OutputRecord,       ETRecord, UVRecord, ER1Record, ER2Record   FROM " + str1 + "ETL_Record_Log" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'" + "   ORDER BY JobSessionID DESC";

      ResultSet localResultSet = localStatement.executeQuery(str2);

      while (localResultSet.next())
      {
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        String str10;
        String str11;
        String str12;
        Vector localVector = new Vector();

        int j = localResultSet.getInt(1);
        if (localResultSet.wasNull())
          j = 0;

        localVector.add(String.valueOf(j));

        String str3 = localResultSet.getString(2);
        if (localResultSet.wasNull())
          str3 = "";

        localVector.add(str3);

        int k = localResultSet.getInt(3);
        if (localResultSet.wasNull())
          str4 = "0";
        else
          str4 = String.valueOf(k);

        k = localResultSet.getInt(4);
        if (localResultSet.wasNull())
          str5 = "0";
        else
          str5 = String.valueOf(k);

        k = localResultSet.getInt(5);
        if (localResultSet.wasNull())
          str6 = "0";
        else
          str6 = String.valueOf(k);

        k = localResultSet.getInt(6);
        if (localResultSet.wasNull())
          str7 = "0";
        else
          str7 = String.valueOf(k);

        k = localResultSet.getInt(7);
        if (localResultSet.wasNull())
          str8 = "0";
        else
          str8 = String.valueOf(k);

        k = localResultSet.getInt(8);
        if (localResultSet.wasNull())
          str9 = "0";
        else
          str9 = String.valueOf(k);

        k = localResultSet.getInt(9);
        if (localResultSet.wasNull())
          str10 = "0";
        else
          str10 = String.valueOf(k);

        k = localResultSet.getInt(10);
        if (localResultSet.wasNull())
          str11 = "0";
        else
          str11 = String.valueOf(k);

        k = localResultSet.getInt(11);
        if (localResultSet.wasNull())
          str12 = "0";
        else
          str12 = String.valueOf(k);

        localVector.add(str4);
        localVector.add(str5);
        localVector.add(str6);
        localVector.add(str7);
        localVector.add(str8);
        localVector.add(str9);
        localVector.add(str10);
        localVector.add(str11);
        localVector.add(str12);

        this.tableModel.addRow(localVector);
      }
      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Could not retrieve data from ETL_Record_Log\n" + localSQLException2.getMessage(), "Error", 0);

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

  static ETLJobRecordViewer access$000(ETLJobRecordViewer paramETLJobRecordViewer)
  {
    return paramETLJobRecordViewer.dlg; } 
  static void access$100(ETLJobRecordViewer paramETLJobRecordViewer) { paramETLJobRecordViewer.refreshLog();
  }

  class TableModel extends DefaultTableModel
  {
    private final ETLJobRecordViewer this$0;

    public TableModel()
    {
      this.this$0 = paramETLJobRecordViewer;
    }

    public boolean isCellEditable(, int paramInt2) {
      return false;
    }
  }
}