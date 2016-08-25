import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.im.InputContext;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class ETLMainFrame extends JFrame
{
  ETLMainFrameMenu mainMenu;
  JSplitPane splitPaneV;
  JSplitPane splitPaneH;
  ETLTreeView treeView;
  ETLInfo infoPane;
  ETLFileLog fileLog;
  JToolBar toolBar;
  ETLJobWatchDog jobWatchDogDlg;
  ETLJobErrorDetection jobErrorDetectionDlg;
  ETLJobTracer jobTracerDlg;
  boolean bRefreshWatchDog = true;
  boolean bRefreshErrorDetection = true;
  boolean bRefreshJobTracer = true;
  String databaseName = "";
  String BUName = "";
  String JobName = "";
  boolean bConnected = false;
  Connection conObj;

  public ETLMainFrame()
  {
    setTitle("ETL Automation Administration, NCR");
  }

  protected void frameInit()
  {
    super.frameInit();

    Container localContainer = super.getContentPane();

    InputContext localInputContext = getInputContext();
    localInputContext.selectInputMethod(new Locale("en", "US"));

    ETLMainFrame.1 local1 = new WindowAdapter(this) { private final ETLMainFrame this$0;

      public void windowClosing() { this.this$0.disconnectDB();
        System.exit(0);
      }

    };
    addWindowListener(local1);

    this.toolBar = new JToolBar();
    this.toolBar.setFloatable(false);

    this.mainMenu = new ETLMainFrameMenu(this, this.toolBar);
    this.mainMenu.setDisconnect();

    this.treeView = new ETLTreeView(this);
    this.infoPane = new ETLInfo(this);
    this.fileLog = new ETLFileLog(this);

    this.splitPaneH = new JSplitPane(1);
    this.splitPaneV = new JSplitPane(0);

    this.splitPaneH.setDividerSize(5);
    this.splitPaneV.setDividerSize(5);

    this.splitPaneV.setTopComponent(this.infoPane);
    this.splitPaneV.setBottomComponent(this.fileLog);

    this.splitPaneH.setLeftComponent(this.treeView);
    this.splitPaneH.setRightComponent(this.splitPaneV);

    Dimension localDimension1 = new Dimension(200, 50);
    this.treeView.setMinimumSize(localDimension1);

    this.splitPaneH.setDividerLocation(200);
    this.splitPaneV.setDividerLocation(200);

    localContainer.add(this.toolBar, "North");
    localContainer.add(this.splitPaneH, "Center");

    setSize(720, 550);

    Dimension localDimension2 = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension localDimension3 = getSize();

    if (localDimension3.height > localDimension2.height)
      localDimension3.height = localDimension2.height;

    if (localDimension3.width > localDimension2.width)
      localDimension3.width = localDimension2.width;

    setLocation((localDimension2.width - localDimension3.width) / 2, (localDimension2.height - localDimension3.height) / 2);

    this.infoPane.showDetailOfRoot();

    setVisible(true);
  }

  public Connection getConnection()
  {
    return this.conObj;
  }

  public void setConnection(Connection paramConnection) {
    this.conObj = paramConnection;
  }

  public ETLTreeView getTreeView()
  {
    return this.treeView;
  }

  public void setDBName(String paramString)
  {
    this.databaseName = paramString;
  }

  public String getDBName()
  {
    String str;
    if (this.databaseName.equals(""))
      str = "";
    else
      str = this.databaseName + ".";

    return str;
  }

  public void setBUName(String paramString)
  {
    this.BUName = paramString;
  }

  public String getBUName()
  {
    String str = this.BUName;

    return str;
  }

  public void setJobName(String paramString)
  {
    this.JobName = paramString;
  }

  public String getJobName()
  {
    String str = this.JobName;

    return str;
  }

  public int connectToDB()
  {
    if (this.bConnected)
      return 0;

    ETLConnectionDlg localETLConnectionDlg = new ETLConnectionDlg(this);

    localETLConnectionDlg.setDBName(this.databaseName);
    localETLConnectionDlg.show();

    if (this.conObj == null)
      return -1;

    Cursor localCursor = getCursor();
    setCursor(new Cursor(3));

    this.bConnected = true;
    this.bRefreshWatchDog = true;
    this.bRefreshErrorDetection = true;
    this.bRefreshJobTracer = true;

    this.mainMenu.setConnect();

    this.treeView.readETLSystem(this.conObj);

    setCursor(localCursor);

    return 0;
  }

  public void disconnectDB()
  {
    if (this.bConnected) {
      try {
        this.conObj.close();
      }
      catch (SQLException localSQLException)
      {
      }

      this.bConnected = false;
      this.conObj = null;

      this.treeView.removeAllParent();

      this.fileLog.deleteFileLog();
      this.infoPane.showDetailOfNone();

      this.mainMenu.setDisconnect();

      this.BUName = "";
      this.JobName = "";
      setTitle("ETL Automation Administration, NCR");
    }
  }

  public void delete()
  {
    if (!(this.bConnected))
      return;

    DefaultTreeModel localDefaultTreeModel = this.treeView.getTreeModel();
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = (DefaultMutableTreeNode)this.treeView.getTree().getLastSelectedPathComponent();

    if ((localDefaultMutableTreeNode1 == null) || (localDefaultMutableTreeNode1 == (DefaultMutableTreeNode)localDefaultTreeModel.getRoot()))
      return;

    int i = this.treeView.getItemType(localDefaultMutableTreeNode1);

    if ((i == ETLTreeView.ItemETLSystem) || (i == ETLTreeView.ItemJobGroupFolder) || (i == ETLTreeView.ItemDependencyFolder) || (i == ETLTreeView.ItemJobStreamFolder) || (i == ETLTreeView.ItemRelatedJobFolder))
    {
      return;
    }
    if ((i == ETLTreeView.ItemSubSystem) && 
      (!(localDefaultMutableTreeNode1.isLeaf()))) {
      JOptionPane.showMessageDialog(this, "You can not delete an ETL system with job associated!", "Error", 0);

      return;
    }

    int j = JOptionPane.showConfirmDialog(this, "Do you want to delete this item?", "Delete", 0, 3);

    if (j != 0)
      return;

    String str1 = this.treeView.getItemName(localDefaultMutableTreeNode1);

    if (i == ETLTreeView.ItemSubSystem) {
      if (deleteSystem(str1) != 0)
        return;

      localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode1);
    } else {
      DefaultMutableTreeNode localDefaultMutableTreeNode2;
      Object localObject1;
      if (i == ETLTreeView.ItemETLJob) {
        localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
        localObject1 = localDefaultMutableTreeNode2.getUserObject().toString();

        if (deleteJob((String)localObject1, str1) != 0)
          return;

        localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode1);
      }
      else if (i == ETLTreeView.ItemJobSource) {
        if (deleteJobSource(str1) != 0)
          return;
        localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode1);
      } else {
        Object localObject2;
        String str2;
        String str3;
        String str4;
        String str5;
        if (i == ETLTreeView.ItemDependentJob) {
          localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
          localObject1 = (DefaultMutableTreeNode)localDefaultMutableTreeNode2.getParent();
          localObject2 = (DefaultMutableTreeNode)((DefaultMutableTreeNode)localObject1).getParent();

          str2 = this.treeView.getSysName(localDefaultMutableTreeNode1);
          str3 = this.treeView.getJobName(localDefaultMutableTreeNode1);

          str4 = ((DefaultMutableTreeNode)localObject1).getUserObject().toString();
          str5 = ((DefaultMutableTreeNode)localObject2).getUserObject().toString();

          deleteJobDependcy(str5, str4, str2, str3);

          localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode1);
          if (localDefaultMutableTreeNode2.isLeaf())
            localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode2);

          return;
        }
        if (i == ETLTreeView.ItemDownStreamJob) {
          localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
          localObject1 = (DefaultMutableTreeNode)localDefaultMutableTreeNode2.getParent();
          localObject2 = (DefaultMutableTreeNode)((DefaultMutableTreeNode)localObject1).getParent();

          str2 = this.treeView.getSysName(localDefaultMutableTreeNode1);
          str3 = this.treeView.getJobName(localDefaultMutableTreeNode1);

          str4 = ((DefaultMutableTreeNode)localObject1).getUserObject().toString();
          str5 = ((DefaultMutableTreeNode)localObject2).getUserObject().toString();

          deleteJobStream(str5, str4, str2, str3);

          localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode1);
          if (localDefaultMutableTreeNode2.isLeaf())
            localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode2);

          return;
        }
        if (i == ETLTreeView.ItemRelatedJob) {
          localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
          localObject1 = (DefaultMutableTreeNode)localDefaultMutableTreeNode2.getParent();
          localObject2 = (DefaultMutableTreeNode)((DefaultMutableTreeNode)localObject1).getParent();

          str2 = ((DefaultMutableTreeNode)localObject1).getUserObject().toString();
          str3 = ((DefaultMutableTreeNode)localObject2).getUserObject().toString();

          deleteRelatedJob(str3, str2, str1);

          localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode1);
          if (localDefaultMutableTreeNode2.isLeaf())
            localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode2);

          return;
        }
        if (i == ETLTreeView.ItemJobGroup) {
          if (deleteJobGroup(str1) != 0)
            return;

          localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode1);
        }
        else if (i == ETLTreeView.ItemJobGroupChild) {
          localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
          localObject1 = localDefaultMutableTreeNode2.getUserObject().toString();

          localObject2 = this.treeView.getSysName(localDefaultMutableTreeNode1);
          str2 = this.treeView.getJobName(localDefaultMutableTreeNode1);

          if (deleteJobGroupChild((String)localObject1, (String)localObject2, str2) != 0)
            return;

          localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode1);
        }
        else if (i == ETLTreeView.ItemNotifyUser) {
          if (deleteETLUser(str1) != 0)
            return;

          localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode1);
        }
        else if (i == ETLTreeView.ItemJobNotifyFolder) {
          localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
          localObject1 = (DefaultMutableTreeNode)localDefaultMutableTreeNode2.getParent();

          localObject2 = localDefaultMutableTreeNode2.getUserObject().toString();
          str2 = ((DefaultMutableTreeNode)localObject1).getUserObject().toString();

          if (deleteNotifyJob(str2, (String)localObject2) != 0)
            return;
          localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode1);
        }
        else if (i == ETLTreeView.ItemNotifyDest) {
          localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
          localObject1 = this.treeView.getSysName(localDefaultMutableTreeNode1);
          localObject2 = this.treeView.getJobName(localDefaultMutableTreeNode1);

          int k = this.treeView.getSeq(localDefaultMutableTreeNode1);

          if (deleteNotifyDest((String)localObject1, (String)localObject2, k) != 0)
            return;
          localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode1);

          if (localDefaultMutableTreeNode2.isLeaf())
            localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode2);
        }
        else if (i == ETLTreeView.ItemNotifyGroup) {
          if (deleteUserGroup(str1) != 0)
            return;
          localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode1);
        }
        else if (i == ETLTreeView.ItemNotifyGroupMember) {
          localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();

          localObject1 = localDefaultMutableTreeNode2.getUserObject().toString();

          if (deleteGroupMember((String)localObject1, str1) != 0)
            return;
          localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode1);
        }
        else if (i == ETLTreeView.ItemNotifyUser) {
          if (deleteUser(str1) != 0)
            return;
          localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode1);
        }
        else if (i == ETLTreeView.ItemSystemServerInfo) {
          if (deleteETLServer(str1) != 0)
            return;

          localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode1);
        }
      }
    }
  }

  private int deleteETLServer(String paramString)
  {
    Statement localStatement;
    String str1 = getDBName();
    try
    {
      localStatement = this.conObj.createStatement();
    }
    catch (SQLException localSQLException1) {
      JOptionPane.showMessageDialog(this, "Unable to create statement object", "Error", 0);

      return -1;
    }
    try
    {
      String str2 = "DELETE FROM " + str1 + "ETL_Server" + "   WHERE ETL_Server = '" + paramString + "'";

      int i = localStatement.executeUpdate(str2);

      localStatement.close();
    }
    catch (SQLException localSQLException2) {
      String str3 = "Some error occured while deleting ETL Server\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);
    }

    return 0;
  }

  private int deleteSystem(String paramString)
  {
    Statement localStatement;
    String str1 = getDBName();
    try
    {
      localStatement = this.conObj.createStatement();
    }
    catch (SQLException localSQLException1) {
      JOptionPane.showMessageDialog(this, "Unable to create statement object", "Error", 0);

      return -1;
    }
    try
    {
      String str2 = "DELETE FROM " + str1 + "ETL_Job_Source" + "   WHERE ETL_System = '" + paramString + "'";

      int i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Job_Dependency" + "   WHERE (ETL_System = '" + paramString + "')" + "      OR (dependency_system = '" + paramString + "')";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Job_Stream" + "   WHERE (ETL_System = '" + paramString + "')" + "      OR (stream_system = '" + paramString + "')";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_RelatedJob" + "   WHERE (ETL_System = '" + paramString + "')" + "      OR (RelatedSystem = '" + paramString + "')";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Job_Status" + "   WHERE ETL_System = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Job_Log" + "   WHERE ETL_System = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Job_Timewindow" + "   WHERE ETL_System = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Job_Groupchild" + "   WHERE ETL_System = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "UPDATE " + str1 + "ETL_Job_Group" + "   SET ETL_System = NULL, ETL_Job = NULL" + "   WHERE ETL_System = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Notification" + "   WHERE ETL_System = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Record_Log" + "   WHERE ETL_System = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Received_File" + "   WHERE ETL_System = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "DataCalendar" + "   WHERE ETL_System = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "DataCalendarYear" + "   WHERE ETL_System = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_TimeTrigger_Calendar" + "   WHERE ETL_System = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_TimeTrigger_Monthly" + "   WHERE ETL_System = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_TimeTrigger_Weekly" + "   WHERE ETL_System = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_TimeTrigger" + "   WHERE ETL_System = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Job" + "   WHERE ETL_System = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Sys" + "   WHERE ETL_System = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      localStatement.close();
    }
    catch (SQLException localSQLException2) {
      String str3 = "Some error occured while deleting ETL System\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);

      return -2;
    }

    return 0;
  }

  private int deleteJob(String paramString1, String paramString2)
  {
    Statement localStatement;
    String str1 = getDBName();
    try
    {
      localStatement = this.conObj.createStatement();
    }
    catch (SQLException localSQLException1) {
      JOptionPane.showMessageDialog(this, "Unable to create statement object", "Error", 0);

      return -1;
    }
    try
    {
      String str2 = "DELETE FROM " + str1 + "ETL_Job_Source" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      int i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Job_Dependency" + "   WHERE (ETL_System = '" + paramString1 + "'" + "          AND ETL_Job = '" + paramString2 + "')" + "      OR (Dependency_System = '" + paramString1 + "'" + "          AND Dependency_Job = '" + paramString2 + "')";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Job_Stream" + "   WHERE (ETL_System = '" + paramString1 + "'" + "          AND ETL_Job = '" + paramString2 + "')" + "      OR (Stream_System = '" + paramString1 + "'" + "          AND Stream_Job = '" + paramString2 + "')";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_RelatedJob" + "   WHERE (ETL_System = '" + paramString1 + "'" + "          AND ETL_Job = '" + paramString2 + "')" + "      OR (RelatedSystem = '" + paramString1 + "'" + "          AND RelatedJob = '" + paramString2 + "')";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Job_Status" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Job_Log" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Job_Timewindow" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Job_GroupChild" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "UPDATE " + str1 + "ETL_Job_Group" + "   SET ETL_System = NULL, ETL_Job = NULL" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Notification" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job= '" + paramString2 + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Received_File" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Record_Log" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "DataCalendar" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "DataCalendarYear" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_TimeTrigger_Calendar" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_TimeTrigger_Monthly" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_TimeTrigger_Weekly" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_TimeTrigger" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_Job" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      i = localStatement.executeUpdate(str2);

      localStatement.close();
    }
    catch (SQLException localSQLException2) {
      String str3 = "Some error occured while deleting ETL Job\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);

      return -2;
    }

    return 0;
  }

  private int deleteJobSource(String paramString)
  {
    Statement localStatement;
    String str1 = getDBName();
    try
    {
      localStatement = this.conObj.createStatement();
    }
    catch (SQLException localSQLException1) {
      JOptionPane.showMessageDialog(this, "Unable to create statement object", "Error", 0);

      return -1;
    }
    try
    {
      String str2 = "DELETE FROM " + str1 + "etl_job_source" + "   WHERE source = '" + paramString + "'";

      int i = localStatement.executeUpdate(str2);

      localStatement.close();
    }
    catch (SQLException localSQLException2) {
      String str3 = "Some error occured while deleting ETL Job's source\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);
    }

    return 0;
  }

  private int deleteJobDependcy(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Statement localStatement;
    String str1 = getDBName();
    try
    {
      localStatement = this.conObj.createStatement();
    }
    catch (SQLException localSQLException1) {
      JOptionPane.showMessageDialog(this, "Unable to create statement object", "Error", 0);

      return -1;
    }
    try
    {
      String str2 = "DELETE FROM " + str1 + "etl_job_dependency" + "   WHERE etl_system = '" + paramString1 + "'" + "     AND etl_job = '" + paramString2 + "'" + "     AND dependency_system = '" + paramString3 + "'" + "     AND dependency_job = '" + paramString4 + "'";

      int i = localStatement.executeUpdate(str2);

      localStatement.close();
    }
    catch (SQLException localSQLException2) {
      String str3 = "Some error occured while deleting ETL Job's dependency\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);
    }

    return 0;
  }

  private int deleteJobStream(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Statement localStatement;
    String str1 = getDBName();
    try
    {
      localStatement = this.conObj.createStatement();
    }
    catch (SQLException localSQLException1) {
      JOptionPane.showMessageDialog(this, "Unable to create statement object", "Error", 0);

      return -1;
    }
    try
    {
      String str2 = "DELETE FROM " + str1 + "etl_job_stream" + "   WHERE etl_system = '" + paramString1 + "'" + "     AND etl_job = '" + paramString2 + "'" + "     AND stream_system = '" + paramString3 + "'" + "     AND stream_job = '" + paramString4 + "'";

      int i = localStatement.executeUpdate(str2);

      localStatement.close();
    }
    catch (SQLException localSQLException2) {
      String str3 = "Some error occured while deleting ETL Job's job stream\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);
    }

    return 0;
  }

  private int deleteRelatedJob(String paramString1, String paramString2, String paramString3)
  {
    Statement localStatement;
    String str3;
    String str1 = getDBName();
    try
    {
      localStatement = this.conObj.createStatement();
    }
    catch (SQLException localSQLException1) {
      JOptionPane.showMessageDialog(this, "Unable to create statement object", "Error", 0);

      return -1;
    }
    try
    {
      String str2 = paramString3.substring(1, 4);
      str3 = paramString3.substring(6, paramString3.length());

      String str4 = "DELETE FROM " + str1 + "ETL_RelatedJob" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'" + "     AND RelatedSystem = '" + str2 + "'" + "     AND RelatedJob = '" + str3 + "'";

      int i = localStatement.executeUpdate(str4);

      localStatement.close();
    }
    catch (SQLException localSQLException2) {
      str3 = "Some error occured while deleting ETL Job's related cube job\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);
    }

    return 0;
  }

  private int deleteJobGroup(String paramString)
  {
    Statement localStatement;
    String str1 = getDBName();
    try
    {
      localStatement = this.conObj.createStatement();
    }
    catch (SQLException localSQLException1) {
      JOptionPane.showMessageDialog(this, "Unable to create statement object", "Error", 0);

      return -1;
    }
    try
    {
      String str2 = "DELETE FROM " + str1 + "etl_job_groupchild" + "   WHERE groupname = '" + paramString + "'";

      int i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "etl_job_group" + "   WHERE groupname = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      localStatement.close();
    }
    catch (SQLException localSQLException2) {
      String str3 = "Some error occured while deleting job group\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);
    }

    return 0;
  }

  private int deleteJobGroupChild(String paramString1, String paramString2, String paramString3)
  {
    Statement localStatement;
    String str1 = getDBName();
    try
    {
      localStatement = this.conObj.createStatement();
    }
    catch (SQLException localSQLException1) {
      JOptionPane.showMessageDialog(this, "Unable to create statement object", "Error", 0);

      return -1;
    }
    try
    {
      String str2 = "DELETE FROM " + str1 + "etl_job_groupchild" + "   WHERE groupname = '" + paramString1 + "'" + "     AND etl_system = '" + paramString2 + "'" + "     AND etl_job = '" + paramString3 + "'";

      int i = localStatement.executeUpdate(str2);

      localStatement.close();
    }
    catch (SQLException localSQLException2) {
      String str3 = "Some error occured while deleting job group child\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);
    }

    return 0;
  }

  public int deleteETLUser(String paramString)
  {
    Statement localStatement;
    String str1 = getDBName();
    try
    {
      localStatement = this.conObj.createStatement();
    }
    catch (SQLException localSQLException1) {
      JOptionPane.showMessageDialog(this, "Unable to create statement object", "Error", 0);

      return -1;
    }
    try
    {
      String str2 = "DELETE FROM " + str1 + "etl_user" + "   WHERE username = '" + paramString + "'";

      int i = localStatement.executeUpdate(str2);

      localStatement.close();
    }
    catch (SQLException localSQLException2) {
      String str3 = "Some error occured while deleting ETL user\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);
    }

    return 0;
  }

  public int deleteNotifyJob(String paramString1, String paramString2)
  {
    Statement localStatement;
    String str1 = getDBName();
    try
    {
      localStatement = this.conObj.createStatement();
    }
    catch (SQLException localSQLException1) {
      JOptionPane.showMessageDialog(this, "Unable to create statement object", "Error", 0);

      return -1;
    }
    try
    {
      String str2 = "DELETE FROM " + str1 + "ETL_Notification" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'";

      int i = localStatement.executeUpdate(str2);

      localStatement.close();
    }
    catch (SQLException localSQLException2) {
      String str3 = "Some error occured while deleting ETL user\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);
    }

    return 0;
  }

  public int deleteNotifyDest(String paramString1, String paramString2, int paramInt)
  {
    Statement localStatement;
    String str1 = getDBName();
    try
    {
      localStatement = this.conObj.createStatement();
    }
    catch (SQLException localSQLException1) {
      JOptionPane.showMessageDialog(this, "Unable to create statement object", "Error", 0);

      return -1;
    }
    try
    {
      String str2 = "DELETE FROM " + str1 + "ETL_Notification" + "   WHERE ETL_System = '" + paramString1 + "'" + "     AND ETL_Job = '" + paramString2 + "'" + "     AND SeqID = " + paramInt;

      int i = localStatement.executeUpdate(str2);

      localStatement.close();
    }
    catch (SQLException localSQLException2) {
      String str3 = "Some error occured while deleting ETL user\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);
    }

    return 0;
  }

  private int deleteUserGroup(String paramString)
  {
    Statement localStatement;
    String str1 = getDBName();
    try
    {
      localStatement = this.conObj.createStatement();
    }
    catch (SQLException localSQLException1) {
      JOptionPane.showMessageDialog(this, "Unable to create statement object", "Error", 0);

      return -1;
    }
    try
    {
      String str2 = "DELETE FROM " + str1 + "ETL_Notification" + "   WHERE DestType = 'G' AND GroupName = '" + paramString + "'";

      int i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_GroupMember" + "   WHERE GroupName = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_UserGroup" + "   WHERE GroupName = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      localStatement.close();
    }
    catch (SQLException localSQLException2) {
      String str3 = "Some error occured while deleting ETL Server\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);
    }

    return 0;
  }

  private int deleteUser(String paramString)
  {
    Statement localStatement;
    String str1 = getDBName();
    try
    {
      localStatement = this.conObj.createStatement();
    }
    catch (SQLException localSQLException1) {
      JOptionPane.showMessageDialog(this, "Unable to create statement object", "Error", 0);

      return -1;
    }
    try
    {
      String str2 = "DELETE FROM " + str1 + "ETL_Notification" + "   WHERE DestType = 'U' AND UserName = '" + paramString + "'";

      int i = localStatement.executeUpdate(str2);

      str2 = "DELETE FROM " + str1 + "ETL_User" + "   WHERE UserName = '" + paramString + "'";

      i = localStatement.executeUpdate(str2);

      localStatement.close();
    }
    catch (SQLException localSQLException2) {
      String str3 = "Some error occured while deleting ETL Server\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);
    }

    return 0;
  }

  private int deleteGroupMember(String paramString1, String paramString2)
  {
    Statement localStatement;
    String str1 = getDBName();
    try
    {
      localStatement = this.conObj.createStatement();
    }
    catch (SQLException localSQLException1) {
      JOptionPane.showMessageDialog(this, "Unable to create statement object", "Error", 0);

      return -1;
    }
    try
    {
      String str2 = "DELETE FROM " + str1 + "ETL_GroupMember" + "   WHERE GroupName = '" + paramString1 + "' AND UserName = '" + paramString2 + "'";

      int i = localStatement.executeUpdate(str2);

      localStatement.close();
    }
    catch (SQLException localSQLException2) {
      String str3 = "Some error occured while deleting ETL Server\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this, str3, "Error", 0);
    }

    return 0;
  }

  public void viewFileLog()
  {
    if (!(this.bConnected))
      return;

    DefaultMutableTreeNode localDefaultMutableTreeNode1 = (DefaultMutableTreeNode)this.treeView.getTree().getLastSelectedPathComponent();

    int i = this.treeView.getItemType(localDefaultMutableTreeNode1);
    String str1 = this.treeView.getItemName(localDefaultMutableTreeNode1);

    if (i == ETLTreeView.ItemETLJob) {
      DefaultMutableTreeNode localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
      String str2 = localDefaultMutableTreeNode2.getUserObject().toString();

      this.fileLog.readFileLog(this.conObj, str2, str1);
    }
  }

  public void setTreeItemChange(DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    if (!(this.bConnected))
      return;

    String str1 = this.treeView.getItemName(paramDefaultMutableTreeNode);
    int i = this.treeView.getItemType(paramDefaultMutableTreeNode);

    DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode)paramDefaultMutableTreeNode.getParent();

    if (!(this.bConnected))
      return;

    Cursor localCursor = getCursor();
    setCursor(new Cursor(3));

    if (i == ETLTreeView.ItemETLSystem) {
      this.mainMenu.setETLSystem();
      this.infoPane.showDetailOfRoot();
    }
    else if (i == ETLTreeView.ItemSubSystem) {
      this.mainMenu.setSubsystem();
      if (paramDefaultMutableTreeNode.isLeaf()) {
        this.treeView.readETLJob(this.conObj, str1, paramDefaultMutableTreeNode);
        this.treeView.expand();
      }
      this.infoPane.showDetailOfSystem(str1);
    } else {
      String str2;
      String str3;
      if (i == ETLTreeView.ItemETLJob) {
        this.mainMenu.setETLJob();
        str2 = this.treeView.getSysName(paramDefaultMutableTreeNode);
        str3 = this.treeView.getJobName(paramDefaultMutableTreeNode);

        if (paramDefaultMutableTreeNode.isLeaf()) {
          this.treeView.readDependency(this.conObj, str2, str1, paramDefaultMutableTreeNode);
          this.treeView.readJobStream(this.conObj, str2, str1, paramDefaultMutableTreeNode);
          this.treeView.readJobRelatedJob(this.conObj, str2, str1, paramDefaultMutableTreeNode);
          this.treeView.readJobNotification(this.conObj, str2, str1, paramDefaultMutableTreeNode);
          this.treeView.readSource(this.conObj, str2, str1, paramDefaultMutableTreeNode);
          this.treeView.readCalendarYear(this.conObj, str2, str1, paramDefaultMutableTreeNode);
          this.treeView.readTriggerJob(this.conObj, str2, str1, paramDefaultMutableTreeNode);
          this.treeView.readJobGroupByHeadJob(this.conObj, str2, str1, paramDefaultMutableTreeNode);

          this.treeView.expand();
        }
        this.infoPane.showDetailOfJob(str2, str3);
      }
      else if (i == ETLTreeView.ItemDependencyFolder) {
        this.mainMenu.setJobDependencyFolder();
        this.infoPane.showDetailOfNone();
      }
      else if (i == ETLTreeView.ItemJobStreamFolder) {
        this.mainMenu.setJobStreamFolder();
        this.infoPane.showDetailOfNone();
      }
      else if (i == ETLTreeView.ItemRelatedJobFolder) {
        this.mainMenu.setJobRelatedJobFolder();
        this.infoPane.showDetailOfNone();
      }
      else if (i == ETLTreeView.ItemJobSource) {
        this.mainMenu.setJobSource();
        this.infoPane.showDetailOfSource(str1);
      }
      else if (i == ETLTreeView.ItemDependentJob) {
        this.mainMenu.setJobDependency();

        str2 = this.treeView.getSysName(paramDefaultMutableTreeNode);
        str3 = this.treeView.getJobName(paramDefaultMutableTreeNode);

        this.infoPane.showDetailOfJob(str2, str3);
        if (paramDefaultMutableTreeNode.isLeaf()) {
          this.treeView.readNextLevelDependency(this.conObj, str2, str3, paramDefaultMutableTreeNode);
          this.treeView.expand();
        }
      }
      else if (i == ETLTreeView.ItemNextLevelDependentJob) {
        this.mainMenu.setNextLevelJobDependency();

        str2 = this.treeView.getSysName(paramDefaultMutableTreeNode);
        str3 = this.treeView.getJobName(paramDefaultMutableTreeNode);

        this.infoPane.showDetailOfJob(str2, str3);
        if (paramDefaultMutableTreeNode.isLeaf()) {
          this.treeView.readNextLevelDependency(this.conObj, str2, str3, paramDefaultMutableTreeNode);
          this.treeView.readDependentJob(this.conObj, str2, str3, paramDefaultMutableTreeNode);
          this.treeView.readTriggerJob(this.conObj, str2, str3, paramDefaultMutableTreeNode);
          this.treeView.expand();
        }
      }
      else if (i == ETLTreeView.ItemDownStreamJob) {
        this.mainMenu.setJobStream();

        str2 = this.treeView.getSysName(paramDefaultMutableTreeNode);
        str3 = this.treeView.getJobName(paramDefaultMutableTreeNode);

        this.infoPane.showDetailOfJob(str2, str3);
        if (paramDefaultMutableTreeNode.isLeaf()) {
          this.treeView.readNextLevelJobStream(this.conObj, str2, str3, paramDefaultMutableTreeNode);
          this.treeView.readDependentJob(this.conObj, str2, str3, paramDefaultMutableTreeNode);
          this.treeView.expand();
        }
      }
      else if (i == ETLTreeView.ItemNextLevelDownStreamJob) {
        this.mainMenu.setNextLevelJobStream();

        str2 = this.treeView.getSysName(paramDefaultMutableTreeNode);
        str3 = this.treeView.getJobName(paramDefaultMutableTreeNode);

        this.infoPane.showDetailOfJob(str2, str3);
        if (paramDefaultMutableTreeNode.isLeaf()) {
          this.treeView.readNextLevelJobStream(this.conObj, str2, str3, paramDefaultMutableTreeNode);
          this.treeView.readDependentJob(this.conObj, str2, str3, paramDefaultMutableTreeNode);
          this.treeView.expand();
        }
      }
      else if (i == ETLTreeView.ItemRelatedJob) {
        this.mainMenu.setRelatedJob();

        str2 = str1.substring(1, 4);
        str3 = str1.substring(6, str1.length());

        this.infoPane.showDetailOfJob(str2, str3);
      }
      else if (i == ETLTreeView.ItemJobGroup) {
        this.mainMenu.setJobGroup();

        if (paramDefaultMutableTreeNode.isLeaf()) {
          this.treeView.readJobGroupChild(this.conObj, str1, paramDefaultMutableTreeNode);
          this.treeView.expand();
        }

        this.infoPane.showDetailOfJobGroup(str1);
      }
      else if (i == ETLTreeView.ItemJobGroup1) {
        this.mainMenu.setJobGroup1();

        if (paramDefaultMutableTreeNode.isLeaf()) {
          this.treeView.readJobGroupChild1(this.conObj, str1, paramDefaultMutableTreeNode);
          this.treeView.expand();
        }

        this.infoPane.showDetailOfJobGroup(str1);
      } else {
        String str4;
        if (i == ETLTreeView.ItemJobGroupChild) {
          this.mainMenu.setJobGroupChild();

          str2 = localDefaultMutableTreeNode.getUserObject().toString();

          str3 = this.treeView.getSysName(paramDefaultMutableTreeNode);
          str4 = this.treeView.getJobName(paramDefaultMutableTreeNode);

          this.infoPane.showDetailOfJobGroupChild(str2, str3, str4);
        }
        else if (i == ETLTreeView.ItemJobGroupChild1) {
          this.mainMenu.setJobGroupChild1();

          str2 = localDefaultMutableTreeNode.getUserObject().toString();

          str3 = this.treeView.getSysName(paramDefaultMutableTreeNode);
          str4 = this.treeView.getJobName(paramDefaultMutableTreeNode);

          this.infoPane.showDetailOfJob(str3, str4);

          if (paramDefaultMutableTreeNode.isLeaf()) {
            this.treeView.readTriggerJob(this.conObj, str3, str4, paramDefaultMutableTreeNode);
            this.treeView.readDependentJob(this.conObj, str3, str4, paramDefaultMutableTreeNode);
            this.treeView.expand();
          }
        }
        else if (i == ETLTreeView.ItemJobGroupFolder) {
          this.mainMenu.setJobGroupFolder();
          if (paramDefaultMutableTreeNode.isLeaf()) {
            this.treeView.readJobGroup(this.conObj);
            this.treeView.expand();
          }
          this.infoPane.showDetailOfRoot();
        }
        else if (i == ETLTreeView.ItemNotifyUserFolder) {
          this.mainMenu.setNotifyUserFolder();
          if (paramDefaultMutableTreeNode.isLeaf()) {
            this.treeView.readNotifyUser(this.conObj, paramDefaultMutableTreeNode);
            this.treeView.expand();
          }
          this.infoPane.showDetailOfNone();
        }
        else if (i == ETLTreeView.ItemNotifyGroupFolder) {
          this.mainMenu.setNotifyUserGroupFolder();
          if (paramDefaultMutableTreeNode.isLeaf()) {
            this.treeView.readNotifyGroup(this.conObj, paramDefaultMutableTreeNode);
            this.treeView.expand();
          }
          this.infoPane.showDetailOfNone();
        }
        else if (i == ETLTreeView.ItemNotifyUser) {
          this.mainMenu.setNotifyUser();
          this.infoPane.showDetailOfUser(str1);
        }
        else if (i == ETLTreeView.ItemNotifyGroup) {
          this.mainMenu.setNotifyUserGroup();
          if (paramDefaultMutableTreeNode.isLeaf())
          {
            this.treeView.readNotifyGroupMember(this.conObj, paramDefaultMutableTreeNode, str1);
            this.treeView.expand();
          }
          this.infoPane.showDetailOfUserGroup(str1);
        }
        else if (i == ETLTreeView.ItemNotifyGroupMember) {
          this.mainMenu.setNotifyGroupMember();
          this.infoPane.showDetailOfUser(str1);
        }
        else if (i == ETLTreeView.ItemCalendarYear) {
          this.mainMenu.setCalendarYear();
          this.infoPane.showDetailOfRoot();
        }
        else if (i == ETLTreeView.ItemJobNotifyFolder) {
          this.mainMenu.setNotifyJobFolder();
          this.infoPane.showDetailOfNone();
        }
        else if (i == ETLTreeView.ItemNotifyDest) {
          this.mainMenu.setNotifyUser();

          str2 = this.treeView.getSysName(paramDefaultMutableTreeNode);
          str3 = this.treeView.getJobName(paramDefaultMutableTreeNode);

          int j = this.treeView.getSeq(paramDefaultMutableTreeNode);

          this.infoPane.showDetailOfNotification(str2, str3, j);
        }
        else if (i == ETLTreeView.ItemSystemInfoFolder) {
          this.mainMenu.setSystemInfoFolder();
          if (paramDefaultMutableTreeNode.isLeaf()) {
            this.treeView.readETLServer(this.conObj);
            this.treeView.expand();
          }
          this.infoPane.showDetailOfNone();
        }
        else if (i == ETLTreeView.ItemSystemServerInfo) {
          this.mainMenu.setSystemInfoServer();
          this.infoPane.showDetailOfServer(str1);
        }
        else if (i == ETLTreeView.ItemTriggerJob) {
          this.mainMenu.setConditionJob();

          str2 = this.treeView.getSysName(paramDefaultMutableTreeNode);
          str3 = this.treeView.getJobName(paramDefaultMutableTreeNode);

          this.infoPane.showDetailOfJob(str2, str3);

          if (paramDefaultMutableTreeNode.isLeaf()) {
            this.treeView.readJobGroupByHeadJob(this.conObj, str2, str3, paramDefaultMutableTreeNode);
            this.treeView.readTriggerJob(this.conObj, str2, str3, paramDefaultMutableTreeNode);
            this.treeView.readDependentJob(this.conObj, str2, str3, paramDefaultMutableTreeNode);

            this.treeView.expand();
          }
        }
        else if (i == ETLTreeView.ItemDependOnJob) {
          this.mainMenu.setConditionJob();

          str2 = this.treeView.getSysName(paramDefaultMutableTreeNode);
          str3 = this.treeView.getJobName(paramDefaultMutableTreeNode);

          this.infoPane.showDetailOfJob(str2, str3);

          if (paramDefaultMutableTreeNode.isLeaf()) {
            this.treeView.readJobGroupByHeadJob(this.conObj, str2, str3, paramDefaultMutableTreeNode);
            this.treeView.readTriggerJob(this.conObj, str2, str3, paramDefaultMutableTreeNode);
            this.treeView.readDependentJob(this.conObj, str2, str3, paramDefaultMutableTreeNode);

            this.treeView.expand();
          }
        }
        else if ((i == ETLTreeView.ItemTriggerFolder) || (i == ETLTreeView.ItemDependOnFolder))
        {
          this.mainMenu.setConnect(); }
      }
    }
    setCursor(localCursor);
  }

  public void addSubsystem()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = this.treeView.getSelectedNode();

    ETLSubsystemDlg localETLSubsystemDlg = new ETLSubsystemDlg(this);
    localETLSubsystemDlg.add(this.conObj, localDefaultMutableTreeNode);
    this.treeView.expand();
  }

  public void addJob()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = this.treeView.getSelectedNode();

    if (localDefaultMutableTreeNode == null)
      return;

    String str = localDefaultMutableTreeNode.getUserObject().toString();

    ETLJobDlg localETLJobDlg = new ETLJobDlg(this);
    localETLJobDlg.add(this.conObj, localDefaultMutableTreeNode, str);
    this.treeView.expand();
  }

  public void addSource()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = this.treeView.getSelectedNode();
    if (localDefaultMutableTreeNode1 == null)
      return;

    int i = this.treeView.getItemType(localDefaultMutableTreeNode1);
    if (i != ETLTreeView.ItemETLJob)
      return;

    String str1 = localDefaultMutableTreeNode1.getUserObject().toString();

    DefaultMutableTreeNode localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
    String str2 = localDefaultMutableTreeNode2.getUserObject().toString();

    ETLSourceDlg localETLSourceDlg = new ETLSourceDlg(this);
    localETLSourceDlg.add(this.conObj, localDefaultMutableTreeNode1, str2, str1);
    this.treeView.expand();
  }

  public void addDependency()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode2;
    Object localObject1;
    String str;
    Object localObject2;
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = this.treeView.getSelectedNode();

    int i = this.treeView.getItemType(localDefaultMutableTreeNode1);

    if (i == ETLTreeView.ItemETLJob) {
      localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();

      localObject1 = localDefaultMutableTreeNode1.getUserObject().toString();
      str = localDefaultMutableTreeNode2.getUserObject().toString();

      localObject2 = new ETLDependencyDlg(this);
      ((ETLDependencyDlg)localObject2).add(this.conObj, localDefaultMutableTreeNode1, str, (String)localObject1);

      refresh(localDefaultMutableTreeNode1);
    }
    else if (i == ETLTreeView.ItemDependencyFolder) {
      localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
      localObject1 = (DefaultMutableTreeNode)localDefaultMutableTreeNode2.getParent();

      str = localDefaultMutableTreeNode2.getUserObject().toString();
      localObject2 = ((DefaultMutableTreeNode)localObject1).getUserObject().toString();

      ETLDependencyDlg localETLDependencyDlg = new ETLDependencyDlg(this);
      localETLDependencyDlg.add1(this.conObj, localDefaultMutableTreeNode1, (String)localObject2, str);
    }

    this.treeView.expand();
  }

  public void addJobStream()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode2;
    Object localObject1;
    String str;
    Object localObject2;
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = this.treeView.getSelectedNode();

    int i = this.treeView.getItemType(localDefaultMutableTreeNode1);

    if (i == ETLTreeView.ItemETLJob) {
      localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();

      localObject1 = localDefaultMutableTreeNode1.getUserObject().toString();
      str = localDefaultMutableTreeNode2.getUserObject().toString();

      localObject2 = new ETLJobStreamDlg(this);
      ((ETLJobStreamDlg)localObject2).add(this.conObj, localDefaultMutableTreeNode1, str, (String)localObject1);

      refresh(localDefaultMutableTreeNode1);
    }
    else if (i == ETLTreeView.ItemJobStreamFolder) {
      localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
      localObject1 = (DefaultMutableTreeNode)localDefaultMutableTreeNode2.getParent();

      str = localDefaultMutableTreeNode2.getUserObject().toString();
      localObject2 = ((DefaultMutableTreeNode)localObject1).getUserObject().toString();

      ETLJobStreamDlg localETLJobStreamDlg = new ETLJobStreamDlg(this);
      localETLJobStreamDlg.add1(this.conObj, localDefaultMutableTreeNode1, (String)localObject2, str);
    }

    this.treeView.expand();
  }

  public void addJobRelatedJob()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode2;
    Object localObject1;
    String str;
    Object localObject2;
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = this.treeView.getSelectedNode();

    int i = this.treeView.getItemType(localDefaultMutableTreeNode1);

    if (i == ETLTreeView.ItemETLJob) {
      localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();

      localObject1 = localDefaultMutableTreeNode1.getUserObject().toString();
      str = localDefaultMutableTreeNode2.getUserObject().toString();

      localObject2 = new ETLJobRelatedJobDlg(this);
      ((ETLJobRelatedJobDlg)localObject2).add(this.conObj, localDefaultMutableTreeNode1, str, (String)localObject1);

      refresh(localDefaultMutableTreeNode1);
    }
    else if (i == ETLTreeView.ItemRelatedJobFolder) {
      localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
      localObject1 = (DefaultMutableTreeNode)localDefaultMutableTreeNode2.getParent();

      str = localDefaultMutableTreeNode2.getUserObject().toString();
      localObject2 = ((DefaultMutableTreeNode)localObject1).getUserObject().toString();

      ETLJobRelatedJobDlg localETLJobRelatedJobDlg = new ETLJobRelatedJobDlg(this);
      localETLJobRelatedJobDlg.add1(this.conObj, localDefaultMutableTreeNode1, (String)localObject2, str);
    }

    this.treeView.expand();
  }

  public void addJobGroup()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = this.treeView.getSelectedNode();

    ETLJobGroupDlg localETLJobGroupDlg = new ETLJobGroupDlg(this);
    localETLJobGroupDlg.add(this.conObj, localDefaultMutableTreeNode);
    this.treeView.expand();
  }

  public void addJobGroupChild()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = this.treeView.getSelectedNode();

    String str = localDefaultMutableTreeNode.getUserObject().toString();

    ETLJobGroupChildDlg localETLJobGroupChildDlg = new ETLJobGroupChildDlg(this);
    localETLJobGroupChildDlg.add(this.conObj, localDefaultMutableTreeNode, str);
    this.treeView.expand();
  }

  public void addUser()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = this.treeView.getSelectedNode();

    ETLUserDlg localETLUserDlg = new ETLUserDlg(this);
    localETLUserDlg.add(this.conObj, localDefaultMutableTreeNode);
    this.treeView.expand();
  }

  public void addUserGroup()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = this.treeView.getSelectedNode();

    ETLUserGroupDlg localETLUserGroupDlg = new ETLUserGroupDlg(this);
    localETLUserGroupDlg.add(this.conObj, localDefaultMutableTreeNode);
    this.treeView.expand();
  }

  public void addGroupMember()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = this.treeView.getSelectedNode();
    String str = localDefaultMutableTreeNode.getUserObject().toString();

    ETLGroupMemberDlg localETLGroupMemberDlg = new ETLGroupMemberDlg(this);
    localETLGroupMemberDlg.add(this.conObj, localDefaultMutableTreeNode, str);
    this.treeView.expand();
  }

  public void addNotifyJob()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode2;
    Object localObject1;
    String str;
    Object localObject2;
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = this.treeView.getSelectedNode();

    int i = this.treeView.getItemType(localDefaultMutableTreeNode1);

    if (i == ETLTreeView.ItemETLJob) {
      localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();

      localObject1 = localDefaultMutableTreeNode1.getUserObject().toString();
      str = localDefaultMutableTreeNode2.getUserObject().toString();

      localObject2 = new ETLNotifyJobDlg(this);
      ((ETLNotifyJobDlg)localObject2).add(this.conObj, str, (String)localObject1, localDefaultMutableTreeNode1);
      this.treeView.expand();
    }
    else if (i == ETLTreeView.ItemJobNotifyFolder) {
      localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
      localObject1 = (DefaultMutableTreeNode)localDefaultMutableTreeNode2.getParent();

      str = localDefaultMutableTreeNode2.getUserObject().toString();
      localObject2 = ((DefaultMutableTreeNode)localObject1).getUserObject().toString();

      ETLNotifyJobDlg localETLNotifyJobDlg = new ETLNotifyJobDlg(this);
      localETLNotifyJobDlg.add1(this.conObj, (String)localObject2, str, localDefaultMutableTreeNode1);
      this.treeView.expand();
    }
  }

  public void property()
  {
    Object localObject1;
    DefaultTreeModel localDefaultTreeModel = this.treeView.getTreeModel();
    DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode)this.treeView.getTree().getLastSelectedPathComponent();

    if (localDefaultMutableTreeNode == null)
      return;

    int i = this.treeView.getItemType(localDefaultMutableTreeNode);
    String str1 = this.treeView.getItemName(localDefaultMutableTreeNode);

    if (i == ETLTreeView.ItemSubSystem) {
      localObject1 = new ETLSubsystemDlg(this);
      ((ETLSubsystemDlg)localObject1).modify(this.conObj, localDefaultMutableTreeNode, str1);

      this.infoPane.showDetailOfSystem(str1);
    } else {
      Object localObject2;
      Object localObject3;
      if (i == ETLTreeView.ItemETLJob) {
        localObject1 = (DefaultMutableTreeNode)localDefaultMutableTreeNode.getParent();
        localObject2 = ((DefaultMutableTreeNode)localObject1).getUserObject().toString();

        localObject3 = new ETLJobDlg(this);
        ((ETLJobDlg)localObject3).modify(this.conObj, localDefaultMutableTreeNode, (String)localObject2, str1);

        this.infoPane.showDetailOfJob((String)localObject2, str1);
      } else {
        Object localObject4;
        Object localObject5;
        if (i == ETLTreeView.ItemJobSource) {
          localObject1 = (DefaultMutableTreeNode)localDefaultMutableTreeNode.getParent();
          localObject2 = (DefaultMutableTreeNode)((DefaultMutableTreeNode)localObject1).getParent();

          localObject3 = ((DefaultMutableTreeNode)localObject1).getUserObject().toString();
          localObject4 = ((DefaultMutableTreeNode)localObject2).getUserObject().toString();

          localObject5 = new ETLSourceDlg(this);
          ((ETLSourceDlg)localObject5).modify(this.conObj, localDefaultMutableTreeNode, (String)localObject4, (String)localObject3, str1);

          this.infoPane.showDetailOfSource(str1);
        } else {
          String str2;
          String str3;
          Object localObject6;
          if (i == ETLTreeView.ItemDependentJob) {
            localObject1 = (DefaultMutableTreeNode)localDefaultMutableTreeNode.getParent();
            localObject2 = (DefaultMutableTreeNode)((DefaultMutableTreeNode)localObject1).getParent();
            localObject3 = (DefaultMutableTreeNode)((DefaultMutableTreeNode)localObject2).getParent();

            localObject4 = ((DefaultMutableTreeNode)localObject2).getUserObject().toString();
            localObject5 = ((DefaultMutableTreeNode)localObject3).getUserObject().toString();

            str2 = this.treeView.getSysName(localDefaultMutableTreeNode);
            str3 = this.treeView.getJobName(localDefaultMutableTreeNode);

            localObject6 = new ETLDependencyDlg(this);
            ((ETLDependencyDlg)localObject6).modify(this.conObj, localDefaultMutableTreeNode, (String)localObject5, (String)localObject4, str2, str3);

            this.treeView.setItemStatus(localDefaultMutableTreeNode, ((ETLDependencyDlg)localObject6).getEnableStatus());
            localDefaultTreeModel.nodeChanged(localDefaultMutableTreeNode);
          }
          else if (i == ETLTreeView.ItemDownStreamJob) {
            localObject1 = (DefaultMutableTreeNode)localDefaultMutableTreeNode.getParent();
            localObject2 = (DefaultMutableTreeNode)((DefaultMutableTreeNode)localObject1).getParent();
            localObject3 = (DefaultMutableTreeNode)((DefaultMutableTreeNode)localObject2).getParent();

            localObject4 = ((DefaultMutableTreeNode)localObject2).getUserObject().toString();
            localObject5 = ((DefaultMutableTreeNode)localObject3).getUserObject().toString();

            str2 = this.treeView.getSysName(localDefaultMutableTreeNode);
            str3 = this.treeView.getJobName(localDefaultMutableTreeNode);

            localObject6 = new ETLJobStreamDlg(this);
            ((ETLJobStreamDlg)localObject6).modify(this.conObj, localDefaultMutableTreeNode, (String)localObject5, (String)localObject4, str2, str3);

            this.treeView.setItemStatus(localDefaultMutableTreeNode, ((ETLJobStreamDlg)localObject6).getEnableStatus());
            localDefaultTreeModel.nodeChanged(localDefaultMutableTreeNode);
          }
          else if (i == ETLTreeView.ItemRelatedJob) {
            localObject1 = (DefaultMutableTreeNode)localDefaultMutableTreeNode.getParent();
            localObject2 = (DefaultMutableTreeNode)((DefaultMutableTreeNode)localObject1).getParent();
            localObject3 = (DefaultMutableTreeNode)((DefaultMutableTreeNode)localObject2).getParent();

            localObject4 = ((DefaultMutableTreeNode)localObject2).getUserObject().toString();
            localObject5 = ((DefaultMutableTreeNode)localObject3).getUserObject().toString();

            str2 = str1.substring(1, 4);
            str3 = str1.substring(6, str1.length());

            localObject6 = new ETLJobRelatedJobDlg(this);
            ((ETLJobRelatedJobDlg)localObject6).modify(this.conObj, localDefaultMutableTreeNode, (String)localObject5, (String)localObject4, str2, str3);
          }
          else if (i == ETLTreeView.ItemJobGroup) {
            localObject1 = new ETLJobGroupDlg(this);
            ((ETLJobGroupDlg)localObject1).modify(this.conObj, localDefaultMutableTreeNode, str1);
          }
          else if (i == ETLTreeView.ItemJobGroupChild) {
            localObject1 = (DefaultMutableTreeNode)localDefaultMutableTreeNode.getParent();

            localObject2 = ((DefaultMutableTreeNode)localObject1).getUserObject().toString();

            localObject3 = this.treeView.getSysName(localDefaultMutableTreeNode);
            localObject4 = this.treeView.getJobName(localDefaultMutableTreeNode);

            localObject5 = new ETLJobGroupChildDlg(this);
            ((ETLJobGroupChildDlg)localObject5).modify(this.conObj, localDefaultMutableTreeNode, (String)localObject2, (String)localObject3, (String)localObject4);

            this.treeView.setItemStatus(localDefaultMutableTreeNode, ((ETLJobGroupChildDlg)localObject5).getEnableStatus());
            localDefaultTreeModel.nodeChanged(localDefaultMutableTreeNode);
          }
          else if (i == ETLTreeView.ItemNotifyUser) {
            localObject1 = new ETLUserDlg(this);
            ((ETLUserDlg)localObject1).modify(this.conObj, localDefaultMutableTreeNode, str1);
          }
          else if (i == ETLTreeView.ItemNotifyGroup) {
            localObject1 = new ETLUserGroupDlg(this);
            ((ETLUserGroupDlg)localObject1).modify(this.conObj, localDefaultMutableTreeNode, str1);
          }
          else if (i == ETLTreeView.ItemNotifyGroupMember) {
            localObject1 = (DefaultMutableTreeNode)localDefaultMutableTreeNode.getParent();

            localObject2 = ((DefaultMutableTreeNode)localObject1).getUserObject().toString();

            localObject3 = new ETLGroupMemberDlg(this);
            ((ETLGroupMemberDlg)localObject3).modify(this.conObj, localDefaultMutableTreeNode, str1, (String)localObject2);
          }
          else if (i == ETLTreeView.ItemNotifyDest) {
            localObject1 = this.treeView.getSysName(localDefaultMutableTreeNode);
            localObject2 = this.treeView.getJobName(localDefaultMutableTreeNode);

            int j = this.treeView.getSeq(localDefaultMutableTreeNode);

            localObject4 = new ETLNotifyJobDlg(this);
            ((ETLNotifyJobDlg)localObject4).modify(this.conObj, localDefaultMutableTreeNode, (String)localObject1, (String)localObject2, j);

            localObject5 = ((ETLNotifyJobDlg)localObject4).getNotificationDestName();
            this.treeView.setItemName(localDefaultMutableTreeNode, (String)localObject5);

            localDefaultTreeModel.nodeChanged(localDefaultMutableTreeNode);
            this.infoPane.showDetailOfNotification((String)localObject1, (String)localObject2, j);
          }
          else if (i == ETLTreeView.ItemSystemServerInfo) {
            localObject1 = new ETLServerDlg(this);
            ((ETLServerDlg)localObject1).modify(this.conObj, localDefaultMutableTreeNode, str1); } } }
    }
  }

  public void SetFilter() {
    ETLFilterDlg localETLFilterDlg = new ETLFilterDlg(this);
    localETLFilterDlg.add(this.conObj);
  }

  public void refresh()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode)this.treeView.getTree().getLastSelectedPathComponent();

    if (localDefaultMutableTreeNode == null)
      return;

    refresh(localDefaultMutableTreeNode);
  }

  public void refresh(DefaultMutableTreeNode paramDefaultMutableTreeNode)
  {
    String str1 = this.treeView.getItemName(paramDefaultMutableTreeNode);
    int i = this.treeView.getItemType(paramDefaultMutableTreeNode);

    if (i == ETLTreeView.ItemETLSystem) {
      this.treeView.removeNodeAllChild(paramDefaultMutableTreeNode);
      this.treeView.readETLSystem(this.conObj);
    }
    else if (i == ETLTreeView.ItemSubSystem) {
      this.treeView.removeNodeAllChild(paramDefaultMutableTreeNode);
      this.treeView.readETLJob(this.conObj, str1, paramDefaultMutableTreeNode);
    }
    else if (i == ETLTreeView.ItemETLJob) {
      this.treeView.removeNodeAllChild(paramDefaultMutableTreeNode);

      DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode)paramDefaultMutableTreeNode.getParent();
      String str2 = localDefaultMutableTreeNode.getUserObject().toString();

      this.treeView.readDependency(this.conObj, str2, str1, paramDefaultMutableTreeNode);
      this.treeView.readJobStream(this.conObj, str2, str1, paramDefaultMutableTreeNode);
      this.treeView.readJobRelatedJob(this.conObj, str2, str1, paramDefaultMutableTreeNode);
      this.treeView.readJobNotification(this.conObj, str2, str1, paramDefaultMutableTreeNode);
      this.treeView.readSource(this.conObj, str2, str1, paramDefaultMutableTreeNode);
      this.treeView.readCalendarYear(this.conObj, str2, str1, paramDefaultMutableTreeNode);
      this.treeView.readTriggerJob(this.conObj, str2, str1, paramDefaultMutableTreeNode);
      this.treeView.readJobGroupByHeadJob(this.conObj, str2, str1, paramDefaultMutableTreeNode);

      this.infoPane.showDetailOfJob(str2, str1);
    }
    else if (i == ETLTreeView.ItemJobGroupFolder) {
      this.treeView.removeNodeAllChild(paramDefaultMutableTreeNode);
      this.treeView.readJobGroup(this.conObj);
    }
    else if (i == ETLTreeView.ItemJobGroup) {
      this.treeView.removeNodeAllChild(paramDefaultMutableTreeNode);
      this.treeView.readJobGroupChild(this.conObj, str1, paramDefaultMutableTreeNode);
    }
    else if (i == ETLTreeView.ItemNotifyUserFolder) {
      this.treeView.removeNodeAllChild(paramDefaultMutableTreeNode);
      this.treeView.readNotifyUser(this.conObj, paramDefaultMutableTreeNode);
    }
    else if (i == ETLTreeView.ItemNotifyGroupFolder) {
      this.treeView.removeNodeAllChild(paramDefaultMutableTreeNode);
      this.treeView.readNotifyGroup(this.conObj, paramDefaultMutableTreeNode);
    }
    else if (i == ETLTreeView.ItemSystemInfoFolder) {
      this.treeView.removeNodeAllChild(paramDefaultMutableTreeNode);
      this.treeView.readETLServer(this.conObj);
    }

    this.treeView.expand();
  }

  public void viewJobRecordLog()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = (DefaultMutableTreeNode)this.treeView.getTree().getLastSelectedPathComponent();

    if (localDefaultMutableTreeNode1 == null)
      return;

    int i = this.treeView.getItemType(localDefaultMutableTreeNode1);
    String str1 = this.treeView.getItemName(localDefaultMutableTreeNode1);

    if (i == ETLTreeView.ItemETLJob) {
      DefaultMutableTreeNode localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
      String str2 = localDefaultMutableTreeNode2.getUserObject().toString();

      ETLJobRecordViewer localETLJobRecordViewer = new ETLJobRecordViewer(this);

      localETLJobRecordViewer.retrieveLog(str2, str1);
      localETLJobRecordViewer.show();
    }
  }

  public void viewJobDetailLog()
  {
    Object localObject;
    String str2;
    ETLJobLogViewer localETLJobLogViewer;
    DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode)this.treeView.getTree().getLastSelectedPathComponent();

    if (localDefaultMutableTreeNode == null)
      return;

    int i = this.treeView.getItemType(localDefaultMutableTreeNode);
    String str1 = this.treeView.getItemName(localDefaultMutableTreeNode);

    if (i == ETLTreeView.ItemETLJob) {
      localObject = (DefaultMutableTreeNode)localDefaultMutableTreeNode.getParent();
      str2 = ((DefaultMutableTreeNode)localObject).getUserObject().toString();

      localETLJobLogViewer = new ETLJobLogViewer(this);

      localETLJobLogViewer.retrieveLog(str2, str1);
      localETLJobLogViewer.show();
    }
    else if ((i == ETLTreeView.ItemDependentJob) || (i == ETLTreeView.ItemDownStreamJob) || (i == ETLTreeView.ItemJobGroupChild))
    {
      localObject = this.treeView.getSysName(localDefaultMutableTreeNode);
      str2 = this.treeView.getJobName(localDefaultMutableTreeNode);

      localETLJobLogViewer = new ETLJobLogViewer(this);

      localETLJobLogViewer.retrieveLog((String)localObject, str2);
      localETLJobLogViewer.show();
    }
  }

  public void viewJobAllStatus()
  {
    Object localObject;
    String str2;
    ETLJobStatusViewer localETLJobStatusViewer;
    DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode)this.treeView.getTree().getLastSelectedPathComponent();

    if (localDefaultMutableTreeNode == null)
      return;

    int i = this.treeView.getItemType(localDefaultMutableTreeNode);
    String str1 = this.treeView.getItemName(localDefaultMutableTreeNode);

    if (i == ETLTreeView.ItemETLJob) {
      localObject = (DefaultMutableTreeNode)localDefaultMutableTreeNode.getParent();
      str2 = ((DefaultMutableTreeNode)localObject).getUserObject().toString();

      localETLJobStatusViewer = new ETLJobStatusViewer(this);

      localETLJobStatusViewer.retrieveLog(str2, str1);
      localETLJobStatusViewer.show();
    }
    else if ((i == ETLTreeView.ItemDependentJob) || (i == ETLTreeView.ItemDownStreamJob) || (i == ETLTreeView.ItemJobGroupChild))
    {
      localObject = this.treeView.getSysName(localDefaultMutableTreeNode);
      str2 = this.treeView.getJobName(localDefaultMutableTreeNode);

      localETLJobStatusViewer = new ETLJobStatusViewer(this);

      localETLJobStatusViewer.retrieveLog((String)localObject, str2);
      localETLJobStatusViewer.show();
    }
  }

  public void jobWatchDog()
  {
    if (this.jobWatchDogDlg == null) {
      this.jobWatchDogDlg = new ETLJobWatchDog(this);
      this.jobWatchDogDlg.fillComBox(this.conObj);
      this.jobWatchDogDlg.retrieveLog();
      this.jobWatchDogDlg.show();
      this.bRefreshWatchDog = false;
    }
    else {
      if (this.bRefreshWatchDog) {
        this.jobWatchDogDlg.fillComBox(this.conObj);
        this.jobWatchDogDlg.retrieveLog();
        this.bRefreshWatchDog = false;
      }
      this.jobWatchDogDlg.show();
    }
  }

  public void jobErrorDetection()
  {
    if (this.jobErrorDetectionDlg == null) {
      this.jobErrorDetectionDlg = new ETLJobErrorDetection(this);
      this.jobErrorDetectionDlg.retrieveLog();
      this.jobErrorDetectionDlg.show();
      this.bRefreshErrorDetection = false;
    }
    else {
      if (this.bRefreshErrorDetection) {
        this.jobErrorDetectionDlg.retrieveLog();
        this.bRefreshErrorDetection = false;
      }
      this.jobErrorDetectionDlg.show();
    }
  }

  public void jobTracer()
  {
    if (this.jobTracerDlg == null) {
      this.jobTracerDlg = new ETLJobTracer(this);
      this.jobTracerDlg.fillSystemList();

      this.jobTracerDlg.show();
      this.bRefreshJobTracer = false;
    }
    else {
      if (this.bRefreshJobTracer) {
        this.jobTracerDlg.fillSystemList();

        this.bRefreshJobTracer = false;
      }
      this.jobTracerDlg.show();
    }
  }

  public void resetJobGroupChild()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode)this.treeView.getTree().getLastSelectedPathComponent();

    if (localDefaultMutableTreeNode == null)
      return;

    int i = this.treeView.getItemType(localDefaultMutableTreeNode);
    String str1 = this.treeView.getItemName(localDefaultMutableTreeNode);

    if (i != ETLTreeView.ItemJobGroup)
      return;

    int j = JOptionPane.showConfirmDialog(this, "Do you want to reset group child job check flag?", "Reset Group Child Job", 0, 3);

    if (j != 0) {
      return;
    }

    String str3 = getDBName();

    Vector localVector = new Vector();
    try
    {
      Statement localStatement = this.conObj.createStatement();

      String str2 = "UPDATE " + str3 + "ETL_Job_GroupChild SET CheckFlag = 'N'" + "   WHERE groupname = '" + str1 + "'";

      int k = localStatement.executeUpdate(str2);

      localStatement.close();
    }
    catch (SQLException localSQLException) {
      String str4 = "Could not update child job's checkflag column\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str4, "Error", 0);

      return;
    }

    refresh(localDefaultMutableTreeNode);
  }

  public void enableJobGroupChild()
  {
    Statement localStatement;
    String str4;
    String str5;
    DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode)this.treeView.getTree().getLastSelectedPathComponent();

    if (localDefaultMutableTreeNode == null)
      return;

    int i = this.treeView.getItemType(localDefaultMutableTreeNode);
    String str1 = this.treeView.getItemName(localDefaultMutableTreeNode);

    if (i != ETLTreeView.ItemJobGroup)
      return;

    int j = JOptionPane.showConfirmDialog(this, "Do you want to enable group child job?", "Enable Group Child Job", 0, 3);

    if (j != 0) {
      return;
    }

    String str2 = getDBName();

    Vector localVector = new Vector();
    try
    {
      localStatement = this.conObj.createStatement();

      String str3 = "SELECT etl_system, etl_job    FROM " + str2 + "etl_job_groupchild" + "  WHERE groupname = '" + str1 + "'" + "   ORDER BY etl_system, etl_job";

      ResultSet localResultSet = localStatement.executeQuery(str3);

      while (localResultSet.next()) {
        str4 = localResultSet.getString(1);
        localVector.add(str4);
        str5 = localResultSet.getString(2);
        localVector.add(str5);
      }
    }
    catch (SQLException localSQLException1) {
      str4 = "Could not retrieve job group child\n" + localSQLException1.getMessage();

      JOptionPane.showMessageDialog(this, str4, "Error", 0);

      return;
    }
    try
    {
      for (int k = 0; k < localVector.size(); k += 2) {
        str4 = (String)localVector.get(k);
        str5 = (String)localVector.get(k + 1);

        String str6 = "UPDATE " + str2 + "etl_job SET enable = '1'" + "   WHERE etl_system = '" + str4 + "'" + "     AND etl_job = '" + str5 + "'";

        int l = localStatement.executeUpdate(str6);
      }
      localStatement.close();
    }
    catch (SQLException localSQLException2) {
      str4 = "Could not update job's enable column\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this, str4, "Error", 0);

      return;
    }
  }

  public void addDataCalendar()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = this.treeView.getSelectedNode();
    if (localDefaultMutableTreeNode1 == null)
      return;

    int i = this.treeView.getItemType(localDefaultMutableTreeNode1);
    if (i != ETLTreeView.ItemETLJob)
      return;

    String str1 = localDefaultMutableTreeNode1.getUserObject().toString();

    DefaultMutableTreeNode localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
    String str2 = localDefaultMutableTreeNode2.getUserObject().toString();

    Cursor localCursor = getCursor();
    setCursor(new Cursor(3));

    ETLAddCalendarDlg localETLAddCalendarDlg = new ETLAddCalendarDlg(this);

    localETLAddCalendarDlg.add(this.conObj, localDefaultMutableTreeNode1, str2, str1);

    hide();
    show();

    setCursor(localCursor);

    this.treeView.expand();
  }

  public void editDataCalendar()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = this.treeView.getSelectedNode();
    if (localDefaultMutableTreeNode1 == null)
      return;

    int i = this.treeView.getItemType(localDefaultMutableTreeNode1);
    if (i != ETLTreeView.ItemCalendarYear)
      return;

    String str1 = localDefaultMutableTreeNode1.getUserObject().toString();
    String str2 = str1.substring(str1.length() - 5, str1.length() - 1);

    DefaultMutableTreeNode localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
    String str3 = localDefaultMutableTreeNode2.getUserObject().toString();

    DefaultMutableTreeNode localDefaultMutableTreeNode3 = (DefaultMutableTreeNode)localDefaultMutableTreeNode2.getParent();
    String str4 = localDefaultMutableTreeNode3.getUserObject().toString();

    int j = Integer.parseInt(str2);

    Cursor localCursor = getCursor();
    setCursor(new Cursor(3));

    ETLEditCalendarDlg localETLEditCalendarDlg = new ETLEditCalendarDlg(this);
    localETLEditCalendarDlg.edit(this.conObj, str4, str3, j);

    hide();
    show();

    setCursor(localCursor);
  }

  public void deleteDataCalendar()
  {
    Statement localStatement;
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = this.treeView.getSelectedNode();
    if (localDefaultMutableTreeNode1 == null)
      return;

    int i = this.treeView.getItemType(localDefaultMutableTreeNode1);
    if (i != ETLTreeView.ItemCalendarYear)
      return;

    String str1 = localDefaultMutableTreeNode1.getUserObject().toString();
    String str2 = str1.substring(str1.length() - 5, str1.length() - 1);

    DefaultMutableTreeNode localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
    String str3 = localDefaultMutableTreeNode2.getUserObject().toString();

    DefaultMutableTreeNode localDefaultMutableTreeNode3 = (DefaultMutableTreeNode)localDefaultMutableTreeNode2.getParent();
    String str4 = localDefaultMutableTreeNode3.getUserObject().toString();

    int j = Integer.parseInt(str2);

    String str5 = "Do you want to delete calendar year " + str2 + "?";
    int k = JOptionPane.showConfirmDialog(this, str5, "Delete", 0, 3);

    if (k != 0)
      return;

    Cursor localCursor = getCursor();
    setCursor(new Cursor(3));

    String str6 = getDBName();
    try
    {
      localStatement = this.conObj.createStatement();
    }
    catch (SQLException localSQLException1) {
      setCursor(localCursor);

      JOptionPane.showMessageDialog(this, "Unable to create statement object", "Error", 0);

      return;
    }
    try
    {
      String str7 = "DELETE FROM " + str6 + "DataCalendar" + "   WHERE etl_system = '" + str4 + "'" + "     AND etl_job = '" + str3 + "'" + "     AND calendaryear = " + j;

      int l = localStatement.executeUpdate(str7);

      str7 = "DELETE FROM " + str6 + "DataCalendarYear" + "   WHERE etl_system = '" + str4 + "'" + "     AND etl_job = '" + str3 + "'" + "     AND calendaryear = " + j;

      l = localStatement.executeUpdate(str7);
    }
    catch (SQLException localSQLException2) {
      setCursor(localCursor);

      String str8 = "Some error occured while deleting calendar year\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this, str8, "Error", 0);

      return;
    }

    DefaultTreeModel localDefaultTreeModel = this.treeView.getTreeModel();
    localDefaultTreeModel.removeNodeFromParent(localDefaultMutableTreeNode1);

    setCursor(localCursor);
  }

  public void setDataCalendar()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = this.treeView.getSelectedNode();
    if (localDefaultMutableTreeNode1 == null)
      return;

    int i = this.treeView.getItemType(localDefaultMutableTreeNode1);
    if (i != ETLTreeView.ItemCalendarYear)
      return;

    String str1 = localDefaultMutableTreeNode1.getUserObject().toString();
    String str2 = str1.substring(str1.length() - 5, str1.length() - 1);

    DefaultMutableTreeNode localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
    String str3 = localDefaultMutableTreeNode2.getUserObject().toString();

    DefaultMutableTreeNode localDefaultMutableTreeNode3 = (DefaultMutableTreeNode)localDefaultMutableTreeNode2.getParent();
    String str4 = localDefaultMutableTreeNode3.getUserObject().toString();

    int j = Integer.parseInt(str2);

    Cursor localCursor = getCursor();
    setCursor(new Cursor(3));

    ETLSetCalendarDlg localETLSetCalendarDlg = new ETLSetCalendarDlg(this);
    localETLSetCalendarDlg.set(this.conObj, str4, str3, j);

    hide();
    show();

    setCursor(localCursor);
  }

  public void addServerInfo()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = this.treeView.getSelectedNode();

    ETLServerDlg localETLServerDlg = new ETLServerDlg(this);
    localETLServerDlg.add(this.conObj, localDefaultMutableTreeNode);
    this.treeView.expand();
  }

  public void resetJobStatus()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = (DefaultMutableTreeNode)this.treeView.getTree().getLastSelectedPathComponent();

    if (localDefaultMutableTreeNode1 == null)
      return;

    int i = this.treeView.getItemType(localDefaultMutableTreeNode1);
    String str1 = this.treeView.getItemName(localDefaultMutableTreeNode1);

    if ((i != ETLTreeView.ItemETLJob) && (i != ETLTreeView.ItemDependentJob) && (i != ETLTreeView.ItemTriggerJob) && (i != ETLTreeView.ItemJobGroupChild1) && (i != ETLTreeView.ItemDependOnJob))
    {
      return;
    }
    DefaultMutableTreeNode localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();

    String str2 = this.treeView.getSysName(localDefaultMutableTreeNode1);
    String str3 = this.treeView.getJobName(localDefaultMutableTreeNode1);

    ETLResetJobStatusDlg localETLResetJobStatusDlg = new ETLResetJobStatusDlg(this);
    localETLResetJobStatusDlg.resetJobStatus(this.conObj, str2, str3);

    if ((i == ETLTreeView.ItemTriggerJob) || (i == ETLTreeView.ItemJobGroupChild1) || (i == ETLTreeView.ItemDependOnJob))
    {
      String str4 = localETLResetJobStatusDlg.getTxDate();
      String str5 = localETLResetJobStatusDlg.getStatus();

      if (!(str5.equals(""))) {
        String str6 = "[" + str2 + "] " + str3 + " (TxDate = '" + str4 + "')";

        this.treeView.setItemName(localDefaultMutableTreeNode1, str6);
        if (str5.equals("Done"))
          this.treeView.setItemStatus(localDefaultMutableTreeNode1, 1);
        else
          this.treeView.setItemStatus(localDefaultMutableTreeNode1, 0);

        this.treeView.getTreeModel().nodeChanged(localDefaultMutableTreeNode1);
      }
    }

    this.infoPane.showDetailOfJob(str2, str3);
    this.bRefreshWatchDog = true;
  }

  public void viewAllDepJobStatus()
  {
    String str1;
    String str2;
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = (DefaultMutableTreeNode)this.treeView.getTree().getLastSelectedPathComponent();

    if (localDefaultMutableTreeNode1 == null)
      return;

    int i = this.treeView.getItemType(localDefaultMutableTreeNode1);

    if (i == ETLTreeView.ItemETLJob) {
      localObject = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();

      str2 = localDefaultMutableTreeNode1.getUserObject().toString();
      str1 = ((DefaultMutableTreeNode)localObject).getUserObject().toString();
    }
    else if (i == ETLTreeView.ItemDependencyFolder) {
      localObject = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
      DefaultMutableTreeNode localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)((DefaultMutableTreeNode)localObject).getParent();

      str2 = ((DefaultMutableTreeNode)localObject).getUserObject().toString();
      str1 = localDefaultMutableTreeNode2.getUserObject().toString();
    }
    else {
      return;
    }

    Object localObject = new ETLViewDepJobStatus(this);
    ((ETLViewDepJobStatus)localObject).retrieveStatus(str1, str2);
  }

  public void viewAllBelongGroup()
  {
    String str1;
    String str2;
    DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode)this.treeView.getTree().getLastSelectedPathComponent();

    if (localDefaultMutableTreeNode == null)
      return;

    int i = this.treeView.getItemType(localDefaultMutableTreeNode);

    if (i == ETLTreeView.ItemETLJob) {
      localObject = (DefaultMutableTreeNode)localDefaultMutableTreeNode.getParent();

      str2 = localDefaultMutableTreeNode.getUserObject().toString();
      str1 = ((DefaultMutableTreeNode)localObject).getUserObject().toString();
    }
    else {
      return;
    }

    Object localObject = new ETLViewAllBelongGroup(this);
    ((ETLViewAllBelongGroup)localObject).retrieveStatus(str1, str2);
  }

  public void viewAllChildJobStatus()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode = (DefaultMutableTreeNode)this.treeView.getTree().getLastSelectedPathComponent();

    if (localDefaultMutableTreeNode == null)
      return;

    int i = this.treeView.getItemType(localDefaultMutableTreeNode);
    String str = this.treeView.getItemName(localDefaultMutableTreeNode);

    if (i != ETLTreeView.ItemJobGroup)
      return;

    ETLViewChildJobStatus localETLViewChildJobStatus = new ETLViewChildJobStatus(this);
    localETLViewChildJobStatus.retrieveStatus(str);
  }

  public void invokeJob()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = (DefaultMutableTreeNode)this.treeView.getTree().getLastSelectedPathComponent();

    if (localDefaultMutableTreeNode1 == null)
      return;

    if (this.treeView.getItemType(localDefaultMutableTreeNode1) != ETLTreeView.ItemETLJob)
      return;

    DefaultMutableTreeNode localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
    String str1 = this.treeView.getItemName(localDefaultMutableTreeNode1);
    String str2 = localDefaultMutableTreeNode2.getUserObject().toString();

    String str3 = ETLAgentInterface.getRunningServer(this.conObj, getDBName(), str2, str1);

    if (str3.equals("")) {
      str4 = "The running server for this job is not defined.\nUnable to invoke this job.";

      JOptionPane.showMessageDialog(this, str4, "Error", 0);

      return;
    }

    String str4 = ETLAgentInterface.getJobConvFileHead(this.conObj, getDBName(), str2, str1);

    if (str4.equals("")) {
      localObject = "The convert file head is not defined at job source, can not invoke this job";

      JOptionPane.showMessageDialog(this, localObject, "Error", 0);

      return;
    }

    Object localObject = new ETLJobGivenDateDlg(this, 0);
    ((Dialog)localObject).show();

    String str5 = ((ETLJobGivenDateDlg)localObject).givenDate;
    if (str5.equals(""))
      return;

    String str6 = str2 + "_" + str4 + "_" + str5 + ".dir";

    ETLAgentInterface localETLAgentInterface = new ETLAgentInterface();

    if (localETLAgentInterface.getServerInfo(this.conObj, getDBName(), str3) == 0) {
      String str8;
      if (localETLAgentInterface.connectToAgent() != 0) {
        String str7 = "Unable to connect ETL Agent";

        JOptionPane.showMessageDialog(this, str7, "Error", 0);

        return;
      }

      if (localETLAgentInterface.readHeaderInfo() != 0) {
        localETLAgentInterface.disconnectAgent();
        localETLAgentInterface = null;
        return;
      }

      int i = localETLAgentInterface.sendCmdInvoke(str6);
      localETLAgentInterface.disconnectAgent();

      if (i == 0)
      {
        str8 = str5.substring(0, 4) + "-" + str5.substring(4, 6) + "-" + str5.substring(6, 8);

        ETLAgentInterface.resetJobToPending(this.conObj, getDBName(), str2, str1, str8);

        String str9 = "Invoke job ok";
        JOptionPane.showMessageDialog(this, str9, "Invoke Job", 1);
      }
      else
      {
        str8 = "Error occured when invoke job";
        JOptionPane.showMessageDialog(this, str8, "Invoke Job", 0);
      }
    }

    localETLAgentInterface = null;
  }

  public void forceStartJob()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = (DefaultMutableTreeNode)this.treeView.getTree().getLastSelectedPathComponent();

    if (localDefaultMutableTreeNode1 == null)
      return;

    if (this.treeView.getItemType(localDefaultMutableTreeNode1) != ETLTreeView.ItemETLJob)
      return;

    DefaultMutableTreeNode localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
    String str1 = this.treeView.getItemName(localDefaultMutableTreeNode1);
    String str2 = localDefaultMutableTreeNode2.getUserObject().toString();

    String str3 = ETLAgentInterface.getRunningServer(this.conObj, getDBName(), str2, str1);

    if (str3.equals("")) {
      str4 = "The running server for this job is not defined.\nUnable to force start this job now.";

      JOptionPane.showMessageDialog(this, str4, "Error", 0);

      return;
    }

    String str4 = ETLAgentInterface.getJobConvFileHead(this.conObj, getDBName(), str2, str1);

    if (str4.equals("")) {
      localObject = "The convert file head is not defined at job source, can not force start this job now.";

      JOptionPane.showMessageDialog(this, localObject, "Error", 0);

      return;
    }

    Object localObject = new ETLJobGivenDateDlg(this, 1);
    ((Dialog)localObject).show();

    String str5 = ((ETLJobGivenDateDlg)localObject).givenDate;
    if (str5.equals(""))
      return;

    String str6 = str2 + "_" + str4 + "_" + str5 + ".dir";

    ETLAgentInterface localETLAgentInterface = new ETLAgentInterface();

    if (localETLAgentInterface.getServerInfo(this.conObj, getDBName(), str3) == 0) {
      String str8;
      if (localETLAgentInterface.connectToAgent() != 0) {
        String str7 = "Unable to connect ETL Agent";

        JOptionPane.showMessageDialog(this, str7, "Error", 0);

        return;
      }

      if (localETLAgentInterface.readHeaderInfo() != 0) {
        localETLAgentInterface.disconnectAgent();
        localETLAgentInterface = null;
        return;
      }

      int i = localETLAgentInterface.sendCmdForce(str6);

      localETLAgentInterface.disconnectAgent();

      if (i == 0) {
        str8 = "Job was forced to start";
        JOptionPane.showMessageDialog(this, str8, "Force Start Job", 1);
      }
      else
      {
        str8 = "Error occured when force starting job";
        JOptionPane.showMessageDialog(this, str8, "Force Start Job", 0);
      }

    }

    localETLAgentInterface = null;
  }

  public void invokeHeadJob()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = (DefaultMutableTreeNode)this.treeView.getTree().getLastSelectedPathComponent();

    if (localDefaultMutableTreeNode1 == null)
      return;

    if (this.treeView.getItemType(localDefaultMutableTreeNode1) != ETLTreeView.ItemJobGroup)
      return;

    DefaultMutableTreeNode localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
    String str1 = this.treeView.getItemName(localDefaultMutableTreeNode1);
    StringBuffer localStringBuffer1 = new StringBuffer();
    StringBuffer localStringBuffer2 = new StringBuffer();

    int i = ETLAgentInterface.getGroupHeadJob(this.conObj, getDBName(), str1, localStringBuffer1, localStringBuffer2);

    if (i != 0)
      return;

    String str2 = ETLAgentInterface.getRunningServer(this.conObj, getDBName(), localStringBuffer1.toString(), localStringBuffer2.toString());

    if (str2.equals("")) {
      str3 = "The running server for the group's head job is not defined.\nUnable to invoke the group's head job.";

      JOptionPane.showMessageDialog(this, str3, "Error", 0);

      return;
    }

    String str3 = ETLAgentInterface.getJobConvFileHead(this.conObj, getDBName(), localStringBuffer1.toString(), localStringBuffer2.toString());

    if (str3.equals("")) {
      localObject = "The convert file head is not defined at job source, can not invoke the group's head job";

      JOptionPane.showMessageDialog(this, localObject, "Error", 0);

      return;
    }

    Object localObject = new ETLJobGivenDateDlg(this, 2);
    ((Dialog)localObject).show();

    String str4 = ((ETLJobGivenDateDlg)localObject).givenDate;
    if (str4.equals(""))
      return;

    String str5 = localStringBuffer1 + "_" + str3 + "_" + str4 + ".dir";

    ETLAgentInterface localETLAgentInterface = new ETLAgentInterface();

    if (localETLAgentInterface.getServerInfo(this.conObj, getDBName(), str2) == 0) {
      String str6;
      if (localETLAgentInterface.connectToAgent() != 0) {
        str6 = "Unable to connect ETL Agent";

        JOptionPane.showMessageDialog(this, str6, "Error", 0);

        return;
      }

      if (localETLAgentInterface.readHeaderInfo() != 0) {
        localETLAgentInterface.disconnectAgent();
        localETLAgentInterface = null;
        return;
      }

      i = localETLAgentInterface.sendCmdInvoke(str5);
      localETLAgentInterface.disconnectAgent();

      if (i == 0)
      {
        str6 = str4.substring(0, 4) + "-" + str4.substring(4, 6) + "-" + str4.substring(6, 8);

        ETLAgentInterface.resetJobToPending(this.conObj, getDBName(), localStringBuffer1.toString(), localStringBuffer2.toString(), str6);

        String str7 = "Invoke group's head job ok";
        JOptionPane.showMessageDialog(this, str7, "Invoke Job", 1);
      }
      else
      {
        str6 = "Error occured when invoke group's head job";
        JOptionPane.showMessageDialog(this, str6, "Invoke Job", 0);
      }

    }

    localETLAgentInterface = null;
  }

  public void forceStartHeadJob()
  {
    DefaultMutableTreeNode localDefaultMutableTreeNode1 = (DefaultMutableTreeNode)this.treeView.getTree().getLastSelectedPathComponent();

    if (localDefaultMutableTreeNode1 == null)
      return;

    if (this.treeView.getItemType(localDefaultMutableTreeNode1) != ETLTreeView.ItemJobGroup)
      return;

    DefaultMutableTreeNode localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localDefaultMutableTreeNode1.getParent();
    String str1 = this.treeView.getItemName(localDefaultMutableTreeNode1);
    StringBuffer localStringBuffer1 = new StringBuffer();
    StringBuffer localStringBuffer2 = new StringBuffer();

    int i = ETLAgentInterface.getGroupHeadJob(this.conObj, getDBName(), str1, localStringBuffer1, localStringBuffer2);

    if (i != 0)
      return;

    String str2 = ETLAgentInterface.getRunningServer(this.conObj, getDBName(), localStringBuffer1.toString(), localStringBuffer2.toString());

    if (str2.equals("")) {
      str3 = "The running server for the group's head job is not defined.\nUnable to force start the group's head job now.";

      JOptionPane.showMessageDialog(this, str3, "Error", 0);

      return;
    }

    String str3 = ETLAgentInterface.getJobConvFileHead(this.conObj, getDBName(), localStringBuffer1.toString(), localStringBuffer2.toString());

    if (str3.equals("")) {
      localObject = "The convert file head is not defined at job source, can not force start the group's head job";

      JOptionPane.showMessageDialog(this, localObject, "Error", 0);

      return;
    }

    Object localObject = new ETLJobGivenDateDlg(this, 3);
    ((Dialog)localObject).show();

    String str4 = ((ETLJobGivenDateDlg)localObject).givenDate;
    if (str4.equals(""))
      return;

    String str5 = localStringBuffer1 + "_" + str3 + "_" + str4 + ".dir";

    ETLAgentInterface localETLAgentInterface = new ETLAgentInterface();

    if (localETLAgentInterface.getServerInfo(this.conObj, getDBName(), str2) == 0) {
      String str6;
      if (localETLAgentInterface.connectToAgent() != 0) {
        str6 = "Unable to connect ETL Agent";

        JOptionPane.showMessageDialog(this, str6, "Error", 0);

        return;
      }

      if (localETLAgentInterface.readHeaderInfo() != 0) {
        localETLAgentInterface.disconnectAgent();
        localETLAgentInterface = null;
        return;
      }

      i = localETLAgentInterface.sendCmdForce(str5);

      localETLAgentInterface.disconnectAgent();

      if (i == 0) {
        str6 = "Group's head job was forced to start";
        JOptionPane.showMessageDialog(this, str6, "Force Start Job", 1);
      }
      else
      {
        str6 = "Error occured when force starting group's head job";
        JOptionPane.showMessageDialog(this, str6, "Force Start Job", 0);
      }

    }

    localETLAgentInterface = null;
  }

  public void queryServiceStatus()
  {
  }

  public void editJobScript()
  {
  }

  public void about()
  {
    ETLAboutDlg localETLAboutDlg = new ETLAboutDlg(this);

    localETLAboutDlg.show();
  }
}