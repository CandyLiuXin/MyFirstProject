import java.awt.BorderLayout;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EventObject;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;

public class ETLSourceDlg extends JDialog
{
  private JPanel jPanelButton;
  private JPanel jPanelData;
  private JButton jBnUpdate;
  private JButton jBnCancel;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JLabel jlBeforeHour;
  private JLabel jlBeforeMin;
  private JLabel jlOffsetDay;
  private InputJTextField jTextField1;
  private InputJTextField jTextField2;
  private JCheckBox jcbFilter;
  private JCheckBox jcbAlert;
  private JSlider jsldBeforeHour;
  private JSlider jsldBeforeMin;
  private JSlider jsldOffsetDay;
  private ETLSourceDlg dlg;
  private DefaultMutableTreeNode parentNode;
  private String etlsys;
  private String etltable;
  private Connection dbcon;
  private int mode = 0;
  private ETLMainFrame mainFrame;

  public ETLSourceDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "ETL Job Source", true);
    setResizable(false);
    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLSourceDlg.1 local1 = new WindowAdapter(this) { private final ETLSourceDlg this$0;

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

    this.jBnUpdate.addActionListener(new ActionListener(this) { private final ETLSourceDlg this$0;

      public void actionPerformed() { ETLSourceDlg.access$100(ETLSourceDlg.access$000(this.this$0));
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLSourceDlg this$0;

      public void actionPerformed() { ETLSourceDlg.access$000(this.this$0).dispose();
      }

    });
    this.jPanelButton.add(this.jBnUpdate);
    this.jPanelButton.add(this.jBnCancel);

    localContainer.add(this.jPanelData, "Center");
    localContainer.add(this.jPanelButton, "South");

    this.jLabel1 = new JLabel("Job Source Name:");
    this.jLabel1.setBounds(new Rectangle(23, 20, 160, 25));

    this.jLabel2 = new JLabel("Converted Job Source:");
    this.jLabel2.setBounds(new Rectangle(23, 50, 160, 25));

    this.jcbFilter = new JCheckBox(" Filter out duplicate data file ");
    this.jcbFilter.setBounds(new Rectangle(23, 80, 300, 25));

    this.jcbAlert = new JCheckBox(" Alert user when file is missing ");
    this.jcbAlert.setBounds(new Rectangle(23, 110, 300, 25));

    ETLSourceDlg.AlertListener localAlertListener = new ETLSourceDlg.AlertListener(this);
    this.jcbAlert.addChangeListener(localAlertListener);

    this.jlBeforeHour = new JLabel("Before Hour: 0 oclock");
    this.jlBeforeHour.setBounds(new Rectangle(23, 140, 180, 25));

    this.jsldBeforeHour = new JSlider(0, 0, 23, 0);
    this.jsldBeforeHour.setMajorTickSpacing(4);
    this.jsldBeforeHour.setMinorTickSpacing(1);
    this.jsldBeforeHour.setPaintTicks(true);
    this.jsldBeforeHour.setPaintLabels(true);
    this.jsldBeforeHour.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    this.jsldBeforeHour.setBounds(new Rectangle(23, 170, 350, 45));

    this.jlBeforeMin = new JLabel("Before Min: 0 minute");
    this.jlBeforeMin.setBounds(new Rectangle(23, 220, 180, 25));

    this.jsldBeforeMin = new JSlider(0, 0, 59, 0);
    this.jsldBeforeMin.setMajorTickSpacing(10);
    this.jsldBeforeMin.setMinorTickSpacing(5);
    this.jsldBeforeMin.setPaintTicks(true);
    this.jsldBeforeMin.setPaintLabels(true);
    this.jsldBeforeMin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    this.jsldBeforeMin.setBounds(new Rectangle(23, 250, 350, 45));

    this.jlOffsetDay = new JLabel("Offset Day: 0 day");
    this.jlOffsetDay.setBounds(new Rectangle(23, 300, 180, 25));

    this.jsldOffsetDay = new JSlider(0, 0, 31, 0);
    this.jsldOffsetDay.setMajorTickSpacing(10);
    this.jsldOffsetDay.setMinorTickSpacing(5);
    this.jsldOffsetDay.setPaintTicks(true);
    this.jsldOffsetDay.setPaintLabels(true);
    this.jsldOffsetDay.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    this.jsldOffsetDay.setBounds(new Rectangle(23, 330, 350, 45));

    ETLSourceDlg.SliderListener localSliderListener1 = new ETLSourceDlg.SliderListener(this, this.jlBeforeHour);
    this.jsldBeforeHour.addChangeListener(localSliderListener1);

    ETLSourceDlg.SliderListener localSliderListener2 = new ETLSourceDlg.SliderListener(this, this.jlBeforeMin);
    this.jsldBeforeMin.addChangeListener(localSliderListener2);

    ETLSourceDlg.SliderListener localSliderListener3 = new ETLSourceDlg.SliderListener(this, this.jlOffsetDay);
    this.jsldOffsetDay.addChangeListener(localSliderListener3);

    this.jTextField1 = new InputJTextField();
    this.jTextField1.setBounds(new Rectangle(165, 20, 150, 25));
    this.jTextField1.setInputLimited(30);

    this.jTextField2 = new InputJTextField(InputJTextField.UPPERCASE);
    this.jTextField2.setBounds(new Rectangle(165, 50, 200, 25));
    this.jTextField2.setInputLimited(50);

    this.jPanelData.add(this.jLabel1);
    this.jPanelData.add(this.jLabel2);
    this.jPanelData.add(this.jcbFilter);
    this.jPanelData.add(this.jcbAlert);
    this.jPanelData.add(this.jsldBeforeHour);
    this.jPanelData.add(this.jsldBeforeMin);
    this.jPanelData.add(this.jsldOffsetDay);
    this.jPanelData.add(this.jlBeforeHour);
    this.jPanelData.add(this.jlBeforeMin);
    this.jPanelData.add(this.jlOffsetDay);

    this.jPanelData.add(this.jTextField1);
    this.jPanelData.add(this.jTextField2);

    this.jsldBeforeHour.setEnabled(false);
    this.jsldBeforeMin.setEnabled(false);
    this.jsldOffsetDay.setEnabled(false);

    setSize(new Dimension(400, 470));

    Dimension localDimension1 = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension localDimension2 = getSize();

    if (localDimension2.height > localDimension1.height)
      localDimension2.height = localDimension1.height;

    if (localDimension2.width > localDimension1.width)
      localDimension2.width = localDimension1.width;

    setLocation((localDimension1.width - localDimension2.width) / 2, (localDimension1.height - localDimension2.height) / 2);
  }

  public void add(Connection paramConnection, DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString1, String paramString2)
  {
    this.mode = 1;

    this.dbcon = paramConnection;
    this.parentNode = paramDefaultMutableTreeNode;
    this.etlsys = paramString1;
    this.etltable = paramString2;

    show();
  }

  public void modify(Connection paramConnection, DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString1, String paramString2, String paramString3)
  {
    this.mode = 2;

    this.dbcon = paramConnection;
    this.parentNode = paramDefaultMutableTreeNode;
    this.etlsys = paramString1;
    this.etltable = paramString2;

    selectData(paramString3);

    this.jTextField1.setEditable(false);

    show();
  }

  private void update()
  {
    if (this.mode == 1)
      insertData();
    else
      updateData();
  }

  private void insertData()
  {
    String str3;
    String str4;
    String str1 = this.jTextField1.getText().trim();
    String str2 = this.jTextField2.getText().trim();

    int i = this.jsldBeforeHour.getValue();
    int j = this.jsldBeforeMin.getValue();
    int k = this.jsldOffsetDay.getValue();

    if (this.jcbFilter.isSelected())
      str3 = "1";
    else
      str3 = "0";

    if (this.jcbAlert.isSelected())
      str4 = "1";
    else {
      str4 = "0";
    }

    String str5 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      int l = localStatement.executeUpdate("INSERT INTO " + str5 + "ETL_Job_Source" + "      (Source, ETL_System, ETL_Job," + "       Conv_File_Head, AutoFilter, Alert," + "       BeforeHour, BeforeMin, OffsetDay, LastCount)" + "   VALUES ( '" + str1 + "', '" + this.etlsys + "', '" + this.etltable + "'," + "            '" + str2 + "', '" + str3 + "', '" + str4 + "'," + i + "," + j + "," + k + ", 0)");

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      JOptionPane.showMessageDialog(this, "Could not insert a new job source record into database" + localSQLException.getMessage(), "Error", 0);

      return;
    }

    this.mainFrame.getTreeView().addJobSource(this.parentNode, str1);

    dispose();
  }

  private void updateData()
  {
    String str3;
    String str4;
    String str1 = this.jTextField1.getText().trim();
    String str2 = this.jTextField2.getText().trim();

    int i = this.jsldBeforeHour.getValue();
    int j = this.jsldBeforeMin.getValue();
    int k = this.jsldOffsetDay.getValue();

    if (this.jcbFilter.isSelected())
      str3 = "1";
    else
      str3 = "0";

    if (this.jcbAlert.isSelected())
      str4 = "1";
    else {
      str4 = "0";
    }

    String str5 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      int l = localStatement.executeUpdate("UPDATE " + str5 + "ETL_Job_Source SET" + "      Conv_File_Head = '" + str2 + "'," + "      AutoFilter = '" + str3 + "'," + "      Alert = '" + str4 + "'," + "      BeforeHour = " + i + "," + "      BeforeMin = " + j + "," + "      OffsetDay = " + k + "   WHERE Source = '" + str1 + "'");

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      JOptionPane.showMessageDialog(this, "Could not update job source record", "Error", 0);

      return;
    }

    dispose();
  }

  private void selectData(String paramString)
  {
    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      ResultSet localResultSet = localStatement.executeQuery("SELECT Conv_File_Head, AutoFilter, Alert, BeforeHour, BeforeMin, OffsetDay   FROM " + str1 + "ETL_Job_Source" + "   WHERE Source = '" + paramString + "'");

      if (localResultSet.next())
      {
        String str2 = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str2 = "";

        String str3 = localResultSet.getString(2);
        if (localResultSet.wasNull())
          str3 = "0";

        String str4 = localResultSet.getString(3);
        if (localResultSet.wasNull())
          str4 = "0";

        int i = localResultSet.getInt(4);
        int j = localResultSet.getInt(5);
        int k = localResultSet.getInt(6);

        this.jTextField1.setText(paramString);
        this.jTextField2.setText(str2);

        if (str3.equals("1"))
          this.jcbFilter.setSelected(true);
        else
          this.jcbFilter.setSelected(false);

        if (str4.equals("1"))
          this.jcbAlert.setSelected(true);
        else
          this.jcbAlert.setSelected(false);

        this.jsldBeforeHour.setValue(i);
        this.jsldBeforeMin.setValue(j);
        this.jsldOffsetDay.setValue(k);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      JOptionPane.showMessageDialog(this, "Could not select job source record from database\n" + localSQLException.getMessage(), "Error", 0);

      return;
    }
  }

  static ETLSourceDlg access$000(ETLSourceDlg paramETLSourceDlg)
  {
    return paramETLSourceDlg.dlg; } 
  static void access$100(ETLSourceDlg paramETLSourceDlg) { paramETLSourceDlg.update(); } 
  static JSlider access$200(ETLSourceDlg paramETLSourceDlg) { return paramETLSourceDlg.jsldBeforeHour; } 
  static JSlider access$300(ETLSourceDlg paramETLSourceDlg) { return paramETLSourceDlg.jsldBeforeMin; } 
  static JSlider access$400(ETLSourceDlg paramETLSourceDlg) { return paramETLSourceDlg.jsldOffsetDay;
  }

  class AlertListener
  implements ChangeListener
  {
    private final ETLSourceDlg this$0;

    public AlertListener()
    {
      this.this$0 = paramETLSourceDlg;
    }

    public void stateChanged() {
      JCheckBox localJCheckBox = (JCheckBox)paramChangeEvent.getSource();

      if (localJCheckBox.isSelected()) {
        ETLSourceDlg.access$200(this.this$0).setEnabled(true);
        ETLSourceDlg.access$300(this.this$0).setEnabled(true);
        ETLSourceDlg.access$400(this.this$0).setEnabled(true);
      }
      else {
        ETLSourceDlg.access$200(this.this$0).setEnabled(false);
        ETLSourceDlg.access$300(this.this$0).setEnabled(false);
        ETLSourceDlg.access$400(this.this$0).setEnabled(false);
      }
    }
  }

  class SliderListener
  implements ChangeListener
  {
    JLabel tf;
    private final ETLSourceDlg this$0;

    public SliderListener(, JLabel paramJLabel)
    {
      this.this$0 = paramETLSourceDlg;
      this.tf = paramJLabel;
    }

    public void stateChanged() {
      JSlider localJSlider = (JSlider)paramChangeEvent.getSource();
      if (localJSlider.equals(ETLSourceDlg.access$200(this.this$0)))
        if (localJSlider.getValue() <= 1)
          this.tf.setText("Before Hour: " + localJSlider.getValue() + " oclock");
        else
          this.tf.setText("Before Hour: " + localJSlider.getValue() + " oclocks");

      else if (localJSlider.equals(ETLSourceDlg.access$300(this.this$0)))
        if (localJSlider.getValue() <= 1)
          this.tf.setText("Before Min: " + localJSlider.getValue() + " minute");
        else
          this.tf.setText("Before Min: " + localJSlider.getValue() + " minutes");

      else if (localJSlider.equals(ETLSourceDlg.access$400(this.this$0)))
        if (localJSlider.getValue() <= 1)
          this.tf.setText("Offset Day: " + localJSlider.getValue() + " day");
        else
          this.tf.setText("Offset Day: " + localJSlider.getValue() + " days");
    }
  }
}