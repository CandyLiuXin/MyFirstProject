import java.awt.BorderLayout;
import java.awt.Container;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class ETLInfo extends JPanel
{
  private JTable infoTable;
  private JScrollPane jScrollPane;
  private ETLMainFrame mainFrame;
  public ETLInfo.TableModel tableModel = new ETLInfo.TableModel(this);

  public ETLInfo(JFrame paramJFrame)
  {
    String[] arrayOfString = { "Column", "Content" };

    this.mainFrame = ((ETLMainFrame)paramJFrame);

    this.infoTable = new JTable(this.tableModel);

    for (int i = 0; i < arrayOfString.length; ++i)
    {
      this.tableModel.addColumn(arrayOfString[i]);
    }

    this.infoTable.setAutoResizeMode(0);
    this.infoTable.setCellEditor(null);

    this.infoTable.getColumn("Column").setPreferredWidth(150);
    this.infoTable.getColumn("Content").setPreferredWidth(300);

    this.jScrollPane = new JScrollPane();
    this.jScrollPane.setViewportView(this.infoTable);

    setLayout(new BorderLayout());

    add(this.jScrollPane, "Center");
  }

  public void clearInfo()
  {
    int i = this.tableModel.getRowCount();

    for (int j = i - 1; j >= 0; --j)
    {
      this.tableModel.removeRow(j);
    }
  }

  public void showDetailOfRoot()
  {
    clearInfo();
  }

  public void showDetailOfNone()
  {
    clearInfo();
  }

  public void showDetailOfSystem(String paramString)
  {
    Statement localStatement;
    String str1 = "";
    int i = 0;
    int j = 0;
    int k = 0;
    String str2 = this.mainFrame.getDBName();

    clearInfo();
    try
    {
      localStatement = this.mainFrame.getConnection().createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Unable to create statement object", "Error", 0);

      return;
    }
    try
    {
      ResultSet localResultSet = localStatement.executeQuery("SELECT Description, DataKeepPeriod,   LogKeepPeriod, RecordKeepPeriod   FROM " + str2 + "etl_sys" + "   WHERE etl_system = '" + paramString + "'");

      if (localResultSet.next())
      {
        str1 = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str1 = "";

        i = localResultSet.getInt(2);
        if (localResultSet.wasNull())
          i = 0;

        j = localResultSet.getInt(3);
        if (localResultSet.wasNull())
          j = 0;

        k = localResultSet.getInt(4);
        if (localResultSet.wasNull())
          k = 0;
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this, "Could not retrieve information from database", "Error", 0);
    }

    Vector localVector = new Vector();
    localVector.add("System");
    localVector.add(paramString);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Description");
    localVector.add(str1);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Data Keep Period");
    localVector.add(String.valueOf(i));
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Log Keep Period");
    localVector.add(String.valueOf(j));
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Record Keep Period");
    localVector.add(String.valueOf(k));
    this.tableModel.addRow(localVector);
  }

  public void showDetailOfJob(String paramString1, String paramString2)
  {
    Statement localStatement;
    Object localObject1 = ""; String str1 = ""; String str2 = ""; String str3 = ""; String str4 = "";
    Object localObject2 = ""; Object localObject3 = ""; Object localObject4 = ""; String str5 = "";
    String str6 = ""; Object localObject5 = ""; Object localObject6 = "";
    String str7 = ""; Object localObject7 = ""; String str8 = ""; String str9 = "";

    int i = 0; int j = 0; int k = 0;
    String str11 = this.mainFrame.getDBName();

    clearInfo();
    try
    {
      localStatement = this.mainFrame.getConnection().createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Unable to create statement object", "Error", 0);

      return;
    }
    try
    {
      ResultSet localResultSet = localStatement.executeQuery("SELECT Description, Frequency, JobType, Enable,   Last_JobStatus, Last_TXDate, Last_FileCnt,   Last_CubeStatus, CubeFlag, CheckFlag,   Last_Starttime, Last_EndTime,   CheckCalendar, CalendarBU, AutoOff,   ETL_Server, JobSessionID, ExpectedRecord, CheckLastStatus   FROM " + str11 + "ETL_Job" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'");

      if (localResultSet.next())
      {
        str1 = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str1 = "";

        str2 = localResultSet.getString(2);

        String str10 = localResultSet.getString(3);
        if (localResultSet.wasNull()) {
          str3 = "Unknown";
        }
        else if (str10.equals("D"))
          str3 = "Daily";
        else if (str10.equals("W"))
          str3 = "Weekly";
        else if (str10.equals("M"))
          str3 = "Monthly";
        else if (str10.equals("9"))
          str3 = "On Demand";
        else if (str10.equals("V"))
          str3 = "Virtual";
        else {
          str3 = "";
        }

        str10 = localResultSet.getString(4);
        if (localResultSet.wasNull()) {
          str4 = "Unknown";
        }
        else if (str10.equals("1"))
          str4 = "Enable";
        else {
          str4 = "Disable";
        }

        str10 = localResultSet.getString(5);
        if (localResultSet.wasNull())
          localObject4 = "Unknown";
        else {
          localObject4 = str10;
        }

        java.sql.Date localDate = localResultSet.getDate(6);
        if (localResultSet.wasNull())
          str5 = "Unknown";
        else
          str5 = localDate.toString();

        i = localResultSet.getInt(7);

        str10 = localResultSet.getString(8);
        if (localResultSet.wasNull())
          localObject5 = "Unknown";
        else
          localObject5 = str10;

        str10 = localResultSet.getString(9);
        if (localResultSet.wasNull()) {
          str6 = "No";
        }
        else if (str10.equals("Y"))
          str6 = "Yes";
        else
          str6 = "No";

        str10 = localResultSet.getString(10);
        if (localResultSet.wasNull())
          localObject6 = "Unknown";
        else
          localObject6 = str10;

        str10 = localResultSet.getString(11);
        if (localResultSet.wasNull())
          localObject2 = "Unknown";
        else
          localObject2 = str10;

        str10 = localResultSet.getString(12);
        if (localResultSet.wasNull())
          localObject3 = "Unknown";
        else
          localObject3 = str10;

        str10 = localResultSet.getString(13);
        if (localResultSet.wasNull()) {
          str7 = "No";
        }
        else if (str10.equals("Y"))
          str7 = "Yes";
        else
          str7 = "No";

        str10 = localResultSet.getString(14);
        if (localResultSet.wasNull())
          localObject7 = "";
        else
          localObject7 = str10;

        str10 = localResultSet.getString(15);
        if (localResultSet.wasNull()) {
          str8 = "";
        }
        else if (str10.equals("Y"))
          str8 = "Yes";
        else {
          str8 = "No";
        }

        str10 = localResultSet.getString(16);
        if (localResultSet.wasNull())
          localObject1 = "";
        else
          localObject1 = str10;

        j = localResultSet.getInt(17);
        if (localResultSet.wasNull())
          j = 0;

        k = localResultSet.getInt(18);
        if (localResultSet.wasNull())
          k = 0;

        str10 = localResultSet.getString(19);
        if (localResultSet.wasNull()) {
          str9 = "Yes";
        }
        else if (str10.equals("Y"))
          str9 = "Yes";
        else {
          str9 = "No";
        }

      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Could not retrieve information from database", "Error", 0);
    }

    Vector localVector = new Vector();
    localVector.add("System Name");
    localVector.add(paramString1);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Job Name");
    localVector.add(paramString2);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Description");
    localVector.add(str1);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Job Type");
    localVector.add(str3);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Enable Status");
    localVector.add(str4);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Cube Job");
    localVector.add(str6);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Check Calendar");
    localVector.add(str7);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Calendar BU");
    localVector.add(localObject7);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Last Start Time");
    localVector.add(localObject2);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Last End Time");
    localVector.add(localObject3);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Last Job Status");
    localVector.add(localObject4);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Last TX Date");
    localVector.add(str5);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Last File Count");
    localVector.add(String.valueOf(i));
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Last Cube Status");
    localVector.add(localObject5);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Check Flag");
    localVector.add(localObject6);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Auto Turn Off");
    localVector.add(str8);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Running At Server");
    localVector.add(localObject1);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Job Session ID");
    localVector.add(String.valueOf(j));
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Expected Record");
    localVector.add(String.valueOf(k));
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Check Job Status Before Execution");
    localVector.add(str9);
    this.tableModel.addRow(localVector);
  }

  public void showDetailOfSource(String paramString)
  {
    Statement localStatement;
    String str1 = ""; String str2 = ""; String str3 = "";
    String str4 = ""; String str5 = "";
    int i = 0; int j = 0; int k = 0;
    String str6 = this.mainFrame.getDBName();

    clearInfo();
    try
    {
      localStatement = this.mainFrame.getConnection().createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Unable to create statement object", "Error", 0);

      return;
    }
    try
    {
      ResultSet localResultSet = localStatement.executeQuery("SELECT ETL_System, ETL_Job, Conv_File_Head,       AutoFilter, Alert, BeforeHour, BeforeMin, OffsetDay   FROM " + str6 + "etl_job_source" + "   WHERE source = '" + paramString + "'");

      if (localResultSet.next())
      {
        str1 = localResultSet.getString(1);
        str2 = localResultSet.getString(2);
        str3 = localResultSet.getString(3);
        str4 = localResultSet.getString(4);
        if (localResultSet.wasNull())
          str4 = "0";

        str5 = localResultSet.getString(5);
        if (localResultSet.wasNull())
          str5 = "0";

        i = localResultSet.getInt(6);
        j = localResultSet.getInt(7);
        k = localResultSet.getInt(8);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Could not retrieve job source information from database", "Error", 0);
    }

    Vector localVector = new Vector();
    localVector.add("Source File Head");
    localVector.add(paramString);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Mapping System");
    localVector.add(str1);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Mapping Job");
    localVector.add(str2);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Convert File Head");
    localVector.add(str3);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Filter Out Duplicate File");
    if (str4.equals("1"))
      localVector.add("Yes");
    else
      localVector.add("No");
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Alert When Missing");
    if (str5.equals("1"))
      localVector.add("Yes");
    else
      localVector.add("No");
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Before Hour");
    localVector.add(String.valueOf(i));
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Before Minute");
    localVector.add(String.valueOf(j));
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Offset Day");
    localVector.add(String.valueOf(k));
    this.tableModel.addRow(localVector);
  }

  public void showDetailOfJobGroup(String paramString)
  {
    Statement localStatement;
    String str1 = ""; String str2 = "";
    String str3 = ""; String str4 = "";

    String str5 = this.mainFrame.getDBName();

    clearInfo();
    try
    {
      localStatement = this.mainFrame.getConnection().createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Unable to create statement object", "Error", 0);

      return;
    }
    try
    {
      ResultSet localResultSet = localStatement.executeQuery("SELECT etl_system, etl_job, autoonchild, description   FROM " + str5 + "etl_job_group" + "   WHERE groupname = '" + paramString + "'");

      if (localResultSet.next())
      {
        str1 = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str1 = "";

        str2 = localResultSet.getString(2);
        if (localResultSet.wasNull())
          str2 = "";

        str3 = localResultSet.getString(3);
        if (localResultSet.wasNull())
          str3 = "N";

        str4 = localResultSet.getString(4);
        if (localResultSet.wasNull())
          str4 = "";
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Could not retrieve information from database", "Error", 0);
    }

    Vector localVector = new Vector();
    localVector.add("Job Group Name");
    localVector.add(paramString);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Description");
    localVector.add(str4);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Trigger System");
    localVector.add(str1);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Trigger Job");
    localVector.add(str2);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Auto On Child Job");
    if (str3.equals("Y"))
      localVector.add("Yes");
    else
      localVector.add("No");
    this.tableModel.addRow(localVector);
  }

  public void showDetailOfJobGroupChild(String paramString1, String paramString2, String paramString3)
  {
    Statement localStatement;
    String str1 = ""; String str2 = "";
    String str3 = ""; String str4 = ""; String str5 = "";
    String str6 = this.mainFrame.getDBName();

    clearInfo();
    try
    {
      localStatement = this.mainFrame.getConnection().createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Unable to create statement object", "Error", 0);

      return;
    }
    try
    {
      ResultSet localResultSet = localStatement.executeQuery("SELECT description   FROM " + str6 + "etl_job_groupchild" + "   WHERE groupname = '" + paramString1 + "'" + "     AND etl_system = '" + paramString2 + "'" + "     AND etl_job = '" + paramString3 + "'");

      if (localResultSet.next())
      {
        str1 = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str1 = "";
      }

      localResultSet = localStatement.executeQuery("SELECT enable, last_jobstatus, last_txdate, checkflag   FROM " + str6 + "etl_job" + "   WHERE etl_system = '" + paramString2 + "'" + "     AND etl_job = '" + paramString3 + "'");

      if (localResultSet.next())
      {
        String str7 = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str2 = "Unknown";
        else if (str7.equals("1"))
          str2 = "Enable";
        else if (str7.equals("0"))
          str2 = "Disable";

        str3 = localResultSet.getString(2);
        if (localResultSet.wasNull())
          str3 = "Unknown";

        java.sql.Date localDate = localResultSet.getDate(3);
        if (localResultSet.wasNull())
          str5 = "Unknown";
        else
          str5 = localDate.toString();

        str4 = localResultSet.getString(4);
        if (localResultSet.wasNull())
          str4 = "Unknown";
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Could not retrieve information from database", "Error", 0);
    }

    Vector localVector = new Vector();
    localVector.add("Job Group Name");
    localVector.add(paramString1);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Child Job System");
    localVector.add(paramString2);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Child Job");
    localVector.add(paramString3);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Description");
    localVector.add(str1);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Enable Status");
    localVector.add(str2);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Last Job Status");
    localVector.add(str3);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Last TX Date");
    localVector.add(str5);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Check Flag");
    localVector.add(str4);
    this.tableModel.addRow(localVector);
  }

  public void showDetailOfUser(String paramString)
  {
    Statement localStatement;
    String str1 = ""; String str2 = "";
    String str3 = ""; String str4 = "";

    String str5 = this.mainFrame.getDBName();

    clearInfo();
    try
    {
      localStatement = this.mainFrame.getConnection().createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Unable to create statement object", "Error", 0);

      return;
    }
    try
    {
      ResultSet localResultSet = localStatement.executeQuery("SELECT Description, Email, Mobile, Status   FROM " + str5 + "ETL_User" + "   WHERE UserName = '" + paramString + "'");

      if (localResultSet.next())
      {
        str3 = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str3 = "";

        str1 = localResultSet.getString(2);
        if (localResultSet.wasNull())
          str1 = "";

        str2 = localResultSet.getString(3);
        if (localResultSet.wasNull())
          str2 = "";

        str4 = localResultSet.getString(4);
        if (localResultSet.wasNull()) {
          str4 = "Disabled";
        }
        else if (str4.equals("1"))
          str4 = "Enabled";
        else
          str4 = "Disabled";
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Could not retrieve information from database", "Error", 0);
    }

    Vector localVector = new Vector();
    localVector.add("User Name");
    localVector.add(paramString);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Description");
    localVector.add(str3);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Email Address");
    localVector.add(str1);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Mobile Phone");
    localVector.add(str2);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Staus");
    localVector.add(str4);
    this.tableModel.addRow(localVector);
  }

  public void showDetailOfUserGroup(String paramString)
  {
    Statement localStatement;
    String str1 = "";

    String str2 = this.mainFrame.getDBName();

    clearInfo();
    try
    {
      localStatement = this.mainFrame.getConnection().createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Unable to create statement object", "Error", 0);

      return;
    }
    try
    {
      ResultSet localResultSet = localStatement.executeQuery("SELECT Description   FROM " + str2 + "ETL_UserGroup" + "   WHERE GroupName = '" + paramString + "'");

      if (localResultSet.next())
      {
        str1 = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str1 = "";
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Could not retrieve information from database", "Error", 0);
    }

    Vector localVector = new Vector();
    localVector.add("Group Name");
    localVector.add(paramString);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Description");
    localVector.add(str1);
    this.tableModel.addRow(localVector);
  }

  public void showDetailOfNotification(String paramString1, String paramString2, int paramInt)
  {
    Statement localStatement;
    String str1 = ""; String str2 = ""; String str3 = "";
    String str4 = ""; String str5 = "";
    String str6 = ""; String str7 = "";
    String str8 = ""; String str9 = "";

    String str10 = this.mainFrame.getDBName();

    clearInfo();
    try
    {
      localStatement = this.mainFrame.getConnection().createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Unable to create statement object", "Error", 0);

      return;
    }
    try
    {
      ResultSet localResultSet = localStatement.executeQuery("SELECT DestType, UserName, GroupName, Timing,       Attachlog, Email, ShortMessage,       MessageSubject, MessageContent   FROM " + str10 + "ETL_Notification" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'" + "     AND SeqID = " + paramInt);

      if (localResultSet.next())
      {
        str1 = localResultSet.getString(1);
        str2 = localResultSet.getString(2);
        str3 = localResultSet.getString(3);

        str4 = localResultSet.getString(4);
        if (localResultSet.wasNull())
          str4 = "D";

        str5 = localResultSet.getString(5);
        if (localResultSet.wasNull())
          str5 = "N";

        str6 = localResultSet.getString(6);
        if (localResultSet.wasNull())
          str6 = "N";

        str7 = localResultSet.getString(7);
        if (localResultSet.wasNull())
          str7 = "N";

        str8 = localResultSet.getString(8).trim();
        if (localResultSet.wasNull())
          str8 = "";

        str9 = localResultSet.getString(9).trim();
        if (localResultSet.wasNull())
          str9 = "";
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Could not retrieve information from database", "Error", 0);
    }

    Vector localVector = new Vector();
    localVector.add("ETL System");
    localVector.add(paramString1);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("ETL Job");
    localVector.add(paramString2);
    this.tableModel.addRow(localVector);

    if (str1.equals("U")) {
      localVector = new Vector();
      localVector.add("Notified User");
      localVector.add(str2);
      this.tableModel.addRow(localVector);
    } else if (str1.equals("G")) {
      localVector = new Vector();
      localVector.add("Notified Group");
      localVector.add(str3);
      this.tableModel.addRow(localVector);
    }

    localVector = new Vector();
    localVector.add("Notity Timing");

    if (str4.equals("D"))
      localVector.add("When job done");
    else if (str4.equals("F"))
      localVector.add("When job failed");
    else if (str4.equals("M"))
      localVector.add("When job source is missing");
    else if (str4.equals("R"))
      localVector.add("When file receiving error");
    else if (str4.equals("E"))
      localVector.add("When loading job has error record");

    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Attach Log Information");
    if (str5.equals("Y"))
      localVector.add("Yes");
    else
      localVector.add("No");

    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Notity via Email");
    if (str6.equals("Y"))
      localVector.add("Yes");
    else
      localVector.add("No");

    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Notity via Short Message");
    if (str7.equals("Y"))
      localVector.add("Yes");
    else
      localVector.add("No");

    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Message Subject");
    localVector.add(str8);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Message Content");
    localVector.add(str9);
    this.tableModel.addRow(localVector);
  }

  public void showDetailOfServer(String paramString)
  {
    Statement localStatement;
    String str1 = "";
    String str2 = "";
    int i = 0;
    String str3 = this.mainFrame.getDBName();

    clearInfo();
    try
    {
      localStatement = this.mainFrame.getConnection().createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Unable to create statement object", "Error", 0);

      return;
    }
    try
    {
      ResultSet localResultSet = localStatement.executeQuery("SELECT Description, IPAddress,   AgentPort   FROM " + str3 + "ETL_Server" + "   WHERE ETL_Server = '" + paramString + "'");

      if (localResultSet.next())
      {
        str1 = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str1 = "";

        str2 = localResultSet.getString(2);
        if (localResultSet.wasNull())
          str2 = "";

        i = localResultSet.getInt(3);
        if (localResultSet.wasNull())
          i = 0;
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(this, "Could not retrieve information from database", "Error", 0);
    }

    Vector localVector = new Vector();
    localVector.add("Server Name");
    localVector.add(paramString);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Description");
    localVector.add(str1);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("IP Address");
    localVector.add(str2);
    this.tableModel.addRow(localVector);

    localVector = new Vector();
    localVector.add("Agent Port");
    localVector.add(String.valueOf(i));
    this.tableModel.addRow(localVector);
  }

  class TableModel extends DefaultTableModel
  {
    private final ETLInfo this$0;

    public TableModel()
    {
      this.this$0 = paramETLInfo;
    }

    public boolean isCellEditable(, int paramInt2) {
      return false;
    }
  }
}