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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;

public class ETLNotifyJobDlg extends JDialog
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
  private JLabel jLabel6;
  private InputJTextField jTextField1;
  private InputJTextField jTextField2;
  private InputJTextField jTextField4;
  private JTextArea textMsgContent;
  private JScrollPane jScrollPane;
  private JPanel jPanelMsgContent;
  private JComboBox jcb_dest;
  private JRadioButton jrb_user;
  private JRadioButton jrb_group;
  private JRadioButton jrb_done;
  private JRadioButton jrb_failed;
  private JRadioButton jrb_missing;
  private JRadioButton jrb_fileerror;
  private JRadioButton jrb_recorderror;
  private JCheckBox jcb_attachlog;
  private JCheckBox jcb_email;
  private JCheckBox jcb_sms;
  private ETLNotifyJobDlg dlg;
  private DefaultMutableTreeNode parentNode;
  private Connection dbcon;
  private int mode = 0;
  private ETLMainFrame mainFrame;
  private int SeqID;

  public ETLNotifyJobDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "ETL Message Notification", true);
    setResizable(false);
    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLNotifyJobDlg.1 local1 = new WindowAdapter(this) { private final ETLNotifyJobDlg this$0;

      public void windowClosing() { this.this$0.dispose();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(new BorderLayout());

    this.jPanelButton = new JPanel();
    this.jPanelMsgContent = new JPanel(new BorderLayout());
    this.jPanelData = new JPanel();
    this.jPanelButton.setLayout(new FlowLayout());
    this.jPanelData.setLayout(null);

    this.jBnUpdate = new JButton("Update");
    this.jBnUpdate.setMnemonic(85);
    this.jBnCancel = new JButton("Cancel");
    this.jBnCancel.setMnemonic(67);

    this.jBnUpdate.addActionListener(new ActionListener(this) { private final ETLNotifyJobDlg this$0;

      public void actionPerformed() { ETLNotifyJobDlg.access$100(ETLNotifyJobDlg.access$000(this.this$0));
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLNotifyJobDlg this$0;

      public void actionPerformed() { ETLNotifyJobDlg.access$000(this.this$0).dispose();
      }

    });
    this.jPanelButton.add(this.jBnUpdate);
    this.jPanelButton.add(this.jBnCancel);

    localContainer.add(this.jPanelData, "Center");
    localContainer.add(this.jPanelButton, "South");

    this.jLabel1 = new JLabel("ETL System:");
    this.jLabel1.setBounds(new Rectangle(20, 20, 120, 25));

    this.jLabel2 = new JLabel("ETL Job Name:");
    this.jLabel2.setBounds(new Rectangle(20, 50, 120, 25));

    this.jLabel3 = new JLabel("Destination Type:");
    this.jLabel3.setBounds(new Rectangle(20, 80, 120, 25));

    this.jLabel4 = new JLabel("Destination Name:");
    this.jLabel4.setBounds(new Rectangle(20, 110, 120, 25));

    this.jLabel5 = new JLabel("Message Subject:");
    this.jLabel5.setBounds(new Rectangle(20, 290, 120, 25));

    this.jLabel6 = new JLabel("Message Content:");
    this.jLabel6.setBounds(new Rectangle(20, 320, 120, 25));

    this.jTextField1 = new InputJTextField(InputJTextField.UPPERCASE);
    this.jTextField1.setBounds(new Rectangle(145, 20, 50, 25));
    this.jTextField1.setInputLimited(3);

    this.jTextField2 = new InputJTextField(InputJTextField.UPPERCASE);
    this.jTextField2.setBounds(new Rectangle(145, 50, 240, 25));
    this.jTextField2.setInputLimited(50);

    this.jcb_dest = new JComboBox();
    this.jcb_dest.setBounds(new Rectangle(145, 110, 150, 25));

    this.jTextField4 = new InputJTextField();
    this.jTextField4.setBounds(new Rectangle(145, 290, 360, 25));
    this.jTextField4.setInputLimited(166);

    this.textMsgContent = new JTextArea();

    this.jScrollPane = new JScrollPane();
    this.jScrollPane.setViewportView(this.textMsgContent);

    this.jPanelMsgContent.add(this.jScrollPane, "Center");
    this.jPanelMsgContent.setBounds(new Rectangle(145, 320, 360, 100));

    this.jrb_user = new JRadioButton("To User");
    this.jrb_user.setBounds(new Rectangle(0, 0, 70, 25));
    this.jrb_group = new JRadioButton("To Group");
    this.jrb_group.setBounds(new Rectangle(80, 0, 80, 25));

    this.jrb_user.addActionListener(new ActionListener(this) { private final ETLNotifyJobDlg this$0;

      public void actionPerformed() { ETLNotifyJobDlg.access$200(this.this$0).removeAllItems();
        ETLUtilityInterface.getNotifyUser(ETLNotifyJobDlg.access$300(this.this$0), ETLNotifyJobDlg.access$400(this.this$0).getDBName(), ETLNotifyJobDlg.access$200(this.this$0));
      }

    });
    this.jrb_group.addActionListener(new ActionListener(this) { private final ETLNotifyJobDlg this$0;

      public void actionPerformed() { ETLNotifyJobDlg.access$200(this.this$0).removeAllItems();
        ETLUtilityInterface.getNotifyUserGroup(ETLNotifyJobDlg.access$300(this.this$0), ETLNotifyJobDlg.access$400(this.this$0).getDBName(), ETLNotifyJobDlg.access$200(this.this$0));
      }

    });
    JPanel localJPanel1 = new JPanel();
    localJPanel1.setLayout(null);
    localJPanel1.setBounds(new Rectangle(145, 80, 170, 25));
    ButtonGroup localButtonGroup1 = new ButtonGroup();

    localButtonGroup1.add(this.jrb_user);
    localButtonGroup1.add(this.jrb_group);
    this.jrb_user.setSelected(true);

    localJPanel1.add(this.jrb_user);
    localJPanel1.add(this.jrb_group);

    this.jrb_done = new JRadioButton("When Job Done");
    this.jrb_done.setBounds(new Rectangle(0, 0, 110, 25));
    this.jrb_failed = new JRadioButton("When Job Failed");
    this.jrb_failed.setBounds(new Rectangle(0, 30, 120, 25));
    this.jrb_missing = new JRadioButton("When Source Missing");
    this.jrb_missing.setBounds(new Rectangle(140, 0, 160, 25));
    this.jrb_fileerror = new JRadioButton("When File Receiving Has Error");
    this.jrb_fileerror.setBounds(new Rectangle(140, 30, 200, 25));
    this.jrb_recorderror = new JRadioButton("When Loading Job Has Error Record");
    this.jrb_recorderror.setBounds(new Rectangle(0, 60, 250, 25));

    JPanel localJPanel2 = new JPanel();
    localJPanel2.setLayout(null);
    localJPanel2.setBounds(new Rectangle(145, 140, 420, 90));
    ButtonGroup localButtonGroup2 = new ButtonGroup();

    localButtonGroup2.add(this.jrb_done);
    localButtonGroup2.add(this.jrb_failed);
    localButtonGroup2.add(this.jrb_missing);
    localButtonGroup2.add(this.jrb_fileerror);
    localButtonGroup2.add(this.jrb_recorderror);

    this.jrb_done.setSelected(true);

    localJPanel2.add(this.jrb_done);
    localJPanel2.add(this.jrb_failed);
    localJPanel2.add(this.jrb_missing);
    localJPanel2.add(this.jrb_fileerror);
    localJPanel2.add(this.jrb_recorderror);

    this.jcb_attachlog = new JCheckBox("Attached Log File");
    this.jcb_attachlog.setBounds(new Rectangle(145, 230, 120, 25));
    this.jcb_email = new JCheckBox("Notify via Email");
    this.jcb_email.setBounds(new Rectangle(145, 260, 120, 25));
    this.jcb_sms = new JCheckBox("Notify via Short Message");
    this.jcb_sms.setBounds(new Rectangle(270, 260, 200, 25));

    this.jcb_email.setSelected(true);

    this.jPanelData.add(this.jLabel1);
    this.jPanelData.add(this.jLabel2);
    this.jPanelData.add(this.jLabel3);
    this.jPanelData.add(this.jLabel4);
    this.jPanelData.add(this.jLabel5);
    this.jPanelData.add(this.jLabel6);

    this.jPanelData.add(this.jTextField1);
    this.jPanelData.add(this.jTextField2);
    this.jPanelData.add(this.jcb_dest);
    this.jPanelData.add(this.jTextField4);
    this.jPanelData.add(this.jPanelMsgContent);

    this.jPanelData.add(localJPanel1);
    this.jPanelData.add(localJPanel2);

    this.jPanelData.add(this.jcb_attachlog);
    this.jPanelData.add(this.jcb_email);
    this.jPanelData.add(this.jcb_sms);

    setSize(new Dimension(570, 500));

    Dimension localDimension1 = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension localDimension2 = getSize();

    if (localDimension2.height > localDimension1.height)
      localDimension2.height = localDimension1.height;

    if (localDimension2.width > localDimension1.width)
      localDimension2.width = localDimension1.width;

    setLocation((localDimension1.width - localDimension2.width) / 2, (localDimension1.height - localDimension2.height) / 2);
  }

  public void add(Connection paramConnection, String paramString1, String paramString2, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    this.dbcon = paramConnection;
    this.parentNode = paramDefaultMutableTreeNode;

    ETLUtilityInterface.getNotifyUser(this.dbcon, this.mainFrame.getDBName(), this.jcb_dest);

    this.jTextField1.setText(paramString1);
    this.jTextField2.setText(paramString2);

    this.jTextField1.setEditable(false);
    this.jTextField2.setEditable(false);

    this.mode = 1;

    show();
  }

  public void add1(Connection paramConnection, String paramString1, String paramString2, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    this.dbcon = paramConnection;
    this.parentNode = paramDefaultMutableTreeNode;

    ETLUtilityInterface.getNotifyUser(this.dbcon, this.mainFrame.getDBName(), this.jcb_dest);

    this.jTextField1.setText(paramString1);
    this.jTextField2.setText(paramString2);

    this.jTextField1.setEditable(false);
    this.jTextField2.setEditable(false);

    this.mode = 2;

    show();
  }

  public void modify(Connection paramConnection, DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString1, String paramString2, int paramInt)
  {
    this.dbcon = paramConnection;
    this.parentNode = paramDefaultMutableTreeNode;
    this.SeqID = paramInt;

    selectData(paramString1, paramString2, paramInt);

    this.jTextField1.setText(paramString1);
    this.jTextField2.setText(paramString2);
    this.jTextField1.setEditable(false);
    this.jTextField2.setEditable(false);

    this.mode = 3;

    show();
  }

  private void update()
  {
    if ((this.mode == 1) || (this.mode == 2))
      insertData();
    else
      updateData();
  }

  private void insertData()
  {
    label151: String str9;
    String str10;
    String str12;
    String str1 = this.jTextField1.getText().trim();
    String str2 = this.jTextField2.getText().trim();
    String str3 = (String)this.jcb_dest.getSelectedItem();
    String str4 = this.jTextField4.getText().trim();
    String str5 = this.textMsgContent.getText().trim();

    String str6 = "U";
    String str7 = "D";
    String str8 = "N";

    if (str5.length() > 255)
      str5 = str5.substring(0, 255);

    if (this.jrb_user.isSelected()) {
      str6 = "U";
      if (isUserExist(str3) != 0) break label151;
      JOptionPane.showMessageDialog(this.mainFrame, "The notified user does not exist at ETL database!", "Error", 0);

      return;
    }

    str6 = "G";
    if (isGroupExist(str3) == 0) {
      JOptionPane.showMessageDialog(this.mainFrame, "The notified group does not exist at ETL database!", "Error", 0);

      return;
    }

    if (this.jrb_done.isSelected())
      str7 = "D";
    else if (this.jrb_failed.isSelected())
      str7 = "F";
    else if (this.jrb_missing.isSelected())
      str7 = "M";
    else if (this.jrb_fileerror.isSelected())
      str7 = "R";
    else if (this.jrb_recorderror.isSelected())
      str7 = "E";

    if (this.jcb_attachlog.isSelected())
      str8 = "Y";
    else
      str8 = "N";

    if (this.jcb_email.isSelected())
      str9 = "Y";
    else
      str9 = "N";

    if (this.jcb_sms.isSelected())
      str10 = "Y";
    else {
      str10 = "N";
    }

    String str11 = this.mainFrame.getDBName();

    int i = getMaxMsgSeq(str1, str2);

    if (i == -1)
      return;

    if (str6.equals("U")) {
      str12 = "INSERT INTO " + str11 + "ETL_Notification" + "       (ETL_System, ETL_Job, SeqID, DestType, UserName, GroupName," + "        Timing, Attachlog, Email, ShortMessage," + "        MessageSubject, MessageContent)" + "   VALUES ('" + str1 + "', '" + str2 + "'," + i + "," + "           'U', '" + str3 + "', NULL," + "           '" + str7 + "', '" + str8 + "'," + "           '" + str9 + "', '" + str10 + "'," + "           ?, ?)";
    }
    else
    {
      str12 = "INSERT INTO " + str11 + "ETL_Notification" + "       (ETL_System, ETL_Job, SeqID, DestType, UserName, GroupName," + "        Timing, Attachlog, Email, ShortMessage," + "        MessageSubject, MessageContent)" + "   VALUES ('" + str1 + "', '" + str2 + "'," + i + "," + "           'G', NULL, '" + str3 + "'," + "           '" + str7 + "', '" + str8 + "'," + "           '" + str9 + "', '" + str10 + "'," + "           ?, ?)";
    }

    try
    {
      PreparedStatement localPreparedStatement = this.dbcon.prepareStatement(str12);

      localPreparedStatement.setString(1, str4);
      localPreparedStatement.setString(2, str5);

      int j = localPreparedStatement.executeUpdate();
      localPreparedStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str13 = "Could not insert a new job group into database\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str13, "Error", 0);

      return;
    }

    if (this.mode == 1) {
      this.mainFrame.getTreeView().addNotifyJob(this.parentNode, str1, str2, i, str3);
    }
    else if (this.mode == 2) {
      this.mainFrame.getTreeView().addNotifyJob1(this.parentNode, str1, str2, i, str3);
    }

    dispose();
  }

  private void updateData()
  {
    label151: String str9;
    String str10;
    String str12;
    String str1 = this.jTextField1.getText().trim();
    String str2 = this.jTextField2.getText().trim();
    String str3 = (String)this.jcb_dest.getSelectedItem();
    String str4 = this.jTextField4.getText().trim();
    String str5 = this.textMsgContent.getText().trim();

    String str6 = "U";
    String str7 = "D";
    String str8 = "N";

    if (str5.length() > 255)
      str5 = str5.substring(0, 255);

    if (this.jrb_user.isSelected()) {
      str6 = "U";
      if (isUserExist(str3) != 0) break label151;
      JOptionPane.showMessageDialog(this.mainFrame, "The notified user does not exist at ETL database!", "Error", 0);

      return;
    }

    str6 = "G";
    if (isGroupExist(str3) == 0) {
      JOptionPane.showMessageDialog(this.mainFrame, "The notified group does not exist at ETL database!", "Error", 0);

      return;
    }

    if (this.jrb_done.isSelected())
      str7 = "D";
    else if (this.jrb_failed.isSelected())
      str7 = "F";
    else if (this.jrb_missing.isSelected())
      str7 = "M";
    else if (this.jrb_fileerror.isSelected())
      str7 = "R";
    else if (this.jrb_recorderror.isSelected())
      str7 = "E";

    if (this.jcb_attachlog.isSelected())
      str8 = "Y";
    else
      str8 = "N";

    if (this.jcb_email.isSelected())
      str9 = "Y";
    else
      str9 = "N";

    if (this.jcb_sms.isSelected())
      str10 = "Y";
    else {
      str10 = "N";
    }

    String str11 = this.mainFrame.getDBName();

    if (str6.equals("U")) {
      str12 = "UPDATE " + str11 + "ETL_Notification SET" + "    DestType = '" + str6 + "'," + "    UserName = '" + str3 + "'," + "    GroupName = NULL," + "    Timing = '" + str7 + "'," + "    Attachlog = '" + str8 + "'," + "    Email = '" + str9 + "'," + "    ShortMessage = '" + str10 + "'," + "    MessageSubject = ?," + "    MessageContent = ?" + "   WHERE ETL_System = '" + str1 + "'" + "     AND ETL_Job = '" + str2 + "'" + "     AND SeqID = " + this.SeqID;
    }
    else
    {
      str12 = "UPDATE " + str11 + "ETL_Notification SET" + "    DestType = '" + str6 + "'," + "    UserName = NULL," + "    GroupName = '" + str3 + "'," + "    Timing = '" + str7 + "'," + "    Attachlog = '" + str8 + "'," + "    Email = '" + str9 + "'," + "    ShortMessage = '" + str10 + "'," + "    MessageSubject = ?," + "    MessageContent = ?" + "   WHERE ETL_System = '" + str1 + "'" + "     AND ETL_Job = '" + str2 + "'" + "     AND SeqID = " + this.SeqID;
    }

    try
    {
      PreparedStatement localPreparedStatement = this.dbcon.prepareStatement(str12);
      localPreparedStatement.setString(1, str4);
      localPreparedStatement.setString(2, str5);

      int i = localPreparedStatement.executeUpdate();
      localPreparedStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str13 = "Could not update user record" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str13, "Error", 0);

      return;
    }

    dispose();
  }

  private void selectData(String paramString1, String paramString2, int paramInt)
  {
    String str6;
    String str1 = this.mainFrame.getDBName();
    String str2 = "";
    String str3 = "";
    String str4 = "";
    try
    {
      Statement localStatement = this.dbcon.createStatement();

      ResultSet localResultSet = localStatement.executeQuery("SELECT DestType, UserName, GroupName, Timing, Attachlog,       Email, ShortMessage, MessageSubject, MessageContent   FROM " + str1 + "ETL_Notification" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'" + "     AND SeqID = '" + paramInt + "'");

      if (localResultSet.next())
      {
        str2 = localResultSet.getString(1);
        str3 = localResultSet.getString(2);
        str4 = localResultSet.getString(3);

        String str5 = localResultSet.getString(4);
        if (localResultSet.wasNull())
          str5 = "N";

        str6 = localResultSet.getString(5);
        if (localResultSet.wasNull())
          str6 = "N";

        String str7 = localResultSet.getString(6);
        if (localResultSet.wasNull())
          str7 = "N";

        String str8 = localResultSet.getString(7);
        if (localResultSet.wasNull())
          str8 = "N";

        String str9 = localResultSet.getString(8).trim();
        if (localResultSet.wasNull())
          str9 = "";

        String str10 = localResultSet.getString(9).trim();
        if (localResultSet.wasNull())
          str10 = "";

        if (str2.equals("U")) {
          this.jrb_user.setSelected(true);
        }
        else if (str2.equals("G")) {
          this.jrb_group.setSelected(true);
        }

        if (str5.equals("D")) {
          this.jrb_done.setSelected(true);
        }
        else if (str5.equals("F")) {
          this.jrb_failed.setSelected(true);
        }
        else if (str5.equals("M")) {
          this.jrb_missing.setSelected(true);
        }
        else if (str5.equals("R")) {
          this.jrb_fileerror.setSelected(true);
        }
        else if (str5.equals("E")) {
          this.jrb_recorderror.setSelected(true);
        }

        if (str6.equals("Y"))
          this.jcb_attachlog.setSelected(true);
        else
          this.jcb_attachlog.setSelected(false);

        if (str7.equals("Y"))
          this.jcb_email.setSelected(true);
        else
          this.jcb_email.setSelected(false);

        if (str8.equals("Y"))
          this.jcb_sms.setSelected(true);
        else
          this.jcb_sms.setSelected(false);

        this.jTextField4.setText(str9);
        this.textMsgContent.setText(str10);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      str6 = "Could not select notification record from database" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str6, "Error", 0);

      return;
    }

    if (str2.equals("U")) {
      this.jrb_user.setSelected(true);
      ETLUtilityInterface.getNotifyUser(this.dbcon, this.mainFrame.getDBName(), this.jcb_dest);
      this.jcb_dest.setSelectedItem(str3);
    }
    else if (str2.equals("G")) {
      this.jrb_group.setSelected(true);
      ETLUtilityInterface.getNotifyUserGroup(this.dbcon, this.mainFrame.getDBName(), this.jcb_dest);
      this.jcb_dest.setSelectedItem(str4);
    }
  }

  private int isUserExist(String paramString)
  {
    int i;
    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      String str2 = "SELECT count(*) FROM " + str1 + "ETL_User" + "   WHERE UserName = '" + paramString + "'";

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

  private int isGroupExist(String paramString)
  {
    int i;
    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      String str2 = "SELECT count(*) FROM " + str1 + "ETL_UserGroup" + "   WHERE GroupName = '" + paramString + "'";

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

  private int getMaxMsgSeq(String paramString1, String paramString2)
  {
    int i;
    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      String str2 = "SELECT max(SeqID) FROM " + str1 + "ETL_Notification" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      ResultSet localResultSet = localStatement.executeQuery(str2);
      localResultSet.next();

      i = localResultSet.getInt(1);
      if (localResultSet.wasNull()) {
        i = 0;
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str3 = "Could not get the max seq id for notification" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);

      return -1;
    }

    ++i;

    return i;
  }

  public String getNotificationDestName()
  {
    String str = (String)this.jcb_dest.getSelectedItem();

    return str;
  }

  static ETLNotifyJobDlg access$000(ETLNotifyJobDlg paramETLNotifyJobDlg)
  {
    return paramETLNotifyJobDlg.dlg; } 
  static void access$100(ETLNotifyJobDlg paramETLNotifyJobDlg) { paramETLNotifyJobDlg.update(); } 
  static JComboBox access$200(ETLNotifyJobDlg paramETLNotifyJobDlg) { return paramETLNotifyJobDlg.jcb_dest; } 
  static Connection access$300(ETLNotifyJobDlg paramETLNotifyJobDlg) { return paramETLNotifyJobDlg.dbcon; } 
  static ETLMainFrame access$400(ETLNotifyJobDlg paramETLNotifyJobDlg) { return paramETLNotifyJobDlg.mainFrame;
  }
}