import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;

public class ETLFilterDlg extends JDialog
{
  private JPanel jPanelButton;
  private JPanel jPanelData;
  private JButton jBnUpdate;
  private JButton jBnCancel;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private InputJTextField jTextField1;
  private InputJTextField jTextField2;
  private ETLFilterDlg dlg;
  private JComboBox jcb_BU;
  private DefaultMutableTreeNode parentNode;
  private Connection dbcon;
  private int mode = 0;
  private ETLMainFrame mainFrame;

  public ETLFilterDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "Filter...", true);
    setResizable(false);
    this.dlg = this;
    this.mainFrame = ((ETLMainFrame)paramJFrame);
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLFilterDlg.1 local1 = new WindowAdapter(this) { private final ETLFilterDlg this$0;

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

    this.jBnUpdate = new JButton("Update");
    this.jBnUpdate.setMnemonic(85);
    this.jBnCancel = new JButton("Cancel");
    this.jBnCancel.setMnemonic(67);

    this.jBnUpdate.addActionListener(new ActionListener(this) { private final ETLFilterDlg this$0;

      public void actionPerformed() { ETLFilterDlg.access$100(ETLFilterDlg.access$000(this.this$0));
      }

    });
    this.jBnCancel.addActionListener(new ActionListener(this) { private final ETLFilterDlg this$0;

      public void actionPerformed() { ETLFilterDlg.access$000(this.this$0).dispose();
      }

    });
    this.jPanelButton.add(this.jBnUpdate);
    this.jPanelButton.add(this.jBnCancel);

    localContainer.add(this.jPanelData, "Center");
    localContainer.add(this.jPanelButton, "South");

    this.jLabel1 = new JLabel("Filter by BU:");
    this.jLabel1.setBounds(new Rectangle(20, 20, 85, 25));

    this.jLabel2 = new JLabel("And Filter like Job Name:");
    this.jLabel2.setBounds(new Rectangle(15, 50, 100, 25));

    this.jcb_BU = new JComboBox();
    this.jcb_BU.setBounds(new Rectangle(115, 20, 150, 25));

    this.jTextField2 = new InputJTextField();
    this.jTextField2.setBounds(new Rectangle(115, 50, 250, 25));
    this.jTextField2.setInputLimited(50);

    this.jPanelData.add(this.jLabel1);
    this.jPanelData.add(this.jLabel2);

    this.jPanelData.add(this.jcb_BU);
    this.jPanelData.add(this.jTextField2);

    setSize(new Dimension(400, 200));

    Dimension localDimension1 = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension localDimension2 = getSize();

    if (localDimension2.height > localDimension1.height)
      localDimension2.height = localDimension1.height;

    if (localDimension2.width > localDimension1.width)
      localDimension2.width = localDimension1.width;

    setLocation((localDimension1.width - localDimension2.width) / 2, (localDimension1.height - localDimension2.height) / 2);
  }

  public void add(Connection paramConnection)
  {
    this.dbcon = paramConnection;
    this.mode = 1;
    this.jcb_BU.addItem("*ALL");
    String str = this.mainFrame.getDBName();
    ETLUtilityInterface.getETLBu(paramConnection, str, this.jcb_BU);
    show();
  }

  private void update()
  {
    insertData();
  }

  private void insertData()
  {
    String str1 = this.jTextField2.getText().trim();
    String str2 = (String)this.jcb_BU.getSelectedItem();
    String str3 = "ETL Automation Administration, NCR";
    this.mainFrame.setJobName(str1);
    this.mainFrame.setBUName(str2);

    this.mainFrame.getTreeView().setTreeRoot(0);
    this.mainFrame.refresh();
    this.mainFrame.getTreeView().setTreeRoot(1);
    this.mainFrame.refresh();
    if (str1.equals("")) {
      if (str2.equals("*ALL")) {
        this.mainFrame.setTitle(str3);
      }
      else
        this.mainFrame.setTitle(str3 + " [Filter by BU:" + str2 + "]");

    }
    else if (str2.equals("*ALL")) {
      this.mainFrame.setTitle(str3 + " [Filter by Job Name like '%" + str1 + "%']");
    }
    else {
      this.mainFrame.setTitle(str3 + " [Filter by BU:" + str2 + " and Job Name like '%" + str1 + "%']");
    }

    dispose();
  }

  static ETLFilterDlg access$000(ETLFilterDlg paramETLFilterDlg)
  {
    return paramETLFilterDlg.dlg; } 
  static void access$100(ETLFilterDlg paramETLFilterDlg) { paramETLFilterDlg.update();
  }
}