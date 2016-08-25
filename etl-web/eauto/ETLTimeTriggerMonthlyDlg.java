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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EventObject;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ETLTimeTriggerMonthlyDlg extends JDialog
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
  private ETLTimeTriggerMonthlyDlg dlg;
  private int mode = 0;
  private ETLMainFrame mainFrame;
  private JCheckBox[] monthDay;
  private JCheckBox jcb_monthend;
  private Connection dbcon;
  private int startHour = 0;
  private int startMin = 0;
  private int offsetDay = 0;
  private String timing = "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN";
  private String endOfMonth = "N";
  private String system = "";
  private String job = "";

  public ETLTimeTriggerMonthlyDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "Set Monthly Time Trigger", true);
    setResizable(false);
    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);

    this.dbcon = this.mainFrame.getConnection();

    this.mode = 0;
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLTimeTriggerMonthlyDlg.1 local1 = new WindowAdapter(this) { private final ETLTimeTriggerMonthlyDlg this$0;

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

    this.jBnOK.addActionListener(new ActionListener(this) { private final ETLTimeTriggerMonthlyDlg this$0;

      public void actionPerformed() { ETLTimeTriggerMonthlyDlg.access$000(this.this$0);
        ETLTimeTriggerMonthlyDlg.access$100(this.this$0).dispose();
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLTimeTriggerMonthlyDlg this$0;

      public void actionPerformed() { ETLTimeTriggerMonthlyDlg.access$100(this.this$0).dispose();
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
    this.jsld_offsetDay.setBounds(new Rectangle(20, 80, 380, 40));

    this.jlb_offsetValue = new JLabel("0 day");
    this.jlb_offsetValue.setBounds(new Rectangle(410, 80, 100, 25));

    ETLTimeTriggerMonthlyDlg.SliderListener localSliderListener = new ETLTimeTriggerMonthlyDlg.SliderListener(this);
    this.jsld_offsetDay.addChangeListener(localSliderListener);

    this.jPanelSetting = new JPanel();
    this.jPanelSetting.setLayout(null);
    this.jPanelSetting.setBounds(new Rectangle(20, 140, 465, 150));
    this.jPanelSetting.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Monthly Setting"));

    this.monthDay = new JCheckBox[31];

    int i = 20;
    int j = 20;

    Color localColor = new Color(0, 0, 255);
    for (int k = 1; k <= 31; ++k) {
      this.monthDay[(k - 1)] = new JCheckBox(String.valueOf(k));
      this.monthDay[(k - 1)].setBounds(new Rectangle(i, j, 40, 25));
      this.monthDay[(k - 1)].setForeground(localColor);
      this.jPanelSetting.add(this.monthDay[(k - 1)]);

      if (k % 9 == 0) {
        j += 30;
        i = 20;
      } else {
        i += 45;
      }
    }

    this.jcb_monthend = new JCheckBox("End of Month");
    this.jcb_monthend.setForeground(new Color(255, 0, 0));
    this.jcb_monthend.setBounds(new Rectangle(i, j, 100, 25));
    this.jPanelSetting.add(this.jcb_monthend);

    this.jPanelData.add(this.jlb_startAt);
    this.jPanelData.add(this.jcb_hour);
    this.jPanelData.add(this.jlb_hour);
    this.jPanelData.add(this.jcb_min);
    this.jPanelData.add(this.jlb_min);
    this.jPanelData.add(this.jlb_offsetDay);
    this.jPanelData.add(this.jsld_offsetDay);
    this.jPanelData.add(this.jlb_offsetValue);

    this.jPanelData.add(this.jPanelSetting);

    setSize(new Dimension(510, 380));

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

      str2 = "SELECT Timing, EndOfMonth  FROM " + str1 + "ETL_TimeTrigger_Monthly" + " WHERE ETL_System = '" + this.system + "' AND ETL_Job = '" + this.job + "'";

      localResultSet = localStatement.executeQuery(str2);

      if (localResultSet.next())
      {
        this.timing = localResultSet.getString(1);
        if (localResultSet.wasNull())
          this.timing = "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN";

        this.endOfMonth = localResultSet.getString(2);
        if (localResultSet.wasNull())
          this.endOfMonth = "N";
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
      str2 = "INSERT INTO " + str1 + "ETL_TimeTrigger" + "   (ETL_System, ETL_Job, TriggerType, StartHour, StartMin," + "    OffsetDay)" + "  VALUES ('" + this.system + "', '" + this.job + "', 'M'," + this.startHour + "," + this.startMin + "," + this.offsetDay + ")";
    }
    else
    {
      str2 = "UPDATE " + str1 + "ETL_TimeTrigger" + "   SET TriggerType = 'M'," + "       StartHour = " + this.startHour + "," + "       StartMin = " + this.startMin + "," + "       OffsetDay = " + this.offsetDay + "  WHERE ETL_System = '" + this.system + "'" + "    AND ETL_Job = '" + this.job + "'";
    }

    try
    {
      int i = localStatement.executeUpdate(str2);

      str2 = "INSERT INTO " + str1 + "ETL_TimeTrigger_Monthly" + "   (ETL_System, ETL_Job, Timing, EndOfMonth)" + "  VALUES ('" + this.system + "', '" + this.job + "'," + "'" + this.timing + "', '" + this.endOfMonth + "')";

      i = localStatement.executeUpdate(str2);

      localStatement.close();
    }
    catch (SQLException localSQLException2) {
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
    else
      this.jlb_offsetValue.setText(this.offsetDay + " day");

    for (int i = 0; i < 31; ++i)
      if (this.timing.charAt(i) == 'Y')
        this.monthDay[i].setSelected(true);
      else
        this.monthDay[i].setSelected(false);


    if (this.endOfMonth.equals("Y"))
      this.jcb_monthend.setSelected(true);
    else
      this.jcb_monthend.setSelected(false);
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

    StringBuffer localStringBuffer = new StringBuffer(31);
    for (int i = 0; i < 31; ++i)
      if (this.monthDay[i].isSelected())
        localStringBuffer.append('Y');
      else
        localStringBuffer.append('N');


    this.timing = localStringBuffer.toString();

    if (this.jcb_monthend.isSelected())
      this.endOfMonth = "Y";
    else
      this.endOfMonth = "N";
  }

  static void access$000(ETLTimeTriggerMonthlyDlg paramETLTimeTriggerMonthlyDlg)
  {
    paramETLTimeTriggerMonthlyDlg.mapUIToData(); } 
  static ETLTimeTriggerMonthlyDlg access$100(ETLTimeTriggerMonthlyDlg paramETLTimeTriggerMonthlyDlg) { return paramETLTimeTriggerMonthlyDlg.dlg; } 
  static JSlider access$200(ETLTimeTriggerMonthlyDlg paramETLTimeTriggerMonthlyDlg) { return paramETLTimeTriggerMonthlyDlg.jsld_offsetDay; } 
  static JLabel access$300(ETLTimeTriggerMonthlyDlg paramETLTimeTriggerMonthlyDlg) { return paramETLTimeTriggerMonthlyDlg.jlb_offsetValue;
  }

  class SliderListener
  implements ChangeListener
  {
    private final ETLTimeTriggerMonthlyDlg this$0;

    public SliderListener()
    {
      this.this$0 = paramETLTimeTriggerMonthlyDlg; }

    public void stateChanged() {
      JSlider localJSlider = (JSlider)paramChangeEvent.getSource();

      if (localJSlider.equals(ETLTimeTriggerMonthlyDlg.access$200(this.this$0)))
        if (localJSlider.getValue() > 1)
          ETLTimeTriggerMonthlyDlg.access$300(this.this$0).setText(localJSlider.getValue() + " days");
        else
          ETLTimeTriggerMonthlyDlg.access$300(this.this$0).setText(localJSlider.getValue() + " day");
    }
  }
}