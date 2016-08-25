import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.EventObject;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class ETLJobWatchDog extends JFrame
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
  private ETLJobWatchDog dlg;
  private ETLMainFrame mainFrame;
  private JComboBox jcb_BU;
  private ETLJobWatchDog.TableModel tableModelFail;
  private ETLJobWatchDog.TableModel tableModelDone;

  public ETLJobWatchDog(JFrame paramJFrame)
  {
    super("ETL Job Watch Dog");

    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  private void DBClick(String paramString)
  {
  }

  protected void frameInit()
  {
    super.frameInit();

    ETLJobWatchDog.1 local1 = new WindowAdapter(this) { private final ETLJobWatchDog this$0;

      public void windowClosing() { this.this$0.hide();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(new BorderLayout());

    String[] arrayOfString = { "Seq.", "System", "Job", "BU", "Type", "Last Status", "Last Run Time", "Last TXDate", "Description" };

    this.tableModelFail = new ETLJobWatchDog.TableModel(this);
    this.tableModelDone = new ETLJobWatchDog.TableModel(this);

    this.failTable = new JTable(this.tableModelFail);
    this.doneTable = new JTable(this.tableModelDone);

    for (int i = 0; i < arrayOfString.length; ++i)
    {
      this.tableModelFail.addColumn(arrayOfString[i]);
      this.tableModelDone.addColumn(arrayOfString[i]);
    }

    this.failTable.getColumn("Seq.").setPreferredWidth(30);
    this.failTable.getColumn("System").setPreferredWidth(50);
    this.failTable.getColumn("Job").setPreferredWidth(180);
    this.failTable.getColumn("BU").setPreferredWidth(100);
    this.failTable.getColumn("Type").setPreferredWidth(60);
    this.failTable.getColumn("Last Status").setPreferredWidth(100);
    this.failTable.getColumn("Last Run Time").setPreferredWidth(120);
    this.failTable.getColumn("Last TXDate").setPreferredWidth(80);
    this.failTable.getColumn("Description").setPreferredWidth(250);

    this.failTable.setAutoResizeMode(0);

    this.failTable.setCellEditor(null);

    this.doneTable.getColumn("Seq.").setPreferredWidth(30);
    this.doneTable.getColumn("System").setPreferredWidth(50);
    this.doneTable.getColumn("Job").setPreferredWidth(180);
    this.doneTable.getColumn("BU").setPreferredWidth(100);
    this.doneTable.getColumn("Type").setPreferredWidth(60);
    this.doneTable.getColumn("Last Status").setPreferredWidth(100);
    this.doneTable.getColumn("Last Run Time").setPreferredWidth(120);
    this.doneTable.getColumn("Last TXDate").setPreferredWidth(80);
    this.doneTable.getColumn("Description").setPreferredWidth(250);

    this.doneTable.setAutoResizeMode(0);
    this.doneTable.setCellEditor(null);

    this.jPanelButton = new JPanel(new FlowLayout());

    this.jBnRefresh = new JButton("Refresh");
    this.jBnRefresh.setMnemonic(82);
    this.jBnClose = new JButton("Close");
    this.jBnClose.setMnemonic(67);

    this.jBnClose.addActionListener(new ActionListener(this) { private final ETLJobWatchDog this$0;

      public void actionPerformed() { ETLJobWatchDog.access$000(this.this$0).hide();
      }

    });
    this.jBnRefresh.addActionListener(new ActionListener(this) { private final ETLJobWatchDog this$0;

      public void actionPerformed() { ETLJobWatchDog.access$100(ETLJobWatchDog.access$000(this.this$0));
      }

    });
    this.failTable.addMouseListener(new MouseAdapter(this) { private final ETLJobWatchDog this$0;

      public void mouseClicked() { if (ETLJobWatchDog.access$200(this.this$0) == (JTable)paramMouseEvent.getSource())
        {
          ETLJobWatchDog.access$200(this.this$0).setCellSelectionEnabled(true);
          ETLJobWatchDog.access$200(this.this$0).isCellEditable(ETLJobWatchDog.access$200(this.this$0).getSelectedRow(), ETLJobWatchDog.access$200(this.this$0).getSelectedColumn());
        }

      }

    });
    this.jPanelButton.add(this.jBnRefresh);
    this.jPanelButton.add(this.jBnClose);

    this.failScrollPane = new JScrollPane();
    this.failScrollPane.setViewportView(this.failTable);

    this.doneScrollPane = new JScrollPane();
    this.doneScrollPane.setViewportView(this.doneTable);

    this.jPanelFail = new JPanel(new BorderLayout());
    this.jPanelFail.add(this.failScrollPane, "Center");
    this.jPanelFail.setPreferredSize(new Dimension(600, 200));

    this.jPanelFail.setBorder(BorderFactory.createTitledBorder("Other Job"));

    this.jPanelDone = new JPanel(new BorderLayout());
    this.jPanelDone.add(this.doneScrollPane, "Center");
    this.jPanelDone.setPreferredSize(new Dimension(600, 200));

    this.jPanelDone.setBorder(BorderFactory.createTitledBorder("Done Job"));

    this.splitPaneV = new JSplitPane(0);
    this.splitPaneV.setDividerSize(5);
    this.splitPaneV.setTopComponent(this.jPanelFail);
    this.splitPaneV.setBottomComponent(this.jPanelDone);

    this.jPanelOption = new JPanel(new FlowLayout());

    this.labelBU = new JLabel("BU");
    this.jcb_BU = new JComboBox();
    this.jcb_BU.setBounds(new Rectangle(115, 20, 150, 25));

    this.jrb_daily = new JRadioButton("Daily Job");
    this.jrb_weekly = new JRadioButton("Weekly Job");
    this.jrb_monthly = new JRadioButton("Monthly Job");

    ButtonGroup localButtonGroup = new ButtonGroup();

    localButtonGroup.add(this.jrb_daily);
    localButtonGroup.add(this.jrb_weekly);
    localButtonGroup.add(this.jrb_monthly);

    this.jPanelOption.add(this.labelBU);
    this.jPanelOption.add(this.jcb_BU);
    this.jPanelOption.add(this.jrb_daily);
    this.jPanelOption.add(this.jrb_weekly);
    this.jPanelOption.add(this.jrb_monthly);

    this.jrb_daily.setSelected(true);

    localContainer.add(this.jPanelOption, "North");
    localContainer.add(this.splitPaneV, "Center");
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

  private void retrieveFailLog()
  {
    Statement localStatement;
    int i = 0;

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

      String str4 = (String)this.jcb_BU.getSelectedItem();
      if (str4.equals("*ALL"))
        str4 = "";

      if (str4.equals("")) {
        str3 = "SELECT etl_system, etl_job, calendarBU, jobtype, last_jobstatus,       last_starttime, last_txdate, description   FROM " + str1 + "etl_job" + "   WHERE (jobtype = '" + str2 + "')" + "     AND (last_jobstatus is null OR last_jobstatus <> 'Done')" + "   ORDER BY jobtype, etl_system, etl_job";
      }
      else
      {
        str3 = "SELECT etl_system, etl_job, calendarBU, jobtype, last_jobstatus,       last_starttime, last_txdate, description   FROM " + str1 + "etl_job" + "   WHERE (jobtype = '" + str2 + "')" + "     AND (calendarBU = '" + str4 + "')" + "     AND (last_jobstatus is null OR last_jobstatus <> 'Done')" + "   ORDER BY jobtype, etl_system, etl_job";
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

        String str11 = localResultSet.getString(3);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(str11);

        String str7 = localResultSet.getString(4);
        if (str7.equals("D"))
          localVector.add("Daily");
        else if (str7.equals("W"))
          localVector.add("Weekly");
        else if (str7.equals("M"))
          localVector.add("Monthly");

        String str8 = localResultSet.getString(5);
        if (localResultSet.wasNull())
          localVector.add("Unknown");
        else
          localVector.add(str8);

        String str9 = localResultSet.getString(6);
        if (localResultSet.wasNull())
          localVector.add("Unknown");
        else
          localVector.add(str9);

        java.sql.Date localDate = localResultSet.getDate(7);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(localDate.toString());

        String str10 = localResultSet.getString(8);
        if (localResultSet.wasNull())
          localVector.add("");
        else {
          localVector.add(str10);
        }

        this.tableModelFail.addRow(localVector);
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
      JOptionPane.showMessageDialog(this, "Unknown error occured", "Error", 0);

      return;
    }
  }

  private void retrieveDoneLog()
  {
    Statement localStatement;
    int i = 0;

    String str1 = this.mainFrame.getDBName();

    deleteDoneLog();
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

      String str4 = (String)this.jcb_BU.getSelectedItem();
      if (str4.equals("*ALL"))
        str4 = "";
      if (str4.equals("")) {
        str3 = "SELECT etl_system, etl_job, calendarBU, jobtype, last_jobstatus,       last_starttime, last_txdate, description   FROM " + str1 + "etl_job" + "   WHERE (jobtype = '" + str2 + "') AND (last_jobstatus = 'Done')" + "   ORDER BY jobtype, etl_system";
      }
      else
      {
        str3 = "SELECT etl_system, etl_job, calendarBU, jobtype, last_jobstatus,       last_starttime, last_txdate, description   FROM " + str1 + "etl_job" + "   WHERE (jobtype = '" + str2 + "')" + "     AND (calendarBU = '" + str4 + "')" + "     AND (last_jobstatus = 'Done')" + "   ORDER BY jobtype, etl_system";
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

        String str10 = localResultSet.getString(3);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(str10);

        String str7 = localResultSet.getString(4);
        if (str7.equals("D"))
          localVector.add("Daily");
        else if (str7.equals("W"))
          localVector.add("Weekly");
        else if (str7.equals("M"))
          localVector.add("Monthly");

        String str8 = localResultSet.getString(5);
        if (localResultSet.wasNull())
          localVector.add("Unknown");
        else
          localVector.add(str8);

        String str9 = localResultSet.getString(6);
        if (localResultSet.wasNull())
          localVector.add("Unknown");
        else
          localVector.add(str9);

        java.sql.Date localDate = localResultSet.getDate(7);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(localDate.toString());

        String str11 = localResultSet.getString(8);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(str11);

        this.tableModelDone.addRow(localVector);
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
      JOptionPane.showMessageDialog(this, "Unknown error occured", "Error", 0);

      return;
    }
  }

  public void fillComBox(Connection paramConnection)
  {
    this.jcb_BU.removeAllItems();
    this.jcb_BU.addItem("*ALL");
    String str = this.mainFrame.getDBName();
    ETLUtilityInterface.getETLBu(paramConnection, str, this.jcb_BU);
    if (this.jcb_BU.getItemCount() <= 1) {
      return;
    }

    this.jcb_BU.setSelectedIndex(1);
  }

  public void retrieveLog()
  {
    Cursor localCursor = getCursor();
    setCursor(new Cursor(3));

    retrieveFailLog();
    retrieveDoneLog();

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

  private void deleteDoneLog()
  {
    int i = this.tableModelDone.getRowCount();

    for (int j = i - 1; j >= 0; --j)
    {
      this.tableModelDone.removeRow(j);
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

  static ETLJobWatchDog access$000(ETLJobWatchDog paramETLJobWatchDog)
  {
    return paramETLJobWatchDog.dlg; } 
  static void access$100(ETLJobWatchDog paramETLJobWatchDog) { paramETLJobWatchDog.refreshLog(); } 
  static JTable access$200(ETLJobWatchDog paramETLJobWatchDog) { return paramETLJobWatchDog.failTable;
  }

  class TableModel extends DefaultTableModel
  {
    private final ETLJobWatchDog this$0;

    public TableModel()
    {
      this.this$0 = paramETLJobWatchDog;
    }

    public boolean isCellEditable(, int paramInt2) {
      return true;
    }
  }
}