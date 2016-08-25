import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
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
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class ETLConnectionDlg extends JDialog
{
  private JPanel jPanelButton;
  private JPanel jPanelData;
  private JButton jBnConnect;
  private JButton jBnCancel;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JLabel jLabel3;
  private JLabel jLabel4;
  private JTextField jTextField1;
  private JTextField jTextField2;
  private JTextField jTextField4;
  private JPasswordField jPasswordField3;
  private ETLConnectionDlg dlg;
  private ETLMainFrame mainFrame;

  public ETLConnectionDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "Connect to DB", true);
    setResizable(false);
    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLConnectionDlg.1 local1 = new WindowAdapter(this) { private final ETLConnectionDlg this$0;

      public void windowClosing() { this.this$0.dispose();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(new BorderLayout());

    this.jPanelButton = new JPanel();
    this.jPanelData = new JPanel();
    this.jPanelButton.setLayout(new FlowLayout());
    this.jPanelData.setLayout(null);

    this.jBnConnect = new JButton("Connect");
    this.jBnConnect.setMnemonic(67);
    this.jBnCancel = new JButton("Cancel");
    this.jBnCancel.setMnemonic(69);

    this.jBnConnect.addActionListener(new ActionListener(this) { private final ETLConnectionDlg this$0;

      public void actionPerformed() { ETLConnectionDlg.access$100(ETLConnectionDlg.access$000(this.this$0));
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLConnectionDlg this$0;

      public void actionPerformed() { ETLConnectionDlg.access$000(this.this$0).dispose();
      }

    });
    this.jPanelButton.add(this.jBnConnect);
    this.jPanelButton.add(this.jBnCancel);

    localContainer.add(this.jPanelData, "Center");
    localContainer.add(this.jPanelButton, "South");

    this.jLabel1 = new JLabel("Data Source");
    this.jLabel1.setBounds(new Rectangle(23, 20, 90, 25));

    this.jLabel2 = new JLabel("User");
    this.jLabel2.setBounds(new Rectangle(23, 50, 90, 25));

    this.jLabel3 = new JLabel("Password");
    this.jLabel3.setBounds(new Rectangle(23, 80, 90, 25));

    this.jLabel4 = new JLabel("ETL Database");
    this.jLabel4.setBounds(new Rectangle(23, 110, 90, 25));

    this.jTextField1 = new JTextField();
    this.jTextField1.setBounds(new Rectangle(125, 20, 110, 25));

    this.jTextField2 = new JTextField();
    this.jTextField2.setBounds(new Rectangle(125, 50, 110, 25));

    this.jPasswordField3 = new JPasswordField();
    this.jPasswordField3.setBounds(new Rectangle(125, 80, 110, 25));

    this.jTextField4 = new JTextField();
    this.jTextField4.setBounds(new Rectangle(125, 110, 110, 25));

    this.jPanelData.add(this.jLabel1);
    this.jPanelData.add(this.jLabel2);
    this.jPanelData.add(this.jLabel3);
    this.jPanelData.add(this.jLabel4);

    this.jPanelData.add(this.jTextField1);
    this.jPanelData.add(this.jTextField2);
    this.jPanelData.add(this.jPasswordField3);
    this.jPanelData.add(this.jTextField4);

    setSize(new Dimension(260, 210));

    Dimension localDimension1 = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension localDimension2 = getSize();

    if (localDimension2.height > localDimension1.height)
      localDimension2.height = localDimension1.height;

    if (localDimension2.width > localDimension1.width)
      localDimension2.width = localDimension1.width;

    setLocation((localDimension1.width - localDimension2.width) / 2, (localDimension1.height - localDimension2.height) / 2);
  }

  private void connect()
  {
    Connection localConnection;
    String str1 = "jdbc:odbc:" + this.jTextField1.getText().trim();
    String str2 = this.jTextField2.getText().trim();
    char[] arrayOfChar = this.jPasswordField3.getPassword();
    String str3 = String.valueOf(arrayOfChar);
    String str4 = this.jTextField4.getText().trim();

    Cursor localCursor = getCursor();
    setCursor(new Cursor(3));
    try
    {
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    }
    catch (ClassNotFoundException localClassNotFoundException) {
      setCursor(localCursor);

      JOptionPane.showMessageDialog(this, "Could not load the jdbc:odbc driver!", "Error", 0);

      return;
    }
    try
    {
      localConnection = DriverManager.getConnection(str1, str2, str3);
    }
    catch (SQLException localSQLException) {
      setCursor(localCursor);

      JOptionPane.showMessageDialog(this, "Could not connect to database!", "Error", 0);

      return;
    }

    this.mainFrame.setConnection(localConnection);

    this.mainFrame.setDBName(str4);

    dispose();
  }

  public void setDBName(String paramString)
  {
    this.jTextField4.setText(paramString);
  }

  static ETLConnectionDlg access$000(ETLConnectionDlg paramETLConnectionDlg)
  {
    return paramETLConnectionDlg.dlg; } 
  static void access$100(ETLConnectionDlg paramETLConnectionDlg) { paramETLConnectionDlg.connect();
  }
}