import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.EventObject;
import java.util.GregorianCalendar;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ETLTimeTriggerDemandDlg extends JDialog
{
  private JPanel jPanelButton;
  private JPanel jPanelData;
  private JPanel jPanelSetting;
  private JButton jBnOK;
  private JButton jBnCancel;
  private JLabel jlb_startAt;
  private JLabel jlb_hour;
  private JLabel jlb_min;
  private JLabel jlb_offsetDay;
  private JLabel jlb_offsetValue;
  private JComboBox jcb_hour;
  private JComboBox jcb_min;
  private JSlider jsld_offsetDay;
  private ETLTimeTriggerDemandDlg dlg;
  private JButton jbn_prev;
  private JButton jbn_next;
  private int mode = 0;
  private ETLMainFrame mainFrame;
  private MonthSelectPad[] months;
  private static int monthCount = 6;
  private Connection dbcon;
  private int startHour = 0;
  private int startMin = 0;
  private int offsetDay = 0;
  private int currentYear;
  private int currentMonth;
  private int currentDay;
  private int editYear;
  private int editMonth;
  private String system = "";
  private String job = "";
  private int[][][] selectedDay;
  private int[][][] backupDay;

  public ETLTimeTriggerDemandDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "Set Demand Time Trigger", true);
    setResizable(false);
    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);

    this.dbcon = this.mainFrame.getConnection();

    this.selectedDay = new int[5][12][31];
    this.backupDay = new int[5][12][31];
    for (int i = 0; i < 5; ++i)
      for (int j = 0; j < 12; ++j)
        for (int k = 0; k < 31; ++k) {
          this.selectedDay[i][j][k] = 0;
          this.backupDay[i][j][k] = 0;
        }



    this.mode = 0;
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLTimeTriggerDemandDlg.1 local1 = new WindowAdapter(this) { private final ETLTimeTriggerDemandDlg this$0;

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

    this.jBnOK = new JButton("OK");
    this.jBnOK.setMnemonic(79);
    this.jBnCancel = new JButton("Cancel");
    this.jBnCancel.setMnemonic(67);

    this.jBnOK.addActionListener(new ActionListener(this) { private final ETLTimeTriggerDemandDlg this$0;

      public void actionPerformed() { ETLTimeTriggerDemandDlg.access$000(this.this$0);
        ETLTimeTriggerDemandDlg.access$100(this.this$0);
        ETLTimeTriggerDemandDlg.access$200(this.this$0).dispose();
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLTimeTriggerDemandDlg this$0;

      public void actionPerformed() { for (int i = 0; i < 5; ++i) {
          for (int j = 0; j < 12; ++j)
            for (int k = 0; k < 31; ++k)
              ETLTimeTriggerDemandDlg.access$300(this.this$0)[i][j][k] = ETLTimeTriggerDemandDlg.access$400(this.this$0)[i][j][k];


        }

        ETLTimeTriggerDemandDlg.access$200(this.this$0).dispose();
      }

    });
    this.jPanelButton.add(this.jBnOK);
    this.jPanelButton.add(this.jBnCancel);

    localContainer.add(this.jPanelData, "Center");
    localContainer.add(this.jPanelButton, "South");

    this.jlb_startAt = new JLabel("Start At:");
    this.jlb_startAt.setBounds(new Rectangle(20, 20, 80, 25));

    this.jcb_hour = new JComboBox();
    this.jcb_hour.setBounds(new Rectangle(100, 20, 45, 25));

    this.jlb_hour = new JLabel("Hour");
    this.jlb_hour.setBounds(new Rectangle(150, 20, 30, 25));

    this.jcb_min = new JComboBox();
    this.jcb_min.setBounds(new Rectangle(180, 20, 45, 25));

    this.jlb_min = new JLabel("Minute");
    this.jlb_min.setBounds(new Rectangle(230, 20, 40, 25));

    this.jlb_offsetDay = new JLabel("Offset day number for TxDate:");
    this.jlb_offsetDay.setBounds(new Rectangle(20, 50, 250, 25));

    initStartTime(this.jcb_hour, this.jcb_min);

    this.jsld_offsetDay = new JSlider(0, 0, 30, 0);
    this.jsld_offsetDay.setMajorTickSpacing(5);
    this.jsld_offsetDay.setMinorTickSpacing(1);
    this.jsld_offsetDay.setPaintTicks(true);
    this.jsld_offsetDay.setPaintLabels(true);
    this.jsld_offsetDay.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    this.jsld_offsetDay.setBounds(new Rectangle(20, 80, 480, 40));

    this.jlb_offsetValue = new JLabel("0 day");
    this.jlb_offsetValue.setBounds(new Rectangle(510, 80, 100, 25));

    ETLTimeTriggerDemandDlg.SliderListener localSliderListener = new ETLTimeTriggerDemandDlg.SliderListener(this);
    this.jsld_offsetDay.addChangeListener(localSliderListener);

    this.jPanelSetting = new JPanel();
    this.jPanelSetting.setLayout(null);
    this.jPanelSetting.setBounds(new Rectangle(15, 130, 560, 370));
    this.jPanelSetting.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Calendar Setting"));

    this.jbn_prev = new JButton("Previous");
    this.jbn_prev.setBounds(new Rectangle(150, 15, 100, 25));
    this.jbn_prev.setEnabled(false);
    this.jbn_next = new JButton("Next");
    this.jbn_next.setBounds(new Rectangle(300, 15, 100, 25));

    this.jPanelSetting.add(this.jbn_prev);
    this.jPanelSetting.add(this.jbn_next);

    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    this.currentYear = localGregorianCalendar.get(1);
    this.currentMonth = (localGregorianCalendar.get(2) + 1);
    this.currentDay = localGregorianCalendar.get(5);

    this.editYear = this.currentYear;
    this.editMonth = (this.currentMonth / monthCount * monthCount + 1);

    this.months = new MonthSelectPad[monthCount];
    Color localColor = new Color(0, 255, 0);
    int i = 15; int j = 45;
    for (int k = 0; k < monthCount; ++k) {
      if (k == 0) {
        i = 15;
        j = 45;
      }
      else if (k == 3) {
        i = 15;
        j = 205;
      }

      this.months[k] = new MonthSelectPad();
      this.months[k].setSelectColor(localColor);
      this.months[k].setBounds(new Rectangle(i, j, 180, 160));
      this.months[k].init(this.editYear, this.editMonth + k);

      if ((this.editYear == this.currentYear) && (this.editMonth + k < this.currentMonth))
        this.months[k].disableAllDay();
      else if ((this.editYear == this.currentYear) && (this.editMonth + k == this.currentMonth))
        this.months[k].disableDay(this.currentDay - 1);

      this.jPanelSetting.add(this.months[k]);
      i += 180;
    }

    this.jbn_prev.addActionListener(new ActionListener(this) { private final ETLTimeTriggerDemandDlg this$0;

      public void actionPerformed() { ETLTimeTriggerDemandDlg.access$500(ETLTimeTriggerDemandDlg.access$200(this.this$0));
      }

    });
    this.jbn_next.addActionListener(new ActionListener(this) { private final ETLTimeTriggerDemandDlg this$0;

      public void actionPerformed() { ETLTimeTriggerDemandDlg.access$600(ETLTimeTriggerDemandDlg.access$200(this.this$0));
      }

    });
    this.jPanelData.add(this.jlb_startAt);
    this.jPanelData.add(this.jcb_hour);
    this.jPanelData.add(this.jlb_hour);
    this.jPanelData.add(this.jcb_min);
    this.jPanelData.add(this.jlb_min);
    this.jPanelData.add(this.jlb_offsetDay);
    this.jPanelData.add(this.jsld_offsetDay);
    this.jPanelData.add(this.jlb_offsetValue);

    this.jPanelData.add(this.jPanelSetting);

    setSize(new Dimension(600, 580));

    Dimension localDimension1 = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension localDimension2 = getSize();

    if (localDimension2.height > localDimension1.height)
      localDimension2.height = localDimension1.height;

    if (localDimension2.width > localDimension1.width)
      localDimension2.width = localDimension1.width;

    setLocation((localDimension1.width - localDimension2.width) / 2, (localDimension1.height - localDimension2.height) / 2);
  }

  private void initStartTime(JComboBox paramJComboBox1, JComboBox paramJComboBox2)
  {
    for (int i = 0; i < 24; ++i) {
      paramJComboBox1.addItem(String.valueOf(i));
    }

    for (i = 0; i < 60; ++i)
      paramJComboBox2.addItem(String.valueOf(i));
  }

  public void setSysJob(String paramString1, String paramString2)
  {
    this.system = paramString1;
    this.job = paramString2;
  }

  public int updateTimeTrigger()
  {
    if (deleteTimeTrigger() != 0)
      return -1;

    if (insertTimeTrigger() != 0)
      return -1;

    return 0;
  }

  public int selectTimeTrigger()
  {
    Statement localStatement;
    ResultSet localResultSet;
    String str1 = this.mainFrame.getDBName();
    String str2 = "";
    try
    {
      localStatement = this.dbcon.createStatement();

      str2 = "SELECT StartHour, StartMin, OffsetDay   FROM " + str1 + "ETL_TimeTrigger" + " WHERE ETL_System = '" + this.system + "' AND ETL_Job = '" + this.job + "'";

      localResultSet = localStatement.executeQuery(str2);

      if (localResultSet.next()) {
        this.startHour = localResultSet.getInt(1);
        if (localResultSet.wasNull())
          this.startHour = 0;

        this.startMin = localResultSet.getInt(2);
        if (localResultSet.wasNull())
          this.startMin = 0;

        this.offsetDay = localResultSet.getInt(3);
        if (localResultSet.wasNull())
          this.offsetDay = 0;

        this.mode = 1;
      }

      localStatement.close();
    }
    catch (SQLException localSQLException1) {
      System.out.println(localSQLException1.getMessage());
      return -1;
    }
    try
    {
      localStatement = this.dbcon.createStatement();

      str2 = "SELECT Seq, YearNum, MonthNum, DayNum   FROM " + str1 + "ETL_TimeTrigger_Calendar" + " WHERE ETL_System = '" + this.system + "' AND ETL_Job = '" + this.job + "'" + " ORDER BY Seq";

      localResultSet = localStatement.executeQuery(str2);

      while (localResultSet.next()) {
        int i = localResultSet.getInt(2);
        int j = localResultSet.getInt(3);
        int k = localResultSet.getInt(4);

        if ((i >= this.currentYear) && (i <= this.currentYear + 4) && 
          (j >= 1) && (j <= 12) && (k >= 1)) { if (k > 31)
            continue;

          if ((i == this.currentYear) && (j == this.currentMonth) && (k < this.currentDay))
            continue;

          int l = i - this.currentYear;
          this.selectedDay[l][(j - 1)][(k - 1)] = 1;
        }
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2) {
      return -1;
    }

    return 0;
  }

  private int insertTimeTrigger()
  {
    Statement localStatement;
    String str1 = this.mainFrame.getDBName();
    String str2 = "";
    try
    {
      localStatement = this.dbcon.createStatement();
    }
    catch (SQLException localSQLException1) {
      return -1;
    }
    if (this.mode == 0) {
      str2 = "INSERT INTO " + str1 + "ETL_TimeTrigger" + "   (ETL_System, ETL_Job, TriggerType, StartHour, StartMin," + "    OffsetDay)" + "  VALUES ('" + this.system + "', '" + this.job + "', '9'," + this.startHour + "," + this.startMin + "," + this.offsetDay + ")";
    }
    else
    {
      str2 = "UPDATE " + str1 + "ETL_TimeTrigger" + "   SET TriggerType = '9'," + "       StartHour = " + this.startHour + "," + "       StartMin = " + this.startMin + "," + "       OffsetDay = " + this.offsetDay + "  WHERE ETL_System = '" + this.system + "'" + "    AND ETL_Job = '" + this.job + "'";
    }

    try
    {
      int i = localStatement.executeUpdate(str2);

      localStatement.close();
    }
    catch (SQLException localSQLException2) {
      return -1;
    }
    try
    {
      int i3;
      str2 = "INSERT INTO " + str1 + "ETL_TimeTrigger_Calendar" + "            (etl_system, etl_job, seq, yearnum, monthnum, daynum)" + "   VALUES ('" + this.system + "', '" + this.job + "', ?,?,?,?)";

      PreparedStatement localPreparedStatement = this.dbcon.prepareStatement(str2);

      int j = 1;

      if (this.currentMonth <= 6)
        i3 = 1;
      else
        i3 = 7;

      for (int l = 0; l < 5; ++l)
        for (int i1 = 0; i1 < 12; ++i1)
          for (int i2 = 0; i2 < 31; ++i2)
            if (this.selectedDay[l][i1][i2] == 1) {
              localPreparedStatement.setInt(1, j++);
              localPreparedStatement.setInt(2, this.currentYear + l);
              localPreparedStatement.setInt(3, i3 + i1);
              localPreparedStatement.setInt(4, i2 + 1);

              int k = localPreparedStatement.executeUpdate();
            }



    }
    catch (SQLException localSQLException3)
    {
      JOptionPane.showMessageDialog(this, "Could not insert a time trigger into database\n" + localSQLException3.getMessage(), "Error", 0);

      return -1;
    }

    return 0;
  }

  private int deleteTimeTrigger()
  {
    Statement localStatement;
    String str1 = this.mainFrame.getDBName();
    String str2 = "";
    try
    {
      localStatement = this.dbcon.createStatement();
    }
    catch (SQLException localSQLException1) {
      return -1;
    }
    try
    {
      str2 = "DELETE FROM " + str1 + "ETL_TimeTrigger_Weekly" + "   WHERE ETL_System = '" + this.system + "'" + "     AND ETL_Job = '" + this.job + "'";

      int i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_TimeTrigger_Monthly" + "   WHERE ETL_System = '" + this.system + "'" + "     AND ETL_Job = '" + this.job + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_TimeTrigger_Calendar" + "   WHERE ETL_System = '" + this.system + "'" + "     AND ETL_Job = '" + this.job + "'";

      i = localStatement.executeUpdate(str2);

      localStatement.close();
    }
    catch (SQLException localSQLException2) {
      System.out.println(localSQLException2.getMessage());
      return -1;
    }

    return 0;
  }

  public void mapDataToUI()
  {
    this.jcb_hour.setSelectedIndex(this.startHour);
    this.jcb_min.setSelectedIndex(this.startMin);

    this.jsld_offsetDay.setValue(this.offsetDay);

    if (this.offsetDay > 1)
      this.jlb_offsetValue.setText(this.offsetDay + " days");
    else {
      this.jlb_offsetValue.setText(this.offsetDay + " day");
    }

    for (int i = 0; i < monthCount; ++i) {
      this.months[i].setVisible(false);
      this.months[i].enableDay();

      if ((this.editYear == this.currentYear) && (this.editMonth + i < this.currentMonth))
        this.months[i].disableAllDay();
      else if ((this.editYear == this.currentYear) && (this.editMonth + i == this.currentMonth))
        this.months[i].disableDay(this.currentDay - 1);

      this.months[i].setSelectedDay1(this.selectedDay[(this.editYear - this.currentYear)][(this.editMonth + i - 1)]);

      this.months[i].setVisible(true);
    }
  }

  private void mapUIToData()
  {
    this.startHour = this.jcb_hour.getSelectedIndex();
    this.startMin = this.jcb_min.getSelectedIndex();

    if (this.startHour == -1)
      this.startHour = 0;

    if (this.startMin == -1)
      this.startMin = 0;

    this.offsetDay = this.jsld_offsetDay.getValue();
  }

  private void prevMonths()
  {
    getSelectedDay();

    this.editMonth -= monthCount;

    if (this.editMonth < 1) {
      this.editYear -= 1;
      this.editMonth = (12 - monthCount + 1);
    }

    for (int i = 0; i < monthCount; ++i) {
      this.months[i].setVisible(false);
      this.months[i].init(this.editYear, this.editMonth + i);
      this.months[i].enableDay();

      if ((this.editYear == this.currentYear) && (this.editMonth + i < this.currentMonth))
        this.months[i].disableAllDay();
      else if ((this.editYear == this.currentYear) && (this.editMonth + i == this.currentMonth))
        this.months[i].disableDay(this.currentDay - 1);

      this.months[i].setSelectedDay1(this.selectedDay[(this.editYear - this.currentYear)][(this.editMonth + i - 1)]);

      this.months[i].setVisible(true);
    }

    this.jbn_next.setEnabled(true);

    if ((this.editYear == this.currentYear) && (this.editMonth <= this.currentMonth))
      this.jbn_prev.setEnabled(false);
  }

  private void nextMonths()
  {
    getSelectedDay();

    this.editMonth += monthCount;
    if (this.editMonth > 12) {
      this.editYear += 1;
      this.editMonth = 1;
    }

    for (int i = 0; i < monthCount; ++i) {
      this.months[i].setVisible(false);
      this.months[i].init(this.editYear, this.editMonth + i);
      this.months[i].enableDay();

      this.months[i].setSelectedDay1(this.selectedDay[(this.editYear - this.currentYear)][(this.editMonth + i - 1)]);

      this.months[i].setVisible(true);
    }

    this.jbn_prev.setEnabled(true);

    if ((this.editYear == this.currentYear + 4) && (this.editMonth > monthCount))
      this.jbn_next.setEnabled(false);
  }

  private void getSelectedDay()
  {
    for (int i = 0; i < monthCount; ++i) {
      int[] arrayOfInt = this.months[i].getSelectedDay1();

      int j = this.editMonth + i - 1;
      int k = this.editYear - this.currentYear;

      for (int l = 0; l < 31; ++l)
        this.selectedDay[k][j][l] = arrayOfInt[l];
    }
  }

  public void backupSelectedDay()
  {
    for (int i = 0; i < 5; ++i)
      for (int j = 0; j < 12; ++j)
        for (int k = 0; k < 31; ++k)
          this.backupDay[i][j][k] = this.selectedDay[i][j][k];
  }

  static void access$000(ETLTimeTriggerDemandDlg paramETLTimeTriggerDemandDlg)
  {
    paramETLTimeTriggerDemandDlg.mapUIToData(); } 
  static void access$100(ETLTimeTriggerDemandDlg paramETLTimeTriggerDemandDlg) { paramETLTimeTriggerDemandDlg.getSelectedDay(); } 
  static ETLTimeTriggerDemandDlg access$200(ETLTimeTriggerDemandDlg paramETLTimeTriggerDemandDlg) { return paramETLTimeTriggerDemandDlg.dlg; } 
  static int[][][] access$300(ETLTimeTriggerDemandDlg paramETLTimeTriggerDemandDlg) { return paramETLTimeTriggerDemandDlg.selectedDay; } 
  static int[][][] access$400(ETLTimeTriggerDemandDlg paramETLTimeTriggerDemandDlg) { return paramETLTimeTriggerDemandDlg.backupDay; } 
  static void access$500(ETLTimeTriggerDemandDlg paramETLTimeTriggerDemandDlg) { paramETLTimeTriggerDemandDlg.prevMonths(); } 
  static void access$600(ETLTimeTriggerDemandDlg paramETLTimeTriggerDemandDlg) { paramETLTimeTriggerDemandDlg.nextMonths(); } 
  static JSlider access$700(ETLTimeTriggerDemandDlg paramETLTimeTriggerDemandDlg) { return paramETLTimeTriggerDemandDlg.jsld_offsetDay; } 
  static JLabel access$800(ETLTimeTriggerDemandDlg paramETLTimeTriggerDemandDlg) { return paramETLTimeTriggerDemandDlg.jlb_offsetValue;
  }

  class SliderListener
  implements ChangeListener
  {
    private final ETLTimeTriggerDemandDlg this$0;

    public SliderListener()
    {
      this.this$0 = paramETLTimeTriggerDemandDlg; }

    public void stateChanged() {
      JSlider localJSlider = (JSlider)paramChangeEvent.getSource();

      if (localJSlider.equals(ETLTimeTriggerDemandDlg.access$700(this.this$0)))
        if (localJSlider.getValue() > 1)
          ETLTimeTriggerDemandDlg.access$800(this.this$0).setText(localJSlider.getValue() + " days");
        else
          ETLTimeTriggerDemandDlg.access$800(this.this$0).setText(localJSlider.getValue() + " day");
    }
  }
}