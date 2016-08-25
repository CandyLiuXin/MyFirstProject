import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class ETLUtilityInterface
{
  public static int getETLSystem(Connection paramConnection, String paramString, JComboBox paramJComboBox)
  {
    Statement localStatement;
    try
    {
      localStatement = paramConnection.createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(null, "Unable to create statement object", "Error", 0);

      return -1;
    }

    int i = 0;
    try {
      ResultSet localResultSet = localStatement.executeQuery("SELECT ETL_System   FROM " + paramString + "ETL_Sys" + "   ORDER BY ETL_System");

      while (localResultSet.next()) {
        String str = localResultSet.getString(1);
        paramJComboBox.addItem(str);
        ++i;
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(null, "Could not retrieve ETL system\n" + localSQLException2.getMessage(), "Error", 0);

      return i;
    }

    return i;
  }

  public static int getETLJob(Connection paramConnection, String paramString1, String paramString2, JComboBox paramJComboBox)
  {
    Statement localStatement;
    try
    {
      localStatement = paramConnection.createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(null, "Unable to create statement object", "Error", 0);

      return -1;
    }

    int i = 0;
    try {
      ResultSet localResultSet = localStatement.executeQuery("SELECT ETL_Job   FROM " + paramString1 + "ETL_Job" + " WHERE ETL_System = '" + paramString2 + "'" + "   ORDER BY ETL_Job");

      while (localResultSet.next()) {
        String str = localResultSet.getString(1);
        paramJComboBox.addItem(str);
        ++i;
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(null, "Could not retrieve ETL job\n" + localSQLException2.getMessage(), "Error", 0);

      return i;
    }

    return i;
  }

  public static int getETLJob(Connection paramConnection, String paramString1, String paramString2, String paramString3, String paramString4, JComboBox paramJComboBox)
  {
    Statement localStatement;
    try
    {
      localStatement = paramConnection.createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(null, "Unable to create statement object", "Error", 0);

      return -1;
    }

    int i = 0;
    try {
      ResultSet localResultSet = localStatement.executeQuery("SELECT ETL_Job   FROM " + paramString1 + "ETL_Job" + " WHERE ETL_System = '" + paramString2 + "'" + "   ORDER BY ETL_Job");

      while (localResultSet.next()) {
        String str = localResultSet.getString(1);
        if ((!(paramString2.equals(paramString3))) || (!(str.equals(paramString4)))) {
          paramJComboBox.addItem(str);
          ++i;
        }
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(null, "Could not retrieve ETL job\n" + localSQLException2.getMessage(), "Error", 0);

      return i;
    }

    return i;
  }

  public static int getDependencyJobDesc(Connection paramConnection, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, JTextField paramJTextField, JCheckBox paramJCheckBox)
  {
    Statement localStatement;
    try
    {
      localStatement = paramConnection.createStatement();
      String str3 = "SELECT Description, Enable FROM " + paramString1 + "ETL_Job_Dependency" + "   WHERE ETL_System = '" + paramString2 + "'" + "     AND ETL_Job = '" + paramString3 + "'" + "     AND Dependency_System = '" + paramString4 + "'" + "     AND Dependency_Job = '" + paramString5 + "'";

      ResultSet localResultSet = localStatement.executeQuery(str3);
      if (localResultSet.next()) {
        String str1 = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str1 = "";

        paramJTextField.setText(str1);

        String str2 = localResultSet.getString(2);
        if (localResultSet.wasNull())
          str2 = "1";

        if (str2.equals("0"))
          paramJCheckBox.setSelected(true);
        else
          paramJCheckBox.setSelected(false);
      }
      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      JOptionPane.showMessageDialog(null, "Could not retrieve descrption\n" + localSQLException.getMessage(), "Error", 0);

      return -1;
    }

    return 0;
  }

  public static int getDownStreamJobDesc(Connection paramConnection, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, JTextField paramJTextField, JCheckBox paramJCheckBox)
  {
    Statement localStatement;
    try
    {
      localStatement = paramConnection.createStatement();
      String str3 = "SELECT Description, Enable FROM " + paramString1 + "ETL_Job_Stream" + "   WHERE ETL_System = '" + paramString2 + "'" + "     AND ETL_Job = '" + paramString3 + "'" + "     AND Stream_System = '" + paramString4 + "'" + "     AND Stream_Job = '" + paramString5 + "'";

      ResultSet localResultSet = localStatement.executeQuery(str3);
      if (localResultSet.next()) {
        String str1 = localResultSet.getString(1);
        if (localResultSet.wasNull())
          str1 = "";

        paramJTextField.setText(str1);

        String str2 = localResultSet.getString(2);
        if (localResultSet.wasNull())
          str2 = "1";

        if (str2.equals("0"))
          paramJCheckBox.setSelected(true);
        else
          paramJCheckBox.setSelected(false);
      }
      localStatement.close();
    }
    catch (SQLException localSQLException)
    {
      JOptionPane.showMessageDialog(null, "Could not retrieve descrption\n" + localSQLException.getMessage(), "Error", 0);

      return -1;
    }
    return 0;
  }

  public static int getNotifyUser(Connection paramConnection, String paramString, JComboBox paramJComboBox)
  {
    Statement localStatement;
    try
    {
      localStatement = paramConnection.createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(null, "Unable to create statement object", "Error", 0);

      return -1;
    }

    int i = 0;
    try {
      ResultSet localResultSet = localStatement.executeQuery("SELECT UserName   FROM " + paramString + "ETL_User" + "   ORDER BY UserName");

      while (localResultSet.next()) {
        String str = localResultSet.getString(1);
        paramJComboBox.addItem(str);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(null, "Could not retrieve user\n" + localSQLException2.getMessage(), "Error", 0);

      return i;
    }

    return i;
  }

  public static int getNotifyUserGroup(Connection paramConnection, String paramString, JComboBox paramJComboBox)
  {
    Statement localStatement;
    try
    {
      localStatement = paramConnection.createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(null, "Unable to create statement object", "Error", 0);

      return -1;
    }

    int i = 0;
    try {
      ResultSet localResultSet = localStatement.executeQuery("SELECT GroupName   FROM " + paramString + "ETL_UserGroup" + "   ORDER BY GroupName");

      while (localResultSet.next()) {
        String str = localResultSet.getString(1);
        paramJComboBox.addItem(str);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(null, "Could not retrieve user\n" + localSQLException2.getMessage(), "Error", 0);

      return i;
    }

    return i;
  }

  public static int getETLServer(Connection paramConnection, String paramString, JComboBox paramJComboBox)
  {
    Statement localStatement;
    try
    {
      localStatement = paramConnection.createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(null, "Unable to create statement object", "Error", 0);

      return -1;
    }

    paramJComboBox.addItem("");

    int i = 0;
    try {
      ResultSet localResultSet = localStatement.executeQuery("SELECT ETL_Server   FROM " + paramString + "ETL_Server" + "   ORDER BY ETL_Server");

      while (localResultSet.next()) {
        String str = localResultSet.getString(1);
        paramJComboBox.addItem(str);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(null, "Could not retrieve user\n" + localSQLException2.getMessage(), "Error", 0);

      return i;
    }

    return i;
  }

  public static int getETLBu(Connection paramConnection, String paramString, JComboBox paramJComboBox)
  {
    Statement localStatement;
    try
    {
      localStatement = paramConnection.createStatement();
    }
    catch (SQLException localSQLException1)
    {
      JOptionPane.showMessageDialog(null, "Unable to create statement object", "Error", 0);

      return -1;
    }

    int i = 0;
    try {
      ResultSet localResultSet = localStatement.executeQuery("select calendarbu from " + paramString + "etl_job where calendarbu  is not NULL and calendarbu <>''  GROUP BY 1 order by  1");

      while (localResultSet.next()) {
        String str = localResultSet.getString(1);
        paramJComboBox.addItem(str);
      }

      localStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      JOptionPane.showMessageDialog(null, "Could not retrieve BU\n" + localSQLException2.getMessage(), "Error", 0);

      return i;
    }

    return i;
  }
}