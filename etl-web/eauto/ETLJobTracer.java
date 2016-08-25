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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;

public class ETLJobTracer extends JFrame
{
  private JTable traceTable;
  private JPanel jPanelTrace;
  private JPanel jPanelButton;
  private JPanel jPanelOption;
  private JButton jBnClose;
  private JButton jBnRefresh;
  private JComboBox jcb_etlsys;
  private JComboBox jcb_etljob;
  private JRadioButton jrb_notexec;
  private JRadioButton jrb_exec;
  private JRadioButton jrb_all;
  private JLabel labelTxDate;
  private InputJTextField fieldTxDate;
  private JScrollPane traceScrollPane;
  private ETLJobTracer dlg;
  private ETLMainFrame mainFrame;
  private ETLJobTracer.TableModel tableModelTrace;
  private ETLJobTracer.SysChangedListener sysItemChanged;

  public ETLJobTracer(JFrame paramJFrame)
  {
    super("ETL Job Tracer");

    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void frameInit()
  {
    super.frameInit();

    ETLJobTracer.1 local1 = new WindowAdapter(this) { private final ETLJobTracer this$0;

      public void windowClosing() { this.this$0.hide();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(new BorderLayout());

    String[] arrayOfString = { "Seq.", "System", "Job", "TxDate", "Status", "Start Time", "End Time" };

    this.tableModelTrace = new ETLJobTracer.TableModel(this);

    this.traceTable = new JTable(this.tableModelTrace);

    for (int i = 0; i < arrayOfString.length; ++i)
    {
      this.tableModelTrace.addColumn(arrayOfString[i]);
    }

    this.traceTable.getColumn("Seq.").setPreferredWidth(30);
    this.traceTable.getColumn("System").setPreferredWidth(50);
    this.traceTable.getColumn("Job").setPreferredWidth(180);
    this.traceTable.getColumn("TxDate").setPreferredWidth(90);
    this.traceTable.getColumn("Status").setPreferredWidth(75);
    this.traceTable.getColumn("Start Time").setPreferredWidth(120);
    this.traceTable.getColumn("End Time").setPreferredWidth(120);

    this.traceTable.setAutoResizeMode(0);
    this.traceTable.setCellEditor(null);

    this.jPanelButton = new JPanel(new FlowLayout());

    this.jBnRefresh = new JButton("Refresh");
    this.jBnRefresh.setMnemonic(82);
    this.jBnClose = new JButton("Close");
    this.jBnClose.setMnemonic(67);

    this.jBnClose.addActionListener(new ActionListener(this) { private final ETLJobTracer this$0;

      public void actionPerformed() { ETLJobTracer.access$100(this.this$0).hide();
      }

    });
    this.jBnRefresh.addActionListener(new ActionListener(this) { private final ETLJobTracer this$0;

      public void actionPerformed() { ETLJobTracer.access$200(ETLJobTracer.access$100(this.this$0));
      }

    });
    this.jPanelButton.add(this.jBnRefresh);
    this.jPanelButton.add(this.jBnClose);

    this.traceScrollPane = new JScrollPane();
    this.traceScrollPane.setViewportView(this.traceTable);

    this.jPanelTrace = new JPanel(new BorderLayout());
    this.jPanelTrace.add(this.traceScrollPane, "Center");
    this.jPanelTrace.setPreferredSize(new Dimension(700, 250));

    this.jPanelTrace.setBorder(BorderFactory.createTitledBorder(""));

    this.jPanelOption = new JPanel();
    this.jPanelOption.setLayout(null);

    this.labelTxDate = new JLabel("TxDate(YYYY-MM-DD):");
    this.labelTxDate.setBounds(new Rectangle(5, 5, 140, 25));

    this.fieldTxDate = new InputJTextField(InputJTextField.UPPERCASE);
    this.fieldTxDate.setInputLimited(10);

    this.fieldTxDate.setBounds(new Rectangle(150, 5, 90, 25));

    this.jrb_notexec = new JRadioButton("Not Executed job");
    this.jrb_exec = new JRadioButton("Executed Job");
    this.jrb_all = new JRadioButton("All Job");

    this.jrb_notexec.setBounds(new Rectangle(250, 5, 140, 25));
    this.jrb_exec.setBounds(new Rectangle(400, 5, 120, 25));
    this.jrb_all.setBounds(new Rectangle(530, 5, 90, 25));

    ButtonGroup localButtonGroup = new ButtonGroup();

    localButtonGroup.add(this.jrb_notexec);
    localButtonGroup.add(this.jrb_exec);
    localButtonGroup.add(this.jrb_all);

    JLabel localJLabel1 = new JLabel("System:");
    localJLabel1.setBounds(new Rectangle(5, 35, 60, 25));

    this.jcb_etlsys = new JComboBox();
    this.jcb_etlsys.setBounds(new Rectangle(70, 35, 70, 25));

    JLabel localJLabel2 = new JLabel("Job:");
    localJLabel2.setBounds(new Rectangle(180, 35, 40, 25));

    this.jcb_etljob = new JComboBox();
    this.jcb_etljob.setBounds(new Rectangle(225, 35, 250, 25));

    this.sysItemChanged = new ETLJobTracer.SysChangedListener(this, this.jcb_etljob);

    this.jcb_etlsys.addItemListener(this.sysItemChanged);

    this.jPanelOption.add(this.labelTxDate);
    this.jPanelOption.add(this.fieldTxDate);
    this.jPanelOption.add(this.jrb_notexec);
    this.jPanelOption.add(this.jrb_exec);
    this.jPanelOption.add(this.jrb_all);
    this.jPanelOption.add(localJLabel1);
    this.jPanelOption.add(this.jcb_etlsys);
    this.jPanelOption.add(localJLabel2);
    this.jPanelOption.add(this.jcb_etljob);

    this.jrb_notexec.setSelected(true);

    this.jPanelOption.setPreferredSize(new Dimension(580, 70));

    localContainer.add(this.jPanelOption, "North");
    localContainer.add(this.jPanelTrace, "Center");
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

  private void retrieveJobTrace()
  {
    Statement localStatement;
    String str6;
    int i = 0;

    String str1 = this.mainFrame.getDBName();

    String str2 = this.fieldTxDate.getText().trim();

    String str3 = (String)this.jcb_etlsys.getSelectedItem();
    String str4 = (String)this.jcb_etljob.getSelectedItem();

    if ((str3.equals("")) && 
      (str2.equals(""))) {
      str5 = "Please specify the TxDate you would like to query";
      JOptionPane.showMessageDialog(this, str5, "Error", 0);

      this.fieldTxDate.requestFocus();
      return;
    }

    if (this.jrb_notexec.isSelected())
      str6 = "JobStatus = 'Waiting'";
    else if (this.jrb_exec.isSelected())
      str6 = "JobStatus <> 'Waiting'";
    else {
      str6 = "";
    }

    if (!(str3.equals(""))) {
      if (str4.equals("")) {
        if (str6.equals(""))
          str6 = "ETL_System = '" + str3 + "'";
        else
          str6 = str6 + "AND ETL_System = '" + str3 + "'";

      }
      else if (str6.equals("")) {
        str6 = "ETL_System = '" + str3 + "' " + " AND ETL_Job = '" + str4 + "'";
      }
      else {
        str6 = str6 + " AND ETL_System = '" + str3 + "' " + " AND ETL_Job = '" + str4 + "'";
      }

    }

    String str5 = "SELECT ETL_System, ETL_Job, TxDate, JobStatus,       StartTime, EndTime   FROM " + str1 + "ETL_Job_Trace";

    if (str2.equals("")) {
      if (!(str6.equals("")))
        str5 = str5 + " WHERE " + str6;
    }
    else {
      str5 = str5 + " WHERE TxDate = '" + str2 + "'";
      if (!(str6.equals("")))
        str5 = str5 + " AND " + str6;

    }

    str5 = str5 + " ORDER BY TxDate, ETL_System, ETL_Job";

    deleteJobTrace();
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
      ResultSet localResultSet = localStatement.executeQuery(str5);

      i = 1;
      while (localResultSet.next())
      {
        Vector localVector = new Vector();

        localVector.add(String.valueOf(i++));

        String str7 = localResultSet.getString(1);
        localVector.add(str7);

        String str8 = localResultSet.getString(2);
        localVector.add(str8);

        java.sql.Date localDate = localResultSet.getDate(3);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(localDate.toString());

        String str9 = localResultSet.getString(4);
        localVector.add(str9);

        String str10 = localResultSet.getString(5);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(str10);

        String str11 = localResultSet.getString(6);
        if (localResultSet.wasNull())
          localVector.add("");
        else
          localVector.add(str11);

        this.tableModelTrace.addRow(localVector);
      }
      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this, "Could not retrieve data from etl_job_trace", "Error", 0);

      return;
    }
    catch (Exception localException)
    {
      JOptionPane.showMessageDialog(this, "Unknown error occured in retrieveJobTrace()", "Error", 0);

      return;
    }
  }

  public void fillSystemList()
  {
    deleteJobTrace();

    this.jcb_etlsys.removeItemListener(this.sysItemChanged);

    this.jcb_etlsys.removeAllItems();
    this.jcb_etljob.removeAllItems();
    this.jcb_etlsys.addItem("");
    this.jcb_etljob.addItem("");

    ETLUtilityInterface.getETLSystem(this.mainFrame.getConnection(), this.mainFrame.getDBName(), this.jcb_etlsys);

    this.jcb_etlsys.addItemListener(this.sysItemChanged);
  }

  public void retrieveLog()
  {
    Cursor localCursor = getCursor();
    setCursor(new Cursor(3));

    retrieveJobTrace();

    setCursor(localCursor);
  }

  private void deleteJobTrace()
  {
    int i = this.tableModelTrace.getRowCount();

    for (int j = i - 1; j >= 0; --j)
    {
      this.tableModelTrace.removeRow(j);
    }
  }

  private void refreshJobTrace()
  {
    retrieveLog();
  }

  static ETLMainFrame access$000(ETLJobTracer paramETLJobTracer)
  {
    return paramETLJobTracer.mainFrame; } 
  static ETLJobTracer access$100(ETLJobTracer paramETLJobTracer) { return paramETLJobTracer.dlg; } 
  static void access$200(ETLJobTracer paramETLJobTracer) { paramETLJobTracer.refreshJobTrace();
  }

  class SysChangedListener
  implements ItemListener
  {
    private JComboBox jcbObj;
    private final ETLJobTracer this$0;

    public SysChangedListener(, JComboBox paramJComboBox)
    {
      this.this$0 = paramETLJobTracer; this.jcbObj = paramJComboBox; }

    public void itemStateChanged() {
      JComboBox localJComboBox = (JComboBox)paramItemEvent.getSource();
      String str = (String)localJComboBox.getSelectedItem();
      this.jcbObj.removeAllItems();
      this.jcbObj.addItem("");

      if (str.equals("")) {
        return;
      }

      Cursor localCursor = this.this$0.getCursor();
      this.this$0.setCursor(new Cursor(3));

      ETLUtilityInterface.getETLJob(ETLJobTracer.access$000(this.this$0).getConnection(), ETLJobTracer.access$000(this.this$0).getDBName(), str, this.jcbObj);

      this.this$0.setCursor(localCursor);
    }
  }

  class TableModel extends DefaultTableModel
  {
    private final ETLJobTracer this$0;

    public TableModel()
    {
      this.this$0 = paramETLJobTracer;
    }

    public boolean isCellEditable(, int paramInt2) {
      return false;
    }
  }
}