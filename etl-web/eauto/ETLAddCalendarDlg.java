import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;

public class ETLAddCalendarDlg extends JDialog
{
  private ETLAddCalendarDlg dlg;
  private DefaultMutableTreeNode parentNode;
  private Connection dbcon;
  private String etlsys;
  private String etljob;
  private int currentYear;
  private int selectYear;
  private int mode = 0;
  private ETLMainFrame mainFrame;
  private YearSelectPad yearSelectPad;
  private JButton jBnUpdate;
  private JButton jBnCancel;
  private JButton jBnLastYear;
  private JButton jBnNextYear;
  private JButton jBnCopyTo;
  private JLabel labelBU;
  private InputJTextField fieldBU;

  public ETLAddCalendarDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "Add Data Calendar", true);
    setResizable(false);
    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLAddCalendarDlg.1 local1 = new WindowAdapter(this) { private final ETLAddCalendarDlg this$0;

      public void windowClosing() { this.this$0.dispose();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(null);

    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    this.currentYear = localGregorianCalendar.get(1);
    this.selectYear = this.currentYear;

    this.yearSelectPad = new YearSelectPad(this.selectYear, new Color(255, 255, 55));
    this.yearSelectPad.setBounds(new Rectangle(0, 0, 720, 500));
    localContainer.add(this.yearSelectPad);

    this.jBnUpdate = new JButton("Update");
    this.jBnCancel = new JButton("Cancel");

    this.jBnLastYear = new JButton("Last Year");
    this.jBnNextYear = new JButton("Next Year");
    this.jBnCopyTo = new JButton("Copy To BU");

    this.labelBU = new JLabel("Calendar BU:");
    this.fieldBU = new InputJTextField(InputJTextField.UPPERCASE);
    this.fieldBU.setInputLimited(15);

    this.jBnUpdate.setMnemonic(85);
    this.jBnCancel.setMnemonic(67);

    this.jBnUpdate.addActionListener(new ActionListener(this) { private final ETLAddCalendarDlg this$0;

      public void actionPerformed() { ETLAddCalendarDlg.access$100(ETLAddCalendarDlg.access$000(this.this$0));
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLAddCalendarDlg this$0;

      public void actionPerformed() { ETLAddCalendarDlg.access$000(this.this$0).dispose();
      }

    });
    this.jBnLastYear.addActionListener(new ActionListener(this) { private final ETLAddCalendarDlg this$0;

      public void actionPerformed() { ETLAddCalendarDlg.access$200(ETLAddCalendarDlg.access$000(this.this$0));
      }

    });
    this.jBnNextYear.addActionListener(new ActionListener(this) { private final ETLAddCalendarDlg this$0;

      public void actionPerformed() { ETLAddCalendarDlg.access$300(ETLAddCalendarDlg.access$000(this.this$0));
      }

    });
    this.jBnCopyTo.addActionListener(new ActionListener(this) { private final ETLAddCalendarDlg this$0;

      public void actionPerformed() { ETLAddCalendarDlg.access$400(ETLAddCalendarDlg.access$000(this.this$0));
      }

    });
    this.jBnUpdate.setBounds(new Rectangle(750, 20, 100, 30));
    this.jBnCancel.setBounds(new Rectangle(750, 70, 100, 30));

    this.jBnLastYear.setBounds(new Rectangle(750, 170, 100, 30));
    this.jBnNextYear.setBounds(new Rectangle(750, 220, 100, 30));

    this.labelBU.setBounds(new Rectangle(750, 325, 100, 25));
    this.fieldBU.setBounds(new Rectangle(750, 350, 100, 25));
    this.jBnCopyTo.setBounds(new Rectangle(750, 380, 100, 30));

    localContainer.add(this.jBnUpdate);
    localContainer.add(this.jBnCancel);
    localContainer.add(this.jBnLastYear);
    localContainer.add(this.jBnNextYear);
    localContainer.add(this.labelBU);
    localContainer.add(this.fieldBU);
    localContainer.add(this.jBnCopyTo);

    setSize(new Dimension(870, 550));

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
    this.etljob = paramString2;

    show();
  }

  private void update()
  {
    String str = "Do you want to add this calendar year into database?";
    int i = JOptionPane.showConfirmDialog(this, str, "Update", 0, 3);

    if (i != 0)
      return;

    if (addDataCalendar(this.etlsys, this.etljob, this.selectYear) != 0)
      return;

    this.mainFrame.getTreeView().addCalendarYear(this.parentNode, this.selectYear);

    dispose();
  }

  private int addDataCalendar(String paramString1, String paramString2, int paramInt)
  {
    String str2;
    int i;
    String str1 = this.mainFrame.getDBName();

    Cursor localCursor = getCursor();
    setCursor(new Cursor(3));
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      str2 = "INSERT INTO " + str1 + "DataCalendarYear" + "            (etl_system, etl_job, calendaryear)" + "   VALUES ('" + paramString1 + "', '" + paramString2 + "', " + paramInt + ")";

      i = localStatement.executeUpdate(str2);
    }
    catch (SQLException localSQLException1)
    {
      setCursor(localCursor);

      JOptionPane.showMessageDialog(this, "Could not insert a data calendar year into database\n" + localSQLException1.getMessage(), "Error", 0);

      return -1;
    }

    try
    {
      str2 = "INSERT INTO " + str1 + "DataCalendar" + "            (etl_system, etl_job, calendaryear," + "             seqnum, calendarmonth, calendarday, checkflag)" + "   VALUES ('" + paramString1 + "', '" + paramString2 + "', " + paramInt + ", ?, ?, ?, 'N')";

      PreparedStatement localPreparedStatement = this.dbcon.prepareStatement(str2);

      i = 1;
      for (int j = 1; j <= 12; ++j)
      {
        int[] arrayOfInt = this.yearSelectPad.getMonthSelectedDay(j);

        for (int k = 0; k < arrayOfInt.length; ++k) {
          localPreparedStatement.setInt(1, i++);
          localPreparedStatement.setInt(2, j);
          localPreparedStatement.setInt(3, arrayOfInt[k]);

          int l = localPreparedStatement.executeUpdate();
        }
      }
    }
    catch (SQLException localSQLException2)
    {
      setCursor(localCursor);

      JOptionPane.showMessageDialog(this, "Could not insert a data calendar date into database\n" + localSQLException2.getMessage(), "Error", 0);

      return -1;
    }

    setCursor(localCursor);

    return 0;
  }

  private void copyToBU()
  {
    Statement localStatement;
    ResultSet localResultSet;
    String str5;
    String str6;
    String str1 = this.fieldBU.getText().trim();

    if (str1.equals("")) {
      JOptionPane.showMessageDialog(this, "Please specify the BU first!", "Warning", 2);

      return;
    }

    String str2 = "This action will override other job's data calendar in the same BU\nDo you want to copy this calendar year to other jobs in BU " + str1 + "?";

    int i = JOptionPane.showConfirmDialog(this, str2, "Copy To BU", 0, 3);

    if (i != 0) {
      return;
    }

    String str3 = this.mainFrame.getDBName();

    String str4 = "";
    try
    {
      localStatement = this.dbcon.createStatement();
      localResultSet = localStatement.executeQuery("SELECT jobtype    FROM " + str3 + "etl_job" + "   WHERE etl_system = '" + this.etlsys + "'" + "     AND etl_job = '" + this.etljob + "'");

      while (localResultSet.next())
      {
        str4 = localResultSet.getString(1);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException1)
    {
      str5 = "Could not retrieve job type information for\n" + localSQLException1.getMessage();

      JOptionPane.showMessageDialog(this, str5, "Error", 0);

      return;
    }

    ArrayList localArrayList = new ArrayList();
    try
    {
      localStatement = this.dbcon.createStatement();
      localResultSet = localStatement.executeQuery("SELECT etl_system, etl_job    FROM " + str3 + "etl_job" + "   WHERE calendarBU = '" + str1 + "'" + "     AND jobtype = '" + str4 + "'" + "   ORDER BY etl_system, etl_job");

      while (localResultSet.next())
      {
        str5 = localResultSet.getString(1);
        str6 = localResultSet.getString(2);

        if ((str5.equals(this.etlsys)) && (str6.equals(this.etljob)))
          continue;

        localArrayList.add(str5);
        localArrayList.add(str6);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      str6 = "Could not retrieve job information for BU " + str1 + "\n" + localSQLException2.getMessage();

      JOptionPane.showMessageDialog(this, str6, "Error", 0);

      return;
    }

    int j = localArrayList.size();

    for (int k = 0; k < j; k += 2) {
      String str7 = (String)localArrayList.get(k);
      String str8 = (String)localArrayList.get(k + 1);

      if (deleteDataCalendar(str7, str8, this.selectYear) != 0)
        break;

      if (addDataCalendar(str7, str8, this.selectYear) != 0)
        break;
    }

    JOptionPane.showMessageDialog(this, "Copy data calendar is done", "Copy BU", -1);
  }

  private int deleteDataCalendar(String paramString1, String paramString2, int paramInt)
  {
    String str2;
    int i;
    String str1 = this.mainFrame.getDBName();

    Cursor localCursor = getCursor();
    setCursor(new Cursor(3));
    try
    {
      Statement localStatement1 = this.dbcon.createStatement();
      str2 = "DELETE FROM " + str1 + "DataCalendar" + "   WHERE etl_system = '" + paramString1 + "'" + "     AND etl_job = '" + paramString2 + "'" + "     AND calendaryear = " + paramInt;

      i = localStatement1.executeUpdate(str2);
    }
    catch (SQLException localSQLException1)
    {
      setCursor(localCursor);

      JOptionPane.showMessageDialog(this, "Could not remove a data calendar from database\n" + localSQLException1.getMessage(), "Error", 0);

      return -1;
    }

    try
    {
      Statement localStatement2 = this.dbcon.createStatement();
      str2 = "DELETE FROM " + str1 + "DataCalendarYear" + "   WHERE etl_system = '" + paramString1 + "'" + "     AND etl_job = '" + paramString2 + "'" + "     AND calendaryear = " + paramInt;

      i = localStatement2.executeUpdate(str2);
    }
    catch (SQLException localSQLException2)
    {
      setCursor(localCursor);

      JOptionPane.showMessageDialog(this, "Could not remove a data calendar year from database\n" + localSQLException2.getMessage(), "Error", 0);

      return -1;
    }

    setCursor(localCursor);

    return 0;
  }

  private void lastYear()
  {
    if (this.selectYear == this.currentYear - 1)
      return;

    this.selectYear -= 1;
    this.yearSelectPad.setVisible(false);
    this.yearSelectPad.init(this.selectYear);
    this.yearSelectPad.setVisible(true);

    if (this.selectYear == this.currentYear - 1)
      this.jBnLastYear.setEnabled(false);
  }

  private void nextYear()
  {
    this.selectYear += 1;
    this.yearSelectPad.setVisible(false);
    this.yearSelectPad.init(this.selectYear);
    this.yearSelectPad.setVisible(true);

    this.jBnLastYear.setEnabled(true);
  }

  static ETLAddCalendarDlg access$000(ETLAddCalendarDlg paramETLAddCalendarDlg)
  {
    return paramETLAddCalendarDlg.dlg; } 
  static void access$100(ETLAddCalendarDlg paramETLAddCalendarDlg) { paramETLAddCalendarDlg.update(); } 
  static void access$200(ETLAddCalendarDlg paramETLAddCalendarDlg) { paramETLAddCalendarDlg.lastYear(); } 
  static void access$300(ETLAddCalendarDlg paramETLAddCalendarDlg) { paramETLAddCalendarDlg.nextYear(); } 
  static void access$400(ETLAddCalendarDlg paramETLAddCalendarDlg) { paramETLAddCalendarDlg.copyToBU();
  }
}