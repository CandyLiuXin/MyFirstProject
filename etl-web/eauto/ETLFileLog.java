import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class ETLFileLog extends JPanel
{
  private JTable jTable;
  private JScrollPane jScrollPane;
  private JButton delButton;
  private JPanel bnPanel;
  private ETLMainFrame mainFrame;
  private int lastReadMode = 0;
  private String lastETLSys;
  private String lastETLJob;
  public ETLFileLog.TableModel jTModel = new ETLFileLog.TableModel(this);

  public ETLFileLog(JFrame paramJFrame)
  {
    [Ljava.lang.Object[] arrayOfObject; = new Object[0][];
    String[] arrayOfString = { "SessionID", "Source File", "Size", "Expected Record", "Arrival Time", "Received Time", "Location" };

    this.mainFrame = ((ETLMainFrame)paramJFrame);

    this.jTable = new JTable(this.jTModel);

    for (int i = 0; i < arrayOfString.length; ++i)
    {
      this.jTModel.addColumn(arrayOfString[i]);
    }

    this.jTable.getColumn("SessionID").setPreferredWidth(60);
    this.jTable.getColumn("Source File").setPreferredWidth(140);
    this.jTable.getColumn("Size").setPreferredWidth(70);
    this.jTable.getColumn("Expected Record").setPreferredWidth(120);
    this.jTable.getColumn("Arrival Time").setPreferredWidth(120);
    this.jTable.getColumn("Received Time").setPreferredWidth(120);
    this.jTable.getColumn("Location").setPreferredWidth(250);

    this.jTable.setAutoResizeMode(0);
    this.jTable.setCellEditor(null);

    this.jScrollPane = new JScrollPane();

    this.jScrollPane.setViewportView(this.jTable);

    setLayout(new BorderLayout());

    this.delButton = new JButton("Delete File Log");

    this.delButton.addActionListener(new ActionListener(this) { private final ETLFileLog this$0;

      public void actionPerformed() { this.this$0.delButton_actionPerformed(paramActionEvent);
      }

    });
    this.bnPanel = new JPanel(new FlowLayout());
    this.bnPanel.add(this.delButton);

    add(this.jScrollPane, "Center");
    add(this.bnPanel, "South");
  }

  public int readFileLog(Connection paramConnection, String paramString1, String paramString2)
  {
    Statement localStatement;
    String str3;
    int i = 0;

    this.lastReadMode = 1;
    this.lastETLSys = paramString1;
    this.lastETLJob = paramString2;

    String str1 = this.mainFrame.getDBName();

    deleteFileLog();
    try
    {
      localStatement = paramConnection.createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Unable to create statement object", "Error", 0);

      return -1;
    }
    try
    {
      String str2 = "SELECT JobSessionID, ReceivedFile, FileSize, ExpectedRecord,       ArrivalTime, ReceivedTime, Location   FROM " + str1 + "ETL_Received_File" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'" + "   ORDER BY JobSessionID DESC, ReceivedFile";

      ResultSet localResultSet = localStatement.executeQuery(str2);

      long l = -5468541228159074304L;

      while (localResultSet.next())
      {
        Vector localVector = new Vector();

        int j = localResultSet.getInt(1);
        localVector.add(String.valueOf(j));

        str3 = localResultSet.getString(2);
        localVector.add(str3);

        l = localResultSet.getLong(3);
        localVector.add(String.valueOf(l));

        int k = localResultSet.getInt(4);
        localVector.add(String.valueOf(k));

        String str4 = localResultSet.getString(5);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(str4);

        String str5 = localResultSet.getString(6);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(str5);

        String str6 = localResultSet.getString(7);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(str6);

        this.jTModel.addRow(localVector);
      }
      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      str3 = "Could not retrieve data from received file log\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this.mainFrame, str3, "Error", 0);

      return -2;
    }
    catch (Exception localException)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Unknown error occured", "Error", 0);

      return -3;
    }

    return 0;
  }

  protected void delButton_actionPerformed(ActionEvent paramActionEvent)
  {
    Statement localStatement;
    String str3;
    int[] arrayOfInt = this.jTable.getSelectedRows();

    if (arrayOfInt.length == 0)
      return;

    Connection localConnection = this.mainFrame.getConnection();
    if (localConnection == null)
      return;

    String str1 = this.mainFrame.getDBName();

    int i = JOptionPane.showConfirmDialog(this, "Do you want to delete the selected file log record?", "Delete File Log", 0, 3);

    if (i != 0) {
      return;
    }

    try
    {
      localStatement = localConnection.createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Unable to create statement object", "Error", 0);

      return;
    }

    try
    {
      for (int k = 0; k < arrayOfInt.length; ++k)
      {
        str3 = (String)this.jTModel.getValueAt(arrayOfInt[k], 0);
        String str4 = (String)this.jTModel.getValueAt(arrayOfInt[k], 1);

        int j = Integer.parseInt(str3);

        String str2 = "DELETE FROM " + str1 + "ETL_Received_File" + "   WHERE ETL_System = '" + this.lastETLSys + "'" + "     AND ETL_job = '" + this.lastETLJob + "'" + "     AND JobSessionID = " + j + "     AND ReceivedFile = '" + str4 + "'";

        int l = localStatement.executeUpdate(str2);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      str3 = "Some error occured while deleting received file log record\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);
    }

    readFileLog(localConnection, this.lastETLSys, this.lastETLJob);
  }

  public void deleteFileLog()
  {
    int i = this.jTModel.getRowCount();

    for (int j = i - 1; j >= 0; --j)
    {
      this.jTModel.removeRow(j);
    }
  }

  class TableModel extends DefaultTableModel
  {
    private final ETLFileLog this$0;

    public TableModel()
    {
      this.this$0 = paramETLFileLog;
    }

    public boolean isCellEditable(, int paramInt2) {
      return false;
    }
  }
}