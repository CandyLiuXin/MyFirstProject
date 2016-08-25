import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

public class ETLAgentInterface
{
  private Socket con;
  private InputStream in;
  private OutputStream out;
  private InputStreamReader irs;
  private int AgentPort;
  public InetAddress ServerAdd;
  public String etlsys;
  public String etljob;
  public String etlserver;

  public int setServerInfo(String paramString, int paramInt)
  {
    try
    {
      this.ServerAdd = InetAddress.getByName(paramString);
      this.AgentPort = paramInt;
    }
    catch (UnknownHostException localUnknownHostException) {
      return 1;
    }
    catch (IOException localIOException) {
      return 2;
    }

    return 0;
  }

  public int getPort()
  {
    return this.AgentPort;
  }

  public String getIP()
  {
    return this.ServerAdd.getHostAddress();
  }

  public int connectToAgent()
  {
    try {
      this.con = new Socket(this.ServerAdd, this.AgentPort);
      this.in = this.con.getInputStream();
      this.out = this.con.getOutputStream();
      this.irs = new InputStreamReader(this.in);
    }
    catch (UnknownHostException localUnknownHostException) {
      return 1;
    }
    catch (IOException localIOException) {
      return 2;
    }

    return 0;
  }

  public int disconnectAgent()
  {
    try {
      if (this.con != null) {
        this.out.flush();

        this.con.close();
        this.con = null;
      }
    }
    catch (IOException localIOException) {
      System.err.println(localIOException);
      return 1;
    }

    return 0;
  }

  public int readHeaderInfo()
  {
    for (int i = 0; i < 3; ++i) {
      String str = read();
      if (str.equals("+OK"))
        return 0;
      if (str.equals(""))
        return 1;
    }

    return 2;
  }

  public String read()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = "";
    try
    {
      int i;
      while ((i = this.irs.read()) != -1) {
        if (i == 13)
          continue;
        if (i == 10)
          break;
        localStringBuffer.append((char)i);
      }
    }
    catch (IOException localIOException) {
      System.err.println(localIOException);
    }

    return localStringBuffer.toString();
  }

  public int write(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramString);
    localStringBuffer.append('\r');
    localStringBuffer.append('\n');

    byte[] arrayOfByte = localStringBuffer.toString().getBytes();

    int i = arrayOfByte.length;
    try
    {
      this.out.write(arrayOfByte, 0, i);
      this.out.flush();
    }
    catch (IOException localIOException) {
      return 1;
    }

    return 0;
  }

  public int sendCmdInvoke(String paramString)
  {
    String str1 = "INVOKE " + paramString;

    write(str1);

    String str2 = read();

    if (str2.startsWith("+OK"))
      return 0;
    if (str2.startsWith("-ERROR"))
      return 1;

    return 2;
  }

  public int sendCmdForce(String paramString)
  {
    String str1 = "FORCE " + paramString;

    write(str1);

    String str2 = read();

    if (str2.startsWith("+OK"))
      return 0;
    if (str2.startsWith("-ERROR"))
      return 1;

    return 2;
  }

  public int sendCmdGetLog(String paramString1, String paramString2, String paramString3)
  {
    String str1 = "GETLOG " + paramString1 + " " + paramString2 + " " + paramString3;

    write(str1);

    String str2 = read();

    if (str2.startsWith("+OK"))
      return 0;
    if (str2.startsWith("-ERROR"))
      return 1;

    return 2;
  }

  public int sendCmdGetScr(String paramString1, String paramString2, String paramString3)
  {
    String str1 = "GETSCR " + paramString1 + " " + paramString2 + " " + paramString3;

    write(str1);

    String str2 = read();

    if (str2.startsWith("+OK"))
      return 0;
    if (str2.startsWith("-ERROR"))
      return 1;

    return 2;
  }

  public int sendCmdPutScr(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    String str1 = "PUTSCR " + paramString1 + " " + paramString2 + " " + paramString3 + " " + paramInt;

    write(str1);

    String str2 = read();

    if (str2.startsWith("+OK"))
      return 0;
    if (str2.startsWith("-ERROR"))
      return 1;

    return 2;
  }

  public int sendCmdQuery(String paramString)
  {
    String str1 = "QUERY " + paramString;

    write(str1);

    String str2 = read();

    if (str2.startsWith("+OK"))
      return 0;
    if (str2.startsWith("-ERROR"))
      return 1;

    return 2;
  }

  public int getLogFile(JTextArea paramJTextArea)
  {
    String str1 = read();

    if (str1.equals(""))
      return 1;

    int i = Integer.parseInt(str1);

    String str2 = "";
    String str3 = "";
    str2 = new String("GB2312");

    for (int j = 0; j < i; ++j) {
      str2 = read();

      paramJTextArea.append(str2);
      paramJTextArea.append("\r\n");
    }

    return 0;
  }

  public int getScriptFile(JTextArea paramJTextArea)
  {
    String str1 = read();

    if (str1.equals(""))
      return 1;

    int i = Integer.parseInt(str1);

    for (int j = 0; j < i; ++j) {
      String str2 = read();
      paramJTextArea.append(str2);
      paramJTextArea.append("\r\n");
    }

    return 0;
  }

  public int putScriptFile(JTextArea paramJTextArea)
  {
    String str = paramJTextArea.getText();

    write(str);

    return 0;
  }

  public static String getJobConvFileHead(Connection paramConnection, String paramString1, String paramString2, String paramString3)
  {
    Statement localStatement;
    String str = "";
    try
    {
      localStatement = paramConnection.createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(null, "Unable to create statement object", "Error", 0);

      return "";
    }
    try
    {
      ResultSet localResultSet = localStatement.executeQuery("SELECT Conv_File_Head   FROM " + paramString1 + "ETL_Job_Source" + "   WHERE ETL_System = '" + paramString2 + "'" + "     AND ETL_Job = '" + paramString3 + "'");

      if (localResultSet.next())
      {
        str = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str = "";
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(null, "Could not retrieve information from database", "Error", 0);

      return "";
    }

    return str;
  }

  public static String getRunningServer(Connection paramConnection, String paramString1, String paramString2, String paramString3)
  {
    Statement localStatement;
    String str = "";
    try
    {
      localStatement = paramConnection.createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(null, "Unable to create statement object", "Error", 0);

      return "";
    }
    try
    {
      ResultSet localResultSet = localStatement.executeQuery("SELECT ETL_Server   FROM " + paramString1 + "ETL_Job" + "   WHERE ETL_System = '" + paramString2 + "'" + "     AND ETL_Job = '" + paramString3 + "'");

      if (localResultSet.next())
      {
        str = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str = "";
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(null, "Could not retrieve information from database", "Error", 0);

      return "";
    }

    return str;
  }

  public static String getJobRunningDate(Connection paramConnection, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Statement localStatement;
    try
    {
      localStatement = paramConnection.createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(null, "Unable to create statement object", "Error", 0);

      return "";
    }

    String str1 = "";
    try
    {
      String str2 = "SELECT StartTime FROM " + paramString1 + "ETL_Job_Status" + "   WHERE ETL_System = '" + paramString2 + "'" + "     AND ETL_Job = '" + paramString3 + "'" + "     AND JobSessionID = " + paramString4;

      ResultSet localResultSet = localStatement.executeQuery(str2);

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
      JOptionPane.showMessageDialog(null, "Could not retrieve information from database", "Error", 0);

      return "";
    }

    if (str1.equals("")) {
      return str1;
    }

    String str3 = str1.substring(0, 4) + str1.substring(5, 7) + str1.substring(8, 10);

    return str3;
  }

  public static int resetJobToPending(Connection paramConnection, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Statement localStatement;
    try
    {
      localStatement = paramConnection.createStatement();
    }
    catch (SQLException localSQLException1)
    {
      return 1;
    }

    String str = "UPDATE " + paramString1 + "ETL_Job SET" + "      Last_TxDate = '" + paramString4 + "'" + "     ,Last_JobStatus = 'Pending'" + "     ,CheckFlag = 'N'" + "   WHERE ETL_System = '" + paramString2 + "'" + "     AND ETL_Job = '" + paramString3 + "'";
    try
    {
      int i = localStatement.executeUpdate(str);
      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      return 2;
    }

    return 0;
  }

  public int getServerInfo(Connection paramConnection, String paramString1, String paramString2)
  {
    Statement localStatement;
    String str = "";
    int i = 6346;
    try
    {
      localStatement = paramConnection.createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(null, "Unable to create statement object", "Error", 0);

      return 1;
    }
    try
    {
      ResultSet localResultSet = localStatement.executeQuery("SELECT IPAddress, AgentPort   FROM " + paramString1 + "ETL_Server" + "   WHERE ETL_Server = '" + paramString2 + "'");

      if (localResultSet.next())
      {
        str = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str = "";

        i = localResultSet.getInt(2);
        if (localResultSet.wasNull())
          i = 6346;
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(null, "Could not retrieve information from database", "Error", 0);

      return 2;
    }
    try
    {
      this.ServerAdd = InetAddress.getByName(str);
      this.AgentPort = i;
    }
    catch (UnknownHostException localUnknownHostException) {
      return 3;
    }
    catch (IOException localIOException) {
      return 4;
    }

    return 0;
  }

  public static int getGroupHeadJob(Connection paramConnection, String paramString1, String paramString2, StringBuffer paramStringBuffer1, StringBuffer paramStringBuffer2)
  {
    Statement localStatement;
    try
    {
      localStatement = paramConnection.createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(null, "Unable to create statement object", "Error", 0);

      return 1;
    }
    try
    {
      ResultSet localResultSet = localStatement.executeQuery("SELECT ETL_System, ETL_Job   FROM " + paramString1 + "ETL_Job_Group" + "   WHERE GroupName = '" + paramString2 + "'");

      if (localResultSet.next())
      {
        paramStringBuffer1.append(localResultSet.getString(1));
        paramStringBuffer2.append(localResultSet.getString(2));
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(null, "Could not retrieve information from database", "Error", 0);

      return 2;
    }

    return 0;
  }
}