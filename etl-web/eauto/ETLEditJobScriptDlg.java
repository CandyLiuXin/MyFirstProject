import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ETLEditJobScriptDlg extends JDialog
{
  private JTextArea textScript;
  private JPanel jPanelLog;
  private JPanel jPanelButton;
  private JButton jBnUpdate;
  private JButton jBnClose;
  private JScrollPane jScrollPane;
  private ETLMainFrame mainFrame;
  private ETLEditJobScriptDlg dlg;
  private String Server;
  private int AgentPort;
  private String ETLSys;
  private String ETLJob;
  private String ScriptFile;

  public ETLEditJobScriptDlg(JFrame paramJFrame)
  {
    super(paramJFrame, "Edit Job Script File", false);
    this.mainFrame = ((ETLMainFrame)paramJFrame);
    this.dlg = this;
  }

  public void setScriptFileName(String paramString)
  {
    this.ScriptFile = paramString;
    String str = "Edit Job Script File - " + paramString;
    setTitle(str);
  }

  public void setServerInfo(String paramString, int paramInt)
  {
    this.Server = paramString;
    this.AgentPort = paramInt;
  }

  public void setJobInfo(String paramString1, String paramString2)
  {
    this.ETLSys = paramString1;
    this.ETLJob = paramString2;
  }

  protected void dialogInit()
  {
    super.dialogInit();

    ETLEditJobScriptDlg.1 local1 = new WindowAdapter(this) { private final ETLEditJobScriptDlg this$0;

      public void windowClosing() { this.this$0.dispose();
      }

    };
    addWindowListener(local1);

    Container localContainer = super.getContentPane();
    localContainer.setLayout(new BorderLayout());

    this.jPanelButton = new JPanel(new FlowLayout());
    this.jPanelLog = new JPanel(new BorderLayout());

    this.jBnUpdate = new JButton("Update Script");
    this.jBnUpdate.setMnemonic(85);
    this.jBnClose = new JButton("Close");
    this.jBnClose.setMnemonic(67);

    this.jBnUpdate.addActionListener(new ActionListener(this) { private final ETLEditJobScriptDlg this$0;

      public void actionPerformed() { ETLEditJobScriptDlg.access$100(ETLEditJobScriptDlg.access$000(this.this$0));
      }

    });
    this.jBnClose.addActionListener(new ActionListener(this) { private final ETLEditJobScriptDlg this$0;

      public void actionPerformed() { ETLEditJobScriptDlg.access$000(this.this$0).dispose();
      }

    });
    this.jPanelButton.add(this.jBnUpdate);
    this.jPanelButton.add(this.jBnClose);

    this.textScript = new JTextArea();
    this.textScript.setFont(new Font("Courier New", 0, 14));

    this.jScrollPane = new JScrollPane();
    this.jScrollPane.setViewportView(this.textScript);

    this.jPanelLog.add(this.jScrollPane, "Center");
    this.jPanelLog.setPreferredSize(new Dimension(760, 560));

    localContainer.add(this.jPanelLog, "Center");
    localContainer.add(this.jPanelButton, "South");

    pack();

    Dimension localDimension1 = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension localDimension2 = getSize();

    if (localDimension2.height > localDimension1.height)
      localDimension2.height = localDimension1.height;

    if (localDimension2.width > localDimension1.width)
      localDimension2.width = localDimension1.width;

    setLocation((localDimension1.width - localDimension2.width) / 2, (localDimension1.height - localDimension2.height) / 2);
  }

  public JTextArea getTextArea()
  {
    return this.textScript;
  }

  private void update()
  {
    int i = JOptionPane.showConfirmDialog(this, "Do you really want to update the job script to serevr?", "Update Job Script", 0, 3);

    if (i != 0)
      return;

    ETLAgentInterface localETLAgentInterface = new ETLAgentInterface();

    if (localETLAgentInterface.setServerInfo(this.Server, this.AgentPort) == 0) {
      String str1;
      if (localETLAgentInterface.connectToAgent() != 0) {
        str1 = "Unable to connect ETL Agent";

        JOptionPane.showMessageDialog(this, str1, "Error", 0);

        return;
      }

      if (localETLAgentInterface.readHeaderInfo() != 0) {
        localETLAgentInterface.disconnectAgent();

        str1 = "Read header information error\n";

        JOptionPane.showMessageDialog(this, str1, "Error", 0);

        return;
      }

      int j = this.textScript.getLineCount();

      int k = localETLAgentInterface.sendCmdPutScr(this.ETLSys, this.ETLJob, this.ScriptFile, j);

      if (k != 0) {
        localETLAgentInterface.disconnectAgent();

        String str2 = "Unable to put the job script file\n";
        JOptionPane.showMessageDialog(this, str2, "Error", 0);

        return;
      }

      localETLAgentInterface.putScriptFile(this.textScript);

      localETLAgentInterface.disconnectAgent();
    }
  }

  static ETLEditJobScriptDlg access$000(ETLEditJobScriptDlg paramETLEditJobScriptDlg)
  {
    return paramETLEditJobScriptDlg.dlg; } 
  static void access$100(ETLEditJobScriptDlg paramETLEditJobScriptDlg) { paramETLEditJobScriptDlg.update();
  }
}