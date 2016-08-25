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
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

public class ETLEditCalendarDlg extends JDialog
{
  private ETLEditCalendarDlg dlg;
  private DefaultMutableTreeNode parentNode;
  private Connection dbcon;
  private String etlsys = "";
  private String etljob = "";
  private int selectYear;
  private ETLMainFrame mainFrame;
  private YearSelectPad yearSelectPad;
  private int[][] monthSetFlag;
  private JButton jBnUpdate;
  private JButton jBnCancel;

  public ETLEditCalendarDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "Edit Data Calendar", true);
    setResizable(false);
    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLEditCalendarDlg.1 local1 = new WindowAdapter(this) { private final ETLEditCalendarDlg this$0;

      public void windowClosing() { this.this$0.dispose();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(null);

    this.yearSelectPad = new YearSelectPad(new Color(255, 255, 55));
    this.yearSelectPad.setBounds(new Rectangle(0, 0, 720, 500));
    localContainer.add(this.yearSelectPad);

    this.jBnUpdate = new JButton("Update");
    this.jBnCancel = new JButton("Cancel");

    this.jBnUpdate.setMnemonic(85);
    this.jBnCancel.setMnemonic(67);

    this.jBnUpdate.addActionListener(new ActionListener(this) { private final ETLEditCalendarDlg this$0;

      public void actionPerformed() { ETLEditCalendarDlg.access$100(ETLEditCalendarDlg.access$000(this.this$0));
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLEditCalendarDlg this$0;

      public void actionPerformed() { ETLEditCalendarDlg.access$000(this.this$0).dispose();
      }

    });
    this.jBnUpdate.setBounds(new Rectangle(750, 20, 100, 30));
    this.jBnCancel.setBounds(new Rectangle(750, 70, 100, 30));

    localContainer.add(this.jBnUpdate);
    localContainer.add(this.jBnCancel);

    setSize(new Dimension(870, 550));

    Dimension localDimension1 = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension localDimension2 = getSize();

    if (localDimension2.height > localDimension1.height)
      localDimension2.height = localDimension1.height;

    if (localDimension2.width > localDimension1.width)
      localDimension2.width = localDimension1.width;

    setLocation((localDimension1.width - localDimension2.width) / 2, (localDimension1.height - localDimension2.height) / 2);
  }

  public void edit(Connection paramConnection, String paramString1, String paramString2, int paramInt)
  {
    this.dbcon = paramConnection;
    this.etlsys = paramString1;
    this.etljob = paramString2;

    this.yearSelectPad.init(paramInt);
    this.selectYear = paramInt;

    this.monthSetFlag = new int[12][];

    for (int i = 1; i <= 12; ++i) {
      int[] arrayOfInt = getMonthSelectedDay(this.selectYear, i);
      this.yearSelectPad.setMonthSelectedDay(i, arrayOfInt);

      this.monthSetFlag[(i - 1)] = getMonthSetFlagDay(this.selectYear, i);
    }

    show();
  }

  private int[] getMonthSelectedDay(int paramInt1, int paramInt2)
  {
    String str1 = this.mainFrame.getDBName();

    int[] arrayOfInt1 = new int[31];
    int i = 0;
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      ResultSet localResultSet = localStatement.executeQuery("SELECT calendarday    FROM " + str1 + "datacalendar" + "   WHERE etl_system = '" + this.etlsys + "'" + "     AND etl_job = '" + this.etljob + "'" + "     AND calendaryear = " + paramInt1 + "     AND calendarmonth = " + paramInt2 + "   ORDER BY calendarday");

      while (localResultSet.next())
      {
        int j = localResultSet.getInt(1);
        arrayOfInt1[(i++)] = j;
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      String str2 = "Could not retrieve data calendar for ETL Job " + this.etljob + "\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str2, "Error", 0);

      return null;
    }

    int[] arrayOfInt2 = new int[i];

    for (int k = 0; k < i; ++k) {
      arrayOfInt2[k] = arrayOfInt1[k];
    }

    return arrayOfInt2;
  }

  private int[] getMonthSetFlagDay(int paramInt1, int paramInt2)
  {
    String str2;
    String str1 = this.mainFrame.getDBName();

    int[] arrayOfInt = new int[31];
    int i = 0;

    for (int j = 0; j < 31; ++j)
      arrayOfInt[j] = 0;
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      ResultSet localResultSet = localStatement.executeQuery("SELECT calendarday, checkflag    FROM " + str1 + "datacalendar" + "   WHERE etl_system = '" + this.etlsys + "'" + "     AND etl_job = '" + this.etljob + "'" + "     AND calendaryear = " + paramInt1 + "     AND calendarmonth = " + paramInt2 + "   ORDER BY calendarday");

      while (localResultSet.next())
      {
        int k = localResultSet.getInt(1);
        str2 = localResultSet.getString(2);
        if (localResultSet.wasNull())
          str2 = "N";

        if (str2.equals("Y"))
          arrayOfInt[(k - 1)] = 1;
      }

      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      str2 = "Could not retrieve data calendar for ETL Job " + this.etljob + "\n" + localSQLException.getMessage();

      JOptionPane.showMessageDialog(this, str2, "Error", 0);

      return null;
    }

    return arrayOfInt;
  }

  private void update()
  {
    String str = "Do you want to update this calendar year into database?";
    int i = JOptionPane.showConfirmDialog(this, str, "Update", 0, 3);

    if (i != 0)
      return;

    if (updateDataCalendar(this.etlsys, this.etljob, this.selectYear) != 0)
      return;

    dispose();
  }

  private int updateDataCalendar(String paramString1, String paramString2, int paramInt)
  {
    String str2;
    int i;
    String str1 = this.mainFrame.getDBName();

    Cursor localCursor = getCursor();
    setCursor(new Cursor(3));
    try
    {
      Statement localStatement = this.dbcon.createStatement();
      str2 = "DELETE FROM " + str1 + "DataCalendar" + "   WHERE etl_system = '" + paramString1 + "'" + "     AND etl_job = '" + paramString2 + "'" + "     AND calendaryear = " + paramInt;

      i = localStatement.executeUpdate(str2);
    }
    catch (SQLException localSQLException1)
    {
      setCursor(localCursor);

      JOptionPane.showMessageDialog(this, "Could not remove a data calendar year from database\n" + localSQLException1.getMessage(), "Error", 0);

      return -1;
    }

    try
    {
      str2 = "INSERT INTO " + str1 + "DataCalendar" + "            (etl_system, etl_job, calendaryear," + "             seqNum, calendarmonth, calendarday, checkflag)" + "   VALUES ('" + paramString1 + "', '" + paramString2 + "', " + paramInt + ", ?, ?, ?, ?)";

      PreparedStatement localPreparedStatement = this.dbcon.prepareStatement(str2);

      i = 1;
      for (int j = 1; j <= 12; ++j) {
        int[] arrayOfInt = this.yearSelectPad.getMonthSelectedDay(j);

        for (int k = 0; k < arrayOfInt.length; ++k) {
          localPreparedStatement.setInt(1, i++);
          localPreparedStatement.setInt(2, j);
          localPreparedStatement.setInt(3, arrayOfInt[k]);

          if (this.monthSetFlag[(j - 1)][(arrayOfInt[k] - 1)] == 0)
            localPreparedStatement.setString(4, "N");
          else
            localPreparedStatement.setString(4, "Y");

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

  static ETLEditCalendarDlg access$000(ETLEditCalendarDlg paramETLEditCalendarDlg)
  {
    return paramETLEditCalendarDlg.dlg; } 
  static void access$100(ETLEditCalendarDlg paramETLEditCalendarDlg) { paramETLEditCalendarDlg.update();
  }
}