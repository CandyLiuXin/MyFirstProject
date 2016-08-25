import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;

public class ETLJobErrorDetection extends JFrame
{
  private JTable failTable;
  private JTable doneTable;
  private JPanel jPanelFail;
  private JPanel jPanelDone;
  private JPanel jPanelButton;
  private JPanel jPanelOption;
  private JSplitPane splitPaneV;
  private JButton jBnClose;
  private JButton jBnRefresh;
  private JRadioButton jrb_daily;
  private JRadioButton jrb_weekly;
  private JRadioButton jrb_monthly;
  private JLabel labelBU;
  private InputJTextField fieldBU;
  private JScrollPane failScrollPane;
  private JScrollPane doneScrollPane;
  private ETLJobErrorDetection dlg;
  private ETLMainFrame mainFrame;
  private ETLJobErrorDetection.TableModel tableModelFail;
  private ETLJobErrorDetection.TableModel tableModelDone;

  public ETLJobErrorDetection(JFrame paramJFrame)
  {
    super("ETL Job Error Detection");

    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void frameInit()
  {
    super.frameInit();

    ETLJobErrorDetection.1 local1 = new WindowAdapter(this) { private final ETLJobErrorDetection this$0;

      public void windowClosing() { this.this$0.hide();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(new BorderLayout());

    String[] arrayOfString = { "Seq.", "System", "Job", "BU", "Type", "Last Status", "Last Run Time", "Last TXDate", "SessionID", "Inserted", "Updated", "Duplicate", "ET", "UV", "ER1", "ER2" };

    this.tableModelFail = new ETLJobErrorDetection.TableModel(this);

    this.failTable = new JTable(this.tableModelFail);

    for (int i = 0; i < arrayOfString.length; ++i)
    {
      this.tableModelFail.addColumn(arrayOfString[i]);
    }

    this.failTable.getColumn("Seq.").setPreferredWidth(30);
    this.failTable.getColumn("System").setPreferredWidth(50);
    this.failTable.getColumn("Job").setPreferredWidth(180);
    this.failTable.getColumn("BU").setPreferredWidth(100);
    this.failTable.getColumn("Type").setPreferredWidth(60);
    this.failTable.getColumn("Last Status").setPreferredWidth(100);
    this.failTable.getColumn("Last Run Time").setPreferredWidth(120);
    this.failTable.getColumn("Last TXDate").setPreferredWidth(80);
    this.failTable.getColumn("Inserted").setPreferredWidth(70);
    this.failTable.getColumn("Updated").setPreferredWidth(70);
    this.failTable.getColumn("Duplicate").setPreferredWidth(70);
    this.failTable.getColumn("ET").setPreferredWidth(70);
    this.failTable.getColumn("UV").setPreferredWidth(70);
    this.failTable.getColumn("ER1").setPreferredWidth(70);
    this.failTable.getColumn("ER2").setPreferredWidth(70);

    this.failTable.setAutoResizeMode(0);
    this.failTable.setCellEditor(null);

    this.jPanelButton = new JPanel(new FlowLayout());

    this.jBnRefresh = new JButton("Refresh");
    this.jBnRefresh.setMnemonic(82);
    this.jBnClose = new JButton("Close");
    this.jBnClose.setMnemonic(67);

    this.jBnClose.addActionListener(new ActionListener(this) { private final ETLJobErrorDetection this$0;

      public void actionPerformed() { ETLJobErrorDetection.access$000(this.this$0).hide();
      }

    });
    this.jBnRefresh.addActionListener(new ActionListener(this) { private final ETLJobErrorDetection this$0;

      public void actionPerformed() { ETLJobErrorDetection.access$100(ETLJobErrorDetection.access$000(this.this$0));
      }

    });
    this.jPanelButton.add(this.jBnRefresh);
    this.jPanelButton.add(this.jBnClose);

    this.failScrollPane = new JScrollPane();
    this.failScrollPane.setViewportView(this.failTable);

    this.jPanelFail = new JPanel(new BorderLayout());
    this.jPanelFail.add(this.failScrollPane, "Center");
    this.jPanelFail.setPreferredSize(new Dimension(700, 250));

    this.jPanelFail.setBorder(BorderFactory.createTitledBorder(""));

    this.jPanelOption = new JPanel(new FlowLayout());

    this.labelBU = new JLabel("BU");
    this.fieldBU = new InputJTextField(InputJTextField.UPPERCASE);
    this.fieldBU.setInputLimited(15);
    this.fieldBU.setPreferredSize(new Dimension(100, 25));

    this.jrb_daily = new JRadioButton("Daily Job");
    this.jrb_weekly = new JRadioButton("Weekly Job");
    this.jrb_monthly = new JRadioButton("Monthly Job");

    ButtonGroup localButtonGroup = new ButtonGroup();

    localButtonGroup.add(this.jrb_daily);
    localButtonGroup.add(this.jrb_weekly);
    localButtonGroup.add(this.jrb_monthly);

    this.jPanelOption.add(this.labelBU);
    this.jPanelOption.add(this.fieldBU);
    this.jPanelOption.add(this.jrb_daily);
    this.jPanelOption.add(this.jrb_weekly);
    this.jPanelOption.add(this.jrb_monthly);

    this.jrb_daily.setSelected(true);

    localContainer.add(this.jPanelOption, "North");
    localContainer.add(this.jPanelFail, "Center");
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

  private void retrieveJobLog()
  {
    Statement localStatement;
    int i = 0;
    ArrayList localArrayList = new ArrayList();

    String str1 = this.mainFrame.getDBName();

    deleteFailLog();
    try
    {
      localStatement = this.mainFrame.getConnection().createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(this, "Unable to create statement object", "Error", 0);

      return;
    }
    try
    {
      String str3;
      String str2 = getJobTypeString();

      String str4 = this.fieldBU.getText().trim();

      if (str4.equals("")) {
        str3 = "SELECT ETL_System, ETL_Job, CalendarBU, Jobtype,       Last_JobStatus, Last_StartTime, Last_Txdate,       JobSessionID   FROM " + str1 + "ETL_Job" + "   WHERE (JobType = '" + str2 + "')" + "     AND (Last_Jobstatus ='Done')" + "   ORDER BY JobType, ETL_System, ETL_Job";
      }
      else
      {
        str3 = "SELECT ETL_System, ETL_Job, CalendarBU, JobType,       Last_JobStatus, Last_StartTime, Last_Txdate,       JobSessionID   FROM " + str1 + "ETL_Job" + "   WHERE (JobType = '" + str2 + "')" + "     AND (CalendarBU = '" + str4 + "')" + "     AND (Last_Jobstatus = 'Done')" + "   ORDER BY JobType, ETL_System, ETL_Job";
      }

      ResultSet localResultSet = localStatement.executeQuery(str3);

      i = 1;
      while (localResultSet.next())
      {
        Vector localVector = new Vector();

        localVector.add(String.valueOf(i++));

        String str5 = localResultSet.getString(1);
        localVector.add(str5);

        String str6 = localResultSet.getString(2);
        localVector.add(str6);

        String str7 = localResultSet.getString(3);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(str7);

        String str8 = localResultSet.getString(4);
        if (str8.equals("D"))
          localVector.add("Daily");
        else if (str8.equals("W"))
          localVector.add("Weekly");
        else if (str8.equals("M"))
          localVector.add("Monthly");

        String str9 = localResultSet.getString(5);
        if (localResultSet.wasNull())
          localVector.add("Unknown");
        else
          localVector.add(str9);

        String str10 = localResultSet.getString(6);
        if (localResultSet.wasNull())
          localVector.add("Unknown");
        else
          localVector.add(str10);

        java.sql.Date localDate = localResultSet.getDate(7);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(localDate.toString());

        int j = localResultSet.getInt(8);
        if (localResultSet.wasNull())
          j = 0;
        else
          --j;

        localVector.add(String.valueOf(j));

        localArrayList.add(localVector);
      }
      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this, "Could not retrieve data from etl_job", "Error", 0);

      return;
    }
    catch (Exception localException)
    {
      JOptionPane.showMessageDialog(this, "Unknown error occured in retrieveJobLog()", "Error", 0);

      return;
    }

    getRecordInfo(localArrayList);
    putView(localArrayList);
  }

  private void getRecordInfo(ArrayList paramArrayList)
  {
    PreparedStatement localPreparedStatement;
    String str1 = this.mainFrame.getDBName();

    String str2 = "SELECT InsertedRecord, UpdatedRecord, DeletedRecord,        DuplicateRecord, ETRecord, UVRecord, ER1Record, ER2Record   FROM " + str1 + "ETL_Record_Log" + "   WHERE ETL_System = ?" + "     AND ETL_Job = ?" + "     AND JobSessionID = ?";
    try
    {
      localPreparedStatement = this.mainFrame.getConnection().prepareStatement(str2);
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(this, "Unable to create statement object", "Error", 0);

      return;
    }

    try
    {
      int i6 = 0;
      while (true) {
        while (true) { if (i6 == paramArrayList.size())
            break label491:

          Vector localVector = (Vector)paramArrayList.get(i6);

          String str3 = (String)localVector.get(1);
          String str4 = (String)localVector.get(2);
          int i = Integer.parseInt((String)localVector.get(8));

          localPreparedStatement.setString(1, str3);
          localPreparedStatement.setString(2, str4);
          localPreparedStatement.setInt(3, i);

          int j = 0;
          int k = 0;
          int l = 0;
          int i1 = 0;
          int i2 = 0; int i3 = 0;
          int i4 = 0; int i5 = 0;

          ResultSet localResultSet = localPreparedStatement.executeQuery();
          while (localResultSet.next())
          {
            j = localResultSet.getInt(1);
            k = localResultSet.getInt(2);
            l = localResultSet.getInt(3);
            i1 = localResultSet.getInt(4);
            i2 = localResultSet.getInt(5);
            i3 = localResultSet.getInt(6);
            i4 = localResultSet.getInt(7);
            i5 = localResultSet.getInt(8);
            break;
          }

          localResultSet.close();

          if ((i1 <= 0) && (i2 <= 0) && (i3 <= 0) && (i4 <= 0) && (i5 <= 0))
            break;

          localVector.set(0, String.valueOf(i6 + 1));
          localVector.add(new Integer(j).toString());
          localVector.add(new Integer(k).toString());
          localVector.add(new Integer(i1).toString());
          localVector.add(new Integer(i2).toString());
          localVector.add(new Integer(i3).toString());
          localVector.add(new Integer(i4).toString());
          localVector.add(new Integer(i5).toString());

          paramArrayList.set(i6, localVector);
          ++i6;
        }

        paramArrayList.remove(i6);
      }

      label491: localPreparedStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this, "Could not retrieve data from ETL_Record_Log\n" + localSQLException2.getMessage(), "Error", 0);

      return;
    }
    catch (Exception localException)
    {
      JOptionPane.showMessageDialog(this, "Unknown error occured getRecordInfo()", "Error", 0);

      return;
    }
  }

  private void putView(ArrayList paramArrayList)
  {
    int i = paramArrayList.size();

    for (int j = 0; j < i; ++j) {
      Vector localVector = (Vector)paramArrayList.get(j);
      this.tableModelFail.addRow(localVector);
    }
  }

  public void retrieveLog()
  {
    Cursor localCursor = getCursor();
    setCursor(new Cursor(3));

    retrieveJobLog();

    setCursor(localCursor);
  }

  private void deleteFailLog()
  {
    int i = this.tableModelFail.getRowCount();

    for (int j = i - 1; j >= 0; --j)
    {
      this.tableModelFail.removeRow(j);
    }
  }

  private void refreshLog()
  {
    retrieveLog();
  }

  private String getDateString()
  {
    Calendar localCalendar = Calendar.getInstance();

    int i = localCalendar.get(1);
    int j = localCalendar.get(2) + 1;
    int k = localCalendar.get(5);

    String str = String.valueOf(i) + "-";
    if (j < 10)
      str = str + "0";

    str = str + String.valueOf(j);
    str = str + "-";
    str = str + String.valueOf(k);

    return str;
  }

  private String getJobTypeString()
  {
    String str = "";

    if (this.jrb_daily.isSelected())
      str = "D";
    else if (this.jrb_weekly.isSelected())
      str = "W";
    else if (this.jrb_monthly.isSelected())
      str = "M";

    return str;
  }

  static ETLJobErrorDetection access$000(ETLJobErrorDetection paramETLJobErrorDetection)
  {
    return paramETLJobErrorDetection.dlg; } 
  static void access$100(ETLJobErrorDetection paramETLJobErrorDetection) { paramETLJobErrorDetection.refreshLog();
  }

  class TableModel extends DefaultTableModel
  {
    private final ETLJobErrorDetection this$0;

    public TableModel()
    {
      this.this$0 = paramETLJobErrorDetection;
    }

    public boolean isCellEditable(, int paramInt2) {
      return false;
    }
  }
}