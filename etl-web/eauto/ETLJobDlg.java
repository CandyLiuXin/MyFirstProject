import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
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
import java.util.EventObject;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;

public class ETLJobDlg extends JDialog
{
  private JPanel jPanelButton;
  private JPanel jPanelData;
  private JButton jBnUpdate;
  private JButton jBnCancel;
  private JButton jbn_setTimeTrigger;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JLabel jLabel3;
  private JLabel jLabel4;
  private JLabel jLabel5;
  private JLabel jLabel6;
  private JLabel jLabel7;
  private JLabel jLabel8;
  private JLabel jLabel9;
  private InputJTextField jTextField1;
  private InputJTextField jTextField2;
  private InputJTextField jTextField3;
  private InputJTextField jTextField4;
  private JComboBox jcb_server;
  private JRadioButton jrb_enabled;
  private JRadioButton jrb_disabled;
  private JRadioButton jrb_daily;
  private JRadioButton jrb_weekly;
  private JRadioButton jrb_monthly;
  private JRadioButton jrb_demand;
  private JRadioButton jrb_virtual;
  private JRadioButton jrb_allow;
  private JRadioButton jrb_notallow;
  private JCheckBox jcb_autoOff;
  private JCheckBox jcb_checkCalendar;
  private JCheckBox jcb_notCheckLastStatus;
  private JCheckBox jcb_triggerByTime;
  private JRadioButton[] jrb_beginTW;
  private JRadioButton[] jrb_endTW;
  private ETLJobDlg dlg;
  private DefaultMutableTreeNode parentNode;
  private Connection dbcon;
  private String etlsys;
  private int mode = 0;
  private ETLMainFrame mainFrame;
  private ETLTimeTriggerDailyDlg TTDailyDlg = null;
  private ETLTimeTriggerWeeklyDlg TTWeeklyDlg = null;
  private ETLTimeTriggerMonthlyDlg TTMonthlyDlg = null;
  private ETLTimeTriggerDemandDlg TTDemandDlg = null;

  public ETLJobDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "ETL Job", true);
    setResizable(false);
    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLJobDlg.1 local1 = new WindowAdapter(this) { private final ETLJobDlg this$0;

      public void windowClosing() { this.this$0.dispose();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(new BorderLayout());

    this.jPanelButton = new JPanel();
    this.jPanelData = new JPanel();
    this.jPanelButton.setLayout(new FlowLayout());
    this.jPanelData.setLayout(null);

    this.jBnUpdate = new JButton("Update");
    this.jBnUpdate.setMnemonic(85);
    this.jBnCancel = new JButton("Cancel");
    this.jBnCancel.setMnemonic(67);

    this.jBnUpdate.addActionListener(new ActionListener(this) { private final ETLJobDlg this$0;

      public void actionPerformed() { ETLJobDlg.access$100(ETLJobDlg.access$000(this.this$0));
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLJobDlg this$0;

      public void actionPerformed() { ETLJobDlg.access$000(this.this$0).dispose();
      }

    });
    this.jPanelButton.add(this.jBnUpdate);
    this.jPanelButton.add(this.jBnCancel);

    this.jLabel1 = new JLabel("Job Name:");
    this.jLabel1.setBounds(new Rectangle(20, 20, 110, 25));

    this.jLabel2 = new JLabel("Description:");
    this.jLabel2.setBounds(new Rectangle(20, 50, 110, 25));

    this.jLabel3 = new JLabel("Job Type:");
    this.jLabel3.setBounds(new Rectangle(20, 80, 110, 25));

    this.jLabel4 = new JLabel("Enable:");
    this.jLabel4.setBounds(new Rectangle(20, 140, 110, 25));

    this.jLabel5 = new JLabel("Frequency:");
    this.jLabel5.setBounds(new Rectangle(20, 170, 110, 25));

    this.jLabel6 = new JLabel("Calender BU:");
    this.jLabel6.setBounds(new Rectangle(20, 200, 110, 25));

    this.jLabel7 = new JLabel("Running At Server:");
    this.jLabel7.setBounds(new Rectangle(20, 230, 110, 25));

    this.jLabel8 = new JLabel("Job Execution:");
    this.jLabel8.setBounds(new Rectangle(20, 260, 110, 25));

    this.jLabel9 = new JLabel("Time Window Type:");
    this.jLabel9.setBounds(new Rectangle(20, 290, 110, 25));

    this.jPanelData.add(this.jLabel1);
    this.jPanelData.add(this.jLabel2);
    this.jPanelData.add(this.jLabel3);
    this.jPanelData.add(this.jLabel4);
    this.jPanelData.add(this.jLabel5);
    this.jPanelData.add(this.jLabel6);
    this.jPanelData.add(this.jLabel7);
    this.jPanelData.add(this.jLabel8);
    this.jPanelData.add(this.jLabel9);

    this.jTextField1 = new InputJTextField(InputJTextField.UPPERCASE);
    this.jTextField1.setBounds(new Rectangle(145, 20, 200, 25));
    this.jTextField1.setInputLimited(50);

    this.jTextField2 = new InputJTextField();
    this.jTextField2.setBounds(new Rectangle(145, 50, 300, 25));
    this.jTextField2.setInputLimited(50);

    this.jTextField3 = new InputJTextField();
    this.jTextField3.setBounds(new Rectangle(145, 170, 90, 25));
    this.jTextField3.setInputLimited(30);

    this.jTextField4 = new InputJTextField(InputJTextField.UPPERCASE);
    this.jTextField4.setBounds(new Rectangle(145, 200, 90, 25));
    this.jTextField4.setInputLimited(15);

    this.jcb_server = new JComboBox();
    this.jcb_server.setBounds(new Rectangle(145, 230, 110, 25));

    this.jcb_autoOff = new JCheckBox("Auto Turn Off");
    this.jcb_autoOff.setBounds(new Rectangle(145, 110, 110, 25));

    this.jcb_triggerByTime = new JCheckBox("Trigger By Time");
    this.jcb_triggerByTime.setBounds(new Rectangle(270, 110, 110, 25));

    this.jbn_setTimeTrigger = new JButton("Set Time Trigger");
    this.jbn_setTimeTrigger.setBounds(new Rectangle(390, 110, 130, 25));
    this.jbn_setTimeTrigger.setEnabled(false);

    this.jcb_checkCalendar = new JCheckBox("Check Data Calendar");
    this.jcb_checkCalendar.setBounds(new Rectangle(260, 200, 180, 25));

    this.jcb_notCheckLastStatus = new JCheckBox("Do Not Check Job Last Status");
    this.jcb_notCheckLastStatus.setBounds(new Rectangle(145, 260, 230, 25));

    JPanel localJPanel1 = new JPanel();
    localJPanel1.setLayout(null);
    ButtonGroup localButtonGroup1 = new ButtonGroup();
    this.jrb_daily = new JRadioButton("Daily");
    this.jrb_daily.setBounds(new Rectangle(0, 0, 60, 25));
    this.jrb_weekly = new JRadioButton("Weekly");
    this.jrb_weekly.setBounds(new Rectangle(65, 0, 70, 25));
    this.jrb_monthly = new JRadioButton("Monthly");
    this.jrb_monthly.setBounds(new Rectangle(140, 0, 75, 25));
    this.jrb_demand = new JRadioButton("Demand");
    this.jrb_demand.setBounds(new Rectangle(220, 0, 70, 25));
    this.jrb_virtual = new JRadioButton("Virtual");
    this.jrb_virtual.setBounds(new Rectangle(295, 0, 80, 25));

    localJPanel1.add(this.jrb_daily);
    localJPanel1.add(this.jrb_weekly);
    localJPanel1.add(this.jrb_monthly);
    localJPanel1.add(this.jrb_demand);
    localJPanel1.add(this.jrb_virtual);
    localJPanel1.setBounds(new Rectangle(145, 80, 380, 25));

    localButtonGroup1.add(this.jrb_daily);
    localButtonGroup1.add(this.jrb_weekly);
    localButtonGroup1.add(this.jrb_monthly);
    localButtonGroup1.add(this.jrb_demand);
    localButtonGroup1.add(this.jrb_virtual);

    this.jrb_daily.setSelected(true);

    JPanel localJPanel2 = new JPanel();
    localJPanel2.setLayout(null);
    ButtonGroup localButtonGroup2 = new ButtonGroup();
    this.jrb_enabled = new JRadioButton("Enabled");
    this.jrb_enabled.setBounds(new Rectangle(0, 0, 80, 25));
    this.jrb_disabled = new JRadioButton("Disabled");
    this.jrb_disabled.setBounds(new Rectangle(90, 0, 80, 25));

    localJPanel2.add(this.jrb_enabled);
    localJPanel2.add(this.jrb_disabled);
    localJPanel2.setBounds(new Rectangle(145, 140, 200, 35));

    localButtonGroup2.add(this.jrb_enabled);
    localButtonGroup2.add(this.jrb_disabled);

    this.jrb_enabled.setSelected(true);

    JPanel localJPanel3 = new JPanel();
    localJPanel3.setLayout(null);
    ButtonGroup localButtonGroup3 = new ButtonGroup();
    this.jrb_allow = new JRadioButton("Allow");
    this.jrb_allow.setBounds(new Rectangle(0, 0, 70, 25));
    this.jrb_notallow = new JRadioButton("Not Allow");
    this.jrb_notallow.setBounds(new Rectangle(80, 0, 90, 25));

    localJPanel3.add(this.jrb_allow);
    localJPanel3.add(this.jrb_notallow);
    localJPanel3.setBounds(new Rectangle(145, 290, 200, 25));

    localButtonGroup3.add(this.jrb_allow);
    localButtonGroup3.add(this.jrb_notallow);

    this.jrb_allow.setSelected(true);

    this.jPanelData.add(this.jTextField1);
    this.jPanelData.add(this.jTextField2);
    this.jPanelData.add(localJPanel1);
    this.jPanelData.add(this.jcb_triggerByTime);
    this.jPanelData.add(this.jbn_setTimeTrigger);

    this.jPanelData.add(this.jTextField3);
    this.jPanelData.add(this.jcb_autoOff);

    this.jPanelData.add(this.jTextField4);
    this.jPanelData.add(this.jcb_server);

    this.jPanelData.add(this.jcb_checkCalendar);
    this.jPanelData.add(this.jcb_notCheckLastStatus);

    this.jPanelData.add(localJPanel2);

    this.jPanelData.add(localJPanel3);

    JPanel localJPanel4 = new JPanel(new BorderLayout());
    localJPanel4.add(this.jPanelData, "Center");

    this.jPanelData.setBorder(BorderFactory.createTitledBorder("Job Attributes"));

    JPanel localJPanel5 = new JPanel();
    initBeginTimeWindow(localJPanel5);

    JPanel localJPanel6 = new JPanel();
    initEndTimeWindow(localJPanel6);

    JPanel localJPanel7 = new JPanel(new GridLayout(2, 0));
    localJPanel7.setBorder(BorderFactory.createTitledBorder("Job Time Window"));

    localJPanel7.add(localJPanel5);
    localJPanel7.add(localJPanel6);

    localJPanel4.add(localJPanel7, "South");

    localContainer.add(localJPanel4, "Center");
    localContainer.add(this.jPanelButton, "South");

    this.jbn_setTimeTrigger.addActionListener(new ActionListener(this) { private final ETLJobDlg this$0;

      public void actionPerformed() { ETLJobDlg.access$200(this.this$0);
      }

    });
    this.jrb_virtual.addChangeListener(new ChangeListener(this) {
      private boolean bSelected;
      private final ETLJobDlg this$0;

      public void stateChanged() { JRadioButton localJRadioButton = (JRadioButton)paramChangeEvent.getSource();
        if (localJRadioButton.isSelected()) {
          if (this.bSelected)
            return;

          ETLJobDlg.access$300(this.this$0).setEnabled(false);
          ETLJobDlg.access$400(this.this$0).setEnabled(false);
          ETLJobDlg.access$500(this.this$0).setEnabled(false);
          ETLJobDlg.access$600(this.this$0).setEnabled(false);
          ETLJobDlg.access$600(this.this$0).setSelected(false);
          ETLJobDlg.access$700(this.this$0).setEnabled(false);
          ETLJobDlg.access$800(this.this$0).setEnabled(false);
          ETLJobDlg.access$900(this.this$0).setEnabled(false);
          ETLJobDlg.access$1000(this.this$0).setEnabled(false);
          ETLJobDlg.access$1100(this.this$0).setEnabled(false);
          ETLJobDlg.access$1200(this.this$0, false);
          this.bSelected = true;
        } else {
          if (!(this.bSelected))
            return;

          ETLJobDlg.access$300(this.this$0).setEnabled(true);
          ETLJobDlg.access$400(this.this$0).setEnabled(true);
          ETLJobDlg.access$500(this.this$0).setEnabled(true);
          ETLJobDlg.access$600(this.this$0).setEnabled(true);
          ETLJobDlg.access$700(this.this$0).setEnabled(true);
          ETLJobDlg.access$800(this.this$0).setEnabled(true);
          ETLJobDlg.access$900(this.this$0).setEnabled(true);
          ETLJobDlg.access$1000(this.this$0).setEnabled(true);
          ETLJobDlg.access$1100(this.this$0).setEnabled(true);
          ETLJobDlg.access$1200(this.this$0, true);
          this.bSelected = false;
        }

      }

    });
    this.jcb_triggerByTime.addActionListener(new ActionListener(this) { private final ETLJobDlg this$0;

      public void actionPerformed() { JCheckBox localJCheckBox = (JCheckBox)paramActionEvent.getSource();
        if (localJCheckBox.isSelected()) {
          ETLJobDlg.access$700(this.this$0).setEnabled(true);
          ETLJobDlg.access$300(this.this$0).setEnabled(false);
          ETLJobDlg.access$400(this.this$0).setEnabled(false);
          ETLJobDlg.access$800(this.this$0).setEnabled(false);
          ETLJobDlg.access$1000(this.this$0).setEnabled(false);
          ETLJobDlg.access$1100(this.this$0).setEnabled(false);
          ETLJobDlg.access$1200(this.this$0, false);
        } else {
          ETLJobDlg.access$700(this.this$0).setEnabled(false);
          ETLJobDlg.access$300(this.this$0).setEnabled(true);
          ETLJobDlg.access$400(this.this$0).setEnabled(true);
          ETLJobDlg.access$800(this.this$0).setEnabled(true);
          ETLJobDlg.access$1000(this.this$0).setEnabled(true);
          ETLJobDlg.access$1100(this.this$0).setEnabled(true);
          ETLJobDlg.access$1200(this.this$0, true);
        }

      }

    });
    setSize(new Dimension(550, 580));

    Dimension localDimension1 = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension localDimension2 = getSize();

    if (localDimension2.height > localDimension1.height)
      localDimension2.height = localDimension1.height;

    if (localDimension2.width > localDimension1.width)
      localDimension2.width = localDimension1.width;

    setLocation((localDimension1.width - localDimension2.width) / 2, (localDimension1.height - localDimension2.height) / 2);
  }

  private void initBeginTimeWindow(JPanel paramJPanel)
  {
    ButtonGroup localButtonGroup = new ButtonGroup();

    paramJPanel.setBorder(BorderFactory.createTitledBorder("Begin Hour"));
    paramJPanel.setLayout(new GridLayout(2, 12));

    this.jrb_beginTW = new JRadioButton[24];
    Color localColor = new Color(0, 0, 255);

    for (int i = 0; i < 24; ++i)
    {
      String str = String.valueOf(i);
      if (i < 10)
        str = "0" + str;

      this.jrb_beginTW[i] = new JRadioButton(str);
      this.jrb_beginTW[i].setForeground(localColor);
      localButtonGroup.add(this.jrb_beginTW[i]);
      paramJPanel.add(this.jrb_beginTW[i]);
    }
  }

  private void initEndTimeWindow(JPanel paramJPanel)
  {
    ButtonGroup localButtonGroup = new ButtonGroup();

    paramJPanel.setBorder(BorderFactory.createTitledBorder("End Hour"));
    paramJPanel.setLayout(new GridLayout(2, 12));
    Color localColor = new Color(255, 0, 0);

    this.jrb_endTW = new JRadioButton[24];

    for (int i = 0; i < 24; ++i)
    {
      String str = String.valueOf(i);
      if (i < 10)
        str = "0" + str;

      this.jrb_endTW[i] = new JRadioButton(str);
      this.jrb_endTW[i].setForeground(localColor);
      localButtonGroup.add(this.jrb_endTW[i]);
      paramJPanel.add(this.jrb_endTW[i]);
    }
  }

  private void resetTimeWindow(boolean paramBoolean)
  {
    for (int i = 0; i < 24; ++i)
    {
      this.jrb_beginTW[i].setEnabled(paramBoolean);
      this.jrb_endTW[i].setEnabled(paramBoolean);
    }
  }

  public void add(Connection paramConnection, DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString)
  {
    this.mode = 1;
    this.dbcon = paramConnection;
    this.parentNode = paramDefaultMutableTreeNode;
    this.etlsys = paramString;

    ETLUtilityInterface.getETLServer(paramConnection, this.mainFrame.getDBName(), this.jcb_server);

    this.jTextField3.setText("0");
    this.jrb_beginTW[0].setSelected(true);
    this.jrb_endTW[23].setSelected(true);

    show();
  }

  public void modify(Connection paramConnection, DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString1, String paramString2)
  {
    this.mode = 2;
    this.dbcon = paramConnection;
    this.parentNode = paramDefaultMutableTreeNode;
    this.etlsys = paramString1;

    ETLUtilityInterface.getETLServer(paramConnection, this.mainFrame.getDBName(), this.jcb_server);

    this.jTextField1.setEditable(false);

    selectData(paramString1, paramString2);

    show();
  }

  private void update()
  {
    if (this.mode == 1) {
      insertData();
    }
    else if (this.mode == 2) {
      updateData();
    }

    if (this.jrb_daily.isSelected()) {
      if (this.TTDailyDlg != null) {
        this.TTDailyDlg.setSysJob(this.etlsys, this.jTextField1.getText().trim());
        this.TTDailyDlg.updateTimeTrigger();
      }
    } else if (this.jrb_weekly.isSelected()) {
      if (this.TTWeeklyDlg != null) {
        this.TTWeeklyDlg.setSysJob(this.etlsys, this.jTextField1.getText().trim());
        this.TTWeeklyDlg.updateTimeTrigger();
      }
    } else if (this.jrb_monthly.isSelected()) {
      if (this.TTMonthlyDlg != null) {
        this.TTMonthlyDlg.setSysJob(this.etlsys, this.jTextField1.getText().trim());
        this.TTMonthlyDlg.updateTimeTrigger();
      }
    } else if ((this.jrb_demand.isSelected()) && 
      (this.TTDemandDlg != null)) {
      this.TTDemandDlg.setSysJob(this.etlsys, this.jTextField1.getText().trim());
      this.TTDemandDlg.updateTimeTrigger();
    }
  }

  private void insertData()
  {
    String str6;
    String str7;
    String str8;
    String str9;
    String str10;
    String str11;
    String str12;
    Statement localStatement;
    int i1;
    String str1 = this.jTextField1.getText().trim();
    String str2 = this.jTextField2.getText().trim();
    String str3 = this.jTextField3.getText().trim();
    String str4 = this.jTextField4.getText().trim();
    String str5 = (String)this.jcb_server.getSelectedItem();

    int i = 0; int j = 23;

    if ((!(str5.equals(""))) && 
      (isServerExist(str5) != 1)) {
      JOptionPane.showMessageDialog(this, "The server name does not exist\n", "Add Job", 0);

      return;
    }

    if (str3.equals(""))
      str3 = "0";

    if (this.jrb_daily.isSelected())
      str6 = "D";
    else if (this.jrb_weekly.isSelected())
      str6 = "W";
    else if (this.jrb_monthly.isSelected())
      str6 = "M";
    else if (this.jrb_demand.isSelected())
      str6 = "9";
    else
      str6 = "V";

    if (this.jrb_enabled.isSelected())
      str7 = "1";
    else
      str7 = "0";

    if (this.jrb_allow.isSelected())
      str8 = "Y";
    else
      str8 = "N";

    if (this.jcb_autoOff.isSelected())
      str9 = "Y";
    else
      str9 = "N";

    if (this.jcb_checkCalendar.isSelected())
      str10 = "Y";
    else
      str10 = "N";

    if (this.jcb_notCheckLastStatus.isSelected())
      str11 = "N";
    else
      str11 = "Y";

    if (this.jcb_triggerByTime.isSelected())
      str12 = "Y";
    else {
      str12 = "N";
    }

    String str13 = "N";

    for (int k = 0; k <= 23; ++k)
    {
      if (this.jrb_beginTW[k].isSelected())
      {
        i = k;
        break;
      }
    }

    for (int l = 0; l <= 23; ++l)
    {
      if (this.jrb_endTW[l].isSelected())
      {
        j = l;
        break;
      }

    }

    String str14 = this.mainFrame.getDBName();
    try
    {
      String str15;
      localStatement = this.dbcon.createStatement();

      if (str5.equals("")) {
        str15 = "INSERT INTO " + str14 + "ETL_Job" + "       (ETL_System, ETL_Job, ETL_Server, Description," + "        Frequency, JobType, Enable, AutoOff," + "        CubeFlag, CheckCalendar, CalendarBU, Last_JobStatus," + "        JobSessionID, ExpectedRecord, CheckLastStatus," + "        TimeTrigger)" + "   VALUES ('" + this.etlsys + "', '" + str1 + "', " + "           NULL, '" + str2 + "', '" + str3 + "', " + "           '" + str6 + "', '" + str7 + "', " + "           '" + str9 + "', '" + str13 + "', " + "           '" + str10 + "', '" + str4 + "'," + "           'Ready', 0, 0, '" + str11 + "'," + "           '" + str12 + "')";
      }
      else
      {
        str15 = "INSERT INTO " + str14 + "ETL_Job" + "       (ETL_System, ETL_Job, ETL_Server, Description," + "        Frequency, JobType, Enable, AutoOff," + "        CubeFlag, CheckCalendar, CalendarBU, Last_JobStatus," + "        JobSessionID, ExpectedRecord, CheckLastStatus," + "        TimeTrigger)" + "   VALUES ('" + this.etlsys + "', '" + str1 + "', " + "           '" + str5 + "', '" + str2 + "', '" + str3 + "', " + "           '" + str6 + "', '" + str7 + "', " + "           '" + str9 + "', '" + str13 + "', " + "           '" + str10 + "', '" + str4 + "'," + "           'Ready', 0, 0, '" + str11 + "'," + "           '" + str12 + "')";
      }

      i1 = localStatement.executeUpdate(str15);
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(this, "Could not insert a new ETL Job definition into database\n" + localSQLException1.getMessage(), "Error", 0);

      return;
    }
    try
    {
      String str16 = "INSERT INTO " + str14 + "ETL_Job_Timewindow" + "       (ETL_System, ETL_Job," + "        Allow, BeginHour, EndHour)" + "   VALUES ('" + this.etlsys + "', '" + str1 + "', " + "           '" + str8 + "', " + i + ", " + j + ")";

      i1 = localStatement.executeUpdate(str16);

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this, "Could not insert a new ETL Job TimeWindow record into database", "Error", 0);

      return;
    }

    this.mainFrame.getTreeView().addETLJob(this.parentNode, this.etlsys, str1);

    dispose();
  }

  private void updateData()
  {
    String str6;
    String str7;
    String str8;
    String str9;
    String str10;
    String str11;
    String str12;
    Statement localStatement;
    String str1 = this.jTextField1.getText().trim();
    String str2 = this.jTextField2.getText().trim();
    String str3 = this.jTextField3.getText().trim();
    String str4 = this.jTextField4.getText().trim();
    String str5 = (String)this.jcb_server.getSelectedItem();

    int i = 0; int j = 23;

    if ((!(str5.equals(""))) && 
      (isServerExist(str5) != 1)) {
      JOptionPane.showMessageDialog(this, "The server name does not exist\n", "Update Job", 0);

      return;
    }

    if (str3.equals(""))
      str3 = "0";

    if (this.jrb_daily.isSelected())
      str6 = "D";
    else if (this.jrb_weekly.isSelected())
      str6 = "W";
    else if (this.jrb_monthly.isSelected())
      str6 = "M";
    else if (this.jrb_demand.isSelected())
      str6 = "9";
    else
      str6 = "V";

    if (this.jrb_enabled.isSelected())
      str7 = "1";
    else
      str7 = "0";

    if (this.jrb_allow.isSelected())
      str8 = "Y";
    else
      str8 = "N";

    if (this.jcb_autoOff.isSelected())
      str9 = "Y";
    else
      str9 = "N";

    if (this.jcb_checkCalendar.isSelected())
      str10 = "Y";
    else
      str10 = "N";

    if (this.jcb_notCheckLastStatus.isSelected())
      str11 = "N";
    else
      str11 = "Y";

    if (this.jcb_triggerByTime.isSelected())
      str12 = "Y";
    else {
      str12 = "N";
    }

    String str13 = "N";

    for (int k = 0; k <= 23; ++k)
    {
      if (this.jrb_beginTW[k].isSelected())
      {
        i = k;
        break;
      }
    }

    for (int l = 0; l <= 23; ++l)
    {
      if (this.jrb_endTW[l].isSelected())
      {
        j = l;
        break;
      }

    }

    String str14 = this.mainFrame.getDBName();
    try
    {
      String str15;
      localStatement = this.dbcon.createStatement();

      if (str5.equals("")) {
        str15 = "UPDATE " + str14 + "ETL_Job SET" + "    ETL_Server = NULL," + "    Description = '" + str2 + "'," + "    Frequency = '" + str3 + "'," + "    Jobtype = '" + str6 + "'," + "    Enable = '" + str7 + "'," + "    AutoOff = '" + str9 + "'," + "    CubeFlag = '" + str13 + "'," + "    CheckCalendar = '" + str10 + "'," + "    CalendarBU = '" + str4 + "'," + "    CheckLastStatus = '" + str11 + "'," + "    TimeTrigger = '" + str12 + "'" + "   WHERE ETL_System = '" + this.etlsys + "'" + "     AND ETL_Job = '" + str1 + "'";
      }
      else
      {
        str15 = "UPDATE " + str14 + "ETL_Job SET" + "    ETL_Server = '" + str5 + "'," + "    Description = '" + str2 + "'," + "    Frequency = '" + str3 + "'," + "    Jobtype = '" + str6 + "'," + "    Enable = '" + str7 + "'," + "    AutoOff = '" + str9 + "'," + "    CubeFlag = '" + str13 + "'," + "    CheckCalendar = '" + str10 + "'," + "    CalendarBU = '" + str4 + "'," + "    CheckLastStatus = '" + str11 + "'," + "    TimeTrigger = '" + str12 + "'" + "   WHERE ETL_System = '" + this.etlsys + "'" + "     AND ETL_Job = '" + str1 + "'";
      }

      int i1 = localStatement.executeUpdate(str15);
    }
    catch (SQLException localSQLException1)
    {
      String str17 = "Could not update ETL Job definition\n" + localSQLException1.getMessage();
      JOptionPane.showMessageDialog(this, str17, "Error", 0);

      return;
    }
    try
    {
      String str16 = "UPDATE " + str14 + "ETL_Job_TimeWindow SET" + "    Allow = '" + str8 + "'," + "    BeginHour = " + i + "," + "    EndHour = " + j + "   WHERE ETL_System = '" + this.etlsys + "'" + "     AND ETL_Job = '" + str1 + "'";

      int i2 = localStatement.executeUpdate(str16);
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this, "Could not update ETL Job TimeWindow record", "Error", 0);

      return;
    }

    dispose();
  }

  private void selectData(String paramString1, String paramString2)
  {
    Statement localStatement;
    ResultSet localResultSet;
    String str1 = this.mainFrame.getDBName();
    try
    {
      String str2 = "SELECT ETL_Server, Description, Frequency, JobType, Enable, AutoOff, CubeFlag, CheckCalendar, CalendarBU, CheckLastStatus, TimeTrigger   FROM " + str1 + "ETL_Job" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      localStatement = this.dbcon.createStatement();
      localResultSet = localStatement.executeQuery(str2);

      if (localResultSet.next())
      {
        String str4 = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str4 = "";

        String str5 = localResultSet.getString(2);
        if (localResultSet.wasNull())
          str5 = "";

        String str6 = localResultSet.getString(3);
        if (localResultSet.wasNull())
          str6 = "";

        String str7 = localResultSet.getString(4);
        if (localResultSet.wasNull())
          str7 = "";

        String str8 = localResultSet.getString(5);
        if (localResultSet.wasNull())
          str8 = "0";

        String str9 = localResultSet.getString(6);
        if (localResultSet.wasNull())
          str9 = "N";

        String str10 = localResultSet.getString(7);
        if (localResultSet.wasNull())
          str10 = "N";

        String str11 = localResultSet.getString(8);
        if (localResultSet.wasNull())
          str11 = "N";

        String str12 = localResultSet.getString(9);
        if (localResultSet.wasNull())
          str12 = "";

        String str13 = localResultSet.getString(10);
        if (localResultSet.wasNull())
          str13 = "Y";

        String str14 = localResultSet.getString(11);
        if (localResultSet.wasNull())
          str14 = "N";

        this.jTextField1.setText(paramString2);
        this.jTextField2.setText(str5);
        this.jTextField3.setText(str6);
        this.jTextField4.setText(str12);
        this.jcb_server.setSelectedItem(str4);

        if (str7.equals("D"))
          this.jrb_daily.setSelected(true);
        else if (str7.equals("W"))
          this.jrb_weekly.setSelected(true);
        else if (str7.equals("M"))
          this.jrb_monthly.setSelected(true);
        else if (str7.equals("9"))
          this.jrb_demand.setSelected(true);
        else
          this.jrb_virtual.setSelected(true);

        if (str8.equals("1"))
          this.jrb_enabled.setSelected(true);
        else
          this.jrb_disabled.setSelected(true);

        if (str9.equals("Y")) {
          this.jcb_autoOff.setSelected(true);
        }

        if (str11.equals("Y"))
          this.jcb_checkCalendar.setSelected(true);

        if (str13.equals("N"))
          this.jcb_notCheckLastStatus.setSelected(true);

        if (str14.equals("Y")) {
          this.jcb_triggerByTime.setSelected(true);
          this.jbn_setTimeTrigger.setEnabled(true);
          this.jTextField3.setEnabled(false);
          this.jTextField4.setEnabled(false);
          this.jcb_checkCalendar.setEnabled(false);
          this.jrb_allow.setEnabled(false);
          this.jrb_notallow.setEnabled(false);
          resetTimeWindow(false);
        } else {
          this.jbn_setTimeTrigger.setEnabled(false);
          this.jTextField3.setEnabled(true);
          this.jTextField4.setEnabled(true);
          this.jcb_checkCalendar.setEnabled(true);
          this.jrb_allow.setEnabled(true);
          this.jrb_notallow.setEnabled(true);
          resetTimeWindow(true);
        }
      }

      localStatement.close();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(this, "Could not select ETL Job record from database", "Error", 0);

      return;
    }
    try
    {
      localStatement = this.dbcon.createStatement();
      localResultSet = localStatement.executeQuery("SELECT Allow, BeginHour, EndHour   FROM " + str1 + "ETL_Job_TimeWindow" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'");

      if (localResultSet.next())
      {
        String str3 = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str3 = "Y";

        if (str3.equals("Y"))
          this.jrb_allow.setSelected(true);
        else
          this.jrb_notallow.setSelected(true);

        int i = localResultSet.getInt(2);
        if (localResultSet.wasNull())
          i = 0;

        this.jrb_beginTW[i].setSelected(true);

        int j = localResultSet.getInt(3);
        if (localResultSet.wasNull())
          j = 23;

        this.jrb_endTW[j].setSelected(true);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this, "Could not select ETL Job TimeWindow from database", "Error", 0);

      return;
    }
  }

  private int isServerExist(String paramString)
  {
    int i;
    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      String str2 = "SELECT count(*) FROM " + str1 + "ETL_Server" + "   WHERE ETL_Server = '" + paramString + "'";

      ResultSet localResultSet = localStatement.executeQuery(str2);
      localResultSet.next();
      i = localResultSet.getInt(1);

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      return 0;
    }

    if (i == 1)
      return 1;

    return 0;
  }

  private void setTimeTrigger()
  {
    if (this.jrb_daily.isSelected()) {
      if (this.TTDailyDlg == null) {
        this.TTDailyDlg = new ETLTimeTriggerDailyDlg(this.mainFrame);
        this.TTDailyDlg.setSysJob(this.etlsys, this.jTextField1.getText().trim());
        this.TTDailyDlg.selectTimeTrigger();
      }

      this.TTDailyDlg.mapDataToUI();
      this.TTDailyDlg.show();
    } else if (this.jrb_weekly.isSelected()) {
      if (this.TTWeeklyDlg == null) {
        this.TTWeeklyDlg = new ETLTimeTriggerWeeklyDlg(this.mainFrame);
        this.TTWeeklyDlg.setSysJob(this.etlsys, this.jTextField1.getText().trim());
        this.TTWeeklyDlg.selectTimeTrigger();
      }

      this.TTWeeklyDlg.mapDataToUI();
      this.TTWeeklyDlg.show();
    } else if (this.jrb_monthly.isSelected()) {
      if (this.TTMonthlyDlg == null) {
        this.TTMonthlyDlg = new ETLTimeTriggerMonthlyDlg(this.mainFrame);
        this.TTMonthlyDlg.setSysJob(this.etlsys, this.jTextField1.getText().trim());
        this.TTMonthlyDlg.selectTimeTrigger();
      }

      this.TTMonthlyDlg.mapDataToUI();
      this.TTMonthlyDlg.show();
    } else if (this.jrb_demand.isSelected()) {
      if (this.TTDemandDlg == null) {
        this.TTDemandDlg = new ETLTimeTriggerDemandDlg(this.mainFrame);
        this.TTDemandDlg.setSysJob(this.etlsys, this.jTextField1.getText().trim());
        this.TTDemandDlg.selectTimeTrigger();
      }

      this.TTDemandDlg.mapDataToUI();
      this.TTDemandDlg.backupSelectedDay();
      this.TTDemandDlg.show();
    }
  }

  static ETLJobDlg access$000(ETLJobDlg paramETLJobDlg)
  {
    return paramETLJobDlg.dlg; } 
  static void access$100(ETLJobDlg paramETLJobDlg) { paramETLJobDlg.update(); } 
  static void access$200(ETLJobDlg paramETLJobDlg) { paramETLJobDlg.setTimeTrigger(); } 
  static InputJTextField access$300(ETLJobDlg paramETLJobDlg) { return paramETLJobDlg.jTextField3; } 
  static InputJTextField access$400(ETLJobDlg paramETLJobDlg) { return paramETLJobDlg.jTextField4; } 
  static JCheckBox access$500(ETLJobDlg paramETLJobDlg) { return paramETLJobDlg.jcb_autoOff; } 
  static JCheckBox access$600(ETLJobDlg paramETLJobDlg) { return paramETLJobDlg.jcb_triggerByTime; } 
  static JButton access$700(ETLJobDlg paramETLJobDlg) { return paramETLJobDlg.jbn_setTimeTrigger; } 
  static JCheckBox access$800(ETLJobDlg paramETLJobDlg) { return paramETLJobDlg.jcb_checkCalendar; } 
  static JCheckBox access$900(ETLJobDlg paramETLJobDlg) { return paramETLJobDlg.jcb_notCheckLastStatus; } 
  static JRadioButton access$1000(ETLJobDlg paramETLJobDlg) { return paramETLJobDlg.jrb_allow; } 
  static JRadioButton access$1100(ETLJobDlg paramETLJobDlg) { return paramETLJobDlg.jrb_notallow; } 
  static void access$1200(ETLJobDlg paramETLJobDlg, boolean paramBoolean) { paramETLJobDlg.resetTimeWindow(paramBoolean);
  }
}