import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class ETLTreeView extends JScrollPane
{
  public static int ItemRoot = 0;
  public static int ItemETLSystem = 10;
  public static int ItemSubSystem = 11;
  public static int ItemETLJob = 12;
  public static int ItemDependencyFolder = 13;
  public static int ItemJobStreamFolder = 14;
  public static int ItemRelatedJobFolder = 15;
  public static int ItemJobSource = 16;
  public static int ItemDependentJob = 17;
  public static int ItemNextLevelDependentJob = 18;
  public static int ItemDownStreamJob = 19;
  public static int ItemNextLevelDownStreamJob = 20;
  public static int ItemRelatedJob = 21;
  public static int ItemJobNotifyFolder = 22;
  public static int ItemCalendarYear = 23;
  public static int ItemJobGroupFolder = 30;
  public static int ItemJobGroup = 31;
  public static int ItemJobGroup1 = 32;
  public static int ItemJobGroupChild = 33;
  public static int ItemJobGroupChild1 = 34;
  public static int ItemNotificationFolder = 41;
  public static int ItemNotifyUserFolder = 42;
  public static int ItemNotifyGroupFolder = 43;
  public static int ItemNotifyUser = 44;
  public static int ItemNotifyGroup = 45;
  public static int ItemNotifyGroupMember = 46;
  public static int ItemNotifyDest = 47;
  public static int ItemSystemInfoFolder = 50;
  public static int ItemSystemServerInfo = 51;
  public static int ItemTriggerFolder = 60;
  public static int ItemTriggerJob = 61;
  public static int ItemDependOnFolder = 62;
  public static int ItemDependOnJob = 63;
  private ETLMainFrame mainFrame;
  private JTree jTree;
  private DefaultTreeModel treeModel;
  private DefaultMutableTreeNode nodeRoot;
  private DefaultMutableTreeNode nodeETLSystem;
  private DefaultMutableTreeNode nodeJobGroup;
  private DefaultMutableTreeNode nodeNotification;
  private DefaultMutableTreeNode nodeETLUserFolder;
  private DefaultMutableTreeNode nodeETLGroupFolder;
  private DefaultMutableTreeNode nodeSystemInfoFolder;

  public ETLTreeView(ETLMainFrame paramETLMainFrame)
  {
    this.mainFrame = paramETLMainFrame;

    this.nodeRoot = new DefaultMutableTreeNode(new ETLTreeView.treeItem("Root", ItemRoot));
    this.treeModel = new DefaultTreeModel(this.nodeRoot);

    this.nodeETLSystem = new DefaultMutableTreeNode(new ETLTreeView.treeItem("ETL System", ItemETLSystem));

    this.nodeJobGroup = new DefaultMutableTreeNode(new ETLTreeView.treeItem("Job Group", ItemJobGroupFolder));

    this.nodeNotification = new DefaultMutableTreeNode(new ETLTreeView.treeItem("Notification", ItemNotificationFolder));

    this.nodeETLUserFolder = new DefaultMutableTreeNode(new ETLTreeView.treeItem("User List", ItemNotifyUserFolder));

    this.nodeETLGroupFolder = new DefaultMutableTreeNode(new ETLTreeView.treeItem("User Group", ItemNotifyGroupFolder));

    this.nodeSystemInfoFolder = new DefaultMutableTreeNode(new ETLTreeView.treeItem("System Info", ItemSystemInfoFolder));

    this.jTree = new JTree(this.treeModel);
    this.jTree.getSelectionModel().setSelectionMode(1);

    this.jTree.setCellRenderer(new ETLTreeView.MyRenderer(this));

    this.nodeRoot.add(this.nodeETLSystem);
    this.nodeRoot.add(this.nodeJobGroup);

    this.nodeRoot.add(this.nodeNotification);
    this.nodeRoot.add(this.nodeSystemInfoFolder);

    this.treeModel.insertNodeInto(this.nodeETLUserFolder, this.nodeNotification, 0);
    this.treeModel.insertNodeInto(this.nodeETLGroupFolder, this.nodeNotification, 1);

    this.jTree.expandRow(0);
    this.jTree.setRootVisible(false);

    this.jTree.addTreeSelectionListener(new TreeSelectionListener(this) {
      private final ETLTreeView this$0;

      public void valueChanged() { DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode)ETLTreeView.access$000(this.this$0).getLastSelectedPathComponent();

        if (localDefaultMutableTreeNode == null) return;

        ETLTreeView.access$100(this.this$0).setTreeItemChange(localDefaultMutableTreeNode);
      }

    });
    super.setViewportView(this.jTree);
  }

  public int readETLSystem(Connection paramConnection)
  {
    String str6;
    String str1 = this.mainFrame.getDBName();

    String str2 = this.mainFrame.getBUName();
    String str3 = this.mainFrame.getJobName();
    if (str2.equals(""))
      str2 = "*ALL";
    String str4 = "";

    if ((str2.equals("*ALL")) && (str3.equals("")))
    {
      str4 = "SELECT ETL_system    FROM " + str1 + "ETL_Job";
    }
    else if (str2.equals("*ALL")) {
      str4 = "SELECT ETL_system    FROM " + str1 + "ETL_Job" + "   WHERE etl_job like '%" + str3 + "%' ";
    }
    else if (str3.equals("")) {
      str4 = "SELECT ETL_system    FROM " + str1 + "ETL_Job" + "   WHERE  calendarbu = '" + str2 + "' ";
    }
    else
    {
      str4 = "SELECT ETL_system    FROM " + str1 + "ETL_Job" + "   WHERE calendarbu = '" + str2 + "' " + "   and etl_job like '%" + str3 + "%' ";
    }

    Vector localVector = new Vector();
    try
    {
      Statement localStatement = paramConnection.createStatement();

      String str5 = "SELECT ETL_System    FROM " + str1 + "ETL_Sys" + "   WHERE ETL_System in (" + str4 + ") " + "   ORDER BY ETL_System";

      ResultSet localResultSet = localStatement.executeQuery(str5);

      while (localResultSet.next()) {
        str6 = localResultSet.getString(1);
        localVector.add(str6);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      str6 = "Could not retrieve ETL system\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this.mainFrame, str6, "Error", 0);

      return -1;
    }

    DefaultMutableTreeNode localDefaultMutableTreeNode = null;

    for (int i = 0; i < localVector.size(); ++i) {
      str6 = (String)localVector.get(i);
      localDefaultMutableTreeNode = new DefaultMutableTreeNode(new ETLTreeView.treeItem(str6, ItemSubSystem));
      this.treeModel.insertNodeInto(localDefaultMutableTreeNode, this.nodeETLSystem, this.nodeETLSystem.getChildCount());
    }

    this.jTree.expandRow(0);

    return 0;
  }

  public int readETLJob(Connection paramConnection, String paramString, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    Object localObject;
    DefaultMutableTreeNode localDefaultMutableTreeNode = null;

    String str1 = this.mainFrame.getDBName();
    String str2 = this.mainFrame.getBUName();
    String str3 = this.mainFrame.getJobName();
    if (str2.equals(""))
      str2 = "*ALL";
    String str4 = "";
    try {
      Statement localStatement = paramConnection.createStatement();
      if ((str2.equals("*ALL")) && (str3.equals("")))
      {
        str4 = "SELECT ETL_Job    FROM " + str1 + "ETL_Job" + "   WHERE ETL_System = '" + paramString + "' " + "   ORDER BY ETL_Job";
      }
      else if (str2.equals("*ALL")) {
        str4 = "SELECT ETL_Job    FROM " + str1 + "ETL_Job" + "   WHERE ETL_System = '" + paramString + "' " + "   and etl_job like '%" + str3 + "%' " + "   ORDER BY ETL_Job";
      }
      else if (str3.equals("")) {
        str4 = "SELECT ETL_Job    FROM " + str1 + "ETL_Job" + "   WHERE ETL_System = '" + paramString + "' " + "   and calendarbu = '" + str2 + "' " + "   ORDER BY ETL_Job";
      }
      else
      {
        str4 = "SELECT ETL_Job    FROM " + str1 + "ETL_Job" + "   WHERE ETL_System = '" + paramString + "' " + "   and calendarbu = '" + str2 + "' " + "   and etl_job like '%" + str3 + "%' " + "   ORDER BY ETL_Job";
      }

      ResultSet localResultSet = localStatement.executeQuery(str4);

      while (localResultSet.next())
      {
        String str5 = localResultSet.getString(1);

        localObject = new ETLTreeView.treeItem(str5, ItemETLJob);
        ((ETLTreeView.treeItem)localObject).setSysJob(paramString, str5);

        localDefaultMutableTreeNode = new DefaultMutableTreeNode(localObject);
        paramDefaultMutableTreeNode.add(localDefaultMutableTreeNode);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      localObject = "Could not retrieve job under system '" + paramString + "'\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this.mainFrame, localObject, "Error", 0);

      return -1;
    }

    return 0;
  }

  public int readDependency(Connection paramConnection, String paramString1, String paramString2, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    String str3;
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = null;
    DefaultMutableTreeNode localDefaultMutableTreeNode2 = null;

    localDefaultMutableTreeNode1 = new DefaultMutableTreeNode(new ETLTreeView.treeItem("Dependency", ItemDependencyFolder));

    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = paramConnection.createStatement();
      String str2 = "SELECT A.Dependency_System, A.Dependency_Job,        B.Last_JobStatus, B.Last_TxDate, A.Enable   FROM " + str1 + "ETL_Job_Dependency A," + "        " + str1 + "ETL_Job B" + "   WHERE A.ETL_Job = '" + paramString2 + "'" + "     AND A.ETL_System = '" + paramString1 + "'" + "     AND (A.Dependency_Job = B.ETL_Job AND" + "          A.Dependency_System = B.ETL_System)" + "   ORDER BY A.Dependency_System, A.Dependency_Job";

      ResultSet localResultSet = localStatement.executeQuery(str2);

      int i = 0;

      while (localResultSet.next())
      {
        String str6;
        if (i == 0)
        {
          paramDefaultMutableTreeNode.add(localDefaultMutableTreeNode1);
          i = 1;
        }
        str3 = localResultSet.getString(1);
        String str4 = localResultSet.getString(2);
        String str5 = localResultSet.getString(3);
        if (localResultSet.wasNull()) {
          str5 = "Unknown";
        }

        java.sql.Date localDate = localResultSet.getDate(4);
        if (localResultSet.wasNull())
          str6 = "Unknown";
        else
          str6 = localDate.toString();

        String str7 = localResultSet.getString(5);
        if (localResultSet.wasNull())
          str7 = "1";

        String str8 = "[" + str3 + "] " + str4 + " " + "(Status = '" + str5 + "', " + "TXdate = '" + str6 + "')";

        ETLTreeView.treeItem localtreeItem = new ETLTreeView.treeItem(str8, ItemDependentJob);
        localtreeItem.setSysJob(str3, str4);

        if (str7.equals("1"))
          localtreeItem.setJobStatus(1);
        else
          localtreeItem.setJobStatus(0);

        localDefaultMutableTreeNode2 = new DefaultMutableTreeNode(localtreeItem);

        localDefaultMutableTreeNode1.add(localDefaultMutableTreeNode2);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      str3 = "Could not retrieve dependency for ETL Job " + paramString2 + "\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this.mainFrame, str3, "Error", 0);

      return -1;
    }

    return 0;
  }

  public int readNextLevelDependency(Connection paramConnection, String paramString1, String paramString2, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    String str4;
    Object localObject = null;
    DefaultMutableTreeNode localDefaultMutableTreeNode = null;

    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = paramConnection.createStatement();
      String str2 = "SELECT A.Dependency_System, A.Dependency_Job,        B.Last_JobStatus, B.Last_TxDate   FROM " + str1 + "ETL_Job_Dependency A," + "        " + str1 + "ETL_Job B" + "   WHERE A.ETL_Job = '" + paramString2 + "'" + "     AND A.ETL_System = '" + paramString1 + "'" + "     AND (A.Dependency_Job = B.ETL_Job AND" + "          A.Dependency_System = B.ETL_System)" + "   ORDER BY A.Dependency_System, A.Dependency_Job";

      ResultSet localResultSet = localStatement.executeQuery(str2);

      while (localResultSet.next())
      {
        String str6;
        String str3 = localResultSet.getString(1);
        str4 = localResultSet.getString(2);
        String str5 = localResultSet.getString(3);
        if (localResultSet.wasNull()) {
          str5 = "Unknown";
        }

        java.sql.Date localDate = localResultSet.getDate(4);
        if (localResultSet.wasNull())
          str6 = "Unknown";
        else
          str6 = localDate.toString();

        String str7 = "[" + str3 + "] " + str4 + " " + "(Status = '" + str5 + "', " + "TXdate = '" + str6 + "')";

        ETLTreeView.treeItem localtreeItem = new ETLTreeView.treeItem(str7, ItemNextLevelDependentJob);
        localtreeItem.setSysJob(str3, str4);
        if (str5.equals("Done"))
          localtreeItem.setJobStatus(1);
        else {
          localtreeItem.setJobStatus(0);
        }

        localDefaultMutableTreeNode = new DefaultMutableTreeNode(localtreeItem);

        paramDefaultMutableTreeNode.add(localDefaultMutableTreeNode);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      str4 = "Could not retrieve dependency for ETL Job " + paramString2 + "\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this.mainFrame, str4, "Error", 0);

      return -1;
    }

    return 0;
  }

  public int readJobStream(Connection paramConnection, String paramString1, String paramString2, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    String str3;
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = null;
    DefaultMutableTreeNode localDefaultMutableTreeNode2 = null;

    localDefaultMutableTreeNode1 = new DefaultMutableTreeNode(new ETLTreeView.treeItem("Job Stream", ItemJobStreamFolder));

    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = paramConnection.createStatement();
      String str2 = "SELECT A.Stream_System, A.Stream_Job,        B.Last_JobStatus, B.Last_TxDate, A.Enable   FROM " + str1 + "ETL_Job_Stream A," + "        " + str1 + "ETL_Job B" + "   WHERE A.ETL_Job = '" + paramString2 + "'" + "     AND A.ETL_System = '" + paramString1 + "'" + "     AND (A.Stream_Job = B.ETL_Job AND A.Stream_System = B.ETL_System)" + "   ORDER BY A.Stream_System, A.Stream_Job";

      ResultSet localResultSet = localStatement.executeQuery(str2);

      int i = 0;

      while (localResultSet.next())
      {
        String str6;
        if (i == 0)
        {
          paramDefaultMutableTreeNode.add(localDefaultMutableTreeNode1);
          i = 1;
        }
        str3 = localResultSet.getString(1);
        String str4 = localResultSet.getString(2);
        String str5 = localResultSet.getString(3);
        if (localResultSet.wasNull()) {
          str5 = "Unknown";
        }

        java.sql.Date localDate = localResultSet.getDate(4);
        if (localResultSet.wasNull())
          str6 = "Unknown";
        else
          str6 = localDate.toString();

        String str7 = localResultSet.getString(5);
        if (localResultSet.wasNull())
          str7 = "1";

        String str8 = "[" + str3 + "] " + str4 + " " + "(Status = '" + str5 + "', " + "TXdate = '" + str6 + "')";

        ETLTreeView.treeItem localtreeItem = new ETLTreeView.treeItem(str8, ItemDownStreamJob);
        localtreeItem.setSysJob(str3, str4);

        if (str7.equals("1"))
          localtreeItem.setJobStatus(1);
        else
          localtreeItem.setJobStatus(0);

        localDefaultMutableTreeNode2 = new DefaultMutableTreeNode(localtreeItem);

        localDefaultMutableTreeNode1.add(localDefaultMutableTreeNode2);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      str3 = "Could not retrieve job stream for ETL Job " + paramString2 + "\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this.mainFrame, str3, "Error", 0);

      return -1;
    }

    return 0;
  }

  public int readNextLevelJobStream(Connection paramConnection, String paramString1, String paramString2, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    String str4;
    DefaultMutableTreeNode localDefaultMutableTreeNode = null;

    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = paramConnection.createStatement();
      String str2 = "SELECT A.Stream_System, A.Stream_Job,        B.Last_JobStatus, B.Last_TxDate   FROM " + str1 + "ETL_Job_Stream A," + "        " + str1 + "ETL_Job B" + "   WHERE A.ETL_Job = '" + paramString2 + "'" + "     AND A.ETL_System = '" + paramString1 + "'" + "     AND (A.Stream_Job = B.ETL_Job AND A.Stream_System = B.ETL_System)" + "   ORDER BY A.Stream_System, A.Stream_Job";

      ResultSet localResultSet = localStatement.executeQuery(str2);

      while (localResultSet.next())
      {
        String str6;
        String str3 = localResultSet.getString(1);
        str4 = localResultSet.getString(2);
        String str5 = localResultSet.getString(3);
        if (localResultSet.wasNull()) {
          str5 = "Unknown";
        }

        java.sql.Date localDate = localResultSet.getDate(4);
        if (localResultSet.wasNull())
          str6 = "Unknown";
        else
          str6 = localDate.toString();

        String str7 = "[" + str3 + "] " + str4 + " " + "(Status = '" + str5 + "', " + "TXdate = '" + str6 + "')";

        ETLTreeView.treeItem localtreeItem = new ETLTreeView.treeItem(str7, ItemNextLevelDownStreamJob);
        localtreeItem.setSysJob(str3, str4);
        if (str5.equals("Done"))
          localtreeItem.setJobStatus(1);
        else {
          localtreeItem.setJobStatus(0);
        }

        localDefaultMutableTreeNode = new DefaultMutableTreeNode(localtreeItem);

        paramDefaultMutableTreeNode.add(localDefaultMutableTreeNode);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      str4 = "Could not retrieve job stream for ETL Job " + paramString2 + "\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this.mainFrame, str4, "Error", 0);

      return -1;
    }

    return 0;
  }

  public int readJobRelatedJob(Connection paramConnection, String paramString1, String paramString2, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    String str2;
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = null;
    DefaultMutableTreeNode localDefaultMutableTreeNode2 = null;

    localDefaultMutableTreeNode1 = new DefaultMutableTreeNode(new ETLTreeView.treeItem("Related Job", ItemRelatedJobFolder));

    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = paramConnection.createStatement();
      ResultSet localResultSet = localStatement.executeQuery("SELECT RelatedSystem, RelatedJob    FROM " + str1 + "ETL_RelatedJob" + "   WHERE ETL_Job = '" + paramString2 + "'" + "     AND ETL_System = '" + paramString1 + "'" + "   ORDER BY RelatedSystem, RelatedJob");

      int i = 0;

      while (localResultSet.next())
      {
        if (i == 0)
        {
          paramDefaultMutableTreeNode.add(localDefaultMutableTreeNode1);
          i = 1;
        }
        str2 = localResultSet.getString(1);
        String str3 = localResultSet.getString(2);

        String str4 = "[" + str2 + "] " + str3;

        localDefaultMutableTreeNode2 = new DefaultMutableTreeNode(new ETLTreeView.treeItem(str4, ItemRelatedJob));

        localDefaultMutableTreeNode1.add(localDefaultMutableTreeNode2);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      str2 = "Could not retrieve related cube job for ETL Job " + paramString2 + "\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this.mainFrame, str2, "Error", 0);

      return -1;
    }

    return 0;
  }

  public int readJobNotification(Connection paramConnection, String paramString1, String paramString2, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = null;
    DefaultMutableTreeNode localDefaultMutableTreeNode2 = null;

    localDefaultMutableTreeNode1 = new DefaultMutableTreeNode(new ETLTreeView.treeItem("Notification", ItemJobNotifyFolder));

    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = paramConnection.createStatement();
      ResultSet localResultSet = localStatement.executeQuery("SELECT SeqID, DestType, UserName, GroupName    FROM " + str1 + "ETL_Notification" + "   WHERE ETL_Job = '" + paramString2 + "'" + "     AND ETL_System = '" + paramString1 + "'" + "   ORDER BY SeqID");

      int i = 0;

      while (localResultSet.next())
      {
        String str6;
        if (i == 0)
        {
          paramDefaultMutableTreeNode.add(localDefaultMutableTreeNode1);
          i = 1;
        }
        int j = localResultSet.getInt(1);
        String str3 = localResultSet.getString(2);
        String str4 = localResultSet.getString(3);
        String str5 = localResultSet.getString(4);

        if (str3.equals("U"))
          str6 = str4;
        else
          str6 = str5;

        ETLTreeView.treeItem localtreeItem = new ETLTreeView.treeItem(str6, ItemNotifyDest);
        localtreeItem.setSeq(j);
        localtreeItem.setSysJob(paramString1, paramString2);

        localDefaultMutableTreeNode2 = new DefaultMutableTreeNode(localtreeItem);

        localDefaultMutableTreeNode1.add(localDefaultMutableTreeNode2);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str2 = "Could not retrieve job notification for ETL Job " + paramString2 + "\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this.mainFrame, str2, "Error", 0);

      return -1;
    }

    return 0;
  }

  public int readSource(Connection paramConnection, String paramString1, String paramString2, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = null;

    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = paramConnection.createStatement();
      ResultSet localResultSet = localStatement.executeQuery("SELECT source    FROM " + str1 + "etl_job_source" + "   WHERE etl_job = '" + paramString2 + "'" + "     AND etl_system = '" + paramString1 + "'" + "   ORDER BY source");

      while (localResultSet.next())
      {
        String str2 = localResultSet.getString(1);

        localDefaultMutableTreeNode = new DefaultMutableTreeNode(new ETLTreeView.treeItem(str2, ItemJobSource));

        paramDefaultMutableTreeNode.add(localDefaultMutableTreeNode);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str3 = "Could not retrieve source for ETL Job " + paramString2 + "\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this.mainFrame, str3, "Error", 0);

      return -1;
    }

    return 0;
  }

  public int readCalendarYear(Connection paramConnection, String paramString1, String paramString2, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    String str2;
    DefaultMutableTreeNode localDefaultMutableTreeNode = null;

    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = paramConnection.createStatement();
      ResultSet localResultSet = localStatement.executeQuery("SELECT calendaryear    FROM " + str1 + "datacalendaryear" + "   WHERE etl_job = '" + paramString2 + "'" + "     AND etl_system = '" + paramString1 + "'" + "   ORDER BY calendaryear");

      while (localResultSet.next())
      {
        int i = localResultSet.getInt(1);
        str2 = "Calendar Year [" + String.valueOf(i) + "]";

        localDefaultMutableTreeNode = new DefaultMutableTreeNode(new ETLTreeView.treeItem(str2, ItemCalendarYear));

        paramDefaultMutableTreeNode.add(localDefaultMutableTreeNode);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      str2 = "Could not retrieve data calendar year for ETL Job " + paramString2 + "\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this.mainFrame, str2, "Error", 0);

      return -1;
    }

    return 0;
  }

  public int readJobGroup(Connection paramConnection)
  {
    String str4;
    String str6;
    String str1 = this.mainFrame.getDBName();

    String str2 = this.mainFrame.getBUName();
    String str3 = this.mainFrame.getJobName();

    if (str2.equals(""))
      str2 = "*ALL";

    if ((str2.equals("*ALL")) && (str3.equals("")))
    {
      str4 = "SELECT A.GroupName   FROM " + str1 + "etl_job_group A";
    }
    else if (str2.equals("*ALL")) {
      str4 = "SELECT A.GroupName   FROM " + str1 + "etl_job_group A" + "   WHERE A.ETL_Job like '%" + str3 + "%'";
    }
    else if (str3.equals("")) {
      str4 = "SELECT A.GroupName   FROM " + str1 + "etl_job_group A," + str1 + "ETL_Job B" + "   WHERE B.ETL_Job = A.Etl_job" + "   and B.calendarbu  ='" + str2 + "'";
    }
    else
    {
      str4 = "SELECT A.GroupName   FROM " + str1 + "etl_job_group A," + str1 + "ETL_Job B" + "   WHERE  A.ETL_Job like '%" + str3 + "%'" + "   and B.ETL_Job = A.Etl_job" + "   and B.calendarbu  ='" + str2 + "'";
    }

    Vector localVector = new Vector();
    try
    {
      Statement localStatement = paramConnection.createStatement();

      String str5 = "SELECT groupname    FROM " + str1 + "etl_job_group" + "   WHERE  groupname in (" + str4 + ")" + "   ORDER BY groupname";

      ResultSet localResultSet = localStatement.executeQuery(str5);

      while (localResultSet.next()) {
        str6 = localResultSet.getString(1);
        localVector.add(str6);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      str6 = "Could not retrieve job group\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this.mainFrame, str6, "Error", 0);

      return -1;
    }

    DefaultMutableTreeNode localDefaultMutableTreeNode = null;

    for (int i = 0; i < localVector.size(); ++i) {
      str6 = (String)localVector.get(i);
      localDefaultMutableTreeNode = new DefaultMutableTreeNode(new ETLTreeView.treeItem(str6, ItemJobGroup));
      this.treeModel.insertNodeInto(localDefaultMutableTreeNode, this.nodeJobGroup, this.nodeJobGroup.getChildCount());
    }

    return 0;
  }

  public int readJobGroupByHeadJob(Connection paramConnection, String paramString1, String paramString2, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    String str3;
    String str1 = this.mainFrame.getDBName();

    Vector localVector = new Vector();
    try
    {
      Statement localStatement = paramConnection.createStatement();

      String str2 = "SELECT GroupName    FROM " + str1 + "ETL_Job_Group" + "  WHERE ETL_System = '" + paramString1 + "'" + "    AND ETL_Job = '" + paramString2 + "'" + "   ORDER BY GroupName";

      ResultSet localResultSet = localStatement.executeQuery(str2);

      while (localResultSet.next()) {
        str3 = localResultSet.getString(1);
        localVector.add(str3);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      return -1;
    }

    DefaultMutableTreeNode localDefaultMutableTreeNode = null;

    for (int i = 0; i < localVector.size(); ++i) {
      str3 = (String)localVector.get(i);

      ETLTreeView.treeItem localtreeItem = new ETLTreeView.treeItem(str3, ItemJobGroup1);
      localDefaultMutableTreeNode = new DefaultMutableTreeNode(localtreeItem);
      paramDefaultMutableTreeNode.add(localDefaultMutableTreeNode);
    }

    return 0;
  }

  public int readJobGroupChild(Connection paramConnection, String paramString, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    String str5;
    String str1 = this.mainFrame.getDBName();

    String str3 = this.mainFrame.getBUName();
    String str4 = this.mainFrame.getJobName();
    if (str3.equals("")) {
      str3 = "*ALL";
    }

    try
    {
      String str2;
      Statement localStatement = paramConnection.createStatement();

      if ((str3.equals("*ALL")) && (str4.equals("")))
      {
        str2 = "SELECT A.ETL_System, A.ETL_Job,        A.TXDate, A.CheckFlag, A.Enable   FROM " + str1 + "ETL_Job_GroupChild A" + "   WHERE A.GroupName = '" + paramString + "'" + "   ORDER BY A.ETL_System, A.ETL_Job";
      }
      else if (str3.equals("*ALL")) {
        str2 = "SELECT A.ETL_System, A.ETL_Job,        A.TXDate, A.CheckFlag, A.Enable   FROM " + str1 + "ETL_Job_GroupChild A" + "   WHERE A.GroupName = '" + paramString + "'" + "   and A.ETL_Job like '%" + str4 + "%'" + "   ORDER BY A.ETL_System, A.ETL_Job";
      }
      else if (str4.equals("")) {
        str2 = "SELECT A.ETL_System, A.ETL_Job,        A.TXDate, A.CheckFlag, A.Enable   FROM " + str1 + "ETL_Job_GroupChild A," + str1 + "ETL_Job B" + "   WHERE A.GroupName = '" + paramString + "'" + "   and B.ETL_Job = A.Etl_job" + "   and B.calendarbu  ='" + str3 + "'" + "   ORDER BY A.ETL_System, A.ETL_Job";
      }
      else
      {
        str2 = "SELECT A.ETL_System, A.ETL_Job,        A.TXDate, A.CheckFlag, A.Enable   FROM " + str1 + "ETL_Job_GroupChild A," + str1 + "ETL_Job B" + "   WHERE A.GroupName = '" + paramString + "'" + "   and A.ETL_Job like '%" + str4 + "%'" + "   and B.ETL_Job = A.Etl_job" + "   and B.calendarbu  ='" + str3 + "'" + "   ORDER BY A.ETL_System, A.ETL_Job";
      }

      ResultSet localResultSet = localStatement.executeQuery(str2);

      DefaultMutableTreeNode localDefaultMutableTreeNode = null;

      while (localResultSet.next()) {
        str5 = localResultSet.getString(1);
        String str6 = localResultSet.getString(2);

        String str7 = localResultSet.getString(3);
        if (localResultSet.wasNull())
          str7 = "Unknown";

        String str8 = localResultSet.getString(4);
        if (localResultSet.wasNull())
          str8 = "N";

        String str9 = localResultSet.getString(5);
        if (localResultSet.wasNull())
          str9 = "1";

        String str10 = "[" + str5 + "] " + str6 + " " + "(TXdate = '" + str7 + "', Flag = '" + str8 + "')";

        ETLTreeView.treeItem localtreeItem = new ETLTreeView.treeItem(str10, ItemJobGroupChild);
        localtreeItem.setSysJob(str5, str6);
        if (str9.equals("1"))
          localtreeItem.setJobStatus(1);
        else
          localtreeItem.setJobStatus(0);

        localDefaultMutableTreeNode = new DefaultMutableTreeNode(localtreeItem);
        this.treeModel.insertNodeInto(localDefaultMutableTreeNode, paramDefaultMutableTreeNode, paramDefaultMutableTreeNode.getChildCount());
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      str5 = "Could not retrieve job group\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this.mainFrame, str5, "Error", 0);

      return -1;
    }

    return 0;
  }

  public int readJobGroupChild1(Connection paramConnection, String paramString, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    String str3;
    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = paramConnection.createStatement();

      String str2 = "SELECT A.ETL_System, A.ETL_Job,        A.TXDate, A.CheckFlag   FROM " + str1 + "ETL_Job_GroupChild A" + "   WHERE A.GroupName = '" + paramString + "'" + "   ORDER BY A.ETL_System, A.ETL_Job";

      ResultSet localResultSet = localStatement.executeQuery(str2);

      DefaultMutableTreeNode localDefaultMutableTreeNode = null;

      while (localResultSet.next()) {
        str3 = localResultSet.getString(1);
        String str4 = localResultSet.getString(2);

        String str5 = localResultSet.getString(3);
        if (localResultSet.wasNull())
          str5 = "Unknown";

        String str6 = localResultSet.getString(4);
        if (localResultSet.wasNull())
          str6 = "N";

        String str7 = "[" + str3 + "] " + str4 + " " + "(TXdate = '" + str5 + "')";

        ETLTreeView.treeItem localtreeItem = new ETLTreeView.treeItem(str7, ItemJobGroupChild1);
        localtreeItem.setSysJob(str3, str4);
        if (str6.equals("Y"))
          localtreeItem.setJobStatus(1);
        else {
          localtreeItem.setJobStatus(0);
        }

        localDefaultMutableTreeNode = new DefaultMutableTreeNode(localtreeItem);
        paramDefaultMutableTreeNode.add(localDefaultMutableTreeNode);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      str3 = "Could not retrieve job group\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this.mainFrame, str3, "Error", 0);

      return -1;
    }

    return 0;
  }

  public int readNotifyUser(Connection paramConnection, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    Object localObject;
    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = paramConnection.createStatement();

      String str2 = "SELECT username    FROM " + str1 + "etl_user" + "   ORDER BY username";

      ResultSet localResultSet = localStatement.executeQuery(str2);

      localObject = null;

      while (localResultSet.next()) {
        String str3 = localResultSet.getString(1);

        localObject = new DefaultMutableTreeNode(new ETLTreeView.treeItem(str3, ItemNotifyUser));
        this.treeModel.insertNodeInto((MutableTreeNode)localObject, paramDefaultMutableTreeNode, paramDefaultMutableTreeNode.getChildCount());
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      localObject = "Could not retrieve user information\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this.mainFrame, localObject, "Error", 0);

      return -1;
    }

    return 0;
  }

  public int readNotifyGroup(Connection paramConnection, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    Object localObject;
    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = paramConnection.createStatement();

      String str2 = "SELECT GroupName    FROM " + str1 + "ETL_UserGroup" + "   ORDER BY GroupName";

      ResultSet localResultSet = localStatement.executeQuery(str2);

      localObject = null;

      while (localResultSet.next()) {
        String str3 = localResultSet.getString(1);

        localObject = new DefaultMutableTreeNode(new ETLTreeView.treeItem(str3, ItemNotifyGroup));
        this.treeModel.insertNodeInto((MutableTreeNode)localObject, paramDefaultMutableTreeNode, paramDefaultMutableTreeNode.getChildCount());
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      localObject = "Could not retrieve user group information\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this.mainFrame, localObject, "Error", 0);

      return -1;
    }

    return 0;
  }

  public int readNotifyGroupMember(Connection paramConnection, DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString)
  {
    Object localObject;
    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = paramConnection.createStatement();

      String str2 = "SELECT UserName    FROM " + str1 + "ETL_GroupMember" + "  WHERE GroupName = '" + paramString + "'" + "   ORDER BY UserName";

      ResultSet localResultSet = localStatement.executeQuery(str2);

      localObject = null;

      while (localResultSet.next()) {
        String str3 = localResultSet.getString(1);

        localObject = new DefaultMutableTreeNode(new ETLTreeView.treeItem(str3, ItemNotifyGroupMember));
        this.treeModel.insertNodeInto((MutableTreeNode)localObject, paramDefaultMutableTreeNode, paramDefaultMutableTreeNode.getChildCount());
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      localObject = "Could not retrieve group member information\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this.mainFrame, localObject, "Error", 0);

      return -1;
    }

    return 0;
  }

  public int readETLServer(Connection paramConnection)
  {
    String str3;
    String str1 = this.mainFrame.getDBName();

    Vector localVector = new Vector();
    try
    {
      Statement localStatement = paramConnection.createStatement();

      String str2 = "SELECT ETL_Server    FROM " + str1 + "ETL_Server" + "   ORDER BY ETL_Server";

      ResultSet localResultSet = localStatement.executeQuery(str2);

      while (localResultSet.next()) {
        str3 = localResultSet.getString(1);
        localVector.add(str3);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      str3 = "Could not retrieve ETL server\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this.mainFrame, str3, "Error", 0);

      return -1;
    }

    DefaultMutableTreeNode localDefaultMutableTreeNode = null;

    for (int i = 0; i < localVector.size(); ++i) {
      str3 = (String)localVector.get(i);
      localDefaultMutableTreeNode = new DefaultMutableTreeNode(new ETLTreeView.treeItem(str3, ItemSystemServerInfo));
      this.treeModel.insertNodeInto(localDefaultMutableTreeNode, this.nodeSystemInfoFolder, this.nodeSystemInfoFolder.getChildCount());
    }

    return 0;
  }

  public int readTriggerJob(Connection paramConnection, String paramString1, String paramString2, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = null;
    DefaultMutableTreeNode localDefaultMutableTreeNode2 = null;

    localDefaultMutableTreeNode1 = new DefaultMutableTreeNode(new ETLTreeView.treeItem("Trigger By", ItemTriggerFolder));

    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = paramConnection.createStatement();
      String str2 = "SELECT A.ETL_System, A.ETL_Job,        B.Last_JobStatus, B.Last_TxDate   FROM " + str1 + "ETL_Job_Stream A," + "        " + str1 + "ETL_Job B" + "   WHERE A.Stream_Job = '" + paramString2 + "'" + "     AND A.Stream_System = '" + paramString1 + "'" + "     AND (A.ETL_Job = B.ETL_Job AND A.ETL_System = B.ETL_System)" + "   ORDER BY A.ETL_System, A.ETL_Job";

      ResultSet localResultSet = localStatement.executeQuery(str2);

      int i = 0;

      while (localResultSet.next())
      {
        String str6;
        if (i == 0)
        {
          paramDefaultMutableTreeNode.add(localDefaultMutableTreeNode1);
          i = 1;
        }
        String str3 = localResultSet.getString(1);
        String str4 = localResultSet.getString(2);
        String str5 = localResultSet.getString(3);
        if (localResultSet.wasNull()) {
          str5 = "Unknown";
        }

        java.sql.Date localDate = localResultSet.getDate(4);
        if (localResultSet.wasNull())
          str6 = "Unknown";
        else
          str6 = localDate.toString();

        String str7 = "[" + str3 + "] " + str4 + " " + "(TXdate = '" + str6 + "')";

        ETLTreeView.treeItem localtreeItem = new ETLTreeView.treeItem(str7, ItemTriggerJob);
        localtreeItem.setSysJob(str3, str4);
        if (str5.equals("Done"))
          localtreeItem.setJobStatus(1);
        else
          localtreeItem.setJobStatus(0);

        localDefaultMutableTreeNode2 = new DefaultMutableTreeNode(localtreeItem);

        localDefaultMutableTreeNode1.add(localDefaultMutableTreeNode2);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      return -1;
    }

    return 0;
  }

  public int readDependentJob(Connection paramConnection, String paramString1, String paramString2, DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = null;
    DefaultMutableTreeNode localDefaultMutableTreeNode2 = null;

    localDefaultMutableTreeNode1 = new DefaultMutableTreeNode(new ETLTreeView.treeItem("Depend On", ItemDependOnFolder));

    String str1 = this.mainFrame.getDBName();
    try
    {
      Statement localStatement = paramConnection.createStatement();
      String str2 = "SELECT A.Dependency_System, A.Dependency_Job,        B.Last_JobStatus, B.Last_TxDate   FROM " + str1 + "ETL_Job_Dependency A," + "        " + str1 + "ETL_Job B" + "   WHERE A.ETL_Job = '" + paramString2 + "'" + "     AND A.ETL_System = '" + paramString1 + "'" + "     AND (A.Dependency_Job = B.ETL_Job AND A.Dependency_System = B.ETL_System)" + "   ORDER BY A.Dependency_System, A.Dependency_Job";

      ResultSet localResultSet = localStatement.executeQuery(str2);

      int i = 0;

      while (localResultSet.next())
      {
        String str6;
        if (i == 0)
        {
          paramDefaultMutableTreeNode.add(localDefaultMutableTreeNode1);
          i = 1;
        }
        String str3 = localResultSet.getString(1);
        String str4 = localResultSet.getString(2);
        String str5 = localResultSet.getString(3);
        if (localResultSet.wasNull()) {
          str5 = "Unknown";
        }

        java.sql.Date localDate = localResultSet.getDate(4);
        if (localResultSet.wasNull())
          str6 = "Unknown";
        else
          str6 = localDate.toString();

        String str7 = "[" + str3 + "] " + str4 + " " + "(TXdate = '" + str6 + "')";

        ETLTreeView.treeItem localtreeItem = new ETLTreeView.treeItem(str7, ItemDependOnJob);
        localtreeItem.setSysJob(str3, str4);
        if (str5.equals("Done"))
          localtreeItem.setJobStatus(1);
        else
          localtreeItem.setJobStatus(0);

        localDefaultMutableTreeNode2 = new DefaultMutableTreeNode(localtreeItem);

        localDefaultMutableTreeNode1.add(localDefaultMutableTreeNode2);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      return -1;
    }

    return 0;
  }

  public JTree getTree()
  {
    return this.jTree;
  }

  public void setTreeRoot(int paramInt) {
    this.jTree.setSelectionPath(this.jTree.getPathForRow(paramInt));
  }

  public DefaultTreeModel getTreeModel() {
    return this.treeModel;
  }

  public DefaultMutableTreeNode getSelectedNode()
  {
    TreePath localTreePath = this.jTree.getSelectionPath();

    if (localTreePath != null)
      return ((DefaultMutableTreeNode)localTreePath.getLastPathComponent());

    return null;
  }

  public void expand()
  {
    TreePath localTreePath = this.jTree.getSelectionPath();

    if (localTreePath != null)
      this.jTree.expandPath(localTreePath);
  }

  public void removeAllParent()
  {
    this.nodeETLSystem.removeAllChildren();
    this.treeModel.reload(this.nodeETLSystem);

    this.nodeJobGroup.removeAllChildren();
    this.treeModel.reload(this.nodeJobGroup);

    this.nodeETLUserFolder.removeAllChildren();
    this.treeModel.reload(this.nodeETLUserFolder);

    this.nodeETLGroupFolder.removeAllChildren();
    this.treeModel.reload(this.nodeETLGroupFolder);

    this.nodeETLUserFolder.removeAllChildren();
    this.treeModel.reload(this.nodeETLUserFolder);

    this.nodeETLGroupFolder.removeAllChildren();
    this.treeModel.reload(this.nodeETLGroupFolder);

    this.nodeSystemInfoFolder.removeAllChildren();
    this.treeModel.reload(this.nodeSystemInfoFolder);
  }

  public void removeNodeAllChild(DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    paramDefaultMutableTreeNode.removeAllChildren();
    this.treeModel.reload(paramDefaultMutableTreeNode);
  }

  public String getItemName(DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    ETLTreeView.treeItem localtreeItem = (ETLTreeView.treeItem)paramDefaultMutableTreeNode.getUserObject();
    return localtreeItem.itemName;
  }

  public int getItemType(DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    ETLTreeView.treeItem localtreeItem = (ETLTreeView.treeItem)paramDefaultMutableTreeNode.getUserObject();
    return localtreeItem.itemType;
  }

  public String getSysName(DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    ETLTreeView.treeItem localtreeItem = (ETLTreeView.treeItem)paramDefaultMutableTreeNode.getUserObject();
    return localtreeItem.etlsys;
  }

  public String getJobName(DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    ETLTreeView.treeItem localtreeItem = (ETLTreeView.treeItem)paramDefaultMutableTreeNode.getUserObject();
    return localtreeItem.etljob;
  }

  public int getSeq(DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    ETLTreeView.treeItem localtreeItem = (ETLTreeView.treeItem)paramDefaultMutableTreeNode.getUserObject();
    return localtreeItem.seq;
  }

  public void setItemName(DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString)
  {
    ETLTreeView.treeItem localtreeItem = (ETLTreeView.treeItem)paramDefaultMutableTreeNode.getUserObject();
    localtreeItem.itemName = paramString;
  }

  public void setItemStatus(DefaultMutableTreeNode paramDefaultMutableTreeNode, int paramInt)
  {
    ETLTreeView.treeItem localtreeItem = (ETLTreeView.treeItem)paramDefaultMutableTreeNode.getUserObject();
    localtreeItem.jobStatus = paramInt;
  }

  public void addETLSubsystem(DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString)
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = new DefaultMutableTreeNode(new ETLTreeView.treeItem(paramString, ItemSubSystem));
    this.treeModel.insertNodeInto(localDefaultMutableTreeNode, paramDefaultMutableTreeNode, paramDefaultMutableTreeNode.getChildCount());
  }

  public void addETLServer(DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString)
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = new DefaultMutableTreeNode(new ETLTreeView.treeItem(paramString, ItemSystemServerInfo));
    this.treeModel.insertNodeInto(localDefaultMutableTreeNode, paramDefaultMutableTreeNode, paramDefaultMutableTreeNode.getChildCount());
  }

  public void addETLJob(DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString1, String paramString2)
  {
    ETLTreeView.treeItem localtreeItem = new ETLTreeView.treeItem(paramString2, ItemETLJob);
    localtreeItem.setSysJob(paramString1, paramString2);

    DefaultMutableTreeNode localDefaultMutableTreeNode = new DefaultMutableTreeNode(localtreeItem);
    this.treeModel.insertNodeInto(localDefaultMutableTreeNode, paramDefaultMutableTreeNode, paramDefaultMutableTreeNode.getChildCount());
  }

  public void addJobSource(DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString)
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = new DefaultMutableTreeNode(new ETLTreeView.treeItem(paramString, ItemJobSource));
    this.treeModel.insertNodeInto(localDefaultMutableTreeNode, paramDefaultMutableTreeNode, paramDefaultMutableTreeNode.getChildCount());
  }

  public void addCalendarYear(DefaultMutableTreeNode paramDefaultMutableTreeNode, int paramInt)
  {
    String str = "Calendar Year [" + String.valueOf(paramInt) + "]";

    DefaultMutableTreeNode localDefaultMutableTreeNode = new DefaultMutableTreeNode(new ETLTreeView.treeItem(str, ItemCalendarYear));
    this.treeModel.insertNodeInto(localDefaultMutableTreeNode, paramDefaultMutableTreeNode, paramDefaultMutableTreeNode.getChildCount());
  }

  public void addDependentJob(DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    ETLTreeView.treeItem localtreeItem = new ETLTreeView.treeItem(paramString3, ItemDependentJob);
    localtreeItem.setSysJob(paramString1, paramString2);
    if (paramString4.equals("1"))
      localtreeItem.setJobStatus(1);
    else
      localtreeItem.setJobStatus(0);

    DefaultMutableTreeNode localDefaultMutableTreeNode = new DefaultMutableTreeNode(localtreeItem);
    this.treeModel.insertNodeInto(localDefaultMutableTreeNode, paramDefaultMutableTreeNode, paramDefaultMutableTreeNode.getChildCount());
  }

  public void addDownStreamJob(DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    ETLTreeView.treeItem localtreeItem = new ETLTreeView.treeItem(paramString3, ItemDownStreamJob);
    localtreeItem.setSysJob(paramString1, paramString2);
    if (paramString4.equals("1"))
      localtreeItem.setJobStatus(1);
    else
      localtreeItem.setJobStatus(0);

    DefaultMutableTreeNode localDefaultMutableTreeNode = new DefaultMutableTreeNode(localtreeItem);
    this.treeModel.insertNodeInto(localDefaultMutableTreeNode, paramDefaultMutableTreeNode, paramDefaultMutableTreeNode.getChildCount());
  }

  public void addRelatedJob(DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString)
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = new DefaultMutableTreeNode(new ETLTreeView.treeItem(paramString, ItemRelatedJob));
    this.treeModel.insertNodeInto(localDefaultMutableTreeNode, paramDefaultMutableTreeNode, paramDefaultMutableTreeNode.getChildCount());
  }

  public void addJobGroup(DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString)
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = new DefaultMutableTreeNode(new ETLTreeView.treeItem(paramString, ItemJobGroup));
    this.treeModel.insertNodeInto(localDefaultMutableTreeNode, paramDefaultMutableTreeNode, paramDefaultMutableTreeNode.getChildCount());
  }

  public void addJobGroupChild(DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString1, String paramString2, String paramString3)
  {
    String str = "[" + paramString1 + "] " + paramString2 + " ";

    ETLTreeView.treeItem localtreeItem = new ETLTreeView.treeItem(str, ItemJobGroupChild);
    localtreeItem.setSysJob(paramString1, paramString2);
    if (paramString3.equals("1"))
      localtreeItem.setJobStatus(1);
    else
      localtreeItem.setJobStatus(0);

    DefaultMutableTreeNode localDefaultMutableTreeNode = new DefaultMutableTreeNode(localtreeItem);
    this.treeModel.insertNodeInto(localDefaultMutableTreeNode, paramDefaultMutableTreeNode, paramDefaultMutableTreeNode.getChildCount());
  }

  public void addNotifyUser(DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString)
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = new DefaultMutableTreeNode(new ETLTreeView.treeItem(paramString, ItemNotifyUser));
    this.treeModel.insertNodeInto(localDefaultMutableTreeNode, paramDefaultMutableTreeNode, paramDefaultMutableTreeNode.getChildCount());
  }

  public void addNotifyGroup(DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString)
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = new DefaultMutableTreeNode(new ETLTreeView.treeItem(paramString, ItemNotifyGroup));
    this.treeModel.insertNodeInto(localDefaultMutableTreeNode, paramDefaultMutableTreeNode, paramDefaultMutableTreeNode.getChildCount());
  }

  public void addNotifyGroupMember(DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString)
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = new DefaultMutableTreeNode(new ETLTreeView.treeItem(paramString, ItemNotifyGroupMember));
    this.treeModel.insertNodeInto(localDefaultMutableTreeNode, paramDefaultMutableTreeNode, paramDefaultMutableTreeNode.getChildCount());
  }

  public void addNotifyJob(DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString1, String paramString2, int paramInt, String paramString3)
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = new DefaultMutableTreeNode(new ETLTreeView.treeItem("Notification", ItemJobNotifyFolder));
    this.treeModel.insertNodeInto(localDefaultMutableTreeNode1, paramDefaultMutableTreeNode, paramDefaultMutableTreeNode.getChildCount());

    ETLTreeView.treeItem localtreeItem = new ETLTreeView.treeItem(paramString3, ItemNotifyDest);
    localtreeItem.setSysJob(paramString1, paramString2);
    localtreeItem.setSeq(paramInt);

    DefaultMutableTreeNode localDefaultMutableTreeNode2 = new DefaultMutableTreeNode(localtreeItem);
    this.treeModel.insertNodeInto(localDefaultMutableTreeNode2, localDefaultMutableTreeNode1, localDefaultMutableTreeNode1.getChildCount());
  }

  public void searchSubSystem(String paramString)
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = null;
    Object localObject = null;
    DefaultMutableTreeNode localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)this.jTree.getModel().getRoot();

    localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode2.getChildAt(0);

    for (Enumeration localEnumeration = localDefaultMutableTreeNode2.depthFirstEnumeration(); localEnumeration.hasMoreElements(); ) {
      DefaultMutableTreeNode localDefaultMutableTreeNode3 = (DefaultMutableTreeNode)localEnumeration.nextElement();

      if (!(localDefaultMutableTreeNode3.getUserObject().equals(paramString)))
        break label87;
      JOptionPane.showMessageDialog(this, localDefaultMutableTreeNode2);
      localObject = localDefaultMutableTreeNode3;
      localDefaultMutableTreeNode1 = (DefaultMutableTreeNode)localDefaultMutableTreeNode3.getParent();
      label87: break;
    }

    if (localDefaultMutableTreeNode1 != null) {
      this.jTree.expandPath(new TreePath(localDefaultMutableTreeNode1.getPath()));
      this.jTree.setSelectionPath(new TreePath(localObject.getPath()));
    }
  }

  public void addNotifyJob1(DefaultMutableTreeNode paramDefaultMutableTreeNode, String paramString1, String paramString2, int paramInt, String paramString3)
  {
    ETLTreeView.treeItem localtreeItem = new ETLTreeView.treeItem(paramString3, ItemNotifyDest);
    localtreeItem.setSysJob(paramString1, paramString2);
    localtreeItem.setSeq(paramInt);

    DefaultMutableTreeNode localDefaultMutableTreeNode = new DefaultMutableTreeNode(localtreeItem);
    this.treeModel.insertNodeInto(localDefaultMutableTreeNode, paramDefaultMutableTreeNode, paramDefaultMutableTreeNode.getChildCount());
  }

  static JTree access$000(ETLTreeView paramETLTreeView)
  {
    return paramETLTreeView.jTree; } 
  static ETLMainFrame access$100(ETLTreeView paramETLTreeView) { return paramETLTreeView.mainFrame;
  }

  private class MyRenderer extends DefaultTreeCellRenderer
  {
    ImageIcon systemIcon;
    ImageIcon jobIcon;
    ImageIcon sourceIcon;
    ImageIcon calendarIcon;
    ImageIcon depJobIcon;
    ImageIcon depJobDisableIcon;
    ImageIcon streamJobIcon;
    ImageIcon streamJobDisableIcon;
    ImageIcon childJobIcon;
    ImageIcon childJobDisableIcon;
    ImageIcon relJobIcon;
    ImageIcon mailIcon;
    ImageIcon systemInfoIcon;
    ImageIcon serverIcon;
    ImageIcon notificationIcon;
    ImageIcon jobGroupFolderIcon;
    ImageIcon jobGroupIcon;
    ImageIcon folderIcon;
    ImageIcon redfolderIcon;
    ImageIcon peopleIcon;
    ImageIcon doneJobIcon;
    ImageIcon failedJobIcon;
    private final ETLTreeView this$0;

    public MyRenderer()
    {
      this.this$0 = paramETLTreeView;
      this.systemIcon = new ImageIcon(getClass().getResource("/images/System24x24.gif"));
      this.jobIcon = new ImageIcon(getClass().getResource("/images/Job24x24.gif"));
      this.sourceIcon = new ImageIcon(getClass().getResource("/images/Source24x24.gif"));
      this.calendarIcon = new ImageIcon(getClass().getResource("/images/Clock24x24.gif"));
      this.depJobIcon = new ImageIcon(getClass().getResource("/images/DepJob24x24.gif"));
      this.depJobDisableIcon = new ImageIcon(getClass().getResource("/images/DepJobDisable24x24.gif"));
      this.streamJobIcon = new ImageIcon(getClass().getResource("/images/StreamJob24x24.gif"));
      this.streamJobDisableIcon = new ImageIcon(getClass().getResource("/images/StreamJobDisable24x24.gif"));
      this.childJobIcon = new ImageIcon(getClass().getResource("/images/ChildJob24x24.gif"));
      this.childJobDisableIcon = new ImageIcon(getClass().getResource("/images/ChildJobDisable24x24.gif"));
      this.relJobIcon = new ImageIcon(getClass().getResource("/images/RelJob24x24.gif"));
      this.mailIcon = new ImageIcon(getClass().getResource("/images/Mail24x24.gif"));
      this.systemInfoIcon = new ImageIcon(getClass().getResource("/images/SystemInfo24x24.gif"));
      this.serverIcon = new ImageIcon(getClass().getResource("/images/Server24x24.gif"));
      this.notificationIcon = new ImageIcon(getClass().getResource("/images/Notification24x24.gif"));
      this.jobGroupFolderIcon = new ImageIcon(getClass().getResource("/images/JobGroupFolder24x24.gif"));
      this.jobGroupIcon = new ImageIcon(getClass().getResource("/images/JobGroup24x24.gif"));
      this.folderIcon = new ImageIcon(getClass().getResource("/images/Folder24x24.gif"));
      this.redfolderIcon = new ImageIcon(getClass().getResource("/images/FolderRed24x24.gif"));
      this.peopleIcon = new ImageIcon(getClass().getResource("/images/People24x24.gif"));
      this.doneJobIcon = new ImageIcon(getClass().getResource("/images/DoneJob24x24.gif"));
      this.failedJobIcon = new ImageIcon(getClass().getResource("/images/FailedJob24x24.gif"));
    }

    public Component getTreeCellRendererComponent(, Object paramObject, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt, boolean paramBoolean4)
    {
      super.getTreeCellRendererComponent(paramJTree, paramObject, paramBoolean1, paramBoolean2, paramBoolean3, paramInt, paramBoolean4);

      int i = getTreeItemType(paramObject);

      if (i == ETLTreeView.ItemETLSystem) {
        setIcon(this.serverIcon);
      }
      else if (i == ETLTreeView.ItemSubSystem)
        setIcon(this.systemIcon);
      else if (i == ETLTreeView.ItemETLJob)
        setIcon(this.jobIcon);
      else if (i == ETLTreeView.ItemJobSource)
        setIcon(this.sourceIcon);
      else if (i == ETLTreeView.ItemCalendarYear)
        setIcon(this.calendarIcon);
      else if (i == ETLTreeView.ItemDependentJob)
        if (getTreeItemJobStatus(paramObject) == 1)
          setIcon(this.depJobIcon);
        else
          setIcon(this.depJobDisableIcon);
      else if (i == ETLTreeView.ItemDownStreamJob)
        if (getTreeItemJobStatus(paramObject) == 1)
          setIcon(this.streamJobIcon);
        else
          setIcon(this.streamJobDisableIcon);
      else if (i == ETLTreeView.ItemJobGroupChild)
        if (getTreeItemJobStatus(paramObject) == 1)
          setIcon(this.childJobIcon);
        else
          setIcon(this.childJobDisableIcon);
      else if (i == ETLTreeView.ItemRelatedJob)
        setIcon(this.relJobIcon);
      else if (i == ETLTreeView.ItemNotificationFolder)
        setIcon(this.mailIcon);
      else if (i == ETLTreeView.ItemSystemInfoFolder)
        setIcon(this.systemInfoIcon);
      else if (i == ETLTreeView.ItemNotifyDest)
        setIcon(this.notificationIcon);
      else if (i == ETLTreeView.ItemSystemServerInfo)
        setIcon(this.serverIcon);
      else if (i == ETLTreeView.ItemJobGroupFolder)
        setIcon(this.jobGroupFolderIcon);
      else if ((i == ETLTreeView.ItemJobGroup) || (i == ETLTreeView.ItemJobGroup1))
      {
        setIcon(this.jobGroupIcon);
      } else if (i == ETLTreeView.ItemNotifyUserFolder)
        setIcon(this.folderIcon);
      else if (i == ETLTreeView.ItemNotifyGroupFolder)
        setIcon(this.folderIcon);
      else if ((i == ETLTreeView.ItemNotifyUser) || (i == ETLTreeView.ItemNotifyGroup) || (i == ETLTreeView.ItemNotifyGroupMember))
      {
        setIcon(this.peopleIcon);
      } else if ((i == ETLTreeView.ItemTriggerFolder) || (i == ETLTreeView.ItemDependOnFolder))
      {
        setIcon(this.redfolderIcon);
      } else if ((i == ETLTreeView.ItemNextLevelDependentJob) || (i == ETLTreeView.ItemNextLevelDownStreamJob) || (i == ETLTreeView.ItemJobGroupChild1) || (i == ETLTreeView.ItemTriggerJob) || (i == ETLTreeView.ItemDependOnJob))
      {
        if (getTreeItemJobStatus(paramObject) == 1)
          setIcon(this.doneJobIcon);
        else {
          setIcon(this.failedJobIcon);
        }

      }
      else if (!(paramBoolean3)) {
        setIcon(this.folderIcon);
      }

      return this;
    }

    protected int getTreeItemType() {
      DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode)paramObject;
      ETLTreeView.treeItem localtreeItem = (ETLTreeView.treeItem)localDefaultMutableTreeNode.getUserObject();
      return localtreeItem.itemType;
    }

    protected int getTreeItemJobStatus() {
      DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode)paramObject;
      ETLTreeView.treeItem localtreeItem = (ETLTreeView.treeItem)localDefaultMutableTreeNode.getUserObject();
      return localtreeItem.jobStatus;
    }
  }

  private static class treeItem
  {
    String itemName;
    String etlsys;
    String etljob;
    int itemType;
    int jobStatus;
    int seq;

    public treeItem(String paramString)
    {
      this.itemName = paramString;
      this.itemType = 0;
    }

    public treeItem(String paramString, int paramInt)
    {
      this.itemName = paramString;
      this.itemType = paramInt;
    }

    public void setItemName(String paramString)
    {
      this.itemName = paramString; }

    public void setSys(String paramString) {
      this.etlsys = paramString; } 
    public void setJob(String paramString) { this.etljob = paramString; } 
    public void setJobStatus(int paramInt) { this.jobStatus = paramInt; } 
    public void setSeq(int paramInt) { this.seq = paramInt;
    }

    public void setSysJob(String paramString1, String paramString2) {
      this.etlsys = paramString1;
      this.etljob = paramString2; }

    public String toString() {
      return this.itemName; } 
    public String getSysName() { return this.etlsys; } 
    public String getJobName() { return this.etljob; } 
    public int getJobStatus() { return this.jobStatus; } 
    public int getSeq() { return this.seq;
    }
  }
}