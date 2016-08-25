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

public class ETLSubsystemDlg extends JDialog
{
  private JPanel jPanelButton;
  private JPanel jPanelData;
  private JButton jBnUpdate;
  private JButton jBnCancel;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JLabel jLabel3;
  private JLabel jLabel4;
  private JLabel jLabel5;
  private InputJTextField jTextField1;
  private InputJTextField jTextField2;
  private JSlider jsldDataKeepPeriod;
  private JSlider jsldLogKeepPeriod;
  private JSlider jsldRecordKeepPeriod;
  private ETLSubsystemDlg dlg;
  private DefaultMutableTreeNode parentNode;
  private Connection dbcon;
  private int mode = 0;
  private ETLMainFrame mainFrame;

  public ETLSubsystemDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "ETL System", true);
    setResizable(false);
    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLSubsystemDlg.1 local1 = new WindowAdapter(this) { private final ETLSubsystemDlg this$0;

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

    this.jBnUpdate.addActionListener(new ActionListener(this) { private final ETLSubsystemDlg this$0;

      public void actionPerformed() { ETLSubsystemDlg.access$100(ETLSubsystemDlg.access$000(this.this$0));
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLSubsystemDlg this$0;

      public void actionPerformed() { ETLSubsystemDlg.access$000(this.this$0).dispose();
      }

    });
    this.jPanelButton.add(this.jBnUpdate);
    this.jPanelButton.add(this.jBnCancel);

    localContainer.add(this.jPanelData, "Center");
    localContainer.add(this.jPanelButton, "South");

    this.jLabel1 = new JLabel("System Name:");
    this.jLabel1.setBounds(new Rectangle(20, 20, 85, 25));

    this.jLabel2 = new JLabel("Description:");
    this.jLabel2.setBounds(new Rectangle(20, 50, 85, 25));

    this.jLabel3 = new JLabel("Data Keep Period: 30 days");
    this.jLabel3.setBounds(new Rectangle(20, 90, 180, 25));

    this.jLabel4 = new JLabel("Log File Keep Period: 30 days");
    this.jLabel4.setBounds(new Rectangle(20, 170, 180, 25));

    this.jLabel5 = new JLabel("Record Keep Period: 30 days");
    this.jLabel5.setBounds(new Rectangle(20, 250, 180, 25));

    this.jTextField1 = new InputJTextField(InputJTextField.UPPERCASE);
    this.jTextField1.setBounds(new Rectangle(115, 20, 50, 25));
    this.jTextField1.setInputLimited(3);

    this.jTextField2 = new InputJTextField();
    this.jTextField2.setBounds(new Rectangle(115, 50, 250, 25));
    this.jTextField2.setInputLimited(50);

    this.jsldDataKeepPeriod = new JSlider(0, 1, 180, 30);
    this.jsldDataKeepPeriod.setMajorTickSpacing(30);
    this.jsldDataKeepPeriod.setMinorTickSpacing(10);
    this.jsldDataKeepPeriod.setPaintTicks(true);
    this.jsldDataKeepPeriod.setPaintLabels(true);
    this.jsldDataKeepPeriod.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

    this.jsldDataKeepPeriod.setBounds(new Rectangle(15, 120, 360, 45));

    ETLSubsystemDlg.DKSliderListener localDKSliderListener = new ETLSubsystemDlg.DKSliderListener(this, this.jLabel3);
    this.jsldDataKeepPeriod.addChangeListener(localDKSliderListener);

    this.jsldLogKeepPeriod = new JSlider(0, 1, 180, 30);
    this.jsldLogKeepPeriod.setMajorTickSpacing(30);
    this.jsldLogKeepPeriod.setMinorTickSpacing(10);
    this.jsldLogKeepPeriod.setPaintTicks(true);
    this.jsldLogKeepPeriod.setPaintLabels(true);
    this.jsldLogKeepPeriod.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

    this.jsldLogKeepPeriod.setBounds(new Rectangle(15, 200, 360, 45));

    ETLSubsystemDlg.LKSliderListener localLKSliderListener = new ETLSubsystemDlg.LKSliderListener(this, this.jLabel4);
    this.jsldLogKeepPeriod.addChangeListener(localLKSliderListener);

    this.jsldRecordKeepPeriod = new JSlider(0, 1, 180, 30);
    this.jsldRecordKeepPeriod.setMajorTickSpacing(30);
    this.jsldRecordKeepPeriod.setMinorTickSpacing(10);
    this.jsldRecordKeepPeriod.setPaintTicks(true);
    this.jsldRecordKeepPeriod.setPaintLabels(true);
    this.jsldRecordKeepPeriod.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

    this.jsldRecordKeepPeriod.setBounds(new Rectangle(15, 280, 360, 45));

    ETLSubsystemDlg.RKSliderListener localRKSliderListener = new ETLSubsystemDlg.RKSliderListener(this, this.jLabel5);
    this.jsldRecordKeepPeriod.addChangeListener(localRKSliderListener);

    this.jPanelData.add(this.jLabel1);
    this.jPanelData.add(this.jLabel2);
    this.jPanelData.add(this.jLabel3);
    this.jPanelData.add(this.jLabel4);
    this.jPanelData.add(this.jLabel5);

    this.jPanelData.add(this.jTextField1);
    this.jPanelData.add(this.jTextField2);

    this.jPanelData.add(this.jsldDataKeepPeriod);
    this.jPanelData.add(this.jsldLogKeepPeriod);
    this.jPanelData.add(this.jsldRecordKeepPeriod);

    setSize(new Dimension(400, 400));

    Dimension localDimension1 = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension localDimension2 = getSize();

    if (localDimension2.height > localDimension1.height)
      localDimension2.height = localDimension1.height;

    if (localDimension2.width > localDimension1.width)
      localDimension2.width = localDimension1.width;

    setLocation((localDimension1.width - localDimension2.width) / 2, (localDimension1.height - localDimension2.height) / 2);
  }

  public void add(Connection paramConnection, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    this.dbcon = paramConnection;
    this.parentNode = paramDefaultMutableTreeNode;

    this.mode = 1;

    show();
  }

  public void modify(Connection paramConnection, DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString)
  {
    this.dbcon = paramConnection;
    this.parentNode = paramDefaultMutableTreeNode;

    selectData(paramString);

    this.jTextField1.setEditable(false);

    this.mode = 2;

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
    String str1 = this.jTextField1.getText().trim();
    String str2 = this.jTextField2.getText().trim();

    int i = this.jsldDataKeepPeriod.getValue();
    int j = this.jsldLogKeepPeriod.getValue();
    int k = this.jsldRecordKeepPeriod.getValue();

    String str3 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      int l = localStatement.executeUpdate("INSERT INTO " + str3 + "ETL_Sys" + "       (ETL_System, Description, DataKeepPeriod," + "        LogKeepPeriod, RecordKeepPeriod)" + "   VALUES ( '" + str1 + "', '" + str2 + "'," + i + "," + j + "," + k + ")");

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str4 = "Could not insert a new ETL system into database\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str4, "Error", 0);

      return;
    }

    this.mainFrame.getTreeView().addETLSubsystem(this.parentNode, str1);

    dispose();
  }

  private void updateData()
  {
    String str1 = this.jTextField1.getText().trim();
    String str2 = this.jTextField2.getText().trim();

    int i = this.jsldDataKeepPeriod.getValue();
    int j = this.jsldRecordKeepPeriod.getValue();
    int k = this.jsldLogKeepPeriod.getValue();

    String str3 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      int l = localStatement.executeUpdate("UPDATE " + str3 + "ETL_Sys SET" + "    Description = '" + str2 + "'," + "    DataKeepPeriod = " + i + "," + "    RecordKeepPeriod = " + j + "," + "    LogKeepPeriod = " + k + "   WHERE ETL_System = '" + str1 + "'");

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str4 = "Could not update ETL system record" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str4, "Error", 0);

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
      ResultSet localResultSet = localStatement.executeQuery("SELECT Description, DataKeepPeriod, RecordKeepPeriod, LogKeepPeriod   FROM " + str1 + "ETL_Sys" + "   WHERE ETL_System = '" + paramString + "'");

      if (localResultSet.next())
      {
        String str2 = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str2 = "";

        int i = localResultSet.getInt(2);
        int j = localResultSet.getInt(3);
        int k = localResultSet.getInt(4);

        this.jTextField1.setText(paramString);
        this.jTextField2.setText(str2);

        this.jsldDataKeepPeriod.setValue(i);
        this.jsldRecordKeepPeriod.setValue(j);
        this.jsldLogKeepPeriod.setValue(k);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str3 = "Could not select ETL system record from database" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);

      return;
    }
  }

  static ETLSubsystemDlg access$000(ETLSubsystemDlg paramETLSubsystemDlg)
  {
    return paramETLSubsystemDlg.dlg; } 
  static void access$100(ETLSubsystemDlg paramETLSubsystemDlg) { paramETLSubsystemDlg.update();
  }

  class RKSliderListener
  implements ChangeListener
  {
    JLabel tf;
    private final ETLSubsystemDlg this$0;

    public RKSliderListener(, JLabel paramJLabel)
    {
      this.this$0 = paramETLSubsystemDlg;
      this.tf = paramJLabel;
    }

    public void stateChanged() {
      JSlider localJSlider = (JSlider)paramChangeEvent.getSource();
      if (localJSlider.getValue() == 1)
        this.tf.setText("Record Keep Period: " + localJSlider.getValue() + " day");
      else
        this.tf.setText("Record Keep Period: " + localJSlider.getValue() + " days");
    }
  }

  class LKSliderListener
  implements ChangeListener
  {
    JLabel tf;
    private final ETLSubsystemDlg this$0;

    public LKSliderListener(, JLabel paramJLabel)
    {
      this.this$0 = paramETLSubsystemDlg;
      this.tf = paramJLabel;
    }

    public void stateChanged() {
      JSlider localJSlider = (JSlider)paramChangeEvent.getSource();
      if (localJSlider.getValue() == 1)
        this.tf.setText("Log File Keep Period: " + localJSlider.getValue() + " day");
      else
        this.tf.setText("Log File Keep Period: " + localJSlider.getValue() + " days");
    }
  }

  class DKSliderListener
  implements ChangeListener
  {
    JLabel tf;
    private final ETLSubsystemDlg this$0;

    public DKSliderListener(, JLabel paramJLabel)
    {
      this.this$0 = paramETLSubsystemDlg;
      this.tf = paramJLabel;
    }

    public void stateChanged() {
      JSlider localJSlider = (JSlider)paramChangeEvent.getSource();
      if (localJSlider.getValue() == 1)
        this.tf.setText("Data Keep Period: " + localJSlider.getValue() + " day");
      else
        this.tf.setText("Data Keep Period: " + localJSlider.getValue() + " days");
    }
  }
}