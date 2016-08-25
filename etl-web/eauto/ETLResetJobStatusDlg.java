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
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.text.JTextComponent;

public class ETLResetJobStatusDlg extends JDialog
{
  private JPanel jPanelButton;
  private JPanel jPanelData;
  private JPanel jPanelStatus;
  private JButton jBnUpdate;
  private JButton jBnCancel;
  private JLabel jLabel1;
  private InputJTextField jTextField1;
  private JRadioButton jrbReady;
  private JRadioButton jrbPending;
  private JRadioButton jrbDone;
  private ETLResetJobStatusDlg dlg;
  private String etlsys;
  private String etljob;
  private String JobStatus = "";
  private String TxDate = "";
  private Connection dbcon;
  private ETLMainFrame mainFrame;

  public ETLResetJobStatusDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "Reset Job Status", true);
    setResizable(false);
    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLResetJobStatusDlg.1 local1 = new WindowAdapter(this) { private final ETLResetJobStatusDlg this$0;

      public void windowClosing() { this.this$0.dispose();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(new BorderLayout());

    this.jPanelButton = new JPanel();
    this.jPanelData = new JPanel();
    this.jPanelStatus = new JPanel();
    this.jPanelButton.setLayout(new FlowLayout());
    this.jPanelData.setLayout(null);
    this.jPanelStatus.setLayout(null);

    this.jPanelStatus.setBounds(new Rectangle(15, 55, 300, 45));
    this.jPanelStatus.setBorder(BorderFactory.createTitledBorder("Job Status"));

    this.jBnUpdate = new JButton("Update");
    this.jBnUpdate.setMnemonic(85);
    this.jBnCancel = new JButton("Cancel");
    this.jBnCancel.setMnemonic(67);

    this.jBnUpdate.addActionListener(new ActionListener(this) { private final ETLResetJobStatusDlg this$0;

      public void actionPerformed() { ETLResetJobStatusDlg.access$100(ETLResetJobStatusDlg.access$000(this.this$0));
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLResetJobStatusDlg this$0;

      public void actionPerformed() { ETLResetJobStatusDlg.access$000(this.this$0).dispose();
      }

    });
    this.jPanelButton.add(this.jBnUpdate);
    this.jPanelButton.add(this.jBnCancel);

    localContainer.add(this.jPanelData, "Center");
    localContainer.add(this.jPanelButton, "South");

    this.jLabel1 = new JLabel("Set TxDate(YYYY-MM-DD):");
    this.jLabel1.setBounds(new Rectangle(20, 20, 150, 25));

    this.jTextField1 = new InputJTextField();
    this.jTextField1.setBounds(new Rectangle(180, 20, 80, 25));
    this.jTextField1.setInputLimited(10);

    this.jrbReady = new JRadioButton(" Ready ");
    this.jrbReady.setBounds(new Rectangle(10, 15, 80, 25));

    this.jrbPending = new JRadioButton(" Pending ");
    this.jrbPending.setBounds(new Rectangle(100, 15, 90, 25));

    this.jrbDone = new JRadioButton(" Done ");
    this.jrbDone.setBounds(new Rectangle(200, 15, 70, 25));

    ButtonGroup localButtonGroup = new ButtonGroup();
    localButtonGroup.add(this.jrbReady);
    localButtonGroup.add(this.jrbPending);
    localButtonGroup.add(this.jrbDone);

    this.jPanelData.add(this.jLabel1);

    this.jPanelData.add(this.jTextField1);

    this.jPanelStatus.add(this.jrbReady);
    this.jPanelStatus.add(this.jrbPending);
    this.jPanelStatus.add(this.jrbDone);

    this.jPanelData.add(this.jPanelStatus);

    this.jrbReady.setSelected(true);

    setSize(new Dimension(340, 180));

    Dimension localDimension1 = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension localDimension2 = getSize();

    if (localDimension2.height > localDimension1.height)
      localDimension2.height = localDimension1.height;

    if (localDimension2.width > localDimension1.width)
      localDimension2.width = localDimension1.width;

    setLocation((localDimension1.width - localDimension2.width) / 2, (localDimension1.height - localDimension2.height) / 2);
  }

  public void resetJobStatus(Connection paramConnection, String paramString1, String paramString2)
  {
    this.dbcon = paramConnection;
    this.etlsys = paramString1;
    this.etljob = paramString2;

    selectData(paramString1, paramString2);
    show();
  }

  private void update()
  {
    int i = JOptionPane.showConfirmDialog(this, "Do you want to reset the job status?", "Reset Job Status", 0, 3);

    if (i != 0)
      return;

    updateData();
  }

  private void updateData()
  {
    String str1 = this.jTextField1.getText().trim();
    String str2 = "Ready";

    String str3 = this.mainFrame.getDBName();

    String str4 = "";

    if (this.jrbReady.isSelected())
      str2 = "Ready";
    else if (this.jrbPending.isSelected())
      str2 = "Pending";
    else if (this.jrbDone.isSelected()) {
      str2 = "Done";
    }

    if ((str2.equals("Ready")) || (str2.equals("Pending"))) {
      str4 = "UPDATE " + str3 + "ETL_Job SET" + "      Last_TxDate = '" + str1 + "'" + "     ,Last_JobStatus = '" + str2 + "'" + "     ,CheckFlag = 'N'" + "   WHERE ETL_System = '" + this.etlsys + "'" + "     AND ETL_Job = '" + this.etljob + "'";
    }
    else if (str2.equals("Done")) {
      str4 = "UPDATE " + str3 + "ETL_Job SET" + "      Last_TxDate = '" + str1 + "'" + "     ,Last_JobStatus = '" + str2 + "'" + "     ,CheckFlag = 'Y'" + "   WHERE ETL_System = '" + this.etlsys + "'" + "     AND ETL_Job = '" + this.etljob + "'";
    }

    try
    {
      Statement localStatement = this.dbcon.createStatement();
      int i = localStatement.executeUpdate(str4);
      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      JOptionPane.showMessageDialog(this, "Could not reset job status", "Error", 0);

      return;
    }

    this.JobStatus = str2;
    this.TxDate = str1;

    dispose();
  }

  private void selectData(String paramString1, String paramString2)
  {
    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      ResultSet localResultSet = localStatement.executeQuery("SELECT Last_Txdate   FROM " + str1 + "ETL_Job" + "   WHERE ETL_System = '" + this.etlsys + "'" + "     AND ETL_Job = '" + this.etljob + "'");

      if (localResultSet.next())
      {
        String str2;
        java.sql.Date localDate = localResultSet.getDate(1);

        if (localResultSet.wasNull())
          str2 = "";
        else
          str2 = localDate.toString();

        this.jTextField1.setText(str2);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      JOptionPane.showMessageDialog(this, "Could not select txdate from database", "Error", 0);

      return; }
  }

  public String getStatus() {
    return this.JobStatus; } 
  public String getTxDate() { return this.TxDate;
  }

  static ETLResetJobStatusDlg access$000(ETLResetJobStatusDlg paramETLResetJobStatusDlg)
  {
    return paramETLResetJobStatusDlg.dlg; } 
  static void access$100(ETLResetJobStatusDlg paramETLResetJobStatusDlg) { paramETLResetJobStatusDlg.update();
  }
}