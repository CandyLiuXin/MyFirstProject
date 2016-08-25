import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class ETLMainFrameMenu
{
  private ETLMainFrame mainFrame;
  private JMenuBar jMenuBar;
  private JMenu jMenu_File;
  private JMenu jMenu_Edit;
  private JMenu jMenu_Job;
  private JMenu jMenu_JobGroup;
  private JMenu jMenu_Calendar;
  private JMenu jMenu_Notification;
  private JMenu jMenu_System;
  private JMenu jMenu_LookFeel;
  private JMenu jMenu_Help;
  private JMenuItem jMI_FileConnect;
  private JMenuItem jMI_FileDisconnect;
  private JMenuItem jMI_FileExit;
  private JMenuItem jMI_EditDelete;
  private JMenuItem jMI_EditProperty;
  private JMenuItem jMI_SetFilter;
  private JMenuItem jMI_EditRefresh;
  private JMenuItem jMI_JobAddSys;
  private JMenuItem jMI_JobAddJob;
  private JMenuItem jMI_JobAddSource;
  private JMenuItem jMI_JobAddDependency;
  private JMenuItem jMI_JobAddJobStream;
  private JMenuItem jMI_JobAddJobRelatedJob;
  private JMenuItem jMI_JobViewFileLog;
  private JMenuItem jMI_JobViewJobRecordLog;
  private JMenuItem jMI_JobViewJobDetailLog;
  private JMenuItem jMI_JobViewJobAllStatus;
  private JMenuItem jMI_JobViewAllDepJobStatus;
  private JMenuItem jMI_JobViewAllBelongGroup;
  private JMenuItem jMI_JobWatchDog;
  private JMenuItem jMI_JobErrorDetection;
  private JMenuItem jMI_JobTracer;
  private JMenuItem jMI_JobResetStatus;
  private JMenuItem jMI_JobInvokeJob;
  private JMenuItem jMI_JobForcedStartJob;
  private JMenuItem jMI_JobEditScript;
  private JMenuItem jMI_JobGroupAddGroup;
  private JMenuItem jMI_JobGroupAddGroupChild;
  private JMenuItem jMI_JobGroupResetGroupChild;
  private JMenuItem jMI_JobGroupEnableGroupChild;
  private JMenuItem jMI_JobGroupViewAllChildJob;
  private JMenuItem jMI_JobGroupInvokeHeadJob;
  private JMenuItem jMI_JobGroupForcedStartHeadJob;
  private JMenuItem jMI_CalendarAddDataCalendar;
  private JMenuItem jMI_CalendarEditDataCalendar;
  private JMenuItem jMI_CalendarDeleteDataCalendar;
  private JMenuItem jMI_CalendarSetDataCalendar;
  private JMenuItem jMI_NotificationAddUser;
  private JMenuItem jMI_NotificationAddGroup;
  private JMenuItem jMI_NotificationAddMember;
  private JMenuItem jMI_NotificationAddNotification;
  private JMenuItem jMI_SystemAddServer;
  private JMenuItem jMI_SystemQueryStatus;
  private JRadioButtonMenuItem jMI_LookFeelWindow;
  private JRadioButtonMenuItem jMI_LookFeelMetal;
  private JRadioButtonMenuItem jMI_LookFeelMotif;
  private JMenuItem jMI_HelpAbout;
  private int currentLookFeel = 2;
  private JButton connectButton;
  private JButton disconnectButton;
  private JButton exitButton;
  private JButton addSystemButton;
  private JButton addJobButton;
  private JButton addSourceButton;
  private JButton addDependencyButton;
  private JButton addJobStreamButton;
  private JButton deleteButton;
  private JButton viewFileLogButton;
  private JButton viewJobLogButton;
  private JButton viewJobStatusButton;
  private JButton propertyButton;
  private JButton refreshButton;
  private JButton watchDogButton;
  private JButton aboutButton;
  private ActionListener actionPerform = new ActionListener(this) { private final ETLMainFrameMenu this$0;

    public void actionPerformed() { this.this$0.actionFunc(paramActionEvent);
    }
  };

  public ETLMainFrameMenu(ETLMainFrame paramETLMainFrame, JToolBar paramJToolBar)
  {
    this.mainFrame = paramETLMainFrame;
    initMenu();
    initToolBar(paramJToolBar);
  }

  private void initMenu()
  {
    this.jMenuBar = new JMenuBar();

    this.jMenu_File = new JMenu("File");
    this.jMenuBar.add(this.jMenu_File);

    this.jMenu_Edit = new JMenu("Edit");
    this.jMenuBar.add(this.jMenu_Edit);

    this.jMenu_Job = new JMenu("Job");
    this.jMenuBar.add(this.jMenu_Job);

    this.jMenu_JobGroup = new JMenu("Job Group");
    this.jMenuBar.add(this.jMenu_JobGroup);

    this.jMenu_Calendar = new JMenu("Calendar");
    this.jMenuBar.add(this.jMenu_Calendar);

    this.jMenu_Notification = new JMenu("Notification");
    this.jMenuBar.add(this.jMenu_Notification);

    this.jMenu_System = new JMenu("System Info");
    this.jMenuBar.add(this.jMenu_System);

    this.jMenu_LookFeel = new JMenu("Look & Feel");
    this.jMenuBar.add(this.jMenu_LookFeel);

    this.jMenu_Help = new JMenu("Help");
    this.jMenuBar.add(this.jMenu_Help);

    this.jMI_FileConnect = new JMenuItem("Connect", 67);
    this.jMI_FileConnect.addActionListener(this.actionPerform);

    this.jMI_FileDisconnect = new JMenuItem("Disconnect", 68);
    this.jMI_FileDisconnect.addActionListener(this.actionPerform);

    this.jMI_FileExit = new JMenuItem("Exit", 88);
    this.jMI_FileExit.addActionListener(this.actionPerform);

    this.jMenu_File.add(this.jMI_FileConnect);
    this.jMenu_File.add(this.jMI_FileDisconnect);
    this.jMenu_File.addSeparator();
    this.jMenu_File.add(this.jMI_FileExit);

    this.jMI_EditDelete = new JMenuItem("Delete", 68);
    this.jMI_EditDelete.addActionListener(this.actionPerform);

    this.jMI_EditProperty = new JMenuItem("Property...", 80);
    this.jMI_EditProperty.addActionListener(this.actionPerform);

    this.jMI_EditRefresh = new JMenuItem("Refresh", 80);
    this.jMI_EditRefresh.addActionListener(this.actionPerform);

    this.jMI_SetFilter = new JMenuItem("Filter");
    this.jMI_SetFilter.addActionListener(this.actionPerform);
    this.jMenu_Edit.add(this.jMI_EditDelete);
    this.jMenu_Edit.add(this.jMI_EditProperty);
    this.jMenu_Edit.addSeparator();
    this.jMenu_Edit.add(this.jMI_EditRefresh);

    this.jMenu_Edit.add(this.jMI_SetFilter);

    this.jMI_JobAddSys = new JMenuItem("Add ETL System...");
    this.jMI_JobAddSys.addActionListener(this.actionPerform);

    this.jMI_JobAddJob = new JMenuItem("Add ETL Job...");
    this.jMI_JobAddJob.addActionListener(this.actionPerform);

    this.jMI_JobAddSource = new JMenuItem("Add Job Source...");
    this.jMI_JobAddSource.addActionListener(this.actionPerform);

    this.jMI_JobAddDependency = new JMenuItem("Add Job Dependency...");
    this.jMI_JobAddDependency.addActionListener(this.actionPerform);

    this.jMI_JobAddJobStream = new JMenuItem("Add Job Stream...");
    this.jMI_JobAddJobStream.addActionListener(this.actionPerform);

    this.jMI_JobAddJobRelatedJob = new JMenuItem("Add Related Job...");
    this.jMI_JobAddJobRelatedJob.addActionListener(this.actionPerform);

    this.jMI_JobViewFileLog = new JMenuItem("View Received File Log");
    this.jMI_JobViewFileLog.addActionListener(this.actionPerform);

    this.jMI_JobViewJobRecordLog = new JMenuItem("View Job Record Log");
    this.jMI_JobViewJobRecordLog.addActionListener(this.actionPerform);

    this.jMI_JobViewJobDetailLog = new JMenuItem("View Job Detail Log...");
    this.jMI_JobViewJobDetailLog.addActionListener(this.actionPerform);

    this.jMI_JobViewJobAllStatus = new JMenuItem("View Job Status Log...");
    this.jMI_JobViewJobAllStatus.addActionListener(this.actionPerform);

    this.jMI_JobViewAllDepJobStatus = new JMenuItem("View All Dependent Job Status...");
    this.jMI_JobViewAllDepJobStatus.addActionListener(this.actionPerform);

    this.jMI_JobViewAllBelongGroup = new JMenuItem("View All Belong Group...");
    this.jMI_JobViewAllBelongGroup.addActionListener(this.actionPerform);

    this.jMI_JobWatchDog = new JMenuItem("ETL Job Watch Dog...");
    this.jMI_JobWatchDog.addActionListener(this.actionPerform);

    this.jMI_JobErrorDetection = new JMenuItem("ETL Job Error Detection...");
    this.jMI_JobErrorDetection.addActionListener(this.actionPerform);

    this.jMI_JobTracer = new JMenuItem("ETL Job Tracer...");
    this.jMI_JobTracer.addActionListener(this.actionPerform);

    this.jMI_JobResetStatus = new JMenuItem("Reset Job Status...");
    this.jMI_JobResetStatus.addActionListener(this.actionPerform);

    this.jMI_JobInvokeJob = new JMenuItem("Invoke Job...");
    this.jMI_JobInvokeJob.addActionListener(this.actionPerform);

    this.jMI_JobForcedStartJob = new JMenuItem("Force Start Job Now...");
    this.jMI_JobForcedStartJob.addActionListener(this.actionPerform);

    this.jMI_JobEditScript = new JMenuItem("Edit Job Script...");
    this.jMI_JobEditScript.addActionListener(this.actionPerform);

    this.jMenu_Job.add(this.jMI_JobAddSys);
    this.jMenu_Job.add(this.jMI_JobAddJob);
    this.jMenu_Job.add(this.jMI_JobAddSource);
    this.jMenu_Job.add(this.jMI_JobAddDependency);
    this.jMenu_Job.add(this.jMI_JobAddJobStream);
    this.jMenu_Job.add(this.jMI_JobAddJobRelatedJob);
    this.jMenu_Job.addSeparator();
    this.jMenu_Job.add(this.jMI_JobViewFileLog);
    this.jMenu_Job.add(this.jMI_JobViewJobRecordLog);
    this.jMenu_Job.add(this.jMI_JobViewJobDetailLog);
    this.jMenu_Job.add(this.jMI_JobViewJobAllStatus);
    this.jMenu_Job.add(this.jMI_JobViewAllDepJobStatus);
    this.jMenu_Job.add(this.jMI_JobViewAllBelongGroup);
    this.jMenu_Job.addSeparator();
    this.jMenu_Job.add(this.jMI_JobResetStatus);
    this.jMenu_Job.addSeparator();
    this.jMenu_Job.add(this.jMI_JobInvokeJob);
    this.jMenu_Job.add(this.jMI_JobForcedStartJob);
    this.jMenu_Job.addSeparator();
    this.jMenu_Job.add(this.jMI_JobEditScript);
    this.jMenu_Job.addSeparator();
    this.jMenu_Job.add(this.jMI_JobWatchDog);
    this.jMenu_Job.add(this.jMI_JobErrorDetection);
    this.jMenu_Job.add(this.jMI_JobTracer);

    this.jMI_JobGroupAddGroup = new JMenuItem("Add Group...");
    this.jMI_JobGroupAddGroup.addActionListener(this.actionPerform);
    this.jMI_JobGroupAddGroupChild = new JMenuItem("Add Group Child Job...");
    this.jMI_JobGroupAddGroupChild.addActionListener(this.actionPerform);
    this.jMI_JobGroupResetGroupChild = new JMenuItem("Reset Group Child Jobs");
    this.jMI_JobGroupResetGroupChild.addActionListener(this.actionPerform);
    this.jMI_JobGroupEnableGroupChild = new JMenuItem("Enable Group Child Jobs");
    this.jMI_JobGroupEnableGroupChild.addActionListener(this.actionPerform);
    this.jMI_JobGroupViewAllChildJob = new JMenuItem("View All Child Job Status...");
    this.jMI_JobGroupViewAllChildJob.addActionListener(this.actionPerform);
    this.jMI_JobGroupInvokeHeadJob = new JMenuItem("Invoke Group Head Job...");
    this.jMI_JobGroupInvokeHeadJob.addActionListener(this.actionPerform);
    this.jMI_JobGroupForcedStartHeadJob = new JMenuItem("Force Start Group Head Job...");
    this.jMI_JobGroupForcedStartHeadJob.addActionListener(this.actionPerform);

    this.jMenu_JobGroup.add(this.jMI_JobGroupAddGroup);
    this.jMenu_JobGroup.add(this.jMI_JobGroupAddGroupChild);
    this.jMenu_JobGroup.addSeparator();
    this.jMenu_JobGroup.add(this.jMI_JobGroupResetGroupChild);
    this.jMenu_JobGroup.add(this.jMI_JobGroupEnableGroupChild);
    this.jMenu_JobGroup.addSeparator();
    this.jMenu_JobGroup.add(this.jMI_JobGroupViewAllChildJob);
    this.jMenu_JobGroup.addSeparator();
    this.jMenu_JobGroup.add(this.jMI_JobGroupInvokeHeadJob);
    this.jMenu_JobGroup.add(this.jMI_JobGroupForcedStartHeadJob);

    this.jMI_CalendarAddDataCalendar = new JMenuItem("Add Data Calendar...");
    this.jMI_CalendarAddDataCalendar.addActionListener(this.actionPerform);
    this.jMI_CalendarEditDataCalendar = new JMenuItem("Edit Data Calendar...");
    this.jMI_CalendarEditDataCalendar.addActionListener(this.actionPerform);
    this.jMI_CalendarDeleteDataCalendar = new JMenuItem("Delete Data Calendar");
    this.jMI_CalendarDeleteDataCalendar.addActionListener(this.actionPerform);
    this.jMI_CalendarSetDataCalendar = new JMenuItem("Set Data Calendar...");
    this.jMI_CalendarSetDataCalendar.addActionListener(this.actionPerform);

    this.jMenu_Calendar.add(this.jMI_CalendarAddDataCalendar);
    this.jMenu_Calendar.add(this.jMI_CalendarEditDataCalendar);
    this.jMenu_Calendar.add(this.jMI_CalendarDeleteDataCalendar);
    this.jMenu_Calendar.addSeparator();
    this.jMenu_Calendar.add(this.jMI_CalendarSetDataCalendar);

    this.jMI_NotificationAddUser = new JMenuItem("Add User...", 85);
    this.jMI_NotificationAddUser.addActionListener(this.actionPerform);
    this.jMI_NotificationAddGroup = new JMenuItem("Add User Group...", 85);
    this.jMI_NotificationAddGroup.addActionListener(this.actionPerform);
    this.jMI_NotificationAddMember = new JMenuItem("Add Group Member...", 85);
    this.jMI_NotificationAddMember.addActionListener(this.actionPerform);

    this.jMI_NotificationAddNotification = new JMenuItem("Add Message Notification...", 74);
    this.jMI_NotificationAddNotification.addActionListener(this.actionPerform);

    this.jMenu_Notification.add(this.jMI_NotificationAddUser);
    this.jMenu_Notification.add(this.jMI_NotificationAddGroup);
    this.jMenu_Notification.add(this.jMI_NotificationAddMember);
    this.jMenu_Notification.addSeparator();
    this.jMenu_Notification.add(this.jMI_NotificationAddNotification);

    this.jMI_SystemAddServer = new JMenuItem("Add Automation Server...");
    this.jMI_SystemAddServer.addActionListener(this.actionPerform);

    this.jMI_SystemQueryStatus = new JMenuItem("Query ETL Service Status");
    this.jMI_SystemQueryStatus.addActionListener(this.actionPerform);

    this.jMenu_System.add(this.jMI_SystemAddServer);
    this.jMenu_System.addSeparator();
    this.jMenu_System.add(this.jMI_SystemQueryStatus);

    ButtonGroup localButtonGroup = new ButtonGroup();

    this.jMI_LookFeelWindow = new JRadioButtonMenuItem("Window Style Look & Feel");
    this.jMI_LookFeelWindow.addActionListener(this.actionPerform);
    this.jMI_LookFeelMetal = new JRadioButtonMenuItem("Java Look & Feel");
    this.jMI_LookFeelMetal.addActionListener(this.actionPerform);
    this.jMI_LookFeelMotif = new JRadioButtonMenuItem("Motif Style Look & Feel");
    this.jMI_LookFeelMotif.addActionListener(this.actionPerform);

    this.jMenu_LookFeel.add(this.jMI_LookFeelWindow);
    this.jMenu_LookFeel.add(this.jMI_LookFeelMetal);
    this.jMenu_LookFeel.add(this.jMI_LookFeelMotif);

    localButtonGroup.add(this.jMI_LookFeelWindow);
    localButtonGroup.add(this.jMI_LookFeelMetal);
    localButtonGroup.add(this.jMI_LookFeelMotif);

    this.jMI_LookFeelMetal.setSelected(true);

    this.jMI_HelpAbout = new JMenuItem("About", 65);
    this.jMI_HelpAbout.addActionListener(this.actionPerform);

    this.jMenu_Help.add(this.jMI_HelpAbout);

    this.mainFrame.setJMenuBar(this.jMenuBar);
  }

  private void initToolBar(JToolBar paramJToolBar)
  {
    this.connectButton = new JButton(new ImageIcon(super.getClass().getResource("/images/Connect.gif")));
    this.connectButton.setToolTipText("Connect");
    this.connectButton.addActionListener(this.actionPerform);
    this.connectButton.setMaximumSize(new Dimension(38, 38));

    this.disconnectButton = new JButton(new ImageIcon(super.getClass().getResource("/images/Disconnect.gif")));
    this.disconnectButton.setToolTipText("Disconnect");
    this.disconnectButton.addActionListener(this.actionPerform);
    this.disconnectButton.setMaximumSize(new Dimension(38, 38));

    this.addSystemButton = new JButton(new ImageIcon(super.getClass().getResource("/images/Add System.gif")));
    this.addSystemButton.setToolTipText("Add ETL System");
    this.addSystemButton.addActionListener(this.actionPerform);
    this.addSystemButton.setMaximumSize(new Dimension(38, 38));

    this.addJobButton = new JButton(new ImageIcon(super.getClass().getResource("/images/Add Job.gif")));
    this.addJobButton.setToolTipText("Add ETL Job");
    this.addJobButton.addActionListener(this.actionPerform);
    this.addJobButton.setMaximumSize(new Dimension(38, 38));

    this.addSourceButton = new JButton(new ImageIcon(super.getClass().getResource("/images/Add Source.gif")));
    this.addSourceButton.setToolTipText("Add ETL Source");
    this.addSourceButton.addActionListener(this.actionPerform);
    this.addSourceButton.setMaximumSize(new Dimension(38, 38));

    this.addDependencyButton = new JButton(new ImageIcon(super.getClass().getResource("/images/Dependency.gif")));
    this.addDependencyButton.setToolTipText("Add Job Dependency");
    this.addDependencyButton.addActionListener(this.actionPerform);
    this.addDependencyButton.setMaximumSize(new Dimension(38, 38));

    this.addJobStreamButton = new JButton(new ImageIcon(super.getClass().getResource("/images/Job Stream.gif")));
    this.addJobStreamButton.setToolTipText("Add Job Stream");
    this.addJobStreamButton.addActionListener(this.actionPerform);
    this.addJobStreamButton.setMaximumSize(new Dimension(38, 38));

    this.deleteButton = new JButton(new ImageIcon(super.getClass().getResource("/images/Delete.gif")));
    this.deleteButton.setToolTipText("Delete");
    this.deleteButton.addActionListener(this.actionPerform);
    this.deleteButton.setMaximumSize(new Dimension(38, 38));

    this.propertyButton = new JButton(new ImageIcon(super.getClass().getResource("/images/Property.gif")));
    this.propertyButton.setToolTipText("Property");
    this.propertyButton.addActionListener(this.actionPerform);
    this.propertyButton.setMaximumSize(new Dimension(38, 38));

    this.refreshButton = new JButton(new ImageIcon(super.getClass().getResource("/images/Refresh.gif")));
    this.refreshButton.setToolTipText("Refresh");
    this.refreshButton.addActionListener(this.actionPerform);
    this.refreshButton.setMaximumSize(new Dimension(38, 38));

    this.viewFileLogButton = new JButton(new ImageIcon(super.getClass().getResource("/images/File Log.gif")));
    this.viewFileLogButton.setToolTipText("View Received File Log");
    this.viewFileLogButton.addActionListener(this.actionPerform);
    this.viewFileLogButton.setMaximumSize(new Dimension(38, 38));

    this.viewJobLogButton = new JButton(new ImageIcon(super.getClass().getResource("/images/Job Log.gif")));
    this.viewJobLogButton.setToolTipText("View Job Detail Log");
    this.viewJobLogButton.addActionListener(this.actionPerform);
    this.viewJobLogButton.setMaximumSize(new Dimension(38, 38));

    this.viewJobStatusButton = new JButton(new ImageIcon(super.getClass().getResource("/images/Job Status.gif")));
    this.viewJobStatusButton.setToolTipText("View Job Status Log");
    this.viewJobStatusButton.addActionListener(this.actionPerform);
    this.viewJobStatusButton.setMaximumSize(new Dimension(38, 38));

    this.watchDogButton = new JButton(new ImageIcon(super.getClass().getResource("/images/Watch Dog.gif")));
    this.watchDogButton.setToolTipText("ETL Job Watch Dog");
    this.watchDogButton.addActionListener(this.actionPerform);
    this.watchDogButton.setMaximumSize(new Dimension(38, 38));

    this.aboutButton = new JButton(new ImageIcon(super.getClass().getResource("/images/About.gif")));
    this.aboutButton.setToolTipText("About");
    this.aboutButton.addActionListener(this.actionPerform);
    this.aboutButton.setMaximumSize(new Dimension(38, 38));

    this.exitButton = new JButton(new ImageIcon(super.getClass().getResource("/images/Exit.gif")));
    this.exitButton.setToolTipText("Exit");
    this.exitButton.addActionListener(this.actionPerform);
    this.exitButton.setMaximumSize(new Dimension(38, 38));

    paramJToolBar.add(this.connectButton);
    paramJToolBar.add(this.disconnectButton);
    paramJToolBar.addSeparator();

    paramJToolBar.add(this.deleteButton);
    paramJToolBar.add(this.propertyButton);
    paramJToolBar.addSeparator();
    paramJToolBar.add(this.refreshButton);
    paramJToolBar.addSeparator();

    paramJToolBar.add(this.addSystemButton);
    paramJToolBar.add(this.addJobButton);
    paramJToolBar.add(this.addSourceButton);
    paramJToolBar.add(this.addDependencyButton);
    paramJToolBar.add(this.addJobStreamButton);
    paramJToolBar.addSeparator();

    paramJToolBar.add(this.viewFileLogButton);
    paramJToolBar.add(this.viewJobLogButton);
    paramJToolBar.add(this.viewJobStatusButton);
    paramJToolBar.addSeparator();

    paramJToolBar.add(this.watchDogButton);
    paramJToolBar.addSeparator();

    paramJToolBar.add(this.aboutButton);
    paramJToolBar.addSeparator();

    paramJToolBar.add(this.exitButton);
  }

  public void actionFunc(ActionEvent paramActionEvent)
  {
    Object localObject = paramActionEvent.getSource();

    if ((localObject.equals(this.connectButton)) || (localObject.equals(this.jMI_FileConnect))) {
      SwingUtilities.invokeLater(new Runnable(this) { private final ETLMainFrameMenu this$0;

        public void run() { ETLMainFrameMenu.access$000(this.this$0).connectToDB();
        }
      });
    }
    else if ((localObject.equals(this.disconnectButton)) || (localObject.equals(this.jMI_FileDisconnect))) {
      this.mainFrame.disconnectDB();
    }
    else if ((localObject.equals(this.exitButton)) || (localObject.equals(this.jMI_FileExit))) {
      this.mainFrame.disconnectDB();
      System.exit(0);
    }
    else if ((localObject.equals(this.deleteButton)) || (localObject.equals(this.jMI_EditDelete))) {
      this.mainFrame.delete();
    }
    else if ((localObject.equals(this.propertyButton)) || (localObject.equals(this.jMI_EditProperty))) {
      this.mainFrame.property();
    }
    else if ((localObject.equals(this.refreshButton)) || (localObject.equals(this.jMI_EditRefresh))) {
      this.mainFrame.refresh();
    }
    else if (localObject.equals(this.jMI_SetFilter)) {
      this.mainFrame.SetFilter();
    }
    else if ((localObject.equals(this.addSystemButton)) || (localObject.equals(this.jMI_JobAddSys))) {
      this.mainFrame.addSubsystem();
    }
    else if ((localObject.equals(this.addJobButton)) || (localObject.equals(this.jMI_JobAddJob))) {
      this.mainFrame.addJob();
    }
    else if ((localObject.equals(this.addSourceButton)) || (localObject.equals(this.jMI_JobAddSource))) {
      this.mainFrame.addSource();
    }
    else if ((localObject.equals(this.addDependencyButton)) || (localObject.equals(this.jMI_JobAddDependency))) {
      this.mainFrame.addDependency();
    }
    else if ((localObject.equals(this.addJobStreamButton)) || (localObject.equals(this.jMI_JobAddJobStream))) {
      this.mainFrame.addJobStream();
    }
    else if (localObject.equals(this.jMI_JobAddJobRelatedJob)) {
      this.mainFrame.addJobRelatedJob();
    }
    else if ((localObject.equals(this.viewFileLogButton)) || (localObject.equals(this.jMI_JobViewFileLog))) {
      this.mainFrame.viewFileLog();
    }
    else if (localObject.equals(this.jMI_JobViewJobRecordLog)) {
      this.mainFrame.viewJobRecordLog();
    }
    else if ((localObject.equals(this.viewJobLogButton)) || (localObject.equals(this.jMI_JobViewJobDetailLog))) {
      this.mainFrame.viewJobDetailLog();
    }
    else if ((localObject.equals(this.viewJobStatusButton)) || (localObject.equals(this.jMI_JobViewJobAllStatus))) {
      this.mainFrame.viewJobAllStatus();
    }
    else if (localObject.equals(this.jMI_JobViewAllDepJobStatus)) {
      this.mainFrame.viewAllDepJobStatus();
    }
    else if (localObject.equals(this.jMI_JobViewAllBelongGroup)) {
      this.mainFrame.viewAllBelongGroup();
    }
    else if ((localObject.equals(this.watchDogButton)) || (localObject.equals(this.jMI_JobWatchDog))) {
      this.mainFrame.jobWatchDog();
    }
    else if (localObject.equals(this.jMI_JobErrorDetection)) {
      this.mainFrame.jobErrorDetection();
    }
    else if (localObject.equals(this.jMI_JobTracer)) {
      this.mainFrame.jobTracer();
    }
    else if (localObject.equals(this.jMI_JobResetStatus)) {
      this.mainFrame.resetJobStatus();
    }
    else if (localObject.equals(this.jMI_JobInvokeJob)) {
      this.mainFrame.invokeJob();
    }
    else if (localObject.equals(this.jMI_JobForcedStartJob)) {
      this.mainFrame.forceStartJob();
    }
    else if (localObject.equals(this.jMI_JobEditScript)) {
      this.mainFrame.editJobScript();
    }
    else if (localObject.equals(this.jMI_JobGroupAddGroup)) {
      this.mainFrame.addJobGroup();
    }
    else if (localObject.equals(this.jMI_JobGroupAddGroupChild)) {
      this.mainFrame.addJobGroupChild();
    }
    else if (localObject.equals(this.jMI_JobGroupResetGroupChild)) {
      this.mainFrame.resetJobGroupChild();
    }
    else if (localObject.equals(this.jMI_JobGroupEnableGroupChild)) {
      this.mainFrame.enableJobGroupChild();
    }
    else if (localObject.equals(this.jMI_JobGroupViewAllChildJob)) {
      this.mainFrame.viewAllChildJobStatus();
    }
    else if (localObject.equals(this.jMI_JobGroupInvokeHeadJob)) {
      this.mainFrame.invokeHeadJob();
    }
    else if (localObject.equals(this.jMI_JobGroupForcedStartHeadJob)) {
      this.mainFrame.forceStartHeadJob();
    }
    else if (localObject.equals(this.jMI_CalendarAddDataCalendar)) {
      this.mainFrame.addDataCalendar();
    }
    else if (localObject.equals(this.jMI_CalendarEditDataCalendar)) {
      this.mainFrame.editDataCalendar();
    }
    else if (localObject.equals(this.jMI_CalendarDeleteDataCalendar)) {
      this.mainFrame.deleteDataCalendar();
    }
    else if (localObject.equals(this.jMI_CalendarSetDataCalendar)) {
      this.mainFrame.setDataCalendar();
    }
    else if (localObject.equals(this.jMI_NotificationAddUser)) {
      this.mainFrame.addUser();
    }
    else if (localObject.equals(this.jMI_NotificationAddGroup)) {
      this.mainFrame.addUserGroup();
    }
    else if (localObject.equals(this.jMI_NotificationAddMember)) {
      this.mainFrame.addGroupMember();
    }
    else if (localObject.equals(this.jMI_NotificationAddNotification)) {
      this.mainFrame.addNotifyJob();
    }
    else if (localObject.equals(this.jMI_SystemAddServer)) {
      this.mainFrame.addServerInfo();
    }
    else if (localObject.equals(this.jMI_SystemQueryStatus)) {
      this.mainFrame.queryServiceStatus();
    }
    else if (localObject.equals(this.jMI_LookFeelWindow)) {
      jMI_LookFeelWindow_actionPerformed(paramActionEvent);
    }
    else if (localObject.equals(this.jMI_LookFeelMetal)) {
      jMI_LookFeelMetal_actionPerformed(paramActionEvent);
    }
    else if (localObject.equals(this.jMI_LookFeelMotif)) {
      jMI_LookFeelMotif_actionPerformed(paramActionEvent);
    }
    else if ((localObject.equals(this.aboutButton)) || (localObject.equals(this.jMI_HelpAbout))) {
      this.mainFrame.about();
    }
  }

  public void jMI_LookFeelWindow_actionPerformed(ActionEvent paramActionEvent)
  {
    if (this.currentLookFeel == 1)
      return;

    this.currentLookFeel = 1;

    String str = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    try
    {
      UIManager.setLookAndFeel(str);
      SwingUtilities.updateComponentTreeUI(this.mainFrame);
    }
    catch (Exception localException)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Could not load Windows LookAndFeel", "Error", 0);
    }
  }

  public void jMI_LookFeelMetal_actionPerformed(ActionEvent paramActionEvent)
  {
    if (this.currentLookFeel == 2)
      return;

    this.currentLookFeel = 2;

    String str = "javax.swing.plaf.metal.MetalLookAndFeel";
    try
    {
      UIManager.setLookAndFeel(str);
      SwingUtilities.updateComponentTreeUI(this.mainFrame);
    }
    catch (Exception localException)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Could not load Metal LookAndFeel", "Error", 0);
    }
  }

  public void jMI_LookFeelMotif_actionPerformed(ActionEvent paramActionEvent)
  {
    if (this.currentLookFeel == 3)
      return;

    this.currentLookFeel = 3;

    String str = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
    try
    {
      UIManager.setLookAndFeel(str);
      SwingUtilities.updateComponentTreeUI(this.mainFrame);
    }
    catch (Exception localException)
    {
      JOptionPane.showMessageDialog(this.mainFrame, "Could not load Motif LookAndFeel", "Error", 0);
    }
  }

  private void disableAllMenuItem()
  {
    this.jMI_FileConnect.setEnabled(false);
    this.jMI_FileDisconnect.setEnabled(false);

    this.jMI_EditDelete.setEnabled(false);
    this.jMI_EditProperty.setEnabled(false);
    this.jMI_EditRefresh.setEnabled(false);
    this.jMI_SetFilter.setEnabled(false);
    this.jMI_JobAddSys.setEnabled(false);
    this.jMI_JobAddJob.setEnabled(false);
    this.jMI_JobAddSource.setEnabled(false);
    this.jMI_JobAddDependency.setEnabled(false);
    this.jMI_JobAddJobStream.setEnabled(false);
    this.jMI_JobAddJobRelatedJob.setEnabled(false);
    this.jMI_JobViewFileLog.setEnabled(false);
    this.jMI_JobViewJobRecordLog.setEnabled(false);
    this.jMI_JobViewJobDetailLog.setEnabled(false);
    this.jMI_JobViewJobAllStatus.setEnabled(false);
    this.jMI_JobViewAllDepJobStatus.setEnabled(false);
    this.jMI_JobViewAllBelongGroup.setEnabled(false);
    this.jMI_JobWatchDog.setEnabled(false);
    this.jMI_JobErrorDetection.setEnabled(false);
    this.jMI_JobTracer.setEnabled(false);
    this.jMI_JobResetStatus.setEnabled(false);
    this.jMI_JobInvokeJob.setEnabled(false);
    this.jMI_JobForcedStartJob.setEnabled(false);
    this.jMI_JobEditScript.setEnabled(false);

    this.jMI_JobGroupAddGroup.setEnabled(false);
    this.jMI_JobGroupAddGroupChild.setEnabled(false);
    this.jMI_JobGroupResetGroupChild.setEnabled(false);
    this.jMI_JobGroupEnableGroupChild.setEnabled(false);
    this.jMI_JobGroupViewAllChildJob.setEnabled(false);
    this.jMI_JobGroupInvokeHeadJob.setEnabled(false);
    this.jMI_JobGroupForcedStartHeadJob.setEnabled(false);

    this.jMI_CalendarAddDataCalendar.setEnabled(false);
    this.jMI_CalendarEditDataCalendar.setEnabled(false);
    this.jMI_CalendarDeleteDataCalendar.setEnabled(false);
    this.jMI_CalendarSetDataCalendar.setEnabled(false);

    this.jMI_NotificationAddUser.setEnabled(false);
    this.jMI_NotificationAddGroup.setEnabled(false);
    this.jMI_NotificationAddMember.setEnabled(false);
    this.jMI_NotificationAddNotification.setEnabled(false);

    this.jMI_SystemAddServer.setEnabled(false);
    this.jMI_SystemQueryStatus.setEnabled(false);

    this.deleteButton.setEnabled(false);
    this.propertyButton.setEnabled(false);
    this.refreshButton.setEnabled(false);

    this.connectButton.setEnabled(false);
    this.disconnectButton.setEnabled(false);
    this.addSystemButton.setEnabled(false);
    this.addJobButton.setEnabled(false);
    this.addSourceButton.setEnabled(false);
    this.addDependencyButton.setEnabled(false);
    this.addJobStreamButton.setEnabled(false);
    this.viewFileLogButton.setEnabled(false);
    this.viewJobLogButton.setEnabled(false);
    this.viewJobStatusButton.setEnabled(false);
    this.watchDogButton.setEnabled(false);
  }

  public void setConnect()
  {
    disableAllMenuItem();
    this.jMI_FileDisconnect.setEnabled(true);
    this.jMI_SetFilter.setEnabled(true);
    this.jMI_JobWatchDog.setEnabled(true);
    this.jMI_JobErrorDetection.setEnabled(true);
    this.jMI_JobTracer.setEnabled(true);
    this.disconnectButton.setEnabled(true);
    this.watchDogButton.setEnabled(true);
  }

  public void setDisconnect()
  {
    disableAllMenuItem();
    this.jMI_FileConnect.setEnabled(true);
    this.connectButton.setEnabled(true);
  }

  public void setETLSystem()
  {
    setConnect();

    this.jMI_EditRefresh.setEnabled(true);
    this.jMI_JobAddSys.setEnabled(true);

    this.refreshButton.setEnabled(true);
    this.addSystemButton.setEnabled(true);
  }

  public void setJobGroupFolder()
  {
    setConnect();

    this.jMI_EditRefresh.setEnabled(true);
    this.jMI_JobGroupAddGroup.setEnabled(true);

    this.refreshButton.setEnabled(true);
  }

  public void setSubsystem()
  {
    setConnect();

    this.jMI_EditDelete.setEnabled(true);
    this.jMI_EditProperty.setEnabled(true);
    this.jMI_EditRefresh.setEnabled(true);

    this.jMI_JobAddJob.setEnabled(true);

    this.deleteButton.setEnabled(true);
    this.propertyButton.setEnabled(true);
    this.refreshButton.setEnabled(true);

    this.addJobButton.setEnabled(true);
  }

  public void setETLJob()
  {
    setConnect();

    this.jMI_EditDelete.setEnabled(true);
    this.jMI_EditProperty.setEnabled(true);
    this.jMI_EditRefresh.setEnabled(true);

    this.jMI_JobAddSource.setEnabled(true);
    this.jMI_JobAddDependency.setEnabled(true);
    this.jMI_JobAddJobStream.setEnabled(true);
    this.jMI_JobAddJobRelatedJob.setEnabled(true);
    this.jMI_JobViewFileLog.setEnabled(true);
    this.jMI_JobViewJobRecordLog.setEnabled(true);
    this.jMI_JobViewJobDetailLog.setEnabled(true);
    this.jMI_JobViewJobAllStatus.setEnabled(true);
    this.jMI_JobViewAllBelongGroup.setEnabled(true);
    this.jMI_JobResetStatus.setEnabled(true);
    this.jMI_JobInvokeJob.setEnabled(true);
    this.jMI_JobForcedStartJob.setEnabled(true);
    this.jMI_JobEditScript.setEnabled(true);

    this.jMI_CalendarAddDataCalendar.setEnabled(true);

    this.jMI_NotificationAddNotification.setEnabled(true);

    this.deleteButton.setEnabled(true);
    this.propertyButton.setEnabled(true);
    this.refreshButton.setEnabled(true);

    this.addSourceButton.setEnabled(true);
    this.addDependencyButton.setEnabled(true);
    this.addJobStreamButton.setEnabled(true);
    this.viewFileLogButton.setEnabled(true);
    this.viewJobLogButton.setEnabled(true);
    this.viewJobStatusButton.setEnabled(true);
  }

  public void setJobSource()
  {
    setConnect();

    this.jMI_EditDelete.setEnabled(true);
    this.jMI_EditProperty.setEnabled(true);
    this.jMI_EditRefresh.setEnabled(true);
    this.deleteButton.setEnabled(true);
    this.propertyButton.setEnabled(true);
    this.refreshButton.setEnabled(true);
  }

  public void setJobDependencyFolder()
  {
    setConnect();

    this.jMI_JobAddDependency.setEnabled(true);
    this.jMI_JobViewAllDepJobStatus.setEnabled(true);
    this.addDependencyButton.setEnabled(true);
  }

  public void setJobStreamFolder()
  {
    setConnect();

    this.jMI_JobAddJobStream.setEnabled(true);
    this.addJobStreamButton.setEnabled(true);
  }

  public void setJobRelatedJobFolder()
  {
    setConnect();

    this.jMI_EditRefresh.setEnabled(true);
    this.jMI_JobAddJobRelatedJob.setEnabled(true);
    this.refreshButton.setEnabled(true);
    this.addJobStreamButton.setEnabled(true);
  }

  public void setJobDependency()
  {
    setConnect();

    this.jMI_JobResetStatus.setEnabled(true);

    this.jMI_EditDelete.setEnabled(true);
    this.jMI_EditProperty.setEnabled(true);
    this.jMI_JobViewJobDetailLog.setEnabled(true);
    this.jMI_JobViewJobAllStatus.setEnabled(true);
    this.deleteButton.setEnabled(true);
    this.propertyButton.setEnabled(true);
    this.viewJobLogButton.setEnabled(true);
    this.viewJobStatusButton.setEnabled(true);
  }

  public void setNextLevelJobDependency()
  {
    setConnect();

    this.jMI_JobResetStatus.setEnabled(true);

    this.jMI_JobViewJobDetailLog.setEnabled(true);
    this.jMI_JobViewJobAllStatus.setEnabled(true);
    this.viewJobLogButton.setEnabled(true);
    this.viewJobStatusButton.setEnabled(true);
  }

  public void setJobStream()
  {
    setConnect();

    this.jMI_EditDelete.setEnabled(true);
    this.jMI_EditProperty.setEnabled(true);
    this.jMI_JobViewJobDetailLog.setEnabled(true);
    this.jMI_JobViewJobAllStatus.setEnabled(true);
    this.deleteButton.setEnabled(true);
    this.propertyButton.setEnabled(true);
    this.viewJobLogButton.setEnabled(true);
    this.viewJobStatusButton.setEnabled(true);
  }

  public void setNextLevelJobStream()
  {
    setConnect();

    this.jMI_JobResetStatus.setEnabled(true);

    this.jMI_JobViewJobDetailLog.setEnabled(true);
    this.jMI_JobViewJobAllStatus.setEnabled(true);
    this.viewJobLogButton.setEnabled(true);
    this.viewJobStatusButton.setEnabled(true);
  }

  public void setConditionJob()
  {
    setConnect();

    this.jMI_JobResetStatus.setEnabled(true);

    this.jMI_JobViewJobDetailLog.setEnabled(true);
    this.jMI_JobViewJobAllStatus.setEnabled(true);
    this.viewJobLogButton.setEnabled(true);
    this.viewJobStatusButton.setEnabled(true);
  }

  public void setRelatedJob()
  {
    setConnect();

    this.jMI_EditDelete.setEnabled(true);
    this.jMI_EditProperty.setEnabled(true);
    this.jMI_JobViewJobDetailLog.setEnabled(true);
    this.jMI_JobViewJobAllStatus.setEnabled(true);
    this.deleteButton.setEnabled(true);
    this.propertyButton.setEnabled(true);
    this.viewJobLogButton.setEnabled(true);
    this.viewJobStatusButton.setEnabled(true);
  }

  public void setJobGroup()
  {
    setConnect();

    this.jMI_EditDelete.setEnabled(true);
    this.jMI_EditProperty.setEnabled(true);
    this.jMI_EditRefresh.setEnabled(true);
    this.jMI_JobGroupAddGroupChild.setEnabled(true);
    this.jMI_JobGroupResetGroupChild.setEnabled(true);
    this.jMI_JobGroupEnableGroupChild.setEnabled(true);
    this.jMI_JobGroupViewAllChildJob.setEnabled(true);
    this.jMI_JobGroupInvokeHeadJob.setEnabled(true);
    this.jMI_JobGroupForcedStartHeadJob.setEnabled(true);

    this.deleteButton.setEnabled(true);
    this.propertyButton.setEnabled(true);
    this.refreshButton.setEnabled(true);
  }

  public void setJobGroup1()
  {
    setConnect();
  }

  public void setJobGroupChild()
  {
    setConnect();

    this.jMI_EditDelete.setEnabled(true);
    this.jMI_EditProperty.setEnabled(true);
    this.jMI_JobViewJobDetailLog.setEnabled(true);
    this.jMI_JobViewJobAllStatus.setEnabled(true);
    this.deleteButton.setEnabled(true);
    this.propertyButton.setEnabled(true);
    this.viewJobLogButton.setEnabled(true);
    this.viewJobStatusButton.setEnabled(true);
  }

  public void setJobGroupChild1()
  {
    setConnect();

    this.jMI_JobResetStatus.setEnabled(true);

    this.jMI_JobViewJobDetailLog.setEnabled(true);
    this.jMI_JobViewJobAllStatus.setEnabled(true);
    this.viewJobLogButton.setEnabled(true);
    this.viewJobStatusButton.setEnabled(true);
  }

  public void setCalendarYear()
  {
    setConnect();

    this.jMI_CalendarEditDataCalendar.setEnabled(true);
    this.jMI_CalendarDeleteDataCalendar.setEnabled(true);
    this.jMI_CalendarSetDataCalendar.setEnabled(true);
  }

  public void setNotifyJobFolder()
  {
    setConnect();

    this.jMI_NotificationAddNotification.setEnabled(true);
  }

  public void setNotificationFolder()
  {
    setConnect();
  }

  public void setNotifyUserFolder()
  {
    setConnect();

    this.jMI_EditRefresh.setEnabled(true);
    this.jMI_NotificationAddUser.setEnabled(true);
    this.refreshButton.setEnabled(true);
  }

  public void setNotifyUserGroupFolder()
  {
    setConnect();

    this.jMI_EditRefresh.setEnabled(true);
    this.jMI_NotificationAddGroup.setEnabled(true);
    this.refreshButton.setEnabled(true);
  }

  public void setNotifyUser()
  {
    setConnect();

    this.jMI_EditDelete.setEnabled(true);
    this.jMI_EditProperty.setEnabled(true);

    this.deleteButton.setEnabled(true);
    this.propertyButton.setEnabled(true);
  }

  public void setNotifyUserGroup()
  {
    setConnect();

    this.jMI_EditDelete.setEnabled(true);
    this.jMI_EditProperty.setEnabled(true);
    this.jMI_NotificationAddMember.setEnabled(true);

    this.deleteButton.setEnabled(true);
    this.propertyButton.setEnabled(true);
  }

  public void setNotifyGroupMember()
  {
    setConnect();

    this.jMI_EditDelete.setEnabled(true);
    this.jMI_EditProperty.setEnabled(true);

    this.deleteButton.setEnabled(true);
    this.propertyButton.setEnabled(true);
  }

  public void setSystemInfoFolder()
  {
    setConnect();

    this.jMI_EditRefresh.setEnabled(true);
    this.jMI_SystemAddServer.setEnabled(true);

    this.refreshButton.setEnabled(true);
  }

  public void setSystemInfoServer()
  {
    setConnect();

    this.jMI_EditDelete.setEnabled(true);
    this.jMI_EditProperty.setEnabled(true);
    this.jMI_SystemQueryStatus.setEnabled(true);

    this.deleteButton.setEnabled(true);
    this.propertyButton.setEnabled(true);
  }

  static ETLMainFrame access$000(ETLMainFrameMenu paramETLMainFrameMenu)
  {
    return paramETLMainFrameMenu.mainFrame;
  }
}